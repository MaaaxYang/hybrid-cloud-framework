package org.github.bodhi.hybrid.norms.validation;

import org.github.bodhi.hybrid.norms.exception.BodhiException;

/**
 * @program: bodhi-distributed
 * @description:
 * @author: Maxxx.Yg
 * @create: 2019-02-19 16:06
 **/
public abstract class AbstractBestsignValidator implements BestsignValidator{

    private BestsignValidator next = null;

    public AbstractBestsignValidator() {

    }

    public AbstractBestsignValidator(BestsignValidator next) {
        this.next = next;
    }

    public void setNext(BestsignValidator next) {
        this.next = next;
    }

    @Override
    public void check() throws BodhiException {

        verify();

        if (next!=null){
            next.check();
        }
    }

    public abstract void verify() throws BodhiException;
}
