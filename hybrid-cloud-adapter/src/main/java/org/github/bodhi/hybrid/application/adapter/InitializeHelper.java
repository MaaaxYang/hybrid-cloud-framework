package org.github.bodhi.hybrid.application.adapter;

import org.github.bodhi.hybird.platform.base.PathMenu;
import org.github.bodhi.hybrid.application.adapter.repository.ObjectMap;
import org.github.bodhi.hybrid.application.adapter.repository.RepositoryManager;
import org.github.bodhi.hybrid.application.adapter.repository.ServiceMap;
import org.github.bodhi.hybrid.context.config.BodhiPackage;
import org.github.bodhi.hybrid.context.config.ClassPathConfig;
import org.github.bodhi.hybrid.utils.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

/**
 * @program: hybrid-cloud-framework
 * @description:
 * @author: Maxxx.Yg
 * @create: 2019-04-07 16:51
 **/
public class InitializeHelper {

    private final static Logger logger = LoggerFactory.getLogger(InitializeHelper.class);

    public static ClassPathConfig run(String clientId, String versionId){

        ServiceMap map = RepositoryManager.versionMap(clientId, versionId);

        ClassPathConfig config = ClassPathConfig.DEFAULT();

        if (map!=null){
            pathInit(map);
            fileInit(map);

            for(ObjectMap objectMap : map.getMaps()){
                BodhiPackage bodhiPackage = new BodhiPackage();
                bodhiPackage.setId(objectMap.getId());
                bodhiPackage.setFileName(objectMap.getFileName());
                bodhiPackage.setPath(objectMap.getRelativePath());
                bodhiPackage.setType(objectMap.getType());
                bodhiPackage.setVersion(objectMap.getVersion());
                config.getPackages().add(bodhiPackage);
            }
        }

        return config;
    }

    public static void pathInit(ServiceMap map){
        if (map==null || map.getMaps()==null || map.getMaps().size()==0){
            return;
        }
        List<ObjectMap> maps = map.getMaps();
        for(ObjectMap objectMap : maps){
            //
            String path = null;
            if ("server".equals(objectMap.getType())){
                path = StringUtils.join(
                        Arrays.asList(
                                PathMenu.BASEPATH.getPath(),
                                objectMap.getType(),
                                objectMap.getVersion()
                        ),"/");
            }else{
                path = StringUtils.join(
                        Arrays.asList(
                                PathMenu.BASEPATH.getPath(),
                                "lib",
                                objectMap.getType(),
                                objectMap.getVersion()
                        ),"/");
            }
            Path dirPath = Paths.get(path);
            if (!Files.exists(dirPath)) {
                try {
                    Files.createDirectories(dirPath);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void fileInit(ServiceMap map){

        for(ObjectMap objectMap : map.getMaps()){
            Path path = Paths.get(PathMenu.getSavePath(objectMap.getRelativePath()));
            if (Files.exists(path)){
                logger.info("package is exist : {} ",objectMap.getRelativePath());
                continue;
            }
            logger.info("package not exist : {} ",objectMap.getRelativePath());
            try(InputStream inputStream = RepositoryManager.pull(objectMap.getId(),objectMap.getFileName())){
                FileUtils.save(path,inputStream);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


}
