package org.github.bodhi.hybrid.norms.exception;

/**
 * @program: hybrid-cloud-framework
 * @description:
 * @author: Maxxx.Yg
 * @create: 2019-03-21 13:44
 **/
public enum  StandardCode {
    SUCCESS("0"),
    WARN("700"),
    ERROR("900")
    ;

    StandardCode(String code) {
        this.code = code;
    }

    private String code;


    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

}
