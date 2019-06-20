package org.github.bodhi.hybrid.internet.appointment;

import org.github.bodhi.hybrid.internet.ClientRequest;
import org.github.bodhi.hybrid.internet.ClientRequest;

import java.util.Comparator;

/**
 * @program: hybrid-cloud-framework
 * @description:
 * @author: Maxxx.Yg
 * @create: 2019-03-18 14:34
 **/
public interface BeforeRequestProcessFilter<S> extends ClientFilter{

    void before(ClientRequest request);

}
