package org.github.bodhi.hybrid.internet.config;

import org.github.bodhi.hybrid.internet.client.ssl.BestsignTrustManager;
import org.github.bodhi.hybrid.internet.client.interceptor.HttpClientInterceptor;
import org.github.bodhi.hybrid.internet.client.ssl.UnsafeSSLSupport;
import okhttp3.ConnectionPool;
import okhttp3.OkHttpClient;
import org.github.bodhi.hybrid.internet.client.interceptor.HttpClientInterceptor;
import org.github.bodhi.hybrid.internet.client.ssl.BestsignTrustManager;
import org.github.bodhi.hybrid.internet.client.ssl.UnsafeSSLSupport;

import java.util.concurrent.TimeUnit;

/**
 * @program: bestsign-distributed
 * @description:
 * @author: Maxxx.Yg
 * @create: 2019-03-07 09:52
 **/
public class OKHttpConfigure implements RemoteConfigure<OkHttpClient> {

    private OkHttpClient okHttpClient;

    private ClientConfig clientConfig;

    public OKHttpConfigure(ClientConfig clientConfig) {
        init(clientConfig);
    }

    @Override
    public void init(ClientConfig clientConfig){
        okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(clientConfig.getConnectTimeoutSeconds(),TimeUnit.SECONDS)
                .readTimeout(clientConfig.getReadTimeoutSeconds(),TimeUnit.SECONDS)
                .writeTimeout(clientConfig.getWriteTimeoutSeconds(),TimeUnit.SECONDS)
                .addInterceptor(new HttpClientInterceptor())
                .connectionPool(new ConnectionPool(
                        clientConfig.getMaxIdleConnections(),
                        clientConfig.getKeepAliveDuration(),
                        TimeUnit.MINUTES))
                .sslSocketFactory(
                        UnsafeSSLSupport.getSSLSocketFactory(),
                        new BestsignTrustManager())
                .hostnameVerifier(UnsafeSSLSupport.getHostnameVerifier())
                .build();
    }

    @Override
    public OkHttpClient get() {
        return okHttpClient;
    }

    public ClientConfig getClientConfig() {
        return clientConfig;
    }

    public void setClientConfig(ClientConfig clientConfig) {
        this.clientConfig = clientConfig;
    }
}
