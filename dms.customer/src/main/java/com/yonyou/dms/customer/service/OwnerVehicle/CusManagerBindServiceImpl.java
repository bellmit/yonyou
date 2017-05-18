/** 
*Copyright 2017 Yonyou Corporation Ltd. All Rights Reserved.
* This software is published under the terms of the Yonyou Software
* License version 1.0, a copy of which has been included with this
* distribution in the LICENSE.txt file.
*
* @Project Name : dms.repair
*
* @File name : LabourPriceService1.java
*
 * @Author : zhengcong
 *
 * @Date : 2017年3月27日
 *
----------------------------------------------------------------------------------
 *     Date       Who       Version     Comments
 * 1. 2017年3月27日    zhengcong    1.0
*
*
*
*
----------------------------------------------------------------------------------
*/
package com.yonyou.dms.customer.service.OwnerVehicle;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.yonyou.dms.common.domains.DTO.basedata.ChargeTypeDTO;
import com.yonyou.dms.common.domains.PO.basedata.ChargeTypePO;
import com.yonyou.dms.common.domains.PO.basedata.VsStockLogPO;
import com.yonyou.dms.common.domains.PO.basedata.VsStockPO;
import com.yonyou.dms.common.domains.PO.stockmanage.VehiclePO;
import com.yonyou.dms.commonSales.domains.PO.stockmanage.SalesOrderPO;
import com.yonyou.dms.framework.DAO.DAOUtil;
import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.framework.util.FrameworkUtil;
import com.yonyou.dms.function.common.DictCodeConstants;
import com.yonyou.dms.function.exception.ServiceBizException;
import com.yonyou.dms.function.utils.common.DateUtil;
import com.yonyou.dms.function.utils.common.StringUtils;


/**
 * 
*收费类别serviceimpl 
 * @author zhengcong
 * @date 2017年3月27日
 */
@Service
@SuppressWarnings({"rawtypes"})
public class CusManagerBindServiceImpl implements CusManagerBindService{
	
	private StringBuffer sqlsb;
	private List<Object> params ;
    /**
     * 根据传入信息查询
 * @author zhengcong
 * @date 2017年3月27日
     */
    @Override
    public PageInfoDto queryAll(Map<String, String> queryParam)throws ServiceBizException {
        sqlsb = new StringBuffer(
                "SELECT A.DEALER_CODE,12781002 AS IS_SELECTED,D.BRAND_NAME,E.SERIES_NAME,F.MODEL_NAME,A.VIN,  ");
        sqlsb.append(" G.USER_NAME SERVICE_ADVISOR,A.SALES_DATE, C.OWNER_NO,C.OWNER_NAME, ");
        sqlsb.append(" CASE ");
        sqlsb.append("   WHEN ( C.OWNER_PROPERTY = 11901001 ) ");
        sqlsb.append("     THEN C.DECISION_MARKER_PHONE ");
        sqlsb.append("   WHEN ( C.OWNER_PROPERTY = 11901002 ) ");
        sqlsb.append("     THEN C.PHONE ");
        sqlsb.append("   ELSE NULL ");
        sqlsb.append(" END PHONE, ");
        sqlsb.append(" CASE ");
        sqlsb.append("   WHEN ( C.OWNER_PROPERTY = 11901001 ) ");
        sqlsb.append("     THEN C.DECISION_MARKER_MOBILE ");
        sqlsb.append("   WHEN ( C.OWNER_PROPERTY = 11901002 ) ");
        sqlsb.append("     THEN C.MOBILE ");
        sqlsb.append("   ELSE NULL ");
        sqlsb.append(" END MOBILE, T.USER_NAME AS LAST_SERVICE_ADVISOR ");
        sqlsb.append(" FROM TM_VEHICLE A ");
        sqlsb.append(" LEFT JOIN TM_OWNER C ");
        sqlsb.append(" ON A.DEALER_CODE=C.DEALER_CODE ");
        sqlsb.append(" AND A.OWNER_NO=C.OWNER_NO ");
        sqlsb.append(" LEFT JOIN ( ");
        sqlsb.append("           SELECT T1.DEALER_CODE,T1.VIN,T1.SERVICE_ADVISOR,T2.USER_NAME ");
        sqlsb.append("     FROM TT_REPAIR_ORDER T1 ");
        sqlsb.append("     LEFT JOIN TM_USER T2 ");
        sqlsb.append("     ON T1.DEALER_CODE=T2.DEALER_CODE ");
        sqlsb.append("     AND T1.SERVICE_ADVISOR=T2.USER_CODE ");
        sqlsb.append("     WHERE (T1.DEALER_CODE,T1.VIN,T1.RO_CREATE_DATE) IN ( ");
        sqlsb.append("         SELECT DEALER_CODE,VIN,MAX(RO_CREATE_DATE) ");
        sqlsb.append("           FROM TT_REPAIR_ORDER ");
        sqlsb.append("           GROUP BY DEALER_CODE,VIN) ) T ");
        sqlsb.append(" ON A.DEALER_CODE=T.DEALER_CODE ");
        sqlsb.append(" AND A.VIN=T.VIN ");
        sqlsb.append(" LEFT JOIN TM_BRAND D ");
        sqlsb.append(" ON A.DEALER_CODE=D.DEALER_CODE ");
        sqlsb.append(" AND A.BRAND=D.BRAND_CODE ");
        sqlsb.append(" LEFT JOIN TM_SERIES E ");
        sqlsb.append(" ON A.DEALER_CODE=E.DEALER_CODE ");
        sqlsb.append(" AND A.SERIES=E.SERIES_CODE ");
        sqlsb.append(" LEFT JOIN TM_MODEL F ");
        sqlsb.append(" ON A.DEALER_CODE=F.DEALER_CODE ");
        sqlsb.append(" AND A.MODEL=F.MODEL_CODE ");
        sqlsb.append(" LEFT JOIN TM_USER G ");
        sqlsb.append(" ON A.DEALER_CODE=G.DEALER_CODE ");
        sqlsb.append(" AND A.SERVICE_ADVISOR=G.USER_CODE ");
        sqlsb.append(" WHERE 1=1 ");

        params= new ArrayList<Object>();

        
        if(!StringUtils.isNullOrEmpty(queryParam.get("BRAND"))) {
            sqlsb.append(" and A.BRAND = ?");
            params.add(queryParam.get("BRAND"));
        }
        if(!StringUtils.isNullOrEmpty(queryParam.get("SERIES"))) {
            sqlsb.append(" and A.SERIES = ?");
            params.add(queryParam.get("SERIES"));
        }
        if(!StringUtils.isNullOrEmpty(queryParam.get("MODEL"))) {
            sqlsb.append(" and A.MODEL = ?");
            params.add(queryParam.get("MODEL"));
        }
        if(!StringUtils.isNullOrEmpty(queryParam.get("VIN"))) {
            sqlsb.append(" and A.VIN like ?");
            params.add("%"+queryParam.get("VIN")+"%");
        }
        if(!StringUtils.isNullOrEmpty(queryParam.get("LAST_SERVICE_ADVISOR"))) {
            sqlsb.append(" and T.SERVICE_ADVISOR = ?");
            params.add(queryParam.get("LAST_SERVICE_ADVISOR"));
        }
        if(!StringUtils.isNullOrEmpty(queryParam.get("IF_BIND"))) {
            if(queryParam.get("IF_BIND").equals("12781001")) {
                sqlsb.append(" and A.SERVICE_ADVISOR IS NOT NULL and A.SERVICE_ADVISOR !='' ");
            }else if(queryParam.get("IF_BIND").equals("12781002")) {
                sqlsb.append(" and (A.SERVICE_ADVISOR IS NULL or A.SERVICE_ADVISOR='') "); 
            }    
        }
        if (!StringUtils.isNullOrEmpty(queryParam.get("salesDateFrom"))) {
            sqlsb.append(" and A.SALES_DATE>= ?");
            params.add(DateUtil.parseDefaultDate(queryParam.get("salesDateFrom")));
        }
        if (!StringUtils.isNullOrEmpty(queryParam.get("salesDateTo"))) {
            sqlsb.append(" and A.SALES_DATE<= ?");
            params.add(DateUtil.addOneDay(queryParam.get("salesDateTo")));
        }
//        sqlsb.append(" and IS_MANAGING = ?");
//        params.add(queryParam.get("IS_MANAGING"));
       
        
        PageInfoDto pageInfoDto = DAOUtil.pageQuery(sqlsb.toString(),params);
        return pageInfoDto;
    }

    
  /**
  * 根据勾选的VIN进行设置客户经理
  * @author zhengcong
  * @date 2017年3月28日
  */   
    @Override
    public void setCusManager(String[] allvin, String manager) {
//        List<Map> setList = new ArrayList<Map>();
//        Map me;// 用于保存vin和修改前库位信息
        System.out.println(allvin.length);
        System.out.println(manager);
        for (int i = 0; i < allvin.length; i++) {

            // 修改车辆表中的专属客户经理
            VehiclePO vs = VehiclePO.findByCompositeKeys(allvin[i],FrameworkUtil.getLoginInfo().getDealerCode());

            if (vs != null) {
                vs.setString("SERVICE_ADVISOR", manager);
                vs.saveIt();
            }

        }
    }
    /**
     * excel导出方法
     * 
  * @author zhengcong
  * @date 2017年3月28日
     */
    @Override
    public List<Map> queryToExport(Map<String, String> queryParam) throws ServiceBizException {
 
    //由于“DAOUtil.findAll(sqlsb.toString(), params)” params带入到DAOUtil.findAll中后框架会自动增加一个dealer_code的查询条件参数，
    //所以每次重新查询时，需要先去除，不然会变成多个然后报错
    	for (int i = 0; i < params.size(); i++) { 
    		  
    		if (params.get(i).equals(FrameworkUtil.getLoginInfo().getDealerCode())) { 
    		  
    			params.remove(i); 
    		  
    		    i--; 
    		  
    		} 
    	}

        List<Map> list = DAOUtil.findAll(sqlsb.toString(), params);
       
        return list;
    }


}
