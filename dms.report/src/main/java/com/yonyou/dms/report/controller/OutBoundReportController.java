package com.yonyou.dms.report.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.util.UriComponentsBuilder;

import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.framework.service.excel.ExcelDataType;
import com.yonyou.dms.framework.service.excel.ExcelExportColumn;
import com.yonyou.dms.framework.service.excel.ExcelGenerator;
import com.yonyou.dms.framework.util.FrameworkUtil;
import com.yonyou.dms.report.domains.DTO.OutBoundReportDTO;
import com.yonyou.dms.report.service.impl.OutBoundReportService;
import com.yonyou.f4.mvc.annotation.TxnConn;
import com.yonyou.f4.mvc.controller.BaseController;

@Controller
@TxnConn
@SuppressWarnings("rawtypes")
@RequestMapping("/report/OutBoundReport")
public class OutBoundReportController extends BaseController{
    @Autowired
    private OutBoundReportService outBoundReportService; 
    @Autowired
    private ExcelGenerator excelService; 
    
    
    @RequestMapping(method=RequestMethod.GET)
    @ResponseBody
    public PageInfoDto searchOutBoundReport(@RequestParam Map<String, String> param){
        return  outBoundReportService.searchOutBoundReport(param);
    }
    
    @RequestMapping(value = "queryEntityNum" ,method=RequestMethod.GET)
    @ResponseBody
    public PageInfoDto queryEntityNum(@RequestParam Map<String, String> param){
        return  outBoundReportService.queryEntityNum(param);
    }
    
    @RequestMapping(value = "queryOutBundDetail/{soldBy}" ,method=RequestMethod.GET)
    @ResponseBody
    public PageInfoDto queryOutBundDetail(@PathVariable(value = "soldBy") String soldBy,@RequestParam Map<String, String> param){
        return  outBoundReportService.queryOutBundDetail(param,soldBy);
    }
    
    @RequestMapping(value = "/{vin}",method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> queryOutBundDetailbyVin(@PathVariable(value = "vin") String vin) {
        Map map = outBoundReportService.queryOutBundDetailbyVin(vin);
        return map; 
    }
    
    @RequestMapping(value="/updateCusInfo",method=RequestMethod.PUT)
    public ResponseEntity<Map<String, Object>> updateCusInfoById(@RequestBody OutBoundReportDTO outBoundReportDTO,
            UriComponentsBuilder uriCB ){
    	outBoundReportService.updateCusInfoById(outBoundReportDTO);
        MultiValueMap<String, String> headers = new HttpHeaders();
        headers.set("Location", uriCB.path("/updateCusInfo").buildAndExpand("").toUriString());
        return new ResponseEntity<Map<String, Object>>(headers, HttpStatus.CREATED);
    }
    
    @RequestMapping(value="/excel",method = RequestMethod.GET)
    public void excel(@RequestParam Map<String, String> queryParam, HttpServletRequest request,
                      HttpServletResponse response){
        List<Map> resultList =outBoundReportService.queryOutBundDetailExport(queryParam);
        Map<String, List<Map>> excelData = new HashMap<String, List<Map>>();
        excelData.put(FrameworkUtil.getLoginInfo().getDealerShortName()+"_新交车客户400核实月报", resultList);
        List<ExcelExportColumn> exportColumnList = new ArrayList<ExcelExportColumn>();
        exportColumnList.add(new ExcelExportColumn("VIN","VIN"));
        exportColumnList.add(new ExcelExportColumn("MODEL_NAME","车型"));
        exportColumnList.add(new ExcelExportColumn("COLOR_NAME","颜色"));
        exportColumnList.add(new ExcelExportColumn("CUSTOMER_NAME","客户姓名"));
        exportColumnList.add(new ExcelExportColumn("CONTACTOR_MOBILE","手机号码"));
        exportColumnList.add(new ExcelExportColumn("CONFIRMED_DATE","交车时间"));
        exportColumnList.add(new ExcelExportColumn("IS_BINDING","是否微信绑定",ExcelDataType.Dict));
        exportColumnList.add(new ExcelExportColumn("BINDING_DATE","微信绑定日期"));
        exportColumnList.add(new ExcelExportColumn("IS_SECOND_TIME","是否二次提交",ExcelDataType.Dict));
        exportColumnList.add(new ExcelExportColumn("OB_IS_SUCCESS","核实结果",ExcelDataType.Dict));
        exportColumnList.add(new ExcelExportColumn("REASONS","失败原因",ExcelDataType.Dict));
        exportColumnList.add(new ExcelExportColumn("IS_UPDATE","信息是否更新",ExcelDataType.Dict));
        exportColumnList.add(new ExcelExportColumn("OUTBOUND_UPLOAD_TIME","提交时间"));
        exportColumnList.add(new ExcelExportColumn("IS_OVERTIME","是否逾期",ExcelDataType.Dict));
        exportColumnList.add(new ExcelExportColumn("OUTBOUND_RETURN_TIME","核实反馈日期"));
        exportColumnList.add(new ExcelExportColumn("EMPLOYEE_NAME","销售顾问"));
        exportColumnList.add(new ExcelExportColumn("REMAIN_TIME","剩余补录时间"));
        
        excelService.generateExcel(excelData, exportColumnList, FrameworkUtil.getLoginInfo().getDealerShortName()+"_新交车客户400核实月报.xls", request, response);
    }
}
