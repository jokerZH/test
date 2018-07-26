package com.didi.test;


import com.alibaba.fastjson.JSONObject;
import com.didi.test.json.JsonFilter;

public class Test2 {
    public static void main(String[] args) {
        String str = "{'matcher':{ 'type':'length','id':'xxx' , 'matcher':{'type':'regex', 'name':'hello'}} }";


        Config config = JSONObject.parseObject(str, Config.class);

        String ret = JSONObject.toJSONString(config, new JsonFilter());

        config = JSONObject.parseObject(ret, Config.class);

        System.out.println(config);
    }

    public static void loop() {
        while(true) {
            sleep(1000);
            System.out.println("hello");
        }
    }
    public static void sleep(int time) {
        try {
            Thread.sleep(time);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
