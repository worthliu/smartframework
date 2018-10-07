package com.worth.smart.framework.common.utils;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;

/**
 * @ClassName FileUtil
 * @Description 文件操作工具类
 * @Author Administrator
 * @Date 2018/10/4 17:19
 * @Version 1.0
 */
public class FileUtil {
    private static final Logger logger = LoggerFactory.getLogger(FileUtil.class);

    /**
     * 获取真实文件名（自动去掉文件路径）
     * @param fileName
     * @return
     */
    public static String getRealFileName(String fileName){
        return FilenameUtils.getName(fileName);
    }

    /**
     * 创建文件
     * @param filePath
     * @return
     */
    public static File createFile(String filePath){
        File file;
        try{
            file = new File(filePath);
            File parentDir = file.getParentFile();
            if (!parentDir.exists()){
                FileUtils.forceMkdir(parentDir);
            }
        } catch (Exception e) {
            logger.error("create file failure", e);
            throw new RuntimeException(e);
        }
        return file;
    }
}
