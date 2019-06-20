package org.github.bodhi.dev.ops;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @program: hybrid-cloud-framework
 * @description:
 * @author: Maxxx.Yg
 * @create: 2019-04-04 15:27
 **/
@ConfigurationProperties("hybrid.cloud.platform")
public class PlatformProperties {

    private String host = "localhost";

    private int port = 10003;

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }
}
