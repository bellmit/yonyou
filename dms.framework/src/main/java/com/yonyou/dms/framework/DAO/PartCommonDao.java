
package com.yonyou.dms.framework.DAO;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.yonyou.dms.function.common.OemDictCodeConstants;
import com.yonyou.dms.function.utils.common.CommonUtils;
import com.yonyou.dms.function.utils.common.DateUtil;

@Repository
public class PartCommonDao extends OemBaseDAO{

    // 定义日志接口
    private static final Logger logger = LoggerFactory.getLogger(PartCommonDao.class);

    /**
	* @Title: getFCACodeByEntityCode 
	* @Description: TODO(根据经销商code取国产车SAP系统dealerCode) 
	* @param  entityCode
	* @return String    返回类型 
	* @throws
	*/
	public String getFCACodeByEntityCode(String entityCode) {
		
		StringBuffer sql = new StringBuffer();
		sql.append(" \n");
		sql.append("SELECT SUBSTR(C.FCA_CODE, 3, 7) AS FCA_CODE \n");
		sql.append("  FROM TM_COMPANY C, TI_DEALER_RELATION DR \n");
		sql.append(" WHERE C.COMPANY_CODE = DR.DCS_CODE \n");
		sql.append("   AND DR.DMS_CODE = '" + entityCode + "' \n");
		sql.append("  WITH UR \n");

		Map<String, Object> map = OemDAOUtil.findFirst(sql.toString(), null);
		
		if (map == null || map.size() == 0) {
			return "";
		} else {
			return map.get("FCA_CODE").toString();
		}
	}
	
	/**
	* @Title: getFCACodeByCompanyId 
	* @Description: TODO(根据经销商companyId取国产车SAP系统dealerCode) 
	* @param  entityCode
	* @return String    返回类型 
	* @throws
	*/
	public String getFCACodeByCompanyId(Long companyId) {
		StringBuffer sql= new StringBuffer();
		sql.append("SELECT SUBSTR(A.FCA_CODE,3,7) FCA_CODE \n");
		sql.append("	FROM TM_COMPANY A \n");
		sql.append("WHERE 1=1 \n");
		sql.append("  AND A.COMPANY_ID = ").append(companyId).append("");
		sql.append("	WITH UR	\n");
		Map<String, Object> map = OemDAOUtil.findFirst(sql.toString(), null);
		if (map == null || map.size() == 0) {
			return "";
		} else {
			return map.get("FCA_CODE").toString();
		}
	}
	
	/**
	* @Title: getDealerInfoByCompanyId 
	* @Description: TODO(根据companyId取国产车dealer_id 小区id 大区id) 
	* @param  entityCode
	* @return map    返回类型 
	* @throws
	*/
	public Map<String, Object> getDealerInfoByCompanyId(Long companyId) {
		StringBuffer sql= new StringBuffer();
		sql.append("SELECT TM.DEALER_ID,TM.DEALER_CODE, \n");
		sql.append("       TM.DEALER_SHORTNAME,TOR2.ORG_DESC BIG_ORG_NAME,TOR3.ORG_DESC ORG_NAME ,  \n");
		sql.append("	   TOR3.PARENT_ORG_ID AS BIG_ORG_ID ,TOR3.ORG_ID AS SMALL_ORG_ID   \n");
		sql.append("FROM TM_DEALER TM ,TM_DEALER_ORG_RELATION TDOR ,TM_ORG  TOR3 ,TM_ORG TOR2  \n");
		sql.append("WHERE TDOR.DEALER_ID = TM.DEALER_ID   \n");
		sql.append("AND (TOR3.ORG_ID = TDOR.ORG_ID AND TOR3.ORG_LEVEL = 3 )  \n");
		sql.append("AND (TOR3.PARENT_ORG_ID = TOR2.ORG_ID AND TOR2.ORG_LEVEL = 2 )  \n");
		sql.append("AND  TOR3.BUSS_TYPE ="+OemDictCodeConstants.ORG_BUSS_TYPE_02+" \n");
		sql.append("AND TOR2.BUSS_TYPE ="+OemDictCodeConstants.ORG_BUSS_TYPE_02+" \n");
		sql.append("AND TM.COMPANY_ID = "+companyId+ " \n");
		sql.append("	WITH UR	\n");
		Map<String, Object> map = OemDAOUtil.findFirst(sql.toString(), null);
		return map;
	}
	
	/**
	* @Title: getDealerInfoByCompanyId 
	* @Description: TODO(根据companyId取国产车dealer_id 小区id 大区id) 
	* @param  entityCode
	* @return map    返回类型 
	* @throws
	*/
	public String getDealerIdByCompanyId(Long companyId) {
		StringBuffer sql= new StringBuffer();
		sql.append("SELECT TM.DEALER_ID,TM.DEALER_CODE, \n");
		sql.append("       TM.DEALER_SHORTNAME,TOR2.ORG_DESC BIG_ORG_NAME,TOR3.ORG_DESC ORG_NAME ,  \n");
		sql.append("	   TOR3.PARENT_ORG_ID AS BIG_ORG_ID ,TOR3.ORG_ID AS SMALL_ORG_ID   \n");
		sql.append("FROM TM_DEALER TM ,TM_DEALER_ORG_RELATION TDOR ,TM_ORG  TOR3 ,TM_ORG TOR2  \n");
		sql.append("WHERE TDOR.DEALER_ID = TM.DEALER_ID   \n");
		sql.append("AND (TOR3.ORG_ID = TDOR.ORG_ID AND TOR3.ORG_LEVEL = 3 )  \n");
		sql.append("AND (TOR3.PARENT_ORG_ID = TOR2.ORG_ID AND TOR2.ORG_LEVEL = 2 )  \n");
		sql.append("AND  TOR3.BUSS_TYPE ="+OemDictCodeConstants.ORG_BUSS_TYPE_02+" \n");
		sql.append("AND TOR2.BUSS_TYPE ="+OemDictCodeConstants.ORG_BUSS_TYPE_02+" \n");
		sql.append("AND TM.COMPANY_ID = "+companyId+ " \n");
		sql.append("	WITH UR	\n");
		Map<String, Object> map = OemDAOUtil.findFirst(sql.toString(), null);
		if (map == null || map.size() == 0) {
			return "";
		} else {
			return map.get("DEALER_ID").toString();
		}
	}
	
	/**
	* @Title: getDealerInfoByEntityCode 
	* @Description: TODO(根据经销商code取国产车dealer_id 小区id 大区id) 
	* @param  entityCode
	* @return map    返回类型 
	* @throws
	*/
	public Map<String, Object> getDealerInfoByEntityCode(String entityCode) {
		StringBuffer sql= new StringBuffer();
		sql.append("SELECT TM.DEALER_ID,TM.DEALER_CODE, \n");
		sql.append("       TM.DEALER_SHORTNAME,TOR2.ORG_DESC BIG_ORG_NAME,TOR3.ORG_DESC ORG_NAME ,  \n");
		sql.append("	   TOR3.PARENT_ORG_ID AS BIG_ORG_ID ,TOR3.ORG_ID AS SMALL_ORG_ID   \n");
		sql.append("FROM TM_DEALER TM ,TM_DEALER_ORG_RELATION TDOR ,TM_ORG  TOR3 ,TM_ORG TOR2 ,TI_DEALER_RELATION TDR  \n");
		sql.append("WHERE TDOR.DEALER_ID = TM.DEALER_ID   \n");
		sql.append("AND (TOR3.ORG_ID = TDOR.ORG_ID AND TOR3.ORG_LEVEL = 3 )  \n");
		sql.append("AND (TOR3.PARENT_ORG_ID = TOR2.ORG_ID AND TOR2.ORG_LEVEL = 2 )  \n");
		sql.append("AND  TOR3.BUSS_TYPE ="+OemDictCodeConstants.ORG_BUSS_TYPE_02+" \n");
		sql.append("AND TOR2.BUSS_TYPE ="+OemDictCodeConstants.ORG_BUSS_TYPE_02+" \n");
		sql.append("AND TDR.DCS_CODE||'A' = TM.DEALER_CODE \n");
		sql.append("AND TDR.STATUS = "+OemDictCodeConstants.STATUS_ENABLE+" \n");
		sql.append("AND TDR.DMS_CODE = '"+entityCode+"' \n");
		sql.append("	WITH UR	\n");
		Map<String, Object> map = OemDAOUtil.findFirst(sql.toString(), null);
		return map;
	}
	
	/**
	* @Title: getDealerInfoByDcsCode 
	* @Description: TODO(根据经销商code取国产车dealer_id 小区id 大区id) 
	* @param  entityCode
	* @return map    返回类型 
	* @throws
	*/
	public Map<String, Object> getDealerInfoByDcsCode(String dcsCode) {
		StringBuffer sql= new StringBuffer();
		sql.append("SELECT TM.DEALER_ID,TM.DEALER_CODE, \n");
		sql.append("       TM.DEALER_SHORTNAME,TOR2.ORG_DESC BIG_ORG_NAME,TOR3.ORG_DESC ORG_NAME ,  \n");
		sql.append("	   TOR3.PARENT_ORG_ID AS BIG_ORG_ID ,TOR3.ORG_ID AS SMALL_ORG_ID   \n");
		sql.append("FROM TM_DEALER TM ,TM_DEALER_ORG_RELATION TDOR ,TM_ORG  TOR3 ,TM_ORG TOR2 ,TI_DEALER_RELATION TDR  \n");
		sql.append("WHERE TDOR.DEALER_ID = TM.DEALER_ID   \n");
		sql.append("AND (TOR3.ORG_ID = TDOR.ORG_ID AND TOR3.ORG_LEVEL = 3 )  \n");
		sql.append("AND (TOR3.PARENT_ORG_ID = TOR2.ORG_ID AND TOR2.ORG_LEVEL = 2 )  \n");
		sql.append("AND  TOR3.BUSS_TYPE ="+OemDictCodeConstants.ORG_BUSS_TYPE_02+" \n");
		sql.append("AND TOR2.BUSS_TYPE ="+OemDictCodeConstants.ORG_BUSS_TYPE_02+" \n");
		sql.append("AND TDR.DCS_CODE||'A' = TM.DEALER_CODE \n");
		sql.append("AND TDR.STATUS = "+OemDictCodeConstants.STATUS_ENABLE+" \n");
		sql.append("AND TDR.DCS_CODE = '"+dcsCode+"' \n");
		sql.append("	WITH UR	\n");
		Map<String, Object> map = OemDAOUtil.findFirst(sql.toString(), null);
		return map;
	}
	
	/**
	* @Title: getDealerInfoByDealerID 
	* @Description: TODO(根据经销商ID取国产车dealer_id 小区id 大区id) 
	* @param  entityCode
	* @return map    返回类型 
	* @throws
	*/
	public Map<String, Object> getDealerInfoByDealerID(Long dealerID) {
		StringBuffer sql= new StringBuffer();
		sql.append("SELECT TM.DEALER_ID,TM.DEALER_CODE, \n");
		sql.append("       TM.DEALER_SHORTNAME,TOR2.ORG_DESC BIG_ORG_NAME,TOR3.ORG_DESC ORG_NAME, \n");
		sql.append("	   TOR3.PARENT_ORG_ID AS BIG_ORG_ID ,TOR3.ORG_ID AS SMALL_ORG_ID \n");
		sql.append("FROM TM_DEALER TM ,TM_DEALER_ORG_RELATION TDOR ,TM_ORG  TOR3 ,TM_ORG TOR2 \n");
		sql.append("WHERE TDOR.DEALER_ID = TM.DEALER_ID \n");
		sql.append("AND (TOR3.ORG_ID = TDOR.ORG_ID AND TOR3.ORG_LEVEL = 3 ) \n");
		sql.append("AND (TOR3.PARENT_ORG_ID = TOR2.ORG_ID AND TOR2.ORG_LEVEL = 2 ) \n");
		sql.append("AND TOR3.BUSS_TYPE ="+OemDictCodeConstants.ORG_BUSS_TYPE_02+" \n");
		sql.append("AND TOR2.BUSS_TYPE ="+OemDictCodeConstants.ORG_BUSS_TYPE_02+" \n");
		sql.append("AND TM.DEALER_ID = "+dealerID+" \n");
		sql.append("	WITH UR	\n");
		Map<String, Object> map = OemDAOUtil.findFirst(sql.toString(), null);
		return map;
	}
	
	/**
	 * 根据国产车SAP系统经销商code取DMS系统的DMS_CODE
	 * @param fcaCode
	 * @return
	 */
	public String getDealerEntityCodeByFCACode(String fcaCode) {
		
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT TDR.DMS_CODE \n");
		sql.append("  FROM TM_COMPANY A,TI_DEALER_RELATION TDR \n");
		sql.append(" WHERE A.COMPANY_CODE = TDR.DCS_CODE \n");
		sql.append("   AND A.FCA_CODE like '%" + fcaCode + "%' \n");
		sql.append("	WITH UR	\n");

		Map<String, Object> map = OemDAOUtil.findFirst(sql.toString(), null);
		if (map == null || map.size() == 0) {
			return "";// 未找到对应经销商，返回空，跳过
		} else {
			return map.get("DMS_CODE").toString();
		}
	}
	
	/**
	 * 根据国产车SAP系统经销商code取DCS系统的COMPANY_CODE
	 * @param fcaCode
	 * @return
	 */
	public String getDealerDCSCodeByFCACode(String fcaCode) {
		
		StringBuffer sql = new StringBuffer();
		sql.append("select A.COMPANY_CODE   FROM TM_COMPANY A where 1=1 \n");
		sql.append("   AND A.FCA_CODE like '%" + fcaCode + "%' \n");
		sql.append(" WITH UR	\n");

		Map<String, Object> map = OemDAOUtil.findFirst(sql.toString(), null);
		if (map == null || map.size() == 0) {
			return "";
		} else {
			return map.get("COMPANY_CODE").toString();
		}
	}
	
	/**
	 * 根据国产车SAP系统经销商code取DCS系统的COMPANY_ID
	 * @param fcaCode
	 * @return
	 */
	public String getCompanyIDByFCACode(String fcaCode) {
		
		StringBuffer sql = new StringBuffer();
		sql.append("select A.COMPANY_ID   FROM TM_COMPANY A where 1=1 \n");
		sql.append("   AND A.FCA_CODE like '%" + fcaCode + "%' \n");
		sql.append(" WITH UR	\n");

		Map<String, Object> map = OemDAOUtil.findFirst(sql.toString(), null);
		if (map == null || map.size() == 0) {
			return "";
		} else {
			return map.get("COMPANY_ID").toString();
		}
	}
	
	/**
	 * 根据国产车SAP系统经销商code取DCS系统的COMPANY_ID
	 * @param fcaCode
	 * @return
	 */
	public String getCompanyIdByFCACode(String fcaCode) {
		
		StringBuffer sql = new StringBuffer();
		sql.append("select A.COMPANY_ID FROM TM_COMPANY A where 1=1 \n");
		sql.append("AND A.FCA_CODE like '%" + fcaCode + "%' \n");
		sql.append("WITH UR \n");

		Map<String, Object> map = OemDAOUtil.findFirst(sql.toString(), null);
		if (map == null || map.size() == 0) {
			return "";
		} else {
			return map.get("COMPANY_ID").toString();
		}
	}
	
	/**
	 * 根据经销商ID获取SAP经销商代码(FAC_CODE)
	 * dealerId 售后经销商ID
	 */
	public String getSAPCodeByPartDealerId(Long dealerId) {
		
		StringBuffer sql = new StringBuffer();
		sql.append(" \n");
		sql.append("SELECT SUBSTR (TC.FCA_CODE, 3, 7) AS FCA_CODE \n");
		sql.append("  FROM TM_DEALER TD, TM_COMPANY TC \n");
		sql.append(" WHERE TD.DEALER_CODE = TC.COMPANY_CODE || 'A' \n");
		sql.append("   AND TD.DEALER_ID = '" + dealerId + "' \n");
		sql.append("  WITH UR \n");

		Map<String, Object> map = OemDAOUtil.findFirst(sql.toString(), null);
		if (map == null || map.size() == 0) {
			return "";
		} else {
			return map.get("FCA_CODE").toString();
		}
	}
	/**
	 * 根据经销商ID获取SAP经销商代码(FAC_CODE)
	 * dealerId 
	 */
	public String getSAPCodeByPartDealerIdSales(Long dealerId) {
			
			StringBuffer sql = new StringBuffer();
			sql.append(" \n");
			sql.append("SELECT SUBSTR (TC.FCA_CODE, 3, 7) AS FCA_CODE \n");
			sql.append("  FROM TM_DEALER TD, TM_COMPANY TC \n");
			sql.append(" WHERE ((TD.DEALER_CODE = TC.COMPANY_CODE) or (TD.DEALER_CODE = TC.COMPANY_CODE || 'A')) \n");
			sql.append("   AND TD.DEALER_ID = '" + dealerId + "' \n");
			sql.append("  WITH UR \n");
	
			Map<String, Object> map = OemDAOUtil.findFirst(sql.toString(), null);
			if (map == null || map.size() == 0) {
				return "";
			} else {
				return map.get("FCA_CODE").toString();
			}
		}
	/**
	 * 开票状态转换 sap-->DCS
	 */
	public String getInvoiceStatusBySap(String invoiceStatus) {
		if (null==invoiceStatus || "".equals(invoiceStatus)) {
			return "";
		}
		if (invoiceStatus.equals("10") ){
			return "未开票";
		}
		if (invoiceStatus.equals("20")) {
			return "部分开票";		
		}
		if (invoiceStatus.equals("30")) {
			return "完全开票";
		}
		return "";
	}
	
	/**
	 * 订单类型转换 DMS-->Sap
	 */
	public String getOrderTypeByIauart(String orderType) {
		
		if (null == orderType || "".equals(orderType)) {return "";}
		if (orderType.equals(OemDictCodeConstants.PART_ORDER_TYPE_01.toString())) { return "C2"; }	// 紧急底盘号订单
		if (orderType.equals(OemDictCodeConstants.PART_ORDER_TYPE_02.toString())) { return "EO"; }	// 紧急零备件订单
		if (orderType.equals(OemDictCodeConstants.PART_ORDER_TYPE_08.toString())) { return "SC"; }	// 标准底盘号订单
		if (orderType.equals(OemDictCodeConstants.PART_ORDER_TYPE_03.toString())) { return "SO"; }	// 标准零备件订单
		if (orderType.equals(OemDictCodeConstants.PART_ORDER_TYPE_05.toString())) { return "TO"; }	// 第三方采购订单
		if (orderType.equals(OemDictCodeConstants.PART_ORDER_TYPE_04.toString())) { return "VO"; }	// VOR销售订单
		if (orderType.equals(OemDictCodeConstants.PART_ORDER_TYPE_06.toString())) { return "WO"; }	// 保修相关订单
		if (orderType.equals(OemDictCodeConstants.PART_ORDER_TYPE_09.toString())) { return "W1"; }	// 客户直销订单
		if (orderType.equals(OemDictCodeConstants.PART_ORDER_TYPE_10.toString())) { return "W2"; }	// 经销商批售订单
		return "";
	}
	
	/**
	 * 订单类型转换 sap-->DCS
	 */
	public String getOrderTypeBySap(String orderType) {
		if (null==orderType || "".equals(orderType)) {
			return "";
		}
		if (orderType.equals("C2") ){//紧急底盘号订单
			return "E-CODE 2";
		}
		if (orderType.equals("EO")) {//紧急零备件订单
			return "E.O.";		
		}
		if (orderType.equals("SC")) {//标准底盘号订单
			return "S-CODE 2";
		}
		if (orderType.equals("SO")) {//标准零备件订单
			return "S.O.";		
		}
		if (orderType.equals("TO")) {//第三方采购订单
			return "T.O.";
		}
		if (orderType.equals("VO")) {//VOR销售订单
			return "V.O.R";
		}
		if (orderType.equals("WO")) {//保修相关订单
			return "W.O.";
		}
		return "";
	}
	
	/**
	 * 订单类型转换 SAP-->DMS
	 */
	public String getIauartByOrderType(String orderType) {
		if (null==orderType || "".equals(orderType)) {
			return "";
		}
		if (orderType.equals("C2")) {//紧急底盘号订单
			return OemDictCodeConstants.PART_ORDER_TYPE_01.toString();
		}
		if (orderType.equals("EO")) {//紧急零备件订单
			return OemDictCodeConstants.PART_ORDER_TYPE_02.toString();		
		}
		if (orderType.equals("SC")) {//标准底盘号订单
			return OemDictCodeConstants.PART_ORDER_TYPE_08.toString();
		}
		if (orderType.equals("SO")) {//标准零备件订单
			return OemDictCodeConstants.PART_ORDER_TYPE_03.toString();		
		}
		if (orderType.equals("TO")) {//第三方采购订单
			return OemDictCodeConstants.PART_ORDER_TYPE_05.toString();
		}
		if (orderType.equals("VO")) {//VOR销售订单
			return OemDictCodeConstants.PART_ORDER_TYPE_04.toString();
		}
		if (orderType.equals("WO")) {//保修相关订单
			return OemDictCodeConstants.PART_ORDER_TYPE_06.toString();
		}
		return "";
	}
	
	/**
	 * 订单类型转换 DCS-->DMS
	 */
	public String getIauartByDCSOrderType(String orderType) {
		if (null==orderType || "".equals(orderType)) {
			return "";
		}
		if (orderType.equals("E-CODE 2")) {//紧急底盘号订单
			return OemDictCodeConstants.PART_ORDER_TYPE_01.toString();
		}
		if (orderType.equals("E.O.")) {//紧急零备件订单
			return OemDictCodeConstants.PART_ORDER_TYPE_02.toString();		
		}
		if (orderType.equals("S-CODE 2")) {//标准底盘号订单
			return OemDictCodeConstants.PART_ORDER_TYPE_08.toString();
		}
		if (orderType.equals("S.O.")) {//标准零备件订单
			return OemDictCodeConstants.PART_ORDER_TYPE_03.toString();		
		}
		if (orderType.equals("T.O.")) {//第三方采购订单
			return OemDictCodeConstants.PART_ORDER_TYPE_05.toString();
		}
		if (orderType.equals("V.O.R")) {//VOR销售订单
			return OemDictCodeConstants.PART_ORDER_TYPE_04.toString();
		}
		if (orderType.equals("W.O.")) {//保修相关订单
			return OemDictCodeConstants.PART_ORDER_TYPE_06.toString();
		}
		return "";
	}
	
	/**
	 * 根据Id得到附件的名称
	 */
	public String getAttNameById(String fjid) {
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT SUBSTR(FILENAME,1,LOCATE('.',FILENAME)-1) FILENAME FROM FS_FILEUPLOAD WHERE FJID ="+fjid+" \n");
		sql.append("	WITH UR	\n");

		Map<String, Object> map =OemDAOUtil.findFirst(sql.toString(), null);
		if (map == null || map.size() == 0) {
			return "";
		} else {
			return map.get("FILENAME").toString();
		}
	}
	
	/**
	 * 得到交货单号
	 */
	public String getDeliverNo() {
		StringBuilder sb = new StringBuilder();
		sb.append("SELECT NVL(MAX(TPD.DELIVER_NO),'0070000000') DELIVER_NO from TT_PT_DELIVER_DCS TPD ");
		sb.append(" WHERE TPD.DELIVER_NO like '007%' WITH UR ");
		Map<String, Object> map = OemDAOUtil.findFirst(sb.toString(), null);
		long maxDeliverNo = new Long(map.get("DELIVER_NO").toString())+1;
		return "00"+maxDeliverNo;
	}
	
	/**
	 * 得到订单号
	 */
	public String getOrderNo(String dealerCode) {
		StringBuilder sql = new StringBuilder();
		sql.append(" SELECT NVL(MAX(SUBSTR(TPO.ORDER_NO,9,4)),0000) ORDER_NO from TT_PT_ORDER_DCS TPO ");
		sql.append(" WHERE TPO.ORDER_NO LIKE 'AO%'  ");
		sql.append(" AND DATE_FORMAT(TPO.CREATE_DATE,'%Y-%m-%d') >='"+DateUtil.formatDefaultDate(new Date())+"' WITH UR\n");
		List<Map> listmap=OemDAOUtil.findAll(sql.toString(), null);
		List<String> list=new ArrayList<String>();
		if(null!=listmap&&listmap.size()>0){
			for(Map map:listmap){
				list.add(CommonUtils.checkNull(map.get("ORDER_NO")));
			}
		}
		String lastFour = (new Long(list.get(0)) + 1 ) + "";
		String orderNo = "AO";
		Calendar calendar = Calendar.getInstance();	// 得到日历
		String year = (calendar.get(Calendar.YEAR)+"").substring(2, 4);
		String month = (calendar.get(Calendar.MONTH) + 1)+"";
		if (month.length()==1) {
			month = "0"+month;
		}
		String day = calendar.get(Calendar.DAY_OF_MONTH)+"";
		if (day.length()==1) {
			day = "0"+day;
		}
		orderNo = orderNo + year + month + day;
		if (lastFour.length()==1) {
			orderNo = orderNo + "000" + lastFour;
		} else if (lastFour.length()==2) {
			orderNo = orderNo + "00" + lastFour;
		} else if (lastFour.length()==3) {
			orderNo = orderNo + "0" + lastFour;
		} else {
			orderNo = orderNo + lastFour;
		}
		return orderNo + dealerCode;
	}
	
	/**
	 * 得到配件订单号
	 */
	public String getPartsOrderNo(String dealerCode) {
		StringBuilder sql = new StringBuilder();
		sql.append(" SELECT NVL(MAX(SUBSTR(TPO.ORDER_NO,9,4)),0000) ORDER_NO from TT_PT_ORDER_DCS TPO ");
		sql.append(" WHERE TPO.ORDER_NO LIKE 'WO%'  ");
		sql.append(" AND DATE_FORMAT(TPO.CREATE_DATE,'%Y-%m-%d') >='"+DateUtil.formatDefaultDate(new Date())+"' WITH UR\n");
		List<Map> listmap=OemDAOUtil.findAll(sql.toString(), null);
		List<String> list=new ArrayList<String>();
		if(null!=listmap&&listmap.size()>0){
			for(Map map:listmap){
				list.add(CommonUtils.checkNull(map.get("ORDER_NO")));
			}
		}
		String lastFour = (new Long(list.get(0)) + 1 ) + "";
		String orderNo = "WO";
		Calendar calendar = Calendar.getInstance();	// 得到日历
		String year = (calendar.get(Calendar.YEAR)+"").substring(2, 4);
		String month = (calendar.get(Calendar.MONTH) + 1)+"";
		if (month.length()==1) {
			month = "0"+month;
		}
		String day = calendar.get(Calendar.DAY_OF_MONTH)+"";
		if (day.length()==1) {
			day = "0"+day;
		}
		orderNo = orderNo + year + month + day;
		if (lastFour.length()==1) {
			orderNo = orderNo + "000" + lastFour;
		} else if (lastFour.length()==2) {
			orderNo = orderNo + "00" + lastFour;
		} else if (lastFour.length()==3) {
			orderNo = orderNo + "0" + lastFour;
		} else {
			orderNo = orderNo + lastFour;
		}
		return orderNo + dealerCode;
	}
	
	/**
	 * 得到补登记订单号
	 */
	public String getOrderNoByRe(String dealerCode) {
		StringBuilder sql = new StringBuilder();
		sql.append(" SELECT NVL(MAX(SUBSTR(TPO.ORDER_NO,9,4)),0000) ORDER_NO from TT_PT_ORDER_DCS TPO ");
		sql.append(" WHERE TPO.ORDER_NO LIKE 'RO%'  ");
		sql.append(" AND DATE_FORMAT(TPO.CREATE_DATE,'%Y-%m-%d') >='"+DateUtil.formatDefaultDate(new Date())+"' WITH UR\n");
		List<Map> listmap=OemDAOUtil.findAll(sql.toString(), null);
		List<String> list=new ArrayList<String>();
		if(null!=listmap&&listmap.size()>0){
			for(Map map:listmap){
				list.add(CommonUtils.checkNull(map.get("ORDER_NO")));
			}
		}
		String lastFour = (new Long(list.get(0)) + 1 ) + "";
		String orderNo = "RO";
		Calendar calendar = Calendar.getInstance();	// 得到日历
		String year = (calendar.get(Calendar.YEAR)+"").substring(2, 4);
		String month = (calendar.get(Calendar.MONTH) + 1)+"";
		if (month.length()==1) {
			month = "0"+month;
		}
		String day = calendar.get(Calendar.DAY_OF_MONTH)+"";
		if (day.length()==1) {
			day = "0"+day;
		}
		orderNo = orderNo + year + month + day;
		if (lastFour.length()==1) {
			orderNo = orderNo + "000" + lastFour;
		} else if (lastFour.length()==2) {
			orderNo = orderNo + "00" + lastFour;
		} else if (lastFour.length()==3) {
			orderNo = orderNo + "0" + lastFour;
		} else {
			orderNo = orderNo + lastFour;
		}
		return orderNo + dealerCode;
	}
	
	/**
	* @Title: getFCACodeByEntityCode 
	* @Description: TODO(根据经销商code取国产车SAP系统dealerCode) 
	* @param  entityCode
	* @return String    返回类型 
	* @throws
	*/
	public String getIyjByVin(String vin) {
		StringBuffer sql= new StringBuffer();
		sql.append("SELECT WARN_TIMES FROM TT_THREEPACK_WARN_DCS WHERE VIN = '"+vin+"' AND WARN_ITEM_NO = '500' WITH UR \n");
		Map<String, Object> map = OemDAOUtil.findFirst(sql.toString(), null);
		if (map == null || map.size() == 0) {
			return "";
		} else {
			return map.get("WARN_TIMES").toString();
		}
	}
	
	/**
	 * sap判断是否存在此交货单号是否存在
	 */
	public List<Map<String, Object>> getDeliverPo(String deliverNo) {
		StringBuilder sb = new StringBuilder();
		sb.append("SELECT DELIVER_NO,DELIVER_STATUS  FROM TT_PT_DELIVER_DCS WHERE \n");
		sb.append(" DELIVER_NO='").append(deliverNo).append("'\n");
		sb.append(" AND DELIVER_STATUS='").append(OemDictCodeConstants.PART_DELIVER_STATUS_02).append("' WITH UR ");
        List<Map<String, Object>> list  = pageQuery(sb.toString(), null, getFunName());
		return list;
	}
	/**
	 * 根据ConpanyId 获取售后经销商ID code
	 * @param companyId
	 * @return
	 */
	public Map<String,Object> getDealerDwr(String companyId){
		StringBuffer sql= new StringBuffer();
		sql.append("SELECT TM.DEALER_ID,TM.DEALER_CODE,TM.DEALER_SHORTNAME \n");
		sql.append(" FROM TM_DEALER TM \n");
		sql.append(" WHERE TM.DEALER_TYPE="+OemDictCodeConstants.DEALER_TYPE_DWR+" \n");
		sql.append(" AND TM.COMPANY_ID = "+companyId+ " \n");
		sql.append(" WITH UR \n");
		Map<String, Object> map = OemDAOUtil.findFirst(sql.toString(), null);
		if (map == null || map.size() == 0) {
			throw new IllegalArgumentException("DCS端不存在companyId为" + companyId + "的经销商记录");
		} else {
			return map;
		}
	}
	/**
	 * 订单类型转换 SAP-->DMS
	 */
	public String getIauartByOrderType2(String orderType) {
		logger.info("------------orderType---------"+orderType);
		if (null==orderType || "".equals(orderType)) {
			return "";
		}
		if (orderType.equals("9034")) {//紧急底盘号订单
			return OemDictCodeConstants.PART_ORDER_TYPE_01.toString();
		}
		if (orderType.equals("9031")) {//紧急零备件订单
			return OemDictCodeConstants.PART_ORDER_TYPE_02.toString();		
		}
		if (orderType.equals("9038")) {//标准底盘号订单
			return OemDictCodeConstants.PART_ORDER_TYPE_08.toString();
		}
		if (orderType.equals("9030")) {//标准零备件订单
			return OemDictCodeConstants.PART_ORDER_TYPE_03.toString();		
		}
		if (orderType.equals("9037")) {//第三方采购订单
			return OemDictCodeConstants.PART_ORDER_TYPE_05.toString();
		}
		if (orderType.equals("9032")) {//VOR销售订单
			return OemDictCodeConstants.PART_ORDER_TYPE_04.toString();
		}
		if (orderType.equals("9033")) {//保修相关订单
			logger.info("------------orderType9033---------"+orderType);
			return OemDictCodeConstants.PART_ORDER_TYPE_06.toString();
		}
		if (orderType.equals("9035")) {//零部件免费发货销售订单
			return OemDictCodeConstants.PART_ORDER_TYPE_07.toString();
		}
		return "";
	}
}
