package org.github.bodhi.hybrid.application.adapter;

import org.github.bodhi.hybrid.norms.exception.BestsignException;
import org.github.bodhi.hybrid.norms.exception.ExceptionLevel;

/**
 * @program: hybrid-cloud-framework
 * @description:
 * @author: Maxxx.Yg
 * @create: 2019-03-22 15:21
 **/
public class BizException extends BestsignException {

    private String code;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public BizException(String code, ExceptionLevel level) {
        super(level);
        this.code = code;
    }

    public BizException(String code, String message, ExceptionLevel level) {
        super(level,message);
        this.code = code;
    }

    public BizException(String code,String message, Throwable cause, ExceptionLevel level) {
        super(message, cause, level);
        this.code = code;
    }

    public BizException(String code, Throwable cause, ExceptionLevel level) {
        super(cause, level);
        this.code = code;
    }

    public BizException(String code,String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace, ExceptionLevel level) {
        super(message, cause, enableSuppression, writableStackTrace, level);
        this.code = code;
    }

    public BizException(String code) {
        this.code = code;
    }

    public BizException(String code,String message) {
        super(message);
        this.code = code;
    }

    public BizException(String code,String message, Throwable cause) {
        super(message, cause);
        this.code = code;
    }

    public BizException(String code,Throwable cause) {
        super(cause);
        this.code = code;
    }

    public BizException(String code,String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
        this.code = code;
    }
}
