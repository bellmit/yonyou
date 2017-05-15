
/** 
*Copyright 2017 Yonyou Auto Corporation Ltd. All Rights Reserved.
* This software is published under the terms of the Yonyou Software
* License version 1.0, a copy of which has been included with this
* distribution in the LICENSE.txt file.
*
* @Project Name : dms.part
*
* @File name : ReleaseObsoleteDataQueryServiceImpl.java
*
* @Author : Administrator
*
* @Date : 2017年4月19日
*
----------------------------------------------------------------------------------
*     Date       Who       Version     Comments
* 1. 2017年4月19日    Administrator    1.0
*
*
*
*
----------------------------------------------------------------------------------
*/

package com.yonyou.dms.part.service.basedata;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.yonyou.dms.framework.DAO.DAOUtil;
import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.framework.util.FrameworkUtil;
import com.yonyou.dms.function.common.CommonConstants;
import com.yonyou.dms.function.common.DictCodeConstants;
import com.yonyou.dms.function.exception.ServiceBizException;
import com.yonyou.dms.function.utils.common.DateUtil;
import com.yonyou.dms.function.utils.common.StringUtils;

/**
 * TODO description
 * 
 * @author Administrator
 * @date 2017年4月19日
 */

@Service
public class ReleaseObsoleteDataQueryServiceImpl implements ReleaseObsoleteDataQueryService {

    /**
     * @author Administrator
     * @date 2017年4月19日
     * @param queryParams
     * @return
     * @throws ServiceBizException (non-Javadoc)
     * @see com.yonyou.dms.part.service.basedata.ReleaseObsoleteDataQueryService#queryReleaseObsoleteDataQuery(java.util.Map)
     */

    @Override
    public PageInfoDto queryReleaseObsoleteDataQuery(Map<String, String> queryParams) throws ServiceBizException {
        StringBuilder sql = new StringBuilder("");
        List<Object> queryParam = new ArrayList<>();
        sql.append(" select " + DictCodeConstants.DICT_IS_NO + " AS IS_SELECTED, " + DictCodeConstants.DICT_IS_YES
                   + " AS IS_SELECT,A.DEALER_CODE,A.STORAGE_CODE,vst.STORAGE_NAME as STORAGE_CODE_NAME,A.part_no,A.STORAGE_POSITION_CODE,A.PART_NAME,A.UNIT_CODE,"
                   + "A.CANCEL_DATE,A.CONTACTS,PHONE,A.ADDRESS,A.REPORTED_COST_PRICE,A.REPORTED_SALE_PRICE,A.REAL_TIME_NUMBER,A.REPORTED_NUMBER,A.REPORTED_PRICE,A.REPORTED_TOTAL,A.REPORTED_DATE,A.EXPIRATION_DATE,A.SHELF_DATE "
                   + "from TM_DEAD_PARTS_MANAGE A " + "inner join(" + CommonConstants.VM_PART_INFO
                   + ")B ON A.DEALER_CODE=B.DEALER_CODE and A.part_no=B.part_no left join("+CommonConstants.VM_STORAGE+")vst on vst.DEALER_CODE=A.DEALER_CODE and vst.STORAGE_CODE=A.STORAGE_CODE left join TM_STORAGE C on C.DEALER_CODE=A.DEALER_CODE and C.STORAGE_CODE=A.STORAGE_CODE where A.DEALER_CODE='"
                   + FrameworkUtil.getLoginInfo().getDealerCode() + "' and A.D_KEY=" + CommonConstants.D_KEY);

        if (!StringUtils.isNullOrEmpty(queryParams.get("partNo"))) {
            sql.append(" AND A.PART_NO LIKE ? ");
            queryParam.add("%" + queryParams.get("partNo") + "%");
        }
        if (!StringUtils.isNullOrEmpty(queryParams.get("storageCode"))) {
            sql.append(" AND A.STORAGE_CODE= ?");
            queryParam.add(queryParams.get("storageCode"));
        }
        if (!StringUtils.isNullOrEmpty(queryParams.get("partName"))) {
            sql.append(" AND A.PART_NAME LIKE ? ");
            queryParam.add("%" + queryParams.get("partName") + "%");
        }
        if (!StringUtils.isNullOrEmpty(queryParams.get("storagePositionCode"))) {
            sql.append(" AND A.STORAGE_POSITION_CODE LIKE ?");
            queryParam.add("%" + queryParams.get("storagePositionCode") + "%");
        }
        if (!StringUtils.isNullOrEmpty(queryParams.get("visit_startdate"))) {
            sql.append(" and A.REPORTED_DATE >= ?");
            queryParam.add(DateUtil.parseDefaultDate(queryParams.get("visit_startdate")));
        }
        if (!StringUtils.isNullOrEmpty(queryParams.get("visit_enddate"))) {
            sql.append(" and A.REPORTED_DATE < ?");
            queryParam.add(DateUtil.addOneDay(queryParams.get("visit_enddate")));
        }
        return DAOUtil.pageQuery(sql.toString(), queryParam);
    }

    /**
     * @author Administrator
     * @date 2017年4月19日
     * @param queryParam
     * @return
     * @throws ServiceBizException (non-Javadoc)
     * @see com.yonyou.dms.part.service.basedata.ReleaseObsoleteDataQueryService#exportReleaseObsoleteDataQuery(java.util.Map)
     */

    @Override
    public List<Map> exportReleaseObsoleteDataQuery(Map<String, String> queryParams) throws ServiceBizException {
        StringBuilder sql = new StringBuilder("");
        List<Object> queryParam = new ArrayList<>();
        sql.append(" select " + DictCodeConstants.DICT_IS_NO + " AS IS_SELECTED, " + DictCodeConstants.DICT_IS_YES
                   + " AS IS_SELECT,A.DEALER_CODE,A.STORAGE_CODE,vst.STORAGE_NAME as STORAGE_CODE_NAME,A.part_no,A.STORAGE_POSITION_CODE,A.PART_NAME,A.UNIT_CODE,"
                   + "A.CANCEL_DATE,A.CONTACTS,PHONE,A.ADDRESS,A.REPORTED_COST_PRICE,A.REPORTED_SALE_PRICE,A.REAL_TIME_NUMBER,A.REPORTED_NUMBER,A.REPORTED_PRICE,A.REPORTED_TOTAL,A.REPORTED_DATE,A.EXPIRATION_DATE,A.SHELF_DATE "
                   + "from TM_DEAD_PARTS_MANAGE A " + "inner join(" + CommonConstants.VM_PART_INFO
                   + ")B ON A.DEALER_CODE=B.DEALER_CODE and A.part_no=B.part_no left join("+CommonConstants.VM_STORAGE+")vst on vst.DEALER_CODE=A.DEALER_CODE and vst.STORAGE_CODE=A.STORAGE_CODE left join TM_STORAGE C on C.DEALER_CODE=A.DEALER_CODE and C.STORAGE_CODE=A.STORAGE_CODE where A.DEALER_CODE='"
                   + FrameworkUtil.getLoginInfo().getDealerCode() + "' and A.D_KEY=" + CommonConstants.D_KEY);

        if (!StringUtils.isNullOrEmpty(queryParams.get("partNo"))) {
            sql.append(" AND A.PART_NO LIKE ? ");
            queryParam.add("%" + queryParams.get("partNo") + "%");
        }
        if (!StringUtils.isNullOrEmpty(queryParams.get("storageCode"))) {
            sql.append(" AND A.STORAGE_CODE= ?");
            queryParam.add(queryParams.get("storageCode"));
        }
        if (!StringUtils.isNullOrEmpty(queryParams.get("partName"))) {
            sql.append(" AND A.PART_NAME LIKE ? ");
            queryParam.add("%" + queryParams.get("partName") + "%");
        }
        if (!StringUtils.isNullOrEmpty(queryParams.get("storagePositionCode"))) {
            sql.append(" AND A.STORAGE_POSITION_CODE LIKE ?");
            queryParam.add("%" + queryParams.get("storagePositionCode") + "%");
        }
        if (!StringUtils.isNullOrEmpty(queryParams.get("visit_startdate"))) {
            sql.append(" and A.REPORTED_DATE >= ?");
            queryParam.add(DateUtil.parseDefaultDate(queryParams.get("visit_startdate")));
        }
        if (!StringUtils.isNullOrEmpty(queryParams.get("visit_enddate"))) {
            sql.append(" and A.REPORTED_DATE < ?");
            queryParam.add(DateUtil.addOneDay(queryParams.get("visit_enddate")));
        }
        return DAOUtil.findAll(sql.toString(), queryParam);
    }

    /**
     * @author Administrator
     * @date 2017年4月13日
     * @return (non-Javadoc)
     * @see com.yonyou.dms.part.service.basedata.ObsoleteDataReportService#queryStorageCode()
     */

    @Override
    public List<Map> queryStorageCode() {
        StringBuilder sql = new StringBuilder("SELECT DEALER_CODE,STORAGE_CODE,STORAGE_NAME,STORAGE_TYPE FROM tm_storage WHERE 1=1 AND STORAGE_TYPE="
                                              + DictCodeConstants.PART_WAREHOUSE);
        List<Object> queryParam = new ArrayList<>();
        return DAOUtil.findAll(sql.toString(), queryParam);
    }

}
