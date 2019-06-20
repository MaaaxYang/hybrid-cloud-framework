package org.github.bodhi.hybrid.context.interceptor;

import org.github.bodhi.hybrid.context.AbstractApplicationContext;

/**
 * @program: bodhi-distributed
 * @description:
 * @author: Maxxx.Yg
 * @create: 2019-03-13 10:39
 **/
public interface ApplicationInterceptor {

    void execute(AbstractApplicationContext context);

}
