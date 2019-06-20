package org.github.bodhi.hybrid.internet.client.interceptor;

import org.github.bodhi.hybrid.context.logs.LogEntity;
import org.github.bodhi.hybrid.context.logs.LogHolder;
import org.github.bodhi.hybrid.context.serializers.SerializerHolder;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Date;
import java.util.UUID;

/**
 * @program: bestsign-distributed
 * @description:
 * @author: Maxxx.Yg
 * @create: 2019-03-05 16:58
 **/
public class HttpClientInterceptor implements Interceptor {

    private static final Logger logger = LoggerFactory.getLogger(HttpClientInterceptor.class);

    @Override
    public Response intercept(Chain chain) throws IOException {

        Request request = chain.request();

        LogEntity parent = LogHolder.get();
        LogEntity logEntity = new LogEntity();
        if (parent!=null){
            logEntity.setReqid(parent.getReqid());
        }else {
            String requestId = UUID.randomUUID().toString();
            logEntity.setReqid(requestId);
            request = request.newBuilder()
                    .addHeader("requestId",requestId)
                    .build()
            ;
        }

        logEntity.setStartTime(new Date());
        logEntity.setGourpId(logEntity.getGourpId());
        logEntity.setModule(request.url().toString());
        logEntity.setObject(request.url().toString());

        long startTime = System.currentTimeMillis();

        Response response =  chain.proceed(request);

        long endTime = System.currentTimeMillis();

        logEntity.setCost(endTime-startTime);
        logEntity.setStatus(String.valueOf(response.code()));
        logEntity.setMsg(response.message());
        logEntity.setResult("success");

        logger.info(SerializerHolder.stringValue(logEntity));

        return response;

    }

}
