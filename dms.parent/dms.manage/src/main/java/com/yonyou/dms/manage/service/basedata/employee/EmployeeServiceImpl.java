
/** 
 *Copyright 2016 Yonyou Corporation Ltd. All Rights Reserved.
 * This software is published under the terms of the Yonyou Software
 * License version 1.0, a copy of which has been included with this
 * distribution in the LICENSE.txt file.
 *
 * @Project Name : dms.manage
 *
 * @File name : EmployeeServiceImpl.java
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

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import com.yonyou.dms.common.domains.DTO.monitor.OperateLogDto;
import com.yonyou.dms.common.domains.PO.basedata.EmployeePo;
import com.yonyou.dms.common.domains.PO.basedata.UserPO;
import com.yonyou.dms.common.service.monitor.OperateLogService;
import com.yonyou.dms.framework.DAO.DAOUtil;
import com.yonyou.dms.framework.DAO.DAOUtilGF;
import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.framework.domains.DTO.baseData.BasicParametersDTO;
import com.yonyou.dms.framework.util.FrameworkUtil;
import com.yonyou.dms.framework.util.Utility;
import com.yonyou.dms.function.common.CommonConstants;
import com.yonyou.dms.function.common.DictCodeConstants;
import com.yonyou.dms.function.exception.ServiceBizException;
import com.yonyou.dms.function.utils.common.CommonUtils;
import com.yonyou.dms.function.utils.common.StringUtils;
import com.yonyou.dms.manage.domains.DTO.basedata.EmployeeDto;
import com.yonyou.dms.manage.domains.DTO.basedata.EmployeeTrainingDto;
import com.yonyou.dms.manage.domains.PO.basedata.DealerBasicinfoPO;
import com.yonyou.dms.manage.domains.PO.basedata.EmployeeRolePo;
import com.yonyou.dms.manage.domains.PO.basedata.EmployeeTrainingPO;
import com.yonyou.dms.manage.service.basedata.user.UserService;

/**
 * 员工信息 实现类
 * 
 * @author jcsi
 * @date 2016年7月8日
 */

@Service
public class EmployeeServiceImpl implements EmployeeService {
	
	 @Autowired
	    private OperateLogService operateLogService;
	 @Autowired
		private UserService userService;

    /**
     * 根据条件查询员工信息
     * 
     * @author jcsi
     * @date 2016年7月29日
     * @param param
     * @return (non-Javadoc)
     * @see com.yonyou.dms.manage.service.basedata.employee.EmployeeService#searchEmployees(java.util.Map)
     */

    public PageInfoDto searchEmployees(@RequestParam Map<String, String> param) throws ServiceBizException {
        StringBuilder sb = new StringBuilder("select tme.EMPLOYEE_ID,tme.DEALER_CODE,tme.EMPLOYEE_NO,tme.EMPLOYEE_NAME,tme.GENDER,tme.CERTIFICATE_ID,tmdo.ORG_NAME,tme.MOBILE,tme.IS_ONJOB from tm_employee tme LEFT JOINtm_organization tmdo on tme.ORGANIZATION_ID=tmdo.ORGDEPT_ID and tmdo.DEALER_CODE=tme.DEALER_CODE where 1=1 ");
        List<Object> queryParam = new ArrayList<Object>();
        if (!StringUtils.isNullOrEmpty(param.get("employeeName"))) {
            sb.append(" and EMPLOYEE_NAME like ?");
            queryParam.add("%" + param.get("employeeName") + "%");
        }
        if (!StringUtils.isNullOrEmpty(param.get("employeeNo"))) {
            sb.append(" and EMPLOYEE_NO like ?");
            queryParam.add("%" + param.get("employeeNo") + "%");
        }
        if (!StringUtils.isNullOrEmpty(param.get("isOnjob"))) {
            sb.append(" and IS_ONJOB = ?");
            queryParam.add(Integer.parseInt(param.get("isOnjob") + ""));
        }
        if (!StringUtils.isNullOrEmpty(param.get("orgName"))) {
            sb.append(" and ORG_NAME like ? ");
            queryParam.add("%" + param.get("orgName") + "%");
        }
        return DAOUtil.pageQuery(sb.toString(), queryParam);

    }

    /**
     * 新增员工信息
     * 
     * @author jcsi
     * @date 2016年7月29日
     * @param empDto
     * @return (non-Javadoc)
     * @see com.yonyou.dms.manage.service.basedata.employee.EmployeeService#addEmployee(com.yonyou.dms.manage.domains.DTO.basedata.EmployeeDto)
     */

    @Override
    public Long addEmployee(EmployeeDto empDto) throws ServiceBizException {
        EmployeePo emp = new EmployeePo();
        EmployeeRolePo erPo = null;
        String employeeNo = FrameworkUtil.getLoginInfo().getDealerCode() + empDto.getEmployeeNo(); // 员工编号
        // 查询数据库中是否已经存在此员工编号 如果不存在保存 否则抛出异常
        int isTrue = SearchByEmployeeNo(employeeNo);
        int mobileIsTrue = SearchByMobile(empDto.getMobile(), null);
     /*   int CertificateIdIsTrue = SearchByCertificateId(empDto.getCertificateId(), null);
        if (CertificateIdIsTrue >0) {
            throw new ServiceBizException("该身份证号已经存在");
        }*/
        if (isTrue==0) {
            if (mobileIsTrue==0) {
                emp.setString("EMPLOYEE_NO", employeeNo);
                setEmployee(emp, empDto);
                emp.saveIt();
                // 保存用户角色
                List<String> list = empDto.getEmployeeRoles();
                for (int i = 0; i < list.size(); i++) {
                    erPo = new EmployeeRolePo();
                    erPo.setString("ROLE", list.get(i));
                    emp.add(erPo);
                }

                return emp.getLongId();
            } else {
                throw new ServiceBizException("该手机号已存在");
            }
        } else {
            throw new ServiceBizException("该员工编号已经存在");
        }

    }

    /**
     * 查询数据库中是否已经存在此员工编号
     * 
     * @author jcsi
     * @date 2016年8月1日
     * @param employeeNo
     * @return
     * @throws ServiceBizException
     */
    @SuppressWarnings("rawtypes")
	public int SearchByEmployeeNo(String employeeNo) throws ServiceBizException {
        StringBuilder sb = new StringBuilder("SELECT t.EMPLOYEE_ID,t.DEALER_CODE from tm_employee t where t.EMPLOYEE_NO=?");
        List<Object> param = new ArrayList<Object>();
        param.add(employeeNo);
        List<Map> map = DAOUtil.findAll(sb.toString(), param);
        
        return map.size();
    }
    /**
     * 保存时查询数据库中是否已经存在此员工编号
     * 
     * @author jcsi
     * @date 2016年8月1日
     * @param employeeNo
     * @return
     * @throws ServiceBizException
     */
    @SuppressWarnings("rawtypes")
	public int SearchByEmployeeId(String employeeNo) throws ServiceBizException {
        StringBuilder sb = new StringBuilder("SELECT t.EMPLOYEE_ID,t.DEALER_CODE from tm_employee t where t.EMPLOYEE_ID=?");
        List<Object> param = new ArrayList<Object>();
        param.add(employeeNo);
        List<Map> map = DAOUtil.findAll(sb.toString(), param);
        
        return map.size();
    }

    /**
     * 查询数据库中是否存在手机号（同一个经销商 、在职员工）
     * 
     * @author jcsi
     * @date 2016年8月1日
     * @param employeeNo
     * @return
     * @throws ServiceBizException
     */
    @SuppressWarnings("rawtypes")
	public int SearchByMobile(String Mobile, String id) throws ServiceBizException {
        List<Object> param = new ArrayList<Object>();
        StringBuilder sb = new StringBuilder("SELECT t.EMPLOYEE_ID,t.DEALER_CODE from tm_employee t where t.MOBILE=? and t.DEALER_CODE=? and IS_VALID=? ");
        param.add(Mobile);
        param.add(FrameworkUtil.getLoginInfo().getDealerCode());
        param.add(DictCodeConstants.STATUS_IS_YES);
        // 修改
        if (!StringUtils.isNullOrEmpty(id)) {
            sb.append(" and EMPLOYEE_NO !=? ");
            param.add(id);
        }
        List<Map> map = DAOUtil.findAll(sb.toString(), param);
        return map.size();
    }
    /**
     * 查询数据库中是否存在手机号（同一个经销商 、在职员工）
     * 
     * @author jcsi
     * @date 2016年8月1日
     * @param employeeNo
     * @return
     * @throws ServiceBizException
     */
    @SuppressWarnings("rawtypes")
	public int editSearchByMobile(String Mobile,String id) throws ServiceBizException {
        List<Object> param = new ArrayList<Object>();
        StringBuilder sb = new StringBuilder("SELECT t.EMPLOYEE_ID,t.DEALER_CODE from tm_employee t where t.MOBILE=? and t.DEALER_CODE=? and IS_VALID=? ");
        param.add(Mobile);
        param.add(FrameworkUtil.getLoginInfo().getDealerCode());
        param.add(DictCodeConstants.STATUS_IS_YES);
        // 修改
        if (!StringUtils.isNullOrEmpty(id)) {
            sb.append(" and EMPLOYEE_NO !=? ");
            param.add(id);
        }
        List<Map> map = DAOUtil.findAll(sb.toString(), param);
        return map.size();
    }

    /**
     * 查询身份证号码是否重复
     * 
     * @author jcsi
     * @date 2016年10月17日
     * @param Mobile
     * @param id
     * @return
     * @throws ServiceBizException
     */
    @SuppressWarnings("rawtypes")
	public int SearchByCertificateId(String CertificateId, String id) throws ServiceBizException {
        List<Object> param = new ArrayList<Object>();
        StringBuilder sb = new StringBuilder("SELECT t.EMPLOYEE_ID,t.CERTIFICATE_ID,t.DEALER_CODE from tm_employee t where t.CERTIFICATE_ID=? and t.DEALER_CODE=? ");
        param.add(CertificateId);
        param.add(FrameworkUtil.getLoginInfo().getDealerCode());
        // 修改
        if (!StringUtils.isNullOrEmpty(id)) {
            sb.append(" and EMPLOYEE_NO !=? ");
            param.add(id);
        }
        List<Map> map = DAOUtil.findAll(sb.toString(), param);
        
        return  map.size();
    }
    /**
     * 查询身份证号码是否重复
     * 
     * @author jcsi
     * @date 2016年10月17日
     * @param Mobile
     * @param id
     * @return
     * @throws ServiceBizException
     */
    @SuppressWarnings("rawtypes")
	public int QueryEmployeeNo(String id) throws ServiceBizException {
        List<Object> param = new ArrayList<Object>();
        StringBuilder sb = new StringBuilder("SELECT t.USER_ID,t.USER_NAME,t.DEALER_CODE,t.USER_CODE,t.EMPLOYEE_NO from TM_USER t where t.EMPLOYEE_NO=? and t.DEALER_CODE=? AND USER_CODE IS NOT NULL");
        param.add(id);
        param.add(FrameworkUtil.getLoginInfo().getDealerCode());
       
        List<Map> map = DAOUtil.findAll(sb.toString(), param);
        
        return  map.size();
    }

    /**
     * 根据不同的查询语句查询
     * 
     * @author jcsi
     * @date 2016年7月11日
     * @param str
     * @return
     */
    @SuppressWarnings("rawtypes")
	public List<Map> FindAll(String str) throws ServiceBizException {
        List<Map> map = DAOUtil.findAll(str.toString(), null);
        return map;
    }

    /**
     * 根据id查找员工信息
     * 
     * @author jcsi
     * @date 2016年7月29日
     * @param id
     * @return (non-Javadoc)
     * @see com.yonyou.dms.manage.service.basedata.employee.EmployeeService#findById(java.lang.Long)
     */

    @SuppressWarnings("rawtypes")
	@Override
    public Map findById(String id) throws ServiceBizException {
        StringBuilder sb = new StringBuilder();
        sb.append(" select tme.*,tMo.ORG_NAME,tpo.POSITION_NAME\n");
    
        sb.append(" FROM tm_employee tme ");
        sb.append(" LEFT JOIN tm_organization tMo ON tMo.ORG_CODE=tme.ORG_CODE AND tMo.DEALER_CODE=tme.DEALER_CODE");
        sb.append(" LEFT JOIN tm_position tpo ON tpo.POSITION_CODE=tme.POSITION_CODE AND tpo.DEALER_CODE=tme.DEALER_CODE");
        sb.append(" WHERE tme.EMPLOYEE_NO=?");
        List<Object> param = new ArrayList<Object>();
        param.add(id);
        return DAOUtil.findFirst(sb.toString(), param);
    }

    /**
     * 根据id更新员工信息
     * 
     * @author jcsi
     * @date 2016年7月29日
     * @param id
     * @param empDto (non-Javadoc)
     * @see com.yonyou.dms.manage.service.basedata.employee.EmployeeService#updateEmpById(java.lang.Long,
     * com.yonyou.dms.manage.domains.DTO.basedata.EmployeeDto)
     */

    @Override
    public void updateEmpById(String id, EmployeeDto empDto) throws ServiceBizException {
        EmployeePo empo = EmployeePo.findById(id);
        int mobileIsTrue = SearchByMobile(empDto.getMobile(), id);
        int certificatedIdIsTrue = SearchByCertificateId(empDto.getCertificateId(), id);
        if (certificatedIdIsTrue >0) {
            throw new ServiceBizException("该身份证号已经存在");
        }
        if (mobileIsTrue==0) {
            setEmployee(empo, empDto);
            // 保存员工信息
            empo.saveIt();
            // 删除之前保存的员工全部角色信息
            List<String> roles = empDto.getEmployeeRoles();
            EmployeeRolePo.delete("EMPLOYEE_ID=?", id);
            EmployeeRolePo erPo = null;
            // 保存修改之后的员工角色信息
            if (roles.size() > 0) {
                for (int i = 0; i < roles.size(); i++) {
                    erPo = new EmployeeRolePo();
                    erPo.setString("EMPLOYEE_ID", id);
                    erPo.setString("ROLE", roles.get(i));
                    erPo.saveIt();
                }
            }
        } else {
            throw new ServiceBizException("该手机号已存在");
        }

    }

    /**
     * 根据id删除员工信息
     * 
     * @author jcsi
     * @date 2016年7月29日
     * @param id (non-Javadoc)
     * @see com.yonyou.dms.manage.service.basedata.employee.EmployeeService#deleteById(java.lang.Long)
     */

    @Override
    public void deleteById(String id) throws ServiceBizException {
        EmployeePo empo = EmployeePo.findById(id);
        empo.deleteCascadeShallow();
    }

    /**
     * 给EmployeePo属性赋值
     * 
     * @author jcsi
     * @date 2016年7月29日
     * @param emp
     * @param empDto
     */

    public void setEmployee(EmployeePo emp, EmployeeDto empDto) throws ServiceBizException {
        emp.setString("EMPLOYEE_NAME", empDto.getEmployeeName());
        emp.setString("ORG_CODE", empDto.getOrgCode());
        emp.setInteger("ORGANIZATION_ID", empDto.getOrganizationId());
        emp.setString("POSITION_CODE", empDto.getPositionCode());
        emp.setLong("GENDER", empDto.getGender());
        emp.setString("CERTIFICATE_ID", empDto.getCertificateId());
        emp.setString("PHONE", empDto.getPhone());
        emp.setString("MOBILE", empDto.getMobile());
        emp.setString("E_MAIL", empDto.geteMail());
        emp.setDate("BIRTHDAY", empDto.getBirthday());
        emp.setString("ADDRESS", empDto.getAddress());
        emp.setString("ZIP_CODE", empDto.getZipCode());
        emp.setString("WORKER_TYPE_CODE", empDto.getWorkerTypeCode());
        emp.setLong("TECHNICIAN_GRADE", empDto.getTechnicianGrade());
        emp.setString("DEFAULT_POSITION", empDto.getDefaultPosition());
        emp.setLong("IS_ONJOB", empDto.getIsOnjob());
        emp.setString("WORKGROUP_CODE", empDto.getWorkgroupCode());
        // 建档时间 ，如果不填 系统默认为当前时间
        if (empDto.getFoundDate() != null) {
            emp.setDate("FOUND_DATE", empDto.getFoundDate());
        } else {
            emp.setDate("FOUND_DATE", new Date());
        }

        // 如果在职状态为“离职”并且离职日期为空，则默认离职时间为当前时间
        if ((DictCodeConstants.EMPLOYEE_NOJOB + "").equals(empDto.getIsOnjob() + "")
            && empDto.getDimissionDate() == null) {
            emp.setDate("DIMISSION_DATE", new Date());
        } else {
            emp.setDate("DIMISSION_DATE", empDto.getDimissionDate());
        }

    }

    /**
     * @author zhanshiwei
     * @date 2017年1月3日
     * @param emp
     * @param empDto
     * @throws ServiceBizException
     */

    public void setGFkEmployee(EmployeePo emp, EmployeeDto empDto) throws ServiceBizException {
    	System.err.println("      ******      "+empDto.getEmployeeNo());
        emp.setString("EMPLOYEE_NO", empDto.getEmployeeNo());
        emp.setString("EMPLOYEE_NAME", empDto.getEmployeeName());
        emp.setString("POSITION_CODE", empDto.getPositionCode());
        emp.setLong("GENDER", empDto.getGender());
        emp.setString("CERTIFICATE_ID", empDto.getCertificateId());
        emp.setString("PHONE", empDto.getPhone());
        emp.setString("ORG_CODE", empDto.getOrgCode());
        emp.setString("MOBILE", empDto.getMobile());
        emp.setString("E_MAIL", empDto.geteMail());
        emp.setDate("BIRTHDAY", empDto.getBirthday());
        emp.setString("ADDRESS", empDto.getAddress());
        emp.setString("ZIP_CODE", empDto.getZipCode());
        emp.setString("WORKER_TYPE_CODE", empDto.getWorkerTypeCode());
        emp.setLong("TECHNICIAN_GRADE", empDto.getTechnicianGrade());
        emp.setString("DEFAULT_POSITION", empDto.getDefaultPosition());
        emp.setString("WORKGROUP_CODE", empDto.getWorkgroupCode());
        emp.setDouble("LABOUR_FACTOR", empDto.getLabourFactor());
        emp.setInteger("IS_TECHNICIAN", empDto.getIsTechnician());
        emp.setInteger("TECHNICIAN_GRADE", empDto.getTechnicianGrade());
        emp.setString("LABOUR_POSITION_CODE", empDto.getLabourPositionCode());
        emp.setInteger("IS_TRACER", empDto.getIsTracer());
        emp.setInteger("IS_TEST_DRIVER", empDto.getIsTestDriver());
        emp.setString("SEC_LABOUR_POSITION_CODE", empDto.getSecLabourPositionCode());
        emp.setInteger("IS_TAKE_PART", empDto.getIsTakePart());
        emp.setInteger("IS_INSURATION_ADVISOR", empDto.getIsInsurationAdvisor());
        emp.setInteger("IS_UPHOLSTER_ADVISOR", empDto.getIsUpholsterAdvisor());
        emp.setDate("ENTRY_DATE", empDto.getEntryDate());
        emp.setInteger("IS_DCRC_ADVISOR", empDto.getIsDcrcAdvisor());
        emp.setInteger("IS_INSURANCE_ADVISOR", empDto.getIsInsuranceAdvisor());
        emp.setInteger("IS_MAINTAIN_ADVISOR", empDto.getIsMaintainAdvisor());
        emp.setString("ADDRESS", empDto.getAddress());
        emp.setString("DISPATCH_CODE", empDto.getDispatchCode());
        emp.setInteger("IS_CHECKER", empDto.getIsChecker());
        emp.setInteger("IS_MAJOR_REPAIRER", empDto.getIsMajorRepairer());
        emp.setInteger("IS_DISPATCHER", empDto.getIsDispatcher());
        emp.setString("ORDER_CODE", empDto.getOrderCode());
        emp.setString("TECH_LEVEL_CODE", empDto.getTechLevelCode());
        emp.setInteger("IS_DEFAULT_MANAGER", empDto.getIsDefaultManager());
        emp.setInteger("IS_SERVICE_ADVISOR", empDto.getIsServiceAdvisor());
        emp.setString("RESUME", empDto.getResume());
        // 建档时间 ，如果不填 系统默认为当前时间
        if (empDto.getFoundDate() != null) {
            emp.setDate("FOUND_DATE", empDto.getFoundDate());
        } else {
            emp.setDate("FOUND_DATE", new Date());
        }

        // 如果在职状态为“离职”并且离职日期为空，则默认离职时间为当前时间
        if ((DictCodeConstants.EMPLOYEE_NOJOB + "").equals(empDto.getIsOnjob() + "")
            && empDto.getDimissionDate() == null) {
            emp.setDate("DIMISSION_DATE", new Date());
        } else {
            emp.setDate("DIMISSION_DATE", empDto.getDimissionDate());
        }
    }

    /**
     * 根据员工id查找员工角色信息
     * 
     * @author jcsi
     * @date 2016年7月29日
     * @param id
     * @return (non-Javadoc)
     * @see com.yonyou.dms.manage.service.basedata.employee.EmployeeService#findEmpRolesByEmpId(java.lang.Long)
     */

    @SuppressWarnings("rawtypes")
	@Override
    public List<Map> findEmpRolesByEmpId(Long id) throws ServiceBizException {
        StringBuilder sb = new StringBuilder("select r.DEALER_CODE,r.ROLE from tm_employee_role r where r.EMPLOYEE_ID=?");
        List<String> param = new ArrayList<String>();
        param.add(Long.toString(id));
        return DAOUtil.findAll(sb.toString(), param);
    }

    /**
     * 员工信息下拉框调用的方法
     * 
     * @author yll
     * @date 2016年7月20日
     * @param queryParam
     * @return
     * @throws ServiceBizException (non-Javadoc)
     * @see com.yonyou.dms.manage.service.basedata.employee.EmployeeService#selectEmployees(java.util.Map)
     */
    @SuppressWarnings("rawtypes")
	@Override
    public List<Map> selectEmployees(Map<String, String> queryParam) throws ServiceBizException {
        StringBuilder sqlSb = new StringBuilder("select em.EMPLOYEE_NO,em.EMPLOYEE_NAME,em.DEALER_CODE, org.ORG_CODE,  org.ORG_NAME   from tm_employee em inner JOIN tm_organization org on em.ORGANIZATION_ID=org.ORGDEPT_ID and   em.DEALER_CODE=org.DEALER_CODE  where 1=1 ");
        //sqlSb.append(" and em.IS_ONJOB=" + DictCodeConstants.EMPLOYEE_ISJOB);
        List<String> params = new ArrayList<String>();
        if (!StringUtils.isNullOrEmpty(queryParam.get("ROLE"))) {
            sqlSb.append(" and   EXISTS ( select 1 from TM_EMPLOYEE_ROLE er where ROLE in (?) and em.EMPLOYEE_ID=er.EMPLOYEE_ID)");
            params.add(queryParam.get("ROLE"));
        }
        if (!StringUtils.isNullOrEmpty(queryParam.get("orgCode"))) {
            sqlSb.append(" and em.ORGANIZATION_ID=?");
            params.add(queryParam.get("orgCode"));
        }

        return DAOUtil.findAll(sqlSb.toString(), params);
    }
    /**
     * 关单人
     * 
     * @author yll
     * @date 2016年7月20日
     * @param queryParam
     * @return
     * @throws ServiceBizException (non-Javadoc)
     * @see com.yonyou.dms.manage.service.basedata.employee.EmployeeService#selectEmployees(java.util.Map)
     */
    @SuppressWarnings("rawtypes")
	@Override
    public List<Map> selectShutEmployees(Map<String, String> queryParam) throws ServiceBizException {
        StringBuilder sqlSb = new StringBuilder("SELECT A.*,B.ORG_NAME AS DEPT_NAME from TM_EMPLOYEE  A left join tm_organization b on  a.dealer_CODE = b.dealer_CODE AND a.ORG_CODE = b.ORG_CODE WHERE A.IS_VALID = "+DictCodeConstants.DICT_IS_YES+" AND A.dealer_CODE = '" + FrameworkUtil.getLoginInfo().getDealerCode() + "'" );
        sqlSb.append(" and (( DOWN_TAG = " +DictCodeConstants.DICT_IS_YES+" and FROM_ENTITY <>'"+FrameworkUtil.getLoginInfo().getDealerCode()+"') or ( DOWN_TAG = " +DictCodeConstants.DICT_IS_NO+"  and A.FROM_ENTITY IS NULL))");
        List<String> params = new ArrayList<String>();
       

        return DAOUtil.findAll(sqlSb.toString(), params);
    }

    /**
     * 查询当前用户信息
     * 
     * @author yll
     * @date 2016年8月30日
     * @param param
     * @return group_concat(tc.CODE_CN_DESC) 暂不使用，后续(张宪超)
     * @throws ServiceBizException (non-Javadoc)
     * @see com.yonyou.dms.manage.service.basedata.employee.EmployeeService#searchUserEmployees(java.util.Map)
     */
    @SuppressWarnings({ "unchecked", "rawtypes" })
    public PageInfoDto searchUserEmployees(@RequestParam Map<String, String> param) throws ServiceBizException {
        StringBuilder sb = new StringBuilder("SELECT te.EMPLOYEE_ID AS EMPLOYEE_ID,'' as EMP_ROLE,te.DEALER_CODE,te.EMPLOYEE_NO,te.EMPLOYEE_NAME,tu.USER_CODE,tu.USER_STATUS,tu.PASSWORD,te.ORGANIZATION_ID,te.POSITION_CODE, ");
        sb.append("te.GENDER,te.CERTIFICATE_ID,te.PHONE,te.MOBILE,te.E_MAIL,te.BIRTHDAY,te.ADDRESS,te.ZIP_CODE,te.WORKER_TYPE_CODE,te.TECHNICIAN_GRADE,te.DEFAULT_POSITION,te.IS_ONJOB,");
        sb.append("te.FOUND_DATE,te.DIMISSION_DATE from tm_employee te LEFT JOIN tm_user tu ON tu.EMPLOYEE_ID=te.EMPLOYEE_ID where 1=1");
        List<Object> queryParam = new ArrayList<Object>();

        if (!StringUtils.isNullOrEmpty(param.get("employeeNo"))) {
            sb.append(" and te.EMPLOYEE_NO like ?");
            queryParam.add("%" + param.get("employeeNo") + "%");
        }
        if (!StringUtils.isNullOrEmpty(param.get("userCode"))) {
            sb.append(" and tu.USER_CODE like ?");
            queryParam.add("%" + param.get("userCode") + "%");
        }
        if (!StringUtils.isNullOrEmpty(param.get("employeeName"))) {
            sb.append(" and te.EMPLOYEE_NAME like ?");
            queryParam.add("%" + param.get("employeeName") + "%");
        }

        if (!StringUtils.isNullOrEmpty(param.get("workingState"))) {
            sb.append(" and te.IS_ONJOB = ?");
            queryParam.add(Integer.parseInt(param.get("workingState")));
        }
        if (!StringUtils.isNullOrEmpty(param.get("userState"))) {
            sb.append(" and tu.USER_STATUS = ?");
            queryParam.add(Integer.parseInt(param.get("userState")));
        }
        // 执行查询
        PageInfoDto pageResult = DAOUtil.pageQuery(sb.toString(), queryParam);

        // 设置员工角色
        List<Map> resultList = pageResult.getRows();
        // 设置用户角色查询的SQL
        final StringBuilder sb2 = new StringBuilder();
        sb2.append("select t2.CODE_CN_DESC CODE_DESC_ZH,t2.CODE_EN_DESC CODE_DESC_EN from tm_employee_role t1,tc_code t2 where t1.ROLE = t2.CODE_ID and t1.EMPLOYEE_ID = ? ");
        if (!CommonUtils.isNullOrEmpty(resultList)) {
            for (Map row : resultList) {
                // 设置用户的员色列表
                List<Object> params = new ArrayList<Object>();
                params.add(row.get("EMPLOYEE_ID"));
                List<Map> roleList = DAOUtil.findAll(sb2.toString(), params, false);
                if (!CommonUtils.isNullOrEmpty(roleList)) {
                    StringBuilder sb3 = new StringBuilder();
                    for (Map roleMap : roleList) {
                        sb3.append(DAOUtil.getLocaleFieldValue(roleMap, "CODE_DESC")).append(",");
                    }
                    row.put("EMP_ROLE", sb3.substring(0, sb3.length() - 1));
                }
            }
        }
        return pageResult;
    }

    /**
     * 根据EmployeeId查找员工对应的账号信息
     * 
     * @author yll
     * @date 2016年8月22日
     * @param id
     * @return
     * @throws ServiceBizException (non-Javadoc)
     * @see com.yonyou.dms.manage.service.basedata.employee.EmployeeService#findbyEmployeeId(java.lang.Long)
     */
    @SuppressWarnings("rawtypes")
	@Override
    public Map findUserByEmployeeId(String id) throws ServiceBizException {
        StringBuilder sb = new StringBuilder("select tu.DEALER_CODE,tu.USER_ID,tu.EMPLOYEE_NO,tu.ORG_CODE,tu.USER_CODE,tu.PASSWORD,tu.USER_STATUS,tu.IS_SERVICE_ADVISOR,tu.IS_CONSULTANT,tu.LOGIN_LAST_TIME,tu.SERIAL_NO,tu.SERIAL_TAG,tu.USE_INFOHERE,tu.USER_NAME,tu.DDCN_UPDATE_DATE,tu.CREATED_BY,tu.CREATED_AT,tu.UPDATED_BY,tu.UPDATED_AT,tMo.ORG_NAME from tm_user tu\n");
        		sb.append(" LEFT JOIN tm_organization tMo ON tMo.ORG_CODE=tu.ORG_CODE");
        		//sb.append(" LEFT JOIN tm_employee teo ON teo.EMPLOYEE_NO=tu.EMPLOYEE_NO and teo.DEALER_CODE=tu.DEALER_CODE");
        		sb.append(" where 1=1 ");
        List<Object> queryParam = new ArrayList<Object>();
        if (!StringUtils.isNullOrEmpty(id)) {
            sb.append(" and tu.EMPLOYEE_NO= ?");
            queryParam.add(id);
        }
        List<Map> list = DAOUtil.findAll(sb.toString(), queryParam);
        Map map = null;
        if (list.size() > 0) {
            map = list.get(0);
        }
        return map;
    }

    /**
     * 整车销售订单-销售顾问下拉数据
     * 
     * @author xukl
     * @date 2016年9月7日
     * @param orgCode
     * @return
     * @throws ServiceBizException (non-Javadoc)
     * @see com.yonyou.dms.manage.service.basedata.employee.EmployeeService#qrySalesConsultant(java.lang.String)
     */

    @SuppressWarnings("rawtypes")
	@Override
    public List<Map> qrySalesConsultant(String orgCode) throws ServiceBizException {
        StringBuilder sqlSb = new StringBuilder("select t.USER_ID,t.USER_NAME,t.DEALER_CODE from TM_USER  t ");
        List<Object> params = new ArrayList<Object>();
       /* params.add(DictCodeConstants.SALES_CONSULTANT);*/
        if (!orgCode.equals("-1")) {
            sqlSb.append(" and t.ORGANIZATION_ID = ?");
            params.add(orgCode);
        }
        List<Map> list = DAOUtil.findAll(sqlSb.toString(), params);
        return list;
    }
    
    /**
     * 整车销售订单-销售顾问下拉数据
     * 
     * @author xukl
     * @date 2016年9月7日
     * @param orgCode
     * @return
     * @throws ServiceBizException (non-Javadoc)
     * @see com.yonyou.dms.manage.service.basedata.employee.EmployeeService#qrySalesConsultant(java.lang.String)
     */

    @SuppressWarnings("rawtypes")
	@Override
    public List<Map> qrySalesInvoice(String orgCode) throws ServiceBizException {
        StringBuilder sqlSb = new StringBuilder("select t.INVOICE_TYPE_CODE,t.INVOICE_TYPE_NAME,t.DEALER_CODE from TM_INVOICE_TYPE  t ");
        List<Object> params = new ArrayList<Object>();
       /* params.add(DictCodeConstants.SALES_CONSULTANT);*/
        List<Map> list = DAOUtil.findAll(sqlSb.toString(), params);
        return list;
    }

    /**
     * 收到数据权限范围管控的 整车销售订单-销售顾问下拉数据
     * 
     * @author Administrator
     * @date 2016年12月8日
     * @param orgCode
     * @return
     * @throws ServiceBizException (non-Javadoc)
     * @see com.yonyou.dms.manage.service.basedata.employee.EmployeeService#qrySalesConsultant(java.lang.String)
     */
    @SuppressWarnings("rawtypes")
	@Override
    public List<Map> qryPowerSalesConsultant(String menuId) throws ServiceBizException {
        StringBuilder sqlSb = new StringBuilder("select t.EMPLOYEE_NO,t.ORGANIZATION_ID,t.EMPLOYEE_NAME,t.DEALER_CODE,t.ORGANIZATION_ID from TM_EMPLOYEE  t LEFT JOIN TM_EMPLOYEE_ROLE tt on t.EMPLOYEE_ID = tt.EMPLOYEE_ID where 1=1 and tt.ROLE = ?");
        List<Object> params = new ArrayList<Object>();
        params.add(DictCodeConstants.SALES_CONSULTANT);
        List<Map> list = DAOUtil.findAll(sqlSb.toString(), params, menuId + "");
        return list;
    }

    /**
     * 查询销售经理和财务经理
     * 
     * @author xukl
     * @date 2016年9月22日
     * @param basiDtolist
     * @return
     * @throws ServiceBizException
     */

    @Override
    public PageInfoDto qryAudit(List<BasicParametersDTO> basiDtolist) throws ServiceBizException {
        String saleaudit = "";// 是否需要经理审核
        for (BasicParametersDTO basicParametersDTO : basiDtolist) {
            if (basicParametersDTO.getParamCode().equals("vehicle_sale_audit")) {
                saleaudit = basicParametersDTO.getParamValue();
            }
        }
        StringBuilder sqlSb = new StringBuilder("select t.EMPLOYEE_ID,t.EMPLOYEE_NO,t.EMPLOYEE_NAME,t.DEALER_CODE,tt.ROLE from TM_EMPLOYEE  t LEFT JOIN TM_EMPLOYEE_ROLE tt on t.EMPLOYEE_ID = tt.EMPLOYEE_ID where tt.ROLE = ?");
        List<Object> params = new ArrayList<Object>();
        if (!StringUtils.isNullOrEmpty(saleaudit)) {
            params.add(DictCodeConstants.SALES_AUDIT);
        } else {
            params.add(DictCodeConstants.FINANCE_AUDIT);
        }
        PageInfoDto list = DAOUtil.pageQuery(sqlSb.toString(), params);
        return list;
    }

    /**
     * 查询财务经理
     * 
     * @author xukl
     * @date 2016年9月28日
     * @return
     * @throws ServiceBizException (non-Javadoc)
     * @see com.yonyou.dms.manage.service.basedata.employee.EmployeeService#qryFinanceAudit()
     */

    @SuppressWarnings("rawtypes")
	@Override
    public List<Map> qryFinanceAudit() throws ServiceBizException {
        StringBuilder sqlSb = new StringBuilder("select t.EMPLOYEE_ID,t.EMPLOYEE_NO,t.EMPLOYEE_NAME,t.DEALER_CODE,tc.CODE_CN_DESC as ROLE from TM_EMPLOYEE  t LEFT JOIN TM_EMPLOYEE_ROLE tt on t.EMPLOYEE_ID = tt.EMPLOYEE_ID LEFT JOIN tc_code tc on tc.CODE_ID=tt.ROLE where tt.ROLE = ?");
        List<Object> params = new ArrayList<Object>();
        params.add(DictCodeConstants.FINANCE_AUDIT);
        List<Map> list = DAOUtil.findAll(sqlSb.toString(), params);
        return list;
    }

    /**
     * 查询技师下拉框
     * 
     * @author 
     * @date 2017年5月27日
     * @return
     * @throws ServiceBizException (non-Javadoc)
     * @see com.yonyou.dms.manage.service.basedata.employee.EmployeeService#queryTechnician()
     */
    @SuppressWarnings("rawtypes")
	@Override
    public List<Map> queryTechnician() throws ServiceBizException {
        StringBuilder sql = new StringBuilder("");
        sql.append("  SELECT t.EMPLOYEE_ID,t.EMPLOYEE_NO,t.EMPLOYEE_NAME,t.DEALER_CODE,t.IS_TECHNICIAN,tu.USER_CODE,tu.USER_ID,tu.USER_NAME FROM   TM_USER  tu ");
        sql.append("  LEFT JOIN TM_EMPLOYEE t  on tu.EMPLOYEE_NO= t.EMPLOYEE_NO and tu.DEALER_CODE=t.DEALER_CODE  ");
        sql.append("  WHERE t.IS_TECHNICIAN = ?  ");List<Object> params = new ArrayList<Object>();
        params.add(DictCodeConstants.DICT_IS_YES);
        return DAOUtil.findAll(sql.toString(), params);

    }

    /**
     * 查询检验人表格下拉框
     * 
     * @author rongzoujie
     * @date 2016年10月11日
     * @return
     * @throws ServiceBizException
     */
    @SuppressWarnings("rawtypes")
	public List<Map> queryFinisher() throws ServiceBizException {
        StringBuilder sqlSb = new StringBuilder("SELECT t1.DEALER_CODE,t1.employee_name,t1.employee_no FROM tm_employee t1 left JOIN tm_employee_role t2 ON (t2.employee_id = t1.employee_id )");
        sqlSb.append(" where t2.role = 10061006 and t1.is_onjob = 10081001");
        List<Object> params = new ArrayList<Object>();
        return DAOUtil.findAll(sqlSb.toString(), params);
    }

    /**
     * 客户经理
     * 
     * @author rongzoujie
     * @date 2016年9月27日
     * @return
     * @throws ServiceBizException (non-Javadoc)
     * @see com.yonyou.dms.manage.service.basedata.employee.EmployeeService#queryServiceAss()
     */
    @SuppressWarnings("rawtypes")
	@Override
    public List<Map> queryServiceAss() throws ServiceBizException {
        StringBuilder sql = new StringBuilder("");
        sql.append("  SELECT t.EMPLOYEE_ID,t.EMPLOYEE_NO,t.EMPLOYEE_NAME,t.DEALER_CODE,t.IS_TECHNICIAN,tu.USER_CODE,tu.USER_ID,tu.USER_NAME FROM   TM_USER  tu ");
        sql.append("  LEFT JOIN TM_EMPLOYEE t  on tu.EMPLOYEE_NO= t.EMPLOYEE_NO and tu.DEALER_CODE=t.DEALER_CODE  ");
        sql.append("  WHERE t.IS_SERVICE_ADVISOR = ?  ");List<Object> params = new ArrayList<Object>();
        params.add(DictCodeConstants.DICT_IS_YES);
        return DAOUtil.findAll(sql.toString(), params);
    }

    /**
     * 只修改员工角色
     * 
     * @author yll
     * @date 2016年10月27日
     * @param id
     * @param employeeRoles
     * @throws ServiceBizException (non-Javadoc)
     * @see com.yonyou.dms.manage.service.basedata.employee.EmployeeService#updateEmpById(java.lang.Long,
     * java.util.List)
     */
    @Override
    public void updateEmpById(Long id, List<String> employeeRoles) throws ServiceBizException {
        EmployeePo empo = EmployeePo.findById(id);
        // 保存员工信息
        empo.saveIt();
        // 删除之前保存的员工全部角色信息
        List<String> roles = employeeRoles;
        EmployeeRolePo.delete("EMPLOYEE_ID=?", id);
        EmployeeRolePo erPo = null;
        // 保存修改之后的员工角色信息
        if (roles.size() > 0) {
            for (int i = 0; i < roles.size(); i++) {
                erPo = new EmployeeRolePo();
                erPo.setString("EMPLOYEE_ID", id);
                erPo.setString("ROLE", roles.get(i));
                erPo.saveIt();
            }
        }
    }

    /**
     * 职位过滤员工
     * 
     * @author zhanshiwei
     * @date 2016年11月1日
     * @param role
     * @return
     * @throws ServiceBizException (non-Javadoc)
     * @see com.yonyou.dms.manage.service.basedata.employee.EmployeeService#selectEmployeeByrole(java.lang.Integer)
     */
    @SuppressWarnings("rawtypes")
	@Override
    public List<Map> selectEmployeeByrole(Integer role) throws ServiceBizException {
        StringBuilder sqlSb = new StringBuilder("select em.EMPLOYEE_NO,em.EMPLOYEE_NAME,em.DEALER_CODE,em.RECORD_VERSION,   org.ORG_NAME   from tm_employee em inner JOINtm_organization org on em.ORGANIZATION_ID=org.ORGDEPT_ID and   em.DEALER_CODE=org.DEALER_CODE  where 1=1 ");
        sqlSb.append(" and em.IS_ONJOB=" + DictCodeConstants.EMPLOYEE_ISJOB);
        List<Object> params = new ArrayList<Object>();
        if (!StringUtils.isNullOrEmpty(role)) {
            sqlSb.append(" and   EXISTS ( select 1 from TM_EMPLOYEE_ROLE er where ROLE in (?) and em.EMPLOYEE_ID=er.EMPLOYEE_ID)");
            params.add(role);
        }
        return DAOUtil.findAll(sqlSb.toString(), params);
    }

    /**
     * 根据数据权限范围控制职位过滤员工
     * 
     * @author Administrator
     * @date 2016年12月11日
     * @param role
     * @return
     * @throws ServiceBizException (non-Javadoc)
     * @see com.yonyou.dms.manage.service.basedata.employee.EmployeeService#selectPowerEmployeeByrole(java.lang.Integer)
     */
    @SuppressWarnings("rawtypes")
	@Override
    public List<Map> selectPowerEmployeeByrole(Integer role, String menuId) throws ServiceBizException {
        StringBuilder sqlSb = new StringBuilder("select em.EMPLOYEE_NO,em.ORGANIZATION_ID,em.EMPLOYEE_NAME,em.DEALER_CODE,em.RECORD_VERSION,   org.ORG_NAME   from tm_employee em inner JOINtm_organization org on em.ORGANIZATION_ID=org.ORGDEPT_ID and   em.DEALER_CODE=org.DEALER_CODE  where 1=1 ");
        sqlSb.append(" and em.IS_ONJOB=" + DictCodeConstants.EMPLOYEE_ISJOB);
        List<Object> params = new ArrayList<Object>();
        if (!StringUtils.isNullOrEmpty(role)) {
            sqlSb.append(" and   EXISTS ( select 1 from TM_EMPLOYEE_ROLE er where ROLE in (?) and em.EMPLOYEE_ID=er.EMPLOYEE_ID)");
            params.add(role);
        }
        return DAOUtil.findAll(sqlSb.toString(), params, menuId + "");
    }

    @SuppressWarnings("rawtypes")
	@Override
    public List<Map> queryTechLevel() throws ServiceBizException {
        List<Object> params = new ArrayList<Object>();
        String sql = " SELECT DEALER_CODE,TECH_LEVEL_CODE,TECH_LEVEL_NAME,TECH_LEVEL_DESC,TECH_LEVEL_RATE FROM TM_TECH_LEVEL WHERE 1=1";
        return DAOUtil.findAll(sql, params);
    }

    @SuppressWarnings("rawtypes")
	@Override
    public List<Map> queryDispatchInfo() throws ServiceBizException {
        List<Object> params = new ArrayList<Object>();
        String sql = " SELECT DEALER_CODE,DISPATCH_CODE,DISPATCH_NAME,SUP_DISPATCH_CODE,IS_BP FROM TM_DISPATCH WHERE 1=1";
        return DAOUtil.findAll(sql, params);
    }

    @SuppressWarnings("rawtypes")
	@Override
    public List<Map> queryWorkOrder() throws ServiceBizException {
        List<Object> params = new ArrayList<Object>();
        String sql = " SELECT DEALER_CODE,ORDER_CODE,ORDER_NAME FROM TM_WORK_ORDER WHERE 1=1";
        return DAOUtil.findAll(sql, params);
    }

    @SuppressWarnings("rawtypes")
	@Override
    public List<Map> queryEmptraInfo(String employeeNo) throws ServiceBizException {
        List<Object> params = new ArrayList<Object>();
        StringBuilder sqlSb = new StringBuilder("SELECT DEALER_CODE,ITEM_ID,EMPLOYEE_NO,TRAINING_TIME,TRAINING_DEPARTMENT,TRAINING_COURSE,TRAINING_LEVEL,TRAINING_RESULT,TRAINING_TYPE,REMARK from TM_EMPLOYEE_TRAINING where EMPLOYEE_NO=?");
        params.add(employeeNo);
        return DAOUtil.findAll(sqlSb.toString(), params);
    }

    /**
     * 离职
     * @author zhanshiwei
     * @date 2017年1月4日
     * @param empDto
     * @return
     * @throws ServiceBizException
     */

    @SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
    public Long updateEmpIsValid(String employeeNo) throws ServiceBizException {
        
        EmployeePo empo = EmployeePo.findFirst("DEALER_CODE=? and EMPLOYEE_NO=?", FrameworkUtil.getLoginInfo().getDealerCode(),employeeNo);
     
        this.queryVehicle(empo.getString("EMPLOYEE_NO"), empo.getInteger("IS_DEFAULT_MANAGER"));
        empo.setInteger("IS_VALID", DictCodeConstants.STATUS_IS_NOT);
        empo.setDate("ENTRY_DATE", null);
        empo.setDate("DIMISSION_DATE", new Date());

        // 更新经销商基本信息会用到同时调用上报接口
        String generalManagerMobile = ""; // 总经理手机
        String salesManagerMobile = ""; // 销售经理手机
        String serviceManagerMobile = ""; // 服务经理手机
        String getMobilesql = "select DEALER_CODE,MOBILE from TM_EMPLOYEE where IS_VALID=? and POSITION_CODE=?  ";
        Map<String, Object> getMobile = new HashMap<String, Object>();

        if (empo.getString("POSITION_CODE") != null) {
            String positionCode = empo.getString("POSITION_CODE");
            if (StringUtils.isEquals("E002", positionCode) || StringUtils.isEquals("M009", positionCode)
                || StringUtils.isEquals("M011", positionCode)) {
                // 若该员工的手机信息不为空，获得该手机信息
                if (!StringUtils.isNullOrEmpty(empo.getString("MOBILE"))) {
                    String mobile = empo.getString("MOBILE");
                    // 获得该员工的手机信息后，比对该员工手机是不是和经销商基本信息上面和该员工职务相对的手机相同，若相同替换为别他没有可替换则清空
                    String sql = "select DEALER_CODE,SALES_MANAGER_MOBILE,GENERAL_MANAGER_MOBILE,SERVICE_MANAGER_MOBILE from tm_dealer_basicinfo where DEALER_CODE=?  ";
                    List<Object> params = new ArrayList<Object>();
                    params.add(FrameworkUtil.getLoginInfo().getDealerCode());
                    List<Map> checkMobile = DAOUtil.findAll(sql, params);
                    Map<String, Object> checkMobileMap = new HashMap<String, Object>();
                    if (checkMobile != null && checkMobile.size() > 0) {
                        checkMobileMap = checkMobile.get(0);
                        if (StringUtils.isEquals(positionCode, "E002")) { // 总经理手机
                            if (!StringUtils.isNullOrEmpty(checkMobileMap.get("GENERAL_MANAGER_MOBILE"))
                                && StringUtils.isEquals(checkMobileMap.get("GENERAL_MANAGER_MOBILE"), mobile)) {
                                generalManagerMobile = "LEAVE";
                                List<Object> genparams = new ArrayList<Object>();
                                params.add(DictCodeConstants.STATUS_IS_YES);
                                params.add("E002");
                                List<Map> genMobileList = DAOUtil.findAll(getMobilesql, genparams);
                                if (genMobileList != null && genMobileList.size() > 0) {
                                    for (int i = 0; i <= genMobileList.size() - 1; i++) {
                                        getMobile = genMobileList.get(i);
                                        if (!StringUtils.isNullOrEmpty(getMobile.get("MOBILE"))) {
                                            generalManagerMobile = getMobile.get("MOBILE").toString();
                                        }
                                    }

                                }
                            }
                        }
                        if (StringUtils.isEquals(positionCode, "M009")) { // 销售经理手机
                            if (!StringUtils.isNullOrEmpty(checkMobileMap.get("SALES_MANAGER_MOBILE"))
                                && StringUtils.isEquals(checkMobileMap.get("SALES_MANAGER_MOBILE"), mobile)) {
                                salesManagerMobile = "LEAVE";
                                List<Object> genparams = new ArrayList<Object>();
                                params.add(DictCodeConstants.STATUS_IS_YES);
                                params.add("M009");
                                List<Map> genMobileList = DAOUtil.findAll(getMobilesql, genparams);
                                if (genMobileList != null && genMobileList.size() > 0) {
                                    for (int i = 0; i <= genMobileList.size() - 1; i++) {
                                        getMobile = genMobileList.get(i);
                                        if (!StringUtils.isNullOrEmpty(getMobile.get("MOBILE"))) {
                                            salesManagerMobile = getMobile.get("MOBILE").toString();
                                        }
                                    }

                                }
                            }
                        }
                        if (StringUtils.isEquals(positionCode, "M011")) {// 服务经理手机
                            if (!StringUtils.isNullOrEmpty(checkMobileMap.get("SERVICE_MANAGER_MOBILE"))
                                && StringUtils.isEquals(checkMobileMap.get("SERVICE_MANAGER_MOBILE"), mobile)) {
                                serviceManagerMobile = "LEAVE";
                                List<Object> genparams = new ArrayList<Object>();
                                params.add(DictCodeConstants.STATUS_IS_YES);
                                params.add("M011");
                                List<Map> genMobileList = DAOUtil.findAll(getMobilesql, genparams);
                                if (genMobileList != null && genMobileList.size() > 0) {
                                    for (int i = 0; i <= genMobileList.size() - 1; i++) {
                                        getMobile = genMobileList.get(i);
                                        if (!StringUtils.isNullOrEmpty(getMobile.get("MOBILE"))) {
                                            salesManagerMobile = getMobile.get("MOBILE").toString();
                                        }
                                    }

                                }
                            }
                        }
                    }
                }
            }
        }
        if (!StringUtils.isNullOrEmpty(generalManagerMobile) || !StringUtils.isNullOrEmpty(salesManagerMobile)
            || !StringUtils.isNullOrEmpty(serviceManagerMobile)) {
            DealerBasicinfoPO dbPo = DealerBasicinfoPO.findByCompositeKeys(FrameworkUtil.getLoginInfo().getDealerCode());
            if (!StringUtils.isNullOrEmpty(generalManagerMobile)
                && !StringUtils.isEquals("LEAVE", generalManagerMobile)) {
                dbPo.setString("GENERAL_MANAGER_MOBILE", generalManagerMobile);
            } else if (StringUtils.isEquals("LEAVE", generalManagerMobile)) {
                dbPo.setString("GENERAL_MANAGER_MOBILE", null);
            }
            if (!StringUtils.isNullOrEmpty(salesManagerMobile) && !StringUtils.isEquals("LEAVE", salesManagerMobile)) {
                dbPo.setString("SALES_MANAGER_MOBILE", salesManagerMobile);
            } else if (StringUtils.isEquals("LEAVE", salesManagerMobile)) {
                dbPo.setString("SALES_MANAGER_MOBILE", null);
            }
            if (!StringUtils.isNullOrEmpty(serviceManagerMobile)
                && !StringUtils.isEquals("LEAVE", serviceManagerMobile)) {
                dbPo.setString("SERVICE_MANAGER_MOBILE", serviceManagerMobile);
            } else if (StringUtils.isEquals("LEAVE", serviceManagerMobile)) {
                dbPo.setString("SERVICE_MANAGER_MOBILE", null);
            }
        }
        String user_sql = "select DEALER_CODE,USER_ID,EMPLOYEE_NO from TM_USER where EMPLOYEE_NO=? ";
        List<Object> user_params = new ArrayList<Object>();
        user_params.add(empo.getString("EMPLOYEE_NO"));
        List<Map> userList = DAOUtil.findAll(user_sql, user_params);
        if (userList != null && userList.size() > 0) {
        	for(int i=0;i<userList.size();i++){
        		userService.deleteUserCtrl((Long)userList.get(i).get("USER_ID"));
        	}
        	
            UserPO userPO = UserPO.findByCompositeKeys(FrameworkUtil.getLoginInfo().getDealerCode(),userList.get(0).get("USER_ID"));
            userPO.setInteger("USER_STATUS", "12101002");
            userPO.saveIt();
        }
        System.err.println(empo.getInteger("employee_id"));
        empo.saveIt();
       
        return empo.getLongId();
    }
    
    /**
     * 复职
     * @author zhanshiwei
     * @date 2017年1月4日
     * @param empDto
     * @return
     * @throws ServiceBizException
     */

    @SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
    public Long changeEmpIsValid(String employeeNo) throws ServiceBizException {
        EmployeePo empo = EmployeePo.findFirst("DEALER_CODE=? and EMPLOYEE_NO=?", FrameworkUtil.getLoginInfo().getDealerCode(),employeeNo);
        
       // this.queryVehicle(empo.getString("EMPLOYEE_NO"), empo.getInteger("IS_DEFAULT_MANAGER"));
        empo.setInteger("IS_VALID", DictCodeConstants.STATUS_IS_YES);
        empo.setDate("ENTRY_DATE", new Date());
        empo.setDate("DIMISSION_DATE", null);

        // 更新经销商基本信息会用到同时调用上报接口
        String generalManagerMobile = ""; // 总经理手机
        String salesManagerMobile = ""; // 销售经理手机
        String serviceManagerMobile = ""; // 服务经理手机
        String getMobilesql = "select DEALER_CODE,MOBILE from TM_EMPLOYEE where IS_VALID=? and POSITION_CODE=?  ";
        Map<String, Object> getMobile = new HashMap<String, Object>();

        if (empo.getString("POSITION_CODE") != null) {
            String positionCode = empo.getString("POSITION_CODE");
            if (StringUtils.isEquals("E002", positionCode) || StringUtils.isEquals("M009", positionCode)
                || StringUtils.isEquals("M011", positionCode)) {
                // 若该员工的手机信息不为空，获得该手机信息
                if (!StringUtils.isNullOrEmpty(empo.getString("MOBILE"))) {
                    String mobile = empo.getString("MOBILE");
                    // 获得该员工的手机信息后，比对该员工手机是不是和经销商基本信息上面和该员工职务相对的手机相同，若相同替换为别他没有可替换则清空
                    String sql = "select DEALER_CODE,SALES_MANAGER_MOBILE,GENERAL_MANAGER_MOBILE,SERVICE_MANAGER_MOBILE from tm_dealer_basicinfo where DEALER_CODE=?  ";
                    List<Object> params = new ArrayList<Object>();
                    params.add(FrameworkUtil.getLoginInfo().getDealerCode());
                    List<Map> checkMobile = DAOUtil.findAll(sql, params);
                    Map<String, Object> checkMobileMap = new HashMap<String, Object>();
                    if (checkMobile != null && checkMobile.size() > 0) {
                        checkMobileMap = checkMobile.get(0);
                        if (StringUtils.isEquals(positionCode, "E002")) { // 总经理手机
                            if (!StringUtils.isNullOrEmpty(checkMobileMap.get("GENERAL_MANAGER_MOBILE"))
                                && StringUtils.isEquals(checkMobileMap.get("GENERAL_MANAGER_MOBILE"), mobile)) {
                                generalManagerMobile = "LEAVE";
                                List<Object> genparams = new ArrayList<Object>();
                                params.add(DictCodeConstants.STATUS_IS_YES);
                                params.add("E002");
                                List<Map> genMobileList = DAOUtil.findAll(getMobilesql, genparams);
                                if (genMobileList != null && genMobileList.size() > 0) {
                                    for (int i = 0; i <= genMobileList.size() - 1; i++) {
                                        getMobile = genMobileList.get(i);
                                        if (!StringUtils.isNullOrEmpty(getMobile.get("MOBILE"))) {
                                            generalManagerMobile = getMobile.get("MOBILE").toString();
                                        }
                                    }

                                }
                            }
                        }
                        if (StringUtils.isEquals(positionCode, "M009")) { // 销售经理手机
                            if (!StringUtils.isNullOrEmpty(checkMobileMap.get("SALES_MANAGER_MOBILE"))
                                && StringUtils.isEquals(checkMobileMap.get("SALES_MANAGER_MOBILE"), mobile)) {
                                salesManagerMobile = "LEAVE";
                                List<Object> genparams = new ArrayList<Object>();
                                params.add(DictCodeConstants.STATUS_IS_YES);
                                params.add("M009");
                                List<Map> genMobileList = DAOUtil.findAll(getMobilesql, genparams);
                                if (genMobileList != null && genMobileList.size() > 0) {
                                    for (int i = 0; i <= genMobileList.size() - 1; i++) {
                                        getMobile = genMobileList.get(i);
                                        if (!StringUtils.isNullOrEmpty(getMobile.get("MOBILE"))) {
                                            salesManagerMobile = getMobile.get("MOBILE").toString();
                                        }
                                    }

                                }
                            }
                        }
                        if (StringUtils.isEquals(positionCode, "M011")) {// 服务经理手机
                            if (!StringUtils.isNullOrEmpty(checkMobileMap.get("SERVICE_MANAGER_MOBILE"))
                                && StringUtils.isEquals(checkMobileMap.get("SERVICE_MANAGER_MOBILE"), mobile)) {
                                serviceManagerMobile = "LEAVE";
                                List<Object> genparams = new ArrayList<Object>();
                                params.add(DictCodeConstants.STATUS_IS_YES);
                                params.add("M011");
                                List<Map> genMobileList = DAOUtil.findAll(getMobilesql, genparams);
                                if (genMobileList != null && genMobileList.size() > 0) {
                                    for (int i = 0; i <= genMobileList.size() - 1; i++) {
                                        getMobile = genMobileList.get(i);
                                        if (!StringUtils.isNullOrEmpty(getMobile.get("MOBILE"))) {
                                            salesManagerMobile = getMobile.get("MOBILE").toString();
                                        }
                                    }

                                }
                            }
                        }
                    }
                }
            }
        }
        if (!StringUtils.isNullOrEmpty(generalManagerMobile) || !StringUtils.isNullOrEmpty(salesManagerMobile)
            || !StringUtils.isNullOrEmpty(serviceManagerMobile)) {
            DealerBasicinfoPO dbPo = DealerBasicinfoPO.findByCompositeKeys(FrameworkUtil.getLoginInfo().getDealerCode());
            if (!StringUtils.isNullOrEmpty(generalManagerMobile)
                && !StringUtils.isEquals("LEAVE", generalManagerMobile)) {
                dbPo.setString("GENERAL_MANAGER_MOBILE", generalManagerMobile);
            } else if (StringUtils.isEquals("LEAVE", generalManagerMobile)) {
                dbPo.setString("GENERAL_MANAGER_MOBILE", null);
            }
            if (!StringUtils.isNullOrEmpty(salesManagerMobile) && !StringUtils.isEquals("LEAVE", salesManagerMobile)) {
                dbPo.setString("SALES_MANAGER_MOBILE", salesManagerMobile);
            } else if (StringUtils.isEquals("LEAVE", salesManagerMobile)) {
                dbPo.setString("SALES_MANAGER_MOBILE", null);
            }
            if (!StringUtils.isNullOrEmpty(serviceManagerMobile)
                && !StringUtils.isEquals("LEAVE", serviceManagerMobile)) {
                dbPo.setString("SERVICE_MANAGER_MOBILE", serviceManagerMobile);
            } else if (StringUtils.isEquals("LEAVE", serviceManagerMobile)) {
                dbPo.setString("SERVICE_MANAGER_MOBILE", null);
            }
        }
        String user_sql = "select DEALER_CODE,USER_ID,EMPLOYEE_NO from TM_USER where EMPLOYEE_NO=? ";
        List<Object> user_params = new ArrayList<Object>();
        user_params.add(empo.getString("EMPLOYEE_NO"));
        List<Map> userList = DAOUtil.findAll(user_sql, user_params);
        if (userList != null && userList.size() > 0) {
            UserPO userPO = UserPO.findByCompositeKeys(FrameworkUtil.getLoginInfo().getDealerCode(),userList.get(0).get("USER_ID"));
            userPO.setInteger("USER_STATUS", "12101001");
            userPO.saveIt();
        }
        empo.saveIt();
        return empo.getLongId();
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
    public List<Map> queryEmpInfoforExport(Map<String, String> param) throws ServiceBizException {
        StringBuilder sb = getSql();
        List<Object> queryParam = new ArrayList<Object>();
        if (StringUtils.isNullOrEmpty(param.get("isValid"))) {
            sb.append(" and tme.IS_VALID = ?");
            queryParam.add(DictCodeConstants.STATUS_IS_YES);
        }
        List<Map> resultList = DAOUtil.findAll(sb.toString(), queryParam);
        for (Map map : resultList) {
            if (map.get("GENDER") != null && map.get("GENDER") != "") {
                 if (Integer.parseInt(map.get("GENDER").toString()) == DictCodeConstants.DICT_GENDER_MAN) {
                     map.put("GENDER", "男");
                 } else if (Integer.parseInt(map.get("GENDER").toString()) == DictCodeConstants.DICT_GENDER_WOMAN) {
                     map.put("GENDER", "女");
                 } else if (Integer.parseInt(map.get("GENDER").toString()) == 0) {
                     map.put("GENDER", " ");
                 }                        
             }
           
             if (map.get("IS_TECHNICIAN") != null && map.get("IS_TECHNICIAN") != "") {
                 if (Integer.parseInt(map.get("IS_TECHNICIAN").toString()) == DictCodeConstants.STATUS_IS_YES) {
                     map.put("IS_TECHNICIAN", "是");
                 } else if (Integer.parseInt(map.get("IS_TECHNICIAN").toString()) == DictCodeConstants.STATUS_IS_NOT) {
                     map.put("IS_TECHNICIAN", "否");
                 } else if (Integer.parseInt(map.get("IS_TECHNICIAN").toString()) == 0) {
                     map.put("IS_TECHNICIAN", " ");
                 } 
             }
             if (map.get("TECHNICIAN_GRADE") != null && map.get("TECHNICIAN_GRADE") != "") {
                 if (Integer.parseInt(map.get("TECHNICIAN_GRADE").toString()) == DictCodeConstants.ONE_LEVEL) {
                     map.put("TECHNICIAN_GRADE", "一级");
                 } else if (Integer.parseInt(map.get("TECHNICIAN_GRADE").toString()) == DictCodeConstants.TWO_LEVEL) {
                     map.put("TECHNICIAN_GRADE", "二级");
                 } else if (Integer.parseInt(map.get("TECHNICIAN_GRADE").toString()) == DictCodeConstants.THREE_LEVEL) {
                     map.put("TECHNICIAN_GRADE", "三级");
                 }else if (Integer.parseInt(map.get("TECHNICIAN_GRADE").toString()) == DictCodeConstants.FOUR_LEVEL) {
                     map.put("TECHNICIAN_GRADE", "四级");
                 }else if (Integer.parseInt(map.get("TECHNICIAN_GRADE").toString()) == 0) {
                     map.put("TECHNICIAN_GRADE", " ");
                 }
             }
             if (map.get("IS_SERVICE_ADVISOR") != null && map.get("IS_SERVICE_ADVISOR") != "") {
                 if (Integer.parseInt(map.get("IS_SERVICE_ADVISOR").toString()) == DictCodeConstants.STATUS_IS_YES) {
                     map.put("IS_SERVICE_ADVISOR", "是");
                 } else if (Integer.parseInt(map.get("IS_SERVICE_ADVISOR").toString()) == DictCodeConstants.STATUS_IS_NOT) {
                     map.put("IS_SERVICE_ADVISOR", "否");
                 } else if (Integer.parseInt(map.get("IS_SERVICE_ADVISOR").toString()) == 0) {
                     map.put("IS_SERVICE_ADVISOR", " ");
                 } 
             }
             if (map.get("IS_INSURANCE_ADVISOR") != null && map.get("IS_INSURANCE_ADVISOR") != "") {
                 if (Integer.parseInt(map.get("IS_INSURANCE_ADVISOR").toString()) == DictCodeConstants.STATUS_IS_YES) {
                     map.put("IS_INSURANCE_ADVISOR", "是");
                 } else if (Integer.parseInt(map.get("IS_INSURANCE_ADVISOR").toString()) == DictCodeConstants.STATUS_IS_NOT) {
                     map.put("IS_INSURANCE_ADVISOR", "否");
                 } else if (Integer.parseInt(map.get("IS_INSURANCE_ADVISOR").toString()) == 0) {
                     map.put("IS_INSURANCE_ADVISOR", " ");
                 } 
             }
             if (map.get("IS_MAINTAIN_ADVISOR") != null && map.get("IS_MAINTAIN_ADVISOR") != "") {
                 if (Integer.parseInt(map.get("IS_MAINTAIN_ADVISOR").toString()) == DictCodeConstants.STATUS_IS_YES) {
                     map.put("IS_MAINTAIN_ADVISOR", "是");
                 } else if (Integer.parseInt(map.get("IS_MAINTAIN_ADVISOR").toString()) == DictCodeConstants.STATUS_IS_NOT) {
                     map.put("IS_MAINTAIN_ADVISOR", "否");
                 } else if (Integer.parseInt(map.get("IS_MAINTAIN_ADVISOR").toString()) == 0) {
                     map.put("IS_MAINTAIN_ADVISOR", " ");
                 } 
             }
             if (map.get("IS_TRACER") != null && map.get("IS_TRACER") != "") {
                 if (Integer.parseInt(map.get("IS_TRACER").toString()) == DictCodeConstants.STATUS_IS_YES) {
                     map.put("IS_TRACER", "是");
                 } else if (Integer.parseInt(map.get("IS_TRACER").toString()) == DictCodeConstants.STATUS_IS_NOT) {
                     map.put("IS_TRACER", "否");
                 } else if (Integer.parseInt(map.get("IS_TRACER").toString()) == 0) {
                     map.put("IS_TRACER", " ");
                 } 
             }
             if (map.get("IS_TEST_DRIVER") != null && map.get("IS_TEST_DRIVER") != "") {
                 if (Integer.parseInt(map.get("IS_TEST_DRIVER").toString()) == DictCodeConstants.STATUS_IS_YES) {
                     map.put("IS_TEST_DRIVER", "是");
                 } else if (Integer.parseInt(map.get("IS_TEST_DRIVER").toString()) == DictCodeConstants.STATUS_IS_NOT) {
                     map.put("IS_TEST_DRIVER", "否");
                 } else if (Integer.parseInt(map.get("IS_TEST_DRIVER").toString()) == 0) {
                     map.put("IS_TEST_DRIVER", " ");
                 } 
             }
             if (map.get("IS_CHECKER") != null && map.get("IS_CHECKER") != "") {
                 if (Integer.parseInt(map.get("IS_CHECKER").toString()) == DictCodeConstants.STATUS_IS_YES) {
                     map.put("IS_CHECKER", "是");
                 } else if (Integer.parseInt(map.get("IS_CHECKER").toString()) == DictCodeConstants.STATUS_IS_NOT) {
                     map.put("IS_CHECKER", "否");
                 } else if (Integer.parseInt(map.get("IS_CHECKER").toString()) == 0) {
                     map.put("IS_CHECKER", " ");
                 } 
             }
             if (map.get("IS_TAKE_PART") != null && map.get("IS_TAKE_PART") != "") {
                 if (Integer.parseInt(map.get("IS_TAKE_PART").toString()) == DictCodeConstants.STATUS_IS_YES) {
                     map.put("IS_TAKE_PART", "是");
                 } else if (Integer.parseInt(map.get("IS_TAKE_PART").toString()) == DictCodeConstants.STATUS_IS_NOT) {
                     map.put("IS_TAKE_PART", "否");
                 } else if (Integer.parseInt(map.get("IS_TAKE_PART").toString()) == 0) {
                     map.put("IS_TAKE_PART", " ");
                 } 
             }
             if (map.get("IS_VALID") != null && map.get("IS_VALID") != "") {
                 if (Integer.parseInt(map.get("IS_VALID").toString()) == DictCodeConstants.STATUS_IS_YES) {
                     map.put("IS_VALID", "是");
                 } else if (Integer.parseInt(map.get("IS_VALID").toString()) == DictCodeConstants.STATUS_IS_NOT) {
                     map.put("IS_VALID", "否");
                 } else if (Integer.parseInt(map.get("IS_VALID").toString()) == 0) {
                     map.put("IS_VALID", " ");
                 } 
             }
             if (map.get("IS_UPHOLSTER_ADVISOR") != null && map.get("IS_UPHOLSTER_ADVISOR") != "") {
                 if (Integer.parseInt(map.get("IS_UPHOLSTER_ADVISOR").toString()) == DictCodeConstants.STATUS_IS_YES) {
                     map.put("IS_UPHOLSTER_ADVISOR", "是");
                 } else if (Integer.parseInt(map.get("IS_UPHOLSTER_ADVISOR").toString()) == DictCodeConstants.STATUS_IS_NOT) {
                     map.put("IS_UPHOLSTER_ADVISOR", "否");
                 } else if (Integer.parseInt(map.get("IS_UPHOLSTER_ADVISOR").toString()) == 0) {
                     map.put("IS_UPHOLSTER_ADVISOR", " ");
                 } 
             }
             if (map.get("IS_INSURATION_ADVISOR") != null && map.get("IS_INSURATION_ADVISOR") != "") {
                 if (Integer.parseInt(map.get("IS_INSURATION_ADVISOR").toString()) == DictCodeConstants.STATUS_IS_YES) {
                     map.put("IS_INSURATION_ADVISOR", "是");
                 } else if (Integer.parseInt(map.get("IS_INSURATION_ADVISOR").toString()) == DictCodeConstants.STATUS_IS_NOT) {
                     map.put("IS_INSURATION_ADVISOR", "否");
                 } else if (Integer.parseInt(map.get("IS_INSURATION_ADVISOR").toString()) == 0) {
                     map.put("IS_INSURATION_ADVISOR", " ");
                 } 
             }
             if (map.get("IS_MAJOR_REPAIRER") != null && map.get("IS_MAJOR_REPAIRER") != "") {
                 if (Integer.parseInt(map.get("IS_MAJOR_REPAIRER").toString()) == DictCodeConstants.STATUS_IS_YES) {
                     map.put("IS_MAJOR_REPAIRER", "是");
                 } else if (Integer.parseInt(map.get("IS_MAJOR_REPAIRER").toString()) == DictCodeConstants.STATUS_IS_NOT) {
                     map.put("IS_MAJOR_REPAIRER", "否");
                 } else if (Integer.parseInt(map.get("IS_MAJOR_REPAIRER").toString()) == 0) {
                     map.put("IS_MAJOR_REPAIRER", " ");
                 } 
             }
             if (map.get("IS_DISPATCHER") != null && map.get("IS_DISPATCHER") != "") {
                 if (Integer.parseInt(map.get("IS_DISPATCHER").toString()) == DictCodeConstants.STATUS_IS_YES) {
                     map.put("IS_DISPATCHER", "是");
                 } else if (Integer.parseInt(map.get("IS_DISPATCHER").toString()) == DictCodeConstants.STATUS_IS_NOT) {
                     map.put("IS_DISPATCHER", "否");
                 } else if (Integer.parseInt(map.get("IS_DISPATCHER").toString()) == 0) {
                     map.put("IS_DISPATCHER", " ");
                 } 
             }
             if (map.get("IS_UPLOAD") != null && map.get("IS_UPLOAD") != "") {
                 if (Integer.parseInt(map.get("IS_UPLOAD").toString()) == DictCodeConstants.STATUS_IS_YES) {
                     map.put("IS_UPLOAD", "是");
                 } else if (Integer.parseInt(map.get("IS_UPLOAD").toString()) == DictCodeConstants.STATUS_IS_NOT) {
                     map.put("IS_UPLOAD", "否");
                 } else if (Integer.parseInt(map.get("IS_UPLOAD").toString()) == 0) {
                     map.put("IS_UPLOAD", " ");
                 } 
             }
           
            
             
        }
        
        
        
        
        
        OperateLogDto operateLogDto=new OperateLogDto();
        operateLogDto.setOperateContent("员工信息导出");
        operateLogDto.setOperateType(DictCodeConstants.LOG_CUSTOMER_MANAGEMENT);
        operateLogService.writeOperateLog(operateLogDto);
        return resultList;
    }

    /**
     * @author zhanshiwei
     * @date 2017年1月3日
     * @param employeeNo
     * @return
     * @throws ServiceBizException (non-Javadoc)
     * @see com.yonyou.dms.manage.service.basedata.employee.EmployeeService#employeSaveBeforeEvent(java.lang.String)
     */

    @SuppressWarnings("rawtypes")
	@Override
    public Map<String, Object> employeSaveBeforeEvent(String employeeNo) throws ServiceBizException {
        Map<String, Object> result = new HashMap<String, Object>();
        List<Object> DefaultManagerParams = new ArrayList<Object>();
        StringBuilder isDefaultManagerSql = new StringBuilder("SELECT t1.employee_name,t1.dealer_code,t1.employee_no FROM tm_employee t1 where IS_DEFAULT_MANAGER=?");
        DefaultManagerParams.add("12781001");
        if (!StringUtils.isNullOrEmpty(employeeNo)) {
            isDefaultManagerSql.append(" and employee_no<>?");
            DefaultManagerParams.add(employeeNo);
        }
        List<Map> isDefaultManagerResult = DAOUtil.findAll(isDefaultManagerSql.toString(), DefaultManagerParams);
        if(isDefaultManagerResult!=null&&isDefaultManagerResult.size()>0){
            result.put("isDefaultManagerResult", isDefaultManagerResult);   
        }
        return result;
    }

    /**
     * @author zhanshiwei
     * @date 2017年1月3日
     * @param empDto
     * @return
     * @throws Exception 
     * @see com.yonyou.dms.manage.service.basedata.employee.EmployeeService#addGFkmployee(com.yonyou.dms.manage.domains.DTO.basedata.EmployeeDto)
     */

    @Override
    public Long addGFkmployee(EmployeeDto empDto) throws Exception {
        EmployeePo emp = new EmployeePo();
        // FrameworkUtil.getLoginInfo().getDealerCode() +
        String employeeNo = empDto.getEmployeeNo(); // 员工编号
        emp.setString("EMPLOYEE_NO", employeeNo);

        setGFkEmployee(emp, empDto);
        this.MaintainEmployee("A", emp, empDto, null);
        emp.saveIt();
        EmployeeTrainingPO.delete("EMPLOYEE_NO=?", employeeNo);
        if (empDto.getListEmpTrain() != null && empDto.getListEmpTrain().size() > 0) {
            for (EmployeeTrainingDto empTra : empDto.getListEmpTrain()) {
                empTra.setEmployeeNo(empTra.getEmployeeNo());
                EmployeeTrainingPO empTraPo = getEmployeeTrainingPO(empTra);
                empTraPo.saveIt();
            }
        }
        return null;
    }

    /**
     * @author zhanshiwei
     * @date 2017年1月3日
     * @param id
     * @param empDto
     * @throws Exception 
     * @see com.yonyou.dms.manage.service.basedata.employee.EmployeeService#updateGFkEmpById(java.lang.Long,
     * com.yonyou.dms.manage.domains.DTO.basedata.EmployeeDto)
     */

    @Override
    public void updateGFkEmpById(String id, EmployeeDto empDto) throws Exception {
        EmployeePo empo = EmployeePo.findById(id);
       // this.CheckSaveBefore(empDto, id);
        setGFkEmployee(empo, empDto);
        
        this.MaintainEmployee("U", empo, empDto, id);
        // 保存员工信息
        empo.saveIt();
        EmployeeTrainingPO.delete("EMPLOYEE_NO=?", empDto.getEmployeeNo());
        if (empDto.getListEmpTrain() != null && empDto.getListEmpTrain().size() > 0) {
            for (EmployeeTrainingDto empTra : empDto.getListEmpTrain()) {
                empTra.setEmployeeNo(empDto.getEmployeeNo());
                EmployeeTrainingPO empTraPo = getEmployeeTrainingPO(empTra);
                empTraPo.saveIt();
            }
        }
    }

    /**
     * 维护员工信息
     * 
     * @author zhanshiwei
     * @date 2016年12月22日
     */

    public void MaintainEmployee(String maintain_state, EmployeePo empo, EmployeeDto empDto, String id) {
        // 更新经销商基本信息会用到同时调用上报接口
        String generalManagerMobile = ""; // 总经理手机
        String salesManagerMobile = ""; // 销售经理手机
        String serviceManagerMobile = ""; // 服务经理手机
        // 无论是新增还是修改都先对是否默认微信客户经理进行判断如果是 是 就讲表中所有数据的该字段都设为否
        // 然后由后面的更新语句在进行更新
        if (!StringUtils.isNullOrEmpty(empDto.getIsDefaultManager())
            && StringUtils.isEquals(empDto.getIsDefaultManager(), "12781001")) {
            EmployeePo.update("IS_DEFAULT_MANAGER=12781002", "DEALER_CODE=?",
                              FrameworkUtil.getLoginInfo().getDealerCode());
        }
        if (StringUtils.isEquals(maintain_state, "A")) {
            // 标记为A进行插入操作
            DealerBasicinfoPO dbPo = DealerBasicinfoPO.findByCompositeKeys(FrameworkUtil.getLoginInfo().getDealerCode());
            if (dbPo != null) {
                if (StringUtils.isEquals("E002", empDto.getPositionCode())) {
                    if (!StringUtils.isNullOrEmpty(dbPo.getString("GENERAL_MANAGER_MOBILE"))) {
                        generalManagerMobile = empDto.getMobile(); // 总经理手机
                    }
                } else if (StringUtils.isEquals("E009", empDto.getPositionCode())) {
                    if (!StringUtils.isNullOrEmpty(dbPo.getString("SALES_MANAGER_MOBILE"))) {
                        salesManagerMobile = empDto.getMobile(); // 销售经理手机
                    }
                } else if (StringUtils.isEquals("M011", empDto.getPositionCode())) {
                    if (!StringUtils.isNullOrEmpty(dbPo.getString("SERVICE_MANAGER_MOBILE"))) {
                        serviceManagerMobile = empDto.getMobile(); // 服务经理手机
                    }
                }
            }

        } else if (StringUtils.isEquals(maintain_state, "U")) {
            empo = EmployeePo.findById(id);
            if (empo.getString("POSITION_CODE") != null) {
                String positionCode = empo.getString("POSITION_CODE");
                // 检验特定字段是否有改变，若有改变则调用上报接口
                if (StringUtils.isEquals("E002", positionCode) || StringUtils.isEquals("M009", positionCode)
                    || StringUtils.isEquals("M011", positionCode)) {
                    // 如果是在职，那么查看手机是否改变（离职变为在职的情况不调用上报接口上报手机）
                    if (StringUtils.isEquals("12781001", empo.getString("USER_STATUS"))) {
                        if (!StringUtils.isEquals(empo.getString("MOBILE"), empDto.getMobile())) {
                            if (StringUtils.isEquals("E002", empDto.getPositionCode())) {
                                if (!StringUtils.isNullOrEmpty(empDto.getMobile())) {
                                    generalManagerMobile = empDto.getMobile(); // 总经理手机
                                } else {
                                    generalManagerMobile = "CLEAN";
                                }
                            } else if (StringUtils.isEquals("E009", empDto.getPositionCode())) {
                                if (!StringUtils.isNullOrEmpty(empDto.getMobile())) {
                                    salesManagerMobile = empDto.getMobile(); // 总经理手机
                                } else {
                                    salesManagerMobile = "CLEAN";
                                }
                            } else if (StringUtils.isEquals("E0011", empDto.getPositionCode())) {
                                if (!StringUtils.isNullOrEmpty(empDto.getMobile())) {
                                    serviceManagerMobile = empDto.getMobile(); // 总经理手机
                                } else {
                                    serviceManagerMobile = "CLEAN";
                                }
                            }
                        }
                    }

                }
            }
        }
        // 保存经销商基本信息的三个手机信息
        if (!StringUtils.isNullOrEmpty(generalManagerMobile) || !StringUtils.isNullOrEmpty(salesManagerMobile)
            || !StringUtils.isNullOrEmpty(serviceManagerMobile)) {
            DealerBasicinfoPO dbPo = DealerBasicinfoPO.findByCompositeKeys(FrameworkUtil.getLoginInfo().getDealerCode());
            if (!StringUtils.isNullOrEmpty(generalManagerMobile)
                && !StringUtils.isEquals("CLEAN", generalManagerMobile)) {
                dbPo.setString("GENERAL_MANAGER_MOBILE", generalManagerMobile);
            }
            if (!StringUtils.isNullOrEmpty(salesManagerMobile) && !StringUtils.isEquals("CLEAN", salesManagerMobile)) {
                dbPo.setString("SALES_MANAGER_MOBILE", salesManagerMobile);
            }
            if (!StringUtils.isNullOrEmpty(serviceManagerMobile)
                && !StringUtils.isEquals("CLEAN", serviceManagerMobile)) {
                dbPo.setString("SERVICE_MANAGER_MOBILE", serviceManagerMobile);
            }
            dbPo.saveIt();
        }

    }

    /**
     * 保存前验证
     * 
     * @author zhanshiwei
     * @date 2016年12月29日
     * @param empDto
     * @param id
     */

   /* private void CheckSaveBefore(EmployeeDto empDto, String id) {
        if (StringUtils.isNullOrEmpty(id) && SearchByEmployeeId(empDto.getEmployeeNo())!=0) {
            throw new ServiceBizException("员工编号在主站/分站中已存在！");
        }
        if (!StringUtils.isNullOrEmpty(id) && editSearchByMobile(empDto.getMobile(), id)!=0) {
            throw new ServiceBizException("不允许重复手机号，请修改后保存！");
        }

    }*/
    /**
     * 新增保存前验证
     * 
     * @author zhanshiwei
     * @date 2016年12月29日
     * @param empDto
     * @param id
     */

    @SuppressWarnings("unused")
	private void CheckAddBefore(EmployeeDto empDto, String id) {
        if (StringUtils.isNullOrEmpty(id) &&SearchByEmployeeNo(empDto.getEmployeeNo())!=0) {
            throw new ServiceBizException("员工编号在主站/分站中已存在！");
        }
        if (StringUtils.isNullOrEmpty(id) && SearchByMobile(empDto.getMobile(), id)!=0) {
            throw new ServiceBizException("不允许重复手机号，请修改后保存！");
        }

    }
    
 

    /**
     * 设置培训po属性
     * 
     * @author zhanshiwei
     * @date 2016年12月19日
     * @param empTra
     * @return
     */

    public EmployeeTrainingPO getEmployeeTrainingPO(EmployeeTrainingDto empTra) {
        EmployeeTrainingPO empTraPo = new EmployeeTrainingPO();
        empTraPo.setString("EMPLOYEE_NO", empTra.getEmployeeNo());
        empTraPo.setString("TRAINING_DEPARTMENT", empTra.getTrainingDepartment());
        empTraPo.setDate("TRAINING_TIME", empTra.getTrainingTime());
        empTraPo.setString("TRAINING_LEVEL", empTra.getTrainingLevel());
        empTraPo.setString("TRAINING_COURSE", empTra.getTrainingCourse());
        empTraPo.setString("TRAINING_RESULT", empTra.getTrainingResult());
        empTraPo.setString("TRAINING_TYPE", empTra.getTrainingType());
        empTraPo.setString("REMARK", empTra.getRemark());
        return empTraPo;
    }

    /**
     * @author zhanshiwei
     * @date 2017年1月3日
     * @param serAdvisor
     */

    public void queryVehicle(String serAdvisor, Integer isDefaultManager) {
        if (StringUtils.isNullOrEmpty(isDefaultManager)
            && StringUtils.isEquals(DictCodeConstants.IS_YES, isDefaultManager.toString())) {
            throw new ServiceBizException("该客户经理是微信默认客户经理，请更改微信默认客户经理,在进行离职！！");
        }
        String sql = "select VIN,DEALER_CODE from tm_vehicle where SERVICE_ADVISOR=?";
        List<Object> queryList = new ArrayList<Object>();
        queryList.add(serAdvisor);
        if (DAOUtil.findAll(sql, queryList).size() > 0) {
            throw new ServiceBizException("该员工下存在专属客户经理绑定信息，请先分派否则不能进行离职！");
        }

    }

    /**
     * 得到sql语句
     * 
     * @author zhanshiwei
     * @date 2016年12月20日
     * @return
     */

    public StringBuilder getSql() {
        StringBuilder sb = new StringBuilder("select \n");
        sb.append(" tme.EMPLOYEE_ID,tme.DEALER_CODE,tme.EMPLOYEE_NO,tme.ORG_CODE,tme.EMPLOYEE_NAME,tme.GENDER,tme.CERTIFICATE_ID,tme.POSITION_CODE,tme.IS_SERVICE_ADVISOR,two.ORDER_NAME,\n");
        sb.append(" posi.POSITION_NAME,works.WORKGROUP_NAME,workType.WORKER_TYPE_NAME,tme.IS_TECHNICIAN,tme.TECHNICIAN_GRADE,tme.IS_INSURANCE_ADVISOR,tme.IS_DEFAULT_MANAGER,\n");
        sb.append(" tme.IS_MAINTAIN_ADVISOR,tme.IS_TRACER,tme.PHONE,tme.MOBILE,tme.E_MAIL,tme.BIRTHDAY,tme.ADDRESS,tme.ZIP_CODE,tme.FOUND_DATE,tme.ENTRY_DATE,tdi.DISPATCH_NAME,\n");
        sb.append(" tme.DIMISSION_DATE,tme.RESUME,tme.LABOUR_POSITION_CODE,tme.IS_TEST_DRIVER,tme.IS_CHECKER,tme.IS_TAKE_PART,tme.IS_VALID,tme.LABOUR_FACTOR,tme.IS_UPHOLSTER_ADVISOR,tme.IS_INSURATION_ADVISOR,\n");
        sb.append(" tme.DISPATCH_CODE,tme.DEFAULT_POSITION,tme.IS_MAJOR_REPAIRER,tme.IS_DISPATCHER,tme.ORDER_CODE,tme.IS_UPLOAD,tme.CREATED_AT,tmdo.ORG_NAME,tle.TECH_LEVEL_NAME,trp.LABOUR_POSITION_NAME,tro.LABOUR_POSITION_NAME as DEFAULT_POSITION_NAME \n");
        sb.append(" from tm_employee tme \n");
        sb.append(" left join TM_POSITION posi on tme.POSITION_CODE=posi.POSITION_CODE and tme.DEALER_CODE=posi.DEALER_CODE\n");
        sb.append(" left join TM_WORKGROUP works on tme.WORKGROUP_CODE=works.WORKGROUP_CODE and tme.DEALER_CODE=works.DEALER_CODE\n");
        sb.append(" left join TM_WORKER_TYPE workType on tme.WORKER_TYPE_CODE=workType.WORKER_TYPE_CODE and tme.DEALER_CODE=workType.DEALER_CODE\n");
        sb.append(" left join TM_TECH_LEVEL tle on tme.TECH_LEVEL_CODE=tle.TECH_LEVEL_CODE and tme.DEALER_CODE=tle.DEALER_CODE\n");
        sb.append(" left join TM_REPAIR_POSITION trp on tme.LABOUR_POSITION_CODE=trp.LABOUR_POSITION_CODE and tme.DEALER_CODE=trp.DEALER_CODE\n");
        sb.append(" left join TM_REPAIR_POSITION tro on tro.LABOUR_POSITION_CODE=tme.DEFAULT_POSITION and tme.DEALER_CODE=tro.DEALER_CODE\n");
        sb.append(" left join TM_DISPATCH tdi on tme.DISPATCH_CODE=tdi.DISPATCH_CODE and tme.DEALER_CODE=tdi.DEALER_CODE\n");
        sb.append(" left join TM_WORK_ORDER two on tme.ORDER_CODE=two.ORDER_CODE and tme.DEALER_CODE=two.DEALER_CODE\n");
        sb.append(" LEFT JOIN tm_organization tmdo on tme.ORG_CODE=tmdo.ORG_CODE and tmdo.DEALER_CODE=tme.DEALER_CODE where 1=1 ");
        return sb;
    }

    /**
     * (GFK员工信息管理)查询
     * 
     * @author zhanshiwei
     * @date 2017年1月4日
     * @param param
     * @return
     * @throws ServiceBizException
     */

    public PageInfoDto searchGFkEmployees(@RequestParam Map<String, String> param) throws ServiceBizException {
        StringBuilder sb = getSql();
        List<Object> queryParam = new ArrayList<Object>();
        if (StringUtils.isNullOrEmpty(param.get("isValid"))) {
            sb.append(" and tme.IS_VALID = ?");
            queryParam.add(DictCodeConstants.STATUS_IS_YES);
        }
        //是否从用户角色中选择的
        if(!StringUtils.isNullOrEmpty(param.get("isUserRole"))&&StringUtils.isEquals(DictCodeConstants.STATUS_IS_YES, param.get("isUserRole"))){
            sb.append(" and  not exists( select 1 from tm_user tu where  tu.EMPLOYEE_NO=tme.EMPLOYEE_NO and tu.DEALER_CODE=tme.DEALER_CODE)");
        }
        return DAOUtil.pageQuery(sb.toString(), queryParam);
    }
    
    /**
     * (GFK)用户权限首页菜单查询
     * 
     * @author zhanshiwei
     * @date 2017年1月4日
     * @param param
     * @return
     * @throws ServiceBizException
     */

    @Override
    public PageInfoDto searchUserGFkEmployee(Map<String, String> param) throws ServiceBizException {
        StringBuilder sb = new StringBuilder("SELECT tu.ORG_CODE,tmdo.ORG_NAME,tu.USER_ID,tu.USER_NAME,tu.IS_SERVICE_ADVISOR,tu.IS_CONSULTANT,te.EMPLOYEE_ID AS EMPLOYEE_ID,te.DEALER_CODE,te.EMPLOYEE_NO,te.EMPLOYEE_NAME,tu.USER_CODE,tu.USER_STATUS,tu.PASSWORD,te.POSITION_CODE, ");
        sb.append("te.GENDER,te.CERTIFICATE_ID,te.PHONE,te.MOBILE,te.E_MAIL,te.BIRTHDAY,te.ADDRESS,te.ZIP_CODE,te.WORKER_TYPE_CODE,te.TECHNICIAN_GRADE,te.DEFAULT_POSITION,");
        sb.append("te.FOUND_DATE,te.DIMISSION_DATE,te.IS_VALID,case WHEN te.IS_VALID='12781001' then '10081001' ELSE '10081002'  END as IS_ONJOB from  tm_user tu inner join tm_employee te ON tu.EMPLOYEE_NO=te.EMPLOYEE_NO and te.DEALER_CODE=tu.DEALER_CODE");
        sb.append(" LEFT JOIN tm_organization tmdo on tu.ORG_CODE=tmdo.ORG_CODE and tmdo.DEALER_CODE=tu.DEALER_CODE where 1=1  ");
        List<Object> queryParam = new ArrayList<Object>();

        if (!StringUtils.isNullOrEmpty(param.get("employeeNo"))) {
            sb.append(" and te.EMPLOYEE_NO like ?");
            queryParam.add("%" + param.get("employeeNo") + "%");
        }
        if (!StringUtils.isNullOrEmpty(param.get("userCode"))) {
            sb.append(" and tu.USER_CODE like ?");
            queryParam.add("%" + param.get("userCode") + "%");
        }
        if (!StringUtils.isNullOrEmpty(param.get("employeeName"))) {
            sb.append(" and te.EMPLOYEE_NAME like ?");
            queryParam.add("%" + param.get("employeeName") + "%");
        }

        if (!StringUtils.isNullOrEmpty(param.get("workingState"))) {
            if(StringUtils.isEquals(DictCodeConstants.EMPLOYEE_ISJOB, param.get("workingState"))){
                param.put("workingState", DictCodeConstants.STATUS_IS_YES+"");
            }else{
                param.put("workingState", DictCodeConstants.STATUS_IS_NOT+"");
            }
            sb.append(" and te.IS_VALID = ?");
            queryParam.add(Integer.parseInt(param.get("workingState")));
        }
        if (!StringUtils.isNullOrEmpty(param.get("userState"))) {
            sb.append(" and tu.USER_STATUS = ?");
            queryParam.add(Integer.parseInt(param.get("userState")));
        }
        // 执行查询
        PageInfoDto pageResult = DAOUtil.pageQuery(sb.toString(), queryParam);

        return pageResult;

    }

    /**
     * (GFK)用户权限菜单查询
     * 
     * @author zhanshiwei
     * @date 2017年1月4日
     * @param param
     * @return
     * @throws ServiceBizException
     */

    @Override
    public PageInfoDto searchUserGFkEmployees(Map<String, String> param) throws ServiceBizException {
    	String employeeNo=FrameworkUtil.getLoginInfo().getEmployeeNo();
        StringBuilder sb = new StringBuilder("SELECT tu.ORG_CODE,tmdo.ORG_NAME,tu.USER_ID,tu.USER_NAME,tu.IS_SERVICE_ADVISOR,tu.IS_CONSULTANT,te.EMPLOYEE_ID AS EMPLOYEE_ID,te.DEALER_CODE,te.EMPLOYEE_NO,te.EMPLOYEE_NAME,tu.USER_CODE,tu.USER_STATUS,tu.PASSWORD,te.POSITION_CODE, ");
        sb.append("te.GENDER,te.CERTIFICATE_ID,te.PHONE,te.MOBILE,te.E_MAIL,te.BIRTHDAY,te.ADDRESS,te.ZIP_CODE,te.WORKER_TYPE_CODE,te.TECHNICIAN_GRADE,te.DEFAULT_POSITION,");
        sb.append("te.FOUND_DATE,te.DIMISSION_DATE,te.IS_VALID,case WHEN te.IS_VALID='12781001' then '10081001' ELSE '10081002'  END as IS_ONJOB from tm_employee te  inner join tm_user tu ON tu.EMPLOYEE_NO=te.EMPLOYEE_NO and te.DEALER_CODE=tu.DEALER_CODE");
        sb.append(" LEFT JOIN tm_organization tmdo on tu.ORG_CODE=tmdo.ORG_CODE and tmdo.DEALER_CODE=tu.DEALER_CODE where 1=1 AND te.EMPLOYEE_NO !="+employeeNo+" ");
        List<Object> queryParam = new ArrayList<Object>();

        if (!StringUtils.isNullOrEmpty(param.get("employeeNo"))) {
            sb.append(" and te.EMPLOYEE_NO like ?");
            queryParam.add("%" + param.get("employeeNo") + "%");
        }
        if (!StringUtils.isNullOrEmpty(param.get("userCode"))) {
            sb.append(" and tu.USER_CODE like ?");
            queryParam.add("%" + param.get("userCode") + "%");
        }
        if (!StringUtils.isNullOrEmpty(param.get("employeeName"))) {
            sb.append(" and te.EMPLOYEE_NAME like ?");
            queryParam.add("%" + param.get("employeeName") + "%");
        }

        if (!StringUtils.isNullOrEmpty(param.get("workingState"))) {
            if(StringUtils.isEquals(DictCodeConstants.EMPLOYEE_ISJOB, param.get("workingState"))){
                param.put("workingState", DictCodeConstants.STATUS_IS_YES+"");
            }else{
                param.put("workingState", DictCodeConstants.STATUS_IS_NOT+"");
            }
            sb.append(" and te.IS_VALID = ?");
            queryParam.add(Integer.parseInt(param.get("workingState")));
        }
        if (!StringUtils.isNullOrEmpty(param.get("userState"))) {
            sb.append(" and tu.USER_STATUS = ?");
            queryParam.add(Integer.parseInt(param.get("userState")));
        }
        // 执行查询
        PageInfoDto pageResult = DAOUtil.pageQuery(sb.toString(), queryParam);

        return pageResult;

    }

    /**
     * 员工新建账号
     * 
     * @author zhanshiwei
     * @date 2017年1月8日
     * @param param
     * @return
     * @throws ServiceBizException (non-Javadoc)
     * @see com.yonyou.dms.manage.service.basedata.employee.EmployeeService#searchEmplAsUser(java.util.Map)
     */

    @Override
    public PageInfoDto searchEmplAsUser(Map<String, String> param) throws ServiceBizException {
        StringBuilder sb = new StringBuilder("SELECT ").append("te.EMPLOYEE_ID,te.DEALER_CODE,te.EMPLOYEE_NO,te.EMPLOYEE_NAME,te.ORG_CODE").append(" from tm_employee te").append(" where 1=1");
        List<Object> queryParam = new ArrayList<Object>();
        if (!StringUtils.isNullOrEmpty(param.get("employeeNo"))) {
            sb.append(" and te.EMPLOYEE_NO like ?");
            queryParam.add("%" + param.get("employeeNo") + "%");
        }
        sb.append(" and te.IS_VALID=").append(DictCodeConstants.STATUS_IS_YES);
        PageInfoDto pageResult = DAOUtil.pageQuery(sb.toString(), queryParam);

        return pageResult;
    }

    
    /**
    * @author zhanshiwei
    * @date 2017年1月14日
    * @param id
    * @return
    * @throws ServiceBizException
    * (non-Javadoc)
    * @see com.yonyou.dms.manage.service.basedata.employee.EmployeeService#findGFkUserByEmployeeId(java.lang.String)
    */
    	
    @SuppressWarnings("rawtypes")
	@Override
    public Map findGFkUserById(Long id) throws ServiceBizException {

        StringBuilder sb = new StringBuilder("select tus.USER_ID ,tus.user_name,tus.DEALER_CODE,tus.USER_CODE,tus.USER_STATUS,tus.EMPLOYEE_NO,"
                + "tus.PASSWORD,tus.LOGIN_LAST_TIME,tus.IS_SERVICE_ADVISOR,tus.IS_CONSULTANT,tmdo1.ORG_CODE,tmdo1.ORG_NAME from tm_user tus\n")
              .append("LEFT JOIN tm_organization tmdo1 ON tus.ORG_CODE=tmdo1.ORG_CODE and tmdo1.DEALER_CODE=tus.DEALER_CODE  ")
              .append("where 1=1 ");
        List<Object> queryParam = new ArrayList<Object>();
        if (!StringUtils.isNullOrEmpty(id)) {
            sb.append(" and tus.USER_ID = ?");
            queryParam.add(id);
        }
        List<Map> list = DAOUtil.findAll(sb.toString(), queryParam);
        Map map = null;
        if (list.size() > 0) {
            map = list.get(0);
        }
        return map;
    
    }

    @Override
    public PageInfoDto addEmplsel(Map<String, String> param) throws ServiceBizException {
        StringBuilder sb = getSql();
        List<Object> queryParam = new ArrayList<Object>();
        if (StringUtils.isNullOrEmpty(param.get("isValid"))) {
            sb.append(" and tme.IS_VALID = ?");
            queryParam.add(DictCodeConstants.STATUS_IS_YES);
        }
        if (!StringUtils.isNullOrEmpty(param.get("employeeNo"))) {
            sb.append(" and tme.EMPLOYEE_NO like ?");
           // queryParam.add(param.get("employeeNo"));
            queryParam.add("%"+param.get("employeeNo")+"%");
        }
        //是否从用户角色中选择的
        if(!StringUtils.isNullOrEmpty(param.get("isUserRole"))&&StringUtils.isEquals(DictCodeConstants.STATUS_IS_YES, param.get("isUserRole"))){
           // sb.append(" and  not exists( select 1 from tm_user tu where  tu.EMPLOYEE_NO=tme.EMPLOYEE_NO and tu.DEALER_CODE=tme.DEALER_CODE)");
        }
        return DAOUtil.pageQuery(sb.toString(), queryParam);
    }

    @SuppressWarnings("rawtypes")
	@Override
    public List<Map> qryJurSalesConsultant(String menuId) throws ServiceBizException{
		String dealercode = FrameworkUtil.getLoginInfo().getDealerCode();
		Long userid = FrameworkUtil.getLoginInfo().getUserId();
		String orgcode = FrameworkUtil.getLoginInfo().getOrgCode();
        StringBuilder sqlSb = new StringBuilder("select t.USER_ID,t.USER_NAME,t.DEALER_CODE from TM_USER  t  where 1=1");
        List<Object> params = new ArrayList<Object>();
        sqlSb.append(DAOUtilGF.getFunRangeByStr("t", "USER_ID", userid, orgcode, menuId, dealercode));
        System.err.println(sqlSb.toString());
        List<Map> list = DAOUtil.findAll(sqlSb.toString(), params);
        return list;	
    }

	@SuppressWarnings("rawtypes")
	@Override
	public List<Map> queryDecorationSpecialist() throws Exception {
		StringBuffer sql = new StringBuffer();
		String DealerCode = FrameworkUtil.getLoginInfo().getDealerCode();
		 sql.append( " SELECT U.DEALER_CODE,U.USER_ID, U.USER_CODE,U.USER_NAME, U.EMPLOYEE_NO, U.SERIAL_NO, U.SERIAL_TAG, ");
		 sql.append( " U.ORG_CODE, U.PASSWORD, U.USER_STATUS,U.CREATED_AT, U.IS_SERVICE_ADVISOR, ");
		 sql.append( " U.IS_CONSULTANT, E.EMPLOYEE_NAME ");
		 sql.append( " FROM TM_USER U ");
		 sql.append(" left join TM_EMPLOYEE E on E.EMPLOYEE_NO=U.EMPLOYEE_NO and E.DEALER_CODE =U.DEALER_CODE");
		 sql.append( " WHERE  ");
		 sql.append( "   U.DEALER_CODE= '" + DealerCode + "'");
		 sql.append( "  AND (( E.DOWN_TAG = " + Utility.getInt(CommonConstants.DICT_IS_YES));
		 sql.append( " and E.FROM_ENTITY <> '" + DealerCode + "') ");
		 sql.append( " or ( E.DOWN_TAG = " + Utility.getInt(CommonConstants.DICT_IS_NO));
		 sql.append("  and E.FROM_ENTITY IS NULL)) ");
		 List<Object> queryParam = new ArrayList<Object>();
		 System.out.println("*****************");
		 System.out.println(sql.toString());
		 System.out.println("*****************");
		 List<Map> list = DAOUtil.findAll(sql.toString(), queryParam);
		return list;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public List<Map> queryIsEmployee() throws ServiceBizException {
		 StringBuilder sqlSb = new StringBuilder("");
		 sqlSb.append(" select t.USER_ID,t.USER_NAME,t.DEALER_CODE from TM_USER  t LEFT JOIN TM_EMPLOYEE te ON ");
		 sqlSb.append(" t.employee_no=te.employee_no and t.DEALER_CODE=te.DEALER_CODE where t.user_status= '"+DictCodeConstants.DICT_IN_USE_START+"' AND  t.IS_CONSULTANT='"+DictCodeConstants.IS_YES+ "' ");
		
		 List<Object> params = new ArrayList<Object>();
        return DAOUtil.findAll(sqlSb.toString(), params);
	}
	
	  /**
     * 保存时查询是否是一级部门组织
     * 
     * @author jcsi
     * @date 2016年8月1日
     * @param employeeNo
     * @return
     * @throws ServiceBizException
     */
	@SuppressWarnings("rawtypes")
	@Override
	public int queryOrgCode(String id) throws Exception {
		StringBuilder sqlSb = new StringBuilder("select *  from tm_organization  where org_code=? and parent_org_code=''");
		List<Object> params = new ArrayList<Object>();
		params.add(id);
		List<Map> list =DAOUtil.findAll(sqlSb.toString(),params);
		return list.size();
	}
	

	@SuppressWarnings("rawtypes")
	@Override
	public List<Map> queryTrancer() throws ServiceBizException {
		String dealerCode = FrameworkUtil.getLoginInfo().getDealerCode();
		 StringBuilder sqlSb = new StringBuilder("");
			sqlSb.append( " SELECT DISTINCT A.WORKER_TYPE_CODE,C.ORG_CODE,A.DEALER_CODE,A.E_MAIL,A.DOWN_TAG,A.PHONE,A.TECHNICIAN_GRADE,A.EMPLOYEE_NO,A.EMPLOYEE_NAME,A.POSITION_CODE,A.LABOUR_POSITION_CODE,A.IS_VALID,A.BIRTHDAY,A.IS_TAKE_PART,A.IS_SERVICE_ADVISOR, ");
			sqlSb.append( " A.MOBILE,A.WORKGROUP_CODE,A.RESUME,A.TRAINING,A.ZIP_CODE,A.LABOUR_FACTOR,A.IS_TRACER,A.FOUND_DATE,A.IS_TEST_DRIVER,A.GENDER,A.CERTIFICATE_ID,A.IS_TECHNICIAN,A.IS_CHECKER,A.IS_CONSULTANT,A.ADDRESS,A.IS_INSURANCE_ADVISOR,A.IS_MAINTAIN_ADVISOR,B.ORG_NAME AS DEPT_NAME ");
			sqlSb.append( " from TM_EMPLOYEE  A ");
			sqlSb.append( " left join tm_organization b on  a.DEALER_CODE = b.DEALER_CODE AND a.ORG_CODE = b.ORG_CODE ");
			sqlSb.append( " left join TM_USER C on  a.DEALER_CODE = C.DEALER_CODE AND a.EMPLOYEE_NO = C.EMPLOYEE_NO AND C.USER_STATUS =12781001 ");
			sqlSb.append( " WHERE A.IS_VALID = 12781001 " );
			sqlSb.append(" AND (( DOWN_TAG = 12781001 and is_tracer = 12781001 " );
			sqlSb.append( " and FROM_ENTITY <> '"+ dealerCode+ "') ");
			sqlSb.append( " or ( DOWN_TAG = 12781002 and is_tracer = 12781001 ");
			sqlSb.append( "  and A.FROM_ENTITY IS NULL)) ");
			
		 List<Object> params = new ArrayList<Object>();
        return DAOUtil.findAll(sqlSb.toString(), params);
	}
    
	/**
	 * 查询装潢专员
	 * @author Benzc
	 * @date 2017年5月12日
	 */
	@SuppressWarnings("rawtypes")
	@Override
	public List<Map> queryDecorationWorker() throws Exception {
		StringBuffer sb = new StringBuffer();
		sb.append("SELECT DEALER_CODE,EMPLOYEE_NAME,EMPLOYEE_NO FROM Tm_employee WHERE IS_UPHOLSTER_ADVISOR = ?");
		List<Object> queryParam = new ArrayList<Object>();
		queryParam.add(DictCodeConstants.STATUS_IS_YES);
		List<Map> list = DAOUtil.findAll(sb.toString(), queryParam);
		return list;
	}
	
    
}
