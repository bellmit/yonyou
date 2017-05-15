package com.yonyou.dms.report.service.impl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.yonyou.dms.framework.DAO.DAOUtil;
import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.framework.util.FrameworkUtil;
import com.yonyou.dms.function.common.DictCodeConstants;
import com.yonyou.dms.function.exception.ServiceBizException;

@Service
public class PotentialCustomersReportServiceImpl implements PotentialCustomersReportService {

	/**
	 * 查询客户来源明细全部
	 */
	public PageInfoDto queryVisitingRecordInfo(Map<String, String> queryParam) throws ServiceBizException {
		StringBuffer sb = new StringBuffer();
		List<Object> queryList = new ArrayList<Object>();
		sb.append(" select df.*,co.code_cn_desc from (");
		sb.append(
				" select A.CUS_SOURCE, A.dealer_code,SUM(A.count_week) as COUNT_WEEK ,SUM(A.count_month) as COUNT_MONTH,SUM(A.count_year) as COUNT_YEAR from (");

		sb.append(
				" select CUS_SOURCE,dealer_code,count(1) as count_week,0 as count_month,0 as count_year from TT_VISITING_RECORD ");
		sb.append("where dealer_code='" + FrameworkUtil.getLoginInfo().getDealerCode() + "' ");
		sb.append(" AND WEEK(TT_VISITING_RECORD.VISIT_TIME) = WEEK (' " + queryParam.get("startdate"));
		sb.append(" ')  AND YEAR(TT_VISITING_RECORD.VISIT_TIME) = YEAR (' " + queryParam.get("startdate"));
		sb.append(" ') group by CUS_SOURCE ");
		sb.append(
				"union all select CUS_SOURCE,dealer_code,0 as count_week,count(1) as count_month, 0 as count_year from TT_VISITING_RECORD ");
		sb.append(" where dealer_code='" + FrameworkUtil.getLoginInfo().getDealerCode() + "' ");
		sb.append(" AND MONTH(TT_VISITING_RECORD.VISIT_TIME) = MONTH (' " + queryParam.get("startdate"));
		sb.append(" ' ) AND YEAR (TT_VISITING_RECORD.VISIT_TIME) = YEAR (' " + queryParam.get("startdate"));
		sb.append(" ' ) group by CUS_SOURCE ");
		sb.append(
				" union all select CUS_SOURCE,dealer_code,0 as count_week,0 as count_month,count(1) as count_year from TT_VISITING_RECORD ");
		sb.append(" where dealer_code='" + FrameworkUtil.getLoginInfo().getDealerCode() + "' ");
		sb.append(" AND YEAR(TT_VISITING_RECORD.VISIT_TIME) =YEAR (' " + queryParam.get("startdate"));
		sb.append(" ' ) group by CUS_SOURCE ) A " + " group by A.CUS_SOURCE ) df");
		sb.append(" left join tc_code co on df.cus_source =co.code_id ");
		
		
		System.err.println(queryParam.get("startdate"));
		
		PageInfoDto pageInfoDto = DAOUtil.pageQuery(sb.toString(), queryList);
		return pageInfoDto;

	}
	
	/**
	 * 展厅来源报表导出
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public List<Map> exportPotentialCustomersStreamAnalysis(Map<String, String> queryParam)
			throws ServiceBizException, SQLException {
		StringBuffer sb = new StringBuffer();
		List<Object> queryList = new ArrayList<Object>();
		sb.append(" select df.*,co.code_cn_desc from (");
		sb.append(
				" select A.CUS_SOURCE, A.dealer_code,SUM(A.count_week) as COUNT_WEEK ,SUM(A.count_month) as COUNT_MONTH,SUM(A.count_year) as COUNT_YEAR from (");

		sb.append(
				" select CUS_SOURCE,dealer_code,count(1) as count_week,0 as count_month,0 as count_year from TT_VISITING_RECORD ");
		sb.append("where dealer_code='" + FrameworkUtil.getLoginInfo().getDealerCode() + "' ");
		sb.append(" AND WEEK(TT_VISITING_RECORD.VISIT_TIME) = WEEK (' " + queryParam.get("startdate"));
		sb.append(" ')  AND YEAR(TT_VISITING_RECORD.VISIT_TIME) = YEAR (' " + queryParam.get("startdate"));
		sb.append(" ') group by CUS_SOURCE ");
		sb.append(
				"union all select CUS_SOURCE,dealer_code,0 as count_week,count(1) as count_month, 0 as count_year from TT_VISITING_RECORD ");
		sb.append(" where dealer_code='" + FrameworkUtil.getLoginInfo().getDealerCode() + "' ");
		sb.append(" AND MONTH(TT_VISITING_RECORD.VISIT_TIME) = MONTH (' " + queryParam.get("startdate"));
		sb.append(" ' ) AND YEAR (TT_VISITING_RECORD.VISIT_TIME) = YEAR (' " + queryParam.get("startdate"));
		sb.append(" ' ) group by CUS_SOURCE ");
		sb.append(
				" union all select CUS_SOURCE,dealer_code,0 as count_week,0 as count_month,count(1) as count_year from TT_VISITING_RECORD ");
		sb.append(" where dealer_code='" + FrameworkUtil.getLoginInfo().getDealerCode() + "' ");
		sb.append(" AND YEAR(TT_VISITING_RECORD.VISIT_TIME) =YEAR (' " + queryParam.get("startdate"));
		sb.append(" ' ) group by CUS_SOURCE ) A " + " group by A.CUS_SOURCE ) df");
		sb.append(" left join tc_code co on df.cus_source =co.code_id ");
		
		System.err.println(sb+"***********");
		System.err.println(queryParam.get("startdate"));
		List<Map> list = DAOUtil.findAll(sb.toString(), queryList);
		for (Map map : list) {
			if (map.get("CUS_SOURCE") != null && map.get("CUS_SOURCE") != "") {
				if (Integer.parseInt(map.get("CUS_SOURCE").toString()) == DictCodeConstants.DICT_CUS_SOURCE_EXHI_HALL) {
					map.put("CUS_SOURCE", "来店/展厅顾客");
				} else if (Integer.parseInt(
						map.get("CUS_SOURCE").toString()) == DictCodeConstants.DICT_CUS_SOURCE_MARKET_ACTIVITY) {
					map.put("CUS_SOURCE", "活动/展厅活动");
				} else if (Integer.parseInt(
						map.get("CUS_SOURCE").toString()) == DictCodeConstants.DICT_CUS_SOURCE_TENURE_CUSTOMER) {
					map.put("CUS_SOURCE", "保客增购");
				} else if (Integer
						.parseInt(map.get("CUS_SOURCE").toString()) == DictCodeConstants.DICT_CUS_SOURCE_FRIEND) {
					map.put("CUS_SOURCE", "保客推荐");
				} else if (Integer
						.parseInt(map.get("CUS_SOURCE").toString()) == DictCodeConstants.DICT_CUS_SOURCE_OTHER) {
					map.put("CUS_SOURCE", "其他");
				} else if (Integer.parseInt(
						map.get("CUS_SOURCE").toString()) == DictCodeConstants.DICT_CUS_SOURCE_PHONE_VISITER) {
					map.put("CUS_SOURCE", "陌生拜访/电话销售");
				} else if (Integer
						.parseInt(map.get("CUS_SOURCE").toString()) == DictCodeConstants.DICT_CUS_SOURCE_INTERNET) {
					map.put("CUS_SOURCE", "网络/电子商务");
				} else if (Integer
						.parseInt(map.get("CUS_SOURCE").toString()) == DictCodeConstants.DICT_CUS_SOURCE_BY_WAY) {
					map.put("CUS_SOURCE", "路过");
				} else if (Integer
						.parseInt(map.get("CUS_SOURCE").toString()) == DictCodeConstants.DICT_CUS_SOURCE_ORG_CODE) {
					map.put("CUS_SOURCE", "代理商/代销网点");
				} else if (Integer.parseInt(
						map.get("CUS_SOURCE").toString()) == DictCodeConstants.DICT_CUS_SOURCE_PHONE_CUSTOMER) {
					map.put("CUS_SOURCE", "来电顾客");
				} else if (Integer
						.parseInt(map.get("CUS_SOURCE").toString()) == DictCodeConstants.DICT_CUS_SOURCE_BY_DCC) {
					map.put("CUS_SOURCE", "DCC转入");
				} else if (Integer
						.parseInt(map.get("CUS_SOURCE").toString()) == DictCodeConstants.DICT_CUS_SOURCE_BY_SHOW) {
					map.put("CUS_SOURCE", "活动-车展");
				} else if (Integer.parseInt(
						map.get("CUS_SOURCE").toString()) == DictCodeConstants.DICT_CUS_SOURCE_BY_EXPERIENCE_DAY) {
					map.put("CUS_SOURCE", "活动-外场试驾活动");
				} else if (Integer
						.parseInt(map.get("CUS_SOURCE").toString()) == DictCodeConstants.DICT_CUS_SOURCE_BY_CARAVAN) {
					map.put("CUS_SOURCE", "活动-巡展/外展");
				} else if (Integer
						.parseInt(map.get("CUS_SOURCE").toString()) == DictCodeConstants.DICT_CUS_SOURCE_BY_WEB) {
					map.put("CUS_SOURCE", "保客置换");
				} else if (Integer
						.parseInt(map.get("CUS_SOURCE").toString()) == DictCodeConstants.DICT_CUS_SOURCE_DS_WEBSITE) {
					map.put("CUS_SOURCE", "官网客户  ");
				}
			}
		}
		return list;
	}
}
