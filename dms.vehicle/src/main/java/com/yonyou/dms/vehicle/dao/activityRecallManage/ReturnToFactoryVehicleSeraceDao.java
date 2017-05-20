package com.yonyou.dms.vehicle.dao.activityRecallManage;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.yonyou.dms.framework.DAO.OemBaseDAO;
import com.yonyou.dms.framework.DAO.OemDAOUtil;
import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.function.utils.common.StringUtils;

/**
* @author liujm
* @date 2017年4月22日
*/
@SuppressWarnings("all")

@Repository
public class ReturnToFactoryVehicleSeraceDao extends OemBaseDAO{
	
	

	
	
	/**
	 * 返厂未召回车辆查询
	 * @param queryParam
	 * @return
	 */
	public PageInfoDto getReturnToFactoryVehicleSeraceQuery(Map<String, String> queryParam) {
		List<Object> params = new ArrayList<Object>();
		String sql = getQuerySql(queryParam, params);
		PageInfoDto pageInfoDto = OemDAOUtil.pageQuery(sql, params);
		return pageInfoDto;

	}
	/**
	 * 返厂未召回车辆下载
	 * @param queryParam
	 * @return
	 */
	public List<Map> getReturnToFactoryVehicleSeraceDownload(Map<String, String> queryParam) {
		List<Object> params = new ArrayList<Object>();
		String sql = getQuerySql(queryParam, params);
		List<Map> orderList = OemDAOUtil.downloadPageQuery(sql, params);
		return orderList;
	}
	
	/**
	 * SQL组装   
	 * @param queryParam
	 * @param params
	 * @return
	 */
	private String getQuerySql(Map<String, String> queryParam, List<Object> params) {
		StringBuffer sql = new StringBuffer("\n");
		sql.append("select * from ( SELECT v.VIN, \n");
		sql.append("	s.RECALL_NO, \n");
		sql.append("	s.RECALL_NAME, \n");
		sql.append("	DATE_FORMAT(s.RECALL_START_DATE,'%Y-%c-%d') RECALL_START_DATE, \n");
		sql.append("	DATE_FORMAT(s.RECALL_END_DATE,'%Y-%c-%d') RECALL_END_DATE, \n");
		sql.append("	tor2.ORG_NAME BIG_AREA, \n");
		sql.append("	tor.ORG_NAME SMALL_AREA, \n");
		sql.append("	CONCAT(REPLACE(td.DEALER_CODE,'A',''),'A') RESPONSIBILITY_DEALER_CODE, \n");
		sql.append("	td.DEALER_SHORTNAME RESPONSIBILITY_DEALER_NAME,        #责任经销商名称 \n");
		sql.append("	v.RECALL_STATUS, \n");
		sql.append("	DATE_FORMAT(MAX(wr.FINISH_DATE),'%Y-%c-%d') FINISH_DATE, \n");
		sql.append("	DATE_FORMAT(MAX(wr.MAKE_DATE),'%Y-%c-%d') MAKE_DATE, \n");
		sql.append("	CONCAT(REPLACE(d.DEALER_CODE,'A',''),'A') RETURM_DEALER_CODE, \n");
		sql.append("	d.DEALER_SHORTNAME    RETURM_DEALER_NAME    #回厂经销商名称 \n");
		sql.append("FROM TT_RECALL_VEHICLE_DCS v \n");
		sql.append("LEFT JOIN TT_RECALL_SERVICE_DCS s ON s.RECALL_ID = v.RECALL_ID \n");
		sql.append("LEFT JOIN TT_WR_REPAIR_DCS wr ON wr.VIN = v.VIN AND wr.FINISH_DATE BETWEEN s.RECALL_START_DATE AND s.RECALL_END_DATE  \n");
		sql.append("LEFT JOIN TM_DEALER td ON td.DEALER_ID = v.DUTY_DEALER \n");
		sql.append("LEFT JOIN TM_DEALER d ON d.DEALER_CODE = wr.DEALER_CODE  #回厂 \n");
		sql.append("LEFT JOIN TM_DEALER_ORG_RELATION tdor ON d.DEALER_ID = tdor.DEALER_ID \n");
		sql.append("LEFT JOIN TM_ORG tor ON tdor.ORG_ID = tor.ORG_ID \n");
		sql.append("LEFT JOIN TM_ORG tor2 ON tor.PARENT_ORG_ID = tor2.ORG_ID \n");
		sql.append("WHERE  1=1 \n");
		
		//条件RECALL_STATUS暂时注释		
		sql.append("   AND  s.RECALL_STATUS = '70451002' AND v.RECALL_STATUS = '40331001' \n");
		sql.append("   AND d.DEALER_CODE IS NOT NULL  \n");
		//条件
		//召回编号
		if(!StringUtils.isNullOrEmpty(queryParam.get("recallNo"))){
			sql.append("	and S.RECALL_NO like'%"+queryParam.get("recallNo")+"%' \n");
		}
		//召回名称
		if(!StringUtils.isNullOrEmpty(queryParam.get("recallName"))){
			sql.append("	AND S.RECALL_NAME like'%"+queryParam.get("recallName")+"%' \n");
		}
		//VIN
		if(!StringUtils.isNullOrEmpty(queryParam.get("vin"))){
			sql.append("	AND V.VIN like'%"+queryParam.get("vin")+"%' \n");
		}
		//责任经销商
		if(!StringUtils.isNullOrEmpty(queryParam.get("dealerCode"))){
			sql.append("	AND D.DEALER_CODE  in( ? ) \n");
			params.add(queryParam.get("dealerCode"));
		}
		//返厂经销商
		if(!StringUtils.isNullOrEmpty(queryParam.get("dealerCode2"))){
			sql.append("	AND TD.DEALER_CODE in( ? ) \n");
			params.add(queryParam.get("dealerCode2"));
		}
		//返厂开始日期
		if(!StringUtils.isNullOrEmpty(queryParam.get("beginDate"))){
			sql.append("	AND WR.FINISH_DATE >= DATE_FORMAT('"+queryParam.get("beginDate")+"','%Y-%c-%d')  \n");
		}		
		//返厂结束日期
		if(!StringUtils.isNullOrEmpty(queryParam.get("endDate"))){
			sql.append("	AND WR.FINISH_DATE <= DATE_FORMAT('"+queryParam.get("endDate")+" 23:59:59','%Y-%c-%d %H:%i:%s')  \n");
		}
		//完成状态
		if(!StringUtils.isNullOrEmpty(queryParam.get("dissatisfy"))){
			sql.append("	AND  V.RECALL_STATUS ='"+queryParam.get("dissatisfy")+"' \n");
		}
		//大区
		if(!StringUtils.isNullOrEmpty(queryParam.get("bigArea"))){
			sql.append("	AND  TOR2.ORG_ID ='"+queryParam.get("bigArea")+"' \n");
		}
		//小区
		if(!StringUtils.isNullOrEmpty(queryParam.get("smallArea"))){
			sql.append("	AND  TOR.ORG_ID ='"+queryParam.get("smallArea")+"' \n");
		}		
		sql.append(" GROUP BY v.VIN, \n");
		sql.append("	s.RECALL_NO,  \n");
		sql.append("	s.RECALL_NAME,  \n");
		sql.append("	s.RECALL_START_DATE, \n");
		sql.append("	s.RECALL_END_DATE, \n");
		sql.append("	tor2.ORG_NAME,  \n");
		sql.append("	tor.ORG_NAME, \n");
		sql.append("	td.DEALER_CODE, \n");
		sql.append("	td.DEALER_SHORTNAME, \n");
		sql.append("	v.RECALL_STATUS, \n");
		sql.append("	d.DEALER_CODE, \n");
		sql.append("	d.DEALER_SHORTNAME  ) tt \n");
		 return sql.toString();
	}
}
