package com.yonyou.dms.part.controller.basedata;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.PathVariable;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.util.UriComponentsBuilder;
import com.yonyou.dms.framework.DAO.OemDAOUtil;
import com.yonyou.dms.framework.DAO.PageInfoDto;

import com.yonyou.dms.framework.service.excel.ExcelDataType;
import com.yonyou.dms.framework.service.excel.ExcelExportColumn;
import com.yonyou.dms.framework.service.excel.ExcelGenerator;
import com.yonyou.dms.function.common.OemDictCodeConstants;
import com.yonyou.dms.part.domains.PO.partBaseInfoManage.TtPtOrderDetailMopDcsPO;
import com.yonyou.dms.part.service.basedata.PartAccountService;
import com.yonyou.dms.part.service.basedata.PartOrderService;
import com.yonyou.f4.mvc.annotation.TxnConn;
/**
 * 配件订单管理
 * @author ZhaoZ
 * @date 2017年3月24日 
 */
@Controller
@TxnConn
@SuppressWarnings("all")
@RequestMapping("/partAccountManage")
public class PartAccountManageController {
	// 定义日志接口
    private static final Logger logger = LoggerFactory.getLogger(PartInfoController.class);
   
    @Autowired
	private PartAccountService partAccountService;
    @Autowired
	private ExcelGenerator excelService;
    /**
	 * 配件往来账查询 接口暂时搁放
	 * @param queryParams
	 * @throws Exception
	 */
	@RequestMapping(value="/queryCurrentAccount",method = RequestMethod.GET)
	@ResponseBody
	public PageInfoDto queryCurrentAccount(@RequestParam Map<String, String> queryParams) {
		 logger.info("=====配件往来账查询=====");
		
		 return partAccountService.queryCurrentList(queryParams);
		
	}
	
	/**
	 * 配件往来账查询 接口暂时搁放
	 * @param queryParams
	 * @throws Exception
	 */
	@RequestMapping(value="/queryCurrent",method = RequestMethod.GET)
	@ResponseBody
	public PageInfoDto queryCurrent(@RequestParam Map<String, String> queryParams) {
		 logger.info("=====配件往来账查询=====");
		
		 return partAccountService.getCurrentList(queryParams);
		
	}
	 
	/**
	 * 配件往来账导出
	 * 接口暂时搁放
	 */
	@RequestMapping(value="/downloadCurrentAccount",method = RequestMethod.GET)
	@ResponseBody
	public void downloadInvoiceDetail(HttpServletRequest request,HttpServletResponse response,@RequestParam Map<String, String> queryParams) {
		logger.info("============配件往来账导出===============");
		//List<Map> dealerList = partOrderService.queryDownLoad(queryParams);
		List<ExcelExportColumn> exportColumnList = new ArrayList<>();
		Map<String, List<Map>> excelData = new HashMap<>();
		excelData.put("配件往来账导出",null);
		
		excelService.generateExcel(excelData, exportColumnList, "配件往来账.xls", request, response);
	}
	
	/**
	 * 配件返利发放查询 接口暂时搁放
	 * @param queryParams
	 * @throws Exception
	 */
	@RequestMapping(value="/queryRebate",method = RequestMethod.GET)
	@ResponseBody
	public PageInfoDto queryRebate(@RequestParam Map<String, String> queryParams) {
		 logger.info("=====配件返利发放查询=====");
		
		 return partAccountService.queryRebateList(queryParams);
		
	}
	
	/**
	 * 配件返利发放导出
	 * 接口暂时搁放
	 */
	@RequestMapping(value="/downloadRebate",method = RequestMethod.GET)
	@ResponseBody
	public void downloadRebate(HttpServletRequest request,HttpServletResponse response,@RequestParam Map<String, String> queryParams) {
		logger.info("============配件返利发放导出===============");
		//List<Map> dealerList = partOrderService.queryDownLoad(queryParams);
		List<ExcelExportColumn> exportColumnList = new ArrayList<>();
		Map<String, List<Map>> excelData = new HashMap<>();
		excelData.put("配件返利发放导出",null);
		
		excelService.generateExcel(excelData, exportColumnList, "配件返利发放.xls", request, response);
	}
	
	/**
	 * 配件返利使用查询    接口暂时搁放
	 * @param queryParams
	 * @throws Exception
	 */
	@RequestMapping(value="/queryRebateUse",method = RequestMethod.GET)
	@ResponseBody
	public PageInfoDto queryRebateUse(@RequestParam Map<String, String> queryParams) {
		 logger.info("=====配件返利使用查询=====");
		
		 return partAccountService.queryRebateUseList(queryParams);
		
	}
	
	/**
	 * 配件返利使用导出
	 * 接口暂时搁放
	 */
	@RequestMapping(value="/downloadRebateUse",method = RequestMethod.GET)
	@ResponseBody
	public void downloadRebateUse(HttpServletRequest request,HttpServletResponse response,@RequestParam Map<String, String> queryParams) {
		logger.info("============配件返利发放导出===============");
		//List<Map> dealerList = partOrderService.queryDownLoad(queryParams);
		List<ExcelExportColumn> exportColumnList = new ArrayList<>();
		Map<String, List<Map>> excelData = new HashMap<>();
		excelData.put("配件返利发放导出",null);
		
		excelService.generateExcel(excelData, exportColumnList, "配件返利发放.xls", request, response);
	}
}
