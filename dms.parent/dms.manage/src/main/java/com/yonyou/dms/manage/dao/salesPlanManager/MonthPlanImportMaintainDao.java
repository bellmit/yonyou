package com.yonyou.dms.manage.dao.salesPlanManager;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.yonyou.dms.framework.DAO.OemBaseDAO;
import com.yonyou.dms.framework.DAO.OemDAOUtil;
import com.yonyou.dms.framework.domain.LoginInfoDto;
import com.yonyou.dms.framework.util.bean.ApplicationContextHelper;
import com.yonyou.dms.function.common.OemDictCodeConstants;
import com.yonyou.dms.function.utils.common.StringUtils;
import com.yonyou.dms.manage.domains.DTO.salesPlanManager.TmpVsMonthlyPlanDTO;
import com.yonyou.dms.manage.domains.PO.salesPlanManager.TmpVsMonthlyPlanPO;
/**
 * 
* @ClassName: MonthPlanImportMaintainDao 
* @Description: 目标任务管理
* @author zhengzengliang 
* @date 2017年3月14日 上午10:08:23 
*
 */
@SuppressWarnings("rawtypes")
@Repository
public class MonthPlanImportMaintainDao extends OemBaseDAO{

	/**
	 * 
	* @Title: getSeriesCode 
	* @Description: 获取车系代码 
	* @param @return    设定文件 
	* @return List<Map>    返回类型 
	* @throws
	 */
	public List<Map> getSeriesCode() {
		List<Object> params = new ArrayList<Object>();
		String sql = getSeriesCodeSql(params);
		List<Map> list = OemDAOUtil.findAll(sql,params);
		return list;
	}
	private String getSeriesCodeSql(List<Object> params) {
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT T.GROUP_CODE -- 车系代码 \n");
		sql.append("  FROM TM_VHCL_MATERIAL_GROUP T \n");
		sql.append(" WHERE T.GROUP_LEVEL = 2 \n");
		/*
		 * 写死业务范围 Begin
		 */
		sql.append("   AND T.GROUP_TYPE = '" + OemDictCodeConstants.GROUP_TYPE_IMPORT + "' \n");
		/*
		 * 写死业务范围 End..
		 */
		sql.append("   AND T.STATUS = '" + OemDictCodeConstants.STATUS_ENABLE + "'\n");
		sql.append(" ORDER BY T.GROUP_CODE ASC\n");
		
		return sql.toString();
	}
	
	// 删除月度目标临时表中数据
	public void deleteTmpVsMonthlyPlanByUserId() {
		//获取当前用户
		LoginInfoDto loginInfo = ApplicationContextHelper.getBeanByType(LoginInfoDto.class);
		TmpVsMonthlyPlanPO.delete(" USER_ID = ?", loginInfo.getUserId().toString());
	}
	
	public void setTmpVsMonthlyPlan(TmpVsMonthlyPlanPO tvmpPO, TmpVsMonthlyPlanDTO rowDto,
			Map<String,String> queryParam) {
		tvmpPO.setString("ROW_NUMBER", rowDto.getRowNO());
		tvmpPO.setString("DEALER_CODE", rowDto.getDealerCode());
		String seriesJson = getSeriesJson(rowDto);
		tvmpPO.setString("SERIES_NUM_JSON", seriesJson);
		tvmpPO.setString("CHECK_RESULT", "验证失败：经销商不存在！");
		tvmpPO.setString("CHECK_STATUS", 0);
		tvmpPO.setString("PLAN_YEAR", queryParam.get("planYearName"));
		//根据CODE获取月
	//	List<Map> tdList = getPlayMonth(queryParam.get("planMonthName"));
	//	if(tdList.size() > 0){
		tvmpPO.setString("PLAN_MONTH", queryParam.get("planMonthName"));
	//	}
		tvmpPO.setString("PLAN_TYPE", queryParam.get("planTypeName"));
		//获取当前用户
		LoginInfoDto loginInfo = ApplicationContextHelper.getBeanByType(LoginInfoDto.class);
		tvmpPO.setString("USER_ID", loginInfo.getUserId().toString());
	}
	
	private String getSeriesJson(TmpVsMonthlyPlanDTO rowDto) {
		String seriesJson = "[";
		seriesJson += "{series:'300C',num:'" + rowDto.getSeries300C().toString() + "'},";
		seriesJson += "{series:'CALIBER',num:'" + rowDto.getCaliber().toString() + "'},";
		seriesJson += "{series:'CHEROKEE',num:'" + rowDto.getCherokee().toString() + "'},";
		seriesJson += "{series:'COMPASS 2.0',num:'" + rowDto.getCompass20().toString() + "'},";
		seriesJson += "{series:'COMPASS 2.4',num:'" + rowDto.getCompass24().toString() + "'},";
		seriesJson += "{series:'GRAND CHEROKEE',num:'" + rowDto.getGrandCherokee().toString() + "'},";
		seriesJson += "{series:'GRAND CHEROKEE 3.0',num:'" + rowDto.getGrandCherokee30().toString() + "'},";
		seriesJson += "{series:'GRAND VOYAGER',num:'" + rowDto.getGrandVoyager().toString() + "'},";
		seriesJson += "{series:'JOURNEY',num:'" + rowDto.getJourney().toString() + "'},";
		seriesJson += "{series:'PATRIOT',num:'" + rowDto.getPatriot().toString() + "'},";
		seriesJson += "{series:'WRANGLER',num:'" + rowDto.getWrangler().toString() + "'}";
		seriesJson += "]";
		return seriesJson;
	}
	
	public void setTmpVsMonthlyPlan2(TmpVsMonthlyPlanPO tvmpPO, TmpVsMonthlyPlanDTO rowDto, Map<String,String> queryParam) {
		tvmpPO.setString("ROW_NUMBER", rowDto.getRowNO());
		tvmpPO.setString("DEALER_CODE", rowDto.getDealerCode());
		String seriesJson = getSeriesJson(rowDto);
		tvmpPO.setString("SERIES_NUM_JSON", seriesJson);
		tvmpPO.setString("CHECK_RESULT", "验证失败：经销商代码重复！");
		tvmpPO.setString("CHECK_STATUS", 0);
		tvmpPO.setString("PLAN_YEAR", queryParam.get("planYearName"));
		//根据CODE获取月
	//	List<Map> tdList = getPlayMonth(queryParam.get("planMonthName"));
	//	if(tdList.size() > 0){
			tvmpPO.setString("PLAN_MONTH", queryParam.get("planMonthName"));
	//	}
		tvmpPO.setString("PLAN_TYPE", queryParam.get("planTypeName"));
		//获取当前用户
		LoginInfoDto loginInfo = ApplicationContextHelper.getBeanByType(LoginInfoDto.class);
		tvmpPO.setString("USER_ID", loginInfo.getUserId().toString());
		
	}
	
	public void setTmpVsMonthlyPlan3(TmpVsMonthlyPlanPO tvmpPO, TmpVsMonthlyPlanDTO rowDto, Map<String,String> queryParam) {
		tvmpPO.setString("ROW_NUMBER", rowDto.getRowNO());
		tvmpPO.setString("DEALER_CODE", rowDto.getDealerCode());
		String seriesJson = getSeriesJson(rowDto);
		tvmpPO.setString("SERIES_NUM_JSON", seriesJson);
		tvmpPO.setString("CHECK_RESULT", "验证成功！");
		tvmpPO.setString("CHECK_STATUS", 1);
		tvmpPO.setString("PLAN_YEAR", queryParam.get("planYearName"));
		//根据CODE获取月
	//	List<Map> tdList = getPlayMonth(queryParam.get("planMonthName"));
	//	if(tdList.size() > 0){
		tvmpPO.setString("PLAN_MONTH", queryParam.get("planMonthName"));
	//	}
		tvmpPO.setString("PLAN_TYPE", queryParam.get("planTypeName"));
		//获取当前用户
		LoginInfoDto loginInfo = ApplicationContextHelper.getBeanByType(LoginInfoDto.class);
		tvmpPO.setString("USER_ID", loginInfo.getUserId().toString());
	}
	
	//根据CODE获取月
	public List<Map> getPlayMonth(String playMonth) {
		StringBuilder sqlSb = new StringBuilder();
		List<Object> params2 = new ArrayList<Object>();
        sqlSb.append("select tc.CODE_CN_DESC from TC_CODE tc where 1=1 \n" );
        if(!StringUtils.isNullOrEmpty(playMonth)){
  			sqlSb.append(" AND tc.CODE_ID = ? \n");
  			params2.add(playMonth);
  		}
		List<Map> tdList = OemDAOUtil.findAll(sqlSb.toString(), params2);
		return tdList;
	}
	
	
	/**
	 * 
	* @Title: allMessageQuery 
	* @Description: 查询零售车辆导入数据（临时表） 
	* @param @param type
	* @param @param loginInfo
	* @param @return    设定文件 
	* @return List<Map<String,String>>    返回类型 
	* @throws
	 */
	public List<Map> allMessageQuery(int type, LoginInfoDto loginInfo) {
		List<Object> params = new ArrayList<Object>();
		String sql = getAllMessageQuerySql(params,type, loginInfo);
		List<Map> list = OemDAOUtil.findAll(sql,params);
		return list;
	}
	private String getAllMessageQuerySql(List<Object> params, int type, LoginInfoDto loginInfo) {
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT T.ROW_NUMBER, -- 行号 \n");
		sql.append("       T.DEALER_CODE, -- 经销商代码 \n");
		sql.append("       T.SERIES_NUM_JSON, -- 车系对应数 \n");
		sql.append("       T.CHECK_RESULT, -- 验证结果 \n");
		sql.append("       T.CHECK_STATUS, -- 验证状态 \n");
		sql.append("       D.DEALER_ID -- 经销商ID \n");
		sql.append("  FROM TMP_VS_MONTHLY_PLAN T \n");
		sql.append("  LEFT JOIN TM_DEALER D ON D.DEALER_CODE = T.DEALER_CODE \n");
		sql.append("  WHERE  USER_ID = " + loginInfo.getUserId()+"\n");
		if(type == 1){
			sql.append(" AND T.CHECK_STATUS = 0 \n");
		}
		sql.append(" ORDER BY (T.ROW_NUMBER + 0) ASC \n");
		return sql.toString();
	}
	
	public List<Map> selectTtVsMonthlyPlan(TmpVsMonthlyPlanDTO rowDto) {
		List<Object> params = new ArrayList<Object>();
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT tvmp.* FROM tt_vs_monthly_plan tvmp WHERE 1=1  \n");
		if(!StringUtils.isNullOrEmpty(rowDto.getPlanYear())){
  			sql.append(" AND tvmp.PLAN_YEAR = ? \n");
  			params.add(rowDto.getPlanYear());
  		}
		if(!StringUtils.isNullOrEmpty(rowDto.getPlanMonth())){
  			sql.append(" AND tvmp.PLAN_MONTH = ? \n");
  			params.add(rowDto.getPlanMonth());
  		}
		if(!StringUtils.isNullOrEmpty(rowDto.getPlanType())){
  			sql.append(" AND tvmp.PLAN_TYPE=? \n");
  			params.add(rowDto.getPlanType());
  		}
  		sql.append(" AND tvmp.PLAN_VER = 1 \n");
  		sql.append(" AND tvmp.BUSINESS_TYPE = 90081002 \n");
  		sql.append(" AND tvmp.TASK_ID = null \n");
		List<Map> list = OemDAOUtil.findAll(sql.toString(),params);
		return list;
	}
	
	public List<Map> selectTmVhclMaterialGroup(String seriesCode) {
		List<Object> params = new ArrayList<Object>();
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT tvmg.* FROM tm_vhcl_material_group tvmg WHERE 1=1 \n") ;
		if(!StringUtils.isNullOrEmpty(seriesCode)){
  			sql.append(" AND tvmg.GROUP_CODE=? \n");
  			params.add(seriesCode);
  		}
		List<Map> list = OemDAOUtil.findAll(sql.toString(),params);
		return list;
	}

	
	
	
	
	
	
	
	
	
	
	
	
}
