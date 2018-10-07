package com.worth.smart.framework.common.helper;

import com.worth.smart.framework.common.utils.ReflectionUtil;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * @ClassName BeanHelper
 * @Description Bean助手类
 * @Author Administrator
 * @Date 2018/10/2 22:28
 * @Version 1.0
 */
public class BeanHelper {
    private static final Map<Class<?>, Object> BEAN_MAP = new HashMap<>();

    static {
        Set<Class<?>> beanClassSet = ClassHelper.getBeanClassSet();
        for (Class<?> beanClass : beanClassSet){
            Object obj = ReflectionUtil.newInstance(beanClass);
            BEAN_MAP.put(beanClass, obj);
        }
    }

    /**
     * 获取Bean映射
     * @return
     */
    public static Map<Class<?>, Object> getBeanMap(){
        return BEAN_MAP;
    }

    /**
     * 获取Bean实例
     * @param clazz
     * @param <T>
     * @return
     */
    public static <T> T getBean(Class<T> clazz){
        if(!BEAN_MAP.containsKey(clazz)){
            throw new RuntimeException("can not get bean by class:" + clazz);
        }
        return (T) BEAN_MAP.get(clazz);
    }

    /**
     * 设置Bean实例
     * @param clazz
     * @param obj
     */
    public static void setBean(Class<?> clazz, Object obj){
        BEAN_MAP.put(clazz, obj);
    }
}
