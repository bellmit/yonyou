
/** 
*Copyright 2016 Yonyou Corporation Ltd. All Rights Reserved.
* This software is published under the terms of the Yonyou Software
* License version 1.0, a copy of which has been included with this
* distribution in the LICENSE.txt file.
*
* @Project Name : dms.framework
*
* @File name : LoginFrameworkServiceImpl.java
*
* @Author : zhangxc
*
* @Date : 2016年8月1日
*
----------------------------------------------------------------------------------
*     Date       Who       Version     Comments
* 1. 2016年8月1日    zhangxc    1.0
*
*
*
*
----------------------------------------------------------------------------------
*/
	
package com.yonyou.dms.framework.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import com.yonyou.dms.framework.DAO.DAOUtil;
import com.yonyou.dms.framework.DAO.OemDAOUtil;
import com.yonyou.dms.framework.domain.LoginInfoDto;
import com.yonyou.dms.framework.domain.LoginInfoTempDto;
import com.yonyou.dms.framework.service.LoginFrameworkService;
import com.yonyou.dms.framework.util.FrameworkUtil;
import com.yonyou.dms.framework.util.bean.ApplicationContextHelper;
import com.yonyou.dms.function.common.DictCodeConstants;
import com.yonyou.dms.function.common.OemDictCodeConstants;
import com.yonyou.dms.function.exception.ServiceBizException;
import com.yonyou.dms.function.exception.UtilException;
import com.yonyou.dms.function.utils.common.CommonUtils;
import com.yonyou.dms.function.utils.common.StringUtils;
import com.yonyou.f4.mvc.annotation.TxnConn;


/**
* 登录相关功能的校验Service
* @author zhangxc
* @date 2016年8月1日
*/
@Service
@TxnConn
public class LoginFrameworkServiceImpl implements LoginFrameworkService {
    // 定义日志接口
    private static final Logger logger = LoggerFactory.getLogger(LoginFrameworkServiceImpl.class);
    
    private static Pattern isContainNumberPattern;
    private static Pattern urlModalPattern;
    
    /**
     *判断是否包含数字
     */
    static{
        isContainNumberPattern = Pattern.compile(".+/rest/\\w+/.*\\d+.*");
        urlModalPattern = Pattern.compile(".*/(.+)/rest/.*");
    }
    /**
     * 确认是否有权限登录
    * @author zhangxc
    * @date 2016年8月1日
    * @param funcId
    * @param requestUrl
    * @return
    * @throws ServiceBizException
    * (non-Javadoc)
    * @see com.yonyou.dms.framework.service.LoginFrameworkService#checkIsCanAccess(java.lang.Long, java.lang.String)
    */

    @Override
    public boolean checkIsCanAccess(Long funcId, String requestUrl,String requestMethod) throws ServiceBizException {
        Matcher matcher = urlModalPattern.matcher(requestUrl);
        String modalName = null;
        if(matcher.find()){
            modalName = matcher.group(1);
        }
        if(modalName ==null){
            throw new UtilException("获取模块名称失败");
        }
        /**
		 * 当前登录信息
		 */
		LoginInfoDto loginInfo = ApplicationContextHelper.getBeanByType(LoginInfoDto.class);
		LoginInfoTempDto tempDto =  ApplicationContextHelper.getBeanByType(LoginInfoTempDto.class);
		if(loginInfo.getSysType()==0){
			
			String sql = "select ACTION_CODE,ACTION_NAME from tc_menu_action where menu_id = ? and ACTION_METHOD = ? and MODULE = ?";
			List<Object> queryParam = new ArrayList<>();
			queryParam.add(funcId);
			queryParam.add(requestMethod);
			queryParam.add(modalName);
			List<Map> result = DAOUtil.findAll(sql, queryParam,false);
			logger.debug("--------------DCS权限 会进这里么？");
            //如果DMS菜单权限表中没有数据 且用户配了映射 去查看DCS的用户权限表   by ceg 2017-04-25
            if(CommonUtils.isNullOrEmpty(result)){
                if(!StringUtils.isNullOrEmpty(loginInfo.getMappingAccount())){
                  //进到if里面说明点击的是DCS的页面
                    sql = " select ACTION_CODE,ACTION_NAME from tc_menu_action_dcs where func_id = ? and ACTION_METHOD = ? and MODULE = ? ";
                    logger.debug(" 查询界面是否在DCS表中存在"+sql.toString()+"      "+queryParam.toString());
                    result = DAOUtil.findAll(sql, queryParam,false); 
                    if(!CommonUtils.isNullOrEmpty(result)){//如果有对应的菜单
                        //先将loginInfoDto里面的值付给中间Dto loginInfoTempDto
                        if(StringUtils.isNull(tempDto.getDealerCode())){
                            //进来说明上一次点击的菜单不是集成页面  因此给temp赋值
                            logger.debug("点集成页面临时dto为空");
                            setLoginInfoTempDto(loginInfo);  
                        }
                        
                        //设置DCS页面的登录信息
                        setDcsLoginInfo(loginInfo);
                        List<Map> lismap = selectDcsPose(loginInfo.getUserId(),null);
                        if (!CommonUtils.isNullOrEmpty(lismap)) {
                               setDcsLoginInfoManager(lismap);
                           }
                    } 
                }      
            }else{
                //如果用户配置了映射且 tempDto中不为空则给loginInfoDto赋值             
                if(!StringUtils.isNullOrEmpty(loginInfo.getMappingAccount()) && !StringUtils.isNull(tempDto.getDealerCode())){
                    logger.debug("点非集成页面临时dto  --- "+StringUtils.isNull(tempDto.getDealerCode()) +""+tempDto.getDealerCode() );
                    //将临时dto中的值赋给loginfoDto
                    BeanUtils.copyProperties(tempDto,loginInfo);
                }
            }
           
			//判断请求的URL 中是否包含数字
			matcher = isContainNumberPattern.matcher(requestUrl);
			
			if(!CommonUtils.isNullOrEmpty(result)){
				for(Map actionMap:result){
					String actionUrl = (String) actionMap.get("ACTION_CODE");
					actionUrl = actionUrl.replaceAll("\\{[^/]+\\}", "[^/]+");
					if(FrameworkUtil.isCanAccess(requestUrl,actionUrl)){
						return true;
					}
				}
			}
		}else{
			StringBuffer sql = new StringBuffer();
			sql.append(" select ACTION_CODE,ACTION_NAME from tc_menu_action_dcs where func_id = ? and ACTION_METHOD = ? and MODULE = ? ");
			sql.append(" UNION SELECT ACTION_CODE,ACTION_NAME  FROM tc_default_action WHERE ACTION_METHOD = ? AND MODULE = ? ");
			List<Object> queryParam = new ArrayList<>();
			queryParam.add(funcId);
			queryParam.add(requestMethod);
			queryParam.add(modalName);
			queryParam.add(requestMethod);
			queryParam.add(modalName);
			List<Map> result = DAOUtil.findAll(sql.toString(), queryParam,false);
			
			//判断请求的URL 中是否包含数字
			matcher = isContainNumberPattern.matcher(requestUrl);
			
			if(!CommonUtils.isNullOrEmpty(result)){
				for(Map actionMap:result){
					String actionUrl = (String) actionMap.get("ACTION_CODE");
					actionUrl = actionUrl.replaceAll("\\{[^/]+\\}", "[^/]+");
					if(FrameworkUtil.isCanAccess(requestUrl,actionUrl)){
						return true;
					}
				}
			}
//			return true;
		}
        return false;
    }
    public void setLoginInfoTempDto(LoginInfoDto loginInfo){ 
        LoginInfoTempDto tempDto = ApplicationContextHelper.getBeanByType(LoginInfoTempDto.class);
        logger.debug("loginInfo"+loginInfo.getDealerCode());
        BeanUtils.copyProperties(loginInfo,tempDto);
        logger.debug("loginInfo"+loginInfo.getDealerCode()+"给临时存放登录信息dto赋值"+tempDto.getDealerCode());
    }
    
    public void setDcsLoginInfo(LoginInfoDto loginInfo){
        List<Object> queryParam = new ArrayList<Object>();
        StringBuilder sql = new StringBuilder("SELECT  USER_ID,COMPANY_ID,EMP_NUM,ACNT,NAME,GENDER,PHONE,USER_TYPE,PASSWORD FROM tc_user WHERE ACNT = ? and USER_STATUS = '10011001' ");
        queryParam.add(loginInfo.getMappingAccount());
        List<Map> listMap = OemDAOUtil.findAll(sql.toString(), queryParam);
        if (!CommonUtils.isNullOrEmpty(listMap)) {
            Map userInfo = listMap.get(0);
            String employeeNo = (String) userInfo.get("EMP_NUM");// 员工编号
            if (!StringUtils.isNullOrEmpty(employeeNo)) {
                loginInfo.setEmployeeNo(employeeNo);
            }
            Object companyId = (Object) userInfo.get("COMPANY_ID");// 公司ID
            if (!StringUtils.isNullOrEmpty(companyId)) {
                loginInfo.setCompanyId(Long.parseLong(companyId.toString()));
            }
            String employeeName = (String) userInfo.get("NAME");// 员工名字
            if (!StringUtils.isNullOrEmpty(employeeName)) {
                loginInfo.setUserName(employeeName);
            }
            Integer gender = (Integer) userInfo.get("GENDER");// 员工性别
            if (!StringUtils.isNullOrEmpty(gender)) {
                loginInfo.setGender(gender);
            }
            String mobile = (String) userInfo.get("PHONE");// 员工手机号
            if (!StringUtils.isNullOrEmpty(mobile)) {
                loginInfo.setMobile(mobile);
            }
            Object userType = (Object) userInfo.get("USER_TYPE");// 用户类型
            if (!StringUtils.isNullOrEmpty(userType)) {
                loginInfo.setUserType(Integer.parseInt(userType.toString()));
            }
            Object userId = userInfo.get("USER_ID");// 用户id
            if (!StringUtils.isNullOrEmpty(userId)) {
                loginInfo.setUserId(Long.parseLong(userId.toString()));
                logger.debug(" USER_ID"+loginInfo.getUserId());
            }
 
        }
        
    }
    
    public List<Map> selectDcsPose(Long userId,String id) throws ServiceBizException {

        List<Object> queryParam = new ArrayList<Object>();

        StringBuilder sql = new StringBuilder(" SELECT O.ORG_ID,  ");
        sql.append(" P.POSE_ID,  ");
        sql.append(" P.POSE_NAME,  ");
        sql.append(" P.POSE_TYPE,  ");
        sql.append(" P.POSE_BUS_TYPE,  ");
        sql.append(" O.ORG_NAME,O.ORG_DESC,O.PARENT_ORG_ID,O.DUTY_TYPE,  ");
        sql.append(" O.ORG_TYPE  ");
        sql.append(" FROM TC_USER U, TC_POSE P, TR_USER_POSE UP, TM_ORG O  ");
        sql.append(" WHERE U.USER_STATUS = '10011001'  ");
        sql.append(" AND P.POSE_STATUS = '10011001'  ");
        sql.append(" AND O.STATUS = '10011001'  ");
        sql.append(" and U.USER_ID  = ? ");
        queryParam.add(userId);
        if(!"".equals(CommonUtils.checkNull(id))){
            sql.append(" and P.POSE_ID  = ? ");
            queryParam.add(id);
        }
        sql.append(" AND U.USER_ID = UP.USER_ID  ");
        sql.append(" AND P.POSE_ID = UP.POSE_ID  ");
        sql.append(" AND P.ORG_ID = O.ORG_ID  ");
        sql.append(" ORDER BY O.ORG_NAME ASC, P.POSE_BUS_TYPE ASC, P.POSE_NAME ASC  ");
        logger.debug("查询用户的职位");
        List<Map> listMap = OemDAOUtil.findAll(sql.toString(), queryParam);

        return listMap;
    }
    
    private void setDcsLoginInfoManager(List<Map> lismap) {
        /**
        * 当前登录信息
        */
        LoginInfoDto loginInfo = ApplicationContextHelper.getBeanByType(LoginInfoDto.class);
        
        Map userInfo = lismap.get(0);

        Object orgID = (Object) userInfo.get("ORG_ID");// orgid
        logger.debug(" orgID"+orgID);
        if (!StringUtils.isNullOrEmpty(orgID)) {
            loginInfo.setOrgId(Long.parseLong(orgID.toString()));
        }
        Object poseId = (Object) userInfo.get("POSE_ID");// 职位ID
        if (!StringUtils.isNullOrEmpty(poseId)) {
           loginInfo.setPoseId(Long.parseLong(poseId.toString()));
           loginInfo.setPoseSeriesIDs(getDcsPoseSeriesIDs(loginInfo.getPoseId()));
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
               getDcsUserDearId(orgID ,poseBusType ,hm);
               loginInfo.setDealerId(Long.parseLong(hm.get("DEALER_IDS").toString()));
               loginInfo.setDealerSeriesIDs(getDcsDealerSeriesIDs(loginInfo.getDealerId()));
               loginInfo.setOemCompanyId(hm.get("OEM_COMPANY_ID"));
               if(hm.get("COMPANY") != null && !"".equals(hm.get("COMPANY")))
                   loginInfo.setCompanyId(Long.valueOf(hm.get("COMPANY")));
               loginInfo.setDealerOrgId(hm.get("DEALER_ORG_ID"));
               loginInfo.setDealerCode(hm.get("DEALER_CODE"));
           }
        
   }
    
    public void getDcsUserDearId(Object orgID, Integer poseBusType,
                              HashMap<String, String> hm) throws ServiceBizException {

        String dealer_id = "";
        String dealer_org_id = "";
        String oem_company_id = "";
        String company_id = "";
        String dealer_code = "";
        String dealer_type = "";
        switch (Integer.valueOf(poseBusType)) {
            case 10781004:
                dealer_type = 10771001 + "," + 10771004;
                break;
            case 10781005:
                dealer_type = 10771002 + "";
                break;
            case 10781006:
                dealer_type = 10771003 + "";
                break;
            default:
                break;
        }
        StringBuffer sql = new StringBuffer("");
        // modify by andy.ten@tom.com begin
        // 2010-8-2 查询条件加上STATUS=10011001
        sql.append(" SELECT D.DEALER_ID,D.DEALER_CODE,D.DEALER_ORG_ID,D.OEM_COMPANY_ID,D.COMPANY_ID FROM TM_DEALER D ");
        sql.append(" WHERE D.DEALER_ORG_ID = ? AND D.DEALER_TYPE IN (?) AND D.STATUS="
                   + DictCodeConstants.STATUS_IS_VALID);
        // end
        List<Object> params = new ArrayList<Object>();
        params.add(orgID);
        params.add(dealer_type);
        List<Map> listMap = OemDAOUtil.findAll(sql.toString(), params);
        if (listMap.size() > 0 && listMap.get(0) != null) {
            /**
             * added by andy.ten@tom.com 一个orgId对应多个dealerId ，中间用","分开 只对整车销售职位有该情况，对于售后，一个orgId还是对应一个dealerId，没有业务范围之分
             */
            Map dealer = listMap.get(0);
            oem_company_id = CommonUtils.checkNull(dealer.get("OEM_COMPANY_ID"));
            dealer_org_id = CommonUtils.checkNull(dealer.get("DEALER_ORG_ID"));
            company_id = CommonUtils.checkNull(dealer.get("COMPANY_ID"));
            dealer_code = CommonUtils.checkNull(dealer.get("DEALER_CODE"));

            if (listMap.size() == 1) {
                dealer_id = CommonUtils.checkNull(dealer.get("DEALER_ID"));
            } else {
                for (Map map : listMap) {
                    if (dealer_id.length() > 0) dealer_id += "," + CommonUtils.checkNull(dealer.get("DEALER_ID"));
                    else dealer_id = CommonUtils.checkNull(dealer.get("DEALER_ID"));
                }
            }
            // end
        }

        hm.put("DEALER_IDS", dealer_id);
        hm.put("OEM_COMPANY_ID", oem_company_id);
        hm.put("DEALER_ORG_ID", dealer_org_id);
        hm.put("COMPANY", company_id);
        hm.put("DEALER_CODE", dealer_code);
    }

    public String getDcsPoseSeriesIDs(Long poseId) throws ServiceBizException {
        String group_id = null;
        List<Object> queryParam = new ArrayList<Object>();
        String sql = " SELECT GROUP_ID FROM TC_POSE_BUSS WHERE POSE_ID = ?  ";
        queryParam.add(poseId);
        List<Map> listMap = OemDAOUtil.findAll(sql.toString(), queryParam);
        if (listMap.size() > 0) {
            for (Map map : listMap) {
                String sr = CommonUtils.checkNull(map.get("GROUP_ID"));
                if (group_id == null) {
                    group_id = sr;
                } else {
                    group_id = group_id + "," + sr;
                }
            }
        }
        return group_id;
    }

    public String getDcsDealerSeriesIDs(Long dealerId) throws ServiceBizException {
        String group_id = null;
        List<Object> queryParam = new ArrayList<Object>();
        String sql = " select group_id from TM_DEALER_BUSS  where DEALER_ID = ? ";
        queryParam.add(dealerId);
        List<Map> listMap = OemDAOUtil.findAll(sql.toString(), queryParam);
        if (listMap.size() > 0) {
            for (Map<String, Object> map : listMap) {
                String sr = CommonUtils.checkNull(map.get("GROUP_ID"));
                if (group_id == null) {
                    group_id = sr;
                } else {
                    group_id = group_id + "," + sr;
                }
            }
        }
        return group_id;
    }
}
