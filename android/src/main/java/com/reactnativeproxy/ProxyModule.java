package com.reactnativeproxy;

import androidx.annotation.NonNull;

import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.module.annotations.ReactModule;

import com.facebook.react.modules.network.OkHttpClientProvider;
import com.facebook.react.modules.network.ReactCookieJarContainer;
import java.net.Proxy;
import okhttp3.OkHttpClient;

import java.util.concurrent.TimeUnit;

@ReactModule(name = ProxyModule.NAME)
public class ProxyModule extends ReactContextBaseJavaModule {
    public static final String NAME = "Proxy";

    public ProxyModule(ReactApplicationContext reactContext) {
        super(reactContext);
    }

    @Override
    @NonNull
    public String getName() {
        return NAME;
    }

    @ReactMethod
    public void disableProxy(Promise promise) {
        OkHttpClientProvider.setOkHttpClientFactory(() -> new OkHttpClient.Builder()
            .connectTimeout(0, TimeUnit.MILLISECONDS)
            .readTimeout(0, TimeUnit.MILLISECONDS)
            .writeTimeout(0, TimeUnit.MILLISECONDS)
            .cookieJar(new ReactCookieJarContainer())
            .proxy(Proxy.NO_PROXY)
            .build());

        isEnabledProxy = false;
        promise.resolve()
    }

    @ReactMethod
    public void enableDefaultProxy(Promise promise) {
        OkHttpClientProvider.setOkHttpClientFactory(() -> new OkHttpClient.Builder()
             .connectTimeout(0, TimeUnit.MILLISECONDS)
             .readTimeout(0, TimeUnit.MILLISECONDS)
             .writeTimeout(0, TimeUnit.MILLISECONDS)
             .cookieJar(new ReactCookieJarContainer())
             .build());

        isEnabledProxy = true;
        promise.resolve()
    }

    @ReactMethod
     public void setProxyUrl(String proxyUrls, int proxyPort, String userName, String password, Promise promise) {
          Proxy proxyOption = new Proxy(Proxy.Type.HTTP,new InetSocketAddress(proxyUrls, proxyPort));

          OkHttpClientProvider.setOkHttpClientFactory(() -> new OkHttpClient.Builder()
              .connectTimeout(0, TimeUnit.MILLISECONDS)
               .readTimeout(0, TimeUnit.MILLISECONDS)
               .writeTimeout(0, TimeUnit.MILLISECONDS)
               .cookieJar(new ReactCookieJarContainer())
               .proxy(proxyOption)
               .build());

          if(!isEnabledProxy) {
            isEnabledProxy = true;
          }
          promise.resolve()
     }
}
