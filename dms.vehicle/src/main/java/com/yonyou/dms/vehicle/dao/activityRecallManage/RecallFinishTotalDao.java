package com.yonyou.dms.vehicle.dao.activityRecallManage;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.yonyou.dms.framework.DAO.OemBaseDAO;
import com.yonyou.dms.framework.DAO.OemDAOUtil;
import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.framework.domain.LoginInfoDto;
import com.yonyou.dms.framework.util.bean.ApplicationContextHelper;
import com.yonyou.dms.function.utils.common.StringUtils;

/**
* @author liujm
* @date 2017年4月20日
*/
@SuppressWarnings("all")

@Repository
public class RecallFinishTotalDao extends OemBaseDAO{
	/**
	 * 召回活动建立 查询
	 * @param queryParam
	 * @return
	 */
	public PageInfoDto getRecallFinishTotalQuery(Map<String, String> queryParam) {
		List<Object> params = new ArrayList<Object>();
		String sql = getRecallFinishQuerySql(queryParam, params);
		PageInfoDto pageInfoDto = OemDAOUtil.pageQuery(sql, params);
		return pageInfoDto;

	}
	/**
	 * 召回活动建立主页面 下载
	 * @param queryParam
	 * @return
	 */
	public List<Map> getRecallFinishTotalDownload(Map<String, String> queryParam) {
		List<Object> params = new ArrayList<Object>();
		String sql = getRecallFinishQuerySql(queryParam, params);
		List<Map> orderList = OemDAOUtil.downloadPageQuery(sql, params);
		return orderList;
	}
	
	/**
	 * SQL组装   
	 * @param queryParam
	 * @param params
	 * @return
	 */
	private String getRecallFinishQuerySql(Map<String, String> queryParam, List<Object> params) {
		//获取当前用户
		LoginInfoDto loginInfo = ApplicationContextHelper.getBeanByType(LoginInfoDto.class);
		StringBuffer sql = new StringBuffer("\n");
		sql.append("SELECT trs.RECALL_NO,	#召回编号 \n");
		sql.append("       trs.RECALL_NAME,	#召回名称 \n");
		sql.append("       trs.RECALL_ID,	#召回ID \n");
		sql.append("       DATE_FORMAT(TRS.RECALL_START_DATE,'%Y-%c-%d') AS RECALL_START_DATE,	#召回开始时间 \n");
		sql.append("       DATE_FORMAT(TRS.RECALL_END_DATE,'%Y-%c-%d') AS RECALL_END_DATE,	#召回结束时间 \n");
		sql.append("       vd.BIG_ORG_NAME AS BIG_AREA,	#大区BIG_ORG_ID \n");
		sql.append("       vd.SMALL_ORG_NAME AS SMALL_AREA,	#小区SMALL_ORG_ID \n");
		sql.append("       vd.DEALER_CODE,	#责任经销商 \n");
		sql.append("       vd.DEALER_SHORTNAME,	#责任经销商简称 \n");
		sql.append("       CONCAT(t.RECALL_NUM) RECALL_NUM,	#计划目标 \n");
		sql.append("       CONCAT(t.RECALL_NUM1) RECALL_NUM1,	#完成目标 \n");
		sql.append("       CONCAT(FORMAT(IFNULL(RECALL_NUM1/RECALL_NUM,0), 2)*100,'%') AS RECALL_RATE	#完成率 \n");
		sql.append("  FROM TT_RECALL_SERVICE_DCS trs, \n");
		sql.append("       (SELECT DUTY_DEALER, \n");
		sql.append("               RECALL_ID, \n");
		sql.append("               COUNT(1) AS RECALL_NUM,	 #计划目标 \n");
		sql.append("               SUM(CASE  RECALL_STATUS WHEN 40331002 THEN 1 ELSE 0 END  ) AS RECALL_NUM1	#完成目标 \n");
		sql.append("          FROM TT_RECALL_VEHICLE_DCS \n");
		sql.append("        GROUP BY DUTY_DEALER, RECALL_ID) t, \n");
		sql.append("	(SELECT TD.DEALER_ID,TD.DEALER_TYPE,TD.DEALER_CODE,TD.DEALER_SHORTNAME,TD.DEALER_NAME ,TD.STATUS,    ORG2.ORG_ID big_org_id, ORG2.ORG_CODE BIG_ORG_CODE,ORG2.ORG_NAME BIG_ORG_NAME,    ORG3.ORG_ID small_org_id,ORG3.ORG_CODE small_org_code,ORG3.ORG_NAME small_org_name      \n");
		sql.append("		FROM TM_DEALER TD , TM_DEALER_ORG_RELATION TDOR , TM_ORG ORG3, TM_ORG ORG2      \n");
		sql.append("		WHERE TD.DEALER_ID = TDOR.DEALER_ID AND TDOR.ORG_ID = ORG3.ORG_ID             AND ORG3.PARENT_ORG_ID = ORG2.ORG_ID  \n");
		sql.append("		UNION ALL  \n");
		sql.append("		SELECT TD.DEALER_ID,TD.DEALER_TYPE,TD.DEALER_CODE,TD.DEALER_SHORTNAME,TD.DEALER_NAME ,TD.STATUS,    NULL big_org_id, NULL BIG_ORG_CODE,NULL BIG_ORG_NAME,    NULL small_org_id,NULL small_org_code,NULL small_org_name      \n");
		sql.append("		FROM TM_DEALER TD     \n");
		sql.append("		WHERE TD.DEALER_CODE = '00000') vd \n");
		sql.append(" WHERE 1=1 \n");
		sql.append("       AND trs.RECALL_ID = t.RECALL_ID \n");
		sql.append("       AND t.DUTY_DEALER = vd.DEALER_ID \n");
		//查询条件 
		//召回编号
		if(!StringUtils.isNullOrEmpty(queryParam.get("recallNo"))){
			sql.append("	AND TRS.RECALL_NO like '%"+queryParam.get("recallNo")+"%' \n");
		}
		//召回名称
		if(!StringUtils.isNullOrEmpty(queryParam.get("recallName"))){
			sql.append("	AND TRS.RECALL_NAME like '%"+queryParam.get("recallName")+"%' \n");
		}
		//召回开始时间
		if(!StringUtils.isNullOrEmpty(queryParam.get("beginDate"))){
			sql.append("	and DATE_FORMAT(TRS.RECALL_START_DATE,'%Y-%c-%d') <= DATE_FORMAT('"+queryParam.get("beginDate")+"','%Y-%c-%d') \n");
			sql.append("	and DATE_FORMAT(TRS.RECALL_END_DATE,'%Y-%c-%d') >= DATE_FORMAT('"+queryParam.get("beginDate")+"','%Y-%c-%d') \n");
		}
		//召回结束时间
		if(!StringUtils.isNullOrEmpty(queryParam.get("endDate"))){
			sql.append("	and DATE_FORMAT(TRS.RECALL_END_DATE,'%Y-%c-%d') >= DATE_FORMAT('"+queryParam.get("endDate")+"','%Y-%c-%d') \n");
			sql.append("	and DATE_FORMAT(TRS.RECALL_START_DATE,'%Y-%c-%d') <= DATE_FORMAT('"+queryParam.get("endDate")+"','%Y-%c-%d') \n");
		}
		//大区
		if(!StringUtils.isNullOrEmpty(queryParam.get("bigArea"))){
			sql.append("	AND vd.BIG_ORG_ID = "+queryParam.get("bigArea")+" \n");
		}
		//小区
		if(!StringUtils.isNullOrEmpty(queryParam.get("smallArea"))){
			sql.append("	AND vd.SMALL_ORG_ID = "+queryParam.get("smallArea")+" \n");
		}
		//经销商
		if(!StringUtils.isNullOrEmpty(queryParam.get("dealerCode"))){
			//sql.append("	AND vd.DEALER_CODE IN ("+queryParam.get("dealerCode")+") \n");
			sql.append("	AND vd.DEALER_CODE IN ( ? ) \n");
			params.add(queryParam.get("dealerCode"));
		}
		//dlr条件限定(经销商)
		if(loginInfo.getPoseType()!=null && loginInfo.getPoseType() == 10021002){
			sql.append("	AND vd.DEALER_CODE IN ('"+loginInfo.getDealerCode()+"') \n");
			
		}

		sql.append("	   \n");
		//sql.append("ORDER BY TRS.CREATE_DATE,TRS.RECALL_NO	#按照创建召回活动时间排序 \n");		
		
		return sql.toString();
	}
	
	/**
	 * 召回活动建立主页面  明细下载
	 * @param queryParam
	 * @return
	 */
	public List<Map> getRecallFinishTotalDownloadDetail(Map<String, String> queryParam) {
		//获取当前用户
		LoginInfoDto loginInfo = ApplicationContextHelper.getBeanByType(LoginInfoDto.class);
		List<Object> params = new ArrayList<Object>();
		StringBuffer sql = new StringBuffer("\n");
		sql.append("SELECT trs.RECALL_NO,	#召回编号 \n");
		sql.append("       trs.RECALL_NAME,	#召回名称 \n");
		sql.append("       DATE_FORMAT(trs.RECALL_START_DATE,'%Y-%c-%d') AS RECALL_START_DATE,	#召回开始时间 \n");
		sql.append("       DATE_FORMAT(trs.RECALL_END_DATE,'%Y-%c-%d') AS RECALL_END_DATE,	#召回结束时间 \n");
		sql.append("       tm.dealer_code,	#责任经销商代码 \n");
		sql.append("       trv.VIN,	#车辆vin \n");
		sql.append("       DATE_FORMAT(wr1.FINISH_DATE_MAX,'%Y-%c-%d') AS IN_REPAIR_DATE,	#最近进厂时间 \n");
		sql.append("       tc.code_desc AS RECALL_STATUS,	#完成状态 \n");
		sql.append("       DATE_FORMAT(wr.FINISH_DATE,'%Y-%c-%d') AS RECALL_REPAIR_DATE,	#召回时间 \n");
		sql.append("       wr.DEALER_CODE AS RECALL_CODE,	#实际召回经销商 \n");
		sql.append("       wr.REPAIR_NO  	#工单号 \n");
		sql.append("FROM TT_RECALL_VEHICLE_DCS trv \n");
		sql.append("INNER JOIN TT_RECALL_SERVICE_DCS trs ON trv.RECALL_ID = trs.RECALL_ID \n");
		sql.append("LEFT JOIN (SELECT wr.REPAIR_NO,wr.PACKAGE_CODE,wr.VIN,wr.FINISH_DATE, \n");
		sql.append("wr.DEALER_CODE \n");
		sql.append("FROM TT_WR_REPAIR_DCS wr) AS wr \n");
		sql.append("ON trv.VIN = wr.VIN AND wr.PACKAGE_CODE LIKE  '%trs.RECALL_NO%' \n");
		sql.append("LEFT JOIN ( SELECT MAX(WR.FINISH_DATE) AS FINISH_DATE_MAX,WR.VIN \n");
		sql.append(" FROM TT_WR_REPAIR_DCS WR ,TT_RECALL_VEHICLE_DCS trv \n");
		sql.append(" WHERE WR.vin = trv.vin \n");
		sql.append(" GROUP BY WR.VIN) wr1 ON wr1.vin = trv.vin \n");
		sql.append("LEFT JOIN TM_DEALER tm ON tm.DEALER_ID = trv.DUTY_DEALER    \n");
		sql.append("LEFT JOIN tc_code_DCS tc ON tc.CODE_ID = trv.RECALL_STATUS   \n");
		sql.append("LEFT JOIN (SELECT TD.DEALER_ID,TD.DEALER_TYPE,TD.DEALER_CODE,TD.DEALER_SHORTNAME,TD.DEALER_NAME ,TD.STATUS,    ORG2.ORG_ID big_org_id, ORG2.ORG_CODE BIG_ORG_CODE,ORG2.ORG_NAME BIG_ORG_NAME,    ORG3.ORG_ID small_org_id,ORG3.ORG_CODE small_org_code,ORG3.ORG_NAME small_org_name      \n");
		sql.append("	    FROM TM_DEALER TD , TM_DEALER_ORG_RELATION TDOR , TM_ORG ORG3, TM_ORG ORG2      \n");
		sql.append("	    WHERE TD.DEALER_ID = TDOR.DEALER_ID AND TDOR.ORG_ID = ORG3.ORG_ID             AND ORG3.PARENT_ORG_ID = ORG2.ORG_ID  \n");
		sql.append("	    UNION ALL  \n");
		sql.append("            SELECT TD.DEALER_ID,TD.DEALER_TYPE,TD.DEALER_CODE,TD.DEALER_SHORTNAME,TD.DEALER_NAME ,TD.STATUS,    NULL big_org_id, NULL BIG_ORG_CODE,NULL BIG_ORG_NAME,    NULL small_org_id,NULL small_org_code,NULL small_org_name      \n");
		sql.append("	    FROM TM_DEALER TD     \n");
		sql.append("            WHERE TD.DEALER_CODE = '00000' ) vd ON tm.DEALER_ID = vd.DEALER_ID    \n");
		sql.append("WHERE 1=1   \n");
		//条件 
		//召回编号
		if(!StringUtils.isNullOrEmpty(queryParam.get("recallNo"))){
			sql.append("	AND TRS.RECALL_NO like '%"+queryParam.get("recallNo")+"%' \n");
		}
		//召回名称
		if(!StringUtils.isNullOrEmpty(queryParam.get("recallName"))){
			sql.append("	AND TRS.RECALL_NAME like '%"+queryParam.get("recallName")+"%' \n");
		}
		//召回开始时间
		if(!StringUtils.isNullOrEmpty(queryParam.get("beginDate"))){
			sql.append("	and DATE_FORMAT(TRS.RECALL_START_DATE,'%Y-%c-%d') <= DATE_FORMAT('"+queryParam.get("beginDate")+"','%Y-%c-%d') \n");
		
		}
		//召回结束时间
		if(!StringUtils.isNullOrEmpty(queryParam.get("endDate"))){
			sql.append("	and DATE_FORMAT(TRS.RECALL_END_DATE,'%Y-%c-%d') >= DATE_FORMAT('"+queryParam.get("endDate")+"','%Y-%c-%d') \n");
			
		}
		//大区
		if(!StringUtils.isNullOrEmpty(queryParam.get("bigArea"))){
			sql.append("	AND vd.BIG_ORG_ID = "+queryParam.get("bigArea")+" \n");
		}
		//小区
		if(!StringUtils.isNullOrEmpty(queryParam.get("smallArea"))){
			sql.append("	AND vd.SMALL_ORG_ID = "+queryParam.get("smallArea")+" \n");
		}
		//经销商
		if(!StringUtils.isNullOrEmpty(queryParam.get("dealerCode"))){
			sql.append("	AND tm.DEALER_CODE  IN ( ? ) \n");
			params.add(queryParam.get("dealerCode"));
		}
		//dlr条件限定(经销商)
		if(loginInfo.getPoseType()!=null && loginInfo.getPoseType() == 10021002){
			sql.append("	AND vd.DEALER_CODE IN ('"+loginInfo.getDealerCode()+"') \n");
			
		}
		List<Map> orderList = OemDAOUtil.downloadPageQuery(sql.toString(), params);
		return orderList;
	}
	
	
	
	/**
	 * 明细页面 查询
	 * @param queryParam
	 * @return
	 */
	public PageInfoDto getDetailRecallFinishTotalQuery(Long recallId, String dealerCode) {
		List<Object> params = new ArrayList<Object>();
		String sql = getDetailQuerySql( recallId, dealerCode, params);
		PageInfoDto pageInfoDto = OemDAOUtil.pageQuery(sql, params);
		return pageInfoDto;

	}
	/**
	 * 明细下载 召回ID、经销商CODE 限制
	 * @param queryParam
	 * @return
	 */
	public List<Map> getDetailQuerySql(Long recallId, String dealerCode) {
		List<Object> params = new ArrayList<Object>();
		String sql = getDetailQuerySql( recallId, dealerCode, params);
		List<Map> orderList = OemDAOUtil.downloadPageQuery(sql, params);
		return orderList;
	}
	
	/**
	 * 明细 查询SQL
	 * @param queryParam
	 * @param params
	 * @return
	 */
	private String getDetailQuerySql(Long recallId, String dealerCode, List<Object> params) {
		StringBuffer sql = new StringBuffer("\n");
		sql.append("SELECT trs.RECALL_NO,	#召回编号 \n");
		sql.append("       trs.RECALL_NAME,	#召回名称 \n");
		sql.append("       DATE_FORMAT(trs.RECALL_START_DATE,'%Y-%c-%d') AS RECALL_START_DATE,	#召回开始时间 \n");
		sql.append("       DATE_FORMAT(trs.RECALL_END_DATE,'%Y-%c-%d') AS RECALL_END_DATE,	#召回结束时间 \n");
		sql.append("       tm.dealer_code,	#责任经销商代码 \n");
		sql.append("       trv.VIN,	#车辆vin \n");
		sql.append("       DATE_FORMAT(wr1.FINISH_DATE_MAX,'%Y-%c-%d') AS IN_REPAIR_DATE,	#最近进厂时间 \n");
		sql.append("       tc.code_desc AS RECALL_STATUS,	#完成状态 \n");
		sql.append("       DATE_FORMAT(wr.FINISH_DATE,'%Y-%c-%d') AS RECALL_REPAIR_DATE,	#召回时间 \n");
		sql.append("       wr.DEALER_CODE AS RECALL_CODE,	#实际召回经销商 \n");
		sql.append("       wr.REPAIR_NO  #工单号 \n");
		sql.append("FROM TT_RECALL_VEHICLE_DCS trv \n");
		sql.append("INNER JOIN TT_RECALL_SERVICE_DCS trs ON trv.RECALL_ID = trs.RECALL_ID \n");
		sql.append("LEFT JOIN (SELECT wr.REPAIR_NO,wr.PACKAGE_CODE,wr.VIN,wr.FINISH_DATE, \n");
		sql.append("wr.DEALER_CODE \n");
		sql.append("FROM TT_WR_REPAIR_DCS wr) AS wr \n");
		sql.append("ON trv.VIN = wr.VIN AND wr.PACKAGE_CODE LIKE  '%trs.RECALL_NO%' \n");
		sql.append("LEFT JOIN ( SELECT MAX(WR.FINISH_DATE) AS FINISH_DATE_MAX,WR.VIN \n");
		sql.append(" FROM TT_WR_REPAIR_DCS WR ,TT_RECALL_VEHICLE_DCS trv \n");
		sql.append(" WHERE WR.vin = trv.vin \n");
		sql.append(" GROUP BY WR.VIN) wr1 ON wr1.vin = trv.vin \n");
		sql.append("LEFT JOIN TM_DEALER tm ON tm.DEALER_ID = trv.DUTY_DEALER \n");
		sql.append("LEFT JOIN tc_code_DCS tc ON tc.CODE_ID = trv.RECALL_STATUS \n");		
		sql.append("	where trv.RECALL_ID = "+recallId+" \n");
		sql.append("	and tm.dealer_code = '"+dealerCode+"' \n");
		sql.append("ORDER BY trv.DUTY_DEALER \n");
	
		return sql.toString();
	}
	
}
















