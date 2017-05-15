
/** 
*Copyright 2017 Yonyou Corporation Ltd. All Rights Reserved.
* This software is published under the terms of the Yonyou Software
* License version 1.0, a copy of which has been included with this
* distribution in the LICENSE.txt file.
*
* @Project Name : dms.customer
*
* @File name : LossVehicleResultController.java
*
* @Author : sqh
*
* @Date : 2017年3月31日
*
----------------------------------------------------------------------------------
*     Date       Who       Version     Comments
* 1. 2017年3月31日    sqh    1.0
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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yonyou.dms.customer.service.vehicleLoss.LossVehicleResultService;
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
* @date 2017年3月31日
*/
@Controller
@TxnConn
@RequestMapping("/customer/lossVehicleResult")
public class LossVehicleResultController extends BaseController{
    @Autowired
    private LossVehicleResultService lossVehicleResultService;
    
	@Autowired
	private ExcelGenerator excelGenerator;
	
    /**
     * 
     * @param queryParam
     * @return
     */
    @RequestMapping(method = RequestMethod.GET)
    @ResponseBody
    public PageInfoDto queryLossVehicleResult(@RequestParam Map<String, String> queryParam) {
            return lossVehicleResultService.queryLossVehicleResult(queryParam);
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
		List<Map> resultList = lossVehicleResultService.exportLossVehicleResult(queryParam);
		Map<String, List<Map>> excelData = new HashMap<String, List<Map>>();
		excelData.put("流失报警回访结果表", resultList);
		List<ExcelExportColumn> exportColumnList = new ArrayList<ExcelExportColumn>();
		exportColumnList.add(new ExcelExportColumn("dealer_shortname", "经销商"));
		exportColumnList.add(new ExcelExportColumn("brand_name", "品牌"));
		exportColumnList.add(new ExcelExportColumn("series_name", "车系"));
		exportColumnList.add(new ExcelExportColumn("MODEL_NAME", "车型"));
		exportColumnList.add(new ExcelExportColumn("VIN", "VIN"));
		exportColumnList.add(new ExcelExportColumn("SALES_DATE", "销售日期"));
		exportColumnList.add(new ExcelExportColumn("DELIVERER", "送修人"));
		exportColumnList.add(new ExcelExportColumn("DELIVERER_GENDER", "送修人性别"));
		exportColumnList.add(new ExcelExportColumn("DELIVERER_PHONE", "送修人电话"));
		exportColumnList.add(new ExcelExportColumn("DELIVERER_MOBILE", "送修人手机"));
		exportColumnList.add(new ExcelExportColumn("WNER_NO", "车主编号"));
		exportColumnList.add(new ExcelExportColumn("LAST_MAINTAIN_DATE", "上次维修日期"));
		exportColumnList.add(new ExcelExportColumn("IS_SELF_COMPANY", "是否本公司购车", ExcelDataType.Dict));
		exportColumnList.add(new ExcelExportColumn("WNER_PROPERTY", "车主性质", ExcelDataType.Dict));
		exportColumnList.add(new ExcelExportColumn("WNER_NAME", "车主"));
		exportColumnList.add(new ExcelExportColumn("GENDER", "性别", ExcelDataType.Dict));
		exportColumnList.add(new ExcelExportColumn("PHONE", "车主电话"));
		exportColumnList.add(new ExcelExportColumn("MOBILE", "车主手机"));
		exportColumnList.add(new ExcelExportColumn("ADDRESS", "车主地址"));
		exportColumnList.add(new ExcelExportColumn("CONTACTOR_NAME", "联系人"));
		exportColumnList.add(new ExcelExportColumn("CONTACTOR_MOBILE", "联系人手机"));
		exportColumnList.add(new ExcelExportColumn("CONTACTOR_PHONE", "联系人电话"));
		exportColumnList.add(new ExcelExportColumn("INPUT_DATE", "跟踪日期"));
		exportColumnList.add(new ExcelExportColumn("inputerName", "录入人"));
		exportColumnList.add(new ExcelExportColumn("user_name", "跟踪人"));
		exportColumnList.add(new ExcelExportColumn("TRACE_STATUS", "跟踪状态", ExcelDataType.Dict));
		exportColumnList.add(new ExcelExportColumn("LICENSE", "车牌号"));
		exportColumnList.add(new ExcelExportColumn("INPUT_DATE_F", "第一次跟踪日期"));
		exportColumnList.add(new ExcelExportColumn("INPUT_DATE_S", "第二次跟踪日期"));
		exportColumnList.add(new ExcelExportColumn("INPUT_DATE_T", "第三次跟踪日期"));
		exportColumnList.add(new ExcelExportColumn("TASK_REMARK", "备注人"));
		excelGenerator.generateExcelForDms(excelData, exportColumnList,
				FrameworkUtil.getLoginInfo().getDealerShortName() + "_流失报警回访结果.xls", request, response);
	}
    
}