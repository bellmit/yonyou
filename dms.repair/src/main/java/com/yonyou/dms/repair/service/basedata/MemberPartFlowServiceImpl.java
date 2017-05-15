
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
import com.yonyou.dms.common.domains.PO.basedata.TtRoRepairPartPO;
import com.yonyou.dms.framework.DAO.DAOUtil;
import com.yonyou.dms.framework.util.FrameworkUtil;
import com.yonyou.dms.function.exception.ServiceBizException;
import com.yonyou.dms.function.utils.common.StringUtils;
import com.yonyou.dms.repair.domains.DTO.basedata.TtMemberPartFlowDTO;
import com.yonyou.dms.repair.domains.DTO.basedata.TtRoRepairPartDTO;

/**
 * 会员配件项目
 * 
 * @author chenwei
 * @date 2017年4月18日
 */
@Service
@SuppressWarnings({ "unchecked", "rawtypes" })
public class MemberPartFlowServiceImpl implements MemberPartFlowService {

    @Override
    public List<Map> selectTtMemberPartFlow(Map<String, Object> queryParams) throws ServiceBizException {
        List<Object> params = new ArrayList<Object>();
        StringBuilder sqlSb = new StringBuilder("SELECT A.RO_NO ,A.CARD_ID,A.DEALER_CODE,A.PART_NO,A.D_KEY,A.THIS_USE_QUANTITY,A.ITEM_ID FROM tt_member_part_flow A where 1=1 ");
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
        if (!StringUtils.isNullOrEmpty(queryParams.get("roNo"))) {
            sqlSb.append(" AND A.RO_NO = ? ");
            params.add(queryParams.get("roNo"));
        }
        return DAOUtil.findAll(sqlSb.toString(), params);
    }

    @Override
    public void deleteTtMemberPartFlow(Map<String, Object> deleteParams) throws ServiceBizException {
        List listParams = new ArrayList();
        StringBuilder sbSql = new StringBuilder();
        if (!StringUtils.isNullOrEmpty(deleteParams.get("cardId"))) {
            sbSql.append(" AND CARD_ID = ? ");
            listParams.add(deleteParams.get("cardId"));
        }
        if (!StringUtils.isNullOrEmpty(deleteParams.get("partNo"))) {
            sbSql.append(" AND PART_NO = ? ");
            listParams.add(deleteParams.get("partNo"));
        }
        if (!StringUtils.isNullOrEmpty(deleteParams.get("roNo"))) {
            sbSql.append(" AND RO_NO = ? ");
            listParams.add(deleteParams.get("roNo"));
        }
        if (!StringUtils.isNullOrEmpty(deleteParams.get("dKey"))) {
            sbSql.append(" AND D_KEY = ? ");
            listParams.add(deleteParams.get("dKey"));
        }
        DAOUtil.deleteByDealer(TtMemberPartFlowPO.class, sbSql.toString(), listParams);
    }

    @Override
    public void modifyMemberPartFlowByParams(List updateParams) throws ServiceBizException {
        StringBuilder sbSql = new StringBuilder("THIS_USE_QUANTITY = ? , USED_PART_QUANTITY = ? , UPDATED_BY = ?,UPDATED_AT = ? ");
        StringBuilder sbWhere = new StringBuilder(" CARD_ID = ? and RO_NO = ? and D_KEY = ? and DEALER_CODE = ? ");
        TtMemberPartFlowPO.update(sbSql.toString(), sbWhere.toString(), updateParams);
    }

    @Override
    public void addMemberPartFlow(TtMemberPartFlowDTO cudto) throws ServiceBizException {
        // TODO Auto-generated method stub
        TtMemberPartFlowPO typo = new TtMemberPartFlowPO();
        setMemberPartFlow(typo, cudto);
        typo.saveIt();
    }

    /**
     * 设置MemberPartFlowPO属性
     * 
     * @author chenwei
     * @date 2017年4月16日
     * @param typo
     * @param pyto
     */

    public void setMemberPartFlow(TtMemberPartFlowPO typo, TtMemberPartFlowDTO pyto) {
        typo.setString("DEALER_CODE", pyto.getDealerCode());
        typo.setInteger("D_KEY", pyto.getdKey());
        typo.setString("CREATED_BY", pyto.getCreatedBy());
        typo.setDate("CREATED_AT", pyto.getCreatedAt());
        typo.setString("RO_NO", pyto.getRoNo());
        typo.setInteger("CARD_ID", pyto.getCardId());
        typo.setString("STORAGE_CODE", pyto.getStorageCode());
        typo.setString("PART_NO", pyto.getPartNo());
        typo.setString("PART_NAME", pyto.getPartName());
        typo.setFloat("USED_PART_QUANTITY", pyto.getUsedPartQuantity());
        typo.setDouble("PART_QUANTITY", pyto.getPartQuantity());
        typo.setString("CHARGE_PARTITION_CODE", pyto.getChargePartitionCode());
        typo.setString("UNIT_NAME", pyto.getUnitName());
        typo.setFloat("THIS_USE_QUANTITY", pyto.getThisUseQuantity());
        typo.setDouble("PART_SALES_PRICE", pyto.getPartSalesPrice());
        typo.setDouble("PART_COST_PRICE", pyto.getPartCostPrice());
        typo.setDouble("PART_SALES_AMOUNT", pyto.getPartSalesAmount());
        typo.setDouble("PART_COST_AMOUNT", pyto.getPartCostAmount());
        typo.setString("OPERATOR", pyto.getOperator());
        typo.setDate("OPERATE_TIME", pyto.getOperateTime());
        typo.setDouble("DISCOUNT", pyto.getDiscount());
        typo.setInteger("IS_MAIN_PART", pyto.getIsMainPart());
        typo.setString("LABOUR_CODE", pyto.getLabourCode());
    }

}
