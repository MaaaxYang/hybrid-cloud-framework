package org.github.bodhi.hybrid.norms.serializers;

/**
 * @program: bestsign-distributed
 * @description:
 * @author: Maxxx.Yg
 * @create: 2019-03-13 09:08
 **/
public interface Serializer<T,S>  {

    T serialize(Object obj);

    <R> R deserialize(T source,Class<R> clazz);

    S newInstance();

}
