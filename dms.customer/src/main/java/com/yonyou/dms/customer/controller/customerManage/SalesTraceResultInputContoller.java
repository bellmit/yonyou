package com.yonyou.dms.customer.controller.customerManage;

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

import com.yonyou.dms.customer.domains.DTO.customerManage.QuestionnaireInputDTO;
import com.yonyou.dms.customer.service.customerManage.SalesTraceResultInputService;
import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.framework.service.excel.ExcelDataType;
import com.yonyou.dms.framework.service.excel.ExcelExportColumn;
import com.yonyou.dms.framework.service.excel.ExcelGenerator;
import com.yonyou.dms.framework.util.FrameworkUtil;
import com.yonyou.f4.mvc.annotation.TxnConn;
import com.yonyou.f4.mvc.controller.BaseController;

@Controller
@TxnConn
@SuppressWarnings("rawtypes")
@RequestMapping("/customerManage/salesTraceResultInput")
public class SalesTraceResultInputContoller extends BaseController {

	@Autowired
	private SalesTraceResultInputService salestraceresultinputservice;

	@Autowired
	private ExcelGenerator excelService;

	private static String ids = "";

	/**
	 * 销售回访任务录入查询
	 * 
	 * @param queryParam
	 * @return
	 */
	@RequestMapping(method = RequestMethod.GET)
	@ResponseBody
	public PageInfoDto querySalesTraceResultInput(@RequestParam Map<String, String> queryParam) {
		PageInfoDto pageInfoDto = salestraceresultinputservice.querySalesTraceResultInput(queryParam);
		return pageInfoDto;
	}

	@RequestMapping(value = "/salesqusetionnaire/{id1}/{id2}/{id3}/{id4}", method = RequestMethod.GET)
	@ResponseBody
	public List<Map> queryQusetionnaire(@PathVariable String id1, @PathVariable String id2,@PathVariable String id3,@PathVariable String id4) {
		List<Map> list = salestraceresultinputservice.queryQusetionnaire(id1, id2, id3, id4);
		return list;
	}

	@RequestMapping(value = "/history/{id}", method = RequestMethod.GET)
	@ResponseBody
	public List<Map> queryBigCustomerHistoryIntent(@PathVariable(value = "id") String id) {
		List<Map> list = salestraceresultinputservice.queryBigCustomerHistoryIntent(id);
		ids = id;
		return list;
	}

	@RequestMapping(value = "/dcrchistory/{id}", method = RequestMethod.GET)
	@ResponseBody
	public PageInfoDto querysaleanddcrc(@PathVariable(value = "id") String id,
			@RequestParam Map<String, String> queryParam) {
		PageInfoDto pageInfoDto = salestraceresultinputservice.querysaleanddcrc(id, queryParam);
		ids = id;
		return pageInfoDto;
	}

	/**
	 * 销售回访任务分配生成excel TODO 销售回访任务分配生成excel
	 * 
	 * @author wangxin
	 * @date 2017年1月4日
	 * @param queryParam
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "/export/excel", method = RequestMethod.GET)
	public void excel(@RequestParam Map<String, String> queryParam, HttpServletRequest request,
			HttpServletResponse response) {
		List<Map> resultList = salestraceresultinputservice.querySafeToExport(queryParam);
		Map<String, List<Map>> excelData = new HashMap<String, List<Map>>();
		excelData.put(FrameworkUtil.getLoginInfo().getDealerShortName() + "_销售回访任务录入", resultList);
		List<ExcelExportColumn> exportColumnList = new ArrayList<ExcelExportColumn>();
		exportColumnList.add(new ExcelExportColumn("EMPLOYEE_NAME", "跟踪人"));
		exportColumnList.add(new ExcelExportColumn("IS_SELECTED", "结束跟踪",ExcelDataType.Dict));
		exportColumnList.add(new ExcelExportColumn("CUSTOMER_NO", "客户编号"));
		exportColumnList.add(new ExcelExportColumn("CUSTOMER_NAME", "客户名称"));
		exportColumnList.add(new ExcelExportColumn("SOLD_NAME", "销售顾问"));
		exportColumnList.add(new ExcelExportColumn("ORG_NAME", "组别"));
		exportColumnList.add(new ExcelExportColumn("VIN", "VIN"));
		exportColumnList.add(new ExcelExportColumn("BRAND_NAME", "品牌"));
		exportColumnList.add(new ExcelExportColumn("SERIES_NAME", "车系"));
		exportColumnList.add(new ExcelExportColumn("MODEL_NAME", "车型"));
		exportColumnList.add(new ExcelExportColumn("STOCK_OUT_DATE", "开票日期"));
		exportColumnList.add(new ExcelExportColumn("GENDER", "性别",ExcelDataType.Dict));
		exportColumnList.add(new ExcelExportColumn("ADDRESS", "地址"));
		exportColumnList.add(new ExcelExportColumn("CONTACTOR_PHONE", "联系人电话"));
		exportColumnList.add(new ExcelExportColumn("CONTACTOR_MOBILE", "联系人手机"));
		exportColumnList.add(new ExcelExportColumn("PROVINCE", "省份",ExcelDataType.Region_Provice));
		exportColumnList.add(new ExcelExportColumn("CITY", "城市",ExcelDataType.Region_City));
		exportColumnList.add(new ExcelExportColumn("DISTRICT", "区县",ExcelDataType.Region_Country));
		exportColumnList.add(new ExcelExportColumn("QUESTIONNAIRE_NAME", "问卷名称"));
		exportColumnList.add(new ExcelExportColumn("INPUT_DATE", "跟踪日期"));
		exportColumnList.add(new ExcelExportColumn("TRACE_STATUS", "跟踪状态",ExcelDataType.Dict));
		exportColumnList.add(new ExcelExportColumn("E_MAIL", "E_MAIL"));
		exportColumnList.add(new ExcelExportColumn("CUSTOMER_TYPE", "客户类型",ExcelDataType.Dict));
		exportColumnList.add(new ExcelExportColumn("OWNER_MARRIAGE", "婚姻状况",ExcelDataType.Dict));
		exportColumnList.add(new ExcelExportColumn("AGE_STAGE", "年龄段",ExcelDataType.Dict));
		exportColumnList.add(new ExcelExportColumn("EDUCATION_LEVEL", "教育水平",ExcelDataType.Dict));
		exportColumnList.add(new ExcelExportColumn("INDUSTRY_FIRST", "行业大类",ExcelDataType.Dict));
		exportColumnList.add(new ExcelExportColumn("INDUSTRY_SECOND", "行业小类",ExcelDataType.Dict));
		exportColumnList.add(new ExcelExportColumn("VOCATION_TYPE", "职业",ExcelDataType.Dict));
		exportColumnList.add(new ExcelExportColumn("POSITION_NAME", "职务名称"));
		exportColumnList.add(new ExcelExportColumn("FAMILY_INCOME", "家庭月收入",ExcelDataType.Dict));
		exportColumnList.add(new ExcelExportColumn("HOBBY", "爱好",ExcelDataType.Dict));
		exportColumnList.add(new ExcelExportColumn("CONFIRMED_DATE", "交车日期"));
		exportColumnList.add(new ExcelExportColumn("CUS_SOURCE", "客户来源",ExcelDataType.Dict));
		exportColumnList.add(new ExcelExportColumn("TASK_REMARK", "备注"));
		excelService.generateExcelForDms(excelData, exportColumnList,
				FrameworkUtil.getLoginInfo().getDealerShortName() + "_销售回访任务录入.xls", request, response);
	}

	/**
	 * 导出
	 * @param queryParam
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "/export/excel1", method = RequestMethod.GET)
	public void excel1(@RequestParam Map<String, String> queryParam, HttpServletRequest request,
			HttpServletResponse response) {
		String id = ids;
		List<Map> resultList = salestraceresultinputservice.querySafeToExport1(id);
		Map<String, List<Map>> excelData = new HashMap<String, List<Map>>();
		excelData.put(FrameworkUtil.getLoginInfo().getDealerShortName() + "_销售回访历史", resultList);
		List<ExcelExportColumn> exportColumnList = new ArrayList<ExcelExportColumn>();
		exportColumnList.add(new ExcelExportColumn("USER_NAME", "跟踪人"));
		exportColumnList.add(new ExcelExportColumn("IS_SELECTED", "结束跟踪"));
		exportColumnList.add(new ExcelExportColumn("CUSTOMER_NO", "客户编号"));
		exportColumnList.add(new ExcelExportColumn("CUSTOMER_NAME", "客户名称"));
		exportColumnList.add(new ExcelExportColumn("SOLD_NAME", "销售顾问"));
		exportColumnList.add(new ExcelExportColumn("ORG_NAME", "组别"));
		exportColumnList.add(new ExcelExportColumn("VIN", "VIN"));
		exportColumnList.add(new ExcelExportColumn("BRAND_NAME", "品牌"));
		exportColumnList.add(new ExcelExportColumn("SERIES_NAME", "车系"));
		exportColumnList.add(new ExcelExportColumn("MODEL_NAME", "车型"));
		exportColumnList.add(new ExcelExportColumn("STOCK_OUT_DATE", "开票日期"));
		exportColumnList.add(new ExcelExportColumn("GENDER", "性别"));
		exportColumnList.add(new ExcelExportColumn("ADDRESS", "地址"));
		exportColumnList.add(new ExcelExportColumn("CONTACTOR_PHONE", "联系人电话"));
		exportColumnList.add(new ExcelExportColumn("CONTACTOR_MOBILE", "联系人手机"));
		exportColumnList.add(new ExcelExportColumn("PROVINCE", "省份"));
		exportColumnList.add(new ExcelExportColumn("CITY", "城市"));
		exportColumnList.add(new ExcelExportColumn("DISTRICT", "区县"));
		exportColumnList.add(new ExcelExportColumn("QUESTIONNAIRE_NAME", "问卷名称"));
		exportColumnList.add(new ExcelExportColumn("INPUT_DATE", "跟踪日期"));
		exportColumnList.add(new ExcelExportColumn("TRACE_STATUS", "跟踪状态"));
		exportColumnList.add(new ExcelExportColumn("E_MAIL", "E_MAIL"));
		exportColumnList.add(new ExcelExportColumn("CUSTOMER_TYPE", "客户类型"));
		exportColumnList.add(new ExcelExportColumn("OWNER_MARRIAGE", "婚姻状况"));
		exportColumnList.add(new ExcelExportColumn("AGE_STAGE", "年龄段"));
		exportColumnList.add(new ExcelExportColumn("EDUCATION_LEVEL", "教育水平"));
		exportColumnList.add(new ExcelExportColumn("INDUSTRY_FIRST", "行业大类"));
		exportColumnList.add(new ExcelExportColumn("INDUSTRY_SECOND", "行业小类"));
		exportColumnList.add(new ExcelExportColumn("VOCATION_TYPE", "职业"));
		exportColumnList.add(new ExcelExportColumn("POSITION_NAME", "职务名称"));
		exportColumnList.add(new ExcelExportColumn("FAMILY_INCOME", "家庭月收入"));
		exportColumnList.add(new ExcelExportColumn("HOBBY", "爱好"));
		exportColumnList.add(new ExcelExportColumn("CONFIRMED_DATE", "交车日期"));
		exportColumnList.add(new ExcelExportColumn("CUS_SOURCE", "客户来源"));
		exportColumnList.add(new ExcelExportColumn("TASK_REMARK", "备注"));
		excelService.generateExcelForDms(excelData, exportColumnList,
				FrameworkUtil.getLoginInfo().getDealerShortName() + "_销售回访历史.xls", request, response);
	}

	/**
	 * 导出
	 * @param queryParam
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "/export/excel2", method = RequestMethod.GET)
	public void excel2(@RequestParam Map<String, String> queryParam, HttpServletRequest request,
			HttpServletResponse response) {
		String id = ids;
		List<Map> resultList = salestraceresultinputservice.querySafeToExport2(id, queryParam);
		Map<String, List<Map>> excelData = new HashMap<String, List<Map>>();
		excelData.put(FrameworkUtil.getLoginInfo().getDealerShortName() + "_销售回访 DCRC回访", resultList);
		List<ExcelExportColumn> exportColumnList = new ArrayList<ExcelExportColumn>();
		exportColumnList.add(new ExcelExportColumn("REMIND_ID", "回访编号"));
		exportColumnList.add(new ExcelExportColumn("description", "回访类型"));
		exportColumnList.add(new ExcelExportColumn("OWNER_NO", "车主/客户编号"));
		exportColumnList.add(new ExcelExportColumn("OWNER_NAME", "车主/客户姓名"));
		exportColumnList.add(new ExcelExportColumn("REMIND_DATE", "回访日期"));
		exportColumnList.add(new ExcelExportColumn("REMIND_CONTENT", "回访内容"));
		exportColumnList.add(new ExcelExportColumn("REMINDER", "回访人"));
		exportColumnList.add(new ExcelExportColumn("CUSTOMER_FEEDBACK", "反馈信息"));
		exportColumnList.add(new ExcelExportColumn("BRAND_NAME", "品牌"));
		exportColumnList.add(new ExcelExportColumn("SERIES_NAME", "车系"));
		exportColumnList.add(new ExcelExportColumn("MODEL_NAME", "车型"));
		exportColumnList.add(new ExcelExportColumn("VIN", "VIN"));
		exportColumnList.add(new ExcelExportColumn("MOBILE", "联系人手机"));
		exportColumnList.add(new ExcelExportColumn("PHONE", "联系人电话"));
		excelService.generateExcelForDms(excelData, exportColumnList,
				FrameworkUtil.getLoginInfo().getDealerShortName() + "_销售回访 DCRC回访.xls", request, response);
	}

	/**
	 * 查询销售顾问
	 * 
	 * @author xukl
	 * @date 2016年9月13日
	 * @param orgCode
	 * @return
	 */
	@RequestMapping(value = "/{orgCode}/salesConsultant", method = RequestMethod.GET)
	@ResponseBody
	public List<Map> qrySalesConsultant(@PathVariable String orgCode) {
		List<Map> list = salestraceresultinputservice.qrySalesConsultant(orgCode);
		return list;
	}

	@RequestMapping(value = "/findByVin/{id}/{id1}", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> findByVin(@PathVariable(value = "id") String id,@PathVariable(value = "id1") String id1) {
		System.err.println(id);
		System.err.println(id1);
		return salestraceresultinputservice.findById(id,id1);
	}
	
	/**
	 * 问卷导入保存
	 * @param id
	 * @param questionnaireInputDTO
	 * @param uriCB
	 * @return
	 */
	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	public ResponseEntity<QuestionnaireInputDTO> updateTracingtaskSales(@PathVariable("id") String id,
			@RequestBody @Valid QuestionnaireInputDTO questionnaireInputDTO, UriComponentsBuilder uriCB) {
		salestraceresultinputservice.updateTracingtaskSales(id, questionnaireInputDTO);
		MultiValueMap<String, String> headers = new HttpHeaders();
		headers.set("Location", uriCB.path("/customerManage/salesTraceResultInput/{id}").buildAndExpand(id).toUriString());
		return new ResponseEntity<QuestionnaireInputDTO>(headers, HttpStatus.CREATED);
	}
	
	@RequestMapping(value = "/querySalesTraceTaskLog/{id}", method = RequestMethod.GET)
	@ResponseBody
	public List<Map> querySalesTraceTaskLog(@PathVariable(value = "id") String id) {
		List<Map> list =  salestraceresultinputservice.querySalesTraceTaskLog(id);
		return list;
	}
	

}
