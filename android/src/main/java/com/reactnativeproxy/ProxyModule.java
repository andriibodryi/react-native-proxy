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

import java.net.InetSocketAddress;

import java.util.concurrent.TimeUnit;

@ReactModule(name = ProxyModule.NAME)
public class ProxyModule extends ReactContextBaseJavaModule {
    public static final String NAME = "Proxy";

    public boolean isEnabledProxy = true;

    public ProxyModule(ReactApplicationContext reactContext) {
        super(reactContext);
    }

    @Override
    @NonNull
    public String getName() {
        return NAME;
    }

    @ReactMethod
    public void disableProxy() {
        OkHttpClientProvider.setOkHttpClientFactory(() -> new OkHttpClient.Builder()
            .connectTimeout(0, TimeUnit.MILLISECONDS)
            .readTimeout(0, TimeUnit.MILLISECONDS)
            .writeTimeout(0, TimeUnit.MILLISECONDS)
            .cookieJar(new ReactCookieJarContainer())
            .proxy(Proxy.NO_PROXY)
            .build());

        isEnabledProxy = false;
    }

    @ReactMethod
    public void enableDefaultProxy() {
        OkHttpClientProvider.setOkHttpClientFactory(() -> new OkHttpClient.Builder()
             .connectTimeout(0, TimeUnit.MILLISECONDS)
             .readTimeout(0, TimeUnit.MILLISECONDS)
             .writeTimeout(0, TimeUnit.MILLISECONDS)
             .cookieJar(new ReactCookieJarContainer())
             .build());

        isEnabledProxy = true;
    }

    @ReactMethod
     public void setProxyUrl(String proxyUrls, int proxyPort) {
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
     }
}
