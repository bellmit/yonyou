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
 * @date 2017年3月16日
 */
@SuppressWarnings("all")
@Repository
public class DealerRebateDetailQueryDlrDao extends OemBaseDAO {

	/**
	 * 返利使用明细(经销商) 查询
	 * 
	 * @param queryParam
	 * @return pageInfoDto
	 */
	public PageInfoDto getDealerRebateDetailDlrQueryList(Map<String, String> queryParam) {
		List<Object> params = new ArrayList<Object>();
		String sql = getRebateDetailDlrSql(queryParam, params);
		PageInfoDto pageInfoDto = OemDAOUtil.pageQuery(sql, params);
		return pageInfoDto;

	}

	/**
	 * 返利使用明细(经销商) 下载
	 * 
	 * @param queryParam
	 * @return pageInfoDto
	 */
	public List<Map> getDealerRebateDetailDlrDownloadList(Map<String, String> queryParam) {
		List<Object> params = new ArrayList<Object>();
		String sql = getRebateDetailDlrSql(queryParam, params);

		List<Map> orderList = OemDAOUtil.downloadPageQuery(sql, params);
		return orderList;
	}

	/**
	 * SQL组装 返利使用明细(经销商)
	 * 
	 * @param queryParam
	 * @param params
	 * @return
	 */
	private String getRebateDetailDlrSql(Map<String, String> queryParam, List<Object> params) {
		LoginInfoDto loginInfo = ApplicationContextHelper.getBeanByType(LoginInfoDto.class);

		StringBuilder sql = new StringBuilder();
		sql.append(
				"SELECT * FROM (  SELECT T1.REBATE_TYPE,(SELECT CODE_DESC FROM TT_VS_REBATE_TYPE WHERE CODE_ID=T1.REBATE_TYPE) REBATE_NAME , \n");
		sql.append("	(SELECT SUM(T2.rebate_amount) FROM TT_VS_REBATE T2 \n");
		sql.append("		WHERE T2.DEALER_CODE = T1.DEALER_CODE \n");
		sql.append("		AND T2.REBATE_TYPE = T1.REBATE_TYPE) REBATE_TOTAL, \n");
		sql.append(
				"		SUM(CASE WHEN T1.REBATE_AMOUNT IS NOT NULL THEN T1.REBATE_AMOUNT  WHEN T1.ticket_amount IS NOT NULL THEN T1.ticket_amount END) REBATE_USE ,   \n");
		sql.append(
				" ( (SELECT SUM(T2.rebate_amount) FROM TT_VS_REBATE T2 WHERE T2.DEALER_CODE = T1.DEALER_CODE AND T2.REBATE_TYPE = T1.REBATE_TYPE)-SUM(CASE WHEN T1.REBATE_AMOUNT IS NOT NULL THEN T1.REBATE_AMOUNT  WHEN T1.ticket_amount IS NOT NULL THEN T1.ticket_amount END) )REBATE_REMAIN   \n");
		sql.append(" FROM TT_VS_REBATE_EMPLOY T1, TT_VS_REBATE_TYPE T3  \n");
		sql.append(" WHERE   T1.REBATE_TYPE = T3.CODE_ID  \n");
		sql.append(" AND T3.STATUS = '10011001' \n");

		// 经销商DEALER_CODE
		sql.append(" AND T1.DEALER_CODE='" + loginInfo.getDealerCode() + "' \n");
		// 返利类型
		if (!StringUtils.isNullOrEmpty(queryParam.get("rebateType"))) {
			sql.append(" and T1.REBATE_TYPE =? \n");
			params.add(queryParam.get("rebateType"));
		}
		sql.append(" GROUP BY T1.REBATE_TYPE,DEALER_CODE  ) tt  \n");

		return sql.toString();
	}

}
