package org.github.bodhi.hybrid.norms.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @program: hybrid-cloud-framework
 * @description:
 * @author: Maxxx.Yg
 * @create: 2019-03-21 19:07
 **/
public interface BestsignWebFilter {

    HttpServletRequest before(HttpServletRequest request, HttpServletResponse response) throws Exception;

    void after(HttpServletRequest request, HttpServletResponse response) throws Exception;

}
