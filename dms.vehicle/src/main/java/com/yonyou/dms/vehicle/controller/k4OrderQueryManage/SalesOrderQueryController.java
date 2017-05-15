package com.yonyou.dms.vehicle.controller.k4OrderQueryManage;

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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yonyou.dms.framework.DAO.DAOUtil;
import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.framework.domain.LoginInfoDto;
import com.yonyou.dms.framework.service.excel.ExcelDataType;
import com.yonyou.dms.framework.service.excel.ExcelExportColumn;
import com.yonyou.dms.framework.service.excel.ExcelGenerator;
import com.yonyou.dms.framework.util.bean.ApplicationContextHelper;
import com.yonyou.dms.vehicle.service.k4OrderQueryManage.SalesOrderQueryService;
import com.yonyou.f4.mvc.annotation.TxnConn;

/**
 * 销售订单查询 Controller
 * 
 * @author DC
 */
@Controller
@TxnConn
@RequestMapping("/salesOrderQuery")
public class SalesOrderQueryController {

	private static final Logger logger = LoggerFactory.getLogger(DAOUtil.class);

	@Autowired
	private SalesOrderQueryService service;

	@Autowired
	private ExcelGenerator excelService;

	/**
	 * 
	 * 销售订单查询
	 * 
	 * @author DC
	 * @date 2017年2月17日
	 * @param queryParam
	 * @return
	 */
	@RequestMapping(method = RequestMethod.GET)
	@ResponseBody
	public PageInfoDto queryList(@RequestParam Map<String, String> queryParam) {
		logger.info("============销售订单查询 ===============");
		/** 当前登录信息 **/
		LoginInfoDto loginInfo = ApplicationContextHelper.getBeanByType(LoginInfoDto.class);
		PageInfoDto pageInfoDto = service.queryList(queryParam, loginInfo);
		return pageInfoDto;
	}

	/**
	 * 根据ID 获取销售订单信息
	 * 
	 * @param id
	 *            ORDER_NO
	 * @return
	 */

	@RequestMapping(value = "/detail/{id}", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> findSalesOrderDetail(@PathVariable(value = "id") String id) {
		logger.info("============销售订单查询 ===============");
		LoginInfoDto loginInfo = ApplicationContextHelper.getBeanByType(LoginInfoDto.class);
		Map<String, Object> map = service.queryDetail(id, loginInfo);
		return map;
	}

	/**
	 * 订单状态联动查询
	 * 
	 * @param id
	 * 
	 * @return
	 */

	@RequestMapping(value = "/findsalesOrder/{id}", method = RequestMethod.GET)
	@ResponseBody
	public List<Map> findsalesOrder(@PathVariable(value = "id") String id) {
		logger.info("============销售订单查询 ===============");
		List<Map> map = service.findsalesOrder(id);
		return map;
	}

	/**
	 * 销售订单信息下载
	 * 
	 * @param queryParam
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(value = "/export/excel", method = RequestMethod.GET)
	@ResponseBody
	public void downLoadList(@RequestParam Map<String, String> queryParam, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		logger.info("============销售订单信息下载===============");
		/** 当前登录信息 **/
		LoginInfoDto loginInfo = ApplicationContextHelper.getBeanByType(LoginInfoDto.class);
		List<Map> resultList = service.findSalesOrderSuccList(queryParam, loginInfo);
		Map<String, List<Map>> excelData = new HashMap<>();
		excelData.put("销售订单信息", resultList);

		List<ExcelExportColumn> exportColumnList = new ArrayList<>();
		exportColumnList.add(new ExcelExportColumn("ORDER_YEAR_M", "订单年月"));
		exportColumnList.add(new ExcelExportColumn("ORDER_WEEK", "执行周"));
		exportColumnList.add(new ExcelExportColumn("ORDER_TYPE", "订单类型", ExcelDataType.Oem_Dict));
		exportColumnList.add(new ExcelExportColumn("ORDER_NO", "订单号"));
		exportColumnList.add(new ExcelExportColumn("EC_ORDER_NO", "官网订单号"));
		exportColumnList.add(new ExcelExportColumn("ORDER_STATUS", "订单状态", ExcelDataType.Oem_Dict));
		exportColumnList.add(new ExcelExportColumn("VIN", "VIN"));
		exportColumnList.add(new ExcelExportColumn("NODE_STATUS", "车辆节点状态", ExcelDataType.Oem_Dict));
		exportColumnList.add(new ExcelExportColumn("ORG_NAME2", "大区"));
		exportColumnList.add(new ExcelExportColumn("ORG_NAME", "小区"));
		exportColumnList.add(new ExcelExportColumn("DEALER_CODE", "经销商代码"));
		exportColumnList.add(new ExcelExportColumn("DEALER_SHORTNAME", "经销商名称"));
		exportColumnList.add(new ExcelExportColumn("MODEL_YEAR", "年款"));
		exportColumnList.add(new ExcelExportColumn("BRAND_CODE", "品牌"));
		exportColumnList.add(new ExcelExportColumn("SERIES_NAME", "车系"));
		exportColumnList.add(new ExcelExportColumn("MODEL_CODE", "CPOS"));
		exportColumnList.add(new ExcelExportColumn("GROUP_NAME", "车款"));
		exportColumnList.add(new ExcelExportColumn("COLOR_NAME", "颜色"));
		exportColumnList.add(new ExcelExportColumn("COLOR_CODE", "颜色代码"));
		exportColumnList.add(new ExcelExportColumn("TRIM_NAME", "内饰"));
		exportColumnList.add(new ExcelExportColumn("TRIM_CODE", "内饰代码"));
		exportColumnList.add(new ExcelExportColumn("VEHICLE_USE", "车辆用途", ExcelDataType.Oem_Dict));
		exportColumnList.add(new ExcelExportColumn("SO_NO", "SO号"));
		exportColumnList.add(new ExcelExportColumn("INVOICE_NO", "发票号"));
		exportColumnList.add(new ExcelExportColumn("ORDER_DATE", "提报日期"));
		exportColumnList.add(new ExcelExportColumn("DEAL_ORDER_AFFIRM_DATE", "确认日期"));
		exportColumnList.add(new ExcelExportColumn("IS_SEND", "是否发送", ExcelDataType.Oem_Dict));
		exportColumnList.add(new ExcelExportColumn("ORDER_CANCEL_DATE", "取消订单时间"));
		exportColumnList.add(new ExcelExportColumn("SEND_DATE", "订单发送时间"));
		exportColumnList.add(new ExcelExportColumn("MSRP_PRICE", "MSRP价格"));
		exportColumnList.add(new ExcelExportColumn("SO_CREATE_DATE", "SO创建时间"));
		exportColumnList.add(new ExcelExportColumn("INVOICE_DATE", "开票日期"));
		exportColumnList.add(new ExcelExportColumn("DELIVER_ORDER_DATE", "发运日期"));
		exportColumnList.add(new ExcelExportColumn("ETA_DATE", "ETA"));
		exportColumnList.add(new ExcelExportColumn("INSPECTION_DATE", "物流到店时间"));
		exportColumnList.add(new ExcelExportColumn("ARRIVE_DATE", "收车日期"));
		exportColumnList.add(new ExcelExportColumn("SO_CR_FAILURE_REASON", "配车失败原因"));
		exportColumnList.add(new ExcelExportColumn("SO_CR_FAILURE_DATE", "配车失败时间"));
		exportColumnList.add(new ExcelExportColumn("PAYMENT_TYPE", "付款方式", ExcelDataType.Oem_Dict));
		exportColumnList.add(new ExcelExportColumn("ORDER_AMOUNT", "最终付款金额"));
		exportColumnList.add(new ExcelExportColumn("WHOLESALE_PRICE", "批售价"));
		exportColumnList.add(new ExcelExportColumn("CANCEL_RESON", "取消备注"));
		exportColumnList.add(new ExcelExportColumn("IS_TOP", "是否置顶"));
		exportColumnList.add(new ExcelExportColumn("IS_FREEZE", "是否截停"));
		exportColumnList.add(new ExcelExportColumn("FREEZE_REASON", "截停原因"));
		exportColumnList.add(new ExcelExportColumn("FREEZE_DATE", "截停时间"));
		exportColumnList.add(new ExcelExportColumn("UNFREEZE_DATE", "解除截停时间"));
		exportColumnList.add(new ExcelExportColumn("FINANCING_STATUS", "融资状态"));
		exportColumnList.add(new ExcelExportColumn("R_DEALER_CODE", "交车经销商代码"));
		exportColumnList.add(new ExcelExportColumn("R_DEALER_SHORTNAME", "交车经销商名称"));
		exportColumnList.add(new ExcelExportColumn("ASSIGNMENT_REMARK", "备注"));

		// exportColumnList.add(new ExcelExportColumn("ADDRESS", "发运地址"));
		// exportColumnList.add(new ExcelExportColumn("IS_REBATE", "是否使用返利",
		// ExcelDataType.Oem_Dict));
		// exportColumnList.add(new ExcelExportColumn("IS_TOP", "是否置顶"));
		// exportColumnList.add(new ExcelExportColumn("VIN", "VIN"));
		// exportColumnList.add(new ExcelExportColumn("DN_NO", "DN号"));
		// exportColumnList.add(new ExcelExportColumn("ORDER_DATE", "提交时间"));
		// exportColumnList.add(new ExcelExportColumn("ALLOT_WEEK_DATE",
		// "分周时间"));
		// exportColumnList.add(new ExcelExportColumn("ALLOT_VEHICLE_DATE",
		// "配车时间"));
		// exportColumnList.add(new ExcelExportColumn("DEAL_BOOK_DATE",
		// "验收入库时间"));
		// exportColumnList.add(new ExcelExportColumn("REMARK", "备注信息"));
		// exportColumnList.add(new ExcelExportColumn("CANCEL_RESON",
		// "订单取消原因"));
		// exportColumnList.add(new ExcelExportColumn("district_opinion",
		// "紧急订单小区审核意见"));
		// exportColumnList.add(new ExcelExportColumn("otd_opinion",
		// "紧急订单OTD审核意见"));
		// exportColumnList.add(new ExcelExportColumn("INVOICE_TYPE", "开票类型",
		// ExcelDataType.Oem_Dict));

		excelService.generateExcel(excelData, exportColumnList, "销售订单信息.xls", request, response);
	}

	/**
	 * ------------------------------------------------经销商端功能代码-----------------
	 * --------------------------------------
	 **/

	/**
	 * 
	 * 销售订单查询 (经销商端)
	 * 
	 * @author DC
	 * @date 2017年2月17日
	 * @param queryParam
	 * @return
	 */
	@RequestMapping(value = "/Dealer", method = RequestMethod.GET)
	@ResponseBody
	public PageInfoDto queryDealerList(@RequestParam Map<String, String> queryParam) {
		logger.info("============销售订单查询 ===============");
		/** 当前登录信息 **/
		LoginInfoDto loginInfo = ApplicationContextHelper.getBeanByType(LoginInfoDto.class);
		PageInfoDto pageInfoDto = service.queryDealerList(queryParam, loginInfo);
		return pageInfoDto;
	}

	/**
	 * 根据ID 获取销售订单信息(经销商端)
	 * 
	 * @param id
	 *            ORDER_NO
	 * @return
	 */

	@RequestMapping(value = "/Dealer/detail/{id}", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> findSalesOrderDealerDetail(@PathVariable(value = "id") String id) {
		logger.info("============销售订单查询 ===============");
		LoginInfoDto loginInfo = ApplicationContextHelper.getBeanByType(LoginInfoDto.class);
		Map<String, Object> map = service.queryDealerDetail(id, loginInfo);
		return map;
	}

	/**
	 * 销售订单信息下载(经销商端)
	 * 
	 * @param queryParam
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(value = "/Dealer/export/excel", method = RequestMethod.GET)
	@ResponseBody
	public void downLoadDealer(@RequestParam Map<String, String> queryParam, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		logger.info("============销售订单信息下载(经销商端)===============");
		/** 当前登录信息 **/
		LoginInfoDto loginInfo = ApplicationContextHelper.getBeanByType(LoginInfoDto.class);
		List<Map> resultList = service.findDealerSalesOrderSuccList(queryParam, loginInfo);
		Map<String, List<Map>> excelData = new HashMap<>();
		excelData.put("销售订单信息", resultList);

		List<ExcelExportColumn> exportColumnList = new ArrayList<>();
		exportColumnList.add(new ExcelExportColumn("ORDER_NO", "订单号"));
		exportColumnList.add(new ExcelExportColumn("ORDER_YEAR_M", "订单年月"));
		exportColumnList.add(new ExcelExportColumn("ORDER_WEEK", "执行周"));
		exportColumnList.add(new ExcelExportColumn("ORDER_TYPE", "订单类型", ExcelDataType.Oem_Dict));
		exportColumnList.add(new ExcelExportColumn("EC_ORDER_NO", "官网订单号"));
		exportColumnList.add(new ExcelExportColumn("ORDER_STATUS", "订单状态", ExcelDataType.Oem_Dict));
		exportColumnList.add(new ExcelExportColumn("VIN", "VIN"));
		exportColumnList.add(new ExcelExportColumn("BRAND_CODE", "品牌"));
		exportColumnList.add(new ExcelExportColumn("SERIES_NAME", "车系"));
		exportColumnList.add(new ExcelExportColumn("MODEL_CODE", "CPOS"));
		exportColumnList.add(new ExcelExportColumn("MODEL_YEAR", "年款"));
		exportColumnList.add(new ExcelExportColumn("GROUP_NAME", "车款"));
		exportColumnList.add(new ExcelExportColumn("COLOR_NAME", "颜色"));
		exportColumnList.add(new ExcelExportColumn("TRIM_NAME", "内饰"));
		exportColumnList.add(new ExcelExportColumn("WHOLESALE_PRICE", "批售价"));
		exportColumnList.add(new ExcelExportColumn("MSRP_PRICE", "MSRP价格"));
		exportColumnList.add(new ExcelExportColumn("PAYMENT_TYPE", "付款方式", ExcelDataType.Oem_Dict));
		exportColumnList.add(new ExcelExportColumn("ORDER_DATE", "提报日期"));
		exportColumnList.add(new ExcelExportColumn("DEAL_ORDER_AFFIRM_DATE", "确认日期"));
		exportColumnList.add(new ExcelExportColumn("IS_SEND", "订单发送状态", ExcelDataType.Oem_Dict));
		exportColumnList.add(new ExcelExportColumn("FINANCING_STATUS", "财务状态", ExcelDataType.Oem_Dict));
		exportColumnList.add(new ExcelExportColumn("SO_CREATE_DATE", "订单发送时间"));
		exportColumnList.add(new ExcelExportColumn("ETA_DATE", "ETA"));
		exportColumnList.add(new ExcelExportColumn("VEHICLE_USE", "车辆用途", ExcelDataType.Oem_Dict));
		exportColumnList.add(new ExcelExportColumn("SO_CR_FAILURE_REASON", "配车失败原因"));
		exportColumnList.add(new ExcelExportColumn("SO_CR_FAILURE_DATE", "配车失效原因"));
		exportColumnList.add(new ExcelExportColumn("IS_FREEZE", "是否截停", ExcelDataType.Oem_Dict));
		exportColumnList.add(new ExcelExportColumn("FREEZE_REASON", "截停原因"));
		exportColumnList.add(new ExcelExportColumn("FREEZE_DATE", "截停时间"));
		exportColumnList.add(new ExcelExportColumn("UNFREEZE_DATE", "解除截停时间"));

		excelService.generateExcel(excelData, exportColumnList, "销售订单信息.xls", request, response);
	}

	/**
	 * 车辆综合查询变更日志
	 * 
	 * @param queryParam
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/orderRecords/{id}", method = RequestMethod.GET)
	@ResponseBody
	public PageInfoDto orderRecords(@RequestParam Map<String, String> queryParam,
			@PathVariable(value = "id") String id) {
		logger.info("============车辆综合详细查询--变更日志===============");
		/** 当前登录信息 **/
		LoginInfoDto loginInfo = ApplicationContextHelper.getBeanByType(LoginInfoDto.class);
		queryParam.put("id", id);
		PageInfoDto pageInfoDto = service.orderRecords(queryParam, loginInfo);
		return pageInfoDto;
	}

}
