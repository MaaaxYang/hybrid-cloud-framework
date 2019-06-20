package org.github.bodhi.hybrid.context.serializers.conditions;


import org.github.bodhi.hybrid.context.enums.JsonSerializerType;
import org.github.bodhi.hybrid.utils.condition.LoadCondition;

/**
 * @program: bestsign-distributed
 * @description:
 * @author: Maxxx.Yg
 * @create: 2019-03-09 12:12
 **/
public class JsonLoadCondition implements LoadCondition {

    private JsonSerializerType type;

    public JsonLoadCondition(JsonSerializerType type) {
        this.type = type;
    }

    @Override
    public boolean judge(Object object) {
        if (object!=null){
            if (type.getName().equals(object.getClass().getCanonicalName())){
                return true;
            }
        }
        return false;
    }
}
