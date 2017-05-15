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
* @date 2017年4月21日
*/
@SuppressWarnings("all")

@Repository
public class RecallVehiclePartDao extends OemBaseDAO{
	
	/**
	 * 召回活动建立 查询
	 * @param queryParam
	 * @return
	 */
	public PageInfoDto getRecallVehiclePartQuery(Map<String, String> queryParam) {
		List<Object> params = new ArrayList<Object>();
		String sql = getQuerySql(queryParam, params);
		PageInfoDto pageInfoDto = OemDAOUtil.pageQuery(sql, params);
		return pageInfoDto;

	}
	/**
	 * 召回活动建立主页面 下载
	 * @param queryParam
	 * @return
	 */
	public List<Map> getRecallVehiclePartDownload(Map<String, String> queryParam) {
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
		//获取当前用户
		LoginInfoDto loginInfo = ApplicationContextHelper.getBeanByType(LoginInfoDto.class);
		StringBuffer sql = new StringBuffer("\n");
		sql.append("SELECT  \n");
		sql.append(" tab2.RECALL_ID,      #召回id \n");
		sql.append("    tab2.RECALL_NO,           #召回编号 \n");
		sql.append("    tab2.RECALL_NAME,         #召回名称 \n");
		sql.append("    tab2.GROUP_NO,            #组 \n");
		sql.append("    tab2.RECALL_START_DATE,     #召回开始时间 \n");
		sql.append("    tab2.RECALL_END_DATE,         #召回结束时间 \n");
		sql.append("    tab2.DUTY_DEALER,        #责任经销商id \n");
		sql.append("    tab2.DUTY_DEALER_CODE,   #责任经销商code \n");
		sql.append("    tab2.DEALER_SHORTNAME,   #责任经销商简称 \n");
		sql.append("    tab2.BIG_AREA,          #大区 \n");
		sql.append("    tab2.SMALL_AREA,          #小区 \n");
		sql.append("    tab2.TOTAL_VEHICLE_NUM,       #车数量 \n");
		sql.append("    tab2.C_TOTAL_VEHICLE_NUM CTWN,  \n");
		sql.append("    tab2.SATISFY,             #满足数量 \n");
		sql.append("    tab2.DISSATISFY,        #不满足数量 \n");
		sql.append("    tab2.IFSATISFY   #是否满足（不满足1，满足0） \n");
		sql.append("FROM (	SELECT  tab.RECALL_ID,      #召回id \n");
		sql.append("	ts.RECALL_NO,           #召回编号 \n");
		sql.append("	ts.RECALL_NAME,         #召回名称 \n");
		sql.append("   	tab.GROUP_NO,            #组 \n");
		sql.append("	DATE_FORMAT(ts.RECALL_START_DATE,'%Y-%c-%d') RECALL_START_DATE,     #召回开始时间 \n");
		sql.append("	DATE_FORMAT(ts.RECALL_END_DATE,'%Y-%c-%d') RECALL_END_DATE,         #召回结束时间 \n");
		sql.append("	tab.DUTY_DEALER,        #责任经销商id \n");
		sql.append("	tab.DUTY_DEALER_CODE,   #责任经销商code \n");
		sql.append("	tab.DEALER_SHORTNAME,   #责任经销商简称 \n");
		sql.append("	tab.TOTAL_VEHICLE_NUM, #未完成车辆总数 \n");
		sql.append("	tab.C_TOTAL_VEHICLE_NUM, #已完成车辆总数 \n");
		sql.append("	tor2.ORG_NAME  BIG_AREA,          #大区 \n");
		sql.append("	tor.ORG_NAME   SMALL_AREA,          #小区  \n");
		sql.append("	SUM(SATISFY) SATISFY,             #满足数量 \n");
		sql.append("	SUM(DISSATISFY) DISSATISFY,        #不满足数量 \n");
		sql.append("	CASE WHEN  SUM(DISSATISFY)> 0 THEN 00 ELSE 11 END IFSATISFY   #是否满足（不满足00，满足11） \n");
		sql.append("	FROM  (		 \n");
		sql.append("		SELECT pn.RECALL_ID,    #主id \n");
		sql.append("		pn.DUTY_DEALER,         #责任经销商id \n");
		sql.append("		pn.DEALER_CODE DUTY_DEALER_CODE, #责任经销商code \n");
		sql.append("		pn.DEALER_SHORTNAME,    #责任经销商简称 \n");
		sql.append("		PN.TOTAL_VEHICLE_NUM ,   #未完成车辆总数   \n");
		sql.append("		PN.C_TOTAL_VEHICLE_NUM,  #已完成车辆总数  \n");
		sql.append("		pn.GROUP_NO,            #组 \n");
		sql.append("		pn.PART_CODE,            #配件code \n");
		sql.append("		pn.PART_NAME,              #配件name \n");
		sql.append("		pn.TOTAL_PART_NUM,      #所需配件数 \n");
		sql.append("		CASE WHEN pds.PART_NUM IS NULL THEN 0 ELSE pds.PART_NUM END STOCK_PART_NUM ,           #库存配件数 \n");
		sql.append("		CASE WHEN pn.TOTAL_PART_NUM > (CASE WHEN pds.PART_NUM IS NULL THEN 0 ELSE pds.PART_NUM END) THEN 1 ELSE 0 END DISSATISFY,  #不满足（1是0不是） \n");
		sql.append("		CASE WHEN pn.TOTAL_PART_NUM <= (CASE WHEN pds.PART_NUM IS NULL THEN 0 ELSE pds.PART_NUM END) THEN 1 ELSE 0 END SATISFY  #满足（1是0不是） \n");
		sql.append("		FROM  (SELECT DISTINCT CNVN.*,  \n");
		sql.append("		p.PART_NUM*CNVN.TOTAL_VEHICLE_NUM*(CASE WHEN p.CHECK_STATUS=70461001 THEN 1 ELSE p.CHANGE_RATIO END) AS TOTAL_PART_NUM , #配件总数  \n");
		sql.append("		p.PART_CODE,         #配件CODE \n");
		sql.append("		pp.PART_NAME       #配件名称   \n");
		sql.append("		FROM  (SELECT  s.RECALL_ID,            #主ID \n");
		sql.append("		v.DUTY_DEALER,                  #责任经销商ID \n");
		sql.append("		CONCAT(REPLACE(td.DEALER_CODE,'A',''),'A') DEALER_CODE,     #责任经销商code \n");
		sql.append("		td.DEALER_SHORTNAME,            #责任经销商简称 \n");
		sql.append("		m.GROUP_NO,                    #组 \n");
		sql.append("		SUM(CVN) AS C_TOTAL_VEHICLE_NUM,   #完成车辆总数 \n");
		sql.append("		SUM(UVN) AS TOTAL_VEHICLE_NUM   #未完成车辆总数 \n");
		sql.append("		FROM ( SELECT \n");
		sql.append("				v.RECALL_ID, v.DUTY_DEALER, v.VIN, \n");
		sql.append("				CASE WHEN v.RECALL_STATUS = 40331001  THEN 1 ELSE 0 END UVN, \n");
		sql.append("				CASE WHEN v.RECALL_STATUS = 40331002  THEN 1 ELSE 0 END CVN \n");
		sql.append("				FROM TT_RECALL_VEHICLE_DCS v) v \n");
		sql.append("				LEFT JOIN TT_RECALL_SERVICE_DCS  s ON v.RECALL_ID = s.RECALL_ID \n");
		sql.append("				LEFT JOIN TM_DEALER td ON td.dealer_ID = v.DUTY_DEALER \n");
		sql.append("				LEFT JOIN TM_VEHICLE_DEC tv ON tv.VIN = v.VIN \n");
		sql.append("				INNER JOIN TT_RECALL_MATERIAL_DCS m ON m.MATERIAL_ID=tv.MATERIAL_ID  AND m.RECALL_ID = v.RECALL_ID \n");
		sql.append("				GROUP BY s.RECALL_ID,v.DUTY_DEALER,CONCAT(REPLACE(td.DEALER_CODE,'A',''),'A'),td.DEALER_SHORTNAME,m.GROUP_NO  ) CNVN \n");
		sql.append("			LEFT JOIN TT_RECALL_PART_DCS p ON  CNVN.GROUP_NO = p.GROUP_NO AND CNVN.RECALL_ID = p.RECALL_ID  \n");
		sql.append("			LEFT JOIN TT_PT_PART_BASE_DCS  pp ON pp.PART_CODE = p.PART_CODE    \n");
		sql.append("			) pn \n");
		sql.append("		LEFT JOIN  (SELECT CONCAT(REPLACE(tpds1.DEALER_CODE,'A',''),'A')  DEALER_CODE,       #经销商code  \n");
		sql.append("				td.DEALER_ID,                   #经销商 \n");
		sql.append("				tpds1.PART_NO,                  #配件编号 \n");
		sql.append("				SUM(tpds1.OH_COUNT)AS PART_NUM  #配件总库存 \n");
		sql.append("				FROM TT_PT_DEALER_STOCK_DCS tpds1 \n");
		sql.append("				LEFT JOIN TM_DEALER td ON  CONCAT(REPLACE(tpds1.DEALER_CODE,'A',''),'A')  = td.DEALER_CODE  \n");
		sql.append("				GROUP BY CONCAT(REPLACE(tpds1.DEALER_CODE,'A',''),'A') ,tpds1.PART_NO,td.DEALER_ID \n");
		sql.append("			)  PDS  ON  PDS.PART_NO = pn.PART_CODE  AND PDS.DEALER_CODE = pn.DEALER_CODE \n");
		sql.append("		WHERE 1=1 \n");
		//-------条件----
		//配件code
		if(!StringUtils.isNullOrEmpty(queryParam.get("partNo"))){
			sql.append("			and pn.PART_CODE  like '%"+queryParam.get("partNo")+"%'  \n");
		}
		//配件name
		if(!StringUtils.isNullOrEmpty(queryParam.get("partName"))){
			sql.append("			and pn.PART_NAME like '%"+queryParam.get("partName")+"%' \n");
		}
				
		sql.append("		) tab  \n");
		sql.append("	LEFT JOIN TT_RECALL_SERVICE_DCS ts ON tab.RECALL_ID = ts.RECALL_ID \n");
		sql.append("	LEFT JOIN TM_DEALER_ORG_RELATION tdor ON tab.DUTY_DEALER = tdor.DEALER_ID \n");
		sql.append("	LEFT JOIN TM_ORG tor ON tdor.ORG_ID = tor.ORG_ID \n");
		sql.append("	LEFT JOIN TM_ORG tor2 ON tor.PARENT_ORG_ID = tor2.ORG_ID \n");
		sql.append("	WHERE 1=1 \n");
		//------条件-------- 
		//召回编号
		if(!StringUtils.isNullOrEmpty(queryParam.get("recallNo"))){
			sql.append("		and  ts.RECALL_NO like '%"+queryParam.get("recallNo")+"%' \n");
		}
		//召回名称
		if(!StringUtils.isNullOrEmpty(queryParam.get("recallName"))){
			sql.append("		and  ts.RECALL_NAME like '%"+queryParam.get("recallName")+"%' \n");
		}
		//责任经销商
		if(!StringUtils.isNullOrEmpty(queryParam.get("dealerCode"))){
			sql.append("		AND tab.DUTY_DEALER_CODE  in( ? ) \n");			
			params.add(queryParam.get("dealerCode"));
		}
		//dlr条件限定(经销商)
		if(loginInfo.getPoseType()!=null && loginInfo.getPoseType() == 10021002){
				sql.append("	AND tab.DUTY_DEALER_CODE IN ('"+loginInfo.getDealerCode()+"') \n");
					
		}
				
		sql.append("	GROUP BY   tab.RECALL_ID,ts.RECALL_NO,ts.RECALL_NAME,tab.GROUP_NO,ts.RECALL_START_DATE,ts.RECALL_END_DATE,  \n");
		sql.append("	tab.DUTY_DEALER,tab.DUTY_DEALER_CODE,tab.DEALER_SHORTNAME, tab.TOTAL_VEHICLE_NUM,tab.C_TOTAL_VEHICLE_NUM, \n");
		sql.append("	tor2.ORG_NAME ,tor.ORG_NAME \n");
		sql.append("	) tab2  WHERE 1=1 \n");
		//-----条件-------
		//满足状态
		if(!StringUtils.isNullOrEmpty(queryParam.get("dissatisfy"))){
			Integer dissatisfy = 0;
			if(Integer.parseInt(queryParam.get("dissatisfy").toString()) == 10041002){
				dissatisfy = 1;
			}
			sql.append("        AND   IFSATISFY = "+dissatisfy+" \n");
		}
		
		return sql.toString();
	}
	
	/**
	 * 主页面 明细下载
	 * @param queryParam
	 * @return
	 */
	public List<Map> getRecallVehiclePartDownloadDetail(Map<String, String> queryParam) {
		//获取当前用户
		LoginInfoDto loginInfo = ApplicationContextHelper.getBeanByType(LoginInfoDto.class);
		
		List<Object> params = new ArrayList<Object>();
		StringBuffer sql = new StringBuffer("\n");
		sql.append("SELECT PN.RECALL_ID,    #主id \n");
		sql.append("  s.RECALL_NO, \n");
		sql.append("  s.RECALL_NAME, \n");
		sql.append("  DATE_FORMAT(s.RECALL_START_DATE,'%Y-%c-%d')  RECALL_START_DATE, \n");
		sql.append("  DATE_FORMAT(s.RECALL_END_DATE,'%Y-%c-%d')  RECALL_END_DATE, \n");
		sql.append("  pn.DUTY_DEALER,         #责任经销商id \n");
		sql.append("  pn.DEALER_CODE DUTY_DEALER_CODE, #责任经销商code \n");
		sql.append("  pn.DEALER_SHORTNAME,    #责任经销商简称 \n");
		sql.append("  tor2.ORG_NAME BIG_AREA,          #大区 \n");
		sql.append("  tor.ORG_NAME SMALL_AREA,          #小区 \n");
		sql.append("  PN.TOTAL_VEHICLE_NUM,   #车辆总数 \n");
		sql.append("  pn.C_TOTAL_VEHICLE_NUM, \n");
		sql.append("  pn.GROUP_NO,            #组 \n");
		sql.append("  pn.PART_CODE,            #配件code \n");
		sql.append("  pn.PART_NAME,              #配件name \n");
		sql.append("  ROUND(CASE WHEN pn.TOTAL_PART_NUM IS NULL THEN 0 ELSE pn.TOTAL_PART_NUM END ,0)  TOTAL_PART_NUM,      #所需配件数 \n");
		sql.append("  CASE WHEN pds.PART_NUM IS NULL THEN 0 ELSE pds.PART_NUM END STOCK_PART_NUM ,           #库存配件数 \n");
		sql.append("  CASE WHEN pn.TOTAL_PART_NUM > (CASE WHEN pds.PART_NUM IS NULL THEN 0 ELSE pds.PART_NUM END) THEN '不满足' ELSE '满足' END IFSATISFY  #不满足（1是0不是） \n");
		sql.append("  FROM (SELECT DISTINCT CNVN.*,  \n");
		sql.append("	p.PART_NUM*CNVN.TOTAL_VEHICLE_NUM*(CASE WHEN p.CHECK_STATUS=70461001 THEN 1 ELSE p.CHANGE_RATIO END) AS TOTAL_PART_NUM , #配件总数  \n");
		sql.append("	p.PART_CODE,         #配件CODE \n");
		sql.append("	pp.PART_NAME       #配件名称   \n");
		sql.append("	FROM  (SELECT  s.RECALL_ID,            #主ID \n");
		sql.append("	v.DUTY_DEALER,                  #责任经销商ID \n");
		sql.append("	CONCAT(REPLACE(td.DEALER_CODE,'A',''),'A') DEALER_CODE,     #责任经销商code \n");
		sql.append("	td.DEALER_SHORTNAME,            #责任经销商简称 \n");
		sql.append("	m.GROUP_NO,                    #组 \n");
		sql.append("	SUM(CVN) AS C_TOTAL_VEHICLE_NUM,   #完成车辆总数 \n");
		sql.append("	SUM(UVN) AS TOTAL_VEHICLE_NUM   #未完成车辆总数 \n");
		sql.append("	FROM ( SELECT \n");
		sql.append("		v.RECALL_ID, v.DUTY_DEALER, v.VIN, \n");
		sql.append("		CASE WHEN v.RECALL_STATUS = 40331001  THEN 1 ELSE 0 END UVN, \n");
		sql.append("		CASE WHEN v.RECALL_STATUS = 40331002  THEN 1 ELSE 0 END CVN \n");
		sql.append("		FROM TT_RECALL_VEHICLE_DCS v) v \n");
		sql.append("		LEFT JOIN TT_RECALL_SERVICE_DCS  s ON v.RECALL_ID = s.RECALL_ID \n");
		sql.append("		LEFT JOIN TM_DEALER td ON td.dealer_ID = v.DUTY_DEALER \n");
		sql.append("		LEFT JOIN TM_VEHICLE_DEC tv ON tv.VIN = v.VIN \n");
		sql.append("		INNER JOIN TT_RECALL_MATERIAL_DCS m ON m.MATERIAL_ID=tv.MATERIAL_ID  AND m.RECALL_ID = v.RECALL_ID \n");
		sql.append("		GROUP BY s.RECALL_ID,v.DUTY_DEALER,CONCAT(REPLACE(td.DEALER_CODE,'A',''),'A'),td.DEALER_SHORTNAME,m.GROUP_NO  ) CNVN \n");
		sql.append("	LEFT JOIN TT_RECALL_PART_DCS p ON  CNVN.GROUP_NO = p.GROUP_NO AND CNVN.RECALL_ID = p.RECALL_ID  \n");
		sql.append("	LEFT JOIN TT_PT_PART_BASE_DCS  pp ON pp.PART_CODE = p.PART_CODE   \n");
		sql.append("  \n");
		sql.append("  ) pn \n");
		sql.append("  LEFT JOIN TT_RECALL_SERVICE_DCS s ON s.RECALL_ID = PN.RECALL_ID \n");
		sql.append("  LEFT JOIN TM_DEALER td ON td.DEALER_ID = pn.DUTY_DEALER \n");
		sql.append("  LEFT JOIN   (SELECT CONCAT(REPLACE(tpds1.DEALER_CODE,'A',''),'A')  DEALER_CODE,       #经销商code  \n");
		sql.append("		td.DEALER_ID,                   #经销商 \n");
		sql.append("		tpds1.PART_NO,                  #配件编号 \n");
		sql.append("		SUM(tpds1.OH_COUNT)AS PART_NUM  #配件总库存 \n");
		sql.append("		FROM TT_PT_DEALER_STOCK_DCS tpds1 \n");
		sql.append("		LEFT JOIN TM_DEALER td ON  CONCAT(REPLACE(tpds1.DEALER_CODE,'A',''),'A')  = td.DEALER_CODE  \n");
		sql.append("		GROUP BY CONCAT(REPLACE(tpds1.DEALER_CODE,'A',''),'A') ,tpds1.PART_NO,td.DEALER_ID \n");
		sql.append("	 ) PDS  ON  PDS.PART_NO = pn.PART_CODE  AND PDS.DEALER_CODE = pn.DEALER_CODE  \n");
		sql.append("  LEFT JOIN TM_DEALER_ORG_RELATION tdor ON td.DEALER_ID = tdor.DEALER_ID \n");
		sql.append("  LEFT JOIN TM_ORG tor ON tdor.ORG_ID = tor.ORG_ID \n");
		sql.append("  LEFT JOIN TM_ORG tor2 ON tor.PARENT_ORG_ID = tor2.ORG_ID \n");
		sql.append("  WHERE 1=1 \n");
		//---条件-----
		//配件code
		if(!StringUtils.isNullOrEmpty(queryParam.get("partNo"))){
			sql.append("			and pn.PART_CODE  like '%"+queryParam.get("partNo")+"%'  \n");
		}
		//配件name
		if(!StringUtils.isNullOrEmpty(queryParam.get("partName"))){
			sql.append("			and pn.PART_NAME like '%"+queryParam.get("partName")+"%' \n");
		}
		//召回编号
		if(!StringUtils.isNullOrEmpty(queryParam.get("recallNo"))){
			sql.append("		and  s.RECALL_NO like '%"+queryParam.get("recallNo")+"%' \n");
		}
		//召回名称
		if(!StringUtils.isNullOrEmpty(queryParam.get("recallName"))){
			sql.append("		and  s.RECALL_NAME like '%"+queryParam.get("recallName")+"%' \n");
		}
		//责任经销商
		if(!StringUtils.isNullOrEmpty(queryParam.get("dealerCode"))){
			sql.append("		AND pn.DEALER_CODE  in( ? ) \n");			
			params.add(queryParam.get("dealerCode"));
		}
		//dlr条件限定(经销商)
		if(loginInfo.getPoseType()!=null && loginInfo.getPoseType() == 10021002){
			sql.append("	AND pn.DEALER_CODE  IN ('"+loginInfo.getDealerCode()+"') \n");
							
		}
		
		
		//满足状态
		if(!StringUtils.isNullOrEmpty(queryParam.get("dissatisfy"))){			
			if(Integer.parseInt(queryParam.get("dissatisfy").toString()) == 10041001){
				sql.append("        and  case when pn.TOTAL_PART_NUM > (case when pds.PART_NUM is null then 0 else pds.PART_NUM end) then '不满足' else '满足' end = '满足' \n");	
			}			
		}

		List<Map> orderList = OemDAOUtil.downloadPageQuery(sql.toString(), params);
		return orderList;
	}
	
	
	
	/**
	 * 查询  明细 （每行记录）
	 * @param queryParam
	 * @return
	 */
	public PageInfoDto getDetailRecallVehiclePartQuery(String dealerCode, Long recallId, String groupNo) {
		List<Object> params = new ArrayList<Object>();
		String sql = getDetailQuerySql(dealerCode, recallId, groupNo, params);
		PageInfoDto pageInfoDto = OemDAOUtil.pageQuery(sql, params);
		return pageInfoDto;

	}
	/**
	 * 明细下载（每行记录）
	 * @param queryParam
	 * @return
	 */
	public List<Map> getDetailRecallVehiclePartDownload(String dealerCode, Long recallId, String groupNo) {
		List<Object> params = new ArrayList<Object>();
		String sql = getDetailQuerySql(dealerCode, recallId, groupNo, params);
		List<Map> orderList = OemDAOUtil.downloadPageQuery(sql, params);
		return orderList;
	}
	
	/**
	 * SQL组装   明细页面
	 * @param queryParam
	 * @param params
	 * @return
	 */
	private String getDetailQuerySql(String dealerCode, Long recallId, String groupNo, List<Object> params) {

		StringBuffer sql = new StringBuffer("\n");
		sql.append(" SELECT PN.RECALL_ID,    #主id \n");
		sql.append("  s.RECALL_NO, \n");
		sql.append("  s.RECALL_NAME, \n");
		sql.append("  DATE_FORMAT(s.RECALL_START_DATE,'%Y-%c-%d')  RECALL_START_DATE, \n");
		sql.append("  DATE_FORMAT(s.RECALL_END_DATE,'%Y-%c-%d')  RECALL_END_DATE, \n");
		sql.append("  pn.GROUP_NO, \n");
		sql.append("  pn.DUTY_DEALER,         #责任经销商id \n");
		sql.append("  pn.DEALER_CODE DUTY_DEALER_CODE, #责任经销商code \n");
		sql.append("  pn.DEALER_SHORTNAME,    #责任经销商简称 \n");
		sql.append("  tor2.ORG_NAME BIG_AREA,          #大区 \n");
		sql.append("  tor.ORG_NAME SMALL_AREA,          #小区 \n");
		sql.append("  PN.TOTAL_VEHICLE_NUM,   #车辆总数 \n");
		sql.append("  pn.GROUP_NO,            #组 \n");
		sql.append("  pn.PART_CODE,            #配件code \n");
		sql.append("  pn.PART_NAME,              #配件name \n");
		sql.append("  ROUND(CASE WHEN pn.TOTAL_PART_NUM IS NULL THEN 0 ELSE pn.TOTAL_PART_NUM END ,0)  TOTAL_PART_NUM,      #所需配件数 \n");
		sql.append("  CASE WHEN pds.PART_NUM IS NULL THEN 0 ELSE pds.PART_NUM END STOCK_PART_NUM ,           #库存配件数 \n");
		sql.append("  CASE WHEN pn.TOTAL_PART_NUM > (CASE WHEN pds.PART_NUM IS NULL THEN 0 ELSE pds.PART_NUM END) THEN '不满足' ELSE '满足' END IFSATISFY  #不满足（1是0不是） \n");
		sql.append("  FROM (SELECT DISTINCT CNVN.*,  \n");
		sql.append("	p.PART_NUM*CNVN.TOTAL_VEHICLE_NUM*(CASE WHEN p.CHECK_STATUS=70461001 THEN 1 ELSE p.CHANGE_RATIO END) AS TOTAL_PART_NUM , #配件总数  \n");
		sql.append("	p.PART_CODE,         #配件CODE \n");
		sql.append("	pp.PART_NAME       #配件名称   \n");
		sql.append("	FROM  (SELECT  s.RECALL_ID,            #主ID \n");
		sql.append("	v.DUTY_DEALER,                  #责任经销商ID \n");
		sql.append("	CONCAT(REPLACE(td.DEALER_CODE,'A',''),'A') DEALER_CODE,     #责任经销商code \n");
		sql.append("	td.DEALER_SHORTNAME,            #责任经销商简称 \n");
		sql.append("	m.GROUP_NO,                    #组 \n");
		sql.append("	SUM(CVN) AS C_TOTAL_VEHICLE_NUM,   #完成车辆总数 \n");
		sql.append("	SUM(UVN) AS TOTAL_VEHICLE_NUM   #未完成车辆总数 \n");
		sql.append("	FROM ( SELECT \n");
		sql.append("		v.RECALL_ID, v.DUTY_DEALER, v.VIN, \n");
		sql.append("		CASE WHEN v.RECALL_STATUS = 40331001  THEN 1 ELSE 0 END UVN, \n");
		sql.append("		CASE WHEN v.RECALL_STATUS = 40331002  THEN 1 ELSE 0 END CVN \n");
		sql.append("		FROM TT_RECALL_VEHICLE_DCS v) v \n");
		sql.append("		LEFT JOIN TT_RECALL_SERVICE_DCS  s ON v.RECALL_ID = s.RECALL_ID \n");
		sql.append("		LEFT JOIN TM_DEALER td ON td.dealer_ID = v.DUTY_DEALER \n");
		sql.append("		LEFT JOIN TM_VEHICLE_DEC tv ON tv.VIN = v.VIN \n");
		sql.append("		INNER JOIN TT_RECALL_MATERIAL_DCS m ON m.MATERIAL_ID=tv.MATERIAL_ID  AND m.RECALL_ID = v.RECALL_ID \n");
		sql.append("		GROUP BY s.RECALL_ID,v.DUTY_DEALER,CONCAT(REPLACE(td.DEALER_CODE,'A',''),'A'),td.DEALER_SHORTNAME,m.GROUP_NO  ) CNVN \n");
		sql.append("	LEFT JOIN TT_RECALL_PART_DCS p ON  CNVN.GROUP_NO = p.GROUP_NO AND CNVN.RECALL_ID = p.RECALL_ID  \n");
		sql.append("	LEFT JOIN TT_PT_PART_BASE_DCS  pp ON pp.PART_CODE = p.PART_CODE   \n");
		sql.append("  \n");
		sql.append("  ) pn \n");
		sql.append("  LEFT JOIN TT_RECALL_SERVICE_DCS s ON s.RECALL_ID = PN.RECALL_ID \n");
		sql.append("  LEFT JOIN TM_DEALER td ON  td.DEALER_CODE = pn.DEALER_CODE \n");
		sql.append("  LEFT JOIN  (SELECT CONCAT(REPLACE(tpds1.DEALER_CODE,'A',''),'A')  DEALER_CODE,       #经销商code  \n");
		sql.append("		td.DEALER_ID,                   #经销商 \n");
		sql.append("		tpds1.PART_NO,                  #配件编号 \n");
		sql.append("		SUM(tpds1.OH_COUNT)AS PART_NUM  #配件总库存 \n");
		sql.append("		FROM TT_PT_DEALER_STOCK_DCS tpds1 \n");
		sql.append("		LEFT JOIN TM_DEALER td ON  CONCAT(REPLACE(tpds1.DEALER_CODE,'A',''),'A')  = td.DEALER_CODE  \n");
		sql.append("		GROUP BY CONCAT(REPLACE(tpds1.DEALER_CODE,'A',''),'A') ,tpds1.PART_NO,td.DEALER_ID \n");
		sql.append("	 )  PDS  ON  PDS.PART_NO = pn.PART_CODE  AND PDS.DEALER_CODE = pn.DEALER_CODE  \n");
		sql.append("  LEFT JOIN TM_DEALER_ORG_RELATION tdor ON td.DEALER_ID = tdor.DEALER_ID \n");
		sql.append("  LEFT JOIN TM_ORG tor ON tdor.ORG_ID = tor.ORG_ID \n");
		sql.append("  LEFT JOIN TM_ORG tor2 ON tor.PARENT_ORG_ID = tor2.ORG_ID \n");
		sql.append("  WHERE 1=1 \n");
		sql.append("	#-------条件----- \n");
		sql.append("	AND PN.RECALL_ID = '"+recallId+"' \n");
		sql.append("	AND Td.DEALER_CODE = CONCAT(REPLACE('"+dealerCode+"','A',''),'A') \n");
		sql.append("	AND pn.GROUP_NO='"+groupNo+"' \n");

	
		return sql.toString();	
	}
	
	
	
}































