package org.github.bodhi.hybrid.internet.factory;


import org.github.bodhi.hybrid.context.AbstractApplicationContext;
import org.github.bodhi.hybrid.internet.client.HttpMethodInterceptor;
import org.github.bodhi.hybrid.internet.client.HttpMethodInterceptor;
import org.github.bodhi.hybrid.norms.serializers.Serializer;
import net.sf.cglib.proxy.Enhancer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ExecutorService;

/**
 * @program: bodhi-distributed
 * @description:
 * @author: Maxxx.Yg
 * @create: 2019-03-06 12:32
 **/
public class HttpProxyFactory {

    private final static Logger logger = LoggerFactory.getLogger(HttpProxyFactory.class);

    private ExecutorService executor;

    private ClassLoader loader;

    private Serializer serializer;

    private Enhancer enhancer = new Enhancer();

    private HttpMethodInterceptor proxy;


    public HttpProxyFactory(AbstractApplicationContext context) {
        this.executor = context.getExecutor();
        this.loader = context.getClassloader();
        this.serializer = context.getSerializer();
        proxy = new HttpMethodInterceptor(executor,serializer,loader);
        enhancer.setClassLoader(loader);
        enhancer.setCallback(proxy);
    }


    public <T> T create(Class<T> clazz){

        enhancer.setSuperclass(clazz);

        Object proxyObj = enhancer.create();

        return (T)proxyObj;
    }

}
