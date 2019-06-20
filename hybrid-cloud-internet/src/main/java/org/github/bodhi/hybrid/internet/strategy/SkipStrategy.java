package org.github.bodhi.hybrid.internet.strategy;

import org.github.bodhi.hybrid.internet.client.Client;
import net.sf.cglib.proxy.MethodProxy;
import org.github.bodhi.hybrid.internet.client.Client;

import java.lang.reflect.Method;

/**
 * @program: bestsign-distributed
 * @description:
 * @author: Maxxx.Yg
 * @create: 2019-03-06 11:20
 **/
public class SkipStrategy extends AbstractStrategy  {


    public SkipStrategy(Client client) {
        super(client);
    }

    @Override
    protected Object run(StrategyParameter parameter) throws Throwable {
        return getResponse(parameter);
    }
}
