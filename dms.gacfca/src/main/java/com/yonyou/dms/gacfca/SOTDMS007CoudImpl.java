
/** 
*Copyright 2017 Yonyou Corporation Ltd. All Rights Reserved.
* This software is published under the terms of the Yonyou Software
* License version 1.0, a copy of which has been included with this
* distribution in the LICENSE.txt file.
*
* @Project Name : dms.gacfca
*
* @File name : SOTDMS007Impl.java
*
* @Author : Administrator
*
* @Date : 2017年3月8日
*
----------------------------------------------------------------------------------
*     Date       Who       Version     Comments
* 1. 2017年3月8日    Administrator    1.0
*
*
*
*
----------------------------------------------------------------------------------
*/
	
package com.yonyou.dms.gacfca;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yonyou.dms.DTO.gacfca.TiAppNCultivateDto;
import com.yonyou.dms.common.domains.PO.basedata.PotentialCusPO;
import com.yonyou.dms.common.domains.PO.basedata.TrackingTaskPO;
import com.yonyou.dms.common.domains.PO.basedata.TtPoCusLinkmanPO;
import com.yonyou.dms.common.domains.PO.basedata.TtSalesPromotionPlanPO;
import com.yonyou.dms.framework.DAO.DAOUtil;
import com.yonyou.dms.framework.service.CommonNoService;
import com.yonyou.dms.framework.util.FrameworkUtil;
import com.yonyou.dms.function.common.DictCodeConstants;
import com.yonyou.dms.function.exception.ServiceBizException;
import com.yonyou.dms.function.utils.common.StringUtils;

/**
* TODO description
* @author Administrator
* @date 2017年3月8日
*/
@Service
public class SOTDMS007CoudImpl implements SOTDMS007Coud{
    @Autowired
    private CommonNoService     commonNoService;
    
    public int getSOTDMS007(List<TiAppNCultivateDto> voList) throws ServiceBizException {
        if(DictCodeConstants.DICT_IS_YES.equals(commonNoService.getDefalutPara("5434"))){
            if(voList!=null&& voList.size()>0){
                for(int i=0;i<voList.size();i++){
                    TiAppNCultivateDto vo = (TiAppNCultivateDto)voList.get(i);
                    String customerNo = null;
                    if(!StringUtils.isNullOrEmpty(vo.getUniquenessID())){
                        customerNo = vo.getUniquenessID();
                    }else if(!StringUtils.isNullOrEmpty(vo.getFcaId())){
                        //根据售中工具id 查找到对应的潜客
                        PotentialCusPO cuspo1 =new PotentialCusPO();
                        List<Object> cus1 = new ArrayList<Object>();
                        cus1.add(vo.getDealerCode());
                        cus1.add(vo.getFcaId().longValue());   
                        cus1.add(DictCodeConstants.D_KEY);   
                        List<PotentialCusPO> cusList = PotentialCusPO.findBySQL("select * from tm_potential_customer where dealer_code=? and Spad_Customer_Id=? and D_Key=? ", cus1.toArray());
                        if(cusList!=null&&cusList.size()>0){
                            cuspo1 = (PotentialCusPO)cusList.get(0);
                            customerNo = cuspo1.getString("Customer_No");
                        }
                            
                    }
                    List<Object> cus3 = new ArrayList<Object>();
                    cus3.add(vo.getDealerCode());
                    cus3.add(customerNo);   
                    cus3.add(DictCodeConstants.D_KEY);   
                    List<PotentialCusPO> listpoten = PotentialCusPO.findBySQL("select * from Tm_Potential_Customer where dealer_code=? and Customer_No=? and D_Key=? ", cus3.toArray());
//                  查询潜在客户信息
                    Integer ver1 = 0;
                    String cusName = null;
                    String intentLevel = null;
                    String customerName= null;
                    String priorGrade = null;
                    
                    if (listpoten != null && listpoten.size() > 0) {
                        ver1 = listpoten.get(0).getInteger("Ver");
                        cusName = listpoten.get(0).getString("Customer_Name");
                        if (listpoten.get(0).get("Intent_Level") != null && listpoten.get(0).getInteger("Intent_Level") != 0) {
                            intentLevel = listpoten.get(0).get("Intent_Level").toString();
                        }
                    }
                        customerName = cusName;
                        priorGrade = intentLevel;
              //      logger.debug("&&&&&&&&priorGrade:"+priorGrade);
                    String intentId=listpoten.get(0).get("Intent_Id").toString();
                    String realPromWay = vo.getCommType();
                    String promContent = vo.getCommContent();
                    String nextGrade = vo.getFollowOppLevelId();
                    String soldBy = vo.getDealerUserId();
                    Date nextPromDate = vo.getNextCommDate();
                    String promWay = vo.getCommType();
                    TtSalesPromotionPlanPO salesplan = null;    
                    if(!StringUtils.isNullOrEmpty(customerNo)){
                        if(!StringUtils.isNullOrEmpty(vo.getDormantType())){
                            if(!StringUtils.isNullOrEmpty(intentLevel)){
                                if("13101009".equals(intentLevel)||"13101008".equals(intentLevel)||"13101007".equals(intentLevel)||"13101006".equals(intentLevel)){
                                //    logger.debug("客户级别:" +intentLevel+"不能做客户休眠！");
                                }else{
                                    String dormantType=vo.getDormantType();//休眠类型
                                    String giveUpReason=vo.getGiveUpReason();//休眠理由
                                    Date giveUpDate=vo.getGiveUpDate();//休眠日期
//                                  潜客信息客户级别更新
                                    PotentialCusPO tmcus4= PotentialCusPO.findByCompositeKeys(FrameworkUtil.getLoginInfo().getDealerCode(),customerNo);
                                    tmcus4.setDate("Ddcn_Update_Date",new Date());
                              //      tmcus4.setUpdateDate(Utility.getCurrentDateTime());
                                    tmcus4.setInteger("Intent_Level",Integer.parseInt(DictCodeConstants.DICT_INTENT_LEVEL_FO));
                                    if(!StringUtils.isNullOrEmpty(giveUpDate))
                                        tmcus4.setDate("Spad_Update_Date",giveUpDate);
                                    if(!StringUtils.isNullOrEmpty(soldBy))
                                        tmcus4.setLong("Updated_By",Long.parseLong(soldBy));
                                    tmcus4.setInteger("Audit_Status",33351001);//经理审核中 字典等下做
                                    if(!StringUtils.isNullOrEmpty(intentLevel))
                                        tmcus4.setInteger("Fail_Intent_Level",Integer.parseInt(intentLevel));
                                    if (!StringUtils.isNullOrEmpty(dormantType))
                                        tmcus4.setInteger("Sleep_Type",Integer.parseInt(dormantType));
                                    if (!StringUtils.isNullOrEmpty(giveUpReason))
                                        tmcus4.setString("Keep_Apply_Reasion",giveUpReason);
                                    tmcus4.saveIt();
                                }
                            }
                    
                        }else{
                            PotentialCusPO tmcus2= PotentialCusPO.findByCompositeKeys(FrameworkUtil.getLoginInfo().getDealerCode(),customerNo);   
//                      潜客信息客户级别更新
                       
                        tmcus2.setInteger("Intent_Level",vo.getFollowOppLevelId());
                        tmcus2.saveIt();
                   //     logger.debug("促进后客户级别:" + vo.getFollowOppLevelId());
                        //判断是否有待跟进任务清单
                        SimpleDateFormat dateformat1=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                        String actionDate = dateformat1.format(vo.getCommDate());
                       
                        List list1 = new ArrayList();
                        list1=QueryPromotionBacklog(Long.parseLong(vo.getDealerUserId()),customerNo,null,null);
                        if (list1 != null && list1.size() > 0) {
                            List<Object> cus9 = new ArrayList<Object>();
                            cus9.add(FrameworkUtil.getLoginInfo().getDealerCode());
                            cus9.add(customerNo);   
                          List <TtSalesPromotionPlanPO> tcPOF =TtSalesPromotionPlanPO.findBySQL("select * from TT_SALES_PROMOTION_PLAN where dealer_code=? and CUSTOMER_NO=? and (PROM_RESULT is null or PROM_RESULT=0)", cus9.toArray());
//                          更新客户未跟进记录为继续跟进
                        if  (tcPOF.size()>0){
                            for (int j=0;j<tcPOF.size();j++){
                                TtSalesPromotionPlanPO tcPOF1 =(TtSalesPromotionPlanPO)tcPOF.get(j);   
                                tcPOF1.setInteger("PROM_RESULT", 13341009);
                                tcPOF1.setDate("ACTION_DATE", actionDate);
                                tcPOF1.setString("REAL_PROM_WAY", realPromWay);
                                tcPOF1.setString("NEXT_GRADE", nextGrade);
                                tcPOF1.saveIt();
                            }
                           
                        }
                         
                    
                       
                       //     tcPOF.UpdateSalesPromotionPlanOnPad(conn, customerNo, entityCode, actionDate, vo.getCommType(), vo.getFollowOppLevelId());
                        } else {
                            salesplan = new TtSalesPromotionPlanPO();
                            //生成一条继续跟进的记录
                         //   String groupCode=Utility.getGroupEntity(conn, entityCode, "TM_TRACKING_TASK");
                            List<Object> cus4 = new ArrayList<Object>();
                            cus4.add(priorGrade);
                            cus4.add(DictCodeConstants.DICT_CUSTOMER_STATUS_LATENCY);   
                            cus4.add(DictCodeConstants.DICT_IS_YES);   
                            List<TrackingTaskPO> list22 = TrackingTaskPO.findBySQL("select * from tm_Tracking_Task where Intent_Level=? and Customer_Status=? and Is_Valid=? ", cus4.toArray());
                          
                            if(list22 != null && list22.size() > 0){
                                TrackingTaskPO ttpo3 = new TrackingTaskPO();
                                ttpo3=(TrackingTaskPO)list22.get(0);
                                salesplan.set("Task_Id",ttpo3.get("Task_Id"));
                            }
                          
                       //     salesplan.setEntityCode(entityCode);
                       //     salesplan.setItemId(id);
                            if (!StringUtils.isNullOrEmpty(customerNo)) {
                                salesplan.setString("Customer_No",customerNo);
                            }
                            if (!StringUtils.isNullOrEmpty(intentId)) {
                                salesplan.setLong("Intent_Id",intentId);
                            }
                            if (!StringUtils.isNullOrEmpty(customerName)) {
                                salesplan.setString("Customer_Name",customerName);
                            }
                            if (!StringUtils.isNullOrEmpty(vo.getDealerUserId())) {
                                salesplan.setLong("Sold_By",vo.getDealerUserId());
                                salesplan.setLong("OwnedBy",vo.getDealerUserId());
                            }
                            if (!StringUtils.isNullOrEmpty(actionDate)) {
                                salesplan.setDate("Action_Date",actionDate);
                            }
                            if (!StringUtils.isNullOrEmpty(realPromWay)) {
                                salesplan.setInteger("Real_Prom_Way",realPromWay);
                            }
                            if (!StringUtils.isNullOrEmpty(promWay)) {
                                salesplan.setInteger("Prom_Way",promWay);
                            }
                            if (!StringUtils.isNullOrEmpty(vo.getCommDate())) {
                                salesplan.setDate("Schedule_Date",vo.getCommDate());
                            }
                            if (!StringUtils.isNullOrEmpty(vo.getNextCommDate())) {
                                salesplan.setDate("Next_Prom_Date",vo.getNextCommDate());
                            }
                            if (!StringUtils.isNullOrEmpty(promContent)) {
                                salesplan.setString("Prom_Content",promContent);
                            }
                            if (!StringUtils.isNullOrEmpty(priorGrade)) {
                                salesplan.setString("Prior_Grade",priorGrade);
                            }
                            if (!StringUtils.isNullOrEmpty(nextGrade)) {
                                salesplan.setString("Next_Grade",nextGrade);
                            }
                            if(!StringUtils.isNullOrEmpty(vo.getFcaId())){
                                salesplan.setLong("Spad_Customer_Id",vo.getFcaId());
                            }
                            
//                          if (Utility.testString(scene)) {
//                              salesplan.setScene(scene);
//                          }
                            //联系人
                            List<Object> cus5 = new ArrayList<Object>();
                            cus5.add(FrameworkUtil.getLoginInfo().getDealerCode());
                            cus5.add(DictCodeConstants.D_KEY);   
                            cus5.add(customerNo);   
                            List<TtPoCusLinkmanPO> listcustomer = TtPoCusLinkmanPO.findBySQL("select * from Tt_Po_Cus_Linkman where dealer_code=? and d_key=? and customer_no=? ", cus5.toArray());
//                            TtPoCusLinkmanPO customer=new TtPoCusLinkmanPO();
                            TtPoCusLinkmanPO customer2=new TtPoCusLinkmanPO();
                            if(listcustomer!=null){
                                for (int j = 0; j < listcustomer.size(); j++)
                                {
                                    customer2=(TtPoCusLinkmanPO)listcustomer.get(0);
                                }
                                if(customer2.get("Contactor_Name")!=null && !"".equals(customer2.getString("Contactor_Name"))){
                                    salesplan.setString("Contactor_Name",customer2.getString("Contactor_Name"));
                                }
                                if(customer2.get("Phone")!=null && !"".equals(customer2.getString("Phone"))){
                                    salesplan.setString("Phone",customer2.getString("Phone"));
                                }
                                if(customer2.get("Mobile")!=null && !"".equals(customer2.getString("Mobile"))){
                                    salesplan.set("Mobile",customer2.getString("Mobile"));
                                }
                            }
                            //PAD新增跟进逻辑变更
                            salesplan.setInteger("PromResult",DictCodeConstants.DICT_PROM_RESULT_CONTINUE);
                            //是否是售中工具跟进
                            salesplan.setInteger("Is_Spad_Create",DictCodeConstants.DICT_IS_YES);
                        //  salesplan.setIsPadPlan(Utility.getInt(DictDataConstant.DICT_IS_YES));
                            //创建方式为手工创建
                            salesplan.setInteger("Create_Type",DictCodeConstants.DICT_CREATE_TYPE_HANDWORK);
                            salesplan.setDate("Spad_Update_Date",new Date());
                        //    salesplan.setCreateBy(userid);
                        //    salesplan.setCreateDate(Utility.getCurrentDateTime());
                            salesplan.setInteger("Ver",1);
                            salesplan.saveIt();
                        }   
                        salesplan = new TtSalesPromotionPlanPO();
                        List<Object> cus6 = new ArrayList<Object>();
                        cus6.add(FrameworkUtil.getLoginInfo().getDealerCode());
                        cus6.add(DictCodeConstants.DICT_CUSTOMER_STATUS_LATENCY);   
                        cus6.add(DictCodeConstants.DICT_IS_YES);   
                        List<TrackingTaskPO> list22 = TrackingTaskPO.findBySQL("select * from tm_Tracking_Task where dealer_code=? and Intent_Level=? and Customer_Status=? and Is_Valid=? ", cus6.toArray());
                //        String groupCode=Utility.getGroupEntity(conn, entityCode, "TM_TRACKING_TASK");
                      
                        if(list22 != null && list22.size() > 0){
                            TrackingTaskPO ttpo3 = new TrackingTaskPO();
                            ttpo3=(TrackingTaskPO)list22.get(0);
                            salesplan.setLong("Task_Id",ttpo3.get("Task_Id"));
                        }
                        if (!StringUtils.isNullOrEmpty(customerNo)) {
                            salesplan.setString("Customer_No",customerNo);
                        }
                        if (!StringUtils.isNullOrEmpty(intentId)) {
                            salesplan.setLong("Intent_Id",intentId);
                        }
                        if (!StringUtils.isNullOrEmpty(customerName)) {
                            salesplan.setString("Customer_Name",customerName);
                        }
                        if (!StringUtils.isNullOrEmpty(vo.getDealerUserId())) {
                            salesplan.setLong("Sold_By",vo.getDealerUserId());
                            salesplan.setLong("OwnedBy",vo.getDealerUserId());
                        }
//                      if (Utility.testString(realPromWay)) {
//                          salesplan.setRealPromWay(Utility.getInt(realPromWay));
//                      }
//                      if (Utility.testString(promWay)) {
//                          salesplan.setPromWay(Utility.getInt(promWay));
//                      }
//                      if (Utility.testString(nextPadApproachPlan)) {
//                          salesplan.setNextPadApproachPlan(Utility.getInt(nextPadApproachPlan));
//                      }
                        
                        if (!StringUtils.isNullOrEmpty(nextPromDate)) {
                            salesplan.setDate("Schedule_Date",nextPromDate);
                        }
//                      if (Utility.testString(nextPromDate)) {
//                          salesplan.setNextPromDate(Utility.getTimeStamp(nextPromDate));
//                      }
                        if (!StringUtils.isNullOrEmpty(vo.getNextCommContent())) {
                            salesplan.setString("Prom_Content",vo.getNextCommContent());
                        }
                        if (!StringUtils.isNullOrEmpty(nextGrade)) {
                            salesplan.setInteger("PriorGrade",nextGrade);
                        }
//                      if (Utility.testString(nextGrade)) {
//                          salesplan.setNextGrade(Utility.getInt(nextGrade));
//                      }
                        //联系人
//                        TtPoCusLinkmanPO customer=new TtPoCusLinkmanPO();
                        TtPoCusLinkmanPO customer2=new TtPoCusLinkmanPO();
                        List<Object> cus7 = new ArrayList<Object>();
                        cus7.add(vo.getDealerCode());
                        cus7.add(DictCodeConstants.D_KEY);   
                        cus7.add(customerNo);   
                        List<TtPoCusLinkmanPO> listcustomer = TtPoCusLinkmanPO.findBySQL("select * from Tt_Po_Cus_Linkman where dealer_code=? and d_key=? and customer_no=? ", cus7.toArray());                 
                        if(listcustomer!=null){
                            for (int j = 0; j < listcustomer.size(); j++)
                            {
                                customer2=(TtPoCusLinkmanPO)listcustomer.get(0);
                            }
                            if(customer2.get("Contactor_Name")!=null && !"".equals(customer2.getString("Contactor_Name"))){
                                salesplan.setString("Contactor_Name",customer2.getString("Contactor_Name"));
                            }
                            if(customer2.get("Phone")!=null && !"".equals(customer2.getString("Phone"))){
                                salesplan.setString("Phone",customer2.getString("Phone"));
                            }
                            if(customer2.get("Mobile")!=null && !"".equals(customer2.getString("Mobile"))){
                                salesplan.set("Mobile",customer2.getString("Mobile"));
                            }
                        }
                        //PAD新增跟进逻辑变更
//                      salesplan.setPromResult(Utility.getInt(DictDataConstant.DICT_PROM_RESULT_CONTINUE));
//                      salesplan.setActionDate(Utility.getCurrentDateTime());
                        if(!StringUtils.isNullOrEmpty(vo.getFcaId())){
                            salesplan.setLong("Spad_Customer_Id",vo.getFcaId());
                        }
                        //是否是PAD跟进
                        salesplan.setInteger("Is_Spad_Create",DictCodeConstants.DICT_IS_YES);
                        salesplan.setDate("Spad_Update_Date",new Date());
    //                  salesplan.setIsPadPlan(Utility.getInt(DictDataConstant.DICT_IS_YES));
                        //创建方式为手工创建
                        salesplan.setInteger("Create_Type",DictCodeConstants.DICT_CREATE_TYPE_HANDWORK);
               //         salesplan.setCreateBy(userid);
               //         salesplan.setCreateDate(Utility.getCurrentDateTime());
                        salesplan.setInteger("Ver",1);
                        salesplan.saveIt();
                        }
                    }
                  }
                }
            }
        return 1;
    }
    public List QueryPromotionBacklog(Long userId, String customerNo, String intentLevel, String contactInfo)  {
      //  List list=new ArrayList();
        StringBuffer sql= new StringBuffer();
        sql.append(" SELECT P.ITEM_ID,P.dealer_code,P.CUSTOMER_NO,P.CUSTOMER_NAME,P.CONTACTOR_NAME,P.PHONE,MOBILE,P.PRIOR_GRADE,P.SOLD_BY,D.INTENT_BRAND," +
                " D.INTENT_SERIES,D.INTENT_MODEL,D.INTENT_CONFIG,D.INTENT_COLOR,P.LAST_SCENE,P.SCHEDULE_DATE,P.ACTION_DATE,P.PROM_WAY,P.PROM_CONTENT," +
                " P.PROM_RESULT,P.REAL_PROM_WAY,CASE WHEN (current DATE-DATE(P.SCHEDULE_DATE))>=1 THEN 'B' WHEN (current DATE-DATE(P.SCHEDULE_DATE))>=0 THEN 'A' " +
                " ELSE 'C' END AS REMIND_COLOR " +
                " FROM TT_SALES_PROMOTION_PLAN P " +
                " LEFT join  TT_CUSTOMER_INTENT i  on  i.dealer_code=p.dealer_code and p.customer_no=i.customer_no and i.intent_id=p.intent_id" +
                " LEFT join  TT_CUSTOMER_INTENT_DETAIL d on d.dealer_code=p.dealer_code and d.INTENT_ID=i.INTENT_ID and d.IS_MAIN_MODEL=12781001  " +
                " LEFT join  TM_POTENTIAL_CUSTOMER Q on Q.dealer_code=p.dealer_code and Q.customer_no=P.customer_no " +
                " WHERE P.ENTITY_CODE='"+FrameworkUtil.getLoginInfo().getDealerCode()+"' AND P.SOLD_BY="+userId+" AND Q.SOLD_BY="+userId+" AND (P.PROM_RESULT IS NULL OR P.PROM_RESULT=0) ");
        if(!StringUtils.isNullOrEmpty(customerNo)){
            sql.append(" AND P.CUSTOMER_NO='"+customerNo+"' ");
        }
        if (!StringUtils.isNullOrEmpty(intentLevel)) {
            sql.append(" AND Q.INTENT_LEVEL="+intentLevel+" ");
        }
        if (!StringUtils.isNullOrEmpty(contactInfo)) {
            sql.append(" AND (Q.CONTACTOR_MOBILE LIKE '%"+contactInfo+"%'");
            sql.append(" OR Q.CONTACTOR_PHONE LIKE '%"+contactInfo+"%')");
        }
        sql.append("  ORDER BY REMIND_COLOR ");
        List<Object> params = new ArrayList<Object>();
        List list = DAOUtil.findAll(sql.toString(), params);
        return list;
    }
}
