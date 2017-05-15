package com.yonyou.dcs.util;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;

import com.infoservice.dms.cgcsl.vo.BaseVO;

public class DEUtil {

	public static boolean compareUpdateDate(Timestamp sourceDate,Timestamp targetDate){
		if(targetDate==null)
			return false;
		if(sourceDate!=null){
			if(sourceDate.compareTo(targetDate)<0)
				return true;
			else
				return false;
		}
		return true;
	}
	/**
	 * 
	* @Title: transType 
	* @Description: TODO(将查出的数据转成HashMap类型 3代框架DE模块集合类型只支持HashMap) 
	* @param @param vos
	* @param @return    设定文件 
	* @return HashMap<Integer,BaseVO>    返回类型 
	* @throws
	 */
	public static <T extends BaseVO> HashMap<Integer, T> transType(List<T> vos) {
		HashMap<Integer, T> map = new HashMap<Integer, T>();
		if (null == vos || vos.size() == 0) {
			return map;
		}
		for (int i = 0; i < vos.size(); i++) {
			map.put(i, vos.get(i));
		}
		return map;
	}
	
	/**
	 * 
	* @Title: assembleBody 
	* @Description: TODO(遍历VO 组装body) 
	* @param @param <T>
	* @param @param vos
	* @param @return    设定文件 
	* @return HashMap<String,Serializable>    返回类型 
	* @throws
	 */
	public static <T extends BaseVO> HashMap<String, Serializable> assembleBody(List<T> vos) {
		HashMap<String, Serializable> body = new HashMap<String, Serializable>();
		for (int i = 0; i < vos.size(); i++) {
			body.put(String.valueOf(i), vos.get(i));
		}
		return body;
	}
	
	/**
	 * 
	* @Title: assembleBody 
	* @Description: TODO(遍历VO 组装body) 
	* @param @param <T>
	* @param @param vos
	* @param @return    设定文件 
	* @return HashMap<String,Serializable>    返回类型 
	* @throws
	 */
	public static HashMap<String, Serializable> assembleBody(String index,Serializable vos) {
		HashMap<String, Serializable> body = new HashMap<String, Serializable>();
		body.put(index,vos);
		return body;
	}
}