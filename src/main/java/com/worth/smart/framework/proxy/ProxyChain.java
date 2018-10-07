package com.worth.smart.framework.proxy;

import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName ProxyChain
 * @Description 代理链
 * @Author Administrator
 * @Date 2018/10/3 22:04
 * @Version 1.0
 */
public class ProxyChain {
    /**
     * 目标类
     */
    private final Class<?> targetClass;
    /**
     * 目标对象
     */
    private final Object targetObject;
    /**
     * 目标方法
     */
    private final Method targetMethod;
    /**
     * 方法代理
     */
    private final MethodProxy methodProxy;
    /**
     * 方法参数
     */
    private final Object[] methodParams;

    private List<Proxy> proxyList;
    private int proxyIndex = 0;

    public ProxyChain(Class<?> targetClass, Object targetObject, Method targetMethod, MethodProxy methodProxy, Object[] methodParams, List<Proxy> proxyList){
        this.targetClass = targetClass;
        this.targetObject = targetObject;
        this.targetMethod = targetMethod;
        this.methodProxy = methodProxy;
        this.methodParams = methodParams;
        this.proxyList = proxyList;
    }

    public Class<?> getTargetClass() {
        return targetClass;
    }

    public Method getTargetMethod() {
        return targetMethod;
    }

    public Object[] getMethodParams() {
        return methodParams;
    }

    /**
     * @return
     * @throws Throwable
     */
    public Object doProxyChain() throws Throwable{
        Object methodResult;
        if (proxyIndex < proxyList.size()){
            methodResult = proxyList.get(proxyIndex++).doProxy(this);
        }else {
            methodResult = methodProxy.invokeSuper(targetObject, methodParams);
        }
        return methodResult;
    }
}
