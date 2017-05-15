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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.util.UriComponentsBuilder;

import com.yonyou.dms.customer.domains.DTO.customerManage.BigCustomerOrgApplyDTO;
import com.yonyou.dms.customer.service.customerManage.BigCustomerOrgApplyService;
import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.framework.service.excel.ExcelExportColumn;
import com.yonyou.dms.framework.service.excel.ExcelGenerator;
import com.yonyou.dms.framework.util.FrameworkUtil;
import com.yonyou.f4.mvc.annotation.TxnConn;
import com.yonyou.f4.mvc.controller.BaseController;

/**
 * 大客户组织架构权限申请查询
 * @author Administrator
 *
 */
@Controller
@TxnConn
@RequestMapping("/bigCustomer/orgApply")
public class BigCustomerOrgApplyContoller extends BaseController{
	
	@Autowired
    private BigCustomerOrgApplyService bigcustomerorgapplyservice;
	
    @Autowired
    private ExcelGenerator excelService;
	
	/**
	 * 大客户组织架构权限申请查询
	 * @param queryParam
	 * @return
	 */
	@RequestMapping(method = RequestMethod.GET)
    @ResponseBody
    public PageInfoDto querybigCusApply(@RequestParam Map<String, String> queryParam) {
        PageInfoDto pageInfoDto = bigcustomerorgapplyservice.querybigCusApply(queryParam);
        return pageInfoDto;
    }
	
    @RequestMapping(method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<BigCustomerOrgApplyDTO> addBigCustomerOrg(@RequestBody @Valid BigCustomerOrgApplyDTO bigCustomerOrgApplyDto,
                                                           UriComponentsBuilder uriCB) {
        bigcustomerorgapplyservice.addBigCustomerOrg(bigCustomerOrgApplyDto);
        MultiValueMap<String, String> headers = new HttpHeaders();
        headers.set("Location", uriCB.path("/bigCustomer/orgApply").buildAndExpand().toUriString());
        return new ResponseEntity<BigCustomerOrgApplyDTO>(bigCustomerOrgApplyDto, headers, HttpStatus.CREATED);
    }
    
    
    @RequestMapping(value="/export/excel",method = RequestMethod.GET)
    public void excel(@RequestParam Map<String, String> queryParam, HttpServletRequest request,
                      HttpServletResponse response){
        List<Map> resultList =bigcustomerorgapplyservice.querySafeToExport(queryParam);
        Map<String, List<Map>> excelData = new HashMap<String, List<Map>>();
        excelData.put(FrameworkUtil.getLoginInfo().getDealerShortName()+"_大客户组织架构权限申请", resultList);
        List<ExcelExportColumn> exportColumnList = new ArrayList<ExcelExportColumn>();
        exportColumnList.add(new ExcelExportColumn("APPLY_NO","申请单号"));
        exportColumnList.add(new ExcelExportColumn("USER_CODE","用户代码"));
        exportColumnList.add(new ExcelExportColumn("EMPLOYEE_NO","员工代码"));
        exportColumnList.add(new ExcelExportColumn("USER_NAME","用户名称"));
        exportColumnList.add(new ExcelExportColumn("APPLY_DATE","申请日期"));
        exportColumnList.add(new ExcelExportColumn("APPLY_STATUS","申请状态"));
        exportColumnList.add(new ExcelExportColumn("REMARK","申请理由"));       
        excelService.generateExcelForDms(excelData, exportColumnList, FrameworkUtil.getLoginInfo().getDealerShortName()+"_大客户组织架构权限申请.xls", request, response);
    }

}
