/**
 * 
 */
package com.yonyou.dms.repair.controller.claimRepairOrder;

import java.sql.SQLException;
import java.text.ParseException;
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

import com.yonyou.dms.common.domains.DTO.basedata.TtRoTimeoutDTO;
import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.framework.service.excel.ExcelDataType;
import com.yonyou.dms.framework.service.excel.ExcelExportColumn;
import com.yonyou.dms.framework.service.excel.ExcelGenerator;
import com.yonyou.dms.framework.util.FrameworkUtil;
import com.yonyou.dms.function.exception.ServiceBizException;
import com.yonyou.dms.function.utils.jsonSerializer.JSONUtil;
import com.yonyou.dms.repair.service.claimRepairOrder.ClaimRepairOrderService;
import com.yonyou.f4.mvc.annotation.TxnConn;
import com.yonyou.f4.mvc.controller.BaseController;

/**
 * @author sqh
 *
 */
@Controller
@TxnConn
@RequestMapping("/repair/claimRepairOrder")
public class ClaimRepairOrderController extends BaseController{

	@Autowired
	private ClaimRepairOrderService claimRepairOrderService;
	
	@Autowired
	private ExcelGenerator excelGenerator;
	
	@RequestMapping(method = RequestMethod.GET)
	@ResponseBody
	public PageInfoDto queryClaimRepairOrder(@RequestParam Map<String, String> queryParam) {
			return claimRepairOrderService.queryClaimRepairOrder(queryParam);
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

	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/export", method = RequestMethod.GET)
	public void exportClaimRepairOrder(@RequestParam Map<String, String> queryParam, HttpServletRequest request,
			HttpServletResponse response) throws ServiceBizException, SQLException {
		List<Map> resultList = claimRepairOrderService.queryClaimRepairOrders(queryParam);
		Map<String, List<Map>> excelData = new HashMap<String, List<Map>>();
		excelData.put("未结算索赔工单查询表", resultList);
		List<ExcelExportColumn> exportColumnList = new ArrayList<ExcelExportColumn>();
		exportColumnList.add(new ExcelExportColumn("dealer_shortname", "经销商"));
		exportColumnList.add(new ExcelExportColumn("RO_CREATE_DATE", "工单开单日期"));
		exportColumnList.add(new ExcelExportColumn("RO_NO", "工单号"));
		exportColumnList.add(new ExcelExportColumn("RO_TYPE", "工单类型", ExcelDataType.Dict));
		exportColumnList.add(new ExcelExportColumn("RO_STATUS", "工单状态", ExcelDataType.Dict));
		exportColumnList.add(new ExcelExportColumn("RO_CLAIM_STATUS", "提报状态", ExcelDataType.Dict));
		exportColumnList.add(new ExcelExportColumn("SERVICE_ADVISOR_NAME", "客户经理"));
		exportColumnList.add(new ExcelExportColumn("BALANCE_CLOSE", "是否关单", ExcelDataType.Dict));
		exportColumnList.add(new ExcelExportColumn("IS_ABANDON_ACTIVITY", "是否放弃活动", ExcelDataType.Dict));
		exportColumnList.add(new ExcelExportColumn("DELIVERY_TAG", "交车标识", ExcelDataType.Dict));
		exportColumnList.add(new ExcelExportColumn("PAY_OFF", "是否结清", ExcelDataType.Dict));
		exportColumnList.add(new ExcelExportColumn("LABOUR_NAME_UNITE", "维修项目名称"));
		exportColumnList.add(new ExcelExportColumn("CHIEF_TECHNICIAN", "指定技师"));
		exportColumnList.add(new ExcelExportColumn("TECHNICIAN_UNITE", "维修技师"));
		exportColumnList.add(new ExcelExportColumn("LICENSE", "车牌号"));
		exportColumnList.add(new ExcelExportColumn("REPAIR_TYPE_CODE", "维修类型"));
		exportColumnList.add(new ExcelExportColumn("BALANCE_HANDLER", "结算员"));
		exportColumnList.add(new ExcelExportColumn("brand_name", "品牌"));
		exportColumnList.add(new ExcelExportColumn("series_name", "车系"));
		exportColumnList.add(new ExcelExportColumn("MODEL_NAME", "车型"));
		exportColumnList.add(new ExcelExportColumn("OWNER_NO", "车主编号"));
		exportColumnList.add(new ExcelExportColumn("OWNER_NAME", "车主"));
		exportColumnList.add(new ExcelExportColumn("RECOMMEND_CUSTOMER_NAME", "推荐单位"));
		exportColumnList.add(new ExcelExportColumn("OWNER_PROPERTY", "车主性质", ExcelDataType.Dict));
		exportColumnList.add(new ExcelExportColumn("REPAIR_AMOUNT", "维修金额"));
		exportColumnList.add(new ExcelExportColumn("FOR_BALANCE_TIME", "提交结算时间"));
		exportColumnList.add(new ExcelExportColumn("DELIVERY_DATE", "交车日期"));
		exportColumnList.add(new ExcelExportColumn("COMPLETE_TAG", "是否竣工", ExcelDataType.Dict));
		exportColumnList.add(new ExcelExportColumn("COMPLETE_TIME", "竣工时间"));
		exportColumnList.add(new ExcelExportColumn("WAIT_INFO_TAG", "是否待信", ExcelDataType.Dict));
		exportColumnList.add(new ExcelExportColumn("WAIT_PART_TAG", "是否待料", ExcelDataType.Dict));
		exportColumnList.add(new ExcelExportColumn("COMPLETE_ON_TIME", "是否准时完工", ExcelDataType.Dict));
		exportColumnList.add(new ExcelExportColumn("IN_MILEAGE", "进厂行驶里程"));
		exportColumnList.add(new ExcelExportColumn("OUT_MILEAGE", "出厂行驶里程"));
		exportColumnList.add(new ExcelExportColumn("END_TIME_SUPPOSED", "预交车时间"));
		exportColumnList.add(new ExcelExportColumn("VIN", "VIN"));
		exportColumnList.add(new ExcelExportColumn("DELIVERER_PHONE", "送修人电话"));
		exportColumnList.add(new ExcelExportColumn("DELIVERER_MOBILE", "送修人手机"));
		exportColumnList.add(new ExcelExportColumn("DELIVERER", "送修人"));
		exportColumnList.add(new ExcelExportColumn("INSURATION_CODE", "保险公司"));
		exportColumnList.add(new ExcelExportColumn("REMARK", "备注"));
		exportColumnList.add(new ExcelExportColumn("REMARK1", "备注1"));
		exportColumnList.add(new ExcelExportColumn("VIP_NO", "VIP编号"));
		exportColumnList.add(new ExcelExportColumn("HAVE_CLAIM_TAG", "是否索赔", ExcelDataType.Dict));
		exportColumnList.add(new ExcelExportColumn("IS_UPDATE_END_TIME_SUPPOSED", "是否修改预交车日期", ExcelDataType.Dict));
		exportColumnList.add(new ExcelExportColumn("SALES_DATE", "车辆销售日期"));
		exportColumnList.add(new ExcelExportColumn("ADDRESS", "地址"));
		exportColumnList.add(new ExcelExportColumn("ZIP_CODE", "邮编"));
		exportColumnList.add(new ExcelExportColumn("E_MAIL", "E_MAIL"));
		exportColumnList.add(new ExcelExportColumn("PROVINCE", "省份"));
		exportColumnList.add(new ExcelExportColumn("CITY", "邮编"));
		exportColumnList.add(new ExcelExportColumn("ZIP_CODE", "城市"));
		exportColumnList.add(new ExcelExportColumn("DISTRICT", "区县"));
		exportColumnList.add(new ExcelExportColumn("PRINT_BALANCE_TIME", "结算打印时间"));
		exportColumnList.add(new ExcelExportColumn("MODIFY_NUM", "工单修改次数"));
		exportColumnList.add(new ExcelExportColumn("RO_TROUBLE_DESC", "故障描述"));
		exportColumnList.add(new ExcelExportColumn("CERTIFICATE_NO", "证件号码"));
		exportColumnList.add(new ExcelExportColumn("CT_CODE", "证件类型", ExcelDataType.Dict));
		exportColumnList.add(new ExcelExportColumn("LAST_MAINTENANCE_DATE", "上次保养日期"));
		exportColumnList.add(new ExcelExportColumn("EWORKSHOP_REMARK", "车间反馈"));
		exportColumnList.add(new ExcelExportColumn("SCHEME_STATUS", "三包状态", ExcelDataType.Dict));
		exportColumnList.add(new ExcelExportColumn("IS_TIMEOUT_SUBMIT", "是否超时上报", ExcelDataType.Dict));
		excelGenerator.generateExcelForDms(excelData, exportColumnList,
				FrameworkUtil.getLoginInfo().getDealerShortName() + "_未结算索赔工单查询.xls", request, response);
	}
	
	/**
	 * 根据工单号编号回显查询超时原因
	 * @author
	 * @param id
	 * @throws ServiceBizException
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping(value = "/QueryRoTimeoutCause/{roNo}", method = RequestMethod.GET)
	@ResponseBody
	public Map<String,Object> QueryRoTimeoutCause(@PathVariable(value = "roNo") String id) throws ServiceBizException {
		List<Map> list = claimRepairOrderService.QueryRoTimeoutCause(id);
		return list.get(0);
	}
	
	/**
	 * 根据工单号编号回显查询超时明细
	 * @author
	 * @param id
	 * @throws ServiceBizException
	 */
	@RequestMapping(value = "/QueryRoTimeoutDetail/{roNo}", method = RequestMethod.GET)
	@ResponseBody
	public PageInfoDto QueryRoTimeoutDetail(@PathVariable(value = "roNo") String id) throws ServiceBizException {
		PageInfoDto pageInfoDto = claimRepairOrderService.QueryRoTimeoutDetail(id);
		return pageInfoDto;
	}
	
	/**
	 * 根据工单号编号回显查询缺料
	 * @author
	 * @param id
	 * @throws ServiceBizException
	 */
	@SuppressWarnings({ "rawtypes"})
	@RequestMapping(value = "/queryRoShortPartDetail/{roNo}", method = RequestMethod.GET)
	@ResponseBody
	public List<Map> queryRoShortPartDetail(@PathVariable(value = "roNo") String id) throws ServiceBizException {
		List<Map> list = claimRepairOrderService.queryRoShortPartDetail(id);
		return list;
	}
	//查询工单配件选择
	@RequestMapping(value = "/queryRoTimeoutPartDetail/{roNo}", method = RequestMethod.GET)
	@ResponseBody
	public PageInfoDto queryRoTimeoutPartDetail(@PathVariable(value = "roNo") String id) {
			return claimRepairOrderService.queryRoTimeoutPartDetail(id);
}

	/**
     *    保存
    * @author sqh
    * @param uriCB
    * @throws ServiceBizException
    * @throws ParseException
     */
   @RequestMapping(value="/save",method = RequestMethod.POST)
   @ResponseBody
   public ResponseEntity<TtRoTimeoutDTO> MaintainRoTimeoutCauseAndDetail(@RequestBody Map timeoutDTO, UriComponentsBuilder uriCB)
			throws ServiceBizException {
	  
	   claimRepairOrderService.MaintainRoTimeoutCauseAndDetail(timeoutDTO);
		MultiValueMap<String, String> headers = new HttpHeaders();
		headers.set("Location",
				uriCB.path("/repair/claimRepairOrder/save").buildAndExpand("").toUriString());
		return new ResponseEntity<TtRoTimeoutDTO>(null, headers, HttpStatus.CREATED);
	}
}