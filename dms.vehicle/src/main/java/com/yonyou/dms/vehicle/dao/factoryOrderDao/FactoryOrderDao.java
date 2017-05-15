package com.yonyou.dms.vehicle.dao.factoryOrderDao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.yonyou.dms.framework.DAO.OemBaseDAO;
import com.yonyou.dms.framework.DAO.OemDAOUtil;
import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.function.common.DictCodeConstants;

/**
 * 
 * @author lianxinglu 2017-01-19
 */
@Repository
// 工厂订单查询
public class FactoryOrderDao extends OemBaseDAO {

	public PageInfoDto orderQuery(Map<String, String> queryParam) {
		List<Object> params = new ArrayList<Object>();
		String sql = getQuerySql(queryParam, params);
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

		String brandId = queryParam.get("brandName");

		String seriesName = queryParam.get("seriesName");
		String groupName = queryParam.get("groupName");
		String modelYear = queryParam.get("modelYear");
		String color = queryParam.get("color");
		String trimCode = queryParam.get("trimCode");
		String vin = queryParam.get("vin");
		String invoiceNumber = queryParam.get("invoiceNumber");
		String vehicleDate1 = queryParam.get("vehicleDate1");
		String vehicleDate2 = queryParam.get("vehicleDate2");
		String vehicleDate3 = queryParam.get("vehicleDate3");
		String vehicleDate4 = queryParam.get("vehicleDate4");
		String vehicleNode = queryParam.get("vehicleNode");

		StringBuffer sql = new StringBuffer();

		sql.append("  SELECT * from ( \n");
		sql.append("  SELECT f.VIN, \n");
		sql.append("         M.BRAND_CODE, M.BRAND_ID, \n");
		sql.append("         M.BRAND_NAME, \n");// 品牌
		sql.append("         M.MODEL_CODE, \n");// 车型
		sql.append("         M.MODEL_NAME, \n");// 车型描述
		sql.append("         M.MODEL_YEAR, \n");// 车型年
		sql.append("         M.COLOR_CODE, \n");// 颜色
		sql.append("         M.COLOR_NAME, \n");// 颜色描述
		sql.append("         M.GROUP_ID, \n");
		sql.append("         M.SERIES_ID, \n");
		sql.append("         M.TRIM_CODE, \n");// 内饰
		sql.append("         M.TRIM_NAME, \n");// TRIM_NAME内饰描述
		sql.append("         M.FACTORY_OPTIONS, \n");// 工厂设置
		sql.append("         M.STANDARD_OPTION, \n");// 标准设置
		sql.append("         M.LOCAL_OPTION, \n");// 本地设置
		sql.append("         (SELECT CODE_DESC \n");
		sql.append("            FROM TC_CODE_DCS \n");
		sql.append("           WHERE CODE_ID = F.NODE) \n");// node车辆节点
		sql.append("            NODE, \n");
		sql.append("         (SELECT CODE_DESC \n");
		sql.append("            FROM TC_CODE_DCS \n");
		sql.append("           WHERE CODE_ID = F.PRIMARY_STATUS) \n");// primary_status订单主状态
		sql.append("            PRIMARY_STATUS, \n");
		sql.append("         (SELECT CODE_DESC \n");
		sql.append("            FROM TC_CODE_DCS \n");
		sql.append("           WHERE CODE_ID = F.SECONDARY_STATUS) \n");// secondary_status订单第二状态
		sql.append("            SECONDARY_STATUS, \n");
		sql.append("         F.INVOICE_NUMBER, \n");// INVOICE_NUMBER发票号
		sql.append("         date_format (F.INVOICE_DATE, '%y-%m-%d') INVOICE_DATE, \n");// invoice_date开票日期
		sql.append("         date_format (F.SHIP_DATE, '%y-%m-%d') SHIP_DATE, \n");// ship_date发运日期
		sql.append("         date_format (F.ETA, '%y-%m-%d') ETA, \n");// ETA
		sql.append("         date_format (tv.EXPECT_PORT_DATE,'%y-%m-%d') EXPECT_PORT_DATE, \n");// expect_port_date预计到岗日期
		sql.append("         F.NODE FNODE, \n");
		sql.append("         TV.VEHICLE_ID, \n");
		sql.append(" concat(COALESCE((SELECT (case when status \n");
		sql.append(" " + "=" + DictCodeConstants.COMMON_RESOURCE_STATUS_02 + "\n");
		sql.append("then '" + DictCodeConstants.COMMON_RESOURCE_STATUS_02 + "' else '"
				+ DictCodeConstants.COMMON_RESOURCE_STATUS_01 + "' end) \n");
		sql.append(" FROM TT_VS_COMMON_RESOURCE tvc\n");
		sql.append(
				" WHERE common_id = (select max(common_id) from TT_VS_COMMON_RESOURCE where vehicle_id = tv.vehicle_id)), "
						+ DictCodeConstants.COMMON_RESOURCE_STATUS_01 + ")) \n");
		sql.append(" FLAG \n");// 是否下发
		sql.append("    FROM tt_vs_factory_order f, (" + getVwMaterialSql() + ") m, TM_VEHICLE_DEC tv \n");
		sql.append("   WHERE  1=1  \n");
		//
		sql.append(" AND  m.GROUP_TYPE=" + DictCodeConstants.GROUP_TYPE_IMPORT + "\n");
		// end

		sql.append(" AND tv.material_id = m.material_id AND F.VIN = TV.VIN and tv.life_cycle = "
				+ DictCodeConstants.LIF_CYCLE_01 + "\n");
		sql.append("   ) T1 where 1=1\n");
		if (null != queryParam.get("flag") && !queryParam.get("flag").equals("")) {
			// String flag =
			// OemDictCodeConstantsUtils.getIf_type(Integer.parseInt(queryParam.get("flag")));
			sql.append(" AND T1.flag = '" + queryParam.get("flag") + "' ");
		}
		// 品牌名不为空
		if (brandId != null && !"".equals(brandId)) {
			sql.append(" AND T1.BRAND_ID in( '" + brandId + "') ");
		}
		if (seriesName != null && !"".equals(seriesName)) {
			sql.append(" AND T1.SERIES_ID in( '" + seriesName + "') ");
		}
		if (groupName != null && !"".equals(groupName)) {
			sql.append(" AND T1.GROUP_ID in( '" + groupName + "') ");
		}
		if (modelYear != null && !"".equals(modelYear)) {
			sql.append(" AND T1.MODEL_YEAR in( '" + modelYear + "') ");
		}
		if (color != null && !"".equals(color)) {
			sql.append(" AND T1.COLOR_Code in( '" + color + "') ");
		}
		if (trimCode != null && !"".equals(trimCode)) {
			sql.append(" AND T1.TRIM_Code in ('" + trimCode + "') ");
		}
		if (vehicleNode != null && !"".equals(vehicleNode)) {
			sql.append("    AND T1.FNODE in ('" + vehicleNode + "') ");
		}
		if (vin != null && !"".equals(vin)) {
			sql.append(" AND T1.VIN like " + "'%" + vin + "%'" + " ");
		}
		if (invoiceNumber != null && !"".equals(invoiceNumber)) {
			sql.append(" AND T1.INVOICE_NUMBER = '" + invoiceNumber + "' ");
		}
		if (vehicleDate1 != null && !"".equals(vehicleDate1)) {
			sql.append(" AND T1.SHIP_DATE >= '" + vehicleDate1 + " 00:00:00'");
		}
		if (vehicleDate2 != null && !"".equals(vehicleDate2)) {
			sql.append(" AND T1.SHIP_DATE <= '" + vehicleDate2 + " 23:59:59' ");
		}
		if (vehicleDate3 != null && !"".equals(vehicleDate3)) {
			sql.append(" AND T1.ETA >= '" + vehicleDate3 + " 00:00:00' ");
		}
		if (vehicleDate4 != null && !"".equals(vehicleDate4)) {
			sql.append(" AND T1.ETA <='" + vehicleDate4 + " 23:59:59'");
		}
		System.out.println(sql.toString());
		return sql.toString();
	}

	/**
	 * 工厂订单下载
	 * 
	 * @param queryParam
	 * @return
	 */
	public List<Map> findFactroyOrderDownload(Map<String, String> queryParam) {
		List<Object> params = new ArrayList<Object>();
		String sql = getQuerySql(queryParam, params);
		List<Map> orderList = OemDAOUtil.downloadPageQuery(sql, null);
		return orderList;
	}

}
