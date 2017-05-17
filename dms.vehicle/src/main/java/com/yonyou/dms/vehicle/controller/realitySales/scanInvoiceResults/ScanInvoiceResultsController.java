package com.yonyou.dms.vehicle.controller.realitySales.scanInvoiceResults;

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
import org.springframework.web.bind.annotation.PathVariable;
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
import com.yonyou.dms.vehicle.service.realitySales.scanInvoiceResults.ScanInvoiceResultsService;
import com.yonyou.f4.mvc.annotation.TxnConn;

/**
 * 发票扫描结果Controller
 * @author DC
 *
 */
@Controller
@TxnConn
@RequestMapping("/invoiceScanResults")
public class ScanInvoiceResultsController {
	
	private static final Logger logger = LoggerFactory.getLogger(DAOUtil.class);
	
	@Autowired
	private ScanInvoiceResultsService service;
	
	@Autowired
	private ExcelGenerator  excelService;
	
	/**
	 * 发票扫描结果查询
	 * @param queryParam
	 * @return
	 */
	@RequestMapping(method = RequestMethod.GET)
	@ResponseBody
	public PageInfoDto scanInvoiceResultsQuery(@RequestParam Map<String, String> queryParam){
		logger.info("============发票扫描结果查询===============");
		/**  当前登录信息 **/
	    LoginInfoDto loginInfo = ApplicationContextHelper.getBeanByType(LoginInfoDto.class);
		PageInfoDto pageInfoDto = service.scanInvoiceResultsQuery(queryParam,loginInfo);
		return pageInfoDto;
	}
	
	/**
     * 根据ID 零售上报信息(经销商段)
     * @param id VIN
     * @return
     */
    @RequestMapping(value = "/dealer/detail/{id}", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> findRealitySalesQueryDetail(@PathVariable(value = "id") Long id) {
    	LoginInfoDto loginInfo = ApplicationContextHelper.getBeanByType(LoginInfoDto.class);
    	logger.info("============根据ID 发票扫描结果信息===============");
    	Map<String, Object> map = service.queryDealerDetail(id,loginInfo);
        return map;
    }
	
	/**
	 * 发票扫描结果信息下载
	 * @author DC
	 * @param queryParam
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/export/excel", method = RequestMethod.GET)
	@ResponseBody
	public void scanInvoiceResultsDownLoadList(@RequestParam Map<String, String> queryParam, HttpServletRequest request,
            HttpServletResponse response) throws Exception {
		logger.info("============发票扫描结果信息下载===============");
		/**  当前登录信息 **/
	    LoginInfoDto loginInfo = ApplicationContextHelper.getBeanByType(LoginInfoDto.class);
		List<Map> resultList = service.scanInvoiceResultsDownLoadList(queryParam,loginInfo);
		Map<String, List<Map>> excelData = new HashMap<>();
		excelData.put("发票扫描结果信息", resultList);	
		List<ExcelExportColumn> exportColumnList = new ArrayList<>();
	    exportColumnList.add(new ExcelExportColumn("DEALER_CODE","经销商代码"));
	    exportColumnList.add(new ExcelExportColumn("DEALER_NAME","经销商名称"));
	    exportColumnList.add(new ExcelExportColumn("IS_YN","是否扫描成功"));
	    exportColumnList.add(new ExcelExportColumn("SCAN_NUM","扫描次数"));
	    
	    exportColumnList.add(new ExcelExportColumn("VIN","上报数据")); // VIN
	    exportColumnList.add(new ExcelExportColumn("IS_VIN","OCR结果"));
	    
	    exportColumnList.add(new ExcelExportColumn("CARD_NUM","上报数据"));//证件号码
	    exportColumnList.add(new ExcelExportColumn("IS_CARD_NUM","OCR结果"));
	    
	    exportColumnList.add(new ExcelExportColumn("INVOICE_DATE","上报数据"));//开票日期
	    exportColumnList.add(new ExcelExportColumn("IS_INVOICE_DATE","OCR结果"));
	    
	    exportColumnList.add(new ExcelExportColumn("SALES_DATE1","扫描日期"));
	    exportColumnList.add(new ExcelExportColumn("ONE_DAY_SCAN","72小时内扫描"));
	    exportColumnList.add(new ExcelExportColumn("TWO_DAY_SCAN","120小时内扫描"));
	    exportColumnList.add(new ExcelExportColumn("CTM_NAME","客户姓名/公司名称"));
	    exportColumnList.add(new ExcelExportColumn("MAIN_PHONE","联系电话"));
	    exportColumnList.add(new ExcelExportColumn("CTM_TYPE","客户类型", ExcelDataType.Oem_Dict));
	    exportColumnList.add(new ExcelExportColumn("CODE2","车系"));
	    exportColumnList.add(new ExcelExportColumn("CODE3","车型代码"));
	    exportColumnList.add(new ExcelExportColumn("GROUP_NAME","车型描述"));
	    exportColumnList.add(new ExcelExportColumn("COLOR_NAME","颜色"));
	    exportColumnList.add(new ExcelExportColumn("SALES_DATE2","交车时间"));
	    excelService.generateExcel(excelData, exportColumnList, "发票扫描结果信息.xls", request,response);
	}

}
