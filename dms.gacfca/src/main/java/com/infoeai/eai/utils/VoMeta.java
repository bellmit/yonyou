package com.infoeai.eai.utils;

import java.io.Serializable;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

public class VoMeta {
	private Class<?> cls;
	private LinkedList<String> mthName = new LinkedList<String>();

	public VoMeta(Object obj) {
		this.cls = obj.getClass();
		Method[] methods = this.cls.getMethods();
		String name = "";
		for (int i = 0; i < methods.length; i++){
			name = methods[i].getName();
			if (name.startsWith("set")) {
				name = name.substring(3);
				if(!name.equals("EntityCode") && !name.equals("DownTimestamp") && !name.equals("IsValid")
		    			&& !name.equals("ErrorMsg")){ //剔除BASEVO中的数据
					this.mthName.addLast(name);
				}
			}
		}	
	}
	
	private Object getColVal(Serializable bean, String name) throws Exception {
		Method mth = this.cls.getMethod("get" + name,
				new Class[0]);
		return mth.invoke(bean, new Object[0]);
	}

	public Map<String, String> getClassMapVal(Serializable bean) throws Exception{
		String name = "";
		Object obj = null;
		Map<String, String> map = new HashMap<String, String>();  
		for(int k = 0;k<mthName.size();k++){
    		name = mthName.get(k);
	    	obj = getColVal(bean, name);
	    	map.put(Character.toLowerCase(name.charAt(0))+name.substring(1), String.valueOf(obj));
	    }
		return map;
	}
}
