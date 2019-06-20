package org.github.bodhi.hybrid.context.logs;

/**
 * @program: hybrid-cloud-framework
 * @description:
 * @author: Maxxx.Yg
 * @create: 2019-03-25 16:43
 **/
public class LogHolder {

    private final static ThreadLocal<LogEntity> logThreadLocal = new ThreadLocal<>();

    public static void set(LogEntity entity){
        logThreadLocal.set(entity);
    }

    public static LogEntity get(){
        return logThreadLocal.get();
    }

}
