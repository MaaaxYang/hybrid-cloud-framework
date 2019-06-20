package org.github.bodhi.hybrid.support.spring.configuration;

import org.github.bodhi.hybird.platform.base.PlatformManager;
import org.github.bodhi.hybrid.application.adapter.BestsignBootstrap;
import org.github.bodhi.hybrid.support.spring.Dispatcher;
import org.github.bodhi.hybrid.support.spring.context.WebApplicationContext;
import org.github.bodhi.hybrid.support.spring.filters.GlobalServletFilter;
import org.github.bodhi.hybrid.support.spring.holder.ServletFilterHolder;
import org.github.bodhi.hybrid.support.spring.properties.BestsignProperties;
import org.github.bodhi.hybrid.support.spring.properties.ClientProperties;
import org.github.bodhi.hybrid.support.spring.properties.ThreadPoolProperties;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;

/**
 * @program: bestsign-distributed
 * @description:
 * @author: Maxxx.Yg
 * @create: 2019-03-13 21:09
 **/
@EnableAspectJAutoProxy(proxyTargetClass = true)
@Configuration
@AutoConfigureBefore(Dispatcher.class)
@Order(Ordered.HIGHEST_PRECEDENCE)
@ComponentScan("org.github.bodhi.hybrid.support.spring")
@EnableConfigurationProperties(
        {
                BestsignProperties.class,
                ThreadPoolProperties.class,
                ClientProperties.class
        }
)
public class BootstrapAutoConfiguration implements InitializingBean,DisposableBean {

    @Autowired
    private BestsignProperties bestsignProperties;

    @Autowired
    private ThreadPoolProperties threadPoolProperties;

    @Autowired
    private ClientProperties clientProperties;

    @Override
    public void destroy() throws Exception {
        PlatformManager.shutdownApplicaiton(false);
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        BestsignBootstrap.start(bestsignProperties,clientProperties,threadPoolProperties,WebApplicationContext.class);
    }

    @Bean
    public GlobalServletFilter globalServletFilter(){
        return ServletFilterHolder.getFilter();
    }

}
