package org.github.bodhi.hybrid.internet.filters;

import org.github.bodhi.hybrid.context.order.Order;
import org.github.bodhi.hybrid.internet.appointment.AfterRequestProcessFilter;
import org.github.bodhi.hybrid.norms.exception.BestsignException;
import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

/**
 * @program: hybrid-cloud-framework
 * @description:
 * @author: Maxxx.Yg
 * @create: 2019-03-18 15:15
 **/
@Order(orderNum = Integer.MIN_VALUE)
public class DefaultAfterFilter implements AfterRequestProcessFilter<DefaultAfterFilter> {

    private static ObjectMapper mapper = new ObjectMapper();

    private static Logger logger = LoggerFactory.getLogger(DefaultAfterFilter.class);

    @Override
    public <T> T after(Response response, Class<T> responseClass, Object prev) {
        if (Response.class.equals(responseClass)){
            return (T)response;
        }

        try {
            String res = response.body().string();
            logger.info("current response content : {} ",res);

            return mapper.readValue(res,responseClass);
        } catch (IOException e) {
            throw new BestsignException(e);
        }
    }

}
