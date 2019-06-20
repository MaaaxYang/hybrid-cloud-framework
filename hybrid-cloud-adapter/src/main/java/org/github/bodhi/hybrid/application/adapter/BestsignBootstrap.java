package org.github.bodhi.hybrid.application.adapter;

import org.github.bodhi.hybird.platform.boot.PlatformBootstrap;
import org.github.bodhi.hybrid.application.adapter.repository.RepositoryManager;
import org.github.bodhi.hybrid.context.AbstractApplicationContext;
import org.github.bodhi.hybrid.context.config.BodhiConfig;
import org.github.bodhi.hybrid.context.config.ThreadPoolConfig;
import org.github.bodhi.hybrid.internet.config.ClientConfig;
import org.github.bodhi.hybrid.internet.context.RelayApplicationContext;
import org.github.bodhi.hybrid.internet.listeners.events.PlatformStartedEvent;
import org.github.bodhi.hybrid.norms.ApplicationContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @program: bodhi-distributed
 * @description:
 * @author: Maxxx.Yg
 * @create: 2019-03-13 23:44
 **/
public class BestsignBootstrap {

    private final static Logger logger = LoggerFactory.getLogger(BestsignBootstrap.class);

    public static void start(BodhiConfig bodhiConfig, ClientConfig clientConfig){
        start(bodhiConfig, clientConfig,ThreadPoolConfig.DEFAULT,RelayApplicationContext.class);
    }

    public static <T extends ApplicationContext> void start(BodhiConfig bodhiConfig, ClientConfig clientConfig , ThreadPoolConfig threadPoolConfig, Class<T> clazz){
        ConfigHelper.setBodhiConfig(bodhiConfig);
        ConfigHelper.setClientConfig(clientConfig);
        ConfigHelper.setThreadPoolConfig(threadPoolConfig);

        RepositoryManager.init(bodhiConfig.getRepositoryHost());
        InitializeHelper.run(bodhiConfig.getClientId(), bodhiConfig.getVersion());

        ApplicationContext context = PlatformBootstrap.run(bodhiConfig,threadPoolConfig,clazz);

        context.getPublisher().push(
                new PlatformStartedEvent(
                        (AbstractApplicationContext) context,
                        clientConfig
                )
        );

        logger.info("-------------------------------------------------------");
        logger.info("bodhi all module started , current version {}", bodhiConfig.getVersion());
        logger.info("-------------------------------------------------------");
    }
}
