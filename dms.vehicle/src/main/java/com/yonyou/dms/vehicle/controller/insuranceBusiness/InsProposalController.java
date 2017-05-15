/**
 * 
 */
package com.yonyou.dms.vehicle.controller.insuranceBusiness;

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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.util.UriComponentsBuilder;

import com.yonyou.dms.common.domains.DTO.customer.InsProposalDTO;
import com.yonyou.dms.common.domains.DTO.customer.ProposalTrackDTO;
import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.framework.service.excel.ExcelDataType;
import com.yonyou.dms.framework.service.excel.ExcelExportColumn;
import com.yonyou.dms.framework.service.excel.ExcelGenerator;
import com.yonyou.dms.framework.util.FrameworkUtil;
import com.yonyou.dms.function.exception.ServiceBizException;
import com.yonyou.dms.vehicle.service.insuranceBusiness.InsProposalService;
import com.yonyou.f4.mvc.annotation.TxnConn;
import com.yonyou.f4.mvc.controller.BaseController;

/**
 * @author sqh
 *
 */
@Controller
@TxnConn
@RequestMapping("/vehicle/insProposal")
public class InsProposalController extends BaseController{
	
	@Autowired
	InsProposalService insProposalService;
	
	@Autowired
	private ExcelGenerator excelGenerator;
	
	@RequestMapping(method = RequestMethod.GET)
	@ResponseBody
	public PageInfoDto queryInsProposal(@RequestParam Map<String, String> queryParam) {
		PageInfoDto pageInfoDto = insProposalService.queryInsProposal(queryParam);
		return pageInfoDto;
	}
	
	@RequestMapping(value="/insCompany",method = RequestMethod.GET)
    @ResponseBody
    public  List<Map> queryInsCompany(@RequestParam Map<String, String> queryParam) {
        return insProposalService.queryInsCompany(queryParam);
    }
	
	@RequestMapping(value="/insBroker",method = RequestMethod.GET)
    @ResponseBody
    public  List<Map> queryInsBroker(@RequestParam Map<String, String> queryParam) {
        return insProposalService.queryInsBroker(queryParam);
    }
	
	@RequestMapping(value="/export",method = RequestMethod.GET)
	@ResponseBody
	public void exportInsProposal(@RequestParam Map<String, String> queryParam, HttpServletRequest request,
			HttpServletResponse response) throws ServiceBizException, SQLException {
		List<Map> resultList = insProposalService.exportInsProposal(queryParam);
		Map<String, List<Map>> excelData = new HashMap<String, List<Map>>();
		excelData.put("保险投保单表", resultList);
		List<ExcelExportColumn> exportColumnList = new ArrayList<ExcelExportColumn>();
		exportColumnList.add(new ExcelExportColumn("PROPOSAL_CODE", "投保单号"));
		exportColumnList.add(new ExcelExportColumn("PROPOSAL_TYPE", "投保类型", ExcelDataType.Dict));
		exportColumnList.add(new ExcelExportColumn("VIN", "VIN"));
		exportColumnList.add(new ExcelExportColumn("LICENSE", "车牌号"));
		exportColumnList.add(new ExcelExportColumn("brand_name", "品牌"));
		exportColumnList.add(new ExcelExportColumn("series_name", "车系"));
		exportColumnList.add(new ExcelExportColumn("MODEL_NAME", "车型"));
		exportColumnList.add(new ExcelExportColumn("PROPOSAL_NAME", "投保人姓名"));
		exportColumnList.add(new ExcelExportColumn("PHONE", "电话"));
		exportColumnList.add(new ExcelExportColumn("MOBILE", "手机"));
		exportColumnList.add(new ExcelExportColumn("ZIP_CODE", "邮编"));
		exportColumnList.add(new ExcelExportColumn("ADDRESS", "地址"));
		exportColumnList.add(new ExcelExportColumn("INS_BROKER", "保险顾问"));
		exportColumnList.add(new ExcelExportColumn("INSURATION_CODE", "保险公司名称"));
		exportColumnList.add(new ExcelExportColumn("RECEIVABLE_COM_AMOUNT", "交强险应收金额"));
		exportColumnList.add(new ExcelExportColumn("RECEIVABLE_BUSI_AMOUNT", "商业险应收金额"));
		exportColumnList.add(new ExcelExportColumn("TRAVEL_TAX_AMOUNT", "车船税"));
		exportColumnList.add(new ExcelExportColumn("OTHER_AMOUNT", "其他业务"));
		exportColumnList.add(new ExcelExportColumn("TOTAL_AMOUNT", "金额合计"));
		exportColumnList.add(new ExcelExportColumn("IS_INS_LOCAL", "是否本地投保", ExcelDataType.Dict));
		exportColumnList.add(new ExcelExportColumn("IS_INS_CREDIT", "是否信贷投保", ExcelDataType.Dict));
		exportColumnList.add(new ExcelExportColumn("INS_CHANNELS", "投保渠道", ExcelDataType.Dict));
		exportColumnList.add(new ExcelExportColumn("TRACER", "跟踪人"));
		exportColumnList.add(new ExcelExportColumn("COMPLETED_DATE", "完成日期"));
		exportColumnList.add(new ExcelExportColumn("TRACE_TYPE", "跟进状态"));
		exportColumnList.add(new ExcelExportColumn("FORM_STATUS", "单据状态", ExcelDataType.Dict));
		exportColumnList.add(new ExcelExportColumn("IS_TRANSFER", "是否已过户", ExcelDataType.Dict));
		exportColumnList.add(new ExcelExportColumn("INS_SALES_DATE", "保险销售日期"));
		exportColumnList.add(new ExcelExportColumn("COM_INS_CODE", "交强险保单号"));
		exportColumnList.add(new ExcelExportColumn("BUSI_INS_CODE", "商业险保单号"));
		exportColumnList.add(new ExcelExportColumn("REMARK", "备注"));
		exportColumnList.add(new ExcelExportColumn("IS_ADD_PROPOSAL", "是否新增续保", ExcelDataType.Dict));
		exportColumnList.add(new ExcelExportColumn("IS_SELF_COMPANY_INSURANCE", "是否本公司投保", ExcelDataType.Dict));
		excelGenerator.generateExcelForDms(excelData, exportColumnList,
				FrameworkUtil.getLoginInfo().getDealerShortName() + "_保险投保单.xls", request, response);
	}
	
	@RequestMapping(value="/trackHistory",method = RequestMethod.GET)
	@ResponseBody
	public PageInfoDto queryTrackHistory(@RequestParam Map<String, String> queryParam) {
		PageInfoDto pageInfoDto = insProposalService.queryTrackHistory(queryParam);
		return pageInfoDto;
}
	
//	@SuppressWarnings("unchecked")
//	@RequestMapping(value="/{proposalCode}",method = RequestMethod.GET)
//    @ResponseBody
//    public  Map<String, Object> queryByProposalCode(@PathVariable(value = "proposalCode") String proposalCode) {
//		@SuppressWarnings("rawtypes")
//		List<Map> list = insProposalService.queryByProposalCode(proposalCode);
//        return  list.get(0);
//    }
	
	/**
	* @return 新增跟踪信息
	 */
	@RequestMapping(value="/addProposalTrack",method = RequestMethod.POST)
	public ResponseEntity<ProposalTrackDTO> addProposalTrack(@RequestBody @Valid ProposalTrackDTO proposalTrackDTO,UriComponentsBuilder uriCB) {
		insProposalService.addProposalTrack(proposalTrackDTO);
		MultiValueMap<String, String> headers = new HttpHeaders();
		headers.set("Location",
				uriCB.path("/vehicle/insProposal/addProposalTrack").buildAndExpand(proposalTrackDTO.getProposalCode()).toUriString());
		return new ResponseEntity<ProposalTrackDTO>(proposalTrackDTO, headers, HttpStatus.CREATED);

	}
	
	@RequestMapping(value = "/update", method = RequestMethod.PUT)
	public ResponseEntity<InsProposalDTO> update(@RequestBody InsProposalDTO insProposalDTO, UriComponentsBuilder uriCB) {
		insProposalService.update(insProposalDTO);
		MultiValueMap<String, String> headers = new HttpHeaders();
		headers.set("Location", uriCB.path("/vehicle/insProposal/update").buildAndExpand().toUriString());
		return new ResponseEntity<InsProposalDTO>(insProposalDTO, headers, HttpStatus.CREATED);
	}
	
	@RequestMapping(value = "/updateStatus", method = RequestMethod.PUT)
	public ResponseEntity<InsProposalDTO> updateStatus(@RequestBody InsProposalDTO insProposalDTO, UriComponentsBuilder uriCB) {
		insProposalService.updateStatus(insProposalDTO);
		MultiValueMap<String, String> headers = new HttpHeaders();
		headers.set("Location", uriCB.path("/vehicle/insProposal/updateStatus").buildAndExpand().toUriString());
		return new ResponseEntity<InsProposalDTO>(insProposalDTO, headers, HttpStatus.CREATED);
	}
	
	@RequestMapping(value = "/queryOwner",method = RequestMethod.GET)
	@ResponseBody
	public PageInfoDto queryOwner(@RequestParam Map<String, String> queryParam) {
		PageInfoDto pageInfoDto = insProposalService.queryOwner(queryParam);
		return pageInfoDto;
	}
}