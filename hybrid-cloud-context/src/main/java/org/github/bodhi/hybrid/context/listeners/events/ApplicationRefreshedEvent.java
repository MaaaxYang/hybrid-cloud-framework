package org.github.bodhi.hybrid.context.listeners.events;

import org.github.bodhi.hybrid.context.AbstractApplicationContext;

/**
 * @program: hybrid-cloud-framework
 * @description:
 * @author: Maxxx.Yg
 * @create: 2019-03-21 16:25
 **/
public class ApplicationRefreshedEvent extends StandardEvent {

    public ApplicationRefreshedEvent(AbstractApplicationContext context) {
        super(context);
    }
}
