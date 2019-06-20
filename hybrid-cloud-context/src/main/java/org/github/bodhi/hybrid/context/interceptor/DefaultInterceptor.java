package org.github.bodhi.hybrid.context.interceptor;

import org.github.bodhi.hybrid.context.AbstractApplicationContext;
import org.github.bodhi.hybrid.context.order.Order;
import org.github.bodhi.hybrid.norms.BestsignApplication;
import org.github.bodhi.hybrid.norms.annotations.BestsignProvider;
import org.github.bodhi.hybrid.norms.bean.BestsignBean;
import org.github.bodhi.hybrid.norms.exception.BestsignException;
import org.github.bodhi.hybrid.utils.StringUtils;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @program: bestsign-distributed
 * @description:
 * @author: Maxxx.Yg
 * @create: 2019-03-13 10:50
 **/
@Order(orderNum = Integer.MIN_VALUE)
public class DefaultInterceptor implements ApplicationInterceptor {

    @Override
    public void execute(AbstractApplicationContext context) {
        Map<String,BestsignBean> beanMap = context.getBeanMap();
        // factory proxy wapper
        for (BestsignApplication app : context.applicaitons()) {
            Class appClass = app.getClass();
            Class[] interfaces = appClass.getInterfaces();
            if (interfaces == null || interfaces.length == 0) {
                throw new BestsignException(appClass.getCanonicalName() + " must implement a interface");
            }

            List<Class> matchInterface = new ArrayList<>();
            for (Class clz : interfaces) {
                if (clz.getAnnotation(BestsignProvider.class) != null) {
                    matchInterface.add(clz);
                }
            }
            if (matchInterface.isEmpty()) {
                throw new BestsignException(appClass.getCanonicalName() + " must implement a interface and the interface has annotation @ProcessProvider");
            }
            for (Class itf : matchInterface) {

                String key = itf.getCanonicalName();
                String alias = "";
                Annotation provider = itf.getAnnotation(BestsignProvider.class);
                if (provider != null) {
                    BestsignProvider pp = (BestsignProvider) provider;
                    alias = pp.value();
                    if (!StringUtils.isNullOrEmpty(alias)) {
                        key = alias;
                    }
                }

                // create bean
                BestsignBean bestsignBean = new BestsignBean();
                bestsignBean.setName(itf.getCanonicalName());
                bestsignBean.setAlias(alias);
                bestsignBean.setApplication(app);
                bestsignBean.setApplicationClass(appClass);

                beanMap.put(key,bestsignBean);
            }
        }
    }

}
