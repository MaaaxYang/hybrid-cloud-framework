package org.github.bodhi.hybrid.context.order;

import java.util.Comparator;

/**
 * @program: hybrid-cloud-framework
 * @description:
 * @author: Maxxx.Yg
 * @create: 2019-03-21 18:02
 **/
public class OrderComparator implements Comparator<Object> {

    @Override
    public int compare(Object o1, Object o2) {
        int i1 = getOrder(o1);
        int i2 = getOrder(o2);
        return (i1 < i2) ? -1 : (i1 > i2) ? 1 : 0;
    }


    private int getOrder(Object object){
        if (object==null){
            return 0;
        }
        Order order = object.getClass().getAnnotation(Order.class);
        if(order!=null){
            return order.orderNum();
        }
        return 0;
    }
}
