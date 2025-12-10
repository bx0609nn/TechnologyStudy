package com.bx.aop;

import com.bx.annotation.AutoFill;
import com.bx.constant.FieldConstant;
import com.bx.enumtype.OperationType;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.Date;

/**
 * @author lili
 * @version 1.0
 * @date 2025/12/10 11:28
 * @description 自动填充创建时间和更新时间切面
 */
@Aspect
@Component
@Slf4j
public class AutoFillAspect {
    //切入点
    @Pointcut("@annotation(com.bx.annotation.AutoFill)")
    public void pointcut() {
    }

    //前置通知
    @Before("pointcut()")
    public void before(JoinPoint joinPoint) throws Exception {
        log.info("开始填充...");
        //1.获取方法签名
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();

        //2.获取方法上的注解
        AutoFill autoFill = method.getAnnotation(AutoFill.class);
        OperationType operationType = autoFill.value();

        //3.获取方法参数
        Object[] args = joinPoint.getArgs();
        if (args == null || args.length == 0) {
            return;
        }
        //4.获取第一个参数
        Object arg = args[0];
        if (arg == null) {
            return;
        }
        //5.递归填充该参数内的所有时间字段(包括类属性的时间字段)
        fillTimeFields(arg, operationType);
        log.info("填充完成！");
    }

    /**
     * 设置字段值
     * @param obj       对象
     * @param fieldName 字段名
     * @param value     值
     */
    private void setFieldValue(Object obj, String fieldName, Object value) {
        try {
            //获取obj对象的私有属性字段
            Field field = obj.getClass().getDeclaredField(fieldName);
            //开启爆破，允许反射访问对象的private属性
            field.setAccessible(true);
            //设置属性值
            field.set(obj, value);
        } catch (NoSuchFieldException e) {
            log.debug("{} 类中没有 {} 字段", obj.getClass().getSimpleName(), fieldName);
        } catch (Exception e) {
            log.error("设置字段 {} 失败", fieldName, e);
        }
    }

    /**
     * 递归填充时间字段
     * @param obj           要填充的对象
     * @param operationType 操作类型（ADD 或 UPDATE）
     */
    private void fillTimeFields(Object obj, OperationType operationType) {
        Class<?> clazz = obj.getClass();
        try {
            // 1. 填充本类的时间字段
            Date now = new Date();
            if (operationType == OperationType.ADD) {
                setFieldValue(obj, FieldConstant.CREATE_TIME, now);
                setFieldValue(obj, FieldConstant.UPDATE_TIME, now);
            } else if (operationType == OperationType.UPDATE) {
                setFieldValue(obj, "updateTime", now);
            }
            // 2. 递归填充本类中的类对象
            Field[] fields = clazz.getDeclaredFields();
            for (Field field : fields) {
                field.setAccessible(true);
                Object fieldValue = field.get(obj);

                // 跳过本类中值为空的属性
                if (fieldValue == null) {
                    continue;
                }
                // 跳过本类中是基本类型和常用类型的属性
                if (isPrimitiveOrCommon(fieldValue.getClass())) {
                    continue;
                }
                // 处理集合类型（List<Subject>、List<Teacher> 等）
                if (fieldValue instanceof Collection) {
                    Collection<?> collection = (Collection<?>) fieldValue;
                    for (Object item : collection) {
                        fillTimeFields(item, operationType);
                    }
                } else {
                    // 处理普通对象类型
                    fillTimeFields(fieldValue, operationType);
                }
            }
        } catch (Exception e) {
            log.error("填充 {} 的时间字段失败", clazz.getSimpleName(), e);
        }
    }

    /**
     * 判断是否是基本类型或常用类型
     * @param clazz 类型
     * @return 是否是基本类型
     */
    private boolean isPrimitiveOrCommon(Class<?> clazz) {
        return clazz.isPrimitive()
                || clazz == String.class
                || clazz == Integer.class
                || clazz == Long.class
                || clazz == Double.class
                || clazz == Float.class
                || clazz == Boolean.class
                || clazz == Character.class
                || clazz == Byte.class
                || clazz == Short.class
                || clazz == Date.class
                || Number.class.isAssignableFrom(clazz)
                || clazz.isEnum();
    }
}
