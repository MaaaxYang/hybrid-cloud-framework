package org.github.bodhi.hybrid.support.spring.holder;

import org.github.bodhi.hybrid.support.spring.exceptions.ExceptionHandler;
import org.github.bodhi.hybrid.utils.ServiceLoadUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @program: hybrid-cloud-framework
 * @description:
 * @author: Maxxx.Yg
 * @create: 2019-04-01 10:31
 **/
public class ExceptionHolder {

    private final static Logger logger = LoggerFactory.getLogger(ExceptionHolder.class);

    private final static Map<String,ExceptionHandler> handlerMap = new ConcurrentHashMap<>(16);

    public static ExceptionHandler get(Throwable e){
        return handlerMap.get(e);
    }

    public static void init(ClassLoader classloader){
        List<ExceptionHandler> handlers = ServiceLoadUtils.loadAllInstance(ExceptionHandler.class,classloader);
        if (!CollectionUtils.isEmpty(handlers)){
            Iterator<ExceptionHandler> iterator = handlers.iterator();
            while (iterator.hasNext()){
                ExceptionHandler handler = iterator.next();
                logger.info("exception handler '{}' register",handler.getClass().getCanonicalName());
                String name = handler.getClass().getMethods()[0].getParameterTypes()[0].getCanonicalName();
                handlerMap.put(name,handler);
            }
        }
    }
}
