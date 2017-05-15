
/** 
*Copyright 2017 Yonyou Corporation Ltd. All Rights Reserved.
* This software is published under the terms of the Yonyou Software
* License version 1.0, a copy of which has been included with this
* distribution in the LICENSE.txt file.
*
* @Project Name : dms.retail
*
* @File name : HasActivityVehicleQueryServiceImpl.java
*
* @Author : dingchaoyu
*
* @Date : 2017年5月15日
*
----------------------------------------------------------------------------------
*     Date       Who       Version     Comments
* 1. 2017年5月15日    dingchaoyu    1.0
*
*
*
*
----------------------------------------------------------------------------------
*/
	
package com.yonyou.dms.retail.service.market;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.javalite.activejdbc.Base;
import org.springframework.stereotype.Service;

import com.yonyou.dms.framework.DAO.DAOUtil;
import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.framework.util.FrameworkUtil;
import com.yonyou.dms.framework.util.Utility;
import com.yonyou.dms.function.common.CommonConstants;
import com.yonyou.dms.function.exception.ServiceBizException;
import com.yonyou.dms.function.utils.common.StringUtils;

/**
* TODO description
* @author dingchaoyu
* @date 2017年5月15日
*/
@Service
@SuppressWarnings("rawtypes")
public class HasActivityVehicleQueryServiceImpl implements HasActivityVehicleQueryService{

    @Override
    public PageInfoDto query(Map map) throws ServiceBizException {
        List<Object> list = new ArrayList<>();
        StringBuffer sql = new StringBuffer("SELECT TA.DEALER_CODE,TA.ACTIVITY_CODE,TA.ACTIVITY_NAME,TA.BEGIN_DATE,TA.END_DATE,TA.RELEASE_DATE,");
        sql.append(" TA.LABOUR_AMOUNT,TA.REPAIR_PART_AMOUNT,TA.ACTIVITY_AMOUNT,case when TA.IS_PART_ACTIVITY='12781001' then '过期' else '不过期' end,TA.DOWN_TAG,");
        sql.append(" TC.DUTY_CAR_SUM,dd.ACHIEVE_CAR_SUM,TB.ALL_CAR_SUM,(TC.DUTY_CAR_SUM/TB.ALL_CAR_SUM) AS DUTY_RATE,");
        sql.append(" (dd.ACHIEVE_CAR_SUM/TB.ALL_CAR_SUM) AS ACHIEVE_RATE,CASE WHEN  now() > TA.END_DATE");
        sql.append(" THEN 12781001 ELSE 12781002 END AS IS_ACTIVITY_STATUS FROM ("+CommonConstants.VT_ACTIVITY+") TA ");
        sql.append(" LEFT JOIN (SELECT COUNT(DISTINCT a.VIN) AS  ALL_CAR_SUM,a.ACTIVITY_CODE,a.DEALER_CODE,a.D_KEY FROM ("+CommonConstants.VT_ACTIVITY_VEHICLE+") a ");
        sql.append(" WHERE a.DEALER_CODE= ? GROUP BY a.ACTIVITY_CODE,a.DEALER_CODE,a.D_KEY) TB ");
        sql.append(" ON TB.DEALER_CODE=TA.DEALER_CODE AND TB.D_KEY=TA.D_KEY AND TB.ACTIVITY_CODE=TA.ACTIVITY_CODE");
        sql.append(" LEFT JOIN (SELECT COUNT(DISTINCT b.VIN ) AS DUTY_CAR_SUM ,b.ACTIVITY_CODE,b.DEALER_CODE,b.D_KEY FROM ("+CommonConstants.VT_ACTIVITY_VEHICLE+") b ");
        sql.append(" WHERE b.DEALER_CODE=?  AND b.DUTY_ENTITY=? GROUP BY b.ACTIVITY_CODE,b.DEALER_CODE,b.D_KEY) TC ");
        sql.append(" ON TC.DEALER_CODE=TA.DEALER_CODE AND TC.ACTIVITY_CODE=TA.ACTIVITY_CODE AND TC.D_KEY=TA.D_KEY ");
        sql.append(" LEFT JOIN ( select DEALER_CODE, ACTIVITY_CODE, COUNT(*) AS ACHIEVE_CAR_SUM from (");
        sql.append(" select distinct e.vin,a.DEALER_CODE,a.activity_code  from ("+CommonConstants.VT_ACTIVITY+") a");
        sql.append(" LEFT JOIN TT_RO_LABOUR B ON ( A.DEALER_CODE=B.DEALER_CODE AND A.ACTIVITY_CODE=B.ACTIVITY_CODE ) LEFT JOIN TT_REPAIR_ORDER E  ON (A.DEALER_CODE=E.DEALER_CODE AND E.IS_ACTIVITY=12781001 AND E.RO_NO =B.RO_NO ) where B.RO_NO IS NOT NULL and A.D_KEY=0");
        sql.append(" union select distinct e.vin,a.DEALER_CODE,a.activity_code from ("+CommonConstants.VT_ACTIVITY+") a");
        sql.append(" LEFT JOIN TT_RO_REPAIR_PART C  ON ( A.DEALER_CODE=c.DEALER_CODE AND A.ACTIVITY_CODE=c.ACTIVITY_CODE ) LEFT JOIN TT_REPAIR_ORDER E  ON ( A.DEALER_CODE=E.DEALER_CODE AND E.IS_ACTIVITY=12781001  AND E.RO_NO =C.RO_NO   )  where c.RO_NO IS NOT NULL and A.D_KEY=0");
        sql.append(" union select distinct e.vin,a.DEALER_CODE,a.activity_code from ("+CommonConstants.VT_ACTIVITY+") a");
        sql.append(" LEFT JOIN TT_RO_ADD_ITEM D ON ( A.DEALER_CODE=D.DEALER_CODE AND A.ACTIVITY_CODE=D.ACTIVITY_CODE ) LEFT JOIN TT_REPAIR_ORDER E  ON ( A.DEALER_CODE=E.DEALER_CODE AND E.RO_NO =D.RO_NO   ) where D.RO_NO IS NOT NULL and A.D_KEY=0");
        sql.append(" ) AA where AA.DEALER_CODE=?  GROUP BY  DEALER_CODE, ACTIVITY_CODE");
        sql.append(" )dd on dd.DEALER_CODE = ta.DEALER_CODE and dd.ACTIVITY_CODE=ta.ACTIVITY_CODE");
        sql.append(" WHERE TA.DEALER_CODE=?  AND TA.D_KEY=?");
        list.add(FrameworkUtil.getLoginInfo().getDealerCode());
        list.add(FrameworkUtil.getLoginInfo().getDealerCode());
        list.add(FrameworkUtil.getLoginInfo().getDealerCode());
        list.add(FrameworkUtil.getLoginInfo().getDealerCode());
        list.add(FrameworkUtil.getLoginInfo().getDealerCode());
        list.add(CommonConstants.D_KEY);
        if (!StringUtils.isNullOrEmpty(map.get("activityCode"))) {
            sql.append(" and TA.ACTIVITY_CODE like ?");
            list.add("%"+map.get("activityCode")+"%");
        }
        if (!StringUtils.isNullOrEmpty(map.get("activityName"))) {
            sql.append(" and TA.ACTIVITY_NAME like ?");
            list.add("%"+map.get("activityName")+"%");
        }
        if (!StringUtils.isNullOrEmpty(map.get("isActivityStatus"))) {
            if (map.get("isActivityStatus").toString().equals("12781001")) {
                sql.append(" AND now() > TA.END_DATE");
            }else if (map.get("isActivityStatus").toString().equals("12781002")) {
                sql.append(" AND now() <= TA.END_DATE");
            }
        }
        Utility.getDateCond("TA", "END_DATE", map.get("endDateFrom").toString(), map.get("endDateTo").toString());
        return DAOUtil.pageQuery(sql.toString(), list);
    }

    @Override
    public PageInfoDto queryDetail(Map map) throws ServiceBizException {
        List<Object> list = new ArrayList<>();
        StringBuffer sql = new StringBuffer("SELECT DISTINCT V.*  FROM ( ");
        sql.append(" SELECT DISTINCT a.DEALER_CODE, A.ACTIVITY_CODE,  coalesce(B.RO_NO,coalesce(C.RO_NO,D.RO_NO)) as RO_NO,A.ACTIVITY_NAME,A.END_DATE  FROM ("+CommonConstants.VT_ACTIVITY+") A ");
        sql.append(" LEFT JOIN TT_RO_LABOUR B ON A.DEALER_CODE=B.DEALER_CODE AND A.ACTIVITY_CODE=B.ACTIVITY_CODE ");
        sql.append(" LEFT JOIN TT_RO_REPAIR_PART C ON A.DEALER_CODE =C.DEALER_CODE AND A.ACTIVITY_CODE=C.ACTIVITY_CODE ");
        sql.append(" LEFT JOIN TT_RO_ADD_ITEM D ON A.DEALER_CODE=D.DEALER_CODE AND A.ACTIVITY_CODE=D.ACTIVITY_CODE ");
        sql.append(" WHERE A.DEALER_CODE=? AND  A.D_KEY=? ");
        list.add(FrameworkUtil.getLoginInfo().getDealerCode());
        list.add(CommonConstants.D_KEY);
        if (!StringUtils.isNullOrEmpty(map.get("activityCode"))) {
            sql.append(" and A.ACTIVITY_CODE like ? ");
            list.add("%"+map.get("activityCode")+"%");
        }
        if (!StringUtils.isNullOrEmpty(map.get("isActivityStatus"))) {
            if (map.get("isActivityStatus").toString().equals("12781001")) {
                sql.append(" AND now() > A.END_DATE");
            }else if (map.get("isActivityStatus").toString().equals("12781002")) {
                sql.append(" AND now() <= A.END_DATE");
            }
        }
        Utility.getDateCond("A", "END_DATE", map.get("endDateFrom").toString(), map.get("endDateTo").toString());
        sql.append(" ) a left join TT_REPAIR_ORDER b on a.DEALER_CODE=b.DEALER_CODE and a.Ro_no=B.RO_NO ");
        sql.append(" LEFT JOIN TM_VEHICLE V ON V.DEALER_CODE=A.DEALER_CODE where a.RO_NO is not null AND b.vin = V.VIN ");
        return DAOUtil.pageQuery(sql.toString(), list);
    }

    @Override
    public List<Map> queryExport(Map map) throws ServiceBizException {
        List<Object> list = new ArrayList<>();
        StringBuffer sql = new StringBuffer("SELECT TA.DEALER_CODE,TA.ACTIVITY_CODE,TA.ACTIVITY_NAME,TA.BEGIN_DATE,TA.END_DATE,TA.RELEASE_DATE,");
        sql.append(" TA.LABOUR_AMOUNT,TA.REPAIR_PART_AMOUNT,TA.ACTIVITY_AMOUNT,case when TA.IS_PART_ACTIVITY='12781001' then '过期' else '不过期' end,TA.DOWN_TAG,");
        sql.append(" TC.DUTY_CAR_SUM,dd.ACHIEVE_CAR_SUM,TB.ALL_CAR_SUM,(TC.DUTY_CAR_SUM/TB.ALL_CAR_SUM) AS DUTY_RATE,");
        sql.append(" (dd.ACHIEVE_CAR_SUM/TB.ALL_CAR_SUM) AS ACHIEVE_RATE,CASE WHEN  now() > TA.END_DATE");
        sql.append(" THEN 12781001 ELSE 12781002 END AS IS_ACTIVITY_STATUS FROM ("+CommonConstants.VT_ACTIVITY+") TA ");
        sql.append(" LEFT JOIN (SELECT COUNT(DISTINCT a.VIN) AS  ALL_CAR_SUM,a.ACTIVITY_CODE,a.DEALER_CODE,a.D_KEY FROM ("+CommonConstants.VT_ACTIVITY_VEHICLE+") a ");
        sql.append(" WHERE a.DEALER_CODE= ? GROUP BY a.ACTIVITY_CODE,a.DEALER_CODE,a.D_KEY) TB ");
        sql.append(" ON TB.DEALER_CODE=TA.DEALER_CODE AND TB.D_KEY=TA.D_KEY AND TB.ACTIVITY_CODE=TA.ACTIVITY_CODE");
        sql.append(" LEFT JOIN (SELECT COUNT(DISTINCT b.VIN ) AS DUTY_CAR_SUM ,b.ACTIVITY_CODE,b.DEALER_CODE,b.D_KEY FROM ("+CommonConstants.VT_ACTIVITY_VEHICLE+") b ");
        sql.append(" WHERE b.DEALER_CODE=?  AND b.DUTY_ENTITY=? GROUP BY b.ACTIVITY_CODE,b.DEALER_CODE,b.D_KEY) TC ");
        sql.append(" ON TC.DEALER_CODE=TA.DEALER_CODE AND TC.ACTIVITY_CODE=TA.ACTIVITY_CODE AND TC.D_KEY=TA.D_KEY ");
        sql.append(" LEFT JOIN ( select DEALER_CODE, ACTIVITY_CODE, COUNT(*) AS ACHIEVE_CAR_SUM from (");
        sql.append(" select distinct e.vin,a.DEALER_CODE,a.activity_code  from ("+CommonConstants.VT_ACTIVITY+") a");
        sql.append(" LEFT JOIN TT_RO_LABOUR B ON ( A.DEALER_CODE=B.DEALER_CODE AND A.ACTIVITY_CODE=B.ACTIVITY_CODE ) LEFT JOIN TT_REPAIR_ORDER E  ON (A.DEALER_CODE=E.DEALER_CODE AND E.IS_ACTIVITY=12781001 AND E.RO_NO =B.RO_NO ) where B.RO_NO IS NOT NULL and A.D_KEY=0");
        sql.append(" union select distinct e.vin,a.DEALER_CODE,a.activity_code from ("+CommonConstants.VT_ACTIVITY+") a");
        sql.append(" LEFT JOIN TT_RO_REPAIR_PART C  ON ( A.DEALER_CODE=c.DEALER_CODE AND A.ACTIVITY_CODE=c.ACTIVITY_CODE ) LEFT JOIN TT_REPAIR_ORDER E  ON ( A.DEALER_CODE=E.DEALER_CODE AND E.IS_ACTIVITY=12781001  AND E.RO_NO =C.RO_NO   )  where c.RO_NO IS NOT NULL and A.D_KEY=0");
        sql.append(" union select distinct e.vin,a.DEALER_CODE,a.activity_code from ("+CommonConstants.VT_ACTIVITY+") a");
        sql.append(" LEFT JOIN TT_RO_ADD_ITEM D ON ( A.DEALER_CODE=D.DEALER_CODE AND A.ACTIVITY_CODE=D.ACTIVITY_CODE ) LEFT JOIN TT_REPAIR_ORDER E  ON ( A.DEALER_CODE=E.DEALER_CODE AND E.RO_NO =D.RO_NO   ) where D.RO_NO IS NOT NULL and A.D_KEY=0");
        sql.append(" ) AA where AA.DEALER_CODE=?  GROUP BY  DEALER_CODE, ACTIVITY_CODE");
        sql.append(" )dd on dd.DEALER_CODE = ta.DEALER_CODE and dd.ACTIVITY_CODE=ta.ACTIVITY_CODE");
        sql.append(" WHERE TA.DEALER_CODE=?  AND TA.D_KEY=?");
        list.add(FrameworkUtil.getLoginInfo().getDealerCode());
        list.add(FrameworkUtil.getLoginInfo().getDealerCode());
        list.add(FrameworkUtil.getLoginInfo().getDealerCode());
        list.add(FrameworkUtil.getLoginInfo().getDealerCode());
        list.add(FrameworkUtil.getLoginInfo().getDealerCode());
        list.add(CommonConstants.D_KEY);
        if (!StringUtils.isNullOrEmpty(map.get("activityCode"))) {
            sql.append(" and TA.ACTIVITY_CODE like ?");
            list.add("%"+map.get("activityCode")+"%");
        }
        if (!StringUtils.isNullOrEmpty(map.get("activityName"))) {
            sql.append(" and TA.ACTIVITY_NAME like ?");
            list.add("%"+map.get("activityName")+"%");
        }
        if (!StringUtils.isNullOrEmpty(map.get("isActivityStatus"))) {
            if (map.get("isActivityStatus").toString().equals("12781001")) {
                sql.append(" AND now() > TA.END_DATE");
            }else if (map.get("isActivityStatus").toString().equals("12781002")) {
                sql.append(" AND now() <= TA.END_DATE");
            }
        }
        Utility.getDateCond("TA", "END_DATE", map.get("endDateFrom").toString(), map.get("endDateTo").toString());
        return DAOUtil.findAll(sql.toString(), list);
    }
    
    @Override
    public List<Map> queryOwnerNoByid(String id) throws ServiceBizException {
        List<Map> list = queryQwnerBy(id);
        Map map = list.get(0);
        String ownerNo = "";
        if (map.get("OWNER_NO") != null && !map.get("OWNER_NO").equals("")) {
            ownerNo = " AND  A.OWNER_NO = "+map.get("OWNER_NO").toString();
        }
        StringBuffer sql = new StringBuffer();
        String dealerCode = FrameworkUtil.getLoginInfo().getDealerCode();
        sql.append(" SELECT  A.*,ve.*,ve.ENGINE_NO,A.OWNER_NAME FROM (" + CommonConstants.VM_OWNER + ") A "
                + " LEFT JOIN TM_OWNER_MEMO B ON B.DEALER_CODE = A.DEALER_CODE AND B.OWNER_NO = A.OWNER_NO "
                + " LEFT JOIN ("+CommonConstants.VM_VEHICLE+") ve ON ve.DEALER_CODE= A.DEALER_CODE AND ve.OWNER_NO=A.OWNER_NO "
                + " WHERE A.DEALER_CODE = '" + dealerCode + "' " + ownerNo );
        return Base.findAll(sql.toString());
    }
    
    private List<Map> queryQwnerBy(String id) {
        String sb=new String(" SELECT  ow.OWNER_NO, ow.OWNER_NAME, ow.OWNER_SPELL, ow.GENDER, ow.CT_CODE, ow.CERTIFICATE_NO, ow.PROVINCE, ow.CITY, ow.DISTRICT, ow.ADDRESS, ow.ZIP_CODE, ow.INDUSTRY_FIRST," 
                + " ow.INDUSTRY_SECOND, ow.TAX_NO, ow.PRE_PAY, ow.ARREARAGE_AMOUNT, ow.CUS_RECEIVE_SORT,ve.* "
                + " FROM tm_owner ow LEFT JOIN tm_vehicle ve ON ow.dealer_code=ve.dealer_code AND ow.owner_no=ve.owner_no"
                + " WHERE ve.vin ='"+id+"' AND 1=1  ");
        List<Object> queryList = new ArrayList<Object>();
        queryList.add(id);
        return Base.findAll(sb.toString());
    }

    @Override
    public List<Map> queryDetailExport(Map map) throws ServiceBizException {
        List<Object> list = new ArrayList<>();
        StringBuffer sql = new StringBuffer("SELECT DISTINCT V.*  FROM ( ");
        sql.append(" SELECT DISTINCT a.DEALER_CODE, A.ACTIVITY_CODE,  coalesce(B.RO_NO,coalesce(C.RO_NO,D.RO_NO)) as RO_NO,A.ACTIVITY_NAME,A.END_DATE  FROM ("+CommonConstants.VT_ACTIVITY+") A ");
        sql.append(" LEFT JOIN TT_RO_LABOUR B ON A.DEALER_CODE=B.DEALER_CODE AND A.ACTIVITY_CODE=B.ACTIVITY_CODE ");
        sql.append(" LEFT JOIN TT_RO_REPAIR_PART C ON A.DEALER_CODE =C.DEALER_CODE AND A.ACTIVITY_CODE=C.ACTIVITY_CODE ");
        sql.append(" LEFT JOIN TT_RO_ADD_ITEM D ON A.DEALER_CODE=D.DEALER_CODE AND A.ACTIVITY_CODE=D.ACTIVITY_CODE ");
        sql.append(" WHERE A.DEALER_CODE=? AND  A.D_KEY=? ");
        list.add(FrameworkUtil.getLoginInfo().getDealerCode());
        list.add(CommonConstants.D_KEY);
        if (!StringUtils.isNullOrEmpty(map.get("activityCode"))) {
            sql.append(" and A.ACTIVITY_CODE like ? ");
            list.add("%"+map.get("activityCode")+"%");
        }
        if (!StringUtils.isNullOrEmpty(map.get("isActivityStatus"))) {
            if (map.get("isActivityStatus").toString().equals("12781001")) {
                sql.append(" AND now() > A.END_DATE");
            }else if (map.get("isActivityStatus").toString().equals("12781002")) {
                sql.append(" AND now() <= A.END_DATE");
            }
        }
        Utility.getDateCond("A", "END_DATE", map.get("endDateFrom").toString(), map.get("endDateTo").toString());
        sql.append(" ) a left join TT_REPAIR_ORDER b on a.DEALER_CODE=b.DEALER_CODE and a.Ro_no=B.RO_NO ");
        sql.append(" LEFT JOIN TM_VEHICLE V ON V.DEALER_CODE=A.DEALER_CODE where a.RO_NO is not null AND b.vin = V.VIN ");
        return DAOUtil.findAll(sql.toString(), list);
    }
}
