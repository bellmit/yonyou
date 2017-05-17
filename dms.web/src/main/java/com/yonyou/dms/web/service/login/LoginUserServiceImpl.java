
/** 
 *Copyright 2016 Yonyou Corporation Ltd. All Rights Reserved.
 * This software is published under the terms of the Yonyou Software
 * License version 1.0, a copy of which has been included with this
 * distribution in the LICENSE.txt file.
 *
 * @Project Name : dms.common
 *
 * @File name : SystemUserServiceImpl.java
 *
 * @Author : yll
 *
 * @Date : 2016年10月8日
 *
----------------------------------------------------------------------------------
 *     Date       Who       Version     Comments
 * 1. 2016年10月8日    yll    1.0
 *
 *
 *
 *
----------------------------------------------------------------------------------
 */

package com.yonyou.dms.web.service.login;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.javalite.activejdbc.Base;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.yonyou.dms.common.domains.PO.basedata.EntityDealerChangePO;
import com.yonyou.dms.framework.DAO.DAOUtil;
import com.yonyou.dms.framework.DAO.OemDAOUtil;
import com.yonyou.dms.framework.domain.LoginInfoDto;
import com.yonyou.dms.framework.domain.UserAccessInfoDto;
import com.yonyou.dms.framework.interceptors.ExceptionControllerAdvice;
import com.yonyou.dms.framework.util.acl.AccessUrlUtils;
import com.yonyou.dms.framework.util.bean.ApplicationContextHelper;
import com.yonyou.dms.function.common.DictCodeConstants;
import com.yonyou.dms.function.common.OemDictCodeConstants;
import com.yonyou.dms.function.exception.ServiceBizException;
import com.yonyou.dms.function.utils.common.CommonUtils;
import com.yonyou.dms.function.utils.common.StringUtils;
import com.yonyou.dms.function.utils.security.MD5Util;

/**
 * @author yll
 * @date 2016年10月8日
 */
@Service
public class LoginUserServiceImpl implements LoginUserService {

    private static final Logger logger = LoggerFactory.getLogger(ExceptionControllerAdvice.class);

    /**
     * 登录校验
     * 
     * @author yll
     * @date 2016年10月10日
     * @param dealerCode
     * @param userCode (non-Javadoc)
     * @see com.yonyou.dms.web.service.login.LoginUserService#logCheck(java.lang.String, java.lang.String)
     */
    @SuppressWarnings({ "rawtypes" })
    @Override
    public Map logCheck(String dealerCode, String userCode, String password) {
        // 判断用户是否存在
        Map information = new HashMap();
        StringBuilder sql = new StringBuilder("SELECT tu.USER_CODE,tdb.DEALER_CODE,te.GENDER,te.EMPLOYEE_NO,tu.PASSWORD,te.EMPLOYEE_NAME,te.MOBILE, tdb.DEALER_SHORTNAME, tdb.DEALER_NAME, tdb.DEALER_ID, tu.USER_ID,tu.USER_CODE,org.ORG_CODE,org.ORG_NAME,org.ORGDEPT_ID FROM tm_employee te INNER JOIN tm_user tu ON te.EMPLOYEE_NO = tu.EMPLOYEE_NO and te.DEALER_CODE = tu.DEALER_CODE LEFT  JOIN tm_dealer_basicinfo tdb ON tdb.DEALER_CODE=te.DEALER_CODE left join TM_ORGANIZATION org on org.ORG_CODE=tu.ORG_CODE and tdb.DEALER_CODE = org.DEALER_CODE WHERE te.IS_VALID='"+DictCodeConstants.STATUS_IS_YES+"' AND tu.USER_STATUS='"+DictCodeConstants.DICT_IN_USE_START+"'");
        List<Object> queryParam = new ArrayList<Object>();
        sql.append("  and tu.USER_CODE  = ?");
        queryParam.add(userCode);
        sql.append(" and tdb.DEALER_CODE = ?");
        queryParam.add(dealerCode);
        List<Map> listMap = DAOUtil.findAll(sql.toString(), queryParam);

        // 如果查询通过
        if (!CommonUtils.isNullOrEmpty(listMap)) {
            Map userInfo = listMap.get(0);
           /*  String passwordMD5=(String) userInfo.get("PASSWORD");//密码 
             boolean validation=MD5Util.validPassword(password, passwordMD5);
             if(validation==false){ throw new
              ServiceBizException("密码不正确"); 
             }*/
             
        /*   System.out.println("输入密码====" +password);
              String passwordMD5=(String) userInfo.get("PASSWORD");//密码 boolean
           System.out.println("数据库保存密码===="+passwordMD5);
              Boolean  validation=MD5Util.validPassword(password, passwordMD5); 
              if(validation==false){ 
                  throw new ServiceBizException("密码不正确");
              }
            */
            // 获取用户信息实体类
            LoginInfoDto loginInfo = ApplicationContextHelper.getBeanByType(LoginInfoDto.class);

            // 获取用户url权限实体类
            UserAccessInfoDto powerUrl = ApplicationContextHelper.getBeanByType(UserAccessInfoDto.class);
            //查询用户是否配置了DCS的映射关系 (用于DCS页面集成)
            List<Map> mappingAccount = queryMappingAccount(dealerCode,userCode);
            if(!CommonUtils.isNullOrEmpty(mappingAccount)){
                Map mapping = mappingAccount.get(0);
                loginInfo.setMappingAccount((String)mapping.get("TARGET_USER"));
                //查询出映射账号对应的user_id
                List<Object> queryUserParam = new ArrayList<Object>();
                StringBuilder sqlUser = new StringBuilder("SELECT  USER_ID,COMPANY_ID,EMP_NUM,ACNT,NAME,GENDER,PHONE,USER_TYPE,PASSWORD FROM tc_user WHERE ACNT = ? and USER_STATUS = '10011001' ");
                queryUserParam.add(loginInfo.getMappingAccount());
                List<Map> listUser = DAOUtil.findAll(sqlUser.toString(), queryUserParam);
                if(!CommonUtils.isNullOrEmpty(listUser)){
                    Map DcsUser = listUser.get(0);
                    //查询出映射账号对应的职位
                    List<Map> lisPose = selectPose((Long)DcsUser.get("USER_ID"),null);
                    if(!CommonUtils.isNullOrEmpty(lisPose)){
                        loginInfo.setDcsPoseId(lisPose);
                    } 
                }
             
            }
            
            if (!StringUtils.isNullOrEmpty(userCode)) {
                loginInfo.setUserCode(userCode);
            }

            String employeeNo = (String) userInfo.get("EMPLOYEE_NO");// 员工编号
            if (!StringUtils.isNullOrEmpty(employeeNo)) {
                loginInfo.setEmployeeNo(employeeNo);
            }
            String employeeName = (String) userInfo.get("EMPLOYEE_NAME");// 员工名字
            if (!StringUtils.isNullOrEmpty(employeeName)) {
                loginInfo.setUserName(employeeName);
            }
            Integer gender = (Integer) userInfo.get("GENDER");// 员工性别
            if (!StringUtils.isNullOrEmpty(gender)) {
                loginInfo.setGender(gender);
            }
            String mobile = (String) userInfo.get("MOBILE");// 员工手机号
            if (!StringUtils.isNullOrEmpty(mobile)) {
                loginInfo.setMobile(mobile);
            }
            String dealerName = (String) userInfo.get("DEALER_NAME");// 经销商名字
            if (!StringUtils.isNullOrEmpty(dealerName)) {
                loginInfo.setDealerName(dealerName);
            }
            String dealerShortName = (String) userInfo.get("DEALER_SHORTNAME");// 经销商短名
            if (!StringUtils.isNullOrEmpty(dealerShortName)) {
                loginInfo.setDealerShortName(dealerShortName);
            }
            Object userId = userInfo.get("USER_ID");// 用户id
            if (!StringUtils.isNullOrEmpty(userId)) {
                loginInfo.setUserId(Long.parseLong(userId.toString()));
                //查大客户权限
                StringBuilder sqlS = new StringBuilder("select * from tm_user_CTRL where DEALER_CODE= ? AND USER_ID= ? AND CTRL_CODE= '80900000' ");
                List<Object> bigparams = new ArrayList<Object>();
                bigparams.add(dealerCode);
                bigparams.add(Long.parseLong(userId.toString()));
                List<Map> biglist = Base.findAll(sqlS.toString(), bigparams.toArray());
                if(biglist!=null&&biglist.size()>0){
                    loginInfo.setHaveBigCustomer(12781001);
                }else{
                    loginInfo.setHaveBigCustomer(12781002);
                }
            }
            Object dealerID = userInfo.get("DEALER_ID");// 经销商id
            if (!StringUtils.isNullOrEmpty(dealerID)) {
                loginInfo.setDealerId(Long.parseLong(dealerID.toString()));
            }
            // 组织orgCode
            String orgCode = (String) userInfo.get("ORG_CODE");// 经销商id
            if (!StringUtils.isNullOrEmpty(orgCode)) {
                loginInfo.setOrgCode(orgCode);
            }
            // 组织orgName
            String orgName = (String) userInfo.get("ORG_NAME");// 经销商id
            if (!StringUtils.isNullOrEmpty(orgName)) {
                loginInfo.setOrgName(orgName);
            }
            // 组织orgID
            Object organizationId = userInfo.get("ORGDEPT_ID");// 经销商id
            if (!StringUtils.isNullOrEmpty(organizationId)) {
                loginInfo.setOrgId(Long.parseLong(organizationId.toString()));
            }
            // 设置经销商信息及账号信息
            loginInfo.setDealerCode(dealerCode);
            loginInfo.setUserAccount(userCode);

            Long userIdPower = Long.parseLong(userId.toString());
            logger.debug("------------------获取用户数据权限");
            Map<String, Object> powerMap = getDataPower(userIdPower, dealerCode);
    
            Map<String, Map<String, Object>> ctlDataAction = getActionCtrl(userIdPower);
            loginInfo.setCtlDataAction(ctlDataAction);
            // 获取该用户接待权限
            loginInfo.setReceptionMaintain((Map) powerMap.get("receptionMaintain"));
            // 获取该用户配件权限
            loginInfo.setPartsOption((Map) powerMap.get("partsOption"));
            // 获取该用户结算权限
            loginInfo.setSettleAccounts((Map) powerMap.get("settleAccounts"));
            // 获取该用户系统权限
            loginInfo.setSystemAttention((Map) powerMap.get("settleAccounts"));
            // 获取客户权限
            loginInfo.setCustomerOption((List) powerMap.get("preferentialMode"));
            // 获取整车权限
            loginInfo.setVehicleOption((List) powerMap.get("vehicleOption"));
            // 2 获取该用户菜单和按钮权限
            logger.debug("------------------获取用户菜单权限");
            powerUrl.setUserResouces(getActionUrl(userIdPower, dealerCode));
        } else {
            throw new ServiceBizException("账号不存在");
        }
        return information;
    }

    /**
     * 登录校验
     * 
     * @author 夏威
     * @date 2017年2月7日
     * @param dealerCode
     * @param userCode (non-Javadoc)
     * @see com.yonyou.dms.web.service.login.LoginUserService#oemLogCheck(java.lang.String, java.lang.String)
     */
    @SuppressWarnings({ "rawtypes" })
    @Override
    public Map oemLogCheck(String dealerCode, String userCode, String password) {
        // 判断用户是否存在
        Map information = new HashMap();
        List<Object> queryParam = new ArrayList<Object>();
        StringBuilder sql = new StringBuilder("SELECT  USER_ID,COMPANY_ID,EMP_NUM,ACNT,NAME,GENDER,PHONE,USER_TYPE,PASSWORD FROM tc_user WHERE ACNT = ? and USER_STATUS = '10011001' ");
        queryParam.add(userCode);
        List<Map> listMap = DAOUtil.findAll(sql.toString(), queryParam);

        // 如果查询通过
        if (!CommonUtils.isNullOrEmpty(listMap)) {
            Map userInfo = listMap.get(0);
            /*
             * String passwordMD5=(String) userInfo.get("PASSWORD");//密码 boolean
             * if(password.equals(passwordMD5) continue;
             * else{	
             * validation=MD5Util.validPassword(password, passwordMD5); if(validation==false){ throw new
             * ServiceBizException("密码不正确"); }
             * }
             */
            // 获取用户信息实体类
            LoginInfoDto loginInfo = ApplicationContextHelper.getBeanByType(LoginInfoDto.class);

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
            }
            // 设置经销商信息及账号信息
            loginInfo.setDealerCode(dealerCode);
            loginInfo.setUserAccount(userCode);

            // 车厂端用户需要选择职位后才能加载菜单
        } else {
            throw new ServiceBizException("账号不存在");
        }
        return information;
    }

    /**
     * 获取到用户的数据权限的url列表
     * 
     * @author yll
     * @date 2016年11月17日
     * @param dealerCode
     * @param userCode
     * @see com.yonyou.dms.web.service.login.LoginUserService#getDataPower(java.lang.Long, java.lang.String)
     */
    @SuppressWarnings({ "unchecked", "rawtypes" })
    public Map<String, Object> getDataPower(Long USER_ID, String dealerCode) {
        List<Object> sqlParams = new ArrayList<Object>();
        StringBuilder powerSql = new StringBuilder("SELECT  CTRL.CTRL_CODE  code , type ,DEALER_CODE FROM  tm_user_ctrl CTRL WHERE CTRL.USER_ID =? ");
        sqlParams.add(USER_ID);
        List<Map> PowerList = DAOUtil.findAll(powerSql.toString(), sqlParams);
        Iterator<Map> powerIterator = PowerList.iterator();
        Map<String, Object> powerString = new HashMap<String, Object>();
        Map<String, List> reusltPower = new HashMap<String, List>();
        // 五种数据权限的TYPE数组
        String[] dataUrlsList = { DictCodeConstants.RECEPTION_MAINTAIN_OPTION + "", DictCodeConstants.PARTS_OPTION + "",
                                  DictCodeConstants.SETTLE_ACCOUNTS_OPTION + "",
                                  DictCodeConstants.SYSTEM_ATTENTION_OPTION + "",
                                  DictCodeConstants.CUSTOMER_OPTION + "", DictCodeConstants.VEHICLE_OPTION + "" };
        for (String urlsList : dataUrlsList) {
            reusltPower.put(urlsList, new ArrayList());
        }
        while (powerIterator.hasNext()) {
            Map power = powerIterator.next();
            String powerType = power.get("type").toString();
            for (String string : dataUrlsList) {
                if (string.equals(powerType)) {
                    reusltPower.get(powerType).add(power.get("code"));
                    break;
                }
            }
        }
        StringBuilder sb = new StringBuilder("SELECT OPTION_CODE as CODE_ID FROM TM_AUTH_OPTION WHERE OPTION_TYPE=?");
        // 获取配件参数权限配置下的所有类型
        List<Map> pl = Base.findAll(sb.toString(), DictCodeConstants.PARTS_OPTION);
        // 获取维修参数权限配置下的所有类型
        List<Map> rl = Base.findAll(sb.toString(), DictCodeConstants.RECEPTION_MAINTAIN_OPTION);
        List<Map> selt = Base.findAll(sb.toString(), DictCodeConstants.SETTLE_ACCOUNTS_OPTION);

        List<Map> sys = Base.findAll(sb.toString(), DictCodeConstants.SYSTEM_ATTENTION_OPTION);
        powerString.put("receptionMaintain", AccessUrlUtils.iteratorToMap(reusltPower.get(dataUrlsList[0]), rl));
        powerString.put("partsOption", AccessUrlUtils.iteratorToMap(reusltPower.get(dataUrlsList[1]), pl));
        powerString.put("settleAccounts", AccessUrlUtils.iteratorToMap(reusltPower.get(dataUrlsList[2]), selt));
        powerString.put("systemAttention", AccessUrlUtils.iteratorToMap(reusltPower.get(dataUrlsList[3]), sys));
        powerString.put("customerOption", reusltPower.get(dataUrlsList[4]));
        powerString.put("vehicleOption", reusltPower.get(dataUrlsList[4]));
        return powerString;
    }

    /**
     * 获取该用户的url权限
     * 
     * @author yll
     * @date 2016年11月17日
     * @param dealerCode
     * @param userCode
     * @see com.yonyou.dms.web.service.login.LoginUserService#getActionUrl(java.lang.Long, java.lang.String)
     */
    @SuppressWarnings("rawtypes")
    public String[] getActionUrl(Long USER_ID, String dealerCode) {
        LoginInfoDto loginInfo = ApplicationContextHelper.getBeanByType(LoginInfoDto.class);
        List<Object> sqlParams = new ArrayList<Object>();
        StringBuilder sqlJC = new StringBuilder();
        StringBuilder actionSql = new StringBuilder(" SELECT ACTION_METHOD method , MODULE model ,ACTION_CODE code ,MENU_CURING_ID FROM tc_menu_action  WHERE ACTION_CTL = 10041002 AND MENU_ID IN (");
        actionSql.append("SELECT MENU_ID  FROM  tm_user_menu WHERE  USER_ID =?");
        sqlParams.add(USER_ID);
        actionSql.append(" AND DEALER_CODE=?");
        sqlParams.add(dealerCode);
        actionSql.append(" ) ");
        actionSql.append(" UNION  SELECT ACTION_METHOD method ,  ACTION.MODULE model,ACTION.ACTION_CODE code,ACTION.MENU_CURING_ID MENU_CURING_ID FROM tm_user_menu MENU ,tc_menu_action ACTION ,tm_user_menu_action TUM  ");
        actionSql.append("WHERE MENU.USER_MENU_ID = TUM.USER_MENU_ID AND MENU.USER_ID=?");
        sqlParams.add(USER_ID);
        actionSql.append(" AND DEALER_CODE=?");
        sqlParams.add(dealerCode);
        actionSql.append("  AND TUM.MENU_CURING_ID= ACTION.MENU_CURING_ID ");
        if(!StringUtils.isNullOrEmpty(loginInfo.getMappingAccount())){
            StringBuilder sql = new StringBuilder();
            sqlJC.append(" "+actionSql+" ");  
            sqlJC.append(" union all ");
            sql.append(" SELECT DISTINCT TF.FUNC_ID "); 
            sql.append(" FROM TR_ROLE_FUNC TRF,TC_FUNC TF,TR_ROLE_POSE TRP  "); 
            sql.append(" WHERE TRF.FUNC_ID = TF.FUNC_ID AND TRF.ROLE_ID = TRP.ROLE_ID  AND (TF.FUNC_LEVEL=1003 OR TF.FUNC_LEVEL=1001)  AND TF.IS_INTEGRATION=12781001  AND TRP.POSE_ID in ( "); 
            for(int i=0;i<loginInfo.getDcsPoseId().size();i++){
                Map pose = loginInfo.getDcsPoseId().get(i);
                Long poseId = (Long)pose.get("POSE_ID");
                if(i!=loginInfo.getDcsPoseId().size()-1){
                    sql.append(" "+poseId+",");  
                }else{
                    sql.append(" "+poseId+")");  
                }
                
            }
            sql.append(" UNION "); 
            sql.append(" SELECT DISTINCT TF.FUNC_ID "); 
            sql.append(" FROM TR_ROLE_FUNC TRF,TC_FUNC TF ,TC_FUNC TF2,TR_ROLE_POSE TRP "); 
            sql.append(" WHERE TRF.FUNC_ID = TF.FUNC_ID AND TF.PAR_FUNC_ID = TF2.FUNC_ID AND TRF.ROLE_ID = TRP.ROLE_ID AND  TF.FUNC_LEVEL=1003 AND TF.IS_INTEGRATION=12781001 AND TRP.POSE_ID in ( "); 
            for(int i=0;i<loginInfo.getDcsPoseId().size();i++){
                Map pose = loginInfo.getDcsPoseId().get(i);
                Long poseId = (Long)pose.get("POSE_ID");
                if(i!=loginInfo.getDcsPoseId().size()-1){
                    sql.append(" "+poseId+",");  
                }else{
                    sql.append(" "+poseId+")");  
                }
                
            }
            sql.append(" UNION "); 
            sql.append(" SELECT DISTINCT TF.FUNC_ID "); 
            sql.append(" FROM TR_ROLE_FUNC TRF,TC_FUNC TF ,TC_FUNC TF2,TC_FUNC TF3,TR_ROLE_POSE TRP "); 
            sql.append(" WHERE TRF.FUNC_ID = TF.FUNC_ID AND TF.PAR_FUNC_ID = TF2.FUNC_ID AND TRF.ROLE_ID = TRP.ROLE_ID AND TF2.PAR_FUNC_ID = TF3.FUNC_ID  AND TF.FUNC_LEVEL=1003 AND TF.IS_INTEGRATION=12781001 AND TRP.POSE_ID in ( "); 
            for(int i=0;i<loginInfo.getDcsPoseId().size();i++){
                Map pose = loginInfo.getDcsPoseId().get(i);
                Long poseId = (Long)pose.get("POSE_ID");
                if(i!=loginInfo.getDcsPoseId().size()-1){
                    sql.append(" "+poseId+",");  
                }else{
                    sql.append(" "+poseId+")");  
                }
                
            }
            sqlJC.append(" SELECT ACTION_METHOD method , MODULE model ,ACTION_CODE CODE ,MENU_CURING_ID FROM TC_MENU_ACTION_DCS TMA WHERE EXISTS (  ");
            sqlJC.append(" SELECT 1 FROM ("+ sql+") trf  ");
            sqlJC.append(" WHERE tma.FUNC_ID = trf.FUNC_ID ) ");
        }else{
            sqlJC=actionSql;
        }
        logger.debug("回去用户菜单权限sql"+sqlJC+"      /n "+sqlParams.toString());
        List<Map> actionUrlList = Base.findAll(sqlJC.toString(), sqlParams.toArray());
        return AccessUrlUtils.iteratorToArray(actionUrlList);
    }

    /**
     * 获取职位列表
     */
    public List<Map> queryPose(Long userId) throws ServiceBizException {
        StringBuffer sql = new StringBuffer();
        List<Object> sqlParams = new ArrayList<Object>();
//        sql.append(" SELECT DISTINCT tp.POSE_ID,tp.POSE_NAME FROM tr_user_pose tup,tc_pose tp WHERE tup.pose_id = tp.POSE_ID AND user_id = ? AND POSE_STATUS = '10011001' ");
        sql.append("SELECT O.ORG_ID, \n");
		sql.append("       P.POSE_ID, \n");
		sql.append("       P.POSE_NAME, \n");
		sql.append("       P.POSE_TYPE, \n");
		sql.append("       P.POSE_BUS_TYPE, \n");
		sql.append("       O.ORG_NAME, \n");
		sql.append("       O.ORG_TYPE \n");
		sql.append("  FROM TC_USER U, TC_POSE P, TR_USER_POSE UP, TM_ORG O \n");
		sql.append(" WHERE U.USER_STATUS = '" + OemDictCodeConstants.STATUS_ENABLE + "' \n");
		sql.append("   AND P.POSE_STATUS = '" + OemDictCodeConstants.STATUS_ENABLE + "' \n");
		sql.append("   AND O.STATUS = '" + OemDictCodeConstants.STATUS_ENABLE + "' \n");
		sql.append("   AND U.USER_ID = ? \n");
		
//		if (!"".equals(poseId)) {
//			sql.append("   AND P.POSE_ID = '" + poseId + "' \n");
//		}
		
		sql.append("   AND U.USER_ID = UP.USER_ID \n");
		sql.append("   AND P.POSE_ID = UP.POSE_ID \n");
		sql.append("   AND P.ORG_ID = O.ORG_ID \n");
		sql.append(" ORDER BY O.ORG_NAME ASC, P.POSE_BUS_TYPE ASC, P.POSE_NAME ASC \n");
        sqlParams.add(userId);
        List<Map> actionUrlList = Base.findAll(sql.toString(), sqlParams.toArray());
        return actionUrlList;
    }

    /**
     * 获取车厂用户职位及其他信息加载到Seesion
     */
    public List<Map> selectPose(Long userId,String id) throws ServiceBizException {

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
        List<Map> listMap = OemDAOUtil.findAll(sql.toString(), queryParam);

        return listMap;
    }

    /**
     * 车厂端获取职位数据权限
     */
    public String getPoseSeriesIDs(Long poseId) throws ServiceBizException {
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

    /**
     * 车长端获取经销商信息
     */
    public void getUserDearId(Object orgID, Integer poseBusType,
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

    /**
     * 车厂端登录获取用户数据信息
     */
    public String getDealerSeriesIDs(Long dealerId) throws ServiceBizException {
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

    /**
     * 加载OEM用户信息
     */
    public String getOemUserArea(Long poseId) throws ServiceBizException {
        StringBuffer sql = new StringBuffer("");
        List<Object> param = new ArrayList<Object>();
        sql.append("select ba.area_id\n");
        sql.append("  from tm_pose_business_area pa, tm_business_area ba\n");
        sql.append(" where pa.area_id = ba.area_id\n");
        sql.append("   and pa.pose_id = ? ");
        sql.append("   and ba.status =" + DictCodeConstants.STATUS_IS_VALID);
        param.add(poseId);
        List<Map> list = OemDAOUtil.findAll(sql.toString(), param);
        String s = "(";
        for (int i = 0; i < list.size(); i++) {
            Map map = (Map) list.get(i);
            s = s + map.get("AREA_ID");
            if (i < list.size() - 1) {
                s = s + ",";
            }
        }
        return s + ")";
    }

    /**
     * 车厂用户获取url权限实体类
     */
    public void getOemActionUrl() throws ServiceBizException {

        // 获取用户url权限实体类
        UserAccessInfoDto powerUrl = ApplicationContextHelper.getBeanByType(UserAccessInfoDto.class);
        // 获取当前用户信息
        LoginInfoDto loginInfo = ApplicationContextHelper.getBeanByType(LoginInfoDto.class);
        List<Object> sqlParams = new ArrayList<Object>();
        StringBuilder sql = new StringBuilder();
        sql.append(" SELECT DISTINCT TF.FUNC_ID "); 
		sql.append(" FROM TR_ROLE_FUNC TRF,TC_FUNC TF,TR_ROLE_POSE TRP  "); 
		sql.append(" WHERE TRF.FUNC_ID = TF.FUNC_ID AND TRF.ROLE_ID = TRP.ROLE_ID  AND (TF.FUNC_LEVEL=1003 OR TF.FUNC_LEVEL=1001) AND TRP.POSE_ID =? "); 
		sql.append(" UNION "); 
		sql.append(" SELECT DISTINCT TF.FUNC_ID "); 
		sql.append(" FROM TR_ROLE_FUNC TRF,TC_FUNC TF ,TC_FUNC TF2,TR_ROLE_POSE TRP "); 
		sql.append(" WHERE TRF.FUNC_ID = TF.FUNC_ID AND TF.PAR_FUNC_ID = TF2.FUNC_ID AND TRF.ROLE_ID = TRP.ROLE_ID AND  TF.FUNC_LEVEL=1003  AND TRP.POSE_ID =? "); 
		sql.append(" UNION "); 
		sql.append(" SELECT DISTINCT TF.FUNC_ID "); 
		sql.append(" FROM TR_ROLE_FUNC TRF,TC_FUNC TF ,TC_FUNC TF2,TC_FUNC TF3,TR_ROLE_POSE TRP "); 
		sql.append(" WHERE TRF.FUNC_ID = TF.FUNC_ID AND TF.PAR_FUNC_ID = TF2.FUNC_ID AND TRF.ROLE_ID = TRP.ROLE_ID AND TF2.PAR_FUNC_ID = TF3.FUNC_ID  AND TF.FUNC_LEVEL=1003  AND TRP.POSE_ID =? "); 
		StringBuilder actionSql = new StringBuilder("SELECT ACTION_METHOD method , MODULE model ,ACTION_CODE CODE ,MENU_CURING_ID,TMA.FUNC_ID FROM TC_MENU_ACTION_DCS TMA WHERE EXISTS (  ");
        actionSql.append(" SELECT 1 FROM ("+ sql+") trf  ");
        actionSql.append(" WHERE tma.FUNC_ID = trf.FUNC_ID ) ");
        sqlParams.add(loginInfo.getPoseId());
        sqlParams.add(loginInfo.getPoseId());
        sqlParams.add(loginInfo.getPoseId());
        System.out.println(actionSql.toString() +sqlParams.toString());
        List<Map> actionUrlList = Base.findAll(actionSql.toString(), sqlParams.toArray());
        // 2 获取该用户菜单和按钮权限
        powerUrl.setUserResouces(AccessUrlUtils.iteratorToArray(actionUrlList));
    }

    /**
     * 获取下级部门信息
     */
    public String getXjbm(Long orgID, Long companyId) throws ServiceBizException {
        String bm = orgID + ",";
        String sql = " select td.ORG_ID,td.PARENT_ORG_ID from tm_org td where " + "  td.company_id = '" + companyId
                     + "' and  td.PARENT_ORG_ID = '" + orgID + "' ";
        List<Map> zDept = OemDAOUtil.findAll(sql, null);
        if (zDept != null && zDept.size() > 0) {
            for (int i = 0; i < zDept.size(); i++) {
                bm += zDept.get(i).get("ORG_ID") + ",";
            }
        }
        return bm.substring(0, bm.length() - 1);
    }

    /**
     * 用户菜单控制权限
     * 
     * @author zhanshiwei
     * @date 2017年2月16日
     * @param USER_ID
     * @param dealerCode
     * @return
     */

    public Map<String, Map<String, Object>> getActionCtrl(Long USER_ID) {
        Map<String, Map<String, Object>> ctlDataAction = new HashMap<>();
        List<Object> paramList = new ArrayList<Object>();
        StringBuilder sql = new StringBuilder();
        sql.append("  SELECT tma.MENU_ID,tma.ACTION_CODE,tu_a.MENU_CURING_ID,tum.DEALER_CODE   from tm_user_menu tum  ");
        sql.append("  LEFT JOIN tc_menu_action tma on tum.MENU_ID=tma.MENU_ID  ");
        sql.append("  LEFT JOIN tm_user_menu_action tu_a on tma.MENU_CURING_ID=tu_a.MENU_CURING_ID  ");
        sql.append("  where tma.ACTION_METHOD NOT in ('GET','PUT','POST','DELETE')    ");
        sql.append("        AND USER_ID=?   ");
        paramList.add(USER_ID);
        List<Map> ctrlDataList = DAOUtil.findAll(sql.toString(), paramList);
        Iterator<Map> ctlIterator = ctrlDataList.iterator();
        while (ctlIterator.hasNext()) {
            /*
             * Map power = ctlIterator.next(); String actCode = power.get("ACTION_CODE").toString(); String[]
             * resrouceSplits = actCode.split("/");
             */
            Map dr = ctlIterator.next();
            String mapKey = dr.get("MENU_ID").toString();
            String actCode = dr.get("ACTION_CODE").toString();
            if (!ctlDataAction.containsKey(mapKey)) {
                ctlDataAction.put(mapKey, new HashMap());
            }
            Map dataRange = ctlDataAction.get(mapKey);
            if (!StringUtils.isNullOrEmpty(actCode)) {
                String[] resrouceSplits = actCode.split("/");
                if(!StringUtils.isNullOrEmpty(dr.get("MENU_CURING_ID"))){
                    dataRange.put(resrouceSplits[0], true);
                }else{
                    dataRange.put(resrouceSplits[0], false);
                }
                
            }
            ctlDataAction.put(mapKey, dataRange);
        }
        return ctlDataAction;
    }

    
    /**
    * @author ceg
    * @date 2017年4月11日
    * @param dealerCode
    * @return
    * (non-Javadoc)
    * @see com.yonyou.dms.web.service.login.LoginUserService#getEntityCode(java.lang.String)
    */
    	
    @Override
    public String getEntityCode(String dealerCode) {
        String entityCode = null;
        List<Object> queryList = new ArrayList<Object>();
        queryList.add(dealerCode);
        queryList.add(12781001);
        EntityDealerChangePO po = EntityDealerChangePO.findFirst("dealer_code = ? AND is_valid = ? ",queryList.toArray());
        entityCode = po.getString("ENTITY_CODE");
        System.out.println(entityCode+"------------------------");
        return entityCode;
    }
    
    public List<Map> queryMappingAccount(String dealerCode, String userCode){
        StringBuilder sql = new StringBuilder(" select EMPLOYEE_NO,DEALER_CODE,USER_CODE,TARGET_SYSTEM,TARGET_USER,TARGET_USER_NAME from TT_LOGIN_USER_MAP where 1=1 ");
        List<Object> queryParam = new ArrayList<Object>();
        sql.append("  and USER_CODE  = ?");
        queryParam.add(userCode);
        sql.append(" and DEALER_CODE = ?");
        queryParam.add(dealerCode);
        List<Map> listMap = DAOUtil.findAll(sql.toString(), queryParam);
        return listMap;
        
    }
}
