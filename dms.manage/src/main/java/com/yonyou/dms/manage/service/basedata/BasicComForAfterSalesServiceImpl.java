/**
 * 
 */
package com.yonyou.dms.manage.service.basedata;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.yonyou.dms.framework.DAO.DAOUtil;
import com.yonyou.dms.framework.domains.PO.TmDefaultParaPO;
import com.yonyou.dms.framework.util.FrameworkUtil;
import com.yonyou.dms.function.common.DictCodeConstants;
import com.yonyou.dms.function.utils.common.StringUtils;

/**
 * @author yangjie
 *
 */
@Service
@SuppressWarnings("rawtypes")
public class BasicComForAfterSalesServiceImpl implements BasicComForAfterSalesService {

	protected static HashSet<String> isCheckBoxCode = new HashSet<String>();// 用于储存前台用于用复选框显示

	/**
	 * 维护前台需要用复选框显示的CODE值
	 */
	public BasicComForAfterSalesServiceImpl() {
		// 维修页面
		isCheckBoxCode.add("1067");
		isCheckBoxCode.add("1068");
		isCheckBoxCode.add("1069");
		isCheckBoxCode.add("1050");
		isCheckBoxCode.add("1782");
		isCheckBoxCode.add("1021");
		isCheckBoxCode.add("1059");
		isCheckBoxCode.add("3335");
		isCheckBoxCode.add("1025");
		isCheckBoxCode.add("1051");
		// 配件页面
		isCheckBoxCode.add("1014");
		isCheckBoxCode.add("2015");
		isCheckBoxCode.add("1023");
		isCheckBoxCode.add("1081");
		isCheckBoxCode.add("2199");
		isCheckBoxCode.add("3115");
		// 报表参数
		isCheckBoxCode.add("2004");
		isCheckBoxCode.add("2016");
		isCheckBoxCode.add("2020");
		isCheckBoxCode.add("2053");
		isCheckBoxCode.add("2056");
		isCheckBoxCode.add("2061");
		isCheckBoxCode.add("2054");
		isCheckBoxCode.add("2057");
		isCheckBoxCode.add("2064");
		isCheckBoxCode.add("2040");
		isCheckBoxCode.add("2055");
		isCheckBoxCode.add("2067");
		isCheckBoxCode.add("2042");
		isCheckBoxCode.add("2080");
		isCheckBoxCode.add("2058");
		// 积分规则定义
		isCheckBoxCode.add("1135");
		isCheckBoxCode.add("1154");
		isCheckBoxCode.add("1153");
		isCheckBoxCode.add("1165");
		isCheckBoxCode.add("1152");
		isCheckBoxCode.add("1149");
		isCheckBoxCode.add("1114");
		isCheckBoxCode.add("1902");
		isCheckBoxCode.add("3312");
		isCheckBoxCode.add("3313");
		isCheckBoxCode.add("3314");
	}

	/**
	 * 查询所有维修参数 1053,1056
	 */
	@Override
	public Map<String, String> findAllRepair() {
		StringBuffer sb = new StringBuffer();
		sb.append("SELECT DEALER_CODE,ITEM_CODE,DEFAULT_VALUE FROM TM_DEFAULT_PARA ");
		sb.append(
				"WHERE ITEM_CODE IN (1002,1001,1003,1019,1004,1005,1006,1903,1010,1119,1011,1904,1008,1082,1060,1047,1048,1049,1053,1056,1084,1094,1095,1225,1156,1067,1068,1069,1050,1782,1021,1059,3335,1038,1072,1063,1009,8011,1030,1121,1029,1096,1025,1051,1062,1070,1071,1078)");
		List<Map> list = DAOUtil.findAll(sb.toString(), null);
		Map<String, String> callback = new HashMap<String, String>();
		for (Map map : list) {
			if (isCheckBoxCode.contains(map.get("ITEM_CODE"))) {// 如果ITEM_CODE在集合内表示前台需要转化成复选框显示.
				if ("12781001".equals(map.get("DEFAULT_VALUE"))) {// 表示:是
					callback.put(isNullCheck(map.get("ITEM_CODE")), "10571001");
				} else {
					callback.put(isNullCheck(map.get("ITEM_CODE")), "");
				}
			} else {
				callback.put(isNullCheck(map.get("ITEM_CODE")), isNullCheck(map.get("DEFAULT_VALUE")));
			}
		}
		return callback;
	}

	// 非空判断
	protected static String isNullCheck(Object obj) {
		return StringUtils.isNullOrEmpty(obj) ? "" : obj.toString();
	}

	/**
	 * 下拉框查询仓库
	 */
	@Override
	public List<Map> findStorageCode() {
		String sql = "SELECT DEALER_CODE,STORAGE_CODE,STORAGE_NAME FROM TM_STORAGE";
		return DAOUtil.findAll(sql, null);
	}

	/**
	 * 下拉框查询发票
	 */
	@Override
	public List<Map> findInvoiceCode() {
		String sql = "SELECT DEALER_CODE,INVOICE_TYPE_CODE,INVOICE_TYPE_NAME FROM TM_INVOICE_TYPE";
		return DAOUtil.findAll(sql, null);
	}

	/**
	 * 下拉框查询付款方式
	 */
	@Override
	public List<Map> findPaymentCode() {
		String sql = "SELECT DEALER_CODE,PAY_TYPE_CODE,PAY_TYPE_NAME FROM TM_PAY_TYPE";
		return DAOUtil.findAll(sql, null);
	}

	/**
	 * 下拉框查询收费类别
	 */
	@Override
	public List<Map> findChargeCode() {
		String sql = "SELECT DEALER_CODE,MANAGE_SORT_CODE,MANAGE_SORT_NAME FROM TM_MANAGE_TYPE";
		return DAOUtil.findAll(sql, null);
	}

	/**
	 * 查询所有配件基本参数
	 */
	@Override
	public Map<String, String> findAllPart() {
		StringBuffer sb = new StringBuffer();
		sb.append("SELECT DEALER_CODE,ITEM_CODE,DEFAULT_VALUE FROM TM_DEFAULT_PARA ");
		sb.append(
				"WHERE ITEM_CODE IN (3101,3102,3003,3004,3005,3006,3007,3008,3009,3010,3011,3012,3013,3014,3017,2034,3116,1058,1061,8035,3015,3016,7001,7002,7003,7004,7005,7006,7007,7008,7009,1014,2015,1023,8551,3115,1076,1077)");
		List<Map> list = DAOUtil.findAll(sb.toString(), null);
		Map<String, String> callback = new HashMap<String, String>();
		for (Map map : list) {
			if (isCheckBoxCode.contains(map.get("ITEM_CODE"))) {// 如果ITEM_CODE在集合内表示前台需要转化成复选框显示.
				if ("12781001".equals(map.get("DEFAULT_VALUE"))) {// 表示:是
					callback.put(isNullCheck(map.get("ITEM_CODE")), "10571001");
				} else {
					callback.put(isNullCheck(map.get("ITEM_CODE")), "");
				}
			} else {
				callback.put(isNullCheck(map.get("ITEM_CODE")), isNullCheck(map.get("DEFAULT_VALUE")));
			}
		}
		return callback;
	}

	/**
	 * 查询报表基本参数
	 */
	@Override
	public Map<String, String> findAllReport() {
		StringBuffer sb = new StringBuffer();
		sb.append("SELECT DEALER_CODE,ITEM_CODE,DEFAULT_VALUE FROM TM_DEFAULT_PARA ");
		sb.append(
				"WHERE ITEM_CODE IN (2004,2005,2009,2080,2016,2006,2008,2058,2020,2007,2010,7012,2053,2054,2040,2056,2057,2055,2061,2064,2067,2050,2051,2052,2062,2041,2042,2043,7011)");
		List<Map> list = DAOUtil.findAll(sb.toString(), null);
		Map<String, String> callback = new HashMap<String, String>();
		for (Map map : list) {
			if (isCheckBoxCode.contains(map.get("ITEM_CODE"))) {// 如果ITEM_CODE在集合内表示前台需要转化成复选框显示.
				if ("12781001".equals(map.get("DEFAULT_VALUE"))) {// 表示:是
					callback.put(isNullCheck(map.get("ITEM_CODE")), "10571001");
				} else {
					callback.put(isNullCheck(map.get("ITEM_CODE")), "");
				}
			} else {
				callback.put(isNullCheck(map.get("ITEM_CODE")), isNullCheck(map.get("DEFAULT_VALUE")));
			}
		}
		return callback;
	}

	/**
	 * 查询积分基本参数
	 */
	@Override
	public Map<String, String> findAllIntegral() {
		StringBuffer sb = new StringBuffer();
		sb.append("SELECT DEALER_CODE,ITEM_CODE,DEFAULT_VALUE FROM TM_DEFAULT_PARA ");
		sb.append(
				"WHERE ITEM_CODE IN (1101,1102,1103,1104,1105,1106,3301,3302,3303,3304,1166,1107,1108,3305,3306,3307,3308,1162,1163,1135,1154,1153,1165,1114,1124,3314,3312,1152,1111,1112,3310,1149,4009,1902,1150,1109,1110,1164)");
		List<Map> list = DAOUtil.findAll(sb.toString(), null);
		Map<String, String> callback = new HashMap<String, String>();
		for (Map map : list) {
			if (isCheckBoxCode.contains(map.get("ITEM_CODE"))) {// 如果ITEM_CODE在集合内表示前台需要转化成复选框显示.
				if ("12781001".equals(map.get("DEFAULT_VALUE"))) {// 表示:是
					callback.put(isNullCheck(map.get("ITEM_CODE")), "10571001");
				} else {
					callback.put(isNullCheck(map.get("ITEM_CODE")), "");
				}
			} else {
				callback.put(isNullCheck(map.get("ITEM_CODE")), isNullCheck(map.get("DEFAULT_VALUE")));
			}
		}
		return callback;
	}

	/**
	 * 修改基本参数方法
	 */
	@Override
	public void updateBasicParam(Map<String, String> param) {
		String[] split = param.get("checkCode").split("-");
		for (int i = 0; i < split.length; i++) {
			if(!param.containsKey(split[i])){//表示前台传过来的CODE中没有此CODE
				param.put(split[i], DictCodeConstants.IS_NOT+"");
			}
		}
		for (Map.Entry<String, String> entry : param.entrySet()) {
			// 1.将前台复选框传过来的值转化为(是,否)形式
			if(isCheckBoxCode.contains(entry.getKey())){//表示前台是以复选框的形式传递
				if("10571001".equals(entry.getValue())){
					param.put(entry.getKey(), DictCodeConstants.IS_YES+"");
				}
			}
			if(!StringUtils.isNullOrEmpty(param.get(entry.getKey()))&&!"checkCode".equals(entry.getKey())){
				//2.通过循环
				TmDefaultParaPO po = TmDefaultParaPO.findByCompositeKeys(FrameworkUtil.getLoginInfo().getDealerCode(),entry.getKey());
				po.setString("DEFAULT_VALUE", param.get(entry.getKey()));
				po.saveIt();
			}
		}
	}
}
