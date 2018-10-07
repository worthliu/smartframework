package com.worth.smart.framework.proxy.impl;

import com.worth.smart.framework.common.annotation.Transaction;
import com.worth.smart.framework.common.helper.DatabaseHelper;
import com.worth.smart.framework.proxy.Proxy;
import com.worth.smart.framework.proxy.ProxyChain;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;

/**
 * @ClassName TransactionProxy
 * @Description 事务代理类
 * @Author Administrator
 * @Date 2018/10/4 13:53
 * @Version 1.0
 */
public class TransactionProxy implements Proxy {
    private static final Logger logger = LoggerFactory.getLogger(TransactionProxy.class);

    private static final ThreadLocal<Boolean> FLAG_HOLDER = ThreadLocal.withInitial(()->false);

    @Override
    public Object doProxy(ProxyChain proxyChain) throws Throwable {
        Object result;
        boolean flag = FLAG_HOLDER.get();
        Method method = proxyChain.getTargetMethod();
        if(!flag && method.isAnnotationPresent(Transaction.class)){
            FLAG_HOLDER.set(true);
            try {
                DatabaseHelper.beginTransaction();
                logger.debug(method.toString() + "|begin transaction");
                result = proxyChain.doProxyChain();
                DatabaseHelper.commitTransaction();
                logger.debug(method.toString() + "|commit transaction");
            }catch (Exception e){
                DatabaseHelper.rollbackTransaction();
                logger.debug(method.toString() + "|rollback transaction");
                throw e;
            }finally {
                FLAG_HOLDER.remove();
            }
        }else {
            result = proxyChain.doProxyChain();
        }
        return result;
    }
}
