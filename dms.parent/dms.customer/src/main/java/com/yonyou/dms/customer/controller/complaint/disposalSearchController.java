package com.yonyou.dms.customer.controller.complaint;

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

import com.yonyou.dms.customer.service.complaint.displsalSearchService;
import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.framework.service.excel.ExcelExportColumn;
import com.yonyou.dms.framework.service.excel.ExcelGenerator;
import com.yonyou.dms.framework.util.FrameworkUtil;
import com.yonyou.dms.function.exception.ServiceBizException;
import com.yonyou.f4.mvc.annotation.TxnConn;
import com.yonyou.f4.mvc.controller.BaseController;

@Controller
@TxnConn
@RequestMapping("/customer/disposal")
public class disposalSearchController extends BaseController{

	@Autowired
	private displsalSearchService displsalSearchService;
	@Autowired
	private ExcelGenerator excelGenerator;
	
	/**
	 * 分页查询客户投诉查询
	 * 
	 * @param queryParam
	 * @return
	 * @throws SQLException
	 * @throws ServiceBizException
	 */

	@RequestMapping(method = RequestMethod.GET)
	@ResponseBody
	public PageInfoDto queryStreamAnalysis(@RequestParam Map<String, String> queryParam)
			throws ServiceBizException, SQLException {
		return displsalSearchService.queryCompaint(queryParam);
	}
	
	/**
	 * 客户投诉导出
	 * 
	 * @author
	 * @param queryParam
	 * @param request
	 * @param response
	 * @throws Exception
	 */

	@RequestMapping(value = "/export", method = RequestMethod.GET)
	public void exportComalaint(@RequestParam Map<String, String> queryParam, HttpServletRequest request,
			HttpServletResponse response) throws ServiceBizException, SQLException {
		@SuppressWarnings("rawtypes")
		List<Map> resultList = displsalSearchService.exportComalaint(queryParam);
		@SuppressWarnings("rawtypes")
		Map<String, List<Map>> excelData = new HashMap<String, List<Map>>();
		excelData.put("客户投诉处理情况查询", resultList);
		List<ExcelExportColumn> exportColumnList = new ArrayList<ExcelExportColumn>();
		exportColumnList.add(new ExcelExportColumn("dealer_shortname", "经销商"));
		exportColumnList.add(new ExcelExportColumn("PRINCIPAL", "处理人"));
		exportColumnList.add(new ExcelExportColumn("COMPLAINT_COUNT", "投诉个数"));
		exportColumnList.add(new ExcelExportColumn("ON_TIME_COUNT", "及时处理"));
		exportColumnList.add(new ExcelExportColumn("ON_TIME_RATE", "及时处理率"));
		exportColumnList.add(new ExcelExportColumn("CONTENTMENT_COUNT", "满意数"));
		exportColumnList.add(new ExcelExportColumn("CONTENTMENT_RATE", "处理结果满意率"));
		exportColumnList.add(new ExcelExportColumn("REPEAT_COUNT", "重复处理数"));
		exportColumnList.add(new ExcelExportColumn("REPEAT_RATE", "重复处理率"));
		excelGenerator.generateExcelForDms(excelData, exportColumnList,
				FrameworkUtil.getLoginInfo().getDealerShortName() + "_客户投诉处理情况.xls", request, response);
	}
}
