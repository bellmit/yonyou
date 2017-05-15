package com.yonyou.dms.repair.service.basicPricing;

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
import com.yonyou.dms.repair.dao.basicPricing.BasicPricingDao;

@Service
public class BasicPricingServiceImpl implements BasicPricingService {
	@Autowired
	private BasicPricingDao dao;
	@Autowired
	private ExcelGenerator excelService;

	@Override
	public PageInfoDto basicPricingQuery(Map<String, String> queryParam) {

		return dao.basicPricingQuery(queryParam);
	}

	@Override
	public void doDownload(Map<String, String> queryParam, HttpServletResponse response, HttpServletRequest request) {
		Map<String, List<Map>> excelData = new HashMap<>();
		List<Map> list = dao.doDownload(queryParam);

		excelData.put("基础定价查询下载", list);

		List<ExcelExportColumn> exportColumnList = new ArrayList<>();
		exportColumnList.add(new ExcelExportColumn("BRAND_NAME", "品牌"));
		exportColumnList.add(new ExcelExportColumn("SERIES_CODE", "车系代码"));
		exportColumnList.add(new ExcelExportColumn("SERIES_NAME", "车系"));
		exportColumnList.add(new ExcelExportColumn("MODEL_CODE", "CPOS"));
		exportColumnList.add(new ExcelExportColumn("MODEL_NAME", "CPOS描述"));
		exportColumnList.add(new ExcelExportColumn("GROUP_CODE", "车款代码"));
		exportColumnList.add(new ExcelExportColumn("GROUP_NAME", "车款"));
		exportColumnList.add(new ExcelExportColumn("MODEL_YEAR", "年款"));
		exportColumnList.add(new ExcelExportColumn("BASE_PRICE", "批售价格"));
		exportColumnList.add(new ExcelExportColumn("MSRP", "MSRP"));
		exportColumnList.add(new ExcelExportColumn("ENABLE_DATE", "生效开始时间"));
		exportColumnList.add(new ExcelExportColumn("DISABLE_DATE", "生效结束时间"));
		exportColumnList.add(new ExcelExportColumn("VALID_STATE", "有效期状态"));

		excelService.generateExcel(excelData, exportColumnList, "基础定价查询下载.xls", request, response);
	}

}
