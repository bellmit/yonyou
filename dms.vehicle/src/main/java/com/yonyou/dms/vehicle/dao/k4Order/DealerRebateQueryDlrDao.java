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
 * @date 2017年3月15日
 */
@Repository
public class DealerRebateQueryDlrDao extends OemBaseDAO {

	/**
	 * 返利发放 查询
	 * 
	 * @param queryParam
	 * @return pageInfoDto
	 */
	public PageInfoDto getDealerRebateDlrQueryList(Map<String, String> queryParam) {
		List<Object> params = new ArrayList<Object>();
		String sql = getRebateDlrQuerySql(queryParam, params);
		PageInfoDto pageInfoDto = OemDAOUtil.pageQuery(sql, params);
		return pageInfoDto;

	}

	/**
	 * 返利发放 下载
	 * 
	 * @param queryParam
	 * @return pageInfoDto
	 */
	public List<Map> getDealerRebateDlrDownloadList(Map<String, String> queryParam) {
		List<Object> params = new ArrayList<Object>();
		String sql = getRebateDlrQuerySql(queryParam, params);
		List<Map> orderList = OemDAOUtil.downloadPageQuery(sql, params);
		return orderList;
	}

	/**
	 * SQL组装 返利发放
	 * 
	 * @param queryParam
	 * @param params
	 * @return
	 */
	private String getRebateDlrQuerySql(Map<String, String> queryParam, List<Object> params) {
		LoginInfoDto loginInfo = ApplicationContextHelper.getBeanByType(LoginInfoDto.class);
		StringBuilder sql = new StringBuilder();
		sql.append(
				"SELECT * FROM ( SELECT DATE_FORMAT(T1.CREATE_DATE,'%Y-%c-%d') CREATE_DATE,(SELECT CODE_DESC FROM TT_VS_REBATE_TYPE WHERE CODE_ID=T1.REBATE_TYPE)  REBATE_TYPE,T1.REBATE_AMOUNT,T1.REBATE_CODE,T1.REMARK,T1.VIN  \n");
		sql.append("	FROM TT_VS_REBATE T1 , \n");
		sql.append("		 TT_VS_REBATE_TYPE T2 \n");
		sql.append("	WHERE 0=0 \n");
		sql.append("		AND T1.REBATE_TYPE = T2.CODE_ID \n");
		sql.append("		AND T2.STATUS = '10011001' \n");
		// 经销商DEALER_CODE
		sql.append("		and T1.DEALER_CODE = '" + loginInfo.getDealerCode() + "' \n");

		// 返利类型
		if (!StringUtils.isNullOrEmpty(queryParam.get("rebateType"))) {
			sql.append(" and T1.REBATE_TYPE =? \n");
			params.add(queryParam.get("rebateType"));
		}
		// 返利发放 起始
		if (!StringUtils.isNullOrEmpty(queryParam.get("beginDate"))) {
			sql.append(" and T1.CREATE_DATE>='" + queryParam.get("beginDate") + "' \n");
		}
		// 返利发放 结束
		if (!StringUtils.isNullOrEmpty(queryParam.get("endDate"))) {
			sql.append("AND T1.CREATE_DATE <='" + queryParam.get("endDate") + "' \n");

		}
		sql.append("		ORDER BY T1.CREATE_DATE,T1.REBATE_TYPE DESC  ) tt \n");

		return sql.toString();
	}

}
