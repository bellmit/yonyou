
/** 
*Copyright 2017 Yonyou Corporation Ltd. All Rights Reserved.
* This software is published under the terms of the Yonyou Software
* License version 1.0, a copy of which has been included with this
* distribution in the LICENSE.txt file.
*
* @Project Name : dms.part
*
* @File name : BookPartReleaseServiceImpl.java
*
* @Author : dingchaoyu
*
* @Date : 2017年4月24日
*
----------------------------------------------------------------------------------
*     Date       Who       Version     Comments
* 1. 2017年4月24日    dingchaoyu    1.0
*
*
*
*
----------------------------------------------------------------------------------
*/
	
package com.yonyou.dms.part.service.basedata;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.yonyou.dms.common.domains.PO.basedata.TmPartStockPO;
import com.yonyou.dms.common.domains.PO.basedata.TtBookingOrderPartPO;
import com.yonyou.dms.common.domains.PO.basedata.TtPartObligatedItemPO;
import com.yonyou.dms.common.domains.PO.basedata.TtPartObligatedPO;
import com.yonyou.dms.framework.DAO.DAOUtil;
import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.framework.util.FrameworkUtil;
import com.yonyou.dms.framework.util.Utility;
import com.yonyou.dms.function.common.CommonConstants;
import com.yonyou.dms.function.common.DictCodeConstants;
import com.yonyou.dms.function.exception.ServiceBizException;
import com.yonyou.dms.function.utils.common.StringUtils;
import com.yonyou.dms.part.domains.DTO.basedata.BookPartReleaseDTO;

/**
* TODO description
* @author dingchaoyu
* @date 2017年4月24日
*/
@Service
public class BookPartReleaseServiceImpl implements BookPartReleaseService{

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
            sql.append(" and A.PART_NO like ?");
            list.add("%"+map.get("PART_NO")+"%");
        }
        if (!StringUtils.isNullOrEmpty(map.get("PART_NAME"))) {
            sql.append(" and A.PART_NAME like ?");
            list.add("%"+map.get("PART_NAME")+"%");
        }
        if (!StringUtils.isNullOrEmpty(map.get("obligatedNo"))) {
            sql.append(" and D.OBLIGATED_NO = ? ");
            list.add("%"+map.get("obligatedNo")+"%");
        }
        if (!StringUtils.isNullOrEmpty(map.get("BOOK_BEGIN_DATE"))&&!StringUtils.isNullOrEmpty(map.get("BOOK_END_DATE"))) {
            sql.append(Utility.getDateCond("A", "CREATED_AT", map.get("BOOK_BEGIN_DATE").toString(), map.get("BOOK_END_DATE").toString()));
        }
        if (!StringUtils.isNullOrEmpty(map.get("OBLIGATED_CLOSE_DATE"))&&!StringUtils.isNullOrEmpty(map.get("END_DATE"))) {
            sql.append(Utility.getDateCond("A", "OBLIGATED_CLOSE_DATE", map.get("BEGIN_DATE").toString(), map.get("END_DATE").toString()));
        }
        return DAOUtil.pageQuery(sql.toString(), list);
    }

    @Override
    public PageInfoDto QueryPartObligatedSec(Map map) throws ServiceBizException {
        List<Object> list = new ArrayList<>();
        StringBuffer sql = new StringBuffer("SELECT '' AS IS_SELECTED,b.ITEM_ID,a.DEALER_CODE,a.OBLIGATED_NO,b.PART_NO,b.PART_NAME,b.STORAGE_CODE, b.IS_OBLIGATED, ");
        sql.append("b.STORAGE_POSITION_CODE,b.UNIT_CODE, b.QUANTITY,b.CREATED_AT as BOOKING_DATE,a.APPLICANT,a.LICENSE,a.SHEET_NO ");
        sql.append("FROM TT_PART_OBLIGATED a LEFT JOIN TT_PART_OBLIGATED_ITEM b ON (a.DEALER_CODE = b.DEALER_CODE ");
        sql.append("AND a.OBLIGATED_NO = b.OBLIGATED_NO) WHERE A.DEALER_CODE =? AND A.D_KEY =? ");
        sql.append("AND a.Obligated_Close =? AND B.QUANTITY > 0 AND B.IS_OBLIGATED =? AND SHEET_NO IS NULL ");
        list.add(FrameworkUtil.getLoginInfo().getDealerCode());
        list.add(CommonConstants.D_KEY);
        list.add(DictCodeConstants.DICT_IS_NO);
        list.add(DictCodeConstants.DICT_IS_YES);
        if (!StringUtils.isNullOrEmpty(map.get("STORAGE_CODE"))) {
            sql.append(" and b.STORAGE_CODE = ? ");
            list.add(map.get("STORAGE_CODE"));
        }
        if (!StringUtils.isNullOrEmpty(map.get("PART_NO"))) {
            sql.append(" and b.PART_NO like ?");
            list.add("%"+map.get("PART_NO")+"%");
        }
        if (!StringUtils.isNullOrEmpty(map.get("PART_NAME"))) {
            sql.append(" and b.PART_NAME like ?");
            list.add("%"+map.get("PART_NAME")+"%");
        }
        if (!StringUtils.isNullOrEmpty(map.get("obligatedNo"))) {
            sql.append(" and A.OBLIGATED_NO = ? ");
            list.add("%"+map.get("obligatedNo")+"%");
        }
        if (!StringUtils.isNullOrEmpty(map.get("BOOK_BEGIN_DATE"))&&!StringUtils.isNullOrEmpty(map.get("BOOK_END_DATE"))) {
            sql.append(Utility.getDateCond("A", "CREATED_AT", map.get("BOOK_BEGIN_DATE").toString(), map.get("BOOK_END_DATE").toString()));
        }
        if (!StringUtils.isNullOrEmpty(map.get("OBLIGATED_CLOSE_DATE"))&&!StringUtils.isNullOrEmpty(map.get("END_DATE"))) {
            sql.append(Utility.getDateCond("A", "OBLIGATED_CLOSE_DATE", map.get("BEGIN_DATE").toString(), map.get("END_DATE").toString()));
        }
        return DAOUtil.pageQuery(sql.toString(), list);
    }
    
    @Override
    public PageInfoDto QueryPartObligatedSecRo(Map map) throws ServiceBizException {
        List<Object> list = new ArrayList<>();
        StringBuffer sql = new StringBuffer("SELECT '' AS IS_SELECTED,a.DEALER_CODE,b.ITEM_ID,a.OBLIGATED_NO,b.PART_NO,b.PART_NAME,b.STORAGE_CODE, b.IS_OBLIGATED, ");
        sql.append("b.STORAGE_POSITION_CODE,b.UNIT_CODE, b.QUANTITY,b.CREATED_AT as BOOKING_DATE,a.APPLICANT,a.LICENSE,a.SHEET_NO ");
        sql.append("FROM TT_PART_OBLIGATED a LEFT JOIN TT_PART_OBLIGATED_ITEM b ON (a.DEALER_CODE = b.DEALER_CODE ");
        sql.append("AND a.OBLIGATED_NO = b.OBLIGATED_NO) WHERE A.DEALER_CODE =? AND A.D_KEY =? ");
        sql.append("AND a.Obligated_Close =? AND B.QUANTITY > 0 AND B.IS_OBLIGATED =? AND SHEET_NO LIKE '%RO%' ");
        list.add(FrameworkUtil.getLoginInfo().getDealerCode());
        list.add(CommonConstants.D_KEY);
        list.add(DictCodeConstants.DICT_IS_NO);
        list.add(DictCodeConstants.DICT_IS_YES);
        if (!StringUtils.isNullOrEmpty(map.get("STORAGE_CODE"))) {
            sql.append(" and b.STORAGE_CODE = ? ");
            list.add(map.get("STORAGE_CODE"));
        }
        if (!StringUtils.isNullOrEmpty(map.get("PART_NO"))) {
            sql.append(" and b.PART_NO like ? ");
            list.add("%"+map.get("PART_NO")+"%");
        }
        if (!StringUtils.isNullOrEmpty(map.get("PART_NAME"))) {
            sql.append(" and b.PART_NAME like ? ");
            list.add("%"+map.get("PART_NAME")+"%");
        }
        if (!StringUtils.isNullOrEmpty(map.get("obligatedNo"))) {
            sql.append(" and A.OBLIGATED_NO = ? ");
            list.add("%"+map.get("obligatedNo")+"%");
        }
        if (!StringUtils.isNullOrEmpty(map.get("BOOK_BEGIN_DATE"))&&!StringUtils.isNullOrEmpty(map.get("BOOK_END_DATE"))) {
            sql.append(Utility.getDateCond("A", "CREATED_AT", map.get("BOOK_BEGIN_DATE").toString(), map.get("BOOK_END_DATE").toString()));
        }
        if (!StringUtils.isNullOrEmpty(map.get("OBLIGATED_CLOSE_DATE"))&&!StringUtils.isNullOrEmpty(map.get("END_DATE"))) {
            sql.append(Utility.getDateCond("A", "OBLIGATED_CLOSE_DATE", map.get("BEGIN_DATE").toString(), map.get("END_DATE").toString()));
        }
        return DAOUtil.pageQuery(sql.toString(), list);
    }

    @Override
    public List<Map> queryPartBookReleaseForExportbooking(Map<String, String> map) throws ServiceBizException {
        List<Object> list = new ArrayList<>();
        StringBuffer sql = new StringBuffer("SELECT '' AS IS_SELECTED,a.DEALER_CODE,a.OBLIGATED_NO,b.PART_NO,b.PART_NAME,b.STORAGE_CODE, b.IS_OBLIGATED, ");
        sql.append("b.STORAGE_POSITION_CODE,b.UNIT_CODE, b.QUANTITY,b.CREATED_AT as BOOKING_DATE,a.APPLICANT,a.LICENSE,a.SHEET_NO ");
        sql.append("FROM TT_PART_OBLIGATED a LEFT JOIN TT_PART_OBLIGATED_ITEM b ON (a.DEALER_CODE = b.DEALER_CODE ");
        sql.append("AND a.OBLIGATED_NO = b.OBLIGATED_NO) WHERE A.DEALER_CODE =? AND A.D_KEY =? ");
        sql.append("AND a.Obligated_Close =? AND B.QUANTITY > 0 AND B.IS_OBLIGATED =? AND SHEET_NO IS NULL ");
        list.add(FrameworkUtil.getLoginInfo().getDealerCode());
        list.add(CommonConstants.D_KEY);
        list.add(DictCodeConstants.DICT_IS_NO);
        list.add(DictCodeConstants.DICT_IS_YES);
        if (!StringUtils.isNullOrEmpty(map.get("STORAGE_CODE"))) {
            sql.append(" and b.STORAGE_CODE = ? ");
            list.add(map.get("STORAGE_CODE"));
        }
        if (!StringUtils.isNullOrEmpty(map.get("PART_NO"))) {
            sql.append(" and b.PART_NO like ? ");
            list.add("%"+map.get("PART_NO")+"%");
        }
        if (!StringUtils.isNullOrEmpty(map.get("PART_NAME"))) {
            sql.append(" and b.PART_NAME like ? ");
            list.add("%"+map.get("PART_NAME")+"%");
        }
        if (!StringUtils.isNullOrEmpty(map.get("obligatedNo "))) {
            sql.append(" and A.OBLIGATED_NO = ? ");
            list.add("%"+map.get("obligatedNo")+"%");
        }
        if (!StringUtils.isNullOrEmpty(map.get("BOOK_BEGIN_DATE"))&&!StringUtils.isNullOrEmpty(map.get("BOOK_END_DATE"))) {
            sql.append(Utility.getDateCond("A", "CREATED_AT", map.get("BOOK_BEGIN_DATE").toString(), map.get("BOOK_END_DATE").toString()));
        }
        if (!StringUtils.isNullOrEmpty(map.get("OBLIGATED_CLOSE_DATE"))&&!StringUtils.isNullOrEmpty(map.get("END_DATE"))) {
            sql.append(Utility.getDateCond("A", "OBLIGATED_CLOSE_DATE", map.get("BEGIN_DATE").toString(), map.get("END_DATE").toString()));
        }
        return DAOUtil.findAll(sql.toString(), list);
    }
    
    @Override
    public List<Map> queryPartBookReleaseForExportro(Map<String, String> map) throws ServiceBizException {
        List<Object> list = new ArrayList<>();
        StringBuffer sql = new StringBuffer("SELECT '' AS IS_SELECTED,a.DEALER_CODE,a.OBLIGATED_NO,b.PART_NO,b.PART_NAME,b.STORAGE_CODE, b.IS_OBLIGATED, ");
        sql.append("b.STORAGE_POSITION_CODE,b.UNIT_CODE, b.QUANTITY,b.CREATED_AT as BOOKING_DATE,a.APPLICANT,a.LICENSE,a.SHEET_NO ");
        sql.append("FROM TT_PART_OBLIGATED a LEFT JOIN TT_PART_OBLIGATED_ITEM b ON (a.DEALER_CODE = b.DEALER_CODE ");
        sql.append("AND a.OBLIGATED_NO = b.OBLIGATED_NO) WHERE A.DEALER_CODE =? AND A.D_KEY =? ");
        sql.append("AND a.Obligated_Close =? AND B.QUANTITY > 0 AND B.IS_OBLIGATED =? AND SHEET_NO LIKE '%RO%' ");
        list.add(FrameworkUtil.getLoginInfo().getDealerCode());
        list.add(CommonConstants.D_KEY);
        list.add(DictCodeConstants.DICT_IS_NO);
        list.add(DictCodeConstants.DICT_IS_YES);
        if (!StringUtils.isNullOrEmpty(map.get("STORAGE_CODE"))) {
            sql.append(" and b.STORAGE_CODE = ? ");
            list.add(map.get("STORAGE_CODE"));
        }
        if (!StringUtils.isNullOrEmpty(map.get("PART_NO"))) {
            sql.append(" and b.PART_NO like ? ");
            list.add("%"+map.get("PART_NO")+"%");
        }
        if (!StringUtils.isNullOrEmpty(map.get("PART_NAME"))) {
            sql.append(" and b.PART_NAME like ? ");
            list.add("%"+map.get("PART_NAME")+"%");
        }
        if (!StringUtils.isNullOrEmpty(map.get("obligatedNo"))) {
            sql.append(" and A.OBLIGATED_NO = ? ");
            list.add("%"+map.get("obligatedNo")+"%");
        }
        if (!StringUtils.isNullOrEmpty(map.get("BOOK_BEGIN_DATE"))&&!StringUtils.isNullOrEmpty(map.get("BOOK_END_DATE"))) {
            sql.append(Utility.getDateCond("A", "CREATED_AT", map.get("BOOK_BEGIN_DATE").toString(), map.get("BOOK_END_DATE").toString()));
        }
        if (!StringUtils.isNullOrEmpty(map.get("OBLIGATED_CLOSE_DATE"))&&!StringUtils.isNullOrEmpty(map.get("END_DATE"))) {
            sql.append(Utility.getDateCond("A", "OBLIGATED_CLOSE_DATE", map.get("BEGIN_DATE").toString(), map.get("END_DATE").toString()));
        }
        return DAOUtil.findAll(sql.toString(), list);
    }
    
    @Override
    public List<Map> queryPartBookReleaseForExportall(Map<String, String> map) throws ServiceBizException {
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
            sql.append(" and D.OBLIGATED_NO = ? ");
            list.add("%"+map.get("obligatedNo")+"%");
        }
        if (!StringUtils.isNullOrEmpty(map.get("BOOK_BEGIN_DATE"))&&!StringUtils.isNullOrEmpty(map.get("BOOK_END_DATE"))) {
            sql.append(Utility.getDateCond("A", "CREATED_AT", map.get("BOOK_BEGIN_DATE").toString(), map.get("BOOK_END_DATE").toString()));
        }
        if (!StringUtils.isNullOrEmpty(map.get("OBLIGATED_CLOSE_DATE"))&&!StringUtils.isNullOrEmpty(map.get("END_DATE"))) {
            sql.append(Utility.getDateCond("A", "OBLIGATED_CLOSE_DATE", map.get("BEGIN_DATE").toString(), map.get("END_DATE").toString()));
        }
        return DAOUtil.findAll(sql.toString(), list);
    }
    
    @Override
    public void performExecute(BookPartReleaseDTO bookPartReleaseDTO) throws ServiceBizException {
        if (StringUtils.isNullOrEmpty(bookPartReleaseDTO.getItemid())) {
            throw new ServiceBizException("请选择要预留的配件");
        }
        String[] itemid = bookPartReleaseDTO.getItemid().split(",");
        for(int i=0;i<itemid.length;i++){
            String id = itemid[i];
            TtBookingOrderPartPO bookingOrderPartPO = TtBookingOrderPartPO.findById(id);
            if (!StringUtils.isNullOrEmpty(bookingOrderPartPO)) {
                bookingOrderPartPO.set("IS_OBLIGATED",DictCodeConstants.DICT_IS_NO);
                bookingOrderPartPO.saveIt();
                TmPartStockPO partStockPO = TmPartStockPO.findByCompositeKeys(FrameworkUtil.getLoginInfo().getDealerCode(),bookingOrderPartPO.get("PART_NO"),bookingOrderPartPO.get("STORAGE_CODE"));
                if (!StringUtils.isNullOrEmpty(partStockPO)) {
                    double sub = Utility.sub(partStockPO.get("LOCKED_QUANTITY").toString(), bookingOrderPartPO.get("BOOKING_QUANTITY").toString());
                    partStockPO.set("LOCKED_QUANTITY",sub);
                    partStockPO.saveIt();
                }
            }
            TtPartObligatedItemPO itemPO = TtPartObligatedItemPO.findById(id);
            if (!StringUtils.isNullOrEmpty(itemPO)) {
                TtPartObligatedPO tpo = TtPartObligatedPO.findByCompositeKeys(FrameworkUtil.getLoginInfo().getDealerCode(),itemPO.get("OBLIGATED_NO"));
                if (tpo.get("OBLIGATED_CLOSE").equals(DictCodeConstants.DICT_IS_YES)) {
                    throw new ServiceBizException("预留单已关单，不能解预留！");
                }
                if (!itemPO.get("IS_OBLIGATED").equals(DictCodeConstants.DICT_IS_YES)) {
                    throw new ServiceBizException("已被解预留的配件不再解预留！");
                }
                itemPO.set("IS_OBLIGATED",DictCodeConstants.DICT_IS_NO);
                itemPO.saveIt();
                TmPartStockPO partStockPO = TmPartStockPO.findByCompositeKeys(FrameworkUtil.getLoginInfo().getDealerCode(),itemPO.get("PART_NO"),itemPO.get("STORAGE_CODE"));
                if (!StringUtils.isNullOrEmpty(partStockPO)) {
                    if (!StringUtils.isNullOrEmpty(partStockPO.get("BORROW_QUANTITY"))) {
                        if(Double.valueOf(partStockPO.get("LOCKED_QUANTITY").toString())>=Double.valueOf(partStockPO.get("BORROW_QUANTITY").toString())){
                            float lockQuantity = (float) Utility.sub(partStockPO.get("LOCKED_QUANTITY").toString(), partStockPO.get("BORROW_QUANTITY").toString());
                            partStockPO.set("LOCKED_QUANTITY",lockQuantity);
                        }else{ 
                            partStockPO.set("LOCKED_QUANTITY",partStockPO.get("LOCKED_QUANTITY"));
                        }
                        partStockPO.saveIt();
                    }
                }
                
            }
        }
    }
    
}
