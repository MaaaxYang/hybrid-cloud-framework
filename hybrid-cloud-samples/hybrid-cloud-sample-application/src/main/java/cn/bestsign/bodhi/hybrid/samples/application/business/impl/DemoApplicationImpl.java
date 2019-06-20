package org.github.bodhi.hybrid.samples.application.business.impl;

import org.github.bodhi.hybrid.application.adapter.AbstractBestsignApplication;
import org.github.bodhi.hybrid.application.adapter.BizException;
import org.github.bodhi.hybrid.internet.appointment.Relay;
import org.github.bodhi.hybrid.internet.enums.RelayTiming;
import org.github.bodhi.hybrid.internet.enums.ResponseType;
import org.github.bodhi.hybrid.samples.application.business.DemoApplication;
import org.github.bodhi.hybrid.samples.application.business.OtherApplication;
import org.github.bodhi.hybrid.samples.application.business.entitys.DemoEntity;

/**
 * @program: hybrid-cloud-framework
 * @description: 需要继承 AbstractBestsignApplication 类 ，或者自己实现 BestsignApplication 接口
 * @author: Maxxx.Yg
 * @create: 2019-03-18 16:38
 **/
public class DemoApplicationImpl extends AbstractBestsignApplication implements DemoApplication, OtherApplication {

    @Override
    public void voidAction() {
        System.out.println("void action");
    }

    @Override
    @Relay(path = "/test/relay", relayTiming = RelayTiming.AFTER, responseType = ResponseType.VOID)
    public void relayAction() {
        System.out.println("relay action");
    }

    @Override
    public String stringAction(String param) {
        return "welcome " + param;
    }

    @Override
    public DemoEntity entityAction(DemoEntity entity) {
        return entity;
    }

    @Override
    public String patchAction() {
        return "i'm patch";
    }

    @Override
    public void error() {
        throw new BizException("code-c","i'm error");
    }

    @Override
    public void nothing() {
        System.out.println("nothing ..");
    }

}
