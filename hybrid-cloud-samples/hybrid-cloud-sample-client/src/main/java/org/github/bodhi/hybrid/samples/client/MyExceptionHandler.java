package org.github.bodhi.hybrid.samples.client;

import org.github.bodhi.hybrid.norms.base.ApiResult;
import org.github.bodhi.hybrid.support.spring.exceptions.ExceptionHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @program: hybrid-cloud-framework
 * @description:
 * @author: Maxxx.Yg
 * @create: 2019-04-01 10:50
 **/
public class MyExceptionHandler implements ExceptionHandler<MyException> {

    private final static Logger logger = LoggerFactory.getLogger(MyExceptionHandler.class);

    @Override
    public Object handler(MyException exception) {
        logger.error("my exception",exception);
        return ApiResult.error(exception.getCode(),exception.getMessage());
    }

}
