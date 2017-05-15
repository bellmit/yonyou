
/** 
*Copyright 2016 Yonyou Corporation Ltd. All Rights Reserved.
* This software is published under the terms of the Yonyou Software
* License version 1.0, a copy of which has been included with this
* distribution in the LICENSE.txt file.
*
* @Project Name : dms.report
*
* @File name : PartSellServiceImpl.java
*
* @Author : zhongsw
*
* @Date : 2016年9月1日
*
----------------------------------------------------------------------------------
*     Date       Who       Version     Comments
* 1. 2016年9月1日    zhongsw    1.0
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
import com.yonyou.dms.function.exception.ServiceBizException;
import com.yonyou.dms.function.utils.common.DateUtil;
import com.yonyou.dms.function.utils.common.StringUtils;

/**
* TODO description
* @author zhongsw
* @date 2016年9月1日
*/
@Service
public class PartSellServiceImpl implements PartSellService{
    
    @Override
    public PageInfoDto searchOrderPut(Map<String, String> param)throws ServiceBizException{
        StringBuilder sb=new StringBuilder("SELECT tps.SALES_PART_ID,tps.DEALER_CODE,tpsi.STORAGE_CODE,tpsi.STORAGE_POSITION_CODE,tps.SALES_PART_NO,tpsi.PART_NO,tpsi.PART_NAME,tpsi.PART_SALES_PRICE,tpsi.PART_QUANTITY,tpsi.PART_SALES_AMOUNT,tpsi.SALES_AMOUNT,tpsi.DISCOUNT,tpsi.COST_PRICE,tps.CUSTOMER_NAME,tps.RO_NO,tpi.PART_MAIN_TYPE,tpsi.FINISHED_DATE,tro.RO_STATUS,tps.REMARK,tps.CREATED_BY FROM TT_PART_SALES tps INNER JOIN TT_PART_SALES_ITEM tpsi ON tps.SALES_PART_ID=tpsi.SALES_PART_ID INNER JOIN TT_REPAIR_ORDER tro on tps.RO_NO=tro.RO_NO INNER JOIN TM_PART_INFO tpi ON tpsi.PART_NO=tpi.PART_CODE INNER JOIN tm_store ts ON ts.STORAGE_CODE=tpsi.STORAGE_CODE where 1=1 ");
        List<Object> queryParam=new ArrayList<Object>();
       
        if(!StringUtils.isNullOrEmpty(param.get("storage_code"))){
            sb.append(" and ts.STORAGE_NAME like ? ");
            queryParam.add("%"+param.get("storage_code")+"%");
        }
        if(!StringUtils.isNullOrEmpty(param.get("customerName"))){
            sb.append(" and tps.CUSTOMER_NAME like ? ");
            queryParam.add("%"+param.get("customerName")+"%");
        }
        if(!StringUtils.isNullOrEmpty(param.get("customer_type"))){
            sb.append(" and tps.CUSTOMER_TYPE = ? ");
            queryParam.add(Integer.parseInt(param.get("customer_type")));
        }
        if(!StringUtils.isNullOrEmpty(param.get("part_name"))){
            sb.append(" and tpsi.PART_NAME like ? ");
            queryParam.add("%"+param.get("part_name")+"%");
        }
        if(!StringUtils.isNullOrEmpty(param.get("ro_status"))){
            sb.append(" and tro.RO_STATUS = ? ");
            queryParam.add(Integer.parseInt(param.get("ro_status")));
        }
        if(!StringUtils.isNullOrEmpty(param.get("storage_code"))){
            sb.append(" and ts.STORAGE_NAME like ? ");
            queryParam.add("%"+param.get("storage_code")+"%");
        }
        if(!StringUtils.isNullOrEmpty(param.get("part_no"))){
            sb.append(" and tpsi.PART_NO like ? ");
            queryParam.add("%"+param.get("part_no")+"%");
        }
        if(!StringUtils.isNullOrEmpty(param.get("PartMainType"))){
            sb.append(" and tpi.PART_MAIN_TYPE = ? ");
            queryParam.add(param.get("PartMainType"));
        }
        if(!StringUtils.isNullOrEmpty(param.get("fit_model_code"))){
            sb.append(" and tpi.FIT_MODEL_CODE like ? ");
            queryParam.add("%"+param.get("fit_model_code")+"%");
        }
        if(!StringUtils.isNullOrEmpty(param.get("remark"))){
            sb.append(" and tps.REMARK like ? ");
            queryParam.add("%"+param.get("remark")+"%");
        }
        if(!StringUtils.isNullOrEmpty(param.get("startFrom"))){
            sb.append(" and tpsi.FINISHED_DATE >= ? ");
            queryParam.add(DateUtil.parseDefaultDate(param.get("startFrom")));
        }
        if(!StringUtils.isNullOrEmpty(param.get("startTo"))){
            sb.append(" and tpsi.FINISHED_DATE < ? ");
            queryParam.add(DateUtil.addOneDay(param.get("startTo")));
        }
        if(!StringUtils.isNullOrEmpty(param.get("cleanFrom"))){
            sb.append(" and tpsi.FINISHED_DATE >= ? ");//结清日期开始时间
            queryParam.add(DateUtil.parseDefaultDate(param.get("cleanFrom")));
        }
        if(!StringUtils.isNullOrEmpty(param.get("cleanTo"))){
            sb.append(" and tpsi.FINISHED_DATE < ? ");//结清日期结束时间
            queryParam.add(DateUtil.addOneDay(param.get("cleanTo")));
        }
        if(!StringUtils.isNullOrEmpty(param.get("endFrom"))){
            sb.append(" and tpsi.FINISHED_DATE >= ? ");//结算日期开始时间
            queryParam.add(DateUtil.parseDefaultDate(param.get("endFrom")));
        }
        if(!StringUtils.isNullOrEmpty(param.get("endTo"))){
            sb.append(" and tpsi.FINISHED_DATE < ? ");//结算日期结束时间
            queryParam.add(DateUtil.addOneDay(param.get("endTo")));
        }
        return DAOUtil.pageQuery(sb.toString(), queryParam);
    }

    
    /**导出
    * @author zhongsw
    * @date 2016年9月2日
    * @param queryParam
    * @return
    * @throws ServiceBizException
    * (non-Javadoc)
    * @see com.yonyou.dms.report.service.impl.PartSellService#queryUsersForExport(java.util.Map)
    */
    	
    @Override
    public List<Map> queryUsersForExport(Map<String, String> queryParam) throws ServiceBizException {
        List<Object> params = new ArrayList<Object>();
        String sql = getQuerySql(queryParam,params);
        List<Map> resultList = DAOUtil.findAll(sql,params);
        return resultList;
    }
    
    /**
     * 导出
    * 封装SQL 语句
    * @author zhongsw
    * @date 2016年7月20日
    * @param queryParam
    * @param params
    * @return
     */
    private String getQuerySql(Map<String,String> queryParam,List<Object> params) throws ServiceBizException{
        StringBuilder sqlSb = new StringBuilder("SELECT tps.SALES_PART_ID,tps.DEALER_CODE,tpsi.STORAGE_CODE,tpsi.STORAGE_POSITION_CODE,tps.SALES_PART_NO,tpsi.PART_NO,tpsi.PART_NAME,tpsi.PART_SALES_PRICE,tpsi.PART_QUANTITY,tpsi.PART_SALES_AMOUNT,tpsi.SALES_AMOUNT,tpsi.DISCOUNT,tpsi.COST_PRICE,tps.CUSTOMER_NAME,tps.RO_NO,tpi.PART_MAIN_TYPE,tpsi.FINISHED_DATE,tro.RO_STATUS,tps.REMARK,tps.CREATED_BY FROM TT_PART_SALES tps INNER JOIN TT_PART_SALES_ITEM tpsi ON tps.SALES_PART_ID=tpsi.SALES_PART_ID INNER JOIN TT_REPAIR_ORDER tro on tps.RO_NO=tro.RO_NO INNER JOIN TM_PART_INFO tpi ON tpsi.PART_NO=tpi.PART_CODE INNER JOIN tm_store ts ON ts.STORAGE_CODE=tpsi.STORAGE_CODE where 1=1 ");
        return sqlSb.toString();
    }
}
