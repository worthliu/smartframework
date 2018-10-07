package com.worth.smart.framework.common.bean;

/**
 * @ClassName FormParam
 * @Description 封装表单参数
 * @Author Administrator
 * @Date 2018/10/4 14:55
 * @Version 1.0
 */
public class FormParam {
    private String fieldName;
    private Object fieldValue;

    public FormParam(String fieldName, Object fieldValue) {
        this.fieldName = fieldName;
        this.fieldValue = fieldValue;
    }

    public String getFieldName() {
        return fieldName;
    }

    public Object getFieldValue() {
        return fieldValue;
    }
}
