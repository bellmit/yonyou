
/** 
*Copyright 2016 Yonyou Corporation Ltd. All Rights Reserved.
* This software is published under the terms of the Yonyou Software
* License version 1.0, a copy of which has been included with this
* distribution in the LICENSE.txt file.
*
* @Project Name : dms.manage
*
* @File name : UserServiceImpl.java
*
* @Author : yll
*
* @Date : 2016年8月15日
*
----------------------------------------------------------------------------------
*     Date       Who       Version     Comments
* 1. 2016年8月15日    yll    1.0
*
*
*
*
----------------------------------------------------------------------------------
*/

package com.yonyou.dms.manage.service.basedata.employee;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.javalite.activejdbc.Base;
import org.springframework.stereotype.Service;

import com.yonyou.dms.common.domains.PO.basedata.UserPO;
import com.yonyou.dms.framework.DAO.DAOUtil;
import com.yonyou.dms.framework.domain.LoginInfoDto;
import com.yonyou.dms.framework.util.FrameworkUtil;
import com.yonyou.dms.framework.util.bean.ApplicationContextHelper;
import com.yonyou.dms.function.exception.ServiceBizException;
import com.yonyou.dms.function.utils.common.CommonUtils;
import com.yonyou.dms.function.utils.common.StringUtils;
import com.yonyou.dms.manage.domains.DTO.basedata.CommonTreeDto;
import com.yonyou.dms.manage.domains.DTO.basedata.CommonTreeStateDto;
import com.yonyou.dms.manage.domains.DTO.basedata.user.UserCopyDto;
import com.yonyou.dms.manage.domains.DTO.basedata.user.UserDto;
import com.yonyou.dms.manage.domains.DTO.basedata.user.UserRoleDTO;
import com.yonyou.dms.manage.domains.PO.basedata.user.UserCtrlPO;
import com.yonyou.dms.manage.domains.PO.basedata.user.UserMenuActionPO;
import com.yonyou.dms.manage.domains.PO.basedata.user.UserMenuPO;
import com.yonyou.dms.manage.domains.PO.basedata.user.UserMenuRangePO;
import com.yonyou.dms.manage.domains.PO.basedata.user.UserRolePO;
import com.yonyou.dms.manage.service.basedata.user.UserService;

/**
 * 用户接口的实现类
 * 
 * @author yll
 * @date 2016年8月15日
 */
@Service
public class EmployeeUserServiceImpl implements UserService {

    /**
     * 修改用户信息
     * 
     * @author yll
     * @date 2016年8月30日
     * @param id
     * @param userDto
     * @throws ServiceBizException (non-Javadoc)
     * @see com.yonyou.dms.manage.service.basedata.user.UserService#modifyUser(java.lang.Long,
     * com.yonyou.dms.manage.domains.DTO.basedata.user.UserDto)
     */
    @Override
    public void modifyUser(Long id, UserDto userDto) throws ServiceBizException {
        System.out.println("2222222222222222222");
        UserPO userPO =UserPO.findByCompositeKeys(FrameworkUtil.getLoginInfo().getDealerCode(),id);
        System.out.println("22222222222222222223");
        setUser(userPO, userDto);
        userPO.saveIt();

    }

    /**
     * 添加用户
     * 
     * @author yll
     * @date 2016年8月30日
     * @param userDto
     * @return
     * @throws ServiceBizException (non-Javadoc)
     * @see com.yonyou.dms.manage.service.basedata.user.UserService#addUser(com.yonyou.dms.manage.domains.DTO.basedata.user.UserDto)
     */
    @Override
    public UserPO addUser(UserDto userDto) throws ServiceBizException {
        String userCode = userDto.getUserCode();
        if (StringUtils.isNullOrEmpty(userDto.getEmployeeId())) {
            throw new ServiceBizException("员工id不能为空");
        }
        if (StringUtils.isNullOrEmpty(userDto.getUserCode())) {
            throw new ServiceBizException("用户账号不能为空");
        }
        if (StringUtils.isNullOrEmpty(userDto.getUserStatus())) {
            throw new ServiceBizException("用户状态");
        }
        if (StringUtils.isNullOrEmpty(userDto.getPassword())) {
            throw new ServiceBizException("用户密码不能为空");
        }

        if (SearchUserCode(userCode)) {
            UserPO userPO = new UserPO();
            setUser(userPO, userDto);
            userPO.saveIt();
            return userPO;
        } else {
            throw new ServiceBizException("用户代码已存在");
        }

    }

    /**
     * 加载树状菜单的基础数据
     * 
     * @author yll
     * @date 2016年8月30日
     * @return
     * @throws ServiceBizException (non-Javadoc)
     * @see com.yonyou.dms.manage.service.basedata.user.UserService#queryMenu()
     */
    @Override
    public List<Map> queryMenu() throws ServiceBizException {
        StringBuilder sqlSb = new StringBuilder("select MENU_ID,MENU_NAME,MENU_URL,MENU_ICON,PARENT_ID,MENU_TYPE,MENU_DESC from tc_menu where 1=1 and menu_status=10011001 ");
        List<String> params = new ArrayList<String>();
        List<Map> list = Base.findAll(sqlSb.toString(), params.toArray());
        return list;
    }

    /**
     * 设置用户
     * 
     * @author yll
     * @date 2016年8月30日
     * @param userPO
     * @param userDto
     */
    private void setUser(UserPO userPO, UserDto userDto) {
        userPO.setString("USER_CODE", userDto.getUserCode());
        if (!StringUtils.isNullOrEmpty(userDto.getPassword())) {
            userPO.setString("PASSWORD", userDto.getPassword());
        }
        if (StringUtils.isNullOrEmpty(userDto.getIsModPassword())) {
            userPO.setString("EMPLOYEE_NO", userDto.getEmployeeNo());
            userPO.setString("IS_SERVICE_ADVISOR", userDto.getIsServiceAdvisor());
            userPO.setString("IS_CONSULTANT", userDto.getIsConsultant());
            userPO.setString("ORG_CODE", userDto.getOrgCode());
            userPO.setInteger("USER_STATUS", userDto.getUserStatus());
        }
        userPO.setString("USER_NAME", userDto.getUserName());
    }

    /**
     * 根据id获取用户
     * 
     * @author yll
     * @date 2016年8月30日
     * @param id
     * @return
     * @throws ServiceBizException (non-Javadoc)
     * @see com.yonyou.dms.manage.service.basedata.user.UserService#getUserById(java.lang.Long)
     */
    @Override
    public UserPO getUserById(Long id) throws ServiceBizException {
        return UserPO.findByCompositeKeys(FrameworkUtil.getLoginInfo().getDealerCode(),id);
    }

    /**
     * 根据员工id获取用户id
     * 
     * @author yll
     * @date 2016年8月30日
     * @param id
     * @return
     * @throws ServiceBizException (non-Javadoc)
     * @see com.yonyou.dms.manage.service.basedata.user.UserService#getUserIDByEmployeeId(java.lang.Long)
     */
    @Override
    public Long getUserIDByEmployeeId(String id) throws ServiceBizException {
        Long userId = null;
        StringBuilder sqlSb = new StringBuilder("select DEALER_CODE,USER_ID  from tm_user  where 1=1 and EMPLOYEE_NO = ?");
        List<Object> queryParams = new ArrayList<Object>();
        queryParams.add(id);
        List<Map> list = DAOUtil.findAll(sqlSb.toString(), queryParams);
        if (list.size() > 0) {

            userId = Long.valueOf("" + list.get(0).get("USER_ID"));
        }
        if (userId == null) {
            return null;
        } else {
            return userId;
        }

    }

    /**
     * 根据用户code查找员工id
     * 
     * @author yll
     * @date 2016年8月30日
     * @param userCode
     * @return
     * @throws ServiceBizException (non-Javadoc)
     * @see com.yonyou.dms.manage.service.basedata.user.UserService#getEmployeeIdByUserCode(java.lang.String)
     */
    @Override
    public String getEmployeeIdByUserCode(String userCode) throws ServiceBizException {
        String employeeId = null;
        StringBuilder sqlSb = new StringBuilder("select DEALER_CODE,EMPLOYEE_NO  from tm_user  where 1=1 and USER_CODE = ?");
        List<Object> queryParams = new ArrayList<Object>();
        queryParams.add(userCode);
        List<Map> list = DAOUtil.findAll(sqlSb.toString(), queryParams);
        if (list.size() > 0) {

            employeeId = (String) list.get(0).get("EMPLOYEE_NO");
        }
        if (employeeId == null) {
            return null;
        } else {
            return employeeId;
        }
    }

    @Override
    public String getPasswordByUserCode(String userCode) throws ServiceBizException {
        String psaaword = null;
        StringBuilder sqlSb = new StringBuilder("select DEALER_CODE,PASSWORD  from tm_user  where 1=1 and USER_CODE = ?");
        List<Object> queryParams = new ArrayList<Object>();
        queryParams.add(userCode);
        List<Map> list = DAOUtil.findAll(sqlSb.toString(), queryParams);
        if (list.size() > 0) {

            psaaword = (String) list.get(0).get("PASSWORD");
        }
        if (psaaword == null) {
            return null;
        } else {
            return psaaword;
        }
    }

    /**
     * 查找用户名是否存在
     * 
     * @author yll
     * @date 2016年10月27日
     * @param brandCode
     * @param brandName
     * @return
     */
    private boolean SearchUserCode(String userCode) {
        StringBuilder sqlSb = new StringBuilder("select USER_ID,DEALER_CODE from tm_user where 1=1");
        List<Object> params = new ArrayList<Object>();
        sqlSb.append(" and USER_CODE = ?");
        params.add(userCode);
        List<Map> map = DAOUtil.findAll(sqlSb.toString(), params);
        if (map.size() == 0) {
            return true;
        }
        return false;
    }

    /**
     * 用户角色对应关系
     * 
     * @author zhanshiwei
     * @date 2017年1月9日
     * @param useRoleDto
     * @return
     * @throws ServiceBizException (non-Javadoc)
     * @see com.yonyou.dms.manage.service.basedata.user.UserService#addUseRole(com.yonyou.dms.manage.domains.DTO.basedata.user.UserRoleDTO)
     */

    @Override
    public Long addUseRole(UserRoleDTO useRoleDto) throws ServiceBizException {
        UserRolePO useRole = new UserRolePO();
        useRole.setLong("USER_ID", useRoleDto.getUserId());
        useRole.setString("ROLE_CODE", useRoleDto.getRoleCode());
        useRole.saveIt();
        return null;
    }

    /**
     * 得到用户菜单
     * 
     * @author zhanshiwei
     * @date 2017年1月9日
     * @param userId
     * @return
     * @throws ServiceBizException
     */

    @SuppressWarnings("rawtypes")
    @Override
    public List<CommonTreeDto> queryUserMenu(Long userId) throws ServiceBizException {
        StringBuffer sb = new StringBuffer("select tcm.MENU_ID,tcm.MENU_NAME,tcm.MENU_URL,tcm.MENU_ICON,tcm.PARENT_ID,tcm.MENU_TYPE,tcm.MENU_DESC \n");
        sb.append(" ,tmu.USER_MENU_ID\n");
        sb.append("from tc_menu tcm\n");
        sb.append("left JOIN tm_user_menu tmu on tcm.MENU_ID=tmu.MENU_ID and tmu.USER_ID=").append(userId).append("\n");
        sb.append("where 1=1 and tcm.menu_status=10011001\n");
        System.err.println("11111:     "+sb.toString());
        
        List<String> params = new ArrayList<String>();
        List<Map> list = Base.findAll(sb.toString(), params.toArray());
        Iterator<Map> it = list.iterator();
        List<CommonTreeDto> orgList = new ArrayList<CommonTreeDto>();
        while (it.hasNext()) {
            CommonTreeDto orgTreeOrg = new CommonTreeDto();
            CommonTreeStateDto CommonTreeStateDto = new CommonTreeStateDto();
            Map menuMap = it.next();
            orgTreeOrg.setId(menuMap.get("MENU_ID").toString());
            String parent = menuMap.get("PARENT_ID").toString();
            if ("0".equals(parent)) {
                parent = "#";
            }
            if(!StringUtils.isNullOrEmpty(menuMap.get("MENU_TYPE")) && !StringUtils.isNullOrEmpty(menuMap.get("MENU_ID"))){
                if(StringUtils.isEquals("1003", menuMap.get("MENU_TYPE").toString())||StringUtils.isEquals("1", menuMap.get("MENU_ID").toString())){
                    if (!StringUtils.isNullOrEmpty(menuMap.get("USER_MENU_ID"))) {
                        CommonTreeStateDto.setChecked(true);
                    } else {
                        CommonTreeStateDto.setChecked(false);
                    } 
                }
            }

            orgTreeOrg.setParent(parent);
            orgTreeOrg.setText(menuMap.get("MENU_NAME").toString());
            orgTreeOrg.setState(CommonTreeStateDto);
            orgList.add(orgTreeOrg);
        }
        return orgList;
    }

    @Override
    public Map<String, Object> findGFkUserCheById(String id) throws ServiceBizException {
        StringBuffer sb = new StringBuffer("SELECT EMPLOYEE_NO,DEALER_CODE from tm_user where EMPLOYEE_NO=?");
        List<String> params = new ArrayList<String>();
        params.add(id);
        List<Map> map = DAOUtil.findAll(sb.toString(), params);
        Map<String, Object> user=new HashMap<String, Object>();
        if (!CommonUtils.isNullOrEmpty(map)&&map.size()>0) {
            user=new HashMap<String, Object>();
            user.put("success", "false");
        }else{
            user.put("success", "true");
        }
        return user;
    }

    
    /**
    * 
    * @author zhanshiwei
    * @date 2017年1月16日
    * @param id
    * @param org
    * @return
    * @throws ServiceBizException
    */
    	
    @Override
    public void usercheckEmpNo(String id, String org,String userName,String userCode,Long userId) throws ServiceBizException {
        StringBuffer sb = new StringBuffer("SELECT EMPLOYEE_NO,DEALER_CODE,USER_NAME,ORG_CODE,USER_CODE from tm_user where EMPLOYEE_NO=? and (org_code=? OR USER_NAME=? or USER_CODE=?) ");
        List<Object> params = new ArrayList<Object>();
        params.add(id);
        params.add(org);
        params.add(userName);
        params.add(userCode);
        if(!StringUtils.isNullOrEmpty(userId)){
            sb.append(" and USER_ID<>?");
            params.add(userId);
        }
        List<Map> map = DAOUtil.findAll(sb.toString(), params);
        if(!CommonUtils.isNullOrEmpty(map)&&map.size()>0){
            for(int i=0;i<map.size();i++){
                if(!StringUtils.isNullOrEmpty(map.get(i).get("ORG_CODE"))&&StringUtils.isEquals(map.get(i).get("ORG_CODE"), org)){
                    throw new ServiceBizException("组织名称已存在");
                }else if(!StringUtils.isNullOrEmpty(map.get(i).get("USER_CODE"))&&StringUtils.isEquals(map.get(i).get("USER_CODE"), userCode)){
                    throw new ServiceBizException("用户代码已存在");
                }else if(!StringUtils.isNullOrEmpty(map.get(i).get("USER_NAME"))&&StringUtils.isEquals(map.get(i).get("USER_NAME"), userName)){
                    throw new ServiceBizException("用户名已存在");
                }
            }
            
        }
    }

    
    /**
    * @author zhanshiwei
    * @date 2017年1月17日
    * @param userCopyDto
    * @return
    * @throws ServiceBizException
    * (non-Javadoc)
    * @see com.yonyou.dms.manage.service.basedata.user.UserService#copUserPermission(com.yonyou.dms.manage.domains.DTO.basedata.user.UserCopyDto)
    */
    	
    @Override
    public Map<String, Object> copUserPermission(UserCopyDto userCopyDto) throws ServiceBizException {
        
        return null;
    }
    
    /**
     * 查询该员工编号是否有大客户架构权限
     * 
     * @author jcsi
     * @date 2016年8月1日
     * @param employeeNo
     * @return
     * @throws ServiceBizException
     */
	@Override
	public List<Map> QueryBigCustomer(Long id) throws Exception {
		  StringBuilder sb = new StringBuilder("SELECT * FROM tm_user_ctrl WHERE  user_id=? and CTRL_CODE=80900000");
	        List<Object> param = new ArrayList<Object>();
	        param.add(id);
	        List<Map> map = DAOUtil.findAll(sb.toString(), param);
	        
	        return map;
	}
	
	/**
	 * 根据用户id删除菜单
	* @author yll
	* @date 2016年8月31日
	* @param id
	* @throws ServiceBizException
	* (non-Javadoc)
	* @see com.yonyou.dms.manage.service.basedata.user.UserMenuService#deleteMenuByUserId(java.lang.String)
	 */
	@Override
	public void deleteUserCtrl(Long id) throws ServiceBizException {
		LoginInfoDto loginInfo = ApplicationContextHelper.getBeanByType(LoginInfoDto.class);
		if(!StringUtils.isNullOrEmpty(loginInfo)&&!StringUtils.isNullOrEmpty(loginInfo.getDealerCode())){
			 String dealerCode = loginInfo.getDealerCode();
				UserCtrlPO.delete("USER_ID=? and DEALER_CODE=? and CTRL_CODE=80900000 ", id,dealerCode);
		}
		
	}

}
