
/** 
*Copyright 2016 Yonyou Corporation Ltd. All Rights Reserved.
* This software is published under the terms of the Yonyou Software
* License version 1.0, a copy of which has been included with this
* distribution in the LICENSE.txt file.
*
* @Project Name : dms.web
*
* @File name : LoginController.java
*
* @Author : zhangxc
*
* @Date : 2016年6月30日
*
----------------------------------------------------------------------------------
*     Date       Who       Version     Comments
* 1. 2016年6月30日    zhangxc    1.0
*
*
*
*
----------------------------------------------------------------------------------
*/

package com.yonyou.dms.web.controller.login;

import java.sql.Connection;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.javalite.activejdbc.Base;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yonyou.dms.common.domains.PO.basedata.TcUserPO;
import com.yonyou.dms.framework.DAO.DAOUtil;
import com.yonyou.dms.framework.domain.FrameworkParamBean;
import com.yonyou.dms.framework.domain.LoginInfoDto;
import com.yonyou.dms.framework.domain.UserAccessInfoDto;
import com.yonyou.dms.framework.service.PowerDataService;
import com.yonyou.dms.framework.service.PowerUrlService;
import com.yonyou.dms.framework.service.TenantDealerMappingService;
import com.yonyou.dms.framework.util.bean.ApplicationContextHelper;
import com.yonyou.dms.function.common.OemDictCodeConstants;
import com.yonyou.dms.function.exception.AuthLoginOutException;
import com.yonyou.dms.function.exception.ServiceBizException;
import com.yonyou.dms.function.utils.common.CommonUtils;
import com.yonyou.dms.function.utils.common.StringUtils;
import com.yonyou.dms.web.domains.DTO.login.MenuDto;
import com.yonyou.dms.web.service.login.LoginUserService;
import com.yonyou.dms.web.service.login.MenuService;
import com.yonyou.f4.common.acl.AclUser;
import com.yonyou.f4.common.database.DBService;
import com.yonyou.f4.mvc.annotation.NoTxn;
import com.yonyou.f4.mvc.annotation.TxnConn;
import com.yonyou.f4.mvc.controller.BaseController;

/**
 * 此Controller 主要实现登录、退出、修改密码等功能
 * 
 * @author zhangxc
 * @date 2016年6月30日
 */
@Controller
@RequestMapping("/common/login")
public class LoginController extends BaseController {

    @Autowired
    FrameworkParamBean          frameworkParam;

    @Autowired
    private MenuService         menuService;
    
    

    
    @Autowired
    DBService                   dbService;
    
    @Autowired
    private TenantDealerMappingService tenantDealerSerivce;

    @Autowired
    LoginUserService            loginUserService;
    
    @Autowired
    private PowerUrlService     powerUrlService;    
    
    @Autowired
    private PowerDataService    powerDataService;

    // 定义日志接口
    private static final Logger logger = LoggerFactory.getLogger(LoginController.class);

    /**
     * 根据查询条件返回对应的用户数据
     * 
     * @author zhangxc
     * @date 2016年6月29日
     * @param queryParam 查询条件
     * @return 查询结果
     * @throws Exception
     */
    @RequestMapping(method = RequestMethod.GET)
    @ResponseBody
    public Map<String,Object> login(@RequestParam("username") String userName, @RequestParam("password") String password,
                      @RequestParam("dealerCode") String dealerCode, HttpServletRequest request) throws Exception {
    	logger.debug("enter LoginController login");
    	boolean flag = false;
    	Map<String,Object> resultMap = new HashMap<>();
        try {
            request.getSession().removeAttribute(frameworkParam.getTenantKey());
            request.getSession().removeAttribute("loginInfoDto");
            
            //获取对应的TENANT_ID
            Map<String,Map<String,String>> tenantDealerMaping = tenantDealerSerivce.getAll();
            logger.debug("LoginController tenantDealerMaping size:"+tenantDealerMaping.size());
            String tenantId = "";
            Map map = tenantDealerMaping.get(dealerCode);
            if(null== map || map.size()<=0 ){
            	flag = true;
            	throw new ServiceBizException("登录域不正确，请重新输入！");
            }else{
            	tenantId = tenantDealerMaping.get(dealerCode).get("TENANT_ID");
            }
            dbService.beginTxn(tenantId);
            Connection conn = dbService.openConn(tenantId);
            Base.attach(conn);
            
            //加载默认通过的url信息
        	powerUrlService.dafaultUrl();
            
        	if("999999".equals(dealerCode)){
        		// 校验账号
        		loginUserService.oemLogCheck(dealerCode, userName, password);
        		
        		/**
        		 * 当前登录信息
        		 */
        		LoginInfoDto loginInfo = ApplicationContextHelper.getBeanByType(LoginInfoDto.class);
        		
        		loginInfo.setSysType(1);
        		//加载tenantID
        		request.getSession().setAttribute(frameworkParam.getTenantKey(), tenantId);
        		
        		//构建AclUser
        		AclUser aclUser = new AclUser();
        		aclUser.setUID(loginInfo.getUserId().toString());
        		aclUser.setName(loginInfo.getUserAccount());
        		request.getSession().setAttribute(frameworkParam.getAclUserKey(), aclUser);
        		logger.info("userName:" + userName + ";dealerCode:" + dealerCode + ";登陆成功");
        		
        		//判断用户职位数是否大于1
        		List<Map> lismap = loginUserService.selectPose(loginInfo.getUserId(),null);
        		if(lismap.size()>1){//当职位数大于1时进入职位选择界面
        			resultMap.put("STATUS", 2);
        			loginInfo.setIsShowChangePose(1);
        		}else if(lismap.size()==1){//当职位数等于1时直接进入
	        		//获取该用户的数据权限范围信息
        			// 如果查询通过
        	        if (!CommonUtils.isNullOrEmpty(lismap)) {
        	        	setLoginInfoManager(lismap);
        	        }
        	        loginInfo.setIsShowChangePose(0);
	        		resultMap.put("STATUS", 1);
        		}else{
        			flag = true;
        			throw new ServiceBizException("当前用户未分配职位权限！");
        		}
        		//修改登陆时间
        		TcUserPO.update("LASTSIGNIN_TIME = ?", "USER_ID = ?", new Date(),loginInfo.getUserId());
        		
        	}else{
        	    //根据dealerCode获取DMS的entity_code
        	    dealerCode = loginUserService.getEntityCode(dealerCode);
        		// 校验账号
        		loginUserService.logCheck(dealerCode, userName, password);
        		
        		/**
        		 * 当前登录信息
        		 */
        		LoginInfoDto loginInfo = ApplicationContextHelper.getBeanByType(LoginInfoDto.class);
        		
        		loginInfo.setSysType(0);
        		loginInfo.setIsShowChangePose(0);
        		//加载tenantID
        		request.getSession().setAttribute(frameworkParam.getTenantKey(), tenantId);
        		
        		//构建AclUser
        		AclUser aclUser = new AclUser();
        		aclUser.setUID(loginInfo.getUserId().toString());
        		aclUser.setName(loginInfo.getUserAccount());
        		request.getSession().setAttribute(frameworkParam.getAclUserKey(), aclUser);
        		logger.info("userName:" + userName + ";dealerCode:" + dealerCode + ";登陆成功");
        		
        		//获取该用户的数据权限范围信息
        		powerDataService.getDataPower(loginInfo.getUserId(),loginInfo.getOrgCode());
        		resultMap.put("STATUS", 1);
        	}
        	return resultMap;
        } catch (Exception e) {
        	flag = true;
            throw e;
        } finally {
        	if(!flag){
        		dbService.endTxn(true);
        		Base.detach();
        		dbService.clean();
        	}
        }

    }
    
    @RequestMapping(value="/acntChange",method = RequestMethod.GET)
    @ResponseBody
    public Map<String,Object> acntChange(@RequestParam("username") String userName, @RequestParam("password") String password,
                      @RequestParam("dealerCode") String dealerCode, HttpServletRequest request) throws Exception {
    	Map<String,Object> resultMap = new HashMap<>();
        try {
            request.getSession().removeAttribute(frameworkParam.getTenantKey());
            request.getSession().removeAttribute("loginInfoDto");
            
            //获取对应的TENANT_ID
            Map<String,Map<String,String>> tenantDealerMaping = tenantDealerSerivce.getAll();
            String tenantId = tenantDealerMaping.get(dealerCode).get("TENANT_ID");
            dbService.beginTxn(tenantId);
            Connection conn = dbService.openConn(tenantId);
            Base.attach(conn);
            
            //加载默认通过的url信息
        	powerUrlService.dafaultUrl();
            
        	if("999999".equals(dealerCode)){
        		// 校验账号
        		loginUserService.oemLogCheck(dealerCode, userName, password);
        		
        		/**
        		 * 当前登录信息
        		 */
        		LoginInfoDto loginInfo = ApplicationContextHelper.getBeanByType(LoginInfoDto.class);
        		
        		loginInfo.setSysType(1);
        		//加载tenantID
        		request.getSession().setAttribute(frameworkParam.getTenantKey(), tenantId);
        		
        		//构建AclUser
        		AclUser aclUser = new AclUser();
        		aclUser.setUID(loginInfo.getUserId().toString());
        		aclUser.setName(loginInfo.getUserAccount());
        		request.getSession().setAttribute(frameworkParam.getAclUserKey(), aclUser);
        		logger.info("userName:" + userName + ";dealerCode:" + dealerCode + ";登陆成功");
        		
        		//判断用户职位数是否大于1
        		List<Map> lismap = loginUserService.selectPose(loginInfo.getUserId(),null);
        		if(lismap.size()>1){//当职位数大于1时进入职位选择界面
        			resultMap.put("STATUS", 2);
        			loginInfo.setIsShowChangePose(1);
        		}else if(lismap.size()==1){//当职位数等于1时直接进入
	        		//获取该用户的数据权限范围信息
        			// 如果查询通过
        	        if (!CommonUtils.isNullOrEmpty(lismap)) {
        	        	setLoginInfoManager(lismap);
        	        }
        	        loginInfo.setIsShowChangePose(0);
        	        /* ======切换账户是需设置，否则无法通过认证======
        	         */
        	        loginInfo.setIsRefreshToken(1);     
	        		resultMap.put("STATUS", 1);
        		}else{
        			throw new ServiceBizException("当前用户未分配职位权限！");
        		}
        		
        	}else{
        		// 校验账号
        		loginUserService.logCheck(dealerCode, userName, password);
        		
        		/**
        		 * 当前登录信息
        		 */
        		LoginInfoDto loginInfo = ApplicationContextHelper.getBeanByType(LoginInfoDto.class);
        		
        		loginInfo.setSysType(0);
        		loginInfo.setIsShowChangePose(0);
        		//加载tenantID
        		request.getSession().setAttribute(frameworkParam.getTenantKey(), tenantId);
        		
        		//构建AclUser
        		AclUser aclUser = new AclUser();
        		aclUser.setUID(loginInfo.getUserId().toString());
        		aclUser.setName(loginInfo.getUserAccount());
        		request.getSession().setAttribute(frameworkParam.getAclUserKey(), aclUser);
        		logger.info("userName:" + userName + ";dealerCode:" + dealerCode + ";登陆成功");
        		
        		//获取该用户的数据权限范围信息
        		powerDataService.getDataPower(loginInfo.getUserId(),loginInfo.getOrgCode());
        		resultMap.put("STATUS", 1);
        	}
        	return resultMap;
        } catch (Exception e) {
            throw e;
        } finally {
            dbService.endTxn(true);
            Base.detach();
            dbService.clean();
        }

    }
    
    
    /**
     * 根据查询条件返回对应的用户数据
     * 
     * @author zhangxc
     * @date 2016年6月29日
     * @param queryParam 查询条件
     * @return 查询结果
     * @throws Exception
     */
    @RequestMapping(value = "/{id}",method = RequestMethod.GET)
    @ResponseBody
    public Map<String,Object> checkPose(@PathVariable(value = "id") String id, HttpServletRequest request) throws Exception {
    	Map<String,Object> resultMap = new HashMap<>();
        try {
            /**
    		 * 当前登录信息
    		 */
    		 LoginInfoDto loginInfo = ApplicationContextHelper.getBeanByType(LoginInfoDto.class);
    		 loginInfo.setIsRefreshToken(1);
            //获取对应的TENANT_ID
            Map<String,Map<String,String>> tenantDealerMaping = tenantDealerSerivce.getAll();
            String tenantId = tenantDealerMaping.get(OemDictCodeConstants.OEM_LOGING_DEALERCODE).get("TENANT_ID");
            dbService.beginTxn(tenantId);
            Connection conn = dbService.openConn(tenantId);
            Base.attach(conn);
            
            //加载默认通过的url信息
        	powerUrlService.dafaultUrl();
            
        	loginInfo.setSysType(1);
        	//加载tenantID
        	request.getSession().setAttribute(frameworkParam.getTenantKey(), tenantId);
        		
        	//构建AclUser
        	AclUser aclUser = new AclUser();
        	aclUser.setUID(loginInfo.getUserId().toString());
        	aclUser.setName(loginInfo.getUserAccount());
        	request.getSession().setAttribute(frameworkParam.getAclUserKey(), aclUser);
        	logger.info("userName:" + loginInfo.getUserName() + ";dealerCode:" + loginInfo.getDealerCode() + ";登陆成功");
        	List<Map> lismap = loginUserService.selectPose(loginInfo.getUserId(),id);
        	setLoginInfoManager(lismap);
        	resultMap.put("STATUS", 1);
        	return resultMap;
        } catch (Exception e) {
            throw e;
        } finally {
            dbService.endTxn(true);
            Base.detach();
            dbService.clean();
        }

    }
    /*
     * 存放登录用户信息
     */
	private void setLoginInfoManager(List<Map> lismap) {
		 /**
		 * 当前登录信息
		 */
		 LoginInfoDto loginInfo = ApplicationContextHelper.getBeanByType(LoginInfoDto.class);
		 
		 Map userInfo = lismap.get(0);

         Object orgID = (Object) userInfo.get("ORG_ID");// orgid
         if (!StringUtils.isNullOrEmpty(orgID)) {
             loginInfo.setOrgId(Long.parseLong(orgID.toString()));
         }
         Object poseId = (Object) userInfo.get("POSE_ID");// 职位ID
         if (!StringUtils.isNullOrEmpty(poseId)) {
         	loginInfo.setPoseId(Long.parseLong(poseId.toString()));
         	loginInfo.setPoseSeriesIDs(loginUserService.getPoseSeriesIDs(loginInfo.getPoseId()));
         }
         Object parentOrgId = (Object) userInfo.get("PARENT_ORG_ID");//上级部门ID
         if (!StringUtils.isNullOrEmpty(parentOrgId)) {
         	loginInfo.setParentOrgId(parentOrgId.toString());
         }
         Integer poseType = (Integer) userInfo.get("POSE_TYPE");//职位类型
         if (!StringUtils.isNullOrEmpty(poseType)) {
             loginInfo.setPoseType(poseType);
         }
         Integer dutyType = (Integer) userInfo.get("DUTY_TYPE");//职位类型
         if (!StringUtils.isNullOrEmpty(dutyType)) {
             loginInfo.setDutyType(dutyType.toString());
         }
         Integer poseBusType = (Integer) userInfo.get("POSE_BUS_TYPE");//职位业务类型
         if (!StringUtils.isNullOrEmpty(poseBusType)) {
         	loginInfo.setPoseBusType(poseBusType);
         }
         Integer orgType = (Integer) userInfo.get("ORG_TYPE");//区域类型
         if (!StringUtils.isNullOrEmpty(orgType)) {
             loginInfo.setOrgType(orgType);
         }
         String dealerShortName = (String) userInfo.get("ORG_NAME");// 经销商简称
         if (!StringUtils.isNullOrEmpty(dealerShortName)) {
             loginInfo.setDealerShortName(dealerShortName);
         }
         String dealerName = (String) userInfo.get("ORG_DESC");// 经销商全称
         if (!StringUtils.isNullOrEmpty(dealerName)) {
         	loginInfo.setDealerName(dealerName);
         }
         
         if(loginInfo.getUserType().equals(OemDictCodeConstants.SYS_USER_DEALER))
			{
				HashMap<String, String> hm = new HashMap<String, String>();
				loginUserService.getUserDearId(orgID ,poseBusType ,hm);
				loginInfo.setDealerId(Long.parseLong(hm.get("DEALER_IDS").toString()));
				loginInfo.setDealerSeriesIDs(loginUserService.getDealerSeriesIDs(loginInfo.getDealerId()));
				loginInfo.setOemCompanyId(hm.get("OEM_COMPANY_ID"));
				if(hm.get("COMPANY") != null && !"".equals(hm.get("COMPANY")))
					loginInfo.setCompanyId(Long.valueOf(hm.get("COMPANY")));
				loginInfo.setDealerOrgId(hm.get("DEALER_ORG_ID"));
				loginInfo.setDealerCode(hm.get("DEALER_CODE"));
			}
         
			if(loginInfo.getUserType().equals(OemDictCodeConstants.SYS_USER_OEM))
			{
				loginInfo.setOemPositionArea(loginUserService.getOemUserArea(Long.parseLong(poseId.toString())));
			}
         
         loginInfo.setBxjDept(loginUserService.getXjbm(Long.parseLong(orgID.toString()), loginInfo.getCompanyId()));
         
         loginUserService.getOemActionUrl();
	}
	/**
     * 厂端用户获取职位列表
     * @param queryParam
     * @throws Exception
     */
    @SuppressWarnings("rawtypes")
	@RequestMapping(value = "/checkPose", method = RequestMethod.GET)
    @ResponseBody
    @TxnConn
   	public List<Map> queryPose(@RequestParam Map<String, String> queryParam) throws Exception {
    	/**
		 * 当前登录信息
		 */
		LoginInfoDto loginInfo = ApplicationContextHelper.getBeanByType(LoginInfoDto.class);
    	List<Map> list = loginUserService.queryPose(loginInfo.getUserId());
    	return list;
    }
    /**
     * 根据查询条件返回对应的用户数据
     * 
     * @author zhangxc
     * @date 2016年6月29日
     * @param queryParam 查询条件
     * @return 查询结果
     */
    @RequestMapping(method = RequestMethod.DELETE)
    @ResponseBody
    public void loginOut(HttpServletRequest request, HttpServletResponse response) {
        Enumeration<String> sessions = request.getSession().getAttributeNames();
        while (sessions.hasMoreElements()) {
            logger.info("sessions:" + sessions.nextElement());
        }
        // LoginInfoDto loginInfo = ApplicationContextHelper.getBeanByType(LoginInfoDto.class);
        // if(loginInfo!=null){
        // loginInfo.setUserAccount(null);
        // loginInfo.setDealerCode(null);
        // logger.info("userName:"+loginInfo.getUserAccount()+";dealerCode:"+loginInfo.getDealerCode()+";退出成功");
        // }
        request.getSession().removeAttribute(frameworkParam.getTenantKey());
        request.getSession().removeAttribute(frameworkParam.getAclUserKey());
        request.getSession().removeAttribute("loginInfoDto");
        request.getSession().removeAttribute("userAccessInfoDto");
    }

    /**
     * 加载用户的菜单
     * 
     * @author zhangxc
     * @date 2016年10月11日
     * @param userId
     * @return
     */
    @RequestMapping(value = "/menus", method = RequestMethod.GET)
    @ResponseBody
    @TxnConn
    public Map<String, MenuDto> getMenus() {
    	
        List<Map> menus = menuService.queryMenu();
        Map<String, MenuDto> childMap = new LinkedHashMap<>();
        Map<String, MenuDto> menuMap = new LinkedHashMap<>();

        for (int i = 0; i < menus.size(); i++) {
            String type = menus.get(i).get("MENU_TYPE").toString();
            MenuDto menuDto = new MenuDto();
            menuDto.setMenuId((String) menus.get(i).get("MENU_ID").toString());	
            menuDto.setMenuRank(Integer.parseInt(menus.get(i).get("RANK").toString()));
            menuDto.setMenuName(DAOUtil.getLocaleFieldValue(menus.get(i), "MENU_NAME"));

            if (menus.get(i).get("MENU_ICON") != null) {
                menuDto.setMenuIcon((String) menus.get(i).get("MENU_ICON").toString());
            }
            String url = "system/pageError.html";
            if (menus.get(i).get("MENU_URL") != null) {
                url = (String) menus.get(i).get("MENU_URL").toString();
            }
            menuDto.setMenuUrl(url);
            String parentId = (String) menus.get(i).get("PARENT_ID").toString();
            menuDto.setParentId(parentId);
            menuDto.setMenuType(type);

            if ("1001".equals(type)) {
                menuDto.setChildren(new LinkedHashMap<String, MenuDto>());
                menuMap.put(menuDto.getMenuId(), menuDto);
            }
            if ("1002".equals(type)) {
                MenuDto parentMenu = menuMap.get(parentId);
                menuDto.setChildren(new LinkedHashMap<String, MenuDto>());
                childMap.put(menuDto.getMenuId(), menuDto);
                if (parentMenu != null) {
                    parentMenu.getChildren().put(menuDto.getMenuId(), menuDto);
                }
            }
            if ("1003".equals(type)) {
                MenuDto parentMenu = childMap.get(parentId);
                if (parentMenu != null) {
                    parentMenu.getChildren().put("menu_"+menuDto.getMenuId(), menuDto);
                }
            }
        }
        return menuMap;
    }
    
    
    /**
     * 加载用户的操作按钮功能
     * 
     * @author zhangxc
     * @date 2016年11月11日
     * @param userId
     * @return 用户的操作按钮权限url
     */
    @SuppressWarnings("rawtypes")
	@RequestMapping(value = "/handles", method = RequestMethod.GET)
    @ResponseBody
    @TxnConn
     public List<Map> getHandles(){
    	List<Map> handles =  menuService.queryHandles();
    	return handles;
    }
    
    
    /**
     * 加载用户的操作按钮功能
     * 
     * @author zhangxc
     * @date 2016年11月11日
     * @param userId
     * @return 用户的操作按钮权限url
     */
    @RequestMapping(value = "/refreshToken", method = RequestMethod.GET)
    @ResponseBody
    @NoTxn
    public String refreshToken( HttpServletRequest request){
        String oldToken = request.getParameter("urlToken");
        UserAccessInfoDto userAccessInfoDto = ApplicationContextHelper.getBeanByType(UserAccessInfoDto.class);
        LoginInfoDto loginInfo = ApplicationContextHelper.getBeanByType(LoginInfoDto.class);
        if(!"1".equals(CommonUtils.checkNull(loginInfo.getIsRefreshToken()))){
	        if(!StringUtils.isNullOrEmpty(oldToken)||userAccessInfoDto.isFirstToken()){
	            if(userAccessInfoDto.isFirstToken()||oldToken.equals(userAccessInfoDto.getValidFirstToken())||oldToken.equals(userAccessInfoDto.getValidSecodeToken())){
	                String uuid = UUID.randomUUID().toString();
	                if(userAccessInfoDto.isFirstToken()){
	                    userAccessInfoDto.setValidFirstToken(uuid);
	                }else{
	                    userAccessInfoDto.setValidFirstToken(userAccessInfoDto.getValidSecodeToken());
	                }
	                userAccessInfoDto.setValidSecodeToken(uuid);
	                userAccessInfoDto.setValidTokenDate(new Date());
	                userAccessInfoDto.setFirstToken(false);
	                return userAccessInfoDto.getValidSecodeToken();
	            }else{
	                throw new AuthLoginOutException("获取验证码失败2");
	            }
	        }else{
	            throw new AuthLoginOutException("获取验证码失败1");
	        }
        }else{
        	String uuid = UUID.randomUUID().toString();
            if(userAccessInfoDto.isFirstToken()){
                userAccessInfoDto.setValidFirstToken(uuid);
            }else{
                userAccessInfoDto.setValidFirstToken(userAccessInfoDto.getValidSecodeToken());
            }
            userAccessInfoDto.setValidSecodeToken(uuid);
            userAccessInfoDto.setValidTokenDate(new Date());
            userAccessInfoDto.setFirstToken(false);
            loginInfo.setIsRefreshToken(0);
            return userAccessInfoDto.getValidSecodeToken();
        }
    }
}
