package org.github.bodhi.hybrid.internet.config;


/**
 * @program: bestsign-distributed
 * @description:
 * @author: Maxxx.Yg
 * @create: 2019-03-07 09:56
 **/
public interface RemoteConfigure<T> {

    void init(ClientConfig clientConfig);

    T get();

}
