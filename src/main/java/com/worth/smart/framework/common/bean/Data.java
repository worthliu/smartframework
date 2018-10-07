package com.worth.smart.framework.common.bean;

/**
 * @ClassName Data
 * @Description 返回数据对象
 * @Author Administrator
 * @Date 2018/10/3 15:10
 * @Version 1.0
 */
public class Data {
    private Object model;

    public Data(Object model){
        this.model = model;
    }

    public Object getModel() {
        return model;
    }
}
