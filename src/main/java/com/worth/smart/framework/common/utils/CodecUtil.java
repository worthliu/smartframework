package com.worth.smart.framework.common.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;

/**
 * @ClassName CodecUtil
 * @Description 编码与解码操作工具类
 * @Author Administrator
 * @Date 2018/10/3 16:07
 * @Version 1.0
 */
public class CodecUtil {
    private static final Logger logger = LoggerFactory.getLogger(CodecUtil.class);

    /**
     * 编码URL
     * @param source
     * @return
     */
    public static String  encodeURL(String source){
        String target;
        try {
            target = URLEncoder.encode(source, "UTF-8");
        } catch (Exception e) {
            logger.error("encode url failure", e);
            throw new RuntimeException(e);
        }
        return target;
    }

    /**
     * 解码URL
     * @param source
     * @return
     */
    public static String decodeURL(String source){
        String target;
        try {
            target = URLDecoder.decode(source, "UTF-8");
        } catch (Exception e) {
            logger.error("decode url failure", e);
            throw new RuntimeException(e);
        }
        return target;
    }

    /**
     * @param source
     * @return
     */
    public static String md5(String source){
        return DigestUtils.md5Hex(source);
    }
}
