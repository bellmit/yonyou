package com.yonyou.dms.vehicle.service.afterSales.warranty;

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
import com.yonyou.dms.function.exception.ServiceBizException;
import com.yonyou.dms.vehicle.dao.afterSales.warranty.WarrantyMainPartAndLocDao;

/**
 * 主因件与位置码维护
 * @author zhanghongyi
 *
 */
@Service
public class WarrantyMainPartAndLocServiceImpl implements WarrantyMainPartAndLocService {
	@Autowired
	private ExcelGenerator excelService;
	
	@Autowired
	WarrantyMainPartAndLocDao dao;

	/**
	 * 查询
	 */
	@Override
	public PageInfoDto search(Map<String, String> queryParam) {
		// TODO Auto-generated method stub
		return dao.getQueryList(queryParam);
	}
	/**
	 * 下载
	 */
	@Override
	public void download(Map<String, String> queryParam, HttpServletRequest request,
			HttpServletResponse response) throws ServiceBizException {
		List<Map> orderList = dao.getDownloadList(queryParam);
		Map<String, List<Map>> excelData = new HashMap<>();
		excelData.put("主因件与位置代码维护", orderList);
		List<ExcelExportColumn> exportColumnList = new ArrayList<>();
		exportColumnList.add(new ExcelExportColumn("OLDPT_CODE", "主因件"));
		exportColumnList.add(new ExcelExportColumn("LOC_CODE", "位置代码"));
		exportColumnList.add(new ExcelExportColumn("STATUS", "状态" ));
		excelService.generateExcel(excelData, exportColumnList, "主因件与位置代码维护.xls", request, response);
	}
}
