package com.yonyou.dms.framework.DAO;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.yonyou.dms.framework.domains.DTO.baseData.VehicleInfoDTO;
import com.yonyou.dms.function.common.DictCodeConstants;
import com.yonyou.dms.function.common.OemDictCodeConstants;
import com.yonyou.dms.function.exception.ServiceBizException;
import com.yonyou.dms.function.utils.common.CommonUtils;
import com.yonyou.dms.function.utils.common.StringUtils;

/**
 * 备用公共DAO
 * 
 * @author 夏威
 *
 */
@SuppressWarnings("rawtypes")
public class OemBaseDAO {

	/**
	 * 权限控制公共方法
	 */
	public String control(String sql, String dealerSeriesIDs, String postSeriesIDs) {
		String control = "";
		if (null != dealerSeriesIDs && dealerSeriesIDs.length() > 0 && null != postSeriesIDs
				&& postSeriesIDs.length() > 0) {
			String[] dealerSeriesID = dealerSeriesIDs.split(",");
			String[] postSeriesID = postSeriesIDs.split(",");
			String ids = "";
			for (int i = 0; i < dealerSeriesID.length; i++) {
				for (int j = 0; j < postSeriesID.length; j++) {
					if (dealerSeriesID[i].equals(postSeriesID[j])) {
						if ("".equals(ids)) {
							ids = dealerSeriesID[i];
						} else {
							ids = ids + "," + dealerSeriesID[i];
						}
					}
				}
			}
			if (!"".equals(ids)) {
				control = " AND " + sql + " in ( " + ids + " ) \n";
			}
		} else {
			// 经销商权限
			if (null != dealerSeriesIDs && dealerSeriesIDs.length() > 0) {
				control = " AND " + sql + " in ( " + dealerSeriesIDs + " ) \n";
			}
			// 职位权限
			if (null != postSeriesIDs && postSeriesIDs.length() > 0) {
				control = control + " AND " + sql + " in ( " + postSeriesIDs + " ) ";
			}
		}
		return control;
	}

	/**
	 * 生成订单号 订单号规则：经销商代码+年+月+日+序列号(从001开始递增)
	 */
	public static String getOrderNO(String dealerCode) {

		StringBuffer orderNO = new StringBuffer();

		Calendar calendar = Calendar.getInstance();
		int year_ = calendar.get(calendar.YEAR);
		String year = year_ + "";
		int month_ = calendar.get(calendar.MONTH) + 1;
		String month = month_ + "";
		if (month.length() < 2) {
			month = month.format("0" + month, month);
		}
		int day_ = calendar.get(calendar.DAY_OF_MONTH);
		String day = day_ + "";
		if (day.length() < 2) {
			day = day.format("0" + day, day);
		}
		/*
		 * 查询数据库中是否已有相关最大订单号，如果有，新订单号=原订单号+1 否则新生成订单号
		 */
		Map<String, String> oldOrderNOMap = getMaxOrderSeq(dealerCode, year, month, day);
		String oldOrderNO = oldOrderNOMap.get("ORDER_NO");
		if (null != oldOrderNO && !"".equals(oldOrderNO)) {
			String commonNO = oldOrderNO.substring(0, oldOrderNO.length() - 4); // 1得到订单序列号前的号码
			String indexNO = oldOrderNO.substring(oldOrderNO.length() - 4, oldOrderNO.length()); // 2.得到订单序列号
			int index_res = Integer.parseInt(indexNO) + 1; // 3.将订单序列号+1
			String index_res_ = index_res + ""; // 4.如果订单序列号不足三位，用0进行填补
			StringBuffer noBuffer = new StringBuffer();
			int index_res_length = 4 - index_res_.length();
			for (int i = 0; i < index_res_length; i++) {
				noBuffer.append("0");
			}
			noBuffer.append(index_res_);
			orderNO.append(commonNO).append(noBuffer); // 5.将从数据中查询到的订单号+1后，赋给orderNO
			return orderNO.toString();
		} else {
			orderNO.append(dealerCode);
			return orderNO.append(year).append(month).append(day).append("0001").toString();
		}
	}

	/**
	 * 查询最大订单号
	 */
	public static Map<String, String> getMaxOrderSeq(String dealerCode, String year, String month, String day) {

		StringBuffer orderNOBuffer = new StringBuffer();
		orderNOBuffer.append(dealerCode).append(year).append(month).append(day);

		StringBuffer sql = new StringBuffer();
		sql.append("SELECT MAX(TTO.ORDER_NO) ORDER_NO FROM TT_VS_ORDER TTO WHERE TTO.ORDER_NO LIKE '" + orderNOBuffer
				+ "%'\n");
		return OemDAOUtil.findFirst(sql.toString(), null);
	}

	public String getFunName() {
		StackTraceElement stack[] = new Throwable().getStackTrace();
		StackTraceElement ste = stack[1];
		StringBuilder strBuilder = new StringBuilder();
		return strBuilder.append(ste.getClassName()).append(".").append(ste.getMethodName()).append(ste.getLineNumber())
				.toString();
	}

	@SuppressWarnings("unchecked")
	public List<Map<String, Object>> pageQuery(String sql, List<Object> queryParam, final String funName) {
		// logger.info(sql);
		List<Map> tmp = OemDAOUtil.findAll(sql, queryParam);
		LinkedList<Map<String, Object>> ret = new LinkedList<Map<String, Object>>();
		for (Map<String, Object> bean : tmp) {
			ret.addLast((Map<String, Object>) bean);
		}
		return ret;
	}

	/**
	 * 
	 * 执行批处理操作，获取物料视图SQL
	 * 
	 * @author xiawei
	 * @date 2017年2月14日
	 * @param sql
	 * @param paramterMap
	 */
	public static String getVwMaterialSql() {
		StringBuffer sql = new StringBuffer();
		sql.append(
				" SELECT MG1.GROUP_ID AS BRAND_ID,         MG1.GROUP_CODE AS BRAND_CODE,         MG1.GROUP_NAME AS BRAND_NAME,               MG1.STATUS AS BRAND_STATUS,         MG2.GROUP_ID AS SERIES_ID,         MG2.GROUP_CODE AS SERIES_CODE,               MG2.GROUP_NAME AS SERIES_NAME,         MG2.STATUS AS SERIES_STATUS,         MG2.GROUP_TYPE AS GROUP_TYPE,               MG3.GROUP_ID AS MODEL_ID,         MG3.GROUP_CODE AS MODEL_CODE,         MG3.GROUP_NAME AS MODEL_NAME,               MG3.STATUS AS MODEL_STATUS,         MG3.WX_ENGINE AS WX_ENGINE,           MG3.OILE_TYPE AS OILE_TYPE,                 MG4.GROUP_ID AS GROUP_ID,         MG4.GROUP_CODE AS GROUP_CODE,         MG4.GROUP_NAME AS GROUP_NAME,               MG4.STATUS AS GROUP_STATUS,         MG4.MODEL_YEAR AS MODEL_YEAR,         MG4.FACTORY_OPTIONS AS FACTORY_OPTIONS,               MG4.STANDARD_OPTION AS STANDARD_OPTION,         MG4.LOCAL_OPTION AS LOCAL_OPTION,               MG4.SPECIAL_SERIE_CODE AS SPECIAL_SERIE_CODE,         M.TRIM_CODE,         M.TRIM_NAME,         M.COLOR_CODE,               M.COLOR_NAME,         M.MATERIAL_ID,         M.MATERIAL_CODE,         M.MATERIAL_NAME,         M.IS_SALES,           MG3.MVS AS MVS      ");
		sql.append(
				" FROM TM_VHCL_MATERIAL M,         TM_VHCL_MATERIAL_GROUP_R MGR,         TM_VHCL_MATERIAL_GROUP MG4,         TM_VHCL_MATERIAL_GROUP MG3,         TM_VHCL_MATERIAL_GROUP MG2,         TM_VHCL_MATERIAL_GROUP MG1             ");
		sql.append(
				" WHERE M.MATERIAL_ID = MGR.MATERIAL_ID     AND MGR.GROUP_ID = MG4.GROUP_ID    AND MG4.PARENT_GROUP_ID = MG3.GROUP_ID     AND MG3.PARENT_GROUP_ID = MG2.GROUP_ID             AND MG2.PARENT_GROUP_ID = MG1.GROUP_ID     AND M.COMPANY_ID = 2010010100070674 ");
		return sql.toString();
	}

	/**
	 * 取经销商信息
	 * 
	 * @param orderId
	 * @return
	 */
	public static Map getDealerScope(String orderId) {
		List<Object> params = new ArrayList<>();
		String sql = "select ORG_LEVEL,BUSS_TYPE from tm_org" + " where ORG_ID = ? ";
		params.add(orderId);
		return OemDAOUtil.findFirst(sql, params);
	}

	/**
	 * 取销售公司信息
	 * 
	 * @param orderId
	 * @return
	 */
	public static List getDealerIdXsgs(String orderId) {
		List<Object> params = new ArrayList<>();
		String sql = "select TMD.DEALER_ID"
				+ "	FROM TM_ORG TM, TM_ORG TMO,TM_ORG TMOR,TM_DEALER_ORG_RELATION TDOR,TM_DEALER TMD " + "	WHERE 1=1 "
				+ "	AND TMO.PARENT_ORG_ID = TM.ORG_ID" + "	AND TMOR.PARENT_ORG_ID = TMO.ORG_ID"
				+ "	AND TDOR.ORG_ID = TMOR.ORG_ID" + "	AND TMD.DEALER_ID = TDOR.DEALER_ID" + "   AND TMD.STATUS = "
				+ DictCodeConstants.STATUS_ENABLE + "	AND TM.ORG_ID = ?" + "	order by TMO.ORG_ID,TMOR.ORG_ID";
		params.add(orderId);
		return OemDAOUtil.findAll(sql, params);
	}

	/**
	 * 取大区信息
	 * 
	 * @param orderId
	 * @return
	 */
	public static List getDealerIdDq(String orderId) {
		List<Object> params = new ArrayList<>();
		String sql = "select TMD.DEALER_ID" + "	FROM TM_ORG TMO,TM_ORG TMOR,TM_DEALER_ORG_RELATION TDOR,TM_DEALER TMD "
				+ "	WHERE 1=1 " + "	AND TMOR.PARENT_ORG_ID = TMO.ORG_ID" + "	AND TDOR.ORG_ID = TMOR.ORG_ID"
				+ "	AND TMD.DEALER_ID = TDOR.DEALER_ID" + "   AND TMD.STATUS = " + DictCodeConstants.STATUS_ENABLE
				+ "	AND TMO.ORG_ID = ? " + "	order by TMO.ORG_ID,TMOR.ORG_ID";
		params.add(orderId);
		return OemDAOUtil.findAll(sql, params);
	}

	/**
	 * 取小区信息
	 * 
	 * @param orderId
	 * @return
	 */
	public static List getDealerIdxq(String orderId) {
		List<Object> params = new ArrayList<>();
		String sql = "select TMD.DEALER_ID" + "	FROM TM_ORG TMOR,TM_DEALER_ORG_RELATION TDOR,TM_DEALER TMD "
				+ "	WHERE 1=1 " + "	AND TDOR.ORG_ID = TMOR.ORG_ID" + "	AND TMD.DEALER_ID = TDOR.DEALER_ID"
				+ "   AND TMD.STATUS = " + DictCodeConstants.STATUS_ENABLE + "	AND TMOR.ORG_ID = ?"
				+ "	order by TMOR.ORG_ID";
		params.add(orderId);
		return OemDAOUtil.findAll(sql, params);
	}

	public static String getDealersByArea(String orgId) {
		Map dealerScopeMap = getDealerScope(orgId);
		int dealerScope = Integer.parseInt(dealerScopeMap.get("ORG_LEVEL") + "");
		// String buss_type = (String) dealerScopeMap.get("BUSS_TYPE");

		List dealerIdList = new ArrayList();
		if (dealerScope == 1) {// 销售公司
			dealerIdList = getDealerIdXsgs(orgId);
		} else if (dealerScope == 2) {// 大区
			dealerIdList = getDealerIdDq(orgId);
		} else if (dealerScope == 3) {// 小区
			dealerIdList = getDealerIdxq(orgId);
		}
		String dealerIds = "";
		for (int i = 0; i < dealerIdList.size(); i++) {
			Map dealerIdMap = (Map) dealerIdList.get(i);
			dealerIds += dealerIdMap.get("DEALER_ID").toString();
			if (i < dealerIdList.size() - 1) {
				dealerIds += ",";
			}
		}
		return dealerIds;
	}

	/*
	 * 入参： 1.ORDER_NO:要查询的ORDER_NO 2.alias：TT_VS_ORDER表的别名
	 * 如果VIN为17位则用‘=‘，如果不足17位，则用’like' update by luoyg 2015/07/31
	 */
	public static String getOrderNOsAuto(String orderNo, String alias) {
		String returnSql = "";
		if (null != orderNo && !"".equals(orderNo)) {
			orderNo = orderNo.trim();
			// IN查询
			StringBuffer inbuffer = new StringBuffer();
			StringBuffer likeBuffer = new StringBuffer();
			// 对 VIN进行拆分
			String sP = "\\r\\n";
			if (orderNo.indexOf("\r\n") == -1 && orderNo.indexOf("\n") > 0) {
				sP = "\n";
			}
			String[] vins = orderNo.split(sP);
			// 得到orderNo长度：strLength 非17位 ：LIKE查询；17位：IN查询
			// 对orderNo进行重复数据过滤
			Set<String> set = new HashSet<String>();
			Set<String> set2 = new HashSet<String>();
			for (int j = 0; j < vins.length; j++) {
				if (!vins[j].equals("")) {
					if (vins[j].trim().length() == 19) {
						set.add(vins[j].trim());
					} else {
						// set.add(vins[j].trim());
						set2.add(vins[j].trim());
					}
				}
			}
			// 对VIN进行组合(in查询)
			StringBuffer buffer = new StringBuffer();
			Iterator<String> i = set.iterator();
			while (i.hasNext()) {
				String s = i.next().trim();
				buffer.append("'").append(s).append("'").append(",");
			}
			// 对VIN进行组合(like查询)
			StringBuffer buffer2 = new StringBuffer();
			Iterator<String> j = set2.iterator();
			while (j.hasNext()) {
				String s = j.next().trim();
				buffer2.append(alias).append(".ORDER_NO LIKE ").append("'").append("%").append(s).append("%")
						.append("'").append(" OR ");
			}

			if (set.size() == 0 && set2.size() > 0) {
				buffer2 = buffer2.delete(buffer2.length() - 3, buffer2.length());
				returnSql += likeBuffer.append(" AND (").append(buffer2).append(")\n");
			}
			if (set.size() > 0 && set2.size() == 0) {
				buffer = buffer.deleteCharAt(buffer.length() - 1);
				returnSql = inbuffer.append(" AND " + alias).append(".VIN").append(" IN (").append(buffer).append(")\n")
						.toString();
			}
			if (set.size() > 0 && set2.size() > 0) {
				buffer = buffer.deleteCharAt(buffer.length() - 1);
				buffer2 = buffer2.delete(buffer2.length() - 3, buffer2.length());
				returnSql = inbuffer.append(" AND (" + alias).append(".VIN").append(" IN (").append(buffer)
						.append(") OR ").toString();
				returnSql += likeBuffer.append(buffer2).append(") \n");
			}
		}
		return returnSql;
	}

	/*
	 * 入参： 1.SO_NO:要查询的SO_NO 2.alias：TT_VS_ORDER表的别名
	 * 如果VIN为17位则用‘=‘，如果不足17位，则用’like' update by wangxing 2015/09/07
	 */
	public static String getSoNOsAuto(String soNo, String alias) {
		String returnSql = "";
		if (null != soNo && !"".equals(soNo)) {
			soNo = soNo.trim();
			// IN查询
			StringBuffer inbuffer = new StringBuffer();
			StringBuffer likeBuffer = new StringBuffer();
			// 对 VIN进行拆分
			String sP = "\\r\\n";
			if (soNo.indexOf("\r\n") == -1 && soNo.indexOf("\n") > 0) {
				sP = "\n";
			}
			String[] vins = soNo.split(sP);
			// 得到orderNo长度：strLength 非17位 ：LIKE查询；17位：IN查询
			// 对orderNo进行重复数据过滤
			Set<String> set = new HashSet<String>();
			Set<String> set2 = new HashSet<String>();
			for (int j = 0; j < vins.length; j++) {
				if (!vins[j].equals("")) {
					if (vins[j].trim().length() == 10) {
						set.add(vins[j].trim());
					} else {
						// set.add(vins[j].trim());
						set2.add(vins[j].trim());
					}
				}
			}
			// 对VIN进行组合(in查询)
			StringBuffer buffer = new StringBuffer();
			Iterator<String> i = set.iterator();
			while (i.hasNext()) {
				String s = i.next().trim();
				buffer.append("'").append(s).append("'").append(",");
			}
			// 对VIN进行组合(like查询)
			StringBuffer buffer2 = new StringBuffer();
			Iterator<String> j = set2.iterator();
			while (j.hasNext()) {
				String s = j.next().trim();
				buffer2.append(alias).append(".SO_NO LIKE ").append("'").append("%").append(s).append("%").append("'")
						.append(" OR ");
			}

			if (set.size() == 0 && set2.size() > 0) {
				buffer2 = buffer2.delete(buffer2.length() - 3, buffer2.length());
				returnSql += likeBuffer.append(" AND (").append(buffer2).append(")\n");
			}
			if (set.size() > 0 && set2.size() == 0) {
				buffer = buffer.deleteCharAt(buffer.length() - 1);
				returnSql = inbuffer.append(" AND " + alias).append(".SO_NO").append(" IN (").append(buffer)
						.append(")\n").toString();
			}
			if (set.size() > 0 && set2.size() > 0) {
				buffer = buffer.deleteCharAt(buffer.length() - 1);
				buffer2 = buffer2.delete(buffer2.length() - 3, buffer2.length());
				returnSql = inbuffer.append(" AND (" + alias).append(".SO_NO").append(" IN (").append(buffer)
						.append(") OR ").toString();
				returnSql += likeBuffer.append(buffer2).append(") \n");
			}
		}
		return returnSql;
	}

	/*
	 * 入参： 1.vin:要查询的vin 2.alias：TM_VEHICLE表的别名
	 */
	public static String getVins(String vin, String alias) {
		String returnSql = "";
		if (null != vin && !"".equals(vin)) {
			vin = vin.trim();
			// IN查询
			StringBuffer inbuffer = new StringBuffer();
			StringBuffer likeBuffer = new StringBuffer();
			// 对 VIN进行拆分
			String sP = "\\r\\n";
			if (vin.indexOf("\r\n") == -1 && vin.indexOf("\n") > 0) {
				inbuffer.append(" AND " + alias).append(".VIN");
				likeBuffer.append(" AND (");
				sP = "\n";
			} else {
				inbuffer.append("   AND ").append(alias).append(".VIN");
				// LIKE查询

				likeBuffer.append("   AND (");
			}
			String[] vins = vin.split(sP);
			// 得到VIN长度：strLength 非17位 ：LIKE查询；17位：IN查询
			int strLength = vins[0].trim().length();
			// 对VIN进行重复数据过滤
			Set<String> set = new HashSet<String>();
			for (int j = 0; j < vins.length; j++) {
				if (!vins[j].equals("")) {
					set.add(vins[j].trim());
				}
			}
			// 对VIN进行组合
			StringBuffer buffer = new StringBuffer();
			Iterator<String> i = set.iterator();
			while (i.hasNext()) {
				String s = i.next().trim();
				if (strLength == 17) {// 如果是IN查询
					buffer.append("'").append(s).append("'").append(",");
				} else {// 如果是LIKE查询
					buffer.append(alias).append(".VIN LIKE ").append("'").append("%").append(s).append("%").append("'")
							.append(" OR ");
				}
			}
			// 将VIN封装成SQL
			// IN查询
			if (strLength == 17) {
				buffer = buffer.deleteCharAt(buffer.length() - 1);
				returnSql = inbuffer.append(" IN (").append(buffer).append(")\n").toString();
			} else {
				buffer = buffer.delete(buffer.length() - 3, buffer.length());
				likeBuffer.append(buffer).append(")\n");
				returnSql = likeBuffer.toString();
			}
		}
		return returnSql;
	}

	/*
	 * @author lianxinglu 入参： 1.vin:要查询的vin 2.alias：TM_VEHICLE表的别名
	 * 如果VIN为17位则用‘=‘，如果不足17位，则用’like' update by luoyg 2013/11/19
	 */
	public static String getVinsAuto(String vin, String alias) {
		String returnSql = "";
		if (null != vin && !"".equals(vin)) {
			vin = vin.trim();
			// IN查询
			StringBuffer inbuffer = new StringBuffer();
			StringBuffer likeBuffer = new StringBuffer();
			// 对 VIN进行拆分
			String sP = "\\r\\n";
			if (vin.indexOf("\r\n") == -1 && vin.indexOf("\n") > 0) {
				sP = "\n";
			}
			String[] vins = vin.split(sP);
			// 得到VIN长度：strLength 非17位 ：LIKE查询；17位：IN查询
			// 对VIN进行重复数据过滤
			Set<String> set = new HashSet<String>();
			Set<String> set2 = new HashSet<String>();
			for (int j = 0; j < vins.length; j++) {
				if (!vins[j].equals("")) {
					if (vins[j].trim().length() == 17) {
						set.add(vins[j].trim());
					} else {
						// set.add(vins[j].trim());
						set2.add(vins[j].trim());
					}
				}
			}
			// 对VIN进行组合(in查询)
			StringBuffer buffer = new StringBuffer();
			Iterator<String> i = set.iterator();
			while (i.hasNext()) {
				String s = i.next().trim();
				buffer.append("'").append(s).append("'").append(",");
			}
			// 对VIN进行组合(like查询)
			StringBuffer buffer2 = new StringBuffer();
			Iterator<String> j = set2.iterator();
			while (j.hasNext()) {
				String s = j.next().trim();
				buffer2.append(alias).append(".VIN LIKE ").append("'").append("%").append(s).append("%").append("'")
						.append(" OR ");
			}

			if (set.size() == 0 && set2.size() > 0) {
				buffer2 = buffer2.delete(buffer2.length() - 3, buffer2.length());
				returnSql += likeBuffer.append(" AND (").append(buffer2).append(")\n");
			}
			if (set.size() > 0 && set2.size() == 0) {
				buffer = buffer.deleteCharAt(buffer.length() - 1);
				returnSql = inbuffer.append(" AND " + alias).append(".VIN").append(" IN (").append(buffer).append(")\n")
						.toString();
			}
			if (set.size() > 0 && set2.size() > 0) {
				buffer = buffer.deleteCharAt(buffer.length() - 1);
				buffer2 = buffer2.delete(buffer2.length() - 3, buffer2.length());
				returnSql = inbuffer.append(" AND (" + alias).append(".VIN").append(" IN (").append(buffer)
						.append(") OR ").toString();
				returnSql += likeBuffer.append(buffer2).append(") \n");
			}
		}
		return returnSql;

	}

	public static Map selectFy(String vehicleUsageType) {
		String sql = "select RELATION_CODE,RELATION_EN from TC_RELATION where CODE_ID = " + vehicleUsageType;
		return OemDAOUtil.findFirst(sql, null);
	}

	/**
	 * 获得车辆业务类别
	 * 
	 * @param vehicleId
	 * @param vin
	 * @throws Exception
	 */
	public static final String getVehicleBusinessType(Long vehicleId, String vin) throws Exception {

		StringBuffer sql = new StringBuffer();
		sql.append(" \n");
		sql.append("SELECT V.VEHICLE_ID, -- 车辆ID \n");
		sql.append("       V.VIN, -- 车架号 \n");
		sql.append("       M.GROUP_TYPE -- 业务类型 \n");
		sql.append("  FROM (" + getVwMaterialSql() + ") M \n");
		sql.append(" INNER JOIN TM_VEHICLE_DEC V ON V.MATERIAL_ID = M.MATERIAL_ID \n");
		sql.append(" WHERE M.BRAND_STATUS = '" + OemDictCodeConstants.STATUS_ENABLE + "' \n");
		sql.append("   AND M.SERIES_STATUS = '" + OemDictCodeConstants.STATUS_ENABLE + "' \n");
		sql.append("   AND M.MODEL_STATUS = '" + OemDictCodeConstants.STATUS_ENABLE + "' \n");
		sql.append("   AND M.GROUP_STATUS = '" + OemDictCodeConstants.STATUS_ENABLE + "' \n");
		if (!StringUtils.isNullOrEmpty(String.valueOf(vehicleId))) {
			sql.append("   AND V.VEHICLE_ID = " + vehicleId + " \n");
		}
		if (!StringUtils.isNullOrEmpty(vin)) {
			sql.append("   AND V.VIN = '" + vin + "' \n");
		}
		System.out.println(sql.toString());
		Map list = OemDAOUtil.findFirst(sql.toString(), null);
		String businessType = null;
		if (null != list && list.size() > 0) {
			businessType = String.valueOf(list.get("GROUP_TYPE"));
		}

		return businessType;
	}

	/**
	 * 根据dealerId查询下端entityCode
	 * 先根据dealerId查询companyCode,再根据companyCode对应entityCode
	 * 
	 * @param dealerId
	 *            上端经销商ID
	 * @return DMS_CODE 下端经销商公司Code
	 */
	public Map getDmsDealerCode(Long dealerId) throws ServiceBizException {
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT C.DMS_CODE,B.COMPANY_SHORTNAME\n");
		sql.append("FROM TM_DEALER A, TM_COMPANY B, TI_DEALER_RELATION C\n");
		sql.append("WHERE A.COMPANY_ID = B.COMPANY_ID\n");
		sql.append("AND B.COMPANY_CODE = C.DCS_CODE\n");
		sql.append("AND C.STATUS=" + OemDictCodeConstants.STATUS_ENABLE + "\n");
		sql.append("AND A.DEALER_ID = ").append(dealerId).append("\n");

		Map map = OemDAOUtil.findFirst(sql.toString(), null);

		return map;
	}

	/**
	 * 根据上端dealerCode查询下端entityCode
	 * 先根据dealerCode查询companyCode,再根据companyCode对应entityCode
	 * 
	 * @param dealerCode
	 *            上端经销商Code
	 * @return DMS_CODE 下端经销商公司Code
	 */
	public static Map getDmsDealerCode(String dealerCode) throws ServiceBizException {
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT C.DMS_CODE,B.COMPANY_SHORTNAME\n");
		sql.append("FROM TM_DEALER A, TM_COMPANY B, TI_DEALER_RELATION C\n");
		sql.append("WHERE A.COMPANY_ID = B.COMPANY_ID\n");
		sql.append("AND B.COMPANY_CODE = C.DCS_CODE\n");
		sql.append("AND C.STATUS=" + OemDictCodeConstants.STATUS_ENABLE + "\n");
		sql.append("AND A.DEALER_CODE = '").append(dealerCode).append("'\n");

		Map map = OemDAOUtil.findFirst(sql.toString(), null);
		return map;
	}

	/**
	 * 
	 * @Title: getAllDmsCode
	 * @Description: (查询下端所有的entityCode)
	 */
	public static List<String> getAllDmsCode(Integer sendType) {
		List<String> list = new ArrayList<>();
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT DMS_CODE\n");
		sql.append("FROM TI_DEALER_RELATION\n");
		sql.append("WHERE SEND_TYPE = " + sendType + " AND STATUS = ").append(OemDictCodeConstants.STATUS_ENABLE)
				.append("\n");
		List<Map> codeList = OemDAOUtil.findAll(sql.toString(), null);
		if (null != codeList && codeList.size() > 0) {
			for (Map map : codeList) {
				list.add(CommonUtils.checkNull(map.get("DMS_CODE")));
			}
		}
		return list;
	}

	/**
	 * 
	 * @Title: getAllDcsCode
	 * @Description: (查询上端所有的DealerCode)
	 */
	public static List<String> getAllDcsCode(Integer sendType) {
		List<String> list = new ArrayList<>();
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT DCS_CODE\n");
		sql.append("FROM TI_DEALER_RELATION\n");
		sql.append("WHERE SEND_TYPE = " + sendType + " AND STATUS = ").append(OemDictCodeConstants.STATUS_ENABLE)
				.append("\n");
		List<Map> codeList = OemDAOUtil.findAll(sql.toString(), null);
		if (null != codeList && codeList.size() > 0) {
			for (Map map : codeList) {
				list.add(CommonUtils.checkNull(map.get("DCS_CODE")));
			}
		}
		return list;
	}
	/**
	 * 根据上端dealerId查询下端entityCode（售后）
	 * 先根据dealerId查询companyCode,再根据companyCode对应entityCode
	 * @param dealerId 上端经销商ID
	 * @return entityCode 下端经销商公司Code
	 */
	public static Map<String, Object> getDmsDealerCodeForDealerId(Long dealerId) {
		StringBuffer sql= new StringBuffer();
		sql.append("SELECT C.DMS_CODE,A.DEALER_CODE DCS_CODE,B.COMPANY_SHORTNAME,A.ACURA_GHAS_TYPE\n" );
		sql.append("FROM TM_DEALER A, TM_COMPANY B, TI_DEALER_RELATION C\n" );
		sql.append("WHERE A.COMPANY_ID = B.COMPANY_ID\n" );
		sql.append("AND B.COMPANY_CODE = C.DCS_CODE\n" );
		sql.append("AND C.STATUS="+OemDictCodeConstants.STATUS_ENABLE+"\n" );//add by huyu 2015-4-9
		sql.append("AND A.DEALER_TYPE = "+OemDictCodeConstants.DEALER_TYPE_DWR+"\n" );
		sql.append("AND A.DEALER_ID = ").append(dealerId).append("\n");
		
		Map map = OemDAOUtil.findFirst(sql.toString(), null);
		if(map == null){
			throw new ServiceBizException("DCS端不存在编码为"+getDealerCode(dealerId)+"的经销商记录");
		}else{
			return map;
		}
	}
	
	/**
	 * 根据上端dealerId查询下端entityCode(销售)
	 * 先根据dealerId查询companyCode,再根据companyCode对应entityCode
	 * @param dealerId 上端经销商ID
	 * @return entityCode 下端经销商公司Code
	 */
	public static Map<String, Object> getAllDmsDealerCodeForDealerId(Long dealerId)  {
		StringBuffer sql= new StringBuffer();
		sql.append("SELECT C.DMS_CODE,B.COMPANY_SHORTNAME\n" );
		sql.append("FROM TM_DEALER A, TM_COMPANY B, TI_DEALER_RELATION C\n" );
		sql.append("WHERE A.COMPANY_ID = B.COMPANY_ID\n" );
		sql.append("AND B.COMPANY_CODE = C.DCS_CODE\n" );
		sql.append("AND C.STATUS="+OemDictCodeConstants.STATUS_ENABLE+"\n" );//add by huyu 2015-4-9
		sql.append("AND A.DEALER_TYPE = "+OemDictCodeConstants.DEALER_TYPE_DVS+"\n" );
		sql.append("AND A.DEALER_ID = ").append(dealerId).append("\n");
		
		Map map = OemDAOUtil.findFirst(sql.toString(), null);
		if(map == null){
			throw new ServiceBizException("DCS端不存在编码为"+getDealerCode(dealerId)+"的经销商记录");
		}else{
			return map;
		}
	}
	/**
	 * 根据上端dealerId查询上端dealerCode
	 * @param dealerId  上端dealerId
	 * @return dealerCode   上端经销商代码
	 * @throws Exception 
	 */
	public static String getDealerCode(Long dealerId) {
		String dealerCode = "";
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT DEALER_ID, DEALER_CODE,DEALER_SHORTNAME AS DEALER_NAME, OEM_COMPANY_ID FROM TM_DEALER A \n");
		sql.append("\t WHERE A.DEALER_ID = '").append(dealerId).append("' \n");
		List<Map> list = OemDAOUtil.findAll(sql.toString(), null);
		if (list!=null && list.size()>0) {
			dealerCode = String.valueOf(list.get(0).get("DEALER_CODE"));
		}else{
			throw new ServiceBizException("DCS端不存在DEALER_ID为"+dealerId+"的经销商记录");
		}
		return dealerCode;
	}
	/**
	 * @Description: TODO(根据下端entityCode查询上端dealerCode 区分销售和售后,此方法为售后)
	 * @param @param
	 *            dealerCode 下端entityCode
	 * @param @return
	 *            上端dealerCode
	 */
	public static Map getSeDcsDealerCode(String dealerCode) throws ServiceBizException {
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT DEALER_ID, DEALER_CODE,DEALER_SHORTNAME AS DEALER_NAME, OEM_COMPANY_ID FROM TM_DEALER A \n");
		sql.append("\t WHERE A.COMPANY_ID = \n");
		sql.append("(SELECT B.COMPANY_ID FROM TM_COMPANY B \n");
		sql.append("\t WHERE B.COMPANY_CODE = \n");
		sql.append("(SELECT DCS_CODE from TI_DEALER_RELATION C \n");
		sql.append("\t WHERE C.DMS_CODE = '").append(dealerCode).append("')) \n");
		sql.append("AND A.DEALER_TYPE = ").append(OemDictCodeConstants.DEALER_TYPE_DWR);
		Map map = OemDAOUtil.findFirst(sql.toString(), null);
		if (map == null || map.size() == 0) {
			throw new ServiceBizException("DCS端不存在编码为" + dealerCode + "的经销商记录");
		} else {
			return map;
		}
	}

	/**
	 * @Description: TODO(根据下端entityCode查询上端dealerCode 区分销售和售后,此方法为销售)
	 * @param @param
	 *            dealerCode 下端entityCode
	 * @param @return
	 *            上端dealerCode
	 */
	public static Map getSaDcsDealerCode(String dealerCode) throws ServiceBizException {
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT DEALER_ID, DEALER_CODE, DEALER_NAME FROM TM_DEALER A \n");
		sql.append("\t WHERE A.COMPANY_ID = \n");
		sql.append("(SELECT B.COMPANY_ID FROM TM_COMPANY B \n");
		sql.append("\t WHERE B.COMPANY_CODE = \n");
		sql.append("(SELECT DCS_CODE from TI_DEALER_RELATION C \n");
		sql.append("\t WHERE C.DMS_CODE = '").append(dealerCode).append("')) \n");
		sql.append("AND A.DEALER_TYPE = ").append(OemDictCodeConstants.DEALER_TYPE_DVS);
		Map map = OemDAOUtil.findFirst(sql.toString(), null);
		if (map == null || map.size() == 0) {
			throw new ServiceBizException("DCS端不存在编码为" + dealerCode + "的经销商记录");
		} else {
			return map;
		}
	}

	/**
	 * 车辆品牌信息
	 * 
	 * @param vfcBean
	 *            查询条件
	 * @param beginDate
	 *            开始日期
	 * @param endDate
	 *            结束日期
	 * @param dealerCode
	 *            经销商代码
	 * @param cityCode
	 *            城市代码
	 * @param pageSize
	 *            页面大小
	 * @param curPage
	 *            当前页
	 * @return
	 */
	public List<Map<String, VehicleInfoDTO>> getVehicle() {
		List<Object> params = new ArrayList<Object>();
		StringBuffer sql = new StringBuffer(" SELECT DISTINCT ");
		sql.append(" TVMG1.GROUP_CODE AS BRAND_CODE, TVMG2.GROUP_CODE AS SERIES_CODE, TVMG3.GROUP_CODE AS MODEL_CODE ");
		sql.append(" ,TVMG1.TREE_CODE AS BRAND_ID ,TVMG2.TREE_CODE AS SERIES_ID ,TVMG3.TREE_CODE AS MODEL_ID ");
		// sql.append(" ,TVM.COLOR_CODE ") ;
		sql.append(" FROM TM_VHCL_MATERIAL TVM ");
		sql.append(" INNER JOIN TM_VHCL_MATERIAL_GROUP_R TVMGR ON TVM.MATERIAL_ID = TVMGR.MATERIAL_ID "); // 车辆物料组关系表
		sql.append(" INNER JOIN TM_VHCL_MATERIAL_GROUP TVMG4 ON TVMGR.GROUP_ID = TVMG4.GROUP_ID ");
		sql.append(" INNER JOIN TM_VHCL_MATERIAL_GROUP TVMG3 ON TVMG4.PARENT_GROUP_ID = TVMG3.GROUP_ID ");
		sql.append(" INNER JOIN TM_VHCL_MATERIAL_GROUP TVMG2 ON TVMG3.PARENT_GROUP_ID = TVMG2.GROUP_ID ");
		sql.append(" INNER JOIN TM_VHCL_MATERIAL_GROUP TVMG1 ON TVMG2.PARENT_GROUP_ID = TVMG1.GROUP_ID ");
		sql.append(" WHERE TVMG1.STATUS = ? AND  TVMG2.STATUS = ? ");
		sql.append(" AND TVMG3.STATUS = ? AND TVMG4.STATUS = ? ");
		params.add(OemDictCodeConstants.STATUS_ENABLE);
		params.add(OemDictCodeConstants.STATUS_ENABLE);
		params.add(OemDictCodeConstants.STATUS_ENABLE);
		params.add(OemDictCodeConstants.STATUS_ENABLE);
		List<Map> rs = OemDAOUtil.findAll(sql.toString(), params);
		List<Map<String, VehicleInfoDTO>> ps = wrapper(rs);
		return ps;
	}
	
	public List<Map<String, VehicleInfoDTO>> wrapper(List<Map> rs) {
		Map<String, VehicleInfoDTO> map = new HashMap<String, VehicleInfoDTO>();
		List<Map<String, VehicleInfoDTO>> listMap = new ArrayList<>();
		VehicleInfoDTO infoVo = new VehicleInfoDTO();
		for(int i =0;i<rs.size();i++){
			String brandCode = rs.get(i).get("BRAND_CODE").toString();
			String seriesCode = rs.get(i).get("SERIES_CODE").toString();
			String modelCode = rs.get(i).get("MODEL_CODE").toString();
			// String colorId = rs.getString("COLOR_CODE");
			infoVo.setBrandId(rs.get(i).get("BRAND_ID").toString());
			infoVo.setModelId(rs.get(i).get("SERIES_ID").toString());
			infoVo.setStyleId(rs.get(i).get("MODEL_ID").toString());
			// infoVo.setColorId(colorId);
			map.put(brandCode + "@" + seriesCode + "@" + modelCode, infoVo);
			listMap.add(map);
		}
		return listMap;
	}

	/**
	 * 获取品牌CODE brandCode
	 * 
	 * @param brandId
	 * @return
	 */
	public String getBrandCode(String brandId) {
		List<Object> params = new ArrayList<Object>();
		StringBuffer sql = new StringBuffer();
		sql.append(" SELECT VM.BRAND_CODE FROM (" + getVwMaterialSql() + ") VM  \n");
		sql.append(" WHERE VM.BRAND_ID = ? ");
		params.add(brandId);
		List<Map> map = OemDAOUtil.findAll(sql.toString(), params);
		String brandCode = (String) map.get(0).get("BRAND_CODE");
		return brandCode;

	}

	/**
	 * 获取车系CODE
	 * 
	 * @param brandId
	 * @return
	 */
	public String getSeriesCode2(String seriesId) {
		List<Object> params = new ArrayList<Object>();
		StringBuffer sql = new StringBuffer();
		sql.append(" SELECT VM.SERIES_CODE FROM (" + getVwMaterialSql() + ") VM  \n");
		sql.append(" WHERE VM.SERIES_ID = ? ");
		params.add(seriesId);
		List<Map> map = OemDAOUtil.findAll(sql.toString(), params);
		String brandCode = (String) map.get(0).get("SERIES_CODE");
		return brandCode;

	}

	/**
	 * 获取车款CODE
	 * 
	 * @param groupId
	 * @return
	 */
	public String getGroupCode(String groupId) {
		List<Object> params = new ArrayList<Object>();
		StringBuffer sql = new StringBuffer();
		sql.append(" SELECT VM.GROUP_CODE FROM (" + getVwMaterialSql() + ") VM  \n");
		sql.append(" WHERE VM.GROUP_ID = ? ");
		params.add(groupId);
		List<Map> map = OemDAOUtil.findAll(sql.toString(), params);
		String brandCode = (String) map.get(0).get("GROUP_CODE");
		return brandCode;

	}

}
