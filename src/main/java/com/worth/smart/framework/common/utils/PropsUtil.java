package com.worth.smart.framework.common.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * @ClassName PropsUtil
 * @Description 属性文件加载工具类
 * @Author Administrator
 * @Date 2018/9/25 21:46
 * @Version 1.0
 */
public class PropsUtil {
    private static final Logger logger = LoggerFactory.getLogger(PropsUtil.class);

    /**
     * 加载属性文件
     * @param fileName
     * @return
     */
    public static Properties loadProps(String fileName){
        Properties props = null;
        InputStream is = null;
        try {
            is = Thread.currentThread().getContextClassLoader().getResourceAsStream(fileName);
            if(is == null){
                throw new FileNotFoundException(fileName + " file is not found");
            }
            props = new Properties();
            props.load(is);
        }catch (IOException e){
            logger.error("loadProps| analysis the properties of file appear exceptions:", e);
        }finally {
            if(is != null){
                try{
                    is.close();
                }catch (IOException e){
                    logger.error("close input stream failure", e);
                }
            }
        }
        return props;
    }

    /**
     * 获取字符型属性(默认值为空字符串)
     * @param props
     * @param key
     * @return
     */
    public static String getString(Properties props, String key){
        return getString(props, key, "");
    }

    /**
     * 获取字符型属性(可指定默认值)
     * @param props
     * @param key
     * @param defaultValue
     * @return
     */
    public static String getString(Properties props, String key, String defaultValue){
        String value = defaultValue;
        if(props != null && props.contains(key)){
            value = props.getProperty(key);
        }
        return value;
    }

    /**
     * @param props
     * @param key
     * @return
     */
    public static Integer getInteger(Properties props, String key){
        return getInteger(props, key, 0);
    }

    /**
     * @param props
     * @param key
     * @param defaultValue
     * @return
     */
    public static Integer getInteger(Properties props, String key, int defaultValue){
        int value = defaultValue;
        if(props != null && props.contains(key)){
            value = CastUtil.castInt(props.getProperty(key));
        }
        return value;
    }

    /**
     * @param props
     * @param key
     * @return
     */
    public static Boolean getBoolean(Properties props, String key){
        return getBoolean(props, key, false);
    }

    /**
     * @param props
     * @param key
     * @param defaultValue
     * @return
     */
    public static Boolean getBoolean(Properties props, String key, Boolean defaultValue){
        Boolean value = defaultValue;
        if(props != null && props.contains(key)){
            value = CastUtil.castBoolean(props.getProperty(key));
        }
        return value;
    }
}
