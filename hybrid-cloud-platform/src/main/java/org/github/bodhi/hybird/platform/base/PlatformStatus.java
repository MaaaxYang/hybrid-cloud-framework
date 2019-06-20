package org.github.bodhi.hybird.platform.base;

/**
 * @program: bodhi-distributed
 * @description:
 * @author: Maxxx.Yg
 * @create: 2019-03-18 09:25
 **/
public final class PlatformStatus {

    public static volatile Status STATE = Status.CLOSED;

    public enum Status{
        INIT,
        START,
        CLOSING,
        CLOSED,
        REFRESHING,
        REFRESHED
    }


}
