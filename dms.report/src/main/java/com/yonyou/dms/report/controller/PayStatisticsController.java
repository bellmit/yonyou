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
import com.yonyou.dms.framework.service.excel.ExcelDataType;
import com.yonyou.dms.framework.service.excel.ExcelExportColumn;
import com.yonyou.dms.framework.service.excel.ExcelGenerator;
import com.yonyou.dms.framework.util.FrameworkUtil;
import com.yonyou.dms.function.exception.ServiceBizException;
import com.yonyou.dms.report.service.impl.PayStatisticsService;
import com.yonyou.f4.mvc.annotation.TxnConn;
import com.yonyou.f4.mvc.controller.BaseController;

/**
 * 销售统计
 * 
 * @author
 *
 */
@Controller
@TxnConn
@RequestMapping("/saleReport/pay")
public class PayStatisticsController extends BaseController {
	@Autowired
	private PayStatisticsService payStaticsService;
	@Autowired
	private ExcelGenerator excelGenerator;

	/**
	 * 收款统计查询
	 * 
	 * @author
	 * @param queryParam
	 * @return
	 * @throws SQLException
	 * @throws ServiceBizException
	 */

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping(method = RequestMethod.GET)
	@ResponseBody
	public PageInfoDto queryPayStatistics(@RequestParam Map<String, String> queryParam)
			throws ServiceBizException, SQLException {
		 PageInfoDto pageInfoDto=payStaticsService.queryPayStatisticsInfo(queryParam);
		//获取当前页所有数据
		List<Map> rows = pageInfoDto.getRows();
	        Map values = new HashMap();
	        values.put("BUSINESS_TYPE", "");
	        values.put("RECEIVE_NO", "");
	        values.put("RECEIVE_DATE", "");
	        values.put("RECORDER", "");
	        values.put("REMARK", "总计");
	        values.put("GATHERING_TYPE", "");
	        values.put("CUSTOMER_NO", "");
	        values.put("CUSTOMER_NAME", "");
	        values.put("BEST_CONTACT_TYPE", "");
	        values.put("TRANSACTOR", "");
	        values.put("PAY_TYPE_CODE", "");
	        values.put("RECEIVE_AMOUNT",0.00);
	       
	        for (Map map : rows) {
	        	System.err.println(map.get("RECEIVE_AMOUNT").toString());
	        	values.put("RECEIVE_AMOUNT", Double.parseDouble(map.get("RECEIVE_AMOUNT").toString())+ Double.parseDouble(values.get("RECEIVE_AMOUNT").toString()));
	        	System.err.println(values.get("RECEIVE_AMOUNT").toString());
			}
	        rows.add(values);
		return pageInfoDto;
	}
	
	/**
	 * 查询付款方式
	 * 
	 * @param queryParam
	 * @return
	 * @throws ServiceBizException
	 * @throws SQLException
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/payTypeName", method = RequestMethod.GET)
	@ResponseBody
	public List<Map> queryAppli(@RequestParam Map<String, String> queryParam) throws ServiceBizException, SQLException {
		return payStaticsService.querypayType(queryParam);

	}

	/**
	 * 导出收款统计
	 * 
	 * @param queryParam
	 * @param request
	 * @param response
	 * @throws ServiceBizException
	 * @throws SQLException
	 */
	@RequestMapping(value = "/export", method = RequestMethod.GET)
	public void exportStreamAnalysis(@RequestParam Map<String, String> queryParam, HttpServletRequest request,
			HttpServletResponse response) throws ServiceBizException, SQLException {
		@SuppressWarnings("rawtypes")
		List<Map> resultList = payStaticsService.exportPayStatistics(queryParam);
		  List<Map> rows = resultList;
          Map values = new HashMap();
          values.put("BUSINESS_TYPE", "");
          values.put("RECEIVE_NO", "");
          values.put("RECEIVE_DATE", "");
          values.put("RECORDER", "");
          values.put("REMARK", "总计");
          values.put("GATHERING_TYPE", "");
          values.put("CUSTOMER_NO", "");
          values.put("CUSTOMER_NAME", "");
          values.put("BEST_CONTACT_TYPE", "");
          values.put("TRANSACTOR", "");
          values.put("PAY_TYPE_CODE", "");
          values.put("RECEIVE_AMOUNT",0.00);
         
          for (Map map : rows) {
              System.err.println(map.get("RECEIVE_AMOUNT").toString());
              values.put("RECEIVE_AMOUNT", Double.parseDouble(map.get("RECEIVE_AMOUNT").toString())+ Double.parseDouble(values.get("RECEIVE_AMOUNT").toString()));
              System.err.println(values.get("RECEIVE_AMOUNT").toString());
          }
          rows.add(values);
		@SuppressWarnings("rawtypes")
		Map<String, List<Map>> excelData = new HashMap<String, List<Map>>();
		excelData.put("收款统计报表", rows);
		List<ExcelExportColumn> exportColumnList = new ArrayList<ExcelExportColumn>();
		exportColumnList.add(new ExcelExportColumn("BUSINESS_TYPE", "业务类型",ExcelDataType.Dict));
		exportColumnList.add(new ExcelExportColumn("RECEIVE_NO", "收款编号"));
		exportColumnList.add(new ExcelExportColumn("RECEIVE_DATE", "收款日期"));
		exportColumnList.add(new ExcelExportColumn("USER_NAME", "收款人"));
		exportColumnList.add(new ExcelExportColumn("REMARK", "备注"));
		exportColumnList.add(new ExcelExportColumn("RECEIVE_AMOUNT", "金额"));
		exportColumnList.add(new ExcelExportColumn("GATHERING_TYPE", "收款类型",ExcelDataType.Dict));
		exportColumnList.add(new ExcelExportColumn("CUSTOMER_NO", "客户编号"));
		exportColumnList.add(new ExcelExportColumn("CUSTOMER_NAME", "客户名称"));
		exportColumnList.add(new ExcelExportColumn("BEST_CONTACT_TYPE", "主要联系方式",ExcelDataType.Dict));
		exportColumnList.add(new ExcelExportColumn("TRANSACTOR", "交款人"));
		exportColumnList.add(new ExcelExportColumn("PAY_TYPE_NAME", "付款方式"));
		excelGenerator.generateExcelForDms(excelData, exportColumnList, 
				FrameworkUtil.getLoginInfo().getDealerShortName()+"_收款统计报表.xls", request, response);
	}
}