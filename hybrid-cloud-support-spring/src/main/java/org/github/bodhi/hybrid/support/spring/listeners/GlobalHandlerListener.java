package org.github.bodhi.hybrid.support.spring.listeners;

import org.github.bodhi.hybrid.context.listeners.events.ApplicationRefreshedEvent;
import org.github.bodhi.hybrid.norms.event.BestsignListener;
import org.github.bodhi.hybrid.support.spring.holder.ServletFilterHolder;

/**
 * @program: hybrid-cloud-framework
 * @description:
 * @author: Maxxx.Yg
 * @create: 2019-03-21 16:24
 **/
public class GlobalHandlerListener implements BestsignListener<ApplicationRefreshedEvent> {

    @Override
    public void onBestsignEvent(ApplicationRefreshedEvent event) {
        ServletFilterHolder.getFilter().resetFilter();
    }

}
