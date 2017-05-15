package com.yonyou.dms.report.controller;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.framework.service.excel.ExcelExportColumn;
import com.yonyou.dms.framework.service.excel.ExcelGenerator;
import com.yonyou.dms.framework.util.FrameworkUtil;
import com.yonyou.dms.function.exception.ServiceBizException;
import com.yonyou.dms.report.service.impl.PotentialCustomersReportService;
import com.yonyou.f4.mvc.annotation.TxnConn;
import com.yonyou.f4.mvc.controller.BaseController;

/**
 * 展厅来源分析
 * 
 * @author
 */
@Controller
@TxnConn
@RequestMapping("/potentialCustomers/streamAnalysis")
public class PotentialCustomersController extends BaseController {
	@Autowired
	private PotentialCustomersReportService potentialCustomersReportService;
	@Autowired
	private ExcelGenerator excelGenerator;

	/**
	 * 查询展厅客户来源
	 * 
	 * @param queryParam
	 * @return
	 */
	@RequestMapping(method = RequestMethod.GET)
	@ResponseBody
	public PageInfoDto queryVisitingRecordInfo(@RequestParam Map<String, String> queryParam) {
		if (queryParam.get("startdate") == null) {
			// throw new ServiceBizException("日期不能为空");
			return null;
		} else {
			return potentialCustomersReportService.queryVisitingRecordInfo(queryParam);

		}

	}

	/**
	 * 展厅来源分析报表导出
	 * 
	 * @author
	 * @param queryParam
	 * @param request
	 * @param response
	 * @throws Exception
	 */

	@RequestMapping(value = "/export", method = RequestMethod.GET)
	public void exportStreamAnalysis(@RequestParam Map<String, String> queryParam, HttpServletRequest request,
			HttpServletResponse response) throws ServiceBizException, SQLException {
		@SuppressWarnings("rawtypes")
		List<Map> resultList = potentialCustomersReportService.exportPotentialCustomersStreamAnalysis(queryParam);
		@SuppressWarnings("rawtypes")
		Map<String, List<Map>> excelData = new HashMap<String, List<Map>>();
		excelData.put("展厅来源分析表", resultList);
		List<ExcelExportColumn> exportColumnList = new ArrayList<ExcelExportColumn>();
		exportColumnList.add(new ExcelExportColumn("CUS_SOURCE", "客户来源"));
		exportColumnList.add(new ExcelExportColumn("COUNT_WEEK", "本周小计"));
		exportColumnList.add(new ExcelExportColumn("COUNT_MONTH", "本月累计"));
		exportColumnList.add(new ExcelExportColumn("COUNT_YEAR", "年度累计"));
		excelGenerator.generateExcelForDms(excelData, exportColumnList,
				FrameworkUtil.getLoginInfo().getDealerShortName()+"_展厅来源分析表.xls", request, response);
	}
}
