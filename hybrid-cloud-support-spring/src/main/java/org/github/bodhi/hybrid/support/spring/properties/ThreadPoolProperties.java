package org.github.bodhi.hybrid.support.spring.properties;

import org.github.bodhi.hybrid.context.config.ThreadPoolConfig;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @program: bestsign-distributed
 * @description:
 * @author: Maxxx.Yg
 * @create: 2019-03-13 21:16
 **/
@ConfigurationProperties("bestsign.thread")
public class ThreadPoolProperties extends ThreadPoolConfig {

}
