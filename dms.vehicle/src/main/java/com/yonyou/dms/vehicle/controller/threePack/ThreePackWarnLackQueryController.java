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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yonyou.dms.framework.DAO.DAOUtil;
import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.framework.service.excel.ExcelExportColumn;
import com.yonyou.dms.framework.service.excel.ExcelGenerator;
import com.yonyou.dms.vehicle.service.threePack.ThreePackWarnLackQueryService;
import com.yonyou.f4.mvc.annotation.TxnConn;
import com.yonyou.f4.mvc.controller.BaseController;

@Controller
@TxnConn
@RequestMapping("/lack")
public class ThreePackWarnLackQueryController  extends BaseController{
	private static final Logger logger = LoggerFactory.getLogger(DAOUtil.class);

	@Autowired
	ThreePackWarnLackQueryService tservice;
	
	@Autowired
	ExcelGenerator excelService;
	
	
    @SuppressWarnings("rawtypes")
	@RequestMapping(value = "/export/excel", method = RequestMethod.GET)
	public void exportCarownerInfo(@RequestParam Map<String, String> queryParam, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		System.out.println("下载方法");
		List<Map> resultList = tservice.queryEmpInfoforExport(queryParam);
		Map<String, List<Map>> excelData = new HashMap<String, List<Map>>();
		excelData.put("三包缺料配件查询", resultList);
		// 生成excel 文件
		List<ExcelExportColumn> exportColumnList = new ArrayList<ExcelExportColumn>();
		exportColumnList.add(new ExcelExportColumn("DEALER_CODE", "经销商代码"));
		exportColumnList.add(new ExcelExportColumn("DEALER_SHORTNAME", "经销商名称"));
		exportColumnList.add(new ExcelExportColumn("REPAIR_WORK_NO", "维修工单号"));
		exportColumnList.add(new ExcelExportColumn("VIN", "VIN"));
		exportColumnList.add(new ExcelExportColumn("START_ORDER_DATE", "开单日期"));
		exportColumnList.add(new ExcelExportColumn("LACK_MATE_CODE", "缺料配件代码"));
		exportColumnList.add(new ExcelExportColumn("LACK_MATE_NAME", "缺料配件名称"));
		exportColumnList.add(new ExcelExportColumn("LACK_MATE_AMOUNT", "缺料数量"));
		excelService.generateExcel(excelData, exportColumnList, "三包缺料配件查询.xls", request, response);

	}
    @RequestMapping(method = RequestMethod.GET)
    @ResponseBody
    public PageInfoDto queryDefeatReason(@RequestParam Map<String, String> queryParam) throws Exception {
    	logger.info("三包缺料配件查询");
        PageInfoDto pageInfoDto=tservice.getTmRetaillist(queryParam);
        return pageInfoDto;
    }
}
