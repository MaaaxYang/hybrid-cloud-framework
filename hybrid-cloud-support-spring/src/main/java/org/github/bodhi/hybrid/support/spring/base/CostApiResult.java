package org.github.bodhi.hybrid.support.spring.base;

import org.github.bodhi.hybrid.norms.base.ApiResult;

import java.io.Serializable;

/**
 * @program: hybrid-cloud-framework
 * @description:
 * @author: Maxxx.Yg
 * @create: 2019-03-29 17:03
 **/
public class CostApiResult<T> extends ApiResult<T> implements Serializable {

    private static final long serialVersionUID = -707923570416459167L;

    private long cost;

    public long getCost() {
        return cost;
    }

    public void setCost(long cost) {
        this.cost = cost;
    }

    public CostApiResult(long cost,ApiResult<T> result) {
        this.cost = cost;
        super.setCode(result.getCode());
        super.setMessage(result.getMessage());
        super.setData(result.getData());
    }

    public CostApiResult(){

    }
}
