package org.github.bodhi.hybrid.internet.listeners;

import org.github.bodhi.hybrid.internet.appointment.AfterRequestProcessFilter;
import org.github.bodhi.hybrid.internet.appointment.BeforeRequestProcessFilter;
import org.github.bodhi.hybrid.internet.holder.ClientHolder;
import org.github.bodhi.hybrid.internet.listeners.events.PlatformStartedEvent;
import org.github.bodhi.hybrid.internet.filters.FilterManager;
import org.github.bodhi.hybrid.internet.appointment.AfterRequestProcessFilter;
import org.github.bodhi.hybrid.internet.appointment.BeforeRequestProcessFilter;
import org.github.bodhi.hybrid.internet.filters.FilterManager;
import org.github.bodhi.hybrid.internet.holder.ClientHolder;
import org.github.bodhi.hybrid.internet.listeners.events.PlatformStartedEvent;
import org.github.bodhi.hybrid.norms.event.BestsignListener;
import org.github.bodhi.hybrid.utils.ServiceLoadUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * @program: hybrid-cloud-framework
 * @description:
 * @author: Maxxx.Yg
 * @create: 2019-03-18 14:42
 **/
public class PlatformStartedListener implements BestsignListener<PlatformStartedEvent> {

    private static Logger logger = LoggerFactory.getLogger(PlatformStartedListener.class);

    @Override
    public void onBestsignEvent(PlatformStartedEvent event) {
        logger.info("listen in platform stared event ...");

        logger.info("internet initialization start");
        // client customize
        ClientHolder.init(
                event.getContext(),
                event.getContext().getConfig(),
                event.getClientConfig()
        );

        // filter customize
        List<BeforeRequestProcessFilter> before = ServiceLoadUtils.loadAllInstance(BeforeRequestProcessFilter.class,event.getContext().getClassloader());
        List<AfterRequestProcessFilter> after = ServiceLoadUtils.loadAllInstance(AfterRequestProcessFilter.class,event.getContext().getClassloader());
        FilterManager.init(before,after);

        logger.info("internet initialization end");
    }

}
