package com.yonyou.dms.repair.service.k4RebateManage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.framework.service.excel.ExcelExportColumn;
import com.yonyou.dms.framework.service.excel.ExcelGenerator;
import com.yonyou.dms.repair.dao.k4RebateManage.AccountDao;

@Service
public class RebateBalanceServiceImpl implements RebateBalanceService {
	@Autowired
	private AccountDao dao;
	@Autowired
	private ExcelGenerator excelService;

	@Override
	public PageInfoDto rebateBalanceQuery(Map<String, String> queryParam) {

		return dao.rebateBalanceQuery(queryParam);
	}

	// 下载
	@Override
	public void doDownload(Map<String, String> queryParam, HttpServletResponse response, HttpServletRequest request) {
		Map<String, List<Map>> excelData = new HashMap<>();
		List<Map> list = dao.doDownload(queryParam);

		excelData.put("返利余额查询下载", list);

		List<ExcelExportColumn> exportColumnList = new ArrayList<>();
		exportColumnList.add(new ExcelExportColumn("DEALER_CODE", "经销商代码"));
		exportColumnList.add(new ExcelExportColumn("DEALER_SHORTNAME", "经销商名称"));
		exportColumnList.add(new ExcelExportColumn("CANUSE", "返利金额"));
		exportColumnList.add(new ExcelExportColumn("USED", "使用金额"));
		exportColumnList.add(new ExcelExportColumn("ACCOUNT", "可用金额"));

		excelService.generateExcel(excelData, exportColumnList, "返利余额查询下载.xls", request, response);
	}

	@Override
	public List<Map> rebateBalanceDealerQuery(Map<String, String> queryParam) {
		List<Map> m = dao.rebateBalanceDealerQuery(queryParam);

		return m;
	}

	@Override
	public PageInfoDto rebateUseDetailQuery(Map<String, String> queryParam) {

		return dao.rebateUseDetailQuery(queryParam);
	}

	@Override
	public void rebateUseDetailDownLoad(Map<String, String> queryParam, HttpServletResponse response,
			HttpServletRequest request) {
		Map<String, List<Map>> excelData = new HashMap<>();
		List<Map> list = dao.rebateUseDetailDownLoad(queryParam);

		excelData.put("返利使用明细下载", list);

		List<ExcelExportColumn> exportColumnList = new ArrayList<>();
		exportColumnList.add(new ExcelExportColumn("DEALER_CODE", "经销商代码"));
		exportColumnList.add(new ExcelExportColumn("DEALER_SHORTNAME", "经销商名称"));
		exportColumnList.add(new ExcelExportColumn("REBATE_TYPE_DESC", "返利使用类型"));
		exportColumnList.add(new ExcelExportColumn("ORDER_NO", "订单号"));
		exportColumnList.add(new ExcelExportColumn("SERIES_NAME", "车系"));
		exportColumnList.add(new ExcelExportColumn("VIN", "VIN"));
		exportColumnList.add(new ExcelExportColumn("AMOUNT", "返利使用金额"));
		exportColumnList.add(new ExcelExportColumn("OPERA_DATE", "返利使用日期"));
		exportColumnList.add(new ExcelExportColumn("REMARK", "备注"));

		excelService.generateExcel(excelData, exportColumnList, "返利使用明细下载.xls", request, response);

	}

}
