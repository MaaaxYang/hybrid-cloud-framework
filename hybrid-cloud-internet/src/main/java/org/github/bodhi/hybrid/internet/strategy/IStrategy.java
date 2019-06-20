package org.github.bodhi.hybrid.internet.strategy;

import org.github.bodhi.hybrid.internet.client.Client;
import org.github.bodhi.hybrid.internet.client.Client;

/**
 * @program: bestsign-distributed
 * @description:
 * @author: Maxxx.Yg
 * @create: 2019-03-06 11:19
 **/
public interface IStrategy {

    Object execute(StrategyParameter parameter) throws Throwable;

    void setClient(Client client);

}
