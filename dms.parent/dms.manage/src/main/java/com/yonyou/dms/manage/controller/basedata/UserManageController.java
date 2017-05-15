
/** 
 *Copyright 2016 Yonyou Corporation Ltd. All Rights Reserved.
 * This software is published under the terms of the Yonyou Software
 * License version 1.0, a copy of which has been included with this
 * distribution in the LICENSE.txt file.
 *
 * @Project Name : dms.manage
 *
 * @File name : UserManageController.java
 *
 * @Author : yll
 *
 * @Date : 2016年8月19日
 *
----------------------------------------------------------------------------------
 *     Date       Who       Version     Comments
 * 1. 2016年8月19日    yll    1.0
 *
 *
 *
 *
----------------------------------------------------------------------------------
 */

package com.yonyou.dms.manage.controller.basedata;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.util.UriComponentsBuilder;

import com.yonyou.dms.common.domains.PO.basedata.UserPO;
import com.yonyou.dms.framework.domain.LoginInfoDto;
import com.yonyou.dms.framework.util.bean.ApplicationContextHelper;
import com.yonyou.dms.function.common.DictCodeConstants;
import com.yonyou.dms.function.exception.ServiceBizException;
import com.yonyou.dms.function.utils.common.CommonUtils;
import com.yonyou.dms.function.utils.common.StringUtils;
import com.yonyou.dms.function.utils.security.MD5Util;
import com.yonyou.dms.manage.domains.DTO.basedata.CommonTreeDto;
import com.yonyou.dms.manage.domains.DTO.basedata.EmployeeDto;
import com.yonyou.dms.manage.domains.DTO.basedata.role.RolePDto;
import com.yonyou.dms.manage.domains.DTO.basedata.user.UserCopyDto;
import com.yonyou.dms.manage.domains.DTO.basedata.user.UserDto;
import com.yonyou.dms.manage.domains.DTO.basedata.user.UserRoleDTO;
import com.yonyou.dms.manage.service.basedata.employee.EmployeeService;
import com.yonyou.dms.manage.service.basedata.user.UserCtrlService;
import com.yonyou.dms.manage.service.basedata.user.UserMenuActionService;
import com.yonyou.dms.manage.service.basedata.user.UserMenuRangeService;
import com.yonyou.dms.manage.service.basedata.user.UserMenuService;
import com.yonyou.dms.manage.service.basedata.user.UserService;
import com.yonyou.f4.mvc.annotation.TxnConn;

/**
 * 用户权限管理控制类
 * @author yll
 * @date 2016年8月19日
 */
@Controller
@TxnConn
@RequestMapping("/basedata/users")
public class UserManageController {

	// 定义日志接口
	private static final Logger logger = LoggerFactory.getLogger(UserManageController.class);

	@Autowired
	private UserService userService;
	@Autowired
	private UserMenuService userMenuService;
	@Autowired
	private UserCtrlService userCtrlService;
	@Autowired
	private UserMenuActionService userMenuActionService;
	@Autowired
	private UserMenuRangeService userMenuRangeService;
	@Autowired
	private EmployeeService employeeService;
	
	 @Autowired
	    private UserCtrlService userctrlservice;

	/**
	 * 
	 * 查询登录用户信息
	 * @author yll
	 * @date 2016年8月24日
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value="/loginuser",method=RequestMethod.GET)
	@ResponseBody
	public Map<String,Object> findByEmployeeId(){
		LoginInfoDto loginInfo = ApplicationContextHelper.getBeanByType(LoginInfoDto.class);
		String userCode=loginInfo.getUserAccount();
		String id=userService.getEmployeeIdByUserCode(userCode);
		Map<String, Object> map=employeeService.findById(id);
		//查找员工角色表  转成字符串用逗号分隔  插入map中
	/*	List<Map>  roles=employeeService.findEmpRolesByEmpId(id);
		String rolesStr="";        
		rolesStr=CommonUtils.listMapToString(roles,",");
		map.put("roles", rolesStr); */  
		//Map<String, Object> map= empo.toMap();
		         
		Map<String, Object> user=employeeService.findUserByEmployeeId(id);
		if(user!=null){
			map.putAll(user);
		}
		return map;
	}
	/**
	 * 
	 * 更新登录用户信息
	 * @author yll
	 * @date 2016年8月24日
	 * @param id
	 * @param empDto
	 * @param uriCB
	 * @return
	 */
	@RequestMapping(value="/loginuser",method=RequestMethod.PUT)
	public ResponseEntity<EmployeeDto> loginedit(@RequestBody EmployeeDto empDto,UriComponentsBuilder uriCB){
		LoginInfoDto loginInfo = ApplicationContextHelper.getBeanByType(LoginInfoDto.class);
		String userCode=loginInfo.getUserAccount();
		String id=userService.getEmployeeIdByUserCode(userCode);
		employeeService.updateEmpById(id, empDto);
		MultiValueMap<String,String> headers = new HttpHeaders();  
		headers.set("Location", uriCB.path("/basedata/users/loginuser").buildAndExpand(id).toUriString());  
		return new ResponseEntity<>(headers, HttpStatus.CREATED);  
	}
	/**
	 * 
	 * 快速修改密码
	 * @author yll
	 * @date 2016年8月24日
	 * @param brandDto
	 * @param uriCB
	 * @return
	 */
	@RequestMapping(value = "/editpassword", method = RequestMethod.PUT)
	public ResponseEntity<UserDto> editpassword(@RequestBody UserDto userDto,UriComponentsBuilder uriCB) {

		String userCode=userDto.getUserCode();
		String password=userDto.getPassword();
		String newPassword=userDto.getNewPassword();
		String passwordMD5=userService.getPasswordByUserCode(userCode);
		boolean validation=MD5Util.validPassword(password, passwordMD5);
		if(validation==true){
			String newPasswordMD5=MD5Util.getEncryptedPwd(newPassword);
			String id=userService.getEmployeeIdByUserCode(userCode);
			userDto.setPassword(newPasswordMD5);
			userDto.setEmployeeId(Long.valueOf(id));
			userDto.setUserStatus(DictCodeConstants.STATUS_QY);//启用状态
			Long userId=userService.getUserIDByEmployeeId(id);
			userService.modifyUser(userId, userDto);
			MultiValueMap<String,String> headers = new HttpHeaders();  
			headers.set("Location", uriCB.path("/basedata/users/editpassword").buildAndExpand(id).toUriString());  
			return new ResponseEntity<>(headers, HttpStatus.CREATED);
		}else{
			throw new ServiceBizException("原密码不正确");
			//return null;
		}
	}

	/**
	 * 
	* 管理员修改密码
	* @author yll
	* @date 2016年10月14日
	* @param userDto
	* @param uriCB
	* @return
	 */
	@RequestMapping(value = "/supereditpassword", method = RequestMethod.PUT)
	public ResponseEntity<UserDto> supereditpassword(@RequestBody UserDto userDto,UriComponentsBuilder uriCB) {
		Long id=userDto.getEmployeeId();
		String userCode=userDto.getUserCode();
		String newPassword=userDto.getNewPassword();
		String newPasswordMD5=MD5Util.getEncryptedPwd(newPassword);
		userDto.setPassword(newPasswordMD5);
		userDto.setUserCode(userCode);
		userDto.setUserStatus(DictCodeConstants.STATUS_QY);//启用状态??
		userDto.setIsModPassword(DictCodeConstants.STATUS_IS_YES);
		Long userId=userDto.getUserId();
		userService.modifyUser(userId, userDto);
		MultiValueMap<String,String> headers = new HttpHeaders();  
		headers.set("Location", uriCB.path("/basedata/users/supereditpassword").buildAndExpand(id).toUriString());  
		return new ResponseEntity<>(headers, HttpStatus.CREATED);

	}
	/**
	 * 
	 * 角色对应的权限的新增及修改
	 * @author yll
	 * @date 2016年8月12日
	 * @param RolePDto
	 * @param uriCB
	 * @return
	 * @throws Exception 
	 */
	@SuppressWarnings({ "rawtypes", "null" })
	@RequestMapping(value = "/permission", method = RequestMethod.PUT)
	public ResponseEntity<Map<String,Object>> ModifyOrg(@RequestBody RolePDto RolePDto,UriComponentsBuilder uriCB) throws Exception {
//		String roleId=RolePDto.getNowRole();
		Long employeeId=RolePDto.getEmployeeId();//员工id，用来查下面是否有账户
//		List<String> employeeRoles=RolePDto.getEmployeeRoles();//员工职位信息;
		String employeeNo=RolePDto.getEmployeeNo();
		Long userId=RolePDto.getUserId();
		String userCode=RolePDto.getUserCode();//账号
//		String employeeName=RolePDto.getEmployeeName();//姓名
//		String password=RolePDto.getPassword();//密码
		String userStatus=RolePDto.getUserStatus();//有效无效
		String nowTree=RolePDto.getNowTree();
		String treeMenuAction=RolePDto.getTreeMenuAction();
		String treeMenuRange=RolePDto.getTreeMenuRange();/////
		if(!StringUtils.isNullOrEmpty(RolePDto.getTreeCtrlCode())){
			String[] StrArray = RolePDto.getTreeCtrlCode().split(",");
			List list1 = Arrays.asList(StrArray);
			System.out.println("***"+list1.size());
			if(!list1.contains("80900000")){
				//删除
				userService.deleteUserCtrl(userId);
			}else{
				 List<Map> list=userService.QueryBigCustomer(userId);
				 if(list.size()==0){
					 throw new ServiceBizException("没有申请大客户组织架构权限，请先申请！");
				 }
				 /*if(list.size()>0&&list!=null){
					 throw new ServiceBizException("不允许自己操作申请大客户组织架构权限！"); 
				 }*/
			}
		}
	
		
		UserPO userPo=new UserPO();
		if(StringUtils.isNullOrEmpty(nowTree)){//菜单权限不能为空
			throw new ServiceBizException("菜单权限不能为空");
		}
		userService.usercheckEmpNo(RolePDto.getEmployeeNo(), RolePDto.getOrgCode(),RolePDto.getEmployeeName(),RolePDto.getUserCode(),RolePDto.getUserId());
		
		//如果员工下面没有账户，新建一个账户
		if(StringUtils.isNullOrEmpty(userId)){
			//employeeService.updateEmpById(employeeId, employeeRoles);
			/////新建账户的默认密码和用户名相同
			String passwordMD5=MD5Util.getEncryptedPwd("a1234567");//!!!!
			UserDto userDto=new UserDto();
			userDto.setEmployeeId(employeeId);
			userDto.setUserCode(userCode);
			userDto.setPassword(passwordMD5);
			userDto.setUserStatus(Integer.parseInt(userStatus));
			userDto.setEmployeeNo(RolePDto.getEmployeeNo());
			userDto.setOrgCode(RolePDto.getOrgCode());
		    userDto.setIsServiceAdvisor(RolePDto.getIsServiceAdvisor());
	        userDto.setIsConsultant(RolePDto.getIsConsultant());
	        userDto.setUserName(RolePDto.getEmployeeName());
	        userPo=userService.addUser(userDto);
			//用户角色对应关系
			if(!StringUtils.isNullOrEmpty(RolePDto.getRoleCode())){
			UserRoleDTO useRoleDto=new UserRoleDTO();
			useRoleDto.setRoleCode(RolePDto.getRoleCode());
			useRoleDto.setUserId(userPo.getLongId());
			userService.addUseRole(useRoleDto);
			}
			
			
			String useridtwice=Long.toString(userPo.getLongId());//类型转换
			
			//如果改变checkbox才做操作
			userPer(nowTree,userPo.getLongId(), treeMenuAction, useridtwice,treeMenuRange,RolePDto);
		}
		else{//如果账户已有
		    System.out.println("11111111111111111111111111");
			UserDto userDto=new UserDto();
			userDto.setEmployeeId(employeeId);
	        userDto.setEmployeeNo(RolePDto.getEmployeeNo());
			userDto.setUserCode(userCode);
			userDto.setUserStatus(Integer.parseInt(userStatus));
			userDto.setOrgCode(RolePDto.getOrgCode());
			userDto.setIsServiceAdvisor(RolePDto.getIsServiceAdvisor());
			userDto.setIsConsultant(RolePDto.getIsConsultant());
			userDto.setUserName(RolePDto.getEmployeeName());
			userService.modifyUser(userId, userDto);
			String userid=Long.toString(userId);//类型转换
			//如果改变checkbox才做操作
			userPer(nowTree,userId, treeMenuAction, userid, treeMenuRange,RolePDto);
		}

		MultiValueMap<String,String> headers = new HttpHeaders(); 
		return new ResponseEntity<Map<String,Object>>(userPo.toMap(),headers, HttpStatus.CREATED);  
		
	}
	
	
	
	

	
	/**
	*  
          个人参数配置
          数据访问，按钮操作
	* @author zhanshiwei
	* @date 2017年1月4日
	* @param nowTree
	* @param userId
	* @param treeMenuAction
	* @param userid
	* @param treeMenuRange
	* @param rolepdto
	*/
		
	public void userPer(String nowTree,Long userId,String treeMenuAction,String userid,String treeMenuRange,RolePDto rolepdto){
		//如果改变checkbox才做操作
	    System.out.println("22222222222222222224");
		if(!StringUtils.isNullOrEmpty(nowTree)){
		    System.out.println("22222222222222222225");
			userMenuService.deleteMenuByUserId(userid);
		}
		String[] sourceStrArray=nowTree.split(",");
		
		for(int i=0;i<sourceStrArray.length;i++)
		{
			userMenuService.addUserMenu(userid, sourceStrArray[i]);
		}
		userCtrlService.deleteMenuByUserId(userid);
		
		userCtrlService.addUserCtrl(userid,rolepdto.getTreeCtrlCode());

		if(treeMenuAction!=null){
			//treeMenuAction
			String[] oneAction=treeMenuAction.split(";");
			
			if(oneAction.length>0){
				for(int i=0;i<oneAction.length;i++)
				{
					if(!StringUtils.isNullOrEmpty(oneAction[i])){
						userMenuActionService.addOneAction(oneAction[i],userId+"");
					}
				} 
			}
		}
		//treeMenuRange
		if(treeMenuRange!=null){
			String[] oneRange=treeMenuRange.split(";");
			
			if(oneRange.length>0){
				for(int i=0;i<oneRange.length;i++)
				{
					if(!StringUtils.isNullOrEmpty(oneRange[i])){
						userMenuRangeService.addOneRange(oneRange[i],userId+"");
					}
				} 
			}
		}
	}

	/**
	 * 
	 * 加载角色权限信息
	 * @author yll
	 * @date 2016年8月12日
	 * @param data
	 * @return
	 */
	@RequestMapping(value = "/userData", method = RequestMethod.GET)
	@ResponseBody
	public Map<String,Object>  userData(String userId) {
		Map<String,Object> userData=new HashMap<String,Object>();
		if(userId==null){
			List<Map> list = userService.queryMenu();
			List<CommonTreeDto> orgList = new ArrayList<>();
			for(int i=0;i<list.size();i++){
				CommonTreeDto orgTreeOrg = new CommonTreeDto();
				orgTreeOrg.setId((String)list.get(i).get("MENU_ID").toString());
				String parent = (String)list.get(i).get("PARENT_ID").toString();
				if("0".equals(parent)){
					parent = "#";
				}
				orgTreeOrg.setParent(parent);
				orgTreeOrg.setText((String)list.get(i).get("MENU_NAME").toString());
				orgList.add(orgTreeOrg);
			}
			Map<String, String> checkbox=new HashMap<String, String>();
			String [] treeMenuActionString= new String [0] ;
			String [] treeMenuRangeString= new String [0] ;
			userData.put("treejson", orgList);
			userData.put("checkbox", checkbox);
			userData.put("treeMenuAction", treeMenuActionString);
			userData.put("treeMenuRange", treeMenuRangeString);
		}else{
			///Map<String, String> checkbox=userCtrlService.queryMenuCtrl( Long.toString(userId));//这个受控权限不用了
		    List<CommonTreeDto> orgList=userService.queryUserMenu(Long.parseLong(userId));
        /*List<Map> list = userService.queryMenu();
			List<CommonTreeDto> orgList = new ArrayList<>(); 
			for(int i=0;i<list.size();i++){
				boolean state=userMenuService.findMenu(Long.toString(userId),(String)list.get(i).get("MENU_ID").toString());
				CommonTreeStateDto CommonTreeStateDto=new CommonTreeStateDto();
				if(state==true){
					CommonTreeStateDto.setChecked(state);
				}else{
					CommonTreeStateDto.setChecked(false);
				}

				CommonTreeDto orgTreeOrg = new CommonTreeDto();
				orgTreeOrg.setId((String)list.get(i).get("MENU_ID").toString());
				String parent = (String)list.get(i).get("PARENT_ID").toString();
				if("0".equals(parent)){
					parent = "#";
				}
				orgTreeOrg.setParent(parent);
				orgTreeOrg.setText((String)list.get(i).get("MENU_DESC").toString());
				orgTreeOrg.setState(CommonTreeStateDto);
				orgList.add(orgTreeOrg);
			}*/
			userData.put("treejson", orgList);
			userData.put("treeMenuAction", userMenuActionService.findMenuAction(userId));
			userData.put("treeMenuRange", userMenuRangeService.findMenuRange(userId));
		}

		return userData;
	}

	/**
	 * 
	 * 查询用户菜单操作按钮 复选框组
	 * @author yll
	 * @date 2016年8月12日
	 * @param menuId
	 * @return
	 */
	@RequestMapping(value = "/userMenuAction/{menuId}", method = RequestMethod.GET)
	@ResponseBody
	public List<Map> actionRemoteUrl(@PathVariable(value = "menuId")Long  menuId){
		return userMenuActionService.remoteUrl(menuId);
	}

	/**
	 * 
	 * 查询角色菜单权限  复选框组
	 * @author yll
	 * @date 2016年8月12日
	 * @param menuId
	 * @return
	 */
	@RequestMapping(value = "/userMenuRange/{menuId}", method = RequestMethod.GET)
	@ResponseBody
	public List<Map> rangeRemoteUrl(@PathVariable(value = "menuId")Long  menuId){
		return userMenuRangeService.remoteUrl(menuId);
	}

    /**
    * 员工账号
    * @author zhanshiwei
    * @date 2017年1月14日
    * @param id
    * @return
    */
        
    @SuppressWarnings("unchecked")
    @RequestMapping(value = "/gfkUser/{id}", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> findGFkUserById(@PathVariable(value="id") Long id) {
        Map<String, Object> user = null;
        user = employeeService.findGFkUserById(id);
        if (CommonUtils.isNullOrEmpty(user)) {
            user=new HashMap<String, Object>();
            user.put("USER_STATUS", DictCodeConstants.STATUS_QY);// 初始值为启用
        }
        return user;
    }
    
    
    /**
    * 账号检查
    * @author zhanshiwei
    * @date 2017年1月15日
    * @param id
    * @return
    */
    	
    @RequestMapping(value = "/gfkChekUser/{id}", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> findGFkUserCheById(@PathVariable(value="id") String id) {
        Map<String, Object> user = userService.findGFkUserCheById(id);
       
        return user;
    }
    
    
    /**
    * 
    * @author zhanshiwei
    * @date 2017年1月17日
    * @param userCopyDto
    * @param uriCB
    * @return
    */
    	
    @RequestMapping(value = "/copUserPermission", method = RequestMethod.PUT)
    @ResponseBody
    public Map<String, Object> copUserPermission(@RequestBody UserCopyDto userCopyDto,UriComponentsBuilder uriCB) {
        Map<String,Object> userData=new HashMap<String,Object>();
//        Map<String, Object> user = userService.copUserPermission(userCopyDto);
        //菜单权限
        List<CommonTreeDto> orgList=userService.queryUserMenu(userCopyDto.getTargetId());
        //控制权限
        List<CommonTreeDto> userCtrlResult = userctrlservice.UserCtrlOption(userCopyDto.getTargetId().toString());
        userData.put("treejson", orgList);
        userData.put("treeMenuAction", userMenuActionService.findMenuAction(userCopyDto.getTargetId().toString()));
        userData.put("treeMenuRange", userMenuRangeService.findMenuRange(userCopyDto.getTargetId().toString()));
        userData.put("userCtrl", userCtrlResult);
        userData.put("isPermCheckRadio", userCopyDto.getIsPermCheckRadio());
        
        return userData;
    }
    


}
