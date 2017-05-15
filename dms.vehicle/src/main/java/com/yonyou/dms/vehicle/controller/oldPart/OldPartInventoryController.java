package com.yonyou.dms.vehicle.controller.oldPart;

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
import com.yonyou.dms.vehicle.service.oldPart.OldPartInventoryService;
import com.yonyou.f4.mvc.annotation.TxnConn;
import com.yonyou.f4.mvc.controller.BaseController;
@Controller
@TxnConn
@RequestMapping("/inventory")
public class OldPartInventoryController extends BaseController {
	private static final Logger logger = LoggerFactory.getLogger(DAOUtil.class);

	@Autowired
	OldPartInventoryService oservice;
	
	@Autowired
	ExcelGenerator excelService;
	
	@RequestMapping(method = RequestMethod.GET)
    @ResponseBody
    public PageInfoDto queryDefeatReason(@RequestParam Map<String, String> queryParam) throws Exception {
    	logger.info("旧件库存信息");
        PageInfoDto pageInfoDto=oservice.findOutStorage(queryParam);
        return pageInfoDto;
	}
	
	@RequestMapping(value = "/{OLDPART_NO}",method = RequestMethod.GET)
    @ResponseBody
    public PageInfoDto querybyId(@PathVariable(value = "OLDPART_NO")  String oldpartNo) throws Exception {
    	logger.info("旧件待出库信息");
        PageInfoDto pageInfoDto=oservice.findOne(oldpartNo);
        return pageInfoDto;
	}
	@RequestMapping(value = "/export/excel", method = RequestMethod.GET)
	public void exportCarownerInfo(@RequestParam Map<String, String> queryParam, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		System.out.println("下载方法");
		List<Map> resultList = oservice.queryEmpInfoforExport(queryParam);
		Map<String, List<Map>> excelData = new HashMap<String, List<Map>>();
		excelData.put("旧件库存信息管理", resultList);
		// 生成excel 文件
		List<ExcelExportColumn> exportColumnList = new ArrayList<ExcelExportColumn>();
		exportColumnList.add(new ExcelExportColumn("STOR_NAME", "旧件仓库"));
		exportColumnList.add(new ExcelExportColumn("OLDPART_NO", "旧件代码"));
		exportColumnList.add(new ExcelExportColumn("OLDPART_NAME", "旧件名称"));
		exportColumnList.add(new ExcelExportColumn("INVENTORY_COUNT", "旧件数量"));
		excelService.generateExcel(excelData, exportColumnList, "旧件库存信息管理.xls", request, response);
	}
	@RequestMapping(value = "/export/excel/{OLDPART_NO}", method = RequestMethod.GET)
	public void exportCarownerMX(@PathVariable(value = "OLDPART_NO")  String oldpartNo, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		System.out.println("明细下载方法");
		List<Map> resultList = oservice.queryEmpInfoforExportMX(oldpartNo);
		Map<String, List<Map>> excelData = new HashMap<String, List<Map>>();
		excelData.put("旧件库存信息管理", resultList);
		// 生成excel 文件
		List<ExcelExportColumn> exportColumnList = new ArrayList<ExcelExportColumn>();
		exportColumnList.add(new ExcelExportColumn("STOR_NAME", "旧件仓库"));
		exportColumnList.add(new ExcelExportColumn("DEALER_CODE", "经销商代码"));
		exportColumnList.add(new ExcelExportColumn("DEALER_NAME", "经销商名称"));
		exportColumnList.add(new ExcelExportColumn("RETURN_BILL_NO", "回运单号"));
		exportColumnList.add(new ExcelExportColumn("BALANCE_NO", "结算单号"));
		exportColumnList.add(new ExcelExportColumn("REPAIR_NO", "工单号"));
		exportColumnList.add(new ExcelExportColumn("CLAIM_NUMBER", "索赔单号"));
		exportColumnList.add(new ExcelExportColumn("VIN", "VIN"));
		exportColumnList.add(new ExcelExportColumn("OLDPART_NO", "旧件代码"));
		exportColumnList.add(new ExcelExportColumn("OLDPART_NAME", "旧件名称"));
		exportColumnList.add(new ExcelExportColumn("OLDPART_ORDER", "旧件序号"));
		exportColumnList.add(new ExcelExportColumn("PART_FEE", "含税索赔价"));
		exportColumnList.add(new ExcelExportColumn("BRAND_CODE", "品牌"));
		exportColumnList.add(new ExcelExportColumn("SERIES_NAME", "车系"));
		exportColumnList.add(new ExcelExportColumn("MODEL_NAME", "车型"));
		exportColumnList.add(new ExcelExportColumn("OLDPART_TYPE", "旧件类型",ExcelDataType.Oem_Dict));
		exportColumnList.add(new ExcelExportColumn("RETURN_BILL_TYPE", "回运类型",ExcelDataType.Oem_Dict));
		exportColumnList.add(new ExcelExportColumn("CLAIM_APPLY_DATE", "索赔申请日期"));
		exportColumnList.add(new ExcelExportColumn("CLAIM_AUDIT_DATE", "索赔申请通过日期"));
		exportColumnList.add(new ExcelExportColumn("MSV", "MSV码"));
		exportColumnList.add(new ExcelExportColumn("PURCHASE_DATE", "购车日期"));
		exportColumnList.add(new ExcelExportColumn("REPAIR_DATE", "维修日期"));
		exportColumnList.add(new ExcelExportColumn("REPAIR_TYPE", "维修类型",ExcelDataType.Oem_Dict));
		exportColumnList.add(new ExcelExportColumn("OUT_MILEAGE", "行驶里程"));
		exportColumnList.add(new ExcelExportColumn("REPAIR_REMARK", "维修备注"));
		exportColumnList.add(new ExcelExportColumn("STORE_STATUS", "库存状态",ExcelDataType.Oem_Dict));
		exportColumnList.add(new ExcelExportColumn("RECEIVE_DATE", "入库日期"));
		excelService.generateExcel(excelData, exportColumnList, "旧件库存明细信息管理.xls", request, response);
    
	}
}
