package org.github.bodhi.hybrid.context.config;

import java.util.concurrent.TimeUnit;

/**
 * @program: bodhi-distributed
 * @description:
 * @author: Maxxx.Yg
 * @create: 2019-03-09 11:22
 **/
public class ThreadPoolConfig {

    public final static ThreadPoolConfig DEFAULT = new ThreadPoolConfig();

    private boolean enalbe = true;

    private int corePoolSize = Runtime.getRuntime().availableProcessors();

    private int maximumPoolSize = Runtime.getRuntime().availableProcessors()<<3;

    private long keepAliveTime = 30;

    private TimeUnit timeUnit = TimeUnit.SECONDS;

    public int getCorePoolSize() {
        return corePoolSize;
    }

    public void setCorePoolSize(int corePoolSize) {
        this.corePoolSize = corePoolSize;
    }

    public int getMaximumPoolSize() {
        return maximumPoolSize;
    }

    public void setMaximumPoolSize(int maximumPoolSize) {
        this.maximumPoolSize = maximumPoolSize;
    }

    public long getKeepAliveTime() {
        return keepAliveTime;
    }

    public void setKeepAliveTime(long keepAliveTime) {
        this.keepAliveTime = keepAliveTime;
    }

    public TimeUnit getTimeUnit() {
        return timeUnit;
    }

    public void setTimeUnit(TimeUnit timeUnit) {
        this.timeUnit = timeUnit;
    }

    public boolean isEnalbe() {
        return enalbe;
    }

    public void setEnalbe(boolean enalbe) {
        this.enalbe = enalbe;
    }
}
