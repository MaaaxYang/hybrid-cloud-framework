package org.github.bodhi.hybrid.internet.strategy;

import org.github.bodhi.hybrid.internet.client.Client;
import net.sf.cglib.proxy.MethodProxy;
import org.github.bodhi.hybrid.internet.client.Client;

import java.lang.reflect.Method;

/**
 * @program: bestsign-distributed
 * @description:
 * @author: Maxxx.Yg
 * @create: 2019-03-06 11:18
 **/
public class BeforeStrategy extends AbstractStrategy{

    private final static ThreadLocal beforeRes = new ThreadLocal();

    public BeforeStrategy(Client client) {
        super(client);
    }

    @Override
    protected Object run(StrategyParameter parameter) throws Throwable {
        Object object = getResponse(parameter);
        beforeRes.set(object);
        Object res = parameter.getProxy().invokeSuper(parameter.getTarget(),parameter.getArgs());
        beforeRes.remove();
        return res;
    }
}
