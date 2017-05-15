package com.yonyou.dms.vehicle.controller.realitySales.scanInvoiceQuery;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yonyou.dms.framework.DAO.DAOUtil;
import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.framework.domain.LoginInfoDto;
import com.yonyou.dms.framework.service.excel.ExcelDataType;
import com.yonyou.dms.framework.service.excel.ExcelExportColumn;
import com.yonyou.dms.framework.service.excel.ExcelGenerator;
import com.yonyou.dms.framework.util.bean.ApplicationContextHelper;
import com.yonyou.dms.vehicle.service.realitySales.scanInvoiceQuery.ScanInvoiceQueryService;
import com.yonyou.f4.mvc.annotation.TxnConn;

/**
 * 扫描发票查询Controller
 * @author DC
 *
 */
@Controller()
@TxnConn
@RequestMapping("/scanInvoiceQuery")
public class ScanInvoiceQuerController {
	
	private static final Logger logger = LoggerFactory.getLogger(DAOUtil.class);
	
	@Autowired
	private ScanInvoiceQueryService service;
	
	@Autowired
	private ExcelGenerator  excelService;
	
	/**
	 * 扫描发票汇总查询(oem)
	 * @param queryParam
	 * @return
	 */
	@RequestMapping(value = "/totalQuery", method = RequestMethod.GET)
	@ResponseBody
	public PageInfoDto oemScanInvoiceTotalQuery(@RequestParam Map<String, String> queryParam){
		logger.info("============扫描发票汇总查询===============");
		/**  当前登录信息 **/
	    LoginInfoDto loginInfo = ApplicationContextHelper.getBeanByType(LoginInfoDto.class);
	    PageInfoDto pageInfoDto = service.oemScanInvoiceTotalQuery(queryParam,loginInfo);
		return pageInfoDto;
	}
	
	/**
	 * 扫描发票汇总查询信息下载
	 * @author DC
	 * @param queryParam
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(value = "/export/excel/inventory", method = RequestMethod.GET)
	@ResponseBody
	public void inventoryDownLoadList(@RequestParam Map<String, String> queryParam, HttpServletRequest request,
            HttpServletResponse response) throws Exception {
		logger.info("============扫描发票汇总查询信息下载===============");
		/**  当前登录信息 **/
	    LoginInfoDto loginInfo = ApplicationContextHelper.getBeanByType(LoginInfoDto.class);
		List<Map> resultList = service.findTotalQuerySuccList(queryParam,loginInfo);
		Map<String, List<Map>> excelData = new HashMap<>();
		excelData.put("扫描发票汇总查询信息", resultList);	
		
		List<ExcelExportColumn> exportColumnList = new ArrayList<>();
		exportColumnList.add(new ExcelExportColumn("DEALER_CODE","经销商代码"));
	    exportColumnList.add(new ExcelExportColumn("DEALER_NAME","经销商名称"));
	    exportColumnList.add(new ExcelExportColumn("SALES_SUM","实销上报数量"));

		excelService.generateExcel(excelData, exportColumnList, "扫描发票汇总查询信息.xls", request,response);
	}
	
	/**
	 * 扫描发票明细查询(oem)
	 */
	@RequestMapping(value = "/inventoryQuery", method = RequestMethod.GET)
	@ResponseBody
	public PageInfoDto oemScanInvoiceQuery(@RequestParam Map<String, String> queryParam){
		logger.info("============扫描发票明细查询===============");
		/**  当前登录信息 **/
	    LoginInfoDto loginInfo = ApplicationContextHelper.getBeanByType(LoginInfoDto.class);
	    PageInfoDto pageInfoDto = service.oemScanInvoiceQuery(queryParam,loginInfo);
		return pageInfoDto;
	}
	
	/**
	 * 扫描发票明细查询信息下载
	 * @author DC
	 * @param queryParam
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(value = "/export/excel/total", method = RequestMethod.GET)
	@ResponseBody
	public void totalDownLoadList(@RequestParam Map<String, String> queryParam, HttpServletRequest request,
            HttpServletResponse response) throws Exception {
		logger.info("============扫描发票明细查询信息下载===============");
		/**  当前登录信息 **/
	    LoginInfoDto loginInfo = ApplicationContextHelper.getBeanByType(LoginInfoDto.class);
		List<Map> resultList = service.findInventoryQuerySuccList(queryParam,loginInfo);
		Map<String, List<Map>> excelData = new HashMap<>();
		excelData.put("扫描发票明细查询信息", resultList);	
		
		List<ExcelExportColumn> exportColumnList = new ArrayList<>();
		exportColumnList.add(new ExcelExportColumn("SMALL_AREA","小区"));
	    exportColumnList.add(new ExcelExportColumn("DEALER_CODE","经销商代码"));
	    exportColumnList.add(new ExcelExportColumn("DEALER_NAME","经销商名称"));
	    exportColumnList.add(new ExcelExportColumn("SAP_CODE","SAP经销商代码"));
	    exportColumnList.add(new ExcelExportColumn("CTM_TYPE","客户类型",ExcelDataType.Oem_Dict));
	    exportColumnList.add(new ExcelExportColumn("SERIES_CODE","车系"));
	    exportColumnList.add(new ExcelExportColumn("MODEL_CODE","车型代码"));
	    exportColumnList.add(new ExcelExportColumn("MODEL_NAME","车型描述"));
	    exportColumnList.add(new ExcelExportColumn("COLOR_NAME","颜色"));
	    exportColumnList.add(new ExcelExportColumn("VIN","车架号"));
	    exportColumnList.add(new ExcelExportColumn("SALES_DATE","交车时间"));
	    exportColumnList.add(new ExcelExportColumn("SOLD_BYID","销售顾问编号"));
	    exportColumnList.add(new ExcelExportColumn("SALES_ADVISER","销售顾问姓名"));
	    exportColumnList.add(new ExcelExportColumn("SOLD_MOBILE","销售顾问电话"));
	    exportColumnList.add(new ExcelExportColumn("SOLD_EMAIL","销售顾问邮箱"));

		excelService.generateExcel(excelData, exportColumnList, "扫描发票明细查询信息.xls", request,response);
	}

}
