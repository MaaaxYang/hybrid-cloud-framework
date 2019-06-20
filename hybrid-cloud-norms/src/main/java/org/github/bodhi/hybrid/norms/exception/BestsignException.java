package org.github.bodhi.hybrid.norms.exception;

/**
 * @program: bestsign-distributed
 * @description:
 * @author: Maxxx.Yg
 * @create: 2019-02-19 16:09
 **/
public class BestsignException extends RuntimeException {

    /**
     * 错误级别
     */
    protected ExceptionLevel level = ExceptionLevel.ENDURE;

    public BestsignException(ExceptionLevel level) {
        this.level = level;
    }

    public BestsignException(ExceptionLevel level,String message) {
        super(message);
        this.level = level;
    }

    public BestsignException(String message, Throwable cause, ExceptionLevel level) {
        super(message, cause);
        this.level = level;
    }

    public BestsignException(Throwable cause, ExceptionLevel level) {
        super(cause);
        this.level = level;
    }

    public BestsignException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace, ExceptionLevel level) {
        super(message, cause, enableSuppression, writableStackTrace);
        this.level = level;
    }

    public BestsignException() {
        this.level = ExceptionLevel.ENDURE;
    }

    public BestsignException(String message) {
        super(message);
    }

    public BestsignException(String message, Throwable cause) {
        super(message, cause);
    }

    public BestsignException(Throwable cause) {
        super(cause);
    }

    public BestsignException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }


    public ExceptionLevel getLevel() {
        return level;
    }

    public void setLevel(ExceptionLevel level) {
        this.level = level;
    }
}
