package org.github.bodhi.hybrid.norms.exception;

/**
 * @program: bodhi-distributed
 * @description:
 * @author: Maxxx.Yg
 * @create: 2019-02-19 16:09
 **/
public class BodhiException extends RuntimeException {

    /**
     * 错误级别
     */
    protected ExceptionLevel level = ExceptionLevel.ENDURE;

    public BodhiException(ExceptionLevel level) {
        this.level = level;
    }

    public BodhiException(ExceptionLevel level, String message) {
        super(message);
        this.level = level;
    }

    public BodhiException(String message, Throwable cause, ExceptionLevel level) {
        super(message, cause);
        this.level = level;
    }

    public BodhiException(Throwable cause, ExceptionLevel level) {
        super(cause);
        this.level = level;
    }

    public BodhiException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace, ExceptionLevel level) {
        super(message, cause, enableSuppression, writableStackTrace);
        this.level = level;
    }

    public BodhiException() {
        this.level = ExceptionLevel.ENDURE;
    }

    public BodhiException(String message) {
        super(message);
    }

    public BodhiException(String message, Throwable cause) {
        super(message, cause);
    }

    public BodhiException(Throwable cause) {
        super(cause);
    }

    public BodhiException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }


    public ExceptionLevel getLevel() {
        return level;
    }

    public void setLevel(ExceptionLevel level) {
        this.level = level;
    }
}
