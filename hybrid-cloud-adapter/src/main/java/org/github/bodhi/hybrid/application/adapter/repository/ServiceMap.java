package org.github.bodhi.hybrid.application.adapter.repository;

import java.io.Serializable;
import java.util.List;

/**
 * @program: application-repository
 * @description:
 * @author: Maxxx.Yg
 * @create: 2019-04-06 17:43
 **/
public class ServiceMap implements Serializable {

    private static final long serialVersionUID = -6525694262583154427L;

    private String version;

    private List<ObjectMap> maps;

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public List<ObjectMap> getMaps() {
        return maps;
    }

    public void setMaps(List<ObjectMap> maps) {
        this.maps = maps;
    }
}
