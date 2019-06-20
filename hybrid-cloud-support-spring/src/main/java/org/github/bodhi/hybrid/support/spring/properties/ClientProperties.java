package org.github.bodhi.hybrid.support.spring.properties;


import org.github.bodhi.hybrid.internet.config.ClientConfig;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @program: bodhi-distributed
 * @description:
 * @author: Maxxx.Yg
 * @create: 2019-03-06 18:16
 **/
@ConfigurationProperties("bodhi.client")
public class ClientProperties extends ClientConfig {

}
