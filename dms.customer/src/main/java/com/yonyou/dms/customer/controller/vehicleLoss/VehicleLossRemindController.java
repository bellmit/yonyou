package com.yonyou.dms.customer.controller.vehicleLoss;

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
import com.yonyou.dms.customer.service.vehicleLoss.VehicleLossRemindService;
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
@RequestMapping("/customer/vehicleLossRemind")
public class VehicleLossRemindController extends BaseController{

	@Autowired
	private VehicleLossRemindService vehicleLossRemindService ;
	
	@Autowired
	private ExcelGenerator excelGenerator;
	/**
	 * 
	 * @param queryParam
	 * @return
	 */
	@RequestMapping(method = RequestMethod.GET)
	@ResponseBody
	public PageInfoDto queryVehicleLossRemind(@RequestParam Map<String, String> queryParam) {
			return vehicleLossRemindService.queryVehicleLossRemind(queryParam);
	}
	/**
	 * 根据车主编号回显查询
	 * 
	 * @author
	 * @param id
	 * @throws ServiceBizException
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping(value = "/{ownerNo}", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> queryOwnerById(@PathVariable(value = "ownerNo") String id) throws ServiceBizException {
		List<Map> result = vehicleLossRemindService.queryOwnerNOByid(id);
		return result.get(0);
	}
	
	/**
	 * 
	 * 修改车主信息
	 * 
	 * @param ownerNo
	 * @param uriCB
	 * @return
	 */
	@RequestMapping(value = "/{ownerNo}", method = RequestMethod.PUT)
	public ResponseEntity<OwnerDTO> ModifyOwnerById(@PathVariable(value = "ownerNo") String id,@RequestBody @Valid OwnerDTO ownerDto,UriComponentsBuilder uriCB) {
		vehicleLossRemindService.modifyOwnerNOByid(id, ownerDto);
		MultiValueMap<String, String> headers = new HttpHeaders();  
        headers.set("Location", uriCB.path("/customer/vehicleLossRemind/{ownerNo}").buildAndExpand(id).toUriString());  
		return new ResponseEntity<OwnerDTO>(headers, HttpStatus.CREATED);  
	}
	
	/**
	 * 根据车辆VIN回显查询提醒
	 * 
	 * @author
	 * @param vin
	 * @throws ServiceBizException
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping(value = "/dit/{vin}", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> queryRemindlById(@PathVariable(value = "vin") String vin) throws ServiceBizException {
		List<Map> result = vehicleLossRemindService.queryVehicelByVin(vin);
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
	public ResponseEntity<LossVehicleRemindDTO> addRemind(@RequestBody LossVehicleRemindDTO lossVehicleRemindDTO, UriComponentsBuilder uriCB)
			throws ServiceBizException {
		vehicleLossRemindService.addRemind(lossVehicleRemindDTO);
		MultiValueMap<String, String> headers = new HttpHeaders();
		headers.set("Location",
				uriCB.path("/customer/vehicleLossRemind/vins").buildAndExpand(lossVehicleRemindDTO.getVin()).toUriString());
		return new ResponseEntity<LossVehicleRemindDTO>(lossVehicleRemindDTO, headers, HttpStatus.CREATED);
	}
	
	/**
	 * 根据vin进行回显查询提醒记录
	 * 
	 * @param queryParam
	 * @return
	 * @throws ServiceBizException
	 * @throws SQLException
	 */
	@RequestMapping(value = "/remindRecord/{vin}/{owner}", method = RequestMethod.GET)
	@ResponseBody
	public PageInfoDto queryremindre(@PathVariable(value = "vin") String vin,
			@PathVariable(value = "owner") String ownerNo) throws ServiceBizException, SQLException {
		return vehicleLossRemindService.queryRemindre(vin, ownerNo);
	}
	
	/**
	 * 根据提醒編號回显查询提醒
	 * 
	 * @author
	 * @param remindId
	 * @throws ServiceBizException
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping(value = "/remind/{remindId}", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> queryRemindlByRemindId(@PathVariable(value = "remindId") String remindId) throws ServiceBizException {
		List<Map> result = vehicleLossRemindService.queryRemindID(remindId);
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
	@RequestMapping(value = "/remind/{remindId}", method = RequestMethod.PUT)
	public ResponseEntity<LossVehicleRemindDTO> ModifyRemindlByRemindId(@PathVariable(value = "remindId") String id,@RequestBody @Valid LossVehicleRemindDTO lossVehicleRemindDTO,UriComponentsBuilder uriCB) {
		vehicleLossRemindService.modifyRemindID(id, lossVehicleRemindDTO);
		MultiValueMap<String, String> headers = new HttpHeaders();  
        headers.set("Location", uriCB.path("/customer/vehicleLossRemind/remind/{remindId}").buildAndExpand(id).toUriString());  
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
		return vehicleLossRemindService.querySales(queryParam);

	}
	/**
	 * 查询维修建议项目
	 * 
	 * @param queryParam
	 * @return
	 * @throws ServiceBizException
	 * @throws SQLException
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/repairSuggest", method = RequestMethod.GET)
	@ResponseBody
	public PageInfoDto QueryRepairSuggest(@RequestParam Map<String, String> queryParam) throws ServiceBizException, SQLException {
		List<Map> list = vehicleLossRemindService.queryVehicleLossRemindByVin(queryParam);
		
		return vehicleLossRemindService.QueryRepairSuggest(list.get(0));

	}
	/**
	 * 查询维修建议项目
	 * 
	 * @param queryParam
	 * @return
	 * @throws ServiceBizException
	 * @throws SQLException
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/repair", method = RequestMethod.GET)
	@ResponseBody
	public PageInfoDto QueryRepair(@RequestParam Map<String, String> queryParam) throws ServiceBizException, SQLException {
		List<Map> list = vehicleLossRemindService.queryVehicleLossRemindByVin(queryParam);
		
		return vehicleLossRemindService.QueryRepair(list.get(0));

	}
	
	/**
	 * 查询投诉历史
	 * 
	 * @param queryParam
	 * @return
	 * @throws ServiceBizException
	 * @throws SQLException
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/complaint", method = RequestMethod.GET)
	@ResponseBody
	public PageInfoDto QueryComplaint(@RequestParam Map<String, String> queryParam) throws ServiceBizException, SQLException {
		List<Map> list = vehicleLossRemindService.queryVehicleLossRemindByVin(queryParam);
		
		return vehicleLossRemindService.QueryComplaint(list.get(0));

	}
	
	
	/**
	 * 导出客户流失报警
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
		List<Map> resultList = vehicleLossRemindService.exportVehicleLossRemind(queryParam);
		Map<String, List<Map>> excelData = new HashMap<String, List<Map>>();
		excelData.put("客户流失报警表", resultList);
		List<ExcelExportColumn> exportColumnList = new ArrayList<ExcelExportColumn>();
		exportColumnList.add(new ExcelExportColumn("dealer_shortname", "经销商"));
		exportColumnList.add(new ExcelExportColumn("LICENSE", "车牌号"));
		exportColumnList.add(new ExcelExportColumn("OWNER_NAME", "车主"));
		exportColumnList.add(new ExcelExportColumn("IS_SUCCESS_REMIND", "是否回厂", ExcelDataType.Dict));
		exportColumnList.add(new ExcelExportColumn("PHONE", "车主电话"));
		exportColumnList.add(new ExcelExportColumn("MOBILE", "车主手机"));
		exportColumnList.add(new ExcelExportColumn("ADDRESS", "车主地址"));
		exportColumnList.add(new ExcelExportColumn("OWNER_PROPERTY", "车主性质", ExcelDataType.Dict));
		exportColumnList.add(new ExcelExportColumn("PROVINCE", "省份"));
		exportColumnList.add(new ExcelExportColumn("CITY", "城市",ExcelDataType.Region_City));
		exportColumnList.add(new ExcelExportColumn("DISTRICT", "区县"));
		exportColumnList.add(new ExcelExportColumn("CONSULTANT", "销售顾问"));
		exportColumnList.add(new ExcelExportColumn("CONTACTOR_NAME", "联系人"));
		exportColumnList.add(new ExcelExportColumn("CONTACTOR_MOBILE", "联系人手机"));
		exportColumnList.add(new ExcelExportColumn("CONTACTOR_PHONE", "联系人电话"));
		exportColumnList.add(new ExcelExportColumn("CONTACTOR_ZIP_CODE", "联系人邮编"));
		exportColumnList.add(new ExcelExportColumn("CONTACTOR_ADDRESS", "联系人详细地址"));
		exportColumnList.add(new ExcelExportColumn("CONTACTOR_PROVINCE", "联系人省份"));
		exportColumnList.add(new ExcelExportColumn("CONTACTOR_CITY", "联系人城市",ExcelDataType.Region_City));
		exportColumnList.add(new ExcelExportColumn("CONTACTOR_DISTRICT", "联系人区县"));
		exportColumnList.add(new ExcelExportColumn("VIN", "VIN"));
		exportColumnList.add(new ExcelExportColumn("DELIVERER_PHONE", "送修人电话"));
		exportColumnList.add(new ExcelExportColumn("DELIVERER_MOBILE", "送修人手机"));
		exportColumnList.add(new ExcelExportColumn("LAST_MAINTAIN_DATE", "上次维修日期"));
		exportColumnList.add(new ExcelExportColumn("LAST_SERVICE_ADVISOR", "上次维修工单的服务专员"));
		exportColumnList.add(new ExcelExportColumn("SALES_DATE", "销售日期"));
		exportColumnList.add(new ExcelExportColumn("NEXT_MAINTAIN_DATE", "预计下次保养日期"));
		exportColumnList.add(new ExcelExportColumn("NEXT_MAINTAIN_MILEAGE", "下次保养里程"));
		exportColumnList.add(new ExcelExportColumn("REMINDER", "提醒人"));
		exportColumnList.add(new ExcelExportColumn("REMIND_STATUS", "提醒状态", ExcelDataType.Dict));
		exportColumnList.add(new ExcelExportColumn("REMIND_FAIL_REASON", "提醒失败原因", ExcelDataType.Dict));
		exportColumnList.add(new ExcelExportColumn("REMIND_CONTENT", "提醒内容"));
		exportColumnList.add(new ExcelExportColumn("REMIND_WAY", "提醒方式", ExcelDataType.Dict));
		exportColumnList.add(new ExcelExportColumn("REMIND_DATE", "提醒时间"));
		exportColumnList.add(new ExcelExportColumn("CUSTOMER_FEEDBACK", "客户反馈"));
		exportColumnList.add(new ExcelExportColumn("brand_name", "品牌"));
		exportColumnList.add(new ExcelExportColumn("series_name", "车系"));
		exportColumnList.add(new ExcelExportColumn("MODEL_NAME", "车型"));
		exportColumnList.add(new ExcelExportColumn("OWNER_NO", "车主编号"));
		exportColumnList.add(new ExcelExportColumn("ON_REPAIR", "是否在修", ExcelDataType.Dict));
		exportColumnList.add(new ExcelExportColumn("SERVICE_ADVISOR_NAME", "服务专员"));
		exportColumnList.add(new ExcelExportColumn("IS_SELF_COMPANY", "是否本公司购车", ExcelDataType.Dict));
		exportColumnList.add(new ExcelExportColumn("MILEAGE", "行驶里程"));
		exportColumnList.add(new ExcelExportColumn("FIRST_IN_DATE", "第一次进厂日期"));
		exportColumnList.add(new ExcelExportColumn("TRACE_TAG", "是否分派", ExcelDataType.Dict));
		exportColumnList.add(new ExcelExportColumn("TRACE_STATUS", "跟踪状态", ExcelDataType.Dict));
		exportColumnList.add(new ExcelExportColumn("IS_VALID", "是否激活", ExcelDataType.Dict));
		exportColumnList.add(new ExcelExportColumn("CARD_TYPE_CODE", "会员卡等级"));
		excelGenerator.generateExcelForDms(excelData, exportColumnList,
				FrameworkUtil.getLoginInfo().getDealerShortName() + "_客户流失报警.xls", request, response);
	}
}
