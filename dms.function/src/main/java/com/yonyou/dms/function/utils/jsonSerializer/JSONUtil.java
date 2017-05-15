
/** 
*Copyright 2017 Yonyou Corporation Ltd. All Rights Reserved.
* This software is published under the terms of the Yonyou Software
* License version 1.0, a copy of which has been included with this
* distribution in the LICENSE.txt file.
*
* @Project Name : dms.function
*
* @File name : JSONUtil.java
*
* @Author : zhangxc
*
* @Date : 2017年1月13日
*
----------------------------------------------------------------------------------
*     Date       Who       Version     Comments
* 1. 2017年1月13日    zhangxc    1.0
*
*
*
*
----------------------------------------------------------------------------------
*/
	
package com.yonyou.dms.function.utils.jsonSerializer;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.yonyou.dms.function.exception.UtilException;
import com.yonyou.dms.function.utils.common.StringUtils;

/**
* JSON 转换公共方法
* @author zhangxc
* @date 2017年1月13日
*/

public class JSONUtil {

    /**
     * 将json转化为实体POJO
     * @param jsonStr
     * @param obj
     * @return
     * @throws Exception 
     */
    public static<T> Object jsonToObj(String jsonStr,Class<T> obj) {
        T t = null;
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            t = objectMapper.readValue(jsonStr,
                    obj);
        } catch (Exception e) {
            throw new UtilException("json 转换失败",e);
        }
        return t;
    }
     
    /**
     * 将实体POJO转化为JSON
     * @param <T>
     * @param obj
     * @return
     * @throws JSONException
     * @throws IOException
     */
    public static<T> String objectToJson(T obj) {
        String jsonStr = null;
        
        if(obj!=null){
            ObjectMapper mapper = new ObjectMapper(); 
            try {
                 jsonStr =  mapper.writeValueAsString(obj);
            } catch (IOException e) {
                throw new UtilException("json 转换失败",e);
            }
        }
        return jsonStr;
    }
    
    /**
     * 
    *
    * @author zhangxc
    * @ description JSON转换为MAP
    * @date 2016年12月7日
    * @param json
    * @return
    * @throws Exception
     */
    public static Map<String , Object> jsonToMap(String json) {
        ObjectMapper mapper = new ObjectMapper();  
        try{  
            @SuppressWarnings("unchecked")
            Map<String , Object> map = (Map<String , Object>)mapper.readValue(json, Map.class);
            return map;
        }catch(Exception e){  
            throw new UtilException("json 转换失败",e);
        }
    }
    /**
     * 
    *
    * @author 夏威
    * @ description JSON转换为List
    * @date 2016年12月7日
    * @param json
    * @return
    * @throws Exception
     */
    public static <T> List<T>  jsonToList(String json,Class<T> obj) {
    	ObjectMapper mapper = new ObjectMapper();  
    	JavaType javaType = getCollectionType(ArrayList.class, obj);
    	if(StringUtils.isNullOrEmpty(json)){
    		return null;
    	}
        try{  
        	if(json.indexOf("[")==-1&& json.indexOf("]")==-1){
        		json="["+json+"]";
        	}
        	json = objectToJson(json).toString();
    		json = json.replace("\"", "");
    		json = json.replace("\\", "");
    		json = json.replace(" {", "{");
    		json = json.replace("} ", "}");
    		json = json.replace("'", "");
    		json = json.replace("{", "{\"");
    		json = json.replace("}", "\"}");
    		json = json.replace(",", "\",\"");
    		json = json.replace(":", "\":\"");
    		json = json.replace("}\",\"{", "},{");
            List<T> t = (List<T>) mapper.readValue(json,javaType);
            return t;
        }catch(Exception e){  
            throw new UtilException("json 转换失败",e);
        }
    }
    
    /**
     * @author xiawei
     * 获取javaType
     * @param collectionClass
     * @param elementClasses
     * @return
     */
	public static JavaType getCollectionType(Class<?> collectionClass, Class<?>... elementClasses) {   
		 ObjectMapper mapper = new ObjectMapper();  
		 return mapper.getTypeFactory().constructParametricType(collectionClass, elementClasses);   
	}  
}
