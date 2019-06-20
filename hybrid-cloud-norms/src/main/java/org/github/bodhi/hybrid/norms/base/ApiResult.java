package org.github.bodhi.hybrid.norms.base;

import org.github.bodhi.hybrid.norms.exception.StandardCode;

import java.io.Serializable;

/**
 * @program: bodhi-distributed
 * @description:
 * @author: Maxxx.Yg
 * @create: 2019-03-06 16:07
 **/
public class ApiResult<T> implements Serializable {

    private static final long serialVersionUID = -5562689566824249907L;

    private String code;

    private T data;

    private String message;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public ApiResult(String code, T data, String message) {
        this.code = code;
        this.data = data;
        this.message = message;
    }


    public ApiResult() {

    }

    public static <T> ApiResult<T> create(T data){
        ApiResult<T> result = new ApiResult<>();
        result.setCode(StandardCode.SUCCESS.getCode());
        result.setData(data);
        return result;
    }

    public static <T> ApiResult<T> warn(String message){
        ApiResult<T> result = new ApiResult<>();
        result.setCode(StandardCode.WARN.getCode());
        result.setMessage(message);
        return result;
    }

    public static <T> ApiResult<T> error(String message){
        ApiResult<T> result = new ApiResult<>();
        result.setCode(StandardCode.ERROR.getCode());
        result.setMessage(message);
        return result;
    }

    public static <T> ApiResult<T> error(String code,String message){
        ApiResult<T> result = new ApiResult<>();
        result.setCode(code);
        result.setMessage(message);
        return result;
    }

}
