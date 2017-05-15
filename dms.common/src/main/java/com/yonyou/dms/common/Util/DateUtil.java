/**********************************************************************
 * <pre>
 * FILE : DateUtil.java
 * CLASS : DateUtil
 *
 * AUTHOR : SuMMeR
 *
 * FUNCTION : TODO
 *
 *
 *======================================================================
 * CHANGE HISTORY LOG
 *----------------------------------------------------------------------
 * MOD. NO.| DATE | NAME | REASON | CHANGE REQ.
 *----------------------------------------------------------------------
 * 		  |2009-9-3| SuMMeR| Created |
 * DESCRIPTION:
 * </pre>
 ***********************************************************************/
/**
 * $Id: DateUtil.java,v 1.23 2016/06/14 02:28:18 xurf Exp $
 */

package com.yonyou.dms.common.Util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * 处理时间转换及格式工具类
 */
public class DateUtil {
	
	@SuppressWarnings("static-access")
	public static Date dateAddAndSubtract(Date date, int value) {
		
		try {
			Calendar calendar = new GregorianCalendar();
			calendar.setTime(date);
			calendar.add(calendar.DATE, value);
			return calendar.getTime();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return date;
	}
	
	/**
	 * 将10位(dd/MM/yyyy)日月年转换为Date类型
	 * @param ddMMyyyy
	 * @param token
	 * @return
	 */
	public static Date ddMMyyyy2Date(String ddMMyyyy, String token) {
		
		if (null == ddMMyyyy ||
			ddMMyyyy.trim().equals("") || 
			ddMMyyyy.toLowerCase().trim().equals("null") ||
			ddMMyyyy.length() != 10) {
			return null;
		}
		
		String[] array = ddMMyyyy.split(token);
		String dd = array[0];
		String MM = array[1];
		String yyyy = array[2];
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		Date date = null;
		try {
			date = sdf.parse(yyyy + MM + dd);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return date;
	}

	/**
	 * 将8位数字年月日转换为Date类型
	 * @param yyyyMMdd
	 * @return
	 */
	public static Date yyyyMMdd2Date(String yyyyMMdd) {
		
		if (null == yyyyMMdd ||
			yyyyMMdd.trim().equals("") || 
			yyyyMMdd.toLowerCase().trim().equals("null")) {
			return null;
		}
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		Date date = null;
		try {
			date = sdf.parse(yyyyMMdd);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return date;
	}
	
	/**
	 * 将10位数字年月日转换为Date类型
	 * @param yyyyMMdd
	 * @return
	 */
	public static Date yyyy_MM_dd2Date(String yyyyMMdd) {
		
		if (null == yyyyMMdd ||
			yyyyMMdd.trim().equals("") || 
			yyyyMMdd.toLowerCase().trim().equals("null")||
			"0000-00-00".equals(yyyyMMdd)) {
			return null;
		}
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date date = null;
		try {
			date = sdf.parse(yyyyMMdd);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return date;
	}
	
	/**
	 * Date 转换成字符串
	 * @param yyyyMMdd
	 * @return
	 */
	public static String yyyy_MM_dd2Str(Date Date) {
		
		if (null == Date) {
			return null;
		}
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		try {
			return sdf.format(Date);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * Date 转换成字符串
	 * @param yyyyMMdd
	 * @return
	 */
	public static String yyyyMMdd2Str(Date Date) {
		
		if (null == Date) {
			return null;
		}
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		try {
			return sdf.format(Date);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 当前的日期减去一天
	 */
	public static String getbeforeDate(Date date) {
		SimpleDateFormat sdf=new SimpleDateFormat("yyyyMMdd");
        Calendar rightNow = Calendar.getInstance();
        rightNow.setTime(date);
        rightNow.add(Calendar.DAY_OF_MONTH, -1);// 前一天
        Date dt1=rightNow.getTime();
        String reStr = sdf.format(dt1);
        return reStr;
	}
	
	/**
	 * 校验字符串是否可以转换为日期格式
	 * @param yyyyMMdd
	 * @return
	 */
	public static boolean yyyyMMdd2DateCheck(String yyyyMMdd) {
		
		boolean result = false;
		
		if (null == yyyyMMdd ||
			yyyyMMdd.trim().equals("") || 
			yyyyMMdd.toLowerCase().trim().equals("null")) {
			result = true;
		}
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		try {
			sdf.setLenient(false);	// false 严格校验
			sdf.parse(yyyyMMdd);
			result = false;
		} catch (Exception e) {
			result = true;
		}
		return result;
	}
	
	/**
	 * 将8位数字年月日和6位数字时分秒转换为Date类型
	 * @param yyyyMMddHHmmss
	 * @return
	 */
	public static Date yyyyMMddHHmmss2Date(String yyyyMMddHHmmss) {
		
		if (null == yyyyMMddHHmmss || 
			yyyyMMddHHmmss.trim().equals("") || 
			yyyyMMddHHmmss.toLowerCase().trim().equals("null")) {
			return null;
		}
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
		Date date = null;
		try {
			date = sdf.parse(yyyyMMddHHmmss);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return date;
	}
	/**
	 * 将10位数字年月日和8位数字时分秒转换为Date类型
	 * @param yyyyMMddHHmmss
	 * @return
	 */
	public static Date yyyy_MM_dd_HHmmss2Date(String yyyyMMddHHmmss) {
		
		if (null == yyyyMMddHHmmss || 
			yyyyMMddHHmmss.trim().equals("") || 
			yyyyMMddHHmmss.toLowerCase().trim().equals("null")||
			"0000-00-00 00:00:00".equals(yyyyMMddHHmmss)) {
			return null;
		}
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date = null;
		try {
			date = sdf.parse(yyyyMMddHHmmss);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return date;
	}
	
	/**
	 * 将日期类型转换为字符串类型
	 * @param date
	 * @param dateType
	 * @param num
	 * @param format
	 * @return
	 */
	public static String date2Str(Date date, String dateType, int num, String format) {
		
		dateType = null == dateType ? "" : dateType;
		
		Calendar calendar = Calendar.getInstance();	// 得到日历
		calendar.setTime(date);	// 把当前时间赋给日历
		
		if (dateType.toUpperCase().equals("DAY")) {
			// 设置N天前或N天后
			calendar.add(Calendar.DATE, num);
			date = calendar.getTime();
		} else if (dateType.toUpperCase().equals("MONTH")) {
			// 设置N月前或N月后
			calendar.add(Calendar.MONTH, num);
			date = calendar.getTime();
		} else if (dateType.toUpperCase().equals("YEAR")) {
			// 设置N年前或N年后
			calendar.add(Calendar.YEAR, num);
			date = calendar.getTime();
		}
		
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		String str = sdf.format(date);
		
		return str;
	}

	/**
	 * 字符格式时间转换为Date 暂时只支持yyyy-MM-dd和yyyy/MM/dd格式
	 * 
	 * @param date
	 *            字符串格式的时间,如yyyy-MM-dd和yyyy/MM/dd
	 * @param token
	 *            时间分割符,如"/","-"
	 * @return
	 */
	public static Date str2Date(String date, String token) {
		Calendar cal = str2Cal(date, token);
		return cal.getTime();
	}

	/**
	 * 字符格式时间转换为Calendar 暂时只支持yyyy-MM-dd和yyyy/MM/dd格式
	 * 
	 * @param date
	 *            字符串格式的时间,如yyyy-MM-dd和yyyy/MM/dd
	 * @param token
	 *            时间分割符,如"/","-"
	 * @return
	 */
	public static Calendar str2Cal(String date, String token) {
		String[] dateArray = date.split(token);
		int year = Integer.valueOf(dateArray[0]).intValue();
		int month = Integer.valueOf(dateArray[1]).intValue();
		int day = Integer.valueOf(dateArray[2]).intValue();
		Calendar cal = Calendar.getInstance();
		cal.set(year, month, day);
		return cal;
	}

	/**
	 * 根据Date对象获取改对象的年份
	 * 
	 * @param date
	 * @return
	 */
	public static String getYearByDate(Date date) {
		return getPartByDate(date, 0);
	}

	/**
	 * 根据Date对象获取改对象的月份
	 * 
	 * @param date
	 * @return
	 */
	public static String getMonthByDate(Date date) {
		return getPartByDate(date, 1);
	}

	/**
	 * 根据Date对象获取改对象的月日期(当月第几号)
	 * 
	 * @param date
	 * @return
	 */
	public static String getDayByDate(Date date) {
		return getPartByDate(date, 2);
	}

	/**
	 * 根据Date对象获取改对象的年,月,日部分
	 * 
	 * @param date
	 * @param pos
	 * @return
	 */
	@SuppressWarnings("finally")
	public static String getPartByDate(Date date, int pos) {
		String part = "";
		try {
			String dateStr = DateTimeUtil.parseDateToDate(date);
			part = dateStr.split("-")[pos];
		} catch (ParseException e) {
			e.printStackTrace();
		} finally {
			return part;
		}
	}

	/**
	 * 取得指定月份的第一天
	 * 
	 * @param strdate
	 *            String
	 * @return String
	 */
	public static String getMonthBegin(Date date) {
		return new SimpleDateFormat("yyyy-MM").format(date) + "-01";
	}

	/**
	 * 取得指定月份的最后一天
	 * 
	 * @param strdate
	 *            String
	 * @return String
	 */
	public static String getMonthEnd(Date d) {
		Calendar calendar = Calendar.getInstance();
		try {
			Date date = new SimpleDateFormat("yyyy-MM-dd")
					.parse(getMonthBegin(d));
			calendar.setTime(date);
			calendar.add(Calendar.MONTH, 1);
			calendar.add(Calendar.DAY_OF_YEAR, -1);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return new SimpleDateFormat("yyyy-MM-dd").format(calendar.getTime());
	}

	public static Date dateAdd(int day) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(new Date());
		calendar.set(Calendar.DAY_OF_MONTH, calendar.get(Calendar.DAY_OF_MONTH)
				+ day);
		return calendar.getTime();
	}
	public static Date dateAddByDate(Date date,int day) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(Calendar.DAY_OF_MONTH, calendar.get(Calendar.DAY_OF_MONTH)
				+ day);
		return calendar.getTime();
	}
	public static Date monthAdd(int month) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(new Date());
		calendar.set(Calendar.MONTH, calendar.get(Calendar.MONTH)
				+ month);
		return calendar.getTime();
	}
	public static Date monthAddByDate(Date date,int month) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(Calendar.MONTH, calendar.get(Calendar.MONTH)
				+ month);
		return calendar.getTime();
	}
	public static String getWeek(Date day) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(day);
		String week = "";
		switch (calendar.get(Calendar.DAY_OF_WEEK)) {
		case 1:
			week = "星期日";
			break;
		case 2:
			week = "星期一";
			break;
		case 3:
			week = "星期二";
			break;
		case 4:
			week = "星期三";
			break;
		case 5:
			week = "星期四";
			break;
		case 6:
			week = "星期五";
			break;
		case 7:
			week = "星期六";
			break;
		}
		return week;
	}
	
/**   
 * 计算两个日期之间相差的天数   
 * @param smdate 较小的时间  
 * @param bdate  较大的时间  
 * @return 相差天数  
 * @throws ParseException   
 */     
public static int daysBetweenDate(Date smdate,Date bdate) throws ParseException     
{     
    SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");   
    smdate=sdf.parse(sdf.format(smdate));   
    bdate=sdf.parse(sdf.format(bdate));   
    Calendar cal = Calendar.getInstance();     
    cal.setTime(smdate);     
    long time1 = cal.getTimeInMillis();                  
    cal.setTime(bdate);     
    long time2 = cal.getTimeInMillis();          
    long between_days=(time2-time1)/(1000*3600*24);   
         
   return Integer.parseInt(String.valueOf(between_days));            
}     
       
/**  
*字符串的日期格式的计算  
*/  
public static int daysBetweenStr(String smdate,String bdate) throws ParseException{   
    SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");   
    Calendar cal = Calendar.getInstance();     
    cal.setTime(sdf.parse(smdate));     
    long time1 = cal.getTimeInMillis();                  
    cal.setTime(sdf.parse(bdate));     
    long time2 = cal.getTimeInMillis();          
    long between_days=(time2-time1)/(1000*3600*24);   
         
   return Integer.parseInt(String.valueOf(between_days));      
}   

}
