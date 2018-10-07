package com.worth.smart.framework.proxy;

/**
 * @ClassName Proxy
 * @Description 代理接口
 * @Author Administrator
 * @Date 2018/10/3 22:03
 * @Version 1.0
 */
public interface Proxy {
    Object doProxy(ProxyChain proxyChain) throws Throwable;
}
