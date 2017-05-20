package com.yonyou.dms.repair.service.accountBalance;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yonyou.dms.framework.DAO.OemDAOUtil;
import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.framework.domain.LoginInfoDto;
import com.yonyou.dms.framework.service.excel.ExcelExportColumn;
import com.yonyou.dms.framework.service.excel.ExcelGenerator;
import com.yonyou.dms.framework.util.bean.ApplicationContextHelper;
import com.yonyou.dms.function.common.OemDictCodeConstants;
import com.yonyou.dms.repair.dao.accountBalanceDao.AccountBalanceDao;

@SuppressWarnings("all")
@Service
public class AccountBalanceServiceImpl implements AccountBalanceService {
	@Autowired
	private AccountBalanceDao dao;
	@Autowired
	private ExcelGenerator excelService;

	@Override
	public PageInfoDto accountBalanceQuery(Map<String, String> queryParam) {
		return dao.accountBalanceQuery(queryParam);
	}

	@Override
	public void doDownload(Map<String, String> queryParam, HttpServletResponse response, HttpServletRequest request) {

		Map<String, List<Map>> excelData = new HashMap<>();
		List<Map> list = dao.download(queryParam);

		excelData.put("账户余额查询下载", list);

		List<ExcelExportColumn> exportColumnList = new ArrayList<>();
		exportColumnList.add(new ExcelExportColumn("DEALER_CODE", "经销商代码"));
		exportColumnList.add(new ExcelExportColumn("DEALER_SHORTNAME", "经销商名称"));
		exportColumnList.add(new ExcelExportColumn("COUNT1", "现金"));
		exportColumnList.add(new ExcelExportColumn("COUNT2", "广汽汇理"));
		exportColumnList.add(new ExcelExportColumn("COUNT3", "菲克金融"));
		exportColumnList.add(new ExcelExportColumn("COUNT5", "交通银行广州荔湾支行"));
		exportColumnList.add(new ExcelExportColumn("COUNT6", "中国银行上海静安支行"));
		exportColumnList.add(new ExcelExportColumn("COUNT7", "建设银行上海卢湾支行"));
		exportColumnList.add(new ExcelExportColumn("AMOUNT", "合计"));

		excelService.generateExcel(excelData, exportColumnList, "账户余额查询下载.xls", request, response);
	}

	@Override
	public PageInfoDto accountChangeDetailQuery(Map<String, String> queryParam) {
		return dao.accountChangeDetailQuery(queryParam);
	}

	@Override
	public void QueryDownload(Map<String, String> queryParam, HttpServletResponse response,
			HttpServletRequest request) {
		Map<String, List<Map>> excelData = new HashMap<>();
		List<Map> list = dao.querydownload(queryParam);

		excelData.put("账户异动明细查询下载", list);

		List<ExcelExportColumn> exportColumnList = new ArrayList<>();
		exportColumnList.add(new ExcelExportColumn("OPERA_DATE", "日期"));
		exportColumnList.add(new ExcelExportColumn("ACC_TYPE", "账户类型"));
		exportColumnList.add(new ExcelExportColumn("AMOUNT_A", "打款金额"));
		exportColumnList.add(new ExcelExportColumn("AMOUNT_B", "扣款金额(不含税)"));
		exportColumnList.add(new ExcelExportColumn("AMOUNT_C", "扣款金额(含税)"));
		exportColumnList.add(new ExcelExportColumn("EVIDENCE_NO", "凭证号"));
		exportColumnList.add(new ExcelExportColumn("VIN", "VIN"));
		exportColumnList.add(new ExcelExportColumn("INVOICE_NO", "SAP发票号"));
		exportColumnList.add(new ExcelExportColumn("ORDER_NO", "销售订单号"));
		exportColumnList.add(new ExcelExportColumn("REMARK", "描述"));

		excelService.generateExcel(excelData, exportColumnList, "账户异动明细查询下载.xls", request, response);

	}

	// 查询
	@Override
	public PageInfoDto rebateIssueQuery(Map<String, String> queryParam) {
		return dao.rebateIssueQuery(queryParam);
	}

	// 下载
	@Override
	public void rebateIssueDownLoad(Map<String, String> queryParam, HttpServletResponse response,
			HttpServletRequest request) {
		Map<String, List<Map>> excelData = new HashMap<>();
		List<Map> list = dao.rebateIssueDownLoad(queryParam);

		excelData.put("返利发放下载", list);

		List<ExcelExportColumn> exportColumnList = new ArrayList<>();
		exportColumnList.add(new ExcelExportColumn("DEALER_CODE", "经销商代码"));
		exportColumnList.add(new ExcelExportColumn("DEALER_SHORTNAME", "经销商名称"));
		exportColumnList.add(new ExcelExportColumn("REBATE_TYPE_DESC", "返利类型"));
		exportColumnList.add(new ExcelExportColumn("OPERA_DATE", "返利发放日期"));
		exportColumnList.add(new ExcelExportColumn("AMOUNT", "返利金额"));
		exportColumnList.add(new ExcelExportColumn("REBATE_MARK", "返利说明"));
		excelService.generateExcel(excelData, exportColumnList, "返利发放下载.xls", request, response);

	}

	// 返利类型下拉框回显
	@Override
	public List<Map> rebateTypeCode(Map<String, String> queryParam) {
		List<Map> list = dao.rebateTypeCode(queryParam);
		return list;
	}

	private double getDoubleValue(Object obj) {
		if (obj == null) {
			return new Double(0);
		}
		try {
			Double big = new Double(obj.toString());
			return big;
		} catch (Exception e) {
			return new Double(0);
		}
	}

	@Override
	public List<Map> dealerPayquery1(Map<String, String> queryParam) {
		List<Map> list = new ArrayList<>();
		List<Map> map1 = dao.dealerPayQuery(queryParam);
		// 应付资金汇总查询
		Map map2 = dao.PageResult(queryParam);
		for (Map map : map1) {
			map.put("COUNTT", map2.get("COUNTT"));
			map.put("AMOUNT", map2.get("AMOUNT"));
			map.put("GROUP_WEEK", map2.get("GROUP_WEEK"));
			if (getDoubleValue(map.get("NOW_WEEK")) != 0d) {
				map.put("RATE1", getDoubleValue(map.get("ACCOUNT")) / getDoubleValue(map.get("NOW_WEEK")));
			} else {
				map.put("RATE1", 0d);
			}

			if ((getDoubleValue(map.get("NOW_WEEK")) + getDoubleValue(map.get("NEXT_WEEK"))) == 0d) {
				map.put("RATE2", 0d);
			} else {
				map.put("RATE2", getDoubleValue(map.get("ACCOUNT"))
						/ (getDoubleValue(map.get("NOW_WEEK")) + getDoubleValue(map.get("NEXT_WEEK"))));
			}
			list.add(map);
		}
		return list;
	}

	@Override
	public PageInfoDto dealerPayQuery(Map<String, String> queryParam) {

		return dao.dealerpayquery(queryParam);
	}

	@Override
	public PageInfoDto accountBalanceDealerQuery(Map<String, String> queryParam) {
		LoginInfoDto loginInfo = ApplicationContextHelper.getBeanByType(LoginInfoDto.class);
		StringBuffer sql = new StringBuffer();
		sql.append(" \n");
		sql.append("SELECT D.DEALER_CODE, -- 经销商代码 \n");
		sql.append("       D.DEALER_SHORTNAME, -- 经销商名称 \n");

		sql.append("       SUM((CASE when T.ACC_TYPE='" + OemDictCodeConstants.K4_PAYMENT_01
				+ "' THEN  T.AMOUNT ELSE '0.00' END)) AS COUNT1, -- 现金 \n");

		sql.append("       SUM(CASE WHEN T.FINANCING_STATUS = 'A' OR T.FINANCING_STATUS = 'W' \n");
		sql.append("                THEN (CASE when T.ACC_TYPE='" + OemDictCodeConstants.K4_PAYMENT_02
				+ "' THEN  T.USABLE_AMOUNT ELSE '0.00' END) \n");
		sql.append("                ELSE '0.00' END) AS COUNT2, -- 广汽汇理汽车金融有限公司 \n");

		sql.append("       SUM(CASE WHEN T.FINANCING_STATUS = 'A' OR T.FINANCING_STATUS = 'W' \n");
		sql.append("                THEN (CASE when T.ACC_TYPE='" + OemDictCodeConstants.K4_PAYMENT_03
				+ "' THEN  T.USABLE_AMOUNT ELSE '0.00' END) \n");
		sql.append("                ELSE '0.00' END) AS COUNT3, -- 菲亚特汽车金融有限责任公司 \n");

		sql.append("       SUM(CASE WHEN T.FINANCING_STATUS = 'A' OR T.FINANCING_STATUS = 'W' \n");
		sql.append("                THEN (CASE when T.ACC_TYPE='" + OemDictCodeConstants.K4_PAYMENT_04
				+ "' THEN  T.USABLE_AMOUNT ELSE '0.00' END) \n");
		sql.append("                ELSE '0.00' END) AS COUNT4, -- 兴业银行 \n");

		sql.append("       SUM(CASE WHEN T.FINANCING_STATUS = 'A' OR T.FINANCING_STATUS = 'W' \n");
		sql.append("                THEN (CASE when T.ACC_TYPE='" + OemDictCodeConstants.K4_PAYMENT_05
				+ "' THEN  T.USABLE_AMOUNT ELSE '0.00' END) \n");
		sql.append("                ELSE '0.00' END) AS COUNT5, -- 交通银行 \n");
		sql.append("       SUM((CASE when T.ACC_TYPE='" + OemDictCodeConstants.K4_PAYMENT_06
				+ "' THEN  T.AMOUNT ELSE '0.00' END)) AS COUNT6, -- 中行的三方承兑 \n");

		sql.append("       SUM(CASE WHEN T.FINANCING_STATUS = 'A' OR T.FINANCING_STATUS = 'W' \n");
		sql.append("                THEN (CASE when T.ACC_TYPE='" + OemDictCodeConstants.K4_PAYMENT_07
				+ "' THEN  T.USABLE_AMOUNT ELSE '0.00' END) \n");
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
		sql.append(" WHERE D.DEALER_ID = '" + loginInfo.getDealerId() + "' \n");
		sql.append(" GROUP BY T.DEALER_ID, D.DEALER_CODE, D.DEALER_SHORTNAME \n");
		sql.append(" ORDER BY D.DEALER_CODE, D.DEALER_SHORTNAME \n");
		return OemDAOUtil.pageQuery(sql.toString(), null);
	}
}
