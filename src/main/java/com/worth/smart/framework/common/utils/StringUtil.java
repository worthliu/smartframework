package com.worth.smart.framework.common.utils;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @ClassName StringUtil
 * @Description TODO
 * @Author Administrator
 * @Date 2018/9/27 21:57
 * @Version 1.0
 */
public class StringUtil {
    private static final Logger logger = LoggerFactory.getLogger(StringUtil.class);

    public static boolean isEmpty(String str){
        if(str != null){
            str = str.trim();
        }
        return StringUtils.isEmpty(str);
    }

    public static boolean isNotEmpty(String str){
        return !isEmpty(str);
    }

    /**
     * @param splitStr
     * @param splitMark
     * @return
     */
    public static String[] splitString(String splitStr, String splitMark){
        String[] splitRes;
        try {
            splitRes = splitStr.split(splitMark);
        }catch (Exception e){
            logger.error("split string failure", e);
            throw new RuntimeException(e);
        }
        return splitRes;
    }
}
