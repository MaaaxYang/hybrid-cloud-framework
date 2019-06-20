package org.github.bodhi.hybrid.internet.strategy;

import org.github.bodhi.hybrid.internet.enums.ResponseType;
import org.github.bodhi.hybrid.internet.enums.ResponseType;
import org.github.bodhi.hybrid.norms.serializers.Serializer;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

/**
 * @program: bodhi-distributed
 * @description:
 * @author: Maxxx.Yg
 * @create: 2019-03-15 11:04
 **/
public class StrategyParameter {

    private Object target;

    private Method method;

    private Object[] args;

    private ResponseType responseType;

    private MethodProxy proxy;

    private Serializer serializer;

    private ClassLoader classLoader;

    public Object getTarget() {
        return target;
    }

    public void setTarget(Object target) {
        this.target = target;
    }

    public Method getMethod() {
        return method;
    }

    public void setMethod(Method method) {
        this.method = method;
    }

    public Object[] getArgs() {
        return args;
    }

    public void setArgs(Object[] args) {
        this.args = args;
    }

    public MethodProxy getProxy() {
        return proxy;
    }

    public void setProxy(MethodProxy proxy) {
        this.proxy = proxy;
    }


    public <T,S> Serializer<T,S> getSerializer() {
        return serializer;
    }

    public <T,S> void setSerializer(Serializer<T,S> serializer) {
        this.serializer = serializer;
    }

    public ClassLoader getClassLoader() {
        return classLoader;
    }

    public void setClassLoader(ClassLoader classLoader) {
        this.classLoader = classLoader;
    }

    public ResponseType getResponseType() {
        return responseType;
    }

    public void setResponseType(ResponseType responseType) {
        this.responseType = responseType;
    }
}
