package com.yonyou.dms.customer.controller.suggestRepair;

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

import com.yonyou.dms.customer.service.suggestQuery.suggestRepairService;
import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.framework.service.excel.ExcelExportColumn;
import com.yonyou.dms.framework.service.excel.ExcelGenerator;
import com.yonyou.dms.framework.util.FrameworkUtil;
import com.yonyou.dms.function.exception.ServiceBizException;
import com.yonyou.f4.mvc.annotation.TxnConn;

@Controller
@TxnConn
@RequestMapping("/customer/suggestRepair")
public class repairQueryController {
	@Autowired
	private suggestRepairService suggestRepairService;
	@Autowired
	private ExcelGenerator excelGenerator;

	/**
	 * 查询
	 * @param queryParam
	 * @return
	 * @throws SQLException
	 * @throws ServiceBizException
	 */

	@RequestMapping(method = RequestMethod.GET)
	@ResponseBody
	public PageInfoDto queryStreamAnalysis(@RequestParam Map<String, String> queryParam)
			throws ServiceBizException, SQLException {
		return suggestRepairService.querySuggestRepair(queryParam);
	}
	
	/**
	 * 维修建议查询导出
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
		List<Map> resultList = suggestRepairService.exportSuggestRepair(queryParam);
		@SuppressWarnings("rawtypes")
		Map<String, List<Map>> excelData = new HashMap<String, List<Map>>();
		excelData.put("客户经理(项目)", resultList);
		List<ExcelExportColumn> exportColumnList = new ArrayList<ExcelExportColumn>();
		exportColumnList.add(new ExcelExportColumn("DEALER_CODE", "经销商"));
		exportColumnList.add(new ExcelExportColumn("PART_NO", "配件代码"));
		exportColumnList.add(new ExcelExportColumn("PART_NAME", "配件名称"));
		exportColumnList.add(new ExcelExportColumn("QUANTITY", "数量"));
		exportColumnList.add(new ExcelExportColumn("SALES_PRICE", "单价"));
		exportColumnList.add(new ExcelExportColumn("REASON", "不修原因"));
		exportColumnList.add(new ExcelExportColumn("SUGGEST_DATE", "建议日期"));
		exportColumnList.add(new ExcelExportColumn("VIN", "VIN"));
		exportColumnList.add(new ExcelExportColumn("LICENSE", "车牌号"));
		exportColumnList.add(new ExcelExportColumn("ENGINE_NO", "发动机号"));
		exportColumnList.add(new ExcelExportColumn("SERVICE_ADVISOR", "客户经理"));
		exportColumnList.add(new ExcelExportColumn("ADDRESS", "车主地址"));
		excelGenerator.generateExcelForDms(excelData, exportColumnList,
				FrameworkUtil.getLoginInfo().getDealerShortName() + "_客户经理.xls", request, response);
	}
}
