package com.worth.smart.framework.common.utils;

/**
 * @ClassName CastUtil
 * @Description TODO
 * @Author Administrator
 * @Date 2018/9/25 22:08
 * @Version 1.0
 */
public class CastUtil {

    public static String castString(Object obj){
        return CastUtil.castString(obj, "");
    }

    public static String castString(Object obj, String defaultValue){
        return obj != null ? String.valueOf(obj) : defaultValue;
    }

    public static double castDouble(Object obj){
        return castDouble(obj, 0);
    }

    public static double castDouble(Object obj, double defaultValue){
        double doubleValue = defaultValue;
        if (obj != null){
            String strValue = castString(obj);
            if(StringUtil.isNotEmpty(strValue)){
                try{
                    doubleValue = Double.parseDouble(strValue);
                }catch (NumberFormatException e){
                    doubleValue = defaultValue;
                }
            }
        }
        return doubleValue;
    }

    public static long castLong(Object obj){
        return castLong(obj, 0);
    }

    public static long castLong(Object obj, long defaultValue){
        long longValue = defaultValue;
        if (obj != null){
            String strValue = castString(obj);
            if(StringUtil.isNotEmpty(strValue)){
                try{
                    longValue = Long.parseLong(strValue);
                }catch (NumberFormatException e){
                    longValue = defaultValue;
                }
            }
        }
        return longValue;
    }

    public static int castInt(Object obj){
        return castInt(obj, 0);
    }

    public static int castInt(Object obj, int defaultValue){
        int intValue = defaultValue;
        if (obj != null){
            String strValue = castString(obj);
            if(StringUtil.isNotEmpty(strValue)){
                try{
                    intValue = Integer.parseInt(strValue);
                }catch (NumberFormatException e){
                    intValue = defaultValue;
                }
            }
        }
        return intValue;
    }

    public static boolean castBoolean(Object obj){
        return castBoolean(obj, false);
    }

    public static boolean castBoolean(Object obj, boolean defaultValue){
        boolean longValue = defaultValue;
        if (obj != null){
            String strValue = castString(obj);
            if(StringUtil.isNotEmpty(strValue)){
                try{
                    longValue = Boolean.parseBoolean(strValue);
                }catch (NumberFormatException e){
                    longValue = defaultValue;
                }
            }
        }
        return longValue;
    }
}
