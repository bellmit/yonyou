
/** 
*Copyright 2017 Yonyou Corporation Ltd. All Rights Reserved.
* This software is published under the terms of the Yonyou Software
* License version 1.0, a copy of which has been included with this
* distribution in the LICENSE.txt file.
*
* @Project Name : dms.part
*
* @File name : BookPartServiceImpl.java
*
* @Author : dingchaoyu
*
* @Date : 2017年4月19日
*
----------------------------------------------------------------------------------
*     Date       Who       Version     Comments
* 1. 2017年4月19日    dingchaoyu    1.0
*
*
*
*
----------------------------------------------------------------------------------
*/
	
package com.yonyou.dms.part.service.basedata;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.javalite.activejdbc.Base;
import org.javalite.activejdbc.Model;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yonyou.dms.common.domains.PO.basedata.TmPartInfoPO;
import com.yonyou.dms.common.domains.PO.basedata.TmPartStockPO;
import com.yonyou.dms.common.domains.PO.basedata.TtPartObligatedItemPO;
import com.yonyou.dms.common.domains.PO.basedata.TtPartObligatedPO;
import com.yonyou.dms.framework.DAO.DAOUtil;
import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.framework.service.CommonNoService;
import com.yonyou.dms.framework.util.FrameworkUtil;
import com.yonyou.dms.framework.util.Utility;
import com.yonyou.dms.function.common.CommonConstants;
import com.yonyou.dms.function.common.DictCodeConstants;
import com.yonyou.dms.function.exception.ServiceBizException;
import com.yonyou.dms.function.utils.common.StringUtils;
import com.yonyou.dms.part.domains.DTO.basedata.BookPartDTO;


/**
* TODO description
* @author dingchaoyu
* @date 2017年4月19日
*/
@Service
public class BookPartServiceImpl implements BookPartService{
    
    @Autowired
    private CommonNoService BookPartServiceImpl;

    @Override
    public PageInfoDto queryPartObligated(Map map) throws ServiceBizException {
        List<Object> list = new ArrayList<>();
        StringBuffer sb = new StringBuffer(); 
        sb.append("SELECT A.*,B.RO_STATUS,B.SERVICE_ADVISOR,B.DELIVERER,B.DELIVERER_PHONE,B.DELIVERER_MOBILE ");
        sb.append("FROM TT_PART_OBLIGATED A LEFT JOIN TT_REPAIR_ORDER B ");
        sb.append("ON A.DEALER_CODE=B.DEALER_CODE AND A.D_KEY=B.D_KEY AND A.SHEET_NO=B.RO_NO WHERE A.DEALER_CODE="+FrameworkUtil.getLoginInfo().getDealerCode()+" ");
        sb.append("AND A.D_KEY="+CommonConstants.D_KEY+" AND A.OBLIGATED_CLOSE="+DictCodeConstants.DICT_IS_NO+" ");
        if (!StringUtils.isNullOrEmpty(map.get("OBLIGATED_NO"))) {
           sb.append(" and A.OBLIGATED_NO like ?");
           list.add("%"+map.get("OBLIGATED_NO")+"%");
        }
        if (!StringUtils.isNullOrEmpty(map.get("RO_NO"))) {
            sb.append(" and B.RO_NO like ?");
            list.add("%"+map.get("RO_NO")+"%");
         }
        if (!StringUtils.isNullOrEmpty(map.get("OWNER_NAME"))) {
            sb.append(" and A.OWNER_NAME like ?");
            list.add("%"+map.get("OWNER_NAME")+"%");
         }
        if (!StringUtils.isNullOrEmpty(map.get("LICENSE"))) {
            sb.append(" and A.LICENSE like ?");
            list.add("%"+map.get("LICENSE")+"%");
         }
        PageInfoDto query = DAOUtil.pageQuery(sb.toString(), list);
        return query;
    }

    @Override
    public PageInfoDto queryRepairOrderBy(Map map) throws ServiceBizException {
        List<Object> list = new ArrayList<>();
        StringBuffer sb = new StringBuffer();
        sb.append("SELECT * FROM TT_REPAIR_ORDER WHERE DEALER_CODE=? ");
        sb.append("AND D_KEY=? ");
        sb.append("AND RO_STATUS=? ");
        list.add(FrameworkUtil.getLoginInfo().getDealerCode());
        list.add(CommonConstants.D_KEY);
        list.add(DictCodeConstants.DICT_RO_STATUS_TYPE_ON_REPAIR);
        if (!StringUtils.isNullOrEmpty(map.get("RO_NO"))) {
            sb.append(" and RO_NO like ?");
            list.add("%"+map.get("RO_NO")+"%");
         }
        if (!StringUtils.isNullOrEmpty(map.get("VIN"))) {
            sb.append(" and VIN like ?");
            list.add("%"+map.get("VIN")+"%");
         }
        PageInfoDto query = DAOUtil.pageQuery(sb.toString(), list);
        return query;
    }

    @Override
    public PageInfoDto queryPartObligatedItem(String id) throws ServiceBizException {
        List<Object> list = new ArrayList<>();
        StringBuffer sb = new StringBuffer();
        sb.append("SELECT  A.DEALER_CODE,A.ITEM_ID,A.OBLIGATED_NO,A.CREATED_BY,A.CREATED_AT AS BOOKING_DATE,A.UPDATED_AT,A.UPDATED_BY,A.STORAGE_CODE,A.PART_NO,A.PART_NAME,B.STORAGE_POSITION_CODE,A.QUANTITY,C.LIMIT_PRICE,C.DOWN_TAG, ");
        sb.append("A.IS_OBLIGATED,B.UNIT_CODE,B.COST_PRICE,B.COST_PRICE*A.QUANTITY as COST_AMOUNT,(B.STOCK_QUANTITY+B.BORROW_QUANTITY-B.LEND_QUANTITY-B.LOCKED_QUANTITY) AS USABLE_STOCK, ");
        sb.append("A.PART_BATCH_NO  FROM TT_PART_OBLIGATED_ITEM A  LEFT JOIN TM_PART_STOCK B on A.PART_NO=B.PART_NO and a.STORAGE_CODE=b.Storage_code ");
        sb.append("AND A.DEALER_CODE=B.DEALER_CODE AND A.D_KEY=B.D_KEY ");
        sb.append("LEFT JOIN TM_PART_INFO C ON (C.PART_NO = A.PART_NO AND C.DEALER_CODE = A.DEALER_CODE) ");
        sb.append("where A.DEALER_CODE=? AND A.D_KEY=?");
        list.add(FrameworkUtil.getLoginInfo().getDealerCode());
        list.add(CommonConstants.D_KEY);
        if (!StringUtils.isNullOrEmpty(id)) {
            sb.append(" and A.OBLIGATED_NO like ?");
            list.add("%"+id+"%");
         }
        PageInfoDto query = DAOUtil.pageQuery(sb.toString(), list);
        return query;
    }
    
    @Override
    public PageInfoDto queryStockInfo(Map map) throws ServiceBizException {
        List<Object> list = new ArrayList<>();
        StringBuffer sb = new StringBuffer();
        sb.append("select A.NODE_PRICE,A.INSURANCE_PRICE,A.DEALER_CODE,A.PART_NO,TS.CJ_TAG,A.STORAGE_CODE,A.PART_BATCH_NO,A.STORAGE_POSITION_CODE,A.PART_NAME,");
        sb.append("A.SPELL_CODE, A.UNIT_CODE,A.STOCK_QUANTITY, A.SALES_PRICE, A.CLAIM_PRICE, B.LIMIT_PRICE, B.INSTRUCT_PRICE, A.LATEST_PRICE,TS.STORAGE_NAME,");
        sb.append("ROUND(A.COST_PRICE,4) AS COST_PRICE, A.COST_AMOUNT, A.BORROW_QUANTITY, A.PART_STATUS,C.LOCKED_QUANTITY, A.LEND_QUANTITY,B.UNIT_NAME,");
        sb.append("A.LAST_STOCK_IN, A.LAST_STOCK_OUT, A.REMARK, A.CREATED_BY, A.CREATED_AT, A.UPDATED_BY, A.UPDATED_AT,B.DOWN_TAG,B.OPTION_NO,D.OPTION_STOCK,");
        sb.append("A.VER, A.PART_GROUP_CODE,C.PART_MODEL_GROUP_CODE_SET,A.STOCK_QUANTITY+A.BORROW_QUANTITY-A.LEND_QUANTITY-C.LOCKED_QUANTITY USEABLE_STOCK,");
        sb.append("(A.STOCK_QUANTITY + A.BORROW_QUANTITY - IFNULL(A.LEND_QUANTITY,0)) AS USEABLE_QUANTITY,B.MIN_LIMIT_PRICE,C.MAX_STOCK,C.MIN_STOCK,");
        sb.append("CASE WHEN (SELECT 1 FROM TM_MAINTAIN_PART CC WHERE  CC.PART_NO = B.PART_NO ");
        sb.append("AND CC.DEALER_CODE = B.DEALER_CODE ) >0 THEN "+DictCodeConstants.DICT_IS_YES+" ELSE "+DictCodeConstants.DICT_IS_NO);
        sb.append(" END  AS IS_MAINTAIN ");
        sb.append("from TM_PART_STOCK_ITEM  A  LEFT OUTER JOIN ("+CommonConstants.VM_PART_INFO+") B ON (A.DEALER_CODE = B.DEALER_CODE AND A.PART_NO = B.PART_NO ) ");
        sb.append("LEFT JOIN TM_PART_STOCK C ON A.DEALER_CODE=C.DEALER_CODE AND A.PART_NO=C.PART_NO AND A.STORAGE_CODE=C.STORAGE_CODE LEFT JOIN (select DEALER_CODE,part_no,sum(C.STOCK_QUANTITY) OPTION_STOCK FROM TM_PART_STOCK C GROUP BY DEALER_CODE,PART_NO) D ON C.DEALER_CODE=D.DEALER_CODE AND C.PART_NO=D.PART_NO AND B.OPTION_NO=D.PART_NO ");
        sb.append("LEFT JOIN TM_STORAGE TS ON  A.DEALER_CODE=TS.DEALER_CODE AND A.STORAGE_CODE=TS.STORAGE_CODE WHERE A.PART_STATUS<>"+DictCodeConstants.DICT_IS_YES);
        sb.append(" AND A.DEALER_CODE ='"+FrameworkUtil.getLoginInfo().getDealerCode()+"' ");
        sb.append("AND C.D_KEY = "+CommonConstants.D_KEY);
        if (!StringUtils.isNullOrEmpty(map.get("STORAGE_CODE"))) {
            sb.append(" and A.STORAGE_CODE like ?");
            list.add("%"+map.get("STORAGE_CODE")+"%");
         }
        if (!StringUtils.isNullOrEmpty(map.get("brand"))) {
            sb.append(" and B.BRAND like ?");
            list.add("%"+map.get("brand")+"%");
         }
        if (!StringUtils.isNullOrEmpty(map.get("PART_NO"))) {
            sb.append(" and A.PART_NO like ?");
            list.add("%"+map.get("PART_NO")+"%");
         }
        if (!StringUtils.isNullOrEmpty(map.get("SPELL_CODE"))) {
            sb.append(" and A.SPELL_CODE like ?");
            list.add("%"+map.get("SPELL_CODE")+"%");
         }
        if (!StringUtils.isNullOrEmpty(map.get("PART_NAME"))) {
            sb.append(" and A.PART_NAME like ?");
            list.add("%"+map.get("PART_NAME")+"%");
         }
        if (!StringUtils.isNullOrEmpty(map.get("PART_GROUP_CODE"))) {
            sb.append(" and A.PART_GROUP_CODE like ?");
            list.add("%"+map.get("PART_GROUP_CODE")+"%");
         }
        if (!StringUtils.isNullOrEmpty(map.get("STORAGE_POSITION_CODE"))) {
            sb.append(" and A.STORAGE_POSITION_CODE like ?");
            list.add("%"+map.get("STORAGE_POSITION_CODE")+"%");
         }
        if (!StringUtils.isNullOrEmpty(map.get("PART_MODEL_GROUP_CODE_SET"))) {
            sb.append(" and C.PART_MODEL_GROUP_CODE_SET like ?");
            list.add("%"+map.get("PART_MODEL_GROUP_CODE_SET")+"%");
         }
        if (!StringUtils.isNullOrEmpty(map.get("isCheck"))) {
            sb.append(" and (A.STOCK_QUANTITY + A.BORROW_QUANTITY - A.LEND_QUANTITY -C.LOCKED_QUANTITY)  > 0 ");
         }
        if (!StringUtils.isNullOrEmpty(map.get("isSalePriceBigger"))) {
            sb.append(" and A.SALES_PRICE > 0 ");
            list.add("%"+map.get("")+"%");
         }
        if (!StringUtils.isNullOrEmpty(map.get("REMARK"))) {
            sb.append(" and A.REMARK like ?");
            list.add("%"+map.get("REMARK")+"%");
         }
        PageInfoDto query = DAOUtil.pageQuery(sb.toString(), list);
        return query;
    }
    
    @Override
    public PageInfoDto queryPartInfo(String id,String ids) throws ServiceBizException {
        List<Object> list = new ArrayList<>();
        StringBuffer sb = new StringBuffer();
        sb.append("SELECT '' as POS_CODE,'' as POS_NAME,A.PART_MODEL_GROUP_CODE_SET, ");
        sb.append("0 as PART_QUANTITY,  A.DEALER_CODE, A.PART_NO, A.STORAGE_CODE, A.STORAGE_POSITION_CODE, A.PART_NAME, ");
        sb.append("A.SPELL_CODE, A.UNIT_CODE, A.STOCK_QUANTITY, A.SALES_PRICE, A.CLAIM_PRICE, A.PART_GROUP_CODE, ");
        sb.append("B.LIMIT_PRICE, A.LATEST_PRICE, A.COST_PRICE AS COST_PRICE, A.COST_AMOUNT, A.MAX_STOCK,B.DOWN_TAG, ");
        sb.append("A.BORROW_QUANTITY, A.LEND_QUANTITY, A.LOCKED_QUANTITY, A.LAST_STOCK_IN, A.PART_STATUS, ");
        sb.append("A.LAST_STOCK_OUT, A.REMARK, A.CREATED_BY, A.CREATED_AT, A.UPDATED_BY, A.UPDATED_AT, A.VER, A.MIN_STOCK, ");
        sb.append("B.OPTION_NO, A.NODE_PRICE, B.PLAN_PRICE, B.OEM_LIMIT_PRICE, B.URGENT_PRICE, ");
        sb.append("B.INSTRUCT_PRICE,A.INSURANCE_PRICE, D.OPTION_STOCK, (A.STOCK_QUANTITY + A.BORROW_QUANTITY - A.LEND_QUANTITY - A.LOCKED_QUANTITY) AS USEABLE_STOCK,B.IS_BACK,B.PART_INFIX, ");
        sb.append("B.MIN_LIMIT_PRICE FROM TM_PART_STOCK A  LEFT OUTER JOIN ("+CommonConstants.VM_PART_INFO+") B ");
        sb.append("ON ( A.DEALER_CODE = B.DEALER_CODE AND A.PART_NO= B.PART_NO ) ");
        sb.append("LEFT OUTER JOIN (select DEALER_CODE, part_no, sum(C.STOCK_QUANTITY) AS OPTION_STOCK FROM TM_PART_STOCK C GROUP BY DEALER_CODE,PART_NO ) D ");
        sb.append("ON ( A.DEALER_CODE = D.DEALER_CODE AND A.PART_NO= D.PART_NO AND B.OPTION_NO = D.PART_NO ) ");
        sb.append("WHERE A.DEALER_CODE = '" + FrameworkUtil.getLoginInfo().getDealerCode() + "' " + " AND A.D_KEY = "+CommonConstants.D_KEY);
        if (!StringUtils.isNullOrEmpty(ids)) {
            sb.append(" and A.STORAGE_CODE like ?");
            list.add("%"+ids+"%");
         }
        if (!StringUtils.isNullOrEmpty(id)) {
            sb.append(" and A.PART_NO like ?");
            list.add("%"+id+"%");
         }
        PageInfoDto query = DAOUtil.pageQuery(sb.toString(), list);
        return query;
    }
    
    @Override
    public Map queryPartInfos(Map map) throws ServiceBizException {
        List<Object> list = new ArrayList<>();
        StringBuffer sb = new StringBuffer();
        sb.append("SELECT '' as POS_CODE,'' as POS_NAME,A.PART_MODEL_GROUP_CODE_SET, ");
        sb.append("0 as PART_QUANTITY,  A.DEALER_CODE, A.PART_NO, A.STORAGE_CODE, A.STORAGE_POSITION_CODE, A.PART_NAME, ");
        sb.append("A.SPELL_CODE, A.UNIT_CODE, A.STOCK_QUANTITY, A.SALES_PRICE, A.CLAIM_PRICE, A.PART_GROUP_CODE, ");
        sb.append("B.LIMIT_PRICE, A.LATEST_PRICE, A.COST_PRICE AS COST_PRICE, A.COST_AMOUNT, A.MAX_STOCK,B.DOWN_TAG, ");
        sb.append("A.BORROW_QUANTITY, A.LEND_QUANTITY, A.LOCKED_QUANTITY, A.LAST_STOCK_IN, A.PART_STATUS, ");
        sb.append("A.LAST_STOCK_OUT, A.REMARK, A.CREATED_BY, A.CREATED_AT, A.UPDATED_BY, A.UPDATED_AT, A.VER, A.MIN_STOCK, ");
        sb.append("B.OPTION_NO, A.NODE_PRICE, B.PLAN_PRICE, B.OEM_LIMIT_PRICE, B.URGENT_PRICE, ");
        sb.append("B.INSTRUCT_PRICE,A.INSURANCE_PRICE, D.OPTION_STOCK, (A.STOCK_QUANTITY + A.BORROW_QUANTITY - A.LEND_QUANTITY - A.LOCKED_QUANTITY) AS USEABLE_STOCK,B.IS_BACK,B.PART_INFIX, ");
        sb.append("B.MIN_LIMIT_PRICE FROM TM_PART_STOCK A  LEFT OUTER JOIN ("+CommonConstants.VM_PART_INFO+") B ");
        sb.append("ON ( A.DEALER_CODE = B.DEALER_CODE AND A.PART_NO= B.PART_NO ) ");
        sb.append("LEFT OUTER JOIN (select DEALER_CODE, part_no, sum(C.STOCK_QUANTITY) AS OPTION_STOCK FROM TM_PART_STOCK C GROUP BY DEALER_CODE,PART_NO ) D ");
        sb.append("ON ( A.DEALER_CODE = D.DEALER_CODE AND A.PART_NO= D.PART_NO AND B.OPTION_NO = D.PART_NO ) ");
        sb.append("WHERE A.DEALER_CODE = '" + FrameworkUtil.getLoginInfo().getDealerCode() + "' " + " AND A.D_KEY = "+CommonConstants.D_KEY);
        if (!StringUtils.isNullOrEmpty(map.get("PART_NO"))) {
            sb.append(" and A.PART_NO like ?");
            list.add("%"+map.get("PART_NO")+"%");
         }
        return DAOUtil.findFirst(sb.toString(), list);
    }
    
    @Override
    public void performExecute(BookPartDTO bookPartDTO) throws ServiceBizException {
        List<Object> list = new ArrayList<>();
        List<Map> lists = bookPartDTO.getTables();
        for (Map map : lists) {
            map.get("IS_OBLIGATED");
            
            if ((DictCodeConstants.DICT_IS_YES).equals(map.get("IS_OBLIGATED"))){
                TmPartStockPO partStockPO =  TmPartStockPO.findByCompositeKeys(FrameworkUtil.getLoginInfo().getDealerCode(),map.get("PART_NO"),map.get("STORAGE_CODE"));
                partStockPO.set("LOCKED_QUANTITY",(Double.parseDouble(partStockPO.get("LOCKED_QUANTITY").toString())-Double.parseDouble(map.get("QUANTITY").toString())));
                partStockPO.saveIt();
            }
            if ((DictCodeConstants.DICT_IS_NO).equals(map.get("IS_OBLIGATED"))){
                String b ="select ITEM_ID from TT_PART_OBLIGATED_ITEM where OBLIGATED_NO=?";
                int exec = Base.exec(b,map.get("OBLIGATED_NO"));
                TtPartObligatedItemPO itemPO = TtPartObligatedItemPO.findById(exec);
                itemPO.set("QUANTITY",0);
                itemPO.saveIt();
            }
        }
        TtPartObligatedPO obligatedPO = TtPartObligatedPO.findByCompositeKeys(FrameworkUtil.getLoginInfo().getDealerCode(),bookPartDTO.getObligatedNo());
        obligatedPO.set("OBLIGATED_CLOSE",DictCodeConstants.DICT_IS_YES);
        obligatedPO.saveIt();
    }
    
    @Override
    public String performExecutes(BookPartDTO bookPartDTO) throws ServiceBizException {
        String no = "";
        String partno = "";
        String biaoji = "";
        String storagecode = "";
        String storagepositioncode = "";
        String quantity = "";
        String usablestock = "";
        double costprice = 0;
        double costamount = 0;
        String bookingdate = "";
        String isobigated = "";
        String partname = "";
        List<Map> list = bookPartDTO.getTables();
        if (list!=null&&list.size()>0) {
            for (Map map : list) {
                partno = map.get("PART_NO").toString();
                partname = map.get("PART_NAME").toString();
                if (map.get("biaoji")!=null) {
                    biaoji = map.get("biaoji").toString();
                }
                storagecode = map.get("STORAGE_CODE").toString();
                if (!StringUtils.isNullOrEmpty(map.get("STORAGE_POSITION_CODE"))) {
                    storagepositioncode = map.get("STORAGE_POSITION_CODE").toString();
                }
                quantity = map.get("QUANTITY").toString();
                usablestock = map.get("USEABLE_STOCK").toString();
                if (!StringUtils.isNullOrEmpty(map.get("COST_PRICE"))) {
                    costprice = Double.parseDouble(map.get("COST_PRICE").toString());
                }
                if (!StringUtils.isNullOrEmpty(map.get("COST_AMOUNT"))) {
                    costamount = Double.parseDouble(map.get("COST_AMOUNT").toString());
                }
                bookingdate = map.get("BOOKING_DATE").toString();
                isobigated = map.get("IS_OBLIGATED").toString();
                
                if ("A".equals(biaoji)) {
                    TmPartStockPO partStockPO = TmPartStockPO.findByCompositeKeys(FrameworkUtil.getLoginInfo().getDealerCode(),partno,storagecode);
                    int DeKey = partStockPO.getInteger("D_KEY");
                    if (DeKey==4) {
                        String msg = "配件"+partno+"已被删除不能预留";
                        throw new ServiceBizException(msg);
                    }
                    no = BookPartServiceImpl.getSystemOrderNo(CommonConstants.SRV_PJYLDH);
                    TtPartObligatedPO obligatedPO = new TtPartObligatedPO();
                    obligatedPO.set("DEALER_CODE",FrameworkUtil.getLoginInfo().getDealerCode());
                    obligatedPO.set("D_KEY",CommonConstants.D_KEY);
                    obligatedPO.set("REMARK",map.get("REMARK"));
                    obligatedPO.set("OBLIGATED_CLOSE",DictCodeConstants.DICT_IS_NO);
                    obligatedPO.set("OBLIGATED_OPERATOR",CommonConstants.SESSION_USERID);
                    obligatedPO.set("BALANCE_CLOSE_TIME",bookPartDTO.getObligatedCloseCate());
                    obligatedPO.set("COST_AMOUNT",costamount);
                    obligatedPO.set("QUANTITY",quantity);
                    obligatedPO.set("SHEET_NO",bookPartDTO.getRoNo());
                    obligatedPO.set("APPLICANT",bookPartDTO.getApplicant());
                    obligatedPO.set("APPLY_DATE",new Date());
                    obligatedPO.set("OBLIGATED_NO",no);
                    obligatedPO.set("OWNER_NAME",bookPartDTO.getOwnerName());
                    obligatedPO.set("OWNER_NO",bookPartDTO.getOwenNo());
                    obligatedPO.set("LICENSE",bookPartDTO.getLicense());
                    TtPartObligatedItemPO itemPO = new TtPartObligatedItemPO();
                    itemPO.set("DEALER_CODE",FrameworkUtil.getLoginInfo().getDealerCode());
                    itemPO.set("OBLIGATED_NO",no);
                    itemPO.set("PART_NAME",partname);
                    itemPO.set("PART_NO",partno);
                    itemPO.set("STORAGE_CODE",storagecode);
                    itemPO.set("QUANTITY",quantity);
                    itemPO.set("COST_PRICE",costprice);
                    itemPO.set("COST_AMOUNT",costamount);
                    itemPO.set("USABLE_STOCK",usablestock);
                    if (DictCodeConstants.DICT_IS_YES.equals(isobigated)){
                        itemPO.set("IS_OBLIGATED",DictCodeConstants.DICT_IS_YES);
                        StringBuffer q = new StringBuffer("select storage_code,part_no,stock_quantity,(stock_quantity+BORROW_QUANTITY-LEND_QUANTITY-LOCKED_QUANTITY) from tm_part_stock where dealer_code='")
                                .append(FrameworkUtil.getLoginInfo().getDealerCode())
                                .append("' and ((stock_quantity+BORROW_QUANTITY-LEND_QUANTITY-LOCKED_QUANTITY)-(")
                                .append(quantity+"))>=0 AND PART_NO='"+partno)
                                .append("' AND STORAGE_CODE='"+storagecode+"'");
                        List<Map> findAll = Base.findAll(q.toString());
                        if (findAll!=null &&findAll.size()>0) {
                            String a = "LOCKED_QUANTITY=(LOCKED_QUANTITY+"+quantity +") WHERE DEALER_CODE='"+FrameworkUtil.getLoginInfo().getDealerCode()+"' AND D_KEY="+CommonConstants.D_KEY+" AND PART_NO='" + partno+ "' AND STORAGE_CODE='" + storagecode + "'";
                            TmPartStockPO.updateAll(a);
                            obligatedPO.saveIt();
                            itemPO.saveIt();
                        }else{
                            String msg = storagecode+ "库配件" + partno + "可用库存小于预留数量！";
                            throw new ServiceBizException(msg);
                        }
                    }
                }else if ("U".equals(biaoji)) {
                    if (bookPartDTO.getObligatedNo()!=null) {
                        TtPartObligatedPO obligatedPO = TtPartObligatedPO.findByCompositeKeys(FrameworkUtil.getLoginInfo().getDealerCode(),map.get("OBLIGATED_NO"));
                        obligatedPO.set("APPLICANT",bookPartDTO.getApplicant());
                        obligatedPO.set("REMARK",bookPartDTO.getRemark());
                        obligatedPO.set("APPLY_DATE",new Date());
                        obligatedPO.set("SHEET_NO",bookPartDTO.getRoNo());
                        obligatedPO.set("OBLIGATED_CLOSE_DATE",bookPartDTO.getObligatedCloseCate());
                        obligatedPO.set("OWNER_NO",bookPartDTO.getOwenNo());
                        obligatedPO.set("OWNER_NAME",bookPartDTO.getOwnerName());
                        obligatedPO.set("LICENSE",bookPartDTO.getLicense());
                        obligatedPO.saveIt();
                        no = bookPartDTO.getObligatedNo();
                        TtPartObligatedItemPO itemPO = new TtPartObligatedItemPO();
                        itemPO.set("PART_NAME",partname);
                        itemPO.set("IS_OBLIGATED",DictCodeConstants.DICT_IS_YES);
                        itemPO.set("QUANTITY",quantity);
                        itemPO.saveIt();
                    }
                }
            }
        }else if ("D".equals(bookPartDTO.getBiaoji())) {
            TtPartObligatedItemPO keys = TtPartObligatedItemPO.findById(bookPartDTO.getItemId());
            if (DictCodeConstants.DICT_IS_NO.equals(keys.get("IS_OBLIGATED").toString())) {
                TtPartObligatedItemPO.delete("item_id=?", bookPartDTO.getItemId());
                TtPartObligatedPO po = TtPartObligatedPO.findByCompositeKeys(FrameworkUtil.getLoginInfo().getDealerCode(),bookPartDTO.getObligatedNo());
                po.saveIt();
            }
        }
        return no;
    }

    @Override
    public PageInfoDto queryPartsFromRepairOrder(String id) throws ServiceBizException {
        List<Object> list = new ArrayList<>();
        StringBuffer sql = new StringBuffer("SELECT A.PART_NO,A.STORAGE_CODE,B.STORAGE_POSITION_CODE,B.PART_NAME,A.DEALER_CODE,B.COST_PRICE,B.COST_AMOUNT,");
        sql.append("(B.STOCK_QUANTITY+B.BORROW_QUANTITY-B.LEND_QUANTITY) AS USABLE_STOCK,0 AS QUANTITY ");      
        sql.append("FROM (SELECT DISTINCT PART_NO,STORAGE_CODE,DEALER_CODE FROM TT_RO_REPAIR_PART WHERE DEALER_CODE=?");      
        sql.append(" AND D_KEY=? AND RO_NO=? ) A ");      
        sql.append("LEFT JOIN TM_PART_STOCK B ON A.DEALER_CODE=B.DEALER_CODE AND A.PART_NO=B.PART_NO AND A.STORAGE_CODE=B.STORAGE_CODE WHERE A.DEALER_CODE=?");  
        list.add(FrameworkUtil.getLoginInfo().getDealerCode());
        list.add(CommonConstants.D_KEY);
        list.add(id);
        list.add(FrameworkUtil.getLoginInfo().getDealerCode());
        return DAOUtil.pageQuery(sql.toString(), list);
    }

    @Override
    public PageInfoDto QueryPartsReplace(String id) throws ServiceBizException {
        List<Object> list = new ArrayList<>();
        String tpartNo = id;
        TmPartInfoPO infoPO = TmPartInfoPO.findByCompositeKeys(FrameworkUtil.getLoginInfo().getDealerCode(),id);
        while (true) {
            tpartNo = infoPO.getString("OPTION_NO");
            if (tpartNo == null || "".equals(tpartNo.trim())) {
                break;
            }
            TmPartInfoPO optionInfoPO = TmPartInfoPO.findByCompositeKeys(FrameworkUtil.getLoginInfo().getDealerCode(),tpartNo);
            if (optionInfoPO == null) {
                break;
            }
            list.add(tpartNo);
            if (infoPO.get("PART_NO").equals(optionInfoPO.getString("OPTION_NO"))) {
                break;
            }
            if (id.equals(optionInfoPO.getString("OPTION_NO"))) {
                break;
            }
            if (list.size() > 20) {
                break;
            }
            infoPO = optionInfoPO;
        }
        List<Object> rlist = new ArrayList();//这个list好像永远都是空的，老代码里拉过来的虽然没什么卵用，但是还是留着吧
        for (int m = 0; m < list.size(); m++) {
            boolean dupl = false;
            for (int k = 0; k < rlist.size(); k++) {
                if (list.get(m).equals(rlist.get(k))) {
                    dupl = true;
                    break;
                }
            }
            if (!dupl) {
                rlist.add(list.get(m));
            }
        }
        
        if (rlist != null && rlist.size() > 0) {
            PageInfoDto queryPartReplaceBy = queryPartReplaceBy(rlist);
            return queryPartReplaceBy;
        }else{
            return null;
        }
        
    }
    
    public PageInfoDto queryPartReplaceBy(List<Object> rlist){
        String value = Utility.getDefaultValue(String.valueOf(CommonConstants.DEFAULT_PARA_PART_RATE));
        StringBuffer sql = new StringBuffer("SELECT B.OPTION_NO,A.COST_PRICE*"+value+ "  AS NET_COST_PRICE,A.COST_AMOUNT*"+value);
        sql.append(" AS NET_COST_AMOUNT,B.ORI_PRO_CODE,A.IS_SUGGEST_ORDER,A.PART_MODEL_GROUP_CODE_SET,A.PART_MAIN_TYPE,A.PART_SPE_TYPE,");
        sql.append(" A.PART_MODEL_GROUP_CODE_SET,A.DEALER_CODE, A.PART_NO, A.FOUND_DATE, A.STORAGE_CODE, A.STORAGE_POSITION_CODE, A.PART_NAME, ");
        sql.append(" A.SPELL_CODE, A.PART_GROUP_CODE, A.UNIT_CODE, A.STOCK_QUANTITY, A.SALES_PRICE, ");
        sql.append(" A.CLAIM_PRICE, A.LIMIT_PRICE, A.LATEST_PRICE, A.COST_PRICE*1.0000 COST_PRICE, A.COST_AMOUNT*1.0000 COST_AMOUNT, A.MAX_STOCK,");
        sql.append(" A.MIN_STOCK, A.BORROW_QUANTITY, A.LEND_QUANTITY, A.LOCKED_QUANTITY, A.PART_STATUS, ");
        sql.append(" A.LAST_STOCK_IN, A.LAST_STOCK_OUT, A.REMARK, A.VER, ");
        sql.append(" A.JAN_MODULUS,A.FEB_MODULUS,A.MAR_MODULUS,A.APR_MODULUS,A.MAY_MODULUS,A.JUN_MODULUS,A.JUL_MODULUS,A.AUG_MODULUS,");
        sql.append(" A. SEP_MODULUS,A.OCT_MODULUS,A.NOV_MODULUS,A.DEC_MODULUS,A.MONTHLY_QTY_FORMULA,A.IS_SUGGEST_ORDER,");
        sql.append(" B.PRODUCTING_AREA, B.BRAND, B.MIN_PACKAGE,B.DOWN_TAG,B.FROM_ENTITY,b.part_infix,'' pos_code,'' pos_name, ");
        sql.append(" D.OPTION_STOCK,A.INSURANCE_PRICE,  B.INSTRUCT_PRICE,(A.STOCK_QUANTITY + A.BORROW_QUANTITY - A.LEND_QUANTITY");
        sql.append(" - A.LOCKED_QUANTITY) AS USEABLE_STOCK , (A.STOCK_QUANTITY + A.BORROW_QUANTITY - A.LEND_QUANTITY - A.LOCKED_QUANTITY) AS USEABLE_QUANTITY ");
        sql.append(" ,'' as PART_BATCH_NO,CASE WHEN ( SELECT 1 FROM TM_MAINTAIN_PART CC  WHERE CC.PART_NO = B.PART_NO ");
        sql.append(" AND CC.DEALER_CODE = B.DEALER_CODE) >0 THEN"+ DictCodeConstants.DICT_IS_YES+" ELSE "+DictCodeConstants.DICT_IS_NO+ " END AS IS_MAINTAIN ");
        sql.append(" ,A.INSURANCE_PRICE,A.NODE_PRICE FROM TM_PART_STOCK A  LEFT OUTER JOIN ("+CommonConstants.VM_PART_INFO+") B");
        sql.append(" ON ( A.DEALER_CODE = B.DEALER_CODE AND A.PART_NO= B.PART_NO  )");
        sql.append(" LEFT OUTER JOIN (select DEALER_CODE, part_no, sum(C.STOCK_QUANTITY) AS OPTION_STOCK");
        sql.append(" FROM TM_PART_STOCK C WHERE  c.part_status<>"+DictCodeConstants.DICT_IS_YES+"  AND  DEALER_CODE='"+FrameworkUtil.getLoginInfo().getDealerCode());
        sql.append(" ' AND D_KEY="+CommonConstants.D_KEY);
        sql.append(" GROUP BY ENTITY_CODE,PART_NO ) D ON ( A.ENTITY_CODE = D.ENTITY_CODE AND A.PART_NO= D.PART_NO AND B.PART_NO = D.PART_NO ) ");
        sql.append(" WHERE A.ENTITY_CODE = '" + FrameworkUtil.getLoginInfo().getDealerCode() + "' " + " AND A.D_KEY = "+CommonConstants.D_KEY);
        sql.append(" AND ( ");
        for (int i = 0; i < rlist.size(); i++)
        {
            sql.append(" A.PART_NO='" + rlist.get(i) + "' ");
            if (i < (rlist.size()-1))
            {
                sql.append(" OR ");
            }
        }
        sql.append(" ) ");
        return DAOUtil.pageQuery(sql.toString(), null);
    }

    @Override
    public void queryPart(Map map) throws ServiceBizException {
        TtPartObligatedItemPO keys = TtPartObligatedItemPO.findById(map.get("itemId"));
        if (DictCodeConstants.DICT_IS_YES.equals(keys.get("IS_OBLIGATED").toString())) {
            throw new ServiceBizException("该配件已经做过预留,不允许删除!");
        }
    }
    /**
     * 车间借料
     * @author wantao
     * @date 2017年5月5日
     * @param param
     * @return
      */
	@Override
	public List<Map> queryPartWorkshopItem(String roNo) throws ServiceBizException {
		StringBuffer sb=new StringBuffer("select a.DEALER_CODE,a.Ro_No,a.STORAGE_POSITION_CODE storagePositionCode,a.PART_NO partNo,a.PART_NAME partName,a.LEND_QUANTITY canLendNum,ts.STORAGE_NAME storageName,a.STORAGE_CODE,c.EMPLOYEE_NAME customerManager,b.UNIT_NAME unitName from tt_part_workshop_item a ")
				.append("left join tm_part_info b on a.DEALER_CODE = b.DEALER_CODE and a.part_no = b.part_no ")
				.append("left join TM_EMPLOYEE c on a.DEALER_CODE = c.DEALER_CODE and c.EMPLOYEE_NO = a.BORROWER ")
				.append("left join tm_store ts ON a.STORAGE_CODE = ts.STORAGE_CODE where 1=1 and a.D_Key=0 and a.DEALER_CODE='"+FrameworkUtil.getLoginInfo().getDealerCode()+"'"+" and a.Ro_No='"+roNo+"'");
		return DAOUtil.findAll(sb.toString(),new ArrayList<>());
	}
    /**
     * 车间借料
     * @author wantao
     * @date 2017年5月9日
     * @param param
     * @return
      */
	@SuppressWarnings("unchecked")
	@Override
	public Map<String,Object> queryPartWorkshopDetail(String roNo, String storageCode, String partNo) throws ServiceBizException {
		StringBuffer sb=new StringBuffer("select a.DEALER_CODE,a.Ro_No,a.STORAGE_POSITION_CODE storagePositionCode,a.PART_NO partNo,a.PART_NAME partName,a.LEND_QUANTITY canLendNum,ts.STORAGE_NAME storageName,a.BORROWER,a.STORAGE_CODE,c.EMPLOYEE_NAME customerManager,b.UNIT_NAME unitName from tt_part_workshop_item a ")
				.append("left join tm_part_info b on a.DEALER_CODE = b.DEALER_CODE and a.part_no = b.part_no ")
				.append("left join TM_EMPLOYEE c on a.DEALER_CODE = c.DEALER_CODE and c.EMPLOYEE_NO = a.BORROWER ")
				.append("left join tm_store ts ON a.STORAGE_CODE = ts.STORAGE_CODE where 1=1 and a.D_Key=0 and a.DEALER_CODE='"+FrameworkUtil.getLoginInfo().getDealerCode()+"'"+" and a.Ro_No='"+roNo+"'"+" and a.STORAGE_CODE='"+storageCode+"'"+" and a.PART_NO='"+partNo+"'");
		return DAOUtil.findFirst(sb.toString(),new ArrayList<>());
	}

	@Override
	public List<Map> queryPartInfoVehicle(String id, String ids) throws ServiceBizException {
        List<Object> list = new ArrayList<>();
        StringBuffer sb = new StringBuffer();
        sb.append("SELECT '' as POS_CODE,'' as POS_NAME,A.PART_MODEL_GROUP_CODE_SET, ");
        sb.append("0 as PART_QUANTITY,  A.DEALER_CODE, A.PART_NO, A.STORAGE_CODE, A.STORAGE_POSITION_CODE, A.PART_NAME, ");
        sb.append("A.SPELL_CODE, A.UNIT_CODE, A.STOCK_QUANTITY, A.SALES_PRICE, A.CLAIM_PRICE, A.PART_GROUP_CODE, ");
        sb.append("B.LIMIT_PRICE, A.LATEST_PRICE, A.COST_PRICE AS COST_PRICE, A.COST_AMOUNT, A.MAX_STOCK,B.DOWN_TAG, ");
        sb.append("A.BORROW_QUANTITY, A.LEND_QUANTITY, A.LOCKED_QUANTITY, A.LAST_STOCK_IN, A.PART_STATUS, ");
        sb.append("A.LAST_STOCK_OUT, A.REMARK, A.CREATED_BY, A.CREATED_AT, A.UPDATED_BY, A.UPDATED_AT, A.VER, A.MIN_STOCK, ");
        sb.append("B.OPTION_NO, A.NODE_PRICE, B.PLAN_PRICE, B.OEM_LIMIT_PRICE, B.URGENT_PRICE, ");
        sb.append("B.INSTRUCT_PRICE,A.INSURANCE_PRICE, D.OPTION_STOCK, (A.STOCK_QUANTITY + A.BORROW_QUANTITY - A.LEND_QUANTITY - A.LOCKED_QUANTITY) AS USEABLE_STOCK,B.IS_BACK,B.PART_INFIX, ");
        sb.append("B.MIN_LIMIT_PRICE FROM TM_PART_STOCK A  LEFT OUTER JOIN ("+CommonConstants.VM_PART_INFO+") B ");
        sb.append("ON ( A.DEALER_CODE = B.DEALER_CODE AND A.PART_NO= B.PART_NO ) ");
        sb.append("LEFT OUTER JOIN (select DEALER_CODE, part_no, sum(C.STOCK_QUANTITY) AS OPTION_STOCK FROM TM_PART_STOCK C GROUP BY DEALER_CODE,PART_NO ) D ");
        sb.append("ON ( A.DEALER_CODE = D.DEALER_CODE AND A.PART_NO= D.PART_NO AND B.OPTION_NO = D.PART_NO ) ");
        sb.append("WHERE A.DEALER_CODE = '" + FrameworkUtil.getLoginInfo().getDealerCode() + "' " + " AND A.D_KEY = "+CommonConstants.D_KEY);
        if (!StringUtils.isNullOrEmpty(ids)) {
            sb.append(" and A.STORAGE_CODE like ?");
            list.add("%"+ids+"%");
         }
        if (!StringUtils.isNullOrEmpty(id)) {
            sb.append(" and A.PART_NO like ?");
            list.add("%"+id+"%");
         }
		return DAOUtil.findAll(sb.toString(),list);
	}   
}
