package org.github.bodhi.hybrid.internet.client.impl;

import org.github.bodhi.hybrid.internet.ClientRequest;
import org.github.bodhi.hybrid.internet.appointment.AfterRequestProcessFilter;
import org.github.bodhi.hybrid.internet.appointment.BeforeRequestProcessFilter;
import org.github.bodhi.hybrid.internet.client.Client;
import org.github.bodhi.hybrid.internet.filters.FilterManager;
import org.github.bodhi.hybrid.norms.exception.BodhiException;
import org.github.bodhi.hybrid.utils.StringUtils;
import okhttp3.Request;
import okhttp3.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * @program: bodhi-distributed
 * @description:
 * @author: Maxxx.Yg
 * @create: 2019-03-07 09:23
 **/
public abstract class AbstractHttpClient implements Client {

    private static final Logger logger = LoggerFactory.getLogger(AbstractHttpClient.class);

    protected ClientRequest request;

    protected String configHost;

    protected String requestHost;

    @Override
    public <T> T execute(ClientRequest request, Class<T> responseClass)throws BodhiException {
        this.request = request;
        try{
            requestHost = request.getHost();
            if (StringUtils.isNullOrEmpty(requestHost)){
                requestHost = configHost;
            }

            if (!request.isSkipFilter()){
                List<BeforeRequestProcessFilter> before = FilterManager.getBeforePlugins();

                for(BeforeRequestProcessFilter filter:before){
                    logger.info("client execute before filter : {}",filter.getClass().getName());
                    filter.before(request);
                }
            }
            Response response = onResponse(build());
            if (response == null){
                throw new BodhiException("internet response is null");
            }

            if (Response.class.equals(responseClass)){
                return (T) response;
            }else{
                if (response.code()!=200){
                    throw new BodhiException("remote server exception ："+response.body().string());
                }
            }

            T res = null;
            List<AfterRequestProcessFilter> after = FilterManager.getAfterPlugins();
            for(AfterRequestProcessFilter filter:after){
                logger.info("client execute after filter : {}",filter.getClass().getName());
                res = (T)filter.after(response,responseClass,res);
            }
            return res;

        }catch (Exception e){
            throw new BodhiException("internet request error ",e);
        }
    }

    @Override
    public Response execute(ClientRequest request) throws BodhiException {
        return execute(request,Response.class);
    }

    protected abstract Request build();

    protected abstract Response onResponse(Request request) throws Exception;

}
