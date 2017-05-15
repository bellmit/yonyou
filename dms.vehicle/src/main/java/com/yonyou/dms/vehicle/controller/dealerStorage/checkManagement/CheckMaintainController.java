package com.yonyou.dms.vehicle.controller.dealerStorage.checkManagement;


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
import com.yonyou.dms.vehicle.service.dealerStorage.checkMaintain.CheckMaintainService;
import com.yonyou.f4.mvc.annotation.TxnConn;

/**
 * 车辆验收查询controller
 * @author DC
 *
 */
@SuppressWarnings("rawtypes")
@Controller
@TxnConn
@RequestMapping("/checkMaintain")
public class CheckMaintainController {

	private static final Logger logger = LoggerFactory.getLogger(DAOUtil.class);
	
	@Autowired
	private CheckMaintainService service;
	
	@Autowired
	private ExcelGenerator  excelService;
	
	/**
	 * 车辆详细验收查询(车厂端)
	 * @author DC
	 * @date 2017年2月10日
	 * @param queryParam
	 * @return
	 */
	@RequestMapping(method = RequestMethod.GET)
	@ResponseBody
	public PageInfoDto queryList(@RequestParam Map<String, String> queryParam) {
		logger.info("============车辆详细验收查询===============");
		/** 当前登录信息 */
	    LoginInfoDto loginInfo = ApplicationContextHelper.getBeanByType(LoginInfoDto.class);
		PageInfoDto pageInfoDto = service.queryList(queryParam,loginInfo);
		return pageInfoDto;
	}
	
	
	/**
     * 根据ID 获取车辆验收详细信息(车厂端)
     * @param id ORDER_NO
     * @return
     */

    @RequestMapping(value = "/detail/{id}/{inspectionId}", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> findCheckMaintainDetail(@PathVariable(value = "id") String id,@PathVariable(value = "inspectionId") Long inspectionId) {
    	/**当前登录信息**/
    	LoginInfoDto loginInfo = ApplicationContextHelper.getBeanByType(LoginInfoDto.class);
    	logger.info("============根据ID获取车辆验收详细信息 ===============");
    	Map<String, Object> map = service.queryDetail(id,loginInfo,inspectionId);
        return map;
    }
    
    /**
     * 根据ID 获取车辆验收详细信息的质损信息
     * @param id INSPECTION_ID
     * @return
     */

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @ResponseBody
    public PageInfoDto findCheckMaintainDetail2(@PathVariable(value = "id") Long id) {
    	logger.info("============根据ID获取车辆验收详细质损信息 ===============");
    	PageInfoDto map = service.queryDealerDetail3(id);
        return map;
    }
	
	/**
	 * 车辆验收信息下载(车厂的端)
	 * @author DC
	 * @date 2017年2月14日
	 * @param queryParam
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(value = "/export/excel", method = RequestMethod.GET)
	@ResponseBody
	public void downLoadList(@RequestParam Map<String, String> queryParam, HttpServletRequest request,
            HttpServletResponse response) throws Exception {
		logger.info("============车辆验收信息下载===============");
		/**  当前登录信息 */
	    LoginInfoDto loginInfo = ApplicationContextHelper.getBeanByType(LoginInfoDto.class);
		List<Map> resultList = service.findVehicleCheckSuccList(queryParam,loginInfo);
		Map<String, List<Map>> excelData = new HashMap<>();
		excelData.put("车辆验收信息", resultList);	
		
		List<ExcelExportColumn> exportColumnList = new ArrayList<>();
		exportColumnList.add(new ExcelExportColumn("DEALER_CODE","经销商代码"));
	    exportColumnList.add(new ExcelExportColumn("DEALER_SHORTNAME","经销商名称"));
	    exportColumnList.add(new ExcelExportColumn("ORDER_NO","订单号"));
	    exportColumnList.add(new ExcelExportColumn("BRAND_NAME","品牌"));
	    exportColumnList.add(new ExcelExportColumn("SERIES_NAME","车系"));
	    exportColumnList.add(new ExcelExportColumn("MODEL_CODE","CPOS"));
	    exportColumnList.add(new ExcelExportColumn("GROUP_NAME","车款"));
	    exportColumnList.add(new ExcelExportColumn("MODEL_YEAR","年款"));
	    exportColumnList.add(new ExcelExportColumn("COLOR_NAME","颜色"));
	    exportColumnList.add(new ExcelExportColumn("TRIM_NAME","内饰"));
	    exportColumnList.add(new ExcelExportColumn("VIN","VIN"));
	    exportColumnList.add(new ExcelExportColumn("DAMAGE_FLAG","是否质损",ExcelDataType.Oem_Dict));
	    exportColumnList.add(new ExcelExportColumn("ARRIVE_DATE","发车日期"));
	    exportColumnList.add(new ExcelExportColumn("ACTUAL_DATE","验收日期"));
	    exportColumnList.add(new ExcelExportColumn("INSPECTION_PERSON","验收人"));

		excelService.generateExcel(excelData, exportColumnList, "车辆验收信息.xls", request,response);
	}
	
	
	
	/**---------------------------------------------------经销商端功能代码---------------------------------------------------------------------**/
	
	
	
	/**
	 * 车辆详细验收(经销商端)
	 * @author DC
	 * @date 2017年2月10日
	 * @param queryParam
	 * @return
	 */
	@RequestMapping(value = "/dealer", method = RequestMethod.GET)
	@ResponseBody
	public PageInfoDto queryDealerList(@RequestParam Map<String, String> queryParam) {
		logger.info("============车辆详细验收查询===============");
		/** 当前登录信息  */
	    LoginInfoDto loginInfo = ApplicationContextHelper.getBeanByType(LoginInfoDto.class);
		PageInfoDto pageInfoDto = service.queryDealerList(queryParam,loginInfo);
		return pageInfoDto;
	}
	
	/**
     * 根据ID 获取车辆验收详细信息(经销商端)
     * @param id INSPECTION_ID
     * @return
     */

    @RequestMapping(value = "/dealer/detail/{id}", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> findDealerCheckMaintainDetail(@PathVariable(value = "id") Long id) {
    	/**当前登录信息**/
    	LoginInfoDto loginInfo = ApplicationContextHelper.getBeanByType(LoginInfoDto.class);
    	logger.info("============根据ID获取车辆验收详细信息 ===============");
    	Map<String, Object> map = service.queryDealerDetail(id,loginInfo);
        return map;
    }
	
	/**
	 * 车辆验收信息下载
	 * @author DC
	 * @date 2017年2月14日
	 * @param queryParam
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(value = "/dealer/export/excel", method = RequestMethod.GET)
	@ResponseBody
	public void dealerVehicleCheckSuccDownLoad(@RequestParam Map<String, String> queryParam, HttpServletRequest request,
            HttpServletResponse response) throws Exception {
		logger.info("============经销商端车辆验收信息下载===============");
		/**  当前登录信息 */
	    LoginInfoDto loginInfo = ApplicationContextHelper.getBeanByType(LoginInfoDto.class);
		List<Map> resultList = service.findDealerVehicleCheckSuccList(queryParam,loginInfo);
		Map<String, List<Map>> excelData = new HashMap<>();
		excelData.put("车辆验收信息", resultList);	
		
		List<ExcelExportColumn> exportColumnList = new ArrayList<>();
	    exportColumnList.add(new ExcelExportColumn("ORDER_NO","订单号"));
	    exportColumnList.add(new ExcelExportColumn("BRAND_NAME","品牌"));
	    exportColumnList.add(new ExcelExportColumn("SERIES_NAME","车系"));
	    exportColumnList.add(new ExcelExportColumn("MODEL_CODE","CPOS"));
	    exportColumnList.add(new ExcelExportColumn("GROUP_NAME","车款"));
	    exportColumnList.add(new ExcelExportColumn("MODEL_YEAR","年款"));
	    exportColumnList.add(new ExcelExportColumn("COLOR_NAME","颜色"));
	    exportColumnList.add(new ExcelExportColumn("TRIM_NAME","内饰"));
	    exportColumnList.add(new ExcelExportColumn("VIN","VIN"));
	    exportColumnList.add(new ExcelExportColumn("DAMAGE_FLAG","是否质损",ExcelDataType.Oem_Dict));
	    exportColumnList.add(new ExcelExportColumn("ARRIVE_DATE","发车日期"));
	    exportColumnList.add(new ExcelExportColumn("ACTUAL_DATE","验收日期"));
	    exportColumnList.add(new ExcelExportColumn("INSPECTION_PERSON","验收人"));
	    

		excelService.generateExcel(excelData, exportColumnList, "车辆验收信息.xls", request,response);
	}
	
	/**
     * 根据ID 获取车辆验收详细信息的质损信息(经销商端)
     * @param id INSPECTION_ID
     * @return
     */

    @RequestMapping(value = "/dealer/{id}", method = RequestMethod.GET)
    @ResponseBody
    public PageInfoDto findDealerCheckMaintainDetail2(@PathVariable(value = "id") Long id) {
    	logger.info("============根据ID获取车辆验收详细质损信息 ===============");
    	PageInfoDto map = service.queryDealerDetail2(id);
        return map;
    }
	
}
