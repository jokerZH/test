package com.didi.test.json;

import com.alibaba.fastjson.serializer.BeforeFilter;

import java.lang.reflect.Method;

public class JsonFilter extends BeforeFilter  {

    public void writeBefore(Object o) {
        try {
            Method method;
            try {
                method = o.getClass().getMethod(JsonDeserializer.METHOD_GETTYPE_STR);
            } catch (NoSuchMethodException e) {
                return;
            }
            String typeStr = method.invoke(null).toString();
            writeKeyValue(JsonDeserializer.FIELD_TYPE_STR, typeStr);

        } catch (Throwable t) {
            throw new RuntimeException("file error, o: " + o, t);
        }
    }
}
