package com.cz.spring.common.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class JSONUtil {

   
    public static int getCode(String json) {
        return getIntValue(json, "code");
    }

   
    public static String getMessage(String json) {
        return getString(json, "msg");
    }

   
    public static String getString(String json, String key) {
        String result = null;
        try {
            JSONObject jsonObject = JSON.parseObject(json);
            result = jsonObject.getString(key);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    
    public static int getIntValue(String json, String key) {
        int result = 0;
        try {
            JSONObject jsonObject = JSON.parseObject(json);
            result = jsonObject.getIntValue(key);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

   
    public static boolean getBooleanValue(String json, String key) {
        boolean result = false;
        try {
            JSONObject jsonObject = JSON.parseObject(json);
            result = jsonObject.getBooleanValue(key);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    
    public static long getLongValue(String json, String key) {
        long result = 0;
        try {
            JSONObject jsonObject = JSON.parseObject(json);
            result = jsonObject.getLongValue(key);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

   
    public static double getDoubleValue(String json, String key) {
        double result = 0;
        try {
            JSONObject jsonObject = JSON.parseObject(json);
            result = jsonObject.getDoubleValue(key);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    
    public static float getFloatValue(String json, String key) {
        float result = 0;
        try {
            JSONObject jsonObject = JSON.parseObject(json);
            result = jsonObject.getFloatValue(key);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    
    public static byte getByteValue(String json, String key) {
        byte result = 0;
        try {
            JSONObject jsonObject = JSON.parseObject(json);
            result = jsonObject.getByteValue(key);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    
    public static short getShortValue(String json, String key) {
        short result = 0;
        try {
            JSONObject jsonObject = JSON.parseObject(json);
            result = jsonObject.getShortValue(key);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    
    public static Integer getInteger(String json, String key) {
        Integer result = null;
        try {
            JSONObject jsonObject = JSON.parseObject(json);
            result = jsonObject.getInteger(key);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

   
    public static Boolean getBoolean(String json, String key) {
        Boolean result = null;
        try {
            JSONObject jsonObject = JSON.parseObject(json);
            result = jsonObject.getBoolean(key);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    
    public static Long getLong(String json, String key) {
        Long result = null;
        try {
            JSONObject jsonObject = JSON.parseObject(json);
            result = jsonObject.getLong(key);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

   
    public static Double getDouble(String json, String key) {
        Double result = null;
        try {
            JSONObject jsonObject = JSON.parseObject(json);
            result = jsonObject.getDouble(key);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    
    public static Float getFloat(String json, String key) {
        Float result = null;
        try {
            JSONObject jsonObject = JSON.parseObject(json);
            result = jsonObject.getFloat(key);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    
    public static Byte getByte(String json, String key) {
        Byte result = null;
        try {
            JSONObject jsonObject = JSON.parseObject(json);
            result = jsonObject.getByte(key);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    
    public static Short getShort(String json, String key) {
        Short result = null;
        try {
            JSONObject jsonObject = JSON.parseObject(json);
            result = jsonObject.getShort(key);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

   
    public static byte[] getBytes(String json, String key) {
        byte[] result = null;
        try {
            JSONObject jsonObject = JSON.parseObject(json);
            result = jsonObject.getBytes(key);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    
    public static BigInteger getBigInteger(String json, String key) {
        BigInteger result = null;
        try {
            JSONObject jsonObject = JSON.parseObject(json);
            result = jsonObject.getBigInteger(key);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    
    public static BigDecimal getBigDecimal(String json, String key) {
        BigDecimal result = null;
        try {
            JSONObject jsonObject = JSON.parseObject(json);
            result = jsonObject.getBigDecimal(key);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

   
    public static Date getDate(String json, String key) {
        Date result = null;
        try {
            JSONObject jsonObject = JSON.parseObject(json);
            result = jsonObject.getDate(key);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

  
    public static <T> T getObject(String json, String key, Class<T> clazz) {
        T result = null;
        try {
            JSONObject jsonObject = JSON.parseObject(json);
            result = jsonObject.getObject(key, clazz);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }


    public static <T> List<T> getArray(String json, String key, Class<T> clazz) {
        List<T> result = null;
        try {
            JSONObject jsonObject = JSON.parseObject(json);
            String string = jsonObject.getString(key);
            result = JSON.parseArray(string, clazz);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (result == null) {
            result = new ArrayList<>();
        }
        return result;
    }

    public static <T> T parseObject(String json, Class<T> clazz) {
        T result = null;
        try {
            result = JSON.parseObject(json, clazz);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }


    public static <T> List<T> parseArray(String json, Class<T> clazz) {
        List<T> result = null;
        try {
            result = JSON.parseArray(json, clazz);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (result == null) {
            result = new ArrayList<>();
        }
        return result;
    }

}
