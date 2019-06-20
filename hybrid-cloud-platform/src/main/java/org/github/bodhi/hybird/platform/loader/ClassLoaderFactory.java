package org.github.bodhi.hybird.platform.loader;


import org.github.bodhi.hybrid.context.config.BodhiPackage;
import org.github.bodhi.hybrid.context.config.ClassPathConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;


/**
 * @program: bodhi-distributed
 * @description:
 * @author: Maxxx.Yg
 * @create: 2019-03-11 09:39
 **/
public class ClassLoaderFactory {

    private final static Logger logger = LoggerFactory.getLogger(ClassLoaderFactory.class);


    public ClassLoaderFactory() {

    }

    public ClassLoader create(){
        return new DefaultClassloader();
    }

    public ClassLoader create(ClassPathConfig config){

        ClassLoader loader = null;

        FilePathBuilder builder = new FilePathBuilder();

        List<Path> paths = new ArrayList<>();
        // lib/common ,lib/extends 默认全部加载
        for(BodhiPackage bodhiPackage : config.getPackages()){
            paths.add(builder.create(bodhiPackage.getPath()));
        }

        if (paths!=null && paths.size()>0){
            try {
                URL[] urls = new URL[paths.size()];
                for(int index = 0;index<paths.size();index++){
                    urls[index] = paths.toArray(new Path[]{})[index].toUri().toURL();
                }
                loader = new DefaultClassloader(urls,Thread.currentThread().getContextClassLoader());
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
        }else{
            loader = new DefaultClassloader(Thread.currentThread().getContextClassLoader());
        }
        return loader;
    }

}
