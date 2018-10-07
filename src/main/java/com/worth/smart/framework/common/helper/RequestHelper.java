package com.worth.smart.framework.common.helper;

import com.worth.smart.framework.common.bean.FormParam;
import com.worth.smart.framework.common.bean.Param;
import com.worth.smart.framework.common.constant.MagicConstant;
import com.worth.smart.framework.common.utils.CodecUtil;
import com.worth.smart.framework.common.utils.StreamUtil;
import com.worth.smart.framework.common.utils.StringUtil;
import org.apache.commons.lang3.ArrayUtils;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Enumeration;
import java.util.List;

/**
 * @ClassName RequestHelper
 * @Description 请求助手类
 * @Author Administrator
 * @Date 2018/10/4 17:27
 * @Version 1.0
 */
public class RequestHelper {
    /**
     * @param request
     * @return
     * @throws IOException
     */
    public static Param createParam(HttpServletRequest request) throws IOException {
        List<FormParam> formParamList = new ArrayList<>();
        formParamList.addAll(parseParameterNames(request));
        formParamList.addAll(parseInputStream(request));
        return new Param(formParamList);
    }

    /**
     * @param request
     * @return
     */
    private static List<? extends FormParam> parseParameterNames(HttpServletRequest request) {
        List<FormParam> formParamList = new ArrayList<>();
        Enumeration<String> paramNames = request.getParameterNames();
        while (paramNames.hasMoreElements()){
            String fieldName = paramNames.nextElement();
            String[] fieldValues = request.getParameterValues(fieldName);
            if(ArrayUtils.isNotEmpty(fieldValues)){
                Object fieldValue;
                if (fieldValues.length == 1){
                    fieldValue = fieldValues[0];
                }else{
                    StringBuffer sb = new StringBuffer();
                    for (int i = 0; i < fieldValues.length; i++){
                        sb.append(fieldValues[i]);
                        if(i != fieldValues.length - 1){
                            sb.append(MagicConstant.SEPARATOR);
                        }
                    }
                    fieldValue = sb.toString();
                }
                formParamList.add(new FormParam(fieldName, fieldValue));
            }
        }
        return formParamList;
    }

    /**
     * @param request
     * @return
     * @throws IOException
     */
    private static List<? extends FormParam> parseInputStream(HttpServletRequest request) throws IOException {
        List<FormParam> formParamList = new ArrayList<>();
        String body = CodecUtil.decodeURL(StreamUtil.getString(request.getInputStream()));
        if(StringUtil.isNotEmpty(body)){
            String[] kvs = StringUtil.splitString(body, "&");
            if(ArrayUtils.isNotEmpty(kvs)){
                for (String kv : kvs){
                    String[] array = StringUtil.splitString(kv, "=");
                    if (ArrayUtils.isNotEmpty(array) && array.length == 2){
                        String fieldName = array[0];
                        String fieldValue = array[1];
                        formParamList.add(new FormParam(fieldName, fieldValue));
                    }
                }
            }
        }
        return formParamList;
    }
}
