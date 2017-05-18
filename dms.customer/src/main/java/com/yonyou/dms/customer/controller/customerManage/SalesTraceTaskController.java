package com.yonyou.dms.customer.controller.customerManage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.util.UriComponentsBuilder;

import com.yonyou.dms.common.domains.DTO.basedata.SalesTraceTaskDTO;
import com.yonyou.dms.customer.service.customerManage.SalesTraceTaskService;
import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.framework.service.excel.ExcelDataType;
import com.yonyou.dms.framework.service.excel.ExcelExportColumn;
import com.yonyou.dms.framework.service.excel.ExcelGenerator;
import com.yonyou.dms.framework.util.FrameworkUtil;
import com.yonyou.f4.mvc.annotation.TxnConn;
import com.yonyou.f4.mvc.controller.BaseController;

/**
 * 销售回访任务分配查询
 * @author Administrator
 *
 */
@Controller
@TxnConn
@RequestMapping("/customerManage/salesTraceTask")
public class SalesTraceTaskController extends BaseController{
	
	@Autowired
    private SalesTraceTaskService salestracetaskservice;
	
    @Autowired
    private ExcelGenerator excelService;
	
	/**
	 * 销售回访任务分配查询
	 * @param queryParam
	 * @return
	 */
	@RequestMapping(method = RequestMethod.GET)
    @ResponseBody
    public PageInfoDto querySalesTraceTask(@RequestParam Map<String, String> queryParam) {
        PageInfoDto pageInfoDto = salestracetaskservice.querySalesTraceTask(queryParam);
        return pageInfoDto;
    }
	
    @RequestMapping(value = "/trance/noList", method = RequestMethod.PUT)
    @ResponseStatus(HttpStatus.CREATED)
    public void modifyReRetainCusTrack(@RequestBody SalesTraceTaskDTO salesTraceTaskDTO,
                                           UriComponentsBuilder uriCB) {
    	salestracetaskservice.modifySoldBy(salesTraceTaskDTO);
    }
    
    /**
     * 销售回访任务分配生成excel
    * TODO 销售回访任务分配生成excel
    * @author wangxin
    * @date 2017年1月4日
    * @param queryParam
    * @param request
    * @param response
     */
    @RequestMapping(value="/export/excel",method = RequestMethod.GET)
    public void excel(@RequestParam Map<String, String> queryParam, HttpServletRequest request,
                      HttpServletResponse response){
        List<Map> resultList =salestracetaskservice.querySafeToExport(queryParam);
        Map<String, List<Map>> excelData = new HashMap<String, List<Map>>();
        excelData.put(FrameworkUtil.getLoginInfo().getDealerShortName()+"_销售回访任务分配", resultList);
        List<ExcelExportColumn> exportColumnList = new ArrayList<ExcelExportColumn>();
        exportColumnList.add(new ExcelExportColumn("FPCS","分配次数"));
        exportColumnList.add(new ExcelExportColumn("HFCS","回访次数"));
        exportColumnList.add(new ExcelExportColumn("IFDISTRIBUTION","是否分配",ExcelDataType.Dict));
        exportColumnList.add(new ExcelExportColumn("BRAND_NAME","品牌"));
        exportColumnList.add(new ExcelExportColumn("SERIES_NAME","车系"));
        exportColumnList.add(new ExcelExportColumn("MODEL_NAME","车型"));
        exportColumnList.add(new ExcelExportColumn("VIN","VIN"));
        exportColumnList.add(new ExcelExportColumn("STOCK_OUT_DATE","开票日期"));
        exportColumnList.add(new ExcelExportColumn("CUSTOMER_NO","客户编号"));
        exportColumnList.add(new ExcelExportColumn("CUSTOMER_NAME","客户名称"));
        exportColumnList.add(new ExcelExportColumn("GENDER","性别"));
        exportColumnList.add(new ExcelExportColumn("PROVINCE","省份",ExcelDataType.Region_Provice));
        exportColumnList.add(new ExcelExportColumn("CITY","城市",ExcelDataType.Region_City));
        exportColumnList.add(new ExcelExportColumn("DISTRICT","区县",ExcelDataType.Region_Country));
        exportColumnList.add(new ExcelExportColumn("ADDRESS","地址"));
        exportColumnList.add(new ExcelExportColumn("CONTACTOR_PHONE","联系人电话"));
        exportColumnList.add(new ExcelExportColumn("CONTACTOR_MOBILE","联系人手机"));
        exportColumnList.add(new ExcelExportColumn("CONFIRMED_DATE","交车日期"));
        excelService.generateExcelForDms(excelData, exportColumnList, FrameworkUtil.getLoginInfo().getDealerShortName()+"_销售回访任务分配.xls", request, response);
    }
	
}
