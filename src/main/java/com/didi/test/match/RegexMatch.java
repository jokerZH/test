package com.didi.test.match;

import lombok.Data;

@Data
public class RegexMatch extends Matcher {
   private String name;

   public static MatchType getType() {
      return MatchType.Regex;
   }
}
