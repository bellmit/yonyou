package com.yonyou.dms.customer.controller.protectRepair;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

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

import com.yonyou.dms.common.domains.DTO.customer.LossVehicleRemindDTO;
import com.yonyou.dms.common.domains.DTO.customer.RepairExpireRemindDTO;
import com.yonyou.dms.customer.service.protect.protectService;
import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.framework.service.excel.ExcelExportColumn;
import com.yonyou.dms.framework.service.excel.ExcelGenerator;
import com.yonyou.dms.framework.util.FrameworkUtil;
import com.yonyou.dms.function.exception.ServiceBizException;
import com.yonyou.f4.mvc.annotation.TxnConn;
import com.yonyou.f4.mvc.controller.BaseController;
@Controller
@TxnConn
@RequestMapping("/customer/protectRepair")
public class protectRepairController extends BaseController{
	@Autowired
	private protectService protectService;
	@Autowired
	private ExcelGenerator excelGenerator;
	
	/**
	 * 
	 * @param queryParam
	 * @return
	 * @throws SQLException
	 * @throws ServiceBizException
	 */

	@RequestMapping(method = RequestMethod.GET)
	@ResponseBody
	public PageInfoDto queryStreamAnalysis(@RequestParam Map<String, String> queryParam)
			throws ServiceBizException, SQLException {
		return protectService.queryProtectInfo(queryParam);
	}
	

	/**
	 * 保修到期导出
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
		List<Map> resultList = protectService.exportProtectInfo(queryParam);
		@SuppressWarnings("rawtypes")
		Map<String, List<Map>> excelData = new HashMap<String, List<Map>>();
		excelData.put("保修到期提醒表", resultList);
		List<ExcelExportColumn> exportColumnList = new ArrayList<ExcelExportColumn>();
		exportColumnList.add(new ExcelExportColumn("DEALER_CODE", "经销商"));
		exportColumnList.add(new ExcelExportColumn("BOOKING_ORDER_NO", "预约单"));
		exportColumnList.add(new ExcelExportColumn("BOOKING_COME_TIME", "预约进厂时间"));
		exportColumnList.add(new ExcelExportColumn("LICENSE", "车牌号"));
		exportColumnList.add(new ExcelExportColumn("VIN", "VIN"));
		exportColumnList.add(new ExcelExportColumn("BRAND", "品牌"));
		exportColumnList.add(new ExcelExportColumn("SERIES", "车系"));
		exportColumnList.add(new ExcelExportColumn("MODEL", "车型"));
		exportColumnList.add(new ExcelExportColumn("OWNER_NO", "车主编号"));
		exportColumnList.add(new ExcelExportColumn("OWNER_NAME", "车主"));
		exportColumnList.add(new ExcelExportColumn("MOBILE", "车主手机"));
		exportColumnList.add(new ExcelExportColumn("PHONE", "车主电话"));
		exportColumnList.add(new ExcelExportColumn("ADDRESS", "车主地址"));
		exportColumnList.add(new ExcelExportColumn("OWNER_PROPERTY", "车主性质"));
		exportColumnList.add(new ExcelExportColumn("DELIVERER", "送修人"));
		exportColumnList.add(new ExcelExportColumn("DELIVERER_MOBILE", "送修人手机"));
		exportColumnList.add(new ExcelExportColumn("DELIVERER_PHONE", "送修人电话"));
		exportColumnList.add(new ExcelExportColumn("CONTACTOR_NAME", "联系人"));
		exportColumnList.add(new ExcelExportColumn("CONTACTOR_MOBILE", "联系人手机"));
		exportColumnList.add(new ExcelExportColumn("CONTACTOR_PHONE", "联系人电话"));
		exportColumnList.add(new ExcelExportColumn("CONTACTOR_ZIP_CODE", "联系人邮箱"));
		exportColumnList.add(new ExcelExportColumn("CONTACTOR_ADDRESS", "联系人详细地址"));
		exportColumnList.add(new ExcelExportColumn("INSURANCE_BEGIN_DATE", "保修开始日期"));
		exportColumnList.add(new ExcelExportColumn("INSURANCE_END_DATE", "保修结束日期"));
		exportColumnList.add(new ExcelExportColumn("REMINDER_NAME", "提醒人"));
		exportColumnList.add(new ExcelExportColumn("REMIND_STATUS", "提醒状态"));
		exportColumnList.add(new ExcelExportColumn("REMIND_FAIL_REASON", "提醒失败原因"));
		exportColumnList.add(new ExcelExportColumn("REMIND_CONTENT", "提醒内容"));
		exportColumnList.add(new ExcelExportColumn("REMIND_DATE", "提醒时间"));
		exportColumnList.add(new ExcelExportColumn("CUSTOMER_FEEDBACK", "客户反馈"));
		exportColumnList.add(new ExcelExportColumn("SERVICE_ADVISOR", "客户经理"));
		exportColumnList.add(new ExcelExportColumn("SALES_DATE", "销售日期"));
		exportColumnList.add(new ExcelExportColumn("MILEAGE", "进厂里程数"));
		exportColumnList.add(new ExcelExportColumn("REMIND_TYPE", "提醒类型"));
		exportColumnList.add(new ExcelExportColumn("LAST_MAINTAIN_MILEAGE", "最后一次进厂里程"));
		exportColumnList.add(new ExcelExportColumn("IS_BACK", "是否回厂"));
		exportColumnList.add(new ExcelExportColumn("BACK_TIME", "进厂时间"));
		excelGenerator.generateExcelForDms(excelData, exportColumnList,
				FrameworkUtil.getLoginInfo().getDealerShortName() + "_保修到期提醒表.xls", request, response);
	}
	
	/**
	 * 
	 * 新增提醒记录
	 * 
	 * @param vin
	 * @param uriCB
	 * @return
	 */
	@RequestMapping(value = "/vins", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<RepairExpireRemindDTO> addRemind(
			@RequestBody RepairExpireRemindDTO repairExpireRemindDTO, UriComponentsBuilder uriCB)
			throws ServiceBizException {
		protectService.addeRemind(repairExpireRemindDTO.getVin(), repairExpireRemindDTO.getOwnerNo(),
				repairExpireRemindDTO);
		MultiValueMap<String, String> headers = new HttpHeaders();
		headers.set("Location",
				uriCB.path("/customer/protectRepair/{id}").buildAndExpand(repairExpireRemindDTO.getVin()).toUriString());
		return new ResponseEntity<RepairExpireRemindDTO>(repairExpireRemindDTO, headers, HttpStatus.CREATED);
	}
	
	/**
	 * 根据vin进行分页查询提醒记录
	 * 
	 * @param queryParam
	 * @return
	 * @throws ServiceBizException
	 * @throws SQLException
	 */
	@RequestMapping(value = "/remindRecord/{vin}/{owner}", method = RequestMethod.GET)
	@ResponseBody
	public PageInfoDto queryremindre(@PathVariable(value = "vin") String vin,
			@PathVariable(value = "owner") String ownerNo)
			throws ServiceBizException, SQLException {
		return protectService.queryRemindre(vin, ownerNo);
	}
	
	/**
	 * 根据提醒编号回显查询提醒
	 * 
	 * @author
	 * @param remindId
	 * @throws ServiceBizException
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping(value = "/remind/remindedId/{remindId}", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> queryRemindlByRemindId(@PathVariable(value = "remindId") String remindId) throws ServiceBizException {
		List<Map> result = protectService.queryByRemindID(remindId);
		return result.get(0);
	}
	
	/**
	 * 
	 * 修改提醒信息
	 * 
	 * @param ownerNo
	 * @param uriCB
	 * @return
	 */
	@RequestMapping(value = "/remind/update/{remindId}", method = RequestMethod.PUT)
	public ResponseEntity<LossVehicleRemindDTO> ModifyRemindlByRemindId(@PathVariable(value = "remindId") String id,
			@RequestBody @Valid RepairExpireRemindDTO repairExpireRemindDTO,UriComponentsBuilder uriCB) {
		protectService.modifyRemindID(id, repairExpireRemindDTO);
		MultiValueMap<String, String> headers = new HttpHeaders();  
        headers.set("Location", uriCB.path("/customer/protectRepair/remind/{remindId}").buildAndExpand(id).toUriString());  
		return new ResponseEntity<LossVehicleRemindDTO>(headers, HttpStatus.CREATED);  
	}
	
	/**
	 * 查询销售回访 DCRC回访结果
	 * @param queryParam
	 * @return
	 * @throws ServiceBizException
	 * @throws SQLException
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/sales", method = RequestMethod.GET)
	@ResponseBody
	public List<Map> querySales(@RequestParam Map<String, String> queryParam) throws ServiceBizException, SQLException {
		return protectService.querySales(queryParam);
	}
}
