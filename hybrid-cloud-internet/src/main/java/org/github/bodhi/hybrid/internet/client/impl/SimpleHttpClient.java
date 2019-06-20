package org.github.bodhi.hybrid.internet.client.impl;

import org.github.bodhi.hybrid.internet.ClientRequest;
import org.github.bodhi.hybrid.internet.client.Client;
import org.github.bodhi.hybrid.internet.config.ClientConfig;
import org.github.bodhi.hybrid.internet.enums.HttpMethod;
import org.github.bodhi.hybrid.internet.config.RemoteConfigure;
import org.github.bodhi.hybrid.internet.config.OKHttpConfigure;
import org.github.bodhi.hybrid.norms.exception.BestsignException;
import org.github.bodhi.hybrid.utils.StringUtils;
import okhttp3.*;

import java.util.Map;

/**
 * @program: bestsign-distributed
 * @description:
 * @author: Maxxx.Yg
 * @create: 2019-03-07 11:06
 **/
public class SimpleHttpClient implements Client {

    private OkHttpClient okHttpClient;

    public SimpleHttpClient(ClientConfig config) {
        RemoteConfigure<OkHttpClient> okHttpHolder = new OKHttpConfigure(config);
        this.okHttpClient = okHttpHolder.get();
    }

    private Request build(ClientRequest request) {
        // url
        String url = StringUtils.combinePath(request.getHost(),request.getPath());

        if (request.getParams()!=null){
            String param = StringUtils.combineParams(request.getParams());
            url = url + param;
        }

        // build
        Request.Builder builder = new Request.Builder().url(url);

        // media type
        String media = request.getHeaders().get("Content-Type");
        if (media==null){
            media = "application/json; charset=utf-8";
        }
        MediaType mediaType = MediaType.parse(media);

        if (HttpMethod.POST.equals(request.getHttpMethod())){
            builder.post(RequestBody.create(mediaType,request.getBody()));
        }else if(HttpMethod.GET.equals(request.getHttpMethod())){
            builder.get();
        }else if(HttpMethod.PUT.equals(request.getHttpMethod())){
            builder.put(RequestBody.create(mediaType,request.getBody()));
        }else if(HttpMethod.DELETE.equals(request.getHttpMethod())){
            builder.delete(RequestBody.create(mediaType,request.getBody()));
        }else {
            throw new BestsignException("Http method only support get or post");
        }

        for(Map.Entry<String,String> entry: request.getHeaders().entrySet()){
            builder.addHeader(entry.getKey(),entry.getValue());
        }

        return builder.build();
    }


    @Override
    public <T> T execute(ClientRequest request, Class<T> responseClass) throws BestsignException {
        if (!Response.class.equals(responseClass)){
            throw new BestsignException("transfer http client only support okhttp3 response");
        }
        try {
            Response response = onResponse(build(request));
            return (T)response;
        } catch (Exception e) {
            throw new BestsignException(e);
        }
    }

    @Override
    public Response execute(ClientRequest request) throws BestsignException {
        return execute(request,Response.class);
    }

    private Response onResponse(Request request) throws Exception {
        return okHttpClient.newCall(request).execute();
    }

}
