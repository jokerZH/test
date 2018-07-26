package com.didi.test.match;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLDecoder;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public enum MatchType {
    Length("length"),Regex("regex");

    private String name;

    MatchType(String name) {
        this.name = name;
    }

    public static MatchType getType(String str) throws Exception {
        for(MatchType type : values()) {
            if(type.name.equalsIgnoreCase(str)) {
                return type;
            }
        }

        throw new Exception("unknown type: " + str);
    }

    @Override
    public String toString() {
        return name;
    }
}

