package com.yonyou.dms.vehicle.controller.threePack;

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
import com.yonyou.dms.vehicle.service.threePack.ThreePackWarnQueryService;
import com.yonyou.f4.mvc.annotation.TxnConn;
import com.yonyou.f4.mvc.controller.BaseController;
@Controller
@TxnConn
@RequestMapping("/query")
public class ThreePackWarnQueryController extends BaseController{
	private static final Logger logger = LoggerFactory.getLogger(DAOUtil.class);

	@Autowired
	ThreePackWarnQueryService tservice;
	
	@Autowired
	ExcelGenerator excelService;
	 @RequestMapping(method = RequestMethod.GET)
	    @ResponseBody
	    public PageInfoDto queryDefeatReason(@RequestParam Map<String, String> queryParam) throws Exception {
	    	logger.info("查询三包预警车辆信息");
	        PageInfoDto pageInfoDto=tservice.findthreePack(queryParam);
	        return pageInfoDto;
	    }

	  @RequestMapping(value="/info/{id}/{vin}",method = RequestMethod.GET)
	    @ResponseBody
	    public Map<String,Object> queryInfo(@PathVariable(value = "id") Long id,@PathVariable(value = "vin") String vin) throws Exception {
	    	logger.info("客户信息");
	    	Map<String,Object> pageInfoDto=new HashMap<>();
	    	pageInfoDto=tservice.queryList(vin,id);
	        return pageInfoDto;
	    }
	  @RequestMapping(value="/his",method = RequestMethod.GET)
	    @ResponseBody
	    public PageInfoDto queryHis(@RequestParam Map<String, String> queryParam) throws Exception {
	    	logger.info("查询历史维修信息");
	        PageInfoDto pageInfoDto=tservice.findHis(queryParam);
	        return pageInfoDto;
	    }
	  @RequestMapping(value="/labour/{VIN}/{WARN_ITEMS}",method = RequestMethod.GET)
	    @ResponseBody
	    public PageInfoDto queryLabourList(@PathVariable(value = "VIN") String vin,@PathVariable(value = "WARN_ITEMS") String itemNo) throws Exception {
	    	logger.info("预警列表查询");
	    	PageInfoDto pageInfoDto=tservice.queryLabourList(vin,itemNo);
	        return pageInfoDto;
	    }
	  @RequestMapping(value="/group",method = RequestMethod.GET)
	    @ResponseBody
	    public List<Map> selectGroupName(@RequestParam Map<String, String> queryParam) throws Exception {
	    	logger.info("车型列表查询");
	    	List<Map> pageInfoDto=tservice.selectGroupName(queryParam);
	        return pageInfoDto;
	    }

	  @SuppressWarnings("rawtypes")
		@RequestMapping(value = "/export/excel", method = RequestMethod.GET)
		public void exportCarownerInfo(@RequestParam Map<String, String> queryParam, HttpServletRequest request,
				HttpServletResponse response) throws Exception {
			System.out.println("下载方法");
			List<Map> resultList = tservice.queryEmpInfoforExport(queryParam);
			Map<String, List<Map>> excelData = new HashMap<String, List<Map>>();
			excelData.put("三包预警车辆", resultList);
			// 生成excel 文件
			List<ExcelExportColumn> exportColumnList = new ArrayList<ExcelExportColumn>();
			exportColumnList.add(new ExcelExportColumn("DEALER_CODE", "经销商代码"));
			exportColumnList.add(new ExcelExportColumn("DEALER_SHORTNAME", "经销商简称"));
			exportColumnList.add(new ExcelExportColumn("VIN", "VIN"));
			exportColumnList.add(new ExcelExportColumn("WARN_ITEMS", "项目编号"));
			exportColumnList.add(new ExcelExportColumn("WARN_ITEM_NAME", "项目名称"));
			exportColumnList.add(new ExcelExportColumn("COLOR", "预警级别"));
			exportColumnList.add(new ExcelExportColumn("WARN_DATE", "预警日期"));
			excelService.generateExcel(excelData, exportColumnList, "三包预警车辆信息.xls", request, response);
		}
	  @SuppressWarnings("rawtypes")
		@RequestMapping(value = "/export/excelMX", method = RequestMethod.GET)
		public void exportM(@RequestParam Map<String, String> queryParam, HttpServletRequest request,
				HttpServletResponse response) throws Exception {
			System.out.println("下载方法");
			List<Map> resultList = tservice.queryMX(queryParam);
			Map<String, List<Map>> excelData = new HashMap<String, List<Map>>();
			excelData.put("三包预警车辆明细", resultList);
			// 生成excel 文件
			List<ExcelExportColumn> exportColumnList = new ArrayList<ExcelExportColumn>();
			exportColumnList.add(new ExcelExportColumn("DEALER_CODE", "经销商代码"));
			exportColumnList.add(new ExcelExportColumn("DEALER_SHORTNAME", "经销商名称"));
			exportColumnList.add(new ExcelExportColumn("VIN", "VIN"));
			exportColumnList.add(new ExcelExportColumn("LICENSE_NO", "牌照"));
			exportColumnList.add(new ExcelExportColumn("CTM_NAME", "车主"));
			exportColumnList.add(new ExcelExportColumn("COLOR", "预警级别",ExcelDataType.Oem_Dict));
			exportColumnList.add(new ExcelExportColumn("WARN_DATE", "预警日期"));
			exportColumnList.add(new ExcelExportColumn("WARN_ITEMS", "预警项目"));
			exportColumnList.add(new ExcelExportColumn("WARN_ITEM_NAME", "项目名称"));
			exportColumnList.add(new ExcelExportColumn("WARN_TIMES", "预警内容"));
			exportColumnList.add(new ExcelExportColumn("REPAIR_NO", "工单号"));
			exportColumnList.add(new ExcelExportColumn("ORDER_TYPE", "工单类型",ExcelDataType.Oem_Dict));
			exportColumnList.add(new ExcelExportColumn("IS_CLAIM", "是否索赔",ExcelDataType.Oem_Dict));
			exportColumnList.add(new ExcelExportColumn("是", "是否召回"));
			exportColumnList.add(new ExcelExportColumn("CREATE_DATE", "开单日期"));
			exportColumnList.add(new ExcelExportColumn("FINISH_DATE", "竣工日期"));
			exportColumnList.add(new ExcelExportColumn("REPAIR_DATE", "维修日期"));
			exportColumnList.add(new ExcelExportColumn("FINISH_DATE", "结算日期"));
			exportColumnList.add(new ExcelExportColumn("COST_DAYS", "维修占用时间/天"));
			exportColumnList.add(new ExcelExportColumn("REPAIR_ID", "更换零件"));
			exportColumnList.add(new ExcelExportColumn("REPAIR_ID", "维修项目"));
			excelService.generateExcel(excelData, exportColumnList, "三包预警车辆明细信息.xls", request, response);
		}
	  @SuppressWarnings("rawtypes")
		@RequestMapping(value = "/export/excelHis", method = RequestMethod.GET)
		public void exportHis(@RequestParam Map<String, String> queryParam, HttpServletRequest request,
				HttpServletResponse response) throws Exception {
			System.out.println("下载方法");
			List<Map> resultList = tservice.queryHis(queryParam);
			Map<String, List<Map>> excelData = new HashMap<String, List<Map>>();
			excelData.put("三包预警车辆维修历史", resultList);
			// 生成excel 文件
			List<ExcelExportColumn> exportColumnList = new ArrayList<ExcelExportColumn>();
			exportColumnList.add(new ExcelExportColumn("DEALER_CODE", "经销商代码"));
			exportColumnList.add(new ExcelExportColumn("DEALER_SHORTNAME", "经销商名称"));
			exportColumnList.add(new ExcelExportColumn("REPAIR_WORK_NO", "维修工单号"));
			exportColumnList.add(new ExcelExportColumn("REPAIR_TYPE", "维修类型"));
			exportColumnList.add(new ExcelExportColumn("VIN", "VIN"));
			exportColumnList.add(new ExcelExportColumn("MATERIAL_NAME", "车型"));
			exportColumnList.add(new ExcelExportColumn("MODEL_YEAR", "车款"));
			exportColumnList.add(new ExcelExportColumn("MILLEAGE", "里程数"));
			exportColumnList.add(new ExcelExportColumn("ENGINE_NO", "发动机号"));
			exportColumnList.add(new ExcelExportColumn("MAIN_PART", "主因零部件"));
			exportColumnList.add(new ExcelExportColumn("REPAIR_DATE_CHAR", "维修日期"));
			exportColumnList.add(new ExcelExportColumn("CUSTOMER_NAME", "客户"));
			excelService.generateExcel(excelData, exportColumnList, "三包预警车辆维修历史信息.xls", request, response);
		}
}

