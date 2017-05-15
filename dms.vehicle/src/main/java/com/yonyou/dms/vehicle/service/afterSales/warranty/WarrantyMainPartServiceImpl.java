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
import com.yonyou.dms.vehicle.dao.afterSales.warranty.WarrantyMainPartDao;

/**
 * 主因件维护
 * 
 * @author Administrator
 *
 */
@Service
public class WarrantyMainPartServiceImpl implements WarrantyMainPartService {
	@Autowired
	private ExcelGenerator excelService;
	
	@Autowired
	WarrantyMainPartDao mainPartDAO;

	/**
	 * 主因件维护查询
	 */
	@Override
	public PageInfoDto mainPartQuery(Map<String, String> queryParam) {
		// TODO Auto-generated method stub
		return mainPartDAO.getMainPartQueryList(queryParam);
	}
	/**
	 * 主因件维护下载
	 */
	@Override
	public void mainPartDownload(Map<String, String> queryParam, HttpServletRequest request,
			HttpServletResponse response) throws ServiceBizException {
		List<Map> orderList = mainPartDAO.getMainPartDownloadList(queryParam);
		Map<String, List<Map>> excelData = new HashMap<>();
		excelData.put("主因件维护", orderList);
		List<ExcelExportColumn> exportColumnList = new ArrayList<>();
		exportColumnList.add(new ExcelExportColumn("OLDPT_CODE", "主因件代码"));
		exportColumnList.add(new ExcelExportColumn("OLDPT_NAME", "主因件描述"));
		exportColumnList.add(new ExcelExportColumn("STATUS", "状态" ));
		exportColumnList.add(new ExcelExportColumn("CREATE_BY", "创建人"));
		exportColumnList.add(new ExcelExportColumn("CREATE_DATE", "创建时间"));
		exportColumnList.add(new ExcelExportColumn("UPDATE_BY", "更新人"));
		exportColumnList.add(new ExcelExportColumn("UPDATE_DATE", "更新时间"));

		excelService.generateExcel(excelData, exportColumnList, "主因件维护.xls", request, response);

	}
}
