/*
* Copyright 2016 Yonyou Corporation Ltd. All Rights Reserved.
*
* This software is published under the terms of the Yonyou Software
* License version 1.0, a copy of which has been included with this
* distribution in the LICENSE.txt file.
*
* @Project Name : cmol.common.function
*
* @File name : HttpUtils.java
*
* @Author : zhangxianchao
*
* @Date : 2016年2月23日
*
----------------------------------------------------------------------------------
*     Date       Who       Version     Comments
* 1. 2016年2月23日    zhangxianchao    1.0
*
*
*
*
----------------------------------------------------------------------------------
*/

package com.yonyou.dms.function.utils.common;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.Enumeration;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.yonyou.dms.function.common.CommonConstants;
import com.yonyou.dms.function.exception.UtilException;

import net.sourceforge.pinyin4j.PinyinHelper;

/*
*
* @author zhangxianchao
* CommonUtils
* @date 2016年2月23日
*/

public class CommonUtils {

    private static final String STR_NEW_LINE                   = "\n\r";
    private static final String STR_SEPARATED_BLANK            = StringUtils.BLANK_SPRING_STRING;
    // 定义日志接口
 	private static final Logger logger = LoggerFactory.getLogger(CommonUtils.class);

    /**
     * check if the list is null or empty
     */
    public static boolean isNullOrEmpty(Collection<?> list) {
        return list == null || list.isEmpty();
    }

    /**
     * check if the map is null or empty
     */
    public static boolean isNullOrEmpty(Map<?, ?> map) {
        return map == null || map.isEmpty();
    }

	/** Checks to see if the passed string is null, if it is returns an empty but non-null string.
	 * @param string1 The passed String
	 * @return The passed String if not null, otherwise an empty non-null String
	 */
	public static String checkNull(Object obj) {
		if (obj != null)
			return obj.toString().trim();
		else
			return "";
	}

	/** Checks to see if the passed string is null, if it is returns an empty but non-null string.
	 * @param string1 The passed String
	 * @return The passed String if not null, otherwise an empty non-null String
	 */
	public static Integer checkNullInt(Object obj,Integer defaultValue) {
		if (obj != null)
			try {
				return Integer.parseInt(obj.toString().trim());
			} catch (Exception e) {
				return defaultValue;
			}
		else
			return defaultValue;
	}
    

    /*
     * @author ChenPeiYu 根据汉字获取拼音首字母
     * @date 2016年3月9日
     * @param str
     * @return
     */
    public static String getHanyuPinyinString(String str) {
        StringBuffer convert = new StringBuffer(str.length() << 1);
        for (int j = 0; j < str.length(); j++) {
            char word = str.charAt(j);
            // 获取拼音首字母
            String[] pinyinArray = PinyinHelper.toHanyuPinyinStringArray(word);
            if (pinyinArray != null) {
                convert.append(pinyinArray[0].charAt(0));
            } else {
                convert.append(word);
            }
        }
        return convert.toString().toUpperCase();
    }

    /*
     * @author ChenPeiYu 获取5位订单号
     * @date 2016年3月10日
     * @param number
     * @return
     */
    public static String getFourOrderNo(int number) {
        String orderNo = String.valueOf(number);
        if (orderNo.length() <= CommonConstants.SYSTEM_ORDER_NO_NUMBER) {
            while (orderNo.length() < CommonConstants.SYSTEM_ORDER_NO_NUMBER) {
                orderNo = "0" + orderNo;
            }
            return orderNo;
        } else {
            return null;
        }
    }
    
    public static String getFourOrderNo(int number,int nonumber ) {
        String orderNo = String.valueOf(number);
        if (orderNo.length() <= nonumber) {
            while (orderNo.length() < nonumber) {
                orderNo = "0" + orderNo;
            }
            return orderNo;
        } else {
            return null;
        }
    }

    /*
     * @author LiuJun 产生紧凑型32位UUID-目前用于SAF taskId
     * @date 2016年3月28日
     * @return
     */
    public static String getUUID() {
        UUID uuid = UUID.randomUUID();
        return uuid.toString().replaceAll("-", "");
    }
    
    /**
     * parse request and get data var html
     */
    public static String parseHttpServletRequest(HttpServletRequest request) {
        StringBuilder sb = new StringBuilder();
        sb.append("date " + new Date() + "-----------------");
        sb.append(STR_NEW_LINE);
        sb.append("request " + request.getPathInfo() + "-----------------");
        sb.append(STR_NEW_LINE);
        sb.append("request method " + request.getMethod() + "-----------------");
        sb.append(STR_NEW_LINE);
        sb.append("request parameter" + "-----------------");
        sb.append(STR_NEW_LINE);
        Enumeration<String> names = request.getParameterNames();
        while (names.hasMoreElements()) {
            String name = names.nextElement();
            String data = request.getParameter(name);
            sb.append("key " + name);
            sb.append(STR_SEPARATED_BLANK);
            sb.append("value " + data);
            sb.append(STR_NEW_LINE);
        }
        sb.append("header parameter" + "-----------------");
        sb.append(STR_NEW_LINE);
        Enumeration<String> headers = request.getHeaderNames();
        while (headers.hasMoreElements()) {
            String name = headers.nextElement();
            String data = request.getHeader(name);
            sb.append("key " + name);
            sb.append(STR_SEPARATED_BLANK);
            sb.append("value " + data);
            sb.append(STR_NEW_LINE);
        }
        sb.append("header parameter" + "-----------------");
        sb.append(STR_NEW_LINE);
        Cookie[] cookies = request.getCookies();
        if (cookies != null && cookies.length != 0) {
            for (Cookie cookie : cookies) {
                sb.append("key " + cookie.getName());
                sb.append(STR_SEPARATED_BLANK);
                sb.append("value " + cookie.getValue());
                sb.append(STR_NEW_LINE);
            }
        }
        return sb.toString();
    }

    

    /**
     * get local host
     */
    public static InetAddress getLocalHost() throws UtilException {
        try {
            return InetAddress.getLocalHost();
        } catch (UnknownHostException e) {
            logger.error(e.getMessage(),e);
            throw new UtilException("get local ip error", e);
        }
    }

    /**
     * get local host
     */
    public static InetAddress[] getAllLocalHosts() throws UtilException {
        String hostName = CommonUtils.getLocalHostName();
        if (StringUtils.isNullOrEmpty(hostName)) {
            throw new UtilException("cannot get local host");
        }
        try {
            return InetAddress.getAllByName(hostName);
        } catch (UnknownHostException e) {
            throw new UtilException(e);
        }

    }

    /**
     * get local host name
     */
    public static String getLocalHostName() throws UtilException {
        InetAddress address = getLocalHost();
        return address.getHostName();
    }
    /**
    * List<Map> 转换字符串 （[{DEALER_CODE=2100000, ROLE=10060001}, {DEALER_CODE=2100000, ROLE=10060002}, {DEALER_CODE=2100000, ROLE=10060003}]）
    * Map中有两列   DEALER_CODE和 ROLE   默认拼接第二个（ROLE）  的value值            
    * @author jcsi
    * @date 2016年7月12日
    * @param list   转换的list
    * @param separator  分隔符
    * @return
     */
    public static String listMapToString(List list, String separator) {   
        StringBuilder sb = new StringBuilder();
        String str="";
        if(list.size()>0){
            for (int i = 0; i < list.size(); i++) {
                Map<String, Object> map=(Map)list.get(i);   
                for (Map.Entry<String, Object> entry : map.entrySet()) {  
                    str=entry.getValue()+"";
                }              
                sb.append(str).append(separator);   
             } 
            return sb.toString().substring(0,sb.toString().length()-1);
        }else{
            return "";
        }
          
        
    }
    
    /**
    * 普通List转换字符串
    * @author jcsi
    * @date 2016年7月12日
    * @param list
    * @param separator
    * @return
     */
    public static String listToString(List list, String separator) {   
        StringBuilder sb = new StringBuilder();    
        for (int i = 0; i < list.size(); i++) {        
            sb.append(list.get(i)).append(separator);    }   
        return sb.toString().substring(0,sb.toString().length()-1);
    }
    
    /**
     * DC
     * @return
     */
    public static Date currentDateTime() {
		return Calendar.getInstance().getTime();
	}
    
    /**
     * DC
     * @param date
     * @return
     */
    public static String printDate(Date date) {
		if(date!=null){
		   SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		   return df.format(date);
		}else{
		   return "";
		}
	}
    
    /**
	 * 检查空值，为NULL则返回给定的默认值
	 * @param obj
	 * @return
	 */
	public static String checkNull(Object obj,String defaultStr) {
		if (obj != null)
			return obj.toString();
		else
			return defaultStr;
	}
	
	/**
	 * 检查是不是为空
	 * @param source
	 * @return
	 */
	public static boolean checkIsNull(String source){
		if(source==null||source.trim().equals("")){
			return true;
		}else{
			return false;
		}
	}
	
	/**
	 * conversion A String number to date type value  DC
	 * @param s
	 * @return
	 */
	public static Date parseDate(String date) {
		DateFormat df = DateFormat.getDateInstance();
		try
		{
			return df.parse(date);
		}
		catch (ParseException e)
		{
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 *判断是否为合法的TimeStamp格式字符串
	 * @param s
	 * @return
	 */
	public static boolean isValidTimestamp(String s) {
		SimpleDateFormat df = null;
		try
		{
			df = new SimpleDateFormat("yyyy-MM-dd");
			df.parse(s);
			return true;
		}
		catch (Exception e)
		{
			return false;
		}
	}
	
	public static boolean checkIsNullStr(String nullStr) {
		boolean ret = false;
		if (nullStr != null && nullStr.length() > 0)
		{
			if (nullStr.equals("null") || "".equals(nullStr.trim()))
			{
				ret = true;
			}
		}
		else
		{
			ret = true;
		}
		return ret;
	}
	public static boolean isNullString(String s) {
		return null == s || s.equals("");
	}
	
	
	/**
	 * 判断字符串是否是数字
	 * @param s
	 * @return
	 */
	public static boolean isNumber(String s)
	{
		boolean ret =true;
		Pattern pattern = Pattern.compile("[0-9+./+-/]");
	    char[] ss = s.toCharArray();
	    for (int i=0;i<ss.length; i++)
	    {
	    	Matcher isNum = pattern.matcher(String.valueOf(ss[i]));
	    	if (!isNum.find())
	    	{
	    		ret = false;
	    		break;
	    	}
	    }
		return ret;
	}
	/** Checks to see if the passed string is a empty string <code>""</code> or a null string.
	 * @param s The passed String 
	 * @return if the passed String if not A <code>""</code> String then return true.
	 */
	public static boolean isEmpty(String str) {
		return str == null || str.trim().length() == 0;
	}
	/**
	 * @param str
	 * @return val=null or val = "" return true
	 * added by wangwenhu 2010-03-18
	 */
	public static boolean notNull(String str){
		return !(str== null || "".equals(str.trim()));
	}
}
