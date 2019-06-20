package org.github.bodhi.hybrid.context;

import org.github.bodhi.hybrid.context.config.BestsignConfig;

/**
 * @program: bestsign-distributed
 * @description:
 * @author: Maxxx.Yg
 * @create: 2019-03-13 09:55
 **/
public class DefaultApplicationContext extends AbstractApplicationContext {

    public DefaultApplicationContext(BestsignConfig config) {
        super(config);
    }

    @Override
    protected void customize() {

    }

}
