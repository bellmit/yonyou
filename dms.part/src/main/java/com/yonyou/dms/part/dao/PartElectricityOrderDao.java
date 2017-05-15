package com.yonyou.dms.part.dao;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.yonyou.dms.framework.DAO.OemDAOUtil;
import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.framework.domain.LoginInfoDto;
import com.yonyou.dms.framework.util.bean.ApplicationContextHelper;
import com.yonyou.dms.function.common.OemDictCodeConstants;
import com.yonyou.dms.function.utils.common.StringUtils;

/**
 * 电商订单确认Dao
 * @author ZhaoZ
 * @date 2017年3月23日
 */
@Repository
public class PartElectricityOrderDao {

	/**
	 * 订单查询
	 * @param queryParams
	 * @return
	 */
	public PageInfoDto eCOrderInfo(Map<String, String> queryParams) {
		List<Object> params = new ArrayList<Object>();
		String sql = eCOrderInfoSql(queryParams, params);
		return OemDAOUtil.pageQuery(sql, params);
	}

	/**
	 * 订单查询SQL
	 * @param queryParams
	 * @param params
	 * @return
	 */
	private String eCOrderInfoSql(Map<String, String> queryParams, List<Object> params) {
		LoginInfoDto logonUser = ApplicationContextHelper.getBeanByType(LoginInfoDto.class);
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT \n");
		sql.append("TPO.ORDER_ID,\n");
		sql.append("TD.DEALER_CODE, \n");
		sql.append("TD.DEALER_SHORTNAME, \n");
		sql.append("TPO.SAP_ORDER_NO,\n");
		sql.append("TPO.ORDER_NO, \n");
		sql.append("TPO.EC_ORDER_NO,\n");
		sql.append("date_format(TPO.DEAL_ORDER_AFFIRM_DATE,'%Y-%m-%d') DEAL_ORDER_AFFIRM_DATE,\n");
		sql.append("(SELECT IF(TPO.ORDER_TYPE='80041010' || TPO.ORDER_TYPE='80041009','电商订单',TPO.ORDER_TYPE)) AS ORDER_TYPE,\n");
		sql.append("TPO.VIN,\n");
		sql.append("date_format(TPO.REPORT_DATE,'%Y-%m-%d') REPORT_DATE, \n");
		sql.append("(SELECT IF(TPO.ORDER_TYPE='80041009','是','否')) AS IS_DIRECT, \n");
		sql.append("TPO.IS_AFFIRM \n");
		sql.append("FROM TT_PT_ORDER_dcs TPO \n");
		sql.append("INNER JOIN TM_DEALER TD ON TD.DEALER_ID = TPO.DEALER_ID \n");
		sql.append("WHERE TPO.DEALER_ID="+logonUser.getDealerId()+" \n");
		System.out.println(logonUser.getDealerId());
		sql.append("AND TPO.ORDER_TYPE ='"+OemDictCodeConstants.PART_ORDER_TYPE_10+"'\n");
		//订单号
		if(!StringUtils.isNullOrEmpty(queryParams.get("orderNo"))){ 
			sql.append("AND TPO.ORDER_NO= ?\n");
       		params.add(queryParams.get("orderNo"));
        }
        //提报时间
		if(!StringUtils.isNullOrEmpty(queryParams.get("startDate"))){ 
			sql.append(" AND  date_format(TPO.REPORT_DATE,'%Y-%m-%d') >='" + queryParams.get("startDate") +"' \n");
        }
        if(!StringUtils.isNullOrEmpty(queryParams.get("endDate"))){ 
        	sql.append(" AND  date_format(TPO.REPORT_DATE,'%Y-%m-%d') <='" + queryParams.get("endDate") +"' \n");
        }
       
        //确认状态
        if(!StringUtils.isNullOrEmpty(queryParams.get("confirmStatus"))){ 
        	if(queryParams.get("confirmStatus").equals("70381002")){
        		sql.append(" AND (TPO.IS_AFFIRM IS NULL OR TPO.IS_AFFIRM = '" + queryParams.get("confirmStatus") + "') \n");
        	}else{
        		sql.append(" AND TPO.IS_AFFIRM = '" + queryParams.get("confirmStatus") + "'  \n");
        	}
        }
       
        //电商订单号
        if(!StringUtils.isNullOrEmpty(queryParams.get("ecOrderNo"))){ 
        	sql.append(" 					AND TPO.EC_ORDER_NO LIKE ? \n");
       		params.add("%"+queryParams.get("ecOrderNo")+"%");
        }
        //确认时间
        if(!StringUtils.isNullOrEmpty(queryParams.get("startDate1"))){ 
			sql.append(" AND  date_format(TPO.DEAL_ORDER_AFFIRM_DATE,'%Y-%m-%d') >='" + queryParams.get("startDate1") +"' \n");
        }
        if(!StringUtils.isNullOrEmpty(queryParams.get("endDate1"))){ 
        	sql.append(" AND  date_format(TPO.DEAL_ORDER_AFFIRM_DATE,'%Y-%m-%d') <='" + queryParams.get("endDate1") +"' \n");
        }
		return sql.toString();
       
	}

	/**
	 * 导出查询
	 * @param queryParams
	 * @return
	 */
	public List<Map> queryDownLoadList(Map<String, String> queryParams) {
		List<Object> params = new ArrayList<Object>();
		String sql = downLoadListSql(queryParams, params);
		return OemDAOUtil.findAll(sql, params);
	}

	private String downLoadListSql(Map<String, String> queryParams, List<Object> params) {
		LoginInfoDto logonUser = ApplicationContextHelper.getBeanByType(LoginInfoDto.class);
		StringBuffer sql = new StringBuffer("\n");
		sql.append("SELECT \n");
		sql.append("TPO.ORDER_ID,\n");
		sql.append("TD.DEALER_CODE, \n");
		sql.append("TD.DEALER_SHORTNAME, \n");
		sql.append("TPO.SAP_ORDER_NO, \n");
		sql.append("TPO.ORDER_NO,\n");
		sql.append("TPO.EC_ORDER_NO,\n");
		sql.append("TPO.DEAL_ORDER_AFFIRM_DATE,\n");
		sql.append("(SELECT IF(TPO.ORDER_TYPE='80041010' || TPO.ORDER_TYPE='80041009','电商订单',TPO.ORDER_TYPE)) AS ORDER_TYPE,\n");
		sql.append("TPO.VIN, \n");
		sql.append("TPO.REPORT_DATE, \n");
		sql.append("(SELECT IF(TPO.ORDER_TYPE='80041009','是','否')) AS IS_DIRECT, \n");
		sql.append("TPO.IS_AFFIRM \n");
		sql.append("FROM TT_PT_ORDER_dcs TPO \n");
		sql.append("INNER JOIN TM_DEALER TD ON TD.DEALER_ID = TPO.DEALER_ID \n");
		sql.append("WHERE TPO.DEALER_ID="+logonUser.getDealerId()+" \n");
		sql.append("AND TPO.ORDER_TYPE ='"+OemDictCodeConstants.PART_ORDER_TYPE_10+"'\n");
		//订单号
				if(!StringUtils.isNullOrEmpty(queryParams.get("orderNo"))){ 
					sql.append("AND TPO.ORDER_NO= ?\n");
		       		params.add(queryParams.get("orderNo"));
		        }
		        //提报时间
				if(!StringUtils.isNullOrEmpty(queryParams.get("startDate"))){ 
					sql.append(" AND  date_format(TPO.REPORT_DATE,'%Y-%m-%d') >='" + queryParams.get("startDate") +"' \n");
		        }
		        if(!StringUtils.isNullOrEmpty(queryParams.get("endDate"))){ 
		        	sql.append(" AND  date_format(TPO.REPORT_DATE,'%Y-%m-%d') <='" + queryParams.get("endDate") +"' \n");
		        }
		       
		        //确认状态
		        if(!StringUtils.isNullOrEmpty(queryParams.get("confirmStatus"))){ 
		        	if(queryParams.get("confirmStatus").equals("70381002")){
		        		sql.append(" AND (TPO.IS_AFFIRM IS NULL OR TPO.IS_AFFIRM = '" + queryParams.get("confirmStatus") + "') \n");
		        	}else{
		        		sql.append(" AND TPO.IS_AFFIRM = '" + queryParams.get("confirmStatus") + "'  \n");
		        	}
		        }
		       
		        //电商订单号
		        if(!StringUtils.isNullOrEmpty(queryParams.get("ecOrderNo"))){ 
		        	sql.append(" 					AND TPO.EC_ORDER_NO LIKE ? \n");
		       		params.add("%"+queryParams.get("ecOrderNo")+"%");
		        }
		        //确认时间
		        if(!StringUtils.isNullOrEmpty(queryParams.get("startDate1"))){ 
					sql.append(" AND  date_format(TPO.DEAL_ORDER_AFFIRM_DATE,'%Y-%m-%d') >='" + queryParams.get("startDate1") +"' \n");
		        }
		        if(!StringUtils.isNullOrEmpty(queryParams.get("endDate1"))){ 
		        	sql.append(" AND  date_format(TPO.DEAL_ORDER_AFFIRM_DATE,'%Y-%m-%d') <='" + queryParams.get("endDate1") +"' \n");
		        }
				return sql.toString();
	}

	/**
	 * 信息回显
	 * @param id
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public Map<String, Object> dealerInfoByOrderId(BigDecimal id) {
		StringBuilder sql = new StringBuilder();
		sql.append("    SELECT \n");
		sql.append("    (SELECT ORG_NAME FROM TM_ORG WHERE ORG_ID = TPO.BIG_ORG_ID) BIG_ORG_NAME,  \n");
		sql.append("    (SELECT ORG_NAME FROM TM_ORG WHERE ORG_ID = TPO.ORG_ID) SAMLL_ORG_NAME, \n");
		sql.append("    TD.DEALER_CODE,\n");
		sql.append("    TD.DEALER_SHORTNAME, \n");
		sql.append("    TPO.EC_CUSTOMER_NAME,\n");
		sql.append("    TPO.EC_CUSTOMER_PHONE,\n");
		sql.append("    TPO.EC_CUSTOMER_ADDRSS,\n");
		sql.append("    TPO.ORDER_TYPE\n");
		sql.append("    FROM TT_PT_ORDER_dcs TPO \n");
		sql.append("   INNER JOIN TM_DEALER TD ON TPO.DEALER_ID = TD.DEALER_ID \n");
		sql.append("   WHERE 1 = 1 \n");
		sql.append("   AND TPO.ORDER_ID = " + id + "\n");
		return OemDAOUtil.findFirst(sql.toString(), null);
	}

	/**
	 * 配件信息查询
	 * @param id
	 * @return
	 */
	public PageInfoDto queryPartInfoList(BigDecimal id) {
		StringBuffer sql = new StringBuffer("\n");
		sql.append("SELECT TPOD.ORDER_ID ORDER_ID, \n");
		sql.append("       TPOD.PART_ID PART_ID,\n");
		sql.append("       ifnull(TPOD.PART_CODE,TPPB.PART_CODE) PART_CODE, \n");
		sql.append("       ifnull(TPOD.PART_NAME,TPPB.PART_NAME ) PART_NAME,\n");
		sql.append("       ifnull(TPOD.PACKAGE_NUM,TPPB.PACKAGE_NUM) PACKAGE_NUM, \n");
		sql.append("       TPOD.ORDER_NUM ORDER_NUM, \n");
		sql.append("       TPOD.NO_TAX_PRICE NO_TAX_PRICE, \n");
		sql.append("       TPOD.NO_TAX_AMOUNT NO_TAX_AMOUNT,\n");
		sql.append("       ifnull(TPOD.UNIT,TPPB.PART_NUIT) UNIT,\n");
		sql.append("       ifnull(TPOD.DISCOUNT,TPPB.DISCOUNT) DISCOUNT  \n");
		sql.append("       FROM TT_PT_ORDER_DETAIL_dcs TPOD \n");
		sql.append("       LEFT JOIN TT_PT_PART_BASE_dcs TPPB ON TPOD.PART_CODE=TPPB.PART_CODE \n");
		sql.append("       WHERE 1=1 \n");
		sql.append("       AND TPOD.ORDER_ID='"+id+"'\n");
		return OemDAOUtil.pageQuery(sql.toString(), null);
	}

	/**
	 * 审核历史
	 * @param id
	 * @return
	 */
	public PageInfoDto checkHidtoryInfoList(BigDecimal id) {
		StringBuilder sql = new StringBuilder();
		sql.append("    SELECT \n");
		sql.append("    TPO.ORDER_ID, \n");
		sql.append("    TC.NAME,  \n");
		sql.append("    TPOH.CHECK_DATE, \n");
		sql.append("    TPOH.CHECK_STATUS, \n");
		sql.append("    TPOH.CHECK_OPINION \n");
		sql.append("    FROM TT_PT_ORDER_HISTORY_dcs TPOH \n");
		sql.append("   INNER JOIN TC_USER TC ON TC.USER_ID = TPOH.CREATE_BY \n");
		sql.append("   INNER JOIN TT_PT_ORDER_dcs TPO ON TPO.ORDER_ID=TPOH.ORDER_ID \n");
		sql.append("   WHERE 1 = 1 \n");
		sql.append("   AND TPO.ORDER_ID = " + id + "\n");
		return OemDAOUtil.pageQuery(sql.toString(), null);
	}

}
