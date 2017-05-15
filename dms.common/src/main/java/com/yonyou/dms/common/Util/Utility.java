package com.yonyou.dms.common.Util;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * @ClassName: Utility 
 * @Description: 系统工具类
 * @author zhengzengliang 
 * @date 2017年4月13日 下午5:32:20 
 *
 */
@SuppressWarnings({"null","unused","deprecation"})
public class Utility {


	//	static POFactory poFactory = POFactoryBuilder.getInstance();

	/**
	 * 描述： int为空
	 */
	public static final int NULL_INT = Integer.MIN_VALUE;

	/**
	 * 描述： double为空
	 */
	public static final double NULL_DOUBLE = Double.MIN_VALUE;

	/**
	 * 描述： sql语句开关 １为db２，０为oracle
	 */
	public static final int DB_TYPE = 1;


	/**
	 * 描述： 默认除法运算精度 主要用于浮点型类型换算中使用
	 */
	private static final int DEF_DIV_SCALE = 2;

	private static DecimalFormat nf;

	private static SimpleDateFormat sdf = new SimpleDateFormat(
			"yyyy-MM-dd HH:mm:ss");

	public static final String FULL_DATE_FORMAT = "yyyy-MM-dd";

	private static SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd");

	public static final String STANDARD_DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";

	/**
	 * 这个工具类不能实例化
	 * 
	 */
	private Utility() {

	}

	/**
	 * string转换成float
	 * 
	 * @param index
	 * @return
	 * @throws Exception
	 */
	public static float getFloat(String index) throws Exception {
		float iRtn;
		if (index == null ||("null").equals(index) || index.trim().equals("")) {
			iRtn = new Float(0);
			return iRtn;
		}
		return Float.parseFloat(index);
	}

	/**
	 * string转换成long
	 * 
	 * @param index
	 * @return
	 * @throws Exception
	 */
	public static long getLong(String index) throws Exception {
		long lRtn;
		if (index == null || index.trim().equals("")) {
			lRtn = new Long(0);
			return lRtn;
		}
		return Long.parseLong(index);
	}

	/**
	 * 判断是否为空或NULL
	 * 
	 * @param index
	 * @return
	 * @throws Exception
	 */
	public static boolean testString(Object src)
	{
		if (src != null && !"".equals(src.toString().trim()))
			return true;
		return false;
	}
	public static boolean testString(String src) {
		if (src != null && !"".equals(src.trim()) && !"null".equalsIgnoreCase(src.trim()))
			return true;
		return false;
	}

	public static boolean testStringIsNull(String src) {
		if (src == null && "".equals(src.trim()))
			return true;
		return false;
	}

	public static boolean testIsNotNull(String src) {
		if (null != src && !"".equals(src) && !"null".equals(src))
			return true;
		return false;
	}

	public static boolean testIsNull(String src) {
		if (null == src || "".equals(src) || "null".equals(src))
			return true;
		return false;
	}

	/**
	 * 判断字符串是否为空, 如果为空返回空字符串, 否则原值返回
	 * @param src
	 * @return
	 */
	public static String stringIsNull(String src) {
		String outString = src;
		if (null == src || "null".equals(src.trim())) {
			outString = "";
		}
		return outString;
	}

	/**
	 * string转换成int
	 * 
	 * @param index
	 * @return
	 * @throws Exception
	 */
	public static int getInt(String index) throws Exception {
		int iRtn;
		if (index == null || index.trim().equals("") || "null".equalsIgnoreCase(index)) {
			iRtn = new Integer(0);
			return iRtn;
		}
		return Integer.parseInt(index);
	}

	/**
	 * 解析数组，如果数组为空或长度小于参数i，则返回null，否则以i作为该数组的下标返回该数组元素
	 * 
	 * @param array
	 * @param i
	 * @return
	 */
	public static String parseArray(String[] array, int i) {
		if (array == null || array.length == 0 || array.length <= i) {
			return null;
		} else if (array[i] == null || array[i].equals("")) {
			return null;
		} else {
			return array[i];
		}
	}

	/**
	 * string转换成double
	 * 
	 * @param index
	 * @return
	 * @throws Exception
	 */
	public static double getDouble(String index) throws Exception {
		double dRtn;
		if (index == null || index.trim().equals("") || "null".equalsIgnoreCase(index)) {
			dRtn = new Double(0);
			return dRtn;
		}
		return Double.parseDouble(index);
	}
	/**
	 * string转换成BigDecimal
	 * 
	 * @param index
	 * @return
	 * @throws Exception
	 */
	public static BigDecimal getBigDecimal(String index) throws Exception {
		BigDecimal dRtn;
		if (index == null || index.trim().equals("") || "null".equalsIgnoreCase(index)) {
			dRtn = new BigDecimal(Double.toString(0));
			return dRtn;
		}
		return  new BigDecimal(index);
	}

	/**
	 * null转换成double
	 * 
	 * @param index
	 * @return
	 * @throws Exception
	 */
	public static Double changeNullToDouble(Double index) throws Exception {
		double dRtn;
		if (index == null || index.equals("")) {
			dRtn = new Double(0.0);
			return dRtn;
		}
		return index;
	}

	/**
	 * string转换成Date
	 * 
	 * @param index , i
	 * @return
	 * @throws Exception
	 */
	public static Date getDate(String index, int i) throws Exception {
		DateFormat formatter;
		Date d = new Date();
		if (index == null || index.trim().equals("")) {
			return null;
		}
		if (i == 0) // for filename
		{
			formatter = new SimpleDateFormat("yyyy-MM-dd-HH");
		} else if (i == 1) {
			formatter = new SimpleDateFormat("yyyy-MM-dd");
		} else if (i == 3) {
			formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		} else if (i == 4) {
			formatter = new SimpleDateFormat("HH:mm");
		} else if (i == 5) {
			formatter = new SimpleDateFormat("HH:mm:ss");
		} else if (i == 6) {
			formatter = new SimpleDateFormat("yyyy");
		} else if (i == 7) {
			formatter = new SimpleDateFormat("MM");
		} else if (i == 8) {
			formatter = new SimpleDateFormat("dd");
		} else if (i == 9) {
			formatter = new SimpleDateFormat("yyyy_MM_dd_hh_mm_ss");
		} else if (i == 10) {
			formatter = new SimpleDateFormat("yyyyMMdd");
		}else if (i == 11) {
			formatter = new SimpleDateFormat("yyyy-MM");
		} else if(i == 12){
			formatter = new SimpleDateFormat("yyyy-MM-DD HH:MM:SS");
		}else if(i == 13){
			formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		}
		else {
			formatter = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");
		}
		d = formatter.parse(index);
		return d;
	}

	/**
	 * string转换成timestamp
	 * 
	 * @param index
	 * @return
	 * @throws Exception
	 */
	public static Timestamp getTimeStamp(String index) throws Exception {
		if (index == null || index.trim().equals("")) {
			return null;
		} else if (index.length() == 10) {
			index += " 00:00:00.000000000";
		} else if (index.length() == 19) {
			index += ".000000000";
		}
		return Timestamp.valueOf(index);
	}

	/**
	 * @param i
	 * @return String
	 * @author  richard
	 */
	public static  String getCurrentTime(int i) {
		SimpleDateFormat formatter;
		Date currentTime_1 = new java.util.Date();
		if (i == 0) //for filename
		{
			formatter = new SimpleDateFormat("yyyy-MM-dd-HH");
		} else if (i == 1) {
			formatter = new SimpleDateFormat("yyyy-MM-dd");
		} else if (i == 3) {
			formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		} else if (i == 4) {
			formatter = new SimpleDateFormat("HH:mm");
		} else if (i == 5) {
			formatter = new SimpleDateFormat("HH:mm:ss");
		} else if (i == 6) {
			formatter = new SimpleDateFormat("yyyy");
		} else if (i == 7) {
			formatter = new SimpleDateFormat("MM");
		} else if (i == 8) {
			formatter = new SimpleDateFormat("dd");
		} else if (i == 9) {
			formatter = new SimpleDateFormat("yyyy_MM_dd_hh_mm_ss");
		} else if (i == 10) {
			formatter = new SimpleDateFormat("yyyyMMdd");
		} else if (i == 11) {
			formatter = new SimpleDateFormat("yyyyMMddHHmmss");
		} else if (i == 12) {
			formatter = new SimpleDateFormat("yyMMdd");
		} else if (i == 13) {
			formatter = new SimpleDateFormat("yyMMddHHmmss");
		} else {
			formatter = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");
		}

		String dateString = formatter.format(currentTime_1);
		return dateString;
	}

	/**
	 * 根据长度补空格
	 * 
	 * @param src
	 * @param len
	 * @return
	 */
	public static String fullSpaceBuffer2(String src, int len) {
		StringBuffer result = new StringBuffer();
		if (src == null) {
			src = " ";
		}
		result.append(src);
		try {
			if (src != null && src.length() >= 0) {
				byte[] bb = src.getBytes("UTF8");
				for (int i = len; i > bb.length; i--) {
					result.append(" ");
				}
			}
		} catch (Exception e) {
			return result.toString();
		}
		return result.toString();
	}

	/**
	 * 根据长度补“0”
	 * @param src
	 * @param len
	 * @return
	 */
	public static String fullSpaceBuffer3(String src, int len) {
		StringBuffer result = new StringBuffer();
		if (src == null) {
			src = "0";
		}
		try {
			if (src != null && src.length() >= 0) {
				byte[] bb = src.getBytes("UTF8");
				for (int i = len; i > bb.length; i--) {
					result.append("0");
				}
			}
			result.append(src);
		} catch (Exception e) {
			return result.toString();
		}
		return result.toString();
	}

	/**
	 * 提供精确的加法运算。加数和被加数都要转换为String类型 String.valueOf()
	 * 
	 * @param v1
	 *            被加数
	 * @param v2
	 *            加数
	 * @return 两个参数的和
	 */
	public static double add(String v1, String v2) {
		BigDecimal b1 = new BigDecimal(v1);
		BigDecimal b2 = new BigDecimal(v2);
		return b1.add(b2).doubleValue();
	}

	/**
	 * 提供精确的减法运算。减数和被减数都要转换为String类型 String.valueOf()
	 * 
	 * @param v1
	 *            被减数
	 * @param v2
	 *            减数
	 * @return 两个参数的差
	 */
	public static double sub(String v1, String v2) {
		BigDecimal b1 = new BigDecimal(v1);
		BigDecimal b2 = new BigDecimal(v2);
		return b1.subtract(b2).doubleValue();
	}

	/**
	 * 提供精确的乘法运算。乘数和被乘数都要转换为String类型 String.valueOf()
	 * 
	 * @param v1
	 *            被乘数
	 * @param v2
	 *            乘数
	 * @return 两个参数的积
	 */
	public static double mul(String v1, String v2) {
		BigDecimal b1 = new BigDecimal(v1);
		BigDecimal b2 = new BigDecimal(v2);
		return b1.multiply(b2).doubleValue();
	}

	/**
	 * 提供（相对）精确的除法运算，当发生除不尽的情况时，精确到 小数点以后10位，以后的数字四舍五入。 除数和被除数都要转换为String类型
	 * 
	 * @param v1
	 *            被除数
	 * @param v2
	 *            除数
	 * @return 两个参数的商
	 */

	public static double div(String v1, String v2) {
		return div(v1, v2, DEF_DIV_SCALE);
	}

	/**
	 * 提供（相对）精确的除法运算。当发生除不尽的情况时，由scale参数指 定精度，以后的数字四舍五入。
	 * 
	 * @param v1
	 *            被除数
	 * @param v2
	 *            除数
	 * @param scale
	 *            表示表示需要精确到小数点以后几位。
	 * @return 两个参数的商
	 */
	public static double div(String v1, String v2, int scale) {
		if (scale < 0) {
			throw new IllegalArgumentException("��ȷС����λ����Ϊ�������0");
		}
		BigDecimal b1 = new BigDecimal(v1);
		BigDecimal b2 = new BigDecimal(v2);
		return b1.divide(b2, scale, BigDecimal.ROUND_HALF_UP).doubleValue();
	}

	/**
	 * 提供精确的小数位四舍五入处理。处理数据都要转换为String类型 String.valueOf()
	 * 
	 * @param v
	 *            需要四舍五入的数字
	 * @param scale
	 *            小数点后保留几位
	 * @return 四舍五入后的结果
	 */
	public static double round(String v, int scale) {
		if (scale < 0) {
			throw new IllegalArgumentException("��ȷС����λ����Ϊ�������0");
		}
		BigDecimal b = new BigDecimal(v);
		BigDecimal one = new BigDecimal("1");
		return b.divide(one, scale, BigDecimal.ROUND_HALF_UP).doubleValue();
	}

	/**
	 * 所谓银行家舍入法，其实质是一种四舍六入五留双（又称四舍六入五奇偶）法。 其规则是：当舍去位的数值小于5时，直接舍去该位；
	 * 当舍去位的数值大于等于6时，在舍去该位的同时向前位进一； 当舍去位的数值等于5时，如果前位数值为奇，则在舍去该位的同时向前位进一，
	 * 如果前位数值为偶，则直接舍去该位。
	 * 
	 * @param v
	 * @param scale
	 * @return
	 */
	public static double bankRound(String v, int scale) {
		if (scale < 0) {
			throw new IllegalArgumentException(
					"The   scale   must   be   a   positive   integer   or   zero");
		}
		BigDecimal b = new BigDecimal(v);
		int dotIndex = v.indexOf(".");//  获取正数的长度��ȡ����ĳ���
		if (dotIndex == -1) {
			v = v + ".0";
			dotIndex = v.indexOf(".");
		}
		int length = v.length();// 字符长度�ַ��
		int len = dotIndex + scale;
		if (length - dotIndex - 1 <= scale) {
			return Utility.round(v, scale);
		} else {
			// ȡС��㱣��取小数点保留位后一位的值ֵ
			char t1 = v.charAt(len + 1);
			if (t1 == '.') {
				t1 = v.charAt(len + 2);
			}
			// �����把数字乘以10 的小数点保留位-1次方�
			double t = b.doubleValue() * Math.pow(10, scale);
			// 判断小数点保留位后一位的值是否为5 如果是采取银行舍入法
			if (t1 == '5') {
				//				 取小数点保留位的值ֵ
				int t2 = (int) v.charAt(len);
				char t3 = v.charAt(len);
				if (t3 == '.') {
					t2 = (int) v.charAt(len - 1);
				}
				//				 判断是奇数还是偶数�ж���������ż��
				if (t2 % 2 != 0) {
					if (t > 0)
						t += 0.5;
					else
						t -= 0.5;
				} else {
					//					 由于还要采用原谅四舍五入方法 所以必须保证小数点保留位后一位的值小于5
					if (t < 0)
						t += 0.5;
					else
						t -= 0.5;
				}
			}
			return Utility.round(String.valueOf(t * Math.pow(10, -scale)),
					scale);
		}

	}

	/**
	 * 处理返回显示的时间格式为yyyy-MM-dd HH:mm:ss
	 * 
	 * @param inTime
	 * @return
	 */
	public static String handleFormatDate(java.util.Date date) {
		if (date == null) {
			return "";
		} else {
			return sdf.format(date);
		}
	}

	public static String getDictValue(Integer i) {
		String nameString = "";
		return nameString;
	}

	/**
	 * string转换成date
	 * 
	 * @param index
	 * @return
	 * @throws Exception
	 */
	public static Date parseString2Date(String dateStr, String dateFormat)
			throws ParseException {

		if (null == dateStr || dateStr.length() <= 0)
			return null;

		if (null == dateFormat || dateFormat.length() <= 0)
			dateFormat = FULL_DATE_FORMAT;

		DateFormat formatter = new SimpleDateFormat(dateFormat);
		Date date = formatter.parse(dateStr);
		return date;
	}

	public static Date parseString2DateTime(String dateStr, String dateFormat)
			throws ParseException {

		if (null == dateStr || dateStr.length() <= 0)
			return null;

		if (null == dateFormat || dateFormat.length() <= 0)
			dateFormat = STANDARD_DATE_TIME_FORMAT;

		DateFormat formatter = new SimpleDateFormat(dateFormat);
		Date date = formatter.parse(dateStr);
		return date;
	}

	/**
	 * 取得票据号码
	 * 
	 * @author guodong
	 * @param pOrg_Code
	 * @param type
	 *            :"EO" 单号
	 * @param conn
	 * @return
	 * @throws SQLException
	 */
	/*public static String GetBillNo(String entityCode, String type) {
		String BillNo = "0";
		try {
			String errcode;
			List ins = new LinkedList<Object>();
			ins.add(entityCode);
			ins.add(type);

			List<Integer> outs = new LinkedList<Integer>();
			outs.add(Types.VARCHAR);
			outs.add(Types.VARCHAR);

			List<Object> listout = poFactory.callProcedure("p_getbillno", ins,
					outs);

			errcode = listout.get(0).toString();
			BillNo = listout.get(1).toString();

			if (!"0000000000".equalsIgnoreCase(errcode)) {
				return "";
			}
		} catch (DAOException e) {
			throw e;
		}
		return BillNo;

	}*/

	/*public static String execMonthPeriod(String entityCode, Connection conn)
			throws SQLException {
		CallableStatement stmt = null;
		try {
			String resultMsg = "1";
			int resultCode = 0;
			stmt = conn.prepareCall(" call P_GEN_PART_MONREPORT(?,?,?) ");
			stmt.setString(1, entityCode);
			stmt.registerOutParameter(2, Types.INTEGER);
			stmt.registerOutParameter(3, Types.VARCHAR);
			stmt.execute();
			resultCode = stmt.getInt(2);
			resultMsg = stmt.getString(3);

			return resultMsg;
		} catch (SQLException Serr) {
			throw Serr;
		} finally {
			try {
				if (stmt != null)
					stmt.close();
			} catch (Exception e) {
			}
		}
	}*/

	/*public static String execMonthPeriodCycle(String entityCode, Connection conn)
			throws SQLException {
		CallableStatement stmt = null;
		try {
			String resultMsg = "1";
			int resultCode = 0;
			stmt = conn.prepareCall(" call P_GEN_PART_PERIOD(?,?,?) ");
			stmt.setString(1, entityCode);
			stmt.registerOutParameter(2, Types.INTEGER);
			stmt.registerOutParameter(3, Types.VARCHAR);
			stmt.execute();
			resultCode = stmt.getInt(2);
			resultMsg = stmt.getString(3);

			return resultMsg;
		} catch (SQLException Serr) {
			throw Serr;
		} finally {
			try {
				if (stmt != null)
					stmt.close();
			} catch (Exception e) {
			}
		}
	}*/


	/**
	 * 处理返回显示的时间格式为yyyy-MM-dd
	 * 
	 * @param inTime
	 * @return
	 */
	public static String handleDate(Timestamp inTime) {
		if (inTime == null) {
			return "";
		} else {
			return sdf2.format(new java.util.Date(inTime.getTime()));
		}
	}
	/**
	 * 处理返回显示的时间格式为yyyy-MM-dd
	 * 
	 * @param inTime
	 * @return
	 */
	public static String handleDate(Date inTime) {
		if (inTime == null) {
			return "";
		} else {
			return sdf2.format(new java.util.Date(inTime.getTime()));
		}
	}
	/**
	 * 处理返回显示的时间格式为yyyy-MM-dd
	 * 
	 * @param inTime
	 * @return
	 */
	public static String handleDateMmSs(Date inTime) {
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		if (inTime == null) {
			return "";
		} else {
			return df.format(new java.util.Date(inTime.getTime()));
		}
	}


	/**
	 * 生成xml的string
	 * 
	 * @param tablename
	 * @param fieldname
	 * @param fieldVal
	 * @return
	 */
	public static String setReturnXML(String tablename, String fieldname,
			String fieldVal) {
		StringBuffer sRtn = new StringBuffer();
		sRtn.append("<" + tablename + ">");
		sRtn.append("<" + fieldname + ">");
		sRtn.append(fieldVal);
		sRtn.append("</" + fieldname + ">");
		sRtn.append("</" + tablename + ">");
		return sRtn.toString();
	}

	/**
	 * author comma date 20050221 para String return int
	 */

	public static int getWeekOfYear(String theDate) {
		if (theDate == null || theDate.length() == 0)
			return 0;
		int year = 0, month = 0, date = 0;
		if (theDate.length() == 8) {
			year = Integer.parseInt(theDate.substring(0, 4));
			month = Integer.parseInt(theDate.substring(4, 6));
			date = Integer.parseInt(theDate.substring(6, 8));
		}
		if (theDate.length() == 10) {
			year = Integer.parseInt(theDate.substring(0, 4));
			month = Integer.parseInt(theDate.substring(5, 7));
			date = Integer.parseInt(theDate.substring(8, 10));
		}

		Calendar cal = Calendar.getInstance();
		cal.setFirstDayOfWeek(2); // set Monday as the first day of week
		cal.setMinimalDaysInFirstWeek(1);
		// the first day of the first month of a year
		cal.set(Calendar.YEAR, year);
		cal.set(Calendar.MONTH, month - 1); // subtract 1
		cal.set(Calendar.DATE, date);

		int weekOfYear = cal.get(Calendar.WEEK_OF_YEAR);

		if (weekOfYear == 1 && month == 12) {
			cal.add(Calendar.DATE, -7);
			weekOfYear = cal.get(Calendar.WEEK_OF_YEAR) + 1;
		}

		return (year - 2000) * 100 + weekOfYear;

	}

	/**
	 * Method handelDouble transfer double to string
	 * 
	 * @param value
	 *            double
	 * @return double,source
	 */
	public static String handelDouble(double value) {
		String sRtn = "";
		try {
			if (value == NULL_DOUBLE)
				sRtn = "0.00";
			else {
				nf.setMaximumFractionDigits(2);
				sRtn = nf.format(value);
				if (sRtn != null
						&& sRtn.length() > 0
						&& sRtn.substring(sRtn.length() - 1, sRtn.length())
						.equals(".")) {
					sRtn = sRtn + "00";
				} else if (sRtn != null
						&& sRtn.length() > 1
						&& sRtn.substring(sRtn.length() - 2, sRtn.length() - 1)
						.equals(".")) {
					sRtn = sRtn + "0";
				}
			}
		} catch (Exception e) {
			return "0.00";
		}
		return sRtn;
	}


	/**
	 * Method 截断double值，保留两位小数
	 * 
	 * @return double
	 */
	public static double doubleTransfer(double value) {
		double transvalue = 0;
		if (value != NULL_DOUBLE) {
			try {
				transvalue = new Double(handelDouble(value)).doubleValue();
			} catch (Exception e) {
				transvalue = 0.00;
			}
		} else {
			transvalue = 0.00;
		}
		return transvalue;
	}

	/**
	 * Method 得到当前年
	 * 
	 * @return int
	 */
	public static String getYear() {
		Calendar calendar = new GregorianCalendar();
		int year = calendar.get(java.util.Calendar.YEAR);
		return (new Integer(year)).toString();
	}

	/**
	 * Method 得到当前月(两位数)
	 * 
	 * @return int
	 */
	public static String getMonth() {
		Calendar calendar = new GregorianCalendar();
		int month = calendar.get(java.util.Calendar.MONTH) + 1;
		String sRtn = String.valueOf(month);
		if (String.valueOf(month).length() == 1) {
			sRtn = "0" + sRtn;
		}
		return sRtn;
	}

	/**
	 * Day 得到当前日(两位数)
	 * 
	 * @return int
	 */
	public static String getDay() {
		Calendar calendar = new GregorianCalendar();
		int day = calendar.get(java.util.Calendar.DAY_OF_MONTH);
		String sRtn = String.valueOf(day);
		if (String.valueOf(day).length() == 1) {
			sRtn = "0" + sRtn;
		}
		return sRtn;
	}

	/**
	 * Method 得到当前月（一位数）
	 * 
	 * @return int
	 */
	public static int getMonthNoZero() {
		Calendar calendar = new GregorianCalendar();
		int month = calendar.get(java.util.Calendar.MONTH) + 1;
		return month;
	}

	public static String getSubMonth(int i) {
		int month = getMonthNoZero();

		int subMonth = month - i;
		if (subMonth <= 0) {
			subMonth = subMonth + 12;
		}
		String sRtn = String.valueOf(subMonth);
		if (String.valueOf(sRtn).length() == 1) {
			sRtn = "0" + sRtn;
		}
		return sRtn;
	}

	public static String getEngMonth(int i) {
		String sRtn = "";
		int month = getMonthNoZero();
		int subMonth = month - i;
		if (subMonth <= 0) {
			subMonth = subMonth + 12;
		}
		switch (subMonth) {
		case 1:
			sRtn = "JAN";
			break;
		case 2:
			sRtn = "FEB";
			break;
		case 3:
			sRtn = "MAR";
			break;
		case 4:
			sRtn = "APR";
			break;
		case 5:
			sRtn = "MAY";
			break;
		case 6:
			sRtn = "JUN";
			break;
		case 7:
			sRtn = "JUL";
			break;
		case 8:
			sRtn = "AUG";
			break;
		case 9:
			sRtn = "SEP";
			break;
		case 10:
			sRtn = "OCT";
			break;
		case 11:
			sRtn = "NOV";
			break;
		case 12:
			sRtn = "DEC";
			break;
		}
		return sRtn;
	}

	public static String getSubYear(int i) {
		int month = getMonthNoZero();
		int year = new Integer(getYear()).intValue();
		int subMonth = month - i;
		if (subMonth <= 0) {
			year = year - 1;
		}
		// �����ǹ�Ԫǰ
		String sRtn = String.valueOf(year);

		return sRtn;
	}

	public static double getSubDate(Date d) {
		Date cur = new Date();
		return (cur.getTime() - d.getTime()) / 3600 / 24 / 1000;

	}

	/*public static void main(String[] args) {
		try {
			// System.out.println(getSubDate(parseString2Date("2008-01-03
			// 12:12:12",null)));
			// Calendar calendar = new GregorianCalendar();
			// calendar.setTime(new Date());
			// calendar.add(Calendar.MINUTE, 1*365*24*60);
			// System.out.println(calendar.getTime());
			// System.out.println(new Date());
			// System.out.println(TimeZone.getDefault());
			// System.out.println(System.getProperty("user.timezone"));
			// System.out.println(System.getProperty("user.country"));
			// System.out.println(System.getProperty("java.home"));
			//System.out.println(getCurrentDateTime());
			// System.out.println(new Date());
			// SimpleDateFormat sdf22 = new
			// SimpleDateFormat("yyyy��MM��dd��hhʱmm��");
			//			
			// System.out.println(sdf22.format(parseString2Date("2008-01-03
			// 12:12:12",null)));
			// System.out.println(getMonth());
			// System.out.println(getYear());
			// System.out.println(getMonthNoZero());
			// for(int i=1;i<6;i++){
			// System.out.println(getSubYear(i));
			// System.out.println(getSubMonth(i));
			// }

			double d = 4.555;
			// System.out.println(Math.round(d * 100));

			Connection conn = null;
			Statement ste = null;
			String colName = "VIN";
			try {
				Class.forName("com.ibm.db2.jcc.DB2Driver");
				conn = DriverManager.getConnection(
						"jdbc:db2://192.168.3.28:50000/HCMI", "administrator",
						"password");
				ste = conn.createStatement();
				String sql = "insert into tm_booking_type2 (entity_code,booking_type_code,booking_type_name) values('1','a','"
						+ colName + "')";
				// ste.execute(sql);
				ste.close();
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} finally {

				if (conn != null)
					try {
						conn.close();
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}*/

	/**
	 * 通过传入的表名和列名(string类型)生成条件内容
	 * 
	 * @param tabName
	 * @param colName
	 * @return
	 */
	public static String getStrCond(String tabName, String colName,
			String colValue) {
		if (colValue == null || colValue.trim().equals("")) {
			return "";
		} else {
			if (tabName == null || tabName.trim().equals("")) {
				return " AND " + colName + "= '" + colValue + "'";
			} else {
				return " AND " + tabName + "." + colName + "= '" + colValue
						+ "'";
			}
		}
	}

	/**
	 * 通过传入的表名和列名(int类型)生成条件内容
	 * 
	 * @param tabName
	 * @param colName
	 * @return
	 */
	public static String getintCond(String tabName, String colName,
			String colValue) {
		if (colValue == null || colValue.trim().equals("")) {
			return "";
		} else {
			if (tabName == null || tabName.trim().equals("")) {
				return " AND " + colName + "= " + colValue;
			} else {
				return " AND " + tabName + "." + colName + "= " + colValue;
			}
		}
	}

	/**
	 * (模糊查询)通过传入的表名和列名(string类型)生成条件内容
	 * 
	 * @param tabName
	 * @param colName
	 * @return
	 */
	public static String getStrLikeCond(String tabName, String colName,
			String colValue) {
		if (colValue == null || colValue.trim().equals("")) {
			return "";
		} else {
			if (tabName == null || tabName.trim().equals("")) {
				return " AND " + colName + " LIKE '%" + colValue + "%'";
			} else {
				return " AND " + tabName + "." + colName + " LIKE '%"
						+ colValue + "%'";
			}
		}
	}

	/**
	 * 通过传入的表名、列名(date)生成条件内容
	 * 
	 * @param tabName
	 * @param startDate
	 * @param endDate
	 * @return
	 * @throws Exception
	 */
	/*public static String getDateCond(String tabName, String colName,
			String startDate, String endDate) {
		String tab = "";
		String str = "";
		if (tabName == null || tabName.trim().equals("")) {

			tab = colName;
		} else {
			tab = tabName + "." + colName;
		}
//		 值为1 时，为DB2数据库��ݿ�
		if (Utility.DB_TYPE == 1) {
			if (startDate != null && !startDate.equals("")) {
				str = " AND DATE ( " + tab + ")>=DATE('" + startDate + "')";
			}
			if (endDate != null && !endDate.equals("")) {
				str = str + " AND DATE ( " + tab + ")<=DATE('" + endDate + "')";
			}
		} else {
			if (startDate != null && !startDate.equals("")) {
				str = " AND TO_CHAR ( " + tab + ",'yyyy-mm-dd')>='" + startDate
						+ "'";
			}
			if (endDate != null && !endDate.equals("")) {
				str = str + " AND TO_CHAR ( " + tab + ",'yyyy-mm-dd') <='"
						+ endDate + "'";
			}
		}

		return str;
	}*/

	/**
	 * 通过传入的表名、列名(date类型)和判断条件（>,<等符号）生成条件内容
	 * 
	 * @param tabName
	 * @param colValue
	 * @param colName
	 * @return
	 * @throws Exception
	 */
	public static String getDateConds(String tabName, String colName,
			String colValue, String cond) {
		if (colValue == null && colValue.trim().equals("")) {
			return "";
		}
		if (tabName == null || tabName.trim().equals("")) {
			return " AND DATE(" + colName + ")" + cond + "DATE(" + colValue
					+ ")";
		} else {
			return " AND DATE(" + tabName + "." + colName + ")" + cond
					+ "DATE(" + colValue + ")";
		}

	}

	public static Date getCurrentDateTime() throws ParseException {
		Date now = new Date(System.currentTimeMillis());
		// DateFormat gmt08Formatter = DateFormat.getDateTimeInstance();
		// TimeZone timezone = TimeZone.getTimeZone("GMT+08:00");
		// gmt08Formatter.setTimeZone(timezone);
		// String gmt08DateTime = gmt08Formatter.format(now);
		// System.out.println(gmt08DateTime);
		// now = parseString2Date(gmt08DateTime, null);
		// return parseString2Date(gmt08DateTime, null);
		//System.out.println("..............." + now);
		return parseString2Date(sdf.format(now), null);
	}

	public static Timestamp getCurrentTimestamp() throws ParseException {
		Date now = new Date(System.currentTimeMillis());
		return new Timestamp(parseString2Date(sdf.format(now), null).getTime());

	}

	// ȡ��ϵͳ��ǰʱ���
	public static String getSystemTimestamp() {
		Date date = new Timestamp(System.currentTimeMillis());
		return "" + date;
	}

	/**
	 * 根据用户ID,ORG_CODE,FUNCTION_CODE返回 OWEND_BY数据权限条件,并不是所有的功能页 面都需要设定数据控制范围的
	 * 需要根据实际业务来设定
	 * 
	 * @param tabName
	 *            表别名
	 * @param userId
	 * @param orgCode
	 * @Param functionCode
	 * @return
	 */
	// public static String getOwnedByStr(
	// Connection conn,
	// String tabName,
	// Long userId,
	// String orgCode,
	// String functionCode,
	// String entityCode) {
	// String str = "";
	// List rsList = new ArrayList();
	// TtUserRangeMappingPO po = new TtUserRangeMappingPO();
	// po.setEntityCode(entityCode);
	// po.setUserId(userId);
	// po.setFunctionCode(functionCode);
	// rsList = POFactory.select(conn, po);
	// if (tabName == null || tabName.trim().equals(""))
	// {
	// str = " AND OWNED_BY ";
	// }
	// else
	// {
	// str = " AND " + tabName + ".OWNED_BY ";
	// }
	// if (rsList.size() > 0)
	// {
	// po = (TtUserRangeMappingPO) rsList.get(0);
	// if (po.getRangeCode() != null && po.getRangeCode().equals("A"))
	// {
	// //��ѯ���˵����
	// str = str + " = " + userId;
	// }
	// if (po.getRangeCode() != null && po.getRangeCode().equals("B"))
	// {
	// //��ѯ����֯�����
	// str = str + " IN " + "(SELECT USER_ID FROM TM_USER WHERE ORG_CODE = '" +
	// orgCode
	// + "' AND USER_STATUS = " + DictDataConstant.DICT_IN_USE_START + ")";
	// }
	// if (po.getRangeCode() != null && po.getRangeCode().equals("C"))
	// {
	// //��ѯ����֯����֯�µ����
	// str = str + " IN " + "(SELECT USER_ID FROM TM_USER WHERE ORG_CODE LIKE '"
	// + orgCode
	// + "%' AND USER_STATUS = " + DictDataConstant.DICT_IN_USE_START + ")";
	// }
	// if (po.getRangeCode() != null && po.getRangeCode().equals("D"))
	// {
	// //��ѯ���е����
	// str = "";
	// }
	// }
	// else
	// {
	// str = str + " = " + userId;
	// }
	// return str;
	// }
	protected static final Logger logger = LoggerFactory.getLogger(Utility.class);

	/*public static String getLikeCond(String tabName, String colName,
			String colValue, String colSpl) {
		String str = "";
		if (colValue == null || colValue.trim().equals("") || colName == null
				|| colName.trim().equals("") || colSpl == null
				|| colSpl.trim().equals("")) {
			return str;
		}
		if (tabName != null && !"".equals(tabName.trim())) {
			str = tabName + ".";
		}
		str = " " + colSpl + " " + str + colName + " LIKE '";
		// if("VIN".equals(colName)||"RO_NO".equals(colName)||"LICENSE".equals(colName)){
		if (1 == 1) {
			str = str + "%" + colValue + "%";
		} else if (1 != 1) {
			str = str + colValue + "%";
			Connection conn = null;
			Statement ste = null;
			try {
				Class.forName("com.ibm.db2.jcc.DB2Driver");
				conn = DriverManager.getConnection(
						"jdbc:db2://192.168.3.28:50000/INFODMS", "infodmsuser",
						"infodmsuser99");
				ste = conn.createStatement();
				String sql = "insert into tm_booking_type2 (entity_code,booking_type_code,booking_type_name) values('1','a','"
						+ colValue + "')";
				ste.execute(sql);
				ste.close();
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} finally {
				if (conn != null)
					try {
						conn.close();
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
			}

		}
		str = str + "'";

		return str;

	}*/


	/**
	 * 根据传过来的参数，拼成一个sql的查询条件
	 * @param paramStr
	 * @param paramsList
	 * @param tableName
	 * @param fieldName
	 * @return
	 */	
	public static String getConSqlByParamForEqual(String paramStr,List<Object> params,String tableName,String fieldName){
		if(paramStr==null||"".equals(paramStr)){
			return "";
		}
		if (paramStr!=null) {
			if (paramStr.split(",").length<=0) {
				return "";
			}
		}
		String returnStr = " and (";
		String[] paramStrArr = paramStr.split(",");
		for(int i=0;i<paramStrArr.length;i++){
			String tempCode = paramStrArr[i];
			if(i==0){
				returnStr += tableName+"."+fieldName+"=?";
				params.add(tempCode);
			}else{
				returnStr += " or "+tableName+"."+fieldName+"=?";	
				params.add(tempCode);	
			}
		}
		returnStr += " )";
		return returnStr;
	}

	public static String getConSqlByParamForEqual(String[] paramStrArr,List<Object> params,String tableName,String fieldName){
		if(paramStrArr==null||"".equals(paramStrArr)){
			return "";
		}
		String returnStr = " and (";
		for(int i=0;i<paramStrArr.length;i++){
			String tempCode = paramStrArr[i];
			if(i==0){
				returnStr += tableName+"."+fieldName+"=?";
				params.add(tempCode);
			}else{
				returnStr += " or "+tableName+"."+fieldName+"=?";	
				params.add(tempCode);	
			}
		}
		returnStr += " )";
		return returnStr;
	}

	/*public static String getConSqlByParamForEqual(List<TmUserPO> userList,List<Object> params,String tableName,String fieldName){
		if(userList==null||userList.size()==0){
			return "";
		}
		String returnStr = " and (";
		for(int i=0;i<userList.size();i++){
			String tempCode = ""+userList.get(i).getUserId();
			if(i==0){
				returnStr += tableName+"."+fieldName+"=?";
				params.add(tempCode);
			}else{
				returnStr += " or "+tableName+"."+fieldName+"=?";	
				params.add(tempCode);	
			}
		}
		returnStr += " )";
		return returnStr;
	}*/

	/**
	 * 根据传过来的参数，拼成一个sql的查询条件
	 * @param paramStr
	 * @param params
	 * @param tableName
	 * @param fieldName
	 * @return
	 */
	public static String getConSqlByParamForInstr(String paramStr,List<Object> params,String tableName,String fieldName) {
		if(paramStr==null||"".equals(paramStr)){
			return "";
		}
		String returnStr = " and (";
		String[] paramStrArr = paramStr.split(",");
		for(int i=0;i<paramStrArr.length;i++){
			String tempCode = paramStrArr[i];
			if(i==0){			
				returnStr += "instr("+tableName+"."+fieldName+",?)>0";
				params.add(tempCode);
			}else{
				returnStr += " or instr("+tableName+"."+fieldName+",?)>0";
				params.add(tempCode);	
			}
		}
		returnStr += " )";
		return returnStr;
	}
	/**
	 * 去掉空格
	 * @param str
	 * @return
	 */
	public static String  StrimAll(String str)   {     
		for(int i = 0;i <str.length() && str.charAt(i) == 32; i++){
			str = str.substring(i,str.length());   
		}   
		for(int i=str.length()-1;i>=0&&str.charAt(i)==32;i--){
			str = str.substring(0,i+1);   
		}
		return   str;   
	} 	
	/**
	 * 计算时间毫秒
	 * @param str
	 * @return
	 */
	public static long fromDateStringToLong(String inVal) { //此方法计算时间毫秒
		Date date = null;   //定义时间类型       
		SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-mm-dd HH:mm:ss"); 
		try { 
			date = inputFormat.parse(inVal); //将字符型转换成日期型
		} catch (Exception e) { 
			e.printStackTrace(); 
		} 
		return date.getTime();   //返回毫秒数
	} 
	/**
	 * 获得当前系统时间（格式类型2007-11-6 15:10:58）
	 * @param str
	 * @return
	 */
	public static  String getCurrentTime() {  //此方法用于获得当前系统时间（格式类型2007-11-6 15:10:58）
		Date date = new Date();  //实例化日期类型
		String today = date.toLocaleString(); //获取当前时间
		//System.out.println("获得当前系统时间 "+today);  //显示
		return today;  //返回当前时间
	}
	/**
	 * 字符转码：
	 * @param str
	 * @return   UTF-8 的字符
	 * @throws Exception
	 */
	public static String getString(String str){
		String re = null;
		if(str != null && !"".equals(str.trim())){
			try {
				re = new String(str.getBytes("iso-8859-1"),"utf-8");
			} catch (UnsupportedEncodingException e) {
				// TODO 自动生成 catch 块
				e.printStackTrace();
			} 
		}
		return re;
	}

	public static String getGBKString(String str){
		String re = null;
		if(str != null && !"".equals(str.trim())){
			try {
				re = new String(str.getBytes("iso-8859-1"),"GBK");
			} catch (UnsupportedEncodingException e) {
				throw new RuntimeException(e);
			} 
		}
		return re;
	}
	/**
	 * 当登录人组织为部门时，返回上级ORG_ID
	 * 当登录人DUTY_TYPE<>部门时，返回登录人ORG_ID
	 * 用于 车厂端和区域端共用SQL
	 * @param logonUser
	 * @return
	 * CREATE BY XIAYANPENG 
	 */
	/*public static Long getDeptOrgId(AclUserBean logonUser){
			//取登录人ORG_ID
			Long orgId = logonUser.getOrgId();
			String dutyType = logonUser.getDutyType();
			//当登录人组织为部门时，取上级组织ID
			if(String.valueOf(Constant.DUTY_TYPE_DEPT).equals(dutyType)){
				orgId = new Long(logonUser.getParentOrgId());
			}
			return orgId;

		}*/
	/**   
	 * @param stype 返回值类型   0为多少天，1为多少个月，2为多少年   
	 * @return   
	 */  
	public static int compareDate(String date1,String date2,int stype){   
		int n = 0;   

		String[] u = {"天","月","年"};   
		String formatStyle ="yyyy-MM-dd";   
		DateFormat df = new SimpleDateFormat(formatStyle);   
		Calendar c1 = Calendar.getInstance();   
		Calendar c2 = Calendar.getInstance();   
		try {   
			c1.setTime(df.parse(date1));   
			c2.setTime(df.parse(date2));   
		} catch (Exception e3) {   
			//System.out.println("wrong occured");   
		}   
		while (!c1.after(c2)) {       
			n++;   
			if(stype==1){   
				c1.add(Calendar.MONTH, 1);// 比较月份，月份+1   
			}   
			else{   
				c1.add(Calendar.DATE, 1); // 比较天数，日期+1   
			}   
		}   

		n = n-1;   

		if(stype==2){  
			int yushu=(int)n%365;  
			n =  yushu==0?(n/365):((n/365)-1);
		}      

		//System.out.println(date1+" -- "+date2+" 相差多少"+u[stype]+":"+n);         
		return n;   
	}   

	public static String getCODES(String codes){
		if(codes!=null&&!codes.equals("")){//截串加单引号
			String[] supp = codes.split(",");
			codes = "";
			for(int i=0;i<supp.length;i++){
				supp[i] = "'"+supp[i]+"'";
				if(!codes.equals("")){
					codes += "," + supp[i];
				}else{
					codes = supp[i];
				}
			}
		}else{
			codes = "";
		}
		return codes;
	}

	public static String checkNull(Object obj) {
		if (obj != null)
			return obj.toString().trim();
		else
			return "";
	}
	/**
	 * 获取异常堆栈信息
	 * @param t
	 * @param length
	 * @return
	 */
	public static String getErrorStack(Throwable t,int length){	
		try{
			StringWriter sw = new StringWriter();
			t.printStackTrace(new PrintWriter(sw));
			byte[] ret = sw.toString().getBytes("UTF-8");	
			if(ret.length >= length){
				return new String(ret, 0,length,"UTF-8");
			}else{
				return new String(ret,0,ret.length,"UTF-8");
			}
		}catch(Exception e){
			logger.error("get Error Stack error! "+e.getMessage(),e);
			return null;
		}
	}


	/**
	 * 计量单位取得
	 * 
	 * @param unit
	 * @return
	 */
	public static String getUnitName(String unit) {
		Map<String, String> unitMap = new HashMap<String, String>();
		unitMap.put("A001", "带");
		unitMap.put("A002", "公分");
		unitMap.put("A003", "个");
		unitMap.put("A004", "Gram");
		unitMap.put("A005", "公斤");
		unitMap.put("A006", "升");
		unitMap.put("A007", "米");
		unitMap.put("A008", "平方米");
		unitMap.put("A009", "双");
		unitMap.put("A010", "包");
		unitMap.put("A011", "英石");
		unitMap.put("A012", "吨（美国）");
		unitMap.put("A013", "把");
		unitMap.put("A014", "本");
		unitMap.put("A015", "部");
		unitMap.put("A016", "车付");
		unitMap.put("A017", "床");
		unitMap.put("A018", "袋");
		unitMap.put("A019", "顶");
		unitMap.put("A020", "份");
		unitMap.put("A021", "付");
		unitMap.put("A022", "副");
		unitMap.put("A023", "根");
		unitMap.put("A024", "盒");
		unitMap.put("A025", "壶");
		unitMap.put("A026", "架");
		unitMap.put("A027", "节");
		unitMap.put("A028", "卷");
		unitMap.put("A029", "颗");
		unitMap.put("A030", "块");
		unitMap.put("A031", "捆");
		unitMap.put("A032", "枚");
		unitMap.put("A033", "盘");
		unitMap.put("A034", "批");
		unitMap.put("A035", "片");
		unitMap.put("A036", "台");
		unitMap.put("A037", "套");
		unitMap.put("A038", "提");
		unitMap.put("A039", "条");
		unitMap.put("A040", "桶");
		unitMap.put("A041", "张");
		unitMap.put("A042", "支");
		unitMap.put("A043", "只");
		unitMap.put("A044", "组");
		if (unitMap.containsKey(unit)) {
			return unitMap.get(unit);
		}
		return unit;
	}

	/**
	 * 计量单位取得
	 * 
	 * @param unit
	 * @return
	 */
	public static String getUnitCode(String name) {
		Map<String, String> unitMap = new HashMap<String, String>();
		unitMap.put("带","A001");
		unitMap.put("公分","A002");
		unitMap.put("个","A003");
		unitMap.put("Gram","A004");
		unitMap.put("公斤","A005");
		unitMap.put("升","A006");
		unitMap.put("米","A007");
		unitMap.put("平方米","A008");
		unitMap.put("双","A009");
		unitMap.put("包","A010");
		unitMap.put("英石","A011");
		unitMap.put("吨（美国）","A012");
		unitMap.put("把","A013");
		unitMap.put("本","A014");
		unitMap.put("部","A015");
		unitMap.put("车付","A016");
		unitMap.put("床","A017");
		unitMap.put("袋","A018");
		unitMap.put("顶","A019");
		unitMap.put("份","A020");
		unitMap.put("付","A021");
		unitMap.put("副","A022");
		unitMap.put("根","A023");
		unitMap.put("盒","A024");
		unitMap.put("壶","A025");
		unitMap.put("架","A026");
		unitMap.put("节","A027");
		unitMap.put("卷","A028");
		unitMap.put("颗","A029");
		unitMap.put("块","A030");
		unitMap.put("捆","A031");
		unitMap.put("枚","A032");
		unitMap.put("盘","A033");
		unitMap.put("批","A034");
		unitMap.put("片","A035");
		unitMap.put("台","A036");
		unitMap.put("套","A037");
		unitMap.put("提","A038");
		unitMap.put("条","A039");
		unitMap.put("桶","A040");
		unitMap.put("张","A041");
		unitMap.put("支","A042");
		unitMap.put("只","A043");
		unitMap.put("组","A044");
		if (unitMap.containsKey(name)) {
			return unitMap.get(name);
		}
		return name;
	}



}
