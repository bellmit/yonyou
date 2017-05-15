
/** 
*Copyright 2016 Yonyou Corporation Ltd. All Rights Reserved.
* This software is published under the terms of the Yonyou Software
* License version 1.0, a copy of which has been included with this
* distribution in the LICENSE.txt file.
*
* @Project Name : dms.manage
*
* @File name : OrganizatioServiceImpl.java
*
* @Author : rongzoujie
*
* @Date : 2016年7月6日
*
----------------------------------------------------------------------------------
*     Date       Who       Version     Comments
* 1. 2016年7月6日    rongzoujie    1.0
*
*
*
*
----------------------------------------------------------------------------------
*/
package com.yonyou.dms.manage.service.basedata.org;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yonyou.dms.common.domains.PO.basedata.OrganizationPO;
import com.yonyou.dms.common.service.monitor.OperateLogService;
import com.yonyou.dms.framework.DAO.DAOUtil;
import com.yonyou.dms.framework.util.FrameworkUtil;
import com.yonyou.dms.function.common.DictCodeConstants;
import com.yonyou.dms.function.exception.ServiceBizException;
import com.yonyou.dms.function.utils.common.StringUtils;
import com.yonyou.dms.manage.domains.DTO.basedata.OrganizationDto;

/**
 * 部门
* @author rongzoujie
* @date 2016年9月6日
 */
@Service
public class OrganizatioServiceImpl implements OrganizationService {
	private static final Logger logger = LoggerFactory.getLogger(OrganizatioServiceImpl.class);
    @Autowired
    private OperateLogService operateLogService;
    
    /**
     * 查询所有部门
    * @author rongzoujie
    * @date 2016年8月3日
    * @return
    * @throws ServiceBizException
    * (non-Javadoc)
    * @see com.yonyou.dms.manage.service.basedata.org.OrganizationService#getOrganization()
     */
	@SuppressWarnings("rawtypes")
	@Override
    public List<Map> getOrganization() throws ServiceBizException{
		
        List<Object> queryParams = new ArrayList<>();
        StringBuilder sqlsb = new StringBuilder("SELECT DEALER_CODE,ORGDEPT_ID,ORG_CODE,ORG_NAME,PARENT_ORG_CODE FROM tm_organization where 1=1");
        sqlsb.append(" and DEALER_CODE = ?");
        queryParams.add(FrameworkUtil.getLoginInfo().getDealerCode());
        List<Map> result = DAOUtil.findAll(sqlsb.toString(),queryParams);
        return result;
    }

    /**
     * 更具code查找部门
    * @author rongzoujie
    * @date 2016年8月3日
    * @param orgCode
    * @return
    * @throws ServiceBizException
    * (non-Javadoc)
    * @see com.yonyou.dms.manage.service.basedata.org.OrganizationService#getOrgByCode(java.lang.String)
     */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
    public Map<String,Object> getOrgByCode(String orgCode) throws ServiceBizException{
        StringBuilder sql = new StringBuilder("SELECT ORGDEPT_ID,DEALER_CODE,PARENT_ORG_CODE,ORG_CODE,ORG_NAME,ORG_TYPE,ORG_SHORT_NAME,ORG_DESC,IS_VALID FROM tm_organization where 1=1");
        List<Object> queryParams = new ArrayList<>();
        if(!StringUtils.isNullOrEmpty(orgCode)){
            sql.append(" and ORG_CODE = ?");
            queryParams.add(orgCode);
        }
        sql.append(" and DEALER_CODE = ?");
        queryParams.add(FrameworkUtil.getLoginInfo().getDealerCode());
        Map orgMap = DAOUtil.findFirst(sql.toString(), queryParams);
//        sql = new StringBuilder("SELECT ORG_CODE,DEALER_CODE FROM tm_organization WHERE PARENT_ORG_CODE = ? ORDER BY ORG_CODE DESC LIMIT 0,1");
//        System.err.println(sql+"&&&&&&&&/&&");
//        queryParams = new ArrayList<>();
//        queryParams.add(orgMap.get("PARENT_ORG_CODE"));
//        Map newMap = DAOUtil.findFirst(sql.toString(), queryParams);
//        orgMap.put("newCode", newMap.get("PARENT_ORG_CODE"));
        return orgMap;
    }
    
    /**
     *
     * 查询添加组织名称是否重复
    * @author rongzoujie
    * @date 2016年10月18日
    * @param orgName
    * @return
     */
    public boolean checkOrgNameDouble(String orgName){
        StringBuilder orgNameSql = new StringBuilder("select DEALER_CODE,ORG_NAME from TM_ORGANIZATION where 1=1 ");
        orgNameSql.append("and ORG_NAME = ?");
        List<Object> orgNameParam = new ArrayList<>();
        orgNameParam.add(orgName);
        
        @SuppressWarnings("rawtypes")
		List<Map> orgResult = DAOUtil.findAll(orgNameSql.toString(),orgNameParam);
        if(orgResult.size()<=0){
            return true;
        }
        return false;
    }

    /**
     * 添加部门
    * @author rongzoujie
    * @date 2016年8月3日
    * @param orgDto
    * @return
    * @throws ServiceBizException
    * (non-Javadoc)
    * @see com.yonyou.dms.manage.service.basedata.org.OrganizationService#addOrg(com.yonyou.dms.manage.domains.DTO.basedata.OrganizationDto)
     */
	@SuppressWarnings("unused")
	@Override
    public String addOrg(OrganizationDto orgDto) throws ServiceBizException {
        
        String orgName = orgDto.getOrgName();
        /*if(!checkOrgNameDouble(orgName)){
            throw new ServiceBizException("该组织名称已经存在");
        }*/
        
        if(StringUtils.isNullOrEmpty(orgDto.getIsValid()!=null&&orgDto.getIsValid()==DictCodeConstants.STATUS_NOT_VALID)){
            checkIsValid(orgDto.getOrgCode());
        }
        
        if(!StringUtils.isNullOrEmpty(orgDto.getOrgCode())){
           // StringBuilder sql = new StringBuilder("SELECT max(org_code) as maxorg,DEALER_CODE FROM tm_organization where org_parent_code= ?");
            StringBuilder sql = new StringBuilder("SELECT max(org_code) as maxorg,DEALER_CODE FROM tm_organization where 1=1");
            List<Object> queryParams = new ArrayList<>();
            sql.append(" and PARENT_ORG_CODE = ?");
            queryParams.add(orgDto.getParentOrgCode());
            sql.append(" and DEALER_CODE = ?");
            queryParams.add(FrameworkUtil.getLoginInfo().getDealerCode());
            @SuppressWarnings("rawtypes")
			List<Map> maxOrg = DAOUtil.findAll(sql.toString(), queryParams);
            String orgCode=orgDto.getOrgCode();//定义新增部门的组织代码
            String orgpartentCode=orgDto.getParentOrgCode();//定义父类组织代码
            
            if (orgpartentCode.equals(orgDto.getOrgCode())) {
                 orgCode = orgDto.getParentOrgCode() + "01";
			}else {
				orgpartentCode=orgDto.getParentOrgCode();
			}
            
			if(maxOrg.size()>0){
                long orgCodeNum = Long.parseLong((String)maxOrg.get(0).get("maxorg"))+1;
                orgCode = String.valueOf(orgCodeNum);
            }
            orgDto.setOrgCode(orgCode);
        }
        
        String orgCode = orgDto.getOrgCode();
        String parentCode=orgDto.getParentOrgCode();
        System.err.println(parentCode);

        if(checkOrgCodeExists(orgCode)){
            OrganizationPO orgPO=new OrganizationPO();
              setOrg(orgPO,orgDto);
              orgPO.saveIt();
        }else{
            throw new ServiceBizException("该部门编码已经存在,请仔细检查重新输入");
        }
        return orgDto.getOrgCode();
    }
    
    /**
     *
     * 效验该部门下的部门是否有有效的部门
    * @author rongzoujie
    * @date 2016年11月14日
    * @param orgCode
     */
    @SuppressWarnings("rawtypes")
	public void checkIsValid(String orgCode){
        List<Object> param = new ArrayList<>();
        StringBuilder sql = new StringBuilder("SELECT DEALER_CODE,ORG_NAME FROM tm_organization WHERE 1 = 1");
        sql.append(" AND ORG_CODE like ?");
        param.add(orgCode + "%");
        sql.append(" AND IS_VALID = ?");
        param.add(DictCodeConstants.STATUS_IS_VALID);
        sql.append(" AND ORG_CODE != ?");
        param.add(orgCode);
        sql.append(" AND DEALER_CODE = ?");
        param.add(FrameworkUtil.getLoginInfo().getDealerCode());
        logger.info(sql+"**************");
        @SuppressWarnings("unused")
		boolean validFlag = false;
        List<Map> result = DAOUtil.findAll(sql.toString(), param);
        if(result.size()>0){
            throw new ServiceBizException("部门下存在有效的部门不能设置无效");
        }
    }
    
    /**
     *检查orgCode是否已经存在 
    * @author rongzoujie
    * @date 2016年11月14日
    * @param orgCode
    * @return
     */
    public boolean checkOrgCodeExists(String orgCode){
        StringBuilder sql = new StringBuilder("SELECT ORG_CODE,DEALER_CODE FROM tm_organization WHERE 1=1");
        List<Object> queryParams = new ArrayList<>();
        sql.append(" AND ORG_CODE = ?");
        queryParams.add(orgCode);
        sql.append(" and DEALER_CODE = ?");
        queryParams.add(FrameworkUtil.getLoginInfo().getDealerCode());
        @SuppressWarnings("rawtypes")
		List<Map> map = DAOUtil.findAll(sql.toString(),queryParams);
        if(map.size()==0){
            return true;
        }
        return false;
    }
    
    public void setOrg(OrganizationPO orgPo,OrganizationDto orgDto){
       /* orgPo.setString("PARENT_ORG_CODE",orgDto.getParentOrgCode());
        orgPo.setString("DEALER_CODE",FrameworkUtil.getLoginInfo().getDealerCode());
        orgPo.setString("ORG_NAME", orgDto.getOrgName());
        orgPo.setString("ORG_SHORT_NAME", orgDto.getorgShortName());
        orgPo.setString("ORG_DESC", orgDto.getOrgDesc());
        orgPo.setString("ORG_CODE", orgDto.getOrgCode());
        orgPo.setInteger("ORG_TYPE", orgDto.getOrgType());
        orgPo.setInteger("IS_VALID", orgDto.getIsValid());*/
    	
        orgPo.setString("DEALER_CODE",FrameworkUtil.getLoginInfo().getDealerCode());
    	orgPo.setString("PARENT_ORG_CODE", orgDto.getParentOrgCode());
    	orgPo.setString("ORG_NAME",orgDto.getOrgName());
    	orgPo.setString("ORG_CODE", orgDto.getOrgCode());
    	orgPo.setString("ORG_TYPE", orgDto.getOrgType());
    	orgPo.setString("ORG_SHORT_NAME", orgDto.getorgShortName());
    	orgPo.setString("ORG_DESC", orgDto.getOrgDesc());
    	orgPo.setInteger("IS_VALID", orgDto.getIsValid());
    	orgPo.setInteger("VER", orgDto.getVer());
        System.err.println(orgDto.getParentOrgCode()+" "+ orgDto.getOrgName() +" "+orgDto.getorgShortName() + " "+ orgDto.getOrgDesc()
        +" "+orgDto.getOrgCode()+" " +orgDto.getOrgType() +" "+orgDto.getIsValid()+" "+orgDto.getVer());
        
    }

    /**
     * 更新部门
    * @author rongzoujie
    * @date 2016年8月3日
    * @param id
    * @param orgDto
    * @throws ServiceBizException
    * (non-Javadoc)
    * @see com.yonyou.dms.manage.service.basedata.org.OrganizationService#modifyOrg(java.lang.Integer, com.yonyou.dms.manage.domains.DTO.basedata.OrganizationDto)
     */
    @Override
    public void modifyOrg(Integer id, OrganizationDto orgDto) throws ServiceBizException {
//        checkOrgNameDup(orgDto.getOrgName(),id);
        if(StringUtils.isNullOrEmpty(orgDto.getIsValid()!=null&&orgDto.getIsValid()==DictCodeConstants.STATUS_NOT_VALID)){
            checkIsValid(orgDto.getOrgCode());
        }
        if(orgDto.getParentOrgCode().equals(orgDto.getOrgCode())){
            orgDto.setParentOrgCode("");
        }
        OrganizationPO orgPo=OrganizationPO.findById(id);
        setOrg(orgPo,orgDto);
        System.err.println(orgPo+" "+orgDto+" ^^^^^^^^^^^^^^ ");
        orgPo.saveIt();
    }
    
    /**
    * 更新组织时判断是否和其他组织名重名 
    * @author rongzoujie
    * @date 2016年11月11日
    * @param orgName
    * @return
     */
   /* private void checkOrgNameDup(String orgName,Integer orgId){
        List<Object> param = new ArrayList<>();
        StringBuilder sql = new StringBuilder("select ORGDEPT_ID,DEALER_CODE,ORG_NAME from TM_ORGANIZATION where 1=1");
        sql.append(" and ORG_NAME = ?");
        param.add(orgName);
        sql.append(" and ORGDEPT_ID != ?");
        param.add(orgId);
        System.err.println();
        @SuppressWarnings("rawtypes")
		List<Map> result = DAOUtil.findAll(sql.toString(), param);
        if(result.size() > 0){
            throw new ServiceBizException("该组织名称已经存在");
        }
    }*/

    /**
    * 删除部门
    * @author rongzoujie
    * @date 2016年8月3日
    * @param id
    * @throws ServiceBizException
    * (non-Javadoc)
    * @see com.yonyou.dms.manage.service.basedata.org.OrganizationService#deleteOrgById(java.lang.Integer)
     */
    @Override
    public void deleteOrgById(Long id) throws ServiceBizException {
    	
        OrganizationPO orgPo = OrganizationPO.findById(id);
        String orgCode = (String)orgPo.getString("ORG_CODE").toString();
        boolean child = checkChild(orgCode);
        
        if(!checkEmployee(id)){
            throw new ServiceBizException("该部门下有员工不能删除");
        }
        
        if(child){
            orgPo.deleteCascadeShallow();
            //记录删除日志
            operateLogService.recordOperateLog("删除组织：组织代码 【"+orgCode+"】",DictCodeConstants.LOG_SYSTEM_MANAGEMENT);
        }else{
            throw new ServiceBizException("该部门下有子部门不能删除");
        }
    }
    
    @SuppressWarnings("rawtypes")
	public boolean checkEmployee(Long id){
      //1：更具id查出org_code
        StringBuilder sqlOrg = new StringBuilder("select DEALER_CODE,ORG_CODE from tm_organization where 1=1");
        sqlOrg.append(" and ORGDEPT_ID = ?");
        List<Object> orgParams = new ArrayList<>();
        orgParams.add(id);
		Map orgResult = DAOUtil.findFirst(sqlOrg.toString(), orgParams);
        String orgCode = orgResult.get("ORG_CODE").toString();
        
        //2: 更具org_code查出部门下是否有员工
        StringBuilder sqlEmployee = new StringBuilder("select DEALER_CODE,EMPLOYEE_ID from tm_employee where 1=1");
        sqlEmployee.append(" and org_code = ?");
        List<Object> empParams = new ArrayList<>();
        empParams.add(orgCode);
        List<Map> empResult = DAOUtil.findAll(sqlEmployee.toString(), empParams);
        if(empResult.size()>0){
            return false;
        }else{
            return true;
        }
    }
    
    /**
     * 检查是否有字节点
    * @author rongzoujie
    * @date 2016年8月3日
    * @param orgCode
    * @return
    * @throws ServiceBizException
    * (non-Javadoc)
    * @see com.yonyou.dms.manage.service.basedata.org.OrganizationService#checkChild(java.lang.String)
     */
    public boolean checkChild(String orgCode) throws ServiceBizException{
        StringBuilder sql = new StringBuilder("SELECT ORG_CODE,DEALER_CODE,ORG_NAME FROM tm_organization WHERE 1=1");
        List<Object> queryParams = new ArrayList<>();
        sql.append(" AND PARENT_ORG_CODE = ?");
        queryParams.add(orgCode);
        @SuppressWarnings("rawtypes")
		List<Map> map = DAOUtil.findAll(sql.toString(),queryParams);
        if(map.size()==0){
            return true;
        }
        return false;
    }

    /**
     * 获取上级组织
    * @author rongzoujie
    * @date 2016年8月3日
    * @return
    * @throws ServiceBizException
    * (non-Javadoc)
    * @see com.yonyou.dms.manage.service.basedata.org.OrganizationService#getParent()
     */
    @SuppressWarnings("rawtypes")
	@Override
    public List<Map> getParents() throws ServiceBizException {
        StringBuilder sql = new StringBuilder("SELECT ORG_CODE,DEALER_CODE,ORG_NAME,ORG_TYPE FROM tm_organization where DEALER_CODE = ? ORDER BY ORGDEPT_ID");
        List<Object> queryParams = new ArrayList<>();
        queryParams.add(FrameworkUtil.getLoginInfo().getDealerCode());
//		queryParams.add(orgDto.getOrgCode());
        List<Map> result = DAOUtil.findAll(sql.toString(),queryParams);
        return result;
    }

    /**
     * 根据员工编号 查询部门信息
    * @author jcsi
    * @date 2016年8月16日
    * @param employeeCode
    * @return
    * (non-Javadoc)
    * @see com.yonyou.dms.manage.service.basedata.org.OrganizationService#findByOrgCode(java.lang.String)
     */
    @SuppressWarnings("rawtypes")
	@Override
    public Map findByOrgCode(String employeeCode) {
        String str="SELECT t.DEALER_CODE,t.ORG_CODE,t.ORG_NAME FROM TM_ORGANIZATION t INNER JOIN tm_employee e on t.ORG_CODE=e.ORG_CODE where e.EMPLOYEE_NO=? and t.DEALER_CODE=? ";
        List<Object> param=new ArrayList<>();
        param.add(employeeCode);
        param.add(FrameworkUtil.getLoginInfo().getDealerCode());
        return DAOUtil.findFirst(str, param);
    }

    /**
     * 
    * @author rongzoujie
    * @date 2016年11月14日
    * @return
    * @throws ServiceBizException
    * (non-Javadoc)
    * @see com.yonyou.dms.manage.service.basedata.org.OrganizationService#getIsValidOrganization()
     */
    @SuppressWarnings("rawtypes")
	@Override
    public List<Map> getIsValidOrganization() throws ServiceBizException {
        List<Object> queryParams = new ArrayList<>();
        StringBuilder sqlsb = new StringBuilder("SELECT DEALER_CODE,ORGDEPT_ID,ORG_CODE,ORG_NAME,PARENT_ORG_CODE FROM tm_organization where 1=1 ");
        sqlsb.append(" and DEALER_CODE = ?");
        queryParams.add(FrameworkUtil.getLoginInfo().getDealerCode());
        List<Map> result = DAOUtil.findAll(sqlsb.toString(),queryParams);
        return result;
    }

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public List<Map> qrySalesOrdersDetial(Map<String, String> queryParam, String parentCode)
			throws ServiceBizException {

	            StringBuilder sql = new StringBuilder("SELECT PARENT_ORG_CODE,(CASE WHEN  MAX(org_code) IS NULL THEN 0 ELSE MAX(org_code) END ) AS maxorg,org_type,DEALER_CODE FROM tm_organization where 1=1");
	            System.out.println(sql.toString());
	            List<Object> queryParams = new ArrayList<>();
	            sql.append(" and PARENT_ORG_CODE = ?");
	            queryParams.add(parentCode);
	            sql.append(" and DEALER_CODE = ?");
	            queryParams.add(FrameworkUtil.getLoginInfo().getDealerCode());
	            @SuppressWarnings("rawtypes")
				List<Map> maxOrg = DAOUtil.findAll(sql.toString(), queryParams);
	            String maxorg = "";
	            
	            if(maxOrg.size()>0){
	               maxorg = maxOrg.get(0).get("maxorg").toString();
	               int max = Integer.parseInt(maxorg);
	               maxorg = String.valueOf(max+1);
	            }else{
	               parentCode = parentCode + "01";
	               maxorg = parentCode;
	            }
	            
	            //检查组织代码是否合法
	            checkIsValid(maxorg);
				
	           
	            List<Map> list = new ArrayList<Map>();
	            Map map = new HashMap();
	            list.add(map);
	            map.put("maxorg", maxorg);
	        
		   return list ;
	}
}
