package com.worth.smart.framework.common.bean;

import java.lang.reflect.Method;

/**
 * @ClassName Handler
 * @Description 封装Action信息
 * @Author Administrator
 * @Date 2018/10/3 10:01
 * @Version 1.0
 */
public class Handler {
    private Class<?> controllerClass;

    private Method actionMethod;

    public Handler(Class<?> controllerClass, Method actionMethod){
        this.controllerClass = controllerClass;
        this.actionMethod = actionMethod;
    }

    public Class<?> getControllerClass() {
        return controllerClass;
    }

    public Method getActionMethod() {
        return actionMethod;
    }
}
