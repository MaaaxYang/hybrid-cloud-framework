package org.github.bodhi.hybrid.support.spring.holder;

import org.github.bodhi.hybrid.support.spring.context.WebApplicationContext;

/**
 * @program: bodhi-distributed
 * @description:
 * @author: Maxxx.Yg
 * @create: 2019-03-13 22:31
 **/
public class WebContextHolder {

    private static final WebContextHolder INSTANCE = new WebContextHolder();

    private WebApplicationContext webApplicationContext;

    private WebContextHolder() {
        if (INSTANCE != null) {
            throw new Error("error");
        }
    }


    public static WebContextHolder getInstance() {
        return INSTANCE;
    }

    public void setContext(WebApplicationContext context){
        INSTANCE.webApplicationContext = context;
    }

    public WebApplicationContext getWebApplicationContext() {
        return INSTANCE.webApplicationContext;
    }
}
