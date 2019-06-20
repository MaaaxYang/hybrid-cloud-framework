package cn.bestsing.bodhi.hybrid.samples;

import org.github.bodhi.hybrid.application.adapter.BestsignBootstrap;
import org.github.bodhi.hybrid.context.config.BestsignConfig;
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
        BestsignConfig bestsignConfig = new BestsignConfig();
        bestsignConfig.setVersion("1.0.0");
        ClientConfig clientConfig = new ClientConfig();
        clientConfig.setHost("http://localhost:9999");
        BestsignBootstrap.start(bestsignConfig, clientConfig);


    }

}
