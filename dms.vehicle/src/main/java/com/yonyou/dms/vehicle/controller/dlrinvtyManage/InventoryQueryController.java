package com.yonyou.dms.vehicle.controller.dlrinvtyManage;

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
import com.yonyou.dms.vehicle.service.dlrinvtyManage.InventoryQueryService;
import com.yonyou.f4.mvc.annotation.TxnConn;

/**
 * 经销商库存查询 Controller
 * @author DC
 *
 */
@SuppressWarnings("rawtypes")
@Controller
@TxnConn
@RequestMapping("/inventoryQuery")
public class InventoryQueryController {

	private static final Logger logger = LoggerFactory.getLogger(DAOUtil.class);
	
	@Autowired
	private InventoryQueryService service;
	
	@Autowired
	private ExcelGenerator  excelService;
	
	/**
	 * 经销商库存汇总查询(oem)
	 * @param queryParam
	 * @return
	 */
	@RequestMapping(value = "/groupQuery", method = RequestMethod.GET)
	@ResponseBody
	public PageInfoDto oemInventoryTotalQuery(@RequestParam Map<String, String> queryParam){
		logger.info("============经销商库存汇总查询===============");
		/**  当前登录信息 **/
	    LoginInfoDto loginInfo = ApplicationContextHelper.getBeanByType(LoginInfoDto.class);
	    PageInfoDto resultList = service.oemInventoryTotalQuery(queryParam,loginInfo);
		return resultList;
	}
	
	/**
	 * 经销商库存明细查询(oem)
	 */
	@RequestMapping(value = "/detailQuery", method = RequestMethod.GET)
	@ResponseBody
	public PageInfoDto oemInventoryQuery(@RequestParam Map<String, String> queryParam){
		logger.info("============经销商库存明细查询===============");
		/**  当前登录信息 **/
	    LoginInfoDto loginInfo = ApplicationContextHelper.getBeanByType(LoginInfoDto.class);
	    PageInfoDto pageInfoDto = service.oemInventoryQuery(queryParam,loginInfo);
		return pageInfoDto;
	}
	
	/**
	 * 经销商库存汇总查询信息下载
	 * @author DC
	 * @param queryParam
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(value = "/export/excel/group", method = RequestMethod.GET)
	public void inventoryDownLoadList(@RequestParam Map<String, String> queryParam, HttpServletRequest request,
            HttpServletResponse response) throws Exception {
		logger.info("============经销商库存汇总查询信息下载===============");
		/**  当前登录信息 **/
	    LoginInfoDto loginInfo = ApplicationContextHelper.getBeanByType(LoginInfoDto.class);
		List<Map> resultList = service.findTotalQuerySuccList(queryParam,loginInfo);
		Map<String, List<Map>> excelData = new HashMap<>();
		excelData.put("经销商库存汇总查询信息", resultList);	
		
		List<ExcelExportColumn> exportColumnList = new ArrayList<>();
		exportColumnList.add(new ExcelExportColumn("DEALER_CODE","经销商代码"));
	    exportColumnList.add(new ExcelExportColumn("DEALER_NAME","经销商名称"));
	    exportColumnList.add(new ExcelExportColumn("VIN","库存数量"));

		excelService.generateExcel(excelData, exportColumnList, "经销商库存汇总查询信息.xls", request,response);
	}
	
	/**
	 * 经销商库存明细查询信息下载
	 * @author DC
	 * @param queryParam
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(value = "/export/excel/detail", method = RequestMethod.GET)
	public void totalDownLoadList(@RequestParam Map<String, String> queryParam, HttpServletRequest request,
            HttpServletResponse response) throws Exception {
		logger.info("============经销商库存明细查询信息下载===============");
		/**  当前登录信息 **/
	    LoginInfoDto loginInfo = ApplicationContextHelper.getBeanByType(LoginInfoDto.class);
		List<Map> resultList = service.findInventoryQuerySuccList(queryParam,loginInfo);
		Map<String, List<Map>> excelData = new HashMap<>();
		excelData.put("经销商库存明细查询信息", resultList);	
		
		List<ExcelExportColumn> exportColumnList = new ArrayList<>();
		exportColumnList.add(new ExcelExportColumn("DEALER_CODE","经销商代码"));
	    exportColumnList.add(new ExcelExportColumn("DEALER_SHORTNAME","经销商名称"));
	    exportColumnList.add(new ExcelExportColumn("BRAND_NAME","品牌"));
	    exportColumnList.add(new ExcelExportColumn("SERIES_NAME","车系"));
	    exportColumnList.add(new ExcelExportColumn("MODEL_CODE","CPOS"));
	    exportColumnList.add(new ExcelExportColumn("GROUP_NAME","车款"));
	    exportColumnList.add(new ExcelExportColumn("MODEL_YEAR","年款"));
	    exportColumnList.add(new ExcelExportColumn("COLOR_NAME","颜色"));
	    exportColumnList.add(new ExcelExportColumn("TRIM_NAME","内饰"));
	    exportColumnList.add(new ExcelExportColumn("VIN","VIN"));
	    exportColumnList.add(new ExcelExportColumn("NODE_STATUS","车辆节点状态",ExcelDataType.Oem_Dict));
	    exportColumnList.add(new ExcelExportColumn("LIFE_CYCLE","车辆库存状态",ExcelDataType.Oem_Dict));
	    exportColumnList.add(new ExcelExportColumn("YOUI_DATE","开票日期"));
	    exportColumnList.add(new ExcelExportColumn("ZVHC_DATE","发运日期"));
	    exportColumnList.add(new ExcelExportColumn("ZPD2_DATE","到店日期"));
	    exportColumnList.add(new ExcelExportColumn("DEALER_STORAGE_DATE","验收日期"));
	    exportColumnList.add(new ExcelExportColumn("CREATE_DATE","零售上报日期"));
	    exportColumnList.add(new ExcelExportColumn("CREATE_DATE","零售上报审核日期"));
	    exportColumnList.add(new ExcelExportColumn("NVDR","是否交车"));

		excelService.generateExcel(excelData, exportColumnList, "经销商库存明细查询信息.xls", request,response);
	}
}
