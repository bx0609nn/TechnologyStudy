package com.bx.json;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * @author lili
 * @version 1.0
 * @date 2024/11/4 15:20
 * @description
 */
public class JsonTo {
    public static void main(String[] args) {
        //将json对象解析为JsonObject
        String jsonObj = "{\"id\":1,\"name\":\"zhangsan\",\"age\":25}";

        JSONObject object = JSON.parseObject(jsonObj);
        System.out.println("object = " + object);
        //获取简单属性
        int id = object.getIntValue("id");//int类型的值
        System.out.println("id = " + id);
        String name = object.getString("name");//字符串类型的值
        System.out.println("name = " + name);

        //将json解析为JSONArray
        String jsonArrayStr = "[{\"name\":\"张三\"}, {\"name\":\"李四\"}]";
        JSONArray jsonArray = JSON.parseArray(jsonArrayStr);
        //获取json数组中的第一个对象
        System.out.println("jsonArray.getJSONObject(0) = " + jsonArray.getJSONObject(0));

        //将Java对象序列化为json
        HashMap hashMap = new HashMap<>();
        hashMap.put("age",22);
        hashMap.put("name","张三");

        String jsonString = JSON.toJSONString(hashMap);
        System.out.println("jsonString = " + jsonString);

        //将JSON解析为Java对象
        Map map = JSON.parseObject(jsonString, Map.class);
        System.out.println("map = " + map);

        //总结：
        //转JSON对象 JSON.parseObject();
        //转JSON数组 JSON.parseArray();
        //转JSON字符串 JSON.toJSONString();
        //转Java对象 JSON.parseObject(jsonString, Map.class);



    }
}
