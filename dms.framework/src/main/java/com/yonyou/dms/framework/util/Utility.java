package com.yonyou.dms.framework.util;

import java.math.BigDecimal;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;
import org.javalite.activejdbc.Base;
import org.javalite.activejdbc.DB;
import org.javalite.activejdbc.MetaModel;

import com.yonyou.dms.framework.DAO.DAOUtil;
import com.yonyou.dms.function.common.CommonConstants;
import com.yonyou.dms.function.common.DictCodeConstants;
import com.yonyou.dms.function.exception.ServiceBizException;

@SuppressWarnings({ "unchecked", "rawtypes" })
public class Utility {

    // entityRelationshipMapping存放BROTHER_MODE模式的<CHILD_ENTITY+BIZ_CODE,PARENT_ENTITY>映射关系，用于子找父
    private static HashMap<String, String> entityRelationshipMappingForFindParent = null;

    private static List<Map>               brotherModeList                        = null;

    private static HashMap<String, String> entityPrivateFieldMapping              = null;

	private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	public static final String FULL_DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";
	/**
	 * 描述： 默认除法运算精度 主要用于浮点型类型换算中使用
	 */
	private static final int DEF_DIV_SCALE = 2;

    /**
     * 返回开关控制参数
     * 
     * @param tabName
     * @param colName
     * @return
     */
    public static String getDefaultValue(String itemCode) {
        String str = "";
        List<Object> list = new ArrayList<Object>();
        list.add(itemCode);
        String sql = "SELECT * FROM TM_DEFAULT_PARA WHERE ITEM_CODE = ? ";
        List<Map> delist = DAOUtil.findAll(sql, list);
        if (delist != null && delist.size() > 0) {
            for (int i = 0; i < delist.size(); i++) {
                str = delist.get(i).get("DEFAULT_VALUE").toString();
            }
        }
        return str;
    }

    /**
     * 核对是否完成,返回12781001/12781002（是/否）
    * TODO description
    * @author yujiangheng
    * @date 2017年5月6日
    * @param sheetNo
    * @param dealerCode
    * @param sheetType
    * @param sheetNoType
    * @return
    * @throws Exception
     */
    public static String checkIsFinished(String sheetNo, String dealerCode, String sheetType,String sheetNoType) throws Exception {
                                     String flag ="12781001";
                                     if (sheetNo != null && !"".equals(sheetNo.trim()) && sheetType != null && !"".equals(sheetType.trim())) {
                                         StringBuffer sql = new StringBuffer(" select * from "+sheetType+" where dealer_code='");
                                         sql.append( dealerCode + "' and d_key=" +0  + " and "+sheetNoType+"='" + sheetNo + "' and IS_FINISHED='12781002'");
                                         List<Map> list=DAOUtil.findAll(sql.toString(), null);
                                         System.out.println(sql);
                                         while (list!=null) {
                                             flag = "12781002";
                                         }
                                     }
                                     return flag;
                                 }
    
    
    /**
     * TODO 拼接sql语句模糊查询
     * 
     * @author yangjie
     * @date 2016年12月28日
	 * @param param
	 *            查询条件
	 * @param field
	 *            查询的字段
	 * @param alias
	 *            表的别名
     * @return
     */
    public static void sqlToLike(StringBuffer sb, String param, String field, String alias) {
        if (StringUtils.isNotBlank(param)) {
            sb.append(" AND ");
            if (StringUtils.isNotBlank(alias)) {
                sb.append(alias + "." + field);
            } else {
                sb.append(field);
            }
            sb.append(" LIKE '%" + param + "%' ");
        }
    }

   
    
    /**
     * TODO 拼接sql语句等量查询
     * 
     * @author yangjie
     * @date 2016年12月28日
	 * @param param
	 *            查询条件
	 * @param field
	 *            查询的字段
	 * @param alias
	 *            表的别名
     * @return
     */
    public static void sqlToEquals(StringBuffer sb, String param, String field, String alias) {
        if (StringUtils.isNotBlank(param)) {
            sb.append(" AND ");
            if (StringUtils.isNotBlank(alias)) {
                sb.append(alias + "." + field);
            } else {
                sb.append(field);
            }
            sb.append(" = '" + param + "' ");
        }
    }

    /**
     * TODO 拼接sql语句时间查询(单个字段)
     * 
     * @author yangjie
     * @date 2016年12月28日
	 * @param begin
	 *            开始时间
	 * @param end
	 *            结束时间
	 * @param field
	 *            查询的字段
	 * @param alias
	 *            表的别名
     * @return
     */
    public static void sqlToDate(StringBuffer sb, String begin, String end, String field, String alias) {
        if (StringUtils.isNotBlank(begin) && StringUtils.isNotBlank(end)) {
            sb.append(" AND ");
            if (StringUtils.isNotBlank(alias)) {
                sb.append(alias + "." + field);
            } else {
                sb.append(field);
            }
            sb.append(" between '" + begin + "' AND '" + end + "' ");
        } else if (StringUtils.isNotBlank(begin) && !StringUtils.isNotBlank(end)) {
            sb.append(" AND ");
            if (StringUtils.isNotBlank(alias)) {
                sb.append(alias + "." + field);
            } else {
                sb.append(field);
            }
            sb.append(" >= '" + begin + "' ");
        } else if (!StringUtils.isNotBlank(begin) && StringUtils.isNotBlank(end)) {
            sb.append(" AND ");
            if (StringUtils.isNotBlank(alias)) {
                sb.append(alias + "." + field);
            } else {
                sb.append(field);
            }
            sb.append(" <= '" + end + "' ");
        }
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
    public static double round(String v, int scale) throws ServiceBizException {
        if (scale < 0) {
            throw new ServiceBizException("精确小数点后几位必须为正数或者0 ");
        }
        BigDecimal b = new BigDecimal(v);
        BigDecimal one = new BigDecimal("1");
        return b.divide(one, scale, BigDecimal.ROUND_HALF_UP).doubleValue();
    }

    /**
     * 功能描述：获取父DEALER_CODE，只适用于BROTHER_MODE模式 TODO description
     * 
     * @author yangjie
     * @date 2017年1月19日
     * @param dealerCode
     * @param bizCode
     * @return
     */
    public static String getGroupEntity(String dealerCode, String bizCode) {
		if (dealerCode == null || dealerCode.trim().equals(""))
			return dealerCode;
        if (entityRelationshipMappingForFindParent == null) {
            entityRelationshipMappingForFindParent = new HashMap();
            String sql = "SELECT * FROM TM_ENTITY_RELATIONSHIP WHERE RELATIONSHIP_MODE='BROTHER_MODE'";
            List<Map> list = Base.findAll(sql);
            if (list != null && list.size() > 0) {
                for (Map map : list) {
                    String tempKey = map.get("PARENT_ENTITY") == null ? "" : map.get("PARENT_ENTITY").toString();
					if (!entityRelationshipMappingForFindParent
							.containsKey(map.get("CHILD_ENTITY").toString() + map.get("BIZ_CODE").toString())
                        || !tempKey.equals(map.get("CHILD_ENTITY").toString()))
						entityRelationshipMappingForFindParent.put(
								map.get("CHILD_ENTITY").toString() + map.get("BIZ_CODE").toString(),
                                                                   map.get("PARENT_ENTITY").toString());
                }
            }
        }

        if (entityRelationshipMappingForFindParent.containsKey(dealerCode + bizCode)) {
            String tmp = entityRelationshipMappingForFindParent.get(dealerCode + bizCode);
            return tmp;
		} else
			return dealerCode;
    }

    /**
     * 功能描述：获取子ENTITY_CODE，只适用于BROTHER_MODE模式 TODO description
     * 
     * @author yangjie
     * @date 2017年2月3日
     * @param conn
     * @param groupCode
     * @param bizCode
     * @return
     */
    public static List<Map> getSubEntityList(String groupCode, String bizCode) {
		if (groupCode == null || groupCode.trim().equals(""))
			return null;

        if (brotherModeList == null) {
            String sql = "SELECT * FROM TM_ENTITY_RELATIONSHIP WHERE RELATIONSHIP_MODE = 'BROTHER_MODE'";
            brotherModeList = Base.findAll(sql);
        }

        if (brotherModeList != null && brotherModeList.size() > 0) {
            List<Map> list = new ArrayList();
            Map poResult = null;
            for (int i = 0; i < brotherModeList.size(); i++) {
                poResult = brotherModeList.get(i);

                if (poResult.get("PARENT_ENTITY").toString().equals(groupCode)
                    && poResult.get("BIZ_CODE").equals(bizCode)) {
                    Map temp = new HashMap();
					temp.put("BIZ_CODE", poResult.get("BIZ_CODE").toString());
                    temp.put("CHILD_ENTITY", poResult.get("CHILD_ENTITY").toString());
                    temp.put("PARENT_ENTITY", poResult.get("PARENT_ENTITY").toString());
                    temp.put("RELATIONSHIP_MODE", poResult.get("RELATIONSHIP_MODE").toString());
                    list.add(temp);
                }
            }
            return list;
		} else
			return null;
    }

    /**
     * string转换成int
     * 
     * @author wangliang
     * @date 2017年1月19日
     * @param index
     * @return
     * @throws Exception
     */
    public static int getInt(String index) throws Exception {
        int iRtn;
        if (index == null || index.trim().equals("")) {
            iRtn = new Integer(0);
            return iRtn;
        }
        return Integer.parseInt(index);
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
		if (index == null || index.trim().equals("")) {
            iRtn = new Float(0);
            return iRtn;
        }
        return Float.parseFloat(index);
    }

    /**
     * string转换成timestamp
     * 
     * @author wangliang
     * @date 2017年1月19日
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
     * 通过传入的表名、列名(date)生成条件内容
     * 
     * @author wangliang
     * @date 2017年1月19日
     * @param tabName
     * @param startDate
     * @param endDate
     * @return
     * @throws Exception
     */
    public static String getDateCond(String tabName, String colName, String startDate, String endDate) {
        // update by obc
        // 判断日期格式是否正确
        boolean flag1 = isValid(startDate);
        boolean flag2 = isValid(endDate);
        if (!flag1 || !flag2) {
            return "";
        }
        // end
        String tab = "";
        String str = "";
        if (colName == null || colName.trim().equals("")) {
            return "";
        }
        if (tabName == null || tabName.trim().equals("")) {

            tab = colName;
        } else {
            tab = tabName + "." + colName;
        }

        if (startDate != null && !startDate.equals("")) {
            str = " AND  " + tab + " >='" + (startDate.indexOf(":") == -1 ? startDate + " 00:00:00" : startDate) + "' ";
        }
        if (endDate != null && !endDate.equals("")) {
            str = str + " AND  " + tab + " <= '" + (endDate.indexOf(":") == -1 ? endDate + " 23:59:59" : endDate)
                  + "' ";
        }
        return str;
    }

    /**
     * 判断日期格式是否合法
     * 
     * @author wangliang
     * @date 2017年1月19日
	 * @param date
	 *            return
     */
    public static boolean isValid(String date) {
        SimpleDateFormat dateFormat;
        if (date != null && !"".equals(date)) {
            if (date.indexOf(":") >= 1) {
                dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
            } else {
                dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            }
            try {
                dateFormat.parse(date);
                return true;
            } catch (ParseException e) {
                return false;
            }
        }
        return false;
    }

    /**
     * 定义1为mysql数据库
     */
    public static final int MySql_TYPE = 1;

    /**
     * String转换成double
     * 
     * @param index
     * @return
     */
    public static double getDouble(String index) {
        double dRtn;
        if (index == null || index.trim().equals("")) {
            dRtn = new Double(0);
            return dRtn;
        }
        return Double.parseDouble(index);

    }

    /**
     * 拼接sql
     * 
	 * @param tabName
	 *            表名
	 * @param colName
	 *            列名
	 * @param colValue
	 *            值
     * @param colSpl
     * @return
     */
    @SuppressWarnings("unused")
    public static String getLikeCond(String tabName, String colName, String colValue, String colSpl) {
        String str = "";
        if (colValue == null || colValue.trim().equals("") || colName == null || colName.trim().equals("")
            || colSpl == null || colSpl.trim().equals("")) {
            return str;
        }
        if (tabName != null && !"".equals(tabName.trim())) {
            str = tabName + ".";
        }
        str = "  " + colSpl + "  " + str + "  " + colName + "  " + " LIKE  " + " '";
        if (1 == 1) {
            str = str + "%" + colValue + "%";
        } else if (1 != 1) {
            str = str + colValue + "%";
        }
        str = str + "'";
        return str;

    }

    /**
     * 通过传入的表名和列名（int 类型）生成条件内容
     * 
     * @param tabName
     * @param colName
     * @return
     */
    public static String getintCond(String tabName, String colName, String colValue) {
        if (colValue == null || colValue.trim().equals("")) {
            return "";
        } else {
            if (tabName == null || tabName.trim().equals("")) {
                return " AND " + colName + "=" + colValue;
            } else {
                return " AND " + tabName + "." + colName + "= " + colValue;
            }
        }

    }

    /**
     * 通过传入的表名和列名(string类型)生成条件内容
     * 
     * @param tabName
     * @param colName
     * @return
     */
    public static String getStrCond(String tabName, String colName, String colValue) {
        if (colValue == null || colValue.trim().equals("")) {
            return "";
        } else {
            if (tabName == null || tabName.trim().equals("")) {
                return " AND " + colName + "= '" + colValue + "'";
            } else {
                return " AND " + tabName + "." + colName + "= '" + colValue + "'";
            }
        }
    }

    /**
     * 说明：返回两个日期相差几天（字段）
     * 
	 * @param tabName1
	 *            表名1或别名1
	 * @param colName1
	 *            列名1
	 * @param tabName2
	 *            表名2或别名2
	 * @param colName2
	 *            列名2
     * @return String
     */
    public static String getDateDiff(String tabName1, String colName1, String tabName2, String colName2) {
        String tab1 = getTableStr(tabName1, colName1);
        String tab2 = getTableStr(tabName2, colName2);
        String str = "";

        // 值为1 时，为DB2数据库
        if (MySql_TYPE == 1) {
            str = " to_days(" + tab1 + ") - to_days(" + tab2 + ") ";
        }
        return str;
    }

    public static String getTableStr(String tabName, String colName) {
        String tab = "";
        if (tabName == null || tabName.trim().equals("")) {
            tab = colName;
        } else {
            tab = tabName + "." + colName;
        }
        return tab;
    }

    /**
     * 说明：返回当年第几周（字段）
     * 
	 * @param tabName
	 *            表名或别名
	 * @param colName
	 *            列名
     * @return String
     */

    public static String getWeekOfYear(String tabName, String colName) {

        String tab = getTableStr(tabName, colName);
        String str = "";
        // 值为1 时，为MySql数据库
        if (MySql_TYPE == 1) {
            str = " WEEK_ISO(" + tab + ") ";
            // str = " DATA_FORMAT(" + tab + ")";
        }

        return str;
    }

    /**
     * 说明：返回当年第几周（参数）
     * 
	 * @param day
	 *            日期
     * @return String
     */
    public static String getWeekOfYear(String day) {
        String str = "";
        // 值为1 时，为MySql数据库
        if (MySql_TYPE == 1) {
            str = " WEEK_ISO('" + day + "') ";
            // str = " DATA_FORMAT(" + day + ")";
        }

        return str;
    }

    /**
     * 说明：取月份 针对于数据库字段
     * 
	 * @param tabName
	 *            表名或别名
	 * @param colName
	 *            列名
     * @return 返回拼接好的SQL
	 * @throws Exception
	 *             intercept
     */

    public static String getMonthStr(String tabName, String colName) {
        String str = "";
        if (colName == null || colName.trim().equals("")) {
            return str;
        }
        String tab = getTableStr(tabName, colName);
        // 值为1 时，为MySql数据库
        if (MySql_TYPE == 1) {
            str = " MONTH(" + tab + ") ";

        }
        /*
         * else { str = " EXTRACT(MONTH  FROM " + tab + ") "; }
         */
        return str;
    }

    /**
     * 说明：取月份 针对于参数
     * 
     * @param DateStr
     * @return 返回拼接好的SQL
	 * @throws Exception
	 *             intercept
     */

    public static String getMonthStr(String DateStr) {
        String str = "";
        if (DateStr == null || DateStr.trim().equals("")) {
            return str;
        }
        // 值为1 时，为MySql数据库
        if (MySql_TYPE == 1) {
            str = " MONTH('" + DateStr + "') ";

        }
        return str;
    }

    /**
     * 说明：取年份 针对于数据库字段
     * 
	 * @param tabName
	 *            表名或别名
	 * @param colName
	 *            列名
     * @return 返回拼接好的SQL
	 * @throws Exception
	 *             intercept
     */

    public static String getYearStr(String tabName, String colName) {
        String str = "";
        if (colName == null || colName.trim().equals("")) {
            return str;
        }
        String tab = getTableStr(tabName, colName);

        // 值为1 时，为MySql数据库
        if (MySql_TYPE == 1) {
            str = " YEAR(" + tab + ") ";

        }
        /*
         * else{ str = " EXTRACT(YEAR   FROM " + tab + ") "; }
         */
        return str;
    }

    /**
     * 说明：取年份 针对于参数
     * 
     * @param DateStr
     * @return 返回拼接好的SQL
	 * @throws Exception
	 *             intercept
     */

    public static String getYearStr(String DateStr) {
        String str = "";
        if (DateStr == null || DateStr.trim().equals("")) {
            return str;
        }
        // 值为1 时，为MySql数据库
        if (MySql_TYPE == 1) {
            str = " YEAR('" + DateStr + "') ";

        }
        return str;
    }

    /**
     * 说明：格式化日期型字段(yyyy-MM-dd HH24:mi:ss)
     * 
	 * @param tabName
	 *            表名或别名
	 * @param colName
	 *            列名
     * @return String
     */
    public static String formatDateTimeToChar(String tabName, String colName) {
        String tab = getTableStr(tabName, colName);
        String str = "";

        if (MySql_TYPE == 1) {
            str = " varchar(" + tab + ") ";
        }
        return str;
    }

    /**
     * 判断是否为空或NULL
     * 
     * @param index
     * @return
     * @throws Exception
     */
    public static boolean testString(String src) {
		if (src != null && !"".equals(src.trim()))
			return true;
        return false;
    }

    /**
     * 判断是否为空或NULL
     * 
     * @param index
     * @return
     * @throws Exception
     */
    public static boolean testString(Long long1) {
        if (long1 != null && !"".equals(long1)) {
            return true;
        }
        return false;
    }

    /**
     * 判断是否为空或NULL
     * 
     * @param index
     * @return
     * @throws Exception
     */
    public static boolean testString(Double double1) {
        if (double1 != null && !"".equals(double1)) {
            return true;
        }
        return false;
    }

    /**
     * 判断是否为空或NULL
     * 
     * @param index
     * @return
     * @throws Exception
     */
    public static boolean testString(Integer integer) {
        if (integer != null && !"".equals(integer)) {
            return true;
        }
        return false;
    }

    /**
     * 判断是否为空或NULL
     * 
     * @param index
     * @return
     * @throws Exception
     */
    public static boolean testString(Date date) {
        if (date != null && !"".equals(date)) {
            return true;
        }
        return false;
    }

    public static java.util.Date getCurrentDateTime() throws ParseException {
        Date now = new Date(System.currentTimeMillis());
        return parseString2Date(sdf.format(now), null);
    }

    /**
     * string转换成date
     * 
     * @param index
     * @return
     * @throws Exception
     */
    public static  java.util.Date parseString2Date(String dateStr, String dateFormat) throws ParseException {

        if (null == dateStr || dateStr.length() <= 0)
            return null;

        if (null == dateFormat || dateFormat.length() <= 0)
            dateFormat = FULL_DATE_FORMAT;

        DateFormat formatter = new SimpleDateFormat(dateFormat);
        java.util.Date date = formatter.parse(dateStr);
        return date;
    }

    public static List<Map> getOwnerSubclassList(String entityCode, List<Map> list) {
		if (list == null)
			return null;

        Map po = null;
        for (int i = 0; i < list.size(); i++) {
            po = list.get(i);
            String sql = "SELECT * FROM TM_OWNER_SUBCLASS WHERE DEALER_CODE = '" + entityCode + "' AND MAIN_ENTITY = '"
                         + po.get("DEALER_CODE") + "' AND OWNER_NO = '" + po.get("OWNER_NO") + "'";
            Map poSub = DAOUtil.findFirst(sql, null);

            if (poSub != null) {
				if (isPrivateField(entityCode, "TM_OWNER", "PRE_PAY"))
					po.put("PRE_PAY", poSub.get("PRE_PAY"));
                if (isPrivateField(entityCode, "TM_OWNER", "ARREARAGE_AMOUNT"))
                    po.put("ARREARAGE_AMOUNT", poSub.get("ARREARAGE_AMOUNT"));
            }
        }
        return list;
    }

    public static boolean isPrivateField(String entityCode, String tableName, String fieldName) {
        if (entityCode == null || entityCode.equals("") || tableName == null || tableName.equals("")
				|| fieldName == null || fieldName.equals(""))
			return false;

        if (entityPrivateFieldMapping == null) {
            entityPrivateFieldMapping = new HashMap();
            Map po = new HashMap();
            String sql = "SELECT * FROM TM_ENTITY_PRIVATE_FIELD WHERE IS_VALID = " + DictCodeConstants.IS_YES;
            List<Map> list = DAOUtil.findAll(sql, null);

            if (list != null && list.size() > 0) {
                for (int i = 0; i < list.size(); i++) {
                    po = list.get(i);
                    entityPrivateFieldMapping.put(po.get("DEALER_CODE").toString() + po.get("TABLE_NAME").toString()
                                                  + po.get("PRIVATE_FIELD").toString(), po.get("IS_VALID").toString());
                }
            }
        }

        if (entityPrivateFieldMapping.containsKey(entityCode + tableName + fieldName)) {
            return true;
        } else {
            return false;
        }
    }

    public static List<Map> getVehicleSubclassList(String entityCode, List<Map> list) {
		if (list == null)
			return null;

        Map po = null;
        for (int i = 0; i < list.size(); i++) {
            po = list.get(i);
            Map poSub = new HashMap();
            String sql = "SELECT * FROM TM_VEHICLE_SUBCLASS WHERE DEALER_CODE = '" + entityCode
                         + "' AND MAIN_ENTITY = '" + po.get("DEALER_CODE").toString() + "' AND VIN = '"
                         + po.get("VIN").toString() + "'";
            poSub = DAOUtil.findFirst(sql, null);

            if (poSub != null) {
                if (isPrivateField(entityCode, "TM_VEHICLE", "CONSULTANT"))
                    po.put("CONSULTANT", poSub.get("CONSULTANT"));
                if (isPrivateField(entityCode, "TM_VEHICLE", "IS_SELF_COMPANY"))
                    po.put("IS_SELF_COMPANY", poSub.get("IS_SELF_COMPANY"));
                if (isPrivateField(entityCode, "TM_VEHICLE", "FIRST_IN_DATE"))
                    po.put("FIRST_IN_DATE", poSub.get("FIRST_IN_DATE"));
                if (isPrivateField(entityCode, "TM_VEHICLE", "CHIEF_TECHNICIAN"))
                    po.put("CHIEF_TECHNICIAN", poSub.get("CHIEF_TECHNICIAN"));
                if (isPrivateField(entityCode, "TM_VEHICLE", "SERVICE_ADVISOR"))
                    po.put("SERVICE_ADVISOR", poSub.get("SERVICE_ADVISOR"));
                if (isPrivateField(entityCode, "TM_VEHICLE", "INSURANCE_ADVISOR"))
                    po.put("INSURANCE_ADVISOR", poSub.get("INSURANCE_ADVISOR"));
                if (isPrivateField(entityCode, "TM_VEHICLE", "MAINTAIN_ADVISOR"))
                    po.put("MAINTAIN_ADVISOR", poSub.get("MAINTAIN_ADVISOR"));
                if (isPrivateField(entityCode, "TM_VEHICLE", "LAST_MAINTAIN_DATE"))
                    po.put("LAST_MAINTAIN_DATE", poSub.get("LAST_MAINTAIN_DATE"));
                if (isPrivateField(entityCode, "TM_VEHICLE", "LAST_MAINTAIN_MILEAGE"))
                    po.put("LAST_MAINTAIN_MILEAGE", poSub.get("LAST_MAINTAIN_MILEAGE"));
                if (isPrivateField(entityCode, "TM_VEHICLE", "LAST_MAINTENANCE_DATE"))
                    po.put("LAST_MAINTENANCE_DATE", poSub.get("LAST_MAINTENANCE_DATE"));
                if (isPrivateField(entityCode, "TM_VEHICLE", "LAST_MAINTENANCE_MILEAGE"))
                    po.put("LAST_MAINTENANCE_MILEAGE", poSub.get("LAST_MAINTENANCE_MILEAGE"));
				if (isPrivateField(entityCode, "TM_VEHICLE", "PRE_PAY"))
					po.put("PRE_PAY", poSub.get("PRE_PAY"));
                if (isPrivateField(entityCode, "TM_VEHICLE", "ARREARAGE_AMOUNT"))
                    po.put("ARREARAGE_AMOUNT", poSub.get("ARREARAGE_AMOUNT"));
                if (isPrivateField(entityCode, "TM_VEHICLE", "DISCOUNT_EXPIRE_DATE"))
                    po.put("DISCOUNT_EXPIRE_DATE", poSub.get("DISCOUNT_EXPIRE_DATE"));
                if (isPrivateField(entityCode, "TM_VEHICLE", "DISCOUNT_MODE_CODE"))
                    po.put("DISCOUNT_MODE_CODE", poSub.get("DISCOUNT_MODE_CODE"));
                if (isPrivateField(entityCode, "TM_VEHICLE", "IS_SELF_COMPANY_INSURANCE"))
                    po.put("IS_SELF_COMPANY_INSURANCE", poSub.get("IS_SELF_COMPANY_INSURANCE"));
                if (isPrivateField(entityCode, "TM_VEHICLE", "ADJUST_DATE"))
                    po.put("ADJUST_DATE", poSub.get("ADJUST_DATE"));
				if (isPrivateField(entityCode, "TM_VEHICLE", "ADJUSTER"))
					po.put("ADJUSTER", poSub.get("ADJUSTER"));
				if (isPrivateField(entityCode, "TM_VEHICLE", "IS_VALID"))
					po.put("IS_VALID", poSub.get("IS_VALID"));
                if (isPrivateField(entityCode, "TM_VEHICLE", "NO_VALID_REASON"))
                    po.put("NO_VALID_REASON", poSub.get("NO_VALID_REASON"));
            }
        }
        return list;
    }

    /**
     * 空字符串转换成数字
     * 
     * @param tabName
     * @param colName
     * @param values
     * @return
     */
    public static String getChangeNull(String tabName, String colName, int values) {
        String tab = getTableStr(tabName, colName);
        String str = "";
        str = " ifnull(" + tab + ", " + values + ") ";
        return str;
    }

    /**
     * 说明：其他特殊情况
     * 
	 * @param db2sqlStr
	 *            DB2 sql
	 * @param oracleSqlStr
	 *            ORACLE sql
     * @return String
     */
    public static String gerSpecialSql(String db2sqlStr, String oracleSqlStr) {

        // 值为1 时，为MySql数据库
        if (MySql_TYPE == 1) {
            return db2sqlStr;
        } else {
            return oracleSqlStr;
        }

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
     * 字段过滤，只保留纯数字类型，若小于零则为零
     * 
     * @param buyCarBugget
     * @return
     * @throws Exception
     */
    public static String StringFilter(String str) throws Exception {
        String num = str.replaceAll("[^0-9]", "");
        if (Utility.testString(num) && Utility.getLong(num) < 0) {
            return "0";
        } else {
            return num.trim();
        }
    }

    public static Timestamp getCurrentTimestamp() throws Exception {
        Date now = new Date(System.currentTimeMillis());
        // DateFormat gmt08Formatter = DateFormat.getDateTimeInstance();
        // TimeZone timezone = TimeZone.getTimeZone("GMT+08:00");
        // gmt08Formatter.setTimeZone(timezone);
        // String gmt08DateTime = gmt08Formatter.format(now);
        // return new Timestamp(parseString2Date(gmt08DateTime,
        // null).getTime());
        System.out.println("..............." + now);
        return new Timestamp(parseString2Date(sdf.format(now), null).getTime());

    }

    /**
     * (模糊查询)通过传入的表名和列名(string类型)生成条件内容
     * 
     * @param tabName
     * @param colName
     * @return
     */
    public static String getStrLikeCond(String tabName, String colName, String colValue) {

        if (colValue == null || colValue.trim().equals("")) {
            return "";
        } else {
            if (tabName == null || tabName.trim().equals("")) {
                return " AND " + colName + " LIKE '%" + colValue + "%'";
            } else {
                return " AND " + tabName + "." + colName + " LIKE '%" + colValue + "%'";
            }
        }

    }

    /**
     * 加锁用户
     * 
     * @author yangjie
	 * @param tabName
	 *            表名
	 * @param empNo
	 *            userId
	 * @param noName
	 *            业务单据号的字段名
	 * @param noValue
	 *            业务单据号的单号值
	 * @param lockName
	 *            锁定人字段
     * @return 1:成功,0:失败
     * @throws ServiceBizException
     */
	public static int updateByLocker(String tabName, String empNo, String noName, String noValue, String lockName) {
		StringBuilder sql = new StringBuilder().append("UPDATE ").append(tabName).append(" SET ").append(lockName)
				.append(" = ?,UPDATED_AT = ?,UPDATED_BY = ?");
		sql.append(" WHERE ").append(noName).append(" = ? AND DEALER_CODE = ? AND (").append(lockName)
				.append(" IS NULL OR "+lockName+"='' OR ").append(lockName).append(" = ? )");
        Object[] allParams = new Object[6];
        allParams[0] = empNo;
        allParams[1] = new Timestamp(System.currentTimeMillis());
        allParams[2] = FrameworkUtil.getLoginInfo().getUserId();
        allParams[3] = noValue;
        allParams[4] = FrameworkUtil.getLoginInfo().getDealerCode();
        allParams[5] = empNo;
        int count = Base.exec(sql.toString(), allParams);
        return count;
    }

    /**
     * 解锁用户
     * 
     * @author yangjie
	 * @param tabName
	 *            表名
	 * @param empNo
	 *            userId
	 * @param noName
	 *            业务单据号的字段名
	 * @param noValue
	 *            业务单据号的单号值
	 * @param lockName
	 *            锁定人字段
     * @return 返回解锁失败的单号值
     * @throws ServiceBizException
     */
	public static String updateByUnLock(String tabName, String empNo, String noName, String[] noValue, String lockName)
			throws ServiceBizException {
        String flag = "";
        for (int i = 0; i < noValue.length; i++) {
			StringBuilder sql = new StringBuilder().append("UPDATE ").append(tabName).append(" SET ").append(lockName)
					.append(" = ?,UPDATED_AT = ?,UPDATED_BY = ?");
			sql.append(" WHERE ").append(noName).append(" = ? AND DEALER_CODE = ? AND (").append(lockName)
					.append(" IS NULL OR ").append(lockName).append(" = ? )");
            Object[] allParams = new Object[6];
            allParams[0] = null;
            allParams[1] = new Timestamp(System.currentTimeMillis());
            allParams[2] = FrameworkUtil.getLoginInfo().getUserId();
            allParams[3] = noValue[i];
            allParams[4] = FrameworkUtil.getLoginInfo().getDealerCode();
            allParams[5] = empNo;
            int count = Base.exec(sql.toString(), allParams);
            if (count > 0) {
            } else {
                if (i != noValue.length - 1) {
                    flag += noValue[i] + ",";
                } else {
                    flag += noValue[i];
                }
            }
        }
        return flag;
    }

    /**
     * 功能描述：查询锁定用户
     * 
	 * @param lockName
	 *            锁定人字段
	 * @param tabName
	 *            表名
	 * @param noName
	 *            业务单据号的字段名
	 * @param noValue
	 *            业务单据号的单号值
     * @return
     */
    public static String selLockerName(String lockName, String tabName, String noName, String noValue) {
        String userId = "";
        StringBuilder sb = new StringBuilder();
		sb.append("SELECT DEALER_CODE,").append(lockName).append(" FROM ").append(tabName).append(" WHERE ")
				.append(noName).append("='").append(noValue).append("' AND DEALER_CODE = ?");
        List queryParam = new ArrayList();
        queryParam.add(FrameworkUtil.getLoginInfo().getDealerCode());
        List<Map> list = DAOUtil.findAll(sb.toString(), queryParam);
        if (list.size() > 0) {
            for (Map map : list) {
            	if(!com.yonyou.dms.function.utils.common.StringUtils.isNullOrEmpty(map.get(lockName))){
            		userId = map.get(lockName).toString();
            	}
            }
        }
        return userId;
    }

    /**
     * 获取当前年份
     * 
     * @return
     */
    public static String getYear() {
        Calendar calendar = new GregorianCalendar();
        int year = calendar.get(java.util.Calendar.YEAR);
        return (new Integer(year)).toString();
    }

    /**
     * Method 得到当前月(两位数)
     * 
     * @return String
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
     * Method 得到当前月（一位数）
     * 
     * @return int
     */
    public static int getMonthNoZero() {
        Calendar calendar = new GregorianCalendar();
        int month = calendar.get(java.util.Calendar.MONTH) + 1;
        return month;
    }

    /**
     * TODO description
     * 
     * @author Administrator
     * @date 2017年4月6日
     * @param dealerCode
     * @param string
     * @param string2
     * @return
     */

    public static String getShareEntityCon(String dealerCode, String bizCode, String tableName) {
        StringBuffer sql = new StringBuffer();
        String fieldName = "DEALER_CODE";

        if (dealerCode == null || dealerCode.trim().equals("") || bizCode == null || bizCode.trim().equals("")) {
            return sql.append(" 1 = 1 ").toString();
        }
        if (tableName != null && !tableName.trim().equals("")) {
            fieldName = tableName + "." + fieldName;
            return sql.append(fieldName).toString();
        }
        return sql.toString();
    }

    // 检查非空
    public static String checkNull(Object obj) {
		if (obj != null)
			return obj.toString().trim();
		else
			return "";
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
            throw new ServiceBizException("精确小数点后几位必须为正数或者0");
        }
        BigDecimal b1 = new BigDecimal(v1);
        BigDecimal b2 = new BigDecimal(v2);
        return b1.divide(b2, scale, BigDecimal.ROUND_HALF_UP).doubleValue();
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
     * 根据用户ID查询出他能操作的仓库 USERID FUNCIONCODE TABNAME
     */
    public static String getStorageByUserId(Long userId) {
        String str = "";
        StringBuffer sb = new StringBuffer();
		sb.append(" SELECT CTRL_CODE,DEALER_CODE FROM TM_USER_CTRL WHERE DEALER_CODE = '")
				.append(FrameworkUtil.getLoginInfo().getDealerCode())
				.append("' AND CTRL_CODE LIKE '4010%' AND USER_ID = ").append(userId);
        List<Map> list = DAOUtil.findAll(sb.toString(), null);
        if (list.size() > 0) {
            for (int i = 0; i < list.size(); i++) {
                if (i > 0) {
                    str = str + ",";
                }

				str = str + "'" + list.get(i).get("CTRL_CODE").toString().substring(4,
						list.get(i).get("CTRL_CODE").toString().length()) + "'";
            }
        }
		if ("".equals(str))
			return "''";
		else
			return str;
    }

    /**
     * 查询本月的报表是否完成
     * 
     * @return
     */
    public static List<Map> isFinishedThisMonth() {
        StringBuffer sb = new StringBuffer();
        sb.append("SELECT DEALER_CODE,REPORT_YEAR FROM TT_MONTH_CYCLE ");
        sb.append("WHERE REPORT_YEAR = '" + Utility.getYear() + "' ");
        sb.append("AND REPORT_MONTH = '" + Utility.getMonth() + "' ");
        return DAOUtil.findAll(sb.toString(), null);
    }

    /**
     * 查询当前时间的会计周期是否做过月结
     * 
     * @return
     */
    public static List<Map> getIsFinished() {
        StringBuffer sb = new StringBuffer();
        sb.append("SELECT DEALER_CODE,B_YEAR,PERIODS,BEGIN_DATE,END_DATE,IS_EXECUTED ");
        sb.append(" FROM TM_ACCOUNTING_CYCLE WHERE ");
        sb.append("CURRENT_TIMESTAMP BETWEEN BEGIN_DATE AND END_DATE");
        return DAOUtil.findAll(sb.toString(), null);
    }
    /**
     * 返回值：当前日期减去输入日期的天数
    * TODO description
    * @author yujiangheng
    * @date 2017年5月5日
    * @param FormatBackDate
    * @return
    * @throws Exception
     */
    public static int TodayCompareFormatDate(String FormatBackDate) throws Exception{
        try
        {
            String snowDate =null;
            String sFormatBackDate=null;
            java.util.Date nowDate =null;
            nowDate = new java.util.Date();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            snowDate=sdf.format(nowDate);
            sFormatBackDate=sdf.format(Utility.getTimeStamp(FormatBackDate));
            return snowDate.compareTo(sFormatBackDate);
        }
        catch(Exception e)
        {
            return 0;
        }
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
     * 判断一个int值是几位数
     * 
     * @author zhanshiwei
     * @date 2017年4月8日
     * @param x
     * @return
     */
    final static int[] sizeTable = { 9, 99, 999, 9999, 99999, 999999, 9999999, 99999999, 999999999, Integer.MAX_VALUE };

    public static int sizeOfInt(int x) {
        for (int i = 0;; i++)
			if (x <= sizeTable[i])
				return i + 1;
	}

	/**
	 * 日期查询OR条件
	 * 
	 * @param tabName
	 * @param colName
	 * @param colValue
	 * @param cond
	 * @return
	 */
	public static String getDateCondsOR(String tabName, String colName, String colValue, String cond) {
		String tab = "";
		String str = "";
		if (colValue == null || colValue.trim().equals("")) {
			return "";
		}
		if (tabName == null || tabName.trim().equals("")) {

			tab = colName;
		} else {
			tab = tabName + "." + colName;
		}

		if (">".equals(cond.trim()) || "<=".equals(cond.trim())) {
			str = " OR " + tab + cond + "'" + (colValue.indexOf(":") == -1 ? colValue + " 23:59:59" : colValue) + "'";
		}
		if ("=".equals(cond.trim())) {
			if (colValue.indexOf(":") == -1) {
				str = " OR( " + tab + " >= '" + colValue + " 00:00:00'" + " AND " + tab + " <= '" + colValue
						+ " 23:59:59')";
			} else {
				str = " OR " + tab + " = '" + colValue + "'";
			}

		}
		if ("<".equals(cond.trim()) || ">=".equals(cond.trim())) {
			str = " OR " + tab + cond + "'" + (colValue.indexOf(":") == -1 ? colValue + " 00:00:00" : colValue) + "'";
		}

		return str;

	}

	/**
	 * 通过传入的表名、列名(date类型)和判断条件（>,<等符号）生成条件内容
	 * 
	 * @param tabName
	 * @param colValue
	 * @param colName
	 * @return
	 * @throws Exception
	 */
	public static String getDateConds(String tabName, String colName, String colValue, String cond) {
		String tab = "";
		String str = "";
		if (colValue == null || colValue.trim().equals("")) {
			return "";
		}
		if (tabName == null || tabName.trim().equals("")) {

			tab = colName;
		} else {
			tab = tabName + "." + colName;
		}

		if (">".equals(cond.trim()) || "<=".equals(cond.trim())) {
			str = " AND " + tab + cond + "'" + (colValue.indexOf(":") == -1 ? colValue + " 23:59:59" : colValue) + "'";
		}
		if ("=".equals(cond.trim())) {
			if (colValue.indexOf(":") == -1) {
				str = " AND " + tab + " >= '" + colValue + " 00:00:00'" + " AND " + tab + " <= '" + colValue
						+ " 23:59:59'";
			} else {
				str = " AND " + tab + " = '" + colValue + "'";
			}

		}
		if ("<".equals(cond.trim()) || ">=".equals(cond.trim())) {
			str = " AND " + tab + cond + "'" + (colValue.indexOf(":") == -1 ? colValue + " 00:00:00" : colValue) + "'";
		}

		return str;

	}

	/**
	 * 将时间戳转换为日期格式
	 * 
	 * @author zhengcong
	 * @date 2017年5月4日
	 * @param s
	 * @return
	 */
	public static String stampToDate(String s) {
		String res;
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		long lt = new Long(s);
		Date date = new Date(lt);
		res = simpleDateFormat.format(date);
		return res;

	}

	/**
	 * 查询OEM仓库出库配件
	 * 
	 * @param conn
	 * @param dealerCode
	 * @return
	 * @throws SQLException
	 */
	public static String getIsOEMPartOutCheck() throws SQLException {
		String defaultValue = "12781002";
		defaultValue = Utility.getDefaultValue(CommonConstants.DEFAULT_PARA_OEM_PART_OUT_CHECK);
		return defaultValue;
	}

	/**
	 * 配件税率
	 * @param dealerCode
	 * @return
	 * @throws SQLException
	 */
	public static String getPartRate(String dealerCode) throws SQLException {
		String defaultValue = "";
		defaultValue = Utility.getDefaultValue(Integer.toString(CommonConstants.DEFAULT_PARA_PART_RATE));
		return defaultValue;
    }

	/**
	 * @description 判断是否允许负库存
	 * @param dealerCode
	 * @param object
	 * @return
	 * @throws Exception 
	 * @throws NumberFormatException 
	 */
	public static boolean checkNegative(String dealerCode, String storageCode) throws NumberFormatException, Exception {
		StringBuffer sb = new StringBuffer();
		sb.append("select * from tm_storage where dealer_code = "+ dealerCode + " and Storage_Code = '" + storageCode+"'");
		List<Map> list = DAOUtil.findAll(sb.toString(), null);
		if (list == null || list.size() < 1)
		{ // modifidy by sf 2011-02-15 FOR DXP
			return false;// 不允许
		}
		Map map = list.get(0);
		if (Utility.getInt(CommonConstants.DICT_IS_NO) == Integer.parseInt(map.get("Is_Negative").toString())){
			// 不允许负库存
			return false;
		}
		return true;
	}
	
	/**
	 * 对数据库中查找的索赔单号进行验证
	 * @param str
	 * @return
	 */
	public static String getRealNumber(String str) {

		// 如果以前没有索赔单号，那么从100000开始
		if (str == null || "".equals(str)) {
			System.out.println("以前没有索赔单号。。。从Z00010开始生成");
			return "Z00010";
			// 校验索赔单号是否规范，可能有遗漏的数据，如果不规范，那么重0开始
		}
		Pattern patn = Pattern.compile("[\\d||Z]\\d\\d\\d\\d0");
		Matcher mt = patn.matcher(str);
		if (!mt.matches()) {
			System.out
					.println("校验索赔单号是否规范，可能有遗漏的数据，如果不规范，那么重Z00010开始。。。从Z00010开始生成");
			return "Z00010";
		}
		// 如果超过了Z99990，那么要重新开始
		else if (str != null && "999990".equals(str)) {
			// 校验索赔单号是是否到顶了，那么要重新开始1000000
			System.out.println("校验索赔单号是是否到顶了，那么要重新开始Z00010");
			return "Z00010";
		} else {
			return getSixNumber(str);
		}
	}
	
	/**
	 * 生成6位索赔单号
	 * @param str
	 * @return
	 */
	public static String getSixNumber(String str) {
		StringBuilder sb = new StringBuilder();
		// 100000
		// 头一位
		String firstNum = str.substring(0, 1);
		char[] fn = firstNum.toCharArray();
		int fn1 = (int) fn[0];
		// 中间4位
		String cenFourNum = str.substring(1, 5);
		// 中间4位+1
		String num = getFourNumber(Integer.parseInt(cenFourNum));
		if (num != null && "0001".equals(num)) {
			// 并且判断如果头一位是在1~8之间，那么进一位，如果是9那么进一位变为Z
			if (fn1 >= 49 && fn1 <= 56) {
				fn1 += 1;
			} else if (fn1 == 90) {
				fn1 = 49;
			} else if (fn1 == 57) {
				fn1 = 90;
			}
		}
		sb.append((char) fn1);
		sb.append(num);
		sb.append(0);
		return sb.toString();
	}
	
	/**
	 * 生产4位编吧0000~9999
	 * @param num
	 * @return
	 */
	public static String getFourNumber(int num) {
		num++;
		String result = "";
		switch ((num + "").length()) {
		case 1:
			result = "000" + num;
			break;
		case 2:
			result = "00" + num;
			break;
		case 3:
			result = "0" + num;
			break;
		case 4:
			result = "" + num;
			break;
		// 此处代表编号已经超过了9999，从0重新开始
		default:
			result = "0001";
			break;
		}
		return result;
	}
	
}
