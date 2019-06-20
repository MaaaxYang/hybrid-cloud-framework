package org.github.bodhi.hybrid.internet.filters;

import org.github.bodhi.hybrid.context.order.Orders;
import org.github.bodhi.hybrid.internet.appointment.AfterRequestProcessFilter;
import org.github.bodhi.hybrid.internet.appointment.BeforeRequestProcessFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * @program: hybrid-cloud-framework
 * @description:
 * @author: Maxxx.Yg
 * @create: 2019-03-18 14:53
 **/
public class FilterManager {

    private final static Logger logger = LoggerFactory.getLogger(FilterManager.class);

    private volatile static List<BeforeRequestProcessFilter> beforeFilter = new ArrayList<>();

    private volatile static List<AfterRequestProcessFilter> afterFilter = new ArrayList<>();

    public static void init(List<BeforeRequestProcessFilter> before, List<AfterRequestProcessFilter> after){

        beforeFilter = Orders.listSort(before);
        for(BeforeRequestProcessFilter filter : beforeFilter){
            logger.info("beforeFilter '{}' init ",filter.getClass().getCanonicalName());
        }
        afterFilter = Orders.listSort(after);
        for(AfterRequestProcessFilter filter : after){
            logger.info("afterFilter '{}' init ",filter.getClass().getCanonicalName());
        }
    }

    public static List<BeforeRequestProcessFilter> getBeforePlugins() {
        return beforeFilter;
    }

    public static void setBeforePlugins(List<BeforeRequestProcessFilter> beforePlugins) {
        FilterManager.beforeFilter = beforePlugins;
    }

    public static List<AfterRequestProcessFilter> getAfterPlugins() {
        return afterFilter;
    }

    public static void setAfterPlugins(List<AfterRequestProcessFilter> afterPlugins) {
        FilterManager.afterFilter = afterPlugins;
    }
}
