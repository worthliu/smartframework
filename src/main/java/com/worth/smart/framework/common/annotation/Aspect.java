package com.worth.smart.framework.common.annotation;

import java.lang.annotation.*;

/**
 * @ClassName Aspect
 * @Description 切面注解
 * @Author Administrator
 * @Date 2018/10/3 21:54
 * @Version 1.0
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface Aspect {
    Class<? extends Annotation> value();
}
