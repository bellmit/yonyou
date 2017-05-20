package com.yonyou.dms.manage.dao.salesPlanManager;

import java.util.ArrayList;
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
* @ClassName: DlrForecastQueryDao 
* @Description: 生产订单管理 
* @author zhengzengliang
* @date 2017年2月17日 下午5:48:15 
*
 */
@SuppressWarnings("rawtypes")
@Repository
public class DlrForecastQueryDao extends OemBaseDAO{

	/**
	 * 
	* @Title: getDealerMonthPlanYearList 
	* @Description: 生产订单确认上报（获取任务编号） 
	* @param @param queryParam
	* @param @return    设定文件 
	* @return List<Map>    返回类型 
	* @throws
	 */
	public List<Map> getDealerMonthPlanYearList(Map<String, String> queryParam) {
		//获取当前用户
//       LoginInfoDto loginInfo = ApplicationContextHelper.getBeanByType(LoginInfoDto.class);
		StringBuilder sqlSb = new StringBuilder();
		sqlSb.append("select distinct TASK_ID from TT_VS_MONTHLY_FORECAST where 1=1 and status="+OemDictCodeConstants.TT_VS_MONTHLY_FORECAST_AUDIT + "\n");
		List<Object> params = new ArrayList<Object>();
		/*if(!StringUtils.isNullOrEmpty(loginInfo.getDealerId())){
			sqlSb.append(" and DEALER_ID = ? \n");
			params.add(loginInfo.getDealerId());
		}*/
		if(!StringUtils.isNullOrEmpty(queryParam.get("yearcode"))){
			sqlSb.append(" and  FORECAST_YEAR = ? \n");
			params.add(queryParam.get("yearcode"));
		}
		if(!StringUtils.isNullOrEmpty(queryParam.get("monthcode"))){
			sqlSb.append(" and FORECAST_MONTH = (select tc.CODE_CN_DESC from TC_CODE tc where 1=1 and tc.CODE_ID = ?) \n");
			params.add(queryParam.get("monthcode"));
		}
		//执行查询操作
		List<Map> result = OemDAOUtil.findAll(sqlSb.toString(),params);
		return result;
	}

	/**
	 * 
	* @Title: getDlrForecastQueryList2 
	* @Description: 生产订单确认上报（查询）
	* @param @param queryParam
	* @param @return    设定文件 
	* @return PageInfoDto    返回类型 
	* @throws
	 */
	public PageInfoDto getDlrForecastQueryList2(Map<String, String> queryParam) {
		List<Object> params = new ArrayList<Object>();
		String sql = getDlrForecastQueryList2Sql(queryParam,params);
		PageInfoDto pageInfoDto = OemDAOUtil.pageQuery(sql,params);
		return pageInfoDto;
	}
	private String getDlrForecastQueryList2Sql(Map<String, String> queryParam, 
			List<Object> params) {
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT DISTINCT TVMF.FORECAST_ID,vw.MODEL_CODE,vw.MATERIAL_ID,VW.BRAND_NAME,VW.SERIES_NAME,VW.GROUP_ID,VW.GROUP_NAME,VW.MODEL_YEAR,\n");
		sql.append("            TVRT.TASK_ID,VW.SERIES_CODE,vw.COLOR_CODE,VW.TRIM_CODE,\n");
		sql.append("			VW.TRIM_NAME,VW.COLOR_NAME, POS.SERIAL_NUMBER,TVMFD.DETAIL_ID,\n");
		sql.append("            TVMFDC.DETAIL_COLOR_ID,IFNULL(TVMFDC.REQUIRE_NUM,0) REQUIRE_NUM,IFNULL(TVMFDC.CONFIRM_NUM,0) CONFIRM_NUM,IFNULL(TVMFDC.REPORT_NUM,0) REPORT_NUM\n");
		sql.append(" 		FROM ("+getVwMaterialSql()+")                   VW,\n");
		sql.append(" 		TT_VS_MONTHLY_FORECAST            TVMF,\n");
		sql.append(" 		TT_VS_MONTHLY_FORECAST_DETAIL_DCS     TVMFD,\n");
		sql.append(" 		TT_VS_RETAIL_TASK				  TVRT,\n");
		sql.append(" 		TT_FORECAST_MATERIAL              TFM, \n");
		sql.append(" 		PRO_ORDER_SERIAL                   POS, \n");
        sql.append("        TT_VS_MONTHLY_FORECAST_DETAIL_COLOR     TVMFDC \n");
		sql.append("	 WHERE  \n");
		sql.append("	TVRT.TASK_ID=TFM.TASK_ID AND TFM.MATERIAL_ID=VW.MATERIAL_ID \n");
		sql.append(" 	AND TVMF.TASK_ID = TVRT.TASK_ID\n");
		sql.append(" 	AND TVMFDC.DETAIL_COLOR_ID = POS.DETAIL_COLOR_ID\n");
		sql.append(" 	AND TVMF.FORECAST_ID = TVMFD.FORECAST_ID \n");
		//获取当前用户
        /*LoginInfoDto loginInfo = ApplicationContextHelper.getBeanByType(LoginInfoDto.class);
        if(!StringUtils.isNullOrEmpty(loginInfo.getDealerId())){
			sql.append(" AND TVMF.DEALER_ID = ?");
			params.add(loginInfo.getDealerId());
		}*/
        //年
  		if(!StringUtils.isNullOrEmpty(queryParam.get("yearName"))){
  			sql.append(" and TVMF.FORECAST_YEAR = ?");
  			params.add(queryParam.get("yearName"));
  		}
  		//月
  		if(!StringUtils.isNullOrEmpty(queryParam.get("monthName"))){
  			sql.append(" and TVMF.FORECAST_MONTH = (select tc.CODE_CN_DESC from TC_CODE tc where 1=1 and tc.CODE_ID = ?)");
  			params.add(queryParam.get("monthName"));
  		}
  		//任务编号
  		if(!StringUtils.isNullOrEmpty(queryParam.get("taskCodeName"))){
  			sql.append(" and TVRT.TASK_ID = ?");
  			params.add(queryParam.get("taskCodeName"));
  		}
		sql.append("  AND TVMFD.DETAIL_ID = TVMFDC.DETAIL_ID   and vw.MATERIAL_ID = TVMFDC.MATERIAL_ID and TVMFDC.REQUIRE_NUM>0 \n");
	//	sql.append("    ORDER BY vw.BRAND_NAME,vw.SERIES_NAME,vw.GROUP_NAME,vw.COLOR_NAME,vw.TRIM_NAME,vw.MODEL_YEAR\n");
		
		return sql.toString();
	}

	/**
	 * 
	* @Title: DlrfindBySerialNumber 
	* @Description:   生产订单序列号跟踪(经销商)（查询）
	* @param @param queryParam
	* @param @return    设定文件 
	* @return PageInfoDto    返回类型 
	* @throws
	 */
	public PageInfoDto DlrfindBySerialNumber(Map<String, String> queryParam) {
		List<Object> params = new ArrayList<Object>();
		String sql = getDlrfindBySerialNumberSql(queryParam,params);
		PageInfoDto pageInfoDto = OemDAOUtil.pageQuery(sql,params);
		return pageInfoDto;
	}
	private String getDlrfindBySerialNumberSql(Map<String, String> queryParam, List<Object> params) {
		StringBuilder sql = new StringBuilder();
		sql.append(" SELECT tvmf.TASK_ID,TOR2.ORG_NAME BIG_AREA,TOR.ORG_NAME SMALL_AREA,TD.DEALER_NAME,TD.DEALER_CODE,TVMF.FORECAST_YEAR,TVMF.FORECAST_MONTH,CONCAT(TVMF.FORECAST_YEAR,'-',TVMF.FORECAST_MONTH) MONTH,POS.SERIAL_NUMBER PON,VM.BRAND_NAME,VM.GROUP_NAME,VM.COLOR_NAME,VM.TRIM_NAME,VM.MODEL_YEAR,VM.SERIES_NAME \n");
		 sql.append(" 	,POS.STATUS FROM PRO_ORDER_SERIAL POS, TT_VS_MONTHLY_FORECAST_DETAIL_COLOR TVMFDC, ("+getVwMaterialSql()+") VM,TT_VS_MONTHLY_FORECAST TVMF,TM_DEALER TD,\n");
		 sql.append(" 	 TT_VS_MONTHLY_FORECAST_DETAIL_DCS TVMFD, TM_DEALER_ORG_RELATION         TDOR,TM_ORG TOR,TM_ORG TOR2 \n");
		 sql.append("WHERE TVMFDC.DETAIL_COLOR_ID=POS.DETAIL_COLOR_ID  AND VM.MATERIAL_ID=TVMFDC.MATERIAL_ID AND TVMFD.DETAIL_ID=TVMFDC.DETAIL_ID \n");
		 sql.append(" 		  AND TVMFD.FORECAST_ID=TVMF.FORECAST_ID  AND TD.DEALER_ID=TVMF.DEALER_ID\n");
		 sql.append(" 		AND TOR.ORG_ID=TDOR.ORG_ID\n");
		 sql.append("  AND TD.DEALER_ID=TDOR.DEALER_ID and TOR.PARENT_ORG_ID=TOR2.ORG_ID\n");  
		 //品牌
		if(!StringUtils.isNullOrEmpty(queryParam.get("brandCode"))){
			sql.append(" AND VM.BRAND_CODE = ? \n");
			params.add(queryParam.get("brandCode"));
		}
		//车系
		if(!StringUtils.isNullOrEmpty(queryParam.get("seriesName"))){
			sql.append(" AND VM.SERIES_CODE = ? \n");
			params.add(queryParam.get("seriesName"));
		}
		//车款
		if(!StringUtils.isNullOrEmpty(queryParam.get("groupName"))){
			sql.append(" AND VM.MODEL_CODE = ? \n");
			params.add(queryParam.get("groupName"));
		}
		//年
		if(!StringUtils.isNullOrEmpty(queryParam.get("yearName"))){
			sql.append(" AND TVMF.FORECAST_YEAR = ? \n");
			params.add(queryParam.get("yearName"));
		}
		//月
		if(!StringUtils.isNullOrEmpty(queryParam.get("monthName"))){
			sql.append(" AND TVMF.FORECAST_MONTH = (select tc.CODE_CN_DESC from TC_CODE tc where 1=1 and tc.CODE_ID = ?) \n");
			params.add(queryParam.get("monthName"));
		}
		//任务编号
		if(!StringUtils.isNullOrEmpty(queryParam.get("taskCodeName"))){
			sql.append(" AND TVMF.TASK_ID = ? \n");
			params.add(queryParam.get("taskCodeName"));
		}
		//PON号
		if(!StringUtils.isNullOrEmpty(queryParam.get("soNo"))){
			sql.append(" AND POS.SERIAL_NUMBER = ? \n");
			params.add(queryParam.get("soNo"));
		}
		//状态
		if(!StringUtils.isNullOrEmpty(queryParam.get("status"))){
			sql.append(" AND POS.STATUS = ? \n");
			params.add(queryParam.get("status"));
		}
		//获取当前用户
        LoginInfoDto loginInfo = ApplicationContextHelper.getBeanByType(LoginInfoDto.class);
        if(!StringUtils.isNullOrEmpty(loginInfo.getDealerId())){
			sql.append(" AND TVMF.DEALER_ID = ? \n");
			params.add(loginInfo.getDealerId());
		}
		// sql.append(" ORDER BY BRAND_NAME,GROUP_NAME,SERIES_NAME,POS.SERIAL_NUMBER \n ");
		return sql.toString();
	}
	
	
	
	

}
