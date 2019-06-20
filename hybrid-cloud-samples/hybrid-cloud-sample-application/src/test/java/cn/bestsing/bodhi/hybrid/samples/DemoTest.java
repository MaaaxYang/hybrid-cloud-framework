package cn.bestsing.bodhi.hybrid.samples;

import org.github.bodhi.hybrid.application.adapter.BestsignBootstrap;
import org.github.bodhi.hybrid.context.config.BodhiConfig;
import org.github.bodhi.hybrid.internet.config.ClientConfig;

/**
 * @program: hybrid-cloud-framework
 * @description:
 * @author: Maxxx.Yg
 * @create: 2019-03-19 16:39
 **/
public class DemoTest {


    public static void main(String[] args) {
        // jar 初始化
        BodhiConfig bodhiConfig = new BodhiConfig();
        bodhiConfig.setVersion("1.0.0");
        ClientConfig clientConfig = new ClientConfig();
        clientConfig.setHost("http://localhost:9999");
        BestsignBootstrap.start(bodhiConfig, clientConfig);


    }

}
