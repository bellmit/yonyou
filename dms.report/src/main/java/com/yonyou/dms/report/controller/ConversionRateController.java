package com.yonyou.dms.report.controller;

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

import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.framework.service.excel.ExcelExportColumn;
import com.yonyou.dms.framework.service.excel.ExcelGenerator;
import com.yonyou.dms.report.service.impl.ConversionRateService;
import com.yonyou.f4.mvc.annotation.TxnConn;
import com.yonyou.f4.mvc.controller.BaseController;

/**
 * *转化率报表
 * @author Benzc
 * @date 2017年1月18日
 */
@Controller
@TxnConn
@RequestMapping("/basedata/conversionRate")
public class ConversionRateController extends BaseController{
	
	@Autowired
    private ConversionRateService conversionrateservice;
	
	@Autowired
    private ExcelGenerator excelService;
	
	@RequestMapping(value = "/rateSearch", method = RequestMethod.GET)
    @ResponseBody
    public PageInfoDto queryVehicleSales(@RequestParam Map<String, String> queryParam) {
        PageInfoDto pageInfoDto = conversionrateservice.queryConversionRate(queryParam);
        return pageInfoDto;
    }
	
	/**
     * 转化率报表导出 
     * @author Benzc
     * @date 2017年2月4日
     */
	@SuppressWarnings("rawtypes")
    @RequestMapping(value = "/export/excel", method = RequestMethod.GET)
    public void exportVisitingRecord(@RequestParam Map<String, String> queryParam, HttpServletRequest request,
                                     HttpServletResponse response) throws Exception {
        List<Map> resultList = conversionrateservice.queryConversionRecordforExport(queryParam);
        Map<String, List<Map>> excelData = new HashMap<String, List<Map>>();
        excelData.put("转化率报表", resultList);
        List<ExcelExportColumn> exportColumnList = new ArrayList<ExcelExportColumn>();

        // 生成excel 文件
        exportColumnList.add(new ExcelExportColumn("TYPE", "单店转化率"));
        exportColumnList.add(new ExcelExportColumn("NOW", "MTD（当前）"));
        exportColumnList.add(new ExcelExportColumn("PAST", "上月"));
        exportColumnList.add(new ExcelExportColumn("EVER", "过去3月平均"));       
        exportColumnList.add(new ExcelExportColumn("REMARK", "KPI数据来源"));
        excelService.generateExcelForDms(excelData, exportColumnList, "转化率报表.xls", request, response);

    }

}
