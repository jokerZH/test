package com.didi.test.match;

import com.alibaba.fastjson.annotation.JSONField;
import com.didi.test.json.JsonDeserializer;
import lombok.Data;

@Data
public class LengthMatch extends Matcher{
   private String id;

   @JSONField(deserializeUsing = JsonDeserializer.class)
   private Matcher matcher;


   public static MatchType getType() {
      return MatchType.Length;
   }
}
