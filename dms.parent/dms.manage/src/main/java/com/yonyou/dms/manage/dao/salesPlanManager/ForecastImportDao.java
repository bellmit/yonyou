package com.yonyou.dms.manage.dao.salesPlanManager;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.yonyou.dms.common.domains.PO.salesPlanManage.TmVhclMaterialGroupPO;
import com.yonyou.dms.framework.DAO.OemBaseDAO;
import com.yonyou.dms.framework.DAO.OemDAOUtil;
import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.framework.domain.LoginInfoDto;
import com.yonyou.dms.framework.util.bean.ApplicationContextHelper;
import com.yonyou.dms.function.common.DictCodeConstants;
import com.yonyou.dms.function.common.OemDictCodeConstants;
import com.yonyou.dms.function.exception.ServiceBizException;
import com.yonyou.dms.function.utils.common.StringUtils;
import com.yonyou.dms.manage.domains.DTO.salesPlanManager.TmpVsMonthlyForecastDTO;
import com.yonyou.dms.manage.domains.DTO.salesPlanManager.TtVsMonthlyForecastDTO;
import com.yonyou.dms.manage.domains.PO.salesPlanManager.TmpVsMonthlyForecastPO;
import com.yonyou.dms.manage.domains.PO.salesPlanManager.TtForecastMaterialPO;
import com.yonyou.dms.manage.domains.PO.salesPlanManager.TtVsMonthlyForecastPO;
import com.yonyou.dms.manage.domains.PO.salesPlanManager.TtVsRetailTaskPO;
/**
 * 
* @ClassName: ForecastImportDao 
* @Description: 生产订单任务下发 
* @author zhengzengliang
* @date 2017年2月14日 下午7:03:58 
*
 */
@SuppressWarnings( {"rawtypes", "static-access", "unchecked" })
@Repository
public class ForecastImportDao extends OemBaseDAO{
	
	/**
	 * 
	* @Title: queryByMaterialid1 
	* @Description: 生产订单任务下发（查询） 
	* @param @param queryParam
	* @param @return    设定文件 
	* @return PageInfoDto    返回类型 
	* @throws
	 */
	public PageInfoDto queryByMaterialid1(Map<String, String> queryParam) {
		List<Object> params = new ArrayList<Object>();
		String sql = getqueryByMaterialid1Sql(queryParam,params);
		PageInfoDto pageInfoDto = OemDAOUtil.pageQuery(sql,params);
		return pageInfoDto;
	}
	private String getqueryByMaterialid1Sql(Map<String, String> queryParam,
			List<Object> params) {
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT VM.MATERIAL_ID,VM.COLOR_CODE,VM.COLOR_NAME,VM.TRIM_CODE	\n");
		sql.append("  ,VM.TRIM_NAME,VM.MODEL_YEAR,VM.BRAND_NAME,VM.SERIES_NAME,VM.GROUP_NAME  \n");
		sql.append("  FROM ("+getVwMaterialSql()+") VM WHERE 1=1  \n");
		sql.append(" AND VM.MATERIAL_ID  IN(SELECT TVMF.MATERIAL_ID FROM TMP_VS_MONTHLY_FORECAST TVMF	\n");
		sql.append("	 WHERE STATUS='"+ OemDictCodeConstants.STATUS_ENABLE +"'	\n");
		if(!StringUtils.isNullOrEmpty(queryParam.get("yearName"))){
			sql.append(" and TVMF.FORECAST_YEAR= ?");
			params.add(queryParam.get("yearName"));
		}
		if(!StringUtils.isNullOrEmpty(queryParam.get("monthName"))){
			sql.append(" and TVMF.FORECAST_MONTH = (select tc.CODE_CN_DESC from TC_CODE tc where 1=1 and tc.CODE_ID = ?)");
			params.add(queryParam.get("monthName"));
		}
		sql.append("	 	)	\n");
		return sql.toString();
	}
	
	/**
	 * 
	* @Title: setUserPO 
	* @Description: 生产订单任务下发（新增） 
	* @param @param tvmfPO
	* @param @param tvmfDto    设定文件 
	* @return void    返回类型 
	* @throws
	 */
	public void setMaterialIdsPO(TmpVsMonthlyForecastPO tvmfPO,TmpVsMonthlyForecastDTO tvmfDto){
		tvmfPO.setLong("MATERIAL_ID", tvmfDto.getMaterialId());
        tvmfPO.setInteger("FORECAST_YEAR", tvmfDto.getForecastYear());
        //根据CODE获取年
        List<Object> params = new ArrayList<Object>();
        StringBuilder sbSql = new StringBuilder();
        sbSql.append("select tc.CODE_CN_DESC from TC_CODE tc where 1=1 and tc.CODE_ID = " + tvmfDto.getForecastMonth());
		String sql = sbSql.toString();
		Map m = OemDAOUtil.findFirst(sql,params);
        tvmfPO.setInteger("FORECAST_MONTH", m.get("CODE_CN_DESC"));
        //获取当前用户
        LoginInfoDto loginInfo = ApplicationContextHelper.getBeanByType(LoginInfoDto.class);
        tvmfPO.setLong("CREATE_BY", loginInfo.getUserId());
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String nowDate = sdf.format(new Date());
        tvmfPO.setTimestamp("CREATE_DATE", nowDate);
	}
	
	/**
	 * 
	* @Title: setretailforecastIssuedPO 
	* @Description: 生产订单任务下发
	* @param @param tvrtPO
	* @param @param tvmfDto    设定文件 
	* @return void    返回类型 
	* @throws
	 */
	public void setretailforecastIssuedPO(TtVsRetailTaskPO tvrtPO,TmpVsMonthlyForecastDTO tvmfDto){
		tvrtPO.setBigDecimal("TASK_TYPE", DictCodeConstants.TASK_TYPE_02);
		tvrtPO.setString("YEAR", tvmfDto.getYearName());
		//根据CODE获取年
        List<Object> params = new ArrayList<Object>();
        StringBuilder sbSql = new StringBuilder();
        sbSql.append("select tc.CODE_CN_DESC from TC_CODE tc where 1=1 and tc.CODE_ID = " + tvmfDto.getMonthName());
		String sql = sbSql.toString();
		Map m = OemDAOUtil.findFirst(sql,params);
		tvrtPO.setString("MONTH", m.get("CODE_CN_DESC"));
		String startDate = tvmfDto.getLastStockInDateFrom();
		tvrtPO.setTimestamp("START_DATE", startDate+=" 00:00:00");
		String endDate = tvmfDto.getLastStockInDateFrom();
		tvrtPO.setTimestamp("END_DATE", endDate+=" 23:59:59");
		//获取当前用户
        LoginInfoDto loginInfo = ApplicationContextHelper.getBeanByType(LoginInfoDto.class);
        tvrtPO.setLong("CREATE_BY", loginInfo.getUserId());
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String nowDate = sdf.format(new Date());
        tvrtPO.setTimestamp("CREATE_DATE", nowDate);
	}
	
	/**
	 * 
	* @Title: getMaterialIdList 
	* @Description: 生产订单任务下发
	* @param @param tvmfDto
	* @param @return    设定文件 
	* @return List<Map>    返回类型 
	* @throws
	 */
	public List<Map> getMaterialIdList(TmpVsMonthlyForecastDTO tvmfDto) {
		List<Object> params = new ArrayList<Object>();
		String sql = getMaterialIdSql(tvmfDto,params);
		List<Map> materialIdList = OemDAOUtil.findAll(sql,params);
		return materialIdList;
	}
	private String getMaterialIdSql(TmpVsMonthlyForecastDTO tvmfDto, List<Object> params) {
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT TVMF.MATERIAL_ID FROM TMP_VS_MONTHLY_FORECAST TVMF WHERE STATUS='"+ DictCodeConstants.STATUS_ENABLE +"'\n");
		sql.append("	AND TVMF.FORECAST_YEAR="+tvmfDto.getYearName()+" AND TVMF.FORECAST_MONTH="+tvmfDto.getMonthName()+"	\n");
		return sql.toString();
	}
	
	/**
	 * 
	* @Title: setMaterialIdPO 
	* @Description: 生产订单任务下发
	* @param @param tvmfPO
	* @param @param materialList    设定文件 
	* @return void    返回类型 
	* @throws
	 */
	public void setMaterialIdPO(TtForecastMaterialPO tfmPO,Map materialMap,Long taskId){
		tfmPO.setLong("TASK_ID", taskId);
		tfmPO.setInteger("MATERIAL_ID", materialMap.get("MATERIAL_ID"));
        //获取当前用户
        LoginInfoDto loginInfo = ApplicationContextHelper.getBeanByType(LoginInfoDto.class);
        tfmPO.setLong("CREATE_BY", loginInfo.getUserId());
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String nowDate = sdf.format(new Date());
        tfmPO.setTimestamp("CREATE_DATE", nowDate);
	}
	
	/**
	 * 
	* @Title: deleteTmpVsMonthlyForecast 
	* @Description: 生产订单任务下发完成删除临时表数据 
	* @param @param tvmfDto    设定文件 
	* @return void    返回类型 
	* @throws
	 */
	public void deleteTmpVsMonthlyForecast(TmpVsMonthlyForecastDTO tvmfDto) {
		TmpVsMonthlyForecastPO tvmfPO = new TmpVsMonthlyForecastPO();
		setTmpVsMonthlyForecastPO(tvmfPO,tvmfDto);
		tvmfPO.delete();
	}
	private void setTmpVsMonthlyForecastPO(TmpVsMonthlyForecastPO tvmfPO, TmpVsMonthlyForecastDTO tvmfDto) {
		tvmfPO.setInteger("FORECAST_YEAR", tvmfDto.getYearName());
		 //根据CODE获取年
        List<Object> params = new ArrayList<Object>();
        StringBuilder sbSql = new StringBuilder();
        sbSql.append("select tc.CODE_CN_DESC from TC_CODE tc where 1=1 and tc.CODE_ID = " + tvmfDto.getMonthName());
		String sql = sbSql.toString();
		Map m = OemDAOUtil.findFirst(sql,params);
        tvmfPO.setInteger("FORECAST_MONTH", m.get("CODE_CN_DESC"));
	}
	
	/**
	 * 
	* @Title: getRetailforecasList3 
	* @Description: 生产订单任务录入（查询） 
	* @param @param queryParam
	* @param @return    设定文件 
	* @return PageInfoDto    返回类型 
	* @throws
	 */
	public PageInfoDto getRetailforecasList3(Map<String, String> queryParam) {
		List<Object> params = new ArrayList<Object>();
		String sql = getRetailforecasList3Sql(queryParam,params);
		PageInfoDto pageInfoDto = OemDAOUtil.pageQuery(sql,params);
		return pageInfoDto;
	}
	private String getRetailforecasList3Sql(Map<String, String> queryParam,
			List<Object> params) {
		/*Calendar calendar = Calendar.getInstance();
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd");*/
		 //获取当前用户
        LoginInfoDto loginInfo = ApplicationContextHelper.getBeanByType(LoginInfoDto.class);
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT distinct * FROM ( \n");
		sql.append("SELECT tt.TASK_ID, \n");
		sql.append("       tt.START_DATE, \n");
		sql.append("       tt.END_DATE, \n");
		sql.append("       tt.MONTH, \n");
		sql.append("       tt.YEAR, \n");
		sql.append("       tt.TASK_ID taskId, \n");
		sql.append("       (SELECT MAX(STATUS) \n");
		sql.append("          FROM TT_VS_MONTHLY_FORECAST T2 WHERE 1=1 \n");
		if(!StringUtils.isNullOrEmpty(loginInfo.getDealerId())){
			sql.append("AND DEALER_ID = ? \n");
			params.add(loginInfo.getDealerId());
		}
		sql.append("           AND T2.TASK_ID = TT.TASK_ID) AS STATUS \n");
		sql.append("  FROM TT_VS_RETAIL_TASK tt  ,Tt_Forecast_Material tfm WHERE \n");
		if(!StringUtils.isNullOrEmpty(OemDictCodeConstants.TASK_TYPE_02)){
			sql.append("       TT.TASK_TYPE=? \n");
			params.add(OemDictCodeConstants.TASK_TYPE_02);
		}
		/*
		 * 
		 * if(!StringUtils.isNullOrEmpty(format.format(calendar.getTime()))){
			sql.append("      AND TT.END_DATE>=? \n");
			params.add(format.format(calendar.getTime()));
		}
		if(!StringUtils.isNullOrEmpty(format.format(calendar.getTime()))){
			sql.append("      AND TT.START_DATE<=? \n");
			params.add(format.format(calendar.getTime()));
		}*/
		sql.append(" and tfm.task_id=tt.task_id and tfm.group_id is null\n");
		sql.append(" ) tt where tt.STATUS IS NULL OR TT.STATUS='"+OemDictCodeConstants.TT_VS_MONTHLY_FORECAST_SAVE+"'");
		
		return sql.toString();
	}
	
	/**
	 * 
	* @Title: forecastImportOTDQuery 
	* @Description: 生产订单任务录入 （品牌，车系）
	* @param @param queryParam
	* @param @return    设定文件 
	* @return List<Map>    返回类型 
	* @throws
	 */
	public List<Map> forecastImportOTDQuery(Map<String, String> queryParam) {
		TtVsRetailTaskPO tvrtPO = new TtVsRetailTaskPO();
		tvrtPO = tvrtPO.findById(queryParam.get("taskId"));
		List<Object> params = new ArrayList<Object>();
		String sql = getforecastImportOTDQuerySql(queryParam,params,tvrtPO);
		List<Map> forecastImportOTDList = OemDAOUtil.findAll(sql,params);
		if(forecastImportOTDList.size() > 0){
			for(int i=0; i<forecastImportOTDList.size(); i++){
				//数量
				forecastImportOTDList.get(i).put("SIZE", forecastImportOTDList.size());
			}
		}
		return forecastImportOTDList;
	}
	private String getforecastImportOTDQuerySql(Map<String, String> queryParam,
			List<Object> params, TtVsRetailTaskPO tvrtPO) {
		StringBuilder sbSql = new StringBuilder();
		sbSql.append("SELECT T1.BRAND_NAME,T1.BRAND_CODE,T1.SERIES_NAME,T1.SERIES_CODE,T1.FORECAST_AMOUNT D_DATE,T2.S UP_LIMIT,T2.LOWER_LIMIT LOWER_LIMIT FROM	\n");
		sbSql.append("     (SELECT    TT3.BRAND_NAME,TT3.BRAND_CODE,TT3.SERIES_NAME,TT3.SERIES_CODE,sum(TT3.FORECAST_AMOUNT) FORECAST_AMOUNT FROM \n");
		sbSql.append("		(SELECT DISTINCT TT1.BRAND_NAME,TT1.BRAND_CODE,TT1.GROUP_NAME,TT1.GROUP_CODE,TT1.SERIES_NAME,TT1.SERIES_CODE,TT2.FORECAST_AMOUNT FROM \n");
		sbSql.append("			(SELECT DISTINCT BRAND_NAME,BRAND_CODE,SERIES_NAME,SERIES_CODE,GROUP_NAME,GROUP_CODE FROM ("+getVwMaterialSql()+") WHERE\n");
		sbSql.append("		 	MATERIAL_ID IN (SELECT MATERIAL_ID FROM TT_FORECAST_MATERIAL WHERE");
		if(!StringUtils.isNullOrEmpty(queryParam.get("taskId"))){
			sbSql.append( " TASK_ID= ? \n");
			params.add(queryParam.get("taskId"));
		}
		sbSql.append("		) ) TT1 \n");
		sbSql.append("		LEFT JOIN\n");
		sbSql.append("			(SELECT TVMG2.GROUP_CODE SERIES_CODE,TVMG4.GROUP_NAME,TVMG4.GROUP_CODE,SUM(TVMFD.FORECAST_AMOUNT) FORECAST_AMOUNT\n");
		sbSql.append("			FROM  TT_VS_MONTHLY_FORECAST_DETAIL     TVMFD, \n");
		sbSql.append("			  TT_VS_MONTHLY_FORECAST            TVMF, \n");
		sbSql.append("		      TM_VHCL_MATERIAL_GROUP TVMG4,\n");
		sbSql.append("		      TM_VHCL_MATERIAL_GROUP TVMG2\n");
		sbSql.append("			WHERE TVMF.FORECAST_ID = TVMFD.FORECAST_ID\n");
		 //获取当前用户
        LoginInfoDto loginInfo = ApplicationContextHelper.getBeanByType(LoginInfoDto.class);
        if(!StringUtils.isNullOrEmpty(loginInfo.getDealerId())){
        	sbSql.append(" AND TVMF.DEALER_ID = ? \n");
			params.add(loginInfo.getDealerId());
		}
        if(!StringUtils.isNullOrEmpty(queryParam.get("taskId"))){
        	sbSql.append(" AND TVMF.TASK_ID = ? \n");
			params.add(queryParam.get("taskId"));
		}
		sbSql.append("				AND TVMG4.GROUP_LEVEL=4 AND TVMG2.GROUP_LEVEL=2 AND TVMG4.GROUP_ID=TVMFD.MATERIAL_ID\n");
		sbSql.append("				AND TVMG2.STATUS='"+OemDictCodeConstants.STATUS_ENABLE+"' AND TVMG4.STATUS='"+OemDictCodeConstants.STATUS_ENABLE+"'\n");
		sbSql.append("	 		GROUP BY TVMG2.GROUP_CODE,TVMG4.GROUP_NAME,TVMG4.GROUP_CODE ) TT2 ON TT1.GROUP_CODE=TT2.GROUP_CODE\n");
		sbSql.append("            )tt3 GROUP BY TT3.BRAND_NAME,TT3.BRAND_CODE,TT3.SERIES_NAME,TT3.SERIES_CODE");
		sbSql.append("		) T1  \n");
		sbSql.append("	LEFT JOIN	 \n");
		sbSql.append("		( SELECT TVMG.GROUP_CODE SERIES_CODE,SUM(TVMPD.SALE_AMOUNT) S,SUM(TVMPD.LOWER_LIMIT) LOWER_LIMIT\n");
		sbSql.append("		FROM  TT_VS_MONTHLY_PLAN     TVMP, ");
		sbSql.append("			  TT_VS_MONTHLY_PLAN_DETAIL     TVMPD,\n");
		sbSql.append(" 			  TM_VHCL_MATERIAL_GROUP TVMG\n");
		sbSql.append(" 		WHERE TVMP.PLAN_ID = TVMPD.PLAN_ID\n");
		if(!StringUtils.isNullOrEmpty(loginInfo.getDealerId())){
			sbSql.append(" AND TVMP.DEALER_ID = ? \n");
			params.add(loginInfo.getDealerId());
		}
		sbSql.append(" AND TVMP.PLAN_TYPE = "+OemDictCodeConstants.TARGET_TYPE_01+"\n");
		if(!StringUtils.isNullOrEmpty(tvrtPO.get("YEAR"))){
			sbSql.append(" AND TVMP.PLAN_YEAR= ? \n");
			params.add(tvrtPO.get("YEAR"));
		}
		if(!StringUtils.isNullOrEmpty(tvrtPO.get("MONTH"))){
			sbSql.append(" AND TVMP.PLAN_MONTH= ? \n");
			params.add(tvrtPO.get("MONTH"));
		}
		if(!StringUtils.isNullOrEmpty(tvrtPO.get("TASK_ID"))){
			sbSql.append("and TVMP.TASK_ID= ? \n");
			params.add(tvrtPO.get("TASK_ID"));
		}
		//经销商录入获取最大版本号
		String planVer = getMaxPlanVer(tvrtPO.get("YEAR").toString(), tvrtPO.get("MONTH").toString(),loginInfo.getDealerId().toString(),tvrtPO.get("TASK_ID").toString());
		if(!StringUtils.isNullOrEmpty(planVer)){
			sbSql.append("AND TVMP.PLAN_VER= ? \n");
			params.add(planVer);
		}
		sbSql.append(" 	AND TVMG.GROUP_ID=TVMPD.MATERIAL_GROUPID\n");
		sbSql.append(" 	GROUP BY TVMG.GROUP_CODE ) T2 ON T1.SERIES_CODE=T2.SERIES_CODE\n");
		
		return sbSql.toString();
	}
	//经销商录入获取最大版本号
	private String getMaxPlanVer(String year,String month,String dealerId,
			String taskId){
		List<Object> params = new ArrayList<Object>();
		String planVer = null;
		StringBuilder sql = new StringBuilder();
		sql.append("select PLAN_VER from Tt_Vs_Monthly_Plan where \n");
		if(!StringUtils.isNullOrEmpty(taskId)){
			sql.append("  task_Id= ? \n");
			params.add(taskId);
		}
		if(!StringUtils.isNullOrEmpty(year)){
			sql.append("and PLAN_YEAR= ? \n");
			params.add(year);
		}
		if(!StringUtils.isNullOrEmpty(month)){
			sql.append("and PLAN_month = ? \n");
			params.add(month);
		}
		if(!StringUtils.isNullOrEmpty(dealerId)){
			sql.append("and dealer_id = ? \n");
			params.add(dealerId);
		}
		sql.append("and plan_type = '"+OemDictCodeConstants.TARGET_TYPE_01+"' order by PLAN_VER desc");
		
		List<Map> materialIdList = OemDAOUtil.findAll(sql.toString(),params);
		Map<String, Object> map = new HashMap<String,Object>();
		if(materialIdList != null && materialIdList.size() > 0){
			map = materialIdList.get(0);
			planVer = (String) map.get("PLAN_VER");
		}else{
			planVer = "0";
		}

		return planVer;
	}
	
	/**
	 * 
	* @Title: modifyforecastOTDSubmit 
	* @Description: 生产订单任务录入（提交） 
	* @param @param taskId
	* @param @param userDto    设定文件 
	* @return void    返回类型 
	* @throws
	 */
	public void modifyforecastOTDSubmit(Long taskId, TtVsMonthlyForecastDTO userDto) {
		TtVsMonthlyForecastPO tvrtPow = new TtVsMonthlyForecastPO();
		List<Object> params = new ArrayList<Object>();
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT tvmf.TASK_ID, tvmf.DEALER_ID, tvmf.STATUS, tvmf.UPDATE_BY, tvmf.UPDATE_DATE FROM tt_vs_monthly_forecast tvmf WHERE 1=1 \n");
		if(!StringUtils.isNullOrEmpty(taskId)){
			sql.append("AND  tvmf.TASK_ID= ? \n");
			params.add(taskId);
		}
		 //获取当前用户
        LoginInfoDto loginInfo = ApplicationContextHelper.getBeanByType(LoginInfoDto.class);
		if(!StringUtils.isNullOrEmpty(loginInfo.getDealerId())){
			sql.append("AND  tvmf.DEALER_ID= ? \n");
			params.add(loginInfo.getDealerId());
		}
		tvrtPow = (TtVsMonthlyForecastPO) OemDAOUtil.findFirst(sql.toString(), params);
		tvrtPow.setLong("STATUS", OemDictCodeConstants.TT_VS_MONTHLY_FORECAST_SUBMIT);
		tvrtPow.setLong("UPDATE_BY",new Long(loginInfo.getUserId()));
		tvrtPow.setDate("UPDATE_DATE", new Date());
		tvrtPow.saveIt();
	}
	
	/**
	 * 
	* @Title: selectUnique 
	* @Description: TODO(查询唯一的记录,确保唯一,如按id或编号查询) 
	* @throws 如果返回值不唯一抛错
	 */
	public List<Map> selectTmVhclMaterialGroupUnique(TmVhclMaterialGroupPO tvmgPo) {
		List<Object> params = new ArrayList<Object>();
        StringBuilder sbSql = new StringBuilder();
        sbSql.append("SELECT tvmg.* FROM tm_vhcl_material_group tvmg where 1=1  \n");
        if(!StringUtils.isNullOrEmpty(tvmgPo.get("GROUP_CODE"))){
        	sbSql.append("AND tvmg.GROUP_CODE = ? \n");
			params.add(tvmgPo.get("GROUP_CODE"));
		}
		Map m = OemDAOUtil.findFirst(sbSql.toString(),params);
		List<Map> list = new ArrayList<Map>();
		list.add(m);
		if (null == list || list.size() == 0) {
			throw new ServiceBizException("生产订单任务录入出错：", "Not found record.");
		}
		if (list.size() > 1) {
			throw new ServiceBizException("生产订单任务录入出错：", "Not unique record."); 
		}
		return list;
	}
	
	/**
	 * 
	* @Title: selectUnique 
	* @Description: TODO(查询唯一的记录,确保唯一,如按id或编号查询) 
	* @throws 如果返回值不唯一抛错
	 */
	public List<Map> selectTtVsRetailTaskUnique(TtVsRetailTaskPO trtPO) {
		List<Object> params = new ArrayList<Object>();
        StringBuilder sbSql = new StringBuilder();
        sbSql.append("SELECT tvrt.* FROM tt_vs_retail_task tvrt where 1=1  \n");
        if(!StringUtils.isNullOrEmpty(trtPO.get("TASK_ID"))){
        	sbSql.append("AND tvrt.TASK_ID = ? \n");
			params.add(trtPO.get("TASK_ID"));
		}
		Map m = OemDAOUtil.findFirst(sbSql.toString(),params);
		List<Map> list = new ArrayList<Map>();
		list.add(m);
		if (null == list || list.size() == 0) {
			throw new ServiceBizException("生产订单任务录入出错：", "Not found record.");
		}
		if (list.size() > 1) {
			throw new ServiceBizException("生产订单任务录入出错：", "Not unique record."); 
		}
		return list;
	}
	
	/**
	 * 获取车系信息，过滤无车型，款式的数据
	 * @param parentId 车系代码
	 * @return
	 */
	public List<Map> selectTtVsRetailTaskUnique(String groupCode, LoginInfoDto loginInfo,
			String taskId) {
		List<Object> params = new ArrayList<Object>();
		String sql = selectTtVsRetailTaskUniqueSql(groupCode,loginInfo,taskId,params);
		List<Map> materialIdList = OemDAOUtil.findAll(sql,params);
		return materialIdList;
	}
	private String selectTtVsRetailTaskUniqueSql(String groupCode, LoginInfoDto loginInfo,
			String taskId, List<Object> params) {
		StringBuffer strBuff = new StringBuffer();
		strBuff.append("SELECT TT1.*,TT2.FORECAST_ID,TT2.DETAIL_ID, TT2.FORECAST_AMOUNT  FROM \n");
		strBuff.append(" 	(SELECT VM.GROUP_ID,VM.SERIES_NAME,VM.SERIES_CODE,VM.MODEL_NAME,VM.MODEL_CODE,VM.GROUP_NAME,VM.GROUP_CODE\n");
		strBuff.append("		 FROM ("+getVwMaterialSql()+") VM WHERE VM.SERIES_CODE=?\n");
		params.add(groupCode);
		strBuff.append(" 		AND EXISTS (SELECT 1 FROM TM_VHCL_MATERIAL_GROUP P WHERE P.GROUP_LEVEL=2 AND P.GROUP_CODE=VM.SERIES_CODE AND P.STATUS=10011001)\n");
		strBuff.append(" 		 AND VM.MATERIAL_ID IN (SELECT MATERIAL_ID FROM TT_FORECAST_MATERIAL WHERE TASK_ID =? )\n");
		params.add(taskId);
		strBuff.append(" 		 GROUP BY VM.SERIES_NAME,VM.SERIES_CODE,VM.MODEL_NAME,VM.MODEL_CODE,VM.GROUP_NAME,VM.GROUP_CODE,VM.GROUP_ID\n");
		strBuff.append(" 	 ) TT1\n");
		strBuff.append(" LEFT JOIN \n");
		strBuff.append(" 	 (SELECT TVMG.GROUP_CODE SERIES_CODE,TVMF.FORECAST_ID,TVMFD.DETAIL_ID,SUM(TVMFD.FORECAST_AMOUNT) FORECAST_AMOUNT\n");
		strBuff.append(" 	 	FROM   TT_VS_MONTHLY_FORECAST_DETAIL     TVMFD, \n");
		strBuff.append(" 			TT_VS_MONTHLY_FORECAST            TVMF,\n");
		strBuff.append(" 			TM_VHCL_MATERIAL_GROUP TVMG\n");
		strBuff.append(" 		WHERE TVMF.FORECAST_ID = TVMFD.FORECAST_ID\n");
		strBuff.append(" 		AND TVMF.DEALER_ID =?\n");
		params.add(loginInfo.getDealerId());
		strBuff.append(" 		AND TVMF.TASK_ID =?\n");
		params.add(taskId);
		strBuff.append(" 		AND TVMG.GROUP_LEVEL=4 AND TVMG.STATUS=10011001 AND TVMG.GROUP_ID=MATERIAL_ID\n");
		strBuff.append(" 		 GROUP BY TVMG.GROUP_CODE,TVMF.FORECAST_ID,TVMFD.DETAIL_ID\n");
		strBuff.append(" 	 ) TT2\n");
		strBuff.append(" ON TT1.GROUP_CODE=TT2.SERIES_CODE\n");
		
		return strBuff.toString();
	}
	
	/**
	 * 获取车系预测数量
	 * @param parentId 
	 * @return
	 */
	public List<Map> getForecastCarOTDListTotal(String groupCode, LoginInfoDto loginInfo,
			String taskId) {
		List<Object> params = new ArrayList<Object>();
		String sql = getForecastCarOTDListTotalSql(groupCode,loginInfo,taskId,params);
		List<Map> list = OemDAOUtil.findAll(sql,params);
		return list;
	}
	private String getForecastCarOTDListTotalSql(String groupCode, LoginInfoDto loginInfo, 
			String taskId, List<Object> params) {
		TtVsRetailTaskPO tvrtPO = new TtVsRetailTaskPO();
		tvrtPO.set("TASK_ID", Long.valueOf(taskId));
		List<Map> tvrtList = selectTtVsRetailTaskUnique(tvrtPO);
		StringBuffer strBuff = new StringBuffer();
		strBuff.append("SELECT T1.SERIES_NAME,T1.SERIES_CODE, sum(T1.FORECAST_AMOUNT) FORECAST_TOTAL,sum(T2.s) S_TOTAl,sum(T2.LOWER_LIMIT) LOWER_LIMIT FROM \n");
		strBuff.append("  (select tt.series_name,tt.SERIES_CODE,sum(tt.FORECAST_AMOUNT) FORECAST_AMOUNT from\n");
		strBuff.append(" 	(SELECT DISTINCT TT1.*,TT2.FORECAST_AMOUNT FROM\n");
		strBuff.append(" 		(SELECT VM.SERIES_NAME,VM.SERIES_CODE,VM.GROUP_CODE FROM ("+getVwMaterialSql()+") VM WHERE VM.SERIES_CODE='"+groupCode+"'\n");
		strBuff.append(" 			AND EXISTS (SELECT 1 FROM TM_VHCL_MATERIAL_GROUP P WHERE P.GROUP_CODE = VM.SERIES_CODE AND P.GROUP_LEVEL=2 AND P.STATUS=10011001)\n");
		strBuff.append(" 			AND VM.MATERIAL_ID IN (SELECT MATERIAL_ID FROM TT_FORECAST_MATERIAL WHERE TASK_ID ="+taskId+")\n");
		strBuff.append(" 		GROUP BY VM.SERIES_NAME,VM.SERIES_CODE,VM.GROUP_CODE\n");
		strBuff.append(" 		) TT1\n");
		strBuff.append(" 	LEFT JOIN\n");
		strBuff.append(" 		(SELECT TVMG4.GROUP_CODE,TVMG2.GROUP_CODE SERIES_CODE,TVMG2.GROUP_NAME,SUM(TVMFD.FORECAST_AMOUNT) FORECAST_AMOUNT\n");
		strBuff.append(" 		FROM  TT_VS_MONTHLY_FORECAST_DETAIL     TVMFD,\n");
		strBuff.append(" 			  TT_VS_MONTHLY_FORECAST            TVMF,\n");
		strBuff.append(" 			  TM_VHCL_MATERIAL_GROUP            TVMG4,\n");
		strBuff.append(" 			  TM_VHCL_MATERIAL_GROUP TVMG2\n");
		strBuff.append(" 		WHERE TVMF.FORECAST_ID = TVMFD.FORECAST_ID \n");
		strBuff.append(" 		      AND TVMF.DEALER_ID ="+loginInfo.getDealerId()+" \n");
		strBuff.append(" 			  AND TVMF.TASK_ID ="+taskId+"\n");
		strBuff.append(" 			 AND TVMG4.GROUP_LEVEL=4 AND TVMG2.GROUP_LEVEL=2 AND TVMG4.STATUS=10011001 AND TVMG2.STATUS=10011001\n");
		strBuff.append(" 			AND TVMG4.GROUP_ID=TVMFD.MATERIAL_ID\n");
		strBuff.append(" 			GROUP BY TVMG4.GROUP_CODE,TVMG2.GROUP_CODE,TVMG2.GROUP_NAME\n");
		strBuff.append(" 		) TT2\n");
		strBuff.append(" ON TT1.GROUP_CODE=TT2.GROUP_CODE) \n");
		strBuff.append(" TT GROUP BY TT.SERIES_NAME,TT.SERIES_CODE)  T1\n");
		strBuff.append("LEFT JOIN (SELECT TVMG.GROUP_CODE SERIES_CODE,SUM(TVMPD.SALE_AMOUNT) S,SUM(TVMPD.LOWER_LIMIT) LOWER_LIMIT \n");
		strBuff.append("			FROM  TT_VS_MONTHLY_PLAN     TVMP, \n");
		strBuff.append(" 				  TT_VS_MONTHLY_PLAN_DETAIL     TVMPD,\n");
		strBuff.append(" 			      TM_VHCL_MATERIAL_GROUP TVMG\n");
		strBuff.append(" 			WHERE TVMP.PLAN_ID = TVMPD.PLAN_ID \n");
		strBuff.append(" 				AND TVMP.DEALER_ID ="+loginInfo.getDealerId()+"\n"); 
		strBuff.append(" 				AND TVMP.PLAN_TYPE ="+OemDictCodeConstants.TARGET_TYPE_01+"\n");    
		strBuff.append(" 				AND TVMP.PLAN_YEAR="+tvrtList.get(0).get("YEAR")+"\n");   
		strBuff.append(" 				AND TVMP.PLAN_MONTH="+tvrtList.get(0).get("MONTH")+"\n");   
		strBuff.append(" 				AND TVMP.task_id="+tvrtList.get(0).get("TASK_ID")+"\n");   
		strBuff.append(" 				AND TVMP.PLAN_VER="+this.getMaxPlanVer(tvrtList.get(0).get("YEAR").toString(), tvrtList.get(0).get("MONTH").toString(),loginInfo.getDealerId().toString(),tvrtList.get(0).get("TASK_ID").toString())+"\n");   
		strBuff.append(" 				AND TVMG.GROUP_LEVEL=2 AND TVMG.STATUS=10011001 AND TVMG.GROUP_ID=TVMPD.MATERIAL_GROUPID\n");   
		strBuff.append(" 				GROUP BY TVMG.GROUP_CODE\n");   
		strBuff.append(" 			 ) T2\n");   
		strBuff.append(" ON T1.SERIES_CODE=T2.SERIES_CODE	GROUP BY T1.SERIES_NAME,T1.SERIES_CODE		\n");  
		
		return strBuff.toString();
	}
	
	

}
