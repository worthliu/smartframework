package com.worth.smart.framework.common.bean;

import com.worth.smart.framework.common.constant.MagicConstant;
import com.worth.smart.framework.common.utils.CastUtil;
import com.worth.smart.framework.common.utils.CollectionUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @ClassName Param
 * @Description 请求参数对象
 * @Author Administrator
 * @Date 2018/10/3 10:17
 * @Version 1.0
 */
public class Param {
    private List<FormParam> formParamList;
    private List<FileParam> fileParamList;

    public Param(List<FormParam> formParamList, List<FileParam> fileParamList) {
        this.formParamList = formParamList;
        this.fileParamList = fileParamList;
    }

    public Param(List<FormParam> formParamList) {
        this.formParamList = formParamList;
    }

    /**
     * 获取请求参数映射
     * @return
     */
    public Map<String, Object> getFieldMap(){
        Map<String, Object> fieldMap = new HashMap<>();
        if (CollectionUtil.isNotEmpty(formParamList)){
            for (FormParam formParam : formParamList){
                String fieldName = formParam.getFieldName();
                Object fieldValue = formParam.getFieldValue();
                if (fieldMap.containsKey(fieldName)){
                    fieldValue = fieldMap.get(fieldName) + MagicConstant.SEPARATOR + fieldValue;
                }
                fieldMap.put(fieldName, fieldValue);
            }
        }
        return fieldMap;
    }

    /**
     * 获取上传文件映射
     * @return
     */
    public Map<String, List<FileParam>> getFileMap(){
        Map<String, List<FileParam>>  fileMap = new HashMap<>();
        if(CollectionUtil.isNotEmpty(fileParamList)){
            for (FileParam fileParam : fileParamList){
                String fieldName = fileParam.getFieldName();
                List<FileParam> fileParams;
                if(fileMap.containsKey(fieldName)){
                    fileParams = fileMap.get(fieldName);
                }else{
                    fileParams = new ArrayList<>();
                }
                fileParams.add(fileParam);
                fileMap.put(fieldName, fileParams);
            }
        }
        return fileMap;
    }

    /**
     * 获取所有上传文件
     * @param fieldName
     * @return
     */
    public List<FileParam> getFileParamList(String fieldName){
        return getFileMap().get(fieldName);
    }

    /**
     * 获取唯一上传文件
     * @param fieldName
     * @return
     */
    public FileParam getFile(String fieldName){
        List<FileParam> fileParamList = getFileParamList(fieldName);
        if(CollectionUtil.isNotEmpty(fileParamList) &&
                fileParamList.size() == 1){
            return fileParamList.get(0);
        }
        return null;
    }

    /**
     * @return
     */
    public boolean isEmpty(){
        return CollectionUtil.isEmpty(formParamList) && CollectionUtil.isEmpty(fileParamList);
    }

    public String getString(String name){
        return CastUtil.castString(getFieldMap().get(name));
    }

    public double getDouble(String name){
        return CastUtil.castDouble(getFieldMap().get(name));
    }

    public long getLong(String name){
        return CastUtil.castLong(getFieldMap().get(name));
    }

    public int getInt(String name){
        return CastUtil.castInt(getFieldMap().get(name));
    }

    public boolean getBoolean(String name){
        return CastUtil.castBoolean(getFieldMap().get(name));
    }
}
