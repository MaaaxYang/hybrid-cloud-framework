package org.github.bodhi.hybrid.support.spring.holder;

import org.springframework.context.ConfigurableApplicationContext;

/**
 * @program: bestsign-distributed
 * @description:
 * @author: Maxxx.Yg
 * @create: 2019-03-06 16:47
 **/
public class SpringContextHolder {

    private static final SpringContextHolder INSTANCE = new SpringContextHolder();

    private ConfigurableApplicationContext cfgContext;

    private SpringContextHolder() {
        if (INSTANCE != null) {
            throw new Error("error");
        }
    }

    /**
     * get SpringBeanUtils.
     * @return SpringBeanUtils
     */
    public static SpringContextHolder getInstance() {
        return INSTANCE;
    }

    public <T> T getBean(final Class<T> type) {
        return cfgContext.getBean(type);
    }


    public void registerBean(final String beanName, final Object obj) {
        cfgContext.getBeanFactory().registerSingleton(beanName, obj);
    }


    public void setCfgContext(final ConfigurableApplicationContext cfgContext) {
        this.cfgContext = cfgContext;
    }

    public ConfigurableApplicationContext getCfgContext() {
        return cfgContext;
    }
}
