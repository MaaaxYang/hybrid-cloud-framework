package org.github.bodhi.hybrid.context.listeners.events;


import org.github.bodhi.hybrid.context.AbstractApplicationContext;

/**
 * @program: bestsign-distributed
 * @description:
 * @author: Maxxx.Yg
 * @create: 2019-03-12 15:24
 **/
public class ApplicationStartEvent extends StandardEvent {

    public ApplicationStartEvent(AbstractApplicationContext context) {
        super(context);
    }

}
