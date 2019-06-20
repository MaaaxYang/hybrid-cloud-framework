package org.github.bodhi.hybrid.samples.client;

import org.github.bodhi.hybrid.norms.BestsignApi;
import org.github.bodhi.hybrid.samples.application.ApplicationMenu;
import org.github.bodhi.hybrid.support.spring.aop.Cost;
import org.github.bodhi.hybrid.norms.base.ApiResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @program: hybrid-cloud-framework
 * @description:
 * @author: Maxxx.Yg
 * @create: 2019-03-29 18:41
 **/
@Cost
@RestController
@RequestMapping("/demo2")
public class Demo2Controller implements BestsignApi {

    @GetMapping("/cost")
    public ApiResult<String> cost(){
        return ApiResult.create("cost 2");
    }

    @GetMapping("/err")
    public ApiResult<String> err(){
        throw new MyException("123","ddd",System.currentTimeMillis());
    }

    @GetMapping("/info")
    public ApiResult<String> info(){
        return ApiResult.create("1.0.2");
    }

    @GetMapping("/err2")
    public ApiResult<String> err2(){
        ApplicationMenu.demoApplication().error();
        return ApiResult.error("err2");
    }
}
