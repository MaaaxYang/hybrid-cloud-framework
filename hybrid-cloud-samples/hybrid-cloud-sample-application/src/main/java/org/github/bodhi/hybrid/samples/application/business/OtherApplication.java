package org.github.bodhi.hybrid.samples.application.business;

/**
 * @program: hybrid-cloud-framework
 * @description: 有 @BestsignProvider 注解的接口实现类才会被容器管理并刷新，没有就是一个普通接口无法热加载
 * @author: Maxxx.Yg
 * @create: 2019-03-18 16:43
 **/
public interface OtherApplication {

    void nothing();

}
