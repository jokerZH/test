package com.didi.test.json;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.parser.DefaultJSONParser;
import com.alibaba.fastjson.parser.deserializer.ObjectDeserializer;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.net.URL;
import java.net.URLDecoder;
import java.util.HashSet;
import java.util.Set;

public class JsonDeserializer implements ObjectDeserializer {

    public static final String FIELD_TYPE_STR = "type";
    public static final String METHOD_GETTYPE_STR = "getType";
    public Object deserialze(DefaultJSONParser defaultJSONParser, Type type, Object o) {
        JSONObject jsonObj = null;

        try {
            jsonObj = defaultJSONParser.parseObject();

            String fatherClazzName = ((Class) type).getName();
            String packStr = fatherClazzName.substring(0, fatherClazzName.lastIndexOf("."));
            Set<String> sonClazzNames = getSons(packStr);

            if(!jsonObj.containsKey(FIELD_TYPE_STR)) {
                throw new Exception("config not have type field, config:" + jsonObj);
            }

            String configType = jsonObj.getString(FIELD_TYPE_STR);
            jsonObj.remove(FIELD_TYPE_STR);


            String typeClazzName = null;
            for (String sonName : sonClazzNames) {
                if (sonName.toLowerCase().endsWith(FIELD_TYPE_STR)) {
                    typeClazzName = sonName;
                }
            }
            sonClazzNames.remove(typeClazzName);
            sonClazzNames.remove(fatherClazzName);


            Class typeClazz = Class.forName(typeClazzName);
            Method method = typeClazz.getMethod(METHOD_GETTYPE_STR, String.class);
            Object tObj = method.invoke(null, configType);


            Class matchClazz = null;
            for (String sonClazzName : sonClazzNames) {
                Class sonClazz = Class.forName(sonClazzName);
                try {
                    Method bMethod = sonClazz.getMethod(METHOD_GETTYPE_STR);
                    if (tObj == bMethod.invoke(null)) {

                        if(matchClazz==null) {
                            matchClazz = sonClazz;

                        } else {
                            throw new Exception("two son clazz match same type, config:" + jsonObj + ", clazz:" + matchClazz.getName());
                        }

                    }

                } catch (NoSuchMethodException e) {
                    continue;
                }
            }

            if(matchClazz==null) {
                throw new Exception("no match son clazz, config:" + jsonObj);
            }

            return JSONObject.parseObject(jsonObj.toString(), matchClazz);

        } catch (Throwable t) {
            throw new RuntimeException("config str:" + jsonObj, t);
        }
    }

    public int getFastMatchToken() {
        return 0;
    }

    public static Set<String> getSons(String packStr) throws UnsupportedEncodingException {
        String path = packStr.replace(".", "/");
        URL url = Thread.currentThread().getContextClassLoader().getResource(path);
        if (url != null && url.toString().startsWith("file")) {
            String filePath = URLDecoder.decode(url.getFile(), "utf-8");

            Set<String> ret = new HashSet<String>();
            File dir = new File(filePath);
            for (String son : dir.list()) {
                if (!son.endsWith(".class")) {
                    continue;
                }

                ret.add(packStr + "." + son .substring(0, son.length() - ".class".length()));
            }
            return ret;
        } else {
            return null;
        }
    }
}
