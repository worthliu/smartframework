package com.worth.smart.framework.proxy.impl;

import com.worth.smart.framework.proxy.Proxy;
import com.worth.smart.framework.proxy.ProxyChain;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;

/**
 * @ClassName AspectProxy
 * @Description 切面代理
 * @Author Administrator
 * @Date 2018/10/3 22:58
 * @Version 1.0
 */
public abstract class AbstractAspectProxy implements Proxy {
    private static final Logger logger = LoggerFactory.getLogger(AbstractAspectProxy.class);

    @Override
    public final Object doProxy(ProxyChain proxyChain) throws Throwable {
        Object result;
        Class<?> clazz = proxyChain.getTargetClass();
        Method method = proxyChain.getTargetMethod();
        Object[] params = proxyChain.getMethodParams();
        //
        begin();
        try {
            if (intercept(clazz, method, params)){
                before(clazz, method, params);
                result = proxyChain.doProxyChain();
                after(clazz, method, params, result);
            }else {
                result = proxyChain.doProxyChain();
            }
        }catch (Exception e){
            logger.error("proxy failure", e);
            error(clazz, method, params, e);
            throw e;
        }finally {
            end();
        }
        return result;
    }

    /**
     * 开头增强
     */
    public void begin(){

    }

    /**
     * 前置增强
     * @param clazz
     * @param method
     * @param params
     */
    public void before(Class<?> clazz, Method method, Object[] params){

    }

    /**
     * 后置增强
     * @param clazz
     * @param method
     * @param params
     * @param result
     */
    public void after(Class<?> clazz, Method method, Object[] params, Object result){

    }

    /**
     * 抛出增强
     * @param clazz
     * @param method
     * @param params
     * @param e
     */
    public void error(Class<?> clazz, Method method, Object[] params, Exception e){

    }

    /**
     * 引入增强
     * @param clazz
     * @param method
     * @param params
     * @return
     */
    public boolean intercept(Class<?> clazz, Method method, Object[] params){
        return true;
    }

    /**
     * 结尾增强
     */
    public void end(){

    }
}
