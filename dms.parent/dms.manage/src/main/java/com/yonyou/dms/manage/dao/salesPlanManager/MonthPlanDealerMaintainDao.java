package com.yonyou.dms.manage.dao.salesPlanManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.yonyou.dms.framework.DAO.OemBaseDAO;
import com.yonyou.dms.framework.DAO.OemDAOUtil;
import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.framework.domain.LoginInfoDto;
import com.yonyou.dms.framework.util.bean.ApplicationContextHelper;
import com.yonyou.dms.function.common.OemDictCodeConstants;
import com.yonyou.dms.function.utils.common.StringUtils;
/**
 * 
* @ClassName: MonthPlanDealerMaintainDao 
* @Description: 目标任务管理
* @author zhengzengliang 
* @date 2017年3月2日 下午2:34:48 
*
 */
@Repository
@SuppressWarnings({"rawtypes","unchecked"})
public class MonthPlanDealerMaintainDao extends OemBaseDAO{

	/**
	 * 
	* @Title: getDealerMonthPlanYearList 
	* @Description:  获取月度目标已存在的年列表(经销商) 
	* @param @return    设定文件 
	* @return List<Map>    返回类型 
	* @throws
	 */
	public List<Map> getDealerMonthPlanYearList() {
		List<Object> params = new ArrayList<Object>();
		String sql = getDealerMonthPlanYearListSql(params);
		List<Map> list = OemDAOUtil.findAll(sql,params);
		return list;
	}
	private String getDealerMonthPlanYearListSql(List<Object> params) {
		StringBuilder sql = new StringBuilder();
		sql.append("select distinct PLAN_YEAR from TT_VS_MONTHLY_PLAN where PLAN_TYPE=? and DEALER_ID=?");
		params.add(String.valueOf(OemDictCodeConstants.TARGET_TYPE_01));
		//获取当前用户
		LoginInfoDto loginInfo = ApplicationContextHelper.getBeanByType(LoginInfoDto.class);
		params.add(loginInfo.getDealerId());
		return sql.toString();
	}
	
	/**
	 * 
	* @Title: dealearQueryMonthPlanDealerInfoList 
	* @Description: 查询月度任务(经销商端) 
	* @param @param queryParam
	* @param @return    设定文件 
	* @return PageInfoDto    返回类型 
	* @throws
	 */
	public PageInfoDto dealearQueryMonthPlanDealerInfoList(Map<String, String> queryParam) {
		List<Object> params = new ArrayList<Object>();
		String sql = getdealearQueryMonthPlanDealerInfoListSql(queryParam,params);
		PageInfoDto pageInfoDto = OemDAOUtil.pageQuery(sql,params);
		return pageInfoDto;
	}
	private String getdealearQueryMonthPlanDealerInfoListSql(Map<String, String> queryParam,
			List<Object> params) {
		String season = new String();
		if(!StringUtils.isNullOrEmpty(queryParam.get("seasonName"))){
			season = queryParam.get("seasonName");
		}
		if("第一季度".equals(season)){
			season = "1";
		}else if("第二季度".equals(season)){
			season = "2";
		}else if("第三季度".equals(season)){
			season = "3";
		}else if("第四季度".equals(season)){
			season = "4";
		}
		
		String planYear = new String();
		if(!StringUtils.isNullOrEmpty(queryParam.get("planYearName"))){
			planYear = queryParam.get("planYearName")!=null?queryParam.get("planYearName").toString():"-1";
		}
		String one = null,two = null,three = null;
		if(season.equals("1")){
			one = "1";two = "2";three = "3";
		}else if(season.equals("2")){
			one = "4";two = "5";three = "6";
		}else if(season.equals("3")){
			one = "7";two = "8";three = "9";
		}else if(season.equals("4")){
			one = "10";two = "11";three = "12";
		}
		//获取当前用户信息
		LoginInfoDto loginInfo = ApplicationContextHelper.getBeanByType(LoginInfoDto.class);
		StringBuilder sql = new StringBuilder();
		sql.append("select  TD.DEALER_CODE,TD.DEALER_SHORTNAME,TVMG.GROUP_NAME,\n");
		sql.append(" CAST((SUM(CASE TVMP.PLAN_MONTH WHEN "+one+" THEN TVMPD.SALE_AMOUNT ELSE 0 END)) AS CHAR) ONE,\n");
		sql.append(" CAST((SUM(CASE TVMP.PLAN_MONTH WHEN "+two+" THEN TVMPD.SALE_AMOUNT ELSE 0 END)) AS CHAR) TWO,\n");
		sql.append(" CAST((SUM(CASE TVMP.PLAN_MONTH WHEN "+three+" THEN TVMPD.SALE_AMOUNT ELSE 0 END)) AS CHAR) THREE,\n");
		sql.append(" (SUM(CASE TVMP.PLAN_MONTH WHEN "+one+" THEN TVMPD.SALE_AMOUNT ELSE 0 END) + SUM(CASE TVMP.PLAN_MONTH WHEN "+two+" THEN TVMPD.SALE_AMOUNT ELSE 0 END) + SUM(CASE TVMP.PLAN_MONTH WHEN "+three+" THEN TVMPD.SALE_AMOUNT ELSE 0 END)) TOTAL \n");
		sql.append("   from  TT_VS_MONTHLY_PLAN            TVMP,\n");
		sql.append("  		 TT_VS_MONTHLY_PLAN_DETAIL     TVMPD,\n");
		sql.append("  		 TM_VHCL_MATERIAL_GROUP        TVMG,\n");
		sql.append("		 TM_DEALER                     TD\n");
		sql.append("   where  TVMP.PLAN_ID = TVMPD.PLAN_ID\n");
		sql.append("  	 and  TVMPD.MATERIAL_GROUPID = TVMG.GROUP_ID\n");
		
		/*
		 * 写死业务范围 Begin
		 */
		sql.append("  	 and TVMG.GROUP_TYPE = '" + OemDictCodeConstants.GROUP_TYPE_IMPORT + "' \n");
		/*
		 * 写死业务范围 End..
		 */
		
		sql.append("     and  TVMP.DEALER_ID = TD.DEALER_ID\n");
		String planType = new String();
		if(!StringUtils.isNullOrEmpty(queryParam.get("planTypeName"))){
			sql.append(" and  TVMP.PLAN_TYPE= ? \n");
			params.add(queryParam.get("planTypeName"));
		}
		if(season.equals("1")){
			if(loginInfo.getDealerId() != null){
				sql.append("			 AND(" );
				sql.append("			(TVMP.PLAN_YEAR='"+planYear+"' AND TVMP.PLAN_MONTH='"+1+"' AND TVMP.PLAN_VER = '"+this.getPlanVer(planType,planYear, "1",loginInfo.getDealerId().toString())+"' )");
				sql.append("		or	(TVMP.PLAN_YEAR='"+planYear+"' AND TVMP.PLAN_MONTH='"+2+"' AND TVMP.PLAN_VER = '"+this.getPlanVer(planType,planYear, "2",loginInfo.getDealerId().toString())+"' )");
				sql.append("		or	(TVMP.PLAN_YEAR='"+planYear+"' AND TVMP.PLAN_MONTH='"+3+"' AND TVMP.PLAN_VER = '"+this.getPlanVer(planType,planYear, "3",loginInfo.getDealerId().toString())+"' )");
				sql.append(") \n");
			}
		}
	
		if(season.equals("2")){
			if(loginInfo.getDealerId() != null){
				sql.append("					 		   AND   ( ");
				sql.append("			(TVMP.PLAN_YEAR='"+planYear+"' AND TVMP.PLAN_MONTH='"+4+"' AND TVMP.PLAN_VER = '"+this.getPlanVer(planType,planYear, "4",loginInfo.getDealerId().toString())+"' )");
				sql.append("		or	(TVMP.PLAN_YEAR='"+planYear+"' AND TVMP.PLAN_MONTH='"+5+"' AND TVMP.PLAN_VER = '"+this.getPlanVer(planType,planYear, "5",loginInfo.getDealerId().toString())+"' )");
				sql.append("		or	(TVMP.PLAN_YEAR='"+planYear+"' AND TVMP.PLAN_MONTH='"+6+"' AND TVMP.PLAN_VER = '"+this.getPlanVer(planType,planYear, "6",loginInfo.getDealerId().toString())+"' )");
				sql.append(") \n");
			}
		}
		if(season.equals("3")){
			if(loginInfo.getDealerId() != null){
				sql.append("					 		   AND (");
				sql.append("			(TVMP.PLAN_YEAR='"+planYear+"' AND TVMP.PLAN_MONTH='"+7+"' AND TVMP.PLAN_VER = '"+this.getPlanVer(planType,planYear, "7",loginInfo.getDealerId().toString())+"' )");
				sql.append("		or	(TVMP.PLAN_YEAR='"+planYear+"' AND TVMP.PLAN_MONTH='"+8+"' AND TVMP.PLAN_VER = '"+this.getPlanVer(planType,planYear, "8",loginInfo.getDealerId().toString())+"' )");
				sql.append("		or	(TVMP.PLAN_YEAR='"+planYear+"' AND TVMP.PLAN_MONTH='"+9+"' AND TVMP.PLAN_VER = '"+this.getPlanVer(planType,planYear, "9",loginInfo.getDealerId().toString())+"' )");
				sql.append(") \n");
			}
		}
		if(season.equals("4")){
			if(loginInfo.getDealerId() != null){
				sql.append("					 		   AND (");
				sql.append("			(TVMP.PLAN_YEAR='"+planYear+"' AND TVMP.PLAN_MONTH='"+10+"' AND TVMP.PLAN_VER = '"+this.getPlanVer(planType,planYear, "10",loginInfo.getDealerId().toString())+"' )");
				sql.append("		or	(TVMP.PLAN_YEAR='"+planYear+"' AND TVMP.PLAN_MONTH='"+11+"' AND TVMP.PLAN_VER = '"+this.getPlanVer(planType,planYear, "11",loginInfo.getDealerId().toString())+"' )");
				sql.append("		or	(TVMP.PLAN_YEAR='"+planYear+"' AND TVMP.PLAN_MONTH='"+12+"' AND TVMP.PLAN_VER = '"+this.getPlanVer(planType,planYear, "12",loginInfo.getDealerId().toString())+"' )");
				sql.append(") \n");
			}
		}
		sql.append("     and  TD.DEALER_ID="+loginInfo.getDealerId()+"\n");  //上线代码
//		sql.append("     and  TD.DEALER_ID= 2013090910479313 \n");   //测试代码
		sql.append("   group  by TD.DEALER_CODE,TD.DEALER_SHORTNAME,TVMG.GROUP_NAME\n");
	//	sql.append("   order  by TD.DEALER_CODE,TVMG.GROUP_NAME\n");
		
		return sql.toString();
	}
	public String getPlanVer(String planType,String year,String month,String dealerId){
		String planId = null;
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT PLAN_VER from Tt_Vs_Monthly_Plan  \n");
		sql.append("  where PLAN_YEAR='"+year+"' and PLAN_month = '"+month+"' and dealer_id = '"+dealerId+"' and plan_type = '"+planType+"'   GROUP BY  PLAN_VER   \n");
		List<Map> list =  OemDAOUtil.findAll(sql.toString(), null);
		Map<String, Object> map = new HashMap<String,Object>();
		if(list != null && list.size() > 0){
			map = list.get(0);
			planId = (String) map.get("PLAN_VER");
		}else{
			planId = "0";
		}

		return planId;
	}
	
	/**
	 * 
	* @Title: monthPlanDealerDownLoad 
	* @Description: 月度任务查询   下载（经销商端）
	* @param @param queryParam
	* @param @return    设定文件 
	* @return List<Map>    返回类型 
	* @throws
	 */
	public List<Map> monthPlanDealerDownLoad(Map<String, String> queryParam) {
		List<Object> params = new ArrayList<Object>();
		String sql = getdealearQueryMonthPlanDealerInfoListSql(queryParam,params);
		List<Map> list = OemDAOUtil.findAll(sql,params);
		return list;
	}

	
	
	
	
}
