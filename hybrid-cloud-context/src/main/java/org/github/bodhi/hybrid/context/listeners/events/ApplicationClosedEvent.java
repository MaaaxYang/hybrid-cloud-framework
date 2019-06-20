package org.github.bodhi.hybrid.context.listeners.events;

import org.github.bodhi.hybrid.context.AbstractApplicationContext;

/**
 * @program: bodhi-distributed
 * @description:
 * @author: Maxxx.Yg
 * @create: 2019-03-13 15:02
 **/
public class ApplicationClosedEvent extends StandardEvent {

    public ApplicationClosedEvent(AbstractApplicationContext context) {
        super(context);
    }

}
