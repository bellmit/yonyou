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
import com.yonyou.dms.function.common.OemDictCodeConstants;
import com.yonyou.dms.function.utils.common.StringUtils;

/**
 *
 * 
 * @author liujiming
 * @date 2017年2月16日
 */

@Repository
public class DealerOrderAuditDao extends OemBaseDAO {

	/**
	 * 经销商撤单审核查询(区域)
	 * 
	 * @param queryParam
	 * @return pageInfoDto
	 */
	public PageInfoDto getDealerCancelOrderList1(Map<String, String> queryParam) {
		List<Object> params = new ArrayList<Object>();
		String sql = getQuerySql1(queryParam, params);
		System.out.println(sql);
		PageInfoDto pageInfoDto = OemDAOUtil.pageQuery(sql, params);
		return pageInfoDto;

	}

	/**
	 * 经销商撤单审核查询(区域)
	 * 
	 * @param queryParam
	 * @return pageInfoDto
	 */
	public PageInfoDto getDealerCancelOrderList(Map<String, String> queryParam) {
		List<Object> params = new ArrayList<Object>();
		String sql = getQuerySql(queryParam, params);
		System.out.println(sql);
		PageInfoDto pageInfoDto = OemDAOUtil.pageQuery(sql, params);
		return pageInfoDto;

	}

	/**
	 * SQL组装 经销商撤单审核查询
	 * 
	 * @param queryParam
	 * @param params
	 * @return
	 */
	private String getQuerySql(Map<String, String> queryParam, List<Object> params) {
		LoginInfoDto loginInfo = ApplicationContextHelper.getBeanByType(LoginInfoDto.class);
		StringBuffer sql = new StringBuffer("\n");
		sql.append(
				"select t.*,IFNULL(trr.REMARK,0)REMARK, IFNULL(trr.OTHER_REMARK,'') OTHER_REMARK,(CASE  WHEN IFNULL(trr.IS_LOCK,0) =0  THEN 10041001  ELSE 10041002 END) IS_LOCK \n");
		sql.append(
				"  from (select tor.ORG_NAME ORG_NAME_SMALL,tor2.ORG_NAME,td.DEALER_CODE,td.DEALER_SHORTNAME,tv.VIN,tvo.ORDER_TYPE,tvo.PAYMENT_TYPE,vm.BRAND_CODE,vm.SERIES_CODE,vm.SERIES_NAME,vm.GROUP_CODE,vm.GROUP_NAME,vm.MODEL_YEAR,vm.COLOR_CODE,vm.COLOR_NAME,vm.TRIM_CODE,vm.TRIM_NAME,tv.VEHICLE_USAGE,\n");
		sql.append(
				"			   topc.ID,topc.AUDIT_STATUS,topc.IS_SUC,topc.ERROR_INFO,DATE_FORMAT(topc.CREATE_DATE,'%Y-%m-%d %H:%i:%s') CREATE_DATE,DATE_FORMAT(topc.AUDIT_DATE,'%Y-%m-%d %H:%i:%s') AUDIT_DATE \n");
		sql.append(" 		  from TM_ORDER_PAY_CHANGE     topc, \n");
		sql.append(" 			   tm_vehicle_dec              tv, \n");
		sql.append("			   (" + getVwMaterialSql() + ")     	vm, \n");
		sql.append(" 			   TT_VS_ORDER             tvo, \n");
		sql.append("			   TM_DEALER			   td, \n");
		sql.append("			   TM_ORG				   tor, \n");
		sql.append("			   TM_ORG				   tor2, \n");
		sql.append("			   TM_DEALER_ORG_RELATION  tdor\n");
		sql.append("		  where topc.VIN=tv.VIN\n");
		sql.append(" AND  VM.GROUP_TYPE=" + OemDictCodeConstants.GROUP_TYPE_IMPORT + "\n");
		sql.append("			and tv.MATERIAL_ID=vm.MATERIAL_ID\n");
		sql.append("			and topc.ORDER_ID=tvo.ORDER_ID\n");
		sql.append("			and topc.DEALER_ID=td.DEALER_ID\n");
		sql.append("			and td.DEALER_ID=tdor.DEALER_ID \n");
		sql.append("			and tor.ORG_ID=tdor.ORG_ID\n");
		sql.append("			and topc.AUDIT_TYPE=" + OemDictCodeConstants.DUTY_TYPE_SMALLREGION + " \n");
		sql.append("			and topc.AUDIT_STATUS=" + OemDictCodeConstants.CANCEL_ORDER_APPLY_STATUS_01 + "  \n");
		sql.append(" and tor.ORG_ID=" + loginInfo.getOrgId() + " \n");
		sql.append("			and tor.PARENT_ORG_ID=tor2.ORG_ID) t \n");
		sql.append("   left join TT_RESOURCE_REMARK  trr on t.VIN=trr.VIN \n");
		sql.append("   where 1=1\n");
		// 品牌
		if (!StringUtils.isNullOrEmpty(queryParam.get("brandCode"))) {
			sql.append(" and t.BRAND_ID=" + queryParam.get("brandCode") + " \n");
			// params.add(queryParam.get("brandId"));
		}
		// 车系
		if (!StringUtils.isNullOrEmpty(queryParam.get("seriesName"))) {
			sql.append(" and t.SERIES_ID=" + queryParam.get("seriesName") + " \n");
			// params.add(queryParam.get("seriesId"));
		}
		// 车款
		if (!StringUtils.isNullOrEmpty(queryParam.get("groupName"))) {
			sql.append(" and t.GROUP_ID=" + queryParam.get("groupName") + "\n");
			/// params.add(queryParam.get("groupId"));
		}
		// 年款
		if (!StringUtils.isNullOrEmpty(queryParam.get("modelYear"))) {
			sql.append(" and t.MODEL_YEAR=" + queryParam.get("modelYear") + "\n");
			// params.add(queryParam.get("modelId"));
		}
		// 外饰
		if (!StringUtils.isNullOrEmpty(queryParam.get("colorName"))) {
			sql.append(" and t.COLOR_CODE=" + queryParam.get("colorName") + " \n");
			// params.add(queryParam.get("colorId"));
		}
		// 内饰
		if (!StringUtils.isNullOrEmpty(queryParam.get("trimName"))) {
			sql.append(" and t.TRIM_CODE=" + queryParam.get("trimName") + "\n");
			// params.add(queryParam.get("trimId"));
		}
		// 经销商
		if (!StringUtils.isNullOrEmpty(queryParam.get("dealerCode"))) {

			sql.append(" and t.DEALER_CODE in (" + queryParam.get("dealerCode") + ") \n");
		}
		// VIN
		if (!StringUtils.isNullOrEmpty(queryParam.get("vin"))) {
			sql.append(" and t.VIN like '%" + queryParam.get("vin") + "%' \n");

		}
		// 申请日期 起始
		if (!StringUtils.isNullOrEmpty(queryParam.get("createDate"))) {
			sql.append(" and t.CREATE_DATE>='" + queryParam.get("createDate") + " 00:00:00'" + " \n");
			// params.add(DateUtil.parseDefaultDate(queryParam.get("createDate")));
			// params.add("'" + queryParam.get("createDate") + " 23:59:59'");

		}
		// 申请日期 结束
		if (!StringUtils.isNullOrEmpty(queryParam.get("auditDate"))) {
			sql.append(" and t.CREATE_DATE <='" + queryParam.get("auditDate") + " 23:59:59'" + " \n");
			// params.add(DateUtil.parseDefaultDate(queryParam.get("auditDate")));
			// params.add("date_format('" + queryParam.get("auditDate") + "
			// 23:59:59'" + ")");

		}
		// 审核状态
		if (!StringUtils.isNullOrEmpty(queryParam.get("auditStatus"))) {
			sql.append(" and t.AUDIT_STATUS=? \n");
			params.add(queryParam.get("auditStatus"));
		}

		return sql.toString();
	}

	/**
	 * SQL组装 经销商撤单审核查询
	 * 
	 * @param queryParam
	 * @param params
	 * @return
	 */
	private String getQuerySql1(Map<String, String> queryParam, List<Object> params) {
		LoginInfoDto loginInfo = ApplicationContextHelper.getBeanByType(LoginInfoDto.class);

		StringBuffer sql = new StringBuffer("\n");
		sql.append(
				"select t.*,IFNULL(trr.REMARK,0)REMARK, IFNULL(trr.OTHER_REMARK,'') OTHER_REMARK,(CASE  WHEN IFNULL(trr.IS_LOCK,0) =0  THEN 10041001  ELSE 10041002 END) IS_LOCK \n");
		sql.append(
				"  from (select tor.ORG_ID,tor.PARENT_ORG_ID,tor.ORG_NAME ORG_NAME_SMALL,tor2.ORG_NAME,td.DEALER_CODE,td.DEALER_SHORTNAME,tv.VIN,tvo.ORDER_TYPE,tvo.PAYMENT_TYPE,vm.BRAND_CODE,vm.BRAND_ID,vm.SERIES_CODE,vm.SERIES_ID,vm.SERIES_NAME,vm.GROUP_CODE,vm.GROUP_NAME,vm.GROUP_ID,vm.MODEL_YEAR,vm.COLOR_CODE,vm.COLOR_NAME,vm.TRIM_CODE,vm.TRIM_NAME,tv.VEHICLE_USAGE,\n");
		sql.append(
				"			   topc.ID,topc.AUDIT_STATUS,topc.IS_SUC,topc.ERROR_INFO,(topc.CREATE_DATE) CREATE_DATE,DATE_FORMAT(topc.AUDIT_DATE,'%Y-%c-%d %H:%i:%s') AUDIT_DATE \n");
		sql.append(" 		  from TM_ORDER_PAY_CHANGE     topc, \n");
		sql.append(" 			   tm_vehicle_dec              tv, \n");
		sql.append("			   (" + getVwMaterialSql() + ")     	vm, \n");
		sql.append(" 			   TT_VS_ORDER             tvo, \n");
		sql.append("			   TM_DEALER			   td, \n");
		sql.append("			   TM_ORG				   tor, \n");
		sql.append("			   TM_ORG				   tor2, \n");
		sql.append("			   TM_DEALER_ORG_RELATION  tdor\n");
		sql.append("		  where topc.VIN=tv.VIN\n");
		sql.append(" AND  VM.GROUP_TYPE=" + OemDictCodeConstants.GROUP_TYPE_IMPORT + "\n");
		sql.append("			and tv.MATERIAL_ID=vm.MATERIAL_ID\n");
		sql.append("			and topc.ORDER_ID=tvo.ORDER_ID\n");
		sql.append("			and topc.DEALER_ID=td.DEALER_ID\n");
		sql.append("			and td.DEALER_ID=tdor.DEALER_ID \n");
		sql.append("			and tor.ORG_ID=tdor.ORG_ID\n");
		sql.append("			and topc.AUDIT_TYPE=" + OemDictCodeConstants.DUTY_TYPE_DEPT + " \n");
		sql.append("			and topc.AUDIT_STATUS=" + OemDictCodeConstants.CANCEL_ORDER_APPLY_STATUS_03 + "  \n");
		// 测试是屏蔽
		// sql.append(" and tor.ORG_ID=" + loginInfo.getOrgId() + " \n");
		sql.append("			and tor.PARENT_ORG_ID=tor2.ORG_ID) t \n");
		sql.append("   left join TT_RESOURCE_REMARK  trr on t.VIN=trr.VIN \n");
		sql.append("   where 1=1\n");
		// 品牌
		if (!StringUtils.isNullOrEmpty(queryParam.get("brandCode"))) {
			sql.append(" and t.BRAND_ID=" + queryParam.get("brandCode") + " \n");
			// params.add(queryParam.get("brandId"));
		}
		// 车系
		if (!StringUtils.isNullOrEmpty(queryParam.get("seriesName"))) {
			sql.append(" and t.SERIES_ID=" + queryParam.get("seriesName") + " \n");
			// params.add(queryParam.get("seriesId"));
		}
		// 车款
		if (!StringUtils.isNullOrEmpty(queryParam.get("groupName"))) {
			sql.append(" and t.GROUP_ID=" + queryParam.get("groupName") + "\n");
			/// params.add(queryParam.get("groupId"));
		}
		// 年款
		if (!StringUtils.isNullOrEmpty(queryParam.get("modelYear"))) {
			sql.append(" and t.MODEL_YEAR=" + queryParam.get("modelYear") + "\n");
			// params.add(queryParam.get("modelId"));
		}
		// 外饰
		if (!StringUtils.isNullOrEmpty(queryParam.get("colorName"))) {
			sql.append(" and t.COLOR_CODE='" + queryParam.get("colorName") + "' \n");
			// params.add(queryParam.get("colorId"));
		}
		// 内饰
		if (!StringUtils.isNullOrEmpty(queryParam.get("trimName"))) {
			sql.append(" and t.TRIM_CODE='" + queryParam.get("trimName") + "'\n");
			// params.add(queryParam.get("trimId"));
		}
		// 经销商
		if (!StringUtils.isNullOrEmpty(queryParam.get("dealerCode"))) {
			String dealerCodes = queryParam.get("dealerCode");
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

			sql.append(" and t.DEALER_CODE in (" + s + ") \n");
		}
		// VIN
		if (!StringUtils.isNullOrEmpty(queryParam.get("vin"))) {
			sql.append(" and t.VIN like '%" + queryParam.get("vin") + "%' \n");

		}
		// 申请日期 起始
		if (!StringUtils.isNullOrEmpty(queryParam.get("createDate"))) {
			sql.append(" and t.CREATE_DATE>='" + queryParam.get("createDate") + " 00:00:00'" + " \n");
			// params.add(DateUtil.parseDefaultDate(queryParam.get("createDate")));
			// params.add("date_format('" + queryParam.get("createDate") + "'
			// '23:59:59'," + "'%Y-%c-%d %H:%i:%s'" + ")");

		}
		// 申请日期 结束
		if (!StringUtils.isNullOrEmpty(queryParam.get("auditDate"))) {
			sql.append(" and t.CREATE_DATE <='" + queryParam.get("auditDate") + " 23:59:59'" + " \n");
			// params.add(DateUtil.parseDefaultDate(queryParam.get("auditDate")));
			// params.add("date_format('" + queryParam.get("auditDate") + "'
			// '23:59:59'," + "'%Y-%c-%d %H:%i:%s'" + ")");

		}
		// and T.PARENT_ORG_ID ='2015110780181043' AND T1.ORG_ID
		// ='2015110780182090'
		if (!StringUtils.isNullOrEmpty(queryParam.get("bigOrgName"))) {
			sql.append(" and T.PARENT_ORG_ID='" + queryParam.get("bigOrgName") + "' \n");
			String string = queryParam.get("bigOrgName");
			// params.add(queryParam.get("bigOrgName"));
		}
		if (!StringUtils.isNullOrEmpty(queryParam.get("smallOrgName"))) {
			sql.append(" and T.ORG_ID='" + queryParam.get("smallOrgName") + "' \n");
			String string = queryParam.get("smallOrgName");
			// params.add(queryParam.get("smallOrgName"));
		}

		// 审核状态
		if (!StringUtils.isNullOrEmpty(queryParam.get("auditStatus"))) {
			sql.append(" and t.AUDIT_STATUS=? \n");
			params.add(queryParam.get("auditStatus"));
		}
		System.out.println(sql.toString());
		return sql.toString();
	}

	/**
	 * 经销商撤单审核（区域）下载
	 * 
	 * @param queryParam
	 * @return
	 */
	public List<Map> findDealerCancelOrderDownload(Map<String, String> queryParam) {
		List<Object> params = new ArrayList<Object>();
		String sql = getQuerySql(queryParam, params);
		List<Map> orderList = OemDAOUtil.downloadPageQuery(sql, params);
		return orderList;
	}

	/**
	 * 经销商撤单查询（区域）查询
	 * 
	 * @param queryParam
	 * @return
	 */
	public PageInfoDto findCancelOrderApplyList(Map<String, String> queryParam) {
		List<Object> params = new ArrayList<Object>();
		String sql = getQueryApplySql(queryParam, params);
		System.out.println(sql);
		PageInfoDto pageInfoDto = OemDAOUtil.pageQuery(sql, params);
		return pageInfoDto;
	}

	/**
	 * 经销商撤单查询（区域）下载
	 * 
	 * @param queryParam
	 * @return
	 */
	public List<Map> findCancelOrderApplyDownloadList(Map<String, String> queryParam) {
		List<Object> params = new ArrayList<Object>();
		String sql = getQueryApplySql(queryParam, params);
		List<Map> orderList = OemDAOUtil.downloadPageQuery(sql, params);
		return orderList;
	}

	/**
	 * SQL组装 审核查询
	 * 
	 * @param queryParam
	 * @param params
	 * @return
	 */
	private String getQueryApplySql(Map<String, String> queryParam, List<Object> params) {
		LoginInfoDto loginInfo = ApplicationContextHelper.getBeanByType(LoginInfoDto.class);
		StringBuilder sql = new StringBuilder();
		sql.append(
				"  SELECT t.*,IFNULL(trr.REMARK,0) REMARK,IFNULL(trr.OTHER_REMARK,'') OTHER_REMARK,(CASE  WHEN IFNULL(trr.IS_LOCK,0) =0  THEN 10041001  ELSE 10041002 END) IS_LOCK\n");
		sql.append(
				"  	  FROM (SELECT tor.ORG_ID,tor.PARENT_ORG_ID,tor.ORG_NAME ORG_NAME_SMALL,tor2.ORG_NAME,td.DEALER_CODE,td.DEALER_SHORTNAME,tv.VIN,tvo.ORDER_TYPE,tvo.PAYMENT_TYPE,vm.BRAND_CODE,vm.BRAND_ID,vm.SERIES_CODE,vm.SERIES_ID,vm.SERIES_NAME,vm.GROUP_CODE,vm.GROUP_ID,vm.GROUP_NAME,vm.MODEL_YEAR,vm.COLOR_CODE,vm.COLOR_NAME,vm.TRIM_CODE,vm.TRIM_NAME,tv.VEHICLE_USAGE,\n");
		sql.append(
				"  				   topc.ID,topc.AUDIT_STATUS,(CASE WHEN topc.IS_SUC=1 THEN '撤单成功' WHEN topc.IS_SUC=2 THEN '撤单失败' WHEN topc.IS_SUC=3 THEN '撤单中' ELSE '' END) IS_SUC,(CASE WHEN topc.IS_SUC=2 THEN topc.ERROR_INFO ELSE '' END) ERROR_INFO,\n");
		sql.append(
				"  				   (topc.CREATE_DATE) CREATE_DATE,DATE_FORMAT(topc.AUDIT_DATE,'%Y-%c-%d %H:%i:%s') AUDIT_DATE\n");
		sql.append("  	 		  FROM TM_ORDER_PAY_CHANGE     	   topc,\n");
		sql.append("  	 			   TM_VEHICLE_DEC          tv,\n");
		sql.append("  				   (" + getVwMaterialSql() + ")             vm,\n");
		sql.append("  	 			   TT_VS_ORDER             tvo,\n");
		sql.append("  				   TM_DEALER		   td,\n");
		sql.append("  				   TM_ORG		   tor,\n");
		sql.append("  				   TM_ORG		   tor2,\n");
		sql.append("  				   TM_DEALER_ORG_RELATION  tdor\n");
		sql.append("  			  WHERE topc.VIN=tv.VIN\n");
		// sql.append(" AND VM.GROUP_TYPE='90081002'\n");
		sql.append(" AND  VM.GROUP_TYPE=" + OemDictCodeConstants.GROUP_TYPE_IMPORT + "\n");
		sql.append("  				AND tv.MATERIAL_ID=vm.MATERIAL_ID\n");
		sql.append("  				AND topc.ORDER_ID=tvo.ORDER_ID\n");
		sql.append("  				AND topc.DEALER_ID=td.DEALER_ID\n");
		sql.append("  				AND td.DEALER_ID=tdor.DEALER_ID\n");
		sql.append("  				AND tor.ORG_ID=tdor.ORG_ID\n");

		// sql.append(" and topc.AUDIT_TYPE=" +
		// OemDictCodeConstants.DUTY_TYPE_SMALLREGION + "\n");
		// sql.append(" and topc.AUDIT_STATUS=" +
		// OemDictCodeConstants.CANCEL_ORDER_APPLY_STATUS_01 + "\n");
		sql.append(" and tor.ORG_ID=" + loginInfo.getOrgId() + "\n");

		sql.append("  				AND tor.PARENT_ORG_ID=tor2.ORG_ID) t\n");
		sql.append("  	   LEFT JOIN TT_RESOURCE_REMARK  trr ON t.VIN=trr.VIN\n");
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
			sql.append(" and t.DEALER_CODE in (" + queryParam.get("dealerCode") + ") \n");
		}
		// VIN
		if (!StringUtils.isNullOrEmpty(queryParam.get("vin"))) {
			sql.append(" and t.VIN like ? \n");
			params.add("%" + queryParam.get("vin") + "%");
		}
		// 申请日期 起始
		if (!StringUtils.isNullOrEmpty(queryParam.get("createDate"))) {
			sql.append(" and t.CREATE_DATE>='" + queryParam.get("createDate") + " 00:00:00'\n");

		}
		// 申请日期 结束
		if (!StringUtils.isNullOrEmpty(queryParam.get("auditDate"))) {
			sql.append(" and t.CREATE_DATE <='" + queryParam.get("auditDate") + " 23:59:59'\n");
		}
		// 审核状态
		if (!StringUtils.isNullOrEmpty(queryParam.get("auditStatus"))) {
			sql.append(" and t.AUDIT_STATUS=? \n");
			params.add(queryParam.get("auditStatus"));
		}
		System.out.println(sql.toString());
		return sql.toString();
	}

}
