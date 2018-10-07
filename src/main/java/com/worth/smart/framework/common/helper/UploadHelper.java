package com.worth.smart.framework.common.helper;

import com.worth.smart.framework.common.bean.FileParam;
import com.worth.smart.framework.common.bean.FormParam;
import com.worth.smart.framework.common.bean.Param;
import com.worth.smart.framework.common.utils.CollectionUtil;
import com.worth.smart.framework.common.utils.FileUtil;
import com.worth.smart.framework.common.utils.StreamUtil;
import com.worth.smart.framework.common.utils.StringUtil;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @ClassName UploadHelper
 * @Description 文件上传助手类
 * @Author Administrator
 * @Date 2018/10/4 16:46
 * @Version 1.0
 */
public class UploadHelper {
    private static final Logger logger = LoggerFactory.getLogger(UploadHelper.class);

    /**
     * apache commons fileUpload 提供的Servlet文件上传对象
     */
    private static ServletFileUpload servletFileUpload;

    /**
     * @param servletContext
     */
    public static void init(ServletContext servletContext){
        File repository = (File) servletContext.getAttribute("javax.servlet.context.tempdir");
        servletFileUpload = new ServletFileUpload(new DiskFileItemFactory(DiskFileItemFactory.DEFAULT_SIZE_THRESHOLD, repository));
        int uploadLimit = ConfigHelper.getAppUploadLimit();
        if (uploadLimit != 0){
            servletFileUpload.setFileSizeMax(uploadLimit * 1024 * 1024);
        }
    }

    /**
     * @param request
     * @return
     */
    public static boolean isMultipart(HttpServletRequest request){
        return ServletFileUpload.isMultipartContent(request);
    }

    /**
     * @return
     */
    public static Param createParam(HttpServletRequest request){
        List<FormParam> formParamList = new ArrayList<>();
        List<FileParam> fileParamList = new ArrayList<>();
        //
        try{
            Map<String, List<FileItem>> fileItemListMap = servletFileUpload.parseParameterMap(request);
            if (CollectionUtil.isNotEmpty(fileItemListMap)){
                for (Map.Entry<String, List<FileItem>> fileItemListEntry : fileItemListMap.entrySet()){
                    String fieldName = fileItemListEntry.getKey();
                    List<FileItem> fileItemList = fileItemListEntry.getValue();
                    if (CollectionUtil.isNotEmpty(fileItemList)){
                        for (FileItem fileItem : fileItemList){
                            if (fileItem.isFormField()){
                                String fieldValue = fileItem.getString("UTF-8");
                                formParamList.add(new FormParam(fieldName, fieldValue));
                            }else{
                                String fileName = FileUtil.getRealFileName(new String(fileItem.getName().getBytes(), "UTF-8"));
                                if(StringUtil.isNotEmpty(fileName)){
                                    long fileSize = fileItem.getSize();
                                    String contentType = fileItem.getContentType();
                                    InputStream inputStream = fileItem.getInputStream();
                                    fileParamList.add(new FileParam(fieldName, fileName, fileSize, contentType, inputStream));
                                }
                            }
                        }
                    }
                }
            }
        } catch (Exception e){
            logger.error("create param failure", e);
            throw new RuntimeException(e);
        }
        return new Param(formParamList, fileParamList);
    }

    public static void uploadFile(String basePath, FileParam fileParam){
        try{
            if (fileParam != null){
                String filePath = basePath + fileParam.getFileName();
                FileUtil.createFile(filePath);
                InputStream inputStream = new BufferedInputStream(fileParam.getInputStream());
                OutputStream outputStream = new BufferedOutputStream(new FileOutputStream(filePath));
                StreamUtil.copyStream(inputStream, outputStream);
            }
        } catch (Exception e) {
            logger.error("upload file failure", e);
            throw new RuntimeException(e);
        }
    }

    public static void uploadFile(String basePath, List<FileParam> fileParamList){
        try {
            if(CollectionUtil.isNotEmpty(fileParamList)){
                for(FileParam fileParam : fileParamList){
                    uploadFile(basePath, fileParam);
                }
            }
        }catch (Exception e){
            logger.error("upload file failure", e);
            throw new RuntimeException(e);
        }
    }
}
