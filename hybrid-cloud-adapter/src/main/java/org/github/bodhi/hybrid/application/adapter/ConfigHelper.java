package org.github.bodhi.hybrid.application.adapter;

import org.github.bodhi.hybrid.context.config.BestsignConfig;
import org.github.bodhi.hybrid.context.config.ThreadPoolConfig;
import org.github.bodhi.hybrid.internet.config.ClientConfig;

/**
 * @program: hybrid-cloud-framework
 * @description:
 * @author: Maxxx.Yg
 * @create: 2019-03-18 18:28
 **/
public class ConfigHelper {

    private static BestsignConfig bestsignConfig;

    private static ClientConfig clientConfig;

    private static ThreadPoolConfig threadPoolConfig;

    public static BestsignConfig getBestsignConfig() {
        return bestsignConfig;
    }

    public static void setBestsignConfig(BestsignConfig bestsignConfig) {
        ConfigHelper.bestsignConfig = bestsignConfig;
    }

    public static ClientConfig getClientConfig() {
        return clientConfig;
    }

    public static void setClientConfig(ClientConfig clientConfig) {
        ConfigHelper.clientConfig = clientConfig;
    }

    public static ThreadPoolConfig getThreadPoolConfig() {
        return threadPoolConfig;
    }

    public static void setThreadPoolConfig(ThreadPoolConfig threadPoolConfig) {
        ConfigHelper.threadPoolConfig = threadPoolConfig;
    }
}
