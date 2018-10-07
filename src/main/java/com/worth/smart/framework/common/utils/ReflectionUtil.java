package com.worth.smart.framework.common.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * @ClassName ReflectionUtil
 * @Description 反射工具类
 * @Author Administrator
 * @Date 2018/10/2 21:39
 * @Version 1.0
 */
public class ReflectionUtil {
    private static final Logger logger = LoggerFactory.getLogger(ReflectionUtil.class);

    /**
     * 创建实例
     * @param className
     * @return
     */
    public static Object newInstance(String className){
        Object instance;
        try {
            instance = Class.forName(className).newInstance();
        } catch (Exception e){
            logger.error("new instance failure", e);
            throw new RuntimeException(e);
        }
        return instance;
    }

    /**
     * 创建实例
     * @param clazz
     * @return
     */
    public static Object newInstance(Class<?> clazz){
        Object instance;
        try {
            instance = clazz.newInstance();
        } catch (Exception e){
            logger.error("new instance failure", e);
            throw new RuntimeException(e);
        }
        return instance;
    }

    /**
     * 调用方法
     * @param obj
     * @param method
     * @param args
     * @return
     */
    public static Object invokeMethod(Object obj, Method method, Object... args){
        Object result;
        try {
            method.setAccessible(true);
            result = method.invoke(obj, args);
        } catch (Exception e) {
           logger.error("invoke method failure", e);
           throw new RuntimeException(e);
        }
        return result;
    }

    /**
     * 设置成员变量的值
     * @param obj
     * @param field
     * @param value
     */
    public static void setField(Object obj, Field field, Object value){
        try {
            field.setAccessible(true);
            field.set(obj, value);
        } catch (Exception e) {
            logger.error("set field failure", e);
            throw new RuntimeException(e);
        }
    }
}
