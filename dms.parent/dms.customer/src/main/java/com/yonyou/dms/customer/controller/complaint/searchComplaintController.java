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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.util.UriComponentsBuilder;

import com.yonyou.dms.customer.service.complaint.searchComplaintService;
import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.framework.service.excel.ExcelExportColumn;
import com.yonyou.dms.framework.service.excel.ExcelGenerator;
import com.yonyou.dms.framework.util.FrameworkUtil;
import com.yonyou.dms.function.exception.ServiceBizException;
import com.yonyou.f4.mvc.annotation.TxnConn;
import com.yonyou.f4.mvc.controller.BaseController;

@Controller
@TxnConn
@RequestMapping("/customer/queryComplaint")
public class searchComplaintController extends BaseController {

	@Autowired
	private searchComplaintService searchComplaintService;
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
		return searchComplaintService.queryCompaint(queryParam);
	}

	/**
	 * 根据投诉编号回显查询投诉明细
	 * 
	 * @author
	 * @param id
	 * @throws ServiceBizException
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping(value = "/comp/{complaintNo}", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> quercomplaintNoById(@PathVariable(value = "complaintNo") String complaintNo)
			throws ServiceBizException {
		List<Map> result = searchComplaintService.querycomplaintNoById(complaintNo);
		return result.get(0);
	}

	/**
	 * 根据查询客户投诉处理结果
	 * 
	 * @param queryParam
	 * @return
	 * @throws ServiceBizException
	 * @throws SQLException
	 */
	@RequestMapping(value = "/dispose/{complaintNo}", method = RequestMethod.GET)
	@ResponseBody
	public PageInfoDto queryDispose(@PathVariable(value = "complaintNo") String complaintNo,
			@RequestParam Map<String, String> queryParam) throws ServiceBizException, SQLException {
		return searchComplaintService.queryDispose(complaintNo, queryParam);
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
		List<Map> resultList = searchComplaintService.exportComalaint(queryParam);
		@SuppressWarnings("rawtypes")
		Map<String, List<Map>> excelData = new HashMap<String, List<Map>>();
		excelData.put("客户投诉查询", resultList);
		List<ExcelExportColumn> exportColumnList = new ArrayList<ExcelExportColumn>();
		exportColumnList.add(new ExcelExportColumn("DEALER_CODE", "经销商"));
		exportColumnList.add(new ExcelExportColumn("COMPLAINT_NO", "投诉编号"));
		exportColumnList.add(new ExcelExportColumn("COMPLAINT_TYPE", "投诉类型"));
		exportColumnList.add(new ExcelExportColumn("COMPLAINT_NAME", "投诉人姓名"));
		exportColumnList.add(new ExcelExportColumn("COMPLAINT_DATE", "接单时间"));
		exportColumnList.add(new ExcelExportColumn("ss", "客户经理"));
		exportColumnList.add(new ExcelExportColumn("user_name", "处理人"));
		exportColumnList.add(new ExcelExportColumn("TECHNICIAN", "责任技师"));
		exportColumnList.add(new ExcelExportColumn("TECHNICIAN_NEW", "维修技师"));
		exportColumnList.add(new ExcelExportColumn("DEAL_STATUS", "处理状态"));
		exportColumnList.add(new ExcelExportColumn("COMPLAINT_END_DATE", "处理完成日期"));
		exportColumnList.add(new ExcelExportColumn("dept", "发生部门"));
		exportColumnList.add(new ExcelExportColumn("sse", "投诉员工"));
		exportColumnList.add(new ExcelExportColumn("CLOSE_DATE", "结案日期"));
		exportColumnList.add(new ExcelExportColumn("COMPLAINT_RESULT", "回访结果"));
		exportColumnList.add(new ExcelExportColumn("COMPLAINT_ORIGIN", "投诉来源"));
		exportColumnList.add(new ExcelExportColumn("IS_INTIME_DEAL", "及时处理"));
		exportColumnList.add(new ExcelExportColumn("COMPLAINT_SUMMARY", "投诉摘要"));
		exportColumnList.add(new ExcelExportColumn("COMPLAINT_REASON", "投诉原因"));
		exportColumnList.add(new ExcelExportColumn("RESOLVENT", "解决方案"));
		exportColumnList.add(new ExcelExportColumn("OWNER_NAME", "车主姓名"));
		exportColumnList.add(new ExcelExportColumn("LICENSE", "车牌号"));

		excelGenerator.generateExcelForDms(excelData, exportColumnList,
				FrameworkUtil.getLoginInfo().getDealerShortName() + "_客户投诉.xls", request, response);
	}

	/**
	 * 根据ID 客户投诉
	 * 
	 * @param id
	 * @param uriCB
	 * @throws ServiceBizException
	 */

	@RequestMapping(value = "/{complaintNo}", method = RequestMethod.DELETE)
	@ResponseBody
	public void deletePlan(@PathVariable(value = "complaintNo") String complaintNo, UriComponentsBuilder uriCB) throws ServiceBizException {
		searchComplaintService.deletePlanById(complaintNo);
	}
}
