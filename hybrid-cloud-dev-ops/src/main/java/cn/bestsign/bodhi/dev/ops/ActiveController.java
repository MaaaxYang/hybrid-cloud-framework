package org.github.bodhi.dev.ops;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * @program: hybrid-cloud-framework
 * @description:
 * @author: Maxxx.Yg
 * @create: 2019-04-03 10:42
 **/
@RestController
public class ActiveController {

    @Autowired
    private HttpHelper httpHelper;

    @PostMapping("/privateKey")
    public String privateKey(@RequestBody String privateKey){
        System.out.println(privateKey);
        httpHelper.post("/config/privateKey",privateKey.getBytes());
        return "ok";
    }

    @PostMapping("/registerCode")
    public String config(@RequestBody String code){
        System.out.println(code);
        httpHelper.post("/config/registerCode",code.getBytes());
        return "ok";
    }

    @PostMapping("/load")
    public String load(){
        System.out.println("load");
        httpHelper.post("/config/load",null);
        return "system-load";
    }


    private LinkedBlockingQueue<String> list = new LinkedBlockingQueue<>();

    @PostConstruct
    public void init(){
        list.add("["+System.currentTimeMillis() + "] - init start ...");
        list.add("["+System.currentTimeMillis() +"] - account : ssq@bestsign.cn");
        list.add("["+System.currentTimeMillis() +"] - developerId : 17779328403249");
        list.add("["+System.currentTimeMillis() +"] - clientId : 17779328403249");
        list.add("["+System.currentTimeMillis() +"] - AccessKey : d8c111924c5b4762b983422df7484c40");
        list.add("["+System.currentTimeMillis() +"] - base library downloading ...");
        list.add("["+System.currentTimeMillis() +"] - base library download success");
        list.add("["+System.currentTimeMillis() +"] - hybrid-cloud-signature-calculation downloading ...");
        list.add("["+System.currentTimeMillis() +"] - hybrid-cloud-signature-calculation download success");
        list.add("["+System.currentTimeMillis() +"] - hybrid-cloud-signature-validation downloading ...");
        list.add("["+System.currentTimeMillis() +"] - hybrid-cloud-signature-validation download success");
        list.add("["+System.currentTimeMillis() +"] - hybrid-cloud-storage downloading ...");
        list.add("["+System.currentTimeMillis() +"] - hybrid-cloud-storage download success");
        list.add("["+System.currentTimeMillis() +"] - bestsign application downloading ...");
        list.add("["+System.currentTimeMillis() +"] - bestsign application download success");
        list.add("["+System.currentTimeMillis() +"] - bestsign client downloading ...");
        list.add("["+System.currentTimeMillis() +"] - bestsign client download success");
        list.add("["+System.currentTimeMillis() +"] - all module download success");
        list.add("["+System.currentTimeMillis() +"] - refreshing .. ");
        list.add("["+System.currentTimeMillis() +"] - bestsign service started");
        list.add("load end");
    }

    @GetMapping("/load/print")
    public List<String> print() throws IOException {

//        Response response = httpHelper.post("/config/load/print",null);
//        if (response!=null){
//            response.body().string();
//        }
//        return null;

        if (list.isEmpty()){
            init();
            return new ArrayList<>();
        }

        return Arrays.asList(list.poll());
    }

}
