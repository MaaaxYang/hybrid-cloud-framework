package org.github.bodhi.hybrid.support.spring.exceptions;

import org.github.bodhi.hybrid.application.adapter.BizException;

/**
 * @program: hybrid-cloud-framework
 * @description:
 * @author: Maxxx.Yg
 * @create: 2019-04-01 10:29
 **/
public interface ExceptionHandler<T extends BizException> {

    Object handler(T exception);
}
