package com.example.jingbin.cloudreader.ui;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.text.Html;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import android.webkit.WebView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;

import com.example.jingbin.cloudreader.R;
import com.example.jingbin.cloudreader.app.Constants;
import com.example.jingbin.cloudreader.data.UserUtil;
import com.example.jingbin.cloudreader.data.model.CollectModel;
import com.example.jingbin.cloudreader.utils.BaseTools;
import com.example.jingbin.cloudreader.utils.CommonUtils;
import com.example.jingbin.cloudreader.utils.DebugUtil;
import com.example.jingbin.cloudreader.utils.DialogBuild;
import com.example.jingbin.cloudreader.utils.PermissionHandler;
import com.example.jingbin.cloudreader.utils.RxSaveImage;
import com.example.jingbin.cloudreader.utils.SPUtils;
import com.example.jingbin.cloudreader.utils.ShareUtils;
import com.example.jingbin.cloudreader.utils.ToastUtil;
import com.example.jingbin.cloudreader.utils.WebUtil;
import com.example.jingbin.cloudreader.view.viewbigimage.ViewBigImageActivity;
import com.example.jingbin.cloudreader.viewmodel.wan.WanNavigator;

import me.jingbin.bymvvm.utils.StatusBarUtil;
import me.jingbin.web.ByWebView;
import me.jingbin.web.OnByWebClientCallback;
import me.jingbin.web.OnTitleProgressCallback;


/**
 * 网页可以处理:
 * 点击相应控件:拨打电话、发送短信、发送邮件、上传图片、播放视频
 * 进度条、返回网页上一层、显示网页标题
 * Thanks to: <a href="https://github.com/youlookwhat/ByWebView"/>
 * contact me: http://www.jianshu.com/u/e43c6e979831
 */
public class WebViewActivity extends AppCompatActivity {

    // title
    private String mTitle;
    // 网页链接
    private String mUrl;
    // 可滚动的title 使用简单 没有渐变效果，文字两旁有阴影
    private TextView tvGunTitle;
    private boolean isTitleFix;
    private CollectModel collectModel;
    private ByWebView byWebView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);
        StatusBarUtil.setColor(this, CommonUtils.getColor(R.color.colorTheme), 0);
        getIntentData();
        initTitle();
        syncCookie(mUrl);
        getDataFromBrowser(getIntent());
    }

    private void getIntentData() {
        if (getIntent() != null) {
            mTitle = getIntent().getStringExtra("title");
            mUrl = getIntent().getStringExtra("url");
            isTitleFix = getIntent().getBooleanExtra("isTitleFix", false);
        }
    }

    private void initTitle() {
        LinearLayout llWebView = findViewById(R.id.ll_webview);
        Toolbar mTitleToolBar = findViewById(R.id.title_tool_bar);
        tvGunTitle = findViewById(R.id.tv_gun_title);

        setSupportActionBar(mTitleToolBar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            //去除默认Title显示
            actionBar.setDisplayShowTitleEnabled(false);
        }
        mTitleToolBar.setOverflowIcon(ContextCompat.getDrawable(this, R.drawable.actionbar_more));
        tvGunTitle.postDelayed(() -> tvGunTitle.setSelected(true), 1900);
        tvGunTitle.setText(mTitle == null ? "加载中..." : Html.fromHtml(mTitle));

        byWebView = ByWebView.with(this)
                .setWebParent(llWebView, new LinearLayout.LayoutParams(-1, -1))
                .useWebProgress(CommonUtils.getColor(R.color.colorRateRed))
                .setOnByWebClientCallback(onByWebClientCallback)
                .setOnTitleProgressCallback(new OnTitleProgressCallback() {
                    @Override
                    public void onReceivedTitle(String title) {
                        setTitle(title);
                    }
                })
                .loadUrl(mUrl);
        byWebView.getWebView().setOnLongClickListener(v -> handleLongImage());
    }

    private OnByWebClientCallback onByWebClientCallback = new OnByWebClientCallback() {
        @Override
        public boolean isOpenThirdApp(String url) {
            // 单独处理简书等App的唤起
            return WebUtil.handleThirdApp(WebViewActivity.this, url);
        }
    };

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.webview_menu, menu);
        return true;
    }

    public void setTitle(String mTitle) {
        if (!isTitleFix) {
            tvGunTitle.setText(mTitle);
            this.mTitle = mTitle;
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                // 返回键
                handleFinish();
                break;
            case R.id.actionbar_share:
                // 分享到
                String shareText = mTitle + " " + byWebView.getWebView().getUrl() + " ( 分享自云阅 " + Constants.DOWNLOAD_URL + " )";
                ShareUtils.share(WebViewActivity.this, shareText);
                break;
            case R.id.actionbar_cope:
                // 复制链接
                BaseTools.copy(byWebView.getWebView().getUrl());
                break;
            case R.id.actionbar_open:
                // 打开链接
                BaseTools.openLink(WebViewActivity.this, byWebView.getWebView().getUrl());
                break;
            case R.id.actionbar_webview_refresh:
                // 刷新页面
                byWebView.reload();
                break;
            case R.id.actionbar_collect:
                // 添加到收藏
                if (UserUtil.isLogin(byWebView.getWebView().getContext())) {
                    if (SPUtils.getBoolean(Constants.IS_FIRST_COLLECTURL, true)) {
                        DialogBuild.show(byWebView.getWebView(), "网址不同于文章，相同网址可多次进行收藏，且不会显示收藏状态。", "知道了", (DialogInterface.OnClickListener) (dialog, which) -> {
                            SPUtils.putBoolean(Constants.IS_FIRST_COLLECTURL, false);
                            collectUrl();
                        });
                    } else {
                        collectUrl();
                    }
                }
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * 收藏
     */
    private void collectUrl() {
        if (collectModel == null) {
            collectModel = new CollectModel();
        }
        collectModel.collectUrl(mTitle, byWebView.getWebView().getUrl(), new WanNavigator.OnCollectNavigator() {
            @Override
            public void onSuccess() {
                ToastUtil.showToastLong("已添加到收藏");
            }

            @Override
            public void onFailure() {
                ToastUtil.showToastLong("收藏网址失败");
            }
        });
    }

    /**
     * 同步cookie
     */
    private void syncCookie(String url) {
        if (!TextUtils.isEmpty(url) && url.contains("wanandroid")) {
            try {
                CookieSyncManager.createInstance(this);
                CookieManager cookieManager = CookieManager.getInstance();
                cookieManager.setAcceptCookie(true);
                cookieManager.removeSessionCookie();// 移除
                cookieManager.removeAllCookie();
                String cookie = SPUtils.getString("cookie", "");
                if (!TextUtils.isEmpty(cookie)) {
                    String[] split = cookie.split(";");
                    for (int i = 0; i < split.length; i++) {
                        cookieManager.setCookie(url, split[i]);
                    }
                }
//            String cookies = cookieManager.getCookie(url);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    cookieManager.flush();
                } else {
                    CookieSyncManager.getInstance().sync();
                }
            } catch (Exception e) {
                DebugUtil.error("==异常==", e.toString());
            }
        }
    }

    /**
     * 上传图片之后的回调
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        byWebView.handleFileChooser(requestCode, resultCode, intent);
    }

    /**
     * 使用singleTask启动模式的Activity在系统中只会存在一个实例。
     * 如果这个实例已经存在，intent就会通过onNewIntent传递到这个Activity。
     * 否则新的Activity实例被创建。
     */
    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        getDataFromBrowser(intent);
    }

    /**
     * 作为三方浏览器打开
     * Scheme: https
     * host: www.jianshu.com
     * path: /p/1cbaf784c29c
     * url = scheme + "://" + host + path;
     */
    private void getDataFromBrowser(Intent intent) {
        Uri data = intent.getData();
        if (data != null) {
            try {
                String scheme = data.getScheme();
                String host = data.getHost();
                String path = data.getPath();
//                String text = "Scheme: " + scheme + "\n" + "host: " + host + "\n" + "path: " + path;
//                Log.e("data", text);
                String url = scheme + "://" + host + path;
                byWebView.loadUrl(url);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 长按图片事件处理
     */
    private boolean handleLongImage() {
        final WebView.HitTestResult hitTestResult = byWebView.getWebView().getHitTestResult();
        // 如果是图片类型或者是带有图片链接的类型
        if (hitTestResult.getType() == WebView.HitTestResult.IMAGE_TYPE ||
                hitTestResult.getType() == WebView.HitTestResult.SRC_IMAGE_ANCHOR_TYPE) {
            // 弹出保存图片的对话框
            new AlertDialog.Builder(WebViewActivity.this)
                    .setItems(new String[]{"查看大图", "发送给朋友", "保存到相册"}, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            //获取图片
                            String picUrl = hitTestResult.getExtra();
                            switch (which) {
                                case 0:
                                    ViewBigImageActivity.start(WebViewActivity.this, picUrl, picUrl);
                                    break;
                                case 1:
                                    if (!PermissionHandler.isHandlePermission(WebViewActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                                        return;
                                    }
                                    ShareUtils.shareNetImage(WebViewActivity.this, picUrl);
                                    break;
                                case 2:
                                    if (!PermissionHandler.isHandlePermission(WebViewActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                                        return;
                                    }
                                    RxSaveImage.saveImageToGallery(WebViewActivity.this, picUrl, picUrl);
                                    break;
                                default:
                                    break;
                            }
                        }
                    })
                    .show();
            return true;
        }
        return false;
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (byWebView.handleKeyEvent(keyCode, event)) {
            return true;
        } else {
            if (keyCode == KeyEvent.KEYCODE_BACK) {
                handleFinish();
            }
            return super.onKeyDown(keyCode, event);
        }
    }

    /**
     * 直接通过三方浏览器打开时，回退到首页
     */
    public void handleFinish() {
        supportFinishAfterTransition();
        if (!MainActivity.isLaunch) {
            MainActivity.start(this);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        byWebView.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        byWebView.onResume();
    }

    @Override
    protected void onDestroy() {
        byWebView.onDestroy();
        tvGunTitle.clearAnimation();
        tvGunTitle.clearFocus();
        if (collectModel != null) {
            collectModel = null;
        }
        super.onDestroy();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if (newConfig.fontScale != 1) {
            getResources();
        }
    }

    /**
     * 禁止改变字体大小
     */
    @Override
    public Resources getResources() {
        Resources res = super.getResources();
        Configuration config = new Configuration();
        config.setToDefaults();
        res.updateConfiguration(config, res.getDisplayMetrics());
        return res;
    }

    /**
     * 打开网页:
     *
     * @param context 上下文
     * @param url     要加载的网页url
     * @param title   title
     */
    public static void loadUrl(Context context, String url, String title) {
        loadUrl(context, url, title, false);
    }

    /**
     * 打开网页:
     *
     * @param context      上下文
     * @param url          要加载的网页url
     * @param title        title
     * @param isTitleFixed title是否固定
     */
    public static void loadUrl(Context context, String url, String title, boolean isTitleFixed) {
        if (!TextUtils.isEmpty(url)) {
            Intent intent = new Intent(context, WebViewActivity.class);
            intent.putExtra("url", url);
            intent.putExtra("isTitleFix", isTitleFixed);
            intent.putExtra("title", TextUtils.isEmpty(title) ? url : title);
            context.startActivity(intent);
        }
    }

}
