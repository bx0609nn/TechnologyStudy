package com.bx.hutool_;

import cn.hutool.core.bean.BeanUtil;
import com.bx.dto.StudentDto;
import com.bx.entity.Student;

import java.util.HashMap;
import java.util.Map;

/**
 * @author lili
 * @version 1.0
 * @date 2025/1/21 9:39
 * @description
 */
public class BeanUtil_ {
    public static void main(String[] args) {
        //1.Bean转Map,Java对象转map,key是属性名，value是属性值
        Student student = new Student(1l, "jack", 22, "男");
        Map<String, Object> beanToMap = BeanUtil.beanToMap(student);
        System.out.println("beanToMap = " + beanToMap);

        //2.Map转Bean
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("id", 2l);
        hashMap.put("name", "tom");
        Student mapToBean = BeanUtil.mapToBean(hashMap, Student.class, true);
        System.out.println("mapToBean = " + mapToBean);

        //3.属性拷贝
        StudentDto studentDto = new StudentDto();
        BeanUtil.copyProperties(student, studentDto);
        System.out.println("studentDto = " + studentDto);

        //4.将对象转换为指定类型的 JavaBean
        StudentDto toBean = BeanUtil.toBean(student, StudentDto.class);
        System.out.println("toBean = " + toBean);

    }
}
