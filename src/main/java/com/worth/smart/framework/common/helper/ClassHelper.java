package com.worth.smart.framework.common.helper;

import com.worth.smart.framework.common.annotation.Controller;
import com.worth.smart.framework.common.annotation.Service;
import com.worth.smart.framework.common.utils.ClassUtil;

import java.lang.annotation.Annotation;
import java.util.HashSet;
import java.util.Set;

/**
 * @ClassName ClassHelper
 * @Description 类操作助手类
 * @Author Administrator
 * @Date 2018/10/2 21:30
 * @Version 1.0
 */
public class ClassHelper {
    /**
     * 定义类集合
     */
    private static final Set<Class<?>> CLASS_SET;

    static {
        String basePackage = ConfigHelper.getAppBasePackage();
        CLASS_SET = ClassUtil.getClassSet(basePackage);
    }

    /**
     * @return
     */
    public static Set<Class<?>> getClassSet(){
        return CLASS_SET;
    }

    /**
     * @return
     */
    public static Set<Class<?>> getServiceClassSet(){
        Set<Class<?>> classSet = new HashSet<>();
        for (Class<?> clazz : CLASS_SET){
            if (clazz.isAnnotationPresent(Service.class)){
                classSet.add(clazz);
            }
        }
        return classSet;
    }

    /**
     * @return
     */
    public static Set<Class<?>> getControllerClassSet(){
        Set<Class<?>> classSet = new HashSet<>();
        for (Class<?> clazz : CLASS_SET){
            if(clazz.isAnnotationPresent(Controller.class)){
                classSet.add(clazz);
            }
        }
        return classSet;
    }

    /**
     * 获取应用包名下所有Bean类
     * @return
     */
    public static Set<Class<?>> getBeanClassSet(){
        Set<Class<?>> beanClassSet = new HashSet<>();
        beanClassSet.addAll(getServiceClassSet());
        beanClassSet.addAll(getControllerClassSet());
        return beanClassSet;
    }

    /**
     * 获取应用包下某父类(或接口)的所有子类(或实现类)
     * @param superClass
     * @return
     */
    public static Set<Class<?>> getClassSetBySuper(Class<?> superClass){
        Set<Class<?>> classSet = new HashSet<>();
        for (Class<?> clazz : CLASS_SET){
            if(superClass.isAssignableFrom(clazz) && !superClass.equals(clazz)){
                classSet.add(clazz);
            }
        }
        return classSet;
    }

    /**
     * 获取应用包下带有某注解的所有类
     * @param annotationClass
     * @return
     */
    public static Set<Class<?>> getClassSetByAnnotation(Class<? extends Annotation> annotationClass){
        Set<Class<?>> classSet = new HashSet<>();
        for (Class<?> clazz : CLASS_SET){
            if(clazz.isAnnotationPresent(annotationClass)){
                classSet.add(clazz);
            }
        }
        return classSet;
    }
}
