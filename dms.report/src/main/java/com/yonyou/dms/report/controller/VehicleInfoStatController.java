package com.yonyou.dms.report.controller;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
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
import com.yonyou.dms.framework.util.FrameworkUtil;
import com.yonyou.dms.framework.util.Utility;
import com.yonyou.dms.report.service.impl.VehicleInfoStatService;
import com.yonyou.f4.mvc.annotation.TxnConn;
import com.yonyou.f4.mvc.controller.BaseController;

/**
 * 车辆进销存分析控制类
 * 
 * @author wangliang
 * @date 2017年01月18日
 */

@Controller
@TxnConn
@RequestMapping("/vehicleInfoStat/rateSearch")
public class VehicleInfoStatController extends BaseController {
	@Autowired
	VehicleInfoStatService veicleInfoStatService;
	
	 @Autowired
	 private ExcelGenerator      excelService;

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(method = RequestMethod.GET)
	@ResponseBody
	public PageInfoDto queryVeicleInfoChecked(@RequestParam Map<String, String> queryParam) {
		// 获取日期框中的日期
		String month = queryParam.get("START_DATE");
		System.out.println("**************************");
		System.out.println(month);
		System.out.println("**************************");
		try {
			if (month != null && !"".equals(month)) {
				int year = Utility.getInt(month.substring(0, 4));
				int monthdb = Utility.getInt(month.substring(5, 7));
				Calendar call = Calendar.getInstance();
				// monthdb为11,为12
				call.set(year, monthdb, 1);
				Date dateNow = call.getTime();
				// monthdb为11
				call.set(year, monthdb - 1, 1);
				Date dateAgo = call.getTime();
				long daterang = (dateNow.getTime() - dateAgo.getTime());
				long time = 1000 * 3600 * 24; // 当前月的上个月天数
				String endDatep = month + "-" + String.valueOf(daterang / time);
				String startDate = month + "-" + "01";
				String endDate = endDatep;
				PageInfoDto pageInfoDto = veicleInfoStatService.queryVeicleInfoChecked(queryParam, startDate, endDate);
    			 List<Map> rows = pageInfoDto.getRows();//获取当前页所有数据
				  Map values = new HashMap();
				  values.put("BRAND_NAME", "总计");
				  values.put("NOW_AMOUNT", 0.00);
				  values.put("SALES_AMOUNT", 0.00);
				  values.put("LAST_MONTH_SALES", 0.00);
				  values.put("ROAD", 0.00);
				  values.put("CUSTOMER_H", 0.00);
				  values.put("CUSTOMER_A", 0.00);
				  values.put("CUSTOMER_B", 0.00);
				 
				  for(Map map : rows){
					  
					  values.put("NOW_AMOUNT", Double.parseDouble(map.get("NOW_AMOUNT").toString())+(double)values.get("NOW_AMOUNT"));
					  values.put("SALES_AMOUNT", Double.parseDouble(map.get("SALES_AMOUNT").toString())+(double)values.get("SALES_AMOUNT"));
					  values.put("LAST_MONTH_SALES", Double.parseDouble(map.get("LAST_MONTH_SALES").toString())+(double)values.get("LAST_MONTH_SALES"));
					  values.put("ROAD", Double.parseDouble(map.get("ROAD").toString())+(double)values.get("ROAD"));
					  values.put("CUSTOMER_H", Double.parseDouble(map.get("CUSTOMER_H").toString())+(double)values.get("CUSTOMER_H"));
					  values.put("CUSTOMER_A", Double.parseDouble(map.get("CUSTOMER_A").toString())+(double)values.get("CUSTOMER_A"));
					  values.put("CUSTOMER_B", Double.parseDouble(map.get("CUSTOMER_B").toString())+(double)values.get("CUSTOMER_B"));
				  }
				  rows.add(values);
				 
				return pageInfoDto;
			}

		} catch (Exception e) {
			
		}
		return null;
	}

	
	
	/**
	 * 导出EXCEL
	 * @param queryParam
	 * @param request
	 * @param response
	 */
	@SuppressWarnings("rawtypes")
    @RequestMapping(value = "/export/excel", method = RequestMethod.GET)
	public void export(@RequestParam Map<String, String> queryParam, HttpServletRequest request, HttpServletResponse response) {
		List<Map> resultList = veicleInfoStatService.queryVehicleInfoStatExport(queryParam);
		Map<String, List<Map>> excelData = new HashMap<String,List<Map>>();
		excelData.put("车辆经销存分析", resultList);
		List<ExcelExportColumn> exportColumnList = new ArrayList<ExcelExportColumn>();
		//exportColumnList.add(new ExcelExportColumn("DEALER_CODE", "供应商代码"));
		exportColumnList.add(new ExcelExportColumn("BRAND_NAME", "品牌"));
		exportColumnList.add(new ExcelExportColumn("SERIES_NAME", "车系"));
		exportColumnList.add(new ExcelExportColumn("MODEL_NAME", "车型"));
		exportColumnList.add(new ExcelExportColumn("NOW_AMOUNT", "当前库存"));
		exportColumnList.add(new ExcelExportColumn("SALES_AMOUNT", "本月累计销售"));
		exportColumnList.add(new ExcelExportColumn("LAST_MONTH_SALES", "上月销售"));
		exportColumnList.add(new ExcelExportColumn("ROAD", "在途"));
		exportColumnList.add(new ExcelExportColumn("CUSTOMER_H", "潜客积累H"));
		exportColumnList.add(new ExcelExportColumn("CUSTOMER_A", "潜客积累A"));
		exportColumnList.add(new ExcelExportColumn("CUSTOMER_B", "潜客积累B"));
		exportColumnList.add(new ExcelExportColumn("STORE_RATE", "库存深度(月)"));
		//生成excel文件
		excelService.generateExcelForDms(excelData, exportColumnList, FrameworkUtil.getLoginInfo().getDealerShortName()+"_车辆进销存分析.XLS", request, response);
	}
	
	
}
