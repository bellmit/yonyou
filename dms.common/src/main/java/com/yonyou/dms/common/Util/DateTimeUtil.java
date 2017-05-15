package com.yonyou.dms.common.Util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

import com.yonyou.dms.function.utils.common.CommonUtils;

public class DateTimeUtil {
	private static final String DATE_PATTERN = "yyyy-MM-dd";
	
//	/**
//	 * 通过年，月，获取所有周的列表
//	 * add by lsy
//	 * @param dcsUploadDate
//	 * @return
//	 * @throws ParseException
//	 */
//	public static list getCurrentWeekByDcsUpload(String year,String month) throws ParseException{
//		Calendar cal =Calendar.getInstance();
//        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
//        cal.setTime(df.parse(dcsUploadDate));
//        cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY); //获取本周一的日期
//        //这种输出的是上个星期周日的日期，因为老外那边把周日当成第一天
//        cal.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
//		//增加一个星期，才是我们中国人理解的本周日的日期
//		cal.add(Calendar.WEEK_OF_YEAR, 1);
//		return cal.getFirstDayOfWeek();
//	}
	
	/**
	 * 当前时间获取当前周
	 * add by lsy
	 * @param dcsUploadDate
	 * @return
	 * @throws Exception
	 */
	public static int getCurrentWeekByDcsUpload(String dcsUploadDate) throws ParseException{
		Calendar cal =Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        cal.setTime(df.parse(dcsUploadDate));
        cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY); //获取本周一的日期
        //这种输出的是上个星期周日的日期，因为老外那边把周日当成第一天
        cal.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
		//增加一个星期，才是我们中国人理解的本周日的日期
		cal.add(Calendar.WEEK_OF_YEAR, 1);
		return cal.getFirstDayOfWeek();
	}
		
	public static Date stringToDate(String time) throws ParseException{
		Date ti = null;
		if(time==null||time.equals("")){
			return null;
		}
		SimpleDateFormat formate = new SimpleDateFormat(DATE_PATTERN);
		ti = new  java.sql.Date(formate.parse(time).getTime());
		return ti;
	}
	
	public static Date stringToDateByPattern(String time, String datePattern) throws ParseException{
		if(time==null||time.equals("")){
			return null;
		}
		SimpleDateFormat formate = new SimpleDateFormat(datePattern);
		return formate.parse(time);
	}
	
	/**
	 * 将日期格式化成字符串
	 * add by zhangxianchao
	 * @param time
	 * @return
	 * @throws ParseException
	 */
	public static String parseDateToString(Date date) throws ParseException{
		if(date==null){
			return null;
		}
		SimpleDateFormat formate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return formate.format(date);
	}
	
	/**
	 * 将日期格式化成字符串
	 * add by zhangxianchao
	 * @param time
	 * @return
	 * @throws ParseException
	 */
	public static String parseDateToDate(Date date) throws ParseException{
		if(date==null){
			return null;
		}
		SimpleDateFormat formate = new SimpleDateFormat("yyyy-MM-dd");
		return formate.format(date);
	}
	/**
	 * 将日期格式化成字符串
	 * add by zhaolunda
	 * @param time
	 * @return
	 * @throws ParseException
	 */
	public static String parseDateToDateYYYYMM(Date date) throws ParseException{
		if(date==null){
			return null;
		}
		SimpleDateFormat formate = new SimpleDateFormat("yyyy-MM");
		return formate.format(date);
	}
	/**
	 * 将日期格式化成字符串
	 * add by wujinbiao
	 * @param time
	 * @return
	 * @throws ParseException
	 */
	public static String parseDateToDateYYYYMMDDHHMMSS(Date date) throws ParseException{
		if(date==null){
			return null;
		}
		SimpleDateFormat formate = new SimpleDateFormat("yyyyMMDDhhmmss");
		return formate.format(date);
	}
	/**
	 * 功能：
	 * ZLD
	 * 2011-10-28
	 */
	public static String parseDateToYYMMDD(Date date) throws ParseException{
		if(date==null){
			return null;
		}
		SimpleDateFormat formate = new SimpleDateFormat("yy-MM");
		return formate.format(date);
	}
	/**
	 * 将毫秒转化成日期的字符串表示
	 * add by zhangxianchao
	 * @param time
	 * @return
	 */
	public static String getDateTimeFormat(long time){
		SimpleDateFormat formate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date = new Date(time);
		String dateStr = formate.format(date);
		return dateStr;
	}
	/**
	 * 将字符串修改长date
	 * add by wujinbiao
	 * @param time
	 * @return
	 */
	public static Date getStringFormat(String time) throws ParseException {
		SimpleDateFormat formate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date dateStr = formate.parse(time);
		return dateStr;
	}
	/**
	 * 将毫秒转化成日期
	 * add by zhangxianchao
	 * @param time
	 * @return
	 */
	public static Date getDateByTime(long time){
		Date date = new Date(time);
		return date;
	}
	/**
	 * 获取当前年份
	 * add by zhilinshu
	 * @param time
	 * @return
	 */
	public static int getNowYear(Date date){
		SimpleDateFormat formate = new SimpleDateFormat("yyyy");
		return Integer.valueOf(formate.format(date));
	}
	/**
	 * 获取当前月份
	 * add by wujinbiao
	 */
	public static String getNowMonth(Date date){
		SimpleDateFormat formate = new SimpleDateFormat("yyyy-MM");
		return formate.format(date).substring(5,7);
	}
	/**
	 * 获取当前天
	 * add by wujinbiao
	 */
	public static String getNowDay(Date date){
		SimpleDateFormat formate = new SimpleDateFormat("yyyy-MM-dd");
		return formate.format(date).substring(8,10);
	}
	/**
	 * 获得日期间隔天数
	 * add by zhaold
	 * @param stratTime
	 * @param endTime
	 * @return
	 * @throws ParseException
	 */
	public static double dateBetween(String startTime,String endTime) throws ParseException{
		if((startTime==null||"".equals(startTime))||(endTime==null||"".equals(endTime))){
			return 0d;
		}
		SimpleDateFormat df   =new SimpleDateFormat("yyyy-MM-dd");
		Date begin=df.parse(startTime);
		
	    Date end=df.parse(endTime);   
	    double between=(end.getTime()-begin.getTime())/1000;//除以1000是为了转换成秒   
	    double day=between/(24*3600);
	    
	    return day;
	}
	/**
	 * 日期累加获得新的日期
	 * @param date
	 * @param addDays
	 * @return
	 * @throws ParseException
	 */
	public static String increaseDays(String date,long addDays) throws ParseException{
		if("".equals(CommonUtils.checkIsNullStr(date))||"".equals(CommonUtils.checkNull(addDays))){
			return "";
		}
		SimpleDateFormat df   =new SimpleDateFormat("yyyy-MM-dd");
		Date _date=df.parse(date);
		long time = _date.getTime(); 
		addDays = addDays*24*60*60*1000;
		time+=addDays;
		String retDate=df.format(new Date(time));
		return retDate;
	}
	
	/**
	 * 解决格林威治时间差8个小时问题
	 * @param date   时间
	 * @param format 格式
	 * @return 
	 * @throws 
	 */
	 public static String getString(Date date, String format) { 
         if (null  == date)  return ""; 
        SimpleDateFormat fmt = new SimpleDateFormat(format); 
         fmt.setTimeZone(TimeZone.getTimeZone("Asia/Shanghai")); //设置时区 
        return fmt.format(date); // 按自定义格式，格式化当前时间。 
} 

}
