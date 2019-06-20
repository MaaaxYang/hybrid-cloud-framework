package org.github.bodhi.hybrid.context.listeners.events;

import org.github.bodhi.hybrid.context.AbstractApplicationContext;
import org.github.bodhi.hybrid.norms.event.BestsignEvent;

/**
 * @program: bestsign-distributed
 * @description:
 * @author: Maxxx.Yg
 * @create: 2019-03-12 15:27
 **/
public abstract class StandardEvent implements BestsignEvent {

    protected AbstractApplicationContext context;

    public StandardEvent(AbstractApplicationContext context) {
        this.context = context;
    }

    public AbstractApplicationContext getContext() {
        return context;
    }

    public void setContext(AbstractApplicationContext context) {
        this.context = context;
    }
}
