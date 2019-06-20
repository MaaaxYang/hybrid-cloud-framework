package org.github.bodhi.hybrid.application.adapter;

import org.github.bodhi.hybird.platform.boot.PlatformBootstrap;
import org.github.bodhi.hybrid.application.adapter.repository.RepositoryManager;
import org.github.bodhi.hybrid.context.AbstractApplicationContext;
import org.github.bodhi.hybrid.context.config.BestsignConfig;
import org.github.bodhi.hybrid.context.config.ThreadPoolConfig;
import org.github.bodhi.hybrid.internet.config.ClientConfig;
import org.github.bodhi.hybrid.internet.context.RelayApplicationContext;
import org.github.bodhi.hybrid.internet.listeners.events.PlatformStartedEvent;
import org.github.bodhi.hybrid.norms.ApplicationContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @program: bestsign-distributed
 * @description:
 * @author: Maxxx.Yg
 * @create: 2019-03-13 23:44
 **/
public class BestsignBootstrap {

    private final static Logger logger = LoggerFactory.getLogger(BestsignBootstrap.class);

    public static void start(BestsignConfig bestsignConfig, ClientConfig clientConfig){
        start(bestsignConfig, clientConfig,ThreadPoolConfig.DEFAULT,RelayApplicationContext.class);
    }

    public static <T extends ApplicationContext> void start(BestsignConfig bestsignConfig, ClientConfig clientConfig , ThreadPoolConfig threadPoolConfig,Class<T> clazz){
        ConfigHelper.setBestsignConfig(bestsignConfig);
        ConfigHelper.setClientConfig(clientConfig);
        ConfigHelper.setThreadPoolConfig(threadPoolConfig);

        RepositoryManager.init(bestsignConfig.getRepositoryHost());
        InitializeHelper.run(bestsignConfig.getClientId(),bestsignConfig.getVersion());

        ApplicationContext context = PlatformBootstrap.run(bestsignConfig,threadPoolConfig,clazz);

        context.getPublisher().push(
                new PlatformStartedEvent(
                        (AbstractApplicationContext) context,
                        clientConfig
                )
        );

        logger.info("-------------------------------------------------------");
        logger.info("bestsign all module started , current version {}",bestsignConfig.getVersion());
        logger.info("-------------------------------------------------------");
    }
}
