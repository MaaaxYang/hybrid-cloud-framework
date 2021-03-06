package org.github.bodhi.hybrid.norms;


import org.github.bodhi.hybrid.norms.bean.BodhiBean;
import org.github.bodhi.hybrid.norms.event.BestsignEventPublisher;
import org.github.bodhi.hybrid.norms.serializers.Serializer;

import java.util.List;
import java.util.concurrent.ExecutorService;

/**
 * @program: bodhi-distributed
 * @description:
 * @author: Maxxx.Yg
 * @create: 2019-03-09 10:28
 **/
public interface ApplicationContext extends LifeCycle {

    <T extends BodhiBean> T getBean(String canonicalName);

    <T extends BodhiBean> T getBean(Class clazz);

    /**----------------------------------------------**/

    void setClassloader(ClassLoader loader);

    ClassLoader getClassloader();

    void setExecutor(ExecutorService executor);

    ExecutorService getExecutor();

    <T,S> void setSerializer(Serializer<T,S> serializer);

    <T,S> Serializer<T,S> getSerializer();

    void setPublisher(BestsignEventPublisher publisher);

    BestsignEventPublisher getPublisher();

    List<BestsignApplication> applicaitons();
}
