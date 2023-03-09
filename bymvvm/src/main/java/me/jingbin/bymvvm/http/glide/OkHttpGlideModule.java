package me.jingbin.bymvvm.http.glide;

import android.content.Context;

import androidx.annotation.NonNull;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Registry;
import com.bumptech.glide.annotation.GlideModule;
import com.bumptech.glide.load.model.GlideUrl;
import com.bumptech.glide.module.AppGlideModule;

import java.io.InputStream;

import okhttp3.Call;
import okhttp3.OkHttpClient;

/**
 * Created by jingbin on 10/19/21.
 * 直接将Glide.with替换为GlideApp.with即可
 */
@GlideModule
public class OkHttpGlideModule extends AppGlideModule {

    @Override
    public void registerComponents(@NonNull Context context, @NonNull Glide glide, @NonNull Registry registry) {
        // 忽略证书
        OkHttpClient mHttpClient = new OkHttpClient().newBuilder()
                .sslSocketFactory(SSLSocketClient.getSSLSocketFactory(),SSLSocketClient.X509TrustManager())
                .hostnameVerifier(SSLSocketClient.getHostnameVerifier())
                .build();
        registry.replace(GlideUrl.class, InputStream.class, new OkHttpUrlLoader.Factory((Call.Factory) mHttpClient));
    }
}

