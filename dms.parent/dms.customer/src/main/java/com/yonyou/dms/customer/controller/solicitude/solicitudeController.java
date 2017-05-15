package com.yonyou.dms.customer.controller.solicitude;

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
import com.yonyou.dms.common.domains.DTO.customer.OwnerDTO;
import com.yonyou.dms.common.domains.DTO.customer.TermlyMaintainRemindDTO;
import com.yonyou.dms.common.domains.DTO.customer.TmVehicleDTO;
import com.yonyou.dms.customer.service.solicitude.solicitudeService;
import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.framework.service.excel.ExcelDataType;
import com.yonyou.dms.framework.service.excel.ExcelExportColumn;
import com.yonyou.dms.framework.service.excel.ExcelGenerator;
import com.yonyou.dms.framework.util.FrameworkUtil;
import com.yonyou.dms.function.exception.ServiceBizException;
import com.yonyou.f4.mvc.annotation.TxnConn;
import com.yonyou.f4.mvc.controller.BaseController;

@Controller
@TxnConn
@RequestMapping("/customer/solicitude")
public class solicitudeController extends BaseController {
	@Autowired
	private solicitudeService solicitudeService;
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
		return solicitudeService.querySolicitudeInfo(queryParam);
	}

	/**
	 * 查询DCRC专员
	 * 
	 * @param queryParam
	 * @return
	 * @throws ServiceBizException
	 * @throws SQLException
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/dcrc", method = RequestMethod.GET)
	@ResponseBody
	public List<Map> queryAppli(@RequestParam Map<String, String> queryParam) throws ServiceBizException, SQLException {
		return solicitudeService.queryApplicant(queryParam);

	}

	/**
	 * 定期保养提醒导出
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
		List<Map> resultList = solicitudeService.exportSolicitudeInfo(queryParam);
		@SuppressWarnings("rawtypes")
		Map<String, List<Map>> excelData = new HashMap<String, List<Map>>();
		excelData.put("定期保养提醒表", resultList);
		List<ExcelExportColumn> exportColumnList = new ArrayList<ExcelExportColumn>();
		exportColumnList.add(new ExcelExportColumn("DEALER_CODE", "经销商"));
		exportColumnList.add(new ExcelExportColumn("LICENSE", "车牌号"));
		exportColumnList.add(new ExcelExportColumn("VIN", "VIN"));
		exportColumnList.add(new ExcelExportColumn("IS_SUCCESS_REMIND", "是否回厂"));
		exportColumnList.add(new ExcelExportColumn("BRAND", "品牌"));
		exportColumnList.add(new ExcelExportColumn("SERIES", "车系"));
		exportColumnList.add(new ExcelExportColumn("MODEL", "车型"));
		exportColumnList.add(new ExcelExportColumn("OWNER_NO", "车主编号"));
		exportColumnList.add(new ExcelExportColumn("OWNER_NAME", "车主"));
		exportColumnList.add(new ExcelExportColumn("PHONE", "车主电话"));
		exportColumnList.add(new ExcelExportColumn("MOBILE", "车主手机"));
		exportColumnList.add(new ExcelExportColumn("ADDRESS", "车主地址"));
		exportColumnList.add(new ExcelExportColumn("OWNER_PROPERTY", "车主性质"));
		exportColumnList.add(new ExcelExportColumn("DELIVERER", "送修人"));
		exportColumnList.add(new ExcelExportColumn("DELIVERER_MOBILE", "送修人手机"));
		exportColumnList.add(new ExcelExportColumn("DELIVERER_PHONE", "送修人电话"));
		exportColumnList.add(new ExcelExportColumn("CONTACTOR_NAME", "联系人"));
		exportColumnList.add(new ExcelExportColumn("CONTACTOR_MOBILE", "联系人手机"));
		exportColumnList.add(new ExcelExportColumn("CONTACTOR_PHONE", "联系人电话"));
		exportColumnList.add(new ExcelExportColumn("CONTACTOR_ZIP_CODE", "联系人邮编"));
		exportColumnList.add(new ExcelExportColumn("CONTACTOR_ADDRESS", "联系人详细地址"));
		exportColumnList.add(new ExcelExportColumn("LAST_MAINTAIN_DATE", "上次维修时间"));
		exportColumnList.add(new ExcelExportColumn("LAST_MAINTAIN_MILEAGE", "上次维修里程"));
		exportColumnList.add(new ExcelExportColumn("NEXT_MAINTAIN_DATE", "预计下次保养日期"));
		exportColumnList.add(new ExcelExportColumn("NEXT_MAINTAIN_MILEAGE", "下次保养里程"));
		exportColumnList.add(new ExcelExportColumn("REMINDER_NAME", "提醒人"));
		exportColumnList.add(new ExcelExportColumn("REMIND_CONTENT", "提醒内容"));
		exportColumnList.add(new ExcelExportColumn("REMIND_STATUS", "提醒状态"));
		exportColumnList.add(new ExcelExportColumn("REMIND_FAIL_REASON", "提醒失败原因"));
		exportColumnList.add(new ExcelExportColumn("REMIND_DATE", "最后一次提醒时间"));
		exportColumnList.add(new ExcelExportColumn("CUSTOMER_FEEDBACK", "客户反馈"));
		exportColumnList.add(new ExcelExportColumn("SERVICE_ADVISOR", "客户经理"));
		exportColumnList.add(new ExcelExportColumn("SALES_DATE", "销售日期"));
		exportColumnList.add(new ExcelExportColumn("IS_VALID", "是否激活"));
		exportColumnList.add(new ExcelExportColumn("DISCOUNT_EXPIRE_DATE", "优惠截至日期"));
		exportColumnList.add(new ExcelExportColumn("VIP_NO", "VIP编号"));
		exportColumnList.add(new ExcelExportColumn("MAINTAIN_ADVISOR", "定保专员"));
		exportColumnList.add(new ExcelExportColumn("LAST_MAINTENANCE_DATE", "上次保养日期"));
		exportColumnList.add(new ExcelExportColumn("LAST_MAINTENANCE_MILEAGE", "上次保养里程"));
		exportColumnList.add(new ExcelExportColumn("REMIND_WAY", "提醒方式"));
		exportColumnList.add(new ExcelExportColumn("DCRC_ADVISOR", "DCRC专员"));
		exportColumnList.add(new ExcelExportColumn("REMIND_DATE_S", "最近两次提醒时间"));
		exportColumnList.add(new ExcelExportColumn("REMIND_DATE_T", "最近三次提醒时间"));
		exportColumnList.add(new ExcelExportColumn("RO_CREATE_DATE", "上次维修时间"));
		excelGenerator.generateExcelForDms(excelData, exportColumnList,
				FrameworkUtil.getLoginInfo().getDealerShortName() + "_定期保养提醒.xls", request, response);
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
	public ResponseEntity<TermlyMaintainRemindDTO> addRemind(
			@RequestBody TermlyMaintainRemindDTO termlyMaintainRemindDTO, UriComponentsBuilder uriCB)
			throws ServiceBizException {
		solicitudeService.addeRemind(termlyMaintainRemindDTO.getVin(), termlyMaintainRemindDTO.getOwnerNo(),
				termlyMaintainRemindDTO);
		MultiValueMap<String, String> headers = new HttpHeaders();
		headers.set("Location",
				uriCB.path("/customer/solicitude/{id}").buildAndExpand(termlyMaintainRemindDTO.getVin()).toUriString());
		return new ResponseEntity<TermlyMaintainRemindDTO>(termlyMaintainRemindDTO, headers, HttpStatus.CREATED);
	}
	
	

	/**
	 * 根据车辆VIN回显查询提醒
	 * 
	 * @author
	 * @param id,license
	 * @throws ServiceBizException
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping(value = "/dict/{vin}/duct/{license}", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> queryRemindlById(@PathVariable(value = "vin") String vin,
			@PathVariable(value = "license") String license) throws ServiceBizException {
		System.out.println(vin + "*****" + license + "*************");
		List<Map> result = solicitudeService.queryVehicelByid(vin, license);
		return result.get(0);
	}

	/**
	 * 根据车主编号回显查询车主信息
	 * 
	 * @author
	 * @param id
	 * @throws ServiceBizException
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping(value = "/{ownerNo}", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> queryOwnerById(@PathVariable(value = "ownerNo") String id) throws ServiceBizException {
		List<Map> result = solicitudeService.queryOwnerNOByid(id);
		return result.get(0);
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
		return solicitudeService.queryRemindre(vin, ownerNo);
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
		List<Map> result = solicitudeService.queryByRemindID(remindId);
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
	public ResponseEntity<LossVehicleRemindDTO> ModifyRemindlByRemindId(@PathVariable(value = "remindId") String id,@RequestBody @Valid TermlyMaintainRemindDTO termlyMaintainRemindDTO,UriComponentsBuilder uriCB) {
		solicitudeService.modifyRemindID(id, termlyMaintainRemindDTO);
		MultiValueMap<String, String> headers = new HttpHeaders();  
        headers.set("Location", uriCB.path("/customer/solicitude/remind/{remindId}").buildAndExpand(id).toUriString());  
		return new ResponseEntity<LossVehicleRemindDTO>(headers, HttpStatus.CREATED);  
	}

	/**
	 * 查询销售回访信息
	 * 
	 * @param queryParam
	 * @return
	 * @throws ServiceBizException
	 * @throws SQLException
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/sales", method = RequestMethod.GET)
	@ResponseBody
	public List<Map> querySales(@RequestParam Map<String, String> queryParam) throws ServiceBizException, SQLException {
		return solicitudeService.querySales(queryParam);
	}

	/**
	 * 
	 * 修改车主信息
	 * 
	 * @param vin
	 * @param uriCB
	 * @return
	 */
	@RequestMapping(value = "/{ownerNo}", method = RequestMethod.PUT)
	@ResponseBody
	public ResponseEntity<OwnerDTO> updateOwner(@PathVariable(value = "ownerNo") String ownerNo,
			@RequestBody @Valid OwnerDTO ownerDTO, UriComponentsBuilder uriCB) throws ServiceBizException {
		solicitudeService.updateOwner(ownerNo, ownerDTO);
		MultiValueMap<String, String> headers = new HttpHeaders();
		headers.set("Location", uriCB.path("/customer/solicitude/{ownerNo}").buildAndExpand(ownerNo).toUriString());
		return new ResponseEntity<OwnerDTO>(headers, HttpStatus.CREATED);
	}

	/**
	 * 
	 * 修改车辆信息
	 * 
	 * @param vin
	 * @param uriCB
	 * @return
	 */
	@RequestMapping(value = "/vehicle/{vin}", method = RequestMethod.PUT)
	public ResponseEntity<TmVehicleDTO> updateVehicle(@PathVariable(value = "vin") String vin,
			@RequestBody @Valid TmVehicleDTO tmVehicleDTO, UriComponentsBuilder uriCB) throws ServiceBizException {
		solicitudeService.updateVehicle(vin, tmVehicleDTO);
		MultiValueMap<String, String> headers = new HttpHeaders();
		headers.set("Location", uriCB.path("/customer/solicitude/vehicle/{vin}").buildAndExpand(vin).toUriString());
		return new ResponseEntity<TmVehicleDTO>(headers, HttpStatus.CREATED);
	}

	/**
	 * 查询维修项目
	 * 
	 * @param queryParam
	 * @return
	 * @throws SQLException
	 * @throws ServiceBizException
	 */

	@RequestMapping(value = "/history", method = RequestMethod.GET)
	@ResponseBody
	public PageInfoDto queryMaintainProject(@RequestParam Map<String, String> queryParam)
			throws ServiceBizException, SQLException {
		return solicitudeService.queryMaintainHistory(queryParam);
	}

	/**
	 * 查询维修历史
	 * 
	 * @param queryParam
	 * @return
	 * @throws ServiceBizException
	 * @throws SQLException
	 */
	@RequestMapping(value = "/maintainhistory", method = RequestMethod.GET)
	@ResponseBody
	public PageInfoDto queryMaintainHistory(@RequestParam Map<String, String> queryParam)
			throws ServiceBizException, SQLException {
		return solicitudeService.queryMaintainhistory(queryParam);
	}

	/**
	 * 查询维修配件
	 * 
	 * @param queryParam
	 * @return
	 * @throws ServiceBizException
	 * @throws SQLException
	 */
	@RequestMapping(value = "/repairType/part", method = RequestMethod.GET)
	@ResponseBody
	public PageInfoDto queryPart(@RequestParam Map<String, String> queryParam)
			throws ServiceBizException, SQLException {
		return solicitudeService.queryPart(queryParam);
	}

	/**
	 * 查询维修类型
	 * 
	 * @param queryParam
	 * @return
	 * @throws ServiceBizException
	 * @throws SQLException
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/repairType", method = RequestMethod.GET)
	@ResponseBody
	public List<Map> queryRepairType(@RequestParam Map<String, String> queryParam)
			throws ServiceBizException, SQLException {
		return solicitudeService.queryRepairType(queryParam);

	}

	/**
	 * 根据车主编号回显查询工单明细
	 * 
	 * @author
	 * @param id
	 * @throws ServiceBizException
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping(value = "/{ownerNo}/account/{roNo}/sale/{salesPartNo}", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> queryRoNoById(@PathVariable(value = "ownerNo") String id,
			@PathVariable(value = "roNo") String roNo, @PathVariable(value = "salesPartNo") String salesPartNo)
			throws ServiceBizException {
		List<Map> result = solicitudeService.queryRoNoByid(id, roNo, salesPartNo);
		return result.get(0);
	}

	/**
	 * 查询工单明细维修项目明细
	 * @param queryParam
	 * @return
	 * @throws SQLException
	 * @throws ServiceBizException
	 */

	@RequestMapping(value = "/maintainHistory/maintainHistoryProject/{roNo}/MaintainProject",method = RequestMethod.GET)
	@ResponseBody
	public PageInfoDto queryMainProject(@RequestParam Map<String, String> queryParam, @PathVariable(value="roNo") String roNo)
			throws ServiceBizException, SQLException {
		return solicitudeService.queryMainProject(queryParam,roNo);
	}
	
	/**
	 * 查询工单维修材料明细
	 * @param queryParam
	 * @return
	 * @throws SQLException
	 * @throws ServiceBizException
	 */

	@RequestMapping(value = "/maintainHistory/maintainHistoryProject/{roNo}/MaintainMaterial",method = RequestMethod.GET)
	@ResponseBody
	public PageInfoDto queryMaintainMaterial(@RequestParam Map<String, String> queryParam, @PathVariable(value="roNo") String roNo)
			throws ServiceBizException, SQLException {
		return solicitudeService.queryMaintainMaterial(queryParam,roNo);
	}
	
	/**
	 * 查询工单销售材料明细
	 * @param queryParam
	 * @return
	 * @throws SQLException
	 * @throws ServiceBizException
	 */

	@RequestMapping(value = "/maintainHistory/maintainHistoryProject/{roNo}/SellMaterial",method = RequestMethod.GET)
	@ResponseBody
	public PageInfoDto querySellMaterial(@RequestParam Map<String, String> queryParam, @PathVariable(value="roNo") String roNo)
			throws ServiceBizException, SQLException {
		return solicitudeService.querySellMaterial(queryParam,roNo);
	}
	
	/**
	 * 查询工单附加项目明细
	 * @param queryParam
	 * @return
	 * @throws SQLException
	 * @throws ServiceBizException
	 */

	@RequestMapping(value = "/maintainHistory/maintainHistoryProject/{roNo}/AccessoryProject",method = RequestMethod.GET)
	@ResponseBody
	public PageInfoDto queryAccessoryPro(@RequestParam Map<String, String> queryParam, @PathVariable(value="roNo") String roNo)
			throws ServiceBizException, SQLException {
		return solicitudeService.queryAccessoryProject(queryParam,roNo);
	}
	
	/**
	 * 查询工单辅料费明细
	 * @param queryParam
	 * @return
	 * @throws SQLException
	 * @throws ServiceBizException
	 */

	@RequestMapping(value = "/maintainHistory/maintainHistoryProject/{roNo}/AccessoryManage",method = RequestMethod.GET)
	@ResponseBody
	public PageInfoDto queryAccessoryManage(@RequestParam Map<String, String> queryParam, @PathVariable(value="roNo") String roNo)
			throws ServiceBizException, SQLException {
		return solicitudeService.queryAccessoryManage(queryParam,roNo);
	}
	
	/**
	 * 查询工单管理费明细
	 * @param queryParam
	 * @return
	 * @throws SQLException
	 * @throws ServiceBizException
	 */

	@RequestMapping(value = "/maintainHistory/maintainHistoryProject/{roNo}/Manage",method = RequestMethod.GET)
	@ResponseBody
	public PageInfoDto queryManage(@RequestParam Map<String, String> queryParam, @PathVariable(value="roNo") String roNo)
			throws ServiceBizException, SQLException {
		return solicitudeService.queryManage(queryParam,roNo);
	}
	
	/**
	 * 查询工单派工明细
	 * @param queryParam
	 * @return
	 * @throws SQLException
	 * @throws ServiceBizException
	 */

	@RequestMapping(value = "/maintainHistory/maintainHistoryProject/{roNo}/assign",method = RequestMethod.GET)
	@ResponseBody
	public PageInfoDto queryAssign(@RequestParam Map<String, String> queryParam, @PathVariable(value="roNo") String roNo)
			throws ServiceBizException, SQLException {
		return solicitudeService.queryAssign(queryParam,roNo);
	}
	
	/**
	 * 根据发动机号、车牌号、车主地址查询建议维修项目
	 * 
	 * @param queryParam
	 * @return
	 * @throws ServiceBizException
	 * @throws SQLException
	 */
	@RequestMapping(value = "/project", method = RequestMethod.GET)
	@ResponseBody
	public PageInfoDto QueryRepairSuggest(@RequestParam Map<String, String> queryParam)
			throws ServiceBizException, SQLException {
		return solicitudeService.QueryRepairProject(queryParam);
	}
	
	/**
	 * 根据发动机号、车牌号、车主地址查询建议维修配件
	 * 
	 * @param queryParam
	 * @return
	 * @throws ServiceBizException
	 * @throws SQLException
	 */
	@RequestMapping(value = "/part", method = RequestMethod.GET)
	@ResponseBody
	public PageInfoDto QueryRepairPart(@RequestParam Map<String, String> queryParam) throws ServiceBizException, SQLException {
		return solicitudeService.QueryRepairPart(queryParam);
	}
	
	/**
	 * 根据vin查询客户投诉
	 * 
	 * @param queryParam
	 * @return
	 * @throws ServiceBizException
	 * @throws SQLException
	 */
	@RequestMapping(value = "/complaint", method = RequestMethod.GET)
	@ResponseBody
	public PageInfoDto QueryComplaint(@RequestParam Map<String, String> queryParam) throws ServiceBizException, SQLException {
		return solicitudeService.queryComplaint(queryParam);
	}
	
	/**
	 * 根据投诉编号回显查询投诉明细
	 * 
	 * @author
	 * @param id
	 * @throws ServiceBizException
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping(value = "/comp/{complaintNo}", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> quercomplaintNoById(@PathVariable(value = "complaintNo") String complaintNo)
			throws ServiceBizException {
		List<Map> result = solicitudeService.querycomplaintNoById(complaintNo);
		return result.get(0);
	}
	
	/**
	 * 根据查询客户投诉处理明细
	 * 
	 * @param queryParam
	 * @return
	 * @throws ServiceBizException
	 * @throws SQLException
	 */
	@RequestMapping(value = "/dispose/{complaintNo}", method = RequestMethod.GET)
	@ResponseBody
	public PageInfoDto queryDispose(@PathVariable(value = "complaintNo") String complaintNo,@RequestParam Map<String, String> queryParam) throws ServiceBizException, SQLException {
		return solicitudeService.queryDispose(queryParam);
	}
	
}	
