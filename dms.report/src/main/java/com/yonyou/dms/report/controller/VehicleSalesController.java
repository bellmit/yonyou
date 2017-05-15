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
import com.yonyou.dms.framework.util.FrameworkUtil;
import com.yonyou.dms.framework.util.Utility;
import com.yonyou.dms.function.common.CommonConstants;
import com.yonyou.dms.function.common.DictCodeConstants;
import com.yonyou.dms.report.service.impl.VehicleSalesService;
import com.yonyou.f4.mvc.annotation.TxnConn;
import com.yonyou.f4.mvc.controller.BaseController;

/**
* 整车销售报表控制类
* @author wangliang
* @date 2017年01月11日
*/

@Controller
@TxnConn
@RequestMapping("/saleReport/vehicleSalesReport")
public class VehicleSalesController extends BaseController {
	  @Autowired
	  VehicleSalesService veicleSalesService;
	  
	  @Autowired
	  private ExcelGenerator excelService;
	  
	  @RequestMapping(method = RequestMethod.GET)
	  @ResponseBody
	  public PageInfoDto queryVehicleSalesed(@RequestParam Map<String,String> queryParam) {
		  PageInfoDto pageInfoDto = veicleSalesService.queryVehicleSalesChecked(queryParam);
		  return pageInfoDto;
	  }
	  
	  
	@SuppressWarnings({ "rawtypes" })
	@RequestMapping(value = "/excel", method = RequestMethod.GET)
	  public void export(@RequestParam Map<String, String> queryParam, HttpServletRequest request, HttpServletResponse response) {
		  List<Map> resultList = veicleSalesService.queryVehicleSalesExport(queryParam);
		  Map<String,List<Map>> excelData = new HashMap<String,List<Map>>();
		  
		  excelData.put("整车销售报表", resultList);
		  List<ExcelExportColumn> exportColmnList = new ArrayList<ExcelExportColumn>();
		  exportColmnList.add(new ExcelExportColumn("SO_NO","订单编码"));
		  exportColmnList.add(new ExcelExportColumn("STOCK_OUT_DATE","出库日期"));
		  exportColmnList.add(new ExcelExportColumn("ORG_NAME","部门"));
		  exportColmnList.add(new ExcelExportColumn("USER_NAME","销售顾问"));
		  exportColmnList.add(new ExcelExportColumn("CUSTOMER_NAME","客户名称"));
		  exportColmnList.add(new ExcelExportColumn("GENDER","客户性别"));
		  exportColmnList.add(new ExcelExportColumn("PHONE","联系电话"));
		  exportColmnList.add(new ExcelExportColumn("ADDRESS","联系地址"));
		  exportColmnList.add(new ExcelExportColumn("BRAND_NAME","品牌"));
		  exportColmnList.add(new ExcelExportColumn("SERIES_NAME","车系"));
		  exportColmnList.add(new ExcelExportColumn("MODEL_NAME","车型"));
		  exportColmnList.add(new ExcelExportColumn("COLOR_NAME","颜色"));
		  exportColmnList.add(new ExcelExportColumn("VIN","VIN"));
		  exportColmnList.add(new ExcelExportColumn("ENGINE_NO","发动机号"));
		  exportColmnList.add(new ExcelExportColumn("PAY_MODE","付款方式"));
		  exportColmnList.add(new ExcelExportColumn("VEHICLE_PRICE","车辆销售价"));
		  exportColmnList.add(new ExcelExportColumn("DIRECTIVE_PRICE","销售指导价"));
		  exportColmnList.add(new ExcelExportColumn("PURCHASE_PRICE","车辆采购价"));
		  exportColmnList.add(new ExcelExportColumn("PURCHASE_PRICE","车辆优惠"));
		  exportColmnList.add(new ExcelExportColumn("OTHER_AMOUNT","其他金额"));
		  exportColmnList.add(new ExcelExportColumn("INVOICE_AMOUNT","开票价格"));
		  exportColmnList.add(new ExcelExportColumn("POSS_PROFIT","销售毛利"));
		  exportColmnList.add(new ExcelExportColumn("POSS_PROFIT_rate","销售毛利率"));
		  exportColmnList.add(new ExcelExportColumn("BUSINESS_TYPE","订单类型"));
		  exportColmnList.add(new ExcelExportColumn("PRODUCT_CODE","产品代码"));
		  exportColmnList.add(new ExcelExportColumn("CONFIG_NAME","配置"));
		  exportColmnList.add(new ExcelExportColumn("FACTORY_DATE","采购日期"));
		  
		  //生成excel文件
		  excelService.generateExcelForDms(excelData, exportColmnList,FrameworkUtil.getLoginInfo().getDealerShortName()+"_整车销售报表.xls", request, response);
	  }
	
	
}
