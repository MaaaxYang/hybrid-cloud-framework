package org.github.bodhi.hybrid.samples.application.business;

import org.github.bodhi.hybrid.internet.appointment.Relay;
import org.github.bodhi.hybrid.norms.annotations.BestsignProvider;

/**
 * @program: hybrid-cloud-framework
 * @description:
 * @author: Maxxx.Yg
 * @create: 2019-03-19 10:01
 **/
@BestsignProvider
public interface ProxyApplication {

    @Relay(path = "/test/proxy")
    String proxy();

}
