package org.github.bodhi.hybrid.support.spring.filters;

import org.github.bodhi.hybird.platform.base.PlatformStatus;
import org.github.bodhi.hybrid.application.adapter.BizException;
import org.github.bodhi.hybrid.context.serializers.SerializerHolder;
import org.github.bodhi.hybrid.norms.exception.BestsignException;
import org.github.bodhi.hybrid.norms.exception.StandardCode;
import org.github.bodhi.hybrid.norms.web.BestsignWebFilter;
import org.github.bodhi.hybrid.norms.base.ApiResult;
import org.github.bodhi.hybrid.context.logs.LogEntity;
import org.github.bodhi.hybrid.context.logs.LogHolder;
import org.github.bodhi.hybrid.support.spring.holder.ServletFilterHolder;
import org.github.bodhi.hybrid.utils.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.UUID;

/**
 * @program: hybrid-cloud-framework
 * @description:
 * @author: Maxxx.Yg
 * @create: 2019-03-21 18:50
 **/
public class GlobalServletFilter implements Filter {

    private final static Logger logger = LoggerFactory.getLogger(GlobalServletFilter.class);

    private static String updatingTip = null;

    static {
        ApiResult result = ApiResult.warn("server updating ...");
        updatingTip = SerializerHolder.stringValue(result);
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    private volatile static LinkedList<BestsignWebFilter> inFilter = null;
    private volatile static LinkedList<BestsignWebFilter> outFilter = null;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;

        // updating check
        if (updating(httpServletRequest)) {
            httpServletResponse.setHeader("Content-Type", "application/json; charset=utf-8");
            httpServletResponse.getWriter().write(updatingTip);
            return;
        }

        // filter init
        init();

        // MDC request Trace
        String requestId = null;
        requestId = httpServletRequest.getHeader("requestId");
        if (StringUtils.isNullOrEmpty(requestId)) {
            requestId = UUID.randomUUID().toString();
        }
        httpServletRequest.setAttribute("requestId", requestId);
        MDC.put("requestId", requestId);

        long startTime = System.currentTimeMillis();
        LogEntity logEntity = new LogEntity();
        logEntity.setGourpId(String.valueOf(httpServletRequest.getAttribute("bestsign-client-id")));
        logEntity.setModule(httpServletRequest.getServletPath());
        logEntity.setObject(httpServletRequest.getServletPath());
        logEntity.setReqid(requestId);
        logEntity.setStartTime(new Date());
        logEntity.setStatus(StandardCode.SUCCESS.getCode());
        LogHolder.set(logEntity);

        try {
            HttpServletRequest newRequest = httpServletRequest;
            // before
            if (inFilter != null) {
                Iterator<BestsignWebFilter> inIterator = inFilter.iterator();
                if (inIterator != null) {
                    while (inIterator.hasNext()) {
                        newRequest = inIterator.next().before(newRequest, httpServletResponse);
                    }
                }
            }


            chain.doFilter(newRequest, response);

            // after
            if (outFilter != null) {
                Iterator<BestsignWebFilter> outIterator = outFilter.iterator();
                if (outIterator != null) {
                    while (outIterator.hasNext()) {
                        outIterator.next().after(newRequest, httpServletResponse);
                    }
                }
            }
        } catch (BizException e) {
            logEntity.setStatus(e.getCode());
            logEntity.setMsg(e.getMessage());
            logEntity.setResult("BizError");

            logger.error("find BizException : ", e);

            ((HttpServletResponse) response).setHeader("Content-Type", "application/json; charset=utf-8");
            response.getWriter().write(SerializerHolder.stringValue(ApiResult.error(e.getMessage())));
            return;

        } catch (BestsignException e) {

            logEntity.setStatus(StandardCode.ERROR.getCode());
            logEntity.setMsg(e.getMessage());
            logEntity.setResult("BizError");

            logger.error("find bestsign exception : ", e);

            ((HttpServletResponse) response).setHeader("Content-Type", "application/json; charset=utf-8");
            response.getWriter().write(SerializerHolder.stringValue(ApiResult.error(e.getMessage())));
            return;
        } catch (Throwable throwable) {

            logEntity.setStatus(StandardCode.ERROR.getCode());
            logEntity.setMsg(throwable.getMessage());
            logEntity.setResult("BizError");

            logger.error("find unknow error : ", throwable);
            ((HttpServletResponse) response).setHeader("Content-Type", "application/json; charset=utf-8");
            response.getWriter().write(SerializerHolder.stringValue(ApiResult.error("busy service")));
            return;

        } finally {
            long cost = System.currentTimeMillis() - startTime;

            logEntity.setCost(cost);
            logger.info(SerializerHolder.stringValue(logEntity));
            MDC.clear();
        }

    }

    @Override
    public void destroy() {

    }

    public void resetFilter() {
        inFilter = new LinkedList<>();
        outFilter = new LinkedList<>();
        Iterator<BestsignWebFilter> iterator = ServletFilterHolder.getInstance().getFilters().iterator();
        while (iterator.hasNext()) {
            BestsignWebFilter filter = iterator.next();
            inFilter.addLast(filter);
            outFilter.addFirst(filter);
        }
    }


    private boolean updating(HttpServletRequest request) {
        if (request.getServletPath().startsWith("/version/update")){
            return false;
        }
        if (PlatformStatus.STATE.equals(PlatformStatus.Status.REFRESHING)) {
            return true;
        }
        return false;
    }

    private void init() {

        if (inFilter == null||outFilter==null) {
            synchronized (GlobalServletFilter.class) {
                if (inFilter == null||outFilter==null) {
                    inFilter = new LinkedList<>();
                    outFilter = new LinkedList<>();

                    Iterator<BestsignWebFilter> iterator = ServletFilterHolder.getInstance().getFilters().iterator();
                    while (iterator.hasNext()) {
                        BestsignWebFilter filter = iterator.next();
                        inFilter.addLast(filter);
                        outFilter.addFirst(filter);
                    }
                }
            }
        }

    }
}
