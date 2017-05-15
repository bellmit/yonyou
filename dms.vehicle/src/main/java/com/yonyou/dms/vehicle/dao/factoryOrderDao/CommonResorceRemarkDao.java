package com.yonyou.dms.vehicle.dao.factoryOrderDao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.yonyou.dms.common.Util.OemDictCodeConstantsUtils;
import com.yonyou.dms.framework.DAO.OemBaseDAO;
import com.yonyou.dms.framework.DAO.OemDAOUtil;
import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.function.common.OemDictCodeConstants;
import com.yonyou.dms.function.utils.common.CommonUtils;

/**
 * 
 * @author lianxinglu
 *
 */
@Repository
@SuppressWarnings("all")
public class CommonResorceRemarkDao extends OemBaseDAO {
	// 查询
	public PageInfoDto getVehicleList(Map<String, String> queryParam) {
		List<Object> params = new ArrayList<Object>();
		String sql = getQuerySql(queryParam, params);
		System.out.println(sql);
		PageInfoDto pageInfoDto = OemDAOUtil.pageQuery(sql, params);

		return pageInfoDto;
	}

	// 下载
	public List<Map> download(Map<String, String> queryParam) {
		List<Object> params = new ArrayList<Object>();
		String sql = getQuerySql(queryParam, params);
		List<Map> download = OemDAOUtil.downloadPageQuery(sql, null);
		return download;
	}

	/**
	 * SQL组装
	 * 
	 * @param queryParam
	 * @param params
	 * @return
	 */
	private String getQuerySql(Map<String, String> queryParam, List<Object> params) {

		String brandId = CommonUtils.checkNull(queryParam.get("brandId"));
		String groupName = CommonUtils.checkNull(queryParam.get("groupName"));
		String colorName = CommonUtils.checkNull(queryParam.get("colorName"));
		String modelYear = CommonUtils.checkNull(queryParam.get("modelYear"));
		String seriesName = CommonUtils.checkNull(queryParam.get("seriesName"));
		String trimCode = CommonUtils.checkNull(queryParam.get("trimCode"));
		String vin = CommonUtils.checkNull(queryParam.get("vin"));
		String isSuppor = CommonUtils.checkNull(queryParam.get("Support"));
		String remark1 = CommonUtils.checkNull(queryParam.get("remark1"));

		// IS_SUPPORT默认值为0表示偶尔支持（否），IS_SUPPORT为1表示偶尔支持（是）
		StringBuffer sql = new StringBuffer();
		sql.append(
				"SELECT t4.* FROM (SELECT DISTINCT t3.*, (CASE  WHEN ALLOT.IS_SUPPORT =0  THEN '12631002' ELSE '12631001' END) AS IS_SUPPORT FROM ( SELECT t2.*,tu.NAME\n");
		sql.append(
				"		 FROM (SELECT t1.*,COALESCE(trr.REMARK,0) REMARK  ,(CASE WHEN trr.REMARK IN(11211010,11211016) THEN trr.OTHER_REMARK END) OTHER_REMARK,\n");
		sql.append(
				"		 (CASE  WHEN trr.IS_LOCK =0  THEN 10041002  ELSE 10041001 END) AS IS_LOCK,trr.UPDATE_DATE,trr.UPDATE_BY\n");
		sql.append("FROM (SELECT TV.VIN,    VM.MODEL_CODE,\n");
		sql.append(" VM.MODEL_NAME,\n");
		sql.append("VM.BRAND_CODE,\n");
		sql.append("VM.SERIES_CODE,VM.SERIES_ID,\n");
		sql.append("		 	VM.GROUP_NAME,VM.GROUP_ID,\n");
		sql.append("		 	VM.MODEL_YEAR,\n");
		sql.append("		  	VM.COLOR_NAME,\n");
		sql.append("		    VM.TRIM_NAME,\n");
		sql.append("			TV.VEHICLE_USAGE,\n");
		sql.append("		TV.VPC_PORT\n");
		sql.append("		   FROM  TM_VEHICLE_DEC                 	   TV,\n");
		sql.append("		  		(" + OemBaseDAO.getVwMaterialSql() + ")             	   VM\n");
		sql.append("		    WHERE TV.MATERIAL_ID = VM.MATERIAL_ID\n");
		sql.append("	      AND VM.GROUP_TYPE=" + OemDictCodeConstants.GROUP_TYPE_IMPORT + "\n");
		if (brandId != null && !"".equals(brandId)) {
			sql.append(" AND VM.BRAND_ID in( '" + brandId + "') ");
		}
		if (groupName != null && !"".equals(groupName)) {
			sql.append(" AND VM.VM.GROUP_ID in( '" + groupName + "') ");
		}
		if (seriesName != null && !"".equals(seriesName)) {
			sql.append(" AND VM.VM.SERIES_ID in( '" + seriesName + "') ");
		}
		if (modelYear != null && !"".equals(modelYear)) {
			sql.append(" AND VM.VM.MODEL_YEAR in( '" + modelYear + "') ");
		}
		if (trimCode != null && !"".equals(trimCode)) {
			sql.append(" AND VM.VM.TRIM_CODE in( '" + trimCode + "') ");
		}
		if (colorName != null && !"".equals(colorName)) {
			sql.append(" AND VM.VM.COLOR_CODE in( '" + colorName + "') ");
		}

		if (vin != null && !vin.equals("")) {
			vin = vin.replaceAll("\\^", "\n");
			vin = vin.replaceAll("\\,", "\n");
			sql.append("  " + OemBaseDAO.getVinsAuto(vin, "TV"));
		}

		sql.append("	)   t1\n");
		sql.append("	 	  LEFT JOIN TT_RESOURCE_REMARK   trr ON t1.VIN=trr.VIN)  t2\n");
		sql.append("	  LEFT JOIN TC_USER  tu ON t2.UPDATE_BY=tu.USER_ID\n");
		sql.append("	  WHERE 1=1\n");
		if (!remark1.equals("") && remark1 != null) {
			String s = "";
			if (remark1.indexOf(",") > 0) {
				s = "0,";
			}
			sql.append("   and t2.REMARK in(" + s + remark1 + ")\n");
		}

		sql.append("	 ) t3 LEFT JOIN TM_ALLOT_SUPPORT ALLOT ON t3.VIN=ALLOT.VIN) t4 WHERE 1=1\n");
		if (isSuppor != "" && isSuppor != null) {
			String isSupport = isSuppor.substring(0, 8);
			String isSupport1 = OemDictCodeConstantsUtils.getISSUPPORT(Integer.parseInt(isSupport));
			sql.append(" and t4.IS_SUPPORT = '" + isSupport + "'");
		}

		return sql.toString();
	}

	public List<Map> checkTableDump() {
		StringBuffer sql = new StringBuffer();
		sql.append("select p.ROW_NUMBER,p.VIN,p.REMARK\n");
		sql.append("   from TMP_COMMON_RESOURCE_REMARK   p\n");
		sql.append("   where p.REMARK_TYPE=" + OemDictCodeConstants.REMARK_COMMON);
		sql.append("     and exists (select 1 from TMP_COMMON_RESOURCE_REMARK t\n");
		sql.append(" 					where p.VIN=t.VIN\n");
		sql.append("   					  AND t.REMARK_TYPE=" + OemDictCodeConstants.REMARK_COMMON + "\n");
		sql.append(" 					  AND p.REMARK = t.REMARK\n");
		sql.append(" AND p.ROW_NUMBER <> t.ROW_NUMBER\n");
		sql.append("                )\n");
		sql.append("   order by ROW_NUMBER\n");
		List<Map> map = OemDAOUtil.findAll(sql.toString(), null);
		return map;
	}

	// 验证vin号是否存在
	public List<Map> checkVinExists() {
		StringBuffer sql = new StringBuffer();
		sql.append("select t.ROW_NUMBER,t.vin\n");
		sql.append("   from TMP_COMMON_RESOURCE_REMARK t\n");
		sql.append("   where t.REMARK_TYPE=" + OemDictCodeConstants.REMARK_COMMON);
		sql.append("     and not exists (select 1 from TM_VEHICLE_DEC where VIN=t.VIN)\n");
		List<Map> l = OemDAOUtil.findAll(sql.toString(), null);
		return l;
	}

	// 验证vin号是否在 ZGOR-车辆到港 到 ZDLD-经销商验收
	public List<Map> checkVins() {
		StringBuffer sql = new StringBuffer();
		sql.append("select t.ROW_NUMBER,t.vin\n");
		sql.append("   from TMP_COMMON_RESOURCE_REMARK t\n");
		sql.append("   where t.REMARK_TYPE=" + OemDictCodeConstants.REMARK_COMMON);
		sql.append(
				"    and not exists (select 1 from TM_VEHICLE_dec where VIN=t.VIN and NODE_STATUS in(select CODE_ID from tc_code_dcs where type = '1151' and num >= 5 and num <19))\n");

		List<Map> l = OemDAOUtil.findAll(sql.toString(), null);
		return l;
	}

	// 验证备注类型是否存在
	public List<Map> checkRemarkExists() {
		StringBuffer sql = new StringBuffer();
		sql.append("select t.ROW_NUMBER,t.REMARK\n");
		sql.append("   from TMP_COMMON_RESOURCE_REMARK t\n");
		sql.append("    where t.REMARK_TYPE=" + OemDictCodeConstants.REMARK_COMMON);
		sql.append("      and not exists (select 1 from TC_CODE_DCS where CODE_DESC=t.REMARK and type<>"
				+ OemDictCodeConstants.VEHICLE_USE + ")\n");

		List<Map> l = OemDAOUtil.findAll(sql.toString(), null);
		return l;
	}

	// 判断是否是进口车
	public List<Map> findIsNotImport() {
		String sql = "select * from TMP_COMMON_RESOURCE_REMARK tvuc where exists(select 1 from (" + getVwMaterialSql()
				+ ")  vm,TM_VEHICLE_dec tv where vm.MATERIAL_ID=tv.MATERIAL_ID and tv.VIN=tvuc.VIN and vm.GROUP_TYPE="
				+ OemDictCodeConstants.GROUP_TYPE_DOMESTIC + ")";
		return OemDAOUtil.findAll(sql.toString(), null);

	}

	public List<Map> checkVinDump() {
		StringBuffer sql = new StringBuffer();
		sql.append("select p.ROW_NUMBER,p.VIN\n");
		sql.append("   from TMP_COMMON_RESOURCE_REMARK   p\n");
		sql.append("   where p.REMARK_TYPE=" + OemDictCodeConstants.REMARK_COMMON);
		sql.append("     and exists (select 1 from TMP_COMMON_RESOURCE_REMARK t\n");
		sql.append(" 					where p.VIN=t.VIN\n");
		sql.append("   					  AND t.REMARK_TYPE=" + OemDictCodeConstants.REMARK_COMMON + "\n");
		sql.append(" 					  AND p.REMARK <> t.REMARK\n");
		sql.append(" AND p.ROW_NUMBER <> t.ROW_NUMBER\n");
		sql.append("                )\n");
		sql.append("   order by ROW_NUMBER\n");

		return OemDAOUtil.findAll(sql.toString(), null);
	}

	// 临时表回显
	public List<Map> importShowCon() {
		StringBuffer sql = new StringBuffer();
		sql.append("select * from TMP_COMMON_RESOURCE_REMARK where REMARK_TYPE=" + OemDictCodeConstants.REMARK_COMMON);
		return OemDAOUtil.findAll(sql.toString(), null);
	}

}
