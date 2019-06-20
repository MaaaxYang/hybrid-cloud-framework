package org.github.bodhi.hybird.platform.base;

/**
 * @program: bodhi-distributed
 * @description:
 * @author: Maxxx.Yg
 * @create: 2019-03-18 11:07
 **/
public enum PathMenu {

    BASEPATH("./bodhi"),
    SERVICE(BASEPATH.path+"/service"),
    LOGPATH(BASEPATH.path+"/logs"),
    METRICPATH(BASEPATH.path+"/metric"),
    LIB_COMMON(BASEPATH.path+"/lib/common"),
    LIB_EXTENDS(BASEPATH.path+"/lib/extends")
    ;
    private String path;

    PathMenu(String path) {
        this.path = path;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public static String getSavePath(String relativePath){
        return PathMenu.BASEPATH.path + "/" + relativePath;
    }

    public String makeVersionPath(String version){
        return this.path + "/" + version;
    }
}
