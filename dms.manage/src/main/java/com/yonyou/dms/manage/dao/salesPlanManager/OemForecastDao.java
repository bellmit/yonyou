package com.yonyou.dms.manage.dao.salesPlanManager;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.itextpdf.text.log.SysoLogger;
import com.yonyou.dms.framework.DAO.OemBaseDAO;
import com.yonyou.dms.framework.DAO.OemDAOUtil;
import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.function.common.DictCodeConstants;
import com.yonyou.dms.function.common.OemDictCodeConstants;
import com.yonyou.dms.function.utils.common.StringUtils;
import com.yonyou.dms.manage.domains.DTO.salesPlanManager.OemForecastAuditDTO;
import com.yonyou.dms.manage.domains.PO.salesPlanManager.TmpVsProImpInfoAuditPO;
/**
 * 
* @ClassName: OemForecastDao 
* @Description: 生产订单审核编写sql语句
* @author zhengzengliang
* @date 2017年2月10日 下午4:07:39 
*
 */
@SuppressWarnings({"rawtypes","unchecked"})
@Repository
public class OemForecastDao extends OemBaseDAO {
	
	/**
	 * 生产订单审核查询
	 * @param queryParam
	 * @return
	 */
	public PageInfoDto getOTDForecastQueryList(Map<String, String> queryParam) {
		List<Object> params = new ArrayList<Object>();
		String sql = getOTDForecastQuerySql(queryParam,params);
		PageInfoDto pageInfoDto = OemDAOUtil.pageQuery(sql,params);
		return pageInfoDto;
	}
	
	/**
	 * 
	* @Title: getOTDForecastQuerySql 
	* @Description: 生产订单审核查询 
	* @param @param queryParam
	* @param @param params
	* @param @return    设定文件 
	* @return String    返回类型 
	* @throws
	 */
	private String getOTDForecastQuerySql(Map<String, String> queryParam, List<Object> params) {
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT DISTINCT TOR2.ORG_NAME BIG_AREA,TOR.ORG_NAME SMALL_AREA,CAST(TVMF.FORECAST_ID AS CHAR) FORECAST_ID,TD.DEALER_CODE,DEALER_NAME,CAST(vw.MATERIAL_ID AS CHAR) MATERIAL_ID,VW.BRAND_NAME,VW.SERIES_NAME,VW.GROUP_ID,VW.GROUP_NAME,VW.MODEL_YEAR,\n");
		sql.append("        CAST(TVRT.TASK_ID AS CHAR) TASK_ID,VW.SERIES_CODE,VW.MODEL_NAME,VW.MODEL_CODE,VW.GROUP_CODE,VW.COLOR_CODE,VW.TRIM_CODE,\n");
		sql.append("		VW.TRIM_NAME,VW.COLOR_NAME,CAST(TVMFD.DETAIL_ID AS CHAR) DETAIL_ID ,\n");
		sql.append("        IFNULL(TVMFDC.REQUIRE_NUM,0) REQUIRE_NUM,IFNULL(TVMFDC.CONFIRM_NUM,0) CONFIRM_NUM,IFNULL(TVMFDC.REPORT_NUM,0) REPORT_NUM\n");
		sql.append(" 		FROM ("+getVwMaterialSql()+")                   VW,\n");
		sql.append(" 		TT_VS_MONTHLY_FORECAST            TVMF,\n");
		sql.append(" 		TT_VS_MONTHLY_FORECAST_DETAIL_DCS     TVMFD,\n");
		sql.append(" 		TT_VS_RETAIL_TASK				  TVRT,\n");
		sql.append(" 		TT_FORECAST_MATERIAL 			  TFM,\n");
		sql.append(" 		TM_DEALER 						 TD,\n");
		sql.append(" 		TM_ORG                  		 TOR,\n");
		sql.append(" 		TM_ORG                  		 TOR2,\n");
		sql.append(" 		TM_DEALER_ORG_RELATION 			 TDOR,\n");
		sql.append("		TT_VS_MONTHLY_FORECAST_DETAIL_COLOR     TVMFDC ");
		sql.append("	 WHERE 1=1  \n");
		sql.append("	AND TD.DEALER_ID=TDOR.DEALER_ID AND TOR.ORG_ID=TDOR.ORG_ID AND TOR.PARENT_ORG_ID=TOR2.ORG_ID AND");
		sql.append("	TVRT.TASK_ID=TFM.TASK_ID AND TFM.MATERIAL_ID=VW.MATERIAL_ID \n");
		sql.append("    AND VW.GROUP_TYPE="+DictCodeConstants.GROUP_TYPE_IMPORT+"\n");
		sql.append(" 	AND TVMF.TASK_ID = TVRT.TASK_ID\n");
		sql.append(" 	AND TVMF.FORECAST_ID = TVMFD.FORECAST_ID \n");
		sql.append(" 	AND TD.DEALER_ID=TVMF.DEALER_ID\n");
		sql.append(" 	AND TVMFD.DETAIL_ID = TVMFDC.DETAIL_ID   and vw.MATERIAL_ID = TVMFDC.MATERIAL_ID and TVMFDC.REQUIRE_NUM>0 \n");
		sql.append(" 	AND TVMF.STATUS="+DictCodeConstants.TT_VS_MONTHLY_FORECAST_SUBMIT+" \n");
		if(!StringUtils.isNullOrEmpty(queryParam.get("yearName"))){
			sql.append(" and FORECAST_YEAR = ?");
			params.add(queryParam.get("yearName"));
		}
		if(!StringUtils.isNullOrEmpty(queryParam.get("monthName"))){
			sql.append(" and FORECAST_MONTH = (select tc.CODE_CN_DESC from TC_CODE tc where 1=1 and tc.CODE_ID = ?)");
			params.add(queryParam.get("monthName"));
		}
		if(!StringUtils.isNullOrEmpty(queryParam.get("taskCodeName"))){
			sql.append(" and TVRT.TASK_ID = ?");
			params.add(queryParam.get("taskCodeName"));
		}
	//	sql.append("  ORDER BY TD.DEALER_CODE,TD.DEALER_NAME,VW.BRAND_NAME,VW.SERIES_NAME,VW.GROUP_NAME,VW.COLOR_NAME,VW.TRIM_NAME,VW.MODEL_YEAR\n");
		
		return sql.toString();
	}
	
	
	/**
	 * 
	* @Title: queryforecastAuditDataExport 
	* @Description:  生产订单审核导出
	* @param @param queryParam
	* @param @return    设定文件 
	* @return List<Map>    返回类型 
	* @throws
	 */
	public List<Map> queryforecastAuditDataExport(Map<String, String> queryParam) {
		List<Object> params = new ArrayList<Object>();
		String sql = getOTDForecastQuerySql(queryParam,params);
		List<Map> forecastAuditList = OemDAOUtil.findAll(sql,params);
		return forecastAuditList;
	}
	
	/**
	 * 
	* @Title: setForecastPO 
	* @Description: 生产订单审核导入
	* @param @param oemForecastAudit
	* @param @param oemForecastAuditDTO    设定文件 
	* @return void    返回类型 
	* @throws
	 */
	public void setForecastPO(TmpVsProImpInfoAuditPO oemForecastAudit,OemForecastAuditDTO oemForecastAuditDTO){
		oemForecastAudit.setString("ROW_NUMBER", oemForecastAuditDTO.getRowNO());
		oemForecastAudit.setLong("DETAIL_ID", oemForecastAuditDTO.getDetailId());
		oemForecastAudit.setLong("MATERIAL_ID", oemForecastAuditDTO.getMaterialId());
		oemForecastAudit.setLong("TASK_ID", oemForecastAuditDTO.getTaskId());
		oemForecastAudit.setString("CONFIRM_NUM", oemForecastAuditDTO.getConfirmNum());
		oemForecastAudit.setLong("FORECAST_ID", oemForecastAuditDTO.getForecastId());
	}
	
	/**
	* 
	* @Title: forecastQueryTotal 
	* @Description: 生产订单查询(大区汇总)
	* @param @param queryParam
	* @param @return    设定文件 
	* @return PageInfoDto    返回类型 
	* @throws
	*/
	public PageInfoDto forecastQueryTotal(Map<String, String> queryParam) {
		List<Object> params = new ArrayList<Object>();
		String sql = getforecastQueryTotalSql(queryParam,params);
		PageInfoDto pageInfoDto = OemDAOUtil.pageQuery(sql,params);
		return pageInfoDto;
	}
	private String getforecastQueryTotalSql(Map<String, String> queryParam, List<Object> params) {
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT TT1.*,IFNULL(TT2.PAY_NUM,0) PAY_NUM FROM (");
		sql.append(" select  TVMFDC.DETAIL_COLOR_ID,TOR2.ORG_ID,TOR2.ORG_NAME,VM.SERIES_CODE,VM.SERIES_NAME,VM.MODEL_CODE,VM.MODEL_NAME, VM.GROUP_CODE,VM.GROUP_NAME,VM.COLOR_NAME,VM.TRIM_NAME,\n");
		sql.append("    TVMF.DEALER_ID,SUM(TVMFDC.REQUIRE_NUM) REQUIRE_NUM,SUM(TVMFDC.Confirm_num) CONFIRM_NUM,SUM(TVMFDC.report_num) REPORT_NUM\n");
		sql.append("    from TT_VS_MONTHLY_FORECAST            TVMF,\n");
		sql.append("         TT_VS_MONTHLY_FORECAST_DETAIL_DCS     TVMFD,\n");		
		sql.append("         TM_DEALER                         TD,\n");
		sql.append("         TM_ORG                            TOR,\n");
		sql.append("         TM_ORG                            TOR2,\n");
		sql.append("         TM_DEALER_ORG_RELATION             TDOR,\n");
		sql.append("         ("+getVwMaterialSql()+")                       VM,\n");
		sql.append("         TT_VS_MONTHLY_FORECAST_DETAIL_COLOR TVMFDC\n");
		sql.append("    where 1=1 AND TVMF.FORECAST_ID = TVMFD.FORECAST_ID\n");
		sql.append("    AND TVMF.DEALER_ID = TD.DEALER_ID\n");
		sql.append("    AND TD.DEALER_ID = TDOR.DEALER_ID\n");
		sql.append("    AND TOR.ORG_ID = TDOR.ORG_ID\n");
		sql.append("    AND TOR.PARENT_ORG_ID = TOR2.ORG_ID\n");
		sql.append("    AND VM.MATERIAL_ID = TVMFDC.MATERIAL_ID\n");
		sql.append("    AND TVMFD.DETAIL_ID = TVMFDC.DETAIL_ID\n");
		sql.append("    AND TVMFDC.REQUIRE_NUM>0\n");
		sql.append("    AND TVMF.STATUS>"+DictCodeConstants.TT_VS_MONTHLY_FORECAST_SAVE+"\n");
		sql.append("    AND TVMF.ORG_TYPE="+DictCodeConstants.ORG_TYPE_DEALER+"\n");
		sql.append("    AND VM.GROUP_TYPE="+DictCodeConstants.GROUP_TYPE_IMPORT+"\n");
		
	
		//品牌
		if(!StringUtils.isNullOrEmpty(queryParam.get("brandId"))){
			sql.append(" and VM.BRAND_id = ?");
			System.err.println(queryParam.get("brandId"));
		}
		//车系
		if(!StringUtils.isNullOrEmpty(queryParam.get("seriesId"))){
			sql.append(" and VM.SERIES_ID = ?");
			params.add(queryParam.get("seriesId"));
			System.err.println(queryParam.get("seriesId"));
		}
		//车款
		if(!StringUtils.isNullOrEmpty(queryParam.get("groupId"))){
			sql.append(" and VM.group_ID = ?");
			params.add(queryParam.get("groupId"));
		}
		//年款
		if(!StringUtils.isNullOrEmpty(queryParam.get("modelYear"))){
			sql.append(" and VM.MODEL_YEAR = ?");
			params.add(queryParam.get("modelYear"));
		}
		//颜色
		if(!StringUtils.isNullOrEmpty(queryParam.get("colorName"))){
			sql.append(" and VM.COLOR_CODE = ?");
			params.add(queryParam.get("colorName"));
		}
		//内饰
		if(!StringUtils.isNullOrEmpty(queryParam.get("trimName"))){
			sql.append(" and VM.TRIM_CODE = ?");
			params.add(queryParam.get("trimName"));
		}
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
			sql.append(" and TVMF.TASK_ID = ?");
			params.add(queryParam.get("taskCodeName"));
		}
		//经销商
		if(!StringUtils.isNullOrEmpty(queryParam.get("dealerCode"))){
			sql.append(" and TD.DEALER_CODE = ?");
			params.add(queryParam.get("dealerCode"));
		}
		sql.append("    group by TVMFDC.DETAIL_COLOR_ID,TOR2.ORG_ID,TOR2.ORG_NAME,VM.SERIES_CODE,VM.SERIES_NAME,VM.MODEL_CODE,VM.MODEL_NAME, VM.GROUP_CODE,VM.GROUP_NAME,VM.COLOR_NAME,VM.TRIM_NAME,TVMFDC.REQUIRE_NUM,TVMF.DEALER_ID\n");
		sql.append(" ) TT1   LEFT JOIN\n");
		sql.append("(SELECT COUNT(*) PAY_NUM,POD.DETAIL_COLOR_ID,POD.STATUS FROM PRO_ORDER_SERIAL POD,  TT_VS_MONTHLY_FORECAST_DETAIL_COLOR TVMFDC	\n");
		sql.append(" WHERE POD.STATUS="+DictCodeConstants.ZHONGJIN_ORDER_CONFIRM+" AND POD.DETAIL_COLOR_ID=TVMFDC.DETAIL_COLOR_ID 	\n");
		sql.append(" GROUP BY POD.DETAIL_COLOR_ID, POD.STATUS       	\n");
		sql.append(" ) TT2");
		sql.append(" ON TT1.DETAIL_COLOR_ID=TT2.DETAIL_COLOR_ID");
		
		System.out.println("*********************");
		System.out.println(sql.toString());
		System.out.println("*********************");
		return sql.toString();
	}
	
	/**
	 * 
	* @Title: getOemForecastQueryTotalList2 
	* @Description: 生产订单查询（全国汇总）
	* @param @param queryParam
	* @param @return    设定文件 
	* @return PageInfoDto    返回类型 
	* @throws
	 */
	public PageInfoDto getOemForecastQueryTotalList2(Map<String, String> queryParam) {
		List<Object> params = new ArrayList<Object>();
		String sql = getOemForecastQueryTotalList2Sql(queryParam,params);
		PageInfoDto pageInfoDto = OemDAOUtil.pageQuery(sql,params);
		return pageInfoDto;
	}
	private String getOemForecastQueryTotalList2Sql(Map<String, String> queryParam, List<Object> params) {
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT TT1.*,IFNULL(TT2.PAY_NUM,0) PAY_NUM FROM ( \n");
		sql.append("select  TVMFDC.DETAIL_COLOR_ID,VM.SERIES_CODE,VM.SERIES_NAME,VM.MODEL_CODE,VM.MODEL_NAME,VM.GROUP_NAME,VM.GROUP_CODE,VM.COLOR_NAME,VM.TRIM_NAME,sum(TVMFDC.REQUIRE_NUM) REQUIRE_NUM,sum(TVMFDC.CONFIRM_NUM) CONFIRM_NUM,sum(TVMFDC.REPORT_NUM) REPORT_NUM\n");
		sql.append("    from ("+getVwMaterialSql()+")                       VM,\n");
		sql.append("         TT_VS_MONTHLY_FORECAST            TVMF,\n");
		sql.append("         TT_VS_MONTHLY_FORECAST_DETAIL_DCS     TVMFD,\n");
		sql.append("         TT_VS_MONTHLY_FORECAST_DETAIL_COLOR TVMFDC,\n");
		sql.append("         TM_DEALER                         TD\n");
		sql.append("    where VM.MATERIAL_ID = TVMFDC.MATERIAL_ID\n");
		sql.append("    AND TVMFDC.DETAIL_ID = TVMFD.DETAIL_ID\n");
		sql.append("    AND TVMF.FORECAST_ID = TVMFD.FORECAST_ID\n");
		sql.append("    AND TD.DEALER_ID = TVMF.DEALER_ID\n");
		sql.append("    AND TVMFDC.REQUIRE_NUM>0\n");
		sql.append("    AND TVMF.STATUS>"+DictCodeConstants.TT_VS_MONTHLY_FORECAST_SAVE+"\n");
		sql.append("    AND TVMF.ORG_TYPE="+DictCodeConstants.ORG_TYPE_DEALER+"\n");
		sql.append("    AND VM.GROUP_TYPE="+DictCodeConstants.GROUP_TYPE_IMPORT+"\n");
		//品牌
		if(!StringUtils.isNullOrEmpty(queryParam.get("brandId"))){
			sql.append(" and VM.brand_Id = ?");
			params.add(queryParam.get("brandId"));
		}
		//车系
		if(!StringUtils.isNullOrEmpty(queryParam.get("seriesId"))){
			sql.append(" and VM.series_Id = ?");
			params.add(queryParam.get("seriesId"));
		}
		//车款
		if(!StringUtils.isNullOrEmpty(queryParam.get("groupId"))){
			sql.append(" and VM.group_Id = ?");
			params.add(queryParam.get("groupId"));
		}
		//年款
		if(!StringUtils.isNullOrEmpty(queryParam.get("modelYear"))){
			sql.append(" and VM.MODEL_YEAR = ?");
			params.add(queryParam.get("modelYear"));
		}
		//颜色
		if(!StringUtils.isNullOrEmpty(queryParam.get("colorName"))){
			sql.append(" and VM.COLOR_CODE = ?");
			params.add(queryParam.get("colorName"));
		}
		//内饰
		if(!StringUtils.isNullOrEmpty(queryParam.get("trimName"))){
			sql.append(" and VM.TRIM_CODE = ?");
			params.add(queryParam.get("trimName"));
		}
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
			sql.append(" and TVMF.TASK_ID = ?");
			params.add(queryParam.get("taskCodeName"));
		}
		//经销商
		if(!StringUtils.isNullOrEmpty(queryParam.get("dealerCode"))){
			sql.append(" and TD.DEALER_CODE = ?");
			params.add(queryParam.get("dealerCode"));
		}
		sql.append("    group by TVMFDC.DETAIL_COLOR_ID,VM.SERIES_CODE,VM.SERIES_NAME,VM.MODEL_CODE,VM.MODEL_NAME,VM.GROUP_NAME,VM.GROUP_CODE,VM.COLOR_NAME,VM.TRIM_NAME\n");
		sql.append(") tt1 left join  \n");
		sql.append("(SELECT COUNT(*) PAY_NUM,POD.DETAIL_COLOR_ID,POD.STATUS FROM PRO_ORDER_SERIAL POD,  TT_VS_MONTHLY_FORECAST_DETAIL_COLOR TVMFDC \n");
		sql.append(" WHERE POD.STATUS="+DictCodeConstants.ZHONGJIN_ORDER_CONFIRM+" AND POD.DETAIL_COLOR_ID=TVMFDC.DETAIL_COLOR_ID GROUP BY POD.DETAIL_COLOR_ID, POD.STATUS   \n");
		sql.append(") TT2 ON TT1.DETAIL_COLOR_ID=TT2.DETAIL_COLOR_ID\n");
		return sql.toString();
	}
	
	/**
	 * 
	* @Title: getOemForecastQueryDetailList 
	* @Description: 生产订单查询（明细查询） 
	* @param @param queryParam
	* @param @return    设定文件 
	* @return PageInfoDto    返回类型 
	* @throws
	 */
	public PageInfoDto getOemForecastQueryDetailList(Map<String, String> queryParam) {
		List<Object> params = new ArrayList<Object>();
		String sql = getOemForecastQueryDetailListSql(queryParam,params);
		PageInfoDto pageInfoDto = OemDAOUtil.pageQuery(sql,params);
		return pageInfoDto;
	}
	private String getOemForecastQueryDetailListSql(Map<String, String> queryParam, List<Object> params) {
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT TT1.*,IFNULL(TT2.PAY_NUM,0) PAY_NUM FROM ( \n");
		sql.append("select  TVMFDC.DETAIL_COLOR_ID,TOR2.ORG_ID PORG_ID,TOR2.ORG_NAME PORG_NAME,TOR.ORG_ID,TOR.ORG_NAME,TD.DEALER_CODE,TD.DEALER_SHORTNAME,VM.SERIES_CODE,VM.SERIES_NAME,VM.MODEL_CODE,VM.MODEL_NAME, VM.GROUP_CODE,VM.GROUP_NAME,VM.COLOR_NAME,VM.TRIM_NAME, \n");
		sql.append("  TVMFDC.REQUIRE_NUM,TVMFDC.CONFIRM_NUM,TVMFDC.REPORT_NUM\n");
		sql.append("    from TT_VS_MONTHLY_FORECAST            TVMF,\n");
		sql.append("         TT_VS_MONTHLY_FORECAST_DETAIL_DCS     TVMFD,\n");
		sql.append("         TM_DEALER                         TD,\n");
		sql.append("         TM_ORG                            TOR,\n");
		sql.append("         TM_ORG                            TOR2,\n");
		sql.append("         TM_DEALER_ORG_RELATION             TDOR, \n");
		sql.append("         ("+getVwMaterialSql()+")                       VM,\n");
		sql.append("         TT_VS_MONTHLY_FORECAST_DETAIL_COLOR TVMFDC\n");
		sql.append("    where TVMF.FORECAST_ID = TVMFD.FORECAST_ID\n");
		sql.append("    AND TVMF.DEALER_ID = TD.DEALER_ID\n");
		sql.append("    AND TD.DEALER_ID = TDOR.DEALER_ID\n");
		sql.append("    AND TOR.ORG_ID = TDOR.ORG_ID\n");
		sql.append("    AND TOR.PARENT_ORG_ID = TOR2.ORG_ID\n");
		sql.append("    AND VM.MATERIAL_ID = TVMFDC.MATERIAL_ID \n");
		sql.append("    AND TVMFD.DETAIL_ID = TVMFDC.DETAIL_ID \n");
		sql.append("    AND TVMFDC.REQUIRE_NUM>0 \n");
		sql.append("    AND TVMF.STATUS > '"+DictCodeConstants.TT_VS_MONTHLY_FORECAST_SAVE+"' \n");
		sql.append("    AND TVMF.ORG_TYPE= '"+DictCodeConstants.ORG_TYPE_DEALER+"' \n");
		sql.append("    AND VM.GROUP_TYPE= '"+DictCodeConstants.GROUP_TYPE_IMPORT+"' \n");
		//品牌
		if(!StringUtils.isNullOrEmpty(queryParam.get("brandId"))){
			sql.append(" and VM.brand_Id = ?");
			params.add(queryParam.get("brandId"));
		}
		//车系
		if(!StringUtils.isNullOrEmpty(queryParam.get("seriesId"))){
			sql.append(" and VM.series_Id = ?");
			params.add(queryParam.get("seriesId"));
		}
		//车款
		if(!StringUtils.isNullOrEmpty(queryParam.get("groupId"))){
			sql.append(" and VM.group_Id = ?");
			params.add(queryParam.get("groupId"));
		}
		//年款
		if(!StringUtils.isNullOrEmpty(queryParam.get("modelYear"))){
			sql.append(" and VM.MODEL_YEAR = ?");
			params.add(queryParam.get("modelYear"));
		}
		//颜色
		if(!StringUtils.isNullOrEmpty(queryParam.get("colorName"))){
			sql.append(" and VM.COLOR_CODE = ?");
			params.add(queryParam.get("colorName"));
		}
		//内饰
		if(!StringUtils.isNullOrEmpty(queryParam.get("trimName"))){
			sql.append(" and VM.TRIM_CODE = ?");
			params.add(queryParam.get("trimName"));
		}
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
			sql.append(" and TVMF.TASK_ID = ?");
			params.add(queryParam.get("taskCodeName"));
		}
		//经销商
		if(!StringUtils.isNullOrEmpty(queryParam.get("dealerCode"))){
			sql.append(" and TD.DEALER_CODE = ?");
			params.add(queryParam.get("dealerCode"));
		}
		sql.append(" ) tt1 left join  \n");
		sql.append("(SELECT COUNT(*) PAY_NUM,POD.DETAIL_COLOR_ID,POD.STATUS FROM PRO_ORDER_SERIAL POD,  TT_VS_MONTHLY_FORECAST_DETAIL_COLOR TVMFDC \n");
		sql.append("  WHERE POD.STATUS="+DictCodeConstants.ZHONGJIN_ORDER_CONFIRM+" AND POD.DETAIL_COLOR_ID=TVMFDC.DETAIL_COLOR_ID GROUP BY POD.DETAIL_COLOR_ID, POD.STATUS \n");
		sql.append("  ) tt2 ON TT1.DETAIL_COLOR_ID=TT2.DETAIL_COLOR_ID\n");
		
		return sql.toString();
	}
	
	/**
	 * 
	* @Title: findNoHandInDelears2 
	* @Description: 生产订单查询（未提报组织 ）
	* @param @param queryParam
	* @param @return    设定文件 
	* @return PageInfoDto    返回类型 
	* @throws
	 */
	public PageInfoDto findNoHandInDelears2(Map<String, String> queryParam) {
		List<Object> params = new ArrayList<Object>();
		String sql = getfindNoHandInDelears2Sql(queryParam,params);
		PageInfoDto pageInfoDto = OemDAOUtil.pageQuery(sql,params);
		return pageInfoDto;
	}
	private String getfindNoHandInDelears2Sql(Map<String, String> queryParam, List<Object> params) {
		StringBuilder sql = new StringBuilder();
		sql.append("select ORG1.ORG_DESC REGION ,ORG.ORG_DESC COMMUNITY,TD.DEALER_CODE,DATE_FORMAT(TD.FOUND_DATE,'%Y-%m-%d %H:%i:%S') FOUND_DATE,TC.SWT_CODE,\n");
		sql.append("       TD.DEALER_SHORTNAME DEALER_NAME,\n");
		sql.append("       TD.LINK_MAN,\n");
		sql.append("       TD.LINK_MAN_MOBILE \n");
		sql.append("    from TM_DEALER TD,TM_COMPANY TC, \n");
		sql.append("    TM_DEALER_ORG_RELATION            TDOR,\n");
		sql.append("    TM_ORG                            ORG,\n");
		sql.append("    TM_ORG                            ORG1\n");
		sql.append("    where TD.DEALER_TYPE="+DictCodeConstants.DEALER_TYPE_DVS+" AND TC.COMPANY_ID = TD.COMPANY_ID \n");
		sql.append(" AND TD.DEALER_ID = TDOR.DEALER_ID \n");
		sql.append(" AND ORG.ORG_ID = TDOR.ORG_ID \n");
		sql.append(" AND ORG.PARENT_ORG_ID = ORG1.ORG_ID and td.STATUS = 10011001 \n");
		sql.append("    and not exists (\n");
		sql.append("     select 1 from TT_VS_MONTHLY_FORECAST TVMF\n");
		sql.append("                  where   TVMF.DEALER_ID = TD.DEALER_ID\n");
		sql.append("                    AND TVMF.STATUS in ('"+DictCodeConstants.TT_VS_MONTHLY_FORECAST_SUBMIT+"',"+"'"+DictCodeConstants.TT_VS_MONTHLY_FORECAST_AUDIT+"',"+"'"+DictCodeConstants.TT_VS_MONTHLY_FORECAST_REPORT+"')\n");
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
			sql.append(" and TVMF.TASK_ID = ?");
			params.add(queryParam.get("taskCodeName"));
		}
		sql.append(" )\n");
		sql.append("     AND  TD.DEALER_TYPE="+DictCodeConstants.DEALER_TYPE_DVS+"  \n");
		
		return sql.toString();
	}
	
	/**
	 * 
	* @Title: forecastTotalDownload 
	* @Description: 生产订单查询（大区汇总下载） 
	* @param @param queryParam
	* @param @return    设定文件 
	* @return List<Map>    返回类型 
	* @throws
	 */
	public List<Map> forecastTotalDownload(Map<String, String> queryParam) {
		List<Object> params = new ArrayList<Object>();
		String sql = getforecastQueryTotalSql(queryParam,params);
		List<Map> forecastAuditList = OemDAOUtil.findAll(sql,params);
		return forecastAuditList;
	}
	
	/**
	 * 
	* @Title: forecastTotalDownload2 
	* @Description: 生产订单查询（全国汇总下载） 
	* @param @param queryParam
	* @param @return    设定文件 
	* @return List<Map>    返回类型 
	* @throws
	 */
	public List<Map> forecastTotalDownload2(Map<String, String> queryParam) {
		List<Object> params = new ArrayList<Object>();
		String sql = getOemForecastQueryTotalList2Sql(queryParam,params);
		List<Map> forecastAuditList = OemDAOUtil.findAll(sql,params);
		return forecastAuditList;
	}
	
	/**
	 * 
	* @Title: forecastTotalDownload 
	* @Description:  生产订单查询（明细下载）
	* @param @param queryParam
	* @param @return    设定文件 
	* @return List<Map>    返回类型 
	* @throws
	 */
	public List<Map> forecastDetailDownload(Map<String, String> queryParam) {
		List<Object> params = new ArrayList<Object>();
		String sql = getOemForecastQueryDetailListSql(queryParam,params);
		List<Map> forecastAuditList = OemDAOUtil.findAll(sql,params);
		return forecastAuditList;
	}
	
	/**
	 * 
	* @Title: noHandInDealerDownload 
	* @Description: 生产订单查询（未提报组织下载） 
	* @param @param queryParam
	* @param @return    设定文件 
	* @return List<Map>    返回类型 
	* @throws
	 */
	public List<Map> noHandInDealerDownload(Map<String, String> queryParam) {
		List<Object> params = new ArrayList<Object>();
		String sql = getfindNoHandInDelears2Sql(queryParam,params);
		List<Map> forecastAuditList = OemDAOUtil.findAll(sql,params);
		int j = 1;
		for(int i=0;i<forecastAuditList.size();i++){
        	forecastAuditList.get(i).put("SERIAL_NUMBER", j);
        	j++;
        }
		return forecastAuditList;
	}

	/**
	 * 
	* @Title: getReportTaskNoList 
	* @Description:  生产订单号序列号跟踪(OTD)(获取任务编号)
	* @param @param queryParam
	* @param @return    设定文件 
	* @return List<Map>    返回类型 
	* @throws
	 */
	public List<Map> getReportTaskNoList(Map<String, String> queryParam) {
		StringBuilder sqlSb = new StringBuilder();
		sqlSb.append("select distinct TASK_ID from TT_VS_MONTHLY_FORECAST where 1=1  and status in ('"+DictCodeConstants.TT_VS_MONTHLY_FORECAST_REPORT+"','"+DictCodeConstants.TT_VS_MONTHLY_FORECAST_AUDIT+"')");
		List<Object> params = new ArrayList<Object>();
		if(!StringUtils.isNullOrEmpty(queryParam.get("yearcode"))){
			sqlSb.append(" and FORECAST_YEAR = ?");
			params.add(queryParam.get("yearcode"));
		}
		if(!StringUtils.isNullOrEmpty(queryParam.get("monthcode"))){
			sqlSb.append(" and FORECAST_MONTH = (select tc.CODE_CN_DESC from TC_CODE tc where 1=1 and tc.CODE_ID = ?)");
			params.add(queryParam.get("monthcode"));
		}
		//执行查询操作
		List<Map> result = OemDAOUtil.findAll(sqlSb.toString(),params);
		return result;
	}

	/**
	 * 
	* @Title: OTDfindBySerialNumber 
	* @Description: 生产订单号序列号跟踪(OTD)(查询) 
	* @param @param queryParam
	* @param @return    设定文件 
	* @return PageInfoDto    返回类型 
	* @throws
	 */
	public PageInfoDto OTDfindBySerialNumber(Map<String, String> queryParam) {
		List<Object> params = new ArrayList<Object>();
		String sql = getOTDfindBySerialNumberSql(queryParam,params);
		PageInfoDto pageInfoDto = OemDAOUtil.pageQuery(sql,params);
		return pageInfoDto;
	}
	private String getOTDfindBySerialNumberSql(Map<String, String> queryParam,
			List<Object> params){
		StringBuilder sql = new StringBuilder();
		sql.append(" SELECT tvmf.TASK_ID,TOR2.ORG_NAME BIG_AREA,TOR.ORG_NAME SMALL_AREA,TD.DEALER_NAME,TD.DEALER_CODE,TVMF.FORECAST_YEAR,TVMF.FORECAST_MONTH,POS.SERIAL_NUMBER PON,VM.BRAND_NAME,VM.GROUP_NAME,VM.COLOR_NAME,VM.TRIM_NAME,VM.MODEL_YEAR,VM.SERIES_NAME \n");
		 sql.append(" 	,POS.STATUS,CONCAT(TVMF.FORECAST_YEAR,'-',TVMF.FORECAST_MONTH) MONTH FROM PRO_ORDER_SERIAL POS, TT_VS_MONTHLY_FORECAST_DETAIL_COLOR TVMFDC, ("+getVwMaterialSql()+") VM,TT_VS_MONTHLY_FORECAST TVMF,TM_DEALER TD,\n");
		 sql.append(" 	 TT_VS_MONTHLY_FORECAST_DETAIL_DCS TVMFD, TM_DEALER_ORG_RELATION         TDOR,TM_ORG TOR,TM_ORG TOR2 \n");
		 sql.append("WHERE TVMFDC.DETAIL_COLOR_ID=POS.DETAIL_COLOR_ID  AND VM.MATERIAL_ID=TVMFDC.MATERIAL_ID AND TVMFD.DETAIL_ID=TVMFDC.DETAIL_ID \n");
		 sql.append(" 		  AND TVMFD.FORECAST_ID=TVMF.FORECAST_ID  AND TD.DEALER_ID=TVMF.DEALER_ID\n");
		 sql.append(" 		AND TOR.ORG_ID=TDOR.ORG_ID\n");
		 //国产化车系判断
		 sql.append("    AND VM.GROUP_TYPE="+DictCodeConstants.GROUP_TYPE_IMPORT+"\n");
		 sql.append("  AND TD.DEALER_ID=TDOR.DEALER_ID and TOR.PARENT_ORG_ID=TOR2.ORG_ID\n");  
		//品牌
		if(!StringUtils.isNullOrEmpty(queryParam.get("brandCode"))){
			sql.append(" and VM.BRAND_ID = ?");
			params.add(queryParam.get("brandCode"));
		}
		//车系
		if(!StringUtils.isNullOrEmpty(queryParam.get("seriesName"))){
			sql.append(" and VM.SERIES_ID = ?");
			params.add(queryParam.get("seriesName"));
		}
		//车款
		if(!StringUtils.isNullOrEmpty(queryParam.get("groupName"))){
			sql.append(" and VM.GROUP_ID = ?");
			params.add(queryParam.get("groupName"));
			
		}
		//大区
		if(!StringUtils.isNullOrEmpty(queryParam.get("bigOrgName"))){
			sql.append(" and TOR.PARENT_ORG_ID = ?");
			params.add(queryParam.get("bigOrgName"));
		}
		//小区
		if(!StringUtils.isNullOrEmpty(queryParam.get("smallOrgName"))){
			sql.append(" and TOR.ORG_ID = ?");
			params.add(queryParam.get("smallOrgName"));
		}
		//经销商
		if(!StringUtils.isNullOrEmpty(queryParam.get("dealerCode"))){
			sql.append(" and TD.DEALER_CODE = ?");
			params.add(queryParam.get("dealerCode"));
		}
		//PON号
		if(!StringUtils.isNullOrEmpty(queryParam.get("soNo"))){
			sql.append(" and POS.SERIAL_NUMBER = ?");
			params.add(queryParam.get("soNo"));
		}
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
			sql.append(" and TVMF.TASK_ID = ?");
			params.add(queryParam.get("taskCodeName"));
		}
		//状态
		if(!StringUtils.isNullOrEmpty(queryParam.get("status"))){
			sql.append(" and POS.STATUS = ?");
			params.add(queryParam.get("status"));
			System.err.println(queryParam.get("status"));
		}
		// sql.append(" ORDER BY BRAND_NAME,GROUP_NAME,SERIES_NAME,POS.SERIAL_NUMBER \n ");
		System.out.println("******************");
		System.out.println(sql.toString());
		System.out.println("******************");
		 return sql.toString();
	}

	/**
	 * 
	* @Title: getOemForecastQueryQueryListOtd 
	* @Description: 生产订单号序列号跟踪(OTD)(下载)  
	* @param @param queryParam
	* @param @return    设定文件 
	* @return List<Map>    返回类型 
	* @throws
	 */
	public List<Map> getOemForecastQueryQueryListOtd(Map<String, String> queryParam) {
		List<Object> params = new ArrayList<Object>();
		String sql = getOemForecastSql(queryParam,params);
		List<Map> oemForecastList = OemDAOUtil.findAll(sql,params);
		return oemForecastList;
	}
	private String getOemForecastSql(Map<String, String> queryParam,
			List<Object> params){
		StringBuilder sql = new StringBuilder();
		sql.append(" SELECT tvmf.TASK_ID,TOR2.ORG_NAME BIG_AREA,TOR.ORG_NAME SMALL_AREA,TD.DEALER_NAME,TD.DEALER_CODE,TVMF.FORECAST_YEAR,TVMF.FORECAST_MONTH,CONCAT(TVMF.FORECAST_YEAR,'-',TVMF.FORECAST_MONTH) MONTH,POS.SERIAL_NUMBER PON,VM.BRAND_NAME,VM.GROUP_NAME,VM.COLOR_NAME,VM.TRIM_NAME,VM.MODEL_YEAR,VM.SERIES_NAME \n");
		 sql.append(" 	,(CASE POS.STATUS WHEN '21112101' THEN '已确认流水号' WHEN '21112102' THEN '中进定金确认' WHEN '21112103' THEN '逾期未付定金撤销' WHEN '21112100' THEN 'OTD已审核订单' WHEN '21112104' THEN '无效流水号' ELSE '' END) STATUS \n");
		 sql.append(" ,CONCAT(TVMF.FORECAST_YEAR,'-',TVMF.FORECAST_MONTH) MONTH \n");
		 sql.append("   FROM PRO_ORDER_SERIAL POS, TT_VS_MONTHLY_FORECAST_DETAIL_COLOR TVMFDC, ("+getVwMaterialSql()+") VM,TT_VS_MONTHLY_FORECAST TVMF,TM_DEALER TD, \n");
		 sql.append(" 	 TT_VS_MONTHLY_FORECAST_DETAIL_DCS TVMFD, TM_DEALER_ORG_RELATION         TDOR,TM_ORG TOR,TM_ORG TOR2 \n");
		 sql.append("WHERE TVMFDC.DETAIL_COLOR_ID=POS.DETAIL_COLOR_ID  AND VM.MATERIAL_ID=TVMFDC.MATERIAL_ID AND TVMFD.DETAIL_ID=TVMFDC.DETAIL_ID \n");
		 sql.append(" 		  AND TVMFD.FORECAST_ID=TVMF.FORECAST_ID  AND TD.DEALER_ID=TVMF.DEALER_ID\n");
		 sql.append(" 		AND TOR.ORG_ID=TDOR.ORG_ID\n");
		 sql.append("  AND TD.DEALER_ID=TDOR.DEALER_ID and TOR.PARENT_ORG_ID=TOR2.ORG_ID\n");   
		//品牌
		if(!StringUtils.isNullOrEmpty(queryParam.get("brandCode"))){
			sql.append(" and VM.BRAND_ID = ?");
			params.add(queryParam.get("brandCode"));
		}
		//车系
		if(!StringUtils.isNullOrEmpty(queryParam.get("seriesName"))){
			sql.append(" and VM.SERIES_ID = ?");
			params.add(queryParam.get("seriesName"));
		}
		//车款
		if(!StringUtils.isNullOrEmpty(queryParam.get("groupName"))){
			sql.append(" and VM.GROUP_ID = ?");
			params.add(queryParam.get("groupName"));
		}
		//大区
		if(!StringUtils.isNullOrEmpty(queryParam.get("bigOrgName"))){
			sql.append(" and TOR.PARENT_ORG_ID = ?");
			params.add(queryParam.get("bigOrgName"));
		}
		//小区
		if(!StringUtils.isNullOrEmpty(queryParam.get("smallOrgName"))){
			sql.append(" and TOR.ORG_ID = ?");
			params.add(queryParam.get("smallOrgName"));
		}
		//经销商
		if(!StringUtils.isNullOrEmpty(queryParam.get("dealerCode"))){
			sql.append(" and TD.DEALER_CODE = ?");
			params.add(queryParam.get("dealerCode"));
		}
		//PON号
		if(!StringUtils.isNullOrEmpty(queryParam.get("soNo"))){
			sql.append(" and POS.SERIAL_NUMBER = ?");
			params.add(queryParam.get("soNo"));
		}
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
			sql.append(" and TVMF.TASK_ID = ?");
			params.add(queryParam.get("taskCodeName"));
		}
		//状态
		if(!StringUtils.isNullOrEmpty(queryParam.get("status"))){
			sql.append(" and POS.STATUS = ?");
			params.add(queryParam.get("status"));
		}
		 sql.append(" ORDER BY BRAND_NAME,GROUP_NAME,SERIES_NAME,POS.SERIAL_NUMBER \n ");
		
		 return sql.toString();
	}

	/**
	 * 
	* @Title: checkTaskId 
	* @Description: 验证临时表任务编号是否和导入是勾选的一致
	* @param @param taskId
	* @param @return    设定文件 
	* @return List<Map>    返回类型 
	* @throws
	 */
	public List<Map> checkTaskId(String taskId) {
		List<Object> params = new ArrayList<Object>();
		StringBuilder sql = new StringBuilder();
		sql.append("select T.ROW_NUMBER,T.TASK_ID \n");
		sql.append("   from TMP_VS_PRO_IMP_INFO_AUDIT   t\n");
		sql.append("   where 1=1 AND t.task_Id !="+taskId);
		List<Map> oemForecastList = OemDAOUtil.findAll(sql.toString(),params);
		return oemForecastList;
	}

	/**
	 * 
	* @Title: checkTaskId 
	* @Description: 验证临时表任务编号是否存在
	* @param @return    设定文件 
	* @return List<Map>    返回类型 
	* @throws
	 */
	public List<Map> checkTaskId() {
		List<Object> params = new ArrayList<Object>();
		StringBuilder sql = new StringBuilder();
		sql.append("select T.ROW_NUMBER,T.TASK_ID \n");
		sql.append("   from TMP_VS_PRO_IMP_INFO_AUDIT   t\n");
		sql.append("   where not exists (select 1 from TT_VS_MONTHLY_FORECAST tc\n");
		sql.append(" 					where 1=1 AND t.TASK_ID=tc.TASK_ID)\n");
		List<Map> list = OemDAOUtil.findAll(sql.toString(),params);
		return list;
	}

	/**
	 * 
	* @Title: checkMaterialId 
	* @Description: //验证临时表物料id是否存在
	* @param @return    设定文件 
	* @return List<Map>    返回类型 
	* @throws
	 */
	public List<Map> checkMaterialId() {
		List<Object> params = new ArrayList<Object>();
		StringBuilder sql = new StringBuilder();
		sql.append("select T.ROW_NUMBER,T.MATERIAL_ID \n");
		sql.append("   from TMP_VS_PRO_IMP_INFO_AUDIT   t\n");
		sql.append("   where not exists (select 1 from TT_VS_MONTHLY_FORECAST_DETAIL_COLOR tc\n");
		sql.append(" 					where 1=1 AND t.MATERIAL_ID=tc.material_Id)\n");
		List<Map> list = OemDAOUtil.findAll(sql.toString(),params);
		return list;
	}

	/**
	 * 
	* @Title: checkForecastId 
	* @Description: 验证临时表FORECASTID是否存在 
	* @param @return    设定文件 
	* @return List<Map>    返回类型 
	* @throws
	 */
	public List<Map> checkForecastId() {
		List<Object> params = new ArrayList<Object>();
		StringBuilder sql = new StringBuilder();
		sql.append("select T.ROW_NUMBER,T.FORECAST_ID \n");
		sql.append("   from TMP_VS_PRO_IMP_INFO_AUDIT   t\n");
		sql.append("   where not exists (select 1 from TT_VS_MONTHLY_FORECAST tc\n");
		sql.append(" 					where 1=1 AND t.FORECAST_ID=tc.FORECAST_ID)\n");
		List<Map> list = OemDAOUtil.findAll(sql.toString(),params);
		return list;
	}

	/**
	 * 
	* @Title: checkForecastIdStatus 
	* @Description: 验证临时表FORECASTID状态 
	* @param @return    设定文件 
	* @return List<Map<String,Object>>    返回类型 
	* @throws
	 */
	public List<Map> checkForecastIdStatus() {
		List<Object> params = new ArrayList<Object>();
		StringBuilder sql = new StringBuilder();
		sql.append("select T.ROW_NUMBER,T.FORECAST_ID \n");
		sql.append("   from TMP_VS_PRO_IMP_INFO_AUDIT   t\n");
		sql.append("   where  exists (select 1 from TT_VS_MONTHLY_FORECAST tc\n");
		sql.append(" 					where  T.FORECAST_ID=TC.FORECAST_ID AND Tc.STATUS!="+ OemDictCodeConstants.TT_VS_MONTHLY_FORECAST_SUBMIT+")\n");
		
		List<Map> list = OemDAOUtil.findAll(sql.toString(),params);
		return list;
	}

	/**
	 * 
	* @Title: checkDetailId 
	* @Description: 验证临时表detail_id是否存在
	* @param @return    设定文件 
	* @return List<Map>    返回类型 
	* @throws
	 */
	public List<Map> checkDetailId() {
		List<Object> params = new ArrayList<Object>();
		StringBuilder sql = new StringBuilder();
		sql.append("select T.ROW_NUMBER,T.DETAIL_ID \n");
		sql.append("   from TMP_VS_PRO_IMP_INFO_AUDIT   t\n");
		sql.append("   where not exists (select 1 from TT_VS_MONTHLY_FORECAST_DETAIL_COLOR tc\n");
		sql.append(" 					where t.detail_id=tc.detail_id)\n");
		List<Map> list = OemDAOUtil.findAll(sql.toString(),params);
		
		return list;
	}

	/**
	 * 
	* @Title: checkConfirmNum 
	* @Description: 验证临时表OTD确认数量是否是整数 
	* @param @return    设定文件 
	* @return List<Map>    返回类型 
	* @throws
	 */
	public List<Map> checkConfirmNum() {
		List<Object> params = new ArrayList<Object>();
		StringBuilder sql = new StringBuilder();
		sql.append("select T.ROW_NUMBER,T.CONFIRM_NUM \n");
		sql.append("   from TMP_VS_PRO_IMP_INFO_AUDIT   t\n");
		sql.append("   where  exists (select 1 from TT_VS_MONTHLY_FORECAST tc\n");
		sql.append(" 					where t.TASK_ID=tc.TASK_ID)\n");
		List<Map> list = OemDAOUtil.findAll(sql.toString(),params);
		
		return list;
	}

	/**
	 * 
	* @Title: getTmVsProImpAudit 
	* @Description: 验证临时表TMP_VS_PRO_IMP_INFO_AUDIT数据 
	* @param @return    设定文件 
	* @return List<Map>    返回类型 
	* @throws
	 */
	public List<Map> getTmVsProImpAudit() {
		List<Object> params = new ArrayList<Object>();
		StringBuilder sql = new StringBuilder();
		sql.append("select * \n");
		sql.append("   from TMP_VS_PRO_IMP_INFO_AUDIT   t\n");
		List<Map> list = OemDAOUtil.findAll(sql.toString(),params);
		
		return list;
	}
	
	
}
