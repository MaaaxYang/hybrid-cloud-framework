package org.github.bodhi.hybrid.norms.event;

/**
 * @program: bestsign-distributed
 * @description:
 * @author: Maxxx.Yg
 * @create: 2019-03-10 09:00
 **/
public interface BestsignListener<T extends BestsignEvent> {

    void onBestsignEvent(T event);

}
