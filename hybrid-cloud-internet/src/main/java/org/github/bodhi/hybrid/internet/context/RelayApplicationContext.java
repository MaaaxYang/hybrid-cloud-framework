package org.github.bodhi.hybrid.internet.context;

import org.github.bodhi.hybrid.context.AbstractApplicationContext;
import org.github.bodhi.hybrid.context.config.BodhiConfig;
import org.github.bodhi.hybrid.internet.factory.HttpProxyFactory;
import org.github.bodhi.hybrid.norms.annotations.BestsignProvider;
import org.github.bodhi.hybrid.norms.bean.BodhiBean;
import org.github.bodhi.hybrid.norms.exception.BodhiException;
import org.github.bodhi.hybrid.utils.StringUtils;

import java.lang.annotation.Annotation;

/**
 * @program: hybrid-cloud-framework
 * @description:
 * @author: Maxxx.Yg
 * @create: 2019-03-19 09:30
 **/
public class RelayApplicationContext extends AbstractApplicationContext {

    public RelayApplicationContext(BodhiConfig config) {
        super(config);
    }

    private static HttpProxyFactory factory = null;

    @Override
    protected void customize() {
        factory = new HttpProxyFactory(this);
    }

    @Override
    public <T extends BodhiBean> T getBean(String canonicalName) {
        BodhiBean bean = super.getBean(canonicalName);
        if (bean == null){
            synchronized (RelayApplicationContext.class){
                bean = super.getBean(canonicalName);
                if (bean==null){
                    try {
                        return createBean(Class.forName(canonicalName,false,loader));
                    } catch (ClassNotFoundException e) {
                        throw new BodhiException(e);
                    }
                }
            }
        }
        return (T)bean;
    }

    @Override
    public <T extends BodhiBean> T getBean(Class clazz) {
        BodhiBean bean = super.getBean(clazz);
        if (bean == null){
            synchronized (RelayApplicationContext.class){
                bean = super.getBean(clazz);
                if (bean==null){
                    return createBean(clazz);
                }
            }
        }
        return (T)bean;
    }


    private <T extends BodhiBean> T createBean(Class clazz){
        if (clazz.isInterface()){
            Annotation provider = clazz.getAnnotation(BestsignProvider.class);
            if (provider!=null){
                BestsignProvider pp = (BestsignProvider) provider;
                String key = clazz.getName();
                String alias = pp.value();
                if (!StringUtils.isNullOrEmpty(alias)) {
                    key = alias;
                }
                Object proxyObj = factory.create(clazz);
                BodhiBean bean = new BodhiBean();
                bean.setName(clazz.getName());
                bean.setProxy(proxyObj);
                bean.setAlias(alias);
                bean.setApplicationClass(clazz);
                beanMap.put(key,bean);
                return (T)bean;
            }
        }
        return null;
    }
}
