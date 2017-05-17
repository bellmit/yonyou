package com.yonyou.dms.vehicle.dao.k4Order;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.javalite.activejdbc.LazyList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.infoeai.eai.action.ctcai.SI25;
import com.yonyou.dms.common.domains.PO.basedata.TmCactAllotPO;
import com.yonyou.dms.common.domains.PO.basedata.TmOrderPayChangePO;
import com.yonyou.dms.common.domains.PO.basedata.TtResourceRemarkPO;
import com.yonyou.dms.common.domains.PO.basedata.TtVsMatchCheckPO;
import com.yonyou.dms.framework.DAO.OemBaseDAO;
import com.yonyou.dms.framework.DAO.OemDAOUtil;
import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.framework.domain.LoginInfoDto;
import com.yonyou.dms.framework.util.bean.ApplicationContextHelper;
import com.yonyou.dms.function.common.OemDictCodeConstants;
import com.yonyou.dms.function.utils.common.CommonUtils;
import com.yonyou.dms.function.utils.common.StringUtils;
import com.yonyou.dms.vehicle.domains.DTO.k4Order.K4OrderDTO;
import com.yonyou.dms.vehicle.domains.PO.k4Order.TmpCactAllotPO;

/**
 *
 * 
 * @author liujiming
 * @date 2017年2月27日
 */

@Repository
public class OrderRepealQueryDao extends OemBaseDAO {
	@Autowired
	SI25 si25;

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

	public List<Map> pass(K4OrderDTO k4OrderDTO) {

		String dealerCode = CommonUtils.checkNull(k4OrderDTO.getDealerCode());
		String isAllot = CommonUtils.checkNull(k4OrderDTO.getIsEc());
		String vins = CommonUtils.checkNull(k4OrderDTO.getVins());
		String zdrrVins = CommonUtils.checkNull(k4OrderDTO.getZdrrVins());
		String arrOrderIds = CommonUtils.checkNull(k4OrderDTO.getIds());
		if (!vins.equals("")) {
			vins = "'" + vins.replace(",", "','") + "'";
		}
		if (!arrOrderIds.equals("")) {
			arrOrderIds = "'" + arrOrderIds.replace(",", "','") + "'";
		}
		if (!zdrrVins.equals("")) {
			zdrrVins = "'" + zdrrVins.replace(",", "','") + "'";
		}
		String lokVins = "";
		if (!vins.equals("") && zdrrVins.equals("")) {
			lokVins = vins;
		} else if (vins.equals("") && !zdrrVins.equals("")) {
			lokVins = zdrrVins;
		} else {
			lokVins = vins + "," + zdrrVins;
		}
		List<Map> falList = new ArrayList<Map>();
		List<Map> reList = new ArrayList<Map>();
		List<Map> lokList = findLockVins(lokVins);
		if (lokList.size() > 0) {
			for (int i = 0; i < lokList.size(); i++) {
				Map<String, String> map = lokList.get(i);
				String vin1 = map.get("VIN").toString();
				map.put("StateCode", "0");
				map.put("ErrorInfo", "资源已锁定");
				falList.add(map);
				// throw new ServiceBizException(vin1 + "资源已锁定");
			}
			return falList;
		}
		String result = "";
		if (zdrrVins.length() > 0) {
			// 调用WSDL文件,只传ZDRR的订单

			try {
				result = si25.doCtcaiMethod(zdrrVins);
			} catch (Exception e) {
			}
		}
		List<Map> tmList = new ArrayList<Map>();
		List<Map> tpList = new ArrayList<Map>();
		List<Map> tcList = new ArrayList<Map>();
		if (!zdrrVins.equals("")) {
			tmList = selectZDRR(zdrrVins);
		}
		if (!vins.equals("")) {
			tpList = selectZRL1(vins);
			tcList = selectForZbil(vins);
		}
		Map<String, String> reMap = new HashMap<String, String>();
		LoginInfoDto loginInfo = ApplicationContextHelper.getBeanByType(LoginInfoDto.class);
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String format = df.format(new Date());
		// ZDRR撤单,中进返回接收结果后预撤单
		if (result.equals("1")) {
			// 确认中进收到以后再修改撤单审核状态
			if (!arrOrderIds.equals("")) {
				OemDAOUtil.execBatchPreparement("update TM_ORDER_PAY_CHANGE set AUDIT_STATUS="
						+ OemDictCodeConstants.CANCEL_ORDER_APPLY_STATUS_04 + ",IS_SUC=3 where ID in(" + arrOrderIds
						+ ")", null);
			}

			reMap.put("ErrorInfo", "1");

			for (int i = 0; i < tmList.size(); i++) {
				Map<String, Object> map = tmList.get(i);

				TtVsMatchCheckPO mcPo = new TtVsMatchCheckPO();
				mcPo.setInteger("ORDER_ID", new Long(map.get("ORDER_ID").toString()));
				mcPo.setInteger("OLD_VEHICLE_ID", new Long(map.get("VEHICLE_ID").toString()));// 原车辆ID
				mcPo.setInteger("CHG_VEHICLE_ID", new Long(map.get("VEHICLE_ID").toString()));
				mcPo.setInteger("UPDATE_BY", loginInfo.getUserId());// 操作人
				mcPo.setTimestamp("UPDATE_DATE", format);// 操作时间
				mcPo.setInteger("CANCEL_TYPE", 1003);// 1001订单取消 1002 订单撤单//
														// 1003撤单中
				mcPo.setString("CANCEL_REASON", "撤单中");
				mcPo.setInteger("OTYPE", 1);// 撤单类型，1.OTD撤单，2.经销商撤单
				mcPo.saveIt();

				TmCactAllotPO tcPO = new TmCactAllotPO();
				tcPO.setString("VIN", map.get("VIN").toString());
				tcPO.setInteger("STATUS", OemDictCodeConstants.STATUS_ENABLE);
				LazyList<TmCactAllotPO> tcaList = TmCactAllotPO.findBySQL(
						"select * from Tm_Cact_Allot where vin=? and STATUS=?", map.get("VIN").toString(),
						OemDictCodeConstants.STATUS_ENABLE);
				if (tcaList.size() == 0) {
					if (isAllot.equals(OemDictCodeConstants.IF_TYPE_YES)) {
						if (zdrrVins.indexOf(",") < 0) {
							tcPO.setString("DEALER_CODE", dealerCode);
						} else {
							TmpCactAllotPO tmpPO = new TmpCactAllotPO();
							LazyList<TmpCactAllotPO> tmcList = TmpCactAllotPO
									.findBySQL("select * from Tm_Cact_Allot where vin=? ", map.get("VIN").toString());
							if (tmcList.size() > 0) {
								tmpPO = tmcList.get(0);
							}
							tcPO.set("DEALER_CODE", tmpPO.get("DEALER_CODE") != null ? tmpPO.get("DEALER_CODE") : "");
						}
					}
					tcPO.setInteger("MATCH_CHECK_ID", mcPo.getId());
					tcPO.setInteger("CREATE_BY", new Long(loginInfo.getUserId()));
					tcPO.setTimestamp("CREATE_DATE", format);
					tcPO.saveIt();

					// 删除已处理的记录
					TmpCactAllotPO tmpPO = new TmpCactAllotPO();
					TmpCactAllotPO.delete("vin=?", tcPO.get("VIN").toString());
				}
				LazyList<TtResourceRemarkPO> list = TtResourceRemarkPO
						.findBySQL("select * from Tt_Resource_Remark where vin=?", tcPO.get("VIN").toString());
				if (list.size() > 0) {
					for (TtResourceRemarkPO valuePO : list) {
						// valuePO.setRemark(new Integer(0));
						valuePO.setInteger("IS_LOCK", new Integer(1));
						valuePO.setInteger("UPDATE_BY", loginInfo.getUserId());
						valuePO.setTimestamp("UPDATE_DATE", format);
						valuePO.saveIt();
					}
				} else {
					TtResourceRemarkPO trrPO = new TtResourceRemarkPO();
					// trrPO.setRemark(new Integer(0));
					trrPO.setString("VIN", tcPO.get("VIN"));
					trrPO.setInteger("IS_LOCK", new Integer(1));
					trrPO.setInteger("UPDATE_BY", loginInfo.getUserId());
					trrPO.setInteger("CREATE_BY", loginInfo.getUserId());
					trrPO.setTimestamp("CREATE_DATE", format);
					trrPO.setTimestamp("UPDATE_DATE", format);
					trrPO.saveIt();
				}
				// 如果在撤单申请中存在撤单审核中的数据，则修改为撤单中状态
				TmOrderPayChangePO topc2 = TmOrderPayChangePO.findFirst(
						"select * from tm_order_pay_change where vin=? and AUDIT_STATUS=?", tcPO.get("VIN").toString(),
						OemDictCodeConstants.CANCEL_ORDER_APPLY_STATUS_01);
				topc2.setInteger("AUDIT_STATUS", OemDictCodeConstants.CANCEL_ORDER_APPLY_STATUS_04);
				topc2.setInteger("IS_SUC", 3);
				topc2.saveIt();
			}
		} else {
			reMap.put("ErrorInfo", "0");
		}
		reList.add(reMap);

		// ZRL1撤单 撤回ZBIL---指派订单
		if (tpList.size() > 0) {
			for (int i = 0; i < tpList.size(); i++) {
				String orderId = tpList.get(i).get("ORDER_ID").toString();
				String vin = tpList.get(i).get("VIN").toString();
				String dealerId = tpList.get(i).get("DEALER_ID").toString();
				// 更改订单表为已撤单
				OemDAOUtil
						.execBatchPreparement(
								"update TT_VS_ORDER set COMMONALITY_ID=0,ORDER_STATUS="
										+ OemDictCodeConstants.ORDER_STATUS_09 + " where ORDER_ID=" + orderId + "",
								new ArrayList<Object>());

				// 更新车辆表状态为ZBIL
				OemDAOUtil.execBatchPreparement("update TM_VEHICLE_DEC set NODE_STATUS="
						+ OemDictCodeConstants.VEHICLE_NODE_08 + ",DEALER_ID=0 where VIN='" + vin + "'",
						new ArrayList<Object>());
				// 记录日志
				OemDAOUtil.execBatchPreparement(
						"insert into TT_VS_MATCH_CHECK(ORDER_ID,CHG_VEHICLE_ID,UPDATE_BY,UPDATE_DATE,CANCEL_TYPE,CANCEL_REASON)\n"
								+ "select \n" + orderId + ",\n" + "tv.VEHICLE_ID,\n" + loginInfo.getUserId() + ",'"
								+ format + "'," + "1001," + "'订单取消'" + " from TM_VEHICLE_DEC tv " + " where tv.vin='"
								+ vin + "'",
						new ArrayList<Object>());

				// 记录详细车籍
				OemDAOUtil
						.execBatchPreparement(
								"insert into TT_VS_VHCL_CHNG(VEHICLE_ID,CHANGE_CODE,CHANGE_DATE,CHANGE_DESC,CREATE_BY,CREATE_DATE,RESOURCE_TYPE,RESOURCE_ID)\n"
										+ "select \n" + "tv.VEHICLE_ID,\n" + "20211019,'" + format + "'," + "'订单取消',"
										+ loginInfo.getUserId() + ",'" + format + "'," + "10191002," + dealerId
										+ " from TM_VEHICLE_DEC tv " + " where tv.vin='" + vin + "'",
								new ArrayList<Object>());
			}
		}
		// ZRL1撤单 撤回ZBIL---期货、现货的订单
		if (tcList.size() > 0) {
			for (int i = 0; i < tcList.size(); i++) {
				Map<String, Object> map = tcList.get(i);
				String vin = map.get("VIN").toString();
				String commonId = map.get("COMMON_ID").toString();
				// 设置公共已取消
				OemDAOUtil.execBatchPreparement("update TT_VS_COMMON_RESOURCE set UPDATE_BY=" + loginInfo.getUserId()
						+ ",UPDATE_DATE='" + format + "',STATUS=" + OemDictCodeConstants.COMMON_RESOURCE_STATUS_03
						+ " where COMMON_ID=" + commonId, new ArrayList<Object>());

				// 公共资源明细设为无效
				OemDAOUtil.execBatchPreparement("update TT_VS_COMMON_RESOURCE_DETAIL set STATUS="
						+ OemDictCodeConstants.STATUS_DISABLE + " where COMMON_ID=" + commonId,
						new ArrayList<Object>());

				// 更新车辆表状态为ZBIL
				OemDAOUtil.execBatchPreparement("update TM_VEHICLE_DEC set NODE_STATUS="
						+ OemDictCodeConstants.VEHICLE_NODE_08 + ",DEALER_ID=0 where  VIN='" + vin + "'",
						new ArrayList<Object>());

				// 记录日志
				OemDAOUtil.execBatchPreparement(
						"insert into TT_VS_MATCH_CHECK(CHG_VEHICLE_ID,UPDATE_BY,UPDATE_DATE,CANCEL_TYPE,CANCEL_REASON)\n"
								+ "select \n" + "tv.VEHICLE_ID,\n" + loginInfo.getUserId() + ",'" + format + "',"
								+ "1001," + "'订单取消'" + " from TM_VEHICLE_DEC tv " + " where tv.vin='" + vin + "'",
						new ArrayList<Object>());

				// 记录详细车籍
				OemDAOUtil.execBatchPreparement(
						"insert into TT_VS_VHCL_CHNG(VEHICLE_ID,CHANGE_CODE,CHANGE_DATE,CHANGE_DESC,CREATE_BY,CREATE_DATE)\n"
								+ "select \n" + "tv.VEHICLE_ID,\n" + "20211019,'" + format + "'," + "'订单取消',"
								+ loginInfo.getUserId() + ",'" + format + "' from TM_VEHICLE_DEC tv "
								+ " where tv.vin='" + vin + "'",
						new ArrayList<Object>());
			}
		}
		if (arrOrderIds.equals("")) {
			return falList;
		} else {
			return reList;
		}
	}

}
