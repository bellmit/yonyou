package com.yonyou.dms.vehicle.controller.realitySales.retailReportQuery;

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
import com.yonyou.dms.vehicle.service.realitySales.retailReportQuery.RetailReportQueryService;
import com.yonyou.f4.mvc.annotation.TxnConn;

/**
 * 零售上报查询Controller
 * @author DC
 *
 */
@Controller
@TxnConn
@RequestMapping("/retailReportQuery")
public class RetailReportQueryController {
	
	private static final Logger logger = LoggerFactory.getLogger(DAOUtil.class);
	
	@Autowired
	private RetailReportQueryService service;
	
	@Autowired
	private ExcelGenerator  excelService;
	
	@RequestMapping(method = RequestMethod.GET)
	@ResponseBody
	public PageInfoDto retailReportQuery(@RequestParam Map<String, String> queryParam){
		logger.info("============零售上报查询===============");
		/**  当前登录信息 **/
	    LoginInfoDto loginInfo = ApplicationContextHelper.getBeanByType(LoginInfoDto.class);
		PageInfoDto pageInfoDto = service.retailReportQuery(queryParam,loginInfo);
		return pageInfoDto;
	}
	
	/**
     * 根据ID 零售上报信息
     * @param id NVDR_ID
     * @return
     */
    @RequestMapping(value = "/detail/{id}", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> findRetailReportQueryDetail(@PathVariable(value = "id") Long nvdrId) {
    	LoginInfoDto loginInfo = ApplicationContextHelper.getBeanByType(LoginInfoDto.class);
    	logger.info("============根据ID 零售上报信息===============");
    	Map<String, Object> map = service.queryDetail(nvdrId,loginInfo);
        return map;
    }
	
	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/export/excel", method = RequestMethod.GET)
	@ResponseBody
	public void totalDownLoadList(@RequestParam Map<String, String> queryParam, HttpServletRequest request,
            HttpServletResponse response) throws Exception {
		logger.info("============零售上报查询信息下载===============");
		/**  当前登录信息 **/
	    LoginInfoDto loginInfo = ApplicationContextHelper.getBeanByType(LoginInfoDto.class);
		List<Map> resultList = service.retailReportQuerySuccList(queryParam,loginInfo);
		Map<String, List<Map>> excelData = new HashMap<>();
		excelData.put("零售上报查询信息", resultList);	
		
		List<ExcelExportColumn> exportColumnList = new ArrayList<>();
		exportColumnList.add(new ExcelExportColumn("BIG_AREA_NAME"					,"大区"));
		exportColumnList.add(new ExcelExportColumn("SMALL_AREA_NAME"					,"小区"));
		exportColumnList.add(new ExcelExportColumn("SWT_CODE"					,"SAP代码"));
		exportColumnList.add(new ExcelExportColumn("DEALER_CODE"					,"经销商代码"));
		exportColumnList.add(new ExcelExportColumn("DEALER_NAME"					,"经销商名称"));
		exportColumnList.add(new ExcelExportColumn("CTM_TYPE"					,"客户类型", ExcelDataType.Oem_Dict));
	    exportColumnList.add(new ExcelExportColumn("BRAND_CODE"				,"品牌"));
	    exportColumnList.add(new ExcelExportColumn("SERIES_NAME"			,"车系"));
	    exportColumnList.add(new ExcelExportColumn("MODEL_NAME"					,"车型"));
	    exportColumnList.add(new ExcelExportColumn("GROUP_NAME"				,"车款"));
	    exportColumnList.add(new ExcelExportColumn("MODEL_YEAR"				,"年款"));
	    exportColumnList.add(new ExcelExportColumn("COLOR_NAME"				,"颜色"));
	    exportColumnList.add(new ExcelExportColumn("TRIM_NAME"				,"内饰"));
	    exportColumnList.add(new ExcelExportColumn("VIN"					,"地盘号"));
	    exportColumnList.add(new ExcelExportColumn("REMARK","车辆分配备注", ExcelDataType.Oem_Dict));
	    exportColumnList.add(new ExcelExportColumn("VEHICLE_USAGE","车辆用途", ExcelDataType.Oem_Dict));
	    exportColumnList.add(new ExcelExportColumn("STATUS","交车上报状态", ExcelDataType.Oem_Dict));
	    exportColumnList.add(new ExcelExportColumn("INVOICE_DATE"				,"开票日期"));
	    exportColumnList.add(new ExcelExportColumn("NVDR_STATUS"				,"NVDR状态", ExcelDataType.Oem_Dict));
	    exportColumnList.add(new ExcelExportColumn("CREATE_DATE"			,"零售上报日期"));
	    exportColumnList.add(new ExcelExportColumn("NVDR_DATE"				,"零售上报审批日期"));
		
		excelService.generateExcel(excelData, exportColumnList, "零售上报查询信息.xls", request,response);
	}
	
	/**-------------------------------------经销商端功能代码-----------------------------------------------**/
	
	/**
	 * 零售上报查询(经销商端)
	 * @param queryParam
	 * @return
	 */
	@RequestMapping(value = "/dealer",method = RequestMethod.GET)
	@ResponseBody
	public PageInfoDto retailDealerReportQuery(@RequestParam Map<String, String> queryParam){
		logger.info("============零售上报查询===============");
		/**  当前登录信息 **/
	    LoginInfoDto loginInfo = ApplicationContextHelper.getBeanByType(LoginInfoDto.class);
		PageInfoDto pageInfoDto = service.retailDealerReportQuery(queryParam,loginInfo);
		return pageInfoDto;
	}
	
	/**
     * 根据ID 零售上报信息(经销商段)
     * @param id VIN
     * @return
     */
    @RequestMapping(value = "/dealer/detail/{id}", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> findRealitySalesQueryDetail(@PathVariable(value = "id") String id) {
    	LoginInfoDto loginInfo = ApplicationContextHelper.getBeanByType(LoginInfoDto.class);
    	logger.info("============根据ID 零售上报信息===============");
    	Map<String, Object> map = service.queryDealerDetail(id,loginInfo);
        return map;
    }
    
    @SuppressWarnings("rawtypes")
	@RequestMapping(value = "/dealer/export/excel", method = RequestMethod.GET)
	@ResponseBody
	public void totalDealerDownLoadList(@RequestParam Map<String, String> queryParam, HttpServletRequest request,
            HttpServletResponse response) throws Exception {
		logger.info("============零售上报查询信息下载===============");
		/**  当前登录信息 **/
	    LoginInfoDto loginInfo = ApplicationContextHelper.getBeanByType(LoginInfoDto.class);
		List<Map> resultList = service.retailDealerReportQuerySuccList(queryParam,loginInfo);
		Map<String, List<Map>> excelData = new HashMap<>();
		excelData.put("零售上报查询信息", resultList);	
		
		List<ExcelExportColumn> exportColumnList = new ArrayList<>();
	    exportColumnList.add(new ExcelExportColumn("DEALER_NAME"			,"经销商"));
	    exportColumnList.add(new ExcelExportColumn("CTM_NAME"				,"客户姓名/公司名称"));
	    exportColumnList.add(new ExcelExportColumn("MAIN_PHONE"				,"联系电话"));
	    exportColumnList.add(new ExcelExportColumn("CTM_TYPE"				,"客户类型", ExcelDataType.Oem_Dict));
	    exportColumnList.add(new ExcelExportColumn("CODE2"					,"车系"));
	    exportColumnList.add(new ExcelExportColumn("CODE3"					,"车型代码"));
	    exportColumnList.add(new ExcelExportColumn("MODEL_NAME"				,"车型描述"));
	    exportColumnList.add(new ExcelExportColumn("GROUP_NAME"				,"车款描述"));
	    exportColumnList.add(new ExcelExportColumn("COLOR_NAME"				,"颜色"));
	    exportColumnList.add(new ExcelExportColumn("VIN"					,"VIN"));
	    exportColumnList.add(new ExcelExportColumn("STATUS"					,"交车上报状态", ExcelDataType.Oem_Dict));
	    exportColumnList.add(new ExcelExportColumn("		  "				,"交车上报日期"));
	    exportColumnList.add(new ExcelExportColumn("CREATE_DATE"			,"零售上报日期"));

		excelService.generateExcel(excelData, exportColumnList, "零售上报查询信息.xls", request,response);
	}

}
