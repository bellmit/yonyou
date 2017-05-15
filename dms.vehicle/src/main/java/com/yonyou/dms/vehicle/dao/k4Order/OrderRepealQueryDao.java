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
 *
 * 
 * @author liujiming
 * @date 2017年2月27日
 */

@Repository
public class OrderRepealQueryDao extends OemBaseDAO {

	/**
	 * 整车订单撤单查询
	 * 
	 * @param queryParam
	 * @return pageInfoDto
	 */
	public PageInfoDto findOrderRepealQueryList(Map<String, String> queryParam) {
		List<Object> params = new ArrayList<Object>();
		String sql = getOrderQuerySql(queryParam, params);
		System.out.println(sql);
		PageInfoDto pageInfoDto = OemDAOUtil.pageQuery(sql, params);
		return pageInfoDto;

	}

	/**
	 * 整车订单撤单下载
	 * 
	 * @param queryParam
	 * @return pageInfoDto
	 */
	public List<Map> findOrderRepealQueryDownload(Map<String, String> queryParam) {
		List<Object> params = new ArrayList<Object>();
		String sql = getOrderQuerySql(queryParam, params);
		List<Map> orderList = OemDAOUtil.downloadPageQuery(sql, params);
		return orderList;
	}

	/**
	 * 整车订单明细查询
	 * 
	 * @param queryParam
	 * @return pageInfoDto
	 */
	public List<Map> findOrderRepealById(long id) {
		List<Object> params = new ArrayList<Object>();
		StringBuilder sql = new StringBuilder();
		sql.append("select t.*,TVFO.SOLD_TO\n");
		sql.append("    from (select  TVO.COMMONALITY_ID,TD.DEALER_CODE,TD.DEALER_SHORTNAME DEALER_NAME,\n");
		sql.append("        		  TVO.ORDER_TYPE,TVO.ORDER_STATUS,\n");
		sql.append("        		  TVO.ORDER_NO,left((DATE_FORMAT(TVO.ORDER_DATE,'%Y-%m-%d')),10) ORDER_DATE,\n");
		sql.append("        		  TVO.VIN,VM.SERIES_CODE,\n");
		sql.append("        		  VM.MODEL_CODE,VM.MODEL_NAME,\n");
		sql.append("        		  VM.COLOR_CODE,VM.COLOR_NAME,\n");
		sql.append("        		  VM.TRIM_CODE, VM.TRIM_NAME,\n");
		sql.append("  				  VM.MODEL_YEAR,TV.NODE_STATUS,\n");
		sql.append(
				"                  left((DATE_FORMAT(TVO.DEAL_ORDER_AFFIRM_DATE,'%Y-%m-%d')),10) DEAL_ORDER_AFFIRM_DATE,\n");
		sql.append(
				"				  TV.INVOICE_NO,left((DATE_FORMAT(TV.EXPECT_PORT_DATE,'%Y-%m-%d')),10) EXPECT_PORT_DATE\n");
		sql.append("     	      from  TT_VS_ORDER                 TVO,\n");
		sql.append("            	    TM_DEALER                   TD,\n");
		sql.append("            	    (" + getVwMaterialSql() + ")                 VM,\n");
		sql.append("            	    TM_VEHICLE_DEC                  TV\n");
		sql.append("     	      where TVO.DEALER_ID = TD.DEALER_ID\n");
		sql.append("       			    AND TVO.MATERAIL_ID = VM.MATERIAL_ID\n");
		sql.append("  	   				AND TVO.VIN = TV.VIN\n");
		sql.append("      				AND TVO.ORDER_ID=" + id + ")   t\n");
		// params.add(id);
		sql.append("	left join TT_VS_FACTORY_ORDER   TVFO\n");
		sql.append("    ON t.vin = TVFO.vin\n");
		System.out.println(sql.toString());
		List<Map> orderList = OemDAOUtil.findAll(sql.toString(), params);
		return orderList;
	}

	/**
	 * SQL组装 整车撤单查询
	 * 
	 * @param queryParam
	 * @param params
	 * @return
	 */
	private String getOrderQuerySql(Map<String, String> queryParam, List<Object> params) {
		StringBuilder sql = new StringBuilder();
		sql.append(
				"    SELECT tt.*,(CASE WHEN tca.STATUS=10011002 AND tca.IS_SUC=1 THEN '撤单成功' WHEN tca.IS_SUC=0 THEN '撤单失败' WHEN tca.STATUS=10011001 THEN '撤单中' END) UNORDER_STATUS,(CASE WHEN tca.IS_SUC=0 THEN tca.ERROR_INFO END) ERROR_INFO,(CASE WHEN trr.IS_LOCK=1 THEN '是' ELSE '否' END) IS_LOCK  FROM (SELECT t.* FROM (\n");
		sql.append(
				"        SELECT  TVO.ORDER_ID,TOR2.ORG_ID,TOR2.ORG_DESC ORG_NAME,TC.SWT_CODE,TD.DEALER_CODE,TD.DEALER_SHORTNAME DEALER_NAME,TVO.ORDER_NO,LEFT(TVO.ORDER_DATE,10) ORDER_DATE,TVO.ORDER_TYPE,TVO.ORDER_STATUS,TVO.VIN,\n");
		sql.append(
				"    	            TV.VEHICLE_ID,TV.NODE_STATUS,LEFT(CHAR(TIMESTAMP(TV.EXPECT_PORT_DATE)),10) EXPECT_PORT_DATE,LEFT(CHAR(TIMESTAMP(TV.DEALER_STORAGE_DATE)),10) DEALER_STORAGE_DATE,\n");
		sql.append(
				"    	            VM.MODEL_CODE,VM.MODEL_NAME,VM.BRAND_CODE,VM.BRAND_ID,VM.COLOR_CODE,VM.TRIM_CODE,VM.SERIES_CODE,VM.SERIES_ID,VM.GROUP_NAME,VM.GROUP_ID,VM.MODEL_YEAR,VM.COLOR_NAME,VM.TRIM_NAME,TVO.PAYMENT_TYPE,TV.VPC_PORT\n");
		sql.append("    	      FROM  TT_VS_ORDER                TVO,\n");
		sql.append("    	            TM_DEALER                  TD,TM_COMPANY TC,\n");
		sql.append("    	            TM_ORG                     TOR,\n");
		sql.append("    	            TM_ORG                     TOR2,\n");
		sql.append("    	            TM_DEALER_ORG_RELATION     TDOR,\n");
		sql.append("    	            TM_VEHICLE_DEC             TV,\n");
		sql.append("    	            (" + getVwMaterialSql() + ")                VM\n");
		sql.append("    	      WHERE  TVO.DEALER_ID = TD.DEALER_ID\n");

		// 测试数据，减少记录
		// sql.append(" AND TVO.ORDER_ID in
		// (2016010823081061,2015121022609401,2015121022609405) "); // 测试数据，减少记录

		sql.append(" 				AND  VM.GROUP_TYPE=" + OemDictCodeConstants.GROUP_TYPE_IMPORT + "\n");
		sql.append("    			AND  TD.DEALER_ID = TDOR.DEALER_ID  AND TC.COMPANY_ID = TD.COMPANY_ID\n");
		sql.append("    	        AND  TOR.ORG_ID = TDOR.ORG_ID\n");
		sql.append("    	        AND  TOR.PARENT_ORG_ID = TOR2.ORG_ID\n");
		sql.append("    	        AND  TVO.VIN = TV.VIN\n");
		sql.append("    	        AND  TVO.MATERAIL_ID = VM.MATERIAL_ID\n");

		// 发运日期 起始
		if (!StringUtils.isNullOrEmpty(queryParam.get("deliverOrderDateStart"))) {
			sql.append(" and date_format( tvo.DELIVER_ORDER_DATE,'%Y-%m-%d %H:%i:%s') >='"
					+ queryParam.get("deliverOrderDateStart") + " 00:00:00'" + " \n");
			// params.add(DateUtil.parseDefaultDate(queryParam.get("createDate")));
			// params.add("date_format('" +
			// queryParam.get("deliverOrderDateStart") + "' '23:59:59',"
			// + "'%Y-%m-%d %H:%i:%s'" + ")");

		}
		// 发运日期 结束
		if (!StringUtils.isNullOrEmpty(queryParam.get("deliverOrderDateEnd"))) {
			sql.append(" and date_format( tvo.DELIVER_ORDER_DATE,'%Y-%m-%d %H:%i:%s') <='"
					+ queryParam.get("deliverOrderDateEnd") + " 23:59:59'" + "  \n");
			// params.add(DateUtil.parseDefaultDate(queryParam.get("auditDate")));
			// params.add("date_format('" +
			// queryParam.get("deliverOrderDateEnd") + "' '23:59:59'," +
			// "'%Y-%m-%d %H:%i:%s'"
			// + ")");
		}

		// 销售大区
		if (!StringUtils.isNullOrEmpty(queryParam.get("bigOrgName"))) {
			sql.append(" and tor.PARENT_ORG_ID in (" + queryParam.get("bigOrgName") + ") \n");
		}

		// sql.append(" AND TVO.ORDER_STATUS IN(20071006,20071007)\n");
		// sql.append(" AND TV.LIFE_CYCLE IN(11521001,11521002)\n");
		// sql.append(" AND TV.NODE_STATUS IN(11511018,11511019)\n");
		sql.append("        		AND  TVO.ORDER_STATUS in(" + OemDictCodeConstants.ORDER_STATUS_06 + ","
				+ OemDictCodeConstants.ORDER_STATUS_07 + ")\n");
		sql.append("        		AND  TV.LIFE_CYCLE in(" + OemDictCodeConstants.LIF_CYCLE_01 + ","
				+ OemDictCodeConstants.LIF_CYCLE_02 + ")\n");
		sql.append("        		AND  TV.NODE_STATUS in(" + OemDictCodeConstants.VEHICLE_NODE_18 + ","
				+ OemDictCodeConstants.VEHICLE_NODE_19 + ")\n");
		sql.append("    	UNION ALL\n");
		sql.append("    SELECT DISTINCT '-1'AS ORDER_ID,\n");
		sql.append("    	    TVCR.RESOURCE_SCOPE ORG_ID,\n");
		// sql.append(" (CASE WHEN TVCR.RESOURCE_SCOPE='2010010100070674' THEN
		// '全国' ELSE (SELECT t.ORG_DESC FROM TM_ORG t WHERE
		// t.ORG_ID=TVCR.RESOURCE_SCOPE AND t.DUTY_TYPE=10431003) END)
		// ORG_NAME,\n");
		sql.append("  			(case when TVCR.RESOURCE_SCOPE=" + OemDictCodeConstants.OEM_ACTIVITIES
				+ " then '全国' else (select t.ORG_DESC from TM_ORG t where t.ORG_ID=TVCR.RESOURCE_SCOPE and t.DUTY_TYPE=10431003) end) ORG_NAME,\n");
		sql.append("    	    ''AS SWT_CODE,\n");
		sql.append("    	    ''AS DEALER_CODE,\n");
		sql.append("    	   	''AS DEALER_NAME,\n");
		sql.append("    	    ''AS ORDER_NO,\n");
		sql.append("    	   	''AS ORDER_DATE,\n");
		sql.append(
				"    	   	(CASE WHEN TVCR.TYPE=20811002 THEN 20831001 WHEN TVCR.TYPE=20811001 THEN 20831002 END) ORDER_TYPE,\n");
		sql.append("    	   	'0'AS ORDER_STATUS,\n");
		sql.append("    	   	TV.VIN,\n");
		sql.append("    	    TV.VEHICLE_ID,\n");
		sql.append("    	   	TV.NODE_STATUS,\n");
		sql.append("    	   	DATE_FORMAT(TV.EXPECT_PORT_DATE,'%Y-%m-%d ') EXPECT_PORT_DATE,\n");
		sql.append("    	 	DATE_FORMAT(TV.DEALER_STORAGE_DATE,'%Y-%m-%d ') DEALER_STORAGE_DATE,\n");
		sql.append("    	    VM.MODEL_CODE,\n"); // 车型代码
		sql.append("    	    VM.MODEL_NAME,\n"); // 车型描述
		sql.append("    		VM.BRAND_CODE,\n");// 品牌
		sql.append("    		VM.BRAND_ID,\n");// 品牌
		sql.append("    		VM.COLOR_CODE,\n");
		sql.append("    		VM.TRIM_CODE,\n");
		sql.append("    		VM.SERIES_CODE,\n");// 车系
		sql.append("    		VM.SERIES_ID,\n");// 车系
		sql.append("    	 	VM.GROUP_NAME,\n"); //
		sql.append("    	 	VM.GROUP_ID,\n"); //
		sql.append("    	 	VM.MODEL_YEAR,\n"); // 车型年
		sql.append("    	  	VM.COLOR_NAME,\n"); // 颜色
		sql.append("    	    VM.TRIM_NAME,\n"); // 内饰描述
		sql.append("    		0 AS PAYMENT_TYPE,\n");
		sql.append("    	    TV.VPC_PORT\n"); // VPC港口
		sql.append("    	   FROM  TM_VEHICLE_DEC                    TV,\n");
		sql.append("    	  		 (" + getVwMaterialSql() + ")                	   VM,\n");
		sql.append("    	  		 TT_VS_COMMON_RESOURCE      	   TVCR,\n");
		sql.append("    	    	 TT_VS_COMMON_RESOURCE_DETAIL      TVCRD\n");
		sql.append("    	    WHERE  TV.MATERIAL_ID = VM.MATERIAL_ID\n");
		// sql.append(" AND VM.GROUP_TYPE='90081002'\n");
		sql.append(" 	  		  AND  VM.GROUP_TYPE=" + OemDictCodeConstants.GROUP_TYPE_IMPORT + "\n");
		sql.append("    	   	  AND  TVCR.VEHICLE_ID = TV.VEHICLE_ID\n");
		sql.append("    	  	  AND  TVCR.COMMON_ID = TVCRD.COMMON_ID\n");
		sql.append("    	      AND  TVCRD.STATUS=10011001\n");
		// sql.append(" AND TVCR.STATUS=20801002\n");
		sql.append("  	  		  and  TVCR.STATUS=" + OemDictCodeConstants.COMMON_RESOURCE_STATUS_02 + "\n");
		sql.append("    	      AND  TV.NODE_STATUS  IN(11511018,11511019)\n");
		sql.append(
				"    	      AND  NOT EXISTS(SELECT 1 FROM TT_VS_ORDER WHERE VIN=TV.VIN AND ORDER_STATUS<20071008)\n");
		sql.append("    	  )   t \n");
		sql.append("    	  ORDER BY t.ORG_NAME,t.ORG_NAME,t.DEALER_CODE ) tt\n");
		sql.append(
				"    	  LEFT JOIN (SELECT * FROM TM_CACT_ALLOT  WHERE TM_ALLOT_ID IN(SELECT MAX(t.TM_ALLOT_ID) FROM TM_CACT_ALLOT t GROUP BY t.VIN)) tca ON tca.VIN=tt.VIN\n");
		sql.append("    	  LEFT JOIN TT_RESOURCE_REMARK trr ON trr.VIN=tt.VIN\n");
		sql.append("    	  WHERE 1=1\n");

		// 品牌
		if (!StringUtils.isNullOrEmpty(queryParam.get("brandId"))) {
			sql.append(" and tt.BRAND_ID= " + queryParam.get("brandId") + " \n");
		}
		// 车系
		if (!StringUtils.isNullOrEmpty(queryParam.get("seriesName"))) {
			sql.append(" and tt.SERIES_ID= " + queryParam.get("seriesName") + " \n");
		}
		// 车款
		if (!StringUtils.isNullOrEmpty(queryParam.get("groupName"))) {
			sql.append(" and tt.GROUP_ID= " + queryParam.get("groupName") + " \n");
		}
		// 年款
		if (!StringUtils.isNullOrEmpty(queryParam.get("modelYear"))) {
			sql.append(" and tt.MODEL_YEAR= " + queryParam.get("modelYear") + " \n");
		}
		// 外饰
		if (!StringUtils.isNullOrEmpty(queryParam.get("colorName"))) {
			sql.append(" and tt.COLOR_CODE= '" + queryParam.get("colorName") + "' \n");
		}
		// 内饰
		if (!StringUtils.isNullOrEmpty(queryParam.get("trimName"))) {
			sql.append(" and tt.TRIM_CODE= '" + queryParam.get("trimName") + "' \n");
		}
		// 车辆节点
		if (!StringUtils.isNullOrEmpty(queryParam.get("vehicleNode"))) {
			sql.append(" and tt.NODE_STATUS= '" + queryParam.get("vehicleNode") + "' \n");
		}
		// 订单类型
		if (!StringUtils.isNullOrEmpty(queryParam.get("orderType"))) {
			sql.append(" and tt.order_Type= '" + queryParam.get("orderType") + "' \n");
		}
		// 经销商
		String dealerCode = queryParam.get("dealerCode");
		if (!StringUtils.isNullOrEmpty(queryParam.get("dealerCode"))) {
			if (!dealerCode.equals("")) {
				String s = "";
				if (dealerCode.indexOf(",") > 0) {
					String[] str = dealerCode.split(",");
					for (int i = 0; i < str.length; i++) {
						s += "'" + str[i] + "'";
						if (i < str.length - 1)
							s += ",";
					}
				} else {
					s = "'" + dealerCode + "'";
				}
				sql.append(" and tt.DEALER_CODE in (" + s + ") \n");
				// conditionWhere.append(" AND tt.DEALER_CODE in ("+s+")\n");
			}
			// sql.append(" and t.DEALER_CODE in ? \n");
			// params.add(queryParam.get("dealerCode"));

		}
		// VIN
		if (!StringUtils.isNullOrEmpty(queryParam.get("vin"))) {
			sql.append(" and tt.VIN like ? \n");
			params.add("%" + queryParam.get("vin") + "%");
		}

		// 订单号
		if (!StringUtils.isNullOrEmpty(queryParam.get("orderNo"))) {
			sql.append(" and tt.ORDER_NO like ? \n");
			params.add("%" + queryParam.get("orderNo") + "%");
		}
		// 提报日期 起始
		if (!StringUtils.isNullOrEmpty(queryParam.get("orderDateStart"))) {
			sql.append(" and DATE_FORMAT(tt.ORDER_DATE,'%Y-%m-%d %H:%i:%s')>='" + queryParam.get("orderDateStart")
					+ " 00:00:00'" + " \n");
			// params.add(
			// "date_format('" + queryParam.get("orderDateStart") + "'
			// '23:59:59'," + "'%Y-%m-%d %H:%i:%s'" + ")");

		}
		// 提报日期 结束
		if (!StringUtils.isNullOrEmpty(queryParam.get("orderDateEnd"))) {
			sql.append(" and DATE_FORMAT(tt.ORDER_DATE,'%Y-%m-%d %H:%i:%s') <='" + queryParam.get("orderDateEnd")
					+ " 23:59:59'" + " \n");
			// params.add(DateUtil.parseDefaultDate(queryParam.get("auditDate")));
			// params.add(
			// "date_format('" + queryParam.get("orderDateEnd") + "'
			// '23:59:59'," + "'%Y-%m-%d %H:%i:%s'" + ")");
		}
		System.out.println(sql.toString());
		return sql.toString();
	}

	public List<Map> findLockVins(String vin) {
		String sql = "select *  from TT_RESOURCE_REMARK where IS_LOCK=1 and VIN in(" + vin + ")";
		List<Map> list = OemDAOUtil.findAll(sql, null);
		return list;
	}

	public List<Map> selectZDRR(String vins) {
		StringBuffer sql = new StringBuffer();
		sql.append("select tvo.ORDER_ID,tvo.VIN,tv.NODE_STATUS,tv.VEHICLE_ID \n");
		sql.append("   from TT_VS_ORDER tvo,TM_VEHICLE_DEC tv, TM_DEALER td, (" + getVwMaterialSql() + ") vm\n");
		sql.append("   where tvo.VIN=tv.VIN\n");
		sql.append("     and tvo.DEALER_ID = td.DEALER_ID\n");
		sql.append("     and tvo.MATERAIL_ID = vm.MATERIAL_ID\n");
		sql.append("     and tv.LIFE_CYCLE in(" + OemDictCodeConstants.LIF_CYCLE_01 + ","
				+ OemDictCodeConstants.LIF_CYCLE_02 + ")\n");
		sql.append("     and tv.VIN in(" + vins + ")\n");
		sql.append("     and TV.NODE_STATUS=" + OemDictCodeConstants.VEHICLE_NODE_19 + "\n");
		sql.append("     and tvo.ORDER_STATUS<" + OemDictCodeConstants.ORDER_STATUS_08 + "\n");
		return OemDAOUtil.findAll(sql.toString(), null);
	}

	public List<Map> selectZRL1(String vins) {
		StringBuffer sql = new StringBuffer();
		sql.append(// (case when tvo.ORDER_STATUS=null then 0 else ORDER_STATUS
					// end)ORDER_STATUS
				"select tvo.DEALER_ID,tvo.ORDER_ID,tv.VEHICLE_ID,tv.VIN,COALESCE(tvo.ORDER_STATUS,0) ORDER_STATUS,tv.NODE_STATUS \n");
		sql.append("   from TT_VS_ORDER tvo,TM_VEHICLE_DEC tv, TM_DEALER td, (" + getVwMaterialSql() + ")  vm\n");
		sql.append("   where tvo.VIN=tv.VIN\n");
		sql.append("     and tvo.DEALER_ID = td.DEALER_ID\n");
		sql.append("     and tvo.MATERAIL_ID = vm.MATERIAL_ID\n");
		sql.append("     and tv.LIFE_CYCLE in(" + OemDictCodeConstants.LIF_CYCLE_01 + ","
				+ OemDictCodeConstants.LIF_CYCLE_02 + ")\n");
		sql.append("     and tv.VIN in(" + vins + ")\n");
		sql.append("     and tv.NODE_STATUS =" + OemDictCodeConstants.VEHICLE_NODE_18 + "\n");
		sql.append("     and tvo.ORDER_STATUS<" + OemDictCodeConstants.ORDER_STATUS_08 + "\n");
		sql.append("	 and  not exists (select 1 from TT_VS_ORDER  where VIN=tv.VIN and ORDER_STATUS<20071008)\n");
		sql.append("union\n");
		sql.append(
				"select tvo.DEALER_ID,TVO.ORDER_ID,TV.VEHICLE_ID,TV.VIN,COALESCE(TVO.ORDER_STATUS,0) ORDER_STATUS,TV.NODE_STATUS\n");
		sql.append("   from  TM_VEHICLE_DEC                 	   TV,\n");
		sql.append("  		 (" + getVwMaterialSql() + ")                	   VM,\n");
		sql.append("  		 TT_VS_ORDER                       TVO\n");
		sql.append("    where  TV.MATERIAL_ID = VM.MATERIAL_ID\n");
		sql.append("      and  TV.VIN = TVO.VIN\n");
		sql.append("      and  TVO.ORDER_TYPE=" + OemDictCodeConstants.ORDER_TYPE_03 + "\n");
		sql.append("      and  TV.NODE_STATUS =" + OemDictCodeConstants.VEHICLE_NODE_18 + "\n");
		sql.append("      and  TVO.ORDER_STATUS<" + OemDictCodeConstants.ORDER_STATUS_08 + "\n");
		sql.append("      and  TV.VIN in(" + vins + ")\n");
		sql.append(
				"      and  not exists (select 1 from TT_VS_COMMON_RESOURCE where VEHICLE_ID=TV.VEHICLE_ID and STATUS="
						+ OemDictCodeConstants.COMMON_RESOURCE_STATUS_02 + ")\n");

		return OemDAOUtil.findAll(sql.toString(), null);
	}

	public List<Map> selectForZbil(String vins) {
		StringBuffer sql = new StringBuffer();
		sql.append("select TV.VIN,TV.NODE_STATUS,TV.VEHICLE_ID,TVCR.COMMON_ID\n");
		sql.append("   from  TM_VEHICLE_DEC                 	   TV,\n");
		sql.append("  		 (" + getVwMaterialSql() + ")                 	   VM,\n");
		sql.append("  		 TT_VS_COMMON_RESOURCE      	   TVCR,\n");
		sql.append("   		 TT_VS_COMMON_RESOURCE_DETAIL      TVCRD\n");
		sql.append("    where  TV.MATERIAL_ID = VM.MATERIAL_ID\n");
		sql.append("   	  and  TVCR.VEHICLE_ID = TV.VEHICLE_ID\n");
		sql.append("  	  and  TVCR.COMMON_ID = TVCRD.COMMON_ID\n");
		sql.append("      and  TVCRD.STATUS=" + OemDictCodeConstants.STATUS_ENABLE + "\n");
		sql.append("  	  and  TVCR.STATUS=" + OemDictCodeConstants.COMMON_RESOURCE_STATUS_02 + "\n");
		sql.append("      and  TV.NODE_STATUS =" + OemDictCodeConstants.VEHICLE_NODE_18 + "\n");
		sql.append("      and  not exists (select 1 from TT_VS_ORDER where vin=TV.VIN  and ORDER_STATUS<20071007)\n");
		sql.append("	  and  TV.vin in(" + vins + ")\n");
		return OemDAOUtil.findAll(sql.toString(), null);
	}

}
