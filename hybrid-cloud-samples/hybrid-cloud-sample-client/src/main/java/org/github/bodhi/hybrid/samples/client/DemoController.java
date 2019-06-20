package org.github.bodhi.hybrid.samples.client;

import org.github.bodhi.hybrid.application.adapter.BizException;
import org.github.bodhi.hybrid.norms.BestsignApi;
import org.github.bodhi.hybrid.norms.exception.BodhiException;
import org.github.bodhi.hybrid.norms.exception.ExceptionLevel;
import org.github.bodhi.hybrid.samples.application.ApplicationMenu;
import org.github.bodhi.hybrid.samples.application.business.entitys.DemoEntity;
import org.github.bodhi.hybrid.support.spring.aop.Cost;
import org.github.bodhi.hybrid.norms.base.ApiResult;
import org.springframework.web.bind.annotation.*;

/**
 * @program: hybrid-cloud-framework
 * @description:
 * @author: Maxxx.Yg
 * @create: 2019-03-18 16:56
 **/
@RestController
@RequestMapping("/demo")
public class DemoController implements BestsignApi {

    @GetMapping("/void")
    public void voidAction() {
        ApplicationMenu.demoApplication().voidAction();
    }

    @GetMapping("/relay")
    public ApiResult<String> relayAction() {
        ApplicationMenu.demoApplication().relayAction();
        return ApiResult.create("relay 1.0.2");
    }

    @GetMapping("/string/{param}")
    public String stringAction(@PathVariable("param") String param) {
        return ApplicationMenu.demoApplication().stringAction(param);
    }

    @PostMapping("/entity")
    public DemoEntity entityAction(@RequestBody DemoEntity entity) {
        return ApplicationMenu.demoApplication().entityAction(entity);
    }

    @GetMapping("/nothing")
    public String nothing() {
        ApplicationMenu.otherApplication().nothing();
        return "nothing";
    }

    @GetMapping("/patch")
    public String patch() {
        return ApplicationMenu.demoApplication().patchAction();
    }

    @GetMapping("/proxy")
    public String proxy() {
        return ApplicationMenu.proxyApplication().proxy();
    }


    @Cost
    @GetMapping("/cost")
    public ApiResult<String> cost(){
        return ApiResult.create("demo cost?");
    }

    @Cost
    @GetMapping("/costerr")
    public ApiResult<String> costerr(){
        throw new BizException("400123","cost err");
    }

    @Cost
    @GetMapping("/costerr2")
    public ApiResult<String> costerr2(){
        throw new BodhiException(ExceptionLevel.NORMAL,"cost err");
    }

}
