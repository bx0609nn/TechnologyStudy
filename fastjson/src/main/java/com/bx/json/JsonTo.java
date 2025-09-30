package com.bx.json;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.bx.entity.Student;

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
        //1、将json字符串解析为JSONObject JSON.parseObject()--------------------------------------------------------------
        String jsonObjStr = "{\n" +
                "    \"student\": {\n" +
                "        \"id\": 1,\n" +
                "        \"name\": \"zhangsan\",\n" +
                "        \"age\": 15\n" +
                "    },\n" +
                "    \"teacher\": {\n" +
                "        \"id\": 1,\n" +
                "        \"name\": \"lisi\",\n" +
                "        \"age\": 36\n" +
                "    }\n" +
                "}";

        JSONObject jsonObject = JSON.parseObject(jsonObjStr);

        //1.1JSONObject获取子JSONObject jsonObject.getJSONObject()
        JSONObject student = jsonObject.getJSONObject("student");
        //1.2JSONObject获取简单属性
        int id = student.getIntValue("id");//int类型的值，不存在该键则返回0
        String name = student.getString("name");//String类型的值，若不存在该键则返回null
        jsonObject.getString("student");//也可以将嵌套的对象获取为String，然后可以直接解析为Java对象



        //2、将json字符串解析为JSONArray JSON.parseArray()----------------------------------------------------------------
        String jsonArrayStr = "[{\"name\":\"张三\"}, {\"name\":\"李四\"}]";
        JSONArray jsonArray = JSON.parseArray(jsonArrayStr);
        //2.1 获取JSONArray中的第一个对象 jsonArray.getJSONObject(0)
        System.out.println("jsonArray.getJSONObject(0) = " + jsonArray.getJSONObject(0));



        //3、将json字符串解析为Java对象 JSON.parseObject();---------------------------------------------------------------
        Student studentObj = JSON.parseObject(String.valueOf(student), Student.class);
        System.out.println("studentObj = " + studentObj);

        jsonObject.getString("student");//也可以将嵌套的对象获取为String，然后可以直接解析为Java对象
        JSON.parseObject(jsonObject.getString("student"), Student.class);


        //4、将Java对象序列化为json字符串 JSON.toJSONString()-------------------------------------------------------------
        Student student1 = new Student(2l, "Jerry", 16, "男");

        String javaToJsonString = JSON.toJSONString(student1);
        System.out.println("javaToJsonString = " + javaToJsonString);



        //总结：
        //字符串转JSONObject JSON.parseObject();
        //字符串转JSONArray JSON.parseArray();
        //字符串转Java对象 JSON.parseObject(jsonString, Map.class);
        //Java对象转json字符串 JSON.toJSONString();
    }
}
