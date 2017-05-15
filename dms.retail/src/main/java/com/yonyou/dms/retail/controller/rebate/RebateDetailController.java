package com.yonyou.dms.retail.controller.rebate;

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
import com.yonyou.dms.framework.service.excel.ExcelDataType;
import com.yonyou.dms.framework.service.excel.ExcelExportColumn;
import com.yonyou.dms.framework.service.excel.ExcelGenerator;
import com.yonyou.dms.retail.service.rebate.RebateDetailService;
import com.yonyou.f4.mvc.annotation.TxnConn;
import com.yonyou.f4.mvc.controller.BaseController;
/**
 * 
* @ClassName: RebateDetailController 
* @Description: 经销商返利核算汇总（OEM）
* @author zhengzengliang 
* @date 2017年4月26日 下午8:00:02 
*
 */
@SuppressWarnings("rawtypes")
@Controller
@TxnConn
@RequestMapping("/rebateDetail")
public class RebateDetailController extends BaseController{
	private static final Logger logger = LoggerFactory.getLogger(DAOUtil.class);
	@Autowired
	RebateDetailService rservice;
	
	@Autowired
	ExcelGenerator excelService;
	
	
		/**
		 * 
		* @Title: exportCarownerInfo 
		* @Description: 经销商返利核算汇总查询（OEM）（下载）
		* @param @param queryParam
		* @param @param request
		* @param @param response
		* @param @throws Exception    设定文件 
		* @return void    返回类型 
		* @throws
		 */
		@RequestMapping(value = "/export/excel", method = RequestMethod.GET)
		public void exportCarownerInfo(@RequestParam Map<String, String> queryParam, HttpServletRequest request,
				HttpServletResponse response) throws Exception {
			List<Map> resultList = rservice.queryEmpInfoforExport(queryParam);
			Map<String, List<Map>> excelData = new HashMap<String, List<Map>>();
			excelData.put("经销商返利明细信息管理", resultList);
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
			exportColumnList.add(new ExcelExportColumn("VIN", "VIN"));
			exportColumnList.add(new ExcelExportColumn("MODEL_NAME", "车型"));
			exportColumnList.add(new ExcelExportColumn("COUNT", "COUNT"));
			exportColumnList.add(new ExcelExportColumn("NOMAL_BONUS", "常规奖金"));
			exportColumnList.add(new ExcelExportColumn("SPECIAL_BONUS", "特批奖金"));	
			exportColumnList.add(new ExcelExportColumn("BACK_BONUSES_EST", "追溯奖金（估算）"));
			exportColumnList.add(new ExcelExportColumn("BACK_BONUSES_DOWN", "追溯奖金（下发）"));
			exportColumnList.add(new ExcelExportColumn("NEW_INCENTIVES", "新经销商返利"));
			exportColumnList.add(new ExcelExportColumn("CREATE_DATE", "上传日期"));
			excelService.generateExcel(excelData, exportColumnList, "经销商返利明细信息管理.xls", request, response);

		}
	 
	 	/**
	 	 * 
	 	* @Title: queryDefeatReason 
	 	* @Description: 经销商返利核算 汇总查询（OEM）（明细）
	 	* @param @param queryParam
	 	* @param @return
	 	* @param @throws Exception    设定文件 
	 	* @return PageInfoDto    返回类型 
	 	* @throws
	 	 */
	    @RequestMapping(value = "/{logId}/{dealerCode}", method = RequestMethod.GET)
	    @ResponseBody
	    public PageInfoDto queryDefeatReason(@RequestParam Map<String, String> queryParam,
	    		@PathVariable(value = "logId") String logId,
	    		@PathVariable(value = "dealerCode") String dealerCode)
	    		throws Exception {
	    	logger.info("=========================经销商返利核算 汇总查询（OEM）（明细）===============================");
	    	PageInfoDto pageInfoDto=rservice.getRebateDetail(logId,dealerCode,queryParam);
	        return pageInfoDto;
	    }
	    
	    /**
	     * 返利核算明细查询
	     * @param queryParam
	     * @param logId
	     * @param dealerCode
	     * @return
	     * @throws Exception
	     */
	    @RequestMapping(value = "/Detail", method = RequestMethod.GET)
	    @ResponseBody
	    public PageInfoDto queryDefeatReason1(@RequestParam Map<String, String> queryParam)
	    		throws Exception {
	    	logger.info("=========================经销商返利核算明细查询===============================");
	    	PageInfoDto pageInfoDto=rservice.getRebateDetail1(queryParam);
	        return pageInfoDto;
	    }
	    
	    
	    @RequestMapping(value = "/Detail/export/excel", method = RequestMethod.GET)
		public void exportCarownerInfo1(@RequestParam Map<String, String> queryParam, HttpServletRequest request,
				HttpServletResponse response) throws Exception {
			List<Map> resultList = rservice.queryEmpInfoforExport1(queryParam);
			Map<String, List<Map>> excelData = new HashMap<String, List<Map>>();
			excelData.put("返利核算明细查询", resultList);
			// 生成excel 文件
			List<ExcelExportColumn> exportColumnList = new ArrayList<ExcelExportColumn>();
			exportColumnList.add(new ExcelExportColumn("BUSINESS_POLICY_NAME", "商务政策"));
			exportColumnList.add(new ExcelExportColumn("BUSINESS_POLICY_TYPE", "商务政策类型",ExcelDataType.Oem_Dict));
			exportColumnList.add(new ExcelExportColumn("APPLICABLE_TIME", "适用时段"));
			exportColumnList.add(new ExcelExportColumn("RELEASE_DATE", "发布时间"));
			exportColumnList.add(new ExcelExportColumn("START_MONTH", "起始月"));
			exportColumnList.add(new ExcelExportColumn("END_MONTH", "结束月"));
			exportColumnList.add(new ExcelExportColumn("DEALER_CODE", "经销商代码"));
			exportColumnList.add(new ExcelExportColumn("DEALER_NAME", "经销商名称"));
			exportColumnList.add(new ExcelExportColumn("VIN", "VIN"));
			exportColumnList.add(new ExcelExportColumn("MODEL_NAME", "车型"));
			exportColumnList.add(new ExcelExportColumn("COUNT", "COUNT"));
			exportColumnList.add(new ExcelExportColumn("NOMAL_BONUS", "常规奖金"));
			exportColumnList.add(new ExcelExportColumn("SPECIAL_BONUS", "特批奖金"));	
			exportColumnList.add(new ExcelExportColumn("BACK_BONUSES_EST", "追溯奖金（估算）"));
			exportColumnList.add(new ExcelExportColumn("BACK_BONUSES_DOWN", "追溯奖金（下发）"));
			exportColumnList.add(new ExcelExportColumn("NEW_INCENTIVES", "新经销商返利"));
			exportColumnList.add(new ExcelExportColumn("CREATE_DATE", "上传日期"));
			excelService.generateExcel(excelData, exportColumnList, "返利核算明查询.xls", request, response);
		}
	    
	    /**
	     * 返利核算汇总查询(DLR)明细
	     * @param queryParam
	     * @param logId
	     * @param dealerCode
	     * @return
	     * @throws Exception
	     */
	    @RequestMapping(value = "/DeatailJSum1/{logId}/{dealerCode}", method = RequestMethod.GET)
	    @ResponseBody
	    public PageInfoDto queryDefeatReason2(@RequestParam Map<String, String> queryParam,
	    		@PathVariable(value = "logId") String logId,
	    		@PathVariable(value = "dealerCode") String dealerCode)
	    		throws Exception {
	    	logger.info("=========================经销商返利核算明细查询===============================");
	    	PageInfoDto pageInfoDto=rservice.getRebateDetail2(logId,dealerCode,queryParam);
	        return pageInfoDto;
	    }
	    
}
