
/** 
 *Copyright 2016 Yonyou Corporation Ltd. All Rights Reserved.
 * This software is published under the terms of the Yonyou Software
 * License version 1.0, a copy of which has been included with this
 * distribution in the LICENSE.txt file.
 *
 * @Project Name : dms.manage
 *
 * @File name : EmployeeService.java
 *
 * @Author : jcsi
 *
 * @Date : 2016年7月8日
 *
----------------------------------------------------------------------------------
 *     Date       Who       Version     Comments
 * 1. 2016年7月8日    Administrator    1.0
 *
 *
 *
 *
----------------------------------------------------------------------------------
 */

package com.yonyou.dms.manage.service.basedata.employee;

import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.RequestParam;

import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.framework.domains.DTO.baseData.BasicParametersDTO;
import com.yonyou.dms.function.exception.ServiceBizException;
import com.yonyou.dms.manage.domains.DTO.basedata.EmployeeDto;

/**
 * 员工信息 接口
 * 
 * @author jcsi
 * @date 2016年7月29日
 */
public interface EmployeeService {

    public Long addEmployee(EmployeeDto empDto) throws ServiceBizException;

    public PageInfoDto searchEmployees(@RequestParam Map<String, String> param) throws ServiceBizException;

    @SuppressWarnings("rawtypes")
	public Map findById(String id) throws ServiceBizException;

    public void updateEmpById(String id, EmployeeDto empDto) throws ServiceBizException;

    public void deleteById(String id) throws ServiceBizException;

    @SuppressWarnings("rawtypes")
	public List<Map> findEmpRolesByEmpId(Long id) throws ServiceBizException;

    @SuppressWarnings("rawtypes")
	public List<Map> selectEmployees(Map<String, String> queryParam) throws ServiceBizException;
    
    @SuppressWarnings("rawtypes")
	public List<Map> queryIsEmployee() throws ServiceBizException;
    
    @SuppressWarnings("rawtypes")
	public List<Map> selectShutEmployees(Map<String, String> queryParam) throws ServiceBizException;

    public PageInfoDto searchUserEmployees(Map<String, String> param) throws ServiceBizException;

    @SuppressWarnings("rawtypes")
	public Map findUserByEmployeeId(String id) throws ServiceBizException;

    @SuppressWarnings("rawtypes")
	public List<Map> qrySalesConsultant(String orgCode) throws ServiceBizException;
    
    @SuppressWarnings("rawtypes")
	public List<Map> qrySalesInvoice(String orgCode) throws ServiceBizException;

    @SuppressWarnings("rawtypes")
	public List<Map> qryPowerSalesConsultant(String menuId) throws ServiceBizException;

    public PageInfoDto qryAudit(List<BasicParametersDTO> basiDtolist) throws ServiceBizException;

    @SuppressWarnings("rawtypes")
	public List<Map> queryTechnician() throws ServiceBizException;

    @SuppressWarnings("rawtypes")
	public List<Map> queryServiceAss() throws ServiceBizException;

    @SuppressWarnings("rawtypes")
	public List<Map> qryFinanceAudit() throws ServiceBizException;

    @SuppressWarnings("rawtypes")
	public List<Map> queryFinisher() throws ServiceBizException;

    public void updateEmpById(Long id, List<String> employeeRoles) throws ServiceBizException;

    @SuppressWarnings("rawtypes")
	public List<Map> selectEmployeeByrole(Integer role) throws ServiceBizException;

    @SuppressWarnings("rawtypes")
	public List<Map> selectPowerEmployeeByrole(Integer role, String menuId) throws ServiceBizException;

    @SuppressWarnings("rawtypes")
	public List<Map> queryTechLevel() throws ServiceBizException;

    @SuppressWarnings("rawtypes")
	public List<Map> queryDispatchInfo() throws ServiceBizException;

    @SuppressWarnings("rawtypes")
	public List<Map> queryWorkOrder() throws ServiceBizException;

    @SuppressWarnings("rawtypes")
	public List<Map> queryEmptraInfo(String employeeNo) throws ServiceBizException;

    public Long updateEmpIsValid(String employeeNo) throws ServiceBizException;//离职
    
    public Long changeEmpIsValid(String employeeNo) throws ServiceBizException;//复职

    @SuppressWarnings("rawtypes")
	public List<Map> queryEmpInfoforExport(Map<String, String> param) throws ServiceBizException;

    public Map<String, Object> employeSaveBeforeEvent(String employeeNo) throws ServiceBizException;

    public Long addGFkmployee(EmployeeDto empDto) throws ServiceBizException, Exception;

    public void updateGFkEmpById(String id, EmployeeDto empDto) throws ServiceBizException, Exception;

    public PageInfoDto searchGFkEmployees(@RequestParam Map<String, String> param) throws ServiceBizException;

    public PageInfoDto searchUserGFkEmployees(Map<String, String> param) throws ServiceBizException;//权限复制员工列表
    
    public PageInfoDto searchUserGFkEmployee(Map<String, String> param) throws ServiceBizException;//首页员工列表

    public PageInfoDto searchEmplAsUser(Map<String, String> param) throws ServiceBizException;
    
    @SuppressWarnings("rawtypes")
	public Map findGFkUserById(Long id) throws ServiceBizException;
    public PageInfoDto addEmplsel(Map<String, String> param) throws ServiceBizException;
    @SuppressWarnings("rawtypes")
	public List<Map> qryJurSalesConsultant(String menuId) throws ServiceBizException;
    
    @SuppressWarnings("rawtypes")
	public List<Map> queryDecorationSpecialist() throws Exception;
    
    public int queryOrgCode(String id) throws Exception;//判断是否是一级部门组织
    
    public int SearchByEmployeeNo(String id) throws Exception;//判断员工编号是否存在
    
    public int editSearchByMobile(String Mobile,String id) throws Exception;//判断手机号是否存在
    
    public int SearchByCertificateId(String CertificateId,String id) throws Exception;//判断身份证号是否存在
   
    public int QueryEmployeeNo(String id) throws Exception;//判断该员工编号是否存在用户
    
    @SuppressWarnings("rawtypes")
	public List<Map> queryTrancer() throws ServiceBizException;
    
    @SuppressWarnings("rawtypes")
	public List<Map> queryDecorationWorker() throws Exception;
    
}
