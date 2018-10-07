package com.worth.smart.framework.loader;

import com.worth.smart.framework.common.helper.*;
import com.worth.smart.framework.common.utils.ClassUtil;

/**
 * @ClassName HelperLoader
 * @Description 加载相应的Helper类
 * @Author Administrator
 * @Date 2018/10/3 10:13
 * @Version 1.0
 */
public class HelperLoader {
    public static void init(){
        Class<?>[] classList = {ClassHelper.class, BeanHelper.class, AopHelper.class, IocHelper.class, ControllerHelper.class};
        for (Class<?> clazz : classList){
            ClassUtil.loadClass(clazz.getName());
        }
    }
}
