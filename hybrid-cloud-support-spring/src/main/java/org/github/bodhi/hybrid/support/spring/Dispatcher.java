package org.github.bodhi.hybrid.support.spring;

import org.github.bodhi.hybrid.context.serializers.SerializerHolder;
import org.github.bodhi.hybrid.internet.ClientRequest;
import org.github.bodhi.hybrid.internet.client.Client;
import org.github.bodhi.hybrid.internet.client.impl.SimpleHttpClient;
import org.github.bodhi.hybrid.internet.enums.HttpMethod;
import org.github.bodhi.hybrid.internet.holder.ClientHolder;
import org.github.bodhi.hybrid.norms.base.ApiResult;
import org.github.bodhi.hybrid.support.spring.context.WebApplicationContext;
import org.github.bodhi.hybrid.support.spring.holder.WebContextHolder;
import org.github.bodhi.hybrid.support.spring.properties.ClientProperties;
import okhttp3.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.StreamUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;


/**
 * @program: bodhi-distributed
 * @description:
 * @author: Maxxx.Yg
 * @create: 2019-03-06 17:03
 **/
@Component
public class Dispatcher {

    private static Logger logger = LoggerFactory.getLogger(Dispatcher.class);

    @Autowired
    private ClientProperties clientProperties;

    private static Client client = null;

    private WebApplicationContext context;

    public ResponseEntity<byte[]> dispatch(HttpServletRequest request) throws Exception {

        if("OPTIONS".equals(request.getMethod())){
            return null;
        }

        if (context==null){
            context = WebContextHolder.getInstance().getWebApplicationContext();
        }

        if (client==null){
            synchronized (Dispatcher.class){
                if (client == null){
                    client = ClientHolder.getClientMap().get(SimpleHttpClient.class);
                }
            }
        }

        String path = request.getServletPath();
        HttpMethod method = HttpMethod.valueOf(request.getMethod());
        byte[] body = StreamUtils.copyToByteArray(request.getInputStream());
        Map<String,String> headers = new HashMap<>();
        Enumeration<String> headNames = request.getHeaderNames();
        while (headNames.hasMoreElements()){
            String headName = headNames.nextElement();
            String headValue = request.getHeader(headName);
            headers.put(headName,headValue);
        }

        Map<String,String> params = new HashMap<>();
        Map<String,String[]> parameterMap = request.getParameterMap();
        if (parameterMap!=null){
           Iterator<Map.Entry<String,String[]>> iterator = parameterMap.entrySet().iterator();
           while (iterator.hasNext()){
               Map.Entry<String,String[]> item = iterator.next();
               params.put(item.getKey(),item.getValue()[0]);
           }
        }


        ClientRequest clientRequest = new ClientRequest();
        clientRequest.setHost(clientProperties.getHost());
        clientRequest.setPath(path);
        clientRequest.setHttpMethod(method);
        clientRequest.setHeaders(headers);
        clientRequest.setBody(body);
        clientRequest.setParams(params);
        Response res = client.execute(clientRequest);

        byte[] bytes = null;
        if(res.code()==404){
            bytes = SerializerHolder.stringValue(ApiResult.warn("404 not found")).getBytes();
        }else{
            bytes = res.body().bytes();
        }

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.putAll(res.headers().toMultimap());
        ResponseEntity<byte[]> responseEntity = new ResponseEntity<>(bytes,httpHeaders,HttpStatus.OK);

        return responseEntity;

    }

}
