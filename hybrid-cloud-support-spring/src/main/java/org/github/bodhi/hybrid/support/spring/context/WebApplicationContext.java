package org.github.bodhi.hybrid.support.spring.context;

import org.github.bodhi.hybrid.context.config.BestsignConfig;
import org.github.bodhi.hybrid.internet.context.RelayApplicationContext;
import org.github.bodhi.hybrid.norms.BestsignApi;
import org.github.bodhi.hybrid.support.spring.aop.ApiFactoryBean;
import org.github.bodhi.hybrid.support.spring.holder.ExceptionHolder;
import org.github.bodhi.hybrid.support.spring.holder.ServletFilterHolder;
import org.github.bodhi.hybrid.support.spring.holder.SpringContextHolder;
import org.github.bodhi.hybrid.support.spring.holder.WebContextHolder;
import org.github.bodhi.hybrid.utils.ServiceLoadUtils;
import org.github.bodhi.hybrid.utils.StringUtils;
import org.springframework.aop.support.AopUtils;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.util.ClassUtils;
import org.springframework.util.CollectionUtils;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @program: bestsign-distributed
 * @description:
 * @author: Maxxx.Yg
 * @create: 2019-03-13 21:10
 **/
public class WebApplicationContext extends RelayApplicationContext {

    public WebApplicationContext(BestsignConfig config) {
        super(config);
    }

    @Override
    protected void customize() {

        super.customize();

        // customize web context holder
        WebContextHolder.getInstance().setContext(this);

        // load servlet filter
        ServletFilterHolder.getInstance().init(loader);

        // exception handler init
        ExceptionHolder.init(loader);

        if (config.isDebug()){
            return;
        }
        // api map
        List<BestsignApi> apis = ServiceLoadUtils.loadAllInstance(
                BestsignApi.class,
                loader
        );

        if (CollectionUtils.isEmpty(apis)){
            return;
        }

        // get spring application context
        ConfigurableApplicationContext context = SpringContextHolder.getInstance().getCfgContext();
        if (context==null){
            return;
        }

        // get custom requestMappingHandlerMapping & spring standard beanDeifnitionRegistry
        RequestMappingHandlerMapping mapping = context.getBean(RequestMappingHandlerMapping.class);
        BeanDefinitionRegistry beanDefinitionRegistry = (DefaultListableBeanFactory) context.getBeanFactory();

        // collect existing mappingInfo
        Map<String,RequestMappingInfo> oldMappingInfoMap = new HashMap<>(32);
        for(Map.Entry<RequestMappingInfo,HandlerMethod> entry : mapping.getHandlerMethods().entrySet()){
            oldMappingInfoMap.put(entry.getKey().getName(),entry.getKey());
        }

        // regeister route & spring context
        for(BestsignApi api : apis){

            String beanName = StringUtils.toLowerCaseFirstOne(api.getClass().getSimpleName());

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

            // singleton
            BeanDefinition definition = BeanDefinitionBuilder.genericBeanDefinition(ApiFactoryBean.class)
                    .addConstructorArgValue(api.getClass())
                    .setScope("singleton")
                    .setAutowireMode(0)
                    .getBeanDefinition();
            beanDefinitionRegistry.registerBeanDefinition(beanName,definition);

            // register mapping
            Method[] methods = api.getClass().getDeclaredMethods();
            for(Method method : methods){

                // get standard class and method
                Class useType = ClassUtils.getUserClass(api.getClass());
                Method invocableMethod = AopUtils.selectInvocableMethod(method, useType);

                // prepare mappingInfo
                RequestMappingInfo mappingInfo = MappingHelper.generateMappingInfo(method,useType);

                // clear old mappingInfo
                RequestMappingInfo oldMappingInfo = oldMappingInfoMap.get(mappingInfo.getName());
                if(oldMappingInfo!=null){
                    mapping.unregisterMapping(oldMappingInfo);
                }

                // register new mappingInfo
                mapping.registerMapping(mappingInfo,beanName,invocableMethod);

            }
        }


    }


}
