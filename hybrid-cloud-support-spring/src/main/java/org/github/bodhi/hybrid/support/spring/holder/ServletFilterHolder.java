package org.github.bodhi.hybrid.support.spring.holder;

import org.github.bodhi.hybrid.context.order.Orders;
import org.github.bodhi.hybrid.norms.web.BestsignWebFilter;
import org.github.bodhi.hybrid.support.spring.filters.GlobalServletFilter;
import org.github.bodhi.hybrid.utils.ServiceLoadUtils;

import java.util.List;

/**
 * @program: hybrid-cloud-framework
 * @description:
 * @author: Maxxx.Yg
 * @create: 2019-03-21 18:51
 **/
public class ServletFilterHolder {

    private List<BestsignWebFilter> filters;

    private final static ServletFilterHolder INSTANCE = ServletFilterHolderInner.HOLDER;

    private static GlobalServletFilter filter = new GlobalServletFilter();

    public static ServletFilterHolder getInstance() {
        return INSTANCE;
    }

    private static class ServletFilterHolderInner{
        public final static ServletFilterHolder HOLDER = new ServletFilterHolder();
    }

    public void init(ClassLoader classloader){
        List<BestsignWebFilter> webFilters = ServiceLoadUtils.loadAllInstance(BestsignWebFilter.class,classloader);
        filters = Orders.listSort(webFilters);
    }

    public List<BestsignWebFilter> getFilters() {
        return filters;
    }

    public static GlobalServletFilter getFilter() {
        return filter;
    }
}
