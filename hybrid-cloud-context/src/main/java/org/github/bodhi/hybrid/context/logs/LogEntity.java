package org.github.bodhi.hybrid.context.logs;

import org.github.bodhi.hybrid.norms.exception.ExceptionLevel;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.io.Serializable;
import java.util.Date;

/**
 * @program: hybrid-cloud-framework
 * @description:
 * @author: Maxxx.Yg
 * @create: 2019-03-25 16:28
 **/
public class LogEntity implements Serializable {

    private static final long serialVersionUID = -8125174572328419393L;

    /**
     * requestId
     */
    private String reqid;

    /**
     * request start time
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss:SSS",timezone = "GMT+8")
    private Date startTime;

    /**
     * requestPath
     */
    private String module;

    /**
     * developerId / clientId
     */
    private String gourpId;

    /**
     * request cost
     */
    private long cost;

    /**
     * success/BizError
     */
    private String result = "success";

    /**
     * response code
     */
    private String status;

    /**
     * response message
     */
    private String msg;

    /**
     * requestPath
     */
    private String object;

    /**
     * ExceptionLevel
     */
    private ExceptionLevel level;


    public String getModule() {
        return module;
    }

    public void setModule(String module) {
        this.module = module;
    }

    public String getGourpId() {
        return gourpId;
    }

    public void setGourpId(String gourpId) {
        this.gourpId = gourpId;
    }

    public String getReqid() {
        return reqid;
    }

    public void setReqid(String reqid) {
        this.reqid = reqid;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public long getCost() {
        return cost;
    }

    public void setCost(long cost) {
        this.cost = cost;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getObject() {
        return object;
    }

    public void setObject(String object) {
        this.object = object;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public ExceptionLevel getLevel() {
        return level;
    }

    public void setLevel(ExceptionLevel level) {
        this.level = level;
    }
}
