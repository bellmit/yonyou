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

@Repository
public class DealerCancelAuditDao extends OemBaseDAO {

	/**
	 * 经销商撤单审核查询(区域)
	 * 
	 * @param queryParam
	 * @return pageInfoDto
	 */
	public PageInfoDto getDealerCanceAuditList(Map<String, String> queryParam) {
		List<Object> params = new ArrayList<Object>();
		String sql = getQuerySql(queryParam, params);
		// System.out.println("sql语句查询");
		PageInfoDto pageInfoDto = OemDAOUtil.pageQuery(sql, params);
		return pageInfoDto;

	}

	/**
	 * 经销商撤单查询（大区）下载
	 * 
	 * @param queryParam
	 * @return
	 */
	public List<Map> findCancelAuditApplyDownloadList(Map<String, String> queryParam) {
		List<Object> params = new ArrayList<Object>();
		String sql = getQuerySql(queryParam, params);
		List<Map> orderList = OemDAOUtil.downloadPageQuery(sql, params);
		return orderList;
	}

	/**
	 * SQL组装
	 * 
	 * @param queryParam
	 * @param params
	 * @return
	 */

	private String getQuerySql(Map<String, String> queryParam, List<Object> params) {
		StringBuilder sql = new StringBuilder();
		sql.append(
				"  SELECT t.*,IFNULL(trr.REMARK,0) REMARK,IFNULL(trr.OTHER_REMARK,'') OTHER_REMARK,(CASE  WHEN IFNULL(trr.IS_LOCK,0) =0  THEN 10041001  ELSE 10041002 END)  IS_LOCK\n");
		sql.append(
				"  	FROM (SELECT tor.ORG_NAME ORG_NAME_SMALL,tor2.ORG_NAME,td.DEALER_CODE,td.DEALER_SHORTNAME,tv.VIN,tvo.ORDER_TYPE,tvo.PAYMENT_TYPE,vm.BRAND_CODE,vm.BRAND_ID,vm.SERIES_CODE,vm.SERIES_ID,vm.SERIES_NAME,vm.GROUP_CODE,vm.GROUP_ID,vm.GROUP_NAME,vm.MODEL_YEAR,vm.COLOR_CODE,vm.COLOR_NAME,vm.TRIM_CODE,vm.TRIM_NAME,tv.VEHICLE_USAGE,\n");
		sql.append(
				"  			   topc.ID,topc.AUDIT_STATUS,(CASE WHEN topc.IS_SUC=1 THEN '撤单成功' WHEN topc.IS_SUC=2 THEN '撤单失败'  WHEN topc.IS_SUC=3 THEN '撤单中' ELSE '' END) IS_SUC,(CASE WHEN topc.IS_SUC=2 THEN topc.ERROR_INFO ELSE '' END) ERROR_INFO,(topc.CREATE_DATE) CREATE_DATE,\n");
		sql.append("  				DATE_FORMAT(topc.AUDIT_DATE,'%Y-%c-%d %H:%i:%s') AUDIT_DATE\n");
		sql.append("   		  FROM TM_ORDER_PAY_CHANGE     	   topc,\n");
		sql.append("  			   TM_VEHICLE_DEC          tv,\n");
		sql.append("  			   (" + getVwMaterialSql() + ")             vm,\n");
		sql.append("  			   TT_VS_ORDER             tvo,\n");
		sql.append("  			   TM_DEALER	     	   td,\n");
		sql.append("  			   TM_ORG		   tor,\n");
		sql.append("  			   TM_ORG		   tor2,\n");
		sql.append("  			   TM_DEALER_ORG_RELATION  tdor\n");
		sql.append("  	  WHERE topc.VIN=tv.VIN	\n");
		sql.append(" 			AND  VM.GROUP_TYPE=" + OemDictCodeConstants.GROUP_TYPE_IMPORT + "\n");
		// sql.append(" AND VM.GROUP_TYPE='90081002' \n");
		sql.append("  			AND tv.MATERIAL_ID=vm.MATERIAL_ID\n");
		sql.append("  			AND topc.ORDER_ID=tvo.ORDER_ID\n");
		sql.append("  			AND topc.DEALER_ID=td.DEALER_ID\n");
		sql.append("  			AND td.DEALER_ID=tdor.DEALER_ID\n");

		// 销售小区
		if (!StringUtils.isNullOrEmpty(queryParam.get("bigOrgName"))) {
			sql.append(" and tor.PARENT_ORG_ID in (" + queryParam.get("bigOrgName") + ") \n");
		}
		// 销售大区
		if (!StringUtils.isNullOrEmpty(queryParam.get("smallOrgName"))) {
			sql.append(" and tor.ORG_ID in (" + queryParam.get("smallOrgName") + ") \n");
		}
		sql.append("  			AND tor.ORG_ID=tdor.ORG_ID\n");
		sql.append("  			AND tor.PARENT_ORG_ID=tor2.ORG_ID) t\n");
		sql.append("    LEFT JOIN TT_RESOURCE_REMARK  trr ON t.VIN=trr.VIN \n");
		sql.append("  	   WHERE 1=1\n");

		// 品牌
		if (!StringUtils.isNullOrEmpty(queryParam.get("brandId"))) {
			sql.append(" and t.BRAND_ID=? \n");
			params.add(queryParam.get("brandId"));
		}
		// 车系
		if (!StringUtils.isNullOrEmpty(queryParam.get("seriesId"))) {
			sql.append(" and t.SERIES_ID=? \n");
			params.add(queryParam.get("seriesId"));
		}
		// 车款
		if (!StringUtils.isNullOrEmpty(queryParam.get("groupId"))) {
			sql.append(" and t.GROUP_ID=? \n");
			params.add(queryParam.get("groupId"));
		}
		// 年款
		if (!StringUtils.isNullOrEmpty(queryParam.get("modelId"))) {
			sql.append(" and t.MODEL_YEAR=? \n");
			params.add(queryParam.get("modelId"));
		}
		// 外饰
		if (!StringUtils.isNullOrEmpty(queryParam.get("colorId"))) {
			sql.append(" and t.COLOR_CODE=? \n");
			params.add(queryParam.get("colorId"));
		}
		// 内饰
		if (!StringUtils.isNullOrEmpty(queryParam.get("trimId"))) {
			sql.append(" and t.TRIM_CODE=? \n");
			params.add(queryParam.get("trimId"));
		}
		// 经销商
		if (!StringUtils.isNullOrEmpty(queryParam.get("dealerCode"))) {
			// sql.append(" and t.DEALER_CODE in ? \n");
			// params.add(queryParam.get("dealerCode"));
			sql.append(" and t.DEALER_CODE in (" + queryParam.get("dealerCode") + ") \n");
		}
		// VIN
		if (!StringUtils.isNullOrEmpty(queryParam.get("vin"))) {
			sql.append(" and t.VIN like ? \n");
			params.add("%" + queryParam.get("vin") + "%");
		}
		// 申请日期 起始
		if (!StringUtils.isNullOrEmpty(queryParam.get("createDate"))) {
			sql.append(" and t.CREATE_DATE>='" + queryParam.get("createDate") + " 00:00:00'" + " \n");
			// params.add(DateUtil.parseDefaultDate(queryParam.get("createDate")));
			// params.add("'" + queryParam.get("createDate") + " 00:00:00'" +
			// ")");

		}
		// 申请日期 结束
		if (!StringUtils.isNullOrEmpty(queryParam.get("auditDate"))) {
			sql.append(" and t.CREATE_DATE <='" + queryParam.get("auditDate") + " 23:59:59'" + " \n");
			// params.add(DateUtil.parseDefaultDate(queryParam.get("auditDate")));
			// params.add("'" + queryParam.get("auditDate") + " 23:59:59'" +
			// ")");

		}
		// 审核状态
		if (!StringUtils.isNullOrEmpty(queryParam.get("auditStatus"))) {
			sql.append(" and t.AUDIT_STATUS=? \n");
			params.add(queryParam.get("auditStatus"));
		}
		System.err.println(sql.toString());
		return sql.toString();
	}

}
