package com.example.jingbin.cloudreader.http;

import android.content.Context;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.FieldNamingStrategy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.jakewharton.retrofit.Ok3Client;

import java.io.File;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import okhttp3.Cache;
import okhttp3.OkHttpClient;
import retrofit.RestAdapter;
import retrofit.client.Client;
import retrofit.converter.GsonConverter;

/**
 * Created by jingbin on 2015/6/24.
 * 网络请求工具类
 * <p>
 * 豆瓣api:
 * 问题：API限制为每分钟40次，一不小心就超了，马上KEY就被封,用不带KEY的API，每分钟只有可怜的10次。
 * 返回：code:112（rate_limit_exceeded2 IP 访问速度限制）
 * 解决：1.使用每分钟访问次数限制（客户端）2.更换ip (更换wifi)
 * 豆瓣开发者服务使用条款: https://developers.douban.com/wiki/?title=terms
 */
public class HttpUtils {

    private static final RestAdapter.LogLevel logLevel = RestAdapter.LogLevel.FULL;
    // gankio、豆瓣、动听（轮播图）
    private final static String API_GANKIO = "http://gank.io/api";
    private final static String API_DOUBAN = "https://api.douban.com";
    private final static String API_DONGTING = "http://api.dongting.com";

    private RestAdapter gankIoRestAdapter;
    private RestAdapter douBanRestAdapter;
    private RestAdapter dongTingRestAdapter;
    private Gson gson;
    private Context context;
    private static HttpUtils sHttpUtils;
    private static RetrofitHttpClient sGankioClient;
    private static RetrofitHttpClient sDouBanClient;
    private static RetrofitHttpClient sDongTingClient;
    /**
     * 分页数据，每页的数量
     */
    public static int per_page = 10;
    public static int per_page_more = 20;


    public void setContext(Context context) {
        this.context = context;
    }

    public static HttpUtils getInstance() {
        if (sHttpUtils == null) {
            sHttpUtils = new HttpUtils();
        }
        return sHttpUtils;
    }

    public RetrofitHttpClient getGankIOServer() {
        if (sGankioClient == null) {
            sGankioClient = getGankIOAdapter().create(RetrofitHttpClient.class);
        }
        return sGankioClient;
    }

    public RetrofitHttpClient getDouBanServer() {
        if (sDouBanClient == null) {
            sDouBanClient = getDouBanAdapter().create(RetrofitHttpClient.class);
        }
        return sDouBanClient;
    }

    public RetrofitHttpClient getDongTingServer() {
        if (sDongTingClient == null) {
            sDongTingClient = getDongTingAdapter().create(RetrofitHttpClient.class);
        }
        return sDongTingClient;
    }

    private RestAdapter getGankIOAdapter() {
        if (gankIoRestAdapter == null) {
            File cacheFile = new File(context.getApplicationContext().getCacheDir().getAbsolutePath(), "HttpCache");
            int cacheSize = 10 * 1024 * 1024;
            Cache cache = new Cache(cacheFile, cacheSize);
            OkHttpClient.Builder okBuilder = new OkHttpClient.Builder();
            okBuilder.cache(cache);
            okBuilder.readTimeout(20, TimeUnit.SECONDS);
            okBuilder.connectTimeout(10, TimeUnit.SECONDS);
            okBuilder.writeTimeout(20, TimeUnit.SECONDS);
            OkHttpClient client = okBuilder.build();

            RestAdapter.Builder builder = new RestAdapter.Builder();
            builder.setClient(new Ok3Client(client));
            builder.setEndpoint(API_GANKIO);//设置远程地址
            builder.setConverter(new GsonConverter(getGson()));
//            builder.setErrorHandler(new ErrorHandler() {
//                @Override
//                public Throwable handleError(RetrofitError retrofitError) {
//                    return RxThrowable.ResolveRetrofitError(context, retrofitError);
//                }
//            });
//            builder.setRequestInterceptor(new RequestInterceptor() {
//                @Override
//                public void intercept(RequestFacade requestFacade) {
//                    requestFacade.addHeader("Accept", "application/json;versions=1");
//                    UserUtils userUtils = new UserUtils(context, new SQuser(context).selectKey());
//                    String token = userUtils.getToken();
//                    requestFacade.addHeader("token", token);
//
//                    if (CheckNetwork.isNetworkConnected(context)) {
//                        int maxAge = 60;
//                        requestFacade.addHeader("Cache-Control", "public, max-age=" + maxAge);
//                    } else {
//                        int maxStale = 60 * 60 * 24 * 28;
//                        requestFacade.addHeader("Cache-Control", "public, only-if-cached, " +
//                                "max-stale=" + maxStale);
//                    }
//                }
//            });
            gankIoRestAdapter = builder.build();
            gankIoRestAdapter.setLogLevel(logLevel);
        }
        return gankIoRestAdapter;
    }

    private RestAdapter getDouBanAdapter() {
        if (douBanRestAdapter == null) {
            File cacheFile = new File(context.getApplicationContext().getCacheDir().getAbsolutePath(), "HttpCache");
            int cacheSize = 10 * 1024 * 1024;
            Cache cache = new Cache(cacheFile, cacheSize);
//            httpGet.setHeader(
//                    "User-Agent",
//                    "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/39.0.2171.71 Safari/537.36");

            OkHttpClient.Builder okBuilder = new OkHttpClient.Builder();
            okBuilder.cache(cache);
            okBuilder.readTimeout(20, TimeUnit.SECONDS);
            okBuilder.connectTimeout(10, TimeUnit.SECONDS);
            okBuilder.writeTimeout(20, TimeUnit.SECONDS);
            OkHttpClient client = okBuilder.build();

            RestAdapter.Builder builder = new RestAdapter.Builder();
            builder.setClient(new Ok3Client(client));
            builder.setEndpoint(API_DOUBAN);//设置远程地址
            builder.setConverter(new GsonConverter(getGson()));
            douBanRestAdapter = builder.build();
            douBanRestAdapter.setLogLevel(logLevel);
        }
        return douBanRestAdapter;
    }

    private RestAdapter getDongTingAdapter() {
        if (dongTingRestAdapter == null) {
            File cacheFile = new File(context.getApplicationContext().getCacheDir().getAbsolutePath(), "HttpCache");
            int cacheSize = 10 * 1024 * 1024;
            Cache cache = new Cache(cacheFile, cacheSize);
            OkHttpClient.Builder okBuilder = new OkHttpClient.Builder();
            okBuilder.cache(cache);
            okBuilder.readTimeout(20, TimeUnit.SECONDS);
            okBuilder.connectTimeout(10, TimeUnit.SECONDS);
            okBuilder.writeTimeout(20, TimeUnit.SECONDS);
            OkHttpClient client = okBuilder.build();

            RestAdapter.Builder builder = new RestAdapter.Builder();
            builder.setClient(new Ok3Client(client));
            builder.setEndpoint(API_DONGTING);//设置远程地址
            builder.setConverter(new GsonConverter(getGson()));
            dongTingRestAdapter = builder.build();
            dongTingRestAdapter.setLogLevel(logLevel);
        }
        return dongTingRestAdapter;
    }

    private Gson getGson() {
        if (gson == null) {
            GsonBuilder builder = new GsonBuilder();
            builder.setFieldNamingStrategy(new AnnotateNaming());
            builder.serializeNulls();
            builder.excludeFieldsWithModifiers(Modifier.TRANSIENT);
            gson = builder.create();
        }
        return gson;
    }

    private static class AnnotateNaming implements FieldNamingStrategy {
        @Override
        public String translateName(Field field) {
            ParamNames a = field.getAnnotation(ParamNames.class);
            return a != null ? a.value() : FieldNamingPolicy.IDENTITY.translateName(field);
        }
    }


    public OkHttpClient getUnsafeOkHttpClient() {

        final TrustManager[] trustAllCerts = new TrustManager[]{new X509TrustManager() {
            @Override
            public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {
            }

            @Override
            public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {
            }

            @Override
            public X509Certificate[] getAcceptedIssuers() {
                return new X509Certificate[0];
            }
        }};
        // Install the all-trusting trust manager
        SSLContext sslContext = null;
        try {
            sslContext = SSLContext.getInstance("TLS");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        try {
            sslContext.init(null, trustAllCerts, new SecureRandom());
        } catch (KeyManagementException e) {
            e.printStackTrace();
        }
        // Create an ssl socket factory with our all-trusting manager
        SSLSocketFactory sslSocketFactory = sslContext.getSocketFactory();
        OkHttpClient.Builder okBuilder = new OkHttpClient.Builder();
        okBuilder.readTimeout(20, TimeUnit.SECONDS);
        okBuilder.connectTimeout(10, TimeUnit.SECONDS);
        okBuilder.writeTimeout(20, TimeUnit.SECONDS);
        okBuilder.sslSocketFactory(sslSocketFactory);
        okBuilder.hostnameVerifier(new HostnameVerifier() {
            @Override
            public boolean verify(String hostname, SSLSession session) {
                return true;
            }
        });
        OkHttpClient client = okBuilder.build();
        return client;

    }

    public Client getOkClient() {
        OkHttpClient client1;
        client1 = getUnsafeOkHttpClient();
        Client _client = new Ok3Client(client1);
        return _client;
    }
}
