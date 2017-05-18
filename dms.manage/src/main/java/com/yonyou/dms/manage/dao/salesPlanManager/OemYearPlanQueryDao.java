package com.yonyou.dms.manage.dao.salesPlanManager;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.yonyou.dms.framework.DAO.OemBaseDAO;
import com.yonyou.dms.framework.DAO.OemDAOUtil;
import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.function.common.OemDictCodeConstants;
import com.yonyou.dms.function.utils.common.StringUtils;
/**
 * 
* @ClassName: OemYearPlanQueryDao 
* @Description: 目标任务管理 
* @author zhengzengliang
* @date 2017年3月1日 上午11:07:56 
*
 */
@SuppressWarnings("rawtypes")
@Repository
public class OemYearPlanQueryDao extends OemBaseDAO {

	/**
	 * 
	* @Title: getYearPlanYearList 
	* @Description: 年度目标查询     年列表(总部) 
	* @param @return    设定文件 
	* @return List<Map>    返回类型 
	* @throws
	 */
	public List<Map> getYearPlanYearList() {
		List<Object> params = new ArrayList<Object>();
		String sql = getYearPlanYearListSql(params);
		List<Map> list = OemDAOUtil.findAll(sql,params);
		return list;
	}
	private String getYearPlanYearListSql(List<Object> params) {
		StringBuilder sql = new StringBuilder();
		sql.append("select distinct PLAN_YEAR from TT_VS_YEARLY_PLAN where PLAN_TYPE= "+ OemDictCodeConstants.TARGET_TYPE_01);
		return sql.toString();
	}
	
	/**
	 * 
	* @Title: findPlanVerByYear 
	* @Description: 年度目标查询  版本号列表（总部） 
	* @param @return    设定文件 
	* @return List<Map>    返回类型 
	* @throws
	 */
	public List<Map> findPlanVerByYear() {
		List<Object> params = new ArrayList<Object>();
		String sql = getfindPlanVerByYearSql(params);
		List<Map> planVerList = OemDAOUtil.findAll(sql,params);
		return planVerList;
	}
	private String getfindPlanVerByYearSql(List<Object> params) {
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT DISTINCT  PLAN_VER FROM TT_VS_YEARLY_PLAN\n");
		sql.append("	where PLAN_TYPE = ? AND PLAN_YEAR = ?\n");
		params.add(String.valueOf(OemDictCodeConstants.TARGET_TYPE_01));
		Calendar calendar = Calendar.getInstance();
		int year = calendar.get(Calendar.YEAR);
		params.add(String.valueOf(year));
		sql.append("    ORDER BY PLAN_VER desc");
		return sql.toString();
	}
	
	/**
	 * 
	* @Title: getMaxPlanVer 
	* @Description: 年度目标查询       获取年度最大版本号 
	* @param @return    设定文件 
	* @return List<Map>    返回类型 
	* @throws
	 */
	public List<Map> getMaxPlanVer() {
		List<Object> params = new ArrayList<Object>();
		String sql = "SELECT max(PLAN_VER) PLAN_VER from TT_VS_YEARLY_PLAN";
		List<Map> nowPlanVerList = OemDAOUtil.findAll(sql,params);
		return nowPlanVerList;
	}
	
	/**
	 * 
	* @Title: yearPlanDetailQuery 
	* @Description: 年度目标查询  （总部） 
	* @param @param queryParam
	* @param @return    设定文件 
	* @return PageInfoDto    返回类型 
	* @throws
	 */
	public PageInfoDto yearPlanDetailQuery(Map<String, String> queryParam) {
		List<Object> params = new ArrayList<Object>();
		String sql = getYearPlanDetailQuerySql(queryParam,params);
		PageInfoDto pageInfoDto = OemDAOUtil.pageQuery(sql,params);
		return pageInfoDto;
	}
	private String getYearPlanDetailQuerySql(Map<String, String> queryParam, List<Object> params) {
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT * FROM (SELECT TC.SWT_CODE,TD.DEALER_CODE,\n");
		sql.append("       TD.DEALER_SHORTNAME,\n");
		sql.append("       TVMG.GROUP_NAME,\n");
		for (int i = 0; i < 12; i++) {
			sql.append("       CAST((SUM(CASE TVYPD.PLAN_MONTH WHEN " + (i + 1) + " THEN TVYPD.SALE_AMOUNT ELSE 0 END)) AS CHAR) AMOUNT" + (i + 1)+",\n");
		}
		sql.append("       CAST(SUM(TVYPD.SALE_AMOUNT) AS CHAR) TOTAL_AMOUNT\n");
		sql.append("  FROM TT_VS_YEARLY_PLAN        TVYP,\n");
		sql.append("       TT_VS_YEARLY_PLAN_DETAIL TVYPD,\n");
		sql.append("       TM_VHCL_MATERIAL_GROUP   TVMG,\n");
		sql.append("       TM_DEALER                TD,\n");
		sql.append("       TM_COMPANY               TC,\n");
		sql.append("       TC_USER                  TU\n");
		sql.append(" WHERE TVYP.PLAN_ID = TVYPD.PLAN_ID\n");
		sql.append("   AND TVYPD.MATERIAL_GROUPID = TVMG.GROUP_ID\n");
		/*
		 * 写死业务范围 Begin
		 */
		sql.append("   AND TVMG.GROUP_TYPE = '" + OemDictCodeConstants.GROUP_TYPE_IMPORT + "' \n");
		/*
		 * 写死业务范围 End..
		 */
		sql.append("   AND TVYP.DEALER_ID = TD.DEALER_ID\n");
		sql.append("   AND TC.COMPANY_ID = TD.COMPANY_ID\n");
		sql.append("   AND TU.USER_ID = TVYP.CREATE_BY \n");
		sql.append("   AND TVYP.STATUS = " + OemDictCodeConstants.PLAN_MANAGE_02 + "\n");
		//上传日期  开始
		/*if(!StringUtils.isNullOrEmpty(queryParam.get("lastStockInDateFrom"))){
			sql.append(" AND TVYPD.CREATE_DATE = ? \n");
			params.add(queryParam.get("lastStockInDateFrom"));
		}
		//上传日期  结束
		if(!StringUtils.isNullOrEmpty(queryParam.get("lastStockInDateTo"))){
			sql.append(" AND TVYPD.CREATE_DATE = ? \n");
			params.add(queryParam.get("lastStockInDateTo"));
		}*/
		//目标类型
		if(!StringUtils.isNullOrEmpty(queryParam.get("planTypeName"))){
			sql.append(" AND TVYP.PLAN_TYPE = ? \n");
			params.add(queryParam.get("planTypeName"));
		}
		//选择经销商
		String dealerCode = queryParam.get("dealerCode");
		if(!StringUtils.isNullOrEmpty(dealerCode)){
			dealerCode.substring(0, dealerCode.length()-1);
			sql.append(" AND TD.DEALER_CODE in ("+dealerCode+") \n");
		}
		//选择年份
		if(!StringUtils.isNullOrEmpty(queryParam.get("planYearName"))){
			sql.append(" AND TVYP.PLAN_YEAR = ? \n");
			params.add(queryParam.get("planYearName"));
		}
		//版本号
		if(!StringUtils.isNullOrEmpty(queryParam.get("planVerName"))){
			sql.append(" AND TVYP.PLAN_VER = ? \n");
			params.add(queryParam.get("planVerName"));
		}
		//上传用户
		if(!StringUtils.isNullOrEmpty(queryParam.get("acnt"))){
			sql.append(" AND TU.ACNT = ? \n");
			params.add(queryParam.get("acnt"));
		}
		sql.append(" GROUP BY TD.DEALER_CODE,TC.SWT_CODE,\n");
		sql.append("          TD.DEALER_SHORTNAME,\n");
		sql.append("          TVMG.GROUP_NAME) yearPlanTable\n");
		
		return sql.toString();
	}
	
	/**
	 * 
	* @Title: getOemYearPlanDetailQueryList 
	* @Description: 年度目标查询（下载） 
	* @param @param queryParam
	* @param @return    设定文件 
	* @return List<Map>    返回类型 
	* @throws
	 */
	public List<Map> getOemYearPlanDetailQueryList(Map<String, String> queryParam) {
		List<Object> params = new ArrayList<Object>();
		String sql = getYearPlanDetailQuerySql(queryParam,params);
		List<Map> list = OemDAOUtil.findAll(sql,params);
		return list;
	}
	
}
