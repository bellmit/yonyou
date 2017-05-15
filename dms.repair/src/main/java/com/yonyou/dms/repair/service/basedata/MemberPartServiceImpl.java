
/** 
 *Copyright 2016 Yonyou Corporation Ltd. All Rights Reserved.
 * This software is published under the terms of the Yonyou Software
 * License version 1.0, a copy of which has been included with this
 * distribution in the LICENSE.txt file.
 *
 * @Project Name : dms.repair
 *
 * @File name : RepairOrderServiceImpl.java
 *
 * @Author : ZhengHe
 *
 * @Date : 2016年8月10日
 *
----------------------------------------------------------------------------------
 *     Date       Who       Version     Comments
 * 1. 2016年8月10日    ZhengHe    1.0
 *
 *
 *
 *
----------------------------------------------------------------------------------
 */

package com.yonyou.dms.repair.service.basedata;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.yonyou.dms.common.domains.PO.basedata.TtMemberPartFlowPO;
import com.yonyou.dms.common.domains.PO.basedata.TtMemberPartPO;
import com.yonyou.dms.framework.DAO.DAOUtil;
import com.yonyou.dms.framework.util.FrameworkUtil;
import com.yonyou.dms.function.exception.ServiceBizException;
import com.yonyou.dms.function.utils.common.StringUtils;
import com.yonyou.dms.repair.domains.DTO.basedata.TtMemberPartDTO;

/**
 * 会员配件项目
 * 
 * @author chenwei
 * @date 2017年4月18日
 */
@Service
@SuppressWarnings({ "unchecked", "rawtypes" })
public class MemberPartServiceImpl implements MemberPartService {

    @Override
    public List<Map> selectTtMemberPart(Map<String, Object> queryParams) throws ServiceBizException {
        List<Object> params = new ArrayList<Object>();
        StringBuilder sqlSb = new StringBuilder("SELECT A.USED_PART_COUNT as usedPartCount,A.CARD_ID as cardId,A.DEALER_CODE as dealerCode,A.PART_NO as partNo,A.D_KEY as dKey,A.PART_QUANTITY as partQuantity,A.ITEM_ID as itemId FROM tt_member_part A where 1=1 ");
        if (!StringUtils.isNullOrEmpty(queryParams.get("cardId"))) {
            sqlSb.append(" AND A.CARD_ID = ? ");
            params.add(queryParams.get("cardId"));
        }
        if (!StringUtils.isNullOrEmpty(queryParams.get("partNo"))) {
            sqlSb.append(" AND A.PART_NO = ? ");
            params.add(queryParams.get("partNo"));
        }
        if (!StringUtils.isNullOrEmpty(queryParams.get("dKey"))) {
            sqlSb.append(" AND A.D_KEY = ? ");
            params.add(queryParams.get("dKey"));
        }
        return DAOUtil.findAll(sqlSb.toString(), params);
    }

    @Override
    public void modifyByItemId(String ItemId, TtMemberPartDTO cudto) throws ServiceBizException {
        // TODO Auto-generated method stub
        TtMemberPartPO lap = TtMemberPartPO.findByCompositeKeys(FrameworkUtil.getLoginInfo().getDealerCode(), ItemId);
        lap.setString("USED_PART_COUNT", cudto.getUsedPartCount());
        lap.setString("UPDATED_BY", cudto.getUpdatedBy());
        lap.setDate("UPDATED_AT", cudto.getUpdatedAt());
        lap.saveIt();
    }
    
    @Override
    public void modifyMemberPartByParams(List updateParams) throws ServiceBizException {
        StringBuilder sbSql = new StringBuilder("USED_PART_COUNT = ? , UPDATED_BY = ? ,UPDATED_AT = ? ");
        StringBuilder sbWhere = new StringBuilder(" CARD_ID = ? and PART_NO = ? and D_KEY = ? and DEALER_CODE = ?");
        TtMemberPartPO.update(sbSql.toString(), sbWhere.toString(), updateParams);
    }

}
