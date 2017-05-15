
/** 
*Copyright 2017 Yonyou Corporation Ltd. All Rights Reserved.
* This software is published under the terms of the Yonyou Software
* License version 1.0, a copy of which has been included with this
* distribution in the LICENSE.txt file.
*
* @Project Name : dms.part
*
* @File name : PartOrderPlanQueryServiceImp.java
*
* @Author : zhanshiwei
*
* @Date : 2017年5月5日
*
----------------------------------------------------------------------------------
*     Date       Who       Version     Comments
* 1. 2017年5月5日    zhanshiwei    1.0
*
*
*
*
----------------------------------------------------------------------------------
*/

package com.yonyou.dms.part.service.partOrder;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.yonyou.dms.framework.DAO.DAOUtil;
import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.framework.util.Utility;
import com.yonyou.dms.function.common.CommonConstants;
import com.yonyou.dms.function.common.DictCodeConstants;
import com.yonyou.dms.function.exception.ServiceBizException;
import com.yonyou.dms.function.utils.common.StringUtils;

/**
 * 配件订货计划查询
 * 
 * @author zhanshiwei
 * @date 2017年5月5日
 */
@Service
public class PartOrderPlanQueryServiceImp implements PartOrderPlanQueryService {

    /**
     * 业务描述：配件订货计划查询
     * 
     * @author zhanshiwei
     * @date 2017年5月5日
     * @param queryParam
     * @return
     * @throws ServiceBizException (non-Javadoc)
     * @see com.yonyou.dms.part.service.partOrder.PartOrderPlanQueryService#queryDmsPtOrderPlan(java.util.Map)
     */

    @Override
    public PageInfoDto queryDmsPtOrderPlan(Map<String, String> queryParam) throws ServiceBizException {
        StringBuffer sql = new StringBuffer();
        List<Object> params = new ArrayList<Object>();
        queryPTOrderPlan(sql, queryParam, params);
        return DAOUtil.pageQuery(sql.toString(), params);
    }

    /**
     * 配件订单计划查询sql
     * 
     * @author zhanshiwei
     * @date 2017年5月5日
     * @param sql
     * @param queryParam
     * @param params
     */

    public void queryPTOrderPlan(StringBuffer sql, Map<String, String> queryParam, List<Object> params) {
        String orderNo = queryParam.get("orderNo");
        String sapOrderNo = queryParam.get("sapOrderNo");
        String mainOrderType = queryParam.get("orderType");
        String orderStatus = queryParam.get("orderStatus");
        String beginTime = queryParam.get("beginTime");
        String endTime = queryParam.get("endTime");
        String isValid = queryParam.get("isValid");
        String isMopOrder = queryParam.get("isMopOrder");
        String partNo = queryParam.get("partNo");

        sql.append(" SELECT COALESCE(IS_VALID,12781001) as IS_VALID,IS_MOP_ORDER,DEALER_CODE,ORDER_NO,OEM_ORDER_NO,OEM_TAG,ORDER_SUM,MAIN_ORDER_TYPE,"
                   + " A.SEND_SAP_DATE,A.SAP_ORDER_NO,A.VIN,PART_ORDER_TYPE,DOE,PDC,IS_LACK_GOODS,CUSTOMER_CODE, "
                   + " CUSTOMER_NAME,ITEM_COUNT,FILLIN_TIME,ORDER_DATE, "
                   + " GKFWB_DATE,SEND_MODE,CONTACTOR_NAME,SUBMIT_TIME,REMARK,ORDER_STATUS, "
                   + " LOCK_USER,D_KEY,CREATED_BY,CREATED_AT, " + " COALESCE(IS_ACHIEVE,12781001) as IS_ACHIEVE,"
                   + " UPDATED_BY,UPDATED_AT,VER,IS_SIGNED,CASE WHEN (SELECT COUNT(ONGOING_COUNT) FROM TT_PT_DMS_ORDER_RETURN_ITEM B "
                   + " WHERE A.DEALER_CODE = B.DEALER_CODE AND A.ORDER_NO = B.ORDER_NO) > 0 THEN 12781001 ELSE 12781002 END AS IS_CHANGEING,"
                   + " 0 AS TOTAL_PRICES,"
                   + " (SELECT SUM( TOTAL) FROM TT_PT_DMS_ORDER_ITEM B WHERE A.DEALER_CODE = B.DEALER_CODE AND A.ORDER_NO = B.ORDER_NO ) AS  TOTAL"
                   + " FROM TT_PT_DMS_ORDER A WHERE 1=1"

                   + " AND D_KEY = " + CommonConstants.D_KEY + " " + "  ");
        sql.append(Utility.getLikeCond(null, "ORDER_NO", orderNo, "AND"));
        sql.append(Utility.getLikeCond(null, "SAP_ORDER_NO", sapOrderNo, "AND"));
        // sql.append(Utility.getLikeCond(null, "OEM_ORDER_NO", oemOrderNo, "AND"));
        if (!StringUtils.isNullOrEmpty(mainOrderType)) {
            sql.append(" AND PART_ORDER_TYPE = ?");
            params.add(mainOrderType);
        }
        if (!StringUtils.isNullOrEmpty(orderStatus)) {
            sql.append(" AND ORDER_STATUS = ?");
            params.add(orderStatus);
        }
        if (!StringUtils.isNullOrEmpty(queryParam.get("ischTims"))) {
            sql.append(Utility.getDateCond("", "SUBMIT_TIME", beginTime, endTime));
        }

        if (!StringUtils.isNullOrEmpty(isValid)) {
            sql.append(" AND  COALESCE(IS_VALID," + DictCodeConstants.DICT_IS_YES + ") = " + isValid);
        }
        if (!StringUtils.isNullOrEmpty(isMopOrder)) {
            sql.append(" AND  COALESCE(IS_MOP_ORDER," + DictCodeConstants.DICT_IS_YES + ") = " + isMopOrder);
        }
        if (!StringUtils.isNullOrEmpty(partNo)) {
            sql.append(" AND EXISTS (SELECT * FROM TT_PT_DMS_ORDER_ITEM B WHERE A.DEALER_CODE = "
                       + " B.DEALER_CODE AND A.ORDER_NO = B.ORDER_NO AND B.PART_NO LIKE ?)");

            params.add("%" + partNo + "%");
        }

    }

    /**
     * 导出
     * 
     * @author zhanshiwei
     * @date 2017年5月6日
     * @param queryParam
     * @return
     * @throws ServiceBizException (non-Javadoc)
     * @see com.yonyou.dms.part.service.partOrder.PartOrderPlanQueryService#queryDmsPtOrderPlanExport(java.util.Map)
     */

    @Override
    public List<Map> queryDmsPtOrderPlanExport(Map<String, String> queryParam) throws ServiceBizException {
        StringBuffer sql = new StringBuffer();
        List<Object> params = new ArrayList<Object>();
        queryPTOrderPlan(sql, queryParam, params);
        return DAOUtil.findAll(sql.toString(), params);
    }

}
