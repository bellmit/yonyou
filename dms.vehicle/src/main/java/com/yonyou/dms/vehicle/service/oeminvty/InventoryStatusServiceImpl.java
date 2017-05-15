package com.yonyou.dms.vehicle.service.oeminvty;

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
import com.yonyou.dms.vehicle.dao.oeminvty.InventoryStatusDao;

@Service
public class InventoryStatusServiceImpl implements InventoryStatusService {
	@Autowired
	private InventoryStatusDao dao;
	@Autowired
	private ExcelGenerator excelService;

	@Override
	public PageInfoDto inventoryStatusQueryCollect(Map<String, String> queryParam) {

		return dao.inventoryStatusQueryCollect(queryParam);
	}

	@Override
	public PageInfoDto inventoryStatusQueryDetails(Map<String, String> queryParam) {
		return dao.inventoryStatusQueryDetails(queryParam);
	}

	@Override
	public void downloadDetailsl(Map<String, String> queryParam, HttpServletResponse response,
			HttpServletRequest request) {
		Map<String, List<Map>> excelData = new HashMap<>();
		List<Map> list = dao.downloadDetailsl(queryParam);

		excelData.put("车厂库存明细下载", list);

		List<ExcelExportColumn> exportColumnList = new ArrayList<>();
		exportColumnList.add(new ExcelExportColumn("BRAND_CODE", "品牌"));
		exportColumnList.add(new ExcelExportColumn("SERIES_CODE", "车系"));
		exportColumnList.add(new ExcelExportColumn("MODEL_CODE", "车型"));
		exportColumnList.add(new ExcelExportColumn("TRIM_NAME", "内饰"));
		exportColumnList.add(new ExcelExportColumn("COLOR_NAME", "颜色"));
		exportColumnList.add(new ExcelExportColumn("VIN", "VIN"));
		// exportColumnList.add(new ExcelExportColumn("VPC_PORT", "VPC港口",
		// ExcelDataType.Oem_Dict));
		exportColumnList.add(new ExcelExportColumn("ORG_STORAGE_DATE", "PDI完成日期"));
		exportColumnList.add(new ExcelExportColumn("ZBIL_DATE", "开票日期"));
		exportColumnList.add(new ExcelExportColumn("D", "库龄（天）"));

		excelService.generateExcel(excelData, exportColumnList, "车厂库存明细下载.xls", request, response);

	}

	@Override
	public List<Map> findnodeStatus(Long id) {

		return dao.findnodeStatus(id);
	}

}
