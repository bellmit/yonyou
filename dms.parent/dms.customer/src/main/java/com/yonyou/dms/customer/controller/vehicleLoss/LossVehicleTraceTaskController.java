
/** 
*Copyright 2017 Yonyou Corporation Ltd. All Rights Reserved.
* This software is published under the terms of the Yonyou Software
* License version 1.0, a copy of which has been included with this
* distribution in the LICENSE.txt file.
*
* @Project Name : dms.customer
*
* @File name : LossVehicleTraceTaskController.java
*
* @Author : sqh
*
* @Date : 2017年3月29日
*
----------------------------------------------------------------------------------
*     Date       Who       Version     Comments
* 1. 2017年3月29日    sqh    1.0
*
*
*
*
----------------------------------------------------------------------------------
*/
	
package com.yonyou.dms.customer.controller.vehicleLoss;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yonyou.dms.customer.service.vehicleLoss.LossVehicleTraceTaskService;
import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.framework.service.excel.ExcelDataType;
import com.yonyou.dms.framework.service.excel.ExcelExportColumn;
import com.yonyou.dms.framework.service.excel.ExcelGenerator;
import com.yonyou.dms.framework.util.FrameworkUtil;
import com.yonyou.dms.function.exception.ServiceBizException;
import com.yonyou.f4.mvc.annotation.TxnConn;
import com.yonyou.f4.mvc.controller.BaseController;

/**
* TODO description
* @author sqh
* @date 2017年3月29日
*/
@Controller
@TxnConn
@RequestMapping("/customer/lossVehicleTraceTask")
public class LossVehicleTraceTaskController extends BaseController{
    @Autowired
    private LossVehicleTraceTaskService lossVehicleTraceTaskService;
    
	@Autowired
	private ExcelGenerator excelGenerator;
	
    /**
     * 
     * @param queryParam
     * @return
     */
    @RequestMapping(method = RequestMethod.GET)
    @ResponseBody
    public PageInfoDto queryLossVehicleTraceTask(@RequestParam Map<String, String> queryParam) {
            return lossVehicleTraceTaskService.queryLossVehicleTraceTask(queryParam);
    }
    
    /**
     * 通过vin号查询流失报警录入回访
     * @param queryParam
     * @return
     */
    @RequestMapping(value = "/{vin}",method = RequestMethod.GET)
    @ResponseBody
    public Map queryLossVehicleTraceTaskByVin(@PathVariable(value = "vin") String vin) {
            return lossVehicleTraceTaskService.queryLossVehicleTraceTaskByVin(vin).get(0);
    }
    
	/**
	 * 导出流失报警录入回访
	 * 
	 * @author
	 * @param queryParam
	 * @param request
	 * @param response
	 * @throws Exception
	 */

	@RequestMapping(value = "/export", method = RequestMethod.GET)
	public void exportStreamAnalysis(@RequestParam Map<String, String> queryParam, HttpServletRequest request,
			HttpServletResponse response) throws ServiceBizException, SQLException {
		List<Map> resultList = lossVehicleTraceTaskService.exportLossVehicleTraceTask(queryParam);
		Map<String, List<Map>> excelData = new HashMap<String, List<Map>>();
		excelData.put("流失报警录入回访表", resultList);
		List<ExcelExportColumn> exportColumnList = new ArrayList<ExcelExportColumn>();
		exportColumnList.add(new ExcelExportColumn("dealer_shortname", "经销商"));
		exportColumnList.add(new ExcelExportColumn("LICENSE", "车牌号"));
		exportColumnList.add(new ExcelExportColumn("OWNER_NO", "车主编号"));
		exportColumnList.add(new ExcelExportColumn("OWNER_NAME", "车主名称"));
		exportColumnList.add(new ExcelExportColumn("GENDER", "车主性别", ExcelDataType.Dict));
		exportColumnList.add(new ExcelExportColumn("DELIVERER", "联系人"));
		exportColumnList.add(new ExcelExportColumn("DELIVERER_PHONE", "联系人电话"));
		exportColumnList.add(new ExcelExportColumn("DELIVERER_MOBILE", "联系人手机"));
		exportColumnList.add(new ExcelExportColumn("VIN", "VIN"));
		exportColumnList.add(new ExcelExportColumn("brand_name", "品牌"));
		exportColumnList.add(new ExcelExportColumn("series_name", "车系"));
		exportColumnList.add(new ExcelExportColumn("MODEL_NAME", "车型"));
		exportColumnList.add(new ExcelExportColumn("PHONE", "车主电话"));
		exportColumnList.add(new ExcelExportColumn("MOBILE", "车主手机"));
		exportColumnList.add(new ExcelExportColumn("E_MAIL", "E_MAIL"));
		exportColumnList.add(new ExcelExportColumn("DELIVERER_ADDRESS", "联系人地址"));
		exportColumnList.add(new ExcelExportColumn("ADDRESS", "车主地址"));
		exportColumnList.add(new ExcelExportColumn("user_name", "跟踪员"));
		exportColumnList.add(new ExcelExportColumn("TRACE_TAG", "结束跟踪", ExcelDataType.Dict));
		exportColumnList.add(new ExcelExportColumn("TRACE_STATUS", "跟踪状态", ExcelDataType.Dict));
		exportColumnList.add(new ExcelExportColumn("TASK_REMARK", "备注"));
		exportColumnList.add(new ExcelExportColumn("INPUT_DATE", "上次录入日期"));
		exportColumnList.add(new ExcelExportColumn("SALES_DATE", "销售日期"));
		exportColumnList.add(new ExcelExportColumn("IS_RETURN_FACTORY", "是否回厂", ExcelDataType.Dict));
		excelGenerator.generateExcelForDms(excelData, exportColumnList,
				FrameworkUtil.getLoginInfo().getDealerShortName() + "_流失报警录入回访.xls", request, response);
	}
	
	/**
	 * 查询DCRC回访信息
	 * 
	 * @param queryParam
	 * @return
	 * @throws ServiceBizException
	 * @throws SQLException
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/sales", method = RequestMethod.GET)
	@ResponseBody
	public List<Map> querySales(@RequestParam Map<String, String> queryParam) throws ServiceBizException, SQLException {
		return lossVehicleTraceTaskService.querySales(queryParam);

	}
	
	/**
	 * 查询销售回访信息
	 * 
	 * @param queryParam
	 * @return
	 * @throws ServiceBizException
	 * @throws SQLException
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping(value = "/trackTask", method = RequestMethod.GET)
	@ResponseBody
	public List<Map> querySalesOrderTrackTask(@RequestParam Map<String, String> queryParam) throws ServiceBizException, SQLException {
		List<Map> list = lossVehicleTraceTaskService.queryLossVehicleTraceTask1(queryParam);
		return lossVehicleTraceTaskService.querySalesOrderTrackTask(list.get(0));

	}
	
	/**
	 * 导出销售回访历史记录
	 * 
	 * @author
	 * @param queryParam
	 * @param request
	 * @param response
	 * @throws Exception
	 */

	@RequestMapping(value = "/exportSales", method = RequestMethod.GET)
	public void exportSales(@RequestParam Map<String, String> queryParam, HttpServletRequest request,
			HttpServletResponse response) throws ServiceBizException, SQLException {
		List<Map> list = lossVehicleTraceTaskService.queryLossVehicleTraceTask1(queryParam);
		List<Map> resultList = lossVehicleTraceTaskService.querySalesOrderTrackTask(list.get(0));
		Map<String, List<Map>> excelData = new HashMap<String, List<Map>>();
		excelData.put("销售回访历史表", resultList);
		List<ExcelExportColumn> exportColumnList = new ArrayList<ExcelExportColumn>();
		exportColumnList.add(new ExcelExportColumn("IS_SELECTED", "结束跟踪", ExcelDataType.Dict));
		exportColumnList.add(new ExcelExportColumn("CUSTOMER_NO", "客户代码"));
		exportColumnList.add(new ExcelExportColumn("OWNER_NO", "车主编号"));
		exportColumnList.add(new ExcelExportColumn("CUSTOMER_NAME", "客户名称"));
		exportColumnList.add(new ExcelExportColumn("SOLD_BY", "销售顾问"));
		exportColumnList.add(new ExcelExportColumn("ORG_NAME", "组别"));
		exportColumnList.add(new ExcelExportColumn("VIN", "VIN"));
		exportColumnList.add(new ExcelExportColumn("brand_name", "品牌"));
		exportColumnList.add(new ExcelExportColumn("series_name", "车系"));
		exportColumnList.add(new ExcelExportColumn("MODEL_NAME", "车型"));
		exportColumnList.add(new ExcelExportColumn("STOCK_OUT_DATE", "开票日期"));
		exportColumnList.add(new ExcelExportColumn("GENDER", "性别", ExcelDataType.Dict));
		exportColumnList.add(new ExcelExportColumn("ADDRESS", "地址"));
		exportColumnList.add(new ExcelExportColumn("CONTACTOR_PHONE", "联系人电话"));
		exportColumnList.add(new ExcelExportColumn("CONTACTOR_MOBILE", "联系人手机"));
		exportColumnList.add(new ExcelExportColumn("PROVINCE", "省份", ExcelDataType.Dict));
		exportColumnList.add(new ExcelExportColumn("CITY", "城市"));
		exportColumnList.add(new ExcelExportColumn("DISTRICT", "区县"));
		exportColumnList.add(new ExcelExportColumn("QUESTIONNAIRE_NAME", "问卷名称"));
		exportColumnList.add(new ExcelExportColumn("TRANCER", "跟踪人"));
		exportColumnList.add(new ExcelExportColumn("INPUT_DATE", "跟踪日期"));
		exportColumnList.add(new ExcelExportColumn("TRACE_STATUS", "跟踪状态", ExcelDataType.Dict));
		exportColumnList.add(new ExcelExportColumn("E_MAIL", "E_MAIL"));
		exportColumnList.add(new ExcelExportColumn("CUSTOMER_TYPE", "客户类型", ExcelDataType.Dict));
		exportColumnList.add(new ExcelExportColumn("OWNER_MARRIAGE", "婚姻状况, ExcelDataType.Dict"));
		exportColumnList.add(new ExcelExportColumn("AGE_STAGE", "年龄段", ExcelDataType.Dict));
		exportColumnList.add(new ExcelExportColumn("EDUCATION_LEVEL", "教育水平", ExcelDataType.Dict));
		exportColumnList.add(new ExcelExportColumn("INDUSTRY_FIRST", "行业大类", ExcelDataType.Dict));
		exportColumnList.add(new ExcelExportColumn("INDUSTRY_SECOND", "行业小类", ExcelDataType.Dict));
		exportColumnList.add(new ExcelExportColumn("VOCATION_TYPE", "职业", ExcelDataType.Dict));
		exportColumnList.add(new ExcelExportColumn("POSITION_NAME", "职务名称"));
		exportColumnList.add(new ExcelExportColumn("FAMILY_INCOME", "家庭月收入", ExcelDataType.Dict));
		exportColumnList.add(new ExcelExportColumn("HOBBY", "爱好", ExcelDataType.Dict));
		exportColumnList.add(new ExcelExportColumn("CONFIRMED_DATE", "交车日期"));
		exportColumnList.add(new ExcelExportColumn("INDUSTRY_SECOND", "客户来源", ExcelDataType.Dict));
		exportColumnList.add(new ExcelExportColumn("TASK_REMARK", "备注"));
		excelGenerator.generateExcelForDms(excelData, exportColumnList,
				FrameworkUtil.getLoginInfo().getDealerShortName() + "_销售回访历史.xls", request, response);
	}
	
	/**
	 * 导出销售回访DCRC回访信息
	 * 
	 * @author
	 * @param queryParam
	 * @param request
	 * @param response
	 * @throws Exception
	 */

	@RequestMapping(value = "/exportTrackTask", method = RequestMethod.GET)
	public void exportTrackTask(@RequestParam Map<String, String> queryParam, HttpServletRequest request,
			HttpServletResponse response) throws ServiceBizException, SQLException {
		List<Map> resultList = lossVehicleTraceTaskService.querySales(queryParam);
		Map<String, List<Map>> excelData = new HashMap<String, List<Map>>();
		excelData.put("销售回访DCRC回访信息表", resultList);
		List<ExcelExportColumn> exportColumnList = new ArrayList<ExcelExportColumn>();
		exportColumnList.add(new ExcelExportColumn("REMIND_ID", "回访编号"));
		exportColumnList.add(new ExcelExportColumn("description", "回访类型"));
		exportColumnList.add(new ExcelExportColumn("OWNER_NO", "车主/客户编号"));
		exportColumnList.add(new ExcelExportColumn("OWNER_NAME", "车主/客户姓名"));
		exportColumnList.add(new ExcelExportColumn("REMIND_DATE", "回访日期"));
		exportColumnList.add(new ExcelExportColumn("REMIND_CONTENT", "回访内容"));
		exportColumnList.add(new ExcelExportColumn("REMINDER", "回访人"));
		exportColumnList.add(new ExcelExportColumn("CUSTOMER_FEEDBACK", "反馈信息"));
		exportColumnList.add(new ExcelExportColumn("brand_name", "品牌"));
		exportColumnList.add(new ExcelExportColumn("series_name", "车系"));
		exportColumnList.add(new ExcelExportColumn("MODEL_NAME", "车型"));
		exportColumnList.add(new ExcelExportColumn("VIN", "VIN"));
		exportColumnList.add(new ExcelExportColumn("MOBILE", "联系人手机"));
		exportColumnList.add(new ExcelExportColumn("PHONE", "联系人电话"));
		excelGenerator.generateExcelForDms(excelData, exportColumnList,
				FrameworkUtil.getLoginInfo().getDealerShortName() + "_销售回访DCRC回访信息.xls", request, response);
	}
	
    /**
     * 查询流失修后跟踪记录
     * @param queryParam
     * @return
     */
    @RequestMapping(value = "/taskLog",method = RequestMethod.GET)
    @ResponseBody
    public PageInfoDto QueryLossAlarmTraceTaskLog(@RequestParam Map<String, String> queryParam) {
            return lossVehicleTraceTaskService.QueryLossAlarmTraceTaskLog(queryParam);
    }
}
