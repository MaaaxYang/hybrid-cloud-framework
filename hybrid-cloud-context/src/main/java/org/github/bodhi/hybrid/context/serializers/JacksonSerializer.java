package org.github.bodhi.hybrid.context.serializers;

import org.github.bodhi.hybrid.norms.exception.BodhiException;
import org.github.bodhi.hybrid.norms.serializers.Serializer;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

/**
 * @program: bodhi-distributed
 * @description:
 * @author: Maxxx.Yg
 * @create: 2019-03-15 10:40
 **/
public class JacksonSerializer implements Serializer<String,ObjectMapper> {

    private static ObjectMapper mapper = new ObjectMapper();

    @Override
    public String serialize(Object obj) {
        try {
            return mapper.writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            throw new BodhiException(e);
        }
    }

    @Override
    public <R> R deserialize(String source, Class<R> clazz) {
        try {
            return mapper.readValue(source,clazz);
        } catch (IOException e) {
            throw new BodhiException(e);
        }
    }

    @Override
    public ObjectMapper newInstance() {
        return JacksonSerializer.mapper;
    }

}
