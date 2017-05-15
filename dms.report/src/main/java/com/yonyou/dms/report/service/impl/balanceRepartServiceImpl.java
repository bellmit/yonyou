
/** 
*Copyright 2016 Yonyou Corporation Ltd. All Rights Reserved.
* This software is published under the terms of the Yonyou Software
* License version 1.0, a copy of which has been included with this
* distribution in the LICENSE.txt file.
*
* @Project Name : dms.report
*
* @File name : balanceRepartService.java
*
* @Author : ZhengHe
*
* @Date : 2016年10月26日
*
----------------------------------------------------------------------------------
*     Date       Who       Version     Comments
* 1. 2016年10月26日    ZhengHe    1.0
*
*
*
*
----------------------------------------------------------------------------------
*/
	
package com.yonyou.dms.report.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.yonyou.dms.framework.DAO.DAOUtil;
import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.function.common.DictCodeConstants;
import com.yonyou.dms.function.exception.ServiceBizException;
import com.yonyou.dms.function.utils.common.DateUtil;
import com.yonyou.dms.function.utils.common.StringUtils;

/**
* 结算Service
* @author ZhengHe
* @date 2016年10月26日
*/
@Service
@SuppressWarnings("rawtypes")
public class balanceRepartServiceImpl implements balanceRepartService{

    
    /**
    * 查询结算单
    * @author ZhengHe
    * @date 2016年10月26日
    * @param queryParam
    * @return
    * @throws ServiceBizException
    * (non-Javadoc)
    * @see com.yonyou.dms.report.service.impl.balanceRepartService#queryBalance(java.util.Map)
    */
    	
    @Override
    public PageInfoDto queryBalance(Map<String, String> queryParam) throws ServiceBizException {
        String businessType=queryParam.get("BUSINESS_TYPE");
        StringBuffer sqlsb=new StringBuffer("SELECT"); 
        sqlsb.append(" tba.BALANCE_ID,");
        sqlsb.append("tbp.BALA_PAYOBJ_ID,");                  
        sqlsb.append("tba.DEALER_CODE,"); 
        sqlsb.append(" tba.BALANCE_NO,"); 
        sqlsb.append(" tba.BALANCE_HANDLER,"); 
        sqlsb.append(" tbp.PAYMENT_OBJECT_CODE,"); 
        sqlsb.append(" tbp.PAYMENT_OBJECT_NAME,"); 
        sqlsb.append("tbp.RECEIVABLE_AMOUNT,"); 
        sqlsb.append("tbp.RECEIVED_AMOUNT,");
        sqlsb.append("tbp.NOT_RECEIVED_AMOUNT,");
        sqlsb.append("tba.BALANCE_TIME,");
        sqlsb.append("tba.PAY_OFF,");
        sqlsb.append(" tba.SQUARE_DATE,");
        sqlsb.append(" tv.LICENSE,");
        sqlsb.append(" tmo.OWNER_NAME,");
        sqlsb.append("tcd.DERATE_AMOUNT,");
        sqlsb.append(businessType+" AS BUSINESS_TYPE,");
        //维修
        if(StringUtils.isEquals(DictCodeConstants.RO_TYPE_REPAIR, businessType)){
            sqlsb.append("tro.RO_NO AS BUSINESS_NO,");
        }
        //销售
        if(StringUtils.isEquals(DictCodeConstants.RO_TYPE_SALES, businessType)){
            sqlsb.append("tro.SALES_PART_ID AS BUSINESS_NO,");
        }
        //调拨
        if(StringUtils.isEquals(DictCodeConstants.RO_TYPE_TRANSFERS, businessType)){
            sqlsb.append("tro.ALLOCATE_OUT_ID AS BUSINESS_NO,");
        }
        sqlsb.append(" tro.RO_STATUS ");
        sqlsb.append("FROM");
        sqlsb.append("    TT_BALANCE_ACCOUNTS tba ");
        sqlsb.append("    LEFT JOIN TT_BALANCE_PAYOBJ tbp ");
        sqlsb.append("ON tba.BALANCE_ID = tbp.BALANCE_ID ");
        sqlsb.append("LEFT JOIN TT_REPAIR_ORDER tro ");
        sqlsb.append("ON tba.RO_ID = tro.RO_ID ");
        sqlsb.append("LEFT JOIN TM_VEHICLE tv ");
        sqlsb.append("ON tv.VEHICLE_ID = tro.VEHICLE_ID ");
        sqlsb.append("LEFT JOIN TT_PART_SALES tps ");
        sqlsb.append("ON tba.SALES_PART_ID = tps.SALES_PART_ID ");
        sqlsb.append("LEFT JOIN TT_PART_ALLOCATE_OUT tpo ");
        sqlsb.append(" ON tba.ALLOCATE_OUT_ID = tpo.ALLOCATE_OUT_ID ");
        sqlsb.append("LEFT JOIN TM_OWNER tmo ");
        sqlsb.append("ON tro.OWNER_ID = tmo.OWNER_ID ");
        sqlsb.append("LEFT JOIN TT_CHARGE_DERATE tcd ");
        sqlsb.append("ON tcd.BALA_PAYOBJ_ID = tbp.BALA_PAYOBJ_ID");
        sqlsb.append(" where 1=1 ");
        sqlsb.append(" and tbp.PAYMENT_OBJECT_TYPE="+DictCodeConstants.CUSTOMER_OWNER);
        List<Object> queryList=new ArrayList<Object>();
        if(!StringUtils.isNullOrEmpty(queryParam.get("BUSINESS_NO"))){
            if(StringUtils.isEquals(DictCodeConstants.RO_TYPE_REPAIR, businessType)){
                sqlsb.append(" and tro.RO_STATUS="+DictCodeConstants.SET_CREATE_STATUS);
                sqlsb.append(" and tro.RO_NO like ?");
                queryList.add("%"+queryParam.get("BUSINESS_NO")+"%");
            }
            //销售
            if(StringUtils.isEquals(DictCodeConstants.RO_TYPE_SALES, businessType)){
                sqlsb.append(" and tro.SALES_PART_ID like ? ");
                queryList.add("%"+queryParam.get("BUSINESS_NO")+"%");
            }
            //调拨
            if(StringUtils.isEquals(DictCodeConstants.RO_TYPE_TRANSFERS, businessType)){
                sqlsb.append(" and tro.ALLOCATE_OUT_ID like ? ");
                queryList.add("%"+queryParam.get("BUSINESS_NO")+"%");
            }
        }
        if(!StringUtils.isNullOrEmpty(queryParam.get("LICENSE"))){
            sqlsb.append(" and tv.LICENSE like ?");
            queryList.add("%"+queryParam.get("LICENSE")+"%");
        }
        if(!StringUtils.isNullOrEmpty(queryParam.get("BALANCE_NO"))){
            sqlsb.append(" and tba.BALANCE_NO like ?");
            queryList.add("%"+queryParam.get("BALANCE_NO")+"%");
        }
        if(!StringUtils.isNullOrEmpty(queryParam.get("BALANCE_TIME_FROM"))){
            sqlsb.append(" and tba.BALANCE_TIME >=?");
            queryList.add(DateUtil.parseDefaultDate(queryParam.get("BALANCE_TIME_FROM")));
        }
        if(!StringUtils.isNullOrEmpty(queryParam.get("BALANCE_TIME_TO"))){
            sqlsb.append(" and tba.BALANCE_TIME <?");
            queryList.add(DateUtil.addOneDay(queryParam.get("BALANCE_TIME_TO")));
        }
        if(!StringUtils.isNullOrEmpty(queryParam.get("IS_RED"))){
            sqlsb.append(" and tba.IS_RED =?");
            if(StringUtils.isEquals(DictCodeConstants.STATUS_IS_NOT, queryParam.get("IS_RED"))){             
                queryList.add(DictCodeConstants.STATUS_IS_YES);
            }else{
                queryList.add(DictCodeConstants.STATUS_IS_NOT);
            }
        }
        PageInfoDto  pageInfoDto=DAOUtil.pageQuery(sqlsb.toString(), queryList);
        return pageInfoDto;
    }

}
