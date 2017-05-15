package com.yonyou.dms.customer.controller.customerManage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yonyou.dms.customer.service.customerManage.IntentVehicleService;
import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.framework.service.excel.ExcelDataType;
import com.yonyou.dms.framework.service.excel.ExcelExportColumn;
import com.yonyou.dms.framework.service.excel.ExcelGenerator;
import com.yonyou.dms.framework.util.FrameworkUtil;
import com.yonyou.f4.mvc.annotation.TxnConn;
import com.yonyou.f4.mvc.controller.BaseController;

@Controller
@TxnConn
@RequestMapping("/customer/intentVehicle")
public class IntentVehicleContoller extends BaseController{
	
	@Autowired
    private IntentVehicleService intentVehicleService;
    @Autowired
    private ExcelGenerator  excelService;
	
	 /**
	    * 客户意向综合查询
	    * @author wangxin
	    * @date 2016年12月26日
	    * @param queryParam
	    * @return
	    */
	    	
	    @RequestMapping(method = RequestMethod.GET)
	    @ResponseBody
	    public PageInfoDto queryintentVehicle(@RequestParam Map<String, String> queryParam) {
	        PageInfoDto pageInfoDto = intentVehicleService.queryintentVehicle(queryParam);
	        return pageInfoDto;
	    }
	    
	    
	    /**
	     * 导出
	     * @param queryParam
	     * @param request
	     * @param response
	     * @throws Exception
	     */
	    
	    @RequestMapping(value = "/export/excel", method = RequestMethod.GET)
		public void exportCarownerInfo(@RequestParam Map<String, String> queryParam, HttpServletRequest request,
				HttpServletResponse response) {
			
			List<Map> resultList = intentVehicleService.queryintentVehiclerExport(queryParam);
			Map<String, List<Map>> excelData = new HashMap<String, List<Map>>();
			excelData.put(FrameworkUtil.getLoginInfo().getDealerShortName() + "_客户意向综合查询", resultList);		
			
			List<ExcelExportColumn> exportColumnList = new ArrayList<ExcelExportColumn>();
	        exportColumnList.add(new ExcelExportColumn("MODEL_NAME","意向车型")); 
	        exportColumnList.add(new ExcelExportColumn("CUSTOMER_TYPE","客户类型",ExcelDataType.Dict));
	        exportColumnList.add(new ExcelExportColumn("CUSTOMER_NAME","客户名称"));
	        exportColumnList.add(new ExcelExportColumn("CONTACTOR_PHONE","联系电话"));
	        exportColumnList.add(new ExcelExportColumn("CONTACTOR_MOBILE","联系手机"));
	        exportColumnList.add(new ExcelExportColumn("EMPLOYEE_NAME","销售顾问"));	  
	        exportColumnList.add(new ExcelExportColumn("INTENT_LEVEL","意向级别",ExcelDataType.Dict));
			excelService.generateExcelForDms(excelData, exportColumnList,
					FrameworkUtil.getLoginInfo().getDealerShortName() + "_客户意向综合查询.xls", request, response);
			
			
		}
	    
	    
	    
}
