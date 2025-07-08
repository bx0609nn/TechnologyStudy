package com.bx.hutool_;

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
        //1、将JSON字符串转为Java对象 JSONUtil.toBean()
        String jsonStr = "{\"id\":12,\"name\":\"Tom\",\"age\":25}";
        Student student = JSONUtil.toBean(jsonStr, Student.class);
        System.out.println("student = " + student.toString());

        //2、Java对象转JSON字符串 JSONUtil.toJsonStr()
        Student student1 = new Student(1l,"Tom", 25,"男");
        String studentJsonStr = JSONUtil.toJsonStr(student1);
        System.out.println("studentJsonStr = " + studentJsonStr);

        //3、JSON字符串转JSON对象 JSONUtil.parseObj()
        String jsonStr2 = "{\"data\": {\n" +
                "  \"name\": \"Tom\",\n" +
                "  \"age\": 25\n" +
                "}}";
        JSONObject jsonObject = JSONUtil.parseObj(jsonStr2);
        System.out.println("jsonObject = " + jsonObject);
        String data = jsonObject.getStr("data");
        System.out.println("jsonObject.getObj(\"data\") = " + data);
        HashMap<String,String> hashMap = JSONUtil.toBean(data, HashMap.class);
        String name = hashMap.get("name");
        System.out.println("name = " + name);

        //总结：转Java对象JSONUtil.toBean()
        //     转JSON字符串JSONUtil.toJsonStr()
        //     转JSON对象JSONUtil.parseObj()

        String jsonStr3 = "{\"data\":{\n" +
                "  \"name\": \"Tom\",\n" +
                "  \"age\": 25\n" +
                "}}";
        JSONObject jsonObject1 = JSONUtil.parseObj(jsonStr3);
        System.out.println("jsonObject1.getObj(\"data\") = " + jsonObject1.getObj("data"));//JSON格式对象
        System.out.println("jsonObject1.getBean(\"data\",Student.class) = " + jsonObject1.getBean("data", Student.class));//Student

        //4、JSONObject获取数据
        //1.根据key获取嵌套JSON对象 getObj("data")，返回的是一个JSON对象{"name":"Tom","age":25}
        //2.根据key获取字符串类型数据 getStr("name")
        //3.根据key获取double类型数据 getInt("age")
        //4.根据key获取对象 getBean("data", Student.class))，内部实现会先将 key 对应的值解析为一个 JSONObject，然后调用 toBean 方法转换为 Java 对象。

    }
}
