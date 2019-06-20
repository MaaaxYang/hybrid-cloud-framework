package org.github.bodhi.hybrid.context;

import org.github.bodhi.hybrid.context.config.BodhiConfig;

/**
 * @program: bodhi-distributed
 * @description:
 * @author: Maxxx.Yg
 * @create: 2019-03-13 09:55
 **/
public class DefaultApplicationContext extends AbstractApplicationContext {

    public DefaultApplicationContext(BodhiConfig config) {
        super(config);
    }

    @Override
    protected void customize() {

    }

}
