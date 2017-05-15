/** 
*Copyright 2017 Yonyou Corporation Ltd. All Rights Reserved.
* This software is published under the terms of the Yonyou Software
* License version 1.0, a copy of which has been included with this
* distribution in the LICENSE.txt file.
 * @Author : zhengcong
 *
 * @Date : 2017年5月9日
 *
----------------------------------------------------------------------------------
 *     Date       Who       Version     Comments
 * 1. 2017年5月9日    zhengcong    1.0
*
*
*
*
----------------------------------------------------------------------------------
*/
package com.yonyou.dms.repair.service.maintenancePackage;


import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.yonyou.dms.framework.DAO.DAOUtil;
import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.framework.util.FrameworkUtil;
import com.yonyou.dms.function.common.DictCodeConstants;
import com.yonyou.dms.function.exception.ServiceBizException;
import com.yonyou.dms.function.utils.common.StringUtils;


/**
 * 
*保养套餐管理serviceimpl 
 * @author zhengcong
 * @date 2017年5月9日
 */
@Service
@SuppressWarnings({})
public class MaintenancePackageServiceImpl implements MaintenancePackageService{
	
	private StringBuffer sqlsb;
	private List<Object> params ;
    /**
     * 根据传入信息查询
 * @author zhengcong
 * @date 2017年5月9日
     */
    @Override
    public PageInfoDto query(Map<String, String> queryParam)throws ServiceBizException {
    	
    	 
        sqlsb = new StringBuffer(
                "select DEALER_CODE,PACKAGE_CODE,PACKAGE_NAME,MODEL_YEAR,SERIES_CODE,EXHAUST_QUANTITY,  ");
        sqlsb.append(" MAINTENANCE_MIL_INTERVAL,OIL_TYPE,PACKAGE_PRICE,CREATED_AT,IS_WX_MAINTAINPACKAGE  ");
        sqlsb.append(" from TT_WX_BOOKING_MAINTAIN_PACKAGE ");
        sqlsb.append("   where 1=1 ");
        sqlsb.append("  and IS_VALID=" + DictCodeConstants.DICT_IS_YES);
        sqlsb.append("  and DEALER_CODE='" + FrameworkUtil.getLoginInfo().getDealerCode() + "' ");
    
        params= new ArrayList<Object>();
       
        if(!StringUtils.isNullOrEmpty(queryParam.get("packageName"))) {
            sqlsb.append(" and PACKAGE_NAME like ?");
            params.add("%"+queryParam.get("packageName")+"%");
        }
        if(!StringUtils.isNullOrEmpty(queryParam.get("seriesCode"))) {
            sqlsb.append(" and SERIES_CODE = ?");
            params.add(queryParam.get("seriesCode"));
        }
        if(!StringUtils.isNullOrEmpty(queryParam.get("modelYear"))) {
            sqlsb.append(" and MODEL_YEAR = ?");
            params.add(queryParam.get("modelYear"));
        }
        if(!StringUtils.isNullOrEmpty(queryParam.get("isWxMaintainpackage"))) {
            sqlsb.append(" and IS_WX_MAINTAINPACKAGE = ?");
            params.add(queryParam.get("isWxMaintainpackage"));
        }
       
        
        PageInfoDto pageInfoDto = DAOUtil.pageQuery(sqlsb.toString(),params);
        return pageInfoDto;
    }
    
    /**
     * 查询项目明细
     * @author zhengcong
     * @date 2017年5月9日
     */
    @Override
    public PageInfoDto queryLabourDtail(String PACKAGE_CODE)throws ServiceBizException {
        StringBuffer sqlsb = new StringBuffer("SELECT DEALER_CODE,ASSIGN_LABOUR_HOUR,CLAIM_LABOUR,CREATED_BY,CREATED_AT,DISCOUNT,D_KEY, ");
        sqlsb.append(" ITEM_ID,LABOUR_AMOUNT,LABOUR_CODE,LABOUR_NAME,PACKAGE_CODE,REPAIR_TYPE_CODE,RO_LABOUR_TYPE, ");
        sqlsb.append(" UPDATED_BY,UPDATED_AT,VER,STD_LABOUR_HOUR,LABOUR_PRICE,LABOUR_DEAL_TYPE  ");
        sqlsb.append(" FROM TT_WX_MAINTAIN_PACKAGE_LABOUR  ");
        sqlsb.append(" WHERE 1=1   ");
        sqlsb.append(" AND D_KEY=" + DictCodeConstants.D_KEY);
        sqlsb.append(" AND DEALER_CODE='" + FrameworkUtil.getLoginInfo().getDealerCode() + "' ");
        sqlsb.append(" AND PACKAGE_CODE='" + PACKAGE_CODE + "'  ");
        PageInfoDto pageInfoDto = DAOUtil.pageQuery(sqlsb.toString(),null);
        return pageInfoDto;
        	

    }      

    
    /**
     * 查询配件明细
     * @author zhengcong
     * @date 2017年5月9日
     */
    @Override
    public PageInfoDto queryPartDtail(String PACKAGE_CODE)throws ServiceBizException {
        StringBuffer sqlsb = new StringBuffer("SELECT DEALER_CODE,CREATED_BY,CREATED_AT,DISCOUNT,D_KEY, ");
        sqlsb.append(" ITEM_ID,LABOUR_CODE,PACKAGE_CODE, ");
        sqlsb.append(" PART_NAME,PART_NO,STORAGE_CODE,UNIT_NAME,UPDATED_BY,UPDATED_AT,VER,PART_SALES_AMOUNT, ");
        sqlsb.append(" PART_SALES_PRICE,IS_MAIN,PART_QUANTITY,PART_DEAL_TYPE  ");
        sqlsb.append(" FROM TT_WX_MAINTAIN_PACKAGE_PART  ");
        sqlsb.append(" WHERE 1=1   ");
        sqlsb.append(" AND D_KEY=" + DictCodeConstants.D_KEY);
        sqlsb.append(" AND DEALER_CODE='" + FrameworkUtil.getLoginInfo().getDealerCode() + "' ");
        sqlsb.append(" AND PACKAGE_CODE='" + PACKAGE_CODE + "'  ");
        PageInfoDto pageInfoDto = DAOUtil.pageQuery(sqlsb.toString(),null);
		return pageInfoDto;

    }      


}
