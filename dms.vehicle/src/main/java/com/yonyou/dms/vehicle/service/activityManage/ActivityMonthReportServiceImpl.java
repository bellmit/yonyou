package com.yonyou.dms.vehicle.service.activityManage;

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
import com.yonyou.dms.vehicle.dao.activityManage.ActivityMonthReportDao;

/**
* @author liujiming
* @date 2017年4月7日
*/
@Service
public class ActivityMonthReportServiceImpl implements ActivityMonthReportService{
	
	@Autowired
	private ExcelGenerator excelService;
	
	@Autowired
	private ActivityMonthReportDao  amrDao;
	
	
	
	/**
	 * 查询
	 */
	@Override
	public PageInfoDto getMonthReportQuery(Map<String, String> queryParam) throws ServiceBizException {
		PageInfoDto pageInfoDto = amrDao.getActivityReportQuery(queryParam);
		return pageInfoDto;
	}
	/**
	 * 下载
	 */
	@Override
	public void getMonthReportDownload(Map<String, String> queryParam, HttpServletRequest request,
			HttpServletResponse response) throws ServiceBizException {
		List<Map> orderList = amrDao.getActivityReportDownload(queryParam);
    	
		Map<String, List<Map>> excelData = new HashMap<>();
		excelData.put("服务月活动报表下载", orderList);
		List<ExcelExportColumn> exportColumnList = new ArrayList<>();
		exportColumnList.add(new ExcelExportColumn("ACTIVITY_CODE","活动系统代码"));
		exportColumnList.add(new ExcelExportColumn("VIN","参加活动车架号"));
		exportColumnList.add(new ExcelExportColumn("DEALER_CODE","经销商代码"));
		exportColumnList.add(new ExcelExportColumn("DEALER_NAME","经销商名称"));
		exportColumnList.add(new ExcelExportColumn("MODEL_NAME","车型" ));
		exportColumnList.add(new ExcelExportColumn("CTM_NAME","客户姓名"));
		exportColumnList.add(new ExcelExportColumn("EMAIL","E-mail"));
		exportColumnList.add(new ExcelExportColumn("AMOUNT", "结算金额"));
		exportColumnList.add(new ExcelExportColumn("EXECUTE_DATE","参加活动日期"));	
		excelService.generateExcel(excelData, exportColumnList, "服务月活动报表下载.xls", request, response);
    	
	}

}
