package com.yonyou.dms.vehicle.dao.k4Order;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.yonyou.dms.framework.DAO.OemBaseDAO;
import com.yonyou.dms.framework.DAO.OemDAOUtil;
import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.function.utils.common.CommonUtils;
import com.yonyou.dms.function.utils.common.StringUtils;

/**
 * @author liujiming
 * @date 2017年3月13日
 */
@Repository
public class DealerRebateQueryDao extends OemBaseDAO {

	/**
	 * 返利发放查询 汇总查询
	 * 
	 * @param queryParam
	 * @return pageInfoDto
	 */
	public PageInfoDto getDealerRebateTotalQueryList(Map<String, String> queryParam) {
		List<Object> params = new ArrayList<Object>();
		String sql = getTotalQuerySql(queryParam, params);
		PageInfoDto pageInfoDto = OemDAOUtil.pageQuery(sql, params);
		return pageInfoDto;

	}

	/**
	 * 返利发放查询 明细下载
	 * 
	 * @param queryParam
	 * @return pageInfoDto
	 */
	public List<Map> getDealerRebateDetailDownloadList(Map<String, String> queryParam) {
		List<Object> params = new ArrayList<Object>();
		String sql = getDetailQuerySql(queryParam, params);
		List<Map> orderList = OemDAOUtil.downloadPageQuery(sql, params);
		return orderList;
	}

	/**
	 * 返利发放查询 明细查询
	 * 
	 * @param queryParam
	 * @return pageInfoDto
	 */
	public PageInfoDto getDealerRebateDetailQueryList(Map<String, String> queryParam) {
		List<Object> params = new ArrayList<Object>();
		String sql = getDetailQuerySql(queryParam, params);
		PageInfoDto pageInfoDto = OemDAOUtil.pageQuery(sql, params);
		return pageInfoDto;

	}

	/**
	 * SQL组装 汇总查询
	 * 
	 * @param queryParam
	 * @param params
	 * @return
	 */
	private String getTotalQuerySql(Map<String, String> queryParam, List<Object> params) {

		StringBuilder sql = new StringBuilder();

		sql.append("SELECT t2.*,t1.CREATE_DATE \n");
		sql.append(
				"  FROM (SELECT t.*,IFNULL(tt.REBATE_USE,0) REBATE_USE,t.REBATE_TOTAL-IFNULL(tt.REBATE_USE,0) REMAIN_MONEY \n");
		sql.append(
				"  		  FROM (SELECT  IFNULL(TVRT.TYPE_CODE,'') REBATE_CODE,TVRT.CODE_DESC REBATE_NAME,TVR.REBATE_TYPE,SUM(TVR.REBATE_AMOUNT) REBATE_TOTAL \n");
		sql.append("          		  FROM  TT_VS_REBATE         TVR, \n");
		sql.append("						TT_VS_REBATE_TYPE    TVRT, \n");
		sql.append("			    		TM_DEALER            TD \n");
		sql.append("		  		  WHERE  TVR.DEALER_CODE = TD.DEALER_CODE \n");
		sql.append("		    		AND  TVR.REBATE_TYPE = TVRT.CODE_ID \n");
		// 经销商代码
		String dealerCode = CommonUtils.checkNull(queryParam.get("dealerCode"));
		if (!dealerCode.equals("") && dealerCode.length() > 0) {
			String s = "";
			if (dealerCode.indexOf(",") > 0) {
				String[] str = dealerCode.split(",");
				for (int i = 0; i < str.length; i++) {
					s += "'" + str[i] + "'";
					if (i < str.length - 1)
						s += ",";
				}
			} else {
				s = "'" + dealerCode + "'";
			}
			sql.append("and TVR.DEALER_CODE in (").append(s).append(")\n");
		}
		sql.append("AND  TVRT.STATUS=10011001 \n");
		sql.append("GROUP BY TVRT.TYPE_CODE,TVRT.CODE_DESC,TVR.REBATE_TYPE)  t \n");
		sql.append(
				"  LEFT JOIN (SELECT REBATE_TYPE,SUM(CASE WHEN REBATE_AMOUNT IS NOT NULL THEN rebate_amount WHEN ticket_amount IS NOT NULL THEN ticket_amount END) REBATE_USE \n");
		sql.append("FROM TT_VS_REBATE_EMPLOY  GROUP BY REBATE_TYPE)  tt \n");
		sql.append(" ON t.REBATE_TYPE=tt.REBATE_TYPE)  t2 \n");
		sql.append(
				"	LEFT JOIN (SELECT TVR.REBATE_TYPE,MAX(DATE_FORMAT(TVR.CREATE_DATE,'%Y-%c-%d %H:%i:%s')) CREATE_DATE \n");
		sql.append(" 	FROM TT_VS_REBATE  TVR GROUP BY TVR.REBATE_TYPE) t1 \n");
		sql.append("    ON  t1.REBATE_TYPE=t2.REBATE_TYPE \n");
		sql.append("  	WHERE 1=1 \n");
		// 查询条件
		// 返利类型
		if (!StringUtils.isNullOrEmpty(queryParam.get("rebateType"))) {
			sql.append(" and T1.REBATE_TYPE =? \n");
			params.add(queryParam.get("rebateType"));
		}
		// 返利上传日期 起始
		if (!StringUtils.isNullOrEmpty(queryParam.get("beginDate"))) {
			sql.append(" and DATE_FORMAT(T1.CREATE_DATE,'%Y-%m-%d')>='" + queryParam.get("beginDate") + "' \n");
		}
		// 返利上传日期 结束
		if (!StringUtils.isNullOrEmpty(queryParam.get("endDate"))) {
			sql.append("AND DATE_FORMAT(T1.CREATE_DATE,'%Y-%m-%d') <= '" + queryParam.get("endDate") + "' \n");

		}
		System.out.println(sql.toString());
		return sql.toString();
	}

	/**
	 * SQL组装 汇总查询
	 * 
	 * @param queryParam
	 * @param params
	 * @return
	 */
	private String getDetailQuerySql(Map<String, String> queryParam, List<Object> params) {

		// 查询条件
		StringBuilder sql = new StringBuilder();
		sql.append(
				"SELECT distinct t1.*,IFNULL(t2.REBATE_USE,0) REBATE_USE,t1.REBATE_TOTAL-IFNULL(t2.REBATE_USE,0) REMAIN_MONEY \n");
		sql.append(
				"  FROM (SELECT  TD.DEALER_CODE,TD.DEALER_SHORTNAME,TVRT.TYPE_CODE REBATE_CODE,TVR.CREATE_DATE,TVRT.CODE_DESC REBATE_NAME,TVR.VIN,TVR.REBATE_TYPE,TVR.REBATE_AMOUNT REBATE_TOTAL \n");
		sql.append("          FROM  TT_VS_REBATE         TVR, \n");
		sql.append("				TT_VS_REBATE_TYPE    TVRT, \n");
		sql.append("			    TM_DEALER            TD \n");
		sql.append("		  WHERE  TVR.DEALER_CODE = TD.DEALER_CODE \n");
		sql.append("		    AND  TVR.REBATE_TYPE = TVRT.CODE_ID \n");
		sql.append("		    AND  TVRT.STATUS=10011001 \n");
		sql.append(
				"          GROUP BY TD.DEALER_CODE,TD.DEALER_SHORTNAME,TVRT.TYPE_CODE,TVR.CREATE_DATE,TVRT.CODE_DESC,TVR.REBATE_TYPE,TVR.VIN,TVR.REBATE_AMOUNT)  t1 \n");
		sql.append(
				"  LEFT JOIN (SELECT DEALER_CODE,REBATE_TYPE,VIN,SUM(CASE WHEN REBATE_AMOUNT IS NOT NULL THEN rebate_amount WHEN ticket_amount IS NOT NULL THEN ticket_amount END) REBATE_USE \n");
		sql.append("	  		   FROM TT_VS_REBATE_EMPLOY  GROUP BY DEALER_CODE,REBATE_TYPE,VIN)  t2 \n");
		sql.append("  ON t1.DEALER_CODE=t2.DEALER_CODE AND t1.REBATE_TYPE=t2.REBATE_TYPE AND t1.VIN=t2.VIN \n");
		sql.append("  WHERE 1=1 \n");
		// 经销商代码
		if (!StringUtils.isNullOrEmpty(queryParam.get("dealerCode"))) {
			String string = queryParam.get("dealerCode");
			String[] split = string.split(",");
			String s = "";
			for (String st : split) {
				s = "'" + st + "'" + "," + s;
			}
			String substring = s.substring(0, s.length() - 1);
			sql.append(" and t1.DEALER_CODE in(" + substring + ")\n");
		}
		// 返利类型
		if (!StringUtils.isNullOrEmpty(queryParam.get("rebateType"))) {
			sql.append(" and T1.REBATE_TYPE =? \n");
			params.add(queryParam.get("rebateType"));
		}
		// 返利上传日期 起始
		if (!StringUtils.isNullOrEmpty(queryParam.get("beginDate"))) {
			sql.append(" and T1.CREATE_DATE>='" + queryParam.get("beginDate") + " 00:00:00" + "' \n");
		}
		// 返利上传日期 结束
		if (!StringUtils.isNullOrEmpty(queryParam.get("endDate"))) {
			sql.append(" AND T1.CREATE_DATE <= '" + queryParam.get("endDate") + " 23:59:59" + "' \n");

		}
		System.out.println(sql.toString());
		return sql.toString();
	}

	public List<Map> getDealerRebatetotalDownloadList(Map<String, String> queryParam) {
		List<Object> params = new ArrayList<Object>();
		String sql = getTotalQuerySql(queryParam, params);
		return OemDAOUtil.downloadPageQuery(sql, null);
	}

}
