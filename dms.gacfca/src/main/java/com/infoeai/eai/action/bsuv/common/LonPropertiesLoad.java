package com.infoeai.eai.action.bsuv.common;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import com.yonyou.dms.common.Util.DomainUtil;


public class LonPropertiesLoad {

	private static LonPropertiesLoad util=null;
	
	private static Properties props=null;
	
    private static String PROP_LOCATION="lon.properties";
	
	public static final LonPropertiesLoad getInstance(){
		try {
			if(util==null){
				loadFile(PROP_LOCATION);
				return new LonPropertiesLoad();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return util;
	}
	private static void loadFile(String path) throws IOException{
		props = new Properties();
		InputStream input=DomainUtil.class.getResourceAsStream(path);
		props.load(input);
	}
	public String getValue(String key){
		if(props==null&&props.isEmpty()) return "";
		return props.getProperty(key);
	}
}
