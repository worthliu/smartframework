package com.worth.smart.framework.common.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;

/**
 * @ClassName StreamUtil
 * @Description 流操作工具类
 * @Author Administrator
 * @Date 2018/10/3 15:55
 * @Version 1.0
 */
public final class StreamUtil {
    private static final Logger logger = LoggerFactory.getLogger(StreamUtil.class);

    /**
     * @param is
     * @return
     */
    public static String getString(InputStream is) {
        StringBuffer sb = new StringBuffer();
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(is));
            String line;
            while ((line = reader.readLine()) != null){
                sb.append(line);
            }
        }catch (Exception e){
            logger.error("reading stream failure", e);
        }
        return sb.toString();
    }

    /**
     * 将输入流复制到输出流
     * @param inputStream
     * @param outputStream
     */
    public static void copyStream(InputStream inputStream, OutputStream outputStream) {
        int length;
        byte[] buffer = new byte[4 * 1024];
        try {
            while ((length = inputStream.read(buffer, 0, buffer.length)) != -1){
                outputStream.write(buffer, 0, length);
            }
            outputStream.flush();
        } catch (Exception e) {
           logger.error("copy stream failure", e);
           throw new RuntimeException(e);
        }finally {
            try {
                inputStream.close();
                outputStream.close();
            } catch (Exception e) {
                logger.error("close stream failure", e);
            }
        }
    }
}
