package org.github.bodhi.hybrid.application.adapter.repository;

import java.io.Serializable;

/**
 * @program: application-repository
 * @description:
 * @author: Maxxx.Yg
 * @create: 2019-04-06 17:44
 **/
public class ObjectMap implements Serializable {

    private static final long serialVersionUID = 3572074211248191842L;

    private String id;

    private String version;

    private String fileName;

    private String type;

    private String relativePath;

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getRelativePath() {
        return relativePath;
    }

    public void setRelativePath(String relativePath) {
        this.relativePath = relativePath;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
