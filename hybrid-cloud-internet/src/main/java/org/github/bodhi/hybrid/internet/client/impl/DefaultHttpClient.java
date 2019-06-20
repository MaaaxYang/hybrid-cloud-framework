package org.github.bodhi.hybrid.internet.client.impl;

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
 * @create: 2019-03-07 10:06
 **/
public class DefaultHttpClient extends AbstractHttpClient {

    private static MediaType postMediaType = MediaType.parse("application/json; charset=utf-8");

    private static MediaType getMediaType = MediaType.parse("application/x-www-form-urlencoded; charset=utf-8");

    private OkHttpClient okHttpClient;

    public DefaultHttpClient(ClientConfig clientConfig) {
        // customize okhttp
        RemoteConfigure<OkHttpClient> okHttpHolder = new OKHttpConfigure(clientConfig);
        okHttpClient = okHttpHolder.get();
        configHost = clientConfig.getHost();
    }

    @Override
    protected Request build() {
        String url = StringUtils.combinePath(requestHost,request.getPath());

        if (request.getParams()!=null){
            String param = StringUtils.combineParams(request.getParams());
            url = url + param;
        }

        Request.Builder builder = new Request.Builder().url(url);

        if (HttpMethod.POST.equals(request.getHttpMethod())){
            builder.post(RequestBody.create(postMediaType,request.getBody()));
        }else if(HttpMethod.GET.equals(request.getHttpMethod())){
            request.getHeaders().put("Content-Type",getMediaType.toString());
            builder.get();
        }else if(HttpMethod.PUT.equals(request.getHttpMethod())){
            builder.put(RequestBody.create(postMediaType,request.getBody()));
        }else if(HttpMethod.DELETE.equals(request.getHttpMethod())){
            builder.delete(RequestBody.create(postMediaType,request.getBody()));
        }else {
            throw new BestsignException("Http method only support get or post");
        }



        for(Map.Entry<String,String> entry: request.getHeaders().entrySet()){
            builder.addHeader(entry.getKey(),entry.getValue());
        }

        return builder.build();
    }

    @Override
    protected Response onResponse(Request request) throws Exception {
        Response response = okHttpClient.newCall(request).execute();
        return response;
    }
}
