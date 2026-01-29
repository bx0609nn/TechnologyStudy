//package com.bx.aop;
//
//import lombok.extern.slf4j.Slf4j;
//import org.aspectj.lang.JoinPoint;
//import org.aspectj.lang.annotation.Aspect;
//import org.aspectj.lang.annotation.Before;
//import org.aspectj.lang.annotation.Pointcut;
//import org.aspectj.lang.reflect.MethodSignature;
//import org.springframework.stereotype.Component;
//import org.springframework.web.bind.annotation.DeleteMapping;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//
//import java.lang.reflect.Method;
//
///**
// * @author lili
// * @version 1.0
// * @date 2025/12/10 16:50
// * @description 请求切面
// */
//@Aspect
//@Component
//@Slf4j
//public class RequestAspect {
//
//    @Pointcut("@annotation(org.springframework.web.bind.annotation.RequestMapping) || " +
//            "@annotation(org.springframework.web.bind.annotation.GetMapping) || " +
//            "@annotation(org.springframework.web.bind.annotation.PostMapping) || " +
//            "@annotation(org.springframework.web.bind.annotation.DeleteMapping)")
//    public void pointcut() {
//
//    }
//
//    @Before("pointcut()")
//    public void before(JoinPoint joinPoint) {
//        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
//        Method method = signature.getMethod();
//
//        String path = "";
//
//        // 检查各种映射注解
//        if (method.isAnnotationPresent(PostMapping.class)) {
//            String[] value = method.getAnnotation(PostMapping.class).value();
//            path = value.length > 0 ? value[0] : "";
//        } else if (method.isAnnotationPresent(GetMapping.class)) {
//            String[] value = method.getAnnotation(GetMapping.class).value();
//            path = value.length > 0 ? value[0] : "";
//        } else if (method.isAnnotationPresent(DeleteMapping.class)) {
//            String[] value = method.getAnnotation(DeleteMapping.class).value();
//            path = value.length > 0 ? value[0] : "";
//        } else if (method.isAnnotationPresent(RequestMapping.class)) {
//            String[] value = method.getAnnotation(RequestMapping.class).value();
//            path = value.length > 0 ? value[0] : "";
//        }
//
//        log.info("请求路径：{}", path);
//    }
//}
