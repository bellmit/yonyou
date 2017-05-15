package com.yonyou.dms.common.Util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;


public class DomainUtil {

	private static DomainUtil util=null;
	
	private static Properties props=null;
	
	private static String PROP_LOCATION="/FileStore.properties";
	
	public static final DomainUtil getInstance(){
		try {
			if(util==null){
				loadFile(PROP_LOCATION);
				return new DomainUtil();
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
	
	public String getDomainValue(String key){
		if(props==null&&props.isEmpty()) return "";
		return props.getProperty(key);
	}
	
	public static void main(String[] a){
		//System.out.println(DomainUtil.getInstance().getDomainValue("outnet"));
		//System.out.println(DomainUtil.getInstance().getDomainValue("innet"));
	}
}
