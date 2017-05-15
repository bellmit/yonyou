
/** 
*Copyright 2017 Yonyou Corporation Ltd. All Rights Reserved.
* This software is published under the terms of the Yonyou Software
* License version 1.0, a copy of which has been included with this
* distribution in the LICENSE.txt file.
*
* @Project Name : dms.part
*
* @File name : PreBookPartListServiceImpl.java
*
* @Author : dingchaoyu
*
* @Date : 2017年4月26日
*
----------------------------------------------------------------------------------
*     Date       Who       Version     Comments
* 1. 2017年4月26日    dingchaoyu    1.0
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
import com.yonyou.dms.function.utils.common.StringUtils;

/**
* TODO description
* @author dingchaoyu
* @date 2017年4月26日
*/
@Service
public class PreBookPartListServiceImpl implements PreBookPartListService{

    @Override
    public PageInfoDto queryPartBookingSec(Map map) throws ServiceBizException {
        List<Object> list = new ArrayList<>();
        StringBuffer sql = new StringBuffer("select '' AS  IS_SELECTED, A.STORAGE_CODE,D.OBLIGATED_NO, A.BOOKING_ORDER_NO, A.ITEM_ID, A.DEALER_CODE, A.IS_OBLIGATED, B.LICENSE,A.PART_NO, A.PART_NAME, A.OBLIGATED_MAN, A.OBLIGATED_DATE, ");
        sql.append("A.BOOKING_QUANTITY,  (C.STOCK_QUANTITY + C.BORROW_QUANTITY - C.LEND_QUANTITY - C.LOCKED_QUANTITY) AS USEABLE_STOCK, A.STORAGE_POSITION_CODE,A.CREATED_AT AS BOOKING_DATE ");
        sql.append("FROM TT_BOOKING_ORDER_PART A   LEFT OUTER JOIN TT_BOOKING_ORDER B ");
        sql.append("ON (A.BOOKING_ORDER_NO = B.BOOKING_ORDER_NO AND A.DEALER_CODE = B.DEALER_CODE) ");
        sql.append("LEFT OUTER JOIN TM_PART_STOCK C ");
        sql.append("ON ( A.DEALER_CODE = C.DEALER_CODE AND A.PART_NO = C.PART_NO AND A.STORAGE_CODE = C.STORAGE_CODE ) ");
        sql.append("LEFT OUTER JOIN TT_PART_OBLIGATED_ITEM D on (A.DEALER_CODE = D.DEALER_CODE AND A.PART_NO = D.PART_NO AND A.STORAGE_CODE = D.STORAGE_CODE )");
        sql.append("WHERE A.IS_OBLIGATED=? AND B.BOOKING_ORDER_STATUS=? AND A.IS_OBLIGATED =?  AND A.DEALER_CODE =? ");
        sql.append("AND A.D_KEY =? AND A.BOOKING_QUANTITY > 0 ");
        list.add(DictCodeConstants.DICT_IS_YES);
        list.add(DictCodeConstants.DICT_BOS_NOT_ENTER);
        list.add(DictCodeConstants.DICT_IS_YES);
        list.add(FrameworkUtil.getLoginInfo().getDealerCode());
        list.add(CommonConstants.D_KEY);
        if (!StringUtils.isNullOrEmpty(map.get("STORAGE_CODE"))) {
            sql.append(" and A.STORAGE_CODE = ? ");
            list.add(map.get("STORAGE_CODE"));
        }
        if (!StringUtils.isNullOrEmpty(map.get("PART_NO"))) {
            sql.append(" and A.PART_NO like ? ");
            list.add("%"+map.get("PART_NO")+"%");
        }
        if (!StringUtils.isNullOrEmpty(map.get("PART_NAME"))) {
            sql.append(" and A.PART_NAME like ? ");
            list.add("%"+map.get("PART_NAME")+"%");
        }
        if (!StringUtils.isNullOrEmpty(map.get("obligatedNo"))) {
            sql.append(" and A.OBLIGATED_NO = ? ");
            list.add("%"+map.get("obligatedNo")+"%");
        }
        return DAOUtil.pageQuery(sql.toString(), list);
    }

    @Override
    public List<Map> queryPreBookPartListExport(Map map) throws ServiceBizException {
        List<Object> list = new ArrayList<>();
        StringBuffer sql = new StringBuffer("select '' AS  IS_SELECTED, A.STORAGE_CODE,D.OBLIGATED_NO, A.BOOKING_ORDER_NO, A.ITEM_ID, A.DEALER_CODE, A.IS_OBLIGATED, B.LICENSE,A.PART_NO, A.PART_NAME, A.OBLIGATED_MAN, A.OBLIGATED_DATE, ");
        sql.append("A.BOOKING_QUANTITY,  (C.STOCK_QUANTITY + C.BORROW_QUANTITY - C.LEND_QUANTITY - C.LOCKED_QUANTITY) AS USEABLE_STOCK, A.STORAGE_POSITION_CODE,A.CREATED_AT AS BOOKING_DATE ");
        sql.append("FROM TT_BOOKING_ORDER_PART A   LEFT OUTER JOIN TT_BOOKING_ORDER B ");
        sql.append("ON (A.BOOKING_ORDER_NO = B.BOOKING_ORDER_NO AND A.DEALER_CODE = B.DEALER_CODE) ");
        sql.append("LEFT OUTER JOIN TM_PART_STOCK C ");
        sql.append("ON ( A.DEALER_CODE = C.DEALER_CODE AND A.PART_NO = C.PART_NO AND A.STORAGE_CODE = C.STORAGE_CODE ) ");
        sql.append("LEFT OUTER JOIN TT_PART_OBLIGATED_ITEM D on (A.DEALER_CODE = D.DEALER_CODE AND A.PART_NO = D.PART_NO AND A.STORAGE_CODE = D.STORAGE_CODE )");
        sql.append("WHERE A.IS_OBLIGATED=? AND B.BOOKING_ORDER_STATUS=? AND A.IS_OBLIGATED =?  AND A.DEALER_CODE =? ");
        sql.append("AND A.D_KEY =? AND A.BOOKING_QUANTITY > 0 ");
        list.add(DictCodeConstants.DICT_IS_YES);
        list.add(DictCodeConstants.DICT_BOS_NOT_ENTER);
        list.add(DictCodeConstants.DICT_IS_YES);
        list.add(FrameworkUtil.getLoginInfo().getDealerCode());
        list.add(CommonConstants.D_KEY);
        if (!StringUtils.isNullOrEmpty(map.get("STORAGE_CODE"))) {
            sql.append("and A.STORAGE_CODE = ? ");
            list.add(map.get("STORAGE_CODE"));
        }
        if (!StringUtils.isNullOrEmpty(map.get("PART_NO"))) {
            sql.append("and A.PART_NO like ?");
            list.add("%"+map.get("PART_NO")+"%");
        }
        if (!StringUtils.isNullOrEmpty(map.get("PART_NAME"))) {
            sql.append("and A.PART_NAME like ?");
            list.add("%"+map.get("PART_NAME")+"%");
        }
        if (!StringUtils.isNullOrEmpty(map.get("obligatedNo"))) {
            sql.append("and A.OBLIGATED_NO = ? ");
            list.add("%"+map.get("obligatedNo")+"%");
        }
        return DAOUtil.findAll(sql.toString(), list);
    }
    
    
}
