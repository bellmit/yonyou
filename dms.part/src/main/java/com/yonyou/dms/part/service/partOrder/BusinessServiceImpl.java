
/** 
*Copyright 2017 Yonyou Corporation Ltd. All Rights Reserved.
* This software is published under the terms of the Yonyou Software
* License version 1.0, a copy of which has been included with this
* distribution in the LICENSE.txt file.
*
* @Project Name : dms.commonAS
*
* @File name : BusinessServiceImpl.java
*
* @Author : 
*
* @Date : 2017年5月11日
*
----------------------------------------------------------------------------------
*     Date       Who       Version     Comments
* 1. 2017年5月11日        1.0
*
*
*
*
----------------------------------------------------------------------------------
*/
	
package com.yonyou.dms.part.service.partOrder;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.javalite.activejdbc.Base;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yonyou.dms.common.domains.PO.basedata.PartDailyReportPO;
import com.yonyou.dms.common.domains.PO.basedata.RepairOrderPO;
import com.yonyou.dms.common.domains.PO.basedata.RoAddItemPO;
import com.yonyou.dms.common.domains.PO.basedata.RoManagePO;
import com.yonyou.dms.common.domains.PO.basedata.TtRoLabourPO;
import com.yonyou.dms.common.domains.PO.basedata.TtRoRepairPartPO;
import com.yonyou.dms.framework.DAO.DAOUtil;
import com.yonyou.dms.framework.service.CommonNoService;
import com.yonyou.dms.framework.util.FrameworkUtil;
import com.yonyou.dms.framework.util.Utility;
import com.yonyou.dms.function.common.CommonConstants;
import com.yonyou.dms.function.common.DictCodeConstants;
import com.yonyou.dms.function.exception.ServiceBizException;
import com.yonyou.dms.function.utils.common.CommonUtils;
import com.yonyou.dms.function.utils.common.StringUtils;
import com.yonyou.dms.part.domains.DTO.partOrder.TtPartPeriodReportDTO;

/**
* 
* @author 
* @date 2017年5月11日
*/
@Service
public class BusinessServiceImpl implements BusinessService{
    @Autowired
    private CommonNoService     commonNoService;
    // 定义日志接口
    private static final Logger logger = LoggerFactory.getLogger(StuffPriceAdjustService.class);
    
    public  void updateRoManage(String entityCode,String roNO,long userId) throws Exception {
        RoManagePO manageYes=new RoManagePO();
        
        RoManagePO manageNo=new  RoManagePO();
        
        List<RoManagePO> manageYesList=RoManagePO.findBySQL("select * from TT_RO_MANAGE where DEALER_CODE=? and RO_NO=? and D_KEY=? and IS_MANAGING=?",  FrameworkUtil.getLoginInfo().getDealerCode(),roNO,CommonConstants.D_KEY,Utility.getInt(DictCodeConstants.DICT_IS_YES));
       
        List<RoManagePO> manageNoList=RoManagePO.findBySQL("select * from  TT_RO_MANAGE where DEALER_CODE=? and RO_NO=? and D_KEY=? and IS_MANAGING=?",  FrameworkUtil.getLoginInfo().getDealerCode(),roNO,CommonConstants.D_KEY,Utility.getInt(DictCodeConstants.DICT_IS_NO));
        
        Map<String,RoManagePO> manageYesMap=new HashMap<String,RoManagePO> ();
        Map<String,RoManagePO> manageNoMap=new HashMap<String,RoManagePO> ();
        if(manageYesList!=null){
            for(int i=0;i<manageYesList.size();i++){
                manageYes=manageYesList.get(i);
                manageYesMap.put(manageYes.getString("MANAGE_SORT_CODE"), manageYes);
            }
        }
        if(manageNoList!=null){
            for(int i=0;i<manageNoList.size();i++){
                manageNo= manageNoList.get(i);
                manageNoMap.put(manageYes.getString("MANAGE_SORT_CODE"), manageNo);
            }
        }
     
        RoManagePO.delete("DEALER_CODE=? and RO_NO=? and D_KEY=? and IS_MANAGING is null", entityCode,roNO,CommonConstants.D_KEY);
        //修改主表update相关信息 2012-11-16

        RepairOrderPO ro = RepairOrderPO.findFirst("DEALER_CODE=? and RO_NO=? and D_KEY=?", entityCode,roNO,CommonConstants.D_KEY);
        ro.saveIt();
        System.out.println(manageYesMap);
        System.out.println(manageNoMap);
        
        insertOrUpdateRoManage(entityCode,roNO,userId,manageYesMap,manageNoMap);
    
    
    }
    private  void insertOrUpdateRoManage(String entityCode,String roNO,long userId,Map<String,RoManagePO> manageYesMap,Map<String,RoManagePO> manageNoMap) throws Exception{

        RoManagePO manage=null;
        Map<String, Double> manageMap=new HashMap<String, Double>();
        TtRoLabourPO labour=new TtRoLabourPO();
        
        RoAddItemPO addItem=new RoAddItemPO();
        
        TtRoRepairPartPO repairPart=new TtRoRepairPartPO();
        
        List<TtRoLabourPO> labourList=TtRoLabourPO.findBySQL("select * from TT_RO_LABOUR where DEALER_CODE=? and RO_NO=? and NEEDLESS_REPAIR=? and D_KEY=?", entityCode,roNO,Utility.getInt(DictCodeConstants.DICT_IS_NO),CommonConstants.D_KEY);
        
        List<RoAddItemPO> addItemList=RoAddItemPO.findBySQL("select * from tt_ro_add_item where DEALER_CODE=? and RO_NO=? and D_KEY=?", entityCode,roNO,CommonConstants.D_KEY);
        
        List<TtRoRepairPartPO> repairPartList=TtRoRepairPartPO.findBySQL("select * from TT_RO_REPAIR_PART where DEALER_CODE=? and RO_NO=? and D_KEY=? and NEEDLESS_REPAIR=?", entityCode,roNO,CommonConstants.D_KEY,Utility.getInt(DictCodeConstants.DICT_IS_NO));
        

        
        for(int i=0;i<labourList.size();i++){
            labour=labourList.get(i);
            RoManagePO manageTypePO=getManageOld(entityCode, labour.getString("MANAGE_SORT_CODE"),manageYesMap);
            if(manageTypePO!=null){

                if(manageMap.get(manageTypePO.getString("MANAGE_SORT_CODE"))==null)
                manageMap.put(manageTypePO.getString("MANAGE_SORT_CODE"), getMul(labour.getDouble("LABOUR_AMOUNT"),manageTypePO.getFloat("LABOUR_AMOUNT_RATE")));
                else
                manageMap.put(manageTypePO.getString("MANAGE_SORT_CODE"), manageMap.get(manageTypePO.getString("MANAGE_SORT_CODE"))+getMul(labour.getDouble("LABOUR_AMOUNT"),manageTypePO.getFloat("LABOUR_AMOUNT_RATE")));
            }
        }
        for(int i=0;i<addItemList.size();i++){
            addItem= addItemList.get(i);
            RoManagePO manageTypePO=getManageOld(entityCode, addItem.getString("MANAGE_SORT_CODE"),manageYesMap);
            if(manageTypePO!=null){
                if(manageMap.get(manageTypePO.getString("MANAGE_SORT_CODE"))==null)
                manageMap.put(manageTypePO.getString("MANAGE_SORT_CODE"), getMul(addItem.getDouble("ADD_ITEM_AMOUNT"),manageTypePO.getFloat("ADD_ITEM_RATE")));
                else
                manageMap.put(manageTypePO.getString("MANAGE_SORT_CODE"), manageMap.get(manageTypePO.getString("MANAGE_SORT_CODE"))+getMul(addItem.getDouble("ADD_ITEM_AMOUNT"),manageTypePO.getFloat("ADD_ITEM_RATE")));
            }
        }
        for(int i=0;i<repairPartList.size();i++){
            repairPart=repairPartList.get(i);
            RoManagePO manageTypePO=getManageOld(entityCode, repairPart.getString("MANAGE_SORT_CODE"),manageYesMap);
            if(manageTypePO!=null){
                if(manageMap.get(manageTypePO.getString("MANAGE_SORT_CODE"))==null)
                manageMap.put(manageTypePO.getString("MANAGE_SORT_CODE"), getMul(repairPart.getDouble("PART_SALES_AMOUNT"),manageTypePO.getFloat("REPAIR_PART_RATE")));
                else
                manageMap.put(manageTypePO.getString("MANAGE_SORT_CODE"), manageMap.get(manageTypePO.getString("MANAGE_SORT_CODE"))+getMul(repairPart.getDouble("PART_SALES_AMOUNT"),manageTypePO.getFloat("REPAIR_PART_RATE")));
            }
        }
        for(Iterator iter = manageMap.entrySet().iterator();iter.hasNext();){ 
            Map.Entry entry = (Map.Entry) iter.next(); 
            manage=getManage(entityCode,roNO,(String)entry.getKey(),Utility.getInt(DictCodeConstants.DICT_IS_YES));
            RoManagePO manageTypePO=getManageOld(entityCode, (String)entry.getKey(),manageYesMap);
            if(manage==null){
                manage=new RoManagePO();
               //manage.setItemId(POFactory.getLongPriKey(conn, manage));
                manage.setInteger("IS_MANAGING", DictCodeConstants.DICT_IS_YES);
                manage.setString("MANAGE_SORT_CODE", entry.getKey());
                manage.setDouble("OVER_ITEM_AMOUNT", entry.getValue());
                manage.setString("LABOUR_AMOUNT_RATE", manageTypePO.getString("LABOUR_AMOUNT_RATE"));
                manage.setString("ADD_ITEM_RATE", manageTypePO.getString("ADD_ITEM_RATE"));
                manage.setString("LABOUR_RATE", manageTypePO.getString("LABOUR_RATE"));
                manage.setString("REPAIR_PART_RATE", manageTypePO.getString("REPAIR_PART_RATE"));
                manage.setString("SALES_PART_RATE", manageTypePO.getString("SALES_PART_RATE"));
                manage.setString("OVERHEAD_EXPENSES_RATE", manageTypePO.getString("OVERHEAD_EXPENSES_RATE"));
                manage.setString("RO_NO", roNO);
                manage.saveIt();
            }
        } 
        Map<String, Double> manageTemp=manageMap;
        manageMap=new HashMap<String, Double>();
        
        for(int i=0;i<labourList.size();i++){
            labour=(TtRoLabourPO) labourList.get(i);
            RoManagePO manageTypePO=getManageOld(entityCode,labour.getString("Manage_Sort_Code"),manageNoMap);
            if(manageTypePO!=null){

                if(manageMap.get(manageTypePO.getString("MANAGE_SORT_CODE"))==null)
                manageMap.put(manageTypePO.getString("MANAGE_SORT_CODE"), getMul(labour.getDouble("LABOUR_AMOUNT"),manageTypePO.getFloat("LABOUR_AMOUNT_RATE"))+getMul(labour.getDouble("STD_LABOUR_HOUR"),manageTypePO.getFloat("LABOUR_RATE")));
                else
                manageMap.put(manageTypePO.getString("MANAGE_SORT_CODE"), manageMap.get(manageTypePO.getString("MANAGE_SORT_CODE"))+getMul(labour.getDouble("LABOUR_AMOUNT"),manageTypePO.getFloat("LABOUR_AMOUNT_RATE"))+getMul(labour.getDouble("STD_LABOUR_HOUR"),manageTypePO.getFloat("LABOUR_RATE")));
            }
        }
        for(int i=0;i<addItemList.size();i++){
            addItem=addItemList.get(i);
            RoManagePO manageTypePO=getManageOld(entityCode, addItem.getString("MANAGE_SORT_CODE"),manageNoMap);
            if(manageTypePO!=null){
                if(manageMap.get(manageTypePO.getString("MANAGE_SORT_CODE"))==null)
                manageMap.put(manageTypePO.getString("MANAGE_SORT_CODE"), getMul(addItem.getDouble("ADD_ITEM_AMOUNT"),manageTypePO.getFloat("ADD_ITEM_RATE")));
                else
                manageMap.put(manageTypePO.getString("MANAGE_SORT_CODE"), manageMap.get(manageTypePO.getString("MANAGE_SORT_CODE"))+getMul(addItem.getDouble("ADD_ITEM_AMOUNT"),manageTypePO.getFloat("ADD_ITEM_RATE")));
            }
        }
        for(int i=0;i<repairPartList.size();i++){
            repairPart=repairPartList.get(i);
            RoManagePO manageTypePO=getManageOld(entityCode, repairPart.getString("MANAGE_SORT_CODE"),manageNoMap);
            if(manageTypePO!=null){
                if(manageMap.get(manageTypePO.getString("MANAGE_SORT_CODE"))==null)
                manageMap.put(manageTypePO.getString("MANAGE_SORT_CODE"), getMul(repairPart.getDouble("PART_SALES_AMOUNT"),manageTypePO.getFloat("REPAIR_PART_RATE")));
                else
                manageMap.put(manageTypePO.getString("MANAGE_SORT_CODE"), manageMap.get(manageTypePO.getString("MANAGE_SORT_CODE"))+getMul(repairPart.getDouble("PART_SALES_AMOUNT"),manageTypePO.getFloat("REPAIR_PART_RATE")));
            }
        }
        
        for(Iterator iter = manageMap.entrySet().iterator();iter.hasNext();){ 
            Map.Entry entry = (Map.Entry) iter.next(); 
            manage=getManage(entityCode,roNO,(String)entry.getKey(),Utility.getInt(DictCodeConstants.DICT_IS_NO));
            RoManagePO manageTypePO=getManageOld(entityCode, (String)entry.getKey(),manageNoMap);
            if(manage==null){
                manage=new RoManagePO();
               // manage.setItemId(POFactory.getLongPriKey(conn, manage));
                manage.setInteger("IS_MANAGING", DictCodeConstants.DICT_IS_NO);
                manage.setString("MANAGE_SORT_CODE", entry.getKey());
                manage.setDouble("OVER_ITEM_AMOUNT", getAdd((Double)entry.getValue(),getMul(manageTemp.get((String)entry.getKey()),manageTypePO.getFloat("OVERHEAD_EXPENSES_RATE"))));
                manage.setFloat("LABOUR_AMOUNT_RATE", manageTypePO.getFloat("LABOUR_AMOUNT_RATE"));
                manage.setString("ADD_ITEM_RATE", manageTypePO.getString("ADD_ITEM_RATE"));
                manage.setString("LABOUR_RATE", manageTypePO.getString("LABOUR_RATE"));
                manage.setString("REPAIR_PART_RATE", manageTypePO.getString("REPAIR_PART_RATE"));
                manage.setString("SALES_PART_RATE", manageTypePO.getString("SALES_PART_RATE"));
                manage.setString("OVERHEAD_EXPENSES_RATE", manageTypePO.getString("OVERHEAD_EXPENSES_RATE"));
                manage.setString("RO_NO", roNO);
                manage.saveIt();
            }
        } 
    }
    
    public  void updateRepairOrder(String entityCode,String roNO){
            String sql = "UPDATE TT_REPAIR_ORDER SET OVER_ITEM_AMOUNT="
                +"COALESCE((SELECT SUM(OVER_ITEM_AMOUNT) FROM TT_RO_MANAGE WHERE RO_NO='"+ roNO +"'),0)"
                +","
                +"REPAIR_AMOUNT="
                +"COALESCE(REPAIR_PART_AMOUNT,0)+COALESCE(ADD_ITEM_AMOUNT,0)+COALESCE((SELECT SUM(OVER_ITEM_AMOUNT) FROM TT_RO_MANAGE WHERE RO_NO='"+ roNO +"'),0)+COALESCE(LABOUR_AMOUNT,0)+COALESCE(SALES_PART_AMOUNT,0)"
                +","
                +"ESTIMATE_AMOUNT="
                +"COALESCE(REPAIR_PART_AMOUNT,0)+COALESCE(ADD_ITEM_AMOUNT,0)+COALESCE((SELECT SUM(OVER_ITEM_AMOUNT) FROM TT_RO_MANAGE WHERE RO_NO='"+ roNO +"'),0)+COALESCE(LABOUR_AMOUNT,0)+COALESCE(SALES_PART_AMOUNT,0)"
                +" WHERE RO_NO='"
                + roNO 
                +"' AND DEALER_CODE = '"
                + entityCode
                +"' AND  D_KEY="
                + CommonConstants.D_KEY + " ";
            Base.exec(sql);
    }

    private  RoManagePO getManageOld(String entityCode,String manageSortCode,Map<String,RoManagePO> manageMap){
        if(manageSortCode==null)
            return null;
        RoManagePO po=manageMap.get(manageSortCode);
        return po;
    } 
    
    private static double getMul(Double v1,Float v2){
        String s1="0";
        String s2="0";
        if(v1==null)
            s1="0.00";
        else
            s1=v1.toString();
        if(v2==null)
            s2="0.00";
        else 
            s2=v2.toString();
        return Utility.round(new Double(Utility.mul(s1, s2)).toString(),2);
    }
    //获取辅料管理费积分基数
    public  String getManageCostCreditBase(String entityCode){
        String defaultValue="";
        defaultValue=commonNoService.getDefalutPara(CommonConstants.DEFAULT_PARA_MANAGE_COST_CREDIT_BASE+"");
        return defaultValue;
    }
    
    private static RoManagePO getManage(String entityCode,String roNO,String manageSortCode,int isManaging){
        if(manageSortCode==null||roNO==null)
            return null;
        RoManagePO po=new RoManagePO();
        
        List<RoManagePO>  listpo=RoManagePO.findBySQL("select * from TT_RO_MANAGE where DEALER_CODE=? and MANAGE_SORT_CODE=? and IS_MANAGING =? and RO_NO=? and D_KEY=?", entityCode,manageSortCode,isManaging,roNO,CommonConstants
                             .D_KEY);
        if(!CommonUtils.isNullOrEmpty(listpo)){
            po=listpo.get(0);
        }
        return po;
    }
    private static double getAdd(Double v1,double v2){
        String s1="0";
        String s2="0";
        if(v1==null)
            s1="0.00";
        else
            s1=v1.toString();
    
        s2=new Double(v2).toString();
        return Utility.round(new Double(Utility.add(s1, s2)).toString(),2);
    }  
    

    public int createOrUpdateDaily(TtPartPeriodReportDTO db, String entityCode) throws Exception {
        StringBuffer sbSqlUpdate = new StringBuffer();
        StringBuffer sbSqlUpdateWhere = new StringBuffer();
        StringBuffer sbSqlInsert = new StringBuffer();
        sbSqlUpdate
        .append(" UPDATE TT_PART_DAILY_REPORT"
                + " set IN_QUANTITY = CASE WHEN IN_QUANTITY IS NULL THEN 0 ELSE IN_QUANTITY END + ?,"
                + " STOCK_IN_AMOUNT = CASE WHEN STOCK_IN_AMOUNT IS NULL THEN 0 ELSE STOCK_IN_AMOUNT END + ?,"
                + " BUY_IN_COUNT = CASE WHEN BUY_IN_COUNT IS NULL THEN 0 ELSE BUY_IN_COUNT END + ?,"
                + " BUY_IN_AMOUNT = CASE WHEN BUY_IN_AMOUNT IS NULL THEN 0 ELSE BUY_IN_AMOUNT END + ?,"
                + " ALLOCATE_IN_COUNT = CASE WHEN ALLOCATE_IN_COUNT IS NULL THEN 0 ELSE ALLOCATE_IN_COUNT END + ?,"
                + " ALLOCATE_IN_AMOUNT = CASE WHEN ALLOCATE_IN_AMOUNT IS NULL THEN 0 ELSE ALLOCATE_IN_AMOUNT END + ?,"
                + " OTHER_IN_COUNT = CASE WHEN OTHER_IN_COUNT IS NULL THEN 0 ELSE OTHER_IN_COUNT END + ?,"
                + " OTHER_IN_AMOUNT = CASE WHEN OTHER_IN_AMOUNT IS NULL THEN 0 ELSE OTHER_IN_AMOUNT END + ?,"
                + " PROFIT_IN_COUNT = CASE WHEN PROFIT_IN_COUNT IS NULL THEN 0 ELSE PROFIT_IN_COUNT END + ?,"
                + " PROFIT_IN_AMOUNT = CASE WHEN PROFIT_IN_AMOUNT IS NULL THEN 0 ELSE PROFIT_IN_AMOUNT END + ?,"
                + " OUT_QUANTITY = CASE WHEN OUT_QUANTITY IS NULL THEN 0 ELSE OUT_QUANTITY END + ?,"
                + " STOCK_OUT_COST_AMOUNT = CASE WHEN STOCK_OUT_COST_AMOUNT IS NULL THEN 0 ELSE STOCK_OUT_COST_AMOUNT END + ?,"
                + " OUT_AMOUNT = CASE WHEN OUT_AMOUNT IS NULL THEN 0 ELSE OUT_AMOUNT END + ?,"
                + " REPAIR_OUT_COUNT = CASE WHEN REPAIR_OUT_COUNT IS NULL THEN 0 ELSE REPAIR_OUT_COUNT END + ?,"
                + " REPAIR_OUT_COST_AMOUNT = CASE WHEN REPAIR_OUT_COST_AMOUNT IS NULL THEN 0 ELSE REPAIR_OUT_COST_AMOUNT END + ?,"
                + " REPAIR_OUT_SALE_AMOUNT = CASE WHEN REPAIR_OUT_SALE_AMOUNT IS NULL THEN 0 ELSE REPAIR_OUT_SALE_AMOUNT END + ?,"
                + " SALE_OUT_COUNT = CASE WHEN SALE_OUT_COUNT IS NULL THEN 0 ELSE SALE_OUT_COUNT END + ?,"
                + " SALE_OUT_COST_AMOUNT = CASE WHEN SALE_OUT_COST_AMOUNT IS NULL THEN 0 ELSE SALE_OUT_COST_AMOUNT END + ?,"
                + " SALE_OUT_SALE_AMOUNT = CASE WHEN SALE_OUT_SALE_AMOUNT IS NULL THEN 0 ELSE SALE_OUT_SALE_AMOUNT END + ?,"
                + " INNER_OUT_COUNT = CASE WHEN INNER_OUT_COUNT IS NULL THEN 0 ELSE INNER_OUT_COUNT END + ?,"
                + " INNER_OUT_COST_AMOUNT = CASE WHEN INNER_OUT_COST_AMOUNT IS NULL THEN 0 ELSE INNER_OUT_COST_AMOUNT END + ?,"
                + " INNER_OUT_SALE_AMOUNT = CASE WHEN INNER_OUT_SALE_AMOUNT IS NULL THEN 0 ELSE INNER_OUT_SALE_AMOUNT END + ?,"
                + " ALLOCATE_OUT_COUNT = CASE WHEN ALLOCATE_OUT_COUNT IS NULL THEN 0 ELSE ALLOCATE_OUT_COUNT END + ?,"
                + " ALLOCATE_OUT_COST_AMOUNT = CASE WHEN ALLOCATE_OUT_COST_AMOUNT IS NULL THEN 0 ELSE ALLOCATE_OUT_COST_AMOUNT END + ?,"
                + " ALLOCATE_OUT_SALE_AMOUNT = CASE WHEN ALLOCATE_OUT_SALE_AMOUNT IS NULL THEN 0 ELSE ALLOCATE_OUT_SALE_AMOUNT END + ?,"
                + " OTHER_OUT_COUNT = CASE WHEN OTHER_OUT_COUNT IS NULL THEN 0 ELSE OTHER_OUT_COUNT END + ?,"
                + " OTHER_OUT_COST_AMOUNT = CASE WHEN OTHER_OUT_COST_AMOUNT IS NULL THEN 0 ELSE OTHER_OUT_COST_AMOUNT END + ?,"
                + " OTHER_OUT_SALE_AMOUNT = CASE WHEN OTHER_OUT_SALE_AMOUNT IS NULL THEN 0 ELSE OTHER_OUT_SALE_AMOUNT END + ?,"
                + " LOSS_OUT_COUNT = CASE WHEN LOSS_OUT_COUNT IS NULL THEN 0 ELSE LOSS_OUT_COUNT END + ?,"
                + " LOSS_OUT_AMOUNT = CASE WHEN LOSS_OUT_AMOUNT IS NULL THEN 0 ELSE LOSS_OUT_AMOUNT END + ?,"
                + " TRANSFER_IN_COUNT = CASE WHEN TRANSFER_IN_COUNT IS NULL THEN 0 ELSE TRANSFER_IN_COUNT END + ?,"
                + " TRANSFER_IN_AMOUNT = CASE WHEN TRANSFER_IN_AMOUNT IS NULL THEN 0 ELSE TRANSFER_IN_AMOUNT END + ?,"
                + " TRANSFER_OUT_COUNT = CASE WHEN TRANSFER_OUT_COUNT IS NULL THEN 0 ELSE TRANSFER_OUT_COUNT END + ?,"
                + " TRANSFER_OUT_COST_AMOUNT = CASE WHEN TRANSFER_OUT_COST_AMOUNT IS NULL THEN 0 ELSE TRANSFER_OUT_COST_AMOUNT END + ?,"
                + " UPHOLSTER_OUT_COUNT = CASE WHEN UPHOLSTER_OUT_COUNT IS NULL THEN 0 ELSE UPHOLSTER_OUT_COUNT END + ?,"
                + " UPHOLSTER_OUT_COST_AMOUNT = CASE WHEN UPHOLSTER_OUT_COST_AMOUNT IS NULL THEN 0 ELSE UPHOLSTER_OUT_COST_AMOUNT END + ?,"
                + " UPHOLSTER_OUT_SALE_AMOUNT = CASE WHEN UPHOLSTER_OUT_SALE_AMOUNT IS NULL THEN 0 ELSE UPHOLSTER_OUT_SALE_AMOUNT END + ?,"                     
                + " CLOSE_QUANTITY = CASE WHEN CLOSE_QUANTITY IS NULL THEN 0 ELSE CLOSE_QUANTITY END + ?-?,"
                + " CLOSE_AMOUNT = CASE WHEN CLOSE_AMOUNT IS NULL THEN 0 ELSE CLOSE_AMOUNT END + ?-?"

                + " WHERE DEALER_CODE = ?"
                + " AND REPORT_DATE = ?" + " AND STORAGE_CODE = ?" + " AND D_KEY = "
                + CommonConstants.D_KEY
        );

        sbSqlInsert.append(" INSERT INTO TT_PART_DAILY_REPORT (REPORT_DATE,STORAGE_CODE, DEALER_CODE, ");
        sbSqlInsert.append(" IN_QUANTITY, STOCK_IN_AMOUNT, BUY_IN_COUNT, BUY_IN_AMOUNT,ALLOCATE_IN_COUNT,");
        sbSqlInsert.append(" ALLOCATE_IN_AMOUNT, OTHER_IN_COUNT,OTHER_IN_AMOUNT, PROFIT_IN_COUNT, PROFIT_IN_AMOUNT,");
        sbSqlInsert.append(" OUT_QUANTITY, STOCK_OUT_COST_AMOUNT, OUT_AMOUNT,");
        sbSqlInsert.append(" REPAIR_OUT_COUNT, REPAIR_OUT_COST_AMOUNT,REPAIR_OUT_SALE_AMOUNT,");
        sbSqlInsert.append(" SALE_OUT_COUNT, SALE_OUT_COST_AMOUNT,SALE_OUT_SALE_AMOUNT,");
        sbSqlInsert.append(" INNER_OUT_COUNT, INNER_OUT_COST_AMOUNT,INNER_OUT_SALE_AMOUNT,");
        sbSqlInsert.append(" ALLOCATE_OUT_COUNT,ALLOCATE_OUT_COST_AMOUNT, ALLOCATE_OUT_SALE_AMOUNT,");
        sbSqlInsert.append(" OTHER_OUT_COUNT, OTHER_OUT_COST_AMOUNT, OTHER_OUT_SALE_AMOUNT,");
        sbSqlInsert.append(" LOSS_OUT_COUNT, LOSS_OUT_AMOUNT, CREATE_BY, CREATE_DATE,TRANSFER_IN_COUNT,TRANSFER_IN_AMOUNT,TRANSFER_OUT_COUNT,TRANSFER_OUT_COST_AMOUNT,");
        sbSqlInsert.append("  UPHOLSTER_OUT_COUNT,UPHOLSTER_OUT_COST_AMOUNT,UPHOLSTER_OUT_SALE_AMOUNT, ");
        sbSqlInsert.append(" CLOSE_QUANTITY,CLOSE_AMOUNT,OPEN_QUANTITY,OPEN_AMOUNT ");
        sbSqlInsert.append(" ) VALUES(?, ?, ?, ?, ?,  ?, ?, ?, ?, ?,  ?, ?, ?, ?, ?,  ?, ?, ?, ?, ?,  ?, ?, ?, ?, ?,  ?, ?, ?,?, ?,  ?, ?, ?, ?, ?,  ?, ?, ?, ?, ?,  ?, ? ");
        sbSqlInsert.append(",(SELECT SUM(STOCK_QUANTITY)  from tm_part_stock  where storage_code=? and dealer_code=?),");
        sbSqlInsert.append("(SELECT SUM(COST_AMOUNT)  from tm_part_stock  where storage_code=? and dealer_code=?),");
        sbSqlInsert.append("(SELECT SUM(STOCK_QUANTITY)  from tm_part_stock  where storage_code=? and dealer_code=?)+?-?,");
        sbSqlInsert.append("(SELECT SUM(COST_AMOUNT)  from tm_part_stock  where storage_code=? and dealer_code=?)+?-?");
        sbSqlInsert.append(" )");

        try {
            if (db != null) {
                if (db.getOpenQuantity() == null) db.setOpenQuantity(0d);
                if (db.getOpenPrice() == null) db.setOpenPrice(0d);
                if (db.getOpenAmount() == null) db.setOpenAmount(0d);
                if (db.getInQuantity() == null) db.setInQuantity(0d);
                if (db.getStockInAmount() == null) db.setStockInAmount(0d);
                if (db.getBuyInCount() == null) db.setBuyInCount(0d);
                if (db.getBuyInAmount() == null) db.setBuyInAmount(0d);
                if (db.getAllocateInAmount() == null) db.setAllocateInAmount(0d);
                if (db.getAllocateInCount() == null) db.setAllocateInCount(0d);
                if (db.getOtherInCount() == null) db.setOtherInCount(0d);
                if (db.getOtherInAmount() == null) db.setOtherInAmount(0d);
                if (db.getProfitInCount() == null) db.setProfitInCount(0d);
                if (db.getProfitInAmount() == null) db.setProfitInAmount(0d);
                if (db.getOutQuantity() == null) db.setOutQuantity(0d);
                if (db.getStockOutCostAmount() == null) db.setStockOutCostAmount(0d);
                if (db.getOutAmount() == null) db.setOutAmount(0d);
                if (db.getRepairOutCount() == null) db.setRepairOutCount(0d);
                if (db.getRepairOutCostAmount() == null) db.setRepairOutCostAmount(0d);
                if (db.getRepairOutSaleAmount() == null) db.setRepairOutSaleAmount(0d);
                if (db.getSaleOutCount() == null) db.setSaleOutCount(0d);
                if (db.getSaleOutCostAmount() == null) db.setSaleOutCostAmount(0d);
                if (db.getSaleOutSaleAmount() == null) db.setSaleOutSaleAmount(0d);
                if (db.getInnerOutCount() == null) db.setInnerOutCount(0d);
                if (db.getInnerOutCostAmount() == null) db.setInnerOutCostAmount(0d);
                if (db.getInnerOutSaleAmount() == null) db.setInnerOutSaleAmount(0d);
                if (db.getAllocateOutCount() == null) db.setAllocateOutCount(0d);
                if (db.getAllocateOutCostAmount() == null) db.setAllocateOutCostAmount(0d);
                if (db.getAllocateOutSaleAmount() == null) db.setAllocateOutSaleAmount(0d);
                if (db.getOtherOutCount() == null) db.setOtherOutCount(0d);
                if (db.getOtherOutCostAmount() == null) db.setOtherOutCostAmount(0d);
                if (db.getOtherOutSaleAmount() == null) db.setOtherOutSaleAmount(0d);
                if (db.getLossOutCount() == null) db.setLossOutCount(0d);
                if (db.getLossOutAmount() == null) db.setLossOutAmount(0d);
                if (db.getCloseQuantity() == null) db.setCloseQuantity(0d);
                if (db.getClosePrice() == null) db.setClosePrice(0d);
                if (db.getCloseAmount() == null) db.setCloseAmount(0d);
                if (db.getTransferInAmount() == null) db.setTransferInAmount(0d);
                if (db.getTransferOutCostAmount() == null) db.setTransferOutCostAmount(0d);
                if (db.getTransferInCount() == null) db.setTransferInCount(0d);
                if (db.getTransferOutCount() == null) db.setTransferOutCount(0d);
                if (db.getUpholsterOutCount() == null) {
                    db.setUpholsterOutCount(0d);
                }
                if (db.getUpholsterOutCostAmount() == null) {
                    db.setUpholsterOutCostAmount(0d);
                }
                if (db.getUpholsterOutSaleAmount() == null) {
                    db.setUpholsterOutSaleAmount(0d);
                }
                Timestamp timestamp = Utility.getCurrentTimestamp();
                String CurDate = timestamp.toString().substring(0, 10);
                Map<String, Object> map = new HashMap<String, Object>();
                map.put("storageCode", db.getStorageCode());
                map.put("reportDate", CurDate);
                map.put("dKey", CommonConstants.D_KEY);
                List<Map> list = queryPartDailyReportList(map);
                if (null != list && list.size() > 0) {
                    List<Object> params = new ArrayList<>();
                    params.add(Math.round(db.getInQuantity() * 100) * 0.01);
                    params.add(Math.round(db.getStockInAmount() * 100) * 0.01);
                    params.add(Math.round(db.getBuyInCount() * 100) * 0.01);
                    params.add(Math.round(db.getBuyInAmount() * 100) * 0.01);
                    params.add(Math.round(db.getAllocateInCount() * 100) * 0.01);

                    // ALLOCATE_IN_AMOUNT,OTHER_IN_COUNT,OTHER_IN_AMOUNT,PROFIT_IN_COUNT,PROFIT_IN_AMOUNT,
                    params.add(Math.round(db.getAllocateInAmount() * 100) * 0.01);
                    params.add(Math.round(db.getOtherInCount() * 100) * 0.01);
                    params.add(Math.round(db.getOtherInAmount() * 100) * 0.01);
                    params.add(Math.round(db.getProfitInCount() * 100) * 0.01);
                    params.add(Math.round(db.getProfitInAmount() * 100) * 0.01);

                    // STOCK_OUT_COUNT,STOCK_OUT_COST_AMOUNT,STOCK_OUT_AMOUNT,REPAIR_OUT_COUNT,REPAIR_OUT_COST_AMOUNT,
                    params.add(Math.round(db.getOutQuantity() * 100) * 0.01);
                    params.add(Math.round(db.getStockOutCostAmount() * 100) * 0.01);
                    params.add(Math.round(db.getOutAmount() * 100) * 0.01);
                    params.add(Math.round(db.getRepairOutCount() * 100) * 0.01);
                    params.add(Math.round(db.getRepairOutCostAmount() * 100) * 0.01);

                    // REPAIR_OUT_SALE_AMOUNT,SALE_OUT_COUNT,SALE_OUT_COST_AMOUNT,SALE_OUT_SALE_AMOUNT,INNER_OUT_COUNT,
                    params.add(Math.round(db.getRepairOutSaleAmount() * 100) * 0.01);
                    params.add(Math.round(db.getSaleOutCount() * 100) * 0.01);
                    params.add(Math.round(db.getSaleOutCostAmount() * 100) * 0.01);
                    params.add(Math.round(db.getSaleOutSaleAmount() * 100) * 0.01);
                    params.add(Math.round(db.getInnerOutCount() * 100) * 0.01);

                    params.add(Math.round(db.getInnerOutCostAmount() * 100) * 0.01);
                    params.add(Math.round(db.getInnerOutSaleAmount() * 100) * 0.01);
                    params.add(Math.round(db.getAllocateOutCount() * 100) * 0.01);
                    params.add(Math.round(db.getAllocateOutCostAmount() * 100) * 0.01);
                    params.add(Math.round(db.getAllocateOutSaleAmount() * 100) * 0.01);

                    params.add(Math.round(db.getOtherOutCount() * 100) * 0.01);
                    params.add(Math.round(db.getOtherOutCostAmount() * 100) * 0.01);
                    params.add(Math.round(db.getOtherOutSaleAmount() * 100) * 0.01);
                    params.add(Math.round(db.getLossOutCount() * 100) * 0.01);
                    params.add(Math.round(db.getLossOutAmount() * 100) * 0.01);
                    params.add(Math.round(db.getTransferInCount() * 100) * 0.01);
                    params.add(Math.round(db.getTransferInAmount() * 100) * 0.01);
                    params.add(Math.round(db.getTransferOutCount() * 100) * 0.01);
                    params.add(Math.round(db.getTransferOutCostAmount() * 100) * 0.01);

                    params.add(Math.round(db.getUpholsterOutCount() * 100) * 0.01);
                    params.add(Math.round(db.getUpholsterOutCostAmount() * 100) * 0.01);
                    params.add(Math.round(db.getUpholsterOutSaleAmount() * 100) * 0.01);

                    logger.debug("db.getInQuantity()" + db.getInQuantity());
                    logger.debug("db.getOutQuantity()" + db.getOutQuantity());
                    params.add(Math.round(db.getInQuantity() * 100) * 0.01);
                    params.add(Math.round(db.getOutQuantity() * 100) * 0.01);
                    params.add(Math.round(db.getStockInAmount() * 100) * 0.01);
                    params.add(Math.round(db.getStockOutCostAmount() * 100) * 0.01);

                    // UPDATE_BY,UPDATE_DATE,PART_PERIOD_REPORT_ID
                    params.add(entityCode);
                    params.add(CurDate);
                    params.add(db.getStorageCode());
                    logger.debug(this.getClass() + "--------->psUpdate over!");
                    modifyPartDailyReportByParams(sbSqlUpdate.toString(),
                                                                       sbSqlUpdateWhere.toString(), params);
                    logger.debug(this.getClass() + "--------->psUpdate Execute over!");
                } else {
                    List<Object> params = new ArrayList<>();
                    params.add(CurDate);
                    params.add(db.getStorageCode());
                    params.add(entityCode);

                    params.add(Math.round(db.getInQuantity() * 100) * 0.01);
                    params.add(Math.round(db.getStockInAmount() * 100) * 0.01);
                    params.add(Math.round(db.getBuyInCount() * 100) * 0.01);

                    params.add(Math.round(db.getBuyInAmount() * 100) * 0.01);
                    params.add(Math.round(db.getAllocateInCount() * 100) * 0.01);
                    params.add(Math.round(db.getAllocateInAmount() * 100) * 0.01);
                    params.add(Math.round(db.getOtherInCount() * 100) * 0.01);
                    params.add(Math.round(db.getOtherInAmount() * 100) * 0.01);

                    params.add(Math.round(db.getProfitInCount() * 100) * 0.01);
                    params.add(Math.round(db.getProfitInAmount() * 100) * 0.01);
                    params.add(Math.round(db.getSaleOutCount() * 100) * 0.01);
                    params.add(Math.round(db.getStockOutCostAmount() * 100) * 0.01);
                    params.add(Math.round(db.getOutAmount() * 100) * 0.01);

                    params.add(Math.round(db.getRepairOutCount() * 100) * 0.01);
                    params.add(Math.round(db.getRepairOutCostAmount() * 100) * 0.01);
                    params.add(Math.round(db.getRepairOutSaleAmount() * 100) * 0.01);
                    params.add(Math.round(db.getSaleOutCount() * 100) * 0.01);
                    params.add(Math.round(db.getSaleOutCostAmount() * 100) * 0.01);

                    params.add(Math.round(db.getSaleOutSaleAmount() * 100) * 0.01);
                    params.add(Math.round(db.getInnerOutCount() * 100) * 0.01);
                    params.add(Math.round(db.getInnerOutCostAmount() * 100) * 0.01);
                    params.add(Math.round(db.getInnerOutSaleAmount() * 100) * 0.01);
                    params.add(Math.round(db.getAllocateOutCount() * 100) * 0.01);

                    params.add(Math.round(db.getAllocateOutCostAmount() * 100) * 0.01);
                    params.add(Math.round(db.getAllocateOutSaleAmount() * 100) * 0.01);
                    params.add(Math.round(db.getOtherOutCount() * 100) * 0.01);
                    params.add(Math.round(db.getOtherOutCostAmount() * 100) * 0.01);
                    params.add(Math.round(db.getOtherOutSaleAmount() * 100) * 0.01);

                    params.add(Math.round(db.getLossOutCount() * 100) * 0.01);
                    params.add(Math.round(db.getLossOutAmount() * 100) * 0.01);
                    params.add(FrameworkUtil.getLoginInfo().getUserId());
                    params.add(new java.sql.Date(System.currentTimeMillis()));
                    params.add(Math.round(db.getTransferInCount() * 100) * 0.01);
                    params.add(Math.round(db.getTransferInAmount() * 100) * 0.01);
                    params.add(Math.round(db.getTransferOutCount() * 100) * 0.01);
                    params.add(Math.round(db.getTransferOutCostAmount() * 100) * 0.01);

                    params.add(Math.round(db.getUpholsterOutCount() * 100) * 0.01);
                    params.add(Math.round(db.getUpholsterOutCostAmount() * 100) * 0.01);
                    params.add(Math.round(db.getUpholsterOutSaleAmount() * 100) * 0.01);

                    params.add(db.getStorageCode());
                    params.add(db.getDealerCode());
                    params.add(db.getStorageCode());
                    params.add(db.getDealerCode());
                    params.add(db.getStorageCode());
                    params.add(db.getDealerCode());
                    params.add(Math.round(db.getOutQuantity() * 100) * 0.01);
                    params.add(Math.round(db.getInQuantity() * 100) * 0.01);

                    params.add(db.getStorageCode());
                    params.add(db.getDealerCode());
                    params.add(Math.round(db.getStockOutCostAmount() * 100) * 0.01);
                    params.add(Math.round(db.getStockInAmount() * 100) * 0.01);
                    logger.debug(this.getClass() + "------->psInsert set over!");
                    logger.debug("db.getDealerCode()--->" + db.getDealerCode());
                     addPartDailyReport(sbSqlInsert.toString(), params);
                    logger.debug(this.getClass() + "------->psInsert Execute over!");
                }
            }
            logger.debug(this.getClass() + "createOrUpdate over!");
            logger.debug(this.getClass() + "createOrUpdateDaily over!");
            return 0;
        } catch (Exception err) {
            err.printStackTrace();
            throw err;
        }
    }
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
    public void addPartDailyReport(String sqlStr, List params) throws ServiceBizException {
        // TODO Auto-generated method stub
        DAOUtil.execBatchPreparement(sqlStr, params);
    }
    
    
    public int modifyPartDailyReportByParams(String sqlStr, String sqlWhere
                                             ,List paramsList) throws ServiceBizException {
        DAOUtil.execBatchPreparement(sqlStr + " " + sqlWhere, paramsList);//PartDailyReportPO.update(sqlStr, sqlWhere, paramsList);
        return 0;
    }
}
