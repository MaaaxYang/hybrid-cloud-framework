package org.github.bodhi.hybrid.norms.validation;


import org.github.bodhi.hybrid.norms.exception.BestsignException;

/**
 * @program: bestsign-distributed
 * @description:
 * @author: Maxxx.Yg
 * @create: 2019-02-19 16:03
 **/
public interface BestsignValidator {

    void check() throws BestsignException;

}
