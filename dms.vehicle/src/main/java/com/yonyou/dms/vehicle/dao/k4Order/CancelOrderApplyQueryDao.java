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
 * @date 2017年3月9日
 */
@Repository
public class CancelOrderApplyQueryDao extends OemBaseDAO {

	/**
	 * 撤单查询(经销商)查询
	 * 
	 * @param queryParam
	 * @return pageInfoDto
	 */
	public PageInfoDto getCancelOrderApplyQueryList(Map<String, String> queryParam) {
		List<Object> params = new ArrayList<Object>();
		String sql = getApplyQuerySql(queryParam, params);
		PageInfoDto pageInfoDto = OemDAOUtil.pageQuery(sql, params);
		return pageInfoDto;

	}

	/**
	 * 撤单查询(经销商)下载
	 * 
	 * @param queryParam
	 * @return pageInfoDto
	 */
	public List<Map> getCancelOrderApplyQueryDownloadList(Map<String, String> queryParam) {
		List<Object> params = new ArrayList<Object>();
		String sql = getApplyQuerySql(queryParam, params);
		List<Map> orderList = OemDAOUtil.downloadPageQuery(sql, params);
		return orderList;
	}

	/**
	 * SQL组装 撤单查询(经销商)
	 * 
	 * @param queryParam
	 * @param params
	 * @return
	 */
	private String getApplyQuerySql(Map<String, String> queryParam, List<Object> params) {
		LoginInfoDto loginInfo = ApplicationContextHelper.getBeanByType(LoginInfoDto.class);
		StringBuilder sql = new StringBuilder();
		sql.append(
				"SELECT t.*,IFNULL(trr.REMARK,0) REMARK,IFNULL(trr.OTHER_REMARK,'') OTHER_REMARK,(CASE  WHEN trr.IS_LOCK =0  THEN 10041001  ELSE 10041002 END) AS IS_LOCK \n");
		sql.append(
				"  FROM (SELECT tv.VIN,tvo.ORDER_TYPE,tvo.PAYMENT_TYPE,vm.BRAND_CODE,vm.BRAND_ID,vm.SERIES_CODE,vm.SERIES_ID,vm.SERIES_NAME,vm.GROUP_CODE,vm.GROUP_ID,vm.GROUP_NAME,vm.MODEL_YEAR,vm.COLOR_CODE,vm.COLOR_NAME,vm.TRIM_CODE,vm.TRIM_NAME,tv.VEHICLE_USAGE, \n");
		sql.append(
				"			   topc.AUDIT_STATUS,(CASE WHEN topc.IS_SUC=1 THEN '撤单成功' WHEN topc.IS_SUC=2 THEN '撤单失败'  WHEN topc.IS_SUC=3 THEN '撤单中' ELSE '' END) IS_SUC,(CASE WHEN topc.IS_SUC=2 THEN topc.ERROR_INFO ELSE '' END) ERROR_INFO,(topc.CREATE_DATE) CREATE_DATE,DATE_FORMAT(topc.AUDIT_DATE,'%Y-%c-%d %H:%i:%s') AUDIT_DATE \n");
		sql.append(" 		  FROM TM_ORDER_PAY_CHANGE   topc, \n");
		sql.append(" 			   TM_VEHICLE_DEC            tv, \n");
		sql.append("			   (" + getVwMaterialSql() + ")           vm, \n");
		sql.append(" 			   TT_VS_ORDER           tvo \n");
		sql.append("		  WHERE topc.VIN=tv.VIN \n");
		sql.append(" AND  VM.GROUP_TYPE=90081002 \n");
		sql.append("			AND tv.MATERIAL_ID=vm.MATERIAL_ID \n");
		sql.append("			AND topc.ORDER_ID=tvo.ORDER_ID \n");
		// 经销商DEALER_ID
		sql.append("			AND tvo.DEALER_ID=" + loginInfo.getDealerId() + " \n");

		sql.append("           AND VM.GROUP_TYPE = 90081002) t \n");
		sql.append("   LEFT JOIN TT_RESOURCE_REMARK  trr ON t.VIN=trr.VIN \n");
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
			sql.append(" and t.COLOR_CODE= '" + queryParam.get("colorId") + "' \n");
		}
		// 内饰
		if (!StringUtils.isNullOrEmpty(queryParam.get("trimId"))) {
			sql.append(" and t.TRIM_CODE= '" + queryParam.get("trimId") + "' \n");
		}

		// VIN
		if (!StringUtils.isNullOrEmpty(queryParam.get("vin"))) {
			sql.append(" and t.VIN like '%" + queryParam.get("vin") + "%' \n");
		}
		// 审核状态
		if (!StringUtils.isNullOrEmpty(queryParam.get("auditStatus"))) {
			sql.append(" and T.AUDIT_STATUS = " + queryParam.get("auditStatus") + " \n");
		}
		// 申请日期 起始DATE_FORMAT('2017-03-16','%Y-%m-%d %H:%i:%s')
		if (!StringUtils.isNullOrEmpty(queryParam.get("applyDateStart"))) {
			sql.append(" and T.CREATE_DATE>='" + queryParam.get("applyDateStart") + " 00:00:00" + "' \n");
		}
		// 订申请日期 日期 结束
		if (!StringUtils.isNullOrEmpty(queryParam.get("applyDateEnd"))) {
			sql.append(" and T.CREATE_DATE <='" + queryParam.get("applyDateEnd") + " 23:59:59" + "' \n");

		}
		System.out.println(sql.toString());
		return sql.toString();
	}

}
