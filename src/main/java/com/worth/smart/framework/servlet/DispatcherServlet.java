package com.worth.smart.framework.servlet;

import com.worth.smart.framework.common.bean.Data;
import com.worth.smart.framework.common.bean.Handler;
import com.worth.smart.framework.common.bean.Param;
import com.worth.smart.framework.common.bean.View;
import com.worth.smart.framework.common.constant.MagicConstant;
import com.worth.smart.framework.common.helper.*;
import com.worth.smart.framework.common.utils.*;
import com.worth.smart.framework.loader.HelperLoader;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Method;
import java.util.Map;

/**
 * @ClassName DispatcherServlet
 * @Description 请求转发器
 * @Author Administrator
 * @Date 2018/10/3 15:12
 * @Version 1.0
 */
@WebServlet(urlPatterns = "/*", loadOnStartup = 0)
public class DispatcherServlet extends HttpServlet {
    @Override
    public void init(ServletConfig config) {
        // 初始化相关Helper类
        HelperLoader.init();
        // 获取ServletContext对象(用于注册Servlet)
        ServletContext servletContext = config.getServletContext();
        // 注册处理JSP的Servlet
        ServletRegistration jspServlet = servletContext.getServletRegistration("jsp");
        jspServlet.addMapping(ConfigHelper.getAppJspPath() + "*");
        // 注册处理静态资源的默认Servlet
        ServletRegistration defaultServlet = servletContext.getServletRegistration("default");
        defaultServlet.addMapping(ConfigHelper.getAppAssetPath() + "*");
        // 上传文件助手类初始化
        UploadHelper.init(servletContext);
    }

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // 获取请求方法与请求路径
        String requestMethod = req.getMethod().toLowerCase();
        String requestPath = req.getPathInfo();
        //
        if (MagicConstant.FAVICON_REQUEST_PATH.equals(requestPath)){
            return;
        }
        // 获取Action处理器
        Handler handler = ControllerHelper.getHandler(requestMethod, requestPath);
        if(handler != null){
            // 获取Controller类及其Bean实例
            Class<?> controllerClass = handler.getControllerClass();
            Object controllerBean = BeanHelper.getBean(controllerClass);
            // 创建请求参数对象
            Param param;
            if(UploadHelper.isMultipart(req)){
                param = UploadHelper.createParam(req);
            }else {
                param = RequestHelper.createParam(req);
            }
            // 调用Action方法
            Object result;
            Method actionMethod = handler.getActionMethod();
            if(param.isEmpty()){
                result = ReflectionUtil.invokeMethod(controllerBean, actionMethod);
            }else{
                result = ReflectionUtil.invokeMethod(controllerBean, actionMethod, param);
            }
            // 处理Action方法返回值
            if(result instanceof View){
                handleViewResult(req, resp, (View) result);
            }else if(result instanceof Data){
                handleDataResult(resp, (Data) result);
            }else{
                throw new RuntimeException("The return value data format cannot be parsed");
            }
        }else {
            throw new RuntimeException("Unable to get controller:" + requestMethod);
        }
    }

    /**
     * JSON数据
     * @param resp
     * @param result
     * @throws IOException
     */
    private void handleDataResult(HttpServletResponse resp, Data result) throws IOException {
        Data data = result;
        Object model = data.getModel();
        if(model != null){
            resp.setContentType("application/json");
            resp.setCharacterEncoding("UTF-8");
            PrintWriter writer = resp.getWriter();
            String json = JsonUtil.toJson(model);
            writer.write(json);
            writer.flush();
            writer.close();
        }
    }

    /**
     * JSP页面
     * @param req
     * @param resp
     * @param result
     * @throws IOException
     * @throws ServletException
     */
    private void handleViewResult(HttpServletRequest req, HttpServletResponse resp, View result) throws IOException, ServletException {
        View view = result;
        String path = view.getPath();
        if(StringUtil.isNotEmpty(path)) {
            if (path.startsWith(MagicConstant.JSP_START_LABEL)) {
                resp.sendRedirect(req.getContextPath() + path);
            } else {
                Map<String, Object> model = view.getModel();
                for (Map.Entry<String, Object> entry : model.entrySet()) {
                    req.setAttribute(entry.getKey(), entry.getValue());
                }
                req.getRequestDispatcher(ConfigHelper.getAppJspPath() + path).forward(req, resp);
            }
        }
    }
}
