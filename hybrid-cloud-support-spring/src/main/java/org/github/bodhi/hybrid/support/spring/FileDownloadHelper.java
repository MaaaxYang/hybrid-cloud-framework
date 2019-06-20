package org.github.bodhi.hybrid.support.spring;

import org.github.bodhi.hybird.platform.loader.FilePathBuilder;
import org.github.bodhi.hybrid.internet.ClientRequest;
import org.github.bodhi.hybrid.internet.client.impl.SimpleHttpClient;
import org.github.bodhi.hybrid.internet.enums.HttpMethod;
import org.github.bodhi.hybrid.internet.holder.ClientHolder;
import org.github.bodhi.hybrid.norms.exception.BodhiException;
import org.github.bodhi.hybrid.utils.FileUtils;
import okhttp3.Response;
import org.springframework.util.CollectionUtils;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @program: hybrid-cloud-framework
 * @description:
 * @author: Maxxx.Yg
 * @create: 2019-03-26 10:18
 **/
public class FileDownloadHelper {

    public static void download(String ver,String host,String savePath){
        // 检查文件是否存在
        FilePathBuilder builder = new FilePathBuilder();
        List<Path> pathList = builder.build(ver);
        if (CollectionUtils.isEmpty(pathList)){

            // 如果没有文件 则需要从云端下载
            Map<String,String> param = new HashMap<>();
            param.put("ver",ver);

            Response response = ClientHolder.getClientMap()
                    .get(SimpleHttpClient.class)
                    .execute(
                            ClientRequest.builder()
                                    .host(host)
                                    .path("/service/publish")
                                    .httpMethod(HttpMethod.GET)
                                    .params(param)
                                    .build()
                    );
            try {
                String downloadUrl = response.body().string();

                String fileName = downloadUrl.substring(downloadUrl.lastIndexOf("/"));

                Response fileResponse = ClientHolder.getClientMap()
                        .get(SimpleHttpClient.class)
                        .execute(
                                ClientRequest.builder()
                                        .host(downloadUrl)
                                        .httpMethod(HttpMethod.GET)
                                        .params(param)
                                        .build()
                        );
                byte[] bytes = fileResponse.body().bytes();

                FileUtils.save(Paths.get(savePath+"/"+ver+"/"+fileName),bytes);

            } catch (IOException e) {
                throw new BodhiException("download updating file fail",e);
            }

        }
    }

}
