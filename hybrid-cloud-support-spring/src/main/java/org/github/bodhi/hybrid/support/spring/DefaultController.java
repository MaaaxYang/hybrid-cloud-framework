package org.github.bodhi.hybrid.support.spring;

import org.github.bodhi.hybird.platform.base.PlatformManager;
import org.github.bodhi.hybrid.application.adapter.InitializeHelper;
import org.github.bodhi.hybrid.context.config.ClassPathConfig;
import org.github.bodhi.hybrid.context.config.RefreshConfig;
import org.github.bodhi.hybrid.norms.base.ApiResult;
import org.github.bodhi.hybrid.support.spring.context.WebApplicationContext;
import org.github.bodhi.hybrid.support.spring.properties.BodhiProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;


/**
 * @program: bodhi-distributed
 * @description:
 * @author: Maxxx.Yg
 * @create: 2019-03-06 15:07
 **/
@RestController
public class DefaultController {

    @Autowired
    private Dispatcher dispatcher;

    @Autowired
    private BodhiProperties properties;

    @RequestMapping("/**")
    public ResponseEntity<byte[]> dispatch(HttpServletRequest request) throws Exception {
        return dispatcher.dispatch(request);
    }

    @GetMapping("/version/update")
    public ApiResult updateVersion(@RequestParam("ver") String ver){

        // FileDownloadHelper.download(ver,properties.getPublishHost(), PathMenu.SERVICE.getPath());

        ClassPathConfig classPathConfig = InitializeHelper.run(properties.getClientId(),ver);

        // 文件已存在
        RefreshConfig config = new RefreshConfig();
        config.setVersion(ver);
        config.setBasePath(properties.getBasePath());
        config.setClassPathConfig(classPathConfig);
        PlatformManager.refreshApplication(config,WebApplicationContext.class);

        properties.setVersion(ver);
        return ApiResult.create("refreshed !!");
    }
}
