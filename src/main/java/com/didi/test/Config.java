package com.didi.test;

import com.alibaba.fastjson.annotation.JSONField;
import com.didi.test.json.JsonDeserializer;
import com.didi.test.match.Matcher;
import lombok.Data;

@Data
public class Config {
    @JSONField(deserializeUsing = JsonDeserializer.class)
    private Matcher matcher;
}
