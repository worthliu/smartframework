package com.worth.smart.framework.common.constant;

/**
 * @ClassName ConfigConstant
 * @Description 相关配置项常量
 * @Author Administrator
 * @Date 2018/10/2 18:35
 * @Version 1.0
 */
public interface ConfigConstant {
    String CONFIG_FILE = "smart.properties";

    String JDBC_DRIVER = "smart.framework.jdbc.driver";
    String JDBC_URL = "smart.framework.jdbc.url";
    String JDBC_USERNAME = "smart.framework.jdbc.username";
    String JDBC_PASSWORD = "smart.framework.jdbc.password";

    String APP_BASE_PACKAGE = "smart.framework.app.base_package";
    String APP_JSP_PATH = "smart.framework.app.jsp_path";
    String APP_ASSET_PATH = "smart.framework.app.asset_path";

    String APP_UPLOAD_LIMIT = "smart.framework.app.upload.limit";
}
