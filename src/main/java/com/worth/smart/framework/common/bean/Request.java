package com.worth.smart.framework.common.bean;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

/**
 * @ClassName Request
 * @Description 封装请求信息
 * @Author Administrator
 * @Date 2018/10/3 9:58
 * @Version 1.0
 */
public class Request {
    private String requestMethod;
    private String requestPath;

    public Request(String requestMethod, String requestPath){
        this.requestMethod = requestMethod;
        this.requestPath = requestPath;
    }

    public String getRequestMethod() {
        return requestMethod;
    }

    public String getRequestPath() {
        return requestPath;
    }

    @Override
    public int hashCode() {
        return HashCodeBuilder.reflectionHashCode(this);
    }

    @Override
    public boolean equals(Object obj) {
        return EqualsBuilder.reflectionEquals(this, obj);
    }
}
