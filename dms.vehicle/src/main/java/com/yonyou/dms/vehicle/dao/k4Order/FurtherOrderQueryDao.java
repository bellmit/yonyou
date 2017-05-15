package com.yonyou.dms.vehicle.dao.k4Order;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.yonyou.dms.framework.DAO.OemBaseDAO;
import com.yonyou.dms.framework.DAO.OemDAOUtil;
import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.function.common.OemDictCodeConstants;
import com.yonyou.dms.function.utils.common.StringUtils;

/**
 * @author liujiming
 * @date 2017年3月7日
 */
@Repository
public class FurtherOrderQueryDao extends OemBaseDAO {

	/**
	 * 期货订单撤单查询
	 * 
	 * @param queryParam
	 * @return pageInfoDto
	 */
	public PageInfoDto getFurtherOrderQueryList(Map<String, String> queryParam) {
		List<Object> params = new ArrayList<Object>();
		String sql = getFurtherOrderQuerySql(queryParam, params);
		PageInfoDto pageInfoDto = OemDAOUtil.pageQuery(sql, params);
		return pageInfoDto;

	}

	/**
	 * 期货订单撤单下载
	 * 
	 * @param queryParam
	 * @return pageInfoDto
	 */
	public List<Map> getFurtherOrderDownloadList(Map<String, String> queryParam) {
		List<Object> params = new ArrayList<Object>();
		String sql = getFurtherOrderQuerySql(queryParam, params);
		List<Map> orderList = OemDAOUtil.downloadPageQuery(sql, params);
		return orderList;
	}

	/**
	 * SQL组装 期货订单撤单
	 * 
	 * @param queryParam
	 * @param params
	 * @return
	 */
	private String getFurtherOrderQuerySql(Map<String, String> queryParam, List<Object> params) {

		StringBuilder sql = new StringBuilder();
		sql.append(
				"SELECT t.* FROM (SELECT  TD.DEALER_SHORTNAME RESOURCE,TVO.ORDER_ID,TVO.ORDER_NO,TVO.VIN,TD.DEALER_CODE,TD.DEALER_SHORTNAME DEALER_NAME,DATE_FORMAT(TVO.DEAL_BOOK_DATE,'%Y-%c-%d %H:%i:%s') DEAL_BOOK_DATE,TVO.ORDER_STATUS,TV.NODE_STATUS, \n");
		sql.append(
				"        VM.MODEL_CODE,VM.MODEL_NAME,VM.BRAND_CODE,VM.BRAND_ID,VM.SERIES_CODE,VM.SERIES_ID,VM.GROUP_NAME,VM.GROUP_ID,VM.MODEL_YEAR,VM.COLOR_NAME,VM.TRIM_NAME,TV.VPC_PORT \n");
		sql.append("   FROM  TT_VS_ORDER                TVO, \n");
		sql.append("         TM_DEALER                  TD, \n");
		sql.append("         TM_VEHICLE_DEC             TV, \n");
		sql.append("         (" + getVwMaterialSql() + ")                VM \n");
		sql.append("   WHERE  TVO.DEALER_ID = TD.DEALER_ID \n");
		sql.append("     AND  VM.GROUP_TYPE=90081002 \n");
		sql.append("     AND  TVO.VIN = TV.VIN \n");
		sql.append("     AND  TV.MATERIAL_ID = VM.MATERIAL_ID \n");
		sql.append("	 AND  TVO.ORDER_TYPE=20831002 \n");
		sql.append("     AND  TVO.ORDER_STATUS=20071005 \n");
		sql.append("     AND  TVO.DEAL_BOOK_DATE IS NOT NULL \n");
		sql.append("UNION \n");
		sql.append(
				"SELECT  (CASE WHEN TOR.ORG_ID=2010010100070674 THEN '全国' ELSE TOR.ORG_DESC END) RESOURCE,0 AS ORDER_ID, '' AS ORDER_NO,TV.VIN,'' AS DEALER_CODE,'' AS DEALER_NAME,NULL AS DEAL_BOOK_DATE,0 AS ORDER_STATUS, TV.NODE_STATUS, \n");
		sql.append(
				"        VM.MODEL_CODE,VM.MODEL_NAME,VM.BRAND_CODE,VM.BRAND_ID,VM.SERIES_CODE,VM.SERIES_ID,VM.GROUP_NAME,VM.GROUP_ID,VM.MODEL_YEAR,VM.COLOR_NAME,VM.TRIM_NAME,TV.VPC_PORT \n");
		sql.append("   FROM  TT_VS_COMMON_RESOURCE         TVCR, \n");
		sql.append("  		 TT_VS_COMMON_RESOURCE_DETAIL   TVCRD, \n");
		sql.append("		TM_VEHICLE_DEC                     TV, \n");
		sql.append("   		 (" + getVwMaterialSql() + ")                    VM, \n");
		sql.append("  		 TM_ORG                         TOR \n");
		sql.append("   WHERE  TVCR.VEHICLE_ID = TV.VEHICLE_ID \n");
		sql.append(" AND  VM.GROUP_TYPE=90081002 \n");
		sql.append("   	 AND TVCR.COMMON_ID = TVCRD.COMMON_ID \n");
		sql.append("   	 AND  TV.MATERIAL_ID = VM.MATERIAL_ID \n");
		sql.append("   	 AND  TVCR.RESOURCE_SCOPE = TOR.ORG_ID \n");
		sql.append("   	 AND  TVCR.TYPE = 20811001 \n");
		sql.append("   	 AND  TVCR.STATUS=20801002 \n");
		sql.append("   	 AND  TVCRD.STATUS=10011001 \n");
		sql.append(
				"   	 AND  (NOT EXISTS (SELECT 1 FROM TT_VS_ORDER WHERE VIN = TV.VIN AND ORDER_TYPE = 20831002 AND ORDER_STATUS < 20071008) \n");
		sql.append(
				"  			OR EXISTS (SELECT 1 FROM TT_VS_ORDER WHERE VIN = TV.VIN AND ORDER_STATUS = 20071007))) t \n");
		sql.append("   WHERE 1=1 \n");
		// 品牌
		if (!StringUtils.isNullOrEmpty(queryParam.get("brandId"))) {
			sql.append(" and t.BRAND_ID= " + queryParam.get("brandId") + " \n");
		}
		// 车系
		if (!StringUtils.isNullOrEmpty(queryParam.get("seriesId"))) {
			sql.append(" and t.SERIES_ID= " + queryParam.get("seriesId") + " \n");
		}
		// 车款
		if (!StringUtils.isNullOrEmpty(queryParam.get("groupId"))) {
			sql.append(" and t.GROUP_ID= " + queryParam.get("groupId") + " \n");
		}
		// 年款
		if (!StringUtils.isNullOrEmpty(queryParam.get("modelId"))) {
			sql.append(" and t.MODEL_YEAR= " + queryParam.get("modelId") + " \n");
		}
		// 外饰
		if (!StringUtils.isNullOrEmpty(queryParam.get("colorId"))) {
			sql.append(" and t.COLOR_NAME= '" + queryParam.get("colorId") + "' \n");
		}
		// 内饰
		if (!StringUtils.isNullOrEmpty(queryParam.get("trimId"))) {
			sql.append(" and t.TRIM_NAME= '" + queryParam.get("trimId") + "' \n");
		}
		// 经销商
		if (!StringUtils.isNullOrEmpty(queryParam.get("dealerCode"))) {
			sql.append(" and t.DEALER_CODE in (" + queryParam.get("dealerCode") + ") \n");
		}
		// VIN
		if (!StringUtils.isNullOrEmpty(queryParam.get("vin"))) {
			sql.append(" and t.VIN like ? \n");
			params.add("%" + queryParam.get("vin") + "%");
		}
		// 订单号
		if (!StringUtils.isNullOrEmpty(queryParam.get("orderNo"))) {
			sql.append(" and t.ORDER_NO like ? \n");
			params.add("%" + queryParam.get("orderNo") + "%");
		}

		// 未付全款天数

		return sql.toString();
	}

	/**
	 * 期货订单撤单查询ByVIN
	 * 
	 * @param queryParam
	 * @return pageInfoDto
	 */
	public List<Map> getFurtherOrderByVIN(String vin) {

		List<Object> params = new ArrayList<Object>();
		StringBuilder sql = new StringBuilder();
		sql.append(
				"	SELECT t.* FROM (SELECT  TD.DEALER_SHORTNAME RESOURCE,TVO.ORDER_ID,TVO.ORDER_NO,TVO.VIN,TD.DEALER_CODE,TD.DEALER_SHORTNAME DEALER_NAME,DATE_FORMAT(TVO.DEAL_BOOK_DATE,'%Y-%c-%d %H:%i:%s') DEAL_BOOK_DATE,TVO.ORDER_STATUS,TV.NODE_STATUS, \n");
		sql.append(
				"		        VM.MODEL_CODE,VM.MODEL_NAME,VM.BRAND_CODE,VM.SERIES_CODE,VM.GROUP_NAME,VM.MODEL_YEAR,VM.COLOR_NAME,VM.TRIM_NAME \n");
		sql.append("		   FROM  TT_VS_ORDER                TVO, \n");
		sql.append("		         TM_DEALER                  TD, \n");
		sql.append("		         TM_VEHICLE_DEC                 TV, \n");
		sql.append("		         (" + getVwMaterialSql() + ")                VM \n");
		sql.append("		   WHERE  TVO.DEALER_ID = TD.DEALER_ID \n");
		sql.append("		     AND  TVO.VIN = TV.VIN \n");
		sql.append("		     AND  TV.MATERIAL_ID = VM.MATERIAL_ID \n");
		sql.append("			 AND  TVO.ORDER_TYPE=20831002 \n");
		sql.append("		     AND  TVO.ORDER_STATUS=20071005 \n");
		sql.append("		     AND  TVO.DEAL_BOOK_DATE IS NOT NULL \n");
		sql.append("		UNION \n");
		sql.append(
				"		SELECT  (CASE WHEN TOR.ORG_ID='2010010100070674' THEN '全国' ELSE TOR.ORG_DESC END) RESOURCE,0 AS ORDER_ID, '' AS ORDER_NO,TV.VIN,'' AS DEALER_CODE,'' AS DEALER_NAME,NULL AS DEAL_BOOK_DATE,0 AS ORDER_STATUS, TV.NODE_STATUS, \n");
		sql.append(
				"		        VM.MODEL_CODE,VM.MODEL_NAME,VM.BRAND_CODE,VM.SERIES_CODE,VM.GROUP_NAME,VM.MODEL_YEAR,VM.COLOR_NAME,VM.TRIM_NAME \n");
		sql.append("		   FROM  TT_VS_COMMON_RESOURCE         TVCR, \n");
		sql.append("		  		 TT_VS_COMMON_RESOURCE_DETAIL   TVCRD, \n");
		sql.append("		    	 TM_VEHICLE_DEC                     TV, \n");
		sql.append("		   		 (" + getVwMaterialSql() + ")                    VM, \n");
		sql.append("		  		 TM_ORG                         TOR \n");
		sql.append("		   WHERE  TVCR.VEHICLE_ID = TV.VEHICLE_ID \n");
		sql.append("		   	 AND TVCR.COMMON_ID = TVCRD.COMMON_ID \n");
		sql.append("		   	 AND  TV.MATERIAL_ID = VM.MATERIAL_ID \n");
		sql.append("		   	 AND  TVCR.RESOURCE_SCOPE = TOR.ORG_ID \n");
		sql.append("		   	 AND  TVCR.TYPE = 20811001 \n");
		sql.append("		   	 AND  TVCR.STATUS=20801002 \n");
		sql.append("		   	 AND  TVCRD.STATUS=10011001 \n");
		sql.append(
				"		   	 AND  (NOT EXISTS (SELECT 1 FROM TT_VS_ORDER WHERE VIN = TV.VIN AND ORDER_TYPE = 20831002 AND ORDER_STATUS < 20071008) \n");
		sql.append(
				"		  			OR EXISTS (SELECT 1 FROM TT_VS_ORDER WHERE VIN = TV.VIN AND ORDER_STATUS = 20071007))) t \n");
		sql.append("		   WHERE 1=1 \n");
		sql.append("		     AND t.VIN = '" + vin + "' \n");

		List<Map> orderList = OemDAOUtil.findAll(sql.toString(), params);
		return orderList;
	}

	public List<Map> findCommonResource(String vin) {
		StringBuffer sql = new StringBuffer();
		sql.append("select tv.VEHICLE_ID,tvcr.TYPE,tvcr.COMMON_ID\n");
		sql.append("   from TM_VEHICLE_DEC  tv,TT_VS_COMMON_RESOURCE  tvcr,TT_VS_COMMON_RESOURCE_DETAIL tvcrd\n");
		sql.append("     where tvcr.VEHICLE_ID=tv.VEHICLE_ID\n");
		sql.append("       and tvcr.COMMON_ID=tvcrd.COMMON_ID\n");
		sql.append("       and tvcrd.STATUS=" + OemDictCodeConstants.STATUS_ENABLE + "\n");
		sql.append("       and tvcr.STATUS=" + OemDictCodeConstants.COMMON_RESOURCE_STATUS_02 + "\n");
		sql.append("	   and tv.NODE_STATUS>" + OemDictCodeConstants.VEHICLE_CHANGE_TYPE_05 + "\n");
		sql.append("       and tv.VIN='" + vin + "'\n");
		return OemDAOUtil.findAll(sql.toString(), null);
	}

}
