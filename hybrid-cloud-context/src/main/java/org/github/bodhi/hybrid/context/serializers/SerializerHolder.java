package org.github.bodhi.hybrid.context.serializers;

import org.github.bodhi.hybrid.norms.exception.BestsignException;
import org.github.bodhi.hybrid.norms.serializers.Serializer;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

/**
 * @program: hybrid-cloud-framework
 * @description:
 * @author: Maxxx.Yg
 * @create: 2019-03-21 19:46
 **/
public class SerializerHolder {

    private final static SerializerHolder INSTANCE = SerializerHolderInner.HOLDER;

    private Serializer<String,ObjectMapper> serializer;

    public Serializer<String,ObjectMapper> getSerializer() {
        return serializer;
    }

    public void setSerializer(Serializer<String,ObjectMapper> serializer) {
        this.serializer = serializer;
    }

    private static class SerializerHolderInner{
        public final static SerializerHolder HOLDER = new SerializerHolder();
        static {
            HOLDER.setSerializer(new JacksonSerializer());
        }
    }

    public static SerializerHolder getInstance() {
        return INSTANCE;
    }

    public static String stringValue(Object obj){
        return INSTANCE.getSerializer().serialize(obj);
    }


    public static  <R> R toObject(String source, Class<R> clazz) {
        return INSTANCE.getSerializer().deserialize(source, clazz);
    }

    public static  <R> R toObject(String source, TypeReference typeReference) {
        try {
            return INSTANCE.getSerializer().newInstance().readValue(source,typeReference);
        } catch (IOException e) {
           throw new BestsignException(e);
        }
    }
}
