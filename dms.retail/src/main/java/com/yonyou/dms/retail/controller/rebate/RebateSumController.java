package com.yonyou.dms.retail.controller.rebate;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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
import com.yonyou.dms.framework.service.excel.ExcelExportColumn;
import com.yonyou.dms.framework.service.excel.ExcelGenerator;
import com.yonyou.dms.framework.util.bean.ApplicationContextHelper;
import com.yonyou.dms.retail.service.rebate.RebateSumService;
import com.yonyou.f4.mvc.annotation.TxnConn;
import com.yonyou.f4.mvc.controller.BaseController;
/**
 * 经销商返利核算汇总
 * @author Administrator
 *
 */
@SuppressWarnings("rawtypes")
@Controller
@TxnConn
@RequestMapping("/rebateSum")
public class RebateSumController  extends BaseController{
	private static final Logger logger = LoggerFactory.getLogger(DAOUtil.class);
	@Autowired
	RebateSumService rservice;
	
	@Autowired
	ExcelGenerator excelService;
	
		@RequestMapping(value = "/export/excel", method = RequestMethod.GET)
		public void exportCarownerInfo(@RequestParam Map<String, String> queryParam, HttpServletRequest request,
				HttpServletResponse response) throws Exception {
			List<Map> resultList = rservice.queryEmpInfoforExport(queryParam);
			Map<String, List<Map>> excelData = new HashMap<String, List<Map>>();
			excelData.put("经销商返利汇总信息管理", resultList);
			// 生成excel 文件
			List<ExcelExportColumn> exportColumnList = new ArrayList<ExcelExportColumn>();
			exportColumnList.add(new ExcelExportColumn("BUSINESS_POLICY_NAME", "商务政策"));
			exportColumnList.add(new ExcelExportColumn("BUSINESS_POLICY_TYPE", "商务政策类型"));
			exportColumnList.add(new ExcelExportColumn("APPLICABLE_TIME", "适用时段"));
			exportColumnList.add(new ExcelExportColumn("RELEASE_DATE", "发布时间"));
			exportColumnList.add(new ExcelExportColumn("START_MONTH", "起始月"));
			exportColumnList.add(new ExcelExportColumn("END_MONTH", "结束月"));
			exportColumnList.add(new ExcelExportColumn("DEALER_CODE", "经销商代码"));
			exportColumnList.add(new ExcelExportColumn("DEALER_NAME", "经销商名称"));
			exportColumnList.add(new ExcelExportColumn("NOMAL_BONUS", "常规奖金"));
			exportColumnList.add(new ExcelExportColumn("SPECIAL_BONUS", "特批奖金"));
			exportColumnList.add(new ExcelExportColumn("BACK_BONUSES_EST", "追溯奖金（估算）"));
			exportColumnList.add(new ExcelExportColumn("BACK_BONUSES_DOWN", "追溯奖金（下发）"));
			exportColumnList.add(new ExcelExportColumn("NEW_INCENTIVES", "新经销商返利"));
			excelService.generateExcel(excelData, exportColumnList, "经销商返利汇总信息管理.xls", request, response);

		}
		
		/**
		 * 
		* @Title: queryDefeatReason 
		* @Description: 经销商返利核算汇总查询（OEM)
		* @param @param queryParam
		* @param @return
		* @param @throws Exception    设定文件 
		* @return PageInfoDto    返回类型 
		* @throws
		 */
	    @RequestMapping(method = RequestMethod.GET)
	    @ResponseBody
	    public PageInfoDto queryDefeatReason(@RequestParam Map<String, String> queryParam) throws Exception {
	    	logger.info("查询经销商返利汇总信息");
	        PageInfoDto pageInfoDto=rservice.getRebateSum(queryParam);
	        return pageInfoDto;
	    }
	    
	    
		@RequestMapping(value = "/export/excelmx", method = RequestMethod.GET)
		public void exportMX(@RequestParam Map<String, String> queryParam, HttpServletRequest request,
				HttpServletResponse response) throws Exception {
			List<Map> resultList = rservice.queryRebateSumMX(queryParam);
			Map<String, List<Map>> excelData = new HashMap<String, List<Map>>();
			excelData.put("经销商返利汇总信息管理", resultList);
			// 生成excel 文件
			List<ExcelExportColumn> exportColumnList = new ArrayList<ExcelExportColumn>();
			exportColumnList.add(new ExcelExportColumn("BUSINESS_POLICY_NAME", "商务政策"));
			exportColumnList.add(new ExcelExportColumn("APPLICABLE_TIME", "适用时段"));
			exportColumnList.add(new ExcelExportColumn("RELEASE_DATE", "发布时间"));
			exportColumnList.add(new ExcelExportColumn("START_MONTH", "起始月"));
			exportColumnList.add(new ExcelExportColumn("END_MONTH", "结束月"));
			exportColumnList.add(new ExcelExportColumn("DEALER_CODE", "经销商代码"));
			exportColumnList.add(new ExcelExportColumn("DEALER_NAME", "经销商名称"));
			exportColumnList.add(new ExcelExportColumn("NOMAL_BONUS", "常规奖金"));
			exportColumnList.add(new ExcelExportColumn("SPECIAL_BONUS", "特批奖金"));
			exportColumnList.add(new ExcelExportColumn("BACK_BONUSES_EST", "追溯奖金（估算）"));
			exportColumnList.add(new ExcelExportColumn("BACK_BONUSES_DOWN", "追溯奖金（下发）"));
			exportColumnList.add(new ExcelExportColumn("NEW_INCENTIVES", "新经销商返利"));
			excelService.generateExcel(excelData, exportColumnList, "经销商返利汇总信息管理.xls", request, response);

		}
	    
		@RequestMapping(value="mingxi",method = RequestMethod.GET)
	    @ResponseBody
	    public List<Map> queryMX(@RequestParam Map<String, String> queryParam) throws Exception {
	    	logger.info("查询经销商返利汇总信息");
	    	List<Map> map=rservice.getRebateSumMX(queryParam);
	        return map;
	    }
		
		/**
		 * 
		* @Title: getDownDetail 
		* @Description: 返利核算汇总明细下载查询（OEM）
		* @param @param queryParam
		* @param @return
		* @param @throws Exception    设定文件 
		* @return List<Map>    返回类型 
		* @throws
		 */
		@RequestMapping(value="/getDownDetail/{logId}/{dealerCode}",method = RequestMethod.GET)
	    @ResponseBody
	    public PageInfoDto getDownDetail(@RequestParam Map<String, String> queryParam,
	    		@PathVariable(value = "logId") String logId,  
	    		@PathVariable(value = "dealerCode") String dealerCode) throws Exception {
	    	logger.info("================返利核算汇总明细下载查询（OEM）====================================");
	    	//获取当前用户
			LoginInfoDto logonUser = ApplicationContextHelper.getBeanByType(LoginInfoDto.class);
			//静态
			String drlFlag = "1002";
        	PageInfoDto pageInfoDto = rservice.pageQueryDetailDownSt(logId,drlFlag,dealerCode,logonUser);
	        return pageInfoDto;
	    }
		
		/**
		 * 
		* @Title: exportMX 
		* @Description: 返利核算汇总查询（DLR），明细下载
		* @param @param logId
		* @param @param dealerCode
		* @param @param drlFlag
		* @param @param request
		* @param @param response
		* @param @throws Exception    设定文件 
		* @return void    返回类型 
		* @throws
		 */
		@RequestMapping(value = "/export/downDetailFile/{logId}/Deatil/{dealerCode}", method = RequestMethod.GET)
		@ResponseBody
		public void downDetailFile1( 
				HttpServletRequest request, @RequestParam Map<String, String> queryParam,
				HttpServletResponse response,	@PathVariable(value = "logId") String logId,  
	    		@PathVariable(value = "dealerCode") String dealerCode ) throws Exception {
			logger.info("=======返利核算汇总查询（OEM），明细下载=====================================");
			
			//获取当前用户
			LoginInfoDto logonUser = ApplicationContextHelper.getBeanByType(LoginInfoDto.class);
			String drlFlag = "1002";
			//静态
        	List<Map> sList = rservice.queryDetailDownSt(logId,drlFlag,dealerCode,logonUser);
			//动态
	//		List<Map> dList = rservice.queryDetailDownDy(logId,drlFlag,dealerCode,logonUser);
			
	//		rservice.addExcelExportColumn(sList, dList, request, response);
			
			Map<String, List<Map>> excelData = new HashMap<String, List<Map>>();
			excelData.put("返利核算明细下载", sList);
			// 生成excel 文件
			List<ExcelExportColumn> exportColumnList = new ArrayList<ExcelExportColumn>();
			exportColumnList.add(new ExcelExportColumn("BUSINESS_POLICY_NAME", "商务政策"));
			exportColumnList.add(new ExcelExportColumn("APPLICABLE_TIME", "适用时段"));
			exportColumnList.add(new ExcelExportColumn("RELEASE_DATE", "发布时间"));
			exportColumnList.add(new ExcelExportColumn("START_MONTH", "起始月"));
			exportColumnList.add(new ExcelExportColumn("END_MONTH", "结束月"));
			exportColumnList.add(new ExcelExportColumn("DEALER_CODE", "经销商代码"));
			exportColumnList.add(new ExcelExportColumn("DEALER_NAME", "经销商名称"));
			exportColumnList.add(new ExcelExportColumn("VIN", "VIN"));
			exportColumnList.add(new ExcelExportColumn("MODEL_NAME", "车型"));
			exportColumnList.add(new ExcelExportColumn("COUNT", "Count"));
			exportColumnList.add(new ExcelExportColumn("NOMAL_BONUS", "常规奖金"));
			exportColumnList.add(new ExcelExportColumn("SPECIAL_BONUS", "特批奖金"));
			exportColumnList.add(new ExcelExportColumn("BACK_BONUSES_EST", "追溯奖金（估算）"));
			exportColumnList.add(new ExcelExportColumn("BACK_BONUSES_DOWN", "追溯奖金（下发）"));
			exportColumnList.add(new ExcelExportColumn("NEW_INCENTIVES", "新经销商返利"));
			excelService.generateExcel(excelData, exportColumnList, "返利核算明细下载"+ getCurrentTime(10)+".xls", request, response);

		}
		
		/**
		 * @param i
		 * @return String
		 * @author  richard
		 */
		public static  String getCurrentTime(int i) {
			SimpleDateFormat formatter;
			Date currentTime_1 = new java.util.Date();
			if (i == 0) //for filename
				{
				formatter = new SimpleDateFormat("yyyy-MM-dd-HH");
			} else if (i == 1) {
				formatter = new SimpleDateFormat("yyyy-MM-dd");
			} else if (i == 3) {
				formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm");
			} else if (i == 4) {
				formatter = new SimpleDateFormat("HH:mm");
			} else if (i == 5) {
				formatter = new SimpleDateFormat("HH:mm:ss");
			} else if (i == 6) {
				formatter = new SimpleDateFormat("yyyy");
			} else if (i == 7) {
				formatter = new SimpleDateFormat("MM");
			} else if (i == 8) {
				formatter = new SimpleDateFormat("dd");
			} else if (i == 9) {
				formatter = new SimpleDateFormat("yyyy_MM_dd_hh_mm_ss");
			} else if (i == 10) {
				formatter = new SimpleDateFormat("yyyyMMdd");
			} else if (i == 11) {
				formatter = new SimpleDateFormat("yyyyMMddHHmmss");
			} else if (i == 12) {
				formatter = new SimpleDateFormat("yyMMdd");
			} else if (i == 13) {
				formatter = new SimpleDateFormat("yyMMddHHmmss");
			} else {
				formatter = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");
			}

			String dateString = formatter.format(currentTime_1);
			return dateString;
		}
		
		@RequestMapping(value = "/export/downDetailFile", method = RequestMethod.GET)
		@ResponseBody
		public void downDetailFile( 
				HttpServletRequest request, @RequestParam Map<String, String> queryParam,
				HttpServletResponse response ) throws Exception {
			logger.info("=======返利核算汇总查询（OEM），明细下载=====================================");
			String logId = queryParam.get("logIdName");
			String dealerCode = queryParam.get("dealerCodeName");
			//获取当前用户
			LoginInfoDto logonUser = ApplicationContextHelper.getBeanByType(LoginInfoDto.class);
			String drlFlag = "1002";
			//静态
        	List<Map> sList = rservice.queryDetailDownSt(logId,drlFlag,dealerCode,logonUser);
			//动态
	//		List<Map> dList = rservice.queryDetailDownDy(logId,drlFlag,dealerCode,logonUser);
			
	//		rservice.addExcelExportColumn(sList, dList, request, response);
			
			Map<String, List<Map>> excelData = new HashMap<String, List<Map>>();
			excelData.put("返利核算明细下载", sList);
			// 生成excel 文件
			List<ExcelExportColumn> exportColumnList = new ArrayList<ExcelExportColumn>();
			exportColumnList.add(new ExcelExportColumn("BUSINESS_POLICY_NAME", "商务政策"));
			exportColumnList.add(new ExcelExportColumn("APPLICABLE_TIME", "适用时段"));
			exportColumnList.add(new ExcelExportColumn("RELEASE_DATE", "发布时间"));
			exportColumnList.add(new ExcelExportColumn("START_MONTH", "起始月"));
			exportColumnList.add(new ExcelExportColumn("END_MONTH", "结束月"));
			exportColumnList.add(new ExcelExportColumn("DEALER_CODE", "经销商代码"));
			exportColumnList.add(new ExcelExportColumn("DEALER_NAME", "经销商名称"));
			exportColumnList.add(new ExcelExportColumn("VIN", "VIN"));
			exportColumnList.add(new ExcelExportColumn("MODEL_NAME", "车型"));
			exportColumnList.add(new ExcelExportColumn("COUNT", "Count"));
			exportColumnList.add(new ExcelExportColumn("NOMAL_BONUS", "常规奖金"));
			exportColumnList.add(new ExcelExportColumn("SPECIAL_BONUS", "特批奖金"));
			exportColumnList.add(new ExcelExportColumn("BACK_BONUSES_EST", "追溯奖金（估算）"));
			exportColumnList.add(new ExcelExportColumn("BACK_BONUSES_DOWN", "追溯奖金（下发）"));
			exportColumnList.add(new ExcelExportColumn("NEW_INCENTIVES", "新经销商返利"));
			excelService.generateExcel(excelData, exportColumnList, "返利核算明细下载"+ getCurrentTime(10)+".xls", request, response);

		}

}
