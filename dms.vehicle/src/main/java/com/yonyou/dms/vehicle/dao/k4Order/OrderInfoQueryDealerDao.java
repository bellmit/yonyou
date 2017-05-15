package com.yonyou.dms.vehicle.dao.k4Order;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.yonyou.dms.framework.DAO.OemBaseDAO;
import com.yonyou.dms.framework.DAO.OemDAOUtil;
import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.function.utils.common.StringUtils;

/**
 * @author liujiming
 * @date 2017年3月8日
 */
@Repository
public class OrderInfoQueryDealerDao extends OemBaseDAO {

	/**
	 * 整车订单查询(经销商)
	 * 
	 * @param queryParam
	 * @return pageInfoDto
	 */
	public PageInfoDto getOrderInfoQueryDealerList(Map<String, String> queryParam) {
		List<Object> params = new ArrayList<Object>();
		String sql = getDealerQuerySql(queryParam, params);
		PageInfoDto pageInfoDto = OemDAOUtil.pageQuery(sql, params);
		return pageInfoDto;

	}

	/**
	 * 整车订单下载(经销商)
	 * 
	 * @param queryParam
	 * @return pageInfoDto
	 */
	public List<Map> getOrderInfoDealerDownloadList(Map<String, String> queryParam) {
		List<Object> params = new ArrayList<Object>();
		String sql = getDealerDownloadSql(queryParam, params);
		List<Map> orderList = OemDAOUtil.downloadPageQuery(sql, params);
		return orderList;
	}

	/**
	 * SQL组装 整车订单查询(经销商)
	 * 
	 * @param queryParam
	 * @param params
	 * @return
	 */
	private String getDealerQuerySql(Map<String, String> queryParam, List<Object> params) {
		StringBuilder sql = new StringBuilder();

		sql.append(
				"SELECT t10.*,t11.*,(CASE WHEN t10.DEALER_NAME!='' THEN t10.DEALER_NAME WHEN t10.BIG_AREA!='' THEN t10.BIG_AREA ELSE '全国' END) RESOURCE \n");
		sql.append("  FROM (SELECT t9.*,DATE_FORMAT(TVN.CREATE_DATE,'%Y-%c-%d') NVDR_DATE \n");
		sql.append("   		  FROM (SELECT t8.*,DATE_FORMAT(tvi.ARRIVE_DATE,'%Y-%m-%d %H:%i:%s') ARRIVE_DATE \n");
		sql.append(
				" 				  FROM (SELECT t7.*,trr.REMARK,trr.OTHER_REMARK,(CASE WHEN trr.IS_LOCK=1 THEN '是' ELSE '否' END) IS_LOCK \n");
		sql.append(
				" 						  FROM (SELECT IFNULL(t6.ORDER_TYPE,0) ORDER_TYPE,t6.ORG_ID,(CASE WHEN t6.DEALER_ID>0 THEN TOR.ORG_DESC WHEN t6.ORG_ID>0 THEN TOR.ORG_DESC ELSE t6.ORG_DESC2 END) BIG_AREA,(CASE WHEN t6.DEALER_ID>0 THEN t6.ORG_DESC3 END) SMALL_AREA, \n");
		sql.append(
				" 								       t6.MODEL_YEAR,t6.BRAND_CODE,t6.SERIES_CODE,t6.SERIES_NAME,t6.GROUP_CODE,t6.GROUP_NAME,t6.DEALER_CODE,t6.DEALER_NAME,t6.VIN,t6.NODE_STATUS,t6.VEHICLE_ID,t6.ALLOT,t6.NODE_DATE,t6.ORDER_STATUS, \n");
		sql.append(
				" 									    t6.COLOR_CODE,t6.COLOR_NAME,t6.TRIM_CODE,t6.TRIM_NAME,t6.FACTORY_OPTIONS,t6.VEHICLE_USAGE,t6.PAYMENT_TYPE,DATE_FORMAT(t6.DEAL_ORDER_AFFIRM_DATE,'%Y-%m-%d %H:%i:%s') DEAL_ORDER_AFFIRM_DATE \n");
		sql.append(" 								  FROM (SELECT t4.DEALER_ID, \n");
		sql.append(
				" 									   		   (CASE WHEN t4.ORDER_TYPE>0 THEN t4.ORDER_TYPE ELSE (CASE WHEN t5.TYPE=20811001 THEN 20831002 WHEN t5.TYPE=20811002 THEN 20831001 END) END) ORDER_TYPE, \n");
		sql.append(
				" 									   		   (CASE WHEN t4.ORG_ID2>0 THEN t4.ORG_ID2 WHEN t5.RESOURCE_SCOPE>0 THEN t5.RESOURCE_SCOPE ELSE t4.ORG_ID1 END) ORG_ID, \n");
		sql.append(
				" 									    	   t4.VEHICLE_ID,t4.VIN,t4.BRAND_CODE,t4.SERIES_CODE,t4.SERIES_NAME,t4.GROUP_CODE,t4.GROUP_NAME,t4.MODEL_YEAR,t4.COLOR_CODE,t4.COLOR_NAME,t4.TRIM_CODE,t4.TRIM_NAME,t4.FACTORY_OPTIONS,t4.NODE_STATUS,t4.LIFE_CYCLE, \n");
		sql.append(
				" 								        	   t4.VEHICLE_USAGE,t4.DEALER_CODE,t4.DEALER_NAME,t4.PAYMENT_TYPE,t4.DEAL_ORDER_AFFIRM_DATE,t4.ALLOT,t4.NODE_DATE,t4.ORDER_STATUS, \n");
		sql.append(
				" 									   		   (CASE WHEN t5.RESOURCE_SCOPE>0 THEN t5.ORG_DESC ELSE t4.ORG_DESC2 END) ORG_DESC2,t4.ORG_DESC3 \n");
		sql.append(
				" 				 						  FROM (SELECT t2.*,t3.ORG_ID2,t3.DEALER_CODE,t3.DEALER_NAME,t3.ORG_DESC2,t3.ORG_DESC3 \n");
		sql.append(
				" FROM (SELECT (CASE WHEN TVO.DEALER_ID>0 THEN TVO.DEALER_ID ELSE t1.DEALER_ID2 END) DEALER_ID,TVO.ORDER_TYPE,TVO.PAYMENT_TYPE,TVO.DEAL_ORDER_AFFIRM_DATE,TVO.ORDER_STATUS,t1.* \n");
		sql.append(
				" 												           FROM (SELECT TV.ORG_ID ORG_ID1,TV.DEALER_ID DEALER_ID2,TV.VEHICLE_ID, TV.VIN,VM.BRAND_CODE,VM.SERIES_CODE,VM.SERIES_NAME,VM.GROUP_CODE,VM.GROUP_NAME,VM.MODEL_YEAR, \n");
		sql.append(
				" 																        VM.COLOR_CODE,VM.COLOR_NAME,VM.TRIM_CODE,VM.TRIM_NAME,VM.FACTORY_OPTIONS,TV.NODE_STATUS,TV.LIFE_CYCLE,TV.VEHICLE_USAGE, \n");
		sql.append(
				" 																	    (CASE WHEN TV.NODE_STATUS IN(11511009,11511010,11511012,11511015,11511016,11511019)  THEN 1 ELSE 2 END) ALLOT, \n");
		sql.append(
				" 																		 DATE_FORMAT(TV.NODE_DATE,'%Y-%m-%d %H:%i:%s ') NODE_DATE \n");
		sql.append(
				" 																	FROM TM_VEHICLE_DEC         TV, \n");
		sql.append(" 																		 (" + getVwMaterialSql()
				+ ")        VM \n");
		sql.append(
				"                                                                    WHERE TV.MATERIAL_ID = VM.MATERIAL_ID \n");
		// 品牌
		String string = queryParam.get("brandCode");
		if (!StringUtils.isNullOrEmpty(queryParam.get("brandName"))) {
			sql.append(" and VM.BRAND_ID= " + queryParam.get("brandName") + " \n");
		}
		// 车系
		if (!StringUtils.isNullOrEmpty(queryParam.get("seriesName"))) {
			sql.append(" and VM.SERIES_ID= " + queryParam.get("seriesName") + " \n");
		}
		// 车款
		if (!StringUtils.isNullOrEmpty(queryParam.get("groupName"))) {
			sql.append(" and VM.GROUP_ID= " + queryParam.get("groupName") + " \n");
		}
		// 年款
		if (!StringUtils.isNullOrEmpty(queryParam.get("modelYear"))) {
			sql.append(" and VM.MODEL_YEAR= " + queryParam.get("modelYear") + " \n");
		}
		// 外饰
		if (!StringUtils.isNullOrEmpty(queryParam.get("colorName"))) {
			sql.append(" and VM.COLOR_CODE= '" + queryParam.get("colorName") + "' \n");
		}
		// 内饰
		if (!StringUtils.isNullOrEmpty(queryParam.get("trimName"))) {
			sql.append(" and VM.TRIM_CODE= '" + queryParam.get("trimName") + "' \n");
		}

		sql.append(
				"                                                                      AND vm.group_type = 90081002) t1 \n");
		sql.append(
				" 														   LEFT JOIN TT_VS_ORDER TVO ON t1.VIN=TVO.VIN \n");
		sql.append(
				" 														   WHERE 1=1 AND TVO.ORDER_STATUS<20071007 \n");
		sql.append(") t2 \n");
		sql.append(
				" 												  LEFT JOIN (SELECT TOR2.ORG_ID ORG_ID2,TD.DEALER_ID,TD.DEALER_CODE,TD.DEALER_SHORTNAME DEALER_NAME,TOR2.ORG_DESC ORG_DESC2,TOR3.ORG_DESC ORG_DESC3 \n");
		sql.append(
				" 															   FROM TM_ORG                     TOR2, \n");
		sql.append(
				" 																	TM_ORG                     TOR3, \n");
		sql.append(
				" 																    TM_DEALER_ORG_RELATION     TDOR, \n");
		sql.append(
				" 																    TM_DEALER                  TD \n");
		sql.append(
				" 															   WHERE TOR3.PARENT_ORG_ID = TOR2.ORG_ID \n");
		sql.append(" 															     AND TOR3.ORG_ID = TDOR.ORG_ID \n");
		sql.append(
				" 															     AND TDOR.DEALER_ID = TD.DEALER_ID \n");
		sql.append(" 																 AND TOR3.ORG_LEVEL = 3 \n");
		sql.append(" 																 AND TOR2.ORG_LEVEL = 2) t3 \n");
		sql.append(" 												  ON t2.DEALER_ID = t3.DEALER_ID \n");
		// 登陆用户DEALER_ID
		sql.append("												  WHERE t3.DEALER_ID=201308217685191) t4 \n");

		sql.append(
				" 								  LEFT JOIN (SELECT TVCR.VEHICLE_ID,TVCR.COMMON_ID,TVCR.RESOURCE_SCOPE,TVCR.TYPE,(CASE WHEN TVCR.RESOURCE_SCOPE=2010010100070674 THEN '' ELSE TOR.ORG_DESC END) ORG_DESC \n");
		sql.append(" 											   FROM TT_VS_COMMON_RESOURCE            TVCR, \n");
		sql.append(" 												    TT_VS_COMMON_RESOURCE_DETAIL     TVCRD, \n");
		sql.append(" 											        TM_ORG                           TOR \n");
		sql.append(" 											   WHERE TVCR.COMMON_ID=TVCRD.COMMON_ID \n");
		sql.append(" 												 AND TVCR.RESOURCE_SCOPE=TOR.ORG_ID \n");
		sql.append(" 											     AND TVCRD.STATUS=10011001 \n");
		sql.append(" 												 AND TVCR.STATUS=20801002) t5 \n");
		sql.append(" 								  ON t4.VEHICLE_ID=t5.VEHICLE_ID) t6 \n");
		sql.append(
				" 						  LEFT JOIN TM_ORG  TOR ON t6.ORG_ID = TOR.ORG_ID AND TOR.ORG_LEVEL=2) t7 \n");
		sql.append(" 			       LEFT JOIN TT_RESOURCE_REMARK TRR ON t7.VIN=TRR.VIN) t8 \n");
		sql.append(
				" 		    LEFT JOIN (SELECT * FROM TT_VS_INSPECTION TV WHERE TV.CREATE_DATE IN(SELECT MAX(TI.CREATE_DATE) FROM TT_VS_INSPECTION TI WHERE TI.VEHICLE_ID=TV.VEHICLE_ID)) TVI ON t8.VEHICLE_ID=TVI.VEHICLE_ID) t9 \n");
		sql.append(" 	   LEFT JOIN TT_VS_NVDR TVN ON t9.VIN=TVN.VIN) t10 \n");
		sql.append(
				"  LEFT JOIN (SELECT TCV.TC_VEHICLE_ID,TCV.VEHICLE_ID,DATE_FORMAT(TCV.ARRIVE_PORT_DATE,'%Y-%m-%d %H:%i:%s') ARRIVE_PORT_DATE,DATE_FORMAT(TCV.PAYMENG_DATE,'%Y-%m-%d %H:%i:%s') PAYMENG_DATE,DATE_FORMAT(TCV.START_SHIPMENT_DATE,'%Y-%m-%d %H:%i:%s') START_SHIPMENT_DATE, \n");
		sql.append(
				"				    TCV.CLORDER_SCANNING_NO,CONCAT(TCV.CLORDER_SCANNING_URL,'') CLORDER_SCANNING_URL,TCV.SCORDER_SCANNING_NO,CONCAT(TCV.SCORDER_SCANNING_URL,'') SCORDER_SCANNING_URL \n");
		sql.append("			   FROM TM_CTCAI_VEHICLE    TCV) t11 \n");
		sql.append("  ON t10.VEHICLE_ID=t11.VEHICLE_ID \n");
		sql.append("  WHERE 1=1 \n");
		// 查询条件
		// VIN
		if (!StringUtils.isNullOrEmpty(queryParam.get("vin"))) {
			sql.append(" and t10.VIN like '%" + queryParam.get("vin") + "%' \n");
		}
		// 车辆节点
		if (!StringUtils.isNullOrEmpty(queryParam.get("nodeStatus1"))) {
			sql.append(" and t10.NODE_STATUS >= '" + queryParam.get("nodeStatus1") + "' \n");
		}
		// 车辆节点
		if (!StringUtils.isNullOrEmpty(queryParam.get("nodeStatus2"))) {
			sql.append(" and t10.NODE_STATUS <= '" + queryParam.get("nodeStatus2") + "' \n");
		}

		// 订单状态
		if (!StringUtils.isNullOrEmpty(queryParam.get("orderStatus"))) {
			sql.append(" and t10.ORDER_STATUS = " + queryParam.get("orderStatus") + " \n");
		}

		//
		if (!StringUtils.isNullOrEmpty(queryParam.get("storeType"))) {
			String string2 = queryParam.get("storeType");
			int parseInt = Integer.parseInt(string2);
			String s = "";
			if ("20131001".equals(string2)) {
				s = "1";

			}
			if ("20131002".equals(string2)) {
				s = "2";

			}

			sql.append("      and t10.ALLOT in('" + s + "') \n");

		}
		// 付款方式
		if (!StringUtils.isNullOrEmpty(queryParam.get("vehicleSalePayType"))) {
			sql.append(" and t10.PAYMENT_TYPE = " + queryParam.get("vehicleSalePayType") + " \n");
		}
		// 工厂选装
		if (!StringUtils.isNullOrEmpty(queryParam.get("factoryOption"))) {
			sql.append(" and t10.FACTORY_OPTIONS like  %" + queryParam.get("factoryOption") + "% \n");
		}
		// 订单类型
		if (!StringUtils.isNullOrEmpty(queryParam.get("orderType"))) {
			sql.append(" and t10.ORDER_TYPE = " + queryParam.get("orderType") + " \n");
		}

		// sql.append(" ORDER BY t10.DEALER_CODE,t10.NODE_DATE DESC \n");
		System.out.println(sql.toString());
		return sql.toString();
	}

	/**
	 * SQL组装 整车订单下载(经销商)
	 * 
	 * @param queryParam
	 * @param params
	 * @return
	 */
	private String getDealerDownloadSql(Map<String, String> queryParam, List<Object> params) {
		StringBuilder sql = new StringBuilder();
		sql.append(
				"SELECT t12.*,TCFI.BALANCE,TCFI.CTCAI_ACCEPTANCES_BALANCE,TCFI.DEALER_ACCEPTANCES_BALANCE,TCFI.CTCAI_ACCEPTANCES_DISCOUNT_BALANCE,TCFI.DEALER_ACCEPTANCES_DISCOUNT_BALANCE,TCFI.REBATES_BALANCE \n");
		sql.append(
				"  FROM (SELECT t10.*,t11.*,(CASE WHEN t10.DEALER_NAME!='' THEN t10.DEALER_NAME WHEN t10.BIG_AREA!='' THEN t10.BIG_AREA ELSE '全国' END) RESOURCE \n");
		sql.append("  		  FROM (SELECT t9.*,DATE_FORMAT(TVN.CREATE_DATE,'%Y-%m-%d %H:%i:%s') NVDR_DATE \n");
		sql.append(
				"   		  		  FROM (SELECT t8.*,DATE_FORMAT(tvi.ARRIVE_DATE,'%Y-%m-%d %H:%i:%s') ARRIVE_DATE2 \n");
		sql.append(
				" 				  		  FROM (SELECT t7.*,(CASE WHEN t7.COMMON_DATE!='' THEN t7.COMMON_DATE WHEN t7.COMMON_DATE='' THEN t7.APPOINT_DATE END) COMMON_APPOINT_DATE,trr.REMARK,trr.OTHER_REMARK,(CASE WHEN trr.IS_LOCK=1 THEN '是' ELSE '否' END) IS_LOCK \n");
		sql.append(
				" 						  		  FROM (SELECT IFNULL(t6.ORDER_TYPE,0) ORDER_TYPE,t6.ORG_ID,(CASE WHEN t6.DEALER_ID>0 THEN TOR.ORG_DESC WHEN t6.ORG_ID>0 THEN TOR.ORG_DESC ELSE t6.ORG_DESC2 END) BIG_AREA,(CASE WHEN t6.DEALER_ID>0 THEN t6.ORG_DESC3 END) SMALL_AREA,t6.MODEL_YEAR,t6.BRAND_CODE, \n");
		sql.append(
				" 								       		   t6.SERIES_CODE,t6.SERIES_NAME,t6.GROUP_CODE,t6.GROUP_NAME,t6.DEALER_CODE,t6.DEALER_NAME,t6.VIN,t6.NODE_STATUS,t6.VEHICLE_ID,t6.ALLOT,t6.NODE_DATE,t6.WHOLESALE_PRICE,t6.RETAIL_PRICE,t6.COLOR_CODE,t6.COLOR_NAME,t6.TRIM_CODE,t6.TRIM_NAME,t6.FACTORY_OPTIONS, \n");
		sql.append(
				" 									   		   t6.VEHICLE_USAGE,t6.PAYMENT_TYPE,t6.CTCAI_CODE,DATE_FORMAT(t6.DEAL_ORDER_AFFIRM_DATE,'%Y-%m-%d %H:%i:%s') DEAL_ORDER_AFFIRM_DATE,t6.ORDER_STATUS, \n");
		sql.append(
				"											   (CASE WHEN t6.ORDER_TYPE IN(20831001,20831002) THEN DATE_FORMAT(t6.COMMON_DATE,'%Y-%m-%d %H:%i:%s') ELSE '' END) COMMON_DATE,(CASE WHEN t6.ORDER_TYPE=20831003 THEN DATE_FORMAT(t6.APPOINT_DATE,'%Y-%m-%d %H:%i:%s') ELSE '' END) APPOINT_DATE \n");
		sql.append(" 								  		  FROM (SELECT t4.DEALER_ID,t4.CTCAI_CODE, \n");
		sql.append(
				" 									   		   		   (CASE WHEN t4.ORDER_TYPE>0 THEN t4.ORDER_TYPE ELSE (CASE WHEN t5.TYPE=20811001 THEN 20831002 WHEN t5.TYPE=20811002 THEN 20831001 END) END) ORDER_TYPE, \n");
		sql.append(
				" 									   		   		   (CASE WHEN t4.ORG_ID2>0 THEN t4.ORG_ID2 WHEN t5.RESOURCE_SCOPE>0 THEN t5.RESOURCE_SCOPE ELSE t4.ORG_ID1 END) ORG_ID, \n");
		sql.append(
				" 									    	   		   t4.VEHICLE_ID,t4.VIN,t4.BRAND_CODE,t4.SERIES_CODE,t4.SERIES_NAME,t4.GROUP_CODE,t4.GROUP_NAME,t4.MODEL_YEAR,t4.COLOR_CODE,t4.COLOR_NAME,t4.TRIM_CODE,t4.TRIM_NAME,t4.FACTORY_OPTIONS,t4.NODE_STATUS,t4.LIFE_CYCLE, \n");
		sql.append(
				" 								        	   		   t4.VEHICLE_USAGE,t4.DEALER_CODE,t4.DEALER_NAME,t4.PAYMENT_TYPE,t4.DEAL_ORDER_AFFIRM_DATE,t4.ALLOT,t4.NODE_DATE,t4.APPOINT_DATE,t4.WHOLESALE_PRICE,t4.RETAIL_PRICE,t4.ORDER_STATUS, \n");
		sql.append(
				" 									   		   		   (CASE WHEN t5.RESOURCE_SCOPE>0 THEN t5.ORG_DESC ELSE t4.ORG_DESC2 END) ORG_DESC2,t4.ORG_DESC3,t5.COMMON_DATE \n");
		sql.append(
				" 				 						  	   	   FROM (SELECT t2.*,t3.ORG_ID2,t3.DEALER_CODE,t3.DEALER_NAME,t3.ORG_DESC2,t3.ORG_DESC3,t3.CTCAI_CODE \n");
		sql.append(
				" 												  		   FROM (SELECT (CASE WHEN TVO.DEALER_ID>0 THEN TVO.DEALER_ID ELSE t1.DEALER_ID2 END) DEALER_ID,TVO.ORDER_TYPE,TVO.PAYMENT_TYPE,TVO.DEAL_ORDER_AFFIRM_DATE,TVO.CREATE_DATE APPOINT_DATE,TVO.ORDER_STATUS,t1.* \n");
		sql.append(
				" 												           		   FROM (SELECT TV.ORG_ID ORG_ID1,TV.DEALER_ID DEALER_ID2,TV.VEHICLE_ID, TV.VIN,VM.BRAND_CODE,VM.SERIES_CODE,VM.SERIES_NAME,VM.GROUP_CODE,VM.GROUP_NAME,VM.MODEL_YEAR, \n");
		sql.append(
				" 																        		VM.COLOR_CODE,VM.COLOR_NAME,VM.TRIM_CODE,VM.TRIM_NAME,VM.FACTORY_OPTIONS,TV.NODE_STATUS,TV.LIFE_CYCLE,TV.VEHICLE_USAGE, \n");
		sql.append(
				" 																	    	    (CASE WHEN TV.NODE_STATUS IN(11511009,11511010,11511012,11511015,11511016,11511018,11511019)  THEN 1 ELSE 2 END) ALLOT, \n");
		sql.append(
				" 																			    DATE_FORMAT(TV.NODE_DATE,'%Y-%m-%d %H:%i:%s') NODE_DATE,TV.WHOLESALE_PRICE,TV.RETAIL_PRICE \n");
		sql.append(
				" 																		   FROM TM_VEHICLE_DEC         TV, \n");
		sql.append(" 																		 	    ("
				+ getVwMaterialSql() + ")        VM \n");
		sql.append(
				"                                                                    	   WHERE TV.MATERIAL_ID = VM.MATERIAL_ID   \n");
		sql.append(
				" 																				AND  VM.GROUP_TYPE=90081002 \n");

		// 查询条件
		// 品牌
		if (!StringUtils.isNullOrEmpty(queryParam.get("brandId"))) {
			sql.append(" and VM.BRAND_ID= " + queryParam.get("brandId") + " \n");
		}
		// 车系
		if (!StringUtils.isNullOrEmpty(queryParam.get("seriesId"))) {
			sql.append(" and VM.SERIES_ID= " + queryParam.get("seriesId") + " \n");
		}
		// 车款
		if (!StringUtils.isNullOrEmpty(queryParam.get("groupId"))) {
			sql.append(" and VM.GROUP_ID= " + queryParam.get("groupId") + " \n");
		}
		// 年款
		if (!StringUtils.isNullOrEmpty(queryParam.get("modelId"))) {
			sql.append(" and VM.MODEL_YEAR= " + queryParam.get("modelId") + " \n");
		}
		// 外饰
		if (!StringUtils.isNullOrEmpty(queryParam.get("colorId"))) {
			sql.append(" and VM.COLOR_NAME= '" + queryParam.get("colorId") + "' \n");
		}
		// 内饰
		if (!StringUtils.isNullOrEmpty(queryParam.get("trimId"))) {
			sql.append(" and VM.TRIM_NAME= '" + queryParam.get("trimId") + "' \n");
		}
		sql.append("     ) t1 \n");
		sql.append(
				" 														   	        LEFT JOIN TT_VS_ORDER TVO ON t1.VIN=TVO.VIN \n");
		sql.append(
				" 														   		    WHERE 1=1 AND TVO.ORDER_STATUS<20071007 \n");

		sql.append(") t2 \n");
		sql.append(
				" 												  	    LEFT JOIN (SELECT TOR2.ORG_ID ORG_ID2,TC.CTCAI_CODE,TD.DEALER_ID,TD.DEALER_CODE,TD.DEALER_SHORTNAME DEALER_NAME,TOR2.ORG_DESC ORG_DESC2,TOR3.ORG_DESC ORG_DESC3 \n");
		sql.append(
				" 															   	     FROM TM_ORG                     TOR2, \n");
		sql.append(
				" 																		  TM_ORG                     TOR3, \n");
		sql.append(
				" 																    	  TM_DEALER_ORG_RELATION     TDOR, \n");
		sql.append(
				" 																    	  TM_DEALER                  TD, \n");
		sql.append(
				"																		  TM_COMPANY                 TC \n");
		sql.append(
				" 															   	     WHERE TOR3.PARENT_ORG_ID = TOR2.ORG_ID \n");
		sql.append(
				" 															     	   AND TOR3.ORG_ID = TDOR.ORG_ID \n");
		sql.append(
				" 															     	   AND TDOR.DEALER_ID = TD.DEALER_ID \n");
		sql.append(
				"																	   AND TD.COMPANY_ID = TC.COMPANY_ID \n");
		sql.append(" 																 	   AND TOR3.ORG_LEVEL = 3 \n");
		sql.append(
				" 																 	   AND TOR2.ORG_LEVEL = 2) t3 \n");
		sql.append(" 												  	 	ON t2.DEALER_ID = t3.DEALER_ID \n");

		// 登陆用户DEALER_ID 条件
		sql.append("												  		WHERE t3.DEALER_ID=201308217685191) t4 \n");

		sql.append(
				" 								  		  LEFT JOIN (SELECT TVCR.VEHICLE_ID,TVCR.COMMON_ID,TVCR.CREATE_DATE COMMON_DATE,TVCR.RESOURCE_SCOPE,TVCR.TYPE,(CASE WHEN TVCR.RESOURCE_SCOPE=2010010100070674 THEN '' ELSE TOR.ORG_DESC END) ORG_DESC \n");
		sql.append(
				" 											   		   FROM TT_VS_COMMON_RESOURCE            TVCR, \n");
		sql.append(
				" 												    		TT_VS_COMMON_RESOURCE_DETAIL     TVCRD, \n");
		sql.append(
				" 											        		TM_ORG                           TOR \n");
		sql.append(" 											   		   WHERE TVCR.COMMON_ID=TVCRD.COMMON_ID \n");
		sql.append(" 												 		 AND TVCR.RESOURCE_SCOPE=TOR.ORG_ID \n");
		sql.append(" 											     		 AND TVCRD.STATUS=10011001 \n");
		sql.append(" 												 		 AND TVCR.STATUS=20801002) t5 \n");
		sql.append(" 								  	       ON t4.VEHICLE_ID=t5.VEHICLE_ID) t6 \n");
		sql.append(
				" 						  	        LEFT JOIN TM_ORG  TOR ON t6.ORG_ID = TOR.ORG_ID AND TOR.ORG_LEVEL=2) t7 \n");
		sql.append(" 			       			LEFT JOIN TT_RESOURCE_REMARK TRR ON t7.VIN=TRR.VIN) t8 \n");
		sql.append(
				" 		    		LEFT JOIN (SELECT * FROM TT_VS_INSPECTION TV WHERE TV.CREATE_DATE IN(SELECT MAX(TI.CREATE_DATE) FROM TT_VS_INSPECTION TI WHERE TI.VEHICLE_ID=TV.VEHICLE_ID)) TVI ON t8.VEHICLE_ID=TVI.VEHICLE_ID) t9 \n");
		sql.append(" 	   		 LEFT JOIN TT_VS_NVDR TVN ON t9.VIN=TVN.VIN) t10 \n");
		sql.append(
				" 	   LEFT JOIN (SELECT TCV.VEHICLE_ID VEHICLE_ID1,TCV.CLORDER_SCANNING_NO,TCV.CLORDER_SCANNING_URL,TCV.SCORDER_SCANNING_NO,TCV.SCORDER_SCANNING_URL, \n");
		sql.append(
				"				        DATE_FORMAT(TCV.ARRIVE_PORT_DATE,'%Y-%m-%d %H:%i:%s') ARRIVE_PORT_DATE,DATE_FORMAT(TCV.PAYMENG_DATE,'%Y-%m-%d %H:%i:%s') PAYMENG_DATE,DATE_FORMAT(TCV.START_SHIPMENT_DATE,'%Y-%m-%d %H:%i:%s') START_SHIPMENT_DATE, \n");
		sql.append(
				"				        DATE_FORMAT(TCV.UNORDER_EVIDENCE_DATE,'%Y-%m-%d %H:%i:%s') UNORDER_EVIDENCE_DATE,DATE_FORMAT(TCV.MACHECK_EVIDENCE_DATE,'%Y-%m-%d %H:%i:%s') MACHECK_EVIDENCE_DATE,DATE_FORMAT(TCV.PLANCE_ORDER_DATE,'%Y-%m-%d %H:%i:%s') PLANCE_ORDER_DATE, \n");
		sql.append(
				"				        DATE_FORMAT(TCV.ARRIVE_DATE,'%Y-%m-%d %H:%i:%s') ARRIVE_DATE,DATE_FORMAT(TCV.SINGLE_POST_DATE,'%Y-%m-%d %H:%i:%s') SINGLE_POST_DATE,DATE_FORMAT(TCV.STOCKOUT_DATE,'%Y-%m-%d %H:%i:%s') STOCKOUT_DATE, \n");
		sql.append(
				"				        TCV.CG_ORDER_NO,TCV.INVOICE_NO,TCV.ONTHEWAY_POSITION,TCV.SINGLE_POST_EMS,TCV.STANDART_CAR_PRICE,TCV.USE_REBATE,TCV.FINAL_CAR_REBATE \n");
		sql.append("			        FROM  TM_CTCAI_VEHICLE    TCV) t11 \n");
		sql.append("  	   ON t10.VEHICLE_ID=t11.VEHICLE_ID1) t12 \n");
		sql.append(
				"  LEFT JOIN (SELECT * FROM TI_CTCAI_FUND_INFO t WHERE t.CREATE_DATE IN (SELECT MAX(tc.CREATE_DATE) FROM TI_CTCAI_FUND_INFO tc WHERE tc.DEALERCODE=t.DEALERCODE))  TCFI ON t12.DEALER_CODE=TCFI.DEALERCODE \n");
		sql.append("  WHERE 1=1 \n");
		// 查询条件
		// VIN
		if (!StringUtils.isNullOrEmpty(queryParam.get("vin"))) {
			sql.append(" and t12.VIN like '%" + queryParam.get("vin") + "%' \n");
		}
		// 车辆节点
		if (!StringUtils.isNullOrEmpty(queryParam.get("vehicleNode"))) {
			sql.append(" and t12.NODE_STATUS = " + queryParam.get("vehicleNode") + " \n");
		}

		// 订单状态
		if (!StringUtils.isNullOrEmpty(queryParam.get("orderStatus"))) {
			sql.append(" and t12.ORDER_STATUS = " + queryParam.get("orderStatus") + " \n");
		}

		// 付款方式
		if (!StringUtils.isNullOrEmpty(queryParam.get("vehicleSalePayType"))) {
			sql.append(" and t12.PAYMENT_TYPE = " + queryParam.get("vehicleSalePayType") + " \n");
		}
		// 工厂选装
		if (!StringUtils.isNullOrEmpty(queryParam.get("factoryOption"))) {
			sql.append(" and t12.FACTORY_OPTIONS like  %" + queryParam.get("factoryOption") + "% \n");
		}

		// 订单类型
		if (!StringUtils.isNullOrEmpty(queryParam.get("orderType"))) {
			sql.append(" and t12.ORDER_TYPE = " + queryParam.get("orderType") + " \n");
		}

		// sql.append(" #and t12.DEALER_CODE in ('33123') \n");

		sql.append("   ORDER BY t12.DEALER_CODE,t12.NODE_DATE DESC \n");

		return sql.toString();
	}

	/**
	 * 整车订单(经销商)明细 车辆属性
	 * 
	 * @param queryParam
	 * @return pageInfoDto
	 */
	public List<Map> getDealerVehicleDetailListByVIN(String vin) {
		List<Object> params = new ArrayList<Object>();
		StringBuilder sql = new StringBuilder();

		sql.append(
				"	SELECT TV.VIN,TV.ENGINE_NO,TV.REMARK,VM.BRAND_NAME,VM.SERIES_NAME,VM.MODEL_NAME,VM.COLOR_NAME,VM.TRIM_NAME \n");
		sql.append("	    FROM TM_VEHICLE_DEC      TV, \n");
		sql.append("	         (" + getVwMaterialSql() + ")     VM \n");
		sql.append("	    WHERE TV.MATERIAL_ID = VM.MATERIAL_ID \n");
		sql.append("	      AND TV.VIN='" + vin + "' \n");
		List<Map> orderList = OemDAOUtil.findAll(sql.toString(), params);
		return orderList;
	}

	/**
	 * 整车订单(经销商)明细 车辆变更日志
	 * 
	 * @param queryParam
	 * @return pageInfoDto
	 */
	public List<Map> getDealerVehicleChangeDetailListByVIN(String vin) {
		List<Object> params = new ArrayList<Object>();
		StringBuilder sql = new StringBuilder();
		sql.append(
				"	SELECT DISTINCT TVVC.CHANGE_CODE,DATE_FORMAT(TVVC.CHANGE_DATE ,'%Y-%m-%d %H:%i:%s') CHANGE_DATE,TVVC.CHANGE_DESC,TD.DEALER_SHORTNAME DEALER_NAME \n");
		sql.append("	     FROM TM_VEHICLE_DEC           TV, \n");
		sql.append("	          TT_VS_VHCL_CHNG      TVVC,	 \n");
		sql.append("	     	   TM_DEALER            TD \n");
		sql.append("	       WHERE TVVC.VEHICLE_ID = TV.VEHICLE_ID \n");
		sql.append("	         AND TV.DEALER_ID = TD.DEALER_ID \n");
		// 经销商端的详细车籍查询的车辆变更类型只有三种：发运出库 验收入库 实销
		sql.append("	         AND TVVC.CHANGE_CODE IN(20211007,20211009,20211010) \n");
		sql.append("	         AND TV.VIN='" + vin + "' \n");
		List<Map> orderList = OemDAOUtil.findAll(sql.toString(), params);
		return orderList;
	}

}
