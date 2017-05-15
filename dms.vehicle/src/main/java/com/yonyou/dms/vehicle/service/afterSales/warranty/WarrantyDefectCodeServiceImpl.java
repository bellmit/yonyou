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
import com.yonyou.dms.vehicle.dao.afterSales.warranty.WarrantyDefectCodeDao;

/**
 * 缺陷代码维护
 * 
 * @author Administrator
 *
 */
@Service
public class WarrantyDefectCodeServiceImpl implements WarrantyDefectCodeService {
	@Autowired
	private ExcelGenerator excelService;
	
	@Autowired
	WarrantyDefectCodeDao defectCodeDAO;

	/**
	 * 缺陷代码查询
	 */
	@Override
	public PageInfoDto defectCodeQuery(Map<String, String> queryParam) {
		// TODO Auto-generated method stub
		return defectCodeDAO.getDefectCodeQueryList(queryParam);
	}
	/**
	 * 缺陷代码下载
	 */
	@Override
	public void defectCodeDownload(Map<String, String> queryParam, HttpServletRequest request,
			HttpServletResponse response) throws ServiceBizException {
		List<Map> orderList = defectCodeDAO.getDefectCodeDownloadList(queryParam);
		Map<String, List<Map>> excelData = new HashMap<>();
		excelData.put("缺陷代码维护", orderList);
		List<ExcelExportColumn> exportColumnList = new ArrayList<>();
		exportColumnList.add(new ExcelExportColumn("DEF_CODE", "缺陷代码代码"));
		exportColumnList.add(new ExcelExportColumn("DEF_NAME", "缺陷代码描述"));
		exportColumnList.add(new ExcelExportColumn("STATUS", "状态" ));
		exportColumnList.add(new ExcelExportColumn("CREATE_BY", "创建人"));
		exportColumnList.add(new ExcelExportColumn("CREATE_DATE", "创建时间"));
		exportColumnList.add(new ExcelExportColumn("UPDATE_BY", "更新人"));
		exportColumnList.add(new ExcelExportColumn("UPDATE_DATE", "更新时间"));

		excelService.generateExcel(excelData, exportColumnList, "缺陷代码维护.xls", request, response);

	}
}
