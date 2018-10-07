package com.worth.smart.framework.common.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Java为我们提供4中元注解(元注解负责注解其他注解)
 *
 * <strong>Target(说明了该注解所修饰的对象范围):</strong>
 * <ul>
 *     <li>PACKAGES:包</li>
 *     <li>TYPE:(类/接口/枚举/Annotation类型)</li>
 *     <li>类型成员(METHOD:方法/CONSTRUCTOR:构造方法/成员变量/枚举值)</li>
 *     <li>PARAMETER:方法参数</li>
 *     <li>LOCAL_VARIABLE:本地变量</li>
 * </ul>
 * <strong>Retention(该注解被保留的时间长短):</strong>
 * <ul>
 *     <li>SOURCE:在源文件中有效</li>
 *     <li>CLASS:在class文件中有效</li>
 *     <li>RUNTIME:在运行时有效</li>
 * </ul>
 * <strong>Documented(描述其他类型Annotation应该被作为被标注的程序成员的公共API)</strong>
 * <strong>Inherited(阐述了某个被标注的类型是被继承的):</strong>
 *
 * //
 * 控制器注解，可以用于标注类、接口、枚举、Annotation类型，运行时有效
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface Controller {
}
