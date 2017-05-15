
/** 
*Copyright 2017 Yonyou Corporation Ltd. All Rights Reserved.
* This software is published under the terms of the Yonyou Software
* License version 1.0, a copy of which has been included with this
* distribution in the LICENSE.txt file.
*
* @Project Name : dms.part
*
* @File name : LendStuffServiceImpl.java
*
* @Author : dingchaoyu
*
* @Date : 2017年5月10日
*
----------------------------------------------------------------------------------
*     Date       Who       Version     Comments
* 1. 2017年5月10日    dingchaoyu    1.0
*
*
*
*
----------------------------------------------------------------------------------
*/
	
package com.yonyou.dms.part.service.basedata;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.yonyou.dms.common.domains.PO.basedata.TmPartStockItemPO;
import com.yonyou.dms.common.domains.PO.basedata.TmPartStockPO;
import com.yonyou.dms.common.domains.PO.basedata.TtPartWorkshopItemPO;
import com.yonyou.dms.commonAS.domains.PO.basedata.PartFlowPO;
import com.yonyou.dms.framework.DAO.DAOUtil;
import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.framework.domain.LoginInfoDto;
import com.yonyou.dms.framework.util.FrameworkUtil;
import com.yonyou.dms.framework.util.Utility;
import com.yonyou.dms.framework.util.bean.ApplicationContextHelper;
import com.yonyou.dms.function.common.CommonConstants;
import com.yonyou.dms.function.common.DictCodeConstants;
import com.yonyou.dms.function.exception.ServiceBizException;
import com.yonyou.dms.function.utils.common.CommonUtils;
import com.yonyou.dms.function.utils.common.StringUtils;
import com.yonyou.dms.part.domains.DTO.basedata.LendStuffDTO;


/**
* TODO description
* @author dingchaoyu
* @date 2017年5月10日
*/
@SuppressWarnings("rawtypes")
@Service
public class LendStuffServiceImpl implements LendStuffService{
    final static ObjectMapper mapper = new ObjectMapper();
    @Override
    public List<Map> queryRepairType() throws ServiceBizException {
        LoginInfoDto loginInfo = ApplicationContextHelper.getBeanByType(LoginInfoDto.class);
        String groupCode = Utility.getGroupEntity(loginInfo.getDealerCode(), "TM_REPAIR_TYPE");
        StringBuilder sqlSb = new StringBuilder("select REPAIR_TYPE_CODE,REPAIR_TYPE_NAME,LABOUR_PRICE,COEFFICIENT,SORT_TYPE,CASE WHEN IS_SPRAY_LABOUR = 12781001 THEN 10571001 END AS IS_SPRAY_LABOUR,CASE WHEN IS_GUARANTEE = 12781001 THEN 10571001  END AS IS_GUARANTEE,DEALER_CODE, ");      
        sqlSb.append(" CASE WHEN IS_INSURANCE = 12781001 THEN 10571001 END AS IS_INSURANCE,CASE WHEN IS_RESERVED = 12781001 THEN 10571001  END AS IS_RESERVED, ");
        sqlSb.append("   CASE WHEN OEM_TAG = 12781001 THEN 10571001 END AS OEM_TAG,CASE WHEN IS_PRE_SERVICE = 12781001 THEN 10571001 END AS IS_PRE_SERVICE,CASE WHEN IS_VALID = 12781001 THEN 10571001 END AS IS_VALID  from  tm_repair_type where 1=1  and DEALER_CODE='"+groupCode+"'"  );
        return DAOUtil.findAll(sqlSb.toString(),null);
    }

    @Override
    public PageInfoDto query(Map map) throws ServiceBizException {
        String selLockerName = Utility.selLockerName("LOCK_USER", "TT_REPAIR_ORDER", "ro_no", map.get("roNo").toString());
        if (!StringUtils.isNullOrEmpty(selLockerName) && !FrameworkUtil.getLoginInfo().getUserId().toString().equals(selLockerName)) {
            throw new ServiceBizException("  工单"+map.get("roNo")+" 已被  "+selLockerName+"加锁！  ");
        }
        if (Utility.updateByLocker("TT_REPAIR_ORDER", FrameworkUtil.getLoginInfo().getUserId().toString(), "ro_no", map.get("roNo").toString(), "LOCK_USER")<=0) {
            throw new ServiceBizException("加锁失败!");
        }
        List<Object> list = new ArrayList<>();
        StringBuffer sql = new StringBuffer("select a.*,'U' as sign,b.down_tag from Tt_Part_Workshop_Item a ");
        sql.append(" left join tm_part_info b on a.DEALER_CODE = b.DEALER_CODE and a.part_no = b.part_no ");
        sql.append("  where 1=1  and a.DEALER_CODE=?  and a.D_Key=0  and a.Ro_No=? ");
        list.add(FrameworkUtil.getLoginInfo().getDealerCode());
        list.add(map.get("roNo"));
        return DAOUtil.pageQuery(sql.toString(), list);
    }

    @Override
    public PageInfoDto queryRepair(String id) throws ServiceBizException {
        List<Object> list = new ArrayList<>();
        StringBuffer sql = new StringBuffer("select * from Tt_Ro_Labour where ro_no=? and dealer_code=?");
        list.add(id);
        list.add(FrameworkUtil.getLoginInfo().getDealerCode());
        return DAOUtil.pageQuery(sql.toString(), list);
    }

    
    @Override
    public void partWorkshopItem(LendStuffDTO dto) throws ServiceBizException {
        String ItemRoNo = "";// 工单号
        String costAmountBeforeA = null;   //批次表入账前成本
        String costAmountBeforeB = null;   //库存表入账前成本
        String costAmountAfterA = null;    //批次表入账后成本
        String costAmountAfterB = null;    //库存表入账后成本
        List<Map> thisMonth = Utility.isFinishedThisMonth();
        String[] s =new String[1];
        s[0] = dto.getRoNo();
        Map findFirst = new HashMap<>();
        if (thisMonth.size()>0) {
            Utility.updateByUnLock("TT_REPAIR_ORDER", FrameworkUtil.getLoginInfo().getUserId().toString(), "ro_no", s, "LOCK_USER");
            throw new ServiceBizException("当前配件月报没有正确执行!");
        }
        List<Map> isFinished = Utility.getIsFinished();
        if (isFinished.size()>0 || isFinished.get(0).get("IS_EXECUTED").equals(DictCodeConstants.DICT_IS_YES)) {
            Utility.updateByUnLock("TT_REPAIR_ORDER", FrameworkUtil.getLoginInfo().getUserId().toString(), "ro_no", s, "LOCK_USER");
            throw new ServiceBizException("当前配件会计月报没有正确执行!");
        }
        List<Object> l = new ArrayList<>();
        StringBuffer q = new StringBuffer("select a.*,'U' as sign,b.down_tag from Tt_Part_Workshop_Item a ");
        q.append(" left join tm_part_info b on a.DEALER_CODE = b.DEALER_CODE and a.part_no = b.part_no ");
        q.append("  where 1=1  and a.DEALER_CODE=?  and a.D_Key=0  and a.Ro_No=? ");
        l.add(FrameworkUtil.getLoginInfo().getDealerCode());
        l.add(dto.getRoNo());
        List<Map> all = DAOUtil.findAll(q.toString(), l);
        if (all.size()>0) {
            findFirst = all.get(0);
        }
        List<Map> list = dto.getTables();
        for (int i=0;i<list.size();i++) {
            Map a = list.get(i);
            if ("A".equals(a.get("sign").toString())){   
                costAmountBeforeA = "0";  //批次表入账前成本
                costAmountBeforeB = "0";  //库存表入账前成本
                costAmountAfterA = "0";   //批次表入账后成本
                costAmountAfterB = "0";   //库存表入账后成本
                TtPartWorkshopItemPO item = new TtPartWorkshopItemPO();
                item.set("DEALER_CODE",FrameworkUtil.getLoginInfo().getDealerCode());
                item.set("BORROWER",a.get("BORROWER"));
                item.set("FINISHED_DATE",new Date(System.currentTimeMillis()));
                item.set("IS_FINISHED",DictCodeConstants.DICT_IS_YES);
                item.set("LEND_DATE",new Date(System.currentTimeMillis()));
                item.set("LEND_QUANTITY",a.get("LEND_QUANTITY"));
                if (all.size()>0) {
                    item.set("PART_SALES_AMOUNT",findFirst.get("PART_SALES_AMOUNT"));
                    item.set("PART_SALES_PRICE",findFirst.get("PART_SALES_PRICE"));
                    item.set("PART_COST_AMOUNT",findFirst.get("PART_COST_AMOUNT"));
                    item.set("PART_COST_PRICE",findFirst.get("PART_COST_PRICE"));
                }
                if (!StringUtils.isNullOrEmpty(a.get("PART_BATCH_NO"))) {
                    item.set("PART_BATCH_NO",a.get("PART_BATCH_NO"));
                }
                if (!StringUtils.isNullOrEmpty(a.get("UNIT_CODE"))) {
                    item.set("UNIT_CODE",a.get("UNIT_CODE"));
                }
                item.set("PART_NAME",a.get("PART_NAME"));
                item.set("PART_NO",a.get("PART_NO"));
                item.set("RO_NO",dto.getRoNo());
                item.set("STORAGE_CODE",a.get("STORAGE_CODE"));
                item.set("STORAGE_POSITION_CODE",a.get("STORAGE_POSITION_CODE"));
                item.saveIt();
                
                TmPartStockItemPO itemPOa = new TmPartStockItemPO();
                itemPOa.set("DEALER_CODE",FrameworkUtil.getLoginInfo().getDealerCode());
                itemPOa.set("D_KEY",CommonConstants.D_KEY);
                itemPOa.set("PART_NO",a.get("PART_NO"));
                itemPOa.set("STORAGE_CODE",a.get("STORAGE_CODE"));
                String sql = "select * from tm_part_stock_item where part_no=? and storage_code=?";
                List<Object> lists = new ArrayList<>();
                lists.add(a.get("PART_NO"));
                lists.add(a.get("STORAGE_CODE"));
                List<Map> findAll = DAOUtil.findAll(sql, lists);
                if (!StringUtils.isNullOrEmpty(findAll)){
                    Map itemPOb = null;
                    itemPOb = findAll.get(0);
                    if (!StringUtils.isNullOrEmpty(itemPOb.get("COST_AMOUNT"))){
                        costAmountBeforeA = itemPOb.get("COST_AMOUNT").toString();//批次表入帐前成本金额 
                    }
                }
                
                TmPartStockPO po = TmPartStockPO.findByCompositeKeys(FrameworkUtil.getLoginInfo().getDealerCode(),a.get("PART_NO"),a.get("STORAGE_CODE"));
                if (!StringUtils.isNullOrEmpty(po)){
                    if (!StringUtils.isNullOrEmpty(po.get("COST_AMOUNT"))){
                        costAmountBeforeB = po.get("COST_AMOUNT").toString(); //库存表入账前成本金额
                    }
                    
                }

                //更改配件库存信息表
                po.set("LEND_QUANTITY",Double.parseDouble(po.get("LEND_QUANTITY").toString())+Double.parseDouble(a.get("LEND_QUANTITY").toString()));
                if (Double.parseDouble(a.get("LEND_QUANTITY").toString()) > 0){
                    po.set("LAST_STOCK_OUT",new Date(System.currentTimeMillis()));
                }
                if (Double.parseDouble(a.get("LEND_QUANTITY").toString()) < 0){
                    po.set("LAST_STOCK_IN",new Date(System.currentTimeMillis()));
                }
                po.saveIt();
                if (!StringUtils.isNullOrEmpty(po)){
                    if (!StringUtils.isNullOrEmpty(po.get("COST_AMOUNT"))){
                        costAmountAfterB = po.get("COST_AMOUNT").toString(); //库存表入账后成本金额
                    }
                    
                }

                //更改配件批次信息
                TmPartStockItemPO ItemCon = TmPartStockItemPO.findByCompositeKeys(FrameworkUtil.getLoginInfo().getDealerCode(),a.get("PART_NO"),a.get("STORAGE_CODE"),a.get("PART_BATCH_NO"));
                if (!StringUtils.isNullOrEmpty(ItemCon)){
                    ItemCon.set("LEND_QUANTITY",Double.parseDouble(ItemCon.get("LEND_QUANTITY").toString())+Double.parseDouble(a.get("LEND_QUANTITY").toString()));
                    if (Double.parseDouble(ItemCon.get("LEND_QUANTITY").toString()) > 0){
                        ItemCon.set("LAST_STOCK_OUT",new Date(System.currentTimeMillis()));
                    }
                    if (Double.parseDouble(ItemCon.get("LEND_QUANTITY").toString()) < 0){
                        ItemCon.set("LAST_STOCK_IN",new Date(System.currentTimeMillis()));
                    }
                    ItemCon.saveIt();
                    if (!StringUtils.isNullOrEmpty(ItemCon.get("COST_AMOUNT")))
                    {
                        costAmountAfterA = ItemCon.get("COST_AMOUNT").toString();   //批次表入账后成本金额
                    }
                }

                //向配件流水账添加记录
                PartFlowPO flow = new PartFlowPO();
                
                flow.set("DEALER_CODE",FrameworkUtil.getLoginInfo().getDealerCode());
                try {
                    double amount = Utility.add("1",Utility.getPartRate(FrameworkUtil.getLoginInfo().getDealerCode()) );
                    String strAmount = Double.toString(amount);
                    if (all.size()>0) {
                        flow.set("IN_OUT_NET_AMOUNT",findFirst.get("PART_COST_AMOUNT")); // 不含税金额
                        flow.set("IN_OUT_NET_PRICE",findFirst.get("PART_COST_PRICE")); // 不含税价格
                        if (Double.parseDouble(a.get("LEND_QUANTITY").toString()) > 0){//车间借料出库
                            flow.set("COST_AMOUNT",findFirst.get("PART_COST_AMOUNT"));
                            flow.set("IN_OUT_TAXED_AMOUNT",Utility.mul(findFirst.get("PART_COST_AMOUNT").toString(), strAmount));
                        }
                        if (Double.parseDouble(a.get("LEND_QUANTITY").toString()) < 0){//车间退料
                            if (!StringUtils.isNullOrEmpty(findFirst.get("PART_COST_AMOUNT"))) {
                                flow.set("COST_AMOUNT",-Double.parseDouble(findFirst.get("PART_COST_AMOUNT").toString()));
                                flow.set("IN_OUT_TAXED_AMOUNT",-Utility.mul(findFirst.get("PART_COST_AMOUNT").toString(), strAmount));
                            }
                        }
                        if (!StringUtils.isNullOrEmpty(findFirst.get("PART_COST_PRICE"))) {
                            flow.set("IN_OUT_TAXED_PRICE",Utility.mul(findFirst.get("PART_COST_PRICE").toString(), strAmount));
                        }
                    }
                    flow.set("IN_OUT_TAG",DictCodeConstants.DICT_IS_YES);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                flow.set("IN_OUT_TYPE",DictCodeConstants.DICT_IN_OUT_TYPE_WORKSHOP_BORROW);

                flow.set("OPERATE_DATE",new Date(System.currentTimeMillis()));
                flow.set("OPERATOR",FrameworkUtil.getLoginInfo().getUserName());
                flow.set("PART_BATCH_NO",a.get("PART_BATCH_NO"));
                flow.set("PART_NAME",a.get("PART_NAME"));
                flow.set("PART_NO",a.get("PART_NO"));
                flow.set("SHEET_NO",dto.getRoNo());
                if (Double.parseDouble(a.get("LEND_QUANTITY").toString()) < 0) //如果为负数，则为入库
                    flow.set("STOCK_IN_QUANTITY",Double.parseDouble(a.get("LEND_QUANTITY").toString()));
                if (Double.parseDouble(a.get("LEND_QUANTITY").toString()) > 0)
                    flow.set("STOCK_OUT_QUANTITY",Double.parseDouble(a.get("LEND_QUANTITY").toString()));

                if (!StringUtils.isNullOrEmpty(ItemCon)){
                    flow.set("STOCK_QUANTITY",ItemCon.get("STOCK_QUANTITY"));
                }
                flow.set("STORAGE_CODE",a.get("STORAGE_CODE"));
                flow.set("LICENSE",dto.getLicense());
                flow.set("CUSTOMER_NAME",dto.getOwnerName());
                flow.set("COST_AMOUNT_BEFORE_A",costAmountBeforeA);
                flow.set("COST_AMOUNT_BEFORE_B",costAmountBeforeB);
                flow.set("COST_AMOUNT_AFTER_A",costAmountAfterA);
                flow.set("COST_AMOUNT_AFTER_B",costAmountAfterB);
                flow.saveIt();

            }

            if ("U".equals(a.get("sign").toString()))
            {
                TtPartWorkshopItemPO itemCon = TtPartWorkshopItemPO.findById(a.get("ITEM_ID"));
                itemCon.set("PART_NAME",a.get("PART_NAME"));
                itemCon.saveIt();   

            }
            
            ItemRoNo = dto.getRoNo(); 
        }
        List<Map> listcheck;
        try {
            listcheck = getNonOemPartListOut("TT_PART_WORKSHOP_ITEM","RO_NO", ItemRoNo);
            String errPart = "";
            if (listcheck != null && listcheck.size() > 0){
                for (Map map2 : listcheck) {
                    if (errPart.equals("")){
                        errPart = map2.get("PART_NO").toString();
                    }else{
                        errPart = errPart + ", "+map2.get("PART_NO").toString(); 
                    }                       
                }
                for (int i = 0;i<listcheck.size() ;i++ ){
                    listcheck.get(i);
                }
                
                if (!errPart.equals("")){
                    throw new ServiceBizException(errPart+" 非OEM配件不允许出OEM库,请重新操作!");
                }   
            }               
        } catch (Exception e) {
            e.printStackTrace();
        }
        Utility.updateByUnLock("TT_REPAIR_ORDER", FrameworkUtil.getLoginInfo().getUserId().toString(), "ro_no", s, "LOCK_USER");
    }
    public List<Map> getNonOemPartListOut(String sheetTable,String sheetName,String sheetNo) throws Exception{
        if (!Utility.getIsOEMPartOutCheck().equals(DictCodeConstants.DICT_IS_YES)) {
            return null;
        }       
        String sql = null;
        if (sheetTable == "TT_SALES_PART_ITEM" || "TT_SALES_PART_ITEM".equals(sheetTable)){
            String fieldName = "PART_QUANTITY";
            sql = "select AA.PART_NO,AA.STORAGE_CODE,SUM(AA.PART_QUANTITY) as PART_QUANTITY FROM" + 
            " (select A.PART_NO,A.PART_NAME,A.STORAGE_CODE," +
            "   A." +fieldName+" As PART_QUANTITY "+            
            " from  "+sheetTable+" A " +
            " left join TM_PART_INFO B on (A.part_NO = B.PART_NO AND A.DEALER_CODE = B.DEALER_CODE) " +
            " WHERE A.DEALER_CODE = '"+FrameworkUtil.getLoginInfo().getDealerCode()+"' " +
            " AND B.DOWN_TAG =  "+DictCodeConstants.DICT_IS_NO+" " +
            " AND A.STORAGE_CODE ='OEMK'"+
            " AND A."+sheetName+" = '"+sheetNo+"'"+ 
            " ) AA group by AA.PART_NO,AA.STORAGE_CODE having SUM(AA.PART_QUANTITY) <> 0  ";
        }
        else{
            sql = " select A.PART_NO,A.PART_NAME,A.STORAGE_CODE from  "+sheetTable+" A " +
            " left join TM_PART_INFO B on (A.part_NO = B.PART_NO AND A.DEALER_CODE = B.DEALER_CODE) " +
            " WHERE A.DEALER_CODE = '"+FrameworkUtil.getLoginInfo().getDealerCode()+"' " +
            " AND B.DOWN_TAG =  "+DictCodeConstants.DICT_IS_NO+" " +
            " AND A.STORAGE_CODE ='OEMK'"+
            " AND A."+sheetName+" = '"+sheetNo+"'";
        }
        return DAOUtil.findAll(sql, null);
    }

    @Override
    public PageInfoDto queryStock(Map dto) throws ServiceBizException {
        StringBuffer sql = new StringBuffer();
        List<Object> list = new ArrayList<>();
        PageInfoDto lists = new PageInfoDto();
        String[] split = dto.get("LEND_QUANTITY").toString().split(",");
        String[] split1 = dto.get("PART_NO").toString().split(",");
        String[] split2 = dto.get("STORAGE_CODE").toString().split(",");
        for (int i=0;i<split.length;i++) {
            TmPartStockPO po = TmPartStockPO.findByCompositeKeys(FrameworkUtil.getLoginInfo().getDealerCode(),split1[i],split2[i]);
            if (StringUtils.isNullOrEmpty(po)) {
                throw new ServiceBizException(split2[i]+"仓库中没有配件"+split1[i]+"的配件信息!");
            }
            sql.append(" select ("+split[i]+") as PART_QUANTITY,DEALER_CODE, PART_NO, STORAGE_CODE, STORAGE_POSITION_CODE, PART_NAME, ");
            sql.append(" SPELL_CODE, PART_GROUP_CODE, UNIT_CODE, STOCK_QUANTITY, SALES_PRICE,");
            sql.append(" STOCK_QUANTITY AS STOCK_QUANTITY_STOCK,COST_PRICE,COST_AMOUNT");
            sql.append(" CLAIM_PRICE, LIMIT_PRICE, LATEST_PRICE,  MAX_STOCK,");
            sql.append(" MIN_STOCK, BORROW_QUANTITY, LEND_QUANTITY, LOCKED_QUANTITY, PART_STATUS,");
            sql.append(" LAST_STOCK_IN, LAST_STOCK_OUT, FOUND_DATE, REMARK, D_KEY, CREATED_BY,");
            sql.append(" (STOCK_QUANTITY+BORROW_QUANTITY-LEND_QUANTITY) AS USEABLE_STOCK,");
            sql.append(" ( CASE WHEN (STOCK_QUANTITY+BORROW_QUANTITY-LEND_QUANTITY-("+split[i]+")-LOCKED_QUANTITY)<-0.00000001 THEN");
            sql.append("  12781001 ELSE 12781002 END )   AS ISNEGATIVE,(CASE WHEN ( (STOCK_QUANTITY*COST_PRICE<>COST_AMOUNT");
            sql.append(" AND (STOCK_QUANTITY=0 OR COST_AMOUNT=0)) OR COST_PRICE=0)  THEN 12781001 ELSE 12781002 END)  AS ISNORMAL");
            sql.append(" from  TM_PART_STOCK  WHERE DEALER_CODE = ?  AND D_KEY = ? ");
            list.add(FrameworkUtil.getLoginInfo().getDealerCode());
            list.add(CommonConstants.D_KEY);
            if (!StringUtils.isNullOrEmpty(split1[i])) {
                sql.append(" and PART_NO =  ?  ");
                list.add(split1[i]);
            }
            if (!StringUtils.isNullOrEmpty(split2[i])) {
                sql.append(" and STORAGE_CODE = ? ");
                list.add(split2[i]);
            }
            sql.append(" AND ( ( (STOCK_QUANTITY+BORROW_QUANTITY-LEND_QUANTITY-("+split[i]+")-LOCKED_QUANTITY<-0.00000001)");
            sql.append(" OR ( (STOCK_QUANTITY*COST_PRICE<>COST_AMOUNT) AND (STOCK_QUANTITY=0 OR COST_AMOUNT=0 ) )) OR  COST_PRICE=0 ) ");
            PageInfoDto item = DAOUtil.pageQuery(sql.toString(), list);
            if(!CommonUtils.isNullOrEmpty(item.getRows())){
                lists.setTotal(item.getTotal()+lists.getTotal());
                lists.getRows().addAll(item.getRows());
            }
            sql = new StringBuffer();
            list = new ArrayList<>();
        }
        return lists;
    }

    @Override
    public String queryStocks(LendStuffDTO dto) throws ServiceBizException {
        List<Map> list2 = dto.getTables();
        List<Map> findAll = new ArrayList<>();
        for (Map map : list2) {
            StringBuffer sql = new StringBuffer();
            List<Object> list = new ArrayList<>();
            TmPartStockPO po = TmPartStockPO.findByCompositeKeys(FrameworkUtil.getLoginInfo().getDealerCode(),map.get("PART_NO"),map.get("STORAGE_CODE"));
            if (StringUtils.isNullOrEmpty(po)) {
                throw new ServiceBizException(map.get("STORAGE_CODE")+"仓库中没有配件"+map.get("PART_NO")+"的配件信息!");
            }
            sql.append(" select ("+map.get("LEND_QUANTITY")+") as PART_QUANTITY,DEALER_CODE, PART_NO, STORAGE_CODE, STORAGE_POSITION_CODE, PART_NAME, ");
            sql.append(" SPELL_CODE, PART_GROUP_CODE, UNIT_CODE, STOCK_QUANTITY, SALES_PRICE,");
            sql.append(" STOCK_QUANTITY AS STOCK_QUANTITY_STOCK,COST_PRICE,COST_AMOUNT");
            sql.append(" CLAIM_PRICE, LIMIT_PRICE, LATEST_PRICE,  MAX_STOCK,");
            sql.append(" MIN_STOCK, BORROW_QUANTITY, LEND_QUANTITY, LOCKED_QUANTITY, PART_STATUS,");
            sql.append(" LAST_STOCK_IN, LAST_STOCK_OUT, FOUND_DATE, REMARK, D_KEY, CREATED_BY,");
            sql.append(" (STOCK_QUANTITY+BORROW_QUANTITY-LEND_QUANTITY) AS USEABLE_STOCK,");
            sql.append(" ( CASE WHEN (STOCK_QUANTITY+BORROW_QUANTITY-LEND_QUANTITY-("+map.get("LEND_QUANTITY")+")-LOCKED_QUANTITY)<-0.00000001 THEN");
            sql.append("  12781001 ELSE 12781002 END )   AS ISNEGATIVE,(CASE WHEN ( (STOCK_QUANTITY*COST_PRICE<>COST_AMOUNT");
            sql.append(" AND (STOCK_QUANTITY=0 OR COST_AMOUNT=0)) OR COST_PRICE=0)  THEN 12781001 ELSE 12781002 END)  AS ISNORMAL");
            sql.append(" from  TM_PART_STOCK  WHERE DEALER_CODE = ?  AND D_KEY = ? ");
            list.add(FrameworkUtil.getLoginInfo().getDealerCode());
            list.add(CommonConstants.D_KEY);
            if (!StringUtils.isNullOrEmpty(map.get("PART_NO"))) {
                sql.append(" and PART_NO =  ?  ");
                list.add(map.get("PART_NO"));
            }
            if (!StringUtils.isNullOrEmpty(map.get("STORAGE_CODE"))) {
                sql.append(" and STORAGE_CODE = ? ");
                list.add(map.get("STORAGE_CODE"));
            }
            sql.append(" AND ( ( (STOCK_QUANTITY+BORROW_QUANTITY-LEND_QUANTITY-("+map.get("LEND_QUANTITY")+")-LOCKED_QUANTITY<-0.00000001)");
            sql.append(" OR ( (STOCK_QUANTITY*COST_PRICE<>COST_AMOUNT) AND (STOCK_QUANTITY=0 OR COST_AMOUNT=0 ) )) OR  COST_PRICE=0 ) ");
            findAll = DAOUtil.findAll(sql.toString(), list);
        }
        if (findAll.size()>0) {
            return "1";
        }else{
            return "0";
        }
    }
}
