package org.github.bodhi.hybrid.internet.appointment;


import okhttp3.Response;

/**
 * @program: hybrid-cloud-framework
 * @description:
 * @author: Maxxx.Yg
 * @create: 2019-03-18 14:34
 **/
public interface AfterRequestProcessFilter<S> extends ClientFilter{

    <T> T after(Response response,Class<T> responseClass,Object prev);

}
