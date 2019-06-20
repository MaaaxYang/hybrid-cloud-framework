package org.github.bodhi.hybrid.context.order;

import java.util.Collections;
import java.util.List;

/**
 * @program: bodhi-distributed
 * @description:
 * @author: Maxxx.Yg
 * @create: 2019-03-13 11:30
 **/
public class Orders {

    private final static OrderComparator comparator = new OrderComparator();

    public static  <T> List<T> listSort(List<T> sourceList){
        if (sourceList!=null&&sourceList.size()>1){
            Collections.sort(sourceList,comparator);
        }
        return sourceList;
    }
}
