package org.github.bodhi.hybrid.application.adapter.repository;

import org.github.bodhi.hybrid.context.serializers.SerializerHolder;
import org.github.bodhi.hybrid.internet.ClientRequest;
import org.github.bodhi.hybrid.internet.client.Client;
import org.github.bodhi.hybrid.internet.client.impl.SimpleHttpClient;
import org.github.bodhi.hybrid.internet.config.ClientConfig;
import org.github.bodhi.hybrid.internet.enums.HttpMethod;
import org.github.bodhi.hybrid.norms.base.ApiResult;
import org.github.bodhi.hybrid.norms.exception.BodhiException;
import com.fasterxml.jackson.core.type.TypeReference;
import okhttp3.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

/**
 * @program: hybrid-cloud-framework
 * @description:
 * @author: Maxxx.Yg
 * @create: 2019-04-07 14:37
 **/
public class RepositoryManager {

    private final static Logger logger = LoggerFactory.getLogger(RepositoryManager.class);

    private static Client client = null;

    private static String host = null;

    public static void init(String repositoryHost){
        ClientConfig clientConfig = new ClientConfig();
        clientConfig.setConnectTimeoutSeconds(120);
        clientConfig.setKeepAliveDuration(10);
        clientConfig.setReadTimeoutSeconds(120);
        clientConfig.setWriteTimeoutSeconds(120);
        clientConfig.setHost(repositoryHost);
        clientConfig.setMaxIdleConnections(1);
        host = repositoryHost;
        client = new SimpleHttpClient(clientConfig);

    }

    public static ServiceMap versionMap(String clientId, String version){
        Map<String,String> params = new HashMap<>();
        params.put("clientId",clientId);
        params.put("version",version);

        ClientRequest request = ClientRequest.builder()
                .host(host)
                .path("repository/map")
                .httpMethod(HttpMethod.GET)
                .params(params)
                .build();


        Response response = client.execute(request);
        String res = null;
        try {
            res = response.body().string();
        } catch (Exception e) {
            throw new BodhiException(e);
        }

        ApiResult<ServiceMap> map = SerializerHolder.toObject(res, new TypeReference<ApiResult<ServiceMap>>() {});
        return map.getData();
    }

    public static InputStream pull(String id,String fileName){
        Map<String,String> params = new HashMap<>();
        params.put("id",id);
        params.put("fileName",fileName);

        ClientRequest request = ClientRequest.builder()
                .host(host)
                .path("repository/pull")
                .httpMethod(HttpMethod.GET)
                .params(params)
                .build();

        logger.info("from repository pull id:{}",id);
        Response response = client.execute(request);
        InputStream res = response.body().byteStream();
        return res;
    }


}
