package org.github.bodhi.hybrid.internet.strategy;


import org.github.bodhi.hybrid.internet.client.Client;
import org.github.bodhi.hybrid.internet.ClientRequest;
import org.github.bodhi.hybrid.internet.appointment.Header;
import org.github.bodhi.hybrid.internet.config.ClientConfig;
import org.github.bodhi.hybrid.internet.enums.HttpMethod;
import org.github.bodhi.hybrid.internet.appointment.Param;
import org.github.bodhi.hybrid.internet.appointment.Relay;
import org.github.bodhi.hybrid.internet.holder.ClientHolder;
import org.github.bodhi.hybrid.internet.ClientRequest;
import org.github.bodhi.hybrid.internet.appointment.Param;
import org.github.bodhi.hybrid.internet.client.Client;
import org.github.bodhi.hybrid.internet.config.ClientConfig;
import org.github.bodhi.hybrid.internet.enums.HttpMethod;
import org.github.bodhi.hybrid.internet.holder.ClientHolder;
import org.github.bodhi.hybrid.norms.exception.BestsignException;
import org.github.bodhi.hybrid.norms.serializers.Serializer;
import org.github.bodhi.hybrid.utils.StringUtils;
import okhttp3.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

/**
 * @program: bestsign-distributed
 * @description:
 * @author: Maxxx.Yg
 * @create: 2019-03-06 11:28
 **/
public abstract class AbstractStrategy implements IStrategy {

    private static final Logger logger = LoggerFactory.getLogger(IStrategy.class);

    protected Client client;

    protected ClientConfig clientConfig;

    public AbstractStrategy(Client client) {
        this.client = client;
    }


    @Override
    public Object execute(StrategyParameter parameter) throws Throwable {
        if (clientConfig == null) {
            synchronized (AbstractStrategy.class) {
                if (clientConfig == null) {
                    clientConfig = ClientHolder.getConfig();
                }
            }
        }

        long startTime = System.currentTimeMillis();
        Object result = null;
        try {
            result = run(parameter);
        } catch (Throwable e) {
            logger.error("run method exception:", e);
            throw e;
        } finally {
            long endTime = System.currentTimeMillis();
            logger.info("method : \"{}\" , cost : {} ms", parameter.getMethod().getName(), endTime - startTime);
        }
        return result;
    }

    protected Object getResponse(StrategyParameter parameter) throws Exception {

        // ready
        Relay relay = parameter.getMethod().getAnnotation(Relay.class);
        HttpMethod httpMethod = relay.httpMethod();
        String path = StringUtils.isNullOrEmpty(relay.value()) ?
                (StringUtils.isNullOrEmpty(relay.path()) ? null : relay.path()) : relay.value();

        Response response = null;

        ClientRequest clientRequest = new ClientRequest();
        clientRequest.setHost(clientConfig.getHost());
        clientRequest.setPath(path);
        if (HttpMethod.GET.equals(httpMethod)) {

            Map<String, String> params = new HashMap<>();
            Map<String, String> headers = new HashMap<>();
            if (parameter.getArgs().length == 1) {

                Object arg = parameter.getArgs()[0];
                params = findParams(parameter.getSerializer(), arg);
                headers = findHeaders(parameter.getSerializer(), arg);

            } else if (parameter.getArgs().length > 1) {
                throw new BestsignException("relay http method post only support one parameter");
            }

            clientRequest.setHeaders(headers);
            clientRequest.setParams(params);
            clientRequest.setHttpMethod(HttpMethod.GET);

            response = client.execute(clientRequest);

        } else if (HttpMethod.POST.equals(httpMethod)) {
            clientRequest.setHttpMethod(HttpMethod.POST);
            if (parameter.getArgs().length == 0) {

                response = client.execute(clientRequest);

            } else if (parameter.getArgs().length == 1) {

                String body = convertParamType(parameter.getSerializer(), parameter.getMethod().getParameterTypes()[0], parameter.getArgs()[0]);
                Map<String, String> headers = findHeaders(parameter.getSerializer(), parameter.getArgs()[0]);

                clientRequest.setBody(body.getBytes());
                clientRequest.setHeaders(headers);

                response = client.execute(clientRequest);
            } else {
                throw new BestsignException("http method post only support one parameter");
            }
        } else {
            throw new BestsignException("http method only support get or post");
        }

        switch (parameter.getResponseType()) {
            case SOURCE:
                return response;
            case VOID:
                return null;
            case STREAM:
                return response.body().byteStream();
            case BYTES:
                return response.body().bytes();
            case DESERIALZIE:
                Class returnType = parameter.getMethod().getReturnType();
                if (String.class.equals(returnType)) {
                    return response.body().string();
                } else {
                    return parameter.getSerializer().deserialize(response.body().string(), returnType);
                }
            case STRING:
            default:
                return response.body().string();
        }
    }


    protected abstract Object run(StrategyParameter parameter) throws Throwable;

    private String convertParamType(Serializer serializer, Class clazz, Object obj) {
        String res = "";
        if (String.class.equals(clazz)) {
            res = (String) obj;
        } else if (Integer.class.equals(clazz)
                || Boolean.class.equals(clazz)
                || Long.class.equals(clazz)) {
            res = String.valueOf(obj);
        } else {
            res = (String) serializer.serialize(obj);
        }

        return res;
    }


    private Map<String, String> findHeaders(Serializer serializer, Object object) throws IllegalAccessException {
        Map<String, String> headers = new HashMap<>();

        Field[] fields = object.getClass().getDeclaredFields();
        for (int i = 0; i < fields.length; i++) {
            Field field = fields[i];
            field.setAccessible(true);
            Header header = field.getAnnotation(Header.class);
            if (header != null) {
                headers.put(header.value(), convertParamType(serializer, field.getDeclaringClass(), field.get(object)));
            }
        }

        return headers;
    }


    private Map<String, String> findParams(Serializer serializer, Object object) throws IllegalAccessException {
        Map<String, String> params = new HashMap<>();
        Field[] fields = object.getClass().getDeclaredFields();
        for (int i = 0; i < fields.length; i++) {
            Field field = fields[i];
            field.setAccessible(true);
            Param param = field.getAnnotation(Param.class);
            if (param != null) {
                params.put(param.value(), convertParamType(serializer, field.getDeclaringClass(), field.get(object)));
            }
        }

        return params;
    }


    @Override
    public void setClient(Client client) {
        this.client = client;
    }


}
