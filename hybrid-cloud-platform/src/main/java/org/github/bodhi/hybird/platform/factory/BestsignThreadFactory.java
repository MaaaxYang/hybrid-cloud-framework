package org.github.bodhi.hybird.platform.factory;

import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicLong;

/**
 * @program: bestsign-distributed
 * @description:
 * @author: Maxxx.Yg
 * @create: 2019-03-09 11:45
 **/
public class BestsignThreadFactory implements ThreadFactory {

    private static final AtomicLong THREAD_NUMBER = new AtomicLong(1);

    private static final ThreadGroup THREAD_GROUP = new ThreadGroup("Bestsign");

    private static final String THREAD_NAME_PREFIX = "Bestsign-Process-";

    private ClassLoader classLoader;

    public BestsignThreadFactory(ClassLoader classLoader) {
        this.classLoader = classLoader;
    }

    @Override
    public Thread newThread(Runnable r) {
        Thread thread = new Thread(THREAD_GROUP,r,THREAD_NAME_PREFIX+THREAD_NUMBER.getAndIncrement());
        thread.setContextClassLoader(classLoader);
        return thread;
    }

}
