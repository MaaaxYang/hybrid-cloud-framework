package org.github.bodhi.hybrid.norms.validation;

import org.github.bodhi.hybrid.norms.exception.BodhiException;

/**
 * @program: bodhi-distributed
 * @description:
 * @author: Maxxx.Yg
 * @create: 2019-02-19 16:13
 **/
public final class ValidatorActuator {

    private AbstractBestsignValidator validatorStater;

    public ValidatorActuator append(BestsignValidator validator){
        if (validatorStater==null){
            validatorStater = (AbstractBestsignValidator)validator;
        }else{
            validatorStater.setNext(validator);
        }
        return this;
    }

    public void execute() throws BodhiException {
        if (validatorStater!=null){
            validatorStater.check();
        }
    }

}
