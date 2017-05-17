package com.yonyou.dms.retail.dao.rebate;

import java.util.ArrayList;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.yonyou.dms.common.Util.Utility;
import com.yonyou.dms.framework.DAO.OemBaseDAO;
import com.yonyou.dms.framework.DAO.OemDAOUtil;
import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.function.utils.common.StringUtils;

/**
 * 
* @ClassName: RebateDetailDao 
* @Description: 返利核算汇总查询（OEM）
* @author zhengzengliang 
* @date 2017年4月26日 下午7:03:02 
*
 */
@SuppressWarnings({"rawtypes","unchecked"})
@Repository
public class RebateDetailDao extends OemBaseDAO{
	
	/**
	 * 
	* @Title: findRebateDetail 
	* @Description: 经销商返利核算汇总查询（OEM）（明细）
	* @param @param queryParam
	* @param @return    设定文件 
	* @return PageInfoDto    返回类型 
	* @throws
	 */
	public PageInfoDto findRebateDetail(String logId,String dealerCode,Map<String, String> queryParam) {
		List params = new ArrayList<>();
		String sql = queryDetailRebsInfo(logId,dealerCode,queryParam, params);
		PageInfoDto pageInfoDto = OemDAOUtil.pageQuery(sql, params);
		return pageInfoDto;
	}
	/**
	 * 返利核算明细查询
	 * @param logId
	 * @param dealerCode
	 * @param queryParam
	 * @param params
	 * @return
	 */
	public PageInfoDto findRebateDetail1(Map<String, String> queryParam) {
		List params = new ArrayList<>();
		String sql = queryDetailRebsInfo1(queryParam, params);
		PageInfoDto pageInfoDto = OemDAOUtil.pageQuery(sql, params);
		return pageInfoDto;
	}
	

	private String queryDetailRebsInfo1(Map<String, String> queryParam, List<Object> params) {
		StringBuffer sql = new StringBuffer();
		sql.append("select  distinct trc.REBATE_ID, trc.LOG_ID, trc.BUSINESS_POLICY_NAME, trcm.BUSINESS_POLICY_TYPE, \n");
		sql.append("  		trc.APPLICABLE_TIME, DATE_FORMAT(trc.RELEASE_DATE,'%Y-%m-%d %H:%i:%s') RELEASE_DATE, trc.START_MONTH, trc.END_MONTH, \n");
		sql.append("  		trc.DEALER_CODE, trc.DEALER_NAME, trc.VIN, trc.MODEL_NAME, trc.COUNT,  \n");
		sql.append("  		trc.NOMAL_BONUS, trc.SPECIAL_BONUS, trc.BACK_BONUSES_EST, trc.BACK_BONUSES_DOWN,  \n");
		sql.append("  		trc.NEW_INCENTIVES, trc.OEM_COMPANY_ID, \n");
		sql.append("  	    trc.CREATE_BY, trc.CREATE_DATE, trc.UPDATE_BY, trc.UPDATE_DATE,vm.BRAND_CODE,vm.SERIES_CODE \n");
		sql.append("from TT_REBATE_CALCULATE trc  \n");
		sql.append("left join TT_REBATE_CALCULATE_MANAGE trcm on trc.LOG_ID= trcm.LOG_ID\n");
		/* add sumin by 查询品牌，车系通过vin关联 start */
		sql.append("LEFT JOIN  TM_VEHICLE_DEC TV on TV.VIN = trc.VIN \n");
		sql.append("LEFT  JOIN ("+getVwMaterialSql()+")  vm on TV.MATERIAL_ID = vm.MATERIAL_ID \n");
		sql.append("WHERE 1=1\n");
		
		//导入车型
		if (!StringUtils.isNullOrEmpty(queryParam.get("MODEL_NAME"))) {
			params.add(" and trc.MODEL_NAME = ? ");
			params.add(queryParam.get("MODEL_NAME"));
		}
		//VIN
		if (!StringUtils.isNullOrEmpty(queryParam.get("VIN"))) {
			sql.append(" and trc.VIN = ? ");
			params.add(queryParam.get("VIN"));
		}
		//政策名称
		if (!StringUtils.isNullOrEmpty(queryParam.get("BUSINESS_POLICY_NAME"))) {
			sql.append(" and trc.BUSINESS_POLICY_NAME = ? ");
			params.add(queryParam.get("BUSINESS_POLICY_NAME"));
		}
		//经销商代码
        if(Utility.testIsNotNull(queryParam.get("dealerCode"))){
        	sql.append(" and  trc.DEALER_CODE in( '" + queryParam.get("dealerCode") +"')\n");
        }
        //品牌
  		if (!StringUtils.isNullOrEmpty(queryParam.get("brandCode"))) {
  			sql.append(" and vm.BRAND_ID = ? ");
  			System.err.println(queryParam.get("brandCode"));
  			params.add(queryParam.get("brandCode"));
  		}
  	    //车系
  		if (!StringUtils.isNullOrEmpty(queryParam.get("seriesName"))) {
  			sql.append(" and vm.SERIES_ID = ? ");
  			params.add(queryParam.get("seriesName"));
  		}
  		//商务政策类型
  		if (!StringUtils.isNullOrEmpty(queryParam.get("businessPolicyType"))) {
  			sql.append(" and trcm.BUSINESS_POLICY_TYPE = ? ");
  			params.add(queryParam.get("businessPolicyType"));
  		}
		//政策开始时间
  		if (!StringUtils.isNullOrEmpty(queryParam.get("beginDate"))) {
  			sql.append("   AND DATE(trc.START_MONTH) >= ? \n");
  			params.add(queryParam.get("beginDate"));
  		}
  		//政策结束时间
  		if (!StringUtils.isNullOrEmpty(queryParam.get("endDate"))) {
  			sql.append("   AND DATE(trc.END_MONTH) <= ? \n");
  			params.add(queryParam.get("endDate"));
  		}
		System.err.println(sql.toString());
		return sql.toString();
	}
	/**
	 * 返利核算明细查询静态部分
	 * @param logId
	 * @param string
	 * @param pageSize
	 * @param curPage
	 * @return
	 */
	private String queryDetailRebsInfo(String logId,String dealerCode,Map<String, String> queryParam, List<Object> params) {
		StringBuffer sql = new StringBuffer();
		sql.append("select  trc.REBATE_ID, trc.LOG_ID, trc.BUSINESS_POLICY_NAME,\n");
		sql.append("  		trc.APPLICABLE_TIME,  DATE_FORMAT(trc.RELEASE_DATE,'%Y-%m-%d')  RELEASE_DATE, trc.START_MONTH, trc.END_MONTH, \n");
		sql.append("  		trc.DEALER_CODE, trc.DEALER_NAME, trc.VIN, trc.MODEL_NAME, trc.COUNT,  \n");
		sql.append("  		trc.NOMAL_BONUS, trc.SPECIAL_BONUS, trc.BACK_BONUSES_EST, trc.BACK_BONUSES_DOWN,  \n");
		sql.append("  		trc.NEW_INCENTIVES, trc.OEM_COMPANY_ID, \n");
		sql.append("  	    trc.CREATE_BY, trc.CREATE_DATE, trc.UPDATE_BY, trc.UPDATE_DATE \n");
		sql.append("from TT_REBATE_CALCULATE trc  \n");
		sql.append("where 1=1 ");
		if(Utility.testIsNotNull(logId)){
			sql.append(" AND trc.LOG_ID  =  " +logId+" \n");
    	}
		if(Utility.testIsNotNull(dealerCode)){
			sql.append(" AND trc.DEALER_CODE  = '" +dealerCode+"'\n");
		}	
		return sql.toString();
	}
	
	
		
	/**
	 * 返利核算政策查询
	 * @param dealerCode
	 * @param execAuthor
	 * @param phone
	 * @param inputDate
	 * @param pageSize
	 * @param curPage
	 * @return
	 */
	private String getQuerySql(Map<String, String> queryParam, List<Object> params) {
	StringBuffer sql = new StringBuffer();
	sql.append("select  distinct trc.REBATE_ID, trc.LOG_ID, trc.BUSINESS_POLICY_NAME, (CASE trcm.BUSINESS_POLICY_TYPE WHEN '91181001' THEN '销售' WHEN '91181002' THEN '售后' WHEN '91181003' THEN '网络'  ELSE '' END) BUSINESS_POLICY_TYPE, \n");
	sql.append("  		trc.APPLICABLE_TIME, DATE_FORMAT(trc.RELEASE_DATE,'%Y-%m-%d') RELEASE_DATE, trc.START_MONTH, trc.END_MONTH, \n");
	sql.append("  		trc.DEALER_CODE, trc.DEALER_NAME, trc.VIN, trc.MODEL_NAME, trc.COUNT,  \n");
	sql.append("  		trc.NOMAL_BONUS, trc.SPECIAL_BONUS, trc.BACK_BONUSES_EST, trc.BACK_BONUSES_DOWN,  \n");
	sql.append("  		trc.NEW_INCENTIVES, trc.OEM_COMPANY_ID, \n");
	sql.append("  	    trc.CREATE_BY,  DATE_FORMAT(trc.CREATE_DATE,'%Y-%m-%d %H:%i:%s') CREATE_DATE, trc.UPDATE_BY, trc.UPDATE_DATE \n");
	sql.append("from TT_REBATE_CALCULATE trc  \n");
	sql.append("left join TT_REBATE_CALCULATE_MANAGE trcm on trc.LOG_ID= trcm.LOG_ID\n");
	/* add sumin by 查询品牌，车系通过vin关联 start */
	sql.append("LEFT JOIN  TM_VEHICLE_DEC TV on TV.VIN = trc.VIN \n");
	sql.append("LEFT  JOIN ("+getVwMaterialSql()+") vm on TV.MATERIAL_ID = vm.MATERIAL_ID \n");
	sql.append("WHERE 1=1\n");
	if (!StringUtils.isNullOrEmpty(queryParam.get("brandName"))) {
		sql.append(" and trc.BRAND_NAME = ?");
		params.add(queryParam.get("brandName"));
	}
	if (!StringUtils.isNullOrEmpty(queryParam.get("seriesName"))) {
		sql.append(" and trc.series_name = ?");
		params.add(queryParam.get("seriesName"));
	}
	//导入车型
	if (!StringUtils.isNullOrEmpty(queryParam.get("MODEL_NAME"))) {
		sql.append(" and trc.MODEL_NAME = ? ");
		params.add(queryParam.get("MODEL_NAME"));
	}
	//VIN
	if (!StringUtils.isNullOrEmpty(queryParam.get("VIN"))) {
		sql.append(" and trc.VIN = ? ");
		params.add(queryParam.get("VIN"));
	}
	//经销商名称
	if (!StringUtils.isNullOrEmpty(queryParam.get("BUSINESS_POLICY_NAME"))) {
		sql.append(" and trc.BUSINESS_POLICY_NAME = ? ");
		params.add(queryParam.get("BUSINESS_POLICY_NAME"));
	}
	//经销商类型
	if (!StringUtils.isNullOrEmpty(queryParam.get("businessPolicyType"))) {
		sql.append(" and trcm.BUSINESS_POLICY_TYPE = ? ");
		params.add(queryParam.get("businessPolicyType"));
	}
	//经销商代码
	if (!StringUtils.isNullOrEmpty(queryParam.get("dealerCode"))) {
		sql.append(" and trc.DEALER_CODE = ? ");
		params.add(queryParam.get("dealerCode"));
	}
	if (!StringUtils.isNullOrEmpty(queryParam.get("beginDate"))) {
		sql.append("   AND DATE(trc.START_MONTH) >= ? \n");
		params.add(queryParam.get("beginDate"));
	}
	if (!StringUtils.isNullOrEmpty(queryParam.get("endDate"))) {
		sql.append("   AND DATE(trc.END_MONTH) <= ? \n");
		params.add(queryParam.get("endDate"));
	}
	
	return sql.toString();
	
}
	
	/**
	 * 下载
	 * 
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public List<Map> queryEmpInfoforExport(Map<String, String> queryParam) throws Exception {
		List<Object> params = new ArrayList<Object>();
		String sql = getQuerySql(queryParam, params);
		return OemDAOUtil.findAll(sql.toString(), params);

	}
	
	public List<Map> queryEmpInfoforExport1(Map<String, String> queryParam) throws Exception {
		List<Object> params = new ArrayList<Object>();
		String sql = getQuerySql1(queryParam, params);
		return OemDAOUtil.findAll(sql.toString(), params);

	}
	
	private String getQuerySql1(Map<String, String> queryParam, List<Object> params) {
		StringBuffer sql = new StringBuffer();
		sql.append("select  distinct trc.REBATE_ID, trc.LOG_ID, trc.BUSINESS_POLICY_NAME, trcm.BUSINESS_POLICY_TYPE, \n");
		sql.append("  		trc.APPLICABLE_TIME, DATE_FORMAT(trc.RELEASE_DATE,'%Y-%m-%d %H:%i:%s') RELEASE_DATE, trc.START_MONTH, trc.END_MONTH, \n");
		sql.append("  		trc.DEALER_CODE, trc.DEALER_NAME, trc.VIN, trc.MODEL_NAME, trc.COUNT,  \n");
		sql.append("  		trc.NOMAL_BONUS, trc.SPECIAL_BONUS, trc.BACK_BONUSES_EST, trc.BACK_BONUSES_DOWN,  \n");
		sql.append("  		trc.NEW_INCENTIVES, trc.OEM_COMPANY_ID, \n");
		sql.append("  	    trc.CREATE_BY, trc.CREATE_DATE, trc.UPDATE_BY, trc.UPDATE_DATE \n");
		sql.append("from TT_REBATE_CALCULATE trc  \n");
		sql.append("left join TT_REBATE_CALCULATE_MANAGE trcm on trc.LOG_ID= trcm.LOG_ID\n");
		/* add sumin by 查询品牌，车系通过vin关联 start */
		sql.append("LEFT JOIN  TM_VEHICLE_DEC TV on TV.VIN = trc.VIN \n");
		sql.append("LEFT  JOIN ("+getVwMaterialSql()+") vm on TV.MATERIAL_ID = vm.MATERIAL_ID \n");
		sql.append("WHERE 1=1\n");
		
		//导入车型
		if (!StringUtils.isNullOrEmpty(queryParam.get("MODEL_NAME"))) {
			params.add(" and trc.MODEL_NAME = ? ");
			params.add(queryParam.get("MODEL_NAME"));
		}
		//VIN
		if (!StringUtils.isNullOrEmpty(queryParam.get("VIN"))) {
			sql.append(" and trc.VIN = ? ");
			params.add(queryParam.get("VIN"));
		}
		//政策名称
		if (!StringUtils.isNullOrEmpty(queryParam.get("BUSINESS_POLICY_NAME"))) {
			sql.append(" and trc.BUSINESS_POLICY_NAME = ? ");
			params.add(queryParam.get("BUSINESS_POLICY_NAME"));
		}
		//经销商代码
        if(Utility.testIsNotNull(queryParam.get("dealerCode"))){
        	sql.append(" and  trc.DEALER_CODE in( '" + queryParam.get("dealerCode") +"')\n");
        }
        //品牌
  		if (!StringUtils.isNullOrEmpty(queryParam.get("brandCode"))) {
  			sql.append(" and vm.BRAND_CODE = ? ");
  			params.add(queryParam.get("brandCode"));
  		}
  	    //车系
  		if (!StringUtils.isNullOrEmpty(queryParam.get("seriesName"))) {
  			sql.append(" and vm.SERIES_CODE = ? ");
  			params.add(queryParam.get("seriesName"));
  		}
  		//商务政策类型
  		if (!StringUtils.isNullOrEmpty(queryParam.get("businessPolicyType"))) {
  			sql.append(" and trcm.BUSINESS_POLICY_TYPE = ? ");
  			params.add(queryParam.get("businessPolicyType"));
  		}
		//政策开始时间
  		if (!StringUtils.isNullOrEmpty(queryParam.get("beginDate"))) {
  			sql.append("   AND DATE(trc.START_MONTH) >= ? \n");
  			params.add(queryParam.get("beginDate"));
  		}
  		//政策结束时间
  		if (!StringUtils.isNullOrEmpty(queryParam.get("endDate"))) {
  			sql.append("   AND DATE(trc.END_MONTH) <= ? \n");
  			params.add(queryParam.get("endDate"));
  		}
		System.err.println(sql.toString());
		return sql.toString();
	}
	
	/**
	 * 返利核算汇总查询(DLR)明细
	 */
	public PageInfoDto findRebateDetail2(String logId, String dealerCode, Map<String, String> queryParam) {
		List params = new ArrayList<>();
		StringBuffer sql = new StringBuffer();
		sql.append("select  trc.REBATE_ID, trc.LOG_ID, trc.BUSINESS_POLICY_NAME,\n");
		sql.append("  		trc.APPLICABLE_TIME,  DATE_FORMAT(trc.RELEASE_DATE,'%Y-%m-%d %H:%i:%s') RELEASE_DATE, trc.START_MONTH, trc.END_MONTH, \n");
		sql.append("  		trc.DEALER_CODE, trc.DEALER_NAME, trc.VIN, trc.MODEL_NAME, trc.COUNT,  \n");
		sql.append("  		trc.NOMAL_BONUS, trc.SPECIAL_BONUS, trc.BACK_BONUSES_EST, trc.BACK_BONUSES_DOWN,  \n");
		sql.append("  		trc.NEW_INCENTIVES, trc.OEM_COMPANY_ID, \n");
		sql.append("  	    trc.CREATE_BY, trc.CREATE_DATE, trc.UPDATE_BY, trc.UPDATE_DATE \n");
		sql.append("from TT_REBATE_CALCULATE trc  \n");
		sql.append("where 1=1 ");
		if(Utility.testIsNotNull(logId)){
			sql.append(" AND trc.LOG_ID  =  " +logId+" \n");
    	}
		if(Utility.testIsNotNull(dealerCode)){
			sql.append(" AND trc.DEALER_CODE  = '" +dealerCode+"'\n");
    	}
		PageInfoDto pageInfoDto = OemDAOUtil.pageQuery(sql.toString(), params);
		return pageInfoDto;
	}
}
