package org.github.bodhi.hybrid.samples.application;

import org.github.bodhi.hybrid.application.adapter.ApplicationHelper;
import org.github.bodhi.hybrid.samples.application.business.DemoApplication;
import org.github.bodhi.hybrid.samples.application.business.OtherApplication;
import org.github.bodhi.hybrid.samples.application.business.ProxyApplication;

/**
 * @program: hybrid-cloud-framework
 * @description:
 * @author: Maxxx.Yg
 * @create: 2019-03-18 16:51
 **/
public final class ApplicationMenu {

    private ApplicationMenu() {

    }

    public static DemoApplication demoApplication() {
        return ApplicationHelper.getApp(DemoApplication.class);
    }

    public static OtherApplication otherApplication() {
        return ApplicationHelper.getApp(OtherApplication.class);
    }

    public static ProxyApplication proxyApplication() {
        return ApplicationHelper.getApp(ProxyApplication.class);
    }
}
