
/** 
*Copyright 2017 Yonyou Corporation Ltd. All Rights Reserved.
* This software is published under the terms of the Yonyou Software
* License version 1.0, a copy of which has been included with this
* distribution in the LICENSE.txt file.
*
* @Project Name : dms.manage
*
* @File name : PlatformServiceImpl.java
*
* @Author : Administrator
*
* @Date : 2017年2月8日
*
----------------------------------------------------------------------------------
*     Date       Who       Version     Comments
* 1. 2017年2月8日    Administrator    1.0
*
*
*
*
----------------------------------------------------------------------------------
*/
	
package com.yonyou.dms.manage.service.basedata.platform;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.yonyou.dms.common.domains.PO.basedata.PotentialCusPO;
import com.yonyou.dms.framework.DAO.DAOUtil;
import com.yonyou.dms.framework.DAO.DAOUtilGF;
import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.framework.domain.LoginInfoDto;
import com.yonyou.dms.framework.util.FrameworkUtil;
import com.yonyou.dms.framework.util.Utility;
import com.yonyou.dms.framework.util.bean.ApplicationContextHelper;
import com.yonyou.dms.function.common.DictCodeConstants;
import com.yonyou.dms.function.exception.ServiceBizException;
import com.yonyou.dms.manage.domains.DTO.platform.PlatformDto;

/**
* TODO description
* @author Administrator
* @date 2017年2月8日
*/
@Service
public class PlatformServiceImpl implements PlatformService {
    

    /**
     * 查询建档超过90天未维护客户
     * 
     * @author xhy
     * @date 2017年2月10日
     * @param param
     * @return
     */
    @Override
    public PageInfoDto queryPotentialCustomer(Map<String, String> queryParams) throws ServiceBizException {
        StringBuffer sql = new StringBuffer("");
            sql.append(" select '' AS IS_SELECT,TP.DEALER_CODE,tu.USER_ID,tu.USER_NAME,TP.CUSTOMER_NO,TP.CUSTOMER_NAME,TP.CONTACTOR_MOBILE,TP.INTENT_LEVEL,TP.SOLD_BY,TP.FOUND_DATE,TP.EXPECT_TIMES_RANGE,TI.INTENT_SERIES, ");
            sql.append(" TP.EXPECT_DATE  FROM TM_POTENTIAL_CUSTOMER  TP LEFT JOIN (  ");
            sql.append(" SELECT a.customer_no , a.DEALER_CODE ,b.INTENT_SERIES ,A.INTENT_ID FROM TT_CUSTOMER_INTENT A LEFT JOIN TT_CUSTOMER_INTENT_DETAIL B ON ");
            sql.append(" A.DEALER_CODE = B.DEALER_CODE AND A.INTENT_ID = B.INTENT_ID and b.IS_MAIN_MODEL = 12781001  ");
            sql.append(" ) TI ON TP.DEALER_CODE=TI.DEALER_CODE AND TP.CUSTOMER_NO = TI.CUSTOMER_NO  and TP.INTENT_ID=TI.INTENT_ID ");
            sql.append(" LEFT JOIN TM_USER tu ON tu.USER_ID = TP.SOLD_BY AND tu.DEALER_CODE=TP.DEALER_CODE ");
            sql.append(" WHERE  DATEDIFF(now(),TP.FOUND_DATE)>"+90+" and TP.INTENT_LEVEL in('13101001',   ");
            sql.append("  '13101002','13101003','13101004','13101005') AND TP.EXPECT_TIMES_RANGE is null ");
            
            Utility.sqlToEquals(sql, queryParams.get("soldBy"), "SOLD_BY", "TP"); 
            Utility.sqlToLike(sql, queryParams.get("intentLevel"), "INTENT_LEVEL", "TP");
            Utility.sqlToLike(sql, queryParams.get("customerName"), "CUSTOMER_NAME", "TP");
            
            
            List<Object> queryList = new ArrayList<Object>();
            PageInfoDto id = DAOUtil.pageQuery(sql.toString(), queryList);   
            
      
        return id;
    }
    

    /**
     * 查询下订超过60天仍未交车的订单
     * 
     * @author xhy
     * @date 2017年2月10日
     * @param param
     * @return
     */
    @Override
    public PageInfoDto querySalesOrder(Map<String, String> queryParams) throws ServiceBizException {
        // TODO Auto-generated method stub
        return null;
    }

  

    /**
     * 修改
     * 
     * @author xhy
     * @date 2017年2月10日
     * @param platformDto
     * @return
     */
    @Override
    public void modifyExceptTimesRange(PlatformDto platformDto) throws ServiceBizException {
        String[] ids = platformDto.getNoList().split(",");
        LoginInfoDto loginInfo = ApplicationContextHelper.getBeanByType(LoginInfoDto.class);
        for (int i = 0; i < ids.length; i++) {
            String no = ids[i];
           
            PotentialCusPO potentialCusPo=PotentialCusPO.findByCompositeKeys(loginInfo.getDealerCode(),no);
           // CustomerTrackingPO traPo = CustomerTrackingPO.findById(id);
            potentialCusPo.setLong("EXPECT_TIMES_RANGE", platformDto.getExceptTimesRange());// 预计成交时段
            if(platformDto.getExceptTimesRange()==Long.parseLong(DictCodeConstants.DICT_TIME_EXPECT_TIMES_RANGE_ONE)){
                potentialCusPo.setInteger("INTENT_LEVEL", Long.parseLong(DictCodeConstants.DICT_INTENT_LEVEL_H));// 意见级别
            }else if(platformDto.getExceptTimesRange()==Long.parseLong(DictCodeConstants.DICT_TIME_EXPECT_TIMES_RANGE_TWO)){
                potentialCusPo.setInteger("INTENT_LEVEL", Long.parseLong(DictCodeConstants.DICT_INTENT_LEVEL_A ));// 意见级别
            }else if(platformDto.getExceptTimesRange()==Long.parseLong(DictCodeConstants.DICT_TIME_EXPECT_TIMES_RANGE_THREE)){
                potentialCusPo.setInteger("INTENT_LEVEL", Long.parseLong(DictCodeConstants.DICT_INTENT_LEVEL_B));// 意见级别
            }else if(platformDto.getExceptTimesRange()==Long.parseLong(DictCodeConstants.DICT_TIME_EXPECT_TIMES_RANGE_FOUR)){
                potentialCusPo.setInteger("INTENT_LEVEL", Long.parseLong(DictCodeConstants.DICT_INTENT_LEVEL_C));// 意见级别
            }else if(platformDto.getExceptTimesRange()==Long.parseLong(DictCodeConstants.DICT_TIME_EXPECT_TIMES_RANGE_FIVE)){
                potentialCusPo.setInteger("INTENT_LEVEL", Long.parseLong(DictCodeConstants.DICT_INTENT_LEVEL_N));// 意见级别
            }
            
            potentialCusPo.saveIt();
        }
        
    }
    

    /**
     * 查询工作平台
     * 
     * @author xhy
     * @date 2017年2月10日
     * @param 
     * @return
     */
    @SuppressWarnings("rawtypes")
	@Override
    public Map queryPlatformCount() throws ServiceBizException {
    	Long userid = FrameworkUtil.getLoginInfo().getUserId();
		String orgCode = FrameworkUtil.getLoginInfo().getOrgCode();
		String dealerCode = FrameworkUtil.getLoginInfo().getDealerCode();
        StringBuffer sql = new StringBuffer("");
        sql.append(" SELECT COUNT(CUSTOMER_NO) AS CUSTOMER_COUNT ,pc.DEALER_CODE FROM ");
        sql.append(" (SELECT '' AS IS_SELECT,TP.DEALER_CODE,tu.USER_ID,tu.USER_NAME,TP.CUSTOMER_NO,TP.CUSTOMER_NAME,TP.CONTACTOR_MOBILE,TP.INTENT_LEVEL,TP.SOLD_BY,TP.FOUND_DATE,TP.EXPECT_TIMES_RANGE,TI.INTENT_SERIES,"); 
        sql.append(" TP.EXPECT_DATE  FROM TM_POTENTIAL_CUSTOMER  TP LEFT JOIN ( "); 
        sql.append(" SELECT a.customer_no , a.DEALER_CODE ,b.INTENT_SERIES ,A.INTENT_ID FROM TT_CUSTOMER_INTENT A LEFT JOIN TT_CUSTOMER_INTENT_DETAIL B ON ");
        sql.append(" A.DEALER_CODE = B.DEALER_CODE AND A.INTENT_ID = B.INTENT_ID AND b.IS_MAIN_MODEL = 12781001  ");
        sql.append(" ) TI ON TP.DEALER_CODE=TI.DEALER_CODE AND TP.CUSTOMER_NO = TI.CUSTOMER_NO  AND TP.INTENT_ID=TI.INTENT_ID ");
        sql.append(" LEFT JOIN TM_USER tu ON tu.USER_ID = TP.SOLD_BY AND tu.DEALER_CODE=TP.DEALER_CODE ");
        sql.append(" WHERE  DATEDIFF(NOW(),TP.FOUND_DATE)>"+90+" AND TP.INTENT_LEVEL IN('13101001', ");  
        sql.append(" '13101002','13101003','13101004','13101005') AND TP.EXPECT_TIMES_RANGE IS NULL) pc");
        DAOUtilGF.getOwnedByStr("pc", userid, orgCode,  "1050", dealerCode);
        
       /* sql.append(" select count(CUSTOMER_NO) AS CUSTOMER_COUNT  from (");
        sql.append(" (select '' AS IS_SELECT,TP.DEALER_CODE,tu.USER_ID,tu.USER_NAME,TP.CUSTOMER_NO,TP.CUSTOMER_NAME,TP.CONTACTOR_MOBILE,TP.INTENT_LEVEL,TP.SOLD_BY,TP.FOUND_DATE,TP.EXPECT_TIMES_RANGE,TI.INTENT_SERIES, ");
        sql.append(" TP.EXPECT_DATE  FROM TM_POTENTIAL_CUSTOMER  TP LEFT JOIN (  ");
        sql.append(" SELECT a.customer_no , a.DEALER_CODE ,b.INTENT_SERIES ,A.INTENT_ID FROM TT_CUSTOMER_INTENT A LEFT JOIN TT_CUSTOMER_INTENT_DETAIL B ON ");
        sql.append(" A.DEALER_CODE = B.DEALER_CODE AND A.INTENT_ID = B.INTENT_ID and b.IS_MAIN_MODEL = 12781001  ");
        sql.append(" ) TI ON TP.DEALER_CODE=TI.DEALER_CODE AND TP.CUSTOMER_NO = TI.CUSTOMER_NO  and TP.INTENT_ID=TI.INTENT_ID ");
        sql.append(" LEFT JOIN TM_USER tu ON tu.USER_ID = TP.SOLD_BY AND tu.DEALER_CODE=TP.DEALER_CODE ");
        sql.append(" WHERE  DATEDIFF(now(),TP.FOUND_DATE)>"+90+" and TP.INTENT_LEVEL in('13101001',   ");
        sql.append("  '13101002','13101003','13101004','13101005') AND TP.EXPECT_TIMES_RANGE is null) pc ");
        sql.append(" )");*/
        
       
        
        List<Object> queryList = new ArrayList<Object>();
          
        
  
        return DAOUtil.findFirst(sql.toString(), queryList); 
        
       
    }

}
