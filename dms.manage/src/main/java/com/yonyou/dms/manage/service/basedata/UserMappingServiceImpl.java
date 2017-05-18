package com.yonyou.dms.manage.service.basedata;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.yonyou.dms.common.domains.PO.basedata.TtLoginUserMapPO;
import com.yonyou.dms.framework.DAO.DAOUtil;
import com.yonyou.dms.framework.DAO.OemDAOUtil;
import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.framework.domain.LoginInfoDto;
import com.yonyou.dms.framework.util.FrameworkUtil;
import com.yonyou.dms.framework.util.bean.ApplicationContextHelper;
import com.yonyou.dms.function.exception.ServiceBizException;
import com.yonyou.dms.function.utils.common.CommonUtils;
import com.yonyou.dms.manage.domains.DTO.basedata.UserMappingDto;


@Service
public class UserMappingServiceImpl implements UserMappingService{

    
    /**
    * @author ceg
    * @date 2017年5月11日
    * @param queryParam
    * @return 查询用户映射
    * @throws ServiceBizException
    * (non-Javadoc)
    * @see com.yonyou.dms.manage.service.basedata.UserMappingService#queryUserMapping(java.util.Map)
    */
    	
    @Override
    public PageInfoDto queryUserMapping(Map<String, String> queryParam) throws ServiceBizException {
        LoginInfoDto loginInfo = ApplicationContextHelper.getBeanByType(LoginInfoDto.class);
        List<Object> params = new ArrayList<Object>();
        String dealerCode = loginInfo.getDealerCode();
        String userCode = loginInfo.getUserCode();
        String empNo = loginInfo.getEmployeeNo();
        StringBuilder sql = new StringBuilder();
        sql.append(" select * from  TT_LOGIN_USER_MAP a where 1=1  ");
        if(null!=dealerCode && !"".equals(dealerCode) && dealerCode.trim().length()>0)
        {
             sql.append(" and a.dealer_Code='"+dealerCode.trim()+"' ");
        }
        if(null !=empNo && !"".equals(empNo))
        {   if(empNo.indexOf("9")!=0)
            {
                if(null!=userCode && !"".equals(userCode) && userCode.trim().length()>0)
                {
                     sql.append(" and a.user_code='"+userCode.trim()+"' ");
                }
            }
        }
        
//        if(null!=targetSystem && !"".equals(targetSystem) && targetSystem.trim().length()>0)
//        {
//             sql.append(" and a.target_system='"+targetSystem+"'");
//        }
        sql.append(" order by CREATED_AT desc");
        PageInfoDto pageInfoDto = DAOUtil.pageQuery(sql.toString(),params);
        return pageInfoDto;
    }

    @Override
    public Map getUserMappingById(String userCode) throws ServiceBizException {
        List<Object> param = new ArrayList<Object>();
        StringBuilder sql = new StringBuilder();
        sql.append(" select * from TT_LOGIN_USER_MAP where user_code=? ");
        param.add(userCode);
        Map first = DAOUtil.findFirst(sql.toString(), param);
        return first;
    }

    @Override
    public List<Map> getSystem() throws ServiceBizException {
        StringBuilder sql = new StringBuilder();
        List<Object>  queryParam =  new ArrayList<Object>();
        LoginInfoDto loginInfo = ApplicationContextHelper.getBeanByType(LoginInfoDto.class);
        sql.append(" SELECT ACNT as TARGET_USER FROM tm_entity_dealer_change te INNER JOIN tm_dealer td ON te.dealer_code = td.dealer_code ");
        sql.append(" INNER JOIN tc_user tu ON td.company_id=tu.company_id WHERE te.entity_code=? AND te.is_valid=? ");
        queryParam.add(loginInfo.getDealerCode());
        queryParam.add(12781001);
        List<Map> result = OemDAOUtil.findAll(sql.toString(), queryParam);
        return result; 
    }

    
    /**
    * @author  修改用户映射
    */
    	
    @Override
    public void modifyUserMapping(String userCode, UserMappingDto userMappingDto) throws ServiceBizException {
       
        TtLoginUserMapPO userMapPo = TtLoginUserMapPO.findByCompositeKeys( FrameworkUtil.getLoginInfo().getDealerCode(),userCode);
        userMapPo.setString("TARGET_SYSTEM", userMappingDto.getTargetSystem());
        userMapPo.setString("TARGET_USER", userMappingDto.getTargetUser());
        userMapPo.setString("TARGET_PASSWORD",userMappingDto.getTargetPassword());
        userMapPo.saveIt();
        
        
    }

    @Override
    public PageInfoDto queryUsers(Map<String, String> queryParam) throws ServiceBizException {
        LoginInfoDto loginInfo = ApplicationContextHelper.getBeanByType(LoginInfoDto.class);
        String dealerCode = loginInfo.getDealerCode();
        String userCode = loginInfo.getUserCode();
        String empNo = loginInfo.getEmployeeNo();
        StringBuilder sql = new StringBuilder();
        List<Object> param = new ArrayList<Object>();
        sql.append(" select DEALER_CODE,EMPLOYEE_NO,USER_CODE from  tm_user a where 1=1  ");
        if(null!=dealerCode && !"".equals(dealerCode) && dealerCode.trim().length()>0)
        {
             sql.append(" and a.dealer_Code='"+dealerCode.trim()+"' ");
        }
        if(null !=empNo && !"".equals(empNo))
        {   if(empNo.indexOf("9")!=0)
            {
                if(null!=userCode && !"".equals(userCode) && userCode.trim().length()>0)
                {
                     sql.append(" and a.user_code='"+userCode.trim()+"' ");
                }
            }
        }
        PageInfoDto pageInfoDto = DAOUtil.pageQuery(sql.toString(), param);
        return pageInfoDto;
    }

    @Override
    public String addUserMapping(UserMappingDto userMappingDto) throws ServiceBizException {
        TtLoginUserMapPO userMappingPo = new TtLoginUserMapPO();
        String userCode = userMappingDto.getUserCode();
        String dealerCode =  FrameworkUtil.getLoginInfo().getDealerCode();
        StringBuilder sql = new StringBuilder(" select * from tt_login_user_map where dealer_code=? and user_code=? ");
        List<Object> param = new ArrayList<Object>();
        param.add(dealerCode);
        param.add(userCode);
        List list = OemDAOUtil.findAll(sql.toString(), param);
        if(!CommonUtils.isNullOrEmpty(list)){
            throw new ServiceBizException(" 账号已经存在！ ");
        }
        userMappingPo.setString("DEALER_CODE",dealerCode);
        userMappingPo.setString("EMPLOYEE_NO", userMappingDto.getEmployeeNo());
        userMappingPo.setString("USER_CODE", userMappingDto.getUserCode());
        userMappingPo.setString("TARGET_SYSTEM",userMappingDto.getTargetSystem() );
        userMappingPo.setString("TARGET_USER", userMappingDto.getTargetUser());
        userMappingPo.setString("TARGET_PASSWORD",userMappingDto.getTargetPassword());
        userMappingPo.setString("TARGET_ROLE", userMappingDto.getTargetRole());
        userMappingPo.setString("TARGET_USER_NAME",userMappingDto.getTargetUser());
        userMappingPo.setInteger("IS_VALID",12781001);
        userMappingPo.saveIt();
        return userCode;
    }

    @Override
    public void deleteUserMapping(String userCode) throws ServiceBizException {
        TtLoginUserMapPO userMapPo = TtLoginUserMapPO.findByCompositeKeys(FrameworkUtil.getLoginInfo().getDealerCode(),userCode);
        userMapPo.delete();
        
    }

}
