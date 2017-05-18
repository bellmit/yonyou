
/** 
*Copyright 2016 Yonyou Corporation Ltd. All Rights Reserved.
* This software is published under the terms of the Yonyou Software
* License version 1.0, a copy of which has been included with this
* distribution in the LICENSE.txt file.
*
* @Project Name : dms.manage
*
* @File name : EmployeeController.java
*
* @Author : jcsi
*
* @Date : 2016年7月7日
*
----------------------------------------------------------------------------------
*     Date       Who       Version     Comments
* 1. 2016年7月7日    Administrator    1.0
*
*
*
*
----------------------------------------------------------------------------------
*/

package com.yonyou.dms.manage.controller.basedata;

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

import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.framework.domains.DTO.baseData.BasicParametersDTO;
import com.yonyou.dms.framework.service.baseData.SystemParamService;
import com.yonyou.dms.framework.service.excel.ExcelExportColumn;
import com.yonyou.dms.framework.service.excel.ExcelGenerator;
import com.yonyou.dms.framework.util.FrameworkUtil;
import com.yonyou.dms.function.common.CommonConstants;
import com.yonyou.dms.function.common.DictCodeConstants;
import com.yonyou.dms.function.exception.ServiceBizException;
import com.yonyou.dms.function.utils.common.CommonUtils;
import com.yonyou.dms.function.utils.common.StringUtils;
import com.yonyou.dms.manage.domains.DTO.basedata.EmployeeDto;
import com.yonyou.dms.manage.service.basedata.employee.EmployeeService;
import com.yonyou.f4.mvc.annotation.TxnConn;
import com.yonyou.f4.mvc.controller.BaseController;

/**
 * 经销商员工信息
 * 
 * @author jcsi
 * @date 2016年7月7日
 */
@Controller
@TxnConn
@RequestMapping("/basedata/employees")
public class EmployeeController extends BaseController {

    @Autowired
    private EmployeeService    employeeService;
    @Autowired
    private SystemParamService systemparamservice;

    @Autowired
    private ExcelGenerator     excelService;

    /**
     * 查询
     * 
     * @author jcsi
     * @date 2016年7月29日
     * @param param
     * @return
     */
    @RequestMapping(method = RequestMethod.GET)
    @ResponseBody
    public PageInfoDto search(@RequestParam Map<String, String> param) {
        PageInfoDto pageInfoDto = employeeService.searchGFkEmployees(param);
        return pageInfoDto;
    }

    /**
     * 员工下拉框加载
     * 
     * @author yll
     * @date 2016年7月20日
     * @param queryParam
     * @return
     */
    @SuppressWarnings("rawtypes")
	@RequestMapping(value = "/employees/dict", method = RequestMethod.GET)
    @ResponseBody
    public List<Map> selectEmployees(@RequestParam Map<String, String> queryParam) {
        List<Map> employeelist = employeeService.selectEmployees(queryParam);
        return employeelist;
    }
    /**
     * 关单人
     * 
     * @author yll
     * @date 2016年7月20日
     * @param queryParam
     * @return
     */
    @SuppressWarnings("rawtypes")
	@RequestMapping(value = "/shut/dict", method = RequestMethod.GET)
    @ResponseBody
    public List<Map> selectShutEmployees(@RequestParam Map<String, String> queryParam) {
        List<Map> employeelist = employeeService.selectShutEmployees(queryParam);
        return employeelist;
    }

    /**
     * 职位过滤员工
     * 
     * @author zhanshiwei
     * @date 2016年11月1日
     * @param role
     * @return
     */

    @SuppressWarnings("rawtypes")
	@RequestMapping(value = "/{role}/employeesdict", method = RequestMethod.GET)
    @ResponseBody
    public List<Map> selectEmployeeByrole(@PathVariable Integer role) {
        List<Map> employeelist = employeeService.selectEmployeeByrole(role);
        return employeelist;
    }

    /**
     * 根据数据权限范围控制职位过滤员工
     * 
     * @author
     * @date 2016年12月11日
     * @param role
     * @return
     */
    @SuppressWarnings("rawtypes")
	@RequestMapping(value = "/{role}/powerEmployeesdict", method = RequestMethod.GET)
    @ResponseBody
    public List<Map> selectPowerEmployeeByrole(@PathVariable Integer role, @RequestParam String menuId) {
        List<Map> employeelist = employeeService.selectPowerEmployeeByrole(role, menuId);
        return employeelist;
    }

    /**
     * 新增
     * 
     * @author jcsi
     * @date 2016年7月7日
     * @param empDto
     * @param uriCB
     * @throws Exception 
     */
    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<EmployeeDto> addEmployee(@RequestBody @Valid EmployeeDto empDto, UriComponentsBuilder uriCB) throws Exception {
        Long id = employeeService.addGFkmployee(empDto);
        MultiValueMap<String, String> headers = new HttpHeaders();
        headers.set("Location", uriCB.path("/basedata/employees/{id}").buildAndExpand(id).toUriString());
        return new ResponseEntity<EmployeeDto>(empDto, headers, HttpStatus.CREATED);
    }
    /**
     * 判断身份证号是否存在
     * 
     * @author jcsi
     * @date 2016年7月7日
     * @param empDto
     * @param uriCB
     * @throws Exception 
     */
    @RequestMapping(value = "/certificateId/{certificateId}/{id}", method = RequestMethod.GET)
    @ResponseBody
    public int queryByCertificateId(@PathVariable String certificateId,@PathVariable String id) throws Exception {
        Integer data = employeeService.SearchByCertificateId(certificateId,id);
       
        return data;
    }
    
    /**
     * 判断员工编号是否存在
     * 
     * @author jcsi
     * @date 2016年7月7日
     * @param empDto
     * @param uriCB
     * @throws Exception 
     */
    @RequestMapping(value = "/isEmployee/{id}", method = RequestMethod.GET)
    @ResponseBody
    public int queryIsEmployee(@PathVariable String id) throws Exception {
        Integer data = employeeService.SearchByEmployeeNo(id);
       
        return data;
    }
    
    /**
     * 判断手机号是否存在
     * 
     * @author jcsi
     * @date 2016年7月7日
     * @param empDto
     * @param uriCB
     * @throws Exception 
     */
    @RequestMapping(value = "/isMobile/{mobile}/{id}", method = RequestMethod.GET)
    @ResponseBody
    public int queryMobile(@PathVariable String mobile,@PathVariable String id) throws Exception {
        Integer data = employeeService.editSearchByMobile(mobile,id);
       
        return data;
    }
    /**
     * 判断手机号是否存在
     * 
     * @author jcsi
     * @date 2016年7月7日
     * @param empDto
     * @param uriCB
     * @throws Exception 
     */
    @RequestMapping(value = "/employeeNo/{id}", method = RequestMethod.GET)
    @ResponseBody
    public int queryEmployeeNo(@PathVariable String id) throws Exception {
        Integer data = employeeService.QueryEmployeeNo(id);
       
        return data;
    }

    
    /**
     * 判断是否是一级部门
     * 
     * @author jcsi
     * @date 2016年7月7日
     * @param empDto
     * @param uriCB
     * @throws Exception 
     */
    @RequestMapping(value = "/isOrgCode/{id}", method = RequestMethod.GET)
    @ResponseBody
    public int queryIsOrgcode(@PathVariable String id) throws Exception {
        Integer data = employeeService.queryOrgCode(id);
       
        return data;
    }


    /**
     * 根据id查找员工信息
     * 
     * @author jcsi
     * @date 2016年7月12日
     * @param id
     * @return
     */
    @SuppressWarnings("unchecked")
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> findByEmployeeId(@PathVariable String id) {
    	System.err.println(id);
        Map<String, Object> map = null;
        Map<String, Object> user = null;
        if (!StringUtils.isEquals(id, "-1")) {
            System.out.println(id);
            map = employeeService.findById(id);
            user = employeeService.findUserByEmployeeId(id);
        } else {
            map = new HashMap<String, Object>();
            user = new HashMap<String, Object>();
        }
        /*
         * // 查找员工角色表 转成字符串用逗号分隔 插入map中 List<Map> roles = employeeService.findEmpRolesByEmpId(id); String rolesStr = "";
         * rolesStr = CommonUtils.listMapToString(roles, ","); map.put("roles", rolesStr);
         */

        if (!CommonUtils.isNullOrEmpty(user)) {
            map.putAll(user);
        } else {
            System.out.println("1111");
            map.put("USER_STATUS", DictCodeConstants.STATUS_QY);// 初始值为启用
        }
        return map;
    }

    /**
     * 根据id删除员工信息
     * 
     * @author jcsi
     * @date 2016年7月12日
     * @param id
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteEmployee(@PathVariable String id) {
        employeeService.deleteById(id);
    }

    /**
     * 更新员工信息
     * 
     * @author jcsi
     * @date 2016年7月12日
     * @param id
     * @param empDto
     * @param uriCB
     * @return
     * @throws Exception 
     * @throws ServiceBizException 
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public ResponseEntity<EmployeeDto> updateEmployee(@PathVariable String id, @RequestBody @Valid EmployeeDto empDto,
                                                      UriComponentsBuilder uriCB) throws ServiceBizException, Exception {
    	
        employeeService.updateGFkEmpById(id, empDto);
        MultiValueMap<String, String> headers = new HttpHeaders();
        headers.set("Location", uriCB.path("/basedata/employees/{id}").buildAndExpand(id).toUriString());
        return new ResponseEntity<EmployeeDto>(headers, HttpStatus.CREATED);
    }

    /**
     * 查询员工信息(员工权限编辑页面)
     * 
     * @author yll
     * @date 2016年8月16日
     * @param param
     * @return
     */
    @RequestMapping(value = "/permission/items", method = RequestMethod.GET)
    @ResponseBody
    public PageInfoDto searchRoleEmp(@RequestParam Map<String, String> param) {
        PageInfoDto pageInfoDto = employeeService.searchUserGFkEmployees(param);
        return pageInfoDto;
    }
    
    /**
     * 查询员工信息(员工权限首页面)
     * 
     * @author yll
     * @date 2016年8月16日
     * @param param
     * @return
     */
    @RequestMapping(value = "/permission/item", method = RequestMethod.GET)
    @ResponseBody
    public PageInfoDto searchRoleEmployee(@RequestParam Map<String, String> param) {
        PageInfoDto pageInfoDto = employeeService.searchUserGFkEmployee(param);
        return pageInfoDto;
    }

    /**
     * 查询销售顾问
     * 
     * @author xukl
     * @date 2016年9月13日
     * @param orgCode
     * @return
     */
    @SuppressWarnings("rawtypes")
    @RequestMapping(value = "/{orgCode}/salesConsultant", method = RequestMethod.GET)
    @ResponseBody
    public List<Map> qrySalesConsultant(@PathVariable String orgCode) {
        List<Map> list = employeeService.qrySalesConsultant(orgCode);
        return list;
    }
    
    /**
     * 查询装潢专员
     * 
     * @author wangliang
     * @date 2017年3月22日
     * @return
     * @throws Exception 
     */
    @SuppressWarnings("rawtypes")
    @RequestMapping(value = "/DecorationSpecialist", method = RequestMethod.GET)
    @ResponseBody
    public List<Map> qrySalesConsultant() throws Exception {
        List<Map> list = employeeService.queryDecorationSpecialist();
        return list;
    }
    
    /**
     * 查询装潢专员
     * 
     * @author Benzc
     * @date 2017年5月12日
     * @return
     * @throws Exception 
     */
    @SuppressWarnings("rawtypes")
    @RequestMapping(value = "/DecorationWorker", method = RequestMethod.GET)
    @ResponseBody
    public List<Map> qryDecorationWorker() throws Exception {
        List<Map> list = employeeService.queryDecorationWorker();
        return list;
    }
    
    /**
     * 查询发票类型
     * 
     * @author gm
     * @date 2016年9月13日
     * @param orgCode
     * @return
    */
    @SuppressWarnings("rawtypes")
    @RequestMapping(value = "/{orgCode}/salesInvoice", method = RequestMethod.GET)
    @ResponseBody
    public List<Map> qrySalesInvoice(@PathVariable String orgCode) {
        List<Map> list = employeeService.qrySalesInvoice(orgCode);
        return list;
    }
    /**
     * 查询受数据范围权限管控的销售顾问
     * 
     * @author xukl
     * @date 2016年9月13日
     * @param orgCode
     * @return
     */
    @SuppressWarnings("rawtypes")
    @RequestMapping(value = "/{orgCode}/powerSalesConsultant", method = RequestMethod.GET)
    @ResponseBody
    public List<Map> qryPowerSalesConsultant(@PathVariable String orgCode, @RequestParam String menuId) {
        List<Map> list = employeeService.qryPowerSalesConsultant(menuId);
        return list;
    }

    /**
     * 根据基本参数查询销售经理财务经理
     * 
     * @author xukl
     * @date 2016年9月13日
     * @param orgCode
     * @return
     */
    @RequestMapping(value = "/slctSOrFAudit/conductors", method = RequestMethod.GET)
    @ResponseBody
    public PageInfoDto qryAudit() {
        List<BasicParametersDTO> basiDtolist = systemparamservice.queryBasicParameterByType(Long.valueOf(DictCodeConstants.VEHICLE_BASIC_CODE));
        PageInfoDto list = employeeService.qryAudit(basiDtolist);
        return list;
    }

    /**
     * @author zhanshiwei
     * @date 2017年1月3日
     * @param employeeNo
     * @return
     */

    @RequestMapping(value = "/employeSaveBefore/{employeeNo}", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> employeSaveBeforeEvent(@PathVariable("employeeNo") String employeeNo) {
        Map<String, Object> result = employeeService.employeSaveBeforeEvent(employeeNo);
        if(result!=null&&!result.isEmpty()){
            result.put("success", "false");     
        }else{
            result=new HashMap<String, Object>();
            result.put("success", "true"); 
        }
        return result;
    }

    /**
     * 销售订单-审核选择财务经理
     * 
     * @author xukl
     * @date 2016年9月28日
     * @return
     */
    @SuppressWarnings("rawtypes")
	@RequestMapping(value = "/slctFAudit/moneys", method = RequestMethod.GET)
    @ResponseBody
    public List<Map> qryFinanceAudit() {
        List<Map> list = employeeService.qryFinanceAudit();
        return list;
    }

    /**
     * 派工技师下拉框
     * 
     * @author rongzoujie
     * @date 2016年9月26日
     * @return
     */
    @SuppressWarnings("rawtypes")
	@RequestMapping(value = "/queryTechnician/dicts", method = RequestMethod.GET)
    @ResponseBody
    public List<Map> queryTechnician() {
        return employeeService.queryTechnician();
    }

    /**
     * 客户经理
     * 
     * @author 
     * @date 2017年5月27日
     * @return
     */
    @SuppressWarnings("rawtypes")
	@RequestMapping(value = "/queryServiceAss/dicts", method = RequestMethod.GET)
    @ResponseBody
    public List<Map> queryServiceAss() {
        return employeeService.queryServiceAss();
    }

    /**
     * @author zhanshiwei
     * @date 2017年1月3日
     * @return
     */

    @SuppressWarnings("rawtypes")
	@RequestMapping(value = "/queryFinishUser/items", method = RequestMethod.GET)
    @ResponseBody
    public List<Map> queryFinishUser() {
        return employeeService.queryFinisher();
    }

    /**
     * 技师级别查询下拉
     * 
     * @author zhanshiwei
     * @date 2016年12月16日
     * @return
     */

    @SuppressWarnings("rawtypes")
	@RequestMapping(value = "/queryTechLevelr/items", method = RequestMethod.GET)
    @ResponseBody
    public List<Map> queryTechLevel() {
        return employeeService.queryTechLevel();
    }

    /**
     * 调度组信息查询
     * 
     * @author zhanshiwei
     * @date 2016年12月16日
     * @return
     */

    @SuppressWarnings("rawtypes")
	@RequestMapping(value = "/queryDispatchInfo/items", method = RequestMethod.GET)
    @ResponseBody
    public List<Map> queryDispatchInfo() {
        return employeeService.queryDispatchInfo();
    }

    /**
     * 工序信息查询
     * 
     * @author zhanshiwei
     * @date 2016年12月16日
     * @return
     */

    @SuppressWarnings("rawtypes")
	@RequestMapping(value = "/queryWorkOrder/items", method = RequestMethod.GET)
    @ResponseBody
    public List<Map> queryWorkOrder() {
        return employeeService.queryWorkOrder();
    }

    /**
     * 根据主表主键id查询明细信息
     * 
     * @author zhanshiwei
     * @date 2016年8月2日
     * @param id
     * @return
     */

    @SuppressWarnings("rawtypes")
    @RequestMapping(value = "/{employeeNo}/emptra", method = RequestMethod.GET)
    @ResponseBody
    public List<Map> queryEmptraInfo(@PathVariable("employeeNo") String employeeNo) {
        List<Map> addressList = employeeService.queryEmptraInfo(employeeNo);
        return addressList;
    }

    /**
     * 离职
     * 
     * @author zhanshiwei
     * @date 2016年12月20日
     * @param id
     * @param empDto
     * @param uriCB
     * @return
     */

    @RequestMapping(value = "/{employeeNo}/updateIsValid", method = RequestMethod.PUT)
    public ResponseEntity<EmployeeDto> updateEmpIsValid(@PathVariable("employeeNo") String employeeNo,
                                                        UriComponentsBuilder uriCB) {
    	String employeesNo=FrameworkUtil.getLoginInfo().getEmployeeNo();
    	if(employeesNo.equals(employeeNo)){
    		throw new ServiceBizException("不能对自己进行离职操作！");
    	}
        Long id = employeeService.updateEmpIsValid(employeeNo);
        MultiValueMap<String, String> headers = new HttpHeaders();
        headers.set("Location", uriCB.path("/basedata/employees/{id}").buildAndExpand(id).toUriString());
        return new ResponseEntity<EmployeeDto>(headers, HttpStatus.CREATED);
    }
    
    /**
     * 复职
     * 
     * @author zhanshiwei
     * @date 2016年12月20日
     * @param id
     * @param empDto
     * @param uriCB
     * @return
     */

    @RequestMapping(value = "/{employeeNo}/changeIsValid", method = RequestMethod.PUT)
    public ResponseEntity<EmployeeDto> changeEmpIsValid(@PathVariable("employeeNo") String employeeNo,
                                                        UriComponentsBuilder uriCB) {
    	System.err.println(employeeNo);
        Long id = employeeService.changeEmpIsValid(employeeNo);
        MultiValueMap<String, String> headers = new HttpHeaders();
        headers.set("Location", uriCB.path("/basedata/employees/{id}").buildAndExpand(id).toUriString());
        return new ResponseEntity<EmployeeDto>(headers, HttpStatus.CREATED);
    }

    /**
     * 导出
     * 
     * @author zhanshiwei
     * @date 2017年1月2日
     * @param queryParam
     * @param request
     * @param response
     * @throws Exception
     */

    @SuppressWarnings("rawtypes")
    @RequestMapping(value = "/export/excel", method = RequestMethod.GET)
    public void exportCarownerInfo(@RequestParam Map<String, String> queryParam, HttpServletRequest request,
                                   HttpServletResponse response) throws Exception {
        List<Map> resultList = employeeService.queryEmpInfoforExport(queryParam);
        Map<String, List<Map>> excelData = new HashMap<String, List<Map>>();
        excelData.put("员工信息管理", resultList);
        // 生成excel 文件
        List<ExcelExportColumn> exportColumnList = new ArrayList<ExcelExportColumn>();
        exportColumnList.add(new ExcelExportColumn("EMPLOYEE_NO", "员工编号"));
        exportColumnList.add(new ExcelExportColumn("EMPLOYEE_NAME", "员工姓名"));
        exportColumnList.add(new ExcelExportColumn("GENDER", "性别"));
        exportColumnList.add(new ExcelExportColumn("CERTIFICATE_ID", "身份证号"));
        exportColumnList.add(new ExcelExportColumn("ORG_NAME", "部门名称"));
        exportColumnList.add(new ExcelExportColumn("WORKGROUP_NAME", "班组代码"));
        exportColumnList.add(new ExcelExportColumn("WORKER_TYPE_NAME", "工种名称"));
        exportColumnList.add(new ExcelExportColumn("POSITION_NAME", "职位"));
        exportColumnList.add(new ExcelExportColumn("IS_TECHNICIAN", "是否是技师"));
        exportColumnList.add(new ExcelExportColumn("TECHNICIAN_GRADE", "是否是技师"));
        exportColumnList.add(new ExcelExportColumn("IS_SERVICE_ADVISOR", "是否客户经理"));
        exportColumnList.add(new ExcelExportColumn("IS_INSURANCE_ADVISOR", "续保专员"));
        exportColumnList.add(new ExcelExportColumn("IS_MAINTAIN_ADVISOR", "定保专员"));
        exportColumnList.add(new ExcelExportColumn("IS_TRACER", "是否跟踪员"));
        exportColumnList.add(new ExcelExportColumn("PHONE", "电话"));
        exportColumnList.add(new ExcelExportColumn("MOBILE", "手机"));
        exportColumnList.add(new ExcelExportColumn("E-Mail", "E-Mail"));
        exportColumnList.add(new ExcelExportColumn("BIRTHDAY", "出生日期",CommonConstants.SIMPLE_DATE_FORMAT));
        exportColumnList.add(new ExcelExportColumn("ADDRESS", "地址"));
        exportColumnList.add(new ExcelExportColumn("ZIP_CODE", "邮政编码"));
        exportColumnList.add(new ExcelExportColumn("FOUND_DATE", "建档日期",CommonConstants.SIMPLE_DATE_FORMAT));
        exportColumnList.add(new ExcelExportColumn("ENTRY_DATE", "入职日期",CommonConstants.SIMPLE_DATE_FORMAT));
        exportColumnList.add(new ExcelExportColumn("DIMISSION_DATE", "离职日期",CommonConstants.SIMPLE_DATE_FORMAT));
        exportColumnList.add(new ExcelExportColumn("RESUME", "个人简历"));
        exportColumnList.add(new ExcelExportColumn("TRAINING", "培训简历"));
        exportColumnList.add(new ExcelExportColumn("LABOUR_POSITION_NAME", "主维修工位"));
        exportColumnList.add(new ExcelExportColumn("IS_TEST_DRIVER", "是否试车员"));
        exportColumnList.add(new ExcelExportColumn("IS_CHECKER", "是否检验员"));
        exportColumnList.add(new ExcelExportColumn("IS_TAKE_PART", "是否领料"));
        exportColumnList.add(new ExcelExportColumn("IS_VALID", "是否有效"));
        exportColumnList.add(new ExcelExportColumn("LABOUR_FACTOR", "工时系数"));
        exportColumnList.add(new ExcelExportColumn("IS_UPHOLSTER_ADVISOR", "是否装潢专员"));
        exportColumnList.add(new ExcelExportColumn("IS_INSURATION_ADVISOR", "是否保险专员"));
        exportColumnList.add(new ExcelExportColumn("DISPATCH_NAME", "默认调度组"));
        exportColumnList.add(new ExcelExportColumn("DEFAULT_POSITION_NAME", "默认工位"));
        exportColumnList.add(new ExcelExportColumn("IS_MAJOR_REPAIRER", "是否主修"));
        exportColumnList.add(new ExcelExportColumn("IS_DISPATCHER", "是否调度员"));
        exportColumnList.add(new ExcelExportColumn("TECH_LEVEL_NAME", "技师级别"));
        exportColumnList.add(new ExcelExportColumn("ORDER_NAME", "默认工序"));
        exportColumnList.add(new ExcelExportColumn("IS_UPLOAD", "是否上报"));
        excelService.generateExcelForDms(excelData, exportColumnList,FrameworkUtil.getLoginInfo().getDealerShortName()+"_员工信息管理.xls", request, response);

    }

    /**
     * 员工新建账号
     * 
     * @author zhanshiwei
     * @date 2017年1月8日
     * @param param
     * @return
     */

    @RequestMapping(value = "/userRoleInfoSel", method = RequestMethod.GET)
    @ResponseBody
    public PageInfoDto searchEmplAsUser(@RequestParam Map<String, String> param) {
        PageInfoDto pageInfoDto = employeeService.searchEmplAsUser(param);
        return pageInfoDto;
    }
    @RequestMapping(value = "/addEmplsel",method = RequestMethod.GET)
    @ResponseBody
    public PageInfoDto searchaddEmplsel(@RequestParam Map<String, String> param) {
    	System.err.println("1");
        PageInfoDto pageInfoDto = employeeService.addEmplsel(param);
        return pageInfoDto;
    }

    @SuppressWarnings("rawtypes")
    @RequestMapping(value = "/{menuId}/qrySalesConsultant", method = RequestMethod.GET)
    @ResponseBody
    public List<Map> qryJurSalesConsultant(@PathVariable String menuId) {
        List<Map> list = employeeService.qryJurSalesConsultant(menuId);
        return list;
    }
    
    /**
     * 查询销售顾问
     * 
     * @author xukl
     * @date 2017年3月27日
     * @param orgCode
     * @return
     */
    @SuppressWarnings("rawtypes")
    @RequestMapping(value = "/isEmployee/salesConsultant", method = RequestMethod.GET)
    @ResponseBody
    public List<Map> queryIsEmployee() {
        List<Map> list = employeeService.queryIsEmployee();
        return list;
    }
    
    @SuppressWarnings("rawtypes")
    @RequestMapping(value = "/queryTrancer", method = RequestMethod.GET)
    @ResponseBody
    public List<Map> queryTrancer() {
        List<Map> list = employeeService.queryTrancer();
        return list;
    }
    
}
