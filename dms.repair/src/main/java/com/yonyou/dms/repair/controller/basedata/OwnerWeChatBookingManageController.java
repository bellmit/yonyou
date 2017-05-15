/**
 * 
 */
package com.yonyou.dms.repair.controller.basedata;

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
import com.yonyou.dms.function.common.CommonConstants;
import com.yonyou.dms.repair.service.basedata.OwnerWeChatBookingManageService;
import com.yonyou.f4.mvc.annotation.TxnConn;
import com.yonyou.f4.mvc.controller.BaseController;

/**
 * @author yangjie
 *
 */
@Controller
@TxnConn
@RequestMapping("/basedata/ownerWeChatBooking")
@SuppressWarnings("rawtypes")
public class OwnerWeChatBookingManageController extends BaseController {

	@Autowired
	private OwnerWeChatBookingManageService ownerWeChatBookingManageService;
	
	@Autowired
	private ExcelGenerator excelService;

	/**
	 * 查询主页面信息
	 * 
	 * @param queryParam
	 * @return
	 */
	@RequestMapping(method = RequestMethod.GET)
	@ResponseBody
	public PageInfoDto findAllMainMsg(@RequestParam Map<String, String> queryParam) {
		return ownerWeChatBookingManageService.findAll(queryParam);
	}

	/**
	 * 查询微信卡券
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	@ResponseBody
	public PageInfoDto findAllWeChatCardsMsg(@PathVariable String id) {
		return ownerWeChatBookingManageService.findAllWeChatCards(id);
	}

	/**
	 * 查询预约类型下拉框
	 * 
	 * @return
	 */
	@RequestMapping(value = "/findAllResType", method = RequestMethod.GET)
	@ResponseBody
	public List<Map> findAllResTypeMsg() {
		return ownerWeChatBookingManageService.findAllResType();
	}

	/**
	 * 查询客户经理
	 * 
	 * @return
	 */
	@RequestMapping(value = "/findEmployee", method = RequestMethod.GET)
	@ResponseBody
	public List<Map> findAllEmployeeMsg() {
		return ownerWeChatBookingManageService.findEmployee();
	}
	/**
	 * 查询客户经理
	 * 
	 * @return
	 */
	@RequestMapping(value = "/findEmployees", method = RequestMethod.GET)
	@ResponseBody
	public List<Map> findAllEmployeesMsg() {
		return ownerWeChatBookingManageService.findEmployees();
	}

	/**
	 * 预约确认按钮
	 * 
	 * @param id
	 * @param param
	 * @param uriCB
	 * @return
	 */
	@RequestMapping(value = "/btnComfirm/{id}", method = RequestMethod.PUT)
	public ResponseEntity<String> btnComfirm(@PathVariable String id, @RequestBody Map<String, String> param,
			UriComponentsBuilder uriCB) {
		ownerWeChatBookingManageService.btnComfirm(id,param);
		MultiValueMap<String, String> headers = new HttpHeaders();
		headers.set("Location", uriCB.path("/basedata/ownerWeChatBooking").buildAndExpand().toUriString());
		return new ResponseEntity<String>(headers, HttpStatus.CREATED);
	}
	
	/**
	 * 导出方法
	 * @param sNo
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(value = "/exportExcel", method = RequestMethod.GET)
	public void exportStockIn(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		List<Map> resultList = ownerWeChatBookingManageService.findAllForExcel();
		Map<String, List<Map>> excelData = new HashMap<String, List<Map>>();
		excelData.put("车主微信预约管理", resultList);
		List<ExcelExportColumn> exportColumnList = new ArrayList<ExcelExportColumn>();
		exportColumnList.add(new ExcelExportColumn("RESERVATION_ID", "预约ID"));
		exportColumnList.add(new ExcelExportColumn("RESERVATION_STATUS", "微信预约状态", ExcelDataType.Dict));
		exportColumnList.add(new ExcelExportColumn("RESERVATION_DATE", "预约到店日期", CommonConstants.SIMPLE_DATE_FORMAT));
		exportColumnList.add(new ExcelExportColumn("RESERVATION_PERIOD", "预约到店时间段"));
		exportColumnList.add(new ExcelExportColumn("LINKMAN_NAME", "客户名称"));
		exportColumnList.add(new ExcelExportColumn("LINKMAN_MOBILE", "联系手机"));
		exportColumnList.add(new ExcelExportColumn("LINKMAN_PHONE", "联系电话"));
		exportColumnList.add(new ExcelExportColumn("VIN", "VIN"));
		exportColumnList.add(new ExcelExportColumn("LICENSE", "车牌号"));
		exportColumnList.add(new ExcelExportColumn("BRAND", "品牌"));
		exportColumnList.add(new ExcelExportColumn("SERIES", "车系"));
		exportColumnList.add(new ExcelExportColumn("MODEL", "车型"));
		exportColumnList.add(new ExcelExportColumn("MILEAGE", "当前里程"));
		exportColumnList.add(new ExcelExportColumn("CREATED_AT", "预约生成日期", CommonConstants.FULL_DATE_TIME_FORMAT));
		exportColumnList.add(new ExcelExportColumn("IS_PRE_GENERATED", "是否已预约确认", ExcelDataType.Dict));
		exportColumnList.add(new ExcelExportColumn("RESERV_CONFIRM_DATE", "预约确认日期", CommonConstants.FULL_DATE_TIME_FORMAT));
		exportColumnList.add(new ExcelExportColumn("REMARK", "备注"));
		exportColumnList.add(new ExcelExportColumn("PACKAGE_NAME", "预约保养套餐名称"));
		excelService.generateExcelForDms(excelData, exportColumnList,
				"车主微信预约管理_" + FrameworkUtil.getLoginInfo().getDealerName() + ".xls", request, response);
	}
}
