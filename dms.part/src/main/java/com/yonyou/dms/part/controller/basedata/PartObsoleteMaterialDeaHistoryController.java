package com.yonyou.dms.part.controller.basedata;

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

import com.yonyou.dms.framework.DAO.OemDAOUtil;
import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.framework.service.excel.ExcelExportColumn;
import com.yonyou.dms.framework.service.excel.ExcelGenerator;
import com.yonyou.dms.part.service.basedata.PartObsoleteMaterialDeaHistoryService;
import com.yonyou.f4.mvc.annotation.TxnConn;
/**
 * 呆滞品交易历史查询
 * @author ZhaoZ
 *@date 2017年4月13日
 */
import com.yonyou.f4.mvc.controller.BaseController;
@Controller
@TxnConn
@RequestMapping("/partObsoleteMaterialDeaHistory")
public class PartObsoleteMaterialDeaHistoryController extends BaseController{
	// 定义日志接口
    private static final Logger logger = LoggerFactory.getLogger(PartInfoController.class);
    @Autowired
	private PartObsoleteMaterialDeaHistoryService service;
    @Autowired
   	private ExcelGenerator excelService;
    
    /**
  	 * 呆滞品交易历史查询
  	 * @param queryParams
  	 * @throws Exception
  	 */
  	@RequestMapping(value="/findALL",method = RequestMethod.GET)
  	@ResponseBody
  	public PageInfoDto findALL(@RequestParam Map<String, String> queryParams) {
  		 logger.info("=====呆滞品交易查询=====");
  		
  		 return service.findALLList(queryParams);
  		
  	}
  	/**
  	 * 状态
  	 * @param queryParams
  	 * @throws Exception
  	 */
  	@RequestMapping(value="/status",method = RequestMethod.GET)
  	@ResponseBody
  	public List<Map> status() {
  		 logger.info("=====状态=====");
  		 StringBuffer pasql = new StringBuffer();
		 pasql.append("SELECT tc.code_id codeId, CASE WHEN tc.code_id LIKE 70351001 THEN '进行中' WHEN tc.code_id LIKE 70351004  THEN '已完成' WHEN tc.code_id  LIKE 70351006  THEN '已取消' END typeName  \n  ");
		 pasql.append("FROM tc_code_dcs tc WHERE TYPE=7035 \n ");
		 pasql.append("AND tc.code_id != 70351002 AND tc.code_id != 70351003  AND tc.code_id != 70351005");
		 List<Map> list = OemDAOUtil.findAll(pasql.toString(), null);
		return list;
  	} 
  	
  	
  	/**
	 * 呆滞品交易历史下载
	 */
	@RequestMapping(value="/download/{type}",method = RequestMethod.GET)
	@ResponseBody
	public void download(HttpServletRequest request,HttpServletResponse response,@RequestParam Map<String, String> queryParams,@PathVariable(value = "type") String type) {
		logger.info("============呆滞品交易历史下载===============");
		List<Map> dealerList = service.queryDownLoad(queryParams);
		List<ExcelExportColumn> exportColumnList = new ArrayList<>();
	
		Map<String, List<Map>> excelData = new HashMap<>();
		excelData.put("呆滞品交易历史下载",dealerList);
		exportColumnList.add(new ExcelExportColumn("ORG_NAME2", "大区"));
		exportColumnList.add(new ExcelExportColumn("ORG_NAME", "小区"));
		exportColumnList.add(new ExcelExportColumn("DDEALER_CODE", "调出经销商代码"));
		exportColumnList.add(new ExcelExportColumn("DDEALER_SHORTNAME", "调出经销商名称"));
		exportColumnList.add(new ExcelExportColumn("ADEALER_CODE", "调入经销商代码"));
		exportColumnList.add(new ExcelExportColumn("ADEALER_SHORTNAME", "调入经销商名称"));
		if("1".equals(type)){
			exportColumnList.add(new ExcelExportColumn("WAREHOUSE", "仓库"));
		}
		exportColumnList.add(new ExcelExportColumn("PART_CODE", "配件代码"));
		exportColumnList.add(new ExcelExportColumn("PART_NAME", "配件名称"));
		exportColumnList.add(new ExcelExportColumn("APPLY_NUMBER", "调拨数量"));
		exportColumnList.add(new ExcelExportColumn("SALES_PRICE", "单价"));
		exportColumnList.add(new ExcelExportColumn("TOTALS", "金额"));
		exportColumnList.add(new ExcelExportColumn("APPLY_DATE", "申请日期"));
		exportColumnList.add(new ExcelExportColumn("AFFIRM_DATE", "确认日期"));
		exportColumnList.add(new ExcelExportColumn("OUT_WAREHOUS_DATE", "出库日期"));
		exportColumnList.add(new ExcelExportColumn("PUT_WAREHOUS_DATE", "入库日期"));
		exportColumnList.add(new ExcelExportColumn("STATUS_NAME", "订单类型"));
		excelService.generateExcel(excelData, exportColumnList, "呆滞品交易历史.xls", request, response);
	}
	
	/**
  	 * 大区售后
  	 * @param queryParams
  	 * @throws Exception
  	 */
  	@RequestMapping(value="/getBigArea",method = RequestMethod.GET)
  	@ResponseBody
  	public List<Map> getBigArea() {
  		 logger.info("=====大区=====");
  		 return service.getBigAreaList();
  	} 
	
	
	/**
  	 * 小区售后
  	 * @param queryParams
  	 * @throws Exception
  	 */
  	@RequestMapping(value="/{bigArea}/getSmallArea",method = RequestMethod.GET)
  	@ResponseBody
  	public List<Map> getSmallArea(@PathVariable(value = "bigArea") Long bigArea) {
  		 logger.info("=====小区售后=====");
  		 return service.getSmallAreaList(bigArea);
  	} 
}
