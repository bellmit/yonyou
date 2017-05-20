package com.yonyou.dms.vehicle.dao.oeminvty;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;

import com.yonyou.dms.common.Util.OemDictCodeConstantsUtils;
import com.yonyou.dms.common.domains.PO.basedata.TmOrgPO;
import com.yonyou.dms.framework.DAO.OemBaseDAO;
import com.yonyou.dms.framework.DAO.OemDAOUtil;
import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.framework.domain.LoginInfoDto;
import com.yonyou.dms.framework.domains.PO.baseData.OemDictPO;
import com.yonyou.dms.framework.util.bean.ApplicationContextHelper;
import com.yonyou.dms.function.common.OemDictCodeConstants;
import com.yonyou.dms.function.utils.common.CommonUtils;

@Repository
@SuppressWarnings("all")
public class OemVehicleDao extends OemBaseDAO {
	// 车辆综合查询
	public PageInfoDto getVehicleQueryList(Map<String, String> queryParam) {

		List<Object> params = new ArrayList<Object>();
		String sql = getQuerySql12(queryParam, params);
		System.out.println(sql);
		PageInfoDto pageInfoDto = OemDAOUtil.pageQuery(sql, params);
		return pageInfoDto;
	}

	private String getQuerySql12(Map<String, String> queryParam, List<Object> params) {

		LoginInfoDto loginInfo = ApplicationContextHelper.getBeanByType(LoginInfoDto.class);
		String orgid = String.valueOf(loginInfo.getOrgId());
		Map dealerScopeMap = this.getDealerScope(orgid);
		int dealerScope = (int) dealerScopeMap.get("ORG_LEVEL");
		List<Map> dCodeList = new ArrayList();
		if (dealerScope == 2) {// 大区
			dCodeList = this.getDealerCode(orgid);
		} else if (dealerScope == 3) {// 小区
			dCodeList = getDealerCode2(orgid);
			List<TmOrgPO> list = TmOrgPO.find("Org_Id=?", new Long(orgid));
			if (list.size() > 0) {
				for (TmOrgPO toPO : list) {

					orgid += "," + toPO.get("Parent_Org_Id");

				}
			}
		}
		if (!orgid.equals(OemDictCodeConstants.OEM_ACTIVITIES)) {
			orgid += "," + OemDictCodeConstants.OEM_ACTIVITIES;
		}
		String dealerIds = "-1";

		for (int i = 0; i < dCodeList.size(); i++) {
			Map dealerIdMap = (Map) dCodeList.get(i);
			String dealerId = (String) dealerIdMap.get("DEALER_CODE");
			dealerIds = dealerIds + "," + dealerId;
		}
		String dealerCodes = queryParam.get("dealerCode");
		String dcs = new String();
		if (StringUtils.isNoneBlank(dealerCodes) && StringUtils.isNoneBlank(dealerCodes)) { // 经销商
			String s = new String();
			if (dealerCodes.indexOf(",") > 0) {
				String[] str = dealerCodes.split(",");
				for (int i = 0; i < str.length; i++) {
					s += "'" + str[i] + "'";
					if (i < str.length - 1)
						s += ",";
				}
			} else {
				s = "'" + dealerCodes + "'";
			}

			List<Long> tdList = this.findDealer(s);
			for (int i = 0; i < tdList.size(); i++) {
				dcs += tdList.get(i);
				if (i < tdList.size() - 1) {
					dcs += ",";
				}
			}
		}
		StringBuffer sql = new StringBuffer();
		sql.append(// (CASE WHEN ALLOTRESOURCE.IS_SUPPORT =0 THEN '任务外' ELSE
					// '任务内' END) AS IS_SUPPORT
					// COALESCE(ALLOTRESOURCE.IS_SUPPORT,0) IS_SUPPORT
				"   select distinct t12.*,(CASE  WHEN ALLOTRESOURCE.IS_SUPPORT =0  THEN '任务外'  ELSE '任务内' END) AS IS_SUPPORT FROM   ( select t10.*,t11.TC_VEHICLE_ID,t11.VEHICLE_ID as VEHICLE_ID_B, t11.PAYMENG_DATE, t11.START_SHIPMENT_DATE, t11.CLORDER_SCANNING_NO, t11.CLORDER_SCANNING_URL,t11.SCORDER_SCANNING_NO, t11.SCORDER_SCANNING_URL,(case when t10.DEALER_NAME!='' then t10.DEALER_NAME when t10.BIG_AREA!='' then t10.BIG_AREA else '全国' end) RESOURCE,scopeData.RESOURCE_TYPE\n");
		sql.append(
				"  from (select t9.*,date_format(TVN.CREATE_DATE,'%Y-%m-%d %H:%s:%i') NVDR_DATE,date_format(TVN.update_DATE,'%Y-%m-%d %H:%s:%i') UPDATE_DATE,tvn.dealer_shortname  RETAIL_DEALER_NAME \n");
		sql.append("    from (select t8.*,date_format(tvi.ARRIVE_DATE,'%y-%m-%d %H:%s:%i') ARRIVE_DATE\n");
		sql.append(
				" from (select t7.*,trr.REMARK,trr.SPECIAL_REMARK,(case when trr.REMARK in(11211010,11211016) then trr.OTHER_REMARK end) OTHER_REMARK,(case when trr.IS_LOCK=1 then '是' else '否' end) IS_LOCK\n");
		sql.append(
				" from (select COALESCE(t6.ORDER_TYPE,0) ORDER_TYPE,t6.ORG_ID,(case when t6.DEALER_ID>0 then TOR.ORG_DESC when t6.ORG_ID>0 then TOR.ORG_DESC else t6.ORG_DESC2 end) BIG_AREA,(case when t6.DEALER_ID>0 then t6.ORG_DESC3 end) SMALL_AREA,\n");
		sql.append(
				" 		 t6.MODEL_YEAR,t6.BRAND_ID,t6.BRAND_CODE,t6.SERIES_ID,t6.SERIES_CODE,t6.SERIES_NAME,t6.GROUP_ID,t6.GROUP_NAME,t6.DEALER_CODE,t6.DEALER_NAME,t6.VIN,t6.NODE_STATUS,t6.VEHICLE_ID,t6.ALLOT,t6.STORE_TYPE,t6.NODE_DATE,t6.WHOLESALE_PRICE,t6.RETAIL_PRICE,\n");
		sql.append(
				" 		 t6.COLOR_CODE,t6.COLOR_NAME,t6.TRIM_CODE,t6.TRIM_NAME,t6.FACTORY_OPTIONS,t6.VEHICLE_USAGE,t6.PAYMENT_TYPE,date_format(t6.DEAL_ORDER_AFFIRM_DATE,'%Y-%m-%d %H:%s:%i') DEAL_ORDER_AFFIRM_DATE,t6.ORDER_STATUS,t6.VPC_PORT,date_format(t6.ARRIVE_PORT_DATE,'%Y-%m-%d') ARRIVE_PORT_DATE, t6.is_Advance, t6.MODEL_CODE\n");
		sql.append(" 		from (select t4.DEALER_ID,\n");
		sql.append(
				" 		(case when t4.ORDER_TYPE>0 then t4.ORDER_TYPE else (case when t5.TYPE=20811001 then 20831002 when t5.TYPE=20811002 then 20831001 when t5.TYPE=20811003 then 20831004 end) end) ORDER_TYPE,\n");
		sql.append(
				"(case when t4.ORG_ID2>0 then t4.ORG_ID2 when t5.RESOURCE_SCOPE>0 then t5.RESOURCE_SCOPE else t4.ORG_ID1 end) ORG_ID,\n");
		sql.append(
				" t4.VEHICLE_ID,t4.VIN,t4.BRAND_ID,t4.BRAND_CODE,t4.SERIES_ID,t4.SERIES_CODE,t4.SERIES_NAME,t4.GROUP_ID,t4.GROUP_NAME,t4.MODEL_YEAR,t4.COLOR_CODE,t4.COLOR_NAME,t4.TRIM_CODE,t4.TRIM_NAME,t4.FACTORY_OPTIONS,t4.NODE_STATUS,t4.LIFE_CYCLE,\n");
		sql.append(
				"  t4.VEHICLE_USAGE,t4.DEALER_CODE,t4.DEALER_NAME,t4.PAYMENT_TYPE,t4.DEAL_ORDER_AFFIRM_DATE,t4.ALLOT,t4.STORE_TYPE,t4.NODE_DATE,t4.WHOLESALE_PRICE,t4.RETAIL_PRICE,t4.ORDER_STATUS,t4.VPC_PORT,t4.ARRIVE_PORT_DATE, t4.is_Advance, \n");
		sql.append(
				" (case when t5.RESOURCE_SCOPE>0 then t5.ORG_DESC else t4.ORG_DESC2 end) ORG_DESC2,t4.ORG_DESC3,t4.MODEL_CODE\n");
		sql.append(" from (select t2.*,t3.ORG_ID2,t3.DEALER_CODE,t3.DEALER_NAME,t3.ORG_DESC2,t3.ORG_DESC3\n");
		sql.append(
				" from (select (case when TVO.DEALER_ID>0 then TVO.DEALER_ID else t1.DEALER_ID2 end) DEALER_ID,TVO.ORDER_TYPE,TVO.PAYMENT_TYPE,TVO.DEAL_ORDER_AFFIRM_DATE,TVO.ORDER_STATUS,t1.* \n");
		sql.append(
				" from (select TV.ORG_ID ORG_ID1,TV.DEALER_ID DEALER_ID2,TV.VEHICLE_ID, TV.VIN,VM.BRAND_ID,VM.BRAND_CODE,VM.SERIES_ID,VM.SERIES_CODE,VM.SERIES_NAME,VM.GROUP_ID,VM.GROUP_NAME,VM.MODEL_YEAR,\n");
		sql.append(
				"  VM.COLOR_CODE,VM.COLOR_NAME,VM.TRIM_CODE,VM.TRIM_NAME,VM.FACTORY_OPTIONS,VM.MODEL_CODE,TV.NODE_STATUS,TV.LIFE_CYCLE,TV.VEHICLE_USAGE,TV.VPC_PORT,TV.ARRIVE_PORT_DATE,\n");
		sql.append(
				" ( case when COALESCE((select 1 from (select distinct va.vin from tt_vs_vehicle_advance va) va where va.vin = tv.vin), 0)=0 then '否' else '是' end) as is_Advance, \n");
		sql.append(
				"  (case when TV.NODE_STATUS in(11511009,11511010,11511012,11511015,11511016,11511018,11511019)  then 1 else 2 end) ALLOT,\n");
		sql.append("(case when TV.LIFE_CYCLE = 11521001 then 1  \n");
		sql.append(
				"   when TV.LIFE_CYCLE = 11521002 and TV.NODE_STATUS in (11511004,11511005,11511006,11511014,11511013,11511007,11511020) then 2 \n");
		sql.append(
				"  when TV.LIFE_CYCLE = 11521002 and TV.NODE_STATUS in (11511008,11511010,11511011,11511009,11511018,11511019)  then 3  \n");
		sql.append("  when (TV.LIFE_CYCLE = 11521003 or TV.LIFE_CYCLE = 11521004)  then 4 \n");
		sql.append("  when (TV.LIFE_CYCLE = 11521005)  then 5  \n");
		sql.append(
				" 	 end) STORE_TYPE,date_format(TV.NODE_DATE,'%Y-%m-%d %H:%s:%i') NODE_DATE,TV.WHOLESALE_PRICE,TV.RETAIL_PRICE \n");
		sql.append(" from TM_VEHICLE_dec         	  TV,\n");
		sql.append(" (" + getVwMaterialSql() + ")         	  VM,\n");
		sql.append("TM_VHCL_MATERIAL_GROUP   TVMG\n");
		sql.append("where TV.MATERIAL_ID = VM.MATERIAL_ID\n");
		sql.append("and TVMG.GROUP_ID=VM.SERIES_ID\n");
		if (StringUtils.isNotBlank(queryParam.get("vehicleUse"))) {
			sql.append(" 	 and tv.vehicle_usage='" + queryParam.get("vehicleUse") + "'\n");
		}
		String vin = queryParam.get("vin");
		if (StringUtils.isNotBlank(vin)) {
			vin = queryParam.get("vin").replaceAll("\\^", "\n");
			vin = queryParam.get("vin").replaceAll("\\,", "\n");
			sql.append(getVinsAuto(vin, "TV"));
		}
		String nodeStatus2 = queryParam.get("nodeStatus2");
		String nodeStatus1 = queryParam.get("nodeStatus1");
		if (StringUtils.isNotBlank(nodeStatus2) || StringUtils.isNotBlank(nodeStatus1)) {
			if (StringUtils.isNotBlank(nodeStatus1)) {
				sql.append("and tv.NODE_STATUS >=(" + nodeStatus1 + ")\n");
			}
			if (StringUtils.isNotBlank(nodeStatus2)) {
				sql.append("and tv.NODE_STATUS <=(" + nodeStatus2 + ")\n");
			}
		} else {
			OemDictPO tc1 = new OemDictPO();
			tc1 = (OemDictPO) tc1.findById(nodeStatus1);

			OemDictPO tc2 = new OemDictPO();
			tc2 = (OemDictPO) tc1.findById(nodeStatus2);
			if (tc1 != null && tc2 != null) {

				String sl = "select CODE_ID from TC_CODE_DEC where TYPE=" + OemDictCodeConstants.VEHICLE_NODE
						+ " and num>=" + tc1.getBigDecimal("NUM") + " and num<=" + tc2.getBigDecimal("NUM");
				List<Map<String, Object>> l = (List<Map<String, Object>>) OemDAOUtil.findFirst(sl, null);
				String codeIds = "";
				if (l.size() > 0) {
					for (int i = 0; i < l.size(); i++) {
						codeIds += l.get(i).get("CODE_ID").toString();
						if (i < l.size() - 1)
							codeIds += ",";
					}
				}
				if (StringUtils.isNotBlank(codeIds)) {
					sql.append(" and tv.NODE_STATUS in(" + codeIds + ")\n");
				}
			}
		}
		String factoryOption = queryParam.get("factoryOption");
		if (StringUtils.isNotBlank(factoryOption) && StringUtils.isNotEmpty(factoryOption)) {
			sql.append("   and tv.FACTORY_OPTIONS like '%" + factoryOption.toUpperCase() + "%'\n");
		}
		sql.append("and TVMG.GROUP_TYPE=" + OemDictCodeConstants.GROUP_TYPE_IMPORT + ") t1 ");
		sql.append("left join TT_VS_ORDER TVO on t1.VIN=TVO.VIN and TVO.ORDER_STATUS<20071008 and TVO.IS_DEL <>1 \n");
		sql.append("where 1=1\n");
		String orderStatus = queryParam.get("orderStatus");
		if (StringUtils.isNotBlank(orderStatus) && orderStatus.indexOf(",") < 0) {
			sql.append("and TVO.ORDER_STATUS=" + orderStatus + "\n");
		}
		String nodeDate1 = queryParam.get("nodeDate1");
		String nodeDate2 = queryParam.get("nodeDate2");
		if (StringUtils.isNotBlank(nodeDate1) && StringUtils.isNotEmpty(nodeDate1)
				|| StringUtils.isNotBlank(nodeDate2) && StringUtils.isNotEmpty(nodeDate2)) {
			sql.append("and T1.NODE_DATE>='" + nodeDate1 + "'\n");
			sql.append("and T1.NODE_DATE<='" + nodeDate2 + "'\n");
		}
		String brandId = queryParam.get("brandId");
		if (StringUtils.isNoneBlank(brandId) && StringUtils.isNoneEmpty(brandId)) {
			sql.append("and T1.BRAND_ID='" + brandId + "'\n");
		}
		String seriesName = queryParam.get("seriesName");
		if (StringUtils.isNoneBlank(seriesName) && StringUtils.isNoneEmpty(seriesName)) {
			sql.append("and T1.SERIES_ID='" + seriesName + "'\n");
		}
		if (StringUtils.isNoneBlank(queryParam.get("storeType"))
				&& StringUtils.isNoneEmpty(queryParam.get("storeType"))) {

			String storeType1 = CommonUtils.checkNull(queryParam.get("storeType"));
			String storeType = OemDictCodeConstantsUtils.getStoreType(Integer.valueOf(storeType1));
			if (StringUtils.isNoneBlank(storeType) && StringUtils.isNoneEmpty(storeType)) {
				sql.append("and T1.ALLOT=" + storeType + "\n");
			}
		}

		String lifcycle = queryParam.get("lifcycle");
		if (StringUtils.isNoneBlank(lifcycle) && StringUtils.isNoneEmpty(lifcycle)) {
			String lifcycle2 = OemDictCodeConstantsUtils.Lifcycle(Integer.parseInt(lifcycle));
			sql.append("and T1.STORE_TYPE=" + lifcycle2 + "\n");
		}
		// 车款
		String groupName = queryParam.get("groupName");
		if (StringUtils.isNoneBlank(groupName) && StringUtils.isNoneEmpty(groupName)) {
			sql.append("and T1.GROUP_ID=" + groupName + "\n");
		}
		String color = queryParam.get("colorName");
		if (StringUtils.isNoneBlank(color) && StringUtils.isNoneEmpty(color)) {
			sql.append("and T1.COLOR_CODE='" + color + "'\n");
		}
		String modelYear = queryParam.get("modelYear");
		if (StringUtils.isNoneBlank(modelYear) && StringUtils.isNoneEmpty(modelYear)) {
			sql.append("and T1.MODEL_YEAR='" + modelYear + "'\n");
		}
		String trimName = queryParam.get("trimName");
		if (StringUtils.isNoneBlank(trimName) && StringUtils.isNoneEmpty(trimName)) {
			sql.append("and T1.TRIM_CODE='" + trimName + "'\n");
		}
		sql.append(") t2\n");
		sql.append(
				"  left join (select TOR2.ORG_ID ORG_ID2,TD.DEALER_ID,TD.DEALER_CODE,TD.DEALER_SHORTNAME DEALER_NAME,TOR2.ORG_DESC ORG_DESC2,TOR3.ORG_DESC ORG_DESC3\n");
		sql.append("  from TM_ORG     TOR2,\n");
		sql.append(" TM_ORG                     TOR3,\n");
		sql.append("  TM_DEALER_ORG_RELATION     TDOR,\n");
		sql.append("    TM_DEALER                  TD\n");
		sql.append("   where TOR3.PARENT_ORG_ID = TOR2.ORG_ID\n");
		sql.append(" and TOR3.ORG_ID = TDOR.ORG_ID\n");
		sql.append("  and TDOR.DEALER_ID = TD.DEALER_ID\n");
		sql.append(" and TOR3.ORG_LEVEL = 3\n");
		sql.append(" and TOR2.ORG_LEVEL = 2) t3\n");
		sql.append("  on t2.DEALER_ID = t3.DEALER_ID");
		System.out.println(dcs);
		if (StringUtils.isNotBlank(dcs)) {
			sql.append(" where t3.DEALER_ID in(" + dcs + ")");
		}
		sql.append(") t4\n");
		sql.append(
				"left join (select TVCR.VEHICLE_ID,TVCR.COMMON_ID,TVCR.RESOURCE_SCOPE,TVCR.TYPE,(case when TVCR.RESOURCE_SCOPE="
						+ OemDictCodeConstants.OEM_ACTIVITIES + " then '' else TOR.ORG_DESC end) ORG_DESC\n");
		sql.append(" from TT_VS_COMMON_RESOURCE            TVCR,\n");
		sql.append(" TT_VS_COMMON_RESOURCE_DETAIL     TVCRD,\n");
		sql.append(" TM_ORG                           TOR\n");
		sql.append("  where TVCR.COMMON_ID=TVCRD.COMMON_ID\n");
		sql.append("  and TVCR.RESOURCE_SCOPE=TOR.ORG_ID\n");
		sql.append("  and TVCRD.STATUS=" + OemDictCodeConstants.STATUS_ENABLE + "\n");
		sql.append("  and TVCR.STATUS=" + OemDictCodeConstants.COMMON_RESOURCE_STATUS_02 + ") t5\n");
		sql.append("  on t4.VEHICLE_ID=t5.VEHICLE_ID) t6\n");
		sql.append(" left join TM_ORG  TOR on t6.ORG_ID = TOR.ORG_ID and TOR.ORG_LEVEL=2) t7\n");
		sql.append(" left join TT_RESOURCE_REMARK TRR on t7.VIN=TRR.VIN) t8\n");
		sql.append(
				" left join (select * from TT_VS_INSPECTION TV where tv.IS_DEL=0 and TV.CREATE_DATE in(select max(TI.CREATE_DATE) from TT_VS_INSPECTION TI where TI.VEHICLE_ID=TV.VEHICLE_ID)) TVI on t8.VEHICLE_ID=TVI.VEHICLE_ID) t9\n");
		sql.append(
				" left join (select tvn.create_date,tvn.update_date,tvn.vin,td.dealer_shortname from tt_vs_nvdr tvn,tm_dealer  td  where tvn.IS_DEL=0 and td.dealer_id=tvn.BUSINESS_ID ) TVN on t9.VIN=TVN.VIN) t10\n");
		sql.append(
				"  left join (select TCV.TC_VEHICLE_ID,TCV.VEHICLE_ID,date_format(TCV.PAYMENG_DATE,'%Y-%m-%d %H:%s:%i') PAYMENG_DATE,date_format(TCV.START_SHIPMENT_DATE,'%Y-%m-%d %H:%s:%i') START_SHIPMENT_DATE,\n");
		sql.append(
				" TCV.CLORDER_SCANNING_NO,concat(TCV.CLORDER_SCANNING_URL,'') CLORDER_SCANNING_URL,TCV.SCORDER_SCANNING_NO,concat(TCV.SCORDER_SCANNING_URL,'') SCORDER_SCANNING_URL\n");
		sql.append("from TM_CTCAI_VEHICLE    TCV WHERE TCV.IS_DEL <>1 ) t11\n");
		sql.append("  on t10.VEHICLE_ID=t11.VEHICLE_ID \n");
		// 添加资源类型
		sql.append("  left join (\n");
		sql.append("  	SELECT VEHICLE_ID, RESOURCE_TYPE, MAX(CREATE_DATE)	\n");
		sql.append("FROM TT_VS_COMMON_RESOURCE	\n");
		sql.append(" WHERE TYPE=" + OemDictCodeConstants.COMMON_RESOURCE_TYPE_02 + "  AND STATUS != "
				+ OemDictCodeConstants.COMMON_RESOURCE_STATUS_03 + "	\n");
		sql.append("  GROUP BY VEHICLE_ID, RESOURCE_TYPE	\n");
		sql.append("  ) scopeData on scopeData.VEHICLE_ID = t10.VEHICLE_ID	\n");
		sql.append("  where 1=1\n");
		String orderType = queryParam.get("orderType");

		if (StringUtils.isNotBlank(orderType)) {
			String s = "";
			if (orderType.indexOf(",") > 0) {
				s = "0,";
			}
			sql.append("and t10.ORDER_TYPE in(" + s + "" + orderType + ")\n");
		}
		if (dealerScope == 2 || dealerScope == 3) {
			sql.append("      and (T10.DEALER_CODE in(" + dealerIds + ") or t10.ORG_ID in(" + orgid + "))\n");
		}
		sql.append("   order by t10.DEALER_CODE,t10.NODE_DATE desc \n");
		sql.append(" ) t12 left join TM_ALLOT_SUPPORT ALLOTRESOURCE ON ALLOTRESOURCE.VIN=T12.VIN");
		String l = "select * from (" + sql.toString() + ")MM";
		return l;

	}

	private List<Map> getDealerCode2(String orgid) {
		String sql = "select TMD.DEALER_CODE" + "	FROM TM_ORG TMO,TM_DEALER_ORG_RELATION TDOR,TM_DEALER TMD "
				+ "	WHERE 1=1 " + "	AND TDOR.ORG_ID = TMO.ORG_ID" + "	AND TMD.DEALER_ID = TDOR.DEALER_ID"
				+ "   AND TMD.STATUS = " + OemDictCodeConstants.STATUS_ENABLE + "   AND TMD.DEALER_TYPE=10771001"
				+ "	AND TMO.ORG_ID = " + orgid;
		return OemDAOUtil.findAll(sql, null);
	}

	private List<Map> getDealerCode(String orgid) {
		String sql = "select TMD.DEALER_CODE"
				+ "	FROM TM_ORG TMO,TM_ORG TMOR,TM_DEALER_ORG_RELATION TDOR,TM_DEALER TMD " + "	WHERE 1=1 "
				+ "	AND TMOR.PARENT_ORG_ID = TMO.ORG_ID" + "	AND TDOR.ORG_ID = TMOR.ORG_ID"
				+ "	AND TMD.DEALER_ID = TDOR.DEALER_ID" + "   AND TMD.STATUS = " + OemDictCodeConstants.STATUS_ENABLE
				+ "   AND TMD.DEALER_TYPE=10771001" + "	AND TMO.ORG_ID = " + orgid
				+ "	order by TMO.ORG_ID,TMOR.ORG_ID";
		return OemDAOUtil.findAll(sql, null);
	}

	/**
	 * SQL组装
	 * 
	 * @param queryParam
	 * @param params
	 * @return
	 */

	private String getQuerySql(Map<String, String> queryParam, List<Object> params) {
		StringBuffer sql = new StringBuffer("\n");
		sql.append("SELECT D.DEALER_CODE, -- 经销商代码  \n");
		sql.append("       D.DEALER_SHORTNAME, -- 经销商简称 \n");
		sql.append("       M.BRAND_NAME, -- 品牌 \n");
		sql.append("       M.SERIES_NAME, -- 车系  \n");
		sql.append("       M.MODEL_CODE, -- CPOS \n");
		sql.append("       M.MODEL_YEAR, -- 年款 \n");
		sql.append("       M.GROUP_NAME, -- 车款 \n");
		sql.append("       M.COLOR_NAME, -- 颜色 \n");
		sql.append("       M.TRIM_NAME, -- 内饰 \n");
		sql.append("       V.VIN, -- 车架号 VIN \n");
		sql.append("       V.LOCK_STATUS, -- 是否锁定  无 \n");
		sql.append("       V.LOCK_REASON, -- 锁定原因  无 \n");
		sql.append("       V.ENGINE_NO, -- 发动机号 \n");
		sql.append("       ' ' AS KEY_NO, -- 钥匙号 \n");
		sql.append("       V.QUALIFIED_NO, -- 合格证号 \n");
		sql.append("       V.NODE_STATUS, -- 车辆节点 \n");
		sql.append("(case when (COALESCE(CM.CODE_DESC, '0'))='0' THEN '' ELSE CM.CODE_DESC END)AS VEHICLE_USAGE,\n");
		sql.append("       date_format(V.OFFLINE_DATE, '%y-%m-%d') AS OFFLINE_DATE, -- 下线日期 \n");
		sql.append("       V.VEHICLE_ID AS VEHICLE_ID, -- 车辆ID \n");
		sql.append("       V.WHOLESALE_PRICE, -- 车辆批发价格  \n");
		sql.append("       V.RETAIL_PRICE, -- MSRP 价格(车辆零售价格) \n");
		sql.append("       date_format(VNH.ZASS_DATE, '%Y-%m-%d') AS ZASS_DATE, -- 总装上线日期 \n");
		sql.append("       date_format(VNH.ZVGO_DATE, '%Y-%m-%d') AS ZVGO_DATE, -- 总装下线日期  \n");
		sql.append("       date_format(VNH.ZVQP_DATE, '%Y-%m-%d') AS ZVQP_DATE, -- 质检完成日期 \n");
		sql.append("       date_format(VNH.ZVGR_DATE, '%Y-%m-%d') AS ZVGR_DATE, -- 入工厂仓库日期(工厂在库) \n");
		sql.append("       date_format(VNH.ZBB1_DATE, '%Y-%m-%d') AS ZBBU_DATE, -- 工厂质量扣留日期 \n");
		sql.append("       date_format(VNH.ZINV_DATE, '%Y-%m-%d') AS ZINV_DATE, -- 内部结算完成日期(内部结算成功)  \n");
		sql.append("       date_format(VNH.ZMBL_DATE, '%Y-%m-%d') AS ZMBL_DATE, -- 销售公司质量扣留日期 \n");
		sql.append("       date_format(VNH.YSOR_DATE, '%Y-%m-%d') AS YSOR_DATE, -- 付款日期(执行扣款) \n");
		sql.append("       date_format(VNH.ZVHC_DATE, '%Y-%m-%d') AS ZVHC_DATE, -- 发运日期(已发运) \n");
		sql.append("       date_format(VNH.ZPD2_DATE, '%Y-%m-%d') AS ZPD2_DATE, -- 物流到店日期  \n");
		sql.append("       date_format(VNH.ZPOD_DATE, '%Y-%m-%d') AS ZPOD_DATE, -- 经销商收车日期 \n");
		sql.append("       date_format(N.CREATE_DATE, '%Y-%m-%d') AS RETAIL_DATE, -- 零售交车日期 \n");
		sql.append("       date_format(N.UPDATE_DATE, '%Y-%m-%d') AS RETAIL_CHECK_DATE, -- 零售审核日期 \n");
		sql.append(
				"              CASE V.PREDICT_STORAGE_DATE WHEN NULL THEN NULL WHEN '' THEN NULL WHEN '00000000' THEN NULL \n");
		sql.append("     		  ELSE date_format(date_format(V.PREDICT_STORAGE_DATE, '%y-%m-%d'), '%y-%m-%d') \n");
		sql.append("              END AS PREDICT_STORAGE_DATE,-- 预计入库日期   \n");
		sql.append("       ' ' AS SCRAP_DATE -- 报废日期 \n");
		sql.append("  FROM tm_vehicle_dec V \n");
		sql.append(" INNER JOIN (" + getVwMaterialSql() + ") M ON M.MATERIAL_ID = V.MATERIAL_ID \n");
		sql.append("  LEFT JOIN TT_VEHICLE_NODE_HISTORY VNH ON V.VEHICLE_ID = VNH.VEHICLE_ID \n");
		sql.append("  LEFT JOIN TM_DEALER D ON D.DEALER_ID = V.DEALER_ID \n");
		sql.append("  LEFT JOIN TT_VS_NVDR N ON N.VIN = V.VIN \n");
		sql.append("  LEFT JOIN TC_CODE_K4_MAPPING CM ON CM.CODE_ID = V.VEHICLE_USAGE  \n");
		sql.append(" WHERE M.GROUP_TYPE = '90081001' \n");
		String brandId = queryParam.get("brandName");
		if (StringUtils.isNoneBlank(brandId) && StringUtils.isNoneEmpty(brandId)) {
			sql.append("and M.BRAND_ID='" + brandId + "'\n");
		}
		String seriesName = queryParam.get("seriesName");
		if (StringUtils.isNoneBlank(seriesName) && StringUtils.isNoneEmpty(seriesName)) {
			sql.append("and M.SERIES_ID='" + seriesName + "'\n");
		}
		// 车款
		String groupName = queryParam.get("groupName");
		if (StringUtils.isNoneBlank(groupName) && StringUtils.isNoneEmpty(groupName)) {
			sql.append("and M.GROUP_ID=" + groupName + "\n");
		}
		String color = queryParam.get("colorName");
		if (StringUtils.isNoneBlank(color) && StringUtils.isNoneEmpty(color)) {
			sql.append("and M.COLOR_CODE='" + color + "'\n");
		}
		String modelYear = queryParam.get("modelYear");
		if (StringUtils.isNoneBlank(modelYear) && StringUtils.isNoneEmpty(modelYear)) {
			sql.append("and M.MODEL_YEAR=" + modelYear + "\n");
		}
		String trimName = queryParam.get("trimName");
		if (StringUtils.isNoneBlank(trimName) && StringUtils.isNoneEmpty(trimName)) {
			sql.append("and M.TRIM_CODE='" + trimName + "'\n");
		}
		String vin = queryParam.get("vin");
		if (StringUtils.isNotBlank(vin)) {
			vin = queryParam.get("vin").replaceAll("\\^", "\n");
			vin = queryParam.get("vin").replaceAll("\\,", "\n");
			sql.append(getVinsAuto(vin, "v"));
		}
		// if (StringUtils.isNoneBlank(vin) && StringUtils.isNoneEmpty(vin)) {
		// // AND (V.VIN LIKE '%fdsfadsf%' )
		// sql.append("and v.VIN LIKE'%" + vin + "%'\n");
		// }
		String dealerCode = queryParam.get("dealerCode");
		if (StringUtils.isNoneBlank(dealerCode) && StringUtils.isNoneEmpty(dealerCode)) {
			String s = "";
			String[] split = dealerCode.split(",");
			for (int i = 0; i < split.length; i++) {
				s = "'" + split[i] + "'" + "," + s;
			}
			String substring = s.substring(0, s.length() - 1);
			// AND D.DEALER_CODE IN ('10036', '10037')
			sql.append("AND D.DEALER_CODE IN (" + substring + ")\n");
		}
		if (StringUtils.isNoneBlank(queryParam.get("storeType"))
				&& StringUtils.isNoneEmpty(queryParam.get("storeType"))) {

			String storeType1 = CommonUtils.checkNull(queryParam.get("storeType"));
			String storeType = OemDictCodeConstantsUtils.getStoreType(Integer.valueOf(storeType1));
			if (StringUtils.isNoneBlank(storeType) && StringUtils.isNoneEmpty(storeType)) {
				sql.append("and T1.ALLOT=" + storeType + "\n");
			}
		}

		String lifcycle = queryParam.get("lifcycle");
		if (StringUtils.isNoneBlank(lifcycle) && StringUtils.isNoneEmpty(lifcycle)) {
			// AND V.NODE_STATUS = '70011020'
			sql.append("AND V.NODE_STATUS  ='" + lifcycle + "'\n");
		}
		// and to_char(VNH.ZVGR_DATE,'yyyy-MM-dd') >= '2017-04-05' and
		// to_char(VNH.ZINV_DATE,'yyyy-MM-dd') >= '2017-04-04'
		String zinvDate1 = queryParam.get("zinvDate1");
		String zinvDate2 = queryParam.get("zinvDate2");
		if (StringUtils.isNoneBlank(zinvDate1) && StringUtils.isNoneEmpty(zinvDate1)) {
			// AND V.NODE_STATUS = '70011020'
			sql.append("and date_format(VNH.ZVGR_DATE,'%Y-%m-%d') >='" + zinvDate1 + "'\n");
		}
		if (StringUtils.isNoneBlank(zinvDate2) && StringUtils.isNoneEmpty(zinvDate2)) {
			sql.append("and date_format(VNH.ZINV_DATE,'%Y-%m-%d') <='" + zinvDate2 + "'\n");
		}
		String startZvgrDate1 = queryParam.get("startZvgrDate1");
		String startZvgrDate2 = queryParam.get("startZvgrDate2");
		if (StringUtils.isNoneBlank(startZvgrDate1) && StringUtils.isNoneEmpty(startZvgrDate1)) {
			sql.append("and date_format(VNH.ZVGR_DATE,'%Y-%m-%d') >='" + startZvgrDate1 + "'\n");
		}
		if (StringUtils.isNoneBlank(startZvgrDate2) && StringUtils.isNoneEmpty(startZvgrDate2)) {
			sql.append("and date_format(VNH.ZVGR_DATE,'%Y-%m-%d') <='" + startZvgrDate2 + "'\n");
		}
		return sql.toString();
	}

	private List<Long> findDealer(String s) {
		String sql = "select DEALER_ID from TM_DEALER where DEALER_CODE in(" + s + ")";
		List<Map> map = OemDAOUtil.findAll(sql, null);
		List<Long> ll = new ArrayList<>();
		for (Map map2 : map) {

			long parseLong = Long.parseLong(map2.get("DEALER_ID").toString());
			ll.add(parseLong);
		}
		return ll;

	}

	// 下载
	public List<Map> download(Map<String, String> queryParam) {
		List<Object> params = new ArrayList<Object>();
		String sql = getQuerySql12(queryParam, params);
		List<Map> download = OemDAOUtil.downloadPageQuery(sql, null);
		return download;

	}

	public List<Map> oemVehicleVinDetailQuery(String vin) {
		// 车辆属性
		List<Object> params = new ArrayList<Object>();
		StringBuffer sql1 = new StringBuffer();
		sql1.append(
				"select TV.VEHICLE_ID, TV.VIN,TV.ENGINE_NO,TV.REMARK,VM.BRAND_NAME,VM.SERIES_NAME,VM.MODEL_NAME,VM.COLOR_NAME,VM.TRIM_NAME\n");
		sql1.append("    from TM_VEHICLE_dec      TV,\n");
		sql1.append("   (" + getVwMaterialSql() + ")      VM\n");
		sql1.append("    where TV.MATERIAL_ID = VM.MATERIAL_ID\n");
		sql1.append("      AND TV.VIN=?\n");
		params.add(vin);
		List<Map> list = OemDAOUtil.findAll(sql1.toString(), params);
		return list;
	}

	public PageInfoDto oemVehicleDetailed(String vin) {
		// 车辆变更日志
		List<Object> params = new ArrayList<Object>();
		StringBuffer sql2 = new StringBuffer();
		// sql2.append(
		// "select distinct TVVC.CHANGE_CODE,date_format(TVVC.CHANGE_DATE
		// ,'%Y-%m-%d') CHANGE_DATE,TVVC.CHANGE_DESC,TD.DEALER_SHORTNAME
		// DEALER_NAME\n");
		// sql2.append(" from TM_VEHICLE_dec TV,\n");
		// sql2.append(" TT_VS_VHCL_CHNG TVVC,\n");
		// sql2.append(" TM_DEALER TD\n");
		// sql2.append(" where TVVC.VEHICLE_ID = TV.VEHICLE_ID\n");
		// sql2.append(" AND TV.DEALER_ID = TD.DEALER_ID\n");
		// /// 经销商端的详细车籍查询的车辆变更类型只有三种：发运出库 验收入库 实销
		// sql2.append(" AND TVVC.CHANGE_CODE in(" +
		// OemDictCodeConstants.VEHICLE_CHANGE_TYPE_07 + ","
		// + OemDictCodeConstants.VEHICLE_CHANGE_TYPE_09 + "," +
		// OemDictCodeConstants.VEHICLE_CHANGE_TYPE_10
		// + ")\n");
		// sql2.append(" AND TV.VIN=?\n");
		// params.add(vin);
		sql2.append(
				"select t.*,(case when t.CHANGE_CODE in(20211024,20211025,20211026,20211020,20211007,20211009,20211010) then  \n");
		sql2.append(
				"          (case when t.CHANGE_CODE is not null then (case when t.RESOURCE_TYPE=10191001 then (case when tor.ORG_ID=2010010100070674 then '全国' else tor.ORG_NAME end) else td.DEALER_SHORTNAME end) else '' end) end) RESOURCE, (case when tu.NAME is null then '' else  tu.NAME end)NAME\n");
		sql2.append(
				"  from (select  TV.VIN,TVVC.CHANGE_CODE,CHANGE_DATE,TVVC.CHANGE_DESC,date_format(TVVC.CREATE_DATE ,'%Y-%m-%d %H:%i:%s') CREATE_DATE,tvvc.CREATE_BY,tvvc.RESOURCE_TYPE,tvvc.RESOURCE_ID\n");
		sql2.append("     from TM_VEHICLE_dec           TV,\n");
		sql2.append("          TT_VS_VHCL_CHNG      TVVC\n");
		sql2.append("       where TVVC.VEHICLE_ID = TV.VEHICLE_ID\n");
		sql2.append("         AND TV.VIN=?\n");
		params.add(vin);
		sql2.append("       order by TVVC.CREATE_DATE,TVVC.CHANGE_DATE,TVVC.CREATE_DATE) t\n");
		sql2.append("  left join TM_DEALER td on td.DEALER_ID=t.RESOURCE_ID\n");
		sql2.append("  left join TM_ORG tor on tor.ORG_ID=t.RESOURCE_ID\n");
		sql2.append("  left join TC_USER tu on t.CREATE_BY=tu.USER_ID order by CHANGE_DATE\n");
		System.out.println(sql2.toString());
		PageInfoDto pageInfoDto = OemDAOUtil.pageQuery(sql2.toString(), params);
		return pageInfoDto;
	}

	// jV车场库存管理------生产订单跟踪
	public PageInfoDto oemVehicleQuery(Map<String, String> queryParam) {
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT V.VIN, -- 车架号 \n");
		sql.append("       M.BRAND_NAME,\n");
		sql.append("       M.SERIES_ID, \n");
		sql.append("       M.SERIES_CODE,\n");
		sql.append("       M.SERIES_NAME, \n");
		sql.append("       M.MODEL_CODE, \n");
		sql.append("       M.MODEL_NAME, \n");
		sql.append("       M.MODEL_YEAR, \n");
		sql.append("       M.STANDARD_OPTION,  \n");
		sql.append("       M.FACTORY_OPTIONS,  \n");
		sql.append("       M.LOCAL_OPTION,  \n");
		sql.append("       M.COLOR_NAME, \n");
		sql.append("       M.TRIM_NAME, \n");
		sql.append("       V.ENGINE_NO, \n");
		sql.append("       V.NODE_STATUS, \n");
		sql.append("       date_format(V.PREDICT_OFFLINE_DATE, '%Y-%m-%d') AS PREDICT_OFFLINE_DATE,\n");
		sql.append(
				"              CASE V.PREDICT_STORAGE_DATE WHEN NULL THEN NULL WHEN '' THEN NULL WHEN '00000000' THEN NULL \n");
		sql.append("     		  ELSE date_format(date_format(V.PREDICT_STORAGE_DATE, '%Y-%m-%d'), '%Y-%m-%d') \n");
		sql.append("              END AS PREDICT_STORAGE_DATE,\n");
		sql.append("       date_format(V.UPDATE_DATE, '%Y-%m-%d') AS UPDATE_DATE\n");
		sql.append("  FROM TM_VEHICLE_DEC V \n");
		sql.append(" INNER JOIN (" + getVwMaterialSql() + ")  M ON M.MATERIAL_ID = V.MATERIAL_ID \n");
		sql.append(" WHERE V.LIFE_CYCLE = '" + OemDictCodeConstants.LIF_CYCLE_01 + "' \n");
		sql.append("   AND M.GROUP_TYPE = '" + OemDictCodeConstants.GROUP_TYPE_DOMESTIC + "' \n");
		String brandName = queryParam.get("brandName");
		String seriesName = queryParam.get("seriesName");
		String groupName = queryParam.get("groupName");
		String modelYear = queryParam.get("modelYear");
		String color = queryParam.get("colorName");
		String trimName = queryParam.get("trimName");
		String k4VehicleNode = queryParam.get("k4VehicleNode");
		String vin = queryParam.get("vin");
		// 品牌
		if (StringUtils.isNoneBlank(brandName) && StringUtils.isNoneEmpty(brandName)) {
			sql.append(" AND M.BRAND_ID=" + brandName);
		}
		// 车系

		if (StringUtils.isNoneBlank(seriesName) && StringUtils.isNoneEmpty(seriesName)) {
			sql.append("   AND M.SERIES_ID = '" + seriesName + "' \n");
		}
		// 车款
		if (StringUtils.isNoneBlank(groupName) && StringUtils.isNoneEmpty(groupName)) {
			sql.append("   AND M.GROUP_ID = '" + groupName + "' \n");
		}

		// 年款
		if (StringUtils.isNoneBlank(modelYear) && StringUtils.isNoneEmpty(modelYear)) {
			sql.append("   AND M.MODEL_YEAR = '" + modelYear + "' \n");
		}

		// 颜色
		if (StringUtils.isNoneBlank(color) && StringUtils.isNoneEmpty(color)) {
			sql.append("   AND M.COLOR_Code = '" + color + "' \n");
		}

		// 内饰
		if (StringUtils.isNoneBlank(trimName) && StringUtils.isNoneEmpty(trimName)) {
			sql.append("   AND M.TRIM_CODE = '" + trimName + "' \n");
		}

		// 车辆节点状态
		if (StringUtils.isNoneBlank(k4VehicleNode) && StringUtils.isNoneEmpty(k4VehicleNode)) {
			sql.append("   AND V.NODE_STATUS = '" + k4VehicleNode + "' \n");
		}

		// 车架号
		if (StringUtils.isNoneBlank(vin) && StringUtils.isNoneEmpty(vin)) {
			sql.append("   AND V.VIN LIKE UPPER('%" + vin + "%') \n");
		}
		System.out.println(sql.toString());
		return OemDAOUtil.pageQuery(sql.toString(), null);
	}

	// 下载
	public List<Map> oemVehicleQueryDownload(Map<String, String> queryParam) {
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT V.VIN, -- 车架号 \n");
		sql.append("       M.BRAND_NAME,\n");
		sql.append("       M.SERIES_ID, \n");
		sql.append("       M.SERIES_CODE,\n");
		sql.append("       M.SERIES_NAME, \n");
		sql.append("       M.MODEL_CODE, \n");
		sql.append("       M.MODEL_NAME, \n");
		sql.append("       M.MODEL_YEAR, \n");
		sql.append("       M.STANDARD_OPTION,  \n");
		sql.append("       M.FACTORY_OPTIONS,  \n");
		sql.append("       M.LOCAL_OPTION,  \n");
		sql.append("       M.COLOR_NAME, \n");
		sql.append("       M.TRIM_NAME, \n");
		sql.append("       V.ENGINE_NO, \n");
		sql.append("       V.NODE_STATUS, \n");
		sql.append("       date_format(V.PREDICT_OFFLINE_DATE, '%y-%m-%d') AS PREDICT_OFFLINE_DATE,\n");
		sql.append(
				"              CASE V.PREDICT_STORAGE_DATE WHEN NULL THEN NULL WHEN '' THEN NULL WHEN '00000000' THEN NULL \n");
		sql.append("     		  ELSE date_format(date_format(V.PREDICT_STORAGE_DATE, '%y-%m-%d'), '%y-%m-%d') \n");
		sql.append("              END AS PREDICT_STORAGE_DATE,\n");
		sql.append("       date_format(V.UPDATE_DATE, '%y-%m-%d') AS UPDATE_DATE\n");
		sql.append("  FROM TM_VEHICLE_DEC V \n");
		sql.append(" INNER JOIN (" + getVwMaterialSql() + ")M ON M.MATERIAL_ID = V.MATERIAL_ID \n");
		sql.append(" WHERE V.LIFE_CYCLE = '" + OemDictCodeConstants.LIF_CYCLE_01 + "' \n");
		sql.append("   AND M.GROUP_TYPE = '" + OemDictCodeConstants.GROUP_TYPE_DOMESTIC + "' \n");
		String brandName = queryParam.get("brandName");
		String seriesName = queryParam.get("seriesName");
		String groupName = queryParam.get("groupName");
		String modelYear = queryParam.get("modelYear");
		String color = queryParam.get("colorName");
		String trimName = queryParam.get("trimName");
		String k4VehicleNode = queryParam.get("k4VehicleNode");
		String vin = queryParam.get("vin");
		// 品牌
		if (StringUtils.isNoneBlank(brandName) && StringUtils.isNoneEmpty(brandName)) {
			sql.append(" AND M.BRAND_ID=" + brandName);
		}
		// 车系

		if (StringUtils.isNoneBlank(seriesName) && StringUtils.isNoneEmpty(seriesName)) {
			sql.append("   AND M.SERIES_ID = '" + seriesName + "' \n");
		}
		// 车款
		if (StringUtils.isNoneBlank(groupName) && StringUtils.isNoneEmpty(groupName)) {
			sql.append("   AND M.GROUP_ID = '" + groupName + "' \n");
		}

		// 年款
		if (StringUtils.isNoneBlank(modelYear) && StringUtils.isNoneEmpty(modelYear)) {
			sql.append("   AND M.MODEL_YEAR = '" + modelYear + "' \n");
		}

		// 颜色
		if (StringUtils.isNoneBlank(color) && StringUtils.isNoneEmpty(color)) {
			sql.append("   AND M.COLOR_NAME = '" + color + "' \n");
		}

		// 内饰
		if (StringUtils.isNoneBlank(trimName) && StringUtils.isNoneEmpty(trimName)) {
			sql.append("   AND M.TRIM_NAME = '" + trimName + "' \n");
		}

		// 车辆节点状态
		if (StringUtils.isNoneBlank(k4VehicleNode) && StringUtils.isNoneEmpty(k4VehicleNode)) {
			sql.append("   AND V.NODE_STATUS = '" + k4VehicleNode + "' \n");
		}

		// 车架号
		if (StringUtils.isNoneBlank(vin) && StringUtils.isNoneEmpty(vin)) {
			sql.append("   AND V.VIN LIKE UPPER('%" + vin + "%') \n");
		}

		return OemDAOUtil.downloadPageQuery(sql.toString(), null);
	}

	// 车籍详细查询
	/**
	 * @param queryParam
	 * @return
	 */
	public PageInfoDto vehicleDetailQuery(Map<String, String> queryParam) {
		String sql = getQuerySql(queryParam, null);
		// end
		System.out.println(sql);
		return OemDAOUtil.pageQuery(sql.toString(), null);
	}

	// 车辆祥籍下载
	public List<Map> doDownload(Map<String, String> queryParam) {
		String sql = getQuerySql(queryParam, null);
		return OemDAOUtil.downloadPageQuery(sql, null);
	}

	/**
	 * SQL组装
	 * 
	 * @param queryParam
	 * @param params
	 * @return
	 */
	private String getQuerySql1(Map<String, String> queryParam, List<Object> params) {
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT D.DEALER_CODE, \n");
		sql.append("       D.DEALER_SHORTNAME, \n");
		sql.append("       M.BRAND_NAME, \n");
		sql.append("       M.SERIES_NAME, \n");
		sql.append("       M.MODEL_CODE,  \n");
		sql.append("       M.MODEL_YEAR, \n");
		sql.append("       M.GROUP_NAME, \n");
		sql.append("       M.COLOR_NAME,  \n");
		sql.append("       M.TRIM_NAME, \n");
		sql.append("       V.VIN, \n");
		sql.append("       V.LOCK_STATUS, \n");
		sql.append("       V.LOCK_REASON, \n");
		sql.append("       V.ENGINE_NO, \n");
		sql.append("       ' ' AS KEY_NO, \n");
		sql.append("       V.QUALIFIED_NO, \n");
		sql.append("       V.NODE_STATUS, \n");

		// update date[20160421] by[maxiang] begin..
		// sql.append(" DECODE(NVL(CM.CODE_DESC, '0'), '0', '普通', CM.CODE_DESC)
		// AS VEHICLE_USAGE, -- 车辆用途 \n");
		sql.append("       (case when COALESCE(CM.CODE_DESC, '0')=0 then '' else CM.CODE_DESC END)AS VEHICLE_USAGE,\n");
		// case when
		sql.append("       date_format(V.OFFLINE_DATE, '%y-%m-%d') AS OFFLINE_DATE,\n");
		sql.append("       V.VEHICLE_ID AS VEHICLE_ID, \n");
		sql.append("       V.WHOLESALE_PRICE,\n");
		sql.append("       V.RETAIL_PRICE,\n");
		sql.append("       date_format(VNH.ZASS_DATE, '%y-%m-%d') AS ZASS_DATE,\n");
		sql.append("       date_format(VNH.ZVGO_DATE, '%y-%m-%d') AS ZVGO_DATE,\n");
		sql.append("       date_format(VNH.ZVQP_DATE, '%y-%m-%d') AS ZVQP_DATE,\n");
		sql.append("       date_format(VNH.ZVGR_DATE, '%y-%m-%d') AS ZVGR_DATE,\n");
		sql.append("       date_format(VNH.ZBB1_DATE, '%y-%m-%d') AS ZBBU_DATE,\n");
		sql.append("       date_format(VNH.ZINV_DATE, '%y-%m-%d') AS ZINV_DATE,\n");
		sql.append("       date_format(VNH.ZMBL_DATE, '%y-%m-%d') AS ZMBL_DATE,\n");
		sql.append("       date_format(VNH.YSOR_DATE, '%y-%m-%d') AS YSOR_DATE, \n");
		sql.append("       date_format(VNH.ZVHC_DATE, '%y-%m-%d') AS ZVHC_DATE, \n");
		sql.append("       date_format(VNH.ZPD2_DATE, '%y-%m-%d') AS ZPD2_DATE, \n");
		sql.append("       date_format(VNH.ZPOD_DATE, '%y-%m-%d') AS ZPOD_DATE,  \n");
		sql.append("       date_format(N.CREATE_DATE, '%y-%m-%d') AS RETAIL_DATE, \n");
		sql.append("       date_format(N.UPDATE_DATE, '%y-%m-%d') AS RETAIL_CHECK_DATE,\n");
		sql.append(
				"              CASE V.PREDICT_STORAGE_DATE WHEN NULL THEN NULL WHEN '' THEN NULL WHEN '00000000' THEN NULL \n");
		sql.append("     		  ELSE date_format(date_format(V.PREDICT_STORAGE_DATE, '%y-%m-%d'), '%y-%m-%d') \n");
		sql.append("              END AS PREDICT_STORAGE_DATE, \n");
		sql.append("       ' ' AS SCRAP_DATE\n");
		sql.append("  FROM TM_VEHICLE_DEC V \n");
		sql.append(" INNER JOIN (" + getVwMaterialSql() + ")M ON M.MATERIAL_ID = V.MATERIAL_ID \n");
		sql.append("  LEFT JOIN TT_VEHICLE_NODE_HISTORY VNH ON V.VEHICLE_ID = VNH.VEHICLE_ID \n");
		sql.append("  LEFT JOIN TM_DEALER D ON D.DEALER_ID = V.DEALER_ID \n");
		sql.append("  LEFT JOIN TT_VS_NVDR N ON N.VIN = V.VIN \n");
		sql.append("  LEFT JOIN TC_CODE_K4_MAPPING CM ON CM.CODE_ID = V.VEHICLE_USAGE \n");
		sql.append(" WHERE M.GROUP_TYPE = '" + OemDictCodeConstants.GROUP_TYPE_DOMESTIC + "' \n");
		String brandName = queryParam.get("brandName");
		String seriesName = queryParam.get("seriesName");
		String groupName = queryParam.get("groupName");
		String modelYear = queryParam.get("modelYear");
		String color = queryParam.get("color");
		String trimName = queryParam.get("trimName");
		String k4VehicleNode = queryParam.get("k4VehicleNode");
		String vin = queryParam.get("vin");
		String startZvgrDate1 = queryParam.get("startZvgrDate1");
		String startZvgrDate2 = queryParam.get("startZvgrDate2");
		String zinvDate1 = queryParam.get("zinvDate1");
		String zinvDate2 = queryParam.get("zinvDate2");
		// 品牌
		if (StringUtils.isNoneBlank(brandName) && StringUtils.isNoneEmpty(brandName)) {
			sql.append("and AND M.BRAND_ID=" + brandName);
		}
		// 车系

		if (StringUtils.isNoneBlank(seriesName) && StringUtils.isNoneEmpty(seriesName)) {
			sql.append("   AND M.SERIES_ID = '" + seriesName + "' \n");
		}
		// 车款
		if (StringUtils.isNoneBlank(groupName) && StringUtils.isNoneEmpty(groupName)) {
			sql.append("   AND M.GROUP_ID = '" + groupName + "' \n");
		}

		// 年款
		if (StringUtils.isNoneBlank(modelYear) && StringUtils.isNoneEmpty(modelYear)) {
			sql.append("   AND M.MODEL_YEAR = '" + modelYear + "' \n");
		}

		// 颜色
		if (StringUtils.isNoneBlank(color) && StringUtils.isNoneEmpty(color)) {
			sql.append("   AND M.COLOR_NAME = '" + color + "' \n");
		}

		// 内饰
		if (StringUtils.isNoneBlank(trimName) && StringUtils.isNoneEmpty(trimName)) {
			sql.append("   AND M.TRIM_NAME = '" + trimName + "' \n");
		}
		// 车架号
		if (StringUtils.isNoneBlank(vin) && StringUtils.isNoneEmpty(vin)) {
			vin = vin.replaceAll("\\^", " \n");
			vin = vin.replaceAll("\\,", " \n");
			sql.append("  " + getVinsAuto(vin, "V") + " \n");
		}

		String dealerCode = queryParam.get("dealerCode");
		// 经销商代码
		if (StringUtils.isNoneBlank(dealerCode) && StringUtils.isNoneEmpty(dealerCode)) {
			String[] dealerCodeArray = dealerCode.split(",");

			for (int i = 0; i < dealerCodeArray.length; i++) {

				if (dealerCodeArray.length == (i + 1)) {
					dealerCode += "'" + dealerCodeArray[i] + "'";
				} else {
					dealerCode += "'" + dealerCodeArray[i] + "', ";
				}
			}
			sql.append("   AND D.DEALER_CODE IN (" + dealerCode + ") \n");
		}

		// 车辆节点状态
		if (StringUtils.isNoneBlank(k4VehicleNode) && StringUtils.isNoneEmpty(k4VehicleNode)) {
			sql.append("   AND V.NODE_STATUS = '" + k4VehicleNode + "' \n");
		}
		// ad by huyu 2016-4-14
		// 工厂入库日期
		if (StringUtils.isNoneBlank(startZvgrDate1) && StringUtils.isNoneEmpty(startZvgrDate1)) {
			sql.append(" and date_format(VNH.ZVGR_DATE,'%y-%m-%d') >= '" + startZvgrDate1 + "'");
		}
		if (StringUtils.isNoneBlank(startZvgrDate2) && StringUtils.isNoneEmpty(startZvgrDate2)) {
			sql.append(" and date_format(VNH.ZVGR_DATE,'%y-%m-%d') <= '" + startZvgrDate2 + "'");
		}
		// 内部结算成功日期
		if (StringUtils.isNoneBlank(zinvDate1) && StringUtils.isNoneEmpty(zinvDate1)) {
			sql.append(" and date_format(VNH.ZINV_DATE,'%y-%m-%d') >= '" + zinvDate1 + "'");
		}
		if (StringUtils.isNoneBlank(zinvDate2) && StringUtils.isNoneEmpty(zinvDate2)) {
			sql.append(" and date_format(VNH.ZINV_DATE,'%y-%m-%d') <= '" + zinvDate2 + "'");
		}
		return sql.toString();
	}

	// jV车场库存管理-----详细车籍明细查询
	public Map<String, Object> doQueryDetail(Long vehicleId) {
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT V.VIN,\n");
		sql.append("       V.ENGINE_NO,\n");
		sql.append("       '' AS KEY_NO, \n");
		sql.append("       date_format(V.OFFLINE_DATE, '%y-%m-%d') AS OFFLINE_DATE,\n");
		sql.append("       M.BRAND_NAME, \n");
		sql.append("       M.SERIES_NAME, \n");
		sql.append("       M.MODEL_CODE, \n");
		sql.append("       M.MODEL_YEAR, \n");
		sql.append("       M.GROUP_NAME, \n");
		sql.append("       M.COLOR_NAME,  \n");
		sql.append("       M.TRIM_NAME,  \n");
		sql.append("       V.LOCK_STATUS,\n");
		sql.append("       V.LOCK_REASON, \n");
		sql.append("       V.REMARK \n");
		sql.append("  FROM TM_VEHICLE_DEC V \n");
		sql.append(" INNER JOIN (" + getVwMaterialSql() + ")  M ON M.MATERIAL_ID = V.MATERIAL_ID \n");
		sql.append(" WHERE V.VEHICLE_ID = '" + vehicleId + "' \n");
		List<Map> list = OemDAOUtil.findAll(sql.toString(), null);
		Map<String, Object> map = new HashMap();
		for (Map map1 : list) {
			map.put("VIN", map1.get("VIN"));
			map.put("ENGINE_NO", map1.get("ENGINE_NO"));
			map.put("KEY_NO", map1.get("KEY_NO"));
			map.put("OFFLINE_DATE", map1.get("OFFLINE_DATE"));
			map.put("BRAND_NAME", map1.get("BRAND_NAME"));
			map.put("SERIES_NAME", map1.get("SERIES_NAME"));
			map.put("MODEL_CODE", map1.get("MODEL_CODE"));
			map.put("MODEL_YEAR", map1.get("MODEL_YEAR"));
			map.put("COLOR_NAME", map1.get("COLOR_NAME"));
			map.put("GROUP_NAME", map1.get("GROUP_NAME"));
			map.put("TRIM_NAME", map1.get("TRIM_NAME"));
			if (Integer.valueOf(map1.get("LOCK_STATUS").toString())
					.equals(OemDictCodeConstants.VEHICLE_LOCK_STATUS_01)) {
				Object lock_status1 = map1.get("LOCK_STATUS");
				lock_status1 = "正常库存";
				map.put("LOCK_STATUS", lock_status1);
			}
			if (Integer.valueOf(map1.get("LOCK_STATUS").toString())
					.equals(OemDictCodeConstants.VEHICLE_LOCK_STATUS_02)) {
				Object lock_status1 = map1.get("LOCK_STATUS");
				lock_status1 = "预留锁定";
				map.put("LOCK_STATUS", lock_status1);
			}
			if (Integer.valueOf(map1.get("LOCK_STATUS").toString())
					.equals(OemDictCodeConstants.VEHICLE_LOCK_STATUS_03)) {
				Object lock_status1 = map1.get("LOCK_STATUS");
				lock_status1 = "移库锁定";
				map.put("LOCK_STATUS", lock_status1);
			}
			if (Integer.valueOf(map1.get("LOCK_STATUS").toString())
					.equals(OemDictCodeConstants.VEHICLE_LOCK_STATUS_04)) {
				Object lock_status1 = map1.get("LOCK_STATUS");
				lock_status1 = "质损锁定";
				map.put("LOCK_STATUS", lock_status1);
			}
			if (Integer.valueOf(map1.get("LOCK_STATUS").toString())
					.equals(OemDictCodeConstants.VEHICLE_LOCK_STATUS_05)) {
				Object lock_status1 = map1.get("LOCK_STATUS");
				lock_status1 = "退库锁定";
				map.put("LOCK_STATUS", lock_status1);
			}

			map.put("LOCK_REASON", map1.get("LOCK_REASON"));
			map.put("REMARK", map1.get("REMARK"));
		}

		return map;
	}

	public PageInfoDto vehicleNodeChangeQuery(Long vehicleId) {
		StringBuffer sql = new StringBuffer();
		sql.append(" \n");
		sql.append("SELECT C.CHANGE_CODE, \n");
		sql.append("       date_format(C.CHANGE_DATE, '%Y-%m-%d %h:%i:%s') AS CHANGE_DATE, \n");
		sql.append("       C.CHANGE_DESC \n");
		sql.append("  FROM TT_VS_VHCL_CHNG C \n");
		sql.append(" WHERE C.CHANGE_CODE IS NOT NULL \n");
		sql.append("   AND C.CHANGE_DATE IS NOT NULL \n");
		sql.append("   AND C.CHANGE_DESC IS NOT NULL \n");
		sql.append("   AND C.VEHICLE_ID = '" + vehicleId + "' \n");
		sql.append(" GROUP BY C.CHANGE_CODE, C.CHANGE_DATE, C.CHANGE_DESC \n");
		sql.append(" ORDER BY C.CHANGE_DATE DESC \n");
		System.out.println(sql.toString());
		PageInfoDto list = OemDAOUtil.pageQuery(sql.toString(), null);
		return list;
	}

	// 车厂库存查询
	public PageInfoDto doQuery(Map<String, String> queryParam) {
		String sql2 = getQuerySql2(queryParam, null);
		return OemDAOUtil.pageQuery(sql2, null);
	}

	//// 车厂库存查询
	public List<Map> depotInventorydoDownload(Map<String, String> queryParam) {
		String sql2 = getQuerySql2(queryParam, null);
		return OemDAOUtil.downloadPageQuery(sql2, null);
	}

	private String getQuerySql2(Map<String, String> queryParam, List<Object> params) {

		StringBuffer sql = new StringBuffer();
		sql.append("SELECT M.BRAND_NAME, \n");
		sql.append("       M.SERIES_NAME,\n");
		sql.append("       M.MODEL_CODE,\n");
		sql.append("       M.GROUP_NAME, \n");
		sql.append("       M.MODEL_YEAR, \n");
		sql.append("       M.COLOR_NAME, \n");
		sql.append("       M.TRIM_NAME, \n");
		sql.append("       V.VIN,  \n");
		sql.append("       date_format(V.OFFLINE_DATE, '%y-%m-%d') AS OFFLINE_DATE, \n");
		sql.append("       date_format(VNH.ZVGR_DATE, '%y-%m-%d') AS ZVGR_DATE, \n");
		sql.append("       date_format(VNH.ZINV_DATE, '%y-%m-%d') AS ZBBU_DATE, \n");
		sql.append("       date_format(VNH.ZBB1_DATE, '%y-%m-%d') AS ZMBL_DATE, \n");
		sql.append("       date_format(VNH.ZBB1_EXPECTED_UNBLOCK_DATE, '%y-%m-%d') AS EXPECTED_UNBLOCK_DATE, \n");
		sql.append("       date_format(VNH.ZBBR_DATE, '%y-%m-%d') AS ZBBR_DATE,\n");
		sql.append(
				"              CASE V.PREDICT_STORAGE_DATE WHEN NULL THEN NULL WHEN '' THEN NULL WHEN '00000000' THEN NULL \n");
		sql.append("     		  ELSE date_format(date_format(V.PREDICT_STORAGE_DATE, '%y-%m-%d'), '%y-%m-%d') \n");
		sql.append("              END AS PREDICT_STORAGE_DATE,\n");
		sql.append("       VNH.ZBB1_BLOCK_REASON BLOCK_REASON, \n");
		sql.append("       V.NODE_STATUS,\n");
		sql.append("       V.REMARK \n");
		sql.append("  FROM TM_VEHICLE_DEC V   \n");
		sql.append(" INNER JOIN (" + getVwMaterialSql() + ") M ON M.MATERIAL_ID = V.MATERIAL_ID  \n");
		sql.append(" LEFT JOIN TT_VEHICLE_NODE_HISTORY VNH ON V.VEHICLE_ID = VNH.VEHICLE_ID  \n");
		sql.append(" WHERE V.LIFE_CYCLE = '" + OemDictCodeConstants.LIF_CYCLE_02 + "' \n");
		sql.append("   AND M.GROUP_TYPE = '" + OemDictCodeConstants.GROUP_TYPE_DOMESTIC + "' \n");

		String brandName = queryParam.get("brandName");
		String seriesName = queryParam.get("seriesName");
		String groupName = queryParam.get("groupName");
		String modelYear = queryParam.get("modelYear");
		String color = queryParam.get("colorName");
		String trimName = queryParam.get("trimName");
		String k4VehicleNode = queryParam.get("k4VehicleNode");
		String vin = queryParam.get("vin");
		String offlineDateBegin = queryParam.get("offlineDateBegin");
		String offlineDateEnd = queryParam.get("offlineDateEnd");
		String zvgoDateBegin = queryParam.get("zvgoDateBegin");
		String zvgoDateEnd = queryParam.get("zvgoDateEnd");
		// 品牌
		if (StringUtils.isNoneBlank(brandName) && StringUtils.isNoneEmpty(brandName)) {
			sql.append(" AND M.BRAND_ID=" + brandName);
		}
		// 车系

		if (StringUtils.isNoneBlank(seriesName) && StringUtils.isNoneEmpty(seriesName)) {
			sql.append("   AND M.SERIES_ID = '" + seriesName + "' \n");
		}
		// 车款
		if (StringUtils.isNoneBlank(groupName) && StringUtils.isNoneEmpty(groupName)) {
			sql.append("   AND M.GROUP_ID = '" + groupName + "' \n");
		}

		// 年款
		if (StringUtils.isNoneBlank(modelYear) && StringUtils.isNoneEmpty(modelYear)) {
			sql.append("   AND M.MODEL_YEAR = '" + modelYear + "' \n");
		}

		// 颜色
		if (StringUtils.isNoneBlank(color) && StringUtils.isNoneEmpty(color)) {
			sql.append("   AND M.COLOR_CODE = '" + color + "' \n");
		}

		// 内饰
		if (StringUtils.isNoneBlank(trimName) && StringUtils.isNoneEmpty(trimName)) {
			sql.append("   AND M.TRIM_CODE = '" + trimName + "' \n");
		}

		// 下线日期 Begin
		if (StringUtils.isNoneBlank(offlineDateBegin) && StringUtils.isNoneEmpty(offlineDateBegin)) {
			sql.append("   AND CHAR(DATE(V.OFFLINE_DATE)) >= '" + offlineDateBegin + "' \n");
		}

		// 下线日期 End
		if (StringUtils.isNoneBlank(offlineDateEnd) && StringUtils.isNoneEmpty(offlineDateEnd)) {
			sql.append("   AND CHAR(DATE(V.OFFLINE_DATE)) <= '" + offlineDateEnd + "' \n");
		}

		// 入库日期（MJV） Begin
		if (StringUtils.isNoneBlank(zvgoDateBegin) && StringUtils.isNoneEmpty(zvgoDateBegin)) {
			sql.append("   AND CHAR(DATE(VNH.ZVGR_DATE)) >= '" + zvgoDateBegin + "' \n");
		}

		// 入库日期 （MJV）End
		if (!StringUtils.isNoneBlank(zvgoDateEnd) && StringUtils.isNoneEmpty(zvgoDateEnd)) {
			sql.append("   AND CHAR(DATE(VNH.ZVGR_DATE)) <= '" + zvgoDateEnd + "' \n");
		}

		// 车架号
		if (!StringUtils.isNoneBlank(vin) && StringUtils.isNoneEmpty(vin)) {
			vin = vin.replaceAll("\\^", " \n");
			vin = vin.replaceAll("\\,", " \n");
			sql.append("  " + getVinsAuto(vin, "V") + " \n");
		}

		// 车辆节点状态
		if (!StringUtils.isNoneBlank(k4VehicleNode) && StringUtils.isNoneEmpty(k4VehicleNode)) {
			sql.append("   AND V.NODE_STATUS = '" + k4VehicleNode + "' \n");
		}
		System.out.println(sql.toString());
		return sql.toString();
	}

	// 车辆变更节点查询下载
	public List<Map> downloadDetailsl(Map<String, String> queryParam) {
		String resource = CommonUtils.checkNull(queryParam.get("resource"));
		String vehicleId = CommonUtils.checkNull(queryParam.get("id"));
		StringBuffer sql = new StringBuffer();
		sql.append(
				"select t.* from (select  TV.VIN,TVVC.CHANGE_CODE,date_format(TVVC.CHANGE_DATE ,'%Y-%m-%d %H:%i:%s') CHANGE_DATE,TVVC.CHANGE_DESC,date_format(TVVC.CREATE_DATE ,'%Y-%m-%d %H:%i:%s') CREATE_DATE,\n");
		sql.append(
				"      (select NAME from TC_USER where USER_ID=TVVC.CREATE_BY) NAME,(case when TVVC.CHANGE_CODE is not null then'"
						+ resource + "' else '' end) RESOURCE\n");
		sql.append("     from TM_VEHICLE_DEC           TV,\n");
		sql.append("          TT_VS_VHCL_CHNG      TVVC\n");
		sql.append("       where TVVC.VEHICLE_ID = TV.VEHICLE_ID\n");
		sql.append("         AND TV.VEHICLE_ID=" + vehicleId + "\n");
		sql.append("       order by TVVC.CREATE_DATE)t\n");
		System.out.println(sql.toString());
		return OemDAOUtil.downloadPageQuery(sql.toString(), null);
	}

}
