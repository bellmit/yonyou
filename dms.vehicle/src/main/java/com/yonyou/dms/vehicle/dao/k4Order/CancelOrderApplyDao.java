package com.yonyou.dms.vehicle.dao.k4Order;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.yonyou.dms.framework.DAO.OemBaseDAO;
import com.yonyou.dms.framework.DAO.OemDAOUtil;
import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.framework.domain.LoginInfoDto;
import com.yonyou.dms.framework.util.bean.ApplicationContextHelper;
import com.yonyou.dms.function.utils.common.StringUtils;

/**
 * @author liujiming
 * @date 2017年3月10日
 */
@Repository
public class CancelOrderApplyDao extends OemBaseDAO {

	/**
	 * 撤单申请(经销商)查询
	 * 
	 * @param queryParam
	 * @return pageInfoDto
	 */
	public PageInfoDto getCancelOrderApplyList(Map<String, String> queryParam) {
		List<Object> params = new ArrayList<Object>();
		String sql = getOrderApplySql(queryParam, params);
		PageInfoDto pageInfoDto = OemDAOUtil.pageQuery(sql, params);
		return pageInfoDto;

	}

	/**
	 * 撤单申请(经销商)下载
	 * 
	 * @param queryParam
	 * @return pageInfoDto
	 */
	public List<Map> getCancelOrderApplyDownloadList(Map<String, String> queryParam) {
		List<Object> params = new ArrayList<Object>();
		String sql = getOrderApplySql(queryParam, params);
		List<Map> orderList = OemDAOUtil.downloadPageQuery(sql, params);
		return orderList;
	}

	/**
	 * SQL组装 撤单申请(经销商)
	 * 
	 * @param queryParam
	 * @param params
	 * @return
	 */
	private String getOrderApplySql(Map<String, String> queryParam, List<Object> params) {

		LoginInfoDto loginInfo = ApplicationContextHelper.getBeanByType(LoginInfoDto.class);
		StringBuilder sql = new StringBuilder();
		sql.append(
				"SELECT t.*,IFNULL(trr.REMARK,0) REMARK,IFNULL(trr.OTHER_REMARK,'') OTHER_REMARK,(CASE  WHEN trr.IS_LOCK =0  THEN 10041002  ELSE 10041001 END) AS IS_LOCK \n");
		sql.append(
				"   FROM (SELECT tvo.ORDER_ID,tvo.ORDER_TYPE,tvo.PAYMENT_TYPE,tvo.VIN,vm.BRAND_CODE,vm.BRAND_ID,vm.SERIES_CODE,vm.SERIES_ID,vm.SERIES_NAME,vm.GROUP_CODE,vm.GROUP_ID,vm.GROUP_NAME,vm.MODEL_YEAR,vm.COLOR_CODE,vm.COLOR_NAME,vm.TRIM_CODE,vm.TRIM_NAME,tv.VEHICLE_USAGE \n");
		sql.append(" 			FROM TT_VS_ORDER    tvo, \n");
		sql.append("				 TM_VEHICLE_DEC     tv, \n");
		sql.append("				 (" + getVwMaterialSql() + ")    vm \n");
		sql.append(" 			WHERE tvo.VIN=tv.VIN \n");
		sql.append("			  AND tv.MATERIAL_ID=vm.MATERIAL_ID \n");
		// 当前经销商用户DEALER_ID201308217685191
		sql.append("			  AND tvo.DEALER_ID=" + loginInfo.getDealerId() + " \n");

		sql.append(" 			  AND tv.NODE_STATUS=11511019 \n");
		sql.append("           AND VM.GROUP_TYPE = 90081002 \n");
		sql.append("			  AND tvo.ORDER_STATUS<20071008) t \n");
		sql.append("	LEFT JOIN TT_RESOURCE_REMARK trr ON t.VIN=trr.VIN \n");
		sql.append("  WHERE 1=1 \n");
		sql.append(
				"	AND NOT EXISTS(SELECT 1 FROM TM_ORDER_PAY_CHANGE p WHERE p.VIN=t.VIN AND p.AUDIT_STATUS=12521001) \n");

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
			sql.append(" and t.COLOR_CODE= '" + queryParam.get("colorId") + "' \n");
		}
		// 内饰
		if (!StringUtils.isNullOrEmpty(queryParam.get("trimId"))) {
			sql.append(" and t.TRIM_CODE= '" + queryParam.get("trimId") + "' \n");
		}

		// VIN
		if (!StringUtils.isNullOrEmpty(queryParam.get("vin"))) {
			sql.append(" and t.VIN like ? \n");
			params.add("%" + queryParam.get("vin") + "%");
		}
		System.out.println(sql.toString());
		return sql.toString();
	}

}
