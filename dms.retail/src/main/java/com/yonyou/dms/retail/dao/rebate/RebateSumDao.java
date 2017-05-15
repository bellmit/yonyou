package com.yonyou.dms.retail.dao.rebate;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.yonyou.dms.framework.DAO.OemBaseDAO;
import com.yonyou.dms.framework.DAO.OemDAOUtil;
import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.framework.domain.LoginInfoDto;
import com.yonyou.dms.function.utils.common.StringUtils;
/**
 * 
* @ClassName: RebateSumDao 
* @Description: 经销商返利核算汇总查询（OEM)
* @author zhengzengliang 
* @date 2017年4月10日 下午3:06:05 
*
 */
@SuppressWarnings({"rawtypes","unchecked"})
@Repository
public class RebateSumDao extends OemBaseDAO{
	
	/**
	 * 
	* @Title: findRebateSum 
	* @Description: 经销商返利核算汇总查询(OEM) 
	* @param @param queryParam
	* @param @return    设定文件 
	* @return PageInfoDto    返回类型 
	* @throws
	 */
	public PageInfoDto findRebateSum(Map<String, String> queryParam) {
		List params = new ArrayList<>();
		String sql = getQuerySql(queryParam, params);
		PageInfoDto pageInfoDto = OemDAOUtil.pageQuery(sql, params);
		return pageInfoDto;
	}
	/**
	 * 经销商返利核算汇总查询(OEM)
	 * @param bussType 
	 * @return
	 */
	private String getQuerySql(Map<String, String> queryParam, List<Object> params) {	
		StringBuffer sql = new StringBuffer();
		sql.append(" select t.* from ( select trcm.BUSINESS_POLICY_NAME,trcm.LOG_ID,(CASE trcm.BUSINESS_POLICY_TYPE WHEN '91181001' THEN '销售' WHEN '91181002' THEN '售后' WHEN '91181003' THEN '网络'  ELSE '' END) BUSINESS_POLICY_TYPE , \n");
		sql.append("	 trc.APPLICABLE_TIME, \n");
		sql.append(" 	 DATE_FORMAT(trc.RELEASE_DATE,'%Y-%m-%d') RELEASE_DATE, \n");
		sql.append(" 	 trcm.START_MONTH, \n");
		sql.append("	 trcm.END_MONTH, \n");
		sql.append("   	trc.DEALER_CODE, \n");
		sql.append(" 	trc.DEALER_NAME, \n");
		sql.append(" 	sum( trc.NOMAL_BONUS) NOMAL_BONUS,  \n");
		sql.append(" 	sum( trc.SPECIAL_BONUS) SPECIAL_BONUS,  \n");
		sql.append(" 	sum( trc.BACK_BONUSES_EST) BACK_BONUSES_EST ,  \n");
		sql.append(" 	sum( trc.BACK_BONUSES_DOWN)  BACK_BONUSES_DOWN ,  \n");
		sql.append(" 	sum( trc.NEW_INCENTIVES) NEW_INCENTIVES \n");
		sql.append(" from TT_REBATE_CALCULATE_MANAGE trcm  \n");
		sql.append(" left join TT_REBATE_CALCULATE trc on trcm.LOG_ID = trc.LOG_ID \n");
		sql.append(" LEFT JOIN TM_DEALER TD ON TD.DEALER_CODE = trc.DEALER_CODE  \n");
		sql.append("  LEFT JOIN TM_DEALER_ORG_RELATION TDOR ON TDOR.DEALER_ID = TD.DEALER_ID  \n");
		sql.append("	LEFT JOIN TM_ORG TOR3 ON TOR3.ORG_ID = TDOR.ORG_ID AND TOR3.ORG_LEVEL = 3   \n");
		sql.append("LEFT JOIN TM_ORG TOR2 ON TOR3.PARENT_ORG_ID = TOR2.ORG_ID AND TOR2.ORG_LEVEL = 2  \n");
		sql.append("where 1=1  \n");
		//经销商名称
		if (!StringUtils.isNullOrEmpty(queryParam.get("businessPolicyName"))) {
			sql.append(" and trc.BUSINESS_POLICY_NAME = ? ");
			params.add(queryParam.get("businessPolicyName"));
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
		sql.append(" group by trcm.BUSINESS_POLICY_NAME,trcm.LOG_ID,trcm.BUSINESS_POLICY_TYPE, trc.APPLICABLE_TIME,trc.RELEASE_DATE,trcm.START_MONTH,");
		sql.append("  trcm.END_MONTH,trc.DEALER_CODE,trc.DEALER_NAME ) t ");
		System.err.println(sql.toString());
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
	/**
	 * 返利核算明细查询动态部分
	 * @param logId
	 * @param string
	 * @param pageSize
	 * @param curPage
	 * @return
	 */
	public String queryDetailRebdInfo(Map<String, String> queryParam, List<Object> params) {
		StringBuffer sql = new StringBuffer();
		sql.append("  select trcd.ID,       trcd.REBATE_ID,        trcd.DYNAMIC_TITLE,       trcd.DYNAMIC_NAME,        trcd.CREATE_BY,\n");
		sql.append("  trcd.CREATE_DATE, \n");
		sql.append("  trcd.UPDATE_BY, \n");
		sql.append("  trcd.UPDATE_DATE from TT_REBATE_CALCULATE_DYNAMIC trcd  \n");
		sql.append("  left join TT_REBATE_CALCULATE trc on trcd.REBATE_ID = trc.REBATE_ID \n");
		sql.append(" where 1=1  \n");
		if (!StringUtils.isNullOrEmpty(queryParam.get("logId"))) {
			sql.append(" and trc.LOG_ID = ? ");
			params.add(queryParam.get("logId"));
		}
		if (!StringUtils.isNullOrEmpty(queryParam.get("dealerCode"))) {
			sql.append(" and trc.DEALER_CODE = ? ");
			params.add(queryParam.get("dealerCode"));
		}
		return sql.toString();
	}
	/**
	 * 明细查询
	 * @param queryParam
	 * @return
	 */
	
	public List<Map> findRebateSumMX(Map<String, String> queryParam) {
		List params = new ArrayList<>();
		String sql = queryDetailRebdInfo(queryParam, params);
		List<Map> map = OemDAOUtil.findAll(sql, params);
		String sql1 =queryDetailRebsInfo(queryParam, params);
		List<Map> map1 = OemDAOUtil.findAll(sql1, params);
		map.addAll(map1);
		return map;
	}
	/**
	 * 返利核算明细查询静态部分
	 * @param logId
	 * @param string
	 * @param pageSize
	 * @param curPage
	 * @return
	 */
	public String queryDetailRebsInfo(Map<String, String> queryParam, List<Object> params) {
		StringBuffer sql = new StringBuffer();
		sql.append("select  trc.REBATE_ID, trc.LOG_ID, trc.BUSINESS_POLICY_NAME,\n");
		sql.append("  		trc.APPLICABLE_TIME,  to_char(trc.RELEASE_DATE,'yyyy-MM-dd') RELEASE_DATE, trc.START_MONTH, trc.END_MONTH, \n");
		sql.append("  		trc.DEALER_CODE, trc.DEALER_NAME, trc.VIN, trc.MODEL_NAME, trc.COUNT,  \n");
		sql.append("  		trc.NOMAL_BONUS, trc.SPECIAL_BONUS, trc.BACK_BONUSES_EST, trc.BACK_BONUSES_DOWN,  \n");
		sql.append("  		trc.NEW_INCENTIVES, trc.OEM_COMPANY_ID, \n");
		sql.append("  	    trc.CREATE_BY, trc.CREATE_DATE, trc.UPDATE_BY, trc.UPDATE_DATE \n");
		sql.append("from TT_REBATE_CALCULATE trc  \n");
		sql.append("where 1=1 ");
		if (!StringUtils.isNullOrEmpty(queryParam.get("logId"))) {
			sql.append(" and trc.LOG_ID = ? ");
			params.add(queryParam.get("logId"));
		}
		if (!StringUtils.isNullOrEmpty(queryParam.get("dealerCode"))) {
			sql.append(" and trc.DEALER_CODE = ? ");
			params.add(queryParam.get("dealerCode"));
		}
		return sql.toString();
	}
	/**
	 * 明细下载静态
	 * 
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public List<Map> queryDetailRebsInfo(Map<String, String> queryParam) throws Exception {
		List<Object> params = new ArrayList<Object>();
		String sql = queryDetailRebsInfo(queryParam, params);
		return OemDAOUtil.findAll(sql.toString(), params);

	}
	/**
	 * 明细下载动态
	 * 
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public List<Map> queryDetailRebdInfo(Map<String, String> queryParam) throws Exception {
		List<Object> params = new ArrayList<Object>();
		String sql = queryDetailRebdInfo(queryParam, params);
		return OemDAOUtil.findAll(sql.toString(), params);
	}
	
	/**
	 * （下载）返利核算明细查询静态部分
	 * @param logId
	 * @param string
	 * @param pageSize
	 * @param curPage
	 * @return
	 */
	public List<Map> queryDetailDownSt(String logId, String drlFlag, String dealerCode,
			LoginInfoDto logonUser) {
		List<Object> params = new ArrayList<Object>();
		String sql = queryDetailDownStSql(logId, drlFlag, dealerCode, logonUser, params);
		List<Map> list = OemDAOUtil.findAll(sql.toString(), params);
		return list;
	}
	private String queryDetailDownStSql(String logId, String drlFlag, String dealerCode, LoginInfoDto logonUser,
			List<Object> params) {
		StringBuffer sql = new StringBuffer();
		sql.append("select  trc.REBATE_ID, trc.LOG_ID, trc.BUSINESS_POLICY_NAME,\n");
		sql.append("  		trc.APPLICABLE_TIME,  DATE_FORMAT(trc.RELEASE_DATE,'%Y-%m-%d') RELEASE_DATE, trc.START_MONTH, trc.END_MONTH, \n");
		sql.append("  		trc.DEALER_CODE, trc.DEALER_NAME, trc.VIN, trc.MODEL_NAME, trc.COUNT,  \n");
		sql.append("  		trc.NOMAL_BONUS, trc.SPECIAL_BONUS, trc.BACK_BONUSES_EST, trc.BACK_BONUSES_DOWN,  \n");
		sql.append("  		trc.NEW_INCENTIVES, trc.OEM_COMPANY_ID, \n");
		sql.append("  	    trc.CREATE_BY, trc.CREATE_DATE, trc.UPDATE_BY, trc.UPDATE_DATE \n");
		sql.append("from TT_REBATE_CALCULATE trc  \n");
		sql.append("where 1=1 ");
		if(testIsNotNull(logId)){
			sql.append(" AND trc.LOG_ID  =  " +logId+" \n");
    	}
		if(testIsNotNull(dealerCode)){
			sql.append(" AND trc.DEALER_CODE  = '" +dealerCode+"'\n");
    	}
		return sql.toString();
	}
	
	/**
	 * (下载)返利核算明细查询动态部分
	 * @param logId
	 * @param string
	 * @param pageSize
	 * @param curPage
	 * @return
	 */
	public List<Map> queryDetailDownDy(String logId, String drlFlag, String dealerCode,
			LoginInfoDto logonUser) {
		List<Object> params = new ArrayList<Object>();
		String sql = queryDetailDownDySql(logId, drlFlag, dealerCode, logonUser, params);
		List<Map> list = OemDAOUtil.findAll(sql.toString(), params);
		return list;
	}
	private String queryDetailDownDySql(String logId, String drlFlag, String dealerCode, LoginInfoDto logonUser,
			List<Object> params) {
		StringBuffer sql = new StringBuffer();
		sql.append("  select trcd.ID,       trcd.REBATE_ID,        trcd.DYNAMIC_TITLE,       trcd.DYNAMIC_NAME,        trcd.CREATE_BY,\n");
		sql.append("  trcd.CREATE_DATE, \n");
		sql.append("  trcd.UPDATE_BY, \n");
		sql.append("  trcd.UPDATE_DATE from TT_REBATE_CALCULATE_DYNAMIC trcd  \n");
		sql.append("  left join TT_REBATE_CALCULATE trc on trcd.REBATE_ID = trc.REBATE_ID \n");
		sql.append(" where 1=1  \n");
		if(testIsNotNull(logId)){
			sql.append(" AND trc.LOG_ID  =  " +logId+" \n");
    	}
		if(testIsNotNull(dealerCode)){
			sql.append(" AND trc.DEALER_CODE  = '" +dealerCode+"'\n");
    	}
		return sql.toString();
	}
	
	public static boolean testIsNotNull(String src) {
		if (null != src && !"".equals(src) && !"null".equals(src))
			return true;
		return false;
	}
	
	/**
	 * 
	* @Title: pageQueryDetailDownSt 
	* @Description: 经销商返利核算汇总明细下载 
	* @param @param logId
	* @param @param drlFlag
	* @param @param dealerCode
	* @param @param logonUser
	* @param @return    设定文件 
	* @return PageInfoDto    返回类型 
	* @throws
	 */
	public PageInfoDto pageQueryDetailDownSt(String logId, String drlFlag, String dealerCode, LoginInfoDto logonUser) {
		List<Object> params = new ArrayList<Object>();
		String sql = queryDetailDownStSql(logId, drlFlag, dealerCode, logonUser, params);
		PageInfoDto pageInfoDto = OemDAOUtil.pageQuery(sql, params);
		return pageInfoDto;
	}
	
}
