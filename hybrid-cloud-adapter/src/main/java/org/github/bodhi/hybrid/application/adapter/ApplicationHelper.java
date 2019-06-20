package org.github.bodhi.hybrid.application.adapter;

import org.github.bodhi.hybird.platform.base.PlatformManager;
import org.github.bodhi.hybrid.norms.ApplicationContext;
import org.github.bodhi.hybrid.norms.bean.BodhiBean;

/**
 * @program: bodhi-distributed
 * @description:
 * @author: Maxxx.Yg
 * @create: 2019-03-14 16:10
 **/
public class ApplicationHelper {

    public static <T> T getApp(Class<T> clazz){

        ApplicationContext context = PlatformManager.getContext();
        BodhiBean bean = context.getBean(clazz);

        if (bean==null){
            return null;
        }

        if (bean.getProxy()!=null){
            return (T) bean.getProxy();
        }else{
            return (T) bean.getApplication();
        }
    }

}
