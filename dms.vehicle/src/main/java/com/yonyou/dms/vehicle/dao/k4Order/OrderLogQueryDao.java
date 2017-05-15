package com.yonyou.dms.vehicle.dao.k4Order;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.yonyou.dms.framework.DAO.OemBaseDAO;
import com.yonyou.dms.framework.DAO.OemDAOUtil;
import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.function.common.OemDictCodeConstants;
import com.yonyou.dms.function.utils.common.CommonUtils;
import com.yonyou.dms.function.utils.common.StringUtils;

/**
 * @author liujiming
 * @date 2017年3月6日
 */
@Repository
public class OrderLogQueryDao extends OemBaseDAO {

	/**
	 * 撤单日志查询
	 * 
	 * @param queryParam
	 * @return pageInfoDto
	 */
	public PageInfoDto getOrderLogQueryList(Map<String, String> queryParam) {
		List<Object> params = new ArrayList<Object>();
		String sql = getLogQuerySql(queryParam, params);
		PageInfoDto pageInfoDto = OemDAOUtil.pageQuery(sql, params);
		return pageInfoDto;

	}

	/**
	 * 撤单日志下载
	 * 
	 * @param queryParam
	 * @return pageInfoDto
	 */
	public List<Map> getOrderLogDownloadList(Map<String, String> queryParam) {
		List<Object> params = new ArrayList<Object>();
		String sql = getLogQuerySql(queryParam, params);
		List<Map> orderList = OemDAOUtil.downloadPageQuery(sql, params);
		return orderList;
	}

	/**
	 * SQL组装 查询
	 * 
	 * @param queryParam
	 * @param params
	 * @return
	 */
	private String getLogQuerySql(Map<String, String> queryParam, List<Object> params) {

		// 查询条件
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT tt.* FROM (SELECT tc.SWT_CODE , \n");
		sql.append("		        td.DEALER_CODE, \n");
		sql.append("		        td.DEALER_SHORTNAME, \n");
		sql.append("		        tvo.ORDER_NO, \n");
		sql.append("		        tvo.ORDER_TYPE, \n");
		sql.append("		        tca.ERROR_INFO, \n");
		// 如果是预撤单的数据，如果中进未返回结果用tca.CREATE_DATE,如果返回结果用tca.UPDATE_DATE
		sql.append(
				"			    (CASE WHEN tca.TM_ALLOT_ID>0 THEN (CASE WHEN tca.STATUS=10011001 THEN tca.CREATE_DATE ELSE tca.UPDATE_DATE END) ELSE t1.UPDATE_DATE END) UPDATE_DATE2, \n");
		sql.append("			    (CASE WHEN tca.STATUS=10011002 AND tca.IS_SUC=1 THEN '"
				+ OemDictCodeConstants.UN_ORDER_STATUS1 + "' WHEN tca.IS_SUC=0 THEN '"
				+ OemDictCodeConstants.UN_ORDER_STATUS2 + "' WHEN tca.STATUS=10011001 THEN '"
				+ OemDictCodeConstants.UN_ORDER_STATUS3 + "' END) UNORDER_STATUS, \n");
		sql.append("		        t1.* \n");
		sql.append("		   FROM (SELECT tvmc.ORDER_ID, \n");
		sql.append("				tvmc.ID MATCH_ID, \n");
		sql.append("		                tv.VEHICLE_ID, \n");
		sql.append("		                tv.VIN, \n");
		sql.append("		                tv.VPC_PORT, \n");
		sql.append("		                vm.BRAND_NAME, \n");
		sql.append("		                vm.SERIES_NAME, \n");
		sql.append("		                vm.MODEL_NAME, \n");
		sql.append("	(case when TVMC.CANCEL_TYPE='1001' then 20011001 else 20011002 end)CANCEL_TYPE, \n");
		sql.append("		                TVMC.UPDATE_DATE, \n");
		sql.append("		                TVMC.CANCEL_REASON, \n");
		sql.append("		               (SELECT NAME FROM TC_USER WHERE USER_ID = TVMC.UPDATE_BY) UPDATE_BY \n");
		sql.append("		          FROM TT_VS_MATCH_CHECK tvmc, \n");
		sql.append("			       tm_vehicle_DEC tv, \n");
		sql.append("			       (" + getVwMaterialSql() + ") vm \n");
		sql.append("		         WHERE tvmc.chg_vehicle_id = tv.VEHICLE_ID 		 \n");
		sql.append("			       AND  VM.GROUP_TYPE='90081002'		 \n");
		sql.append("		               AND tv.MATERIAL_ID = vm.MATERIAL_ID \n");
		// VIN
		if (!StringUtils.isNullOrEmpty(queryParam.get("vin"))) {
			sql.append(" and tv.VIN like ? \n");
			params.add("%" + queryParam.get("vin") + "%");
		}
		String cancelType = CommonUtils.checkNull(queryParam.get("cancelType"));
		// 操作类型
		if (!cancelType.equals("")) {
			if (cancelType.equals(OemDictCodeConstants.CANCEL_TYPE1)) {
				cancelType = "1001";
			}
			if (cancelType.equals(OemDictCodeConstants.CANCEL_TYPE2)) {
				cancelType = "1002";
			}

			sql.append(" and tvmc.CANCEL_TYPE = '" + cancelType + "'\n");
		}
		String string = queryParam.get("beginDate");
		// 操作时间 起始DATE_FORMAT(NOW(),'%b %d %Y %h:%i %p')
		if (!StringUtils.isNullOrEmpty(queryParam.get("beginDate"))) {
			sql.append(" and tvmc.update_date >='" + queryParam.get("beginDate") + "'  \n");

		}
		// 操作时间 结束
		if (!StringUtils.isNullOrEmpty(queryParam.get("endDate"))) {
			sql.append(" and tvmc.update_date  <='" + queryParam.get("endDate") + "'  \n");
		}

		// 测试数据
		// sql.append(" AND tv.VEHICLE_ID IN (2013083109869314,201308228451160)
		// \n");

		sql.append("		    ) t1   LEFT JOIN TT_VS_ORDER tvo \n");
		sql.append("		          ON t1.ORDER_ID = tvo.ORDER_ID \n");
		sql.append("		       LEFT JOIN tm_dealer td \n");
		sql.append("		          ON tvo.DEALER_ID = td.DEALER_ID \n");
		sql.append("		       LEFT JOIN TM_COMPANY tc \n");
		sql.append("		          ON tc.COMPANY_ID = td.COMPANY_ID \n");
		sql.append("			   LEFT JOIN TM_CACT_ALLOT tca \n");
		sql.append("				  ON t1.VIN=tca.VIN AND t1.MATCH_ID=tca.MATCH_CHECK_ID \n");
		sql.append("			   WHERE 1=1		 \n");
		// 订单号
		if (!StringUtils.isNullOrEmpty(queryParam.get("orderNo"))) {
			sql.append(" and tvo.ORDER_NO like ? \n");
			params.add("%" + queryParam.get("orderNo") + "%");
		}
		sql.append("		           ORDER BY t1.UPDATE_DATE DESC \n");
		sql.append("		) tt \n");
		// 撤单状态
		String unOrderStatus = CommonUtils.checkNull(queryParam.get("unOrderStatus"));
		if (!unOrderStatus.equals("")) {
			if (unOrderStatus.equals(OemDictCodeConstants.UN_ORDER_STATUS1)) {
				sql.append("  where tt.UNORDER_STATUS='" + unOrderStatus + "' \n");
			}
			if (unOrderStatus.equals(OemDictCodeConstants.UN_ORDER_STATUS2)) {
				sql.append("  where tt.UNORDER_STATUS='" + unOrderStatus + "' \n");
			}
			if (unOrderStatus.equals(OemDictCodeConstants.UN_ORDER_STATUS3)) {
				sql.append("  where tt.UNORDER_STATUS='" + unOrderStatus + "' \n");
			}
			// sql.append(" where tt.UNORDER_STATUS='" + unOrderStatus + "'
			// \n");

		}
		System.out.println(sql.toString());
		return sql.toString();
	}
}
