package org.github.bodhi.hybrid.internet.holder;

import org.github.bodhi.hybrid.context.config.BodhiConfig;
import org.github.bodhi.hybrid.internet.client.Client;
import org.github.bodhi.hybrid.internet.client.impl.DefaultHttpClient;
import org.github.bodhi.hybrid.internet.client.impl.SimpleHttpClient;
import org.github.bodhi.hybrid.internet.config.ClientConfig;
import org.github.bodhi.hybrid.norms.ApplicationContext;

import java.util.HashMap;
import java.util.Map;

/**
 * @program: bodhi-distributed
 * @description:
 * @author: Maxxx.Yg
 * @create: 2019-03-13 23:29
 **/
public class ClientHolder {

    private static Map<Class<? extends Client>,Client> clientMap = new HashMap<>();

    private static ClientConfig config;

    public static void init(ApplicationContext context, BodhiConfig bodhiConfig, ClientConfig clientConfig){
        config = clientConfig;
        clientMap.put(DefaultHttpClient.class,new DefaultHttpClient(clientConfig));
        clientMap.put(SimpleHttpClient.class,new SimpleHttpClient(clientConfig));
    }

    public static Map<Class<? extends Client>, Client> getClientMap() {
        return clientMap;
    }

    public static void setClientMap(Map<Class<? extends Client>, Client> clientMap) {
        ClientHolder.clientMap = clientMap;
    }

    public static ClientConfig getConfig() {
        return config;
    }
}
