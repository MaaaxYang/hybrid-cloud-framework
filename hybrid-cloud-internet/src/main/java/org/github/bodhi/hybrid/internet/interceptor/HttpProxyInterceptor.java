package org.github.bodhi.hybrid.internet.interceptor;

import org.github.bodhi.hybrid.context.AbstractApplicationContext;
import org.github.bodhi.hybrid.context.interceptor.ApplicationInterceptor;
import org.github.bodhi.hybrid.context.order.Order;
import org.github.bodhi.hybrid.internet.factory.HttpProxyFactory;
import org.github.bodhi.hybrid.norms.bean.BodhiBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

/**
 * @program: bodhi-distributed
 * @description:
 * @author: Maxxx.Yg
 * @create: 2019-03-13 10:58
 **/
@Order(orderNum = Integer.MIN_VALUE+1)
public class HttpProxyInterceptor implements ApplicationInterceptor {

    private static final Logger logger = LoggerFactory.getLogger(HttpProxyInterceptor.class);

    @Override
    public void execute(AbstractApplicationContext context) {
        // 包装代理类
        Map<String,BodhiBean> beanMap = context.getBeanMap();

        if (beanMap!=null && beanMap.size()>0){
            HttpProxyFactory factory = new HttpProxyFactory(context);
            for(BodhiBean bean : beanMap.values()){
                Object proxy = factory.create(bean.getApplicationClass());
                bean.setProxy(proxy);
            }
        }
    }
}
