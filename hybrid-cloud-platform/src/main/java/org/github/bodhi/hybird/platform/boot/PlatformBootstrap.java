package org.github.bodhi.hybird.platform.boot;

import org.github.bodhi.hybird.platform.base.PlatformManager;
import org.github.bodhi.hybrid.context.config.BodhiConfig;
import org.github.bodhi.hybrid.context.config.ThreadPoolConfig;
import org.github.bodhi.hybrid.norms.ApplicationContext;

/**
 * @program: bodhi-distributed
 * @description:
 * @author: Maxxx.Yg
 * @create: 2019-03-13 15:26
 **/
public class PlatformBootstrap {

    public static <T extends ApplicationContext> ApplicationContext run(BodhiConfig config, ThreadPoolConfig threadPoolConfig, Class<T> clazz){
        PlatformManager.init(config,threadPoolConfig);
        return PlatformManager.startApplication(clazz);
    }

    public static <T extends ApplicationContext> ApplicationContext run(BodhiConfig config, Class<T> clazz){
        PlatformManager.init(config,ThreadPoolConfig.DEFAULT);
        return PlatformManager.startApplication(clazz);
    }
}
