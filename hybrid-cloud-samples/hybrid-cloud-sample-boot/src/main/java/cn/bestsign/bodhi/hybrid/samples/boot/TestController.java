package org.github.bodhi.hybrid.samples.boot;

import org.github.bodhi.hybrid.support.spring.aop.Cost;
import org.github.bodhi.hybrid.norms.base.ApiResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.RequestContextHolder;

import java.util.Map;

/**
 * @program: hybrid-cloud-framework
 * @description:
 * @author: Maxxx.Yg
 * @create: 2019-03-18 17:15
 **/
@RestController
@RequestMapping("/test")
public class TestController {

    private final static Logger logger = LoggerFactory.getLogger(TestController.class);

    @GetMapping("/relay")
    public String relay() {
        System.out.println("remote controller relay");
        return "remote controller relay";
    }

    @GetMapping("/proxy")
    public String proxy() {
        System.out.println("remote controller proxy");
        return "remote controller proxy";
    }

    @GetMapping("/log")
    public String log() {
        logger.info("current thread log");
        int i = 0;
        final String val = (String) RequestContextHolder.currentRequestAttributes().getAttribute("requestId", 0);
        while (i++ < 5) {
            final Map<String, String> map = MDC.getCopyOfContextMap();
            Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    MDC.setContextMap(map);
                    logger.info("child thread log {}", val);
                }
            });
            thread.start();
        }
        return "log";
    }

    @Cost
    @GetMapping("/cost")
    public ApiResult<String> cost() {
        return ApiResult.create("request cost ? ");
    }
}
