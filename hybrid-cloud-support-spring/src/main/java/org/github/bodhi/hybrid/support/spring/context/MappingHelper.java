package org.github.bodhi.hybrid.support.spring.context;

import org.github.bodhi.hybrid.norms.exception.BestsignException;
import org.springframework.core.annotation.AnnotatedElementUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;


import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Method;

/**
 * @program: bestsign-distributed
 * @description:
 * @author: Maxxx.Yg
 * @create: 2019-03-18 09:37
 **/
public class MappingHelper {


    public static RequestMappingInfo generateMappingInfo (Method method, Class<?> handlerType){
        if (method==null||handlerType==null){
            throw new BestsignException("method and handlerType can't is null");
        }
        RequestMappingInfo info = createRequestMappingInfo(method.getName(),method);
        if (info != null) {
            RequestMappingInfo typeInfo = createRequestMappingInfo(handlerType.getName(),handlerType);
            if (typeInfo != null) {
                info = typeInfo.combine(info);
            }
        }
        return info;

    }

    private static RequestMappingInfo createRequestMappingInfo(String name,AnnotatedElement element){
        RequestMapping requestMapping = AnnotatedElementUtils.findMergedAnnotation(element, RequestMapping.class);
        if (requestMapping!=null){
            RequestMappingInfo mappingInfo =  RequestMappingInfo
                    .paths(requestMapping.path())
                    .methods(requestMapping.method())
                    .params(requestMapping.params())
                    .headers(requestMapping.headers())
                    .consumes(requestMapping.consumes())
                    .produces(requestMapping.produces())
                    .mappingName(name)
                    .options(new RequestMappingInfo.BuilderConfiguration())
                    .build();
            return mappingInfo;
        }
        return null;
    }

}
