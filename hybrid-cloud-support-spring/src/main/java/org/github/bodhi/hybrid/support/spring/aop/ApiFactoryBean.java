package org.github.bodhi.hybrid.support.spring.aop;

import net.sf.cglib.proxy.Enhancer;
import org.springframework.beans.factory.FactoryBean;

/**
 * @program: hybrid-cloud-framework
 * @description:
 * @author: Maxxx.Yg
 * @create: 2019-03-29 17:37
 **/
public class ApiFactoryBean implements FactoryBean {

    private Class clazz;

    private Enhancer enhancer;

    private ClassLoader loader;

    public ApiFactoryBean(Class clazz){
        this.clazz = clazz;
        enhancer = new Enhancer();
        enhancer.setClassLoader(loader);
        enhancer.setCallback(new GlobalInvokeInterceptor());
        enhancer.setSuperclass(clazz);
    }

    @Override
    public Object getObject() throws Exception {
        return enhancer.create();
    }

    @Override
    public Class<?> getObjectType() {
        return clazz;
    }

    @Override
    public boolean isSingleton() {
        return true;
    }
}
