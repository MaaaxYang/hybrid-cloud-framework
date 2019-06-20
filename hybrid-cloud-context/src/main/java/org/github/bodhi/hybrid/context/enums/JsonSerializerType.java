package org.github.bodhi.hybrid.context.enums;

import org.github.bodhi.hybrid.context.serializers.JacksonSerializer;

/**
 * @program: bestsign-distributed
 * @description:
 * @author: Maxxx.Yg
 * @create: 2019-03-09 11:58
 **/
public enum JsonSerializerType {
    JACAKSON(JacksonSerializer.class.getCanonicalName())
    ;

    private String name;

    JsonSerializerType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
