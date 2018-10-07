package com.worth.smart.framework.common.bean;

import java.util.HashMap;
import java.util.Map;

/**
 * @ClassName View
 * @Description 返回视图对象
 * @Author Administrator
 * @Date 2018/10/3 15:06
 * @Version 1.0
 */
public class View {
    private String path;
    private Map<String, Object> model;

    public View(String path){
        this.path = path;
        this.model = new HashMap<>();
    }

    public View addModel(String key, Object value){
        model.put(key, value);
        return this;
    }

    public String getPath() {
        return path;
    }

    public Map<String, Object> getModel() {
        return model;
    }
}
