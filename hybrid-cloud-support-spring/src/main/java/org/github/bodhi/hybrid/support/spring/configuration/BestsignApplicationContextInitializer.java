package org.github.bodhi.hybrid.support.spring.configuration;

import org.github.bodhi.hybrid.support.spring.holder.SpringContextHolder;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;

/**
 * @program: bodhi-distributed
 * @description: 用来收集控制器路由
 * @author: Maxxx.Yg
 * @create: 2019-03-06 16:24
 **/
public class BestsignApplicationContextInitializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {

    @Override
    public void initialize(ConfigurableApplicationContext applicationContext) {
        SpringContextHolder.getInstance().setCfgContext(applicationContext);
    }

}
