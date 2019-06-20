package org.github.bodhi.hybrid.norms.bean;

import org.github.bodhi.hybrid.norms.BestsignApplication;

/**
 * @program: bodhi-distributed
 * @description:
 * @author: Maxxx.Yg
 * @create: 2019-03-13 09:03
 **/
public class BodhiBean {

    private String name;

    private String alias;

    private BestsignApplication application;

    private Class applicationClass;

    private Object proxy;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public BestsignApplication getApplication() {
        return application;
    }

    public void setApplication(BestsignApplication application) {
        this.application = application;
    }

    public Class getApplicationClass() {
        return applicationClass;
    }

    public void setApplicationClass(Class applicationClass) {
        this.applicationClass = applicationClass;
    }

    public Object getProxy() {
        return proxy;
    }

    public void setProxy(Object proxy) {
        this.proxy = proxy;
    }
}
