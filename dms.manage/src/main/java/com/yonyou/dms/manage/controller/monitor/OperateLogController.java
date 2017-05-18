
/** 
 *Copyright 2016 Yonyou Corporation Ltd. All Rights Reserved.
 * This software is published under the terms of the Yonyou Software
 * License version 1.0, a copy of which has been included with this
 * distribution in the LICENSE.txt file.
 *
 * @Project Name : dms.manage
 *
 * @File name : OperateLogController.java
 *
 * @Author : yll
 *
 * @Date : 2016年7月14日
 *
----------------------------------------------------------------------------------
 *     Date       Who       Version     Comments
 * 1. 2016年7月14日    yll    1.0
 *
 *
 *
 *
----------------------------------------------------------------------------------
 */

package com.yonyou.dms.manage.controller.monitor;

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

import com.yonyou.dms.common.service.monitor.OperateLogService;
import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.framework.service.excel.ExcelExportColumn;
import com.yonyou.dms.framework.service.excel.ExcelGenerator;
import com.yonyou.dms.framework.util.FrameworkUtil;
import com.yonyou.dms.function.exception.ServiceBizException;
import com.yonyou.f4.mvc.annotation.TxnConn;
import com.yonyou.f4.mvc.controller.BaseController;

/**
 * 操作日志的控制类
 * @author yll
 * @date 2016年7月14日
 */
@Controller
@TxnConn
@RequestMapping("/monitor")
public class OperateLogController extends BaseController{

	// 定义日志接口
	private static final Logger logger = LoggerFactory.getLogger(OperateLogController.class);
	@Autowired
	private OperateLogService operateLogService;

	 @Autowired
	  private ExcelGenerator excelService;//导出接口
	/**
	 * 
	 * 根据查询条件返回对应的操作日志数据
	 * @author yll
	 * @date 2016年7月14日
	 * @param queryParam
	 * @return
	 */
	@RequestMapping( value="/operatelog" ,method = RequestMethod.GET)
	@ResponseBody
	public PageInfoDto queryOperateLog(@RequestParam Map<String, String> queryParam) {
	    System.out.println(queryParam);
		PageInfoDto pageInfoDto = operateLogService.queryOperateLog(queryParam);
		return pageInfoDto;
	}
	 /**
     * 直接查出所有操作员
    * TODO description
    * @author yujiangheng
    * @date 2017年4月1日
    * @param 
    * @return
    * @throws ServiceBizException
     */
    @RequestMapping(value="/operatelog/Employee/Select",method = RequestMethod.GET)
    @ResponseBody
    public List<Map> getAllSelect() throws ServiceBizException{
       List<Map> all = operateLogService.getAllSelect();
        return all;
    }  
    
    /*
    * 查询导出excel
    * @author yujaingheng
    * @date 2017年3月29日
    */
   @RequestMapping(value="/operatelog/export",method = RequestMethod.GET)
   public void excel(@RequestParam Map<String, String> queryParam, HttpServletRequest request,
                     HttpServletResponse response){
       List<Map> resultList =operateLogService.queryToExport(queryParam);//查询出的数据
       Map<String, List<Map>> excelData = new HashMap<String, List<Map>>();  //导出数据
       excelData.put(FrameworkUtil.getLoginInfo().getDealerShortName()+"_工具定义", resultList);
       List<ExcelExportColumn> exportColumnList = new ArrayList<ExcelExportColumn>(); //设置导出格式
       exportColumnList.add(new ExcelExportColumn("OPERATE_TYPE_NAME","日志分类"));
       exportColumnList.add(new ExcelExportColumn("OPERATOR_NAME","操作员"));
       exportColumnList.add(new ExcelExportColumn("OPERATE_DATE","操作时间"));
       exportColumnList.add(new ExcelExportColumn("OPERATE_CONTENT","操作内容"));
       excelService.generateExcelForDms(excelData, exportColumnList, FrameworkUtil.getLoginInfo().getDealerShortName()+"_操作日志.xls", request, response);
   }
    
    
}
