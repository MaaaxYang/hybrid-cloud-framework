package org.github.bodhi.hybrid.support.spring.aop;

import org.github.bodhi.hybrid.application.adapter.BizException;
import org.github.bodhi.hybrid.context.logs.LogEntity;
import org.github.bodhi.hybrid.context.logs.LogHolder;
import org.github.bodhi.hybrid.norms.exception.BestsignException;
import org.github.bodhi.hybrid.norms.exception.ExceptionLevel;
import org.github.bodhi.hybrid.norms.exception.StandardCode;
import org.github.bodhi.hybrid.norms.base.ApiResult;
import org.github.bodhi.hybrid.support.spring.base.CostApiResult;
import org.github.bodhi.hybrid.support.spring.exceptions.ExceptionHandler;
import org.github.bodhi.hybrid.support.spring.holder.ExceptionHolder;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;

/**
 * @program: hybrid-cloud-framework
 * @description:
 * @author: Maxxx.Yg
 * @create: 2019-03-29 17:05
 **/
public class GlobalInvokeInterceptor implements MethodInterceptor {

    private final static Logger logger = LoggerFactory.getLogger(GlobalInvokeInterceptor.class);

    @Override
    public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
        long start  = System.currentTimeMillis();
        Object obj = null;
        try{
            obj = methodProxy.invokeSuper(o,objects);
        }catch (BizException bizException){
            LogEntity logEntity = LogHolder.get();
            logEntity.setStatus(bizException.getCode());
            logEntity.setResult("BizError");
            logEntity.setMsg(bizException.getMessage());
            logEntity.setLevel(bizException.getLevel());

            obj = ApiResult.error(bizException.getCode(),bizException.getMessage());
            logger.error("biz exception",bizException);
        }catch (BestsignException bestsignException){
            LogEntity logEntity = LogHolder.get();
            logEntity.setStatus(StandardCode.ERROR.getCode());
            logEntity.setResult("BizError");
            logEntity.setMsg(bestsignException.getMessage());
            logEntity.setLevel(bestsignException.getLevel());

            obj = ApiResult.error(StandardCode.ERROR.getCode(),bestsignException.getMessage());
            logger.error("bestsign exception",bestsignException);
        }catch (Throwable e){
            LogEntity logEntity = LogHolder.get();
            logEntity.setResult("BizError");
            logEntity.setMsg(e.getMessage());

            ExceptionHandler handler = ExceptionHolder.get(e);
            if (handler==null){
                logEntity.setStatus(StandardCode.ERROR.getCode());
                logEntity.setLevel(ExceptionLevel.PROFOUND);

                obj = ApiResult.error(StandardCode.ERROR.getCode(),e.getMessage());
                logger.error("unknow exception",e);
            }else{
                BizException bizException = (BizException)e;
                logEntity.setStatus(bizException.getCode());
                logEntity.setLevel(bizException.getLevel());

                obj = handler.handler(bizException);
            }
        }finally {
            long end = System.currentTimeMillis();
            long cost = end - start;
            if (obj instanceof ApiResult){
                Cost costAnnotation = method.getAnnotation(Cost.class);
                if (costAnnotation==null){
                    costAnnotation = method.getDeclaringClass().getAnnotation(Cost.class);
                }
                if (costAnnotation!=null){
                    return new CostApiResult(cost,(ApiResult)obj);
                }
            }
        }

        return obj;
    }
}
