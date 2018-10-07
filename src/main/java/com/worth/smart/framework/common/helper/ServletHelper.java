package com.worth.smart.framework.common.helper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * @ClassName ServletHelper
 * @Description Servlet 助手类
 * @Author Administrator
 * @Date 2018/10/4 20:11
 * @Version 1.0
 */
public class ServletHelper {
    private static final Logger logger = LoggerFactory.getLogger(ServletHelper.class);
    //
    private static final ThreadLocal<ServletHelper> SERVLET_HELPER_HOLDER = new ThreadLocal<>();

    private HttpServletRequest request;
    private HttpServletResponse response;

    private ServletHelper(HttpServletRequest request, HttpServletResponse response) {
        this.request = request;
        this.response = response;
    }

    /**
     * @param request
     * @param response
     */
    public static void init(HttpServletRequest request, HttpServletResponse response){
        SERVLET_HELPER_HOLDER.set(new ServletHelper(request, response));
    }

    /**
     *
     */
    public static void destory(){
        SERVLET_HELPER_HOLDER.remove();
    }

    /**
     * @return
     */
    private static HttpServletRequest getRequest() {
        return SERVLET_HELPER_HOLDER.get().request;
    }

    /**
     * @return
     */
    private static HttpServletResponse getResponse() {
        return SERVLET_HELPER_HOLDER.get().response;
    }

    private static HttpSession getSession(){
        return getRequest().getSession();
    }

    private static ServletContext getServletContext(){
        return getRequest().getServletContext();
    }

    public static void setRequestAttribute(String key, Object value){
        getRequest().setAttribute(key, value);
    }

    public static <T> T getRequestAttribute(String key){
        return (T) getRequest().getAttribute(key);
    }

    public static void remoteRequestAttribute(String key){
        getRequest().removeAttribute(key);
    }

    public static void sendRedirect(String location){
        try {
            getResponse().sendRedirect(getRequest().getContextPath() + location);
        } catch (Exception e) {
            logger.error("redirect failure", e);
        }
    }

    public static void setSessionAttribute(String key, Object value){
        getSession().setAttribute(key, value);
    }

    public static void removeSessionAttribute(String key){
        getRequest().getSession().removeAttribute(key);
    }

    public static void invalidateSession(){
        getRequest().getSession().invalidate();
    }
}
