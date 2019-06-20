package org.github.bodhi.hybrid.internet.appointment;

import org.github.bodhi.hybrid.internet.enums.HttpMethod;
import org.github.bodhi.hybrid.internet.enums.RelayTiming;
import org.github.bodhi.hybrid.internet.enums.ResponseType;
import org.github.bodhi.hybrid.internet.enums.HttpMethod;
import org.github.bodhi.hybrid.internet.enums.RelayTiming;
import org.github.bodhi.hybrid.internet.enums.ResponseType;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @program: bestsign-distributed
 * @description:
 * @author: Maxxx.Yg
 * @create: 2019-03-06 10:30
 **/
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface Relay {

    String value() default "";

    String path() default "";

    HttpMethod httpMethod() default HttpMethod.GET;

    RelayTiming relayTiming() default RelayTiming.SKIP;

    ResponseType responseType() default ResponseType.DESERIALZIE;
}
