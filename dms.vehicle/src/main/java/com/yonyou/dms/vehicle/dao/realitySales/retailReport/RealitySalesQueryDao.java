package com.yonyou.dms.vehicle.dao.realitySales.retailReport;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.yonyou.dms.framework.DAO.DAOUtil;
import com.yonyou.dms.framework.DAO.OemBaseDAO;
import com.yonyou.dms.framework.DAO.OemDAOUtil;
import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.framework.domain.LoginInfoDto;
import com.yonyou.dms.function.common.OemDictCodeConstants;
import com.yonyou.dms.function.utils.common.StringUtils;

@Repository
public class RealitySalesQueryDao extends OemBaseDAO{ 
	
	private static final Logger logger = LoggerFactory.getLogger(DAOUtil.class);

	/**
	 * 零售上报查询
	 * @param queryParam
	 * @param loginInfo
	 * @return
	 */
	public PageInfoDto queryList(Map<String, String> queryParam, LoginInfoDto loginInfo) {
		List<Object> params = new ArrayList<Object>();
		String sql = getQuerySql(queryParam,params,loginInfo);
		PageInfoDto pageInfoDto = OemDAOUtil.pageQuery(sql,params);
		return pageInfoDto;
	}

	/**
	 * 零售上报查询SQL拼接
	 * @param queryParam
	 * @param params
	 * @param loginInfo
	 * @return
	 */
	private String getQuerySql(Map<String, String> queryParam, List<Object> params, LoginInfoDto loginInfo) {
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT M.SERIES_CODE,M.GROUP_CODE, -- 车款代码 \n");
		sql.append("       M.GROUP_NAME, -- 车款 \n");
		sql.append("       V.VIN, -- VIN号\n");
		sql.append("       M.COLOR_NAME, -- 颜色\n");
		sql.append("       DATE_FORMAT(V.STOCKOUT_DEALER_DATE,'%Y-%m-%d') AS STOCKOUT_DEALER_DATE, -- 车辆发运日期 \n");
		sql.append("       V.LIFE_CYCLE, -- 车辆状态 \n");
		sql.append("       O.DEALER_ID -- 经销商ID \n");
		sql.append("  FROM TM_VEHICLE_DEC V \n");
		sql.append("  INNER JOIN TT_VS_ORDER O ON O.VIN = V.VIN AND (O.ORDER_STATUS = "+ OemDictCodeConstants.ORDER_STATUS_01 + " \n");
		sql.append(" OR O.ORDER_STATUS = " + OemDictCodeConstants.ORDER_STATUS_02
						+ " OR O.ORDER_STATUS = " + OemDictCodeConstants.ORDER_STATUS_03 + "  \n");
		sql.append(" OR O.ORDER_STATUS = " + OemDictCodeConstants.ORDER_STATUS_04
						+ " OR O.ORDER_STATUS = " + OemDictCodeConstants.ORDER_STATUS_05 + "  \n");
		sql.append(" OR O.ORDER_STATUS = " + OemDictCodeConstants.ORDER_STATUS_06
						+ " OR O.ORDER_STATUS = " + OemDictCodeConstants.ORDER_STATUS_07 + "  \n");
		sql.append(" OR O.ORDER_STATUS = " + OemDictCodeConstants.SALE_ORDER_TYPE_01
						+ " OR O.ORDER_STATUS = " + OemDictCodeConstants.SALE_ORDER_TYPE_02
						+ "  \n");
		sql.append(" OR O.ORDER_STATUS = " + OemDictCodeConstants.SALE_ORDER_TYPE_03
						+ " OR O.ORDER_STATUS = " + OemDictCodeConstants.SALE_ORDER_TYPE_04
						+ "  \n");
		sql.append(" OR O.ORDER_STATUS = " + OemDictCodeConstants.SALE_ORDER_TYPE_05
						+ " OR O.ORDER_STATUS = " + OemDictCodeConstants.SALE_ORDER_TYPE_06
						+ "  \n");
		sql.append(" OR O.ORDER_STATUS = " + OemDictCodeConstants.SALE_ORDER_TYPE_07
						+ " OR O.ORDER_STATUS = " + OemDictCodeConstants.SALE_ORDER_TYPE_08
						+ "  \n");
		sql.append(" OR O.ORDER_STATUS = " + OemDictCodeConstants.SALE_ORDER_TYPE_09
						+ " OR O.ORDER_STATUS = " + OemDictCodeConstants.SALE_ORDER_TYPE_10
						+ "  \n");
		sql.append(" OR O.ORDER_STATUS = " + OemDictCodeConstants.SALE_ORDER_TYPE_11
						+ " OR O.ORDER_STATUS = " + OemDictCodeConstants.SALE_ORDER_TYPE_12
						+ ") \n");
		sql.append(" INNER JOIN ("+ getVwMaterialSql()+") M ON M.MATERIAL_ID = V.MATERIAL_ID  \n");
		sql.append(" WHERE V.DEALER_ID = " + loginInfo.getDealerId() + " \n");
		sql.append(" "+ control(" M.SERIES_ID ", loginInfo.getDealerSeriesIDs(),loginInfo.getPoseSeriesIDs()) + " \n");
		sql.append("   AND V.LIFE_CYCLE =  " + OemDictCodeConstants.LIF_CYCLE_04 + " \n");
		sql.append("   AND NOT EXISTS (SELECT 1 FROM TT_VS_VEHICLE_TRANSFER VT \n");
		sql.append("         				 WHERE VT.VEHICLE_ID = V.VEHICLE_ID \n");
		sql.append("       					 AND (VT.CHECK_STATUS = '"+ OemDictCodeConstants.TRANSFER_CHECK_STATUS_01 + "' \n");
		sql.append("          				 OR VT.CHECK_STATUS = '"+ OemDictCodeConstants.TRANSFER_CHECK_STATUS_05 + "'))\n");
		sql.append("   AND ((NOT EXISTS (SELECT 1 FROM TT_VS_NVDR WHERE VIN = V.VIN)) \n");
		sql.append("   OR (EXISTS (SELECT 1 FROM TT_VS_NVDR WHERE VIN = V.VIN AND IS_DEL = 1))) \n"); // 无效的可以查询出来
		//品牌
		if(!StringUtils.isNullOrEmpty(queryParam.get("brandCode"))){
			sql.append(" and M.BRAND_ID  = ? \n");
			params.add(queryParam.get("brandCode"));
		}
		//车系
		if(!StringUtils.isNullOrEmpty(queryParam.get("seriesName"))){
			sql.append(" and M.SERIES_ID  = ? \n");
			params.add(queryParam.get("seriesName"));
		}
		//车款
		if(!StringUtils.isNullOrEmpty(queryParam.get("groupName"))){
			sql.append(" and M.GROUP_ID  = ? \n");
			params.add(queryParam.get("groupName"));
		}
		//年款
		if(!StringUtils.isNullOrEmpty(queryParam.get("modelYear"))){
			sql.append(" and M.MODEL_YEAR  = '"+queryParam.get("modelYear")+"' \n");
		}
		//颜色
		if(!StringUtils.isNullOrEmpty(queryParam.get("colorName"))){
			sql.append(" and M.COLOR_CODE  = '"+queryParam.get("colorName")+"' \n");
		}
		//内饰
		if(!StringUtils.isNullOrEmpty(queryParam.get("trimName"))){
			sql.append(" and M.TRIM_CODE  = '"+queryParam.get("trimName")+"' \n");
		}
		//VIN
		if (!StringUtils.isNullOrEmpty(queryParam.get("vin"))) {
			String vin = queryParam.get("vin");
			vin = vin.replaceAll("\\^\\^", "\n");
			vin = vin.replaceAll("\\,", "\n");
			sql.append("      " +  getVinsAuto(vin, "V"));
		}
		//车辆运发日期
		if(!StringUtils.isNullOrEmpty(queryParam.get("beginDate"))){
			sql.append("   AND DATE_FORMAT(V.STOCKOUT_DEALER_DATE,'%Y-%m-%d') >= '"+queryParam.get("beginDate")+"'");
			sql.append("   AND DATE_FORMAT(V.STOCKOUT_DEALER_DATE,'%Y-%m-%d') <= '"+queryParam.get("endDate")+"'");
		}
		
		logger.debug(sql.toString()+"  "+ params.toString());
		return sql.toString();
	}

	/**
	 * 详细sql查询
	 * @param id DEALER_ID
	 * @param loginInfo
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public List<Map> queryDetail(String id) {
		String sql = getDetailQuerySql(id);
		List<Map> listMap= OemDAOUtil.findAll(sql, null);
		return listMap;
	}

	/**
	 * 详细sql组装
	 * @param id
	 * @param loginInfo
	 * @return
	 */
	private String getDetailQuerySql(String id) {
		StringBuffer sql = new StringBuffer();
		sql.append("select TD.DEALER_ID,TD.ZIP_CODE,TR.REGION_NAME,IFNULL((select REGION_NAME from TM_REGION where TD.CITY_ID=REGION_CODE),'') REGION_NAME_A\n");
		sql.append("   from  TM_DEALER   TD\n");
		sql.append("   left join  TM_REGION_DCS   TR\n");
		sql.append("   on TD.PROVINCE_ID = TR.REGION_CODE");
		sql.append("   where TD.DEALER_ID="+id+"\n");
		return sql.toString();
	}

}
