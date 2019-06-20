package org.github.bodhi.hybrid.internet.config;


/**
 * @program: bodhi-distributed
 * @description:
 * @author: Maxxx.Yg
 * @create: 2019-03-06 17:38
 **/
public class ClientConfig{

    public static final ClientConfig DEFAULT = new ClientConfig();

    private String host = "";

    private int connectTimeoutSeconds = 3;

    private int readTimeoutSeconds = 3;

    private int writeTimeoutSeconds = 3;

    private int maxIdleConnections = Runtime.getRuntime().availableProcessors();

    private long keepAliveDuration = 5L;

    public int getConnectTimeoutSeconds() {
        return connectTimeoutSeconds;
    }

    public void setConnectTimeoutSeconds(int connectTimeoutSeconds) {
        this.connectTimeoutSeconds = connectTimeoutSeconds;
    }

    public int getReadTimeoutSeconds() {
        return readTimeoutSeconds;
    }

    public void setReadTimeoutSeconds(int readTimeoutSeconds) {
        this.readTimeoutSeconds = readTimeoutSeconds;
    }

    public int getWriteTimeoutSeconds() {
        return writeTimeoutSeconds;
    }

    public void setWriteTimeoutSeconds(int writeTimeoutSeconds) {
        this.writeTimeoutSeconds = writeTimeoutSeconds;
    }

    public int getMaxIdleConnections() {
        return maxIdleConnections;
    }

    public void setMaxIdleConnections(int maxIdleConnections) {
        this.maxIdleConnections = maxIdleConnections;
    }

    public long getKeepAliveDuration() {
        return keepAliveDuration;
    }

    public void setKeepAliveDuration(long keepAliveDuration) {
        this.keepAliveDuration = keepAliveDuration;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }
}