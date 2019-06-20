package org.github.bodhi.hybrid.internet.filters;

import org.github.bodhi.hybrid.context.order.Order;
import org.github.bodhi.hybrid.internet.ClientRequest;
import org.github.bodhi.hybrid.internet.appointment.BeforeRequestProcessFilter;
import org.github.bodhi.hybrid.norms.exception.BestsignException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @program: hybrid-cloud-framework
 * @description:
 * @author: Maxxx.Yg
 * @create: 2019-03-18 15:13
 **/
@Order(orderNum = Integer.MIN_VALUE)
public class DefaultBeforeFilter implements BeforeRequestProcessFilter<DefaultBeforeFilter> {

    private static ObjectMapper mapper = new ObjectMapper();

    private static Logger logger = LoggerFactory.getLogger(DefaultBeforeFilter.class);

    @Override
    public void before(ClientRequest request) {
        try {
            logger.info("current request content : {} ",mapper.writeValueAsString(request));
        } catch (JsonProcessingException e) {
            throw new BestsignException(e);
        }
    }
}
