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
import com.yonyou.dms.function.common.OemDictCodeConstants;
import com.yonyou.dms.vehicle.service.threePack.ThreePackWarnNotSettledQueryService;
import com.yonyou.f4.mvc.annotation.TxnConn;
import com.yonyou.f4.mvc.controller.BaseController;
@Controller
@TxnConn
@RequestMapping("/nosettled")
public class ThreePackWarnNotSettledQueryController  extends BaseController{
	private static final Logger logger = LoggerFactory.getLogger(DAOUtil.class);
	
	@Autowired
	ExcelGenerator excelService;
	@Autowired
	ThreePackWarnNotSettledQueryService tservice;
	

	 @RequestMapping(method = RequestMethod.GET)
	    @ResponseBody
	    public PageInfoDto queryDefeatReason(@RequestParam Map<String, String> queryParam) throws Exception {
	    	logger.info("查询超48小时未结算车辆信息");
	        PageInfoDto pageInfoDto=tservice.findthreePack(queryParam);
	        return pageInfoDto;
	    }

	  @RequestMapping(value="/info/{VIN}",method = RequestMethod.GET)
	    @ResponseBody
	    public Map<String,Object> queryInfo(@PathVariable(value = "VIN") String vin) throws Exception {
	    	logger.info("客户信息");
	    	Map<String,Object> pageInfoDto=new HashMap<>();
	    	pageInfoDto=tservice.queryList(vin);
	        return pageInfoDto;
	    }
	  @RequestMapping(value="/info/{REPAIR_ID}/{vin}",method = RequestMethod.GET)
	    @ResponseBody
	    public Map<String,Object> queryInfo(@PathVariable(value = "REPAIR_ID") Long id,@PathVariable(value = "vin") String vin) throws Exception {
	    	logger.info("历史客户信息");
	    	Map<String,Object> pageInfoDto=new HashMap<>();
	    	pageInfoDto=tservice.queryHisList(vin,id);
	        return pageInfoDto;
	    }
	  @RequestMapping(value="/his",method = RequestMethod.GET)
	    @ResponseBody
	    public PageInfoDto queryHis(@RequestParam Map<String, String> queryParam) throws Exception {
	    	logger.info("查询历史维修信息");
	        PageInfoDto pageInfoDto=tservice.findHis(queryParam);
	        return pageInfoDto;
	    }
	  @RequestMapping(value="/fbzc/{VIN}",method = RequestMethod.GET)
	    @ResponseBody
	    public PageInfoDto queryLabourList(@PathVariable(value = "VIN") String vin) throws Exception {
	    	logger.info(" 发动机和变速器总成监控查询");
	    	String itemNo=OemDictCodeConstants.WARN_ITEMNO_100;
	    	PageInfoDto pageInfoDto=tservice.queryLabourList(vin,itemNo);
	        return pageInfoDto;
	    }
	  @RequestMapping(value="/fbzy/{VIN}",method = RequestMethod.GET)
	    @ResponseBody
	    public PageInfoDto queryFbzyList(@PathVariable(value = "VIN") String vin) throws Exception {
	    	logger.info(" 发动机和变速器主要零件监控");
	    	String itemNo=OemDictCodeConstants.WARN_ITEMNO_200;
	    	PageInfoDto pageInfoDto=tservice.queryLabourList(vin,itemNo);
	        return pageInfoDto;
	    }
	  @RequestMapping(value="/dg/{REPAIR_ID}",method = RequestMethod.GET)
	    @ResponseBody
	    public PageInfoDto queryDG(@PathVariable(value = "REPAIR_ID") Long id) throws Exception {
	    	logger.info(" 待工");
	    	PageInfoDto pageInfoDto=tservice.findTtWrRepairNotSettleReasonByReasonId(id);
	        return pageInfoDto;
	    }
	  @RequestMapping(value="/dl/{REPAIR_ID}",method = RequestMethod.GET)
	    @ResponseBody
	    public PageInfoDto queryDL(@PathVariable(value = "REPAIR_ID") Long id) throws Exception {
	    	logger.info(" 待料");
	    	PageInfoDto pageInfoDto=tservice.findTtWrRepairNotSettleReasonByRepairId(id);
	        return pageInfoDto;
	    }
	  @RequestMapping(value="/qtzc/{VIN}",method = RequestMethod.GET)
	    @ResponseBody
	    public PageInfoDto queryQtzcList(@PathVariable(value = "VIN") String vin) throws Exception {
	    	logger.info(" 其它总成主要零件监控");
	    	String itemNo=OemDictCodeConstants.WARN_ITEMNO_300;
	    	PageInfoDto pageInfoDto=tservice.queryLabourList(vin,itemNo);
	        return pageInfoDto;
	    }
	  @RequestMapping(value="/tycp/{VIN}",method = RequestMethod.GET)
	    @ResponseBody
	    public PageInfoDto queryQtzyList(@PathVariable(value = "VIN") String vin) throws Exception {
	    	logger.info(" 同一产品质量问题监控");
	    	String itemNo=OemDictCodeConstants.WARN_ITEMNO_400;
	    	PageInfoDto pageInfoDto=tservice.queryLabourList(vin,itemNo);
	        return pageInfoDto;
	    }
	  @SuppressWarnings("rawtypes")
		@RequestMapping(value = "/export/excel", method = RequestMethod.GET)
		public void exportCarownerInfo(@RequestParam Map<String, String> queryParam, HttpServletRequest request,
				HttpServletResponse response) throws Exception {
			System.out.println("下载方法");
			List<Map> resultList = tservice.queryEmpInfoforExport(queryParam);
			Map<String, List<Map>> excelData = new HashMap<String, List<Map>>();
			excelData.put("超48小时未结算车辆", resultList);	excelData.put("超48小时未结算车辆", resultList);
			// 生成excel 文件
			List<ExcelExportColumn> exportColumnList = new ArrayList<ExcelExportColumn>();
			exportColumnList.add(new ExcelExportColumn("RO_NO", "工单号"));
			exportColumnList.add(new ExcelExportColumn("ORDER_TYPE", "工单类型",ExcelDataType.Oem_Dict));
			exportColumnList.add(new ExcelExportColumn("PURCHASE_DATE", "购车日期"));
			exportColumnList.add(new ExcelExportColumn("DEALER_CODE", "经销商代码"));
			exportColumnList.add(new ExcelExportColumn("DEALER_SHORTNAME", "经销商名称"));
			exportColumnList.add(new ExcelExportColumn("VIN", "VIN"));
			exportColumnList.add(new ExcelExportColumn("LICENSE_NO", "牌照号"));
			exportColumnList.add(new ExcelExportColumn("CTM_NAME", "车主"));
			exportColumnList.add(new ExcelExportColumn("COLOR", "预警级别",ExcelDataType.Oem_Dict));
			exportColumnList.add(new ExcelExportColumn("DEAYHOUR", "维修占用累计时间（天）"));
			exportColumnList.add(new ExcelExportColumn("VIN", "工时延迟预警颜色"));
			exportColumnList.add(new ExcelExportColumn("REASON", "未结算原因"));
			excelService.generateExcel(excelData, exportColumnList, "超48小时未结算车辆信息.xls", request, response);
		}
	  @SuppressWarnings("rawtypes")
		@RequestMapping(value = "/export/excelhis", method = RequestMethod.GET)
		public void exportHis(@RequestParam Map<String, String> queryParam, HttpServletRequest request,
				HttpServletResponse response) throws Exception {
			System.out.println("下载方法");
			List<Map> resultList = tservice.queryHis(queryParam);
			Map<String, List<Map>> excelData = new HashMap<String, List<Map>>();
			excelData.put("历史未结算车辆", resultList);
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
			excelService.generateExcel(excelData, exportColumnList, "历史未结算车辆信息.xls", request, response);
		}
	  @SuppressWarnings("rawtypes")
		@RequestMapping(value = "/export/excelno", method = RequestMethod.GET)
		public void threePackWarnInfo( HttpServletRequest request,
				HttpServletResponse response) throws Exception {
			System.out.println("下载方法");
			List<Map> resultList = tservice.threePackWarnNotSettleDownloadDownload();
			Map<String, List<Map>> excelData = new HashMap<String, List<Map>>();
			excelData.put("未结算原因", resultList);
			// 生成excel 文件
			List<ExcelExportColumn> exportColumnList = new ArrayList<ExcelExportColumn>();
			exportColumnList.add(new ExcelExportColumn("RO_NO", "工单号"));
			exportColumnList.add(new ExcelExportColumn("OPERATOR", "填写人"));
			exportColumnList.add(new ExcelExportColumn("VIN", "VIN"));
			exportColumnList.add(new ExcelExportColumn("WAIT_WORK_REASON", "待工原因"));
			exportColumnList.add(new ExcelExportColumn("WAIT_WORK_REMARK", "待工备注"));
			exportColumnList.add(new ExcelExportColumn("WAIT_MATERIAL_PART_QUANTITY", "待料数量"));
			exportColumnList.add(new ExcelExportColumn("WAIT_MATERIAL_PART_NAME", "待料名称"));
			exportColumnList.add(new ExcelExportColumn("WAIT_MATERIAL_PART_CODE", "待料代码"));
			exportColumnList.add(new ExcelExportColumn("WAIT_MATERIAL_ORDER_NO", "待料订单号"));
			exportColumnList.add(new ExcelExportColumn("WAIT_MATERIAL_ORDER_DATE", "待料订单日期"));
			exportColumnList.add(new ExcelExportColumn("WAIT_MATERIAL_REMARK", "待料备注"));
			exportColumnList.add(new ExcelExportColumn("CREATE_DATE", "创建时间"));
			excelService.generateExcel(excelData, exportColumnList, "未结算原因信息.xls", request, response);
		}
}

