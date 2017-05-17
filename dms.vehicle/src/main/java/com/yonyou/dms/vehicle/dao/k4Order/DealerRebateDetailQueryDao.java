package com.yonyou.dms.vehicle.dao.k4Order;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.yonyou.dms.framework.DAO.OemBaseDAO;
import com.yonyou.dms.framework.DAO.OemDAOUtil;
import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.function.utils.common.StringUtils;

/**
 * @author liujiming
 * @date 2017年3月15日
 */
@Repository
public class DealerRebateDetailQueryDao extends OemBaseDAO {

	/**
	 * 返利使用明细 查询
	 * 
	 * @param queryParam
	 * @return pageInfoDto
	 */
	public PageInfoDto getDealerRebateDetailQueryList(Map<String, String> queryParam) {
		List<Object> params = new ArrayList<Object>();
		String sql = getRebateDetailQuerySql(queryParam, params);
		PageInfoDto pageInfoDto = OemDAOUtil.pageQuery(sql, params);
		return pageInfoDto;

	}

	/**
	 * 返利使用明细 下载
	 * 
	 * @param queryParam
	 * @return pageInfoDto
	 */
	public List<Map> getDealerRebateDetailDownloadList(Map<String, String> queryParam) {
		List<Object> params = new ArrayList<Object>();
		String sql = getRebateDetailQuerySql(queryParam, params);
		List<Map> orderList = OemDAOUtil.downloadPageQuery(sql, params);
		return orderList;
	}

	/**
	 * SQL组装 返利使用明细
	 * 
	 * @param queryParam
	 * @param params
	 * @return
	 */
	private String getRebateDetailQuerySql(Map<String, String> queryParam, List<Object> params) {

		StringBuilder sql = new StringBuilder();
		sql.append("select t1.*,t2.REBATE_USE, (t1.REBATE_TOTAL-t2.REBATE_USE)REBATE_REMAIN \n");
		sql.append(
				"   from  (select  TD.DEALER_CODE,TD.DEALER_SHORTNAME,TVR.REBATE_TYPE,(select CODE_DESC from TT_VS_REBATE_TYPE where CODE_ID=TVR.REBATE_TYPE) REBATE_NAME,sum(TVR.REBATE_AMOUNT) REBATE_TOTAL \n");
		sql.append("				from  TT_VS_REBATE     TVR, \n");
		sql.append("  			 	  TM_DEALER        TD \n");
		sql.append(
				"    			where  TVR.DEALER_CODE = TD.DEALER_CODE group by TD.DEALER_CODE,TD.DEALER_SHORTNAME,TVR.REBATE_TYPE)    t1 \n");
		sql.append(
				"   right join  (select DEALER_CODE,REBATE_TYPE,sum(case when REBATE_AMOUNT is not null then rebate_amount when ticket_amount is not null then ticket_amount end ) REBATE_USE \n");
		sql.append("   				from TT_VS_REBATE_EMPLOY \n");
		sql.append("  				group by DEALER_CODE,REBATE_TYPE)  t2 \n");
		sql.append("    on t1.DEALER_CODE=t2.DEALER_CODE \n");
		sql.append("   and t1.REBATE_TYPE=t2.REBATE_TYPE \n");
		sql.append("   where 1=1 \n");
		sql.append(" \n");
		// and T1.DEALER_CODE in('10037', '10046') AND T1.REBATE_TYPE
		// ='2013092209920818'
		// order by t1.DEALER_CODE,t1.REBATE_TYPE
		// 经销商代码
		if (!StringUtils.isNullOrEmpty(queryParam.get("dealerCode"))) {
			String dealerCode = queryParam.get("dealerCode");
			String s = "";
			String[] split = dealerCode.split(",");
			for (String dealer : split) {
				s = s + "'" + dealer + "'" + ",";
			}
			String ss = s.substring(0, s.length() - 1);
			sql.append(" and t1.DEALER_CODE in(" + ss + ") \n");
			// params.add(queryParam.get("dealerCode"));
		}
		// 返利类型
		if (!StringUtils.isNullOrEmpty(queryParam.get("rebateType"))) {
			sql.append(" and T1.REBATE_TYPE =? \n");
			params.add(queryParam.get("rebateType"));
		} // )tt
			// sql.append(" ) tt \n");
		System.out.println(sql.toString());
		return sql.toString();
	}

}
