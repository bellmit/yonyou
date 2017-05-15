package com.yonyou.dms.repair.dao.accountBalanceDao;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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
 * @author lianxinglu
 *
 */
@Repository
@SuppressWarnings("all")
public class AccountBalanceDao extends OemBaseDAO {
	// 余额查询(OEm)
	public PageInfoDto accountBalanceQuery(Map<String, String> queryParam) {
		String sql = getQuerySql(queryParam, null);
		return OemDAOUtil.pageQuery(sql, null);

	}

	// 账户余额下载
	public List<Map> download(Map<String, String> queryParam) {
		String sql = getQuerySql(queryParam, null);
		return OemDAOUtil.downloadPageQuery(sql, null);
	}

	// 账户异动明细查询
	public PageInfoDto accountChangeDetailQuery(Map<String, String> queryParam) {
		String sql = getQuerySql1(queryParam, null);
		System.out.println(sql.toString());
		return OemDAOUtil.pageQuery(sql, null);
	}

	// 账户异动明细查询下载
	public List<Map> querydownload(Map<String, String> queryParam) {
		String sql = getQuerySql1(queryParam, null);
		return OemDAOUtil.downloadPageQuery(sql, null);
	}

	// 返利发放查询
	public PageInfoDto rebateIssueQuery(Map<String, String> queryParam) {
		String sql2 = getQuerySql2(queryParam, null);
		return OemDAOUtil.pageQuery(sql2, null);
	}

	public List<Map> rebateIssueDownLoad(Map<String, String> queryParam) {
		String sql2 = getQuerySql2(queryParam, null);
		return OemDAOUtil.downloadPageQuery(sql2, null);
	}

	// 返利发放查询下载
	private String getQuerySql2(Map<String, String> queryParam, List<Object> params) {
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT DR.DEALER_ID, -- 经销商ID \n");
		sql.append("       D.DEALER_CODE, -- 经销商代码 \n");
		sql.append("       D.DEALER_SHORTNAME, -- 经销商简称 \n");
		sql.append("       DRT.REBATE_TYPE_DESC AS REBATE_TYPE_DESC, -- 返利发放类型 \n");
		sql.append("       date_format(DTD.CREATE_DATE, '%Y-%m-%d') AS OPERA_DATE, -- 返利发放日期 \n");
		sql.append("       DTD.AMOUNT, -- 返利金额 \n");
		sql.append(" DTD.TYPE_ID, -- 返利类型 \n");
		sql.append("       DTD.REBATE_MARK -- 返利说明 \n");
		sql.append("  FROM TT_DEALER_REBATE DR \n");
		sql.append(" INNER JOIN TT_DEALER_REBATE_DTL DTD ON DTD.REBATE_ID = DR.REBATE_ID \n");
		sql.append(" INNER JOIN TM_DEALER D ON D.DEALER_ID = DR.DEALER_ID \n");
		sql.append(" INNER JOIN TT_DEALER_REBATE_TYPE DRT ON DRT.REBATE_TYPE_ID = DTD.TYPE_ID \n");
		sql.append(" WHERE DTD.REBATE_DIRECTION = '" + OemDictCodeConstants.REBATE_DIRACT_01 + "' \n");
		// 经销商
		if (!StringUtils.isNullOrEmpty(queryParam.get("dealerCode"))) {
			String dealerCode = queryParam.get("dealerCode");
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
			sql.append("   AND D.DEALER_CODE IN (" + s + ") \n");
		}

		// 返利发放日期 begin
		if (!StringUtils.isNullOrEmpty(queryParam.get("beginDate"))) {
			sql.append("   AND date_format(DTD.CREATE_DATE, '%Y-%m-%d') >= '" + queryParam.get("beginDate") + "' \n");
		}

		// 返利发放日期 end
		if (!StringUtils.isNullOrEmpty(queryParam.get("endDate"))) {
			sql.append("   AND date_format(DTD.CREATE_DATE, '%Y-%m-%d') <= '" + queryParam.get("endDate") + "' \n");
		}
		// 返利发放类型
		if (!StringUtils.isNullOrEmpty(queryParam.get("rebateTypeCode"))) {
			sql.append("   AND DRT.REBATE_TYPE_CODE = '" + queryParam.get("rebateTypeCode") + "' \n");
		}

		// sql.append(" ORDER BY D.DEALER_CODE, D.DEALER_SHORTNAME,
		// date_format(DTD.CREATE_DATE, '%y-%m-%d') \n");
		System.out.println(sql.toString());
		return sql.toString();
	}

	private String getQuerySql1(Map<String, String> queryParam, List<Object> params) {
		LoginInfoDto loginInfo = ApplicationContextHelper.getBeanByType(LoginInfoDto.class);
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT t.* from (SELECT (DATE(T.OPERA_DATE)) AS OPERA_DATE, -- 操作日期 \n");
		sql.append("       D.DEALER_CODE, -- 经销商代码 \n");
		sql.append("       D.DEALER_SHORTNAME, -- 经销商简称 \n");
		sql.append("       (SELECT CODE_DESC FROM TC_CODE_DCS WHERE CODE_ID = A.ACC_TYPE) AS ACC_TYPE, -- 账户类型 \n");
		sql.append("       (CASE WHEN T.OPERA_TYPE = '" + OemDictCodeConstants.ACCOUNT_OPPER_TYPE_01 + "' OR \n");
		sql.append("                  T.OPERA_TYPE = '" + OemDictCodeConstants.ACCOUNT_OPPER_TYPE_05
				+ "' THEN T.AMOUNT ELSE 0 END) AMOUNT_A, -- 打款金额 \n");
		sql.append("       (CASE WHEN T.INVOICE_TYPE = '" + OemDictCodeConstants.K4_INVOICE_TYPE_01
				+ "' THEN T.AMOUNT \n");
		sql.append("             WHEN T.INVOICE_TYPE = '" + OemDictCodeConstants.K4_INVOICE_TYPE_02
				+ "' THEN T.AMOUNT * -1 \n");
		sql.append("             ELSE 0 END) AMOUNT_B, -- 扣款金额(不含税) \n");
		sql.append("       (CASE WHEN T.INVOICE_TYPE = '" + OemDictCodeConstants.K4_INVOICE_TYPE_01
				+ "' THEN (T.AMOUNT + T.TAX_AMOUNT ) \n");
		sql.append("             WHEN T.INVOICE_TYPE = '" + OemDictCodeConstants.K4_INVOICE_TYPE_02
				+ "' THEN (T.AMOUNT + T.TAX_AMOUNT ) * -1 \n");
		sql.append("             ELSE 0 END) AMOUNT_C, -- 扣款金额(含税) \n");
		// sql.append(" T.AMOUNT, -- 金额 \n");
		sql.append("       TVO.VIN, -- VIN\n");
		sql.append("       T.EVIDENCE_NO, -- 凭证号 \n");
		sql.append("       T.INVOICE_NO, -- SAP发票号 \n");
		sql.append("       T.ORDER_NO, -- 销售订单号 \n");
		sql.append("       T.REMARK -- 描述 \n");
		sql.append("  FROM TT_DEALER_ACCOUNT_DTL T \n");
		sql.append("  LEFT JOIN TT_DEALER_ACCOUNT A ON T.ACC_ID = A.ACC_ID \n");
		sql.append("  LEFT JOIN TM_DEALER D ON D.DEALER_ID = A.DEALER_ID \n");
		sql.append("  LEFT JOIN TT_VS_ORDER TVO on TVO.INVOICE_NO=T.INVOICE_NO \n");
		sql.append(" WHERE 1 = 1 \n");

		if (!StringUtils.isNullOrEmpty(loginInfo.getDealerCode())) {
			// 针对经销商端的查询条件
			sql.append("   AND D.DEALER_CODE = '" + loginInfo.getDealerCode() + "' \n");
		} else {
			// 针对车厂端（OTD、大区、小区）的查询条件
			if (!loginInfo.getDutyType().toString().equals(OemDictCodeConstants.DUTY_TYPE_DEPT.toString())
					&& !loginInfo.getDutyType().toString().equals(OemDictCodeConstants.DUTY_TYPE_COMPANY.toString())) {
				sql.append("   AND (D.DEALER_ID IN (" + getDealersByArea(loginInfo.getOrgId().toString()) + ") \n");
				sql.append("    OR  D.DEALER_ID IN (" + getDealersByArea(loginInfo.getOrgId().toString()) + ")) \n");
			}
		}

		// 时间区间 BeginqueryParam.get("beginDate")
		if (!StringUtils.isNullOrEmpty(queryParam.get("beginDate"))) {
			sql.append("   AND T.OPERA_DATE >= '" + queryParam.get("beginDate") + "' \n");
		}

		// 时间区间 End
		if (!StringUtils.isNullOrEmpty(queryParam.get("endDate"))) {
			sql.append("   AND T.OPERA_DATE <= '" + queryParam.get("endDate") + "' \n");
		}

		// 经销商
		if (!StringUtils.isNullOrEmpty(queryParam.get("dealerCode"))) {
			String dealerCode = queryParam.get("dealerCode");
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
			sql.append("   AND D.DEALER_CODE IN (" + s + ") \n");
		}

		// 账户类型
		if (!StringUtils.isNullOrEmpty(queryParam.get("accType"))) {
			sql.append("   AND A.ACC_TYPE = '" + queryParam.get("accType") + "' \n");
		}

		sql.append(" ORDER BY D.DEALER_CODE, T.OPERA_DATE DESC ) t \n");
		return sql.toString();
	}

	/**
	 * SQL组装
	 * 
	 * @param queryParam
	 * @param params
	 * @return
	 */
	private String getQuerySql(Map<String, String> queryParam, List<Object> params) {
		LoginInfoDto loginInfo = ApplicationContextHelper.getBeanByType(LoginInfoDto.class);

		StringBuffer sql = new StringBuffer();
		sql.append(" \n");
		sql.append("SELECT D.DEALER_CODE, -- 经销商代码 \n");
		sql.append("       D.DEALER_SHORTNAME, -- 经销商名称 \n");

		sql.append("       SUM((case WHEN T.ACC_TYPE='" + OemDictCodeConstants.K4_PAYMENT_01
				+ "' THEN T.AMOUNT ELSE '0.00' END)) AS COUNT1, -- 现金 \n");

		sql.append("       SUM(CASE WHEN T.FINANCING_STATUS = 'A' OR T.FINANCING_STATUS = 'W' \n");
		sql.append("                THEN (case WHEN T.ACC_TYPE='" + OemDictCodeConstants.K4_PAYMENT_02
				+ "' THEN T.USABLE_AMOUNT ELSE '0.00' END) \n");
		sql.append("                ELSE '0.00' END) AS COUNT2, -- 广汽汇理汽车金融有限公司 \n");

		sql.append("       SUM(CASE WHEN T.FINANCING_STATUS = 'A' OR T.FINANCING_STATUS = 'W' \n");
		sql.append("                THEN (case WHEN T.ACC_TYPE='" + OemDictCodeConstants.K4_PAYMENT_03
				+ "' THEN T.USABLE_AMOUNT ELSE '0.00' END) \n");
		sql.append("                ELSE '0.00' END) AS COUNT3, -- 菲亚特汽车金融有限责任公司 \n");

		sql.append("       SUM(CASE WHEN T.FINANCING_STATUS = 'A' OR T.FINANCING_STATUS = 'W' \n");
		sql.append("                THEN (case WHEN T.ACC_TYPE='" + OemDictCodeConstants.K4_PAYMENT_04
				+ "' THEN T.USABLE_AMOUNT ELSE '0.00' END) \n");
		sql.append("                ELSE '0.00' END) AS COUNT4, -- 兴业银行 \n");

		sql.append("       SUM(CASE WHEN T.FINANCING_STATUS = 'A' OR T.FINANCING_STATUS = 'W' \n");
		sql.append("                THEN (case WHEN T.ACC_TYPE='" + OemDictCodeConstants.K4_PAYMENT_05
				+ "' THEN T.USABLE_AMOUNT ELSE '0.00' END) \n");
		sql.append("                ELSE '0.00' END) AS COUNT5, -- 交通银行 \n");
		sql.append("       SUM((case WHEN T.ACC_TYPE='" + OemDictCodeConstants.K4_PAYMENT_06
				+ "' THEN T.AMOUNT ELSE '0.00' END)) AS COUNT6, -- 中行的三方承兑 \n");

		sql.append("       SUM(CASE WHEN T.FINANCING_STATUS = 'A' OR T.FINANCING_STATUS = 'W' \n");
		sql.append("                THEN (case WHEN T.ACC_TYPE='" + OemDictCodeConstants.K4_PAYMENT_07
				+ "' THEN T.USABLE_AMOUNT ELSE '0.00' END) \n");
		sql.append("                ELSE '0.00' END) AS COUNT7, -- 建行融资 \n");

		sql.append("       SUM((CASE WHEN T.ACC_TYPE = '" + OemDictCodeConstants.K4_PAYMENT_01 + "' THEN T.AMOUNT \n");
		sql.append("                 WHEN T.ACC_TYPE = '" + OemDictCodeConstants.K4_PAYMENT_02
				+ "' AND (T.FINANCING_STATUS = 'A' OR T.FINANCING_STATUS = 'W') THEN T.USABLE_AMOUNT \n");
		sql.append("                 WHEN T.ACC_TYPE = '" + OemDictCodeConstants.K4_PAYMENT_03
				+ "' AND (T.FINANCING_STATUS = 'A' OR T.FINANCING_STATUS = 'W') THEN T.USABLE_AMOUNT \n");
		sql.append("                 WHEN T.ACC_TYPE = '" + OemDictCodeConstants.K4_PAYMENT_04
				+ "' AND (T.FINANCING_STATUS = 'A' OR T.FINANCING_STATUS = 'W') THEN T.USABLE_AMOUNT \n");
		sql.append("                 WHEN T.ACC_TYPE = '" + OemDictCodeConstants.K4_PAYMENT_05
				+ "' AND (T.FINANCING_STATUS = 'A' OR T.FINANCING_STATUS = 'W') THEN T.USABLE_AMOUNT \n");
		sql.append("                 WHEN T.ACC_TYPE = '" + OemDictCodeConstants.K4_PAYMENT_06 + "' THEN T.AMOUNT \n");
		sql.append("                 WHEN T.ACC_TYPE = '" + OemDictCodeConstants.K4_PAYMENT_07
				+ "' AND (T.FINANCING_STATUS = 'A' OR T.FINANCING_STATUS = 'W') THEN T.USABLE_AMOUNT \n");
		sql.append("            ELSE '0.00' END)) AS AMOUNT -- 合计 \n");

		sql.append("  FROM TT_DEALER_ACCOUNT T \n");
		sql.append(" RIGHT JOIN TM_DEALER D ON T.DEALER_ID = D.DEALER_ID \n");
		sql.append(" WHERE D.DEALER_TYPE = '" + OemDictCodeConstants.DEALER_TYPE_DVS + "' \n");
		sql.append("   AND D.STATUS = " + OemDictCodeConstants.STATUS_ENABLE + " \n");
		sql.append("   AND D.IS_DEL = '" + OemDictCodeConstants.IS_DEL_00 + "' \n");

		if (!StringUtils.isNullOrEmpty(loginInfo.getDealerCode())) {
			// 针对经销商端的查询条件
			sql.append("   AND D.DEALER_CODE = " + loginInfo.getDealerCode() + " \n");
		} else {
			// 针对车厂端（OTD、大区、小区）的查询条件
			if (!loginInfo.getDutyType().toString().equals(OemDictCodeConstants.DUTY_TYPE_DEPT.toString())
					&& !loginInfo.getDutyType().toString().equals(OemDictCodeConstants.DUTY_TYPE_COMPANY.toString())) {
				sql.append("   AND (D.DEALER_ID IN (" + getDealersByArea(loginInfo.getOrgId().toString()) + ") \n");
				sql.append("    OR  D.DEALER_ID IN (" + getDealersByArea(loginInfo.getOrgId().toString()) + ")) \n");
			}
		}

		// 经销商
		if (!StringUtils.isNullOrEmpty(queryParam.get("dealerCode"))) {
			String dealerCode = queryParam.get("dealerCode");
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
			sql.append("   AND D.DEALER_CODE IN (" + s + ") \n");
		}

		sql.append(" GROUP BY T.DEALER_ID, D.DEALER_CODE, D.DEALER_SHORTNAME \n");

		return sql.toString();

	}

	public List<Map> rebateTypeCode(Map<String, String> queryParam) {
		StringBuffer sql = new StringBuffer();
		sql.append(
				"select pp.REBATE_TYPE_CODE as REBATE_TYPE_CODE ,pp.REBATE_TYPE_DESC as REBATE_TYPE_DESC from TT_DEALER_REBATE_TYPE as pp ");
		List<Map> list = OemDAOUtil.findAll(sql.toString(), null);
		return OemDAOUtil.findAll(sql.toString(), null);
	}

	private DateFormat df = new SimpleDateFormat("yyyy-MM-dd");// 时间格式

	public List<Map> dealerPayQuery(Map<String, String> queryParam) {
		LoginInfoDto loginInfo = ApplicationContextHelper.getBeanByType(LoginInfoDto.class);
		String dateStr = df.format(new Date());

		StringBuffer sql = new StringBuffer();
		sql.append("SELECT (SELECT SUM(T.AMOUNT) AS ACCOUNT \n");
		sql.append(" FROM TT_DEALER_ACCOUNT T \n");
		sql.append("   WHERE T.DEALER_ID = " + loginInfo.getDealerId() + ") AS ACCOUNT, \n");
		sql.append("  (SELECT SUM(TM.BASE_PRICE) \n");
		sql.append("   FROM TT_VS_ORDER O, TM_MATERIAL_PRICE TM \n");
		sql.append(" WHERE TM.MATERIAL_ID = O.MATERAIL_ID \n");
		sql.append("  AND O.DEALER_ID = '" + loginInfo.getDealerId() + "' \n");
		sql.append(" and o.ORDER_STATUS in " + getOrderStatuStr()
				+ "AND ((O.ORDER_YEAR < (SELECT W.WORK_YEAR from TM_WORK_WEEK as w  WHERE DATE_FORMAT(W.START_DATE,'%Y-%m-%d %H:%s:%i') <='"
				+ dateStr + " 00:00:00' AND DATE_FORMAT(W.END_DATE, '%Y-%m-%d %H:%s:%i') >='" + dateStr
				+ " 23:59:59'))  \n");
		sql.append(
				"OR (O.ORDER_YEAR = (SELECT W.WORK_YEAR from TM_WORK_WEEK as w  WHERE DATE_FORMAT(W.START_DATE,'%Y-%m-%d %H:%s:%i') <='"
						+ dateStr + " 00:00:00' AND DATE_FORMAT(W.END_DATE, '%Y-%m-%d %H:%s:%i') >='" + dateStr
						+ " 23:59:59') AND \n");
		sql.append(
				"O.ORDER_MONTH <= (SELECT W.WORK_MONTH from TM_WORK_WEEK as w  WHERE DATE_FORMAT(W.START_DATE,'%Y-%m-%d %H:%s:%i') <='"
						+ dateStr + " 00:00:00' AND DATE_FORMAT(W.END_DATE, '%Y-%m-%d %H:%s:%i') >='" + dateStr
						+ " 23:59:59') AND \n");
		sql.append(
				" O.ORDER_WEEK < (SELECT W.WORK_WEEK+ 1 from TM_WORK_WEEK as w  WHERE DATE_FORMAT(W.START_DATE,'%Y-%m-%d %H:%s:%i') <='"
						+ dateStr + " 00:00:00' AND DATE_FORMAT(W.END_DATE, '%Y-%m-%d %H:%s:%i') >='" + dateStr
						+ " 23:59:59')))) AS NOW_WEEK, \n");
		sql.append("(SELECT SUM(TM.BASE_PRICE) FROM TT_VS_ORDER O, TM_MATERIAL_PRICE TM \n");
		sql.append("  WHERE TM.MATERIAL_ID = O.MATERAIL_ID \n");
		sql.append("AND O.DEALER_ID = '" + loginInfo.getDealerId() + "' \n");
		sql.append(" and o.ORDER_STATUS in    " + getOrderStatuStr() + " AND O.ORDER_WEEK =  (SELECT W.WORK_WEEK \n");
		sql.append(
				"FROM TM_WORK_WEEK W  WHERE WORK_WEEK > (SELECT W.WORK_WEEK from TM_WORK_WEEK as w  WHERE DATE_FORMAT(W.START_DATE,'%Y-%m-%d %H:%s:%i') <='"
						+ dateStr + " 00:00:00' AND DATE_FORMAT(W.END_DATE, '%Y-%m-%d %H:%s:%i') >='" + dateStr
						+ " 23:59:59') \n");
		sql.append(" ORDER BY WORK_WEEK ASC limit 1)) AS NEXT_WEEK \n");
		System.out.println(sql.toString());
		List<Map> list = OemDAOUtil.findAll(sql.toString(), null);
		return list;
	}

	/**
	 * 应付资金 订单状态字符串
	 */
	private String getOrderStatuStr() {
		StringBuilder sb = new StringBuilder();
		sb.append("  (").append(OemDictCodeConstants.SALE_ORDER_TYPE_02).append(",")
				.append(OemDictCodeConstants.SALE_ORDER_TYPE_04).append(",")
				.append(OemDictCodeConstants.SALE_ORDER_TYPE_05).append(",")
				.append(OemDictCodeConstants.SALE_ORDER_TYPE_06).append(")");

		return sb.toString();
	}

	public PageInfoDto dealerpayquery(Map<String, String> queryParam) {
		LoginInfoDto loginInfo = ApplicationContextHelper.getBeanByType(LoginInfoDto.class);
		String dateStr = df.format(new Date());// 取得当前时间，以便于取得工作周相应工作周
		List<Object> param = new ArrayList<Object>();
		StringBuffer sql = new StringBuffer();
		sql.append(" \n");
		sql.append("SELECT VM.BRAND_NAME, \n");
		sql.append("       VM.SERIES_NAME, \n");
		sql.append("       VM.MODEL_NAME, \n");
		sql.append("       VM.COLOR_NAME, \n");
		sql.append("       VM.TRIM_NAME, \n");
		sql.append("       O.ORDER_NO, \n");
		sql.append("       O.ORDER_STATUS, \n");
		sql.append("       O.ORDER_DATE, \n");
		sql.append("       O.ORDER_WEEK, \n");
		sql.append("       O.WHOLESALE_PRICE, \n");
		sql.append("       O.ORDER_TYPE, \n");
		sql.append("       T.BASE_PRICE \n");
		sql.append("  FROM TT_VS_ORDER O \n");
		sql.append("  LEFT JOIN (" + getVwMaterialSql() + ") VM \n");
		sql.append("    ON O.MATERAIL_ID = VM.MATERIAL_ID \n");
		sql.append("  LEFT JOIN TM_MATERIAL_PRICE T \n");
		sql.append("    ON T.MATERIAL_ID = VM.MATERIAL_ID \n");
		sql.append(" WHERE O.DEALER_ID = " + loginInfo.getDealerId() + "  \n");
		// 订单状态
		sql.append("   AND O.ORDER_STATUS IN  ").append(getOrderStatuStr());
		Map map2 = PageResult(queryParam);
		String orderW = map2.get("GROUP_WEEK").toString();
		int orderWeek = Integer.parseInt(orderW);
		if (orderWeek != -1) {// 相对应周的查询
			sql.append(
					"   AND O.ORDER_YEAR = (SELECT w.WORK_YEAR from TM_WORK_WEEK w WHERE     DATE_FORMAT(W.START_DATE,'%Y-%m-%d %H:%s:%i')<='"
							+ dateStr + "'" + "AND DATE_FORMAT(W.END_DATE, '%Y-%m-%d %H:%s:%i') >= '" + dateStr
							+ "') AND O.ORDER_MONTH = (SELECT w.WORK_MONTH from TM_WORK_WEEK w WHERE     DATE_FORMAT(W.START_DATE,'%Y-%m-%d %H:%s:%i')<='"
							+ dateStr + "' " + "AND DATE_FORMAT(W.END_DATE, '%Y-%m-%d %H:%s:%i') >= '" + dateStr
							+ "')     \n");
			sql.append("   AND O.ORDER_WEEK = " + orderWeek + "   \n");
		} else {
			// 历史订单查询
			sql.append(
					"   AND ((O.ORDER_YEAR =(SELECT w.WORK_YEAR from TM_WORK_WEEK w WHERE  DATE_FORMAT(W.START_DATE,'%Y-%m-%d %H:%s:%i')<='"
							+ dateStr + "'" + "AND DATE_FORMAT(W.END_DATE, '%Y-%m-%d %H:%s:%i') >= '" + dateStr
							+ "') AND O.ORDER_MONTH < (SELECT w.WORK_MONTH from TM_WORK_WEEK w WHERE     DATE_FORMAT(W.START_DATE,'%Y-%m-%d %H:%s:%i')<='"
							+ dateStr + "' " + "AND DATE_FORMAT(W.END_DATE, '%Y-%m-%d %H:%s:%i') >= '" + dateStr
							+ "') OR (O.ORDER_YEAR < (SELECT w.WORK_YEAR from TM_WORK_WEEK w WHERE     DATE_FORMAT(W.START_DATE,'%Y-%m-%d %H:%s:%i')<='"
							+ dateStr + "'" + "AND DATE_FORMAT(W.END_DATE, '%Y-%m-%d %H:%s:%i') >= '" + dateStr
							+ "'))))     \n");

		}
		System.out.println(sql.toString());
		PageInfoDto findAll = OemDAOUtil.pageQuery(sql.toString(), param);
		return findAll;
	}

	public Map PageResult(Map<String, String> queryParam) {
		LoginInfoDto loginInfo = ApplicationContextHelper.getBeanByType(LoginInfoDto.class);
		StringBuffer sql = new StringBuffer();

		String dateStr = df.format(new Date());
		List<Object> param = new ArrayList<Object>();

		sql.append("SELECT COUNT(*) AS COUNTT, SUM(BASE_PRICE) AS AMOUNT, T.GROUP_WEEK \n");
		sql.append("  FROM (SELECT (CASE \n");
		sql.append(
				"                 WHEN T.ORDER_YEAR < (SELECT w.WORK_YEAR from TM_WORK_WEEK w WHERE  DATE_FORMAT(W.START_DATE,'%Y-%m-%d %H:%s:%i')<='"
						+ dateStr + "'" + "AND DATE_FORMAT(W.END_DATE, '%Y-%m-%d %H:%s:%i') >= '" + dateStr
						+ "') THEN \n");
		sql.append("                  -1 \n");
		sql.append(
				"                 WHEN T.ORDER_YEAR = (SELECT w.WORK_YEAR from TM_WORK_WEEK w WHERE  DATE_FORMAT(W.START_DATE,'%Y-%m-%d %H:%s:%i')<='"
						+ dateStr + "'" + "AND DATE_FORMAT(W.END_DATE, '%Y-%m-%d %H:%s:%i') >= '" + dateStr
						+ "') AND \n");
		sql.append(
				"                      T.ORDER_MONTH < (SELECT w.WORK_YEAR from TM_WORK_WEEK w WHERE  DATE_FORMAT(W.START_DATE,'%Y-%m-%d %H:%s:%i')<='"
						+ dateStr + "'" + "AND DATE_FORMAT(W.END_DATE, '%Y-%m-%d %H:%s:%i') >= '" + dateStr
						+ "') THEN \n");
		sql.append("                  -1 \n");
		sql.append("                 ELSE \n");
		sql.append("                  T.ORDER_WEEK \n");
		sql.append("               END) AS GROUP_WEEK, \n");
		sql.append("               T.WHOLESALE_PRICE, \n");
		sql.append("               TMR.BASE_PRICE AS BASE_PRICE -- 订单金额 \n");
		sql.append("          FROM TT_VS_ORDER T \n");
		sql.append("          LEFT JOIN TM_MATERIAL_PRICE TMR \n");
		sql.append("            ON TMR.MATERIAL_ID = T.MATERAIL_ID \n");
		sql.append(
				"         WHERE (T.ORDER_YEAR < (SELECT w.WORK_YEAR from TM_WORK_WEEK w WHERE  DATE_FORMAT(W.START_DATE,'%Y-%m-%d %H:%s:%i')<='"
						+ dateStr + "'" + "AND DATE_FORMAT(W.END_DATE, '%Y-%m-%d %H:%s:%i') >= '" + dateStr
						+ "') OR \n");
		sql.append(
				"               T.ORDER_YEAR = (SELECT w.WORK_YEAR from TM_WORK_WEEK w WHERE  DATE_FORMAT(W.START_DATE,'%Y-%m-%d %H:%s:%i')<='"
						+ dateStr + "'" + "AND DATE_FORMAT(W.END_DATE, '%Y-%m-%d %H:%s:%i') >= '" + dateStr
						+ "') AND T.ORDER_MONTH <= (SELECT w.WORK_YEAR from TM_WORK_WEEK w WHERE  DATE_FORMAT(W.START_DATE,'%Y-%m-%d %H:%s:%i')<='"
						+ dateStr + "'" + "AND DATE_FORMAT(W.END_DATE, '%Y-%m-%d %H:%s:%i') >= '" + dateStr + "')) \n");
		sql.append("           AND T.DEALER_ID = " + loginInfo.getDealerId() + " \n");
		// 订单状态
		sql.append(" and t.ORDER_STATUS in  ").append(getOrderStatuStr());
		sql.append(" ) T \n");
		sql.append(" GROUP BY T.GROUP_WEEK \n");
		sql.append(" ORDER BY T.GROUP_WEEK \n");
		System.out.println(sql.toString());
		return OemDAOUtil.findFirst(sql.toString(), null);
	}

}
