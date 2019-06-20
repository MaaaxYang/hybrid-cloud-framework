package org.github.bodhi.dev.ops;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

/**
 * @program: hybrid-cloud-framework
 * @description:
 * @author: Maxxx.Yg
 * @create: 2019-04-03 10:56
 **/
@SpringBootApplication
@EnableConfigurationProperties({
        PlatformProperties.class
})
public class Application{

    public static void main(String[] args){
        SpringApplication.run(Application.class,args);
    }

}
