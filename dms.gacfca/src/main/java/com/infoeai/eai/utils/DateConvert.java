package com.infoeai.eai.utils;


import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.beanutils.ConversionException;
import org.apache.commons.beanutils.Converter;

public class DateConvert implements Converter {
	static SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");// HH:mm:ss
	static SimpleDateFormat dfs = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");// HH:mm:ss

	public DateConvert() { 
	} 

	@SuppressWarnings("unchecked")
	public Object convert(Class type, Object value) { 
		try { 
			if(value instanceof Date){
				return value;
			}else{
				if(value==null) return null; 
				if(((String)value).trim().length()==0) return null; 
				if(value instanceof String) { 
					//解析接收到字符串 
					try{
						return df.parse((String)value);
					}catch(Exception t){
						return dfs.parse((String)value); 
					} 
				}else { 
					//其他异常 
					throw new ConversionException("输入的不是字符类型" + value.getClass()); 
				}
			}	
		} catch (Exception ex) {
		    //发生解析异常
   			throw new ConversionException("输入的日期类型不合乎yyyy-MM-dd HH:mm:ss 或者 yyyy-MM-dd" + value.getClass());
		} 
	} 
}