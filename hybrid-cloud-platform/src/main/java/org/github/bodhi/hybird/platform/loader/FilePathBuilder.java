package org.github.bodhi.hybird.platform.loader;

import org.github.bodhi.hybird.platform.base.PathMenu;
import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/**
 * @program: bodhi-distributed
 * @description:
 * @author: Maxxx.Yg
 * @create: 2019-03-11 09:38
 **/
public class FilePathBuilder {

    /**
     * partten : besePath/version/{xxx.jar}
     */
    public List<Path> build(String version){

        List<Path> pathList = new ArrayList<>();
        if (!StringUtils.isNoneEmpty(version)){
            return pathList;
        }


        String filePath = PathMenu.SERVICE.makeVersionPath(version);
        // version path
        Path path = Paths.get(filePath);
        if (!Files.exists(path)){
            return pathList;
        }

        // dir files
        File dirFile = new File(path.toUri());
        File[] files = dirFile.listFiles();
        if (dirFile==null||dirFile.length()==0){
            return pathList;
        }

        for(File file:files){
            pathList.add(file.toPath());
        }

        return pathList;
    }

    public List<Path> latest(){
        List<Path> pathList = new ArrayList<>();

        String dir = PathMenu.SERVICE.getPath();
        Path path = Paths.get(dir);

        // 路径不存在
        if (!Files.exists(path)){
            return pathList;
        }

        // 文件不存在
        File file = new File(path.toUri());
        File[] files = file.listFiles();
        if (files==null||files.length==0){
            return  pathList;
        }

        String version = "";
        Integer maxVersion = 0;
        for(File fl:files){
            String versionStr = fl.getName();
            String digits = StringUtils.getDigits(versionStr);
            Integer temp = Integer.valueOf(digits);
            if (maxVersion.compareTo(temp)<0){
                maxVersion = temp;
                version = versionStr;
            }
        }

        return build(version);
    }

    public List<Path> select(String rootPath){
        List<Path> pathList = new ArrayList<>();
        Path path = Paths.get(rootPath);
        // 路径不存在
        if (!Files.exists(path)){
            return pathList;
        }

        // 文件不存在
        File file = new File(path.toUri());
        File[] files = file.listFiles();
        if (files==null||files.length==0){
            return  pathList;
        }

        for(File fl:files){
            pathList.add(fl.toPath());
        }
        return pathList;
    }

    public Path create(String path){
        return Paths.get(path);
    }
}
