package org.github.bodhi.hybrid.context.config;

/**
 * @program: bodhi-distributed
 * @description:
 * @author: Maxxx.Yg
 * @create: 2019-03-13 20:55
 **/
public class RefreshConfig {

    private String basePath;

    private String version;

    private ClassPathConfig classPathConfig;

    public String getBasePath() {
        return basePath;
    }

    public void setBasePath(String basePath) {
        this.basePath = basePath;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public ClassPathConfig getClassPathConfig() {
        return classPathConfig;
    }

    public void setClassPathConfig(ClassPathConfig classPathConfig) {
        this.classPathConfig = classPathConfig;
    }
}
