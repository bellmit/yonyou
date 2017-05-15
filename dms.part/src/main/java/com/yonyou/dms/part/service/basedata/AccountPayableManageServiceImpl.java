
/** 
*Copyright 2017 Yonyou Corporation Ltd. All Rights Reserved.
* This software is published under the terms of the Yonyou Software
* License version 1.0, a copy of which has been included with this
* distribution in the LICENSE.txt file.
*
* @Project Name : dms.part
*
* @File name : AccountPayableManageServiceImpl.java
*
* @Author : dingchaoyu
*
* @Date : 2017年5月3日
*
----------------------------------------------------------------------------------
*     Date       Who       Version     Comments
* 1. 2017年5月3日    dingchaoyu    1.0
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

import com.yonyou.dms.common.domains.PO.stockmanage.PartAllocateInItemPO;
import com.yonyou.dms.commonAS.domains.PO.basedata.PartAllocateOutItemPo;
import com.yonyou.dms.framework.DAO.DAOUtil;
import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.framework.util.FrameworkUtil;
import com.yonyou.dms.framework.util.Utility;
import com.yonyou.dms.function.common.CommonConstants;
import com.yonyou.dms.function.common.DictCodeConstants;
import com.yonyou.dms.function.exception.ServiceBizException;
import com.yonyou.dms.function.utils.common.StringUtils;
import com.yonyou.dms.part.domains.PO.stockmanage.PartBuyItemPO;

/**
* TODO description
* @author dingchaoyu
* @date 2017年5月3日
*/
@SuppressWarnings("rawtypes")
@Service
public class AccountPayableManageServiceImpl implements AccountPayableManageService{

    @Override
    public PageInfoDto retrieveByReceive(Map map) throws ServiceBizException {
        List<Object> list = new ArrayList<>();
        StringBuffer sql = new StringBuffer();
        if (DictCodeConstants.DICT_BUSINESS_TYPE_PART_STOCK.equals(map.get("tag"))) {//采购入库 
            sql.append("SELECT A.DEALER_CODE,A.PAY_OFF_DATE,(A.FINISHED_DATE+C.ACCOUNT_AGE) arrive_date,");
            sql.append(" A.F_PAY_OFF_TAX ,A.PAY_OFF_TAX, 12781002 AS IS_SELECT,A.ORDER_REGEDIT_NO,A.STOCK_IN_NO AS STOCK_IN_NO,");
            sql.append(" A.before_TAX_AMOUNT,A.TOTAL_AMOUNT,");
            sql.append(" A.IS_FINISHED,A.FINISHED_DATE,c.CUSTOMER_NAME,A.is_payoff,a.credence,");
            sql.append(" (select count(d.item_id) from tt_part_buy_item d where d.stock_in_no=a.stock_in_no");
            sql.append(" and d.DEALER_CODE = a.DEALER_CODE AND D.D_KEY ="+CommonConstants.D_KEY);
            sql.append(" ) ITEM_COUNT,");
            sql.append(" (select RECEIVE_REMARK from tt_part_buy_item e where e.stock_in_no=a.stock_in_no ");
            sql.append(" and e.DEALER_CODE = a.DEALER_CODE AND e.D_KEY ="+CommonConstants.D_KEY);
            sql.append(" LIMIT 1 ) RECEIVE_REMARK ,tax ");
            sql.append(" ,A.CUSTOMER_CODE,a.PAY_OFF_MAN from tt_part_buy a,("+CommonConstants.VM_PART_CUSTOMER+") C");
            sql.append("  WHERE  A.is_finished= ?");
            sql.append(" AND A.D_KEY = ?");
            sql.append(" AND LTRIM(RTRIM(A.CUSTOMER_CODE))=LTRIM(RTRIM(C.CUSTOMER_CODE))");
            sql.append(" AND A.DEALER_CODE = C.DEALER_CODE AND A.DEALER_CODE = ?");
            list.add(DictCodeConstants.DICT_IS_YES);
            list.add(CommonConstants.D_KEY);
            list.add(FrameworkUtil.getLoginInfo().getDealerCode());
            if (!StringUtils.isNullOrEmpty(map.get("orderRegeditNo"))) {
                sql.append(" AND A.ORDER_REGEDIT_NO=?");
                list.add(map.get("orderRegeditNo"));
            }
            if (!StringUtils.isNullOrEmpty(map.get("isPayoff"))) {
                sql.append(" and a.IS_PAYOFF= ?");
                list.add(map.get("isPayoff"));
            }
            if (!StringUtils.isNullOrEmpty(map.get("customerCode"))) {
                sql.append(" and a.CUSTOMER_CODE like ?");
                list.add("%"+map.get("customerCode")+"%");
            }
            if (!StringUtils.isNullOrEmpty(map.get("credence"))) {
                sql.append(" and a.CREDENCE like ?");
                list.add("%"+map.get("credence")+"%");
            }
            if (!StringUtils.isNullOrEmpty(map.get("arriveBeginDate"))&&!StringUtils.isNullOrEmpty(map.get("arriveEndDate"))) {
                Utility.getDateCond("A", "finished_date", map.get("arriveBeginDate").toString(), map.get("arriveEndDate").toString());
            }
            if (!StringUtils.isNullOrEmpty(map.get("beginDate"))&&!StringUtils.isNullOrEmpty(map.get("endDate"))) {
                Utility.getDateCond("", "(A.FINISHED_DATE+C.ACCOUNT_AGE)", map.get("beginDate").toString(), map.get("endDate").toString());
            }
            sql.append("  ORDER BY STOCK_IN_NO ");
        }else if (DictCodeConstants.DICT_BUSINESS_TYPE_PART_ALLOCATE_IN.equals(map.get("tag"))) {// 调拨入库
            sql.append("SELECT A.DEALER_CODE,A.PAY_OFF_DATE,(A.FINISHED_DATE+C.ACCOUNT_AGE ) arrive_date,A.F_PAY_OFF_TAX,A.PAY_OFF_TAX,12781002 AS IS_SELECT,A.ALLOCATE_IN_NO AS STOCK_IN_NO,");
            sql.append(" A.BEFOE_TAX_AMOUNT AS TOTAL_AMOUNT,A.IS_FINISHED,A.FINISHED_DATE, A.STOCK_IN_DATE, c.CUSTOMER_NAME,A.is_payoff,a.credence,");
            sql.append(" (select count(d.item_id) from TT_part_allocate_in_item d where d.ALLOCATE_IN_NO=a.ALLOCATE_IN_NO ");
            sql.append(" AND D.DEALER_CODE = A.DEALER_CODE and d.D_KEY ="+CommonConstants.D_KEY);
            sql.append(" )ITEM_COUNT,");
            sql.append(" (select RECEIVE_REMARK from TT_part_allocate_in_item e where e.ALLOCATE_IN_NO=a.ALLOCATE_IN_NO");
            sql.append(" and e.DEALER_CODE = a.DEALER_CODE AND e.D_KEY ="+CommonConstants.D_KEY);
            sql.append("  LIMIT 1 ) RECEIVE_REMARK ");
            sql.append(" ,A.CUSTOMER_CODE,a.PAY_OFF_MAN from TT_part_allocate_in a,("+CommonConstants.VM_PART_CUSTOMER+") C ");
            sql.append(" WHERE  A.is_finished= ? AND A.D_KEY =?");
            sql.append(" AND LTRIM(RTRIM(A.CUSTOMER_CODE)) = LTRIM(RTRIM(C.CUSTOMER_CODE)) ");
            sql.append("  AND A.DEALER_CODE = C.DEALER_CODE ");
            sql.append(" AND A.DEALER_CODE =?");
            list.add(DictCodeConstants.DICT_IS_YES);
            list.add(CommonConstants.D_KEY);
            list.add(FrameworkUtil.getLoginInfo().getDealerCode());
            if (!StringUtils.isNullOrEmpty(map.get("orderRegeditNo"))) {
                sql.append(" AND A.ORDER_REGEDIT_NO=?");
                list.add(map.get("orderRegeditNo"));
            }
            if (!StringUtils.isNullOrEmpty(map.get("isPayoff"))) {
                sql.append(" and a.IS_PAYOFF= ?");
                list.add(map.get("isPayoff"));
            }
            if (!StringUtils.isNullOrEmpty(map.get("customerCode"))) {
                sql.append(" and a.CUSTOMER_CODE like ?");
                list.add("%"+map.get("customerCode")+"%");
            }
            if (!StringUtils.isNullOrEmpty(map.get("credence"))) {
                sql.append(" and a.CREDENCE like ?");
                list.add("%"+map.get("credence")+"%");
            }
            if (!StringUtils.isNullOrEmpty(map.get("arriveBeginDate"))&&!StringUtils.isNullOrEmpty(map.get("arriveEndDate"))) {
                Utility.getDateCond("A", "finished_date", map.get("arriveBeginDate").toString(), map.get("arriveEndDate").toString());
            }
            if (!StringUtils.isNullOrEmpty(map.get("beginDate"))&&!StringUtils.isNullOrEmpty(map.get("endDate"))) {
                Utility.getDateCond("", "(A.FINISHED_DATE+C.ACCOUNT_AGE)", map.get("beginDate").toString(), map.get("endDate").toString());
            }
            sql.append("  ORDER BY STOCK_IN_NO ");
        }else if (DictCodeConstants.DICT_BUSINESS_TYPE_PART_ALLOCATE_OUT.equals(map.get("tag"))) {// 调拨出库
            sql.append("SELECT A.DEALER_CODE,A.PAY_OFF_DATE,(A.FINISHED_DATE+C.ACCOUNT_AGE ) arrive_date,12781002 AS IS_SELECT,A.F_PAY_OFF_TAX,A.PAY_OFF_TAX,A.ALLOCATE_OUT_NO AS STOCK_IN_NO,A.STOCK_OUT_DATE,");
            sql.append(" A.OUT_AMOUNT AS TOTAL_AMOUNT, A.IS_FINISHED, A.FINISHED_DATE, c.CUSTOMER_NAME, A.is_payoff, a.credence,");
            sql.append(" (select count(d.item_id) from TT_part_allocate_out_item d where d.ALLOCATE_OUT_NO = a.ALLOCATE_OUT_NO ");
            sql.append(" AND A.DEALER_CODE = D.DEALER_CODE and d.D_KEY ="+CommonConstants.D_KEY);
            sql.append(" ) ITEM_COUNT,");
            sql.append(" (select RECEIVE_REMARK from TT_part_allocate_out_item e where e.ALLOCATE_OUT_NO = a.ALLOCATE_OUT_NO");
            sql.append(" and e.DEALER_CODE = a.DEALER_CODE AND e.D_KEY ="+CommonConstants.D_KEY);
            sql.append(" LIMIT 1 ) RECEIVE_REMARK,a.PAY_OFF_MAN  ");
            sql.append(" from TT_part_allocate_out A, ("+CommonConstants.VM_PART_CUSTOMER+") C ");
            sql.append(" WHERE  A.is_finished= ? AND A.D_KEY =?");
            sql.append(" AND LTRIM(RTRIM(A.CUSTOMER_CODE)) = LTRIM(RTRIM(C.CUSTOMER_CODE)) ");
            sql.append("   AND A.DEALER_CODE = C.DEALER_CODE ");
            sql.append("  AND A.DEALER_CODE = ?");
            list.add(DictCodeConstants.DICT_IS_YES);
            list.add(CommonConstants.D_KEY);
            list.add(FrameworkUtil.getLoginInfo().getDealerCode());
            if (!StringUtils.isNullOrEmpty(map.get("orderRegeditNo"))) {
                sql.append(" AND A.ORDER_REGEDIT_NO=?");
                list.add(map.get("orderRegeditNo"));
            }
            if (!StringUtils.isNullOrEmpty(map.get("isPayoff"))) {
                sql.append(" and a.IS_PAYOFF= ?");
                list.add(map.get("isPayoff"));
            }
            if (!StringUtils.isNullOrEmpty(map.get("customerCode"))) {
                sql.append(" and a.CUSTOMER_CODE like ?");
                list.add("%"+map.get("customerCode")+"%");
            }
            if (!StringUtils.isNullOrEmpty(map.get("credence"))) {
                sql.append(" and a.CREDENCE like ?");
                list.add("%"+map.get("credence")+"%");
            }
            if (!StringUtils.isNullOrEmpty(map.get("arriveBeginDate"))&&!StringUtils.isNullOrEmpty(map.get("arriveEndDate"))) {
                Utility.getDateCond("A", "finished_date", map.get("arriveBeginDate").toString(), map.get("arriveEndDate").toString());
            }
            if (!StringUtils.isNullOrEmpty(map.get("beginDate"))&&!StringUtils.isNullOrEmpty(map.get("endDate"))) {
                Utility.getDateCond("", "(A.FINISHED_DATE+C.ACCOUNT_AGE)", map.get("beginDate").toString(), map.get("endDate").toString());
            }
            sql.append("  ORDER BY STOCK_IN_NO ");
        }
        return DAOUtil.pageQuery(sql.toString(), list);
    }

    @Override
    public PageInfoDto queryPartsBuyInfo(String id) throws ServiceBizException {
        List<Object> list = new ArrayList<>();
        StringBuffer sql = new StringBuffer("SELECT '12781002' AS IS_SELECTED, B.STOCK_QUANTITY,");
        sql.append("(cast(a.IN_PRICE AS decimal(14,2))) AS IN_PRICE,(cast(a.IN_PRICE_TAXED AS decimal(14,2))) AS IN_PRICE_TAXED,A.OLD_STOCK_IN_NO, ");
        sql.append("A.ITEM_ID,A.DEALER_CODE,A.STOCK_IN_NO,A.STORAGE_CODE,A.STORAGE_POSITION_CODE,A.PART_BATCH_NO,A.PART_NO,A.PART_NAME,A.UNIT_CODE, ");
        sql.append("A.UNIT_NAME,A.IN_QUANTITY,A.NEED_QUANTITY,A.INBOUND_QUANTITY,A.IS_FIAT,A.IN_AMOUNT,A.IN_AMOUNT_TAXED,A.COST_PRICE,A.COST_AMOUNT,");
        sql.append("A.D_KEY,A.CREATED_BY,A.CREATED_AT,A.UPDATED_BY,A.UPDATED_AT,A.VER,A.FROM_TYPE,A.RECEIVE_REMARK,A.OTHER_PART_COST_PRICE,A.OTHER_PART_COST_AMOUNT,");
        sql.append("B.MAX_STOCK,B.MIN_STOCK,(B.STOCK_QUANTITY+B.BORROW_QUANTITY-B.LEND_QUANTITY-B.LOCKED_QUANTITY) AS USEABLE_QUANTITY, ");
        sql.append("C.DOWN_TAG,C.CLAIM_PRICE FROM TT_PART_BUY_ITEM A ");
        sql.append("LEFT JOIN TM_PART_STOCK B ON A.PART_NO=B.PART_NO AND A.STORAGE_CODE=B.STORAGE_CODE AND A.DEALER_CODE=B.DEALER_CODE ");
        sql.append("LEFT JOIN TM_PART_INFO C ON A.DEALER_CODE = C.DEALER_CODE AND A.PART_NO = C.PART_NO WHERE A.DEALER_CODE=? ");
        sql.append("AND A.D_KEY=? AND A.STOCK_IN_NO=? ");
        list.add(FrameworkUtil.getLoginInfo().getDealerCode());
        list.add(CommonConstants.D_KEY);
        list.add(id);
        return DAOUtil.pageQuery(sql.toString(), list);
    }

    @Override
    public PageInfoDto queryPartAllocateInItem(String id) throws ServiceBizException {
        List<Object> list = new ArrayList<>();
        StringBuffer sql = new StringBuffer("select A.ITEM_ID,A.ALLOCATE_IN_NO,A.DEALER_CODE,A.STORAGE_CODE,A.STORAGE_POSITION_CODE,A.PART_BATCH_NO,A.PART_NO,");
        sql.append("A.PART_NAME,A.UNIT_CODE,A.COST_AMOUNT,A.IN_QUANTITY,A.COST_PRICE,A.IN_PRICE,A.IN_AMOUNT,b.down_tag ");
        sql.append("from Tt_Part_Allocate_In_Item a left join tm_part_info b on a.DEALER_CODE = b.DEALER_CODE and a.part_no = b.part_no ");
        sql.append("where 1=1  and a.DEALER_CODE=?  and a.D_Key=?  and a.Allocate_In_No=?");
        list.add(FrameworkUtil.getLoginInfo().getDealerCode());
        list.add(CommonConstants.D_KEY);
        list.add(id);
        return DAOUtil.pageQuery(sql.toString(), list);
    }

    @Override
    public PageInfoDto queryPartAllocateOutItem(String id) throws ServiceBizException {
        List<Object> list = new ArrayList<>();
        StringBuffer sql = new StringBuffer("select a.*,b.down_tag from Tt_Part_Allocate_Out_Item a left join tm_part_info b on a.DEALER_CODE = b.DEALER_CODE and a.part_no = b.part_no ");
        sql.append("where 1=1  and a.DEALER_CODE=?  and a.D_Key=?  and a.Allocate_Out_No=?  ");
        list.add(FrameworkUtil.getLoginInfo().getDealerCode());
        list.add(CommonConstants.D_KEY);
        list.add(id);
        return DAOUtil.pageQuery(sql.toString(), list);
    }

    @Override
    public PageInfoDto queryPartCustomer(Map map) throws ServiceBizException {
        List<Object> list = new ArrayList<>();
        StringBuffer sql = new StringBuffer("select A.BAL_OBJ_CODE,A.BAL_OBJ_NAME,A.ACCOUNT_AGE,A.ARREARAGE_AMOUNT,A.CUS_RECEIVE_SORT,");
        sql.append(" A.CUSTOMER_CODE, A.DEALER_CODE, A.CUSTOMER_TYPE_CODE, A.CUSTOMER_NAME, A.PRE_PAY,");
        sql.append(" A.CUSTOMER_SPELL, A.CUSTOMER_SHORT_NAME, A.ADDRESS, A.ZIP_CODE, A.CONTACTOR_NAME,");
        sql.append(" A.CONTACTOR_PHONE, A.CONTACTOR_MOBILE, A.FAX, A.CONTRACT_NO, A.AGREEMENT_BEGIN_DATE,");
        sql.append(" A.AGREEMENT_END_DATE, A.PRICE_ADD_RATE, A.PRICE_RATE, A.SALES_BASE_PRICE_TYPE,");
        sql.append(" A.CREDIT_AMOUNT, A.TOTAL_ARREARAGE_AMOUNT, A.ACCOUNT_TERM, A.TOTAL_ARREARAGE_TERM, ");
        sql.append(" A.OEM_TAG, A.LEAD_TIME, A.CREATED_BY, A.CREATED_AT, A.UPDATED_BY, A.UPDATED_AT, A.VER,");
        sql.append(" B.DEALER_SHORT_NAME as DEALER_NAME,D.VIN ");
        sql.append(" from ("+CommonConstants.VM_PART_CUSTOMER+") A");
        sql.append(" left join TM_PART_CUSTOMER C on A.DEALER_CODE=C.DEALER_CODE and A.CUSTOMER_CODE=C.CUSTOMER_CODE");
        sql.append(" left join TM_PART_CUSTOMER_DEALER B on C.DEALER_CODE=B.DEALER_CODE and C.DEALER_CODE=B.DEALER_CODE");
        sql.append(" LEFT JOIN ("+CommonConstants.VM_OWNER+") E ON A.DEALER_CODE=E.DEALER_CODE AND A.CUSTOMER_CODE=E.CUSTOMER_CODE ");
        sql.append(" LEFT JOIN ("+CommonConstants.VM_VEHICLE+") D ON E.DEALER_CODE=D.DEALER_CODE AND E.OWNER_NO=D.OWNER_NO ");
        sql.append(" WHERE  A.DEALER_CODE = ?");
        list.add(FrameworkUtil.getLoginInfo().getDealerCode());
        if (!StringUtils.isNullOrEmpty(map.get("customerNo"))) {
            sql.append(" and a.CUSTOMER_CODE like ?");
            list.add("%"+map.get("customerNo")+"%");
        }
        if (!StringUtils.isNullOrEmpty(map.get("oemTag"))) {
            sql.append(" and a.OEM_TAG = ?");
            list.add(map.get("oemTag"));
        }
        if (!StringUtils.isNullOrEmpty(map.get("customerName"))) {
            sql.append(" and a.CUSTOMER_NAME like ?");
            list.add("%"+map.get("customerName")+"%");
        }
        if (!StringUtils.isNullOrEmpty(map.get("customerType"))) {
            sql.append(" and A.CUSTOMER_TYPE_CODE = ?");
            list.add(map.get("customerType"));
        }
        sql.append(" ORDER BY A.CUSTOMER_CODE");
        return DAOUtil.pageQuery(sql.toString(), list);
    }

    @Override
    public List<Map> queryPartCustomers() throws ServiceBizException {
        List<Object> list = new ArrayList<>();
        StringBuffer sql = new StringBuffer("select A.BAL_OBJ_CODE,A.BAL_OBJ_NAME,A.ACCOUNT_AGE,A.ARREARAGE_AMOUNT,A.CUS_RECEIVE_SORT,");
        sql.append(" A.CUSTOMER_CODE, A.DEALER_CODE, A.CUSTOMER_TYPE_CODE, A.CUSTOMER_NAME, A.PRE_PAY,");
        sql.append(" A.CUSTOMER_SPELL, A.CUSTOMER_SHORT_NAME, A.ADDRESS, A.ZIP_CODE, A.CONTACTOR_NAME,");
        sql.append(" A.CONTACTOR_PHONE, A.CONTACTOR_MOBILE, A.FAX, A.CONTRACT_NO, A.AGREEMENT_BEGIN_DATE,");
        sql.append(" A.AGREEMENT_END_DATE, A.PRICE_ADD_RATE, A.PRICE_RATE, A.SALES_BASE_PRICE_TYPE,");
        sql.append(" A.CREDIT_AMOUNT, A.TOTAL_ARREARAGE_AMOUNT, A.ACCOUNT_TERM, A.TOTAL_ARREARAGE_TERM, ");
        sql.append(" A.OEM_TAG, A.LEAD_TIME, A.CREATED_BY, A.CREATED_AT, A.UPDATED_BY, A.UPDATED_AT, A.VER,");
        sql.append(" B.DEALER_SHORT_NAME as DEALER_NAME,D.VIN ");
        sql.append(" from ("+CommonConstants.VM_PART_CUSTOMER+") A");
        sql.append(" left join TM_PART_CUSTOMER C on A.DEALER_CODE=C.DEALER_CODE and A.CUSTOMER_CODE=C.CUSTOMER_CODE");
        sql.append(" left join TM_PART_CUSTOMER_DEALER B on C.DEALER_CODE=B.DEALER_CODE and C.DEALER_CODE=B.DEALER_CODE");
        sql.append(" LEFT JOIN ("+CommonConstants.VM_OWNER+") E ON A.DEALER_CODE=E.DEALER_CODE AND A.CUSTOMER_CODE=E.CUSTOMER_CODE ");
        sql.append(" LEFT JOIN ("+CommonConstants.VM_VEHICLE+") D ON E.DEALER_CODE=D.DEALER_CODE AND E.OWNER_NO=D.OWNER_NO ");
        sql.append(" WHERE  A.DEALER_CODE = ?");
        list.add(FrameworkUtil.getLoginInfo().getDealerCode());
        sql.append(" ORDER BY A.CUSTOMER_CODE");
        return DAOUtil.findAll(sql.toString(), list);
    }

    @Override
    public List<Map> queryPartsBuyInfoExport(String id) throws ServiceBizException {
        List<Object> list = new ArrayList<>();
        StringBuffer sql = new StringBuffer("SELECT '12781002' AS IS_SELECTED, B.STOCK_QUANTITY,");
        sql.append("(cast(a.IN_PRICE AS decimal(14,2))) AS IN_PRICE,(cast(a.IN_PRICE_TAXED AS decimal(14,2))) AS IN_PRICE_TAXED,A.OLD_STOCK_IN_NO, ");
        sql.append("A.ITEM_ID,A.DEALER_CODE,A.STOCK_IN_NO,A.STORAGE_CODE,A.STORAGE_POSITION_CODE,A.PART_BATCH_NO,A.PART_NO,A.PART_NAME,A.UNIT_CODE, ");
        sql.append("A.UNIT_NAME,A.IN_QUANTITY,A.NEED_QUANTITY,A.INBOUND_QUANTITY,A.IS_FIAT,A.IN_AMOUNT,A.IN_AMOUNT_TAXED,A.COST_PRICE,A.COST_AMOUNT,");
        sql.append("A.D_KEY,A.CREATED_BY,A.CREATED_AT,A.UPDATED_BY,A.UPDATED_AT,A.VER,A.FROM_TYPE,A.RECEIVE_REMARK,A.OTHER_PART_COST_PRICE,A.OTHER_PART_COST_AMOUNT,");
        sql.append("B.MAX_STOCK,B.MIN_STOCK,(B.STOCK_QUANTITY+B.BORROW_QUANTITY-B.LEND_QUANTITY-B.LOCKED_QUANTITY) AS USEABLE_QUANTITY, ");
        sql.append("C.DOWN_TAG,C.CLAIM_PRICE FROM TT_PART_BUY_ITEM A ");
        sql.append("LEFT JOIN TM_PART_STOCK B ON A.PART_NO=B.PART_NO AND A.STORAGE_CODE=B.STORAGE_CODE AND A.DEALER_CODE=B.DEALER_CODE ");
        sql.append("LEFT JOIN TM_PART_INFO C ON A.DEALER_CODE = C.DEALER_CODE AND A.PART_NO = C.PART_NO WHERE A.DEALER_CODE=? ");
        sql.append("AND A.D_KEY=? AND A.STOCK_IN_NO=? ");
        list.add(FrameworkUtil.getLoginInfo().getDealerCode());
        list.add(CommonConstants.D_KEY);
        list.add(id);
        return DAOUtil.findAll(sql.toString(), list);
    }

    @Override
    public List<Map> queryPartAllocateInItemExport(String id) throws ServiceBizException {
        List<Object> list = new ArrayList<>();
        StringBuffer sql = new StringBuffer("select A.ITEM_ID,A.ALLOCATE_IN_NO,A.DEALER_CODE,A.STORAGE_CODE,A.STORAGE_POSITION_CODE,A.PART_BATCH_NO,A.PART_NO,");
        sql.append("A.PART_NAME,A.UNIT_CODE,A.COST_AMOUNT,A.IN_QUANTITY,A.COST_PRICE,A.IN_PRICE,A.IN_AMOUNT,b.down_tag ");
        sql.append("from Tt_Part_Allocate_In_Item a left join tm_part_info b on a.DEALER_CODE = b.DEALER_CODE and a.part_no = b.part_no ");
        sql.append("where 1=1  and a.DEALER_CODE=?  and a.D_Key=?  and a.Allocate_In_No=?");
        list.add(FrameworkUtil.getLoginInfo().getDealerCode());
        list.add(CommonConstants.D_KEY);
        list.add(id);
        return DAOUtil.findAll(sql.toString(), list);
    }

    @Override
    public List<Map> queryPartAllocateOutItemExport(String id) throws ServiceBizException {
        List<Object> list = new ArrayList<>();
        StringBuffer sql = new StringBuffer("select a.*,b.down_tag from Tt_Part_Allocate_Out_Item a left join tm_part_info b on a.DEALER_CODE = b.DEALER_CODE and a.part_no = b.part_no ");
        sql.append("where 1=1  and a.DEALER_CODE=?  and a.D_Key=?  and a.Allocate_Out_No=?  ");
        list.add(FrameworkUtil.getLoginInfo().getDealerCode());
        list.add(CommonConstants.D_KEY);
        list.add(id);
        return DAOUtil.findAll(sql.toString(), list);
    }

    @Override
    public List<Map> retrieveByReceiveExport(Map map) throws ServiceBizException {
        List<Object> list = new ArrayList<>();
        StringBuffer sql = new StringBuffer();
        if (DictCodeConstants.DICT_BUSINESS_TYPE_PART_STOCK.equals(map.get("tag"))) {//采购入库 
            sql.append("SELECT A.DEALER_CODE,A.PAY_OFF_DATE,(A.FINISHED_DATE+C.ACCOUNT_AGE) arrive_date,");
            sql.append(" A.F_PAY_OFF_TAX ,A.PAY_OFF_TAX, 12781002 AS IS_SELECT,A.ORDER_REGEDIT_NO,A.STOCK_IN_NO AS STOCK_IN_NO,");
            sql.append(" A.before_TAX_AMOUNT,A.TOTAL_AMOUNT,");
            sql.append(" A.IS_FINISHED,A.FINISHED_DATE,c.CUSTOMER_NAME,A.is_payoff,a.credence,");
            sql.append(" (select count(d.item_id) from tt_part_buy_item d where d.stock_in_no=a.stock_in_no");
            sql.append(" and d.DEALER_CODE = a.DEALER_CODE AND D.D_KEY ="+CommonConstants.D_KEY);
            sql.append(" ) ITEM_COUNT,");
            sql.append(" (select RECEIVE_REMARK from tt_part_buy_item e where e.stock_in_no=a.stock_in_no ");
            sql.append(" and e.DEALER_CODE = a.DEALER_CODE AND e.D_KEY ="+CommonConstants.D_KEY);
            sql.append(" LIMIT 1 ) RECEIVE_REMARK ,tax ");
            sql.append(" ,A.CUSTOMER_CODE,a.PAY_OFF_MAN from tt_part_buy a,("+CommonConstants.VM_PART_CUSTOMER+") C");
            sql.append("  WHERE  A.is_finished= ?");
            sql.append(" AND A.D_KEY = ?");
            sql.append(" AND LTRIM(RTRIM(A.CUSTOMER_CODE))=LTRIM(RTRIM(C.CUSTOMER_CODE))");
            sql.append(" AND A.DEALER_CODE = C.DEALER_CODE AND A.DEALER_CODE = ?");
            list.add(DictCodeConstants.DICT_IS_YES);
            list.add(CommonConstants.D_KEY);
            list.add(FrameworkUtil.getLoginInfo().getDealerCode());
            if (!StringUtils.isNullOrEmpty(map.get("orderRegeditNo"))) {
                sql.append(" AND A.ORDER_REGEDIT_NO=?");
                list.add(map.get("orderRegeditNo"));
            }
            if (!StringUtils.isNullOrEmpty(map.get("isPayoff"))) {
                sql.append(" and a.IS_PAYOFF= ?");
                list.add(map.get("isPayoff"));
            }
            if (!StringUtils.isNullOrEmpty(map.get("customerCode"))) {
                sql.append(" and a.CUSTOMER_CODE like ?");
                list.add("%"+map.get("customerCode")+"%");
            }
            if (!StringUtils.isNullOrEmpty(map.get("credence"))) {
                sql.append(" and a.CREDENCE like ?");
                list.add("%"+map.get("credence")+"%");
            }
            if (!StringUtils.isNullOrEmpty(map.get("arriveBeginDate"))&&!StringUtils.isNullOrEmpty(map.get("arriveEndDate"))) {
                Utility.getDateCond("A", "finished_date", map.get("arriveBeginDate").toString(), map.get("arriveEndDate").toString());
            }
            if (!StringUtils.isNullOrEmpty(map.get("beginDate"))&&!StringUtils.isNullOrEmpty(map.get("endDate"))) {
                Utility.getDateCond("", "(A.FINISHED_DATE+C.ACCOUNT_AGE)", map.get("beginDate").toString(), map.get("endDate").toString());
            }
            sql.append("  ORDER BY STOCK_IN_NO ");
        }else if (DictCodeConstants.DICT_BUSINESS_TYPE_PART_ALLOCATE_IN.equals(map.get("tag"))) {// 调拨入库
            sql.append("SELECT A.DEALER_CODE,A.PAY_OFF_DATE,(A.FINISHED_DATE+C.ACCOUNT_AGE ) arrive_date,A.F_PAY_OFF_TAX,A.PAY_OFF_TAX,12781002 AS IS_SELECT,A.ALLOCATE_IN_NO AS STOCK_IN_NO,");
            sql.append(" A.BEFOE_TAX_AMOUNT AS TOTAL_AMOUNT,A.IS_FINISHED,A.FINISHED_DATE, A.STOCK_IN_DATE, c.CUSTOMER_NAME,A.is_payoff,a.credence,");
            sql.append(" (select count(d.item_id) from TT_part_allocate_in_item d where d.ALLOCATE_IN_NO=a.ALLOCATE_IN_NO ");
            sql.append(" AND D.DEALER_CODE = A.DEALER_CODE and d.D_KEY ="+CommonConstants.D_KEY);
            sql.append(" )ITEM_COUNT,");
            sql.append(" (select RECEIVE_REMARK from TT_part_allocate_in_item e where e.ALLOCATE_IN_NO=a.ALLOCATE_IN_NO");
            sql.append(" and e.DEALER_CODE = a.DEALER_CODE AND e.D_KEY ="+CommonConstants.D_KEY);
            sql.append("  LIMIT 1 ) RECEIVE_REMARK ");
            sql.append(" ,A.CUSTOMER_CODE,a.PAY_OFF_MAN from TT_part_allocate_in a,("+CommonConstants.VM_PART_CUSTOMER+") C ");
            sql.append(" WHERE  A.is_finished= ? AND A.D_KEY =?");
            sql.append(" AND LTRIM(RTRIM(A.CUSTOMER_CODE)) = LTRIM(RTRIM(C.CUSTOMER_CODE)) ");
            sql.append("  AND A.DEALER_CODE = C.DEALER_CODE ");
            sql.append(" AND A.DEALER_CODE =?");
            list.add(DictCodeConstants.DICT_IS_YES);
            list.add(CommonConstants.D_KEY);
            list.add(FrameworkUtil.getLoginInfo().getDealerCode());
            if (!StringUtils.isNullOrEmpty(map.get("orderRegeditNo"))) {
                sql.append(" AND A.ORDER_REGEDIT_NO=?");
                list.add(map.get("orderRegeditNo"));
            }
            if (!StringUtils.isNullOrEmpty(map.get("isPayoff"))) {
                sql.append(" and a.IS_PAYOFF= ?");
                list.add(map.get("isPayoff"));
            }
            if (!StringUtils.isNullOrEmpty(map.get("customerCode"))) {
                sql.append(" and a.CUSTOMER_CODE like ?");
                list.add("%"+map.get("customerCode")+"%");
            }
            if (!StringUtils.isNullOrEmpty(map.get("credence"))) {
                sql.append(" and a.CREDENCE like ?");
                list.add("%"+map.get("credence")+"%");
            }
            if (!StringUtils.isNullOrEmpty(map.get("arriveBeginDate"))&&!StringUtils.isNullOrEmpty(map.get("arriveEndDate"))) {
                Utility.getDateCond("A", "finished_date", map.get("arriveBeginDate").toString(), map.get("arriveEndDate").toString());
            }
            if (!StringUtils.isNullOrEmpty(map.get("beginDate"))&&!StringUtils.isNullOrEmpty(map.get("endDate"))) {
                Utility.getDateCond("", "(A.FINISHED_DATE+C.ACCOUNT_AGE)", map.get("beginDate").toString(), map.get("endDate").toString());
            }
            sql.append("  ORDER BY STOCK_IN_NO ");
        }else if (DictCodeConstants.DICT_BUSINESS_TYPE_PART_ALLOCATE_OUT.equals(map.get("tag"))) {// 调拨出库
            sql.append("SELECT A.DEALER_CODE,A.PAY_OFF_DATE,(A.FINISHED_DATE+C.ACCOUNT_AGE ) arrive_date,12781002 AS IS_SELECT,A.F_PAY_OFF_TAX,A.PAY_OFF_TAX,A.ALLOCATE_OUT_NO AS STOCK_IN_NO,A.STOCK_OUT_DATE,");
            sql.append(" A.OUT_AMOUNT AS TOTAL_AMOUNT, A.IS_FINISHED, A.FINISHED_DATE, c.CUSTOMER_NAME, A.is_payoff, a.credence,");
            sql.append(" (select count(d.item_id) from TT_part_allocate_out_item d where d.ALLOCATE_OUT_NO = a.ALLOCATE_OUT_NO ");
            sql.append(" AND A.DEALER_CODE = D.DEALER_CODE and d.D_KEY ="+CommonConstants.D_KEY);
            sql.append(" ) ITEM_COUNT,");
            sql.append(" (select RECEIVE_REMARK from TT_part_allocate_out_item e where e.ALLOCATE_OUT_NO = a.ALLOCATE_OUT_NO");
            sql.append(" and e.DEALER_CODE = a.DEALER_CODE AND e.D_KEY ="+CommonConstants.D_KEY);
            sql.append(" LIMIT 1 ) RECEIVE_REMARK,a.PAY_OFF_MAN  ");
            sql.append(" from TT_part_allocate_out A, ("+CommonConstants.VM_PART_CUSTOMER+") C ");
            sql.append(" WHERE  A.is_finished= ? AND A.D_KEY =?");
            sql.append(" AND LTRIM(RTRIM(A.CUSTOMER_CODE)) = LTRIM(RTRIM(C.CUSTOMER_CODE)) ");
            sql.append("   AND A.DEALER_CODE = C.DEALER_CODE ");
            sql.append("  AND A.DEALER_CODE = ?");
            list.add(DictCodeConstants.DICT_IS_YES);
            list.add(CommonConstants.D_KEY);
            list.add(FrameworkUtil.getLoginInfo().getDealerCode());
            if (!StringUtils.isNullOrEmpty(map.get("orderRegeditNo"))) {
                sql.append(" AND A.ORDER_REGEDIT_NO=?");
                list.add(map.get("orderRegeditNo"));
            }
            if (!StringUtils.isNullOrEmpty(map.get("isPayoff"))) {
                sql.append(" and a.IS_PAYOFF= ?");
                list.add(map.get("isPayoff"));
            }
            if (!StringUtils.isNullOrEmpty(map.get("customerCode"))) {
                sql.append(" and a.CUSTOMER_CODE like ?");
                list.add("%"+map.get("customerCode")+"%");
            }
            if (!StringUtils.isNullOrEmpty(map.get("credence"))) {
                sql.append(" and a.CREDENCE like ?");
                list.add("%"+map.get("credence")+"%");
            }
            if (!StringUtils.isNullOrEmpty(map.get("arriveBeginDate"))&&!StringUtils.isNullOrEmpty(map.get("arriveEndDate"))) {
                Utility.getDateCond("A", "finished_date", map.get("arriveBeginDate").toString(), map.get("arriveEndDate").toString());
            }
            if (!StringUtils.isNullOrEmpty(map.get("beginDate"))&&!StringUtils.isNullOrEmpty(map.get("endDate"))) {
                Utility.getDateCond("", "(A.FINISHED_DATE+C.ACCOUNT_AGE)", map.get("beginDate").toString(), map.get("endDate").toString());
            }
            sql.append("  ORDER BY STOCK_IN_NO ");
        }
        return DAOUtil.findAll(sql.toString(), list);
    }

    @SuppressWarnings("unchecked")
    @Override
    public void maintainPartPayUpdate(Map map) throws ServiceBizException {
        map.get("tables");
        map.get("stables");
        if (DictCodeConstants.DICT_BUSINESS_TYPE_PART_STOCK.equals(map.get("tag"))) {
            List<Map> object = (List<Map>)map.get("dmstable");
            for (Map map2 : object) {
                if (!StringUtils.isNullOrEmpty(map2.get("STOCK_IN_NO"))) {
                    PartBuyItemPO.update("RECEIVE_REMARK=?", "STOCK_IN_NO=?", map2.get("RECEIVE_REMARK"),map2.get("STOCK_IN_NO"));
                }
            }
        }else if (DictCodeConstants.DICT_BUSINESS_TYPE_PART_ALLOCATE_IN.equals(map.get("tag"))) {
            List<Map> object = (List<Map>)map.get("tables");
            for (Map map2 : object) {
                if (!StringUtils.isNullOrEmpty(map2.get("STOCK_IN_NO"))) {
                    PartAllocateInItemPO.update("RECEIVE_REMARK=?", "STOCK_IN_NO=?", map2.get("RECEIVE_REMARK"),map2.get("STOCK_IN_NO"));
                }
            }
        }else if (DictCodeConstants.DICT_BUSINESS_TYPE_PART_ALLOCATE_OUT.equals(map.get("tag"))) {
            List<Map> object = (List<Map>)map.get("stables");
            for (Map map2 : object) {
                if (!StringUtils.isNullOrEmpty(map2.get("STOCK_IN_NO"))) {
                    PartAllocateOutItemPo.update("RECEIVE_REMARK=?", "ALLOCATE_OUT_NO=?", map2.get("RECEIVE_REMARK"),map2.get("STOCK_IN_NO"));
                }
            }
        }
    }
}
