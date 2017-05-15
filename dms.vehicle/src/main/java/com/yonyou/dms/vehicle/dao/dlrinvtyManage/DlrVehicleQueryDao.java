package com.yonyou.dms.vehicle.dao.dlrinvtyManage;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.yonyou.dms.common.Util.OemDictCodeConstantsUtils;
import com.yonyou.dms.framework.DAO.DAOUtil;
import com.yonyou.dms.framework.DAO.OemBaseDAO;
import com.yonyou.dms.framework.DAO.OemDAOUtil;
import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.framework.domain.LoginInfoDto;
import com.yonyou.dms.framework.domains.PO.baseData.OemDictPO;
import com.yonyou.dms.framework.util.bean.ApplicationContextHelper;
import com.yonyou.dms.function.common.OemDictCodeConstants;
import com.yonyou.dms.function.utils.common.CommonUtils;
import com.yonyou.dms.function.utils.common.StringUtils;

@Repository
@SuppressWarnings("rawtypes")
public class DlrVehicleQueryDao extends OemBaseDAO {
	
	private static final Logger logger = LoggerFactory.getLogger(DAOUtil.class);

	/**
	 * 查询
	 * @param queryParam
	 * @param loginInfo
	 * @return
	 */
	public PageInfoDto queryList(Map<String, String> queryParam, LoginInfoDto loginInfo) {
		List<Object> params = new ArrayList<Object>();
		String sql = getQuerySql(queryParam, params, loginInfo);
		PageInfoDto pageInfoDto = OemDAOUtil.pageQuery(sql, params);
		return pageInfoDto;
	}

	/**
	 * 查询SQL
	 * @param queryParam
	 * @param params
	 * @param loginInfo
	 * @return
	 */
	private String getQuerySql(Map<String, String> queryParam, List<Object> params, LoginInfoDto loginInfo) {
		StringBuffer sql = new StringBuffer();
		sql.append("select t10.*,t11.TC_VEHICLE_ID,t11.ARRIVE_PORT_DATE,t11.PAYMENG_DATE,t11.START_SHIPMENT_DATE,t11.CLORDER_SCANNING_NO,t11.CLORDER_SCANNING_URL,t11.SCORDER_SCANNING_NO,t11.SCORDER_SCANNING_URL, \n");
		sql.append("(case when t10.DEALER_NAME!='' then t10.DEALER_NAME when t10.BIG_AREA!='' then t10.BIG_AREA else '全国' end) RESOURCE  \n");
		sql.append("  from (select t9.*,DATE_FORMAT(TVN.CREATE_DATE,'%Y-%m-%d') NVDR_DATE\n");
		sql.append("   		  from (select t8.*,DATE_FORMAT(tvi.ARRIVE_DATE,'%Y-%m-%d') ARRIVE_DATE\n");
		sql.append(" 				  from (select t7.*,trr.REMARK,trr.OTHER_REMARK,(case when trr.IS_LOCK=1 then '是' else '否' end) IS_LOCK\n");
		sql.append(" 						  from (select IFNULL(t6.ORDER_TYPE,0) ORDER_TYPE,t6.ORG_ID,(case when t6.DEALER_ID>0 then TOR.ORG_DESC when t6.ORG_ID>0 then TOR.ORG_DESC else t6.ORG_DESC2 end) BIG_AREA,(case when t6.DEALER_ID>0 then t6.ORG_DESC3 end) SMALL_AREA,\n");
		sql.append(" 								       t6.MODEL_YEAR,t6.BRAND_CODE,t6.SERIES_CODE,t6.SERIES_NAME,t6.GROUP_CODE,t6.GROUP_NAME,t6.DEALER_CODE,t6.DEALER_NAME,t6.VIN,t6.NODE_STATUS,t6.VEHICLE_ID,t6.ALLOT,t6.NODE_DATE,t6.ORDER_STATUS,\n");
		sql.append(" 									    t6.COLOR_CODE,t6.COLOR_NAME,t6.TRIM_CODE,t6.TRIM_NAME,t6.FACTORY_OPTIONS,t6.VEHICLE_USAGE,t6.PAYMENT_TYPE,DATE_FORMAT(t6.DEAL_ORDER_AFFIRM_DATE,'%Y-%m-%d') DEAL_ORDER_AFFIRM_DATE\n");
		sql.append(" 								  from (select t4.DEALER_ID,\n");
		sql.append(" 									   		   (case when t4.ORDER_TYPE>0 then t4.ORDER_TYPE else (case when t5.TYPE=20811001 then 20831002 when t5.TYPE=20811002 then 20831001 end) end) ORDER_TYPE,\n");
		sql.append(" 									   		   (case when t4.ORG_ID2>0 then t4.ORG_ID2 when t5.RESOURCE_SCOPE>0 then t5.RESOURCE_SCOPE else t4.ORG_ID1 end) ORG_ID,\n");
		sql.append(" 									    	   t4.VEHICLE_ID,t4.VIN,t4.BRAND_CODE,t4.SERIES_CODE,t4.SERIES_NAME,t4.GROUP_CODE,t4.GROUP_NAME,t4.MODEL_YEAR,t4.COLOR_CODE,t4.COLOR_NAME,t4.TRIM_CODE,t4.TRIM_NAME,t4.FACTORY_OPTIONS,t4.NODE_STATUS,t4.LIFE_CYCLE,\n");
		sql.append(" 								        	   t4.VEHICLE_USAGE,t4.DEALER_CODE,t4.DEALER_NAME,t4.PAYMENT_TYPE,t4.DEAL_ORDER_AFFIRM_DATE,t4.ALLOT,t4.NODE_DATE,t4.ORDER_STATUS,\n");
		sql.append(" 									   		   (case when t5.RESOURCE_SCOPE>0 then t5.ORG_DESC else t4.ORG_DESC2 end) ORG_DESC2,t4.ORG_DESC3\n");
		sql.append(" 				 						  from (select t2.*,t3.ORG_ID2,t3.DEALER_CODE,t3.DEALER_NAME,t3.ORG_DESC2,t3.ORG_DESC3\n");
		sql.append(" 												  from (select (case when TVO.DEALER_ID>0 then TVO.DEALER_ID else t1.DEALER_ID2 end) DEALER_ID,TVO.ORDER_TYPE,TVO.PAYMENT_TYPE,TVO.DEAL_ORDER_AFFIRM_DATE,TVO.ORDER_STATUS,t1.* \n");
		sql.append(" 												           from (select TV.ORG_ID ORG_ID1,TV.DEALER_ID DEALER_ID2,TV.VEHICLE_ID, TV.VIN,VM.BRAND_CODE,VM.SERIES_CODE,VM.SERIES_NAME,VM.GROUP_CODE,VM.GROUP_NAME,VM.MODEL_YEAR,\n");
		sql.append(" 																        VM.COLOR_CODE,VM.COLOR_NAME,VM.TRIM_CODE,VM.TRIM_NAME,VM.FACTORY_OPTIONS,TV.NODE_STATUS,TV.LIFE_CYCLE,TV.VEHICLE_USAGE,\n");
		sql.append(" 														   VM.BRAND_ID,VM.SERIES_ID,VM.GROUP_ID,\n");
		sql.append(" 																	    (case when TV.NODE_STATUS in(11511009,11511010,11511012,11511015,11511016,11511019)  then 1 else 2 end) ALLOT,\n");
		sql.append(" 																		 DATE_FORMAT(TV.NODE_DATE,'%Y-%m-%d %H:%i:%s') NODE_DATE\n");
		sql.append(" 																	from TM_VEHICLE_DEC         TV,\n");
		sql.append(" 																		 (" + getVwMaterialSql() + ")        VM\n");
		sql.append("                                                                    where TV.MATERIAL_ID = VM.MATERIAL_ID \n");
		sql.append("                                                                      and vm.group_type = "+OemDictCodeConstants.GROUP_TYPE_IMPORT+") t1\n");
		sql.append(" 														   left join TT_VS_ORDER TVO on t1.VIN=TVO.VIN\n");
		sql.append(" 														   where 1=1 and TVO.ORDER_STATUS<20071007\n");
		// 品牌
		if (!StringUtils.isNullOrEmpty(queryParam.get("brandCode"))) {
			sql.append(" and T1.BRAND_ID  = ? \n");
			params.add(queryParam.get("brandCode"));
		}
		// 车系
		if (!StringUtils.isNullOrEmpty(queryParam.get("seriesName"))) {
			sql.append(" and T1.SERIES_ID  = ? \n");
			params.add(queryParam.get("seriesName"));
		}
		// 车款
		if (!StringUtils.isNullOrEmpty(queryParam.get("groupName"))) {
			sql.append(" and T1.GROUP_ID  = ? \n");
			params.add(queryParam.get("groupName"));
		}
		// 年款
		if (!StringUtils.isNullOrEmpty(queryParam.get("modelYear"))) {
			sql.append(" and T1.MODEL_YEAR  = '" + queryParam.get("modelYear") + "' \n");
		}
		// 颜色
		if (!StringUtils.isNullOrEmpty(queryParam.get("colorName"))) {
			sql.append(" and T1.COLOR_CODE  = '" + queryParam.get("colorName") + "' \n");
		}
		// 内饰
		if (!StringUtils.isNullOrEmpty(queryParam.get("trimName"))) {
			sql.append(" and T1.TRIM_CODE  = '" + queryParam.get("trimName") + "' \n");
		}
		//车辆节点
		String nodeStatus2 = queryParam.get("nodeStatus2");
		String nodeStatus1 = queryParam.get("nodeStatus1");
		if(StringUtils.isNullOrEmpty(nodeStatus2)){
			if(!StringUtils.isNullOrEmpty(nodeStatus1)){
				sql.append("     													and t1.NODE_STATUS in("+queryParam.get("nodeStatus1")+")\n");
			}
		}else{
			OemDictPO tc1 = OemDictPO.findById(nodeStatus1);
			OemDictPO tc2 = OemDictPO.findById(nodeStatus2);
			String sl = "select CODE_ID from TC_CODE where TYPE="+OemDictCodeConstants.VEHICLE_NODE+" and num>="+tc1.getInteger("NUM")+" and num<="+tc2.getInteger("NUM");
			List<Map> l = OemDAOUtil.findAll(sl, null);
			String codeIds = "";
			if(l.size()>0){
				for(int i=0;i<l.size();i++){
					codeIds+=l.get(i).get("CODE_ID").toString();
					if(i<l.size()-1) codeIds+=",";
				}
			}
			if(!codeIds.equals("")){
				sql.append("													    and t1.NODE_STATUS in("+codeIds+")\n");
			}			
		}
		//节点时间
		if (!StringUtils.isNullOrEmpty(queryParam.get("nodeDate1"))){
			String nodeDate1 = queryParam.get("nodeDate1");
			String nodeDate2 = queryParam.get("nodeDate2");
			sql.append("and T1.NODE_DATE>=" + "date_format(" + nodeDate1 + ",'%Y-%m-%d')" + "\n");
			sql.append("and T1.NODE_DATE<=" + "date_format(" + nodeDate2 + ",'%Y-%m-%d')" + "\n");
		}
		//订单状态
		if(!StringUtils.isNullOrEmpty(queryParam.get("orderStatus"))){
			sql.append(" and TVO.ORDER_STATUS=?");
			params.add(queryParam.get("orderStatus"));
		}
		//确认状态
		if (!StringUtils.isNullOrEmpty(queryParam.get("storeType"))) {
			String storeType1 = CommonUtils.checkNull(queryParam.get("storeType"));
			String storeType = OemDictCodeConstantsUtils.getConfirmStatus(Integer.valueOf(storeType1));
			sql.append("and T1.ALLOT=" + storeType + "\n");
		}
		//VIN
		if(!StringUtils.isNullOrEmpty(queryParam.get("vin"))){
			String vin = queryParam.get("vin");
			vin = vin.replace("，", ",");
			vin = vin.replaceAll("\\^", "\n");
			vin = vin.replaceAll("\\,", "\n");
			sql.append("      														"+getVinsAuto(vin, "T1"));
		}
		
		//工厂选装
		if(!StringUtils.isNullOrEmpty(queryParam.get("factoryOption"))){
			sql.append("     														and t1.FACTORY_OPTIONS like '%"+queryParam.get("factoryOption").toUpperCase()+"%'\n");
		}
		
		sql.append(") t2\n");
		sql.append(" 												  left join (select TOR2.ORG_ID ORG_ID2,TD.DEALER_ID,TD.DEALER_CODE,TD.DEALER_SHORTNAME DEALER_NAME,TOR2.ORG_DESC ORG_DESC2,TOR3.ORG_DESC ORG_DESC3\n");
		sql.append(" 															   from TM_ORG                     TOR2,\n");
		sql.append(" 																	TM_ORG                     TOR3,\n");
		sql.append(" 																    TM_DEALER_ORG_RELATION     TDOR,\n");
		sql.append(" 																    TM_DEALER                  TD\n");
		sql.append(" 															   where TOR3.PARENT_ORG_ID = TOR2.ORG_ID\n");
		sql.append(" 															     and TOR3.ORG_ID = TDOR.ORG_ID\n");
		sql.append(" 															     and TDOR.DEALER_ID = TD.DEALER_ID\n");
		sql.append(" 																 and TOR3.ORG_LEVEL = 3\n");
		sql.append(" 																 and TOR2.ORG_LEVEL = 2) t3\n");
		sql.append(" 												  on t2.DEALER_ID = t3.DEALER_ID\n");
		sql.append("												  where t3.DEALER_ID="+loginInfo.getDealerId()+") t4\n");
		sql.append(" 								  left join (select TVCR.VEHICLE_ID,TVCR.COMMON_ID,TVCR.RESOURCE_SCOPE,TVCR.TYPE,(case when TVCR.RESOURCE_SCOPE="+OemDictCodeConstants.OEM_ACTIVITIES+" then '' else TOR.ORG_DESC end) ORG_DESC\n");
		sql.append(" 											   from TT_VS_COMMON_RESOURCE            TVCR,\n");
		sql.append(" 												    TT_VS_COMMON_RESOURCE_DETAIL     TVCRD,\n");
		sql.append(" 											        TM_ORG                           TOR\n");
		sql.append(" 											   where TVCR.COMMON_ID=TVCRD.COMMON_ID\n");
		sql.append(" 												 and TVCR.RESOURCE_SCOPE=TOR.ORG_ID\n");
		sql.append(" 											     and TVCRD.STATUS="+OemDictCodeConstants.STATUS_ENABLE+"\n");
		sql.append(" 												 and TVCR.STATUS="+OemDictCodeConstants.COMMON_RESOURCE_STATUS_02+") t5\n");
		sql.append(" 								  on t4.VEHICLE_ID=t5.VEHICLE_ID) t6\n");
		sql.append(" 						  left join TM_ORG  TOR on t6.ORG_ID = TOR.ORG_ID and TOR.ORG_LEVEL=2) t7\n");
		sql.append(" 			       left join TT_RESOURCE_REMARK TRR on t7.VIN=TRR.VIN) t8\n");
		sql.append(" 		    left join (select * from TT_VS_INSPECTION TV where TV.CREATE_DATE in(select max(TI.CREATE_DATE) from TT_VS_INSPECTION TI where TI.VEHICLE_ID=TV.VEHICLE_ID)) TVI on t8.VEHICLE_ID=TVI.VEHICLE_ID) t9\n");
		sql.append(" 	   left join TT_VS_NVDR TVN on t9.VIN=TVN.VIN) t10\n");
		sql.append("  left join (select TCV.TC_VEHICLE_ID,TCV.VEHICLE_ID,DATE_FORMAT(TCV.ARRIVE_PORT_DATE,'%Y-%m-%d') ARRIVE_PORT_DATE,DATE_FORMAT(TCV.PAYMENG_DATE,'%Y-%m-%d') PAYMENG_DATE,DATE_FORMAT(TCV.START_SHIPMENT_DATE,'%Y-%m-%d') START_SHIPMENT_DATE,\n");
		sql.append("				    TCV.CLORDER_SCANNING_NO,concat(TCV.CLORDER_SCANNING_URL,'') CLORDER_SCANNING_URL,TCV.SCORDER_SCANNING_NO,concat(TCV.SCORDER_SCANNING_URL,'') SCORDER_SCANNING_URL\n");
		sql.append("			   from TM_CTCAI_VEHICLE    TCV) t11\n");
		sql.append("  on t10.VEHICLE_ID=t11.VEHICLE_ID \n");
		sql.append("  where 1=1\n");	
		//sql.append("  and not exists(select 1 from TM_CACT_ALLOT where VIN=t10.VIN and STATUS="+Constant.STATUS_ENABLE+")\n");
		//订单类型
		if(!StringUtils.isNullOrEmpty(queryParam.get("orderType"))){
			String s = "";
			if(queryParam.get("orderType").indexOf(",")>0){
				s="0,";
			}
			sql.append("      and t10.ORDER_TYPE in("+s+""+queryParam.get("orderType")+")\n");
		}
		
		logger.debug("车辆详细查询SQL： " + sql.toString()  + " " + params.toString());
		return sql.toString();
	}

	/**
	 * 详细查询
	 * @param vin
	 * @return
	 */
	public List<Map> detailQuery(String vin) {
		// 车辆属性
		List<Object> params = new ArrayList<Object>();
		StringBuffer sql1 = new StringBuffer();
		sql1.append(
				"select TV.VEHICLE_ID, TV.VIN,TV.ENGINE_NO,TV.REMARK,VM.BRAND_NAME,VM.SERIES_NAME,VM.MODEL_NAME,VM.COLOR_NAME,VM.TRIM_NAME\n");
		sql1.append("    from TM_VEHICLE_DEC      TV,\n");
		sql1.append("   (" + getVwMaterialSql() + ")      VM\n");
		sql1.append("    where TV.MATERIAL_ID = VM.MATERIAL_ID\n");
		sql1.append("      AND TV.VIN=?\n");
		params.add(vin);
		List<Map> list = OemDAOUtil.findAll(sql1.toString(), params);
		return list;
	}

	/**
	 * 变更日志查询
	 * @param vin
	 * @return
	 */
	public PageInfoDto oemVehicleDetailed(String vin) {
		// 车辆变更日志
		List<Object> params = new ArrayList<Object>();
		StringBuffer sql = new StringBuffer();
		sql.append("    SELECT * FROM (\n");
		sql.append("select distinct TVVC.CHANGE_CODE,DATE_FORMAT(TVVC.CHANGE_DATE ,'%Y-%m-%d') CHANGE_DATE,TVVC.CHANGE_DESC,TD.DEALER_SHORTNAME DEALER_NAME\n");
		sql.append("     from TM_VEHICLE_DEC    TV,\n");
		sql.append("          TT_VS_VHCL_CHNG      TVVC,\n");	
		sql.append("     	   TM_DEALER            TD\n");
		sql.append("       where TVVC.VEHICLE_ID = TV.VEHICLE_ID\n");
		sql.append("         AND TV.DEALER_ID = TD.DEALER_ID\n");
		// 经销商端的详细车籍查询的车辆变更类型只有三种：发运出库 验收入库 实销
		sql.append("         AND TVVC.CHANGE_CODE in(" + OemDictCodeConstants.VEHICLE_CHANGE_TYPE_07 + ","
				+ OemDictCodeConstants.VEHICLE_CHANGE_TYPE_09 + "," + OemDictCodeConstants.VEHICLE_CHANGE_TYPE_10
				+ ")\n");
		sql.append("         AND TV.VIN=? \n");
		sql.append(" ) A\n");
		params.add(vin);
		PageInfoDto pageInfoDto = OemDAOUtil.pageQuery(sql.toString(), params);
		return pageInfoDto;
	}

	/**
	 * 下载查询
	 * @param queryParam
	 * @return
	 */
	public List<Map> download(Map<String, String> queryParam) {
		List<Object> params = new ArrayList<Object>();
		String sql = getDownloadQuerySql(queryParam, params);
		List<Map> download = OemDAOUtil.downloadPageQuery(sql, null);
		return download;
	}

	private String getDownloadQuerySql(Map<String, String> queryParam, List<Object> params) {
		LoginInfoDto loginInfo = ApplicationContextHelper.getBeanByType(LoginInfoDto.class);
		
		StringBuffer sql = new StringBuffer();
		sql.append("select * from (select t12.*,TCFI.BALANCE,TCFI.CTCAI_ACCEPTANCES_BALANCE,TCFI.DEALER_ACCEPTANCES_BALANCE,TCFI.CTCAI_ACCEPTANCES_DISCOUNT_BALANCE,TCFI.DEALER_ACCEPTANCES_DISCOUNT_BALANCE,TCFI.REBATES_BALANCE\n");
		sql.append("  from (select t10.*,t11.*,(case when t10.DEALER_NAME!='' then t10.DEALER_NAME when t10.BIG_AREA!='' then t10.BIG_AREA else '全国' end) RESOURCE\n");
		sql.append("  		  from (select t9.*,DATE_FORMAT(TVN.CREATE_DATE,'%Y-%m-%d') NVDR_DATE\n");
		sql.append("   		  		  from (select t8.*,DATE_FORMAT(tvi.ARRIVE_DATE,'%Y-%m-%d') ARRIVE_DATE2\n");
		sql.append(" 				  		  from (select t7.*,(case when t7.COMMON_DATE!='' then t7.COMMON_DATE when t7.COMMON_DATE='' then t7.APPOINT_DATE end) COMMON_APPOINT_DATE,trr.REMARK,trr.OTHER_REMARK,(case when trr.IS_LOCK=1 then '是' else '否' end) IS_LOCK\n");
		sql.append(" 						  		  from (select IFNULL(t6.ORDER_TYPE,0) ORDER_TYPE,t6.ORG_ID,(case when t6.DEALER_ID>0 then TOR.ORG_DESC when t6.ORG_ID>0 then TOR.ORG_DESC else t6.ORG_DESC2 end) BIG_AREA,(case when t6.DEALER_ID>0 then t6.ORG_DESC3 end) SMALL_AREA,t6.MODEL_YEAR,t6.BRAND_CODE,\n");
		sql.append(" 								       		   t6.SERIES_CODE,t6.SERIES_NAME,t6.GROUP_CODE,t6.GROUP_NAME,t6.DEALER_CODE,t6.DEALER_NAME,t6.VIN,t6.NODE_STATUS,t6.VEHICLE_ID,t6.ALLOT,t6.NODE_DATE,t6.WHOLESALE_PRICE,t6.RETAIL_PRICE,t6.COLOR_CODE,t6.COLOR_NAME,t6.TRIM_CODE,t6.TRIM_NAME,t6.FACTORY_OPTIONS,\n");
		sql.append(" 									   		   t6.VEHICLE_USAGE,t6.PAYMENT_TYPE,t6.CTCAI_CODE,DATE_FORMAT(t6.DEAL_ORDER_AFFIRM_DATE,'%Y-%m-%d') DEAL_ORDER_AFFIRM_DATE,t6.ORDER_STATUS,\n");
		sql.append("											   (case when t6.ORDER_TYPE in(20831001,20831002) then DATE_FORMAT(t6.COMMON_DATE,'%Y-%m-%d') else '' end) COMMON_DATE,(case when t6.ORDER_TYPE=20831003 then DATE_FORMAT(t6.APPOINT_DATE,'%Y-%m-%d') else '' end) APPOINT_DATE\n");
		sql.append(" 								  		  from (select t4.DEALER_ID,t4.CTCAI_CODE,\n");
		sql.append(" 									   		   		   (case when t4.ORDER_TYPE>0 then t4.ORDER_TYPE else (case when t5.TYPE=20811001 then 20831002 when t5.TYPE=20811002 then 20831001 end) end) ORDER_TYPE,\n");
		sql.append(" 									   		   		   (case when t4.ORG_ID2>0 then t4.ORG_ID2 when t5.RESOURCE_SCOPE>0 then t5.RESOURCE_SCOPE else t4.ORG_ID1 end) ORG_ID,\n");
		sql.append(" 									    	   		   t4.VEHICLE_ID,t4.VIN,t4.BRAND_CODE,t4.SERIES_CODE,t4.SERIES_NAME,t4.GROUP_CODE,t4.GROUP_NAME,t4.MODEL_YEAR,t4.COLOR_CODE,t4.COLOR_NAME,t4.TRIM_CODE,t4.TRIM_NAME,t4.FACTORY_OPTIONS,t4.NODE_STATUS,t4.LIFE_CYCLE,\n");
		sql.append(" 								        	   		   t4.VEHICLE_USAGE,t4.DEALER_CODE,t4.DEALER_NAME,t4.PAYMENT_TYPE,t4.DEAL_ORDER_AFFIRM_DATE,t4.ALLOT,t4.NODE_DATE,t4.APPOINT_DATE,t4.WHOLESALE_PRICE,t4.RETAIL_PRICE,t4.ORDER_STATUS,\n");
		sql.append(" 									   		   		   (case when t5.RESOURCE_SCOPE>0 then t5.ORG_DESC else t4.ORG_DESC2 end) ORG_DESC2,t4.ORG_DESC3,t5.COMMON_DATE\n");
		sql.append(" 				 						  	   	   from (select t2.*,t3.ORG_ID2,t3.DEALER_CODE,t3.DEALER_NAME,t3.ORG_DESC2,t3.ORG_DESC3,t3.CTCAI_CODE\n");
		sql.append(" 												  		   from (select (case when TVO.DEALER_ID>0 then TVO.DEALER_ID else t1.DEALER_ID2 end) DEALER_ID,TVO.ORDER_TYPE,TVO.PAYMENT_TYPE,TVO.DEAL_ORDER_AFFIRM_DATE,TVO.CREATE_DATE APPOINT_DATE,TVO.ORDER_STATUS,t1.* \n");
		sql.append(" 												           		   from (select TV.ORG_ID ORG_ID1,TV.DEALER_ID DEALER_ID2,TV.VEHICLE_ID, TV.VIN,VM.BRAND_CODE,VM.SERIES_CODE,VM.SERIES_NAME,VM.GROUP_CODE,VM.GROUP_NAME,VM.MODEL_YEAR,\n");
		sql.append(" 																        		VM.COLOR_CODE,VM.COLOR_NAME,VM.TRIM_CODE,VM.TRIM_NAME,VM.FACTORY_OPTIONS,TV.NODE_STATUS,TV.LIFE_CYCLE,TV.VEHICLE_USAGE,\n");
		sql.append(" 																	    	    (case when TV.NODE_STATUS in(11511009,11511010,11511012,11511015,11511016,11511018,11511019)  then 1 else 2 end) ALLOT,\n");
		sql.append(" 																			    DATE_FORMAT(TV.NODE_DATE,'%Y-%m-%d HH24:MI:SS') NODE_DATE,TV.WHOLESALE_PRICE,TV.RETAIL_PRICE\n");
		sql.append(" 																		   from TM_VEHICLE_DEC         TV,\n");
		sql.append(" 																		 	    (" + getVwMaterialSql() + ")        VM\n");
		sql.append("                                                                    	   where TV.MATERIAL_ID = VM.MATERIAL_ID   \n");
		//add by lsy 2015-7-21 
		sql.append(" 																				AND  VM.GROUP_TYPE="+OemDictCodeConstants.GROUP_TYPE_IMPORT+"\n");
		//end
		sql.append("     ) t1\n");
		sql.append(" 														   	        left join TT_VS_ORDER TVO on t1.VIN=TVO.VIN\n");
		sql.append(" 														   		    where 1=1 and TVO.ORDER_STATUS<20071007\n");
		// 品牌
		if (!StringUtils.isNullOrEmpty(queryParam.get("brandCode"))) {
			sql.append(" and VM.BRAND_ID  = ? \n");
			params.add(queryParam.get("brandCode"));
		}
		// 车系
		if (!StringUtils.isNullOrEmpty(queryParam.get("seriesName"))) {
			sql.append(" and VM.SERIES_ID  = ? \n");
			params.add(queryParam.get("seriesName"));
		}
		// 车款
		if (!StringUtils.isNullOrEmpty(queryParam.get("groupName"))) {
			sql.append(" and VM.GROUP_ID  = ? \n");
			params.add(queryParam.get("groupName"));
		}
		// 年款
		if (!StringUtils.isNullOrEmpty(queryParam.get("modelYear"))) {
			sql.append(" and VM.MODEL_YEAR  = '" + queryParam.get("modelYear") + "' \n");
		}
		// 颜色
		if (!StringUtils.isNullOrEmpty(queryParam.get("colorName"))) {
			sql.append(" and VM.COLOR_CODE  = '" + queryParam.get("colorName") + "' \n");
		}
		// 内饰
		if (!StringUtils.isNullOrEmpty(queryParam.get("trimName"))) {
			sql.append(" and VM.TRIM_CODE  = '" + queryParam.get("trimName") + "' \n");
		}
		//车辆节点
		String nodeStatus2 = queryParam.get("nodeStatus2");
		String nodeStatus1 = queryParam.get("nodeStatus1");
		if(StringUtils.isNullOrEmpty(nodeStatus2)){
			if(!StringUtils.isNullOrEmpty(nodeStatus1)){
				sql.append("     													and t1.NODE_STATUS in("+queryParam.get("nodeStatus1")+")\n");
			}
		}else{
			OemDictPO tc1 = OemDictPO.findById(nodeStatus1);
			OemDictPO tc2 = OemDictPO.findById(nodeStatus2);
			String sl = "select CODE_ID from TC_CODE where TYPE="+OemDictCodeConstants.VEHICLE_NODE+" and num>="+tc1.getInteger("NUM")+" and num<="+tc2.getInteger("NUM");
			List<Map> l = OemDAOUtil.findAll(sl, null);
			String codeIds = "";
			if(l.size()>0){
				for(int i=0;i<l.size();i++){
					codeIds+=l.get(i).get("CODE_ID").toString();
					if(i<l.size()-1) codeIds+=",";
				}
			}
			if(!codeIds.equals("")){
				sql.append("													    and t1.NODE_STATUS in("+codeIds+")\n");
			}			
		}
		//节点时间
		if (!StringUtils.isNullOrEmpty(queryParam.get("nodeDate1"))){
			String nodeDate1 = queryParam.get("nodeDate1");
			String nodeDate2 = queryParam.get("nodeDate2");
			sql.append("and T1.NODE_DATE>=" + "date_format(" + nodeDate1 + ",'%Y-%m-%d')" + "\n");
			sql.append("and T1.NODE_DATE<=" + "date_format(" + nodeDate2 + ",'%Y-%m-%d')" + "\n");
		}
		//订单状态
		if(!StringUtils.isNullOrEmpty(queryParam.get("orderStatus"))){
			sql.append(" and TVO.ORDER_STATUS=?");
			params.add(queryParam.get("orderStatus"));
		}
		//确认状态
		if (!StringUtils.isNullOrEmpty(queryParam.get("storeType"))) {
			String storeType1 = CommonUtils.checkNull(queryParam.get("storeType"));
			String storeType = OemDictCodeConstantsUtils.getConfirmStatus(Integer.valueOf(storeType1));
			sql.append("and T1.ALLOT=" + storeType + "\n");
		}
		//VIN
		if(!StringUtils.isNullOrEmpty(queryParam.get("vin"))){
			String vin = queryParam.get("vin");
			vin = vin.replaceAll("\\^", "\n");
			vin = vin.replaceAll("\\,", "\n");
			sql.append("      														"+getVins(vin, "T1"));
		}
		//工厂选装
		if(!StringUtils.isNullOrEmpty(queryParam.get("factoryOption"))){
			sql.append("     														and t1.FACTORY_OPTIONS like '%"+queryParam.get("factoryOption").toUpperCase()+"%'\n");
		}
		sql.append(") t2\n");
		sql.append(" 												  	    left join (select TOR2.ORG_ID ORG_ID2,TC.CTCAI_CODE,TD.DEALER_ID,TD.DEALER_CODE,TD.DEALER_SHORTNAME DEALER_NAME,TOR2.ORG_DESC ORG_DESC2,TOR3.ORG_DESC ORG_DESC3\n");
		sql.append(" 															   	     from TM_ORG                     TOR2,\n");
		sql.append(" 																		  TM_ORG                     TOR3,\n");
		sql.append(" 																    	  TM_DEALER_ORG_RELATION     TDOR,\n");
		sql.append(" 																    	  TM_DEALER                  TD,\n");
		sql.append("																		  TM_COMPANY                 TC\n");
		sql.append(" 															   	     where TOR3.PARENT_ORG_ID = TOR2.ORG_ID\n");
		sql.append(" 															     	   and TOR3.ORG_ID = TDOR.ORG_ID\n");
		sql.append(" 															     	   and TDOR.DEALER_ID = TD.DEALER_ID\n");
		sql.append("																	   and TD.COMPANY_ID = TC.COMPANY_ID\n");
		sql.append(" 																 	   and TOR3.ORG_LEVEL = 3\n");
		sql.append(" 																 	   and TOR2.ORG_LEVEL = 2) t3\n");
		sql.append(" 												  	 	on t2.DEALER_ID = t3.DEALER_ID\n");
		sql.append("												  		where t3.DEALER_ID="+loginInfo.getDealerId()+") t4\n");
		sql.append(" 								  		  left join (select TVCR.VEHICLE_ID,TVCR.COMMON_ID,TVCR.CREATE_DATE COMMON_DATE,TVCR.RESOURCE_SCOPE,TVCR.TYPE,(case when TVCR.RESOURCE_SCOPE="+OemDictCodeConstants.OEM_ACTIVITIES+" then '' else TOR.ORG_DESC end) ORG_DESC\n");
		sql.append(" 											   		   from TT_VS_COMMON_RESOURCE            TVCR,\n");
		sql.append(" 												    		TT_VS_COMMON_RESOURCE_DETAIL     TVCRD,\n");
		sql.append(" 											        		TM_ORG                           TOR\n");
		sql.append(" 											   		   where TVCR.COMMON_ID=TVCRD.COMMON_ID\n");
		sql.append(" 												 		 and TVCR.RESOURCE_SCOPE=TOR.ORG_ID\n");
		sql.append(" 											     		 and TVCRD.STATUS="+OemDictCodeConstants.STATUS_ENABLE+"\n");
		sql.append(" 												 		 and TVCR.STATUS="+OemDictCodeConstants.COMMON_RESOURCE_STATUS_02+") t5\n");
		sql.append(" 								  	       on t4.VEHICLE_ID=t5.VEHICLE_ID) t6\n");
		sql.append(" 						  	        left join TM_ORG  TOR on t6.ORG_ID = TOR.ORG_ID and TOR.ORG_LEVEL=2) t7\n");
		sql.append(" 			       			left join TT_RESOURCE_REMARK TRR on t7.VIN=TRR.VIN) t8\n");
		sql.append(" 		    		left join (select * from TT_VS_INSPECTION TV where TV.CREATE_DATE in(select max(TI.CREATE_DATE) from TT_VS_INSPECTION TI where TI.VEHICLE_ID=TV.VEHICLE_ID)) TVI on t8.VEHICLE_ID=TVI.VEHICLE_ID) t9\n");
		sql.append(" 	   		 left join TT_VS_NVDR TVN on t9.VIN=TVN.VIN) t10\n");
		sql.append(" 	   left join (select TCV.VEHICLE_ID VEHICLE_ID1,TCV.CLORDER_SCANNING_NO,TCV.CLORDER_SCANNING_URL,TCV.SCORDER_SCANNING_NO,TCV.SCORDER_SCANNING_URL,\n");
		sql.append("				        DATE_FORMAT(TCV.ARRIVE_PORT_DATE,'%Y-%m-%d') ARRIVE_PORT_DATE,DATE_FORMAT(TCV.PAYMENG_DATE,'%Y-%m-%d') PAYMENG_DATE,DATE_FORMAT(TCV.START_SHIPMENT_DATE,'%Y-%m-%d') START_SHIPMENT_DATE,\n");
		sql.append("				        DATE_FORMAT(TCV.UNORDER_EVIDENCE_DATE,'%Y-%m-%d') UNORDER_EVIDENCE_DATE,DATE_FORMAT(TCV.MACHECK_EVIDENCE_DATE,'%Y-%m-%d') MACHECK_EVIDENCE_DATE,DATE_FORMAT(TCV.PLANCE_ORDER_DATE,'%Y-%m-%d') PLANCE_ORDER_DATE,\n");
		sql.append("				        DATE_FORMAT(TCV.ARRIVE_DATE,'%Y-%m-%d') ARRIVE_DATE,DATE_FORMAT(TCV.SINGLE_POST_DATE,'%Y-%m-%d') SINGLE_POST_DATE,DATE_FORMAT(TCV.STOCKOUT_DATE,'%Y-%m-%d') STOCKOUT_DATE,\n");	
		sql.append("				        TCV.CG_ORDER_NO,TCV.INVOICE_NO,TCV.ONTHEWAY_POSITION,TCV.SINGLE_POST_EMS,TCV.STANDART_CAR_PRICE,TCV.USE_REBATE,TCV.FINAL_CAR_REBATE\n");
		sql.append("			        from  TM_CTCAI_VEHICLE    TCV) t11\n");
		sql.append("  	   on t10.VEHICLE_ID=t11.VEHICLE_ID1) t12\n");
		sql.append("  left join (select * from TI_CTCAI_FUND_INFO t where t.CREATE_DATE in (select max(tc.CREATE_DATE) from TI_CTCAI_FUND_INFO tc where tc.DEALERCODE=t.DEALERCODE))  TCFI on t12.DEALER_CODE=TCFI.DEALERCODE\n");
		sql.append("  where 1=1\n");	
		sql.append("    and t12.DEALER_CODE in ('"+loginInfo.getDealerCode()+"')\n");
		//订单类型
		if(!StringUtils.isNullOrEmpty(queryParam.get("orderType"))){
			String s = "";
			if(queryParam.get("orderType").indexOf(",")>0){
				s="0,";
			}
			sql.append("      and t10.ORDER_TYPE in("+s+""+queryParam.get("orderType")+")\n");
		}
		sql.append("   order by t12.DEALER_CODE,t12.NODE_DATE desc \n");
		sql.append(" ) aa where 1=1");
		//付款方式
		if(!StringUtils.isNullOrEmpty(queryParam.get("paymentType"))){
			sql.append(" and aa.PAYMENT_TYPE IN ("+queryParam.get("paymentType")+")");
		}
		
		return sql.toString();
	}

}
