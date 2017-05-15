package com.yonyou.dms.vehicle.dao.activityRecallManage;

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
import com.yonyou.dms.function.utils.common.StringUtils;

/**
* @author liujiming
* @date 2017年4月13日
*/
@Repository
public class RecallVehicleManagerDao extends OemBaseDAO{
	
	/**
	 * 召回车辆管理 查询
	 * @param queryParam
	 * @return
	 */
	public PageInfoDto getRecallVehicleManagerQuery(Map<String, String> queryParam) {
		List<Object> params = new ArrayList<Object>();
		String sql = getRecallVehicleQuerySql(queryParam, params);
		PageInfoDto pageInfoDto = OemDAOUtil.pageQuery(sql, params);
		return pageInfoDto;

	}
	/**
	 * 召回车辆管理 下载
	 * @param queryParam
	 * @return
	 */
	public List<Map> getRecallVehicleDownloadList(Map<String, String> queryParam) {
		List<Object> params = new ArrayList<Object>();
		String sql = getRecallVehicleQuerySql(queryParam, params);
		List<Map> orderList = OemDAOUtil.downloadPageQuery(sql, params);
		return orderList;
	}
	
	/**
	 * SQL组装  
	 * 
	 * @param queryParam
	 * @param params
	 * @return
	 */
	private String getRecallVehicleQuerySql(Map<String, String> queryParam, List<Object> params) {

		StringBuffer sql = new StringBuffer("\n");
		sql.append("SELECT \n");
		sql.append("   IF(@pdept=ID,@rank:=@rank+1,@rank:=1) AS rank, \n");
		sql.append("	@pdept:=ID,@rownum:=@rownum+1,b.*  \n");
		sql.append("   FROM \n");
		sql.append("     (SELECT trv.VIN,  	#车辆vin \n");
		sql.append("       trs.RECALL_NO,	#召回编号 \n");
		sql.append("       trv.ID,	       #召回id \n");
		sql.append("       trs.RECALL_NAME,	  #召回名称 \n");
		sql.append("       trs.RECALL_THEME,	#召回类别 \n");
		sql.append("       trs.RECALL_STATUS,	#召回状态 \n");
		sql.append("       DATE_FORMAT(trs.RECALL_START_DATE,'%Y-%c-%d') AS RECALL_START_DATE,	  #召回开始时间 \n");
		sql.append("       DATE_FORMAT(trs.RECALL_END_DATE,'%Y-%c-%d') AS RECALL_END_DATE,	  #召回结束时间 \n");
		sql.append("       tor2.ORG_NAME AS BIG_AREA,	  #大区 \n");
		sql.append("       tor3.ORG_NAME AS SMALL_AREA,	  #小区 \n");
		sql.append("       td.DEALER_CODE,	  #经销商代码 \n");
		sql.append("       td.DEALER_SHORTNAME,	  #经销商简称 \n");
		sql.append("       trv.RECALL_STATUS AS TRV_RECALL_STATUS,	  # 完成状态 \n");
		sql.append("       trv.DMS_RECALL_STATUS,	 #DMS \n");
		sql.append("       trv.GCS_RECALL_STATUS,	  #GCS \n");
		sql.append("       wr.REPAIR_NO,    #工单号 \n");
		sql.append("       wr.BALANCE_DATE,    #工单结算日期 \n");
		sql.append("       DATE_FORMAT(wr.BALANCE_TIME,'%Y-%c-%d') AS BALANCE_TIME,    #结算时间 \n");
		sql.append("       DATE_FORMAT(TRV.EXPECT_RECALL_DATE,'%Y-%c-%d') AS EXPECT_RECALL_DATE	#预计召回时间 \n");
		sql.append("FROM TT_RECALL_VEHICLE_DCS trv \n");
		sql.append("INNER JOIN TT_RECALL_SERVICE_DCS trs ON trv.RECALL_ID = trs.RECALL_ID \n");
		sql.append("INNER JOIN TM_DEALER td ON trv.DUTY_DEALER=td.DEALER_ID \n");
		sql.append("LEFT JOIN TM_DEALER_ORG_RELATION tdor ON tdor.DEALER_ID = td.DEALER_ID \n");
		sql.append("LEFT JOIN TM_ORG tor3 ON tor3.ORG_ID=tdor.ORG_ID AND tor3.ORG_LEVEL=3 AND tor3.BUSS_TYPE =12351002 \n");
		sql.append("LEFT JOIN TM_ORG tor2 ON tor2.ORG_ID = tor3.PARENT_ORG_ID AND tor2.ORG_LEVEL=2 AND tor3.BUSS_TYPE =12351002 \n");
		sql.append("LEFT JOIN TT_WR_REPAIR_DCS wr \n");
		sql.append("ON trv.VIN = wr.VIN  AND wr.PACKAGE_CODE LIKE  '%trs.RECALL_NO%'   AND wr.REFUND<>12781001 AND wr.IS_DEL=0 \n");
		sql.append("WHERE 1=1 \n");
		
		if(!StringUtils.isNullOrEmpty(queryParam.get("recallNo"))){
			sql.append("   AND TRS.RECALL_NO like '%"+queryParam.get("recallNo")+"%' \n");
		}
		
		if(!StringUtils.isNullOrEmpty(queryParam.get("recallName"))){
			sql.append("   AND TRS.RECALL_NAME like '%"+queryParam.get("recallName")+"%' \n");
		}
		
		if(!StringUtils.isNullOrEmpty(queryParam.get("beginDate"))){
			sql.append("   AND TRV.EXPECT_RECALL_DATE >= DATE_FORMAT('"+queryParam.get("beginDate")+" 00:00:00', '%Y-%c-%d %H:%i:%s') \n");
		}
		
		if(!StringUtils.isNullOrEmpty(queryParam.get("endDate"))){
			sql.append("   AND TRV.EXPECT_RECALL_DATE <= DATE_FORMAT('"+queryParam.get("endDate")+" 23:59:59', '%Y-%c-%d %H:%i:%s') \n");
		}
		
		if(!StringUtils.isNullOrEmpty(queryParam.get("recallStatus"))){
			sql.append("   AND TRV.RECALL_STATUS = "+queryParam.get("recallStatus")+" \n");
		}
		
		if(!StringUtils.isNullOrEmpty(queryParam.get("bigArea"))){
			sql.append("   AND tor2.ORG_ID = "+queryParam.get("bigArea")+" \n");
		}
		
		if(!StringUtils.isNullOrEmpty(queryParam.get("smallArea"))){
			sql.append("   AND tor3.ORG_ID = "+queryParam.get("smallArea")+" \n");
		}
		
		if(!StringUtils.isNullOrEmpty(queryParam.get("dealerCode"))){
			sql.append("   AND td.DEALER_CODE IN ("+queryParam.get("dealerCode")+") \n");
		}
		
		if(!StringUtils.isNullOrEmpty(queryParam.get("vin"))){
			sql.append("   AND TRV.VIN  LIKE '%"+queryParam.get("vin")+"%' \n");
		}
		
		if(!StringUtils.isNullOrEmpty(queryParam.get("beginTime"))){
			sql.append("   AND WR.BALANCE_TIME >= DATE_FORMAT('"+queryParam.get("beginTime")+" 00:00:00', '%Y-%c-%d %H:%i:%s') \n");
		}
		if(!StringUtils.isNullOrEmpty(queryParam.get("endTime"))){
			sql.append("   AND WR.BALANCE_TIME <= DATE_FORMAT('"+queryParam.get("endTime")+" 23:59:59', '%Y-%c-%d %H:%i:%s') \n");
		}
		sql.append("    ) b, \n");
		sql.append("      (SELECT @rownum :=0 , @pdept :=NULL,@rank:=0) a \n");
		sql.append(" \n");

		return sql.toString();
	}
	/**
	 * 根据Id查询车辆信息
	 * @param id
	 * @return
	 */
	public Map getRecallDutyDealerInitList(Long id) {
		List<Object> params = new ArrayList<Object>();
		StringBuffer sql = new StringBuffer("\n");
		sql.append("SELECT TRS.RECALL_NO,	#召回编号 \n");
		sql.append("       TRS.RECALL_NAME,		#召回名称 \n");
		sql.append("       TC1.CODE_DESC AS RECALL_THEME,	#召回类别 \n");
		sql.append("       TC2.CODE_DESC AS RECALL_TYPE,	#召回参与方式 \n");
		sql.append("       DATE_FORMAT(TRS.RECALL_START_DATE,'%Y-%c-%d') AS RECALL_START_DATE,	#召回开始时间 \n");
		sql.append("       DATE_FORMAT(TRS.RECALL_END_DATE,'%Y-%c-%d') AS RECALL_END_DATE,		#召回结束时间 \n");
		sql.append("       TM.DEALER_CODE,	#责任经销商 \n");
		sql.append("       TRV.ID			#召回车辆ID \n");
		sql.append("	FROM TT_RECALL_SERVICE_DCS TRS, \n");
		sql.append("		TT_RECALL_VEHICLE_DCS TRV, \n");
		sql.append("		TC_CODE_DCS TC1, \n");
		sql.append("		TC_CODE_DCS TC2, \n");
		sql.append("		TM_DEALER TM \n");
		sql.append("     WHERE TRS.RECALL_ID = TRV.RECALL_ID \n");
		sql.append("       AND TC1.CODE_ID = TRS.RECALL_THEME \n");
		sql.append("       AND TC2.CODE_ID = TRS.RECALL_TYPE \n");
		sql.append("       AND TM.DEALER_ID = TRV.DUTY_DEALER \n");
		sql.append("       AND TRV.ID = "+id+" \n");
		List<Map> list = OemDAOUtil.findAll(sql.toString(), params);
		Map map = new HashMap();
		if(list !=null && list.size()>0){
			map = list.get(0);
		}
		return map;
	}
	/**
	 * 根据经销商CODE查询ID
	 * @param dealerCode
	 * @return
	 */
	public Long getDealerIdByCode(String dealerCode) {
		List<Object> params = new ArrayList<Object>();
		StringBuffer sql = new StringBuffer("\n");
		sql.append("	SELECT * FROM TM_DEALER WHERE  DEALER_CODE = '"+dealerCode+"'	\n");
		List<Map> list = OemDAOUtil.findAll(sql.toString(), params);
		Long id = (long) 0 ;
		if(list != null && list.size()>0 ){
			id = Long.parseLong(list.get(0).get("DEALER_ID").toString());
		}
	return id;
	}
	/**
	 * 校验临时表数据TMP_RECALL_VEHICLE_DCS
	 * @param queryParam
	 * @return
	 */
	public List<Map> checkData2() {
		List<Object> params = new ArrayList<Object>();
		StringBuffer sql = new StringBuffer("\n");
		sql.append("	select ROW_NO ROW_NUM,'召回编号为空或车架号为空' ERROR FROM TMP_RECALL_VEHICLE_DCS where RECALL_NO is null or RECALL_NO  = '' or VIN is null or VIN  = '' \n");
		sql.append("	 union all \n");
		sql.append("	select m.ROW_NO ROW_NUM,CONCAT('召回编号：',m.RECALL_NO,'不存在') AS ERROR FROM TMP_RECALL_VEHICLE_DCS  m left join TT_RECALL_SERVICE_DCS t  on  t.RECALL_NO = m.RECALL_NO where t.RECALL_NO is null or VIN  = '' \n");
		sql.append("	 union all \n");
		sql.append("	select m.ROW_NO ROW_NUM,CONCAT('车架号：',m.VIN,',不存在') AS ERROR from TMP_RECALL_VEHICLE_DCS m left join TM_VEHICLE_DEC t on t.VIN = m.VIN where t.VIN is null or t.VIN  = '' \n");
		sql.append("	 union all \n");
		sql.append("	select m.ROW_NO ROW_NUM,CONCAT('召回活动信息车架号：' ,m.VIN , ',不存在') AS ERROR from TMP_RECALL_VEHICLE_DCS m left join TT_RECALL_SERVICE_DCS  s on m.RECALL_NO = s.RECALL_NO \n");
		sql.append("		left join TT_RECALL_VEHICLE_DCS v on v.VIN = m.VIN and v.RECALL_ID = s.RECALL_ID where v.vin is null or v.vin = '' or  s.RECALL_NO is null or s.RECALL_NO ='' \n");
		sql.append(" \n");

		List<Map> resultList = OemDAOUtil.findAll(sql.toString(), params);
		return resultList;
	}
	/**
	 * 查询临时表数据
	 * @return
	 */
	public List<Map> findTmpRecallVehicleDcsList() {
		//获取当前用户
		LoginInfoDto loginInfo = ApplicationContextHelper.getBeanByType(LoginInfoDto.class);
		List<Object> params = new ArrayList<Object>();
		StringBuffer sql = new StringBuffer("\n");
		sql.append(" SELECT * FROM  tmp_recall_vehicle_dcs   \n");
		sql.append(" WHERE  CREATE_BY =  "+loginInfo.getUserId()+"  \n");
		sql.append(" order by ROW_NO  \n");
		List<Map> resultList = OemDAOUtil.findAll(sql.toString(), params);
		return resultList;
	}
}







