package org.github.bodhi.hybrid.context.config;


/**
 * @program: hybrid-cloud-framework
 * @description:
 * @author: Maxxx.Yg
 * @create: 2019-03-21 17:04
 **/
public class FrameworkConfig {

    private String basePath = "./bodhi";

    private String version = "1.0.0";

    private boolean debug = false;

    private String repositoryHost = "";

    private ClassPathConfig classPathConfig = new ClassPathConfig();

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

    public boolean isDebug() {
        return debug;
    }

    public void setDebug(boolean debug) {
        this.debug = debug;
    }

    public String getRepositoryHost() {
        return repositoryHost;
    }

    public void setRepositoryHost(String repositoryHost) {
        this.repositoryHost = repositoryHost;
    }

    public ClassPathConfig getClassPathConfig() {
        return classPathConfig;
    }

    public void setClassPathConfig(ClassPathConfig classPathConfig) {
        this.classPathConfig = classPathConfig;
    }
}
