package com.yonyou.dms.report.controller;

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

import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.framework.service.excel.ExcelExportColumn;
import com.yonyou.dms.framework.service.excel.ExcelGenerator;
import com.yonyou.dms.framework.util.FrameworkUtil;
import com.yonyou.dms.function.exception.ServiceBizException;
import com.yonyou.dms.report.service.impl.SaleReportCarStoresService;
import com.yonyou.f4.mvc.annotation.TxnConn;
import com.yonyou.f4.mvc.controller.BaseController;

/**
 * 车辆库存明细表
 * 
 * @author
 *
 */
@Controller
@TxnConn
@RequestMapping("/saleReport/carStores")
public class SaleReportCarStoresController extends BaseController {
	@Autowired
	private SaleReportCarStoresService saleReportCarStoresService;
	@Autowired
	private ExcelGenerator excelGenerator;

	/**
	 * 车辆库存明细查询
	 * 
	 * @author
	 * @param queryParam
	 * @return
	 * @throws SQLException
	 * @throws ServiceBizException
	 */

	@RequestMapping(method = RequestMethod.GET)
	@ResponseBody
	public PageInfoDto queryStreamAnalysis(@RequestParam Map<String, String> queryParam)
			throws ServiceBizException, SQLException {
		return saleReportCarStoresService.querySaleReportCarStoresInfo(queryParam);
	}

	/**
	 * 查询仓库
	 * 
	 * @param queryParam
	 * @return
	 * @throws ServiceBizException
	 * @throws SQLException
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/store", method = RequestMethod.GET)
	@ResponseBody
	public List<Map> queryStorage(@RequestParam Map<String, String> queryParam)
			throws ServiceBizException, SQLException {
		return saleReportCarStoresService.queryStorage(queryParam);

	}

	/**
	 * 查询部门
	 * 
	 * @param queryParam
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/org", method = RequestMethod.GET)
	@ResponseBody
	public List<Map> queryOrg(@RequestParam Map<String, String> queryParam) {
		return saleReportCarStoresService.queryOrg(queryParam);

	}

	/**
	 * 车辆库存明细表导出
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
		@SuppressWarnings("rawtypes")
		List<Map> resultList = saleReportCarStoresService.exportSaleReportCarStores(queryParam);
		@SuppressWarnings("rawtypes")
		Map<String, List<Map>> excelData = new HashMap<String, List<Map>>();
		excelData.put("车辆库存明细表", resultList);
		List<ExcelExportColumn> exportColumnList = new ArrayList<ExcelExportColumn>();
		exportColumnList.add(new ExcelExportColumn("BRAND_NAME", "品牌"));
		exportColumnList.add(new ExcelExportColumn("SERIES_NAME", "车系"));
		exportColumnList.add(new ExcelExportColumn("MODEL_NAME", "车型"));
		exportColumnList.add(new ExcelExportColumn("CONFIG_NAME", "配置"));
		exportColumnList.add(new ExcelExportColumn("COLOR_NAME", "颜色"));
		exportColumnList.add(new ExcelExportColumn("PRODUCT_CODE", "产品代码"));
		exportColumnList.add(new ExcelExportColumn("PRODUCT_NAME", "产品名称"));
		exportColumnList.add(new ExcelExportColumn("MANUFACTURE_DATE", "生产日期"));
		exportColumnList.add(new ExcelExportColumn("PURCHASE_DATE", "采购日期"));
		exportColumnList.add(new ExcelExportColumn("PURCHASE_PRICE", "采购价格"));
		exportColumnList.add(new ExcelExportColumn("NOPURCHASEPRICE", "不含税价格"));
		exportColumnList.add(new ExcelExportColumn("TRANSFER_DATE", "移库日期"));
		exportColumnList.add(new ExcelExportColumn("VIN", "VIN"));
		exportColumnList.add(new ExcelExportColumn("FIRST_STOCK_IN_DATE", "首次入库日期"));
		exportColumnList.add(new ExcelExportColumn("LATEST_STOCK_IN_DATE", "最后入库日期"));
		exportColumnList.add(new ExcelExportColumn("STOCK_TIME", "库龄"));
		exportColumnList.add(new ExcelExportColumn("STORAGE_CODE", "仓库"));
		exportColumnList.add(new ExcelExportColumn("DISPATCHED_STATUS", "配车状态"));
		exportColumnList.add(new ExcelExportColumn("STOCK_STATUS", "库存状态"));
		exportColumnList.add(new ExcelExportColumn("MAR_STATUS", "质损状态"));
		exportColumnList.add(new ExcelExportColumn("KEY_NUMBER", "钥匙编号"));
		exportColumnList.add(new ExcelExportColumn("HAS_CERTIFICATE", "是否有合格证"));
		exportColumnList.add(new ExcelExportColumn("CERTIFICATE_NUMBER", "合格证号"));
		exportColumnList.add(new ExcelExportColumn("IS_VIP", "是否VIP预留"));
		exportColumnList.add(new ExcelExportColumn("OEM_TAG", "OEM标志"));
		exportColumnList.add(new ExcelExportColumn("IS_SECONDHAND", "是否二手车"));
		exportColumnList.add(new ExcelExportColumn("IS_TEST_DRIVE_CAR", "是否试乘试驾车"));
		exportColumnList.add(new ExcelExportColumn("IS_CONSIGNED", "是否受托付交车"));
		exportColumnList.add(new ExcelExportColumn("IS_PROMOTION", "是否促销车"));
		exportColumnList.add(new ExcelExportColumn("IS_PURCHASE_RETURN", "是否退车"));
		exportColumnList.add(new ExcelExportColumn("SOLD_BY", "销售顾问"));
		exportColumnList.add(new ExcelExportColumn("DELIVERING_DATE", "预定交车日期"));
		exportColumnList.add(new ExcelExportColumn("INVOICE_DATE", "开票日期"));
		exportColumnList.add(new ExcelExportColumn("DISPATCHED_DATE", "配车日期"));
		exportColumnList.add(new ExcelExportColumn("OUT_STOCK_DATE", "出库日期"));
		exportColumnList.add(new ExcelExportColumn("OUT_STOCK_NO", "出库单号"));
		exportColumnList.add(new ExcelExportColumn("ORG_CODE", "部门"));
		exportColumnList.add(new ExcelExportColumn("OUT_STOCK_TYPE", "出库类型"));
		exportColumnList.add(new ExcelExportColumn("CUSTOMER_NAME", "客户名称"));
		exportColumnList.add(new ExcelExportColumn("VENDOR_NAME", "供应商名称"));
		exportColumnList.add(new ExcelExportColumn("IN_STOCK_NO", "入库单号"));
		exportColumnList.add(new ExcelExportColumn("DIRECT_PRICE", "销售指导价"));
		exportColumnList.add(new ExcelExportColumn("STORAGE_POSITION_CODE", "库位代码"));
		exportColumnList.add(new ExcelExportColumn("ENGINE_NO", "发动机编号"));
		exportColumnList.add(new ExcelExportColumn("ADDITIONAL_COST", "附加成本"));
		exportColumnList.add(new ExcelExportColumn("FACTORY_DATE", "出厂日期"));
		exportColumnList.add(new ExcelExportColumn("PO_NO", "采购订单编号"));
		exportColumnList.add(new ExcelExportColumn("DISCHARGE_STANDARD", "排气标准"));
		exportColumnList.add(new ExcelExportColumn("PRODUCTING_AREA", "产地"));
		exportColumnList.add(new ExcelExportColumn("INSPECTION_CONSIGNED", "是否代验收"));
		exportColumnList.add(new ExcelExportColumn("IS_DIRECT", "是否直销"));
		exportColumnList.add(new ExcelExportColumn("SHIPPING_DATE", "发车日期"));
		exportColumnList.add(new ExcelExportColumn("DELIVERYMAN_NAME", "送车人姓名"));
		exportColumnList.add(new ExcelExportColumn("DELIVERYMAN_PHONE", "送车人电话"));
		exportColumnList.add(new ExcelExportColumn("SHIPPER_LICENSE", "承运车编号"));
		exportColumnList.add(new ExcelExportColumn("SHIPPER_NAME", "承运商名称"));
		exportColumnList.add(new ExcelExportColumn("SHIPPING_ADDRESS", "收货地址"));
		exportColumnList.add(new ExcelExportColumn("CONSIGNER_CODE", "委托单位代码"));
		exportColumnList.add(new ExcelExportColumn("CONSIGNER_NAME", "委托单位名称"));
		excelGenerator.generateExcelForDms(excelData, exportColumnList,
				FrameworkUtil.getLoginInfo().getDealerShortName()+"_车辆库存明细表.xls", request, response);
	}
}
