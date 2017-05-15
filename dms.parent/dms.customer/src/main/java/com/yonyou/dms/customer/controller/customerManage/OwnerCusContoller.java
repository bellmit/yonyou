/**
 * 
 */
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
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.util.UriComponentsBuilder;

import com.yonyou.dms.common.domains.DTO.basedata.OwnerCusDTO;
import com.yonyou.dms.customer.service.customerManage.OwnerCusService;
import com.yonyou.dms.framework.domain.LoginInfoDto;
import com.yonyou.dms.framework.service.CommonNoService;
import com.yonyou.dms.framework.service.excel.ExcelDataType;
import com.yonyou.dms.framework.service.excel.ExcelExportColumn;
import com.yonyou.dms.framework.service.excel.ExcelGenerator;
import com.yonyou.dms.framework.util.FrameworkUtil;
import com.yonyou.dms.framework.util.bean.ApplicationContextHelper;
import com.yonyou.dms.function.common.CommonConstants;
import com.yonyou.f4.mvc.annotation.TxnConn;
import com.yonyou.f4.mvc.controller.BaseController;

/**
 * @author Administrator
 *
 */
@Controller
@TxnConn
@RequestMapping("/OwnerManage/ownercus")
public class OwnerCusContoller extends BaseController {

	@Autowired
	private OwnerCusService ownercusservice;
	@Autowired
	private CommonNoService commonNoService;
    @Autowired
    private ExcelGenerator excelService;

	  /**
     * 潜在客户新增
     * 
     * @author zhanshiwei
     * @date 2016年9月1日
     * @param potentialCusDto
     * @param uriCB
     * @return
     */

    @RequestMapping(method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<Map<String, Object>> addOwnerCusInfo(@RequestBody  OwnerCusDTO ownerCusDto,
                                                                   UriComponentsBuilder uriCB) {
        LoginInfoDto loginInfo = ApplicationContextHelper.getBeanByType(LoginInfoDto.class);
        String customerNo = ownercusservice.addOwnerCusInfo(ownerCusDto,
                                                          commonNoService.getSystemOrderNo(CommonConstants.CUSTOMER_PREFIX));
        MultiValueMap<String, String> headers = new HttpHeaders();
        headers.set("Location", uriCB.path("/OwnerManage/ownercus/{customerNo}").buildAndExpand(customerNo).toUriString());
        return new ResponseEntity<Map<String, Object>>(headers, HttpStatus.CREATED);
    }
	/**
	 * 修改保客
	 * 
	 * @author zhanshiwei
	 * @date 2016年9月1日
	 * @param potentialCusDto
	 * @param uriCB
	 * @return
	 */

	 @RequestMapping(value = "/{CUSTOMER_NO}/in/{VIN}/ni/{OWNER_NO}", method = RequestMethod.PUT)
	@ResponseBody
    public ResponseEntity<OwnerCusDTO> modifyOwnerCusInfo(@PathVariable("CUSTOMER_NO") String customerNo,@PathVariable("VIN") String vin,@PathVariable("OWNER_NO") String ownerNo,
            @RequestBody @Valid OwnerCusDTO ownerCusDto,
            UriComponentsBuilder uriCB) {
		    ownercusservice.modifyOwnerCusInfo(customerNo,vin,ownerNo, ownerCusDto);
		   MultiValueMap<String, String> headers = new HttpHeaders();
	        headers.set("Location", uriCB.path("/OwnerManage/ownercus/{CUSTOMER_NO}/ni/{VIN}/in/{OWNER_NO}").buildAndExpand(customerNo,vin,ownerNo).toUriString());
	        return new ResponseEntity<OwnerCusDTO>(headers, HttpStatus.CREATED);
		
	}
    /**
     * 根据客户查询保有客户信息
     * 
     * @author zhanshiwei
     * @date 2016年8月17日
     * @param vehicle_id
     * @return
     */
	@SuppressWarnings({ "rawtypes", "unchecked" })
    @RequestMapping(value = "/{EMPLOYEE_NO}/in/{VIN}/ni/{OWNER_NO}", method = RequestMethod.GET)
    @ResponseBody
    public Map queryOwnerCusByEmployeeNo(@PathVariable(value = "EMPLOYEE_NO") String employeeNo,@PathVariable(value = "VIN") String vin,@PathVariable(value = "OWNER_NO") String ownerNo) {
    	Map pp=new HashMap();
    	String dealerCode = FrameworkUtil.getLoginInfo().getDealerCode();
    	List<Map> addressList = ownercusservice.queryOwnerCusByEmployee(employeeNo,vin,dealerCode);
    	if (addressList.size()>0){
    		pp.putAll(addressList.get(0));	
    	}
    	List<Map> ownerlist = ownercusservice.queryOwnerName(ownerNo,vin,dealerCode);
    	if (ownerlist.size()>0){
    		pp.putAll(ownerlist.get(0));	
    	}
    	List<Map> vehicleList = ownercusservice.queryOwnerVehicle(employeeNo,vin,dealerCode);
    	if (vehicleList.size()>0){
    		pp.putAll(vehicleList.get(0));	
    	}	
        return pp;
    }
    /**
     * 家庭
     * 
     * @author zhanshiwei
     * @date 2016年8月17日
     * @param vehicle_id
     * @return
     */
    @RequestMapping(value = "/{EMPLOYEE_NO}/family", method = RequestMethod.GET)
    @ResponseBody
    public List<Map> queryOwnerCusByFamilyNo(@PathVariable(value = "EMPLOYEE_NO") String employeeNo) {
    	String dealerCode = FrameworkUtil.getLoginInfo().getDealerCode();
    	List<Map> addressList = ownercusservice.queryOwnerCusByFamily(employeeNo,dealerCode);
        return addressList;
    }
    
    /**
     * 意向
     * 
     * @author zhanshiwei
     * @date 2016年8月17日
     * @param vehicle_id
     * @return
     */
    @RequestMapping(value = "/{CUSTOMER_NO}/history", method = RequestMethod.GET)
    @ResponseBody
    public List<Map> queryOwnerCusHistory(@PathVariable(value = "CUSTOMER_NO") String customerNo) {
        String dealerCode = FrameworkUtil.getLoginInfo().getDealerCode();
        List<Map> addressList = ownercusservice.queryOwnerCusHistroy(customerNo,dealerCode);
        return addressList;
    }
    
    /**
     * 意向
     * 
     * @author zhanshiwei
     * @date 2016年8月17日
     * @param vehicle_id
     * @return
     */
    @RequestMapping(value = "/{CUSTOMER_NO}/intent/{INTENT_ID}", method = RequestMethod.GET)
    @ResponseBody
    public List<Map> queryOwnerCusHistory(@PathVariable(value = "CUSTOMER_NO") String customerNo,@PathVariable(value = "INTENT_ID") String intentId) {
        String dealerCode = FrameworkUtil.getLoginInfo().getDealerCode();
        List<Map> addressList = ownercusservice.queryOwnerCusIntent(customerNo,dealerCode,intentId);
        return addressList;
    }
    /**
     * 联系人
     * 
     * @author zhanshiwei
     * @date 2016年8月17日
     * @param vehicle_id
     * @return
     */
    @RequestMapping(value = "/{EMPLOYEE_NO}/linkman", method = RequestMethod.GET)
    @ResponseBody
    public List<Map> queryOwnerCusByLinkmanNo(@PathVariable(value = "EMPLOYEE_NO") String employeeNo) {
    	String dealerCode = FrameworkUtil.getLoginInfo().getDealerCode();
    	List<Map> addressList = ownercusservice.queryOwnerCusByLinkman(employeeNo,dealerCode);
        return addressList;
    }
    
    /**
     * 根据客户查询保有客户信息
     * 
     * @author zhanshiwei
     * @date 2016年8月17日
     * @param vehicle_id
     * @return
     */
    @RequestMapping(value = "/{VIN}/insurance", method = RequestMethod.GET)
    @ResponseBody
    public List<Map> queryOwnerCusByInsurance(@PathVariable(value = "VIN") String vin) {
    	String dealerCode = FrameworkUtil.getLoginInfo().getDealerCode();
    	List<Map> addressList = ownercusservice.queryOwnerCusByInsurance(vin,dealerCode);
        return addressList;
    }
    
    /**
     * 根据客户查询保有客户信息
     * 
     * @author zhanshiwei
     * @date 2016年8月17日
     * @param vehicle_id
     * @return
     */
    @RequestMapping(value = "/vehicle", method = RequestMethod.GET)
    @ResponseBody
    public List<Map> queryOwnerCusVehicle() {
        String dealerCode = FrameworkUtil.getLoginInfo().getDealerCode();
        List<Map> addressList = ownercusservice.queryOwnerCusVehicle(dealerCode);
        return addressList;
    }
    
    /**
     * 关怀查询
     * 
     * @author gaoming
     * @date 2016年8月17日
     * @param vehicle_id
     * @return
     */
    @RequestMapping(value = "/{vin}/treat/{CUSTOMER_NO}", method = RequestMethod.GET)
    @ResponseBody
    public List<Map> queryOwnerCusByTreat(@PathVariable(value = "vin") String vin,@PathVariable(value = "CUSTOMER_NO") String customerNo) {
    	String dealerCode = FrameworkUtil.getLoginInfo().getDealerCode();
    	List<Map> addressList = ownercusservice.queryOwnerCusByTreat(customerNo,vin,dealerCode);
        return addressList;
    }
    /**
     * 潜客信息导出
     * 
     * @author zhanshiwei
     * @date 2016年9月11日
     * @param queryParam
     * @param request
     * @param response
     * @throws Exception
     */

    @SuppressWarnings("rawtypes")
    @RequestMapping(value = "/export/excel", method = RequestMethod.GET)
    public void exportPotentialCus(@RequestParam Map<String, String> queryParam, HttpServletRequest request,
                                   HttpServletResponse response) throws Exception {
        List<Map> resultList = ownercusservice.queryOwnerCusforExport(queryParam);
        Map<String, List<Map>> excelData = new HashMap<String, List<Map>>();
        excelData.put("潜客信息", resultList);
        List<ExcelExportColumn> exportColumnList = new ArrayList<ExcelExportColumn>();
        exportColumnList.add(new ExcelExportColumn("SUBMIT_STATUS", "上报状态", ExcelDataType.Dict));
        exportColumnList.add(new ExcelExportColumn("EXCEPTION_CAUSE", "异常原因"));
        exportColumnList.add(new ExcelExportColumn("VIS_UPLOAD", "是否上报", ExcelDataType.Dict));
        exportColumnList.add(new ExcelExportColumn("OEM_TAG", "OEM标志", ExcelDataType.Dict));
        exportColumnList.add(new ExcelExportColumn("CUSTOMER_NO", "潜客编号"));
        exportColumnList.add(new ExcelExportColumn("CUSTOMER_NAME", "客户名称"));
        exportColumnList.add(new ExcelExportColumn("CUSTOMER_TYPE", "客户类型", ExcelDataType.Dict));   
        exportColumnList.add(new ExcelExportColumn("GENDER", "客户性别", ExcelDataType.Dict));
        exportColumnList.add(new ExcelExportColumn("BIRTHDAY", "出生日期"));
        exportColumnList.add(new ExcelExportColumn("SOLD_BY", "销售顾问"));
        exportColumnList.add(new ExcelExportColumn("PHONE", "电话"));       
        exportColumnList.add(new ExcelExportColumn("PROVINCE", "省份", ExcelDataType.Region_Provice));
        exportColumnList.add(new ExcelExportColumn("CITY", " 城市", ExcelDataType.Region_City));
        exportColumnList.add(new ExcelExportColumn("DISTRICT", "区县", ExcelDataType.Region_Country));
        exportColumnList.add(new ExcelExportColumn("ZIP_CODE", "邮编"));
        exportColumnList.add(new ExcelExportColumn("ADDRESS", "地址"));
        exportColumnList.add(new ExcelExportColumn("E_MAIL", "邮箱"));
        exportColumnList.add(new ExcelExportColumn("CT_CODE", "证件类型", ExcelDataType.Dict));
        exportColumnList.add(new ExcelExportColumn("CERTIFICATE_NO", "证件号码"));
        exportColumnList.add(new ExcelExportColumn("EDUCATION_LEVEL", "学历", ExcelDataType.Dict));       
        exportColumnList.add(new ExcelExportColumn("OWNER_MARRIAGE", "婚姻状况", ExcelDataType.Dict));
        exportColumnList.add(new ExcelExportColumn("FAMILY_INCOME", "家庭月收入", ExcelDataType.Dict));
        exportColumnList.add(new ExcelExportColumn("INDUSTRY_FIRST", "行业大类", ExcelDataType.Dict));
        exportColumnList.add(new ExcelExportColumn("INDUSTRY_SECOND", "行业小类", ExcelDataType.Dict));
        exportColumnList.add(new ExcelExportColumn("AGE_STAGE", "年龄段", ExcelDataType.Dict));
        exportColumnList.add(new ExcelExportColumn("HOBBY", "爱好",ExcelDataType.Dict));
        exportColumnList.add(new ExcelExportColumn("VOCATION_TYPE", "职业", ExcelDataType.Dict));     
        exportColumnList.add(new ExcelExportColumn("POSITION_NAME", "职务名称"));        
        exportColumnList.add(new ExcelExportColumn("SALES_DATE", "开票日期"));
        exportColumnList.add(new ExcelExportColumn("SUBMIT_TIME", "上报日期"));
        exportColumnList.add(new ExcelExportColumn("LAST_SOLD_BY", "前销售顾问"));      
        exportColumnList.add(new ExcelExportColumn("IS_FIRST_BUY", "是否首次购车", ExcelDataType.Dict));
        exportColumnList.add(new ExcelExportColumn("BUY_PURPOSE", "购车目的", ExcelDataType.Dict));
        exportColumnList.add(new ExcelExportColumn("BUY_REASON", "购车因素", ExcelDataType.Dict));
        exportColumnList.add(new ExcelExportColumn("DCRC_SERVICE", "DCRC顾问"));
        exportColumnList.add(new ExcelExportColumn("PRODUCT_CODE", "产品代码车"));
        exportColumnList.add(new ExcelExportColumn("LICENSE", "车牌号"));
        exportColumnList.add(new ExcelExportColumn("INVOICE_CUSTOMER", "开票客户"));
        exportColumnList.add(new ExcelExportColumn("ORDER_SUM", "订单总额"));
        exportColumnList.add(new ExcelExportColumn("SALES_AGENT_NAME", "建档日期"));
        exportColumnList.add(new ExcelExportColumn("IS_DIRECT", "购车目的", ExcelDataType.Dict));
        exportColumnList.add(new ExcelExportColumn("IS_CONSIGNED", "购车因素", ExcelDataType.Dict));       
        exportColumnList.add(new ExcelExportColumn("SALES_AGENT_NAME", "经销商"));
        exportColumnList.add(new ExcelExportColumn("LICENSE_DATE", "上牌日期"));
        exportColumnList.add(new ExcelExportColumn("SO_NO", "销售订单"));
        exportColumnList.add(new ExcelExportColumn("IS_REMOVED", "建档日期", ExcelDataType.Dict));      
        exportColumnList.add(new ExcelExportColumn("REMARK", "备注"));
        exportColumnList.add(new ExcelExportColumn("MODEL_CODE", "车型"));
        exportColumnList.add(new ExcelExportColumn("COLOR_CODE", "颜色"));     
        // 生成excel 文件
        excelService.generateExcel(excelData, exportColumnList, "保有客户.xls", request, response);

    }
    
    /**
     * 再分配
     * 
     * @author LGQ
     * @date 2017年03月20日
     * @param OwnerCusDTO
     * @param uriCB
     * @return
     */
    @RequestMapping(value = "/soldBy/noList", method = RequestMethod.PUT)
    @ResponseStatus(HttpStatus.CREATED)
    public void modifyReRetainCusTrack(@RequestBody OwnerCusDTO ownerCusDTO,
                                           UriComponentsBuilder uriCB) {
        ownercusservice.modifySoldBy(ownerCusDTO);
    }
}
