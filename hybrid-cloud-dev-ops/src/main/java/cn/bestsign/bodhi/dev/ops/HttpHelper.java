package org.github.bodhi.dev.ops;

import org.github.bodhi.hybrid.utils.StringUtils;
import okhttp3.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @program: hybrid-cloud-framework
 * @description:
 * @author: Maxxx.Yg
 * @create: 2019-04-04 15:32
 **/
@Component
public class HttpHelper {

    @Autowired
    private PlatformProperties platformProperties;

    private OkHttpClient okHttpClient;

    @PostConstruct
    public void init(){
        okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(3,TimeUnit.SECONDS)
                .readTimeout(3,TimeUnit.SECONDS)
                .writeTimeout(3,TimeUnit.SECONDS)
                .connectionPool(new ConnectionPool(
                        4,
                        5,
                        TimeUnit.MINUTES))
                .build();
    }

    public Response post(String path,byte[] content){
        String url = url(path);
        MediaType mediaType = MediaType.parse("application/json; charset=utf-8");
        Request request = new Request.Builder()
                .url(url)
                .post(RequestBody.create(mediaType,content))
                .build()
                ;

        Response response = null;
        try {
            response = okHttpClient.newCall(request).execute();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return response;


    }

    public Response get(String path,Map<String,String> param){
        String url = url(path);

        if (param!=null){
            String paramStr = StringUtils.combineParams(param);
            url = url + paramStr;
        }

        Request request = new Request.Builder()
                .url(url)
                .get()
                .build()
                ;

        Response response = null;
        try {
            response = okHttpClient.newCall(request).execute();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return response;
    }



    private String url(String path){
        StringBuilder builder = new StringBuilder("http://")
                .append(platformProperties.getHost())
                .append(":")
                .append(platformProperties.getPort())
                .append(path);
        return builder.toString();
    }
}
