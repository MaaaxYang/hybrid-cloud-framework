package org.github.bodhi.hybrid.samples.boot;

import org.github.bodhi.hybrid.internet.ClientRequest;
import org.github.bodhi.hybrid.internet.config.ClientConfig;

/**
 * @program: hybrid-cloud-framework
 * @description:
 * @author: Maxxx.Yg
 * @create: 2019-03-20 17:07
 **/
public class TestMain {

    public static void main(String[] args) {

//        BestsignConfig bestsignConfig = new BestsignConfig();
//        ClientConfig clientConfig = new ClientConfig();
//        clientConfig.setHost("http://localhost:9009");
//        BestsignBootstrap.start(bestsignConfig,clientConfig);

        ClientRequest clientRequest = ClientRequest.builder().host("123").build();
        System.out.println("");
    }

}
