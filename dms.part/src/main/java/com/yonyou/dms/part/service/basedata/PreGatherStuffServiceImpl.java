
/** 
*Copyright 2017 Yonyou Corporation Ltd. All Rights Reserved.
* This software is published under the terms of the Yonyou Software
* License version 1.0, a copy of which has been included with this
* distribution in the LICENSE.txt file.
*
* @Project Name : dms.part
*
* @File name : PreGatherStuffServiceImpl.java
*
* @Author : dingchaoyu
*
* @Date : 2017年4月27日
*
----------------------------------------------------------------------------------
*     Date       Who       Version     Comments
* 1. 2017年4月27日    dingchaoyu    1.0
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
import com.yonyou.dms.framework.util.Utility;
import com.yonyou.dms.function.common.CommonConstants;
import com.yonyou.dms.function.common.DictCodeConstants;
import com.yonyou.dms.function.exception.ServiceBizException;
import com.yonyou.dms.function.utils.common.StringUtils;

/**
* TODO description
* @author dingchaoyu
* @date 2017年4月27日
*/
@Service
public class PreGatherStuffServiceImpl implements PreGatherStuffService{

    @Override
    public PageInfoDto queryPick(Map map) throws ServiceBizException {
        List<Object> list = new ArrayList<>();
        StringBuffer sql = new StringBuffer("SELECT  A.DEALER_CODE,A.RO_NO, A.LICENSE, A.OWNER_NO,A.VIN,    A.VEHICLE_TOP_DESC, A.SERVICE_ADVISOR, ");
        sql.append("A.REMARK, A.OWNER_NAME,P.PART_NO,P.PART_NAME,P.STORAGE_CODE,P.STORAGE_POSITION_CODE, ");
        sql.append("P.PRE_CHECK,P.PART_QUANTITY,P.PART_SALES_PRICE,P.PART_SALES_AMOUNT,P.RECEIVER,A.PRINT_SEND_TIME,A.PRINT_RP_TIME ");
        sql.append("FROM  TT_REPAIR_ORDER A  ");
        sql.append("LEFT JOIN TT_RO_REPAIR_PART P ON (A.DEALER_CODE = P.DEALER_CODE AND A.RO_NO = P.RO_NO) ");
        sql.append("WHERE EXISTS(SELECT RO_NO FROM TT_RO_REPAIR_PART B WHERE B.PRE_CHECK = ?");
        sql.append("AND B.DEALER_CODE = ? AND B.D_KEY = ? AND B.RO_NO = A.RO_NO ");
        sql.append(") AND A.DEALER_CODE = ?");
        list.add(DictCodeConstants.DICT_IS_YES);
        list.add(FrameworkUtil.getLoginInfo().getDealerCode());
        list.add(CommonConstants.D_KEY);
        list.add(FrameworkUtil.getLoginInfo().getDealerCode());
        if (!StringUtils.isNullOrEmpty(map.get("RO_NO"))) {
            sql.append(" and A.RO_NO  like ? ");
            list.add("%"+map.get("RO_NO")+"%");
        }
        if (!StringUtils.isNullOrEmpty(map.get("LICENSE"))) {
            sql.append(" and A.LICENSE like ? ");
            list.add("%"+map.get("LICENSE")+"%");
        }
        if (!StringUtils.isNullOrEmpty(map.get("PRE_CHECK"))) {
            sql.append(" and P.PRE_CHECK= ? ");
            list.add(map.get("PRE_CHECK"));
        }
        if (!StringUtils.isNullOrEmpty(map.get("SERVICE_ADVISOR"))) {
            sql.append(" and A.SERVICE_ADVISOR= ? ");
            list.add(map.get("SERVICE_ADVISOR"));
        }
        if (!StringUtils.isNullOrEmpty(map.get("START_TIME"))&&!StringUtils.isNullOrEmpty(map.get("END_TIME"))) {
            Utility.getDateCond("A", "RO_CREATE_DATE", map.get("START_TIME").toString(), map.get("END_TIME").toString());
        }
        if (!StringUtils.isNullOrEmpty(map.get("PRE_START_TIME"))&&!StringUtils.isNullOrEmpty(map.get("PRE_END_TIME"))) {
            Utility.getDateCond("A", "RO_CREATE_DATE", map.get("PRE_START_TIME").toString(), map.get("PRE_END_TIME").toString());
        }
        return DAOUtil.pageQuery(sql.toString(), list);
    }

    @Override
    public List<Map> queryPreGatherStuffExport(Map map) throws ServiceBizException {
        List<Object> list = new ArrayList<>();
        StringBuffer sql = new StringBuffer("SELECT  A.DEALER_CODE,A.RO_NO, A.LICENSE, A.OWNER_NO,A.VIN,    A.VEHICLE_TOP_DESC, A.SERVICE_ADVISOR, ");
        sql.append("A.REMARK, A.OWNER_NAME,P.PART_NO,P.PART_NAME,P.STORAGE_CODE,P.STORAGE_POSITION_CODE, ");
        sql.append("P.PRE_CHECK,P.PART_QUANTITY,P.PART_SALES_PRICE,P.PART_SALES_AMOUNT,P.RECEIVER,A.PRINT_SEND_TIME,A.PRINT_RP_TIME ");
        sql.append("FROM  TT_REPAIR_ORDER A  ");
        sql.append("LEFT JOIN TT_RO_REPAIR_PART P ON (A.DEALER_CODE = P.DEALER_CODE AND A.RO_NO = P.RO_NO) ");
        sql.append("WHERE EXISTS(SELECT RO_NO FROM TT_RO_REPAIR_PART B WHERE B.PRE_CHECK = ?");
        sql.append("AND B.DEALER_CODE = ? AND B.D_KEY = ? AND B.RO_NO = A.RO_NO ");
        sql.append(") AND A.DEALER_CODE = ?");
        list.add(DictCodeConstants.DICT_IS_YES);
        list.add(FrameworkUtil.getLoginInfo().getDealerCode());
        list.add(CommonConstants.D_KEY);
        list.add(FrameworkUtil.getLoginInfo().getDealerCode());
        if (!StringUtils.isNullOrEmpty(map.get("RO_NO"))) {
            sql.append(" and A.RO_NO  like ? ");
            list.add("%"+map.get("RO_NO")+"%");
        }
        if (!StringUtils.isNullOrEmpty(map.get("LICENSE"))) {
            sql.append(" and A.LICENSE like ? ");
            list.add("%"+map.get("LICENSE")+"%");
        }
        if (!StringUtils.isNullOrEmpty(map.get("PRE_CHECK"))) {
            sql.append(" and P.PRE_CHECK= ? ");
            list.add(map.get("PRE_CHECK"));
        }
        if (!StringUtils.isNullOrEmpty(map.get("SERVICE_ADVISOR"))) {
            sql.append(" and A.SERVICE_ADVISOR= ? ");
            list.add(map.get("SERVICE_ADVISOR"));
        }
        if (!StringUtils.isNullOrEmpty(map.get("START_TIME"))&&!StringUtils.isNullOrEmpty(map.get("END_TIME"))) {
            Utility.getDateCond("A", "RO_CREATE_DATE", map.get("START_TIME").toString(), map.get("END_TIME").toString());
        }
        if (!StringUtils.isNullOrEmpty(map.get("PRE_START_TIME"))&&!StringUtils.isNullOrEmpty(map.get("PRE_END_TIME"))) {
            Utility.getDateCond("A", "RO_CREATE_DATE", map.get("PRE_START_TIME").toString(), map.get("PRE_END_TIME").toString());
        }
        return DAOUtil.findAll(sql.toString(), list);
    }

    @Override
    public List<Map> queryPreGatherStuffDetailExport(Map map) throws ServiceBizException {
        List<Object> list = new ArrayList<>();
        String sql = "select * from tt_ro_repair_part where ro_no=? and dealer_code=? and d_key=? ";
        list.add(map.get("RO_NO"));
        list.add(FrameworkUtil.getLoginInfo().getDealerCode());
        list.add(CommonConstants.D_KEY);
        return DAOUtil.findAll(sql, list);
    }

    @Override
    public PageInfoDto QueryPartPickDetail(String id) throws ServiceBizException {
        List<Object> list = new ArrayList<>();
        String sql = "select * from tt_ro_repair_part where ro_no=? and dealer_code=? and d_key=? ";
        list.add(id);
        list.add(FrameworkUtil.getLoginInfo().getDealerCode());
        list.add(CommonConstants.D_KEY);
        return DAOUtil.pageQuery(sql, list);
    }

}
