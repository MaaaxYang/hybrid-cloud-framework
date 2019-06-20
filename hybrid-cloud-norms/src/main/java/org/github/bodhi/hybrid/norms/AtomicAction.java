package org.github.bodhi.hybrid.norms;

/**
 * @program: hybrid-cloud-framework
 * @description:
 * @author: Maxxx.Yg
 * @create: 2019-03-26 09:50
 **/
public interface AtomicAction<R> {

    <T> R invoke(T obj);

}
