package com.yonyou.dms.vehicle.controller.bigCustomerManage;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.util.UriComponentsBuilder;

import com.yonyou.dms.common.domains.DTO.basedata.TtBigCustomerReportApprovalHisPO;
import com.yonyou.dms.common.domains.PO.basedata.TtBigCustomerReportApprovalPO;
import com.yonyou.dms.common.domains.PO.customer.TtBigCustomerRebateApprovalPO;
import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.framework.domain.LoginInfoDto;
import com.yonyou.dms.framework.service.excel.ExcelDataType;
import com.yonyou.dms.framework.service.excel.ExcelExportColumn;
import com.yonyou.dms.framework.service.excel.ExcelGenerator;
import com.yonyou.dms.framework.util.bean.ApplicationContextHelper;
import com.yonyou.dms.function.exception.ServiceBizException;
import com.yonyou.dms.function.utils.common.StringUtils;
import com.yonyou.dms.vehicle.domains.DTO.bigCustomer.TtBigCustomerAuthorityApprovalDTO;
import com.yonyou.dms.vehicle.domains.DTO.bigCustomer.TtBigCustomerReportApprovalDTO;
import com.yonyou.dms.vehicle.domains.PO.bigCustomer.TtBigCustomerPolicyFilePO;

import com.yonyou.dms.vehicle.service.bigCustomerManage.BigCustomerManageAaService;
import com.yonyou.f4.mvc.annotation.TxnConn;

/**
 * 大客户管理
 * @author ZhaoZ
 * @date 2017年3月10日
 */
@SuppressWarnings("rawtypes")
@Controller
@TxnConn
@RequestMapping("/bigCustomerAaManage")
public class BigCustomerManageAaController {

	private static int flag = 0;
	
	private static final Logger logger = LoggerFactory.getLogger(BigCustomerManageAaController.class);
	
	@Autowired
	private BigCustomerManageAaService customerService;
	@Autowired
	private ExcelGenerator excelService;
	
	/**
	 * 报备未审批查询
	 * @param queryParams
	 * @return
	 */
	@RequestMapping(value = "/dontApprovalQuery", method = RequestMethod.GET)
	@ResponseBody
	public PageInfoDto dontApprovalQuery(@RequestParam Map<String, String> queryParams){
		 logger.info("=====报备未审批查询=====");
		  flag = 1;
		return customerService.QueryCustomerByStatus(queryParams,flag);
		
	}
	
	/**
	 * 报备审批通过查询
	 * @param queryParams
	 * @return
	 */
	
	@RequestMapping(value = "/passApprovalQuery", method = RequestMethod.GET)
	@ResponseBody
	public PageInfoDto passApprovalQuery(@RequestParam Map<String, String> queryParams){
		 logger.info("=====报备审批通过查询=====");
		 	flag = 2;
			System.out.println("12345==="+flag);
		return customerService.QueryCustomerByStatus(queryParams,flag);
		
	}
	/**
	 * 报备审批驳回查询
	 * @param queryParams
	 * @return
	 */
	
	@RequestMapping(value = "/rejectApprovalQuery", method = RequestMethod.GET)
	@ResponseBody
	public PageInfoDto rejectApprovalQuery(@RequestParam Map<String, String> queryParams){
		 logger.info("=====报备审批驳回查询=====");
		 	flag = 3;
			System.out.println("1234==="+flag);
		return customerService.QueryCustomerByStatus(queryParams,flag);
		
	}
	/**
	 * 报备审批拒绝查询
	 * @param queryParams
	 * @return
	 */
	
	@RequestMapping(value = "/refuseApprovalQuery", method = RequestMethod.GET)
	@ResponseBody
	public PageInfoDto refuseApprovalQuery(@RequestParam Map<String, String> queryParams){
		 logger.info("=====报备审批拒绝查询=====");
		 	flag = 4;
			System.out.println("123==="+flag);
		return customerService.QueryCustomerByStatus(queryParams,flag);
		
	}
	/**
	 * 大客户报备审批信息下载
	 * @param queryParams
	 * @return
	 */
	//报备未审批
	@RequestMapping(value="/dlrFilingApprovalInfoDownLoad",method = RequestMethod.GET)
	@ResponseBody
	public void dlrFilingApprovalInfoDownLoad (@RequestParam Map<String, String> queryParams, HttpServletRequest request,
			HttpServletResponse response ){
		logger.info("============大客户报备审批信息下载===============");
		flag = 1;
		List<Map> customerList = customerService.dlrFilingInfoExport(queryParams,flag);
		Map<String, List<Map>> excelData = new HashMap<>();
		excelData.put("大客户报备审批信息",customerList);
		List<ExcelExportColumn> exportColumnList = getExcelExportColumn();
		excelService.generateExcel(excelData, exportColumnList, "大客户报备审批信息.xls", request, response);
	}
	
	//报备审批通过
	@RequestMapping(value="/dlrFilingApprovalInfoDownLoad1",method = RequestMethod.GET)
	@ResponseBody
	public void dlrFilingApprovalInfoDownLoad1 (@RequestParam Map<String, String> queryParams, HttpServletRequest request,
			HttpServletResponse response ){
		logger.info("============大客户报备审批信息下载===============");
		
		flag = 2;
		List<Map> customerList = customerService.dlrFilingInfoExport(queryParams,flag);
		Map<String, List<Map>> excelData = new HashMap<>();
		excelData.put("大客户报备审批信息",customerList);
		List<ExcelExportColumn> exportColumnList = getExcelExportColumn();
		excelService.generateExcel(excelData, exportColumnList, "大客户报备审批信息.xls", request, response);
	}
	
	//报备审批驳回
	@RequestMapping(value="/dlrFilingApprovalInfoDownLoad2",method = RequestMethod.GET)
	@ResponseBody
	public void dlrFilingApprovalInfoDownLoad2 (@RequestParam Map<String, String> queryParams, HttpServletRequest request,
			HttpServletResponse response ){
		logger.info("============大客户报备审批信息下载===============");
		flag = 3;
		List<Map> customerList = customerService.dlrFilingInfoExport(queryParams,flag);
		Map<String, List<Map>> excelData = new HashMap<>();
		excelData.put("大客户报备审批信息",customerList);
		List<ExcelExportColumn> exportColumnList = getExcelExportColumn();
		excelService.generateExcel(excelData, exportColumnList, "大客户报备审批信息.xls", request, response);
	}
	
	//报备审批拒绝
	@RequestMapping(value="/dlrFilingApprovalInfoDownLoad3",method = RequestMethod.GET)
	@ResponseBody
	public void dlrFilingApprovalInfoDownLoad3 (@RequestParam Map<String, String> queryParams, HttpServletRequest request,
			HttpServletResponse response ){
		logger.info("============大客户报备审批信息下载===============");
		flag = 4;
		List<Map> customerList = customerService.dlrFilingInfoExport(queryParams,flag);
		Map<String, List<Map>> excelData = new HashMap<>();
		excelData.put("大客户报备审批信息",customerList);
		List<ExcelExportColumn> exportColumnList = getExcelExportColumn();
		excelService.generateExcel(excelData, exportColumnList, "大客户报备审批信息.xls", request, response);
	}
	
	
	public  static List<ExcelExportColumn> getExcelExportColumn(){
		List<ExcelExportColumn> exportColumnList = new ArrayList<>();
		exportColumnList.add(new ExcelExportColumn("BIG_ORG_NAME", "大区"));
		exportColumnList.add(new ExcelExportColumn("ORG_NAME", "小区"));
		exportColumnList.add(new ExcelExportColumn("DEALER_CODE", "经销商代码"));
		exportColumnList.add(new ExcelExportColumn("DEALER_NAME", "经销商名称"));
		exportColumnList.add(new ExcelExportColumn("WS_NO", "报备单号"));
		exportColumnList.add(new ExcelExportColumn("RP_DATE", "报备日期"));
		exportColumnList.add(new ExcelExportColumn("ESTIMATE_APPLY_TIME", "预计申请时间"));
		exportColumnList.add(new ExcelExportColumn("PS_TYPE", "政策类别",ExcelDataType.Oem_Dict));
		exportColumnList.add(new ExcelExportColumn("EMPLOYEE_TYPE", "客户类型",ExcelDataType.Oem_Dict));
		exportColumnList.add(new ExcelExportColumn("CUSTOMER_SUB_TYPE", "客户细分类别",ExcelDataType.Oem_Dict));
		exportColumnList.add(new ExcelExportColumn("COMPANY_NAME", "公司名称"));
		exportColumnList.add(new ExcelExportColumn("MODEL_CODE", "车型代码"));
		exportColumnList.add(new ExcelExportColumn("MODEL_NAME", "车型名称"));
		exportColumnList.add(new ExcelExportColumn("PS_SOURCE_APPLY_NUM", "数量"));
		exportColumnList.add(new ExcelExportColumn("REPORT_APPROVAL_STATUS", "审批状态",ExcelDataType.Oem_Dict));
		return exportColumnList;
	}
	
	/**
	 * 大客户报备审批明细信息下载
	 * @param queryParams
	 * @return
	 */
	@RequestMapping(value="/dlrFilingApprovalInfoDownLoadDetail",method = RequestMethod.GET)
	@ResponseBody
	public void dlrFilingApprovalInfoDownLoadDetail (@RequestParam Map<String, String> queryParams, HttpServletRequest request,
			HttpServletResponse response ){
		logger.info("============大客户报备审批信息下载===============");
		System.out.println("111222==="+flag);
		List<Map> customerList = customerService.dlrFilingInfoDetailExport(queryParams,flag);
		List<ExcelExportColumn> exportColumnList = new ArrayList<>();
		Map<String, List<Map>> excelData = new HashMap<>();
		excelData.put("大客户报备审批明细信息",customerList);
		exportColumnList.add(new ExcelExportColumn("BIG_ORG_NAME", "大区"));
		exportColumnList.add(new ExcelExportColumn("ORG_NAME", "小区"));
		exportColumnList.add(new ExcelExportColumn("DEALER_CODE", "经销商代码"));
		exportColumnList.add(new ExcelExportColumn("DEALER_NAME", "经销商名称"));
		exportColumnList.add(new ExcelExportColumn("WS_NO", "报备单号"));
		exportColumnList.add(new ExcelExportColumn("RP_DATE", "报备日期"));
		exportColumnList.add(new ExcelExportColumn("ESTIMATE_APPLY_TIME", "预计申请时间"));
		exportColumnList.add(new ExcelExportColumn("PS_TYPE", "政策类别",ExcelDataType.Oem_Dict));
		exportColumnList.add(new ExcelExportColumn("EMPLOYEE_TYPE", "客户类型",ExcelDataType.Oem_Dict));
		exportColumnList.add(new ExcelExportColumn("CUSTOMER_SUB_TYPE", "客户细分类别",ExcelDataType.Oem_Dict));
		exportColumnList.add(new ExcelExportColumn("COMPANY_NAME", "公司名称"));
		exportColumnList.add(new ExcelExportColumn("MODEL_CODE", "车型代码"));
		exportColumnList.add(new ExcelExportColumn("MODEL_NAME", "车型名称"));
		exportColumnList.add(new ExcelExportColumn("PS_SOURCE_APPLY_NUM", "数量"));
		exportColumnList.add(new ExcelExportColumn("REPORT_APPROVAL_STATUS", "审批状态",ExcelDataType.Oem_Dict));

		excelService.generateExcel(excelData, exportColumnList, "大客户报备审批明细信息.xls", request, response);
	}
	
	
	/**
	 * 功能描述：查询大客户返利审批信息
	 * @param queryParams
	 * @return
	 */
	
	@RequestMapping(value = "/applyforQuery/{type}", method = RequestMethod.GET)
	@ResponseBody
	public PageInfoDto applyforQuery(@PathVariable(value = "type") int type,@RequestParam Map<String, String> queryParams){
		 logger.info("=====功能描述：查询大客户返利审批信息=====");
		 System.out.println(type+"============================");
		 	flag = type;
			return customerService.QueryapplyforQuery(queryParams,type);
	}
	
	/**
	 * 大客户申请审批信息下载
	 * @param queryParams
	 * @return
	 */
	//申请未审批
	@RequestMapping(value="/dlrRebateApprovalInfoDownLoad",method = RequestMethod.GET)
	@ResponseBody
	public void dlrRebateApprovalInfoDownLoad (@RequestParam Map<String, String> queryParams, HttpServletRequest request,
			HttpServletResponse response ){
		logger.info("============大客户申请审批信息下载===============");
		flag = 1;
		List<Map> customerList = customerService.dlrRebateApprovalInfoExport(queryParams,flag);
		Map<String, List<Map>> excelData = new HashMap<>();
		excelData.put("大客户申请审批信息",customerList);
		List<ExcelExportColumn> exportColumnList = getExcelExportColumn1();
		excelService.generateExcel(excelData, exportColumnList, "大客户申请审批信息.xls", request, response);
	}
	
    //申请审批通过
	@RequestMapping(value="/dlrRebateApprovalInfoDownLoad2",method = RequestMethod.GET)
	@ResponseBody
	public void dlrRebateApprovalInfoDownLoad1 (@RequestParam Map<String, String> queryParams, HttpServletRequest request,
			HttpServletResponse response ){
		logger.info("============大客户申请审批信息下载===============");
		flag = 2;
		List<Map> customerList = customerService.dlrRebateApprovalInfoExport(queryParams,flag);
		Map<String, List<Map>> excelData = new HashMap<>();
		excelData.put("大客户申请审批信息",customerList);
		List<ExcelExportColumn> exportColumnList = getExcelExportColumn1();
		excelService.generateExcel(excelData, exportColumnList, "大客户申请审批信息.xls", request, response);
	}
	
	//申请审批拒绝
	@RequestMapping(value="/dlrRebateApprovalInfoDownLoad3",method = RequestMethod.GET)
	@ResponseBody
	public void dlrRebateApprovalInfoDownLoad3 (@RequestParam Map<String, String> queryParams, HttpServletRequest request,
			HttpServletResponse response ){
		logger.info("============大客户申请审批信息下载===============");
		flag = 3;
		List<Map> customerList = customerService.dlrRebateApprovalInfoExport(queryParams,flag);
		Map<String, List<Map>> excelData = new HashMap<>();
		excelData.put("大客户申请审批信息",customerList);
		List<ExcelExportColumn> exportColumnList = getExcelExportColumn1();
		excelService.generateExcel(excelData, exportColumnList, "大客户申请审批信息.xls", request, response);
	}
	
    //申请审批通过
	@RequestMapping(value="/dlrRebateApprovalInfoDownLoad4",method = RequestMethod.GET)
	@ResponseBody
	public void dlrRebateApprovalInfoDownLoad4 (@RequestParam Map<String, String> queryParams, HttpServletRequest request,
			HttpServletResponse response ){
		logger.info("============大客户申请审批信息下载===============");
		flag = 4;
		List<Map> customerList = customerService.dlrRebateApprovalInfoExport(queryParams,flag);
		Map<String, List<Map>> excelData = new HashMap<>();
		excelData.put("大客户申请审批信息",customerList);
		List<ExcelExportColumn> exportColumnList = getExcelExportColumn1();
		excelService.generateExcel(excelData, exportColumnList, "大客户申请审批信息.xls", request, response);
	}
		
	 //资料完整待签字
	@RequestMapping(value="/dlrRebateApprovalInfoDownLoad1",method = RequestMethod.GET)
	@ResponseBody
	public void dlrRebateApprovalInfoDownLoad2 (@RequestParam Map<String, String> queryParams, HttpServletRequest request,
			HttpServletResponse response ){
		logger.info("============大客户申请审批信息下载===============");
		flag = 5;
		List<Map> customerList = customerService.dlrRebateApprovalInfoExport(queryParams,flag);
		Map<String, List<Map>> excelData = new HashMap<>();
		excelData.put("大客户申请审批信息",customerList);
		List<ExcelExportColumn> exportColumnList = getExcelExportColumn1();
		excelService.generateExcel(excelData, exportColumnList, "大客户申请审批信息.xls", request, response);
	}

	public static List<ExcelExportColumn> getExcelExportColumn1(){
		List<ExcelExportColumn> exportColumnList = new ArrayList<>();
		exportColumnList.add(new ExcelExportColumn("BIG_ORG_NAME", "大区"));
		exportColumnList.add(new ExcelExportColumn("ORG_NAME", "小区"));
		exportColumnList.add(new ExcelExportColumn("DEALER_CODE", "经销商代码"));
		exportColumnList.add(new ExcelExportColumn("DEALER_SHORTNAME", "经销商名称"));
		exportColumnList.add(new ExcelExportColumn("WS_NO", "报备单号"));
		exportColumnList.add(new ExcelExportColumn("REBATE_APPROVAL_STATUS", "审批状态",ExcelDataType.Oem_Dict));
		exportColumnList.add(new ExcelExportColumn("REBATE_APPROVAL_DATE", "报备日期"));
		exportColumnList.add(new ExcelExportColumn("RP_DATE", "申请日期"));
		exportColumnList.add(new ExcelExportColumn("PS_TYPE", "政策类别",ExcelDataType.Oem_Dict));
		exportColumnList.add(new ExcelExportColumn("EMPLOYEE_TYPE", "客户类型",ExcelDataType.Oem_Dict));
		exportColumnList.add(new ExcelExportColumn("CUSTOMER_SUB_TYPE", "客户细分类别",ExcelDataType.Oem_Dict));
		exportColumnList.add(new ExcelExportColumn("AMOUNT", "合计申请数量"));
		exportColumnList.add(new ExcelExportColumn("COMPANY_NAME", "公司名称"));
		exportColumnList.add(new ExcelExportColumn("CTM_NAME", "开票名称"));
		exportColumnList.add(new ExcelExportColumn("LM_CELLPHONE", "联系方式"));
		exportColumnList.add(new ExcelExportColumn("VIN", "VIN"));
		exportColumnList.add(new ExcelExportColumn("UPDATE_DATE", "NVDR日期"));
		exportColumnList.add(new ExcelExportColumn("INVOICE_DATE", "开票日期"));
		exportColumnList.add(new ExcelExportColumn("MODEL_CODE", "车型代码"));
		exportColumnList.add(new ExcelExportColumn("MODEL_NAME", "车型名称"));
		exportColumnList.add(new ExcelExportColumn("RETAIL_PRICE", "MSRP"));
		exportColumnList.add(new ExcelExportColumn("VEHICLE_USAGE", "用途",ExcelDataType.Oem_Dict));
		return exportColumnList;
	}
	
	/**
	 * 大客户申请明细下载
	 * @param queryParams
	 * @return
	 */
	@RequestMapping(value="/dlrRebateApprovalInfoDownLoadDetail",method = RequestMethod.GET)
	@ResponseBody
	public void dlrRebateApprovalInfoDownLoadDetail (@RequestParam Map<String, String> queryParams, HttpServletRequest request,
			HttpServletResponse response ){
		logger.info("============大客户申请审批明细信息下载===============");
		List<Map> customerList = customerService.reBateApprovalInfoDownLoadDetail(queryParams);
		List<ExcelExportColumn> exportColumnList = new ArrayList<>();
	
		Map<String, List<Map>> excelData = new HashMap<>();
		excelData.put("大客户申请审批明细信息",customerList);
		exportColumnList.add(new ExcelExportColumn("BIG_ORG_NAME", "大区"));
		exportColumnList.add(new ExcelExportColumn("ORG_NAME", "小区"));
		exportColumnList.add(new ExcelExportColumn("DEALER_CODE", "经销商代码"));
		exportColumnList.add(new ExcelExportColumn("DEALER_SHORTNAME", "经销商名称"));
		exportColumnList.add(new ExcelExportColumn("WS_NO", "报备单号"));
		exportColumnList.add(new ExcelExportColumn("REBATE_APPROVAL_STATUS", "审批状态",ExcelDataType.Oem_Dict));
		exportColumnList.add(new ExcelExportColumn("REBATE_APPROVAL_DATE", "报备日期"));
		exportColumnList.add(new ExcelExportColumn("RP_DATE", "申请日期"));
		exportColumnList.add(new ExcelExportColumn("PS_TYPE", "政策类别",ExcelDataType.Oem_Dict));
		exportColumnList.add(new ExcelExportColumn("EMPLOYEE_TYPE", "客户类型",ExcelDataType.Oem_Dict));
		exportColumnList.add(new ExcelExportColumn("CUSTOMER_SUB_TYPE", "客户细分类别",ExcelDataType.Oem_Dict));
		exportColumnList.add(new ExcelExportColumn("AMOUNT", "合计申请数量"));
		exportColumnList.add(new ExcelExportColumn("COMPANY_NAME", "公司名称"));
		exportColumnList.add(new ExcelExportColumn("CTM_NAME", "开票名称"));
		exportColumnList.add(new ExcelExportColumn("LM_CELLPHONE", "联系方式"));
		exportColumnList.add(new ExcelExportColumn("VIN", "VIN"));
		exportColumnList.add(new ExcelExportColumn("UPDATE_DATE", "NVDR日期"));
		exportColumnList.add(new ExcelExportColumn("INVOICE_DATE", "开票日期"));
		exportColumnList.add(new ExcelExportColumn("MODEL_CODE", "车型代码"));
		exportColumnList.add(new ExcelExportColumn("MODEL_NAME", "车型名称"));
		exportColumnList.add(new ExcelExportColumn("RETAIL_PRICE", "MSRP"));
		exportColumnList.add(new ExcelExportColumn("VEHICLE_USAGE", "用途",ExcelDataType.Oem_Dict));
		excelService.generateExcel(excelData, exportColumnList, "大客户申请审批明细信息.xls", request, response);
	}
	
	
	/**
	 * 大客户申购车型查询
	 * @param queryParams
	 * @return
	 */
	
	@RequestMapping(value = "/getCustomerBatchVhclInfo/{wsno}", method = RequestMethod.GET)
	@ResponseBody
	public PageInfoDto getCustomerBatchVhclInfo(@PathVariable(value = "wsno") String wsno){
		TtBigCustomerReportApprovalPO tbcraPO = TtBigCustomerReportApprovalPO.findFirst("WS_NO LIKE ?", wsno);
		
	
		return customerService.getCustomerBatchVhclInfoS(wsno,tbcraPO);
		
	}
	
	/**
	 * 查询审批信息(大客户报备审批)
	 * @param queryParams
	 * @return
	 */
	@RequestMapping(value = "/getApprovalHisInfo/{wsno}", method = RequestMethod.GET)
	@ResponseBody
	public PageInfoDto getApprovalHisInfo(@PathVariable(value = "wsno") String wsno){
		logger.info("============查询审批信息===============");
		TtBigCustomerReportApprovalHisPO tbcraPO = TtBigCustomerReportApprovalHisPO.findFirst("WS_NO LIKE ?", wsno);
		String companyCode = null;
		if(StringUtils.isNullOrEmpty(tbcraPO)) {
			companyCode = "";
		}else{
			companyCode = tbcraPO.getString("CUSTOMER_COMPANY_CODE");
		}
		return customerService.getApprovalHisInfos(wsno,companyCode);
		
	}
	
	/**
	 * 查询审批信息(大客户申请审批)
	 * @param queryParams
	 * @return
	 */
	@RequestMapping(value = "/getApprovalHisInfo1/{wsno}", method = RequestMethod.GET)
	@ResponseBody
	public PageInfoDto getApprovalHisInfo1(@PathVariable(value = "wsno") String wsno){
		logger.info("============查询审批信息===============");
		TtBigCustomerRebateApprovalPO tbcraPO = TtBigCustomerRebateApprovalPO.findFirst("WS_NO LIKE ?", wsno);
		return customerService.getApprovalHisInfos1(wsno,tbcraPO.getString("BIG_CUSTOMER_CODE"));
		
	}
	
	/**
	 * 大客户申请审批显示信息
	 * @param dealerId
	 * @return
	 */
	@RequestMapping(value = "/getMapInfo/{wsno}", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> getMapInfo(@PathVariable(value = "wsno") String wsno){
		logger.info("============大客户申请审批显示信息===============");
		Map<String, Object> mapA = customerService.QueryCustomerByStatus(wsno,flag);
		TtBigCustomerReportApprovalPO tbcraPO = TtBigCustomerReportApprovalPO.findFirst("WS_NO LIKE ?", wsno);
		Map<String, Object> mapB = customerService.getCustomerInfo(tbcraPO.getString("CUSTOMER_COMPANY_CODE"),tbcraPO.getString("DEALER_CODE"),wsno);
		if(mapA!=null){
			if(!StringUtils.isNullOrEmpty(mapB)){
				mapA.putAll(mapB);	
			}
			return mapA;
		}
		return mapB;
	}
	
	/**
	 * 大客户报备审批显示信息
	 * @param dealerId
	 * @return
	 */
	@RequestMapping(value = "/getMapInfo1/{wsno}", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> getMapInfo1(@PathVariable(value = "wsno") String wsno){
		logger.info("============大客户报备审批显示信息===============");
		Map<String, Object> mapA = customerService.QueryCustomerByStatus1(wsno,flag);
		TtBigCustomerReportApprovalPO tbcraPO = TtBigCustomerReportApprovalPO.findFirst("WS_NO LIKE ?", wsno);
		Map<String, Object> mapB = customerService.getCustomerInfo(tbcraPO.getString("CUSTOMER_COMPANY_CODE"),tbcraPO.getString("DEALER_CODE"),wsno);
		if(mapA!=null){
			if(!StringUtils.isNullOrEmpty(mapB)){
				mapA.putAll(mapB);	
			}
			return mapA;
		}
		return mapB;
	}
	
	
	
	/**
	 * 功能描述：保存大客户报备审批信息
	 */
	@RequestMapping(value = "/saveApprovalInfo/{wsno}", method = RequestMethod.PUT)
	@ResponseBody
	public ResponseEntity<TtBigCustomerReportApprovalDTO> saveApprovalInfo(@PathVariable(value = "wsno") String wsno,@RequestBody TtBigCustomerReportApprovalDTO dto,UriComponentsBuilder uriCB) {
		logger.info("==================保存大客户报备审批信息================");
		
		customerService.saveApprovalInfos(dto,wsno);
		
        MultiValueMap<String,String> headers = new HttpHeaders();  
        headers.set("Location", uriCB.path("/saveApprovalInfo").buildAndExpand().toUriString());
        return new ResponseEntity<>(headers, HttpStatus.CREATED);
	}
	
	
	/**
	 * 功能描述：保存大客户报备审批信息(未审批)
	 */
	@RequestMapping(value = "/saveApprovalInfo1/{wsno}", method = RequestMethod.PUT)
	@ResponseBody
	public ResponseEntity<TtBigCustomerReportApprovalDTO> saveApprovalInfo1(@PathVariable(value = "wsno") String wsno,@RequestBody TtBigCustomerReportApprovalDTO dto,UriComponentsBuilder uriCB) {
		logger.info("==================保存大客户申请审批信息================");
		
		customerService.saveApprovalInfos1(dto,wsno);
		
        MultiValueMap<String,String> headers = new HttpHeaders();  
        headers.set("Location", uriCB.path("/saveApprovalInfo1").buildAndExpand().toUriString());
        return new ResponseEntity<>(headers, HttpStatus.CREATED);
	}
	
	
	/**
	 * 功能描述：保存大客户报备审批信息(资料完整待签字)
	 */
	@RequestMapping(value = "/saveApprovalInfo2/{wsno}", method = RequestMethod.PUT)
	@ResponseBody
	public ResponseEntity<TtBigCustomerReportApprovalDTO> saveApprovalInfo2(@PathVariable(value = "wsno") String wsno,@RequestBody TtBigCustomerReportApprovalDTO dto,UriComponentsBuilder uriCB) {
		logger.info("==================保存大客户申请审批信息(资料完整待签字)================");
		
		customerService.saveApprovalInfos2(dto,wsno);
		
        MultiValueMap<String,String> headers = new HttpHeaders();  
        headers.set("Location", uriCB.path("/saveApprovalInfo2").buildAndExpand().toUriString());
        return new ResponseEntity<>(headers, HttpStatus.CREATED);
	}
	
	/**
	 * 大客户激活
	 */
	@RequestMapping(value = "/activationApprovalInfo/{wsno}", method = RequestMethod.PUT)
	@ResponseBody
	public ResponseEntity<TtBigCustomerReportApprovalDTO> activationApprovalInfo(@PathVariable(value = "wsno") String wsno,@RequestBody TtBigCustomerReportApprovalDTO dto,UriComponentsBuilder uriCB) {
		logger.info("==================大客户激活================");
		customerService.activationApprovalInfos(dto,wsno);
        MultiValueMap<String,String> headers = new HttpHeaders();  
        headers.set("Location", uriCB.path("/saveApprovalInfo").buildAndExpand().toUriString());
        return new ResponseEntity<>(headers, HttpStatus.CREATED);
	}
	
	/**
	 * 查询大客户的信息
	 * @param queryParams
	 * @return
	 */
	
	@RequestMapping(value = "/queryBigCustomerPolicyInfo", method = RequestMethod.GET)
	@ResponseBody
	public PageInfoDto queryBigCustomerPolicyInfo(@RequestParam Map<String, String> queryParams){
		 LoginInfoDto loginUser = ApplicationContextHelper.getBeanByType(LoginInfoDto.class);
		 logger.info("=====查询大客户的信息=====");
		 return customerService.queryBigCustomerPolicy(queryParams,loginUser);
	}
	
	/**
	 * 
	* @Title: uploadFiles 
	* @Description:  大客户政策（上传）
	* @param @param dto
	* @param @param uriCB
	* @param @return    设定文件 
	* @return ResponseEntity<DealerDetailedinfoDTO>    返回类型 
	* @throws
	* @author zhengzengliang
	 */
	@RequestMapping(value = "/uploadFiles", method = RequestMethod.POST)
	 @ResponseStatus(HttpStatus.NO_CONTENT)
 	public void uploadFiles(@RequestParam(value = "file") MultipartFile importFile,
 			UriComponentsBuilder uriCB){
		logger.info("================== 大客户政策（上传）================");
		customerService.uploadFiles(importFile);
        /*MultiValueMap<String, String> headers = new HttpHeaders();
        headers.set("Location", uriCB.path("").buildAndExpand().toUriString());
        return new ResponseEntity<> (headers, HttpStatus.CREATED);*/
 	}
	
	 /**
	  * 删除文件
	  * @param id
	  * @return
	  */
	@RequestMapping(value = "/deleteBigCustomerPolicy/{id}", method = RequestMethod.DELETE)
	@ResponseBody
	public ResponseEntity<TtBigCustomerReportApprovalDTO> deleteBigCustomerPolicy(
			@PathVariable(value = "id") BigDecimal id){
		logger.info("===== 删除文件=====");
		TtBigCustomerReportApprovalDTO dto = new TtBigCustomerReportApprovalDTO();
		TtBigCustomerPolicyFilePO po = TtBigCustomerPolicyFilePO.findById(id);
		try {
			po.deleteCascadeShallow();
		} catch (Exception e) {
			throw new ServiceBizException("删除失败...");

		}
		
		return new ResponseEntity<>(dto, HttpStatus.CREATED);
	}
	
	/**
	 * 
	* @Title: downBigCustomerPolicy 
	* @Description: 大客户政策（下载） 
	* @param @param queryParams    设定文件 
	* @return void    返回类型 
	* @throws
	* @author zhengzengliang
	 */
	@RequestMapping(value="/downBigCustomerPolicy/{policyFileId}",method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<TtBigCustomerReportApprovalDTO> downBigCustomerPolicy (
			@RequestParam Map<String, String> queryParams,
			@PathVariable(value = "policyFileId") BigDecimal policyFileId ){
		logger.info("===== 大客户政策（下载）=====");
		customerService.downBigCustomerPolicy(policyFileId);
		
		return new ResponseEntity<>( HttpStatus.CREATED);
	}
	
	/**
	 * 查询大客户组织架构权限审批信息
	 * @param queryParams
	 * @return
	 */
	
	@RequestMapping(value = "/queryBigCustomerAuthorityApprovalInfo", method = RequestMethod.GET)
	@ResponseBody
	public PageInfoDto queryBigCustomerAuthorityApprovalInfo(@RequestParam Map<String, String> queryParams){
		 logger.info("=====查询大客户组织架构权限审批信息=====");
		 return customerService.queryBigCustomerAuthorityApproval(queryParams);
	}
	
	/**
	 * 大客户组织架构权限审批明细页面
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/getApprovalDetailPre/{id}", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> getApprovalDetailPre(@PathVariable(value = "id") BigDecimal id){
		logger.info("============大客户组织架构权限审批明细页面===============");
		Map<String, Object> mapA = customerService.approvalDetailPre(id);
	
		return mapA;
	}
	
	/**
	 * 审核历史
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/approvalHis/{id}", method = RequestMethod.GET)
	@ResponseBody
	public PageInfoDto approvalHis (@PathVariable(value = "id") BigDecimal id){
		logger.info("============审核历史===============");
		
		return customerService.approvalHis(id);
	}
	
	/**
     * 保存大客户组织架构权限审批信息
     */
	
	@RequestMapping(value = "/saveAuthorityInfo/{id}", method = RequestMethod.PUT)
	@ResponseBody
	public ResponseEntity<TtBigCustomerAuthorityApprovalDTO> saveApprovalInfo(@PathVariable(value = "id") BigDecimal id,@RequestBody TtBigCustomerAuthorityApprovalDTO dto,UriComponentsBuilder uriCB){
		logger.info("===== 保存大客户组织架构权限审批信息=====");
		
	System.out.println(dto);
		
		customerService.saveApprovalInfo(id,dto);
		
		MultiValueMap<String,String> headers = new HttpHeaders();  
	    headers.set("Location", uriCB.path("/saveAuthorityInfo").buildAndExpand().toUriString());
	    return new ResponseEntity<>(headers, HttpStatus.CREATED);
	}
}
