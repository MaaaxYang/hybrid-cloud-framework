package org.github.bodhi.hybrid.support.spring.beans;

import org.github.bodhi.hybrid.support.spring.configuration.BootstrapAutoConfiguration;
import org.github.bodhi.hybrid.support.spring.holder.SpringContextHolder;
import org.github.bodhi.hybrid.support.spring.holder.WebContextHolder;
import org.github.bodhi.hybrid.utils.ServiceLoadUtils;
import org.github.bodhi.hybrid.utils.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * @program: hybrid-cloud-framework
 * @description:
 * @author: Maxxx.Yg
 * @create: 2019-03-25 11:01
 **/
@Configuration
@AutoConfigureAfter(BootstrapAutoConfiguration.class)
public class SpringBeanAutoConfiguration implements InitializingBean {

    private final static Logger logger = LoggerFactory.getLogger(SpringBeanAutoConfiguration.class);

    @Override
    public void afterPropertiesSet() throws Exception {

        ClassLoader loader = WebContextHolder.getInstance().getWebApplicationContext().getClassloader();

        List<SpringBean> beans = ServiceLoadUtils.loadAllInstance(SpringBean.class,loader);

        if (CollectionUtils.isEmpty(beans)){
            return;
        }

        ConfigurableApplicationContext context = SpringContextHolder.getInstance().getCfgContext();
        BeanDefinitionRegistry beanDefinitionRegistry = (DefaultListableBeanFactory) context.getBeanFactory();


        for(SpringBean bean : beans){
            String beanName = StringUtils.toLowerCaseFirstOne(bean.getClass().getSimpleName());

            // register singleton
            boolean isHas = ((DefaultListableBeanFactory) beanDefinitionRegistry).containsBean(beanName);
            if (isHas){
                ((DefaultListableBeanFactory) beanDefinitionRegistry).destroySingleton(beanName);
            }

            // register BeanDefinition
            boolean isHasDefine = beanDefinitionRegistry.containsBeanDefinition(beanName);
            if (isHasDefine){
                beanDefinitionRegistry.removeBeanDefinition(beanName);
            }
            BeanDefinition definition = BeanDefinitionBuilder.genericBeanDefinition(bean.getClass())
                    .setScope("singleton")
                    .setAutowireMode(0)
                    .getBeanDefinition();
            beanDefinitionRegistry.registerBeanDefinition(beanName,definition);
        }

        logger.info("spring bean init end");
    }
}
