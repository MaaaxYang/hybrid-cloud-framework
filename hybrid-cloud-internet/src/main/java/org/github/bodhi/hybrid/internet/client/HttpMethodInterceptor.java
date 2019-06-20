package org.github.bodhi.hybrid.internet.client;

import org.github.bodhi.hybrid.internet.strategy.IStrategy;
import org.github.bodhi.hybrid.internet.strategy.StrategyParameter;
import org.github.bodhi.hybrid.internet.strategy.StrategySelector;
import org.github.bodhi.hybrid.norms.serializers.Serializer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;
import java.util.concurrent.ExecutorService;

/**
 * @program: bodhi-distributed
 * @description:
 * @author: Maxxx.Yg
 * @create: 2019-03-13 11:04
 **/
public class HttpMethodInterceptor implements MethodInterceptor {

    private ExecutorService executor;

    private ClassLoader classLoader;

    private StrategySelector selector;

    private Serializer serializer;

    public HttpMethodInterceptor(ExecutorService executor, Serializer serializer, ClassLoader classLoader) {
        this.executor = executor;
        this.classLoader = classLoader;
        this.serializer = serializer;
        selector = new StrategySelector();
    }

    @Override
    public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
        StrategyParameter parameter = new StrategyParameter();
        parameter.setArgs(objects);
        parameter.setMethod(method);
        parameter.setTarget(o);
        parameter.setProxy(methodProxy);
        parameter.setClassLoader(classLoader);
        parameter.setSerializer(serializer);

        // todo: 限流？
        final IStrategy strategy = selector.select(parameter);
        return strategy.execute(parameter);
    }
}
