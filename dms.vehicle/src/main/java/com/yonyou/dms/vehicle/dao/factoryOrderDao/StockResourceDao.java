package com.yonyou.dms.vehicle.dao.factoryOrderDao;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.javalite.activejdbc.LazyList;
import org.springframework.stereotype.Repository;

import com.yonyou.dms.framework.DAO.OemBaseDAO;
import com.yonyou.dms.framework.DAO.OemDAOUtil;
import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.framework.domain.LoginInfoDto;
import com.yonyou.dms.framework.util.bean.ApplicationContextHelper;
import com.yonyou.dms.function.common.DictCodeConstants;
import com.yonyou.dms.function.common.OemDictCodeConstants;
import com.yonyou.dms.function.utils.common.DateUtil;
import com.yonyou.dms.vehicle.domains.PO.orderManager.TtOrderSaleGroupPO;

/**
 * 
 * @author 廉兴鲁
 *
 */
@Repository
public class StockResourceDao extends OemBaseDAO {

	public PageInfoDto stockResourceQuery(Map<String, String> queryParam) {
		List<Object> params = new ArrayList<Object>();
		String sql = getQuerySql(queryParam, params);
		System.out.println(sql.toString() + params.toString());
		PageInfoDto pageInfoDto = OemDAOUtil.pageQuery(sql, params);
		return pageInfoDto;

	}

	/**
	 * SQL组装
	 * 
	 * @param queryParam
	 * @param params
	 * @return
	 */

	private String getQuerySql(Map<String, String> queryParam, List<Object> params) {
		LoginInfoDto loginInfo = ApplicationContextHelper.getBeanByType(LoginInfoDto.class);
		// Map map =
		// OemDAOUtil.getDealerScope(String.valueOf(loginInfo.getDealerId()));
		// Object orgId = map.get("orgId");
		// String oemCompanyId = loginInfo.getOemCompanyId();
		// 拼接条件
		String brandId = queryParam.get("brandId");
		String seriesName = queryParam.get("seriesName");
		String groupName = queryParam.get("groupName");
		String modelYear = queryParam.get("modelYear");
		String color = queryParam.get("color");
		String trimCode = queryParam.get("trimCode");
		String vin = queryParam.get("vin");
		String orderType = queryParam.get("orderType");
		String remark = queryParam.get("remark");
		// TcOrgBigPO tcOrg = new TcOrgBigPO();

		StringBuffer sql = new StringBuffer();
		sql.append("SELECT * FROM (SELECT R.COMMON_ID,\n");
		sql.append("       0 AS ORDER_ID, \n");
		sql.append("	       NULL AS ORDER_NO, \n");
		sql.append("	       NULL AS ORDER_DATE,\n");
		sql.append("	       V.VIN,\n");
		sql.append("		   VM.BRAND_CODE, \n");
		sql.append("		   VM.SERIES_ID, \n");
		sql.append("		   VM.SERIES_NAME, \n");
		sql.append("	       VM.GROUP_CODE,\n");
		sql.append(" VM.GROUP_NAME, \n");
		sql.append("			       VM.GROUP_ID, \n");
		sql.append("			       VM.MODEL_YEAR, \n");
		sql.append("			       VM.COLOR_CODE, \n");
		sql.append("VM.COLOR_NAME, \n");
		sql.append("	       VM.TRIM_CODE,\n");
		sql.append("	       VM.TRIM_NAME, \n");
		sql.append("(CASE R.TYPE WHEN 20811002 THEN 20831001 WHEN 20811003 THEN 20831004 END) ORDER_TYPE,\n");
		sql.append("COALESCE((SELECT DISTINCT REMARK FROM TT_RESOURCE_REMARK WHERE VIN=V.VIN),0) REMARK, \n");
		sql.append("(SELECT DISTINCT OTHER_REMARK FROM TT_RESOURCE_REMARK WHERE VIN=V.VIN) OTHER_REMARK, \n");
		sql.append("		   V.VEHICLE_USAGE,       V.RETAIL_PRICE \n");
		sql.append("	  FROM TT_VS_COMMON_RESOURCE        R, \n");
		sql.append("	       TT_VS_COMMON_RESOURCE_DETAIL D, \n");
		sql.append("	       TM_VEHICLE_dec       V, \n");
		sql.append("	       (" + getVwMaterialSql() + ")       VM \n");
		sql.append("	 WHERE  R.VEHICLE_ID = V.VEHICLE_ID \n");
		sql.append("	   AND R.RESOURCE_SCOPE IN (  " + loginInfo.getOrgId() + "," + loginInfo.getUserId() + ","
				+ loginInfo.getOemCompanyId() + ")\n");
		sql.append("  AND R.COMMON_ID = D.COMMON_ID \n");
		sql.append("   AND V.MATERIAL_ID = VM.MATERIAL_ID \n");
		sql.append("   AND V.LOCK_STATUS = " + OemDictCodeConstants.VEHICLE_LOCK_STATUS_01 + " \n");
		sql.append("   AND  VM.GROUP_TYPE=" + OemDictCodeConstants.GROUP_TYPE_IMPORT + "\n");
		sql.append("   AND NOT EXISTS (SELECT 1 FROM TT_VS_ORDER OD WHERE OD.IS_DEL <> '1' AND OD.ORDER_STATUS IN \n");
		sql.append(" (" + OemDictCodeConstants.ORDER_STATUS_02 + "," + OemDictCodeConstants.ORDER_STATUS_03 + ","
				+ OemDictCodeConstants.ORDER_STATUS_04 + "," + OemDictCodeConstants.ORDER_STATUS_06 + ","
				+ OemDictCodeConstants.ORDER_STATUS_07 + "," + OemDictCodeConstants.ORDER_STATUS_05
				+ ")       AND OD.VIN = V.VIN)   AND R.STATUS = '" + OemDictCodeConstants.COMMON_RESOURCE_STATUS_02
				+ "' \n");
		sql.append("   AND D.STATUS = '" + OemDictCodeConstants.STATUS_ENABLE + "' \n");
		sql.append("   AND R.TYPE IN(" + OemDictCodeConstants.COMMON_RESOURCE_TYPE_02 + ","
				+ OemDictCodeConstants.COMMON_RESOURCE_TYPE_03 + ") \n");
		// 在经销商订货、预测功能默认品牌、车型筛选限制在车型年款是2013年之后车型
		sql.append("   AND VM.MODEL_YEAR>'2012'");
		if (brandId != null && !"".equals(brandId)) {
			sql.append(" AND VM.BRAND_ID in( '" + brandId + "') ");
		}
		if (seriesName != null && !"".equals(seriesName)) {
			sql.append(" AND VM.SERIES_ID in( '" + seriesName + "') ");
		}

		if (groupName != null && !"".equals(groupName)) {
			sql.append(" AND VM.GROUP_ID in( '" + groupName + "') ");
		}

		if (modelYear != null && !"".equals(modelYear)) {
			sql.append(" AND VM.MODEL_YEAR in( '" + modelYear + "') ");
		}
		if (color != null && !"".equals(color)) {
			sql.append(" AND VM.COLOR_Code in( '" + color + "') ");
		}
		if (trimCode != null && !"".equals(trimCode)) {
			sql.append(" AND VM.TRIM_CODE in( '" + trimCode + "') ");
		}
		if (vin != null && !vin.equals("vin")) {
			vin = vin.replaceAll("\\^", "\n");
			vin = vin.replaceAll("\\,", "\n");
			sql.append(getVinsAuto(vin, "V"));
		}

		sql.append("UNION ALL\n");
		sql.append("	SELECT DISTINCT 0 AS COMMON_ID, \n");
		sql.append("	   o.ORDER_ID, \n");
		sql.append("       o.ORDER_NO, \n");
		sql.append("			       o.ORDER_DATE, \n");
		sql.append("			       o.vin,\n");
		sql.append("			       VM.BRAND_CODE, \n");
		sql.append("		       VM.SERIES_CODE, \n");
		sql.append("			   VM.SERIES_NAME, \n");
		sql.append("		       VM.GROUP_CODE, \n");
		sql.append("			       VM.GROUP_ID, \n");
		sql.append("		       VM.GROUP_NAME, \n");
		sql.append("		       VM.MODEL_YEAR, \n");
		sql.append("		       VM.COLOR_CODE, \n");
		sql.append("	       VM.COLOR_NAME, \n");
		sql.append("	       VM.TRIM_CODE, \n");
		sql.append("	       VM.TRIM_NAME, \n");
		sql.append("	       o.ORDER_TYPE,\n");
		sql.append("COALESCE((SELECT DISTINCT REMARK FROM TT_RESOURCE_REMARK WHERE VIN=o.VIN),0) REMARK,\n");
		sql.append("     (SELECT DISTINCT OTHER_REMARK FROM TT_RESOURCE_REMARK WHERE VIN=V.VIN) OTHER_REMARK, \n");
		sql.append("		   V.VEHICLE_USAGE,       V.RETAIL_PRICE \n");
		sql.append("	  FROM   (" + getVwMaterialSql() + ") vm,TT_VS_ORDER o,TM_VEHICLE_dec V \n");
		sql.append("	 WHERE  1=1 AND o.IS_DEL <> '1' AND O.VIN = V.VIN \n");
		sql.append("	 AND  V.MATERIAL_ID = vm.MATERIAL_ID   \n");
		sql.append("	   AND  VM.GROUP_TYPE=" + DictCodeConstants.GROUP_TYPE_IMPORT + "\n");
		sql.append("			 AND  o.DEALER_ID =" + loginInfo.getDealerId() + " AND  o.order_type!="
				+ OemDictCodeConstants.ORDER_TYPE_01 + "\n");
		sql.append("			 AND  o.order_status = " + OemDictCodeConstants.ORDER_STATUS_07 + "\n");
		sql.append("			 AND  vm.MODEL_YEAR>'2012'");
		if (brandId != null && !"".equals(brandId)) {
			sql.append(" AND VM.BRAND_ID in( '" + brandId + "') ");
		}
		if (seriesName != null && !"".equals(seriesName)) {
			sql.append(" AND VM.SERIES_ID in( '" + seriesName + "') ");
		}

		if (groupName != null && !"".equals(groupName)) {
			sql.append(" AND VM.GROUP_ID in( '" + groupName + "') ");
		}

		if (modelYear != null && !"".equals(modelYear)) {
			sql.append(" AND VM.MODEL_YEAR in( '" + modelYear + "') ");
		}
		if (color != null && !"".equals(color)) {
			sql.append(" AND VM.COLOR_CODE in( '" + color + "') ");
		}
		if (trimCode != null && !"".equals(trimCode)) {
			sql.append(" AND VM.TRIM_CODE in( '" + trimCode + "') ");
		}
		if (vin != null && !"".equals(vin)) {
			vin = vin.replaceAll("\\^", "\n");
			vin = vin.replaceAll("\\,", "\n");
			sql.append(getVinsAuto(vin, "o"));
		}

		if (remark != null && !"".equals(remark)) {
			sql.append(" AND V.REMARK in( '" + remark + "') ");
		}

		sql.append("	 ) t\n		 WHERE 1=1\n");
		if (orderType != null && !"".equals(orderType)) {
			sql.append(" AND t.ORDER_TYPE in( '" + orderType + "') ");
		}
		if (remark != null && !"".equals(remark)) {
			sql.append(" AND t.REMARK in( '" + remark + "') ");
		}
		return sql.toString();
	}

	public Map<String, Object> findStockResourceById(Long commonId) {
		StringBuffer sql = new StringBuffer();
		sql.append(
				"select TVCR.COMMON_ID,VM.GROUP_ID,TV.VIN,VM.BRAND_CODE,VM.SERIES_NAME,VM.GROUP_NAME,VM.MODEL_YEAR,VM.COLOR_NAME,VM.TRIM_NAME\n");
		sql.append("	from TT_VS_COMMON_RESOURCE    TVCR,\n");
		sql.append("		 TM_VEHICLE_dec               TV,\n");
		sql.append("		 (" + getVwMaterialSql() + ")              VM\n");
		sql.append("	where  TVCR.VEHICLE_ID = TV.VEHICLE_ID\n");
		sql.append("	  and  TV.MATERIAL_ID = VM.MATERIAL_ID\n");
		sql.append("      and  TVCR.COMMON_ID=" + commonId);
		Map<String, Object> findAll = OemDAOUtil.findFirst(sql.toString(), null);

		return findAll;
	}

	public List<Map> findAlreadSubmited(String vin) {
		String sql = "select 1 from TT_VS_ORDER TVO,TT_VS_COMMON_RESOURCE TVCR where TVO.IS_DEL <> '1' AND TVO.COMMONALITY_ID = TVCR.COMMON_ID and TVO.vin='"
				+ vin + "' and TVO.ORDER_STATUS <" + OemDictCodeConstants.ORDER_STATUS_08;
		List<Map> list = OemDAOUtil.findAll(sql, null);// 逻辑删除过滤条件IS_DEL

		return list;

	}

	public Map<String, Object> findDealerOrders(Long groupId, LoginInfoDto loginInfo) {
		Date now = new Date();
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		String monthbegin = df.format(DateUtil.getFirstDayOfMonth(now));
		String monthend = df.format(DateUtil.getLastDayOfMonth(now));
		StringBuffer sql = new StringBuffer();
		sql.append("select count(*) TOTAL\n");
		sql.append("   from  TT_VS_ORDER                TVO,\n");
		sql.append("  	      TM_VEHICLE_dec                 TV,\n");
		sql.append("  		  (" + getVwMaterialSql() + ")                 VM,\n");
		sql.append("  		  TM_VHCL_MATERIAL_GROUP     TVMG3,\n");
		sql.append("  		  TM_VHCL_MATERIAL_GROUP     TVMG4\n");
		sql.append("   where TVO.IS_DEL <> '1' AND TVO.VIN = TV.VIN\n");
		sql.append(" 	  AND TV.MATERIAL_ID = VM.MATERIAL_ID\n");
		sql.append("  	  AND TVMG4.PARENT_GROUP_ID = TVMG3.GROUP_ID\n");
		sql.append(" 	  AND VM.GROUP_ID = TVMG4.GROUP_ID\n");
		sql.append("  	  AND TVMG4.GROUP_LEVEL=4\n");
		sql.append(" 	  AND TVMG3.GROUP_LEVEL=3\n");
		sql.append("  	  AND TVO.DEALER_ID=" + loginInfo.getDealerId() + "\n");
		sql.append("     AND TVO.ORDER_STATUS not in(" + OemDictCodeConstants.ORDER_STATUS_08 + ","
				+ OemDictCodeConstants.ORDER_STATUS_09 + ")\n");
		sql.append(
				"     AND not exists (select 1 from TT_VS_ORDER  p where p.IS_DEL <> '1' AND p.VIN=TV.VIN and p.ORDER_TYPE=20831003 and p.ORDER_STATUS=20071007)\n");
		sql.append("     AND TVO.ORDER_DATE >= '" + monthbegin + "'\n");
		sql.append("     AND TVO.ORDER_DATE <= '" + monthend + " 23:59:59'\n");
		sql.append("     AND TVMG3.PARENT_GROUP_ID=" + groupId + "\n");
		sql.append("     AND  TVO.ORDER_TYPE =" + OemDictCodeConstants.ORDER_TYPE_01 + "\n");

		Map map = OemDAOUtil.findFirst(sql.toString(), null);

		return map;
	}

	public List<Map> findMonthPlan(Long materialId, LoginInfoDto loginInfo) {
		StringBuffer sql = new StringBuffer();
		sql.append(
				"select COALESCE(t2.SALE_AMOUNT,0) SALE_AMOUNT,COALESCE(t2.HAS_AMOUNT,0) HAS_AMOUNT,t2.MATERIAL_GROUPID,t2.DETAIL_ID,t1.PARENT_GROUP_ID\n");
		sql.append("     from (select distinct TVMG3.PARENT_GROUP_ID\n");
		sql.append("		  	   from  	  (" + getVwMaterialSql() + ")                      VM,\n");
		sql.append("					 TM_VHCL_MATERIAL_GROUP          TVMG3,\n");
		sql.append(" 					 TM_VHCL_MATERIAL_GROUP          TVMG4\n");
		sql.append("			   where  TVMG4.PARENT_GROUP_ID = TVMG3.GROUP_ID\n");
		sql.append("  				 and  VM.GROUP_ID = TVMG4.GROUP_ID\n");
		sql.append("  				 and  TVMG4.GROUP_LEVEL=4\n");
		sql.append(" 				 and  TVMG3.GROUP_LEVEL=3\n");
		sql.append(" 				 and  VM.GROUP_ID=" + materialId + ")   t1\n");
		sql.append(" 	left join (select TVMPD.SALE_AMOUNT,TVMPD.HAS_AMOUNT,TVMPD.MATERIAL_GROUPID,TVMPD.DETAIL_ID\n");
		sql.append(" 				  from TT_VS_MONTHLY_PLAN              TVMP,\n");
		sql.append(" 					   TT_VS_MONTHLY_PLAN_DETAIL       TVMPD\n");
		sql.append(" 				  where  TVMP.PLAN_ID = TVMPD.PLAN_ID\n");
		sql.append("					and  TVMP.PLAN_YEAR=year(current_date)\n");
		sql.append("  					and  TVMP.PLAN_MONTH=month(current_date)\n");
		sql.append(" 	   				and  TVMP.PLAN_TYPE=" + OemDictCodeConstants.TARGET_TYPE_01 + "\n");
		sql.append(
				" 	   				and  TVMP.PLAN_VER=(select max(PLAN_VER) from TT_VS_MONTHLY_PLAN where PLAN_YEAR=year(current_date) and PLAN_MONTH=month(current_date) and PLAN_TYPE="
						+ OemDictCodeConstants.TARGET_TYPE_01 + " and DEALER_ID=" + loginInfo.getDealerId() + ")\n");
		sql.append(" 	   				and  TVMP.DEALER_ID=" + loginInfo.getDealerId() + ")   t2\n");
		sql.append("	on t1.PARENT_GROUP_ID=t2.MATERIAL_GROUPID\n");
		System.out.println(sql.toString());
		List<Map> list = OemDAOUtil.findAll(sql.toString(), null);
		Date now = new Date();
		StringBuffer sql2 = new StringBuffer();
		sql2.append("select count(*) TOTAL\n");
		sql2.append("   from  TT_VS_ORDER                TVO,\n");
		sql2.append("  	      TM_VEHICLE_dec                 TV,\n");
		sql2.append("  		  (" + getVwMaterialSql() + ")                VM,\n");
		sql2.append("  		  TM_VHCL_MATERIAL_GROUP     TVMG3,\n");
		sql2.append("  		  TM_VHCL_MATERIAL_GROUP     TVMG4\n");
		sql2.append("   where TVO.IS_DEL <> '1' AND TVO.VIN = TV.VIN\n");// 逻辑删除过滤条件IS_DEL
		sql2.append(" 	  AND TV.MATERIAL_ID = VM.MATERIAL_ID\n");
		sql2.append("  	  AND TVMG4.PARENT_GROUP_ID = TVMG3.GROUP_ID\n");
		sql2.append(" 	  AND VM.GROUP_ID = TVMG4.GROUP_ID\n");
		sql2.append("  	  AND TVMG4.GROUP_LEVEL=4\n");
		sql2.append(" 	  AND TVMG3.GROUP_LEVEL=3\n");
		sql2.append("  	  AND TVO.DEALER_ID=" + loginInfo.getDealerId() + "\n");
		sql2.append("     AND TVO.ORDER_STATUS not in(20071008,20071009)\n");
		sql2.append(
				"     AND not exists (select 1 from TT_VS_ORDER  p where p.IS_DEL <> '1' AND p.VIN=TV.VIN and p.ORDER_TYPE="
						+ OemDictCodeConstants.ORDER_TYPE_03 + " and p.ORDER_STATUS="
						+ OemDictCodeConstants.ORDER_STATUS_07 + ")\n");
		sql2.append("     AND TVO.ORDER_DATE >= date_format('" + DateUtil.getFirstDayOfMonth(now) + "','%y-%m-%d')\n");
		sql2.append("     AND TVO.ORDER_DATE <= date_format('" + DateUtil.getLastDayOfMonth(now)
				+ " 23:59:59','%y-%m-%d %h:%i:%s')\n");
		sql2.append("     AND TVMG3.PARENT_GROUP_ID=" + list.get(0).get("PARENT_GROUP_ID") + "\n");
		sql2.append("     AND  TVO.ORDER_TYPE =" + OemDictCodeConstants.ORDER_TYPE_01 + "\n");

		List<Map> list2 = OemDAOUtil.findAll(sql2.toString(), null);

		return list;
	}

	public List<Map> findIsSubmited(String vin) {
		List<Map> list1 = new ArrayList<Map>();
		String sql = "select 1 from TT_VS_ORDER where IS_DEL <> '1' AND vin='" + vin + "' and ORDER_STATUS<"
				+ OemDictCodeConstants.ORDER_STATUS_08;
		List<Map> list = OemDAOUtil.findAll(sql.toString(), null);
		if (null != list && list.size() > 0) {
			return list;
		} else {
			return list1;
		}
	}

	public List<Map> findIsCancelOrder(String vin) {

		List<Map> list3 = new ArrayList<Map>();
		String sql1 = "select 1 from TMP_IMPORT_STATUS where STATUS=" + OemDictCodeConstants.TMP_IMPORT_STATUS_01;
		List<Map> list1 = OemDAOUtil.findAll(sql1, null);
		String sql2 = "select 1 from TT_VS_UPLOAD_COMMON_RESOURCE where vin='" + vin + "'";
		List<Map> list2 = OemDAOUtil.findAll(sql2, null);

		if (null != list1 && null != list2 && list1.size() > 0 && list2.size() > 0) {
			return list1;
		} else {
			return list3;
		}

	}

	public Map findOrderTepy(String commonId) {
		String sql = "select t1.COMMON_ID,ORDER_TYPE from TT_VS_COMMON_RESOURCE t1, tm_vehicle_dec t2 , tt_vs_order t3 where t1.VEHICLE_ID = t2.VEHICLE_ID and t2.VIN = t3.VIN and t1.COMMON_ID ='"
				+ commonId + "'";
		Map map = OemDAOUtil.findFirst(sql, null);
		System.out.println(sql);
		return map;

	}

	/**
	 * 销售组合(现货)
	 * 
	 * @param vin
	 * @return
	 */
	public Map<String, Object> checkSaleGroupSetting(String vin, LoginInfoDto loginInfo) {
		Map<String, Object> map = new HashMap<>();
		String said = "0";
		map.put("SALES_GROUP_ID", said);
		Date date = new Date();
		LazyList<TtOrderSaleGroupPO> list = TtOrderSaleGroupPO.findBySQL(
				"select * from TT_ORDER_SALE_GROUP where GTYPE=0 and STATUS=?", OemDictCodeConstants.STATUS_ENABLE);
		a: for (int i = 0; i < list.size(); i++) {
			TtOrderSaleGroupPO tPO = list.get(i);
			StringBuffer sql = new StringBuffer();
			sql.append("select v.* from TM_VEHICLE_DEC t,(" + getVwMaterialSql() + ") v \n");
			sql.append("   where t.MATERIAL_ID=v.MATERIAL_ID\n");
			sql.append("     and t.vin='" + vin + "'\n");
			sql.append("	 and v.MATERIAL_ID in(select MATERIAL_ID from (" + getVwMaterialSql() + ")vm where 1=1 ");
			sql.append(tPO.get("BRAND_CODE") != null ? " and vm.BRAND_CODE='" + tPO.get("BRAND_CODE") + "'" : "");
			sql.append(tPO.get("SERIES_NAME") != null ? " and vm.SERIES_NAME='" + tPO.get("SERIES_NAME") + "'" : "");
			sql.append(tPO.get("GROUP_NAME") != null ? " and vm.GROUP_NAME='" + tPO.get("GROUP_NAME") + "'" : "");
			sql.append(tPO.get("MODEL_YEAR") != null ? " and vm.MODEL_YEAR='" + tPO.get("MODEL_YEAR") + "'" : "");
			sql.append(tPO.get("COLOR_NAME") != null ? " and vm.COLOR_NAME='" + tPO.get("COLOR_NAME") + "'" : "");
			sql.append(tPO.get("TRIM_NAME") != null ? " and vm.TRIM_NAME='" + tPO.get("TRIM_NAME") + "'" : "");
			sql.append(")");
			List<Map> l = OemDAOUtil.findAll(sql.toString(), null);
			// 如果车辆在组合车辆中
			if (l.size() > 0) {
				String sales_group_id = tPO.get("SALES_GROUP_ID").toString();
				LazyList<TtOrderSaleGroupPO> ll = TtOrderSaleGroupPO.findBySQL(
						"select * from TT_ORDER_SALE_GROUP where GTYPE=0 and STATUS=? and PARENT_GROUP_ID=?",
						OemDictCodeConstants.STATUS_ENABLE, Integer.parseInt(sales_group_id));
				if (ll.size() > 0) {
					for (int j = 0; j < ll.size(); j++) {
						TtOrderSaleGroupPO ttPO = ll.get(j);
						StringBuffer sql2 = new StringBuffer();
						sql2.append("select count(*) total from TT_VS_ORDER where  1=1 AND IS_DEL <> '1' \n");// 逻辑删除过滤条件IS_DEL
						sql2.append("	    and DEALER_ID=" + loginInfo.getDealerId() + "\n");
						sql2.append("		and ORDER_TYPE=" + OemDictCodeConstants.ORDER_TYPE_01 + "\n");
						sql2.append("       and DEAL_ORDER_AFFIRM_DATE between '" + DateUtil.getFirstDayOfMonth(date)
								+ "' and '" + DateUtil.getLastDayOfMonth(date) + " 23:59:59.999' \n");
						sql2.append("		and ORDER_STATUS <" + OemDictCodeConstants.ORDER_STATUS_08 + "\n");
						sql2.append("	    and MATERAIL_ID  in(select MATERIAL_ID from (" + getVwMaterialSql()
								+ ") where 1=1 ");
						sql2.append(ttPO.get("BRAND_CODE") != null ? " and BRAND_CODE='" + ttPO.get("BRAND_CODE") + "'"
								: "");
						sql2.append(ttPO.get("SERIES_NAME") != null
								? " and SERIES_NAME='" + ttPO.get("SERIES_NAME") + "'" : "");
						sql2.append(ttPO.get("GROUP_NAME") != null ? " and GROUP_NAME='" + ttPO.get("GROUP_NAME") + "'"
								: "");
						sql2.append(ttPO.get("MODEL_YEAR") != null ? " and MODEL_YEAR='" + ttPO.get("MODEL_YEAR") + "'"
								: "");
						sql2.append(ttPO.get("COLOR_NAME") != null ? " and COLOR_NAME='" + ttPO.get("COLOR_NAME") + "'"
								: "");
						sql2.append(
								ttPO.get("TRIM_NAME") != null ? " and TRIM_NAME='" + ttPO.get("TRIM_NAME") + "'" : "");
						sql2.append(")\n");

						List<Map> li = OemDAOUtil.findAll(sql2.toString(), null);
						String n = ttPO.get("Num").toString();
						String n2 = ttPO.get("Hasnum").toString();
						int num1 = Integer.parseInt(n);
						int num2 = Integer.parseInt(n2);
						int num = num1 - (new Integer(li.get(0).get("TOTAL").toString()) - num1 * num2);
						// 如果当月修改了绑定车型的数量,需要当月立即生效的加上下一句,如果绑定车型数量不允许修改则不需要
						if (num > num1) {
							num = num % num1;
						}
						if (num > 0) {
							map.put("NUM", num);
						} else {
							map.put("SALES_GROUP_ID", tPO.get("SALES_GROUP_ID"));
						}
					}
				}
				break a;
			}
		}
		return map;

	}

	public List<Map> findBandResource(String id, String num) {
		StringBuffer sql = new StringBuffer();
		sql.append("select  BRAND_CODE,SERIES_NAME,GROUP_NAME,MODEL_YEAR,COLOR_NAME,TRIM_NAME," + num + " as NUM\n");
		sql.append("  from TT_ORDER_SALE_GROUP where SALES_GROUP_ID=" + id);
		List<Map> list = OemDAOUtil.findAll(sql.toString(), null);
		return list;
	}

}
