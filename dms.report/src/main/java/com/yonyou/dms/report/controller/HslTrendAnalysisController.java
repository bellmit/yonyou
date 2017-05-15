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
import com.yonyou.dms.report.service.impl.HslTrendAnalysisService;
import com.yonyou.f4.mvc.annotation.TxnConn;
import com.yonyou.f4.mvc.controller.BaseController;

/**
 * 业务描述：客流HSL趋势分析
 * @author Benzc
 * @date 2017年2月6日
 */
@Controller
@TxnConn
@RequestMapping("/basedata/hslTrendAnalysis")
public class HslTrendAnalysisController extends BaseController{
	
	@Autowired
    private HslTrendAnalysisService hsltrendanalysisservice;
	
	@Autowired
    private ExcelGenerator excelService;
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = "/hslSearch", method = RequestMethod.GET)
    @ResponseBody
    public PageInfoDto queryVehicleSales(@RequestParam Map<String, String> queryParam) {
		System.err.println(queryParam.get("soldBy"));
        PageInfoDto pageInfoDto = hsltrendanalysisservice.queryHslTrendAnalysis(queryParam);
        List<Map> rows = pageInfoDto.getRows();//获取当前页所有数据
        Map values = new HashMap();//留档
        values.put("SERIES_NAME", "总计");
        values.put("TYPE", "留档");
        values.put("W8", 0L);
        values.put("W7", 0L);
        values.put("W6", 0L);
        values.put("W5", 0L);
        values.put("W4", 0L);
        values.put("W3", 0L);
        values.put("W2", 0L);
        values.put("W1", 0L);
        values.put("WN", 0L);
        values.put("AVERAGE", 0.00);
        Map hsl = new HashMap();//HSL
        hsl.put("SERIES_NAME", "");
        hsl.put("TYPE", "HSL");
        hsl.put("W8", 0L);
        hsl.put("W7", 0L);
        hsl.put("W6", 0L);
        hsl.put("W5", 0L);
        hsl.put("W4", 0L);
        hsl.put("W3", 0L);
        hsl.put("W2", 0L);
        hsl.put("W1", 0L);
        hsl.put("WN", 0L);
        hsl.put("AVERAGE", 0.00);
        for (Map map : rows) {
			String type = map.get("TYPE").toString();
			if (type.equals("留档")) {
				values.put("W8", (Long)map.get("W8")+(Long)values.get("W8"));
				values.put("W7", (Long)map.get("W7")+(Long)values.get("W7"));
				values.put("W6", (Long)map.get("W6")+(Long)values.get("W6"));
				values.put("W5", (Long)map.get("W5")+(Long)values.get("W5"));
				values.put("W4", (Long)map.get("W4")+(Long)values.get("W4"));
				values.put("W3", (Long)map.get("W3")+(Long)values.get("W3"));
				values.put("W2", (Long)map.get("W2")+(Long)values.get("W2"));
				values.put("W1", (Long)map.get("W1")+(Long)values.get("W1"));
				values.put("WN", (Long)map.get("WN")+(Long)values.get("WN"));
				values.put("AVERAGE", (double)values.get("AVERAGE")+Double.parseDouble(map.get("AVERAGE").toString()));
			}else{
				hsl.put("W8", (Long)map.get("W8")+(Long)hsl.get("W8"));
				hsl.put("W7", (Long)map.get("W7")+(Long)hsl.get("W7"));
				hsl.put("W6", (Long)map.get("W6")+(Long)hsl.get("W6"));
				hsl.put("W5", (Long)map.get("W5")+(Long)hsl.get("W5"));
				hsl.put("W4", (Long)map.get("W4")+(Long)hsl.get("W4"));
				hsl.put("W3", (Long)map.get("W3")+(Long)hsl.get("W3"));
				hsl.put("W2", (Long)map.get("W2")+(Long)hsl.get("W2"));
				hsl.put("W1", (Long)map.get("W1")+(Long)hsl.get("W1"));
				hsl.put("WN", (Long)map.get("WN")+(Long)hsl.get("WN"));
				hsl.put("AVERAGE", (double)hsl.get("AVERAGE")+Double.parseDouble(map.get("AVERAGE").toString()));
			}
		}
        rows.add(values);
        rows.add(hsl);
        return pageInfoDto;
    }
	
	/**
     * 客流HSL趋势分析导出 
     * @author Benzc
     * @date 2017年2月7日
     */
	@SuppressWarnings({ "rawtypes", "unchecked" })
    @RequestMapping(value = "/export/excel", method = RequestMethod.GET)
    public void exportHslTrendAnalysis(@RequestParam Map<String, String> queryParam, HttpServletRequest request,
                                     HttpServletResponse response) throws Exception {
        List<Map> resultList = hsltrendanalysisservice.queryHslTrendAnalysisforExport(queryParam);
        List<Map> rows = resultList;//获取当前页所有数据
        Map values = new HashMap();//留档
        values.put("SERIES_NAME", "总计");
        values.put("TYPE", "留档");
        values.put("W8", 0L);
        values.put("W7", 0L);
        values.put("W6", 0L);
        values.put("W5", 0L);
        values.put("W4", 0L);
        values.put("W3", 0L);
        values.put("W2", 0L);
        values.put("W1", 0L);
        values.put("WN", 0L);
        values.put("AVERAGE", 0.00);
        Map hsl = new HashMap();//HSL
        hsl.put("SERIES_NAME", "");
        hsl.put("TYPE", "HSL");
        hsl.put("W8", 0L);
        hsl.put("W7", 0L);
        hsl.put("W6", 0L);
        hsl.put("W5", 0L);
        hsl.put("W4", 0L);
        hsl.put("W3", 0L);
        hsl.put("W2", 0L);
        hsl.put("W1", 0L);
        hsl.put("WN", 0L);
        hsl.put("AVERAGE", 0.00);
        for (Map map : rows) {
			String type = map.get("TYPE").toString();
			if (type.equals("留档")) {
				values.put("W8", (Long)map.get("W8")+(Long)values.get("W8"));
				values.put("W7", (Long)map.get("W7")+(Long)values.get("W7"));
				values.put("W6", (Long)map.get("W6")+(Long)values.get("W6"));
				values.put("W5", (Long)map.get("W5")+(Long)values.get("W5"));
				values.put("W4", (Long)map.get("W4")+(Long)values.get("W4"));
				values.put("W3", (Long)map.get("W3")+(Long)values.get("W3"));
				values.put("W2", (Long)map.get("W2")+(Long)values.get("W2"));
				values.put("W1", (Long)map.get("W1")+(Long)values.get("W1"));
				values.put("WN", (Long)map.get("WN")+(Long)values.get("WN"));
				values.put("AVERAGE", (double)values.get("AVERAGE")+Double.parseDouble(map.get("AVERAGE").toString()));
			}else{
				hsl.put("W8", (Long)map.get("W8")+(Long)hsl.get("W8"));
				hsl.put("W7", (Long)map.get("W7")+(Long)hsl.get("W7"));
				hsl.put("W6", (Long)map.get("W6")+(Long)hsl.get("W6"));
				hsl.put("W5", (Long)map.get("W5")+(Long)hsl.get("W5"));
				hsl.put("W4", (Long)map.get("W4")+(Long)hsl.get("W4"));
				hsl.put("W3", (Long)map.get("W3")+(Long)hsl.get("W3"));
				hsl.put("W2", (Long)map.get("W2")+(Long)hsl.get("W2"));
				hsl.put("W1", (Long)map.get("W1")+(Long)hsl.get("W1"));
				hsl.put("WN", (Long)map.get("WN")+(Long)hsl.get("WN"));
				hsl.put("AVERAGE", (double)hsl.get("AVERAGE")+Double.parseDouble(map.get("AVERAGE").toString()));
			}
		}
        rows.add(values);
        rows.add(hsl);
        Map<String, List<Map>> excelData = new HashMap<String, List<Map>>();
        excelData.put("客流HSL趋势分析", resultList);
        List<ExcelExportColumn> exportColumnList = new ArrayList<ExcelExportColumn>();

        // 生成excel 文件
        exportColumnList.add(new ExcelExportColumn("SERIES_NAME", "车系"));
        exportColumnList.add(new ExcelExportColumn("TYPE", ""));
        exportColumnList.add(new ExcelExportColumn("W8", "前8周"));
        exportColumnList.add(new ExcelExportColumn("W7", "前7周"));       
        exportColumnList.add(new ExcelExportColumn("W6", "前6周"));
        exportColumnList.add(new ExcelExportColumn("W5", "前5周"));
        exportColumnList.add(new ExcelExportColumn("W4", "前4周"));
        exportColumnList.add(new ExcelExportColumn("W3", "前3周"));
        exportColumnList.add(new ExcelExportColumn("W2", "前2周"));
        exportColumnList.add(new ExcelExportColumn("W1", "前1周"));
        exportColumnList.add(new ExcelExportColumn("WN", "当前周"));
        exportColumnList.add(new ExcelExportColumn("AVERAGE", "前8周平均"));
        excelService.generateExcelForDms(excelData, exportColumnList, "客流HSL趋势分析.xls", request, response);

    }

}
