package com.yonyou.dms.vehicle.dao.factoryOrderDao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;

import com.yonyou.dms.framework.DAO.OemBaseDAO;
import com.yonyou.dms.framework.DAO.OemDAOUtil;
import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.function.common.OemDictCodeConstants;

/**
 * 
 * @author lianxinglu
 */
@Repository
@SuppressWarnings("rawtypes")
public class OTDResourceRemarkDao extends OemBaseDAO {
	// 初始化查询
	public PageInfoDto remarkInit(Map<String, String> queryParam) {
		List<Object> params = new ArrayList<Object>();
		String sql = getQuerySql(queryParam, params);
		PageInfoDto pageInfoDto = OemDAOUtil.pageQuery(sql, params);
		return pageInfoDto;
	}

	// 下载
	public List<Map> download(Map<String, String> queryParam) {
		List<Object> params = new ArrayList<Object>();
		String sql = getQuerySql(queryParam, params);
		List<Map> list = OemDAOUtil.downloadPageQuery(sql, null);
		return list;
	}

	/**
	 * SQL组装
	 * 
	 * @param queryParam
	 * @param params
	 * @return
	 */
	private String getQuerySql(Map<String, String> queryParam, List<Object> params) {
		String brandId = queryParam.get("brandId");
		String seriesName = queryParam.get("seriesName");
		String groupName = queryParam.get("groupName");
		String modelYear = queryParam.get("modelYear");
		String color = queryParam.get("colorName");
		String trimName = queryParam.get("trimName");
		String remark = queryParam.get("remark");
		String vin = queryParam.get("vin");

		StringBuffer sql = new StringBuffer();
		sql.append("select t2.*,tu.NAME\n");
		sql.append(
				" from (select t1.*,COALESCE(trr.SPECIAL_REMARK,0) SPECIAL_REMARK,(CASE  WHEN trr.IS_LOCK =0  THEN 10041001  ELSE 10041002 END) AS IS_LOCK,trr.UPDATE_DATE,trr.UPDATE_BY\n");
		sql.append("	from (select TV.VIN,");
		sql.append("    VM.MODEL_CODE,\n");// 车型代码
		sql.append("    VM.MODEL_NAME,\n");// 车型描述
		sql.append("	VM.BRAND_CODE,\n");// 品牌
		sql.append("	VM.SERIES_CODE,\n");// 车系
		sql.append(" 	VM.GROUP_NAME,\n");// 物料
		sql.append(" 	VM.MODEL_YEAR,\n");// 车型年
		sql.append("  	VM.COLOR_NAME,\n");// 颜色
		sql.append("    VM.TRIM_NAME,\n");// 内饰描述
		sql.append("	TV.VEHICLE_USAGE\n");// 车辆用途
		sql.append("   from  TM_VEHICLE_dec                 	   TV,\n");
		sql.append("  		 (" + getVwMaterialSql() + ") 	   VM\n");
		sql.append("    where  TV.MATERIAL_ID = VM.MATERIAL_ID\n");
		// add by lsy 2015-7-22
		sql.append(" AND  VM.GROUP_TYPE=" + OemDictCodeConstants.GROUP_TYPE_IMPORT + "\n");
		// end
		// sql.append(" and tv.node_status in (select code_id from tc_code where
		// type = '1151' and num >= 5 and num <19) \n ");
		if (brandId != null && !"".equals(brandId)) {
			sql.append(" AND VM.BRAND_ID in( '" + brandId + "') ");
		}
		if (seriesName != null && !"".equals(seriesName)) {
			sql.append(" AND VM.VM.SERIES_ID in( '" + seriesName + "') ");
		}
		if (groupName != null && !"".equals(groupName)) {
			sql.append(" AND VM.VM.GROUP_ID in( '" + groupName + "') ");
		}

		if (color != null && !"".equals(color)) {
			sql.append(" AND VM.VM.COLOR_CODE in( '" + color + "') ");
		}

		if (modelYear != null && !"".equals(modelYear)) {
			sql.append(" AND VM.VM.MODEL_YEAR in( '" + modelYear + "') ");
		}
		if (trimName != null && !"".equals(trimName)) {
			sql.append(" AND VM.VM.TRIM_CODE in( '" + trimName + "') ");
		}

		if (vin != null && !vin.equals("")) {
			vin = vin.replaceAll("\\^", "\n");
			vin = vin.replaceAll("\\,", "\n");
			sql.append("  " + getVinsAuto(vin, "TV"));
		}
		sql.append(")   t1\n");
		sql.append(" 	  left join TT_RESOURCE_REMARK   trr on t1.VIN=trr.VIN)  t2\n");
		sql.append("  left join TC_USER  tu on t2.UPDATE_BY=tu.USER_ID\n");
		sql.append("  where 1=1\n");
		if (StringUtils.isNoneBlank(remark) && StringUtils.isNotEmpty(remark)) {
			String s = "";
			if (remark.indexOf(",") > 0) {
				s = "0,";
			}
			sql.append("   and t2.SPECIAL_REMARK in(" + s + remark + ")\n");
		}

		return sql.toString();

	}

	// 验证临时表重复数据
	public List<Map> checkTableDump() {
		StringBuffer sql = new StringBuffer();
		sql.append("select p.ROW_NUMBER,p.VIN,p.REMARK\n");
		sql.append("   from TMP_COMMON_RESOURCE_REMARK   p\n");
		sql.append("   where p.REMARK_TYPE=" + OemDictCodeConstants.REMARK_SPECIAL);
		sql.append("     and exists (select 1 from TMP_COMMON_RESOURCE_REMARK t\n");
		sql.append(" 					where p.VIN=t.VIN\n");
		sql.append("					  AND t.REMARK_TYPE=" + OemDictCodeConstants.REMARK_SPECIAL + "\n");
		sql.append(" 					  AND p.REMARK = t.REMARK\n");
		sql.append("  					  AND p.ROW_NUMBER <> t.ROW_NUMBER\n");
		sql.append("                )\n");
		sql.append("   order by ROW_NUMBER\n");
		return OemDAOUtil.findAll(sql.toString(), null);
	}

	// 验证临时表VIN码是否重复
	public List<Map> checkVinDump() {
		StringBuffer sql = new StringBuffer();
		sql.append("select p.ROW_NUMBER,p.VIN\n");
		sql.append("   from TMP_COMMON_RESOURCE_REMARK   p\n");
		sql.append("   where p.REMARK_TYPE=" + OemDictCodeConstants.REMARK_SPECIAL);
		sql.append("     and exists (select 1 from TMP_COMMON_RESOURCE_REMARK t\n");
		sql.append(" 					where p.VIN=t.VIN\n");
		sql.append("					  AND t.REMARK_TYPE=" + OemDictCodeConstants.REMARK_SPECIAL + "\n");
		sql.append(" 					  AND p.REMARK <> t.REMARK\n");
		sql.append("  					  AND p.ROW_NUMBER <> t.ROW_NUMBER\n");
		sql.append("                )\n");
		sql.append("   order by ROW_NUMBER\n");

		return OemDAOUtil.findAll(sql.toString(), null);
	}

	// 验证vin号是否存在
	public List<Map> checkVinExists() {
		StringBuffer sql = new StringBuffer();
		sql.append("select t.ROW_NUMBER,t.vin\n");
		sql.append("   from TMP_COMMON_RESOURCE_REMARK t\n");
		sql.append("   where t.REMARK_TYPE=" + OemDictCodeConstants.REMARK_SPECIAL + "\n");
		sql.append("     and not exists (select 1 from TM_VEHICLE_DEC where VIN=t.VIN)\n");
		return OemDAOUtil.findAll(sql.toString(), null);
	}

	// 验证备注类型是否存在
	public List<Map> checkRemarkExists() {
		StringBuffer sql = new StringBuffer();
		sql.append("select t.ROW_NUMBER,t.REMARK\n");
		sql.append("   from TMP_COMMON_RESOURCE_REMARK t\n");
		sql.append("    where t.REMARK_TYPE=" + OemDictCodeConstants.REMARK_SPECIAL + "\n");
		sql.append("      and not exists (select 1 from TC_CODE_DCS where CODE_DESC=t.REMARK and type="
				+ OemDictCodeConstants.OTD_REMARK + ")\n");

		return OemDAOUtil.findAll(sql.toString(), null);
	}

	// 验证是否为进口车

	public List<Map> chekJingkouExists() {
		StringBuffer sql = new StringBuffer();
		sql.append("select t.ROW_NUMBER,t.VIN\n");
		sql.append(" from TMP_COMMON_RESOURCE_REMARK t where \n");
		sql.append(" exists(select 1 from ("+getVwMaterialSql()+") vm,TM_VEHICLE_DEC tv \n");
		sql.append(" where vm.MATERIAL_ID=tv.MATERIAL_ID \n");
		sql.append(" and tv.VIN=t.VIN \n");
		sql.append(" and vm.GROUP_TYPE=" + OemDictCodeConstants.GROUP_TYPE_DOMESTIC + " )\n");
		return OemDAOUtil.findAll(sql.toString(), null);
	}

	public List<Map> isShow() {
		StringBuffer sql = new StringBuffer();
		sql.append("select * from TMP_COMMON_RESOURCE_REMARK where REMARK_TYPE=" + OemDictCodeConstants.REMARK_SPECIAL);
		return OemDAOUtil.findAll(sql.toString(), null);
	}

}
