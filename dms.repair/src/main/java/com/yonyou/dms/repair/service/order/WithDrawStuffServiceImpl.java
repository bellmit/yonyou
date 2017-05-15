
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

package com.yonyou.dms.repair.service.order;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.javalite.activejdbc.Base;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.yonyou.dms.common.domains.PO.basedata.PartDailyReportPO;
import com.yonyou.dms.common.domains.PO.basedata.RepairOrderPO;
import com.yonyou.dms.common.domains.PO.basedata.RoManagePO;
import com.yonyou.dms.common.domains.PO.basedata.TmMemberCardActivityPO;
import com.yonyou.dms.common.domains.PO.basedata.TtAccountsTransFlowPO;
import com.yonyou.dms.common.domains.PO.basedata.TtMemCardActiDetailPO;
import com.yonyou.dms.common.domains.PO.basedata.TtMemberPartFlowPO;
import com.yonyou.dms.common.domains.PO.basedata.TtPartObligatedItemPO;
import com.yonyou.dms.common.domains.PO.basedata.TtPartPeriodReportPO;
import com.yonyou.dms.common.domains.PO.basedata.TtRoRepairPartPO;
import com.yonyou.dms.common.domains.PO.basedata.TtShortPartPO;
import com.yonyou.dms.commonAS.domains.DTO.order.RepairOrderDTO;
import com.yonyou.dms.framework.DAO.DAOUtil;
import com.yonyou.dms.framework.DAO.OemDAOUtil;
import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.framework.util.FrameworkUtil;
import com.yonyou.dms.framework.util.Utility;
import com.yonyou.dms.function.common.CommonConstants;
import com.yonyou.dms.function.common.DictCodeConstants;
import com.yonyou.dms.function.exception.ServiceBizException;
import com.yonyou.dms.function.utils.common.StringUtils;
import com.yonyou.dms.repair.domains.DTO.basedata.RoManageDTO;
import com.yonyou.dms.repair.domains.DTO.basedata.TtAccountsTransFlowDTO;
import com.yonyou.dms.repair.domains.DTO.basedata.TtMemCardActiDetailDTO;
import com.yonyou.dms.repair.domains.DTO.basedata.TtPartObligatedDTO;
import com.yonyou.dms.repair.domains.DTO.basedata.TtPartObligatedItemDTO;
import com.yonyou.dms.repair.domains.DTO.basedata.TtPartPeriodReportDTO;
import com.yonyou.dms.repair.domains.DTO.basedata.TtRoRepairPartDTO;

/**
 * 工单接口实现
 * 
 * @author chenwei
 * @date 2017年4月1日
 */
@Service
@SuppressWarnings({ "unchecked", "rawtypes" })
public class WithDrawStuffServiceImpl implements WithDrawStuffService {

    // 定义日志接口
    private static final Logger logger = LoggerFactory.getLogger(WithDrawStuffServiceImpl.class);

    /**
     * （维修领料）查询工单信息列表
     * 
     * @author chenwei
     * @date 2017年3月31日
     * @param queryParams
     * @return
     * @throws ServiceBizException (non-Javadoc)
     * @see com.yonyou.dms.part.service.basedata.TtRoRepairPartService#checkRoDetail(java.util.Map)
     */
    @Override
    public List<Map> checkRoDetail(Map<String, String> queryParams) throws ServiceBizException {
        List<Object> params = new ArrayList<Object>();
        StringBuffer sql = new StringBuffer();
        sql.append("SELECT ( SELECT (CASE WHEN COUNT(item_id) >0 THEN '12781001' else '12781002' end ) FROM TT_RO_REPAIR_PART ro  WHERE ro.DEALER_CODE=a.DEALER_CODE and ");
        sql.append(" ro.ro_no=a.ro_no AND (is_finished is null or is_finished=0 or is_finished=12781002) ) AS COLOR_FLAG,  A.VIN,(SELECT CONSULTANT FROM TT_SALES_PART B WHERE  ");
        sql.append("A.DEALER_CODE=B.DEALER_CODE AND A.RO_NO=B.RO_NO) AS CONSULTANT, A.DEALER_CODE, A.RO_NO, A.SALES_PART_NO, A.BOOKING_ORDER_NO, A.ESTIMATE_NO,A.RO_TYPE, ");
        sql.append("A.REPAIR_TYPE_CODE,E.REPAIR_TYPE_NAME, A.OTHER_REPAIR_TYPE, A.VEHICLE_TOP_DESC,A.SEQUENCE_NO, A.PRIMARY_RO_NO, A.INSURATION_NO, A.INSURATION_CODE,    A.IS_CUSTOMER_IN_ASC, ");
        sql.append("A.IS_SEASON_CHECK, A.OIL_REMAIN, A.IS_WASH, A.IS_TRACE,   A.TRACE_TIME, A.NO_TRACE_REASON, A.NEED_ROAD_TEST, A.RECOMMEND_EMP_NAME,     A.RECOMMEND_CUSTOMER_NAME, ");
        sql.append("A.SERVICE_ADVISOR, A.SERVICE_ADVISOR_ASS, A.RO_STATUS,    A.RO_CREATE_DATE, A.END_TIME_SUPPOSED, A.CHIEF_TECHNICIAN, A.OWNER_NO, A.OWNER_NAME,  ");
        sql.append("A.OWNER_PROPERTY, A.LICENSE,  A.ENGINE_NO, A.BRAND, A.SERIES, A.MODEL, A.IN_MILEAGE,    A.OUT_MILEAGE, A.IS_CHANGE_ODOGRAPH, A.CHANGE_MILEAGE, ");
        sql.append("A.TOTAL_CHANGE_MILEAGE,    A.TOTAL_MILEAGE, A.DELIVERER, A.DELIVERER_GENDER, A.DELIVERER_PHONE,    A.DELIVERER_MOBILE, A.FINISH_USER, A.COMPLETE_TAG, ");
        sql.append("A.WAIT_INFO_TAG, A.WAIT_PART_TAG,    A.COMPLETE_TIME, A.FOR_BALANCE_TIME, A.DELIVERY_TAG, A.DELIVERY_DATE, A.LABOUR_PRICE    , A.LABOUR_AMOUNT, ");
        sql.append("A.REPAIR_PART_AMOUNT, A.SALES_PART_AMOUNT, A.ADD_ITEM_AMOUNT,    A.OVER_ITEM_AMOUNT, A.REPAIR_AMOUNT, A.ESTIMATE_AMOUNT, A.BALANCE_AMOUNT,    A.RECEIVE_AMOUNT, ");
        sql.append("A.SUB_OBB_AMOUNT, A.DERATE_AMOUNT, A.TRACE_TAG, A.REMARK,    A.TEST_DRIVER, A.PRINT_RO_TIME, A.RO_CHARGE_TYPE, A.PRINT_RP_TIME, A.IS_ACTIVITY, ");
        sql.append("A.SCHEME_STATUS,     A.CUSTOMER_DESC, A.LOCK_USER, A.IS_CLOSE_RO, A.RO_SPLIT_STATUS,A.SO_NO,A.VER,A.IS_MAINTAIN,A.IS_LARGESS_MAINTAIN, ");
        sql.append("(SELECT  C.SALES_DATE  FROM(SELECT A.VIN ,B.CHILD_ENTITY AS DEALER_CODE ,A.OWNER_NO ,A.SALES_DATE FROM TM_VEHICLE A LEFT OUTER JOIN TM_ENTITY_RELATIONSHIP B ");
        sql.append("ON A.DEALER_CODE = B.PARENT_ENTITY AND B.BIZ_CODE = 'TM_VEHICLE' LEFT OUTER JOIN TM_VEHICLE_SUBCLASS C ON B.CHILD_ENTITY = C.DEALER_CODE ");
        sql.append("AND A.OWNER_NO = C.OWNER_NO AND A.VIN=C.VIN LEFT OUTER JOIN( SELECT  DEALER_CODE FROM TM_ENTITY_PRIVATE_FIELD  WHERE  TABLE_NAME = 'TM_VEHICLE' GROUP BY ");
        sql.append("DEALER_CODE)D ON D.DEALER_CODE = A.DEALER_CODE) C WHERE A.DEALER_CODE = C.DEALER_CODE AND A.VIN = C.VIN) AS SALES_DATE,A.IS_QS FROM TT_REPAIR_ORDER A ");
        sql.append(" Left join TM_REPAIR_TYPE E on E.DEALER_CODE = A.DEALER_CODE and E.repair_type_code = A.REPAIR_TYPE_CODE ");
        sql.append(" where  A.D_KEY = ").append(CommonConstants.D_KEY);
        if (!StringUtils.isNullOrEmpty(queryParams.get("license"))) {
            sql.append(" AND A.LICENSE = ? ");
            params.add(queryParams.get("license"));
        }
        if (!StringUtils.isNullOrEmpty(queryParams.get("vin"))) {
            sql.append(" AND A.VIN = ? ");
            params.add(queryParams.get("vin"));
        }
        if (!StringUtils.isNullOrEmpty(queryParams.get("roNo"))) {
            sql.append(" AND ( A.RO_NO LIKE ? or  A.LICENSE LIKE ? ) ");
            params.add("%" +queryParams.get("roNo") + "%");
            params.add("%" +queryParams.get("roNo") + "%");
        }
        if (!StringUtils.isNullOrEmpty(queryParams.get("owner"))) {
            sql.append(" AND A.OWNER_NAME LIKE ? ");
            params.add("%" + queryParams.get("owner") + "%");
        }
        if (!StringUtils.isNullOrEmpty(queryParams.get("soNo"))) {
            sql.append(" AND A.SO_NO = ? ");
            params.add(queryParams.get("soNo"));
        }
        if (!StringUtils.isNullOrEmpty(queryParams.get("roStatus"))) {
            sql.append(" AND A.RO_STATUS = ? ");
            params.add(queryParams.get("roStatus"));
        }
        if (!StringUtils.isNullOrEmpty(queryParams.get("serviceAdvisor"))) {
            sql.append(" AND A.SERVICE_ADVISOR = ? ");
            params.add(queryParams.get("serviceAdvisor"));
        }
        if (!StringUtils.isNullOrEmpty(queryParams.get("brand"))) {
            sql.append(" AND A.BRAND = ? ");
            params.add(queryParams.get("brand"));
        }
        if (!StringUtils.isNullOrEmpty(queryParams.get("completeTag"))) {
            sql.append(" AND A.COMPLETE_TAG = ? ");
            params.add(queryParams.get("completeTag"));
        }
        if (!StringUtils.isNullOrEmpty(queryParams.get("isQs"))) {
            sql.append(" AND A.IS_QS = ? ");
            params.add(queryParams.get("isQs"));
        }
        if (!StringUtils.isNullOrEmpty(queryParams.get("status")) && "1".equals(queryParams.get("status"))) {
            sql.append(" and (A.RO_STATUS=? OR A.RO_STATUS=?) ");
            params.add(DictCodeConstants.DICT_RO_STATUS_TYPE_ON_REPAIR);
            params.add(DictCodeConstants.DICT_RO_STATUS_TYPE_FOR_BALANCE);
        }
        sql.append(" ORDER BY A.RO_NO ASC LIMIT 0, 50 ");
        System.out.println(sql.toString() + params.toString());
        return OemDAOUtil.findAll(sql.toString(), params);
    }

    @Override
    public PageInfoDto queryMaintainPicking(Map<String, String> queryParams) throws ServiceBizException {
        // TODO Auto-generated method stub
        List<Object> params = new ArrayList<Object>();
        StringBuffer sql = new StringBuffer();
        sql.append("select 'R' AS UPDATE_STATUS,S.STORAGE_NAME,M.CARD_CODE,A.IS_DISCOUNT,C.PART_MODEL_GROUP_CODE_SET,ITEM_ID, A.PART_BATCH_NO,RO_NO, A.DEALER_CODE, A.PART_NO, A.PART_NAME, A.STORAGE_CODE, ");
        sql.append("A.CHARGE_PARTITION_CODE, A.STORAGE_POSITION_CODE, A.UNIT_CODE, A.CARD_ID,  A.MANAGE_SORT_CODE, A.OUT_STOCK_NO, A.PRICE_TYPE, A.IS_MAIN_PART, ");
        sql.append("A.PART_QUANTITY,  PRICE_RATE, A.OEM_LIMIT_PRICE, A.PART_COST_PRICE, A.PART_SALES_PRICE, A.DISCOUNT,  PART_COST_AMOUNT, PART_SALES_AMOUNT, SENDER, RECEIVER, ");
        sql.append("SEND_TIME,LABOUR_CODE, A.NON_ONE_OFF, IS_FINISHED, CAST(CAST(BATCH_NO AS SIGNED) AS CHAR(4))AS BATCH_NO, ACTIVITY_CODE, PRE_CHECK, NEEDLESS_REPAIR,  ");
        sql.append("CONSIGN_EXTERIOR, PRINT_RP_TIME, PRINT_BATCH_NO,  0 as IS_SELECTED  ,C.LOCKED_QUANTITY, ");
        sql.append("(C.STOCK_QUANTITY + C.BORROW_QUANTITY -  ifnull(C.LEND_QUANTITY, 0)  -  ifnull(C.LOCKED_QUANTITY, 0) )  AS USEABLE_STOCK, ");
        sql.append("CASE when  ifnull(A.PART_COST_AMOUNT, 0)  = 0 then  0 else PART_SALES_AMOUNT / PART_COST_AMOUNT end as ADD_RATE, ");
        sql.append("D.DOWN_TAG,D.LIMIT_PRICE,  A.LABOUR_NAME,D.IS_BACK,D.PART_INFIX ");
        sql.append("from TT_RO_REPAIR_PART A LEFT JOIN TM_MEMBER_CARD M on A.DEALER_CODE=M.DEALER_CODE AND A.CARD_ID=M.CARD_ID ");
        sql.append("LEFT JOIN TM_PART_STOCK C ON A.DEALER_CODE = C.DEALER_CODE  AND A.D_KEY = C.D_KEY AND A.STORAGE_CODE = C.STORAGE_CODE  AND A.PART_NO = C.PART_NO ");
        sql.append("left join TM_PART_INFO D ON (D.PART_NO = A.PART_NO AND D.DEALER_CODE = A.DEALER_CODE) ");
        sql.append("left join TM_STORAGE S ON (S.DEALER_CODE = A.DEALER_CODE AND S.STORAGE_CODE = A.STORAGE_CODE) where  A.NEEDLESS_REPAIR = ").append(DictCodeConstants.DICT_IS_NO);
        if (!StringUtils.isNullOrEmpty(queryParams.get("roNo"))) {
            sql.append(" AND A.RO_NO = ? ");
            params.add(queryParams.get("roNo"));
        }
        if (!StringUtils.isNullOrEmpty(queryParams.get("dKey"))) {
            sql.append(" AND A.D_KEY  = ? ");
            params.add(queryParams.get("dKey"));
        }
        PageInfoDto id = DAOUtil.pageQuery(sql.toString(), params);
        return id;
    }

    /**
     * 检查工单单据是否被锁住
     * 
     * @author chenwei
     * @date 2017年4月5日
     * @param queryParams
     * @return
     * @throws ServiceBizException (non-Javadoc)
     * @see com.yonyou.dms.repair.service.order.WithDrawStuffService#checkRepairOrderLock(java.util.Map)
     */
    @Override
    public List<Map> checkRepairOrderLock(Map<String, String> queryParams) throws ServiceBizException {
        // TODO Auto-generated method stub
        List<Object> params = new ArrayList<Object>();
        StringBuffer sql = new StringBuffer();
        sql.append("SELECT LOCK_USER FROM TT_REPAIR_ORDER WHERE RO_NO=? AND DEALER_CODE ='"
                   + FrameworkUtil.getLoginInfo().getDealerCode() + "'");
        if (!StringUtils.isNullOrEmpty(queryParams.get("roNo"))) {
            params.add(queryParams.get("roNo"));
        }
        return OemDAOUtil.findAll(sql.toString(), params);
    }

    /**
     * 锁住维修工单单据
     * 
     * @author chenwei
     * @date 2017年4月5日
     * @param roNo
     * @param repairOrderto
     * @throws ServiceBizException (non-Javadoc)
     * @see com.yonyou.dms.repair.service.order.WithDrawStuffService#lockRepairOrder(java.lang.String,
     * com.yonyou.dms.commonAS.domains.DTO.order.RepairOrderDTO)
     */
    @Override
    public void lockRepairOrder(String roNo, RepairOrderDTO repairOrderto) throws ServiceBizException {
        // TODO Auto-generated method stub
        RepairOrderPO lap = RepairOrderPO.findByCompositeKeys(FrameworkUtil.getLoginInfo().getDealerCode(), roNo);
        lap.setString("LOCK_USER", FrameworkUtil.getLoginInfo().getEmployeeNo());
        lap.saveIt();
    }

    @Override
    public PageInfoDto queryPartInfo(Map<String, String> queryParams) throws ServiceBizException {
        List<Object> params = new ArrayList<Object>();
        StringBuffer sql = new StringBuffer();
        sql.append("select A.NODE_PRICE,A.INSURANCE_PRICE,TS.STORAGE_NAME, A.DEALER_CODE, A.PART_NO,TS.CJ_TAG, A.STORAGE_CODE, A.PART_BATCH_NO,A.STORAGE_POSITION_CODE, ");
        sql.append("A.PART_NAME,  A.SPELL_CODE, A.UNIT_CODE, A.STOCK_QUANTITY, A.SALES_PRICE, A.CLAIM_PRICE, B.LIMIT_PRICE, B.INSTRUCT_PRICE, A.LATEST_PRICE, ROUND(A.COST_PRICE,4) ");
        sql.append("AS COST_PRICE, A.COST_AMOUNT, A.BORROW_QUANTITY, A.PART_STATUS,C.LOCKED_QUANTITY, A.LEND_QUANTITY,  A.LAST_STOCK_IN, A.LAST_STOCK_OUT, A.REMARK, A.CREATED_BY, ");
        sql.append("A.CREATED_AT, A.UPDATED_BY, A.UPDATED_AT,B.DOWN_TAG,B.OPTION_NO,  A.VER, A.PART_GROUP_CODE,  C.PART_MODEL_GROUP_CODE_SET,  ");
        sql.append("(A.STOCK_QUANTITY + A.BORROW_QUANTITY -  IFNULL(A.LEND_QUANTITY, 0)  ) AS USEABLE_QUANTITY, ");
        sql.append("CASE WHEN (SELECT 1 FROM TM_MAINTAIN_PART CC WHERE  CC.PART_NO = B.PART_NO  AND CC.DEALER_CODE = B.DEALER_CODE ) >0 THEN 12781001 ELSE 12781002 END  AS IS_MAINTAIN  ");
        sql.append("from TM_PART_STOCK_ITEM  A  LEFT OUTER JOIN ");
        sql.append("(SELECT B.CHILD_ENTITY AS DEALER_CODE, A.PART_NO,A.LIMIT_PRICE,A.DOWN_TAG,A.OPTION_NO,A.INSTRUCT_PRICE FROM TM_PART_INFO A INNER JOIN TM_ENTITY_RELATIONSHIP B ");
        sql.append("ON A.DEALER_CODE = B.PARENT_ENTITY AND B.BIZ_CODE = 'TM_PART_INFO') B ON (A.DEALER_CODE = B.DEALER_CODE AND A.PART_NO = B.PART_NO )  ");
        sql.append("LEFT OUTER JOIN TM_PART_STOCK C ON (A.DEALER_CODE = C.DEALER_CODE AND A.PART_NO = C.PART_NO AND A.STORAGE_CODE=C.STORAGE_CODE) ");
        sql.append("LEFT JOIN TM_STORAGE TS ON  A.DEALER_CODE=TS.DEALER_CODE AND A.STORAGE_CODE=TS.STORAGE_CODE ");
        sql.append("WHERE TS.CJ_TAG=12781001  and A.SALES_PRICE > 0    and (A.STOCK_QUANTITY + A.BORROW_QUANTITY - A.LEND_QUANTITY -C.LOCKED_QUANTITY)  > 0 ");
        sql.append("AND ( 1=2  OR A.STORAGE_CODE='CB10'   OR A.STORAGE_CODE='CKA1'   OR A.STORAGE_CODE='CKA2'   OR A.STORAGE_CODE='CKA3'   OR A.STORAGE_CODE='CKA4'   OR ");
        sql.append("A.STORAGE_CODE='CKA5'   OR A.STORAGE_CODE='CKA6'   OR A.STORAGE_CODE='CKB1'   OR A.STORAGE_CODE='CKB2'   OR A.STORAGE_CODE='CKB3'   OR A.STORAGE_CODE='CKB4' ");
        sql.append("OR A.STORAGE_CODE='CKB5'   OR A.STORAGE_CODE='CKB6'   OR A.STORAGE_CODE='CKB7'   OR A.STORAGE_CODE='CKB8'   OR A.STORAGE_CODE='CKB9'   OR ");
        sql.append("A.STORAGE_CODE='CKF1'   OR A.STORAGE_CODE='JPCK'   OR A.STORAGE_CODE='OEMK'   OR A.STORAGE_CODE='QTCK'   OR A.STORAGE_CODE='SBCK'   OR A.STORAGE_CODE='WJGK' ");
        sql.append("OR A.STORAGE_CODE='YLCK'   OR A.STORAGE_CODE='ZCCK'   OR A.STORAGE_CODE='ZSCK') and ");
        sql.append("A.PART_STATUS<>12781001  AND C.D_KEY = 0 and TS.CJ_TAG=12781001 ");
        if (!StringUtils.isNullOrEmpty(queryParams.get("partNo"))) {
            sql.append(" AND A.PART_NO like ? ");
            params.add("%" + queryParams.get("partNo") + "%");
        }
        if (!StringUtils.isNullOrEmpty(queryParams.get("storageCode"))) {
            sql.append(" AND A.storage_Code = ? ");
            params.add(queryParams.get("storageCode"));
        }
        if (!StringUtils.isNullOrEmpty(queryParams.get("brand"))) {
            sql.append(" AND A.brand like ? ");
            params.add("%" + queryParams.get("brand") + "%");
        }
        if (!StringUtils.isNullOrEmpty(queryParams.get("spellCode"))) {
            sql.append(" AND A.spell_Code = ? ");
            params.add(queryParams.get("spellCode"));
        }
        if (!StringUtils.isNullOrEmpty(queryParams.get("partName"))) {
            sql.append(" AND A.part_Name like ? ");
            params.add("%" + queryParams.get("partName") + "%");
        }
        if (!StringUtils.isNullOrEmpty(queryParams.get("partGroupCode"))) {
            sql.append(" AND A.part_Group_Code = ? ");
            params.add(queryParams.get("partGroupCode"));
        }
        if (!StringUtils.isNullOrEmpty(queryParams.get("storagePositionCode"))) {
            sql.append(" AND A.storage_Position_Code = ? ");
            params.add(queryParams.get("storagePositionCode"));
        }
        if (!StringUtils.isNullOrEmpty(queryParams.get("partModelGroupCodeSet"))) {
            sql.append(" AND C.PART_MODEL_GROUP_CODE_SET = ? ");
            params.add(queryParams.get("partModelGroupCodeSet"));
        }
        //if (!StringUtils.isNullOrEmpty(queryParams.get("isCheck"))) {
            // sql.append(" AND A.storage_Position_Code = ? ");
           // params.add(queryParams.get("isCheck"));
       // }
        //if (!StringUtils.isNullOrEmpty(queryParams.get("isSalePriceBigger"))) {
            // sql.append(" AND A.storage_Position_Code = ? ");
        //    params.add(queryParams.get("isSalePriceBigger"));
        //}
        PageInfoDto id = DAOUtil.pageQuery(sql.toString(), params);
        return id;
    }

    /**
     * 根据配件信息查询配件库存
     * 
     * @author chenwei
     * @date 2017年4月7日
     * @param queryParam
     * @return
     * @throws ServiceBizException (non-Javadoc)
     * @see com.yonyou.dms.repair.service.order.WithDrawStuffService#queryPartStock(java.util.Map)
     */
    @Override
    public PageInfoDto queryPartStock(Map<String, String> queryParams) throws ServiceBizException {
        List<Object> params = new ArrayList<Object>();
        StringBuffer sql = new StringBuffer();
        sql.append("SELECT '' as POS_CODE,'' as POS_NAME,A.PART_MODEL_GROUP_CODE_SET, 0.0 as PART_QUANTITY,  A.DEALER_CODE, A.PART_NO, A.STORAGE_CODE, A.STORAGE_POSITION_CODE, ");
        sql.append("A.PART_NAME,  A.SPELL_CODE, A.UNIT_CODE, A.STOCK_QUANTITY, A.SALES_PRICE, A.CLAIM_PRICE, A.PART_GROUP_CODE,  B.LIMIT_PRICE, A.LATEST_PRICE, A.COST_PRICE AS ");
        sql.append("COST_PRICE, A.COST_AMOUNT, A.MAX_STOCK,B.DOWN_TAG,  A.BORROW_QUANTITY, A.LEND_QUANTITY, A.LOCKED_QUANTITY, A.LAST_STOCK_IN, B.INSTRUCT_PRICE, A.PART_STATUS,  ");
        sql.append("A.LAST_STOCK_OUT, A.REMARK, A.CREATED_BY, A.CREATED_AT, A.UPDATED_BY, A.UPDATED_AT, A.VER, A.MIN_STOCK,  B.OPTION_NO,  ");
        sql.append("A.NODE_PRICE, B.PLAN_PRICE, B.OEM_LIMIT_PRICE, B.URGENT_PRICE,A.INSURANCE_PRICE, D.OPTION_STOCK, ");
        sql.append("(A.STOCK_QUANTITY + A.BORROW_QUANTITY - A.LEND_QUANTITY - A.LOCKED_QUANTITY) AS USEABLE_STOCK, ");
        sql.append("B.IS_BACK,B.PART_INFIX,  B.MIN_LIMIT_PRICE  ");
        sql.append("FROM TM_PART_STOCK A  LEFT OUTER JOIN ( ");
        sql.append("SELECT B.CHILD_ENTITY AS DEALER_CODE,A.PART_NO,A.LIMIT_PRICE,A.DOWN_TAG,A.INSTRUCT_PRICE,A.OPTION_NO,A.PLAN_PRICE,A.OEM_LIMIT_PRICE, ");
        sql.append("A.URGENT_PRICE,a.is_back,a.PART_INFIX,A.MIN_LIMIT_PRICE FROM TM_PART_INFO A INNER JOIN TM_ENTITY_RELATIONSHIP B ON A.DEALER_CODE = B.PARENT_ENTITY ");
        sql.append("AND B.BIZ_CODE = 'TM_PART_INFO') B ");
        sql.append("ON ( A.DEALER_CODE = B.DEALER_CODE AND A.PART_NO= B.PART_NO )  ");
        sql.append("LEFT OUTER JOIN (select DEALER_CODE, part_no, sum(C.STOCK_QUANTITY) AS OPTION_STOCK FROM TM_PART_STOCK C GROUP BY DEALER_CODE,PART_NO ) D  ON ( A.DEALER_CODE = ");
        sql.append("D.DEALER_CODE AND A.PART_NO= D.PART_NO AND B.OPTION_NO = D.PART_NO ) WHERE 1=1 AND A.D_KEY =  0 ");
        if (!StringUtils.isNullOrEmpty(queryParams.get("storageCode"))) {
            sql.append(" AND A.STORAGE_CODE = ? ");
            params.add(queryParams.get("storageCode"));
        }
        if (!StringUtils.isNullOrEmpty(queryParams.get("partNo"))) {
            sql.append(" AND A.PART_NO = ? ");
            params.add(queryParams.get("partNo"));
        }
        PageInfoDto id = DAOUtil.pageQuery(sql.toString(), params);
        return id;
    }

    /**
     * 修改
     * 
     * @author chenwei
     * @date 2017年4月17日
     * @param ItemId
     * @param cudto
     * @throws ServiceBizException (non-Javadoc)
     * @see com.yonyou.dms.repair.service.order.WithDrawStuffService#modifyByItemId(java.lang.String,
     * com.yonyou.dms.repair.domains.DTO.basedata.TtRoRepairPartDTO)
     */
    @Override
    public void modifyByItemId(Long ItemId, TtRoRepairPartDTO cudto) throws ServiceBizException {
        // TODO Auto-generated method stub
        TtRoRepairPartPO lap = TtRoRepairPartPO.findByCompositeKeys(FrameworkUtil.getLoginInfo().getDealerCode(),
                                                                    ItemId);
        lap.setString("ACTIVITY_CODE", cudto.getACTIVITY_CODE());
        lap.setInteger("CARD_ID", cudto.getCARD_ID());
        lap.setInteger("BATCH_NO", cudto.getBATCH_NO());
        lap.setString("CHARGE_PARTITION_CODE", cudto.getCHARGE_PARTITION_CODE());
        lap.setInteger("CONSIGN_EXTERIOR", cudto.getCONSIGN_EXTERIOR());
        lap.setDouble("DISCOUNT", cudto.getDISCOUNT());
        lap.setString("DEALER_CODE", cudto.getDEALER_CODE());
        lap.setInteger("IS_MAIN_PART", cudto.getIS_MAIN_PART());
        lap.setString("MANAGE_SORT_CODE", cudto.getMANAGE_SORT_CODE());
        lap.setInteger("NEEDLESS_REPAIR", cudto.getNEEDLESS_REPAIR());
        lap.setDouble("OEM_LIMIT_PRICE", cudto.getOEM_LIMIT_PRICE());
        lap.setString("PART_BATCH_NO", cudto.getPART_BATCH_NO());
        lap.setDouble("PART_COST_AMOUNT", cudto.getPART_COST_AMOUNT());
        lap.setDouble("PART_COST_PRICE", cudto.getPART_COST_PRICE());
        lap.setString("OUT_STOCK_NO", cudto.getOUT_STOCK_NO());
        lap.setDouble("PART_QUANTITY", cudto.getPART_QUANTITY());
        lap.setString("PART_NAME", cudto.getPART_NAME());
        lap.setDouble("PART_SALES_AMOUNT", cudto.getPART_SALES_AMOUNT());
        lap.setDouble("PART_SALES_PRICE", cudto.getPART_SALES_PRICE());
        lap.setInteger("PRE_CHECK", cudto.getPRE_CHECK());
        lap.setDouble("PRICE_RATE", cudto.getPRICE_RATE());
        lap.setInteger("PRICE_TYPE", cudto.getPRICE_TYPE());
        lap.setInteger("PRINT_BATCH_NO", cudto.getPRINT_BATCH_NO());
        lap.setDate("PRINT_RP_TIME", cudto.getPRINT_RP_TIME());
        lap.setString("RECEIVER", cudto.getRECEIVER());
        lap.setString("RO_NO", cudto.getRO_NO());
        lap.setString("SENDER", cudto.getSENDER());
        lap.setString("STORAGE_CODE", cudto.getSTORAGE_CODE());
        lap.setString("STORAGE_POSITION_CODE", cudto.getSTORAGE_POSITION_CODE());
        lap.setString("UNIT_CODE", cudto.getUNIT_CODE());
        lap.setInteger("IS_DISCOUNT", cudto.getIS_DISCOUNT());
        lap.setString("LABOUR_CODE", cudto.getLABOUR_CODE());
        lap.setInteger("ITEM_ID_LABOUR", cudto.getITEM_ID_LABOUR());
        lap.setString("LABOUR_NAME", cudto.getLABOUR_NAME());
        lap.setLong("FROM_TYPE", cudto.getFROM_TYPE());
        lap.setString("PART_INFIX", cudto.getPART_INFIX());
        lap.saveIt();
    }

    @Override
    public Long addTtRoRepairPart(TtRoRepairPartDTO cudto) throws ServiceBizException {
        // TODO Auto-generated method stub
        TtRoRepairPartPO typo = new TtRoRepairPartPO();
        checkRoRepairPart(cudto);
        setRoRepairPart(typo, cudto);
        typo.saveIt();
        return typo.getLongId();
    }

    /**
     * 新增之前检查是否已存在 同一个经销商下同一个工单、仓库、配件id只能存在一个
     * 
     * @author chenwei
     * @date 2017年4月16日
     * @param pyto
     */
    public void checkRoRepairPart(TtRoRepairPartDTO pyto) {
        StringBuffer sb = new StringBuffer("select DEALER_CODE,ITEM_ID from TT_RO_REPAIR_PART where 1=1  and ITEM_ID=?");
        List<Object> list = new ArrayList<Object>();
        list.add(pyto.getITEM_ID());
        List<Map> map = DAOUtil.findAll(sb.toString(), list);
        if (map.size() > 0) {
            throw new ServiceBizException("工单维修配件明细已经存在！");
        }
    }

    /**
     * 设置TroubleDescPO属性
     * 
     * @author chenwei
     * @date 2017年4月16日
     * @param typo
     * @param pyto
     */

    public void setRoRepairPart(TtRoRepairPartPO typo, TtRoRepairPartDTO pyto) {
        typo.setInteger("ITEM_ID", pyto.getITEM_ID());
        typo.setString("DEALER_CODE", pyto.getDEALER_CODE());
        typo.setInteger("ITEM_ID_LABOUR", pyto.getITEM_ID_LABOUR());
        typo.setString("PART_NO", pyto.getPART_NO());
        typo.setString("PART_NAME", pyto.getPART_NAME());
        typo.setString("STORAGE_CODE", pyto.getSTORAGE_CODE());
        typo.setString("CHARGE_PARTITION_CODE", pyto.getCHARGE_PARTITION_CODE());
        typo.setString("STORAGE_POSITION_CODE", pyto.getSTORAGE_POSITION_CODE());
        typo.setString("UNIT_CODE", pyto.getUNIT_CODE());
        typo.setString("PART_BATCH_NO", pyto.getPART_BATCH_NO());
        typo.setString("MANAGE_SORT_CODE", pyto.getMANAGE_SORT_CODE());
        typo.setString("OUT_STOCK_NO", pyto.getOUT_STOCK_NO());
        typo.setInteger("PRICE_TYPE", pyto.getPRICE_TYPE());
        typo.setInteger("IS_MAIN_PART", pyto.getIS_MAIN_PART());
        typo.setDouble("PART_QUANTITY", pyto.getPART_QUANTITY());
        typo.setDouble("PRICE_RATE", pyto.getPRICE_RATE());
        typo.setDouble("OEM_LIMIT_PRICE", pyto.getOEM_LIMIT_PRICE());
        typo.setDouble("PART_COST_PRICE", pyto.getPART_COST_PRICE());
        typo.setDouble("PART_SALES_PRICE", pyto.getPART_SALES_PRICE());
        typo.setDouble("PART_COST_AMOUNT", pyto.getPART_COST_AMOUNT());
        typo.setDouble("PART_SALES_AMOUNT", pyto.getPART_SALES_AMOUNT());
        typo.setString("SENDER", pyto.getSENDER());
        typo.setString("RECEIVER", pyto.getRECEIVER());
        typo.setDate("SEND_TIME", pyto.getSEND_TIME());
        typo.setInteger("IS_FINISHED", pyto.getIS_FINISHED());
        typo.setInteger("BATCH_NO", pyto.getBATCH_NO());
        typo.setString("ACTIVITY_CODE", pyto.getACTIVITY_CODE());
        typo.setInteger("PRE_CHECK", pyto.getPRE_CHECK());
        typo.setDouble("DISCOUNT", pyto.getDISCOUNT());
        typo.setInteger("INTER_RETURN", pyto.getINTER_RETURN());
        typo.setInteger("NEEDLESS_REPAIR", pyto.getNEEDLESS_REPAIR());
        typo.setInteger("CONSIGN_EXTERIOR", pyto.getCONSIGN_EXTERIOR());
        typo.setDate("PRINT_RP_TIME", pyto.getPRINT_RP_TIME());
        typo.setInteger("PRINT_BATCH_NO", pyto.getPRINT_BATCH_NO());
        typo.setString("LABOUR_CODE", pyto.getLABOUR_CODE());
        typo.setString("MODEL_LABOUR_CODE", pyto.getMODEL_LABOUR_CODE());
        typo.setString("PACKAGE_CODE", pyto.getPACKAGE_CODE());
        typo.setInteger("IS_DISCOUNT", pyto.getIS_DISCOUNT());
        typo.setString("REPAIR_TYPE_CODE", pyto.getREPAIR_TYPE_CODE());
        typo.setInteger("NON_ONE_OFF", pyto.getNON_ONE_OFF());
        typo.setInteger("ADD_TAG", pyto.getADD_TAG());
        typo.setInteger("REASON", pyto.getREASON());
        typo.setInteger("CARD_ID", pyto.getCARD_ID());
        typo.setLong("FROM_TYPE", pyto.getFROM_TYPE());
        typo.setDate("DXP_DATE", pyto.getDXP_DATE());
        typo.setString("LABOUR_NAME", pyto.getLABOUR_NAME());
        typo.setString("RO_NO", pyto.getRO_NO());
        typo.setDouble("OTHER_PART_COST_PRICE", pyto.getOTHER_PART_COST_PRICE());
        typo.setDouble("OTHER_PART_COST_AMOUNT", pyto.getOTHER_PART_COST_AMOUNT());
        typo.setString("POS_CODE", pyto.getPOS_CODE());
        typo.setString("POS_NAME", pyto.getPOS_NAME());
        typo.setString("PART_INFIX", pyto.getPART_INFIX());
        typo.setInteger("WAR_LEVEL", pyto.getWAR_LEVEL());
        typo.setInteger("IS_OLDPART_TREAT", pyto.getIS_OLDPART_TREAT());
        typo.setInteger("PART_CATEGORY", pyto.getPART_CATEGORY());
        typo.setString("MAINTAIN_PACKAGE_CODE", pyto.getMAINTAIN_PACKAGE_CODE());
        typo.setDate("REPORT_B_I_DATETIME", pyto.getREPORT_B_I_DATETIME());
    }

    @Override
    public List<Map> selectEmployees(Map<String, String> queryParam) throws ServiceBizException {
        StringBuilder sqlSb = new StringBuilder("select em.EMPLOYEE_NO,em.EMPLOYEE_NAME,em.DEALER_CODE  from tm_employee em where 1=1 ");
        List<String> params = new ArrayList<String>();
        return DAOUtil.findAll(sqlSb.toString(), params);
    }

    @Override
    public List<Map> selectRepairOrder(Map<String, Object> queryParams) throws ServiceBizException {
        List<Object> params = new ArrayList<Object>();
        StringBuilder sqlSb = new StringBuilder("select RO_NO as roNo,DEALER_CODE as dealerCode,D_KEY as dKey,REPAIR_TYPE_CODE as repairTypeCode,BRAND as brand,SERIES as series,ro_status as roStatus from TT_REPAIR_ORDER tt where 1=1 ");
        if (!StringUtils.isNullOrEmpty(queryParams.get("roNo"))) {
            sqlSb.append(" AND RO_NO = ? ");
            params.add(queryParams.get("roNo"));
        }
        if (!StringUtils.isNullOrEmpty(queryParams.get("dKey"))) {
            sqlSb.append(" AND  D_KEY = ?");
            params.add(queryParams.get("dKey"));
        }
        return DAOUtil.findAll(sqlSb.toString(), params);
    }

    @Override
    public String selectDefaultPara(Map<String, Object> queryParams) throws ServiceBizException {
        List<Object> params = new ArrayList<Object>();
        StringBuilder sqlSb = new StringBuilder("select A.DEALER_CODE as dealerCode,A.Item_Code as itemCode,A.DEFAULT_VALUE as defaultValue from TM_DEFAULT_PARA A where 1=1 ");
        if (!StringUtils.isNullOrEmpty(queryParams.get("itemCode"))) {
            sqlSb.append(" AND A.Item_Code = ? ");
            params.add(queryParams.get("itemCode"));
        }
        List<Map> list = DAOUtil.findAll(sqlSb.toString(), params);
        if(null != list && list.size() > 0){
            Map map = list.get(0);
            if(null != map && !StringUtils.isNullOrEmpty(map.get("defaultValue")))
            return map.get("defaultValue").toString();
            else return null;
        }else{
            return null;
        }
    }

    @Override
    public List<Map> selectRoRepairPart(Map<String, Object> queryParams) throws ServiceBizException {
        List<Object> params = new ArrayList<Object>();
        StringBuilder sqlSb = new StringBuilder("select A.DEALER_CODE as dealerCode,A.RO_NO as roNo,A.D_KEY as dKey,A.ACTIVITY_CODE as activityCode,A.PART_SALES_PRICE as partSalesPrice,A.is_finished as isFinished,A.card_id as cardId,");
        sqlSb.append("A.STORAGE_CODE AS storageCode,A.PART_NO AS partNo,A.`PART_NAME` AS partName,");
        sqlSb.append("A.`CHARGE_PARTITION_CODE` AS chargePartitionCode,A.`UNIT_CODE` AS unitCode,A.`PART_QUANTITY` AS partQuantity,A.`PART_SALES_PRICE` AS partSalesPrice,");
        sqlSb.append("A.`PART_COST_PRICE` AS partCostPrice,A.`PART_SALES_AMOUNT` AS partSalesAmount,A.`PART_COST_AMOUNT` AS partCostAmount,A.`DISCOUNT` AS discount,");
        sqlSb.append(" A.`IS_MAIN_PART` AS isMainPart,A.`LABOUR_CODE` AS labourCode,A.MANAGE_SORT_CODE AS manageSortCode FROM TT_RO_REPAIR_PART A where 1=1 ");
        if (!StringUtils.isNullOrEmpty(queryParams.get("roNo"))) {
            sqlSb.append(" AND A.RO_NO = ? ");
            params.add(queryParams.get("roNo"));
        }
        if (!StringUtils.isNullOrEmpty(queryParams.get("dKey"))) {
            sqlSb.append(" AND A.D_KEY = ? ");
            params.add(queryParams.get("dKey"));
        }
        if (!StringUtils.isNullOrEmpty(queryParams.get("itemId"))) {
            sqlSb.append(" AND A.ITEM_ID = ? ");
            params.add(queryParams.get("itemId"));
        }
        if (!StringUtils.isNullOrEmpty(queryParams.get("needlessRepair"))) {
            sqlSb.append(" AND A.NEEDLESS_REPAIR = ? ");
            params.add(queryParams.get("needlessRepair"));
        }
        return DAOUtil.findAll(sqlSb.toString(), params);
    }
    
    @Override
    public List<TtRoRepairPartDTO> changeMapToRoRepairPart(List<Map> list) {
        List<TtRoRepairPartDTO> dtoList = new ArrayList<TtRoRepairPartDTO>();
        if(null != list && list.size() > 0){
            for(int i = 0; i < list.size(); i++){
                Map map = list.get(i);
                TtRoRepairPartDTO dto = new TtRoRepairPartDTO();
                if(!StringUtils.isNullOrEmpty(map.get("isFinished")))
                    dto.setIS_FINISHED(Integer.valueOf(map.get("isFinished").toString()));
                if(!StringUtils.isNullOrEmpty(map.get("partSalesAmount")))
                    dto.setPART_SALES_AMOUNT(Double.valueOf(map.get("partSalesAmount").toString()));
                if(!StringUtils.isNullOrEmpty(map.get("roNo")))
                    dto.setRO_NO(map.get("roNo").toString());
                dtoList.add(dto);
            }
            return dtoList;
        }else{
            return dtoList;
        }
    }

    @Override
    public List<Map> selectTtActivity(Map<String, String> queryParam) throws ServiceBizException {
        StringBuilder sqlSb = new StringBuilder("select DEALER_CODE,DOWN_TAG from tt_activity where 1=1 ");
        if (StringUtils.isNullOrEmpty(queryParam.get("temp"))) {
            sqlSb.append(" and activity_code in (" + queryParam.get("temp") + ")");
        }
        List<String> params = new ArrayList<String>();
        return DAOUtil.findAll(sqlSb.toString(), params);
    }

    /**
     * 根据工单号,项目代码,维修组合,查询维修项目ID TODO description
     * 
     * @author chenwei
     * @date 2017年4月16日
     * @param conn
     * @param entityCode
     * @param labourCode
     * @param modelLabourCode
     * @param packageCode
     * @param roNo
     * @param labourName
     * @return
     * @throws Exception
     */
    public List<Map> queryRoLabourByCodeAndRoNo(String dealerCode, String labourCode, String modelLabourCode,
                                                String packageCode, String roNo,
                                                String labourName) throws ServiceBizException {
        StringBuffer sql = new StringBuffer("");
        sql.append(" select ITEM_ID,DEALER_CODE,LABOUR_CODE from TT_RO_LABOUR where DEALER_CODE='" + dealerCode
                   + "'  ");
        if (!StringUtils.isNullOrEmpty(labourCode)) {
            sql.append(" and LABOUR_CODE='" + labourCode + "' ");
        }
        if (!StringUtils.isNullOrEmpty(roNo)) {
            sql.append(" and RO_NO='" + roNo + "' ");
        }
        if (!StringUtils.isNullOrEmpty(labourName)) {
            sql.append(" and LABOUR_NAME = '" + labourName + "' ");
        }
        sql.append(" order by ITEM_ID desc  LIMIT 1");
        List<String> params = new ArrayList<String>();
        return DAOUtil.findAll(sql.toString(), params);
    }

    @Override
    public void deleteRoRepairPart(Long id) throws ServiceBizException {
        // TODO Auto-generated method stub
        TtRoRepairPartPO lomipo = TtRoRepairPartPO.findByCompositeKeys(FrameworkUtil.getLoginInfo().getDealerCode(),
                                                                       id);
        lomipo.delete();
    }

    @Override
    public List<Map> getNonOemPartListOutReturn(String fieldName, String sheetTable, String sheetName, String sheetNo,
                                                String quantityFieldName) throws ServiceBizException {
        StringBuilder sqlSb = new StringBuilder("select AA.part_no,AA.STORAGE_CODE,SUM(AA.PART_QUANTITY) as PART_QUANTITY FROM ");
        sqlSb.append("(select A.PART_NO,A.PART_NAME,A.STORAGE_CODE,A.").append(fieldName).append("As PART_QUANTITY ");
        sqlSb.append(" from ").append(sheetTable);
        sqlSb.append(" A left join TM_PART_INFO B on (A.part_NO = B.PART_NO AND A.DEALER_CODE = B.DEALER_CODE) ");
        sqlSb.append(" WHERE A.DEALER_CODE = '").append(FrameworkUtil.getLoginInfo().getDealerCode());
        sqlSb.append("' AND B.DOWN_TAG = ").append(DictCodeConstants.DICT_IS_NO);
        sqlSb.append(" AND A.STORAGE_CODE ='OEMK' AND A.").append(sheetName).append(" = '").append(sheetNo).append("' ");
        sqlSb.append(" ) AA group by AA.PART_NO,AA.STORAGE_CODE having SUM(AA.PART_QUANTITY) > 0 ");
        List<String> params = new ArrayList<String>();
        /*
         * if(StringUtils.isNullOrEmpty(queryParams.get("temp"))){ sqlSb.append(" and activity_code in (" +
         * queryParams.get("temp") + ")"); } if(!StringUtils.isNullOrEmpty(queryParams.get("itemId"))){
         * sqlSb.append(" AND A.ITEM_ID = ? "); params.add(""); }
         */
        return DAOUtil.findAll(sqlSb.toString(), params);
    }

    @Override
    public void reCalcRepairAmount(String roNo, RepairOrderDTO cudto) throws ServiceBizException {
        // TODO Auto-generated method stub
        StringBuilder sbSql = new StringBuilder("REPAIR_PART_AMOUNT =( SELECT SUM(Coalesce(DISCOUNT,1)*Coalesce(PART_QUANTITY,0)*Coalesce(PART_SALES_PRICE,0)) ");
        sbSql.append(" from TT_RO_REPAIR_PART  where DEALER_CODE = '").append(cudto.getDealerCode()).append("' ");
        sbSql.append(" AND D_KEY = ").append(cudto.getdKey()).append("AND RO_NO = '").append(roNo);
        sbSql.append("' and NEEDLESS_REPAIR= ").append(DictCodeConstants.DICT_IS_NO);
        sbSql.append(" AND (CHARGE_PARTITION_CODE='' or CHARGE_PARTITION_CODE is null))");
        StringBuilder sbWhere = new StringBuilder("");
        sbWhere.append(" RO_NO = '").append(roNo).append("' AND D_KEY = ").append(cudto.getdKey());
        sbWhere.append(" AND DEALER_CODE = '").append(cudto.getDealerCode()).append("' ");
        TtMemberPartFlowPO.update(sbSql.toString(), sbWhere.toString());
    }

    /**
     * 查询工单辅料管理费
     */
    @Override
    public List<Map> queryManage(Map<String, Object> queryParams) throws ServiceBizException {
        List<Object> params = new ArrayList<Object>();
        StringBuffer sqlSb = new StringBuffer("");
        sqlSb.append("SELECT ITEM_ID, DEALER_CODE, RO_NO, MANAGE_SORT_CODE, OVER_ITEM_AMOUNT, ");
        sqlSb.append(" LABOUR_RATE, REPAIR_PART_RATE, SALES_PART_RATE, ADD_ITEM_RATE,CREATED_BY,CREATED_AT,");
        sqlSb.append("LABOUR_AMOUNT_RATE, OVERHEAD_EXPENSES_RATE, IS_MANAGING, DISCOUNT,D_KEY");
        sqlSb.append("FROM TT_RO_MANAGE WHERE 1=1 ");
        if (StringUtils.isNullOrEmpty(queryParams.get("roNo"))) {
            sqlSb.append(" and ro_no = ?");
            params.add(queryParams.get("roNo"));
        }
        if (!StringUtils.isNullOrEmpty(queryParams.get("dKey"))) {
            sqlSb.append(" AND D_KEY = ? ");
            params.add(queryParams.get("dKey"));
        }
        if (!StringUtils.isNullOrEmpty(queryParams.get("isManaging"))) {
            sqlSb.append(" AND IS_MANAGING = ? ");
            params.add(queryParams.get("isManaging"));
        }
        if (!StringUtils.isNullOrEmpty(queryParams.get("manageSortCode"))) {
            sqlSb.append(" AND MANAGE_SORT_CODE = ? ");
            params.add(queryParams.get("manageSortCode"));
        }
        return DAOUtil.findAll(sqlSb.toString(), params);
    }

    @Override
    public void deleteTtRoManage(Map<String, Object> deleteParams) throws ServiceBizException {
        List listParams = new ArrayList();
        StringBuilder sbSql = new StringBuilder();
        if (!StringUtils.isNullOrEmpty(deleteParams.get("roNo"))) {
            sbSql.append(" AND RO_NO = ? ");
            listParams.add(deleteParams.get("roNo"));
        }
        if (!StringUtils.isNullOrEmpty(deleteParams.get("dKey"))) {
            sbSql.append(" AND D_KEY = ? ");
            listParams.add(deleteParams.get("dKey"));
        }
        sbSql.append(" AND IS_MANAGING = ? ");
        listParams.add(deleteParams.get("isManaging"));
        DAOUtil.deleteByDealer(RoManagePO.class, sbSql.toString(), listParams);
    }

    @Override
    public void modifyRepairOrderByParams(List updateParams) throws ServiceBizException {
        // TODO Auto-generated method stub
        StringBuilder sbSql = new StringBuilder(" UPDATED_BY = ?,UPDATED_AT = ? ");
        StringBuilder sbWhere = new StringBuilder(" RO_NO = ? and D_KEY = ? and DEALER_CODE = ? ");
        TtMemberPartFlowPO.update(sbSql.toString(), sbWhere.toString(), updateParams);
    }

    /**
     * 根據查詢條件查詢工单维修项目明细表 TODO description
     * 
     * @author chenwei
     * @date 2017年4月19日
     * @param queryParams
     * @return
     * @throws ServiceBizException
     */
    @Override
    public List<Map> findRoLabourList(Map<String, Object> queryParams) throws ServiceBizException {
        StringBuilder sbSql = new StringBuilder("SELECT t.MANAGE_SORT_CODE,t.DEALER_CODE,t.LABOUR_CODE,t.LABOUR_NAME,t.LABOUR_AMOUNT,t.STD_LABOUR_HOUR from TT_RO_LABOUR t where 1=1 ");
        List<Object> queryParam = new ArrayList<Object>();
        if (!StringUtils.isNullOrEmpty(queryParams.get("roNo"))) {
            sbSql.append(" AND t.RO_NO = ? ");
            queryParam.add(queryParams.get("roNo"));
        }
        if (!StringUtils.isNullOrEmpty(queryParams.get("dKey"))) {
            sbSql.append(" AND t.D_KEY = ? ");
            queryParam.add(queryParams.get("dKey"));
        }
        if (!StringUtils.isNullOrEmpty(queryParams.get("NeedlessRepair"))) {
            sbSql.append(" AND t.NEEDLESS_REPAIR = ? ");
            queryParam.add(queryParams.get("NeedlessRepair"));
        }
        return DAOUtil.findAll(sbSql.toString(), queryParam);
    }

    @Override
    public List<Map> findRoAddItem(Map<String, Object> queryParams) throws ServiceBizException {
        StringBuilder sbSql = new StringBuilder("SELECT DEALER_CODE,ITEM_ID,RO_NO,MANAGE_SORT_CODE,ADD_ITEM_CODE,ADD_ITEM_NAME, ");
        sbSql.append("ADD_ITEM_AMOUNT,CHARGE_PARTITION_CODE,ACTIVITY_CODE,REMARK,DISCOUNT,D_KEY");
        sbSql.append(" from TT_RO_ADD_ITEM  where 1=1 ");
        List<Object> queryParam = new ArrayList<Object>();
        if (!StringUtils.isNullOrEmpty(queryParams.get("roNo"))) {
            sbSql.append(" AND t.RO_NO = ? ");
            queryParam.add(queryParams.get("roNo"));
        }
        if (!StringUtils.isNullOrEmpty(queryParams.get("dKey"))) {
            sbSql.append(" AND t.D_KEY = ? ");
            queryParam.add(queryParams.get("dKey"));
        }
        return DAOUtil.findAll(sbSql.toString(), queryParam);
    }

    @Override
    public void addTtRoManage(RoManageDTO cudto) throws ServiceBizException {
        // TODO Auto-generated method stub
        RoManagePO typo = new RoManagePO();
        setRoManage(typo, cudto);
        typo.saveIt();
    }

    public void setRoManage(RoManagePO typo, RoManageDTO cudto) {
        typo.setInteger("IS_MANAGING", cudto.getIsManaging());
        typo.setString("MANAGE_SORT_CODE", cudto.getManageSortCode());
        typo.setDouble("OVER_ITEM_AMOUNT", cudto.getOverItemAmount());
        typo.setDouble("LABOUR_AMOUNT_RATE", cudto.getLabourAmountRate());
        typo.setDouble("ADD_ITEM_RATE", cudto.getAddItemRate());
        typo.setDouble("LABOUR_RATE", cudto.getLabourRate());
        typo.setDouble("REPAIR_PART_RATE", cudto.getRepairPartRate());
        typo.setDouble("SALES_PART_RATE", cudto.getSalesPartRate());
        typo.setDouble("OVERHEAD_EXPENSES_RATE", cudto.getOverheadExpensesRate());
        typo.setString("CREATED_BY", cudto.getCreatedBy());
        typo.setDate("CREATED_AT", cudto.getCreatedAt());
        typo.setString("DEALER_CODE", cudto.getDealerCode());
        typo.setString("RO_NO", cudto.getRoNo());
    }

    /**
     * 根据修改字段map和条件map修改维修工单表
     * 
     * @author chenwei
     * @date 2017年4月20日
     * @param updateParams
     * @param whereParams
     * @throws ServiceBizException (non-Javadoc)
     * @see com.yonyou.dms.repair.service.order.WithDrawStuffService#updateRepairOrder(java.util.Map, java.util.Map)
     */
    @Override
    public void updateRepairOrder(Map<String, Object> updateParams,
                                  Map<String, Object> whereParams) throws ServiceBizException {
        // TODO Auto-generated method stub
        List params = new ArrayList();
        StringBuilder sbSql = new StringBuilder();
        StringBuilder sbWhere = new StringBuilder();
        if (!StringUtils.isNullOrEmpty(updateParams.get("OVER_ITEM_AMOUNT"))) {
            sbSql.append(" OVER_ITEM_AMOUNT = ?,");
            params.add(updateParams.get("OVER_ITEM_AMOUNT"));
        }
        if (!StringUtils.isNullOrEmpty(updateParams.get("REPAIR_AMOUNT"))) {
            sbSql.append(" REPAIR_AMOUNT = ?,");
            params.add(updateParams.get("REPAIR_AMOUNT"));
        }
        if (!StringUtils.isNullOrEmpty(updateParams.get("ESTIMATE_AMOUNT"))) {
            sbSql.append(" ESTIMATE_AMOUNT = ?,");
            params.add(updateParams.get("ESTIMATE_AMOUNT"));
        }
        String sbStrSql = sbSql.substring(0, sbSql.length() - 1);

        if (!StringUtils.isNullOrEmpty(whereParams.get("RO_NO"))) {
            sbWhere.append(" and RO_NO = ? ");
            params.add(whereParams.get("RO_NO"));
        }
        if (!StringUtils.isNullOrEmpty(whereParams.get("DEALER_CODE"))) {
            sbWhere.append(" and DEALER_CODE = ? ");
            params.add(whereParams.get("DEALER_CODE"));
        }
        if (!StringUtils.isNullOrEmpty(whereParams.get("D_KEY"))) {
            sbWhere.append(" and D_KEY = ? ");
            params.add(whereParams.get("D_KEY"));
        }
        TtMemberPartFlowPO.update(sbStrSql, sbWhere.toString(), params);

    }

    /**
     * 删除 配件缺料记录表
     * 
     * @author chenwei
     * @date 2017年4月20日
     * @param deleteParams
     * @throws ServiceBizException (non-Javadoc)
     * @see com.yonyou.dms.repair.service.order.WithDrawStuffService#deleteShortPart(java.util.Map)
     */
    @Override
    public void deleteShortPart(Map<String, Object> deleteParams) throws ServiceBizException {
        List listParams = new ArrayList();
        StringBuilder sbSql = new StringBuilder();
        sbSql.append("not exists (select 1 from TT_RO_REPAIR_PART b where a.dealer_code = b.dealer_Code ");
        sbSql.append("and a.sheet_No = b.ro_no and a.part_no = b.part_no ");
        sbSql.append(" and a.storage_code=b.storage_code and a.STORAGE_POSITION_CODE=b.STORAGE_POSITION_CODE)");
        if (!StringUtils.isNullOrEmpty(deleteParams.get("sheetNo"))) {
            sbSql.append(" AND a.sheet_no = ? ");
            listParams.add(deleteParams.get("sheetNo"));
        }
        sbSql.append("and a.is_bo=12781002");
        DAOUtil.deleteByDealer(TtShortPartPO.class, sbSql.toString(), listParams);
    }

    @Override
    public List<Map> queryMonthCycle(Map<String, Object> queryParam) throws ServiceBizException {
        // TODO Auto-generated method stub
        List params = new ArrayList();
        StringBuilder sql = new StringBuilder("SELECT DEALER_CODE,REPORT_YEAR FROM TT_MONTH_CYCLE WHERE 1=1 ");
        if (!StringUtils.isNullOrEmpty(queryParam.get("REPORT_YEAR"))) {
            sql.append(" AND REPORT_YEAR = ?");
            params.add(queryParam.get("REPORT_YEAR"));
        }
        if (!StringUtils.isNullOrEmpty(queryParam.get("REPORT_MONTH"))) {
            sql.append(" AND REPORT_MONTH = ?");
            params.add(queryParam.get("REPORT_MONTH"));
        }
        return DAOUtil.findAll(sql.toString(), params);
    }

    @Override
    public List<Map> queryAccountingCycle(Map<String, Object> queryParam) throws ServiceBizException {
        List params = new ArrayList();
        StringBuilder sql = new StringBuilder("SELECT DEALER_CODE,B_YEAR,PERIODS,BEGIN_DATE,END_DATE,IS_EXECUTED  FROM TM_ACCOUNTING_CYCLE  WHERE 1=1 ");
        sql.append("AND CURRENT_TIMESTAMP BETWEEN BEGIN_DATE AND END_DATE");
        return DAOUtil.findAll(sql.toString(), params);
    }

    /**
     * 判断当前登陆用户是否有：低于成本价出库的权限
     * 
     * @author chenwei
     * @date 2017年4月21日
     * @return
     * @throws ServiceBizException (non-Javadoc)
     * @see com.yonyou.dms.repair.service.order.WithDrawStuffService#checkUserRights()
     */
    @Override
    public List<Map> checkUserRights(Map<String, Object> queryParam) throws ServiceBizException {
        List<Object> params = new ArrayList<Object>();
        StringBuilder sqlSb = new StringBuilder("select a.user_id,a.user_code,a.user_name,b.user_id,b.option_code,c.option_code,c.option_name,c.option_type,c.type_name ");
        sqlSb.append("from TM_USER a ");
        sqlSb.append("inner join TT_USER_OPTION_MAPPING b on  a.dealer_code=b.dealer_code and a.USER_ID=b.USER_ID ");
        sqlSb.append("inner join TM_AUTH_OPTION C on b.OPTION_CODE=c.OPTION_CODE where a.dealer_code='");
        sqlSb.append(FrameworkUtil.getLoginInfo().getDealerCode());
        sqlSb.append("' and c.option_code='20180000' AND A.user_id=");
        sqlSb.append(FrameworkUtil.getLoginInfo().getUserId());
        return DAOUtil.findAll(sqlSb.toString(), params);
    }

    @Override
    public List checkCostSize(Map<String, Object> queryParam) throws ServiceBizException {
        List<Object> params = new ArrayList<Object>();
        StringBuilder sqlSb = new StringBuilder(" select part_no,storage_code,cost_price from tm_part_stock ");
        sqlSb.append("where d_key=0 and floor(cost_price*100)/100-").append(queryParam.get("partSalesPrice"));
        sqlSb.append(">0  and part_no='").append(queryParam.get("partNo"));
        sqlSb.append("' and storage_code='");
        sqlSb.append(queryParam.get("storageCode")).append("'");
        sqlSb.append(FrameworkUtil.getLoginInfo().getUserId());
        return DAOUtil.findAll(sqlSb.toString(), params);
    }

    @Override
    public List<Map> queryPartInfoList(Map<String, Object> queryParam) throws ServiceBizException {
        List<Object> params = new ArrayList<Object>();
        String sql = getObjectSQL(queryParam, params);
        List<Map> resultList = DAOUtil.findAll(sql, params);
        return resultList;
    }

    /**
     * 封装sql
     * 
     * @author xukl
     * @date 2016年8月2日
     * @param queryParam
     * @param params
     * @return
     */

    private String getObjectSQL(Map<String, Object> queryParam, List<Object> params) throws ServiceBizException {

        StringBuilder sb = new StringBuilder("SELECT DEALER_CODE,PART_NO,PART_NAME,DOWN_TAG,D_KEY,LIMIT_PRICE FROM tm_part_info WHERE 1=1 ");
        if (!StringUtils.isNullOrEmpty(queryParam.get("brand"))) {
            sb.append(" and BRAND = ?");
            params.add(queryParam.get("brand"));
        }
        if (!StringUtils.isNullOrEmpty(queryParam.get("partCode"))) {
            sb.append(" and PART_NO like ?");
            params.add("%" + queryParam.get("partCode") + "%");
        }
        if (!StringUtils.isNullOrEmpty(queryParam.get("downTag"))) {
            sb.append(" and DOWN_TAG = ?");
            params.add(queryParam.get("downTag"));
        }
        if (!StringUtils.isNullOrEmpty(queryParam.get("dKey"))) {
            sb.append(" and D_KEY = ?");
            params.add(queryParam.get("dKey"));
        }
        if (!StringUtils.isNullOrEmpty(queryParam.get("partName"))) {
            sb.append(" and PART_NAME like ?");
            params.add("%" + queryParam.get("partName") + "%");
        }
        if (!StringUtils.isNullOrEmpty(queryParam.get("spellCode"))) {
            sb.append(" and SPELL_CODE like ?");
            params.add("%" + queryParam.get("spellCode") + "%");
        }
        if (!StringUtils.isNullOrEmpty(queryParam.get("partGroupCode"))) {
            sb.append(" and PART_GROUP_CODE = ?");
            params.add(queryParam.get("partGroupCode"));
        }
        if (!StringUtils.isNullOrEmpty(queryParam.get("partInfix"))) {
            sb.append(" and PART_INFIX like ?");
            params.add("%" + queryParam.get("partInfix") + "%");
        }
        if (!StringUtils.isNullOrEmpty(queryParam.get("partInfixName"))) {
            sb.append(" and PART_INFIX_NAME like ?");
            params.add("%" + queryParam.get("partInfixName") + "%");
        }
        return sb.toString();
    }

    @Override
    public List<Map> queryStorageList(Map<String, Object> queryParam) throws ServiceBizException {
        List<Object> params = new ArrayList<Object>();
        StringBuilder sqlSb = new StringBuilder("SELECT DEALER_CODE,STORAGE_CODE,STORAGE_NAME,IS_NEGATIVE FROM TM_STORAGE where 1=1 ");
        if (!StringUtils.isNullOrEmpty(queryParam.get("storageCode"))) {
            sqlSb.append("AND STORAGE_CODE = ? ");
            params.add(queryParam.get("storageCode"));
        }
        return DAOUtil.findAll(sqlSb.toString(), params);
    }

    /**
     * 配件会计月报表
     * 
     * @author chenwei
     * @date 2017年4月24日
     * @param queryParam
     * @return
     * @throws ServiceBizException (non-Javadoc)
     * @see com.yonyou.dms.repair.service.order.WithDrawStuffService#queryPartPeriodReportList(java.util.Map)
     */
    @Override
    public List<Map> queryPartPeriodReportList(Map<String, Object> queryParam) throws ServiceBizException {
        List<Object> params = new ArrayList<Object>();
        StringBuilder sql = new StringBuilder(" SELECT DEALER_CODE FROM TT_PART_PERIOD_REPORT WHERE 1=1 ");
        if (!StringUtils.isNullOrEmpty(queryParam.get("storageCode"))) {
            sql.append(" AND STORAGE_CODE = ?");
            params.add(queryParam.get("storageCode"));
        }
        if (!StringUtils.isNullOrEmpty(queryParam.get("partNo"))) {
            sql.append(" AND PART_NO = ?");
            params.add(queryParam.get("partNo"));
        }
        if (!StringUtils.isNullOrEmpty(queryParam.get("reportYear"))) {
            sql.append(" AND REPORT_YEAR = ?");
            params.add(queryParam.get("reportYear"));
        }
        if (!StringUtils.isNullOrEmpty(queryParam.get("reportMonth"))) {
            sql.append(" AND REPORT_MONTH = ?");
            params.add(queryParam.get("reportMonth"));
        }
        if (!StringUtils.isNullOrEmpty(queryParam.get("partBatchNo"))) {
            sql.append(" AND PART_BATCH_NO = ?");
            params.add(queryParam.get("partBatchNo"));
        }
        if (!StringUtils.isNullOrEmpty(queryParam.get("dKey"))) {
            sql.append(" AND D_KEY = ?");
            params.add(queryParam.get("dKey"));
        }
        return DAOUtil.findAll(sql.toString(), params);
    }

    /**
     * 配件会计月报表
     * 
     * @author chenwei
     * @date 2017年4月24日
     * @param sqlStr
     * @param sqlWhere
     * @param paramsList
     * @throws ServiceBizException (non-Javadoc)
     * @see com.yonyou.dms.repair.service.order.WithDrawStuffService#modifyPartPeriodReportByParams(java.lang.String,
     * java.lang.String, java.util.List)
     */
    @Override
    public int modifyPartPeriodReportByParams(String sqlStr, String sqlWhere,
                                              List paramsList) throws ServiceBizException {
        // TODO Auto-generated method stub
        int record = TtPartPeriodReportPO.update(sqlStr, sqlWhere, paramsList);
        return record;
    }

    /**
     * 配件会计月报表
     * 
     * @author chenwei
     * @date 2017年4月24日
     * @param modeldto
     * @return
     * @throws ServiceBizException (non-Javadoc)
     * @see com.yonyou.dms.repair.service.order.WithDrawStuffService#addPartMonthReport(com.yonyou.dms.repair.domains.DTO.basedata.TtPartPeriodReportDTO)
     */
    @Override
    public void addPartMonthReport(TtPartPeriodReportDTO modeldto) throws ServiceBizException {
        TtPartPeriodReportPO typo = new TtPartPeriodReportPO();
        setPartMonthReport(typo, modeldto);
        typo.saveIt();
        // Base.exec(query, params)
    }

    /**
     * 设置TtPartPeriodReportPO属性
     * 
     * @author chenwei
     * @date 2017年4月24日
     * @param typo
     * @param pyto
     */
    public void setPartMonthReport(TtPartPeriodReportPO typo, TtPartPeriodReportDTO pyto) {
        typo.setString("REPORT_YEAR", pyto.getReportYear());
        typo.setString("REPORT_MONTH", pyto.getReportMonth());
        typo.setString("DEALER_CODE", pyto.getDealerCode());
        typo.setString("STORAGE_CODE", pyto.getStorageCode());
        typo.setString("PART_BATCH_NO", pyto.getPartBatchNo());
        typo.setString("PART_NO", pyto.getPartNo());
        typo.setString("PART_NAME", pyto.getPartName());
        typo.setDouble("IN_QUANTITY", pyto.getInQuantity());
        typo.setDouble("STOCK_IN_AMOUNT", pyto.getStockInAmount());
        typo.setDouble("BUY_IN_COUNT", pyto.getBuyInCount());
        typo.setDouble("BUY_IN_AMOUNT", pyto.getBuyInAmount());
        typo.setDouble("ALLOCATE_IN_COUNT", pyto.getAllocateInCount());
        typo.setDouble("ALLOCATE_IN_AMOUNT", pyto.getAllocateInAmount());
        typo.setDouble("OTHER_IN_COUNT", pyto.getOtherInCount());
        typo.setDouble("TRANSFER_IN_AMOUNT", pyto.getOtherInAmount());
        typo.setDouble("PROFIT_IN_COUNT", pyto.getProfitInCount());
        typo.setDouble("TRANSFER_IN_AMOUNT", pyto.getProfitInAmount());
        typo.setDouble("OUT_QUANTITY", pyto.getOutQuantity());
        typo.setDouble("STOCK_OUT_COST_AMOUNT", pyto.getStockOutCostAmount());
        typo.setDouble("OUT_AMOUNT", pyto.getOutAmount());
        typo.setDouble("REPAIR_OUT_COUNT", pyto.getRepairOutCount());
        typo.setDouble("REPAIR_OUT_COST_AMOUNT", pyto.getRepairOutCostAmount());
        typo.setDouble("REPAIR_OUT_SALE_AMOUNT", pyto.getRepairOutSaleAmount());
        typo.setDouble("SALE_OUT_COUNT", pyto.getSaleOutCount());
        typo.setDouble("SALE_OUT_COST_AMOUNT", pyto.getSaleOutCostAmount());
        typo.setDouble("SALE_OUT_SALE_AMOUNT", pyto.getSaleOutSaleAmount());
        typo.setDouble("INNER_OUT_COUNT", pyto.getInnerOutCount());
        typo.setDouble("INNER_OUT_COST_AMOUNT", pyto.getInnerOutCostAmount());
        typo.setDouble("INNER_OUT_SALE_AMOUNT", pyto.getInnerOutSaleAmount());
        typo.setDouble("ALLOCATE_OUT_COUNT", pyto.getAllocateOutCount());
        typo.setDouble("ALLOCATE_OUT_COST_AMOUNT", pyto.getAllocateOutCostAmount());
        typo.setDouble("ALLOCATE_OUT_SALE_AMOUNT", pyto.getAllocateOutSaleAmount());
        typo.setDouble("OTHER_OUT_COUNT", pyto.getOtherOutCount());
        typo.setDouble("OTHER_OUT_COST_AMOUNT", pyto.getOtherOutCostAmount());
        typo.setDouble("OTHER_OUT_SALE_AMOUNT", pyto.getOtherOutSaleAmount());
        typo.setDouble("LOSS_OUT_COUNT", pyto.getLossOutCount());
        typo.setDouble("LOSS_OUT_AMOUNT", pyto.getLossOutAmount());
        typo.setDouble("TRANSFER_IN_COUNT", pyto.getTransferInCount());
        typo.setDouble("TRANSFER_IN_AMOUNT", pyto.getTransferInAmount());
        typo.setDouble("TRANSFER_OUT_COUNT", pyto.getTransferOutCount());
        typo.setDouble("OTHER_OUT_COST_AMOUNT", pyto.getTransferOutCostAmount());
        typo.setDouble("OPEN_QUANTITY", pyto.getOpenQuantity());
        typo.setDouble("OPEN_PRICE", pyto.getOpenPrice());
        typo.setDouble("OPEN_AMOUNT", pyto.getOpenAmount());
        typo.setDouble("UPHOLSTER_OUT_COUNT", pyto.getUpholsterOutCount());
        typo.setDouble("UPHOLSTER_OUT_COST_AMOUNT", pyto.getUpholsterOutCostAmount());
        typo.setDouble("UPHOLSTER_OUT_SALE_AMOUNT", pyto.getUpholsterOutSaleAmount());
        typo.setDouble("CLOSE_QUANTITY", pyto.getCloseQuantity());
        typo.setDouble("CLOSE_PRICE", pyto.getClosePrice());
        typo.setDouble("CLOSE_AMOUNT", pyto.getCloseAmount());
    }

    @Override
    public List<Map> queryPartDailyReportList(Map<String, Object> queryParam) throws ServiceBizException {
        List<Object> params = new ArrayList<Object>();
        StringBuilder sql = new StringBuilder(" SELECT DEALER_CODE FROM TT_PART_DAILY_REPORT WHERE 1=1 ");
        if (!StringUtils.isNullOrEmpty(queryParam.get("storageCode"))) {
            sql.append(" AND STORAGE_CODE = ?");
            params.add(queryParam.get("storageCode"));
        }
        if (!StringUtils.isNullOrEmpty(queryParam.get("reportDate"))) {
            sql.append(" AND REPORT_DATE = ?");
            params.add(queryParam.get("reportDate"));
        }
        if (!StringUtils.isNullOrEmpty(queryParam.get("dKey"))) {
            sql.append(" AND D_KEY = ?");
            params.add(queryParam.get("dKey"));
        }
        return DAOUtil.findAll(sql.toString(), params);
    }

    @Override
    public int modifyPartDailyReportByParams(String sqlStr, String sqlWhere
                                             ,List paramsList) throws ServiceBizException {
        int record = PartDailyReportPO.update(sqlStr, sqlWhere, paramsList);
        return record;
    }

    @Override
    public void addPartDailyReport(String sqlStr, List params) throws ServiceBizException {
        // TODO Auto-generated method stub
        Base.exec(sqlStr, params);
    }

    /**
     * @param conn
     * @param entityCode
     * @param memActivityCode
     * @param flag 标识正常配件与退料配件
     * @return
     * @throws Exception
     */
    @Override
    public List<Map> queryMemActivityByCode(String roNo, String memActivityCode
                                            ,String flag) throws ServiceBizException {
        StringBuffer sql = new StringBuffer();
        sql.append(" SELECT DEALER_CODE,ACTIVITY_CODE,RO_NO,PART_QUANTITY,CARD_ID FROM  TT_RO_REPAIR_PART WHERE CARD_ID IS NOT NULL AND CARD_ID!=0 ");
        sql.append(" AND IS_FINISHED = " + DictCodeConstants.DICT_IS_YES);
        List params = new ArrayList();
        if (Utility.testString(memActivityCode)) {
            sql.append(" AND ACTIVITY_CODE = ? ");
            params.add(memActivityCode);
        }
        if (Utility.testString(roNo)) {
            sql.append(" AND RO_NO= ? ");
            params.add(roNo);
        }
        if (flag.equals("1")) {
            sql.append(" AND PART_QUANTITY >0  ");
        } else if (flag.equals("0")) {
            sql.append(" AND PART_QUANTITY <0 ");
        }
        logger.debug(sql.toString());

        return DAOUtil.findAll(sql.toString(), params);
    }

    @Override
    public List<Map> queryMemberCardActivityByCode(Map<String, Object> queryParam) throws ServiceBizException {
        StringBuffer sql = new StringBuffer();
        sql.append("SELECT DEALER_CODE,ITEM_ID,MEMBER_ACTIVITY_CODE,MEMBER_ACTIVITY_AMOUNT,PURCHASE_COUNT,USED_TICKET_COUNT,CARD_ID FROM TM_MEMBER_CARD_ACTIVITY WHERE 1=1 ");
        List params = new ArrayList();
        if (!StringUtils.isNullOrEmpty(queryParam.get("cardId"))) {
            sql.append(" AND CARD_ID = ? ");
            params.add(queryParam.get("cardId"));
        }
        if (!StringUtils.isNullOrEmpty(queryParam.get("dKey"))) {
            sql.append(" AND D_KEY = ? ");
            params.add(queryParam.get("dKey"));
        }
        if (!StringUtils.isNullOrEmpty(queryParam.get("memberActivityCode"))) {
            sql.append(" AND MEMBER_ACTIVITY_CODE = ? ");
            params.add(queryParam.get("memberActivityCode"));
        }
        return DAOUtil.findAll(sql.toString(), params);
    }

    @Override
    public int modifyMemberCardActivityByParams(String sqlStr, String sqlWhere
                                                ,List paramsList) throws ServiceBizException {
        int record = TmMemberCardActivityPO.update(sqlStr, sqlWhere, paramsList);
        return record;
    }
    
    @Override
    public void deleteMemberCardActivity(TtMemCardActiDetailDTO deleteParams) throws ServiceBizException {
        List listParams = new ArrayList();
        StringBuilder sbSql = new StringBuilder();
        if (!StringUtils.isNullOrEmpty(deleteParams.getCardId())) {
            sbSql.append(" AND CARD_ID = ? ");
            listParams.add(deleteParams.getCardId());
        }
        if (!StringUtils.isNullOrEmpty(deleteParams.getRoNo())) {
            sbSql.append(" AND RO_NO = ? ");
            listParams.add(deleteParams.getRoNo());
        }
        if (!StringUtils.isNullOrEmpty(deleteParams.getMemberActivityCode())) {
            sbSql.append(" AND MEMBER_ACTIVITY_CODE = ? ");
            listParams.add(deleteParams.getMemberActivityCode());
        }
        if (!StringUtils.isNullOrEmpty(deleteParams.getdKey())) {
            sbSql.append(" AND D_KEY = ? ");
            listParams.add(deleteParams.getdKey());
        }
        DAOUtil.deleteByDealer(TtMemCardActiDetailPO.class, sbSql.toString(), listParams);
    }

    @Override
    public List<Map> queryTtPartObligatedByObject(TtPartObligatedDTO queryParam) throws ServiceBizException {
        StringBuffer sql = new StringBuffer();
        sql.append("SELECT DEALER_CODE,OBLIGATED_NO,OBLIGATED_CLOSE,SHEET_NO FROM TT_PART_OBLIGATED WHERE 1=1 ");
        List params = new ArrayList();
        if (!StringUtils.isNullOrEmpty(queryParam.getSheetNo())) {
            sql.append(" AND SHEET_NO = ? ");
            params.add(queryParam.getSheetNo());
        }
        if (!StringUtils.isNullOrEmpty(queryParam.getdKey())) {
            sql.append(" AND D_KEY = ? ");
            params.add(queryParam.getdKey());
        }
        return DAOUtil.findAll(sql.toString(), params);
    }

    @Override
    public List<Map> queryTtPartObligatedItemByObject(TtPartObligatedItemDTO queryParam) throws ServiceBizException {
        StringBuffer sql = new StringBuffer();
        sql.append("SELECT DEALER_CODE,OBLIGATED_NO,IS_OBLIGATED,PART_NO,STORAGE_CODE,QUANTITY,ITEM_ID FROM TT_PART_OBLIGATED_ITEM WHERE 1=1 ");
        List params = new ArrayList();
        if (!StringUtils.isNullOrEmpty(queryParam.getObligatedNo())) {
            sql.append(" AND OBLIGATED_NO = ? ");
            params.add(queryParam.getObligatedNo());
        }
        if (!StringUtils.isNullOrEmpty(queryParam.getDKey())) {
            sql.append(" AND D_KEY = ? ");
            params.add(queryParam.getDKey());
        }
        return DAOUtil.findAll(sql.toString(), params);
    }

    @Override
    public List<TtPartObligatedItemDTO> changeMapToTtPartObligatedItem(List<Map> list) {
        List<TtPartObligatedItemDTO> dtoList = new ArrayList<TtPartObligatedItemDTO>();
        if(null != list && list.size() > 0){
            for(int i =0;i < list.size(); i++){
                Map map = list.get(i);
                TtPartObligatedItemDTO dto = new TtPartObligatedItemDTO();
                if(!StringUtils.isNullOrEmpty(map.get("IS_OBLIGATED")))
                    dto.setIsObligated(Integer.valueOf(map.get("IS_OBLIGATED").toString()));
                if(!StringUtils.isNullOrEmpty(map.get("PART_NO")))
                    dto.setPartNo(map.get("PART_NO").toString());
                if(!StringUtils.isNullOrEmpty(map.get("STORAGE_CODE")))
                    dto.setStorageCode(map.get("STORAGE_CODE").toString());
                if(!StringUtils.isNullOrEmpty(map.get("QUANTITY")))
                    dto.setQuantity(Double.valueOf(map.get("QUANTITY").toString()));
                if(!StringUtils.isNullOrEmpty(map.get("ITEM_ID")))
                    dto.setItemId(Long.valueOf(map.get("ITEM_ID").toString()));
                dtoList.add(dto);
            }
            return dtoList;
        }else{
            return dtoList;
        }
    }

    @Override
    public int modifyTtPartObligatedItemByParams(String sqlStr, String sqlWhere,
                                                 List paramsList) throws ServiceBizException {
        int record = TtPartObligatedItemPO.update(sqlStr, sqlWhere, paramsList);
        return record;
    }

    @Override
    public void addTtAccountsTransFlow(TtAccountsTransFlowDTO modeldto) throws ServiceBizException {
        TtAccountsTransFlowPO typo = new TtAccountsTransFlowPO();
        setAccountsTransFlow(typo, modeldto);
        typo.saveIt();
    }

    /**
     * 设置TtAccountsTransFlowPO属性
     * 
     * @author chenwei
     * @date 2017年5月1日
     * @param typo
     * @param pyto
     */
    public void setAccountsTransFlow(TtAccountsTransFlowPO typo, TtAccountsTransFlowDTO pyto) {
        typo.setString("ORG_CODE", pyto.getOrgCode());
        typo.setString("DEALER_CODE", pyto.getDealerCode());
        typo.setDate("TRANS_DATE", pyto.getTransDate());
        typo.setInteger("TRANS_TYPE", pyto.getTransType());
        typo.setString("SUB_BUSINESS_NO", pyto.getSubBusinessNo());
        typo.setString("BUSINESS_NO", pyto.getBusinessNo());
        typo.setDouble("TAXED_AMOUNT", pyto.getTaxAmount());
        typo.setDouble("NET_AMOUNT", pyto.getNetAmount());
        typo.setInteger("IS_VALID", pyto.getIsValid());
        typo.setInteger("EXEC_NUM", pyto.getExecNum());
        //typo.setInteger("STORAGE_CODE", pyto.getExecStatus());待确认
    }

    @Override
    public int modifyTmPartBackDTOByParams(String sqlStr, String sqlWhere, List paramsList) throws ServiceBizException {
        int record = TtPartObligatedItemPO.update(sqlStr, sqlWhere, paramsList);
        return record;
    }

    /*
     * 查询 维修发料退料界面：查询出已结算和已关单的 单子做配件退料操作
    * @author chenwei
    * @date 2017年5月2日
    * @param queryParam
    * @return
    * @throws ServiceBizException
    * @see com.yonyou.dms.repair.service.order.WithDrawStuffService#queryRepairOrderByCommitClose(java.util.Map)
     */
    @Override
    public PageInfoDto queryRepairOrderByCommitClose(Map<String, Object> queryParams) throws ServiceBizException {
        List<Object> params = new ArrayList<Object>();
        StringBuffer sql = new StringBuffer();
        sql.append("select a.DEALER_CODE,t.STORAGE_NAME,b.UNIT_CODE,b.PART_COST_AMOUNT,b.PART_COST_PRICE,b.PART_NAME,b.STORAGE_CODE,b.STORAGE_POSITION_CODE,b.PART_QUANTITY,b.PART_SALES_PRICE,b.PART_SALES_AMOUNT ");
        sql.append(",b.CHARGE_PARTITION_CODE,b.DISCOUNT,a.RO_NO,a.VIN,a.LICENSE,b.PART_NO,a.OWNER_NO,a.OWNER_NAME ");
        sql.append("from TT_REPAIR_ORDER a  ");
        sql.append("left join TT_RO_REPAIR_PART b on a.DEALER_CODE=b.DEALER_CODE and a.RO_NO=b.RO_NO   ");
        sql.append("left join TT_BALANCE_ACCOUNTS c on a.DEALER_CODE=c.DEALER_CODE and a.RO_NO=c.RO_NO  ");
        sql.append("left join TM_STORAGE t on b.DEALER_CODE=t.DEALER_CODE and b.STORAGE_CODE=t.STORAGE_CODE ");
        sql.append("where c.IS_RED=12781002  and (c.BALANCE_CLOSE=").append(DictCodeConstants.DICT_IS_YES);
        sql.append(" or  a.RO_STATUS= ").append(DictCodeConstants.DICT_RO_STATUS_TYPE_BALANCED).append(" )");
        if (!StringUtils.isNullOrEmpty(queryParams.get("roNo")))
        {
            sql.append(" AND A.RO_NO = ? ");
            params.add(queryParams.get("roNo"));
        }
        if (!StringUtils.isNullOrEmpty(queryParams.get("vin")))
        {
            sql.append(" AND A.VIN= ? ");
            params.add(queryParams.get("vin"));
        }
        if (!StringUtils.isNullOrEmpty(queryParams.get("license")))
        {
            sql.append(" AND A.LICENSE= ? ");
            params.add(queryParams.get("license"));
        }
        if (!StringUtils.isNullOrEmpty(queryParams.get("ownerName")))
        {
            sql.append(" AND A.OWNER_NAME= ? ");
            params.add(queryParams.get("ownerName"));
        }
        if (!StringUtils.isNullOrEmpty(queryParams.get("ownerNo")))
        {
            sql.append(" AND A.OWNER_NO= ? ");
            params.add(queryParams.get("ownerNo"));
        }
        if (!StringUtils.isNullOrEmpty(queryParams.get("partNo")))
        {
            sql.append(" AND B.PART_NO = ?");
            params.add(queryParams.get("partNo"));
        }
        PageInfoDto id = DAOUtil.pageQuery(sql.toString(), params);
        return id;
    }

    @Override
    public PageInfoDto queryTtRoLabourList(Map<String, Object> queryParam) throws ServiceBizException {
        List<Object> params = new ArrayList<Object>();
        StringBuffer sql = new StringBuffer();
        sql.append("SELECT DEALER_CODE,RO_NO,ITEM_ID, MANAGE_SORT_CODE, CHARGE_PARTITION_CODE, LABOUR_CODE,LABOUR_NAME, LABOUR_PRICE, STD_LABOUR_HOUR,LABOUR_AMOUNT ");
        sql.append(",ASSIGN_LABOUR_HOUR,DISCOUNT,WORKER_TYPE_CODE,TECHNICIAN,ASSIGN_TAG,CONSIGN_EXTERIOR,NEEDLESS_REPAIR,PRE_CHECK,INTER_RETURN ");
        sql.append(",ACTIVITY_CODE,APP_NAME,REASON,REMARK FROM tt_ro_labour Where 1=1 ");
        if(!StringUtils.isNullOrEmpty(queryParam.get("roNo"))){
            sql.append(" AND RO_NO = ?");
            params.add(queryParam.get("roNo"));
        }
        PageInfoDto id = DAOUtil.pageQuery(sql.toString(), params);
        return id;
    }

    @Override
    public PageInfoDto queryBorrowPartInfo(Map<String, Object> queryParam) throws ServiceBizException {
        List<Object> params = new ArrayList<Object>();
        StringBuffer sql = new StringBuffer();
        sql.append(" select D.DOWN_TAG,B.NODE_PRICE,A.INSURANCE_PRICE, A.DEALER_CODE, A.PART_NO, A.STORAGE_CODE, A.PART_BATCH_NO,A.STORAGE_POSITION_CODE, A.PART_NAME,  ");
        sql.append(" A.SPELL_CODE, A.UNIT_CODE, A.STOCK_QUANTITY, A.SALES_PRICE, A.CLAIM_PRICE, A.LIMIT_PRICE, A.LATEST_PRICE, ");
        sql.append(" ROUND(A.COST_PRICE,2) AS COST_PRICE, A.COST_AMOUNT, A.BORROW_QUANTITY, A.PART_STATUS, A.LEND_QUANTITY, ");
        sql.append(" A.LAST_STOCK_IN, A.LAST_STOCK_OUT, A.REMARK as REMARK, A.CREATED_BY, A.CREATED_AT, A.UPDATED_BY, A.UPDATED_AT,");
        sql.append(" A.VER, A.PART_GROUP_CODE,  C.PART_MODEL_GROUP_CODE_SET,  (A.STOCK_QUANTITY + A.BORROW_QUANTITY - ");
        sql.append("ifnull(A.LEND_QUANTITY, 0) ) AS USEABLE_QUANTITY,CC.LEND_QUANTITY AS SHOP_BORROW_QUANTITY,CC.BORROWER AS SHOP_BORROWER,CC.ORDER_TYPE ");
        sql.append(", CASE  WHEN(  SELECT 1  FROM TM_MAINTAIN_PART CC  WHERE CC.PART_NO = B.PART_NO  AND CC.DEALER_CODE = B.DEALER_CODE  )   >0 THEN 12781001  ELSE 12781002  END AS IS_MAINTAIN ");
        sql.append(" from TM_PART_STOCK_ITEM  A  LEFT OUTER JOIN VM_PART_INFO B ON (A.DEALER_CODE = B.DEALER_CODE AND A.PART_NO = B.PART_NO ) ");
        sql.append(" INNER JOIN ( ");
        sql.append(" SELECT  A.PART_NO,  A.STORAGE_CODE,A.DEALER_CODE,LEND_QUANTITY, BORROWER,'车间借料' as ORDER_TYPE ");
        sql.append(" FROM TT_PART_WORKSHOP_ITEM A   WHERE  DEALER_CODE = '").append(FrameworkUtil.getLoginInfo().getDealerCode() + "' AND RO_NO ='").append(queryParam.get("roNo")).append("' AND A.LEND_QUANTITY > 0 ");
        sql.append(" UNION ALL ");
        sql.append(" SELECT  A.PART_NO, A.STORAGE_CODE, A.DEALER_CODE,A.QUANTITY  AS LEND_QUANTITY, B.APPLICANT AS BORROWER,'工单预留' as ORDER_TYPE ");
        sql.append(" FROM TT_PART_OBLIGATED_ITEM A  LEFT JOIN TT_PART_OBLIGATED B ON (A.DEALER_CODE = B.DEALER_CODE AND A.OBLIGATED_NO = B.OBLIGATED_NO )");
        sql.append(" WHERE B.OBLIGATED_CLOSE = ").append(DictCodeConstants.DICT_IS_NO);
        sql.append(" AND A.DEALER_CODE ='").append(FrameworkUtil.getLoginInfo().getDealerCode()).append("'  AND B.SHEET_NO ='");
        sql.append(queryParam.get("roNo")).append("') CC ").append(" ON ( CC.DEALER_CODE = A.DEALER_CODE  AND CC.PART_NO = A.PART_NO ");
        sql.append(" AND CC.STORAGE_CODE = A.STORAGE_CODE ) ");
        sql.append(" LEFT OUTER JOIN TM_PART_STOCK C ON (A.DEALER_CODE = C.DEALER_CODE AND A.PART_NO = C.PART_NO AND A.STORAGE_CODE=C.STORAGE_CODE) ");
        sql.append(" LEFT OUTER JOIN TM_PART_INFO D ON  A.DEALER_CODE = D.DEALER_CODE  AND A.PART_NO = D.PART_NO WHERE A.PART_STATUS<>").append(DictCodeConstants.DICT_IS_YES);
        sql.append(" AND A.DEALER_CODE = '").append(FrameworkUtil.getLoginInfo().getDealerCode()).append("' ");
        if(!StringUtils.isNullOrEmpty(queryParam.get("model"))){
            sql.append(" AND C.PART_MODEL_GROUP_CODE_SET like ?");
            params.add("%" + queryParam.get("model") + "%");
        }
        if(!StringUtils.isNullOrEmpty(queryParam.get("storageCode"))){
            sql.append("  AND A.STORAGE_CODE =  ?");
            params.add(queryParam.get("storageCode"));
        }
        if(!StringUtils.isNullOrEmpty(queryParam.get("partNo"))){
            sql.append("  AND A.PART_NO =  ?");
            params.add(queryParam.get("partNo"));
        }
        if(!StringUtils.isNullOrEmpty(queryParam.get("partName"))){
            sql.append("  AND A.PART_NAME =  ?");
            params.add(queryParam.get("partName"));
        }
        if(!StringUtils.isNullOrEmpty(queryParam.get("groupCode"))){
            sql.append(" and A.PART_GROUP_CODE = ?");
            params.add(queryParam.get("groupCode"));
        }
        if(!StringUtils.isNullOrEmpty(queryParam.get("positionCode"))){
            sql.append(" and A.STORAGE_POSITION_CODE = ?");
            params.add(queryParam.get("positionCode"));
        }
        if(!StringUtils.isNullOrEmpty(queryParam.get("spellCode"))){
            sql.append(" and A.SPELL_CODE = ?");
            params.add(queryParam.get("spellCode"));
        }
        if(!StringUtils.isNullOrEmpty(queryParam.get("sale"))){
            sql.append(" and A.SALES_PRICE > 0");
        }
        if(!StringUtils.isNullOrEmpty(queryParam.get("stock"))){
            sql.append(" and (A.STOCK_QUANTITY + A.BORROW_QUANTITY - A.LEND_QUANTITY )  > 0 ");
        }
        if(!StringUtils.isNullOrEmpty(queryParam.get("brand"))){
            sql.append("  and B.BRAND = ? ");
            params.add(queryParam.get("brand"));
        }
        String[] stoC = Utility.getStorageByUserId(FrameworkUtil.getLoginInfo().getUserId()).split(",");
        /*if(!StringUtils.isNullOrEmpty(stoC) && stoC.length > 0){
            sql.append(" AND ( 1=2 ");
            for (int i = 0; i < stoC.length; i++)
            {
                if (stoC[i] != null && !"".equals(stoC[i].trim()))
                {
                    sql.append(" OR A.STORAGE_CODE=" + stoC[i] + "  ");
                }
            }
            sql.append(" ) ");
        }*/
        PageInfoDto id = DAOUtil.pageQuery(sql.toString(), params);
        return id;
    }
    

}
