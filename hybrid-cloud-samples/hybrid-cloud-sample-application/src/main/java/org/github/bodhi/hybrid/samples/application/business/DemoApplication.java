package org.github.bodhi.hybrid.samples.application.business;

import org.github.bodhi.hybrid.norms.annotations.BestsignProvider;
import org.github.bodhi.hybrid.samples.application.business.entitys.DemoEntity;

/**
 * @program: hybrid-cloud-framework
 * @description: 有 @BestsignProvider 注解的接口实现类才会被容器管理并刷新，没有就是一个普通接口无法热加载
 * @author: Maxxx.Yg
 * @create: 2019-03-18 16:38
 **/
@BestsignProvider
public interface DemoApplication {

    void voidAction();

    void relayAction();

    String stringAction(String param);

    DemoEntity entityAction(DemoEntity entity);

    String patchAction();

    void error();
}
