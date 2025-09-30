package com.bx.hutool_;

import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.bx.entity.Student;

import java.util.HashMap;

/**
 * @author lili
 * @version 1.0
 * @date 2025/1/10 16:36
 * @description
 */
public class JSONUtil_ {
    public static void main(String[] args) {
        //1、将json字符串转JSONObject JSONUtil.parseObj()----------------------------------------------------------------
        String jsonObjStr = "{\"data\": {\n" +
                "  \"name\": \"Tom\",\n" +
                "  \"age\": 25\n" +
                "}}";
        JSONObject jsonObject = JSONUtil.parseObj(jsonObjStr);

        //1.1 JSONObject获取子JSONObject
        JSONObject data = jsonObject.getJSONObject("data");
        //1.2 JSONObject获取简单属性
        String name = data.getStr("name");//获取String类型值，若不存在改键则返回null
        Integer age = data.getInt("age");//获取int类型值，若不存在改键则返回null



        //2、将json字符串转为JSONArray JSONUtil.parseArray()--------------------------------------------------------------
        String jsonArrayStr = "[{\"name\":\"张三\"}, {\"name\":\"李四\"}]";
        JSONArray jsonArray = JSONUtil.parseArray(jsonArrayStr);
        //JSONArray获取第一个对象
        jsonArray.get(0,Student.class);



        //3、json字符串/JSONObject解析为Java对象 JSONUtil.toBean()--------------------------------------------------------
        //3.1 json字符串-->Java对象
        String jsonStr = "{\"id\":12,\"name\":\"Tom\",\"age\":25}";
        Student student = JSONUtil.toBean(jsonStr, Student.class);

        //3.2 JSONObject-->Java对象
        HashMap<String,String> hashMap = JSONUtil.toBean(data, HashMap.class);



        //4、Java对象转json字符串 JSONUtil.toJsonStr()--------------------------------------------------------------------
        Student student1 = new Student(1l,"Tom", 25,"男");

        String studentJsonStr = JSONUtil.toJsonStr(student1);
        System.out.println("studentJsonStr = " + studentJsonStr);



        //总结：
        //1.字符串转JSONObject JSONUtil.parseObj()
        //2.字符串转JSONArray JSONUtil.parseArray();
        //3.字符串转Java对象 JSONUtil.toBean()
        //4.Java对象转json字符串 JSONUtil.toJsonStr()

        String jsonStr3 = "{\"data\":{\n" +
                "  \"name\": \"Tom\",\n" +
                "  \"age\": 25\n" +
                "}}";
        JSONObject jsonObject1 = JSONUtil.parseObj(jsonStr3);
        System.out.println("jsonObject1.getObj(\"data\") = " + jsonObject1.getObj("data"));//JSON格式对象
        System.out.println("jsonObject1.getBean(\"data\",Student.class) = " + jsonObject1.getBean("data", Student.class));//Student

        //JSONObject获取数据
        //1.根据key获取嵌套JSON对象 getJSONObject("data") 或 getObj("data")，返回的是一个JSON对象{"name":"Tom","age":25}
        //2.根据key获取字符串类型数据 getStr("name")
        //3.根据key获取int类型数据 getInt("age")
        //4.根据key获取对象 getBean("data", Student.class))，内部实现会先将 key 对应的值解析为一个 JSONObject，然后调用 toBean 方法转换为 Java 对象。

    }
}
