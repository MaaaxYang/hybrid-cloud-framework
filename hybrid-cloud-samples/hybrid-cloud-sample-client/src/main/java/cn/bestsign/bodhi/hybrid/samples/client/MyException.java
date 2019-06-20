package org.github.bodhi.hybrid.samples.client;

import org.github.bodhi.hybrid.application.adapter.BizException;

/**
 * @program: hybrid-cloud-framework
 * @description:
 * @author: Maxxx.Yg
 * @create: 2019-04-01 10:51
 **/
public class MyException extends BizException {

    private long time;

    public MyException(String code, String message) {
        super(code, message);
    }

    public MyException(String code, String message, long time) {
        super(code, message);
        this.time = time;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }
}
