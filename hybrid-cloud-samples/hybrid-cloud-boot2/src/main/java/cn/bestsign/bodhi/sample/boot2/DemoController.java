package org.github.bodhi.sample.boot2;

import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

/**
 * @program: hybrid-cloud-framework
 * @description:
 * @author: Maxxx.Yg
 * @create: 2019-03-21 09:25
 **/
@RestController
public class DemoController {

    @GetMapping("/demo/boot2")
    public String boot2() {
        return "boot2";
    }


    @GetMapping("/service/publish")
    public String publish(@RequestParam("ver") String ver) {
        return "http://localhost:10001/service/download";
    }

    @GetMapping("/service/download")
    public void download(HttpServletResponse response) throws IOException {
        byte[] bytes = StreamUtils.copyToByteArray(Files.newInputStream(Paths.get("/Users/yanghaoran/coding/bodhi/lib/demo.jar"), StandardOpenOption.READ));
        response.getOutputStream().write(bytes);
        response.getOutputStream().flush();
    }

    @PostMapping("/boot/post")
    public String post(){
        return "4567890-dd";
    }
}
