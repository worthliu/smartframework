package com.worth.smart.framework.common.helper;

import com.worth.smart.framework.common.annotation.Aspect;
import com.worth.smart.framework.common.annotation.Service;
import com.worth.smart.framework.common.annotation.Transaction;
import com.worth.smart.framework.proxy.Proxy;
import com.worth.smart.framework.proxy.ProxyManager;
import com.worth.smart.framework.proxy.impl.AbstractAspectProxy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.annotation.Annotation;
import java.util.*;

/**
 * @ClassName AopHelper
 * @Description 切面助手类
 * @Author Administrator
 * @Date 2018/10/4 9:46
 * @Version 1.0
 */
public class AopHelper {
    private static final Logger logger = LoggerFactory.getLogger(AopHelper.class);

    static {
        try{
            Map<Class<?>, Set<Class<?>>> proxyMap = createProxyMap();
            Map<Class<?>, List<Proxy>> targetMap = createTargetMap(proxyMap);
            for (Map.Entry<Class<?>, List<Proxy>> targetEntry : targetMap.entrySet()){
                Class<?> targetClass = targetEntry.getKey();
                List<Proxy> proxyList = targetEntry.getValue();
                Object proxy = ProxyManager.createProxy(targetClass, proxyList);
                BeanHelper.setBean(targetClass, proxy);
            }
        }catch (Exception e){
            logger.error("aop initialize failure", e);
        }
    }


//    /**
//     * @return
//     * @throws Exception
//     */
//    private static Map<Class<?>, Set<Class<?>>> createProxyMap() throws Exception{
//        Map<Class<?>, Set<Class<?>>> proxyMap = new HashMap<>();
//        Set<Class<?>> proxyClassSet = ClassHelper.getClassSetBySuper(AbstractAspectProxy.class);
//        for (Class<?> proxyClass : proxyClassSet){
//            if(proxyClass.isAnnotationPresent(Aspect.class)){
//                Aspect aspect = proxyClass.getAnnotation(Aspect.class);
//                Set<Class<?>> targetClassSet = createTargetClassSet(aspect);
//                proxyMap.put(proxyClass, targetClassSet);
//            }
//        }
//        return proxyMap;
//    }

    /**
     * @param aspect
     * @return
     * @throws Exception
     */
    private static Set<Class<?>> createTargetClassSet(Aspect aspect){
        Set<Class<?>> targetClassSet = new HashSet<>();
        Class<? extends Annotation> annotation = aspect.value();
        if(annotation != null && !annotation.equals(Aspect.class)){
            targetClassSet.addAll(ClassHelper.getClassSetByAnnotation(annotation));
        }
        return targetClassSet;
    }

    /**
     * @param proxyMap
     * @return
     * @throws Exception
     */
    private static Map<Class<?>, List<Proxy>> createTargetMap(Map<Class<?>, Set<Class<?>>> proxyMap) throws Exception{
        Map<Class<?>, List<Proxy>> targetMap = new HashMap<>();
        for (Map.Entry<Class<?>, Set<Class<?>>> proxyEntity : proxyMap.entrySet()){
            Class<?> proxyClass = proxyEntity.getKey();
            Set<Class<?>> targetClassSet = proxyEntity.getValue();
            for(Class<?> targetClass : targetClassSet){
                Proxy proxy = (Proxy) proxyClass.newInstance();
                if(targetMap.containsKey(targetClass)){
                    targetMap.get(targetClass).add(proxy);
                }else {
                    List<Proxy> proxyList = new ArrayList<>();
                    proxyList.add(proxy);
                    targetMap.put(targetClass, proxyList);
                }
            }
        }
        return targetMap;
    }

    /**
     * @return
     */
    private static Map<Class<?>, Set<Class<?>>> createProxyMap(){
        Map<Class<?>, Set<Class<?>>> proxyMap = new HashMap<>();
        addAspectProxy(proxyMap);
        addTransactionProxy(proxyMap);
        return proxyMap;
    }

    /**
     * @param proxyMap
     */
    private static void addAspectProxy(Map<Class<?>,Set<Class<?>>> proxyMap) {
        Set<Class<?>> proxyClassSet = ClassHelper.getClassSetBySuper(Aspect.class);
        for (Class<?> proxyClass : proxyClassSet){
            if(proxyClass.isAnnotationPresent(Aspect.class)){
                Aspect aspect = proxyClass.getAnnotation(Aspect.class);
                Set<Class<?>> targetClassSet = createTargetClassSet(aspect);
                proxyMap.put(proxyClass, targetClassSet);
            }
        }
    }

    /**
     * @param proxyMap
     */
    private static void addTransactionProxy(Map<Class<?>,Set<Class<?>>> proxyMap) {
        Set<Class<?>> serviceClassSet = ClassHelper.getClassSetByAnnotation(Service.class);
        proxyMap.put(Transaction.class, serviceClassSet);
    }
}
