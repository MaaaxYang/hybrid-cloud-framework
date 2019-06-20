package org.github.bodhi.hybrid.samples.client;

import org.github.bodhi.hybrid.context.order.Order;
import org.github.bodhi.hybrid.norms.web.BestsignWebFilter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @program: hybrid-cloud-framework
 * @description:
 * @author: Maxxx.Yg
 * @create: 2019-03-21 14:36
 **/
@Order(orderNum = 1)
public class MyInterceptor1 implements BestsignWebFilter {

    @Override
    public HttpServletRequest before(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        System.out.println("filter 1 - in");
        return request;
    }

    @Override
    public void after(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        System.out.println("filter 1 - out");
    }
}
