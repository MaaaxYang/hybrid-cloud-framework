package org.github.bodhi.hybrid.internet.strategy;

import org.github.bodhi.hybrid.internet.client.Client;
import net.sf.cglib.proxy.MethodProxy;
import org.github.bodhi.hybrid.internet.client.Client;

import java.lang.reflect.Method;

/**
 * @program: bodhi-distributed
 * @description:
 * @author: Maxxx.Yg
 * @create: 2019-03-06 11:18
 **/
public class AfterStrategy extends AbstractStrategy{

    public AfterStrategy(Client client) {
        super(client);
    }

    @Override
    protected Object run(StrategyParameter parameter) throws Throwable {
        parameter.getProxy().invokeSuper(parameter.getTarget(),parameter.getArgs());
        return getResponse(parameter);
    }
}
