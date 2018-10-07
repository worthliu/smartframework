package com.worth.smart.framework.common.helper;

import com.worth.smart.framework.common.annotation.Inject;
import com.worth.smart.framework.common.utils.CollectionUtil;
import com.worth.smart.framework.common.utils.ReflectionUtil;
import org.apache.commons.lang3.ArrayUtils;

import java.lang.reflect.Field;
import java.util.Map;

/**
 * @ClassName IocHelper
 * @Description 依赖注入助手类
 * @Author Administrator
 * @Date 2018/10/3 9:49
 * @Version 1.0
 */
public class IocHelper {
    static {
        //获取所有的bean类与bean实例之间的映射关系
        Map<Class<?>, Object> beanMap = BeanHelper.getBeanMap();
        if(CollectionUtil.isNotEmpty(beanMap)){
            // 遍历BeanMap
            for (Map.Entry<Class<?>, Object> beanEntry : beanMap.entrySet()){
                // 从BeanMap中获取Bean类与Bean实例
                Class<?> beanClass = beanEntry.getKey();
                Object beanInstance = beanEntry.getValue();
                // 获取Bean类定义的所有成员变量(Bean Field)
                Field[] beanFields = beanClass.getDeclaredFields();
                if(ArrayUtils.isNotEmpty(beanFields)){
                    // 遍历Bean Field
                    for (Field beanField : beanFields){
                        // 判断当前Bean Field是否带有Inject注解
                        if(beanField.isAnnotationPresent(Inject.class)){
                            // 在Bean Map 中获取Bean Field对应的实例
                            Class<?> beanFieldClass = beanField.getType();
                            Object beanFieldInstance = beanMap.get(beanFieldClass);
                            if(beanFieldInstance != null){
                                // 通过反射初始化BeanField的值
                                ReflectionUtil.setField(beanInstance, beanField, beanFieldInstance);
                            }
                        }
                    }
                }
            }
        }
    }
}
