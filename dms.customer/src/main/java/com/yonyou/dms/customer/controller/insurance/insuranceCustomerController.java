package com.yonyou.dms.customer.controller.insurance;

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

import com.yonyou.dms.common.domains.DTO.customer.InsuranceExpireRemindDTO;
import com.yonyou.dms.common.domains.DTO.customer.LossVehicleRemindDTO;
import com.yonyou.dms.common.domains.DTO.customer.OwnerDTO;
import com.yonyou.dms.common.domains.DTO.customer.TermlyMaintainRemindDTO;
import com.yonyou.dms.common.domains.DTO.customer.TmVehicleDTO;
import com.yonyou.dms.customer.service.insurance.insuranceCustomerService;
import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.framework.service.excel.ExcelExportColumn;
import com.yonyou.dms.framework.service.excel.ExcelGenerator;
import com.yonyou.dms.framework.util.FrameworkUtil;
import com.yonyou.dms.function.exception.ServiceBizException;
import com.yonyou.f4.mvc.annotation.TxnConn;
import com.yonyou.f4.mvc.controller.BaseController;

@Controller
@TxnConn
@RequestMapping("/customer/insurance")
public class insuranceCustomerController extends BaseController{
	@Autowired
	private insuranceCustomerService insuranceService;
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
		return insuranceService.queryInsuranceInfo(queryParam);
	}
	
	/**
	 * 查询险种
	 * 
	 * @param queryParam
	 * @return
	 * @throws ServiceBizException
	 * @throws SQLException
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/insuranceTypeName", method = RequestMethod.GET)
	@ResponseBody
	public List<Map> queryAppli(@RequestParam Map<String, String> queryParam) throws ServiceBizException, SQLException {
		return insuranceService.queryInsurersTypeName(queryParam);

	}

	/**
	 * 保险到期导出
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
		List<Map> resultList = insuranceService.exportInsuranceInfo(queryParam);
		@SuppressWarnings("rawtypes")
		Map<String, List<Map>> excelData = new HashMap<String, List<Map>>();
		excelData.put("保险到期提醒表", resultList);
		List<ExcelExportColumn> exportColumnList = new ArrayList<ExcelExportColumn>();
		exportColumnList.add(new ExcelExportColumn("DEALER_CODE", "经销商"));
		exportColumnList.add(new ExcelExportColumn("INSURANCE_TYPE_NAME", "险种"));
		exportColumnList.add(new ExcelExportColumn("LICENSE", "车牌号"));
		exportColumnList.add(new ExcelExportColumn("VIN", "VIN"));
		exportColumnList.add(new ExcelExportColumn("BRAND", "品牌"));
		exportColumnList.add(new ExcelExportColumn("SERIES", "车系"));
		exportColumnList.add(new ExcelExportColumn("MODEL", "车型"));
		exportColumnList.add(new ExcelExportColumn("OWNER_NAME", "车主"));
		exportColumnList.add(new ExcelExportColumn("OWNER_NO", "车主编号"));
		exportColumnList.add(new ExcelExportColumn("PHONE", "车主电话"));
		exportColumnList.add(new ExcelExportColumn("MOBILE", "车主手机"));
		exportColumnList.add(new ExcelExportColumn("ADDRESS", "车主地址"));
		exportColumnList.add(new ExcelExportColumn("OWNER_PROPERTY", "车主性质"));
		exportColumnList.add(new ExcelExportColumn("DELIVERER", "送修人"));
		exportColumnList.add(new ExcelExportColumn("DELIVERER_MOBILE", "送修人手机"));
		exportColumnList.add(new ExcelExportColumn("CONTACTOR_NAME", "联系人"));
		exportColumnList.add(new ExcelExportColumn("CONTACTOR_MOBILE", "联系人手机"));
		exportColumnList.add(new ExcelExportColumn("CONTACTOR_PHONE", "联系人电话"));
		exportColumnList.add(new ExcelExportColumn("CONTACTOR_ZIP_CODE", "联系人邮箱"));
		exportColumnList.add(new ExcelExportColumn("CONTACTOR_ADDRESS", "联系人详细地址"));
		exportColumnList.add(new ExcelExportColumn("INSURATION_SHORT_NAME", "保修公司"));
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
		exportColumnList.add(new ExcelExportColumn("IS_VALID", "是否激活"));
		exportColumnList.add(new ExcelExportColumn("IS_SELF_COMPANY_INSURANCE", "是否本公司投保"));
		exportColumnList.add(new ExcelExportColumn("MAINTAIN_ADVISOR", "续保专员"));
		exportColumnList.add(new ExcelExportColumn("DCRC_ADVISOR", "DCRC专员"));
		exportColumnList.add(new ExcelExportColumn("DCRC_ADVISOR", "是否提醒回厂"));
		excelGenerator.generateExcelForDms(excelData, exportColumnList,
				FrameworkUtil.getLoginInfo().getDealerShortName() + "_保险到期提醒表.xls", request, response);
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
		List<Map> result = insuranceService.queryOwnerNoByid(id);
		return result.get(0);
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
		insuranceService.updateOwner(ownerNo, ownerDTO);
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
		insuranceService.updateVehicle(vin, tmVehicleDTO);
		MultiValueMap<String, String> headers = new HttpHeaders();
		headers.set("Location", uriCB.path("/customer/solicitude/vehicle/{vin}").buildAndExpand(vin).toUriString());
		return new ResponseEntity<TmVehicleDTO>(headers, HttpStatus.CREATED);
	}
	
	/**
	 * 查询保险保修
	 * @param queryParam
	 * @return
	 * @throws SQLException
	 * @throws ServiceBizException
	 */

	@RequestMapping(value = "/insurance/{vin}",method = RequestMethod.GET)
	@ResponseBody
	public PageInfoDto queryInsurance(@RequestParam Map<String, String> queryParam,@PathVariable(value = "vin") String vin)
			throws ServiceBizException, SQLException {
		return insuranceService.queryInsuranceTypeNameInfo(queryParam,vin);
	}
	

	/**
	 * 根据vin、车主编号回显查询提醒
	 * @param vin
	 * @param license
	 * @return
	 * @throws ServiceBizException
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping(value = "/dict/{vin}/duct/{ownerNo}", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> queryRemindlById(@PathVariable(value = "vin") String vin,
			@PathVariable(value = "ownerNo") String ownerNo) throws ServiceBizException {
		System.out.println(vin + "*****" + ownerNo + "*************");
		List<Map> result = insuranceService.queryVehicelByid(vin, ownerNo);
		return result.get(0);
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
	public ResponseEntity<InsuranceExpireRemindDTO> addRemind(
			@RequestBody InsuranceExpireRemindDTO insuranceExpireRemindDTO, UriComponentsBuilder uriCB)
			throws ServiceBizException {
		insuranceService.addeRemind(insuranceExpireRemindDTO.getVin(), insuranceExpireRemindDTO.getOwnerNo(),
				insuranceExpireRemindDTO);
		MultiValueMap<String, String> headers = new HttpHeaders();
		headers.set("Location",
				uriCB.path("/customer/insurance/{id}").buildAndExpand(insuranceExpireRemindDTO.getVin()).toUriString());
		return new ResponseEntity<InsuranceExpireRemindDTO>(insuranceExpireRemindDTO, headers, HttpStatus.CREATED);
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
	public PageInfoDto queryremindre(@PathVariable(value = "vin") String vin,@PathVariable(value = "owner") String ownerNo)
			throws ServiceBizException, SQLException {
		return insuranceService.queryRemindre(vin, ownerNo);
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
		List<Map> result = insuranceService.queryByRemindID(remindId);
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
	public ResponseEntity<InsuranceExpireRemindDTO> ModifyRemindlByRemindId(@PathVariable(value = "remindId") String id,
			@RequestBody @Valid InsuranceExpireRemindDTO insuranceExpireRemindDTO,UriComponentsBuilder uriCB) {
				insuranceService.modifyRemindID(id, insuranceExpireRemindDTO);
		MultiValueMap<String, String> headers = new HttpHeaders();  
        headers.set("Location", uriCB.path("/customer/insurance/remind/{remindId}").buildAndExpand(id).toString());  
		return new ResponseEntity<InsuranceExpireRemindDTO>(headers, HttpStatus.CREATED);  
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
		return insuranceService.querySales(queryParam);
	}
	
}
