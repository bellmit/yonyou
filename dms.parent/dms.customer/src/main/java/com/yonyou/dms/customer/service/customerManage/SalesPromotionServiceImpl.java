
/** 
*Copyright 2016 Yonyou Corporation Ltd. All Rights Reserved.
* This software is published under the terms of the Yonyou Software
* License version 1.0, a copy of which has been included with this
* distribution in the LICENSE.txt file.
*
* @Project Name : dms.customer
*
* @File name : SalesPromotionServiceImpl.java
*
* @Author : zhanshiwei
*
* @Date : 2016年9月12日
*
----------------------------------------------------------------------------------
*     Date       Who       Version     Comments
* 1. 2016年9月12日    zhanshiwei    1.0
*
*
*
*
----------------------------------------------------------------------------------
*/

package com.yonyou.dms.customer.service.customerManage;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.javalite.activejdbc.Base;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yonyou.dms.common.domains.DTO.basedata.TtBigCustomerVisitingIntentDTO;
import com.yonyou.dms.common.domains.DTO.basedata.TtSalesPromotionPlanDTO;
import com.yonyou.dms.common.domains.DTO.monitor.OperateLogDto;
import com.yonyou.dms.common.domains.PO.basedata.PotentialCusPO;
import com.yonyou.dms.common.domains.PO.basedata.SalesPromotionPO;
import com.yonyou.dms.common.domains.PO.basedata.TrackingTaskPO;
import com.yonyou.dms.common.domains.PO.basedata.TtBigCustomerVisitingIntentPO;
import com.yonyou.dms.common.domains.PO.basedata.TtCusIntentPO;
import com.yonyou.dms.common.domains.PO.basedata.TtCustomerIntentDetailPO;
import com.yonyou.dms.common.domains.PO.basedata.TtPoCusLinkmanPO;
import com.yonyou.dms.common.domains.PO.basedata.TtSalesPromotionPlanDPO;
import com.yonyou.dms.common.domains.PO.basedata.TtSalesPromotionPlanPO;
import com.yonyou.dms.common.domains.PO.basedata.VisitingRecordPO;
import com.yonyou.dms.common.service.monitor.OperateLogService;
import com.yonyou.dms.customer.domains.DTO.customerManage.SalesPromotionDTO;
import com.yonyou.dms.framework.DAO.DAOUtil;
import com.yonyou.dms.framework.DAO.DAOUtilGF;
import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.framework.domain.LoginInfoDto;
import com.yonyou.dms.framework.service.CommonNoService;
import com.yonyou.dms.framework.util.FrameworkUtil;
import com.yonyou.dms.framework.util.bean.ApplicationContextHelper;
import com.yonyou.dms.function.common.DictCodeConstants;
import com.yonyou.dms.function.exception.ServiceBizException;
import com.yonyou.dms.function.utils.common.DateUtil;
import com.yonyou.dms.function.utils.common.StringUtils;
import com.yonyou.dms.gacfca.SOTDCS007;
import com.yonyou.dms.gacfca.SOTDCS007Impl;
import com.yonyou.dms.gacfca.SOTDCS013;

/**
 * 潜客跟进
 * 
 * @author zhanshiwei
 * @date 2016年9月12日
 */
@SuppressWarnings("rawtypes")
@Service
public class SalesPromotionServiceImpl implements SalesPromotionService {
    private static final Logger logger = LoggerFactory.getLogger(SalesPromotionServiceImpl.class);
    @Autowired
    SOTDCS007 sotdcs007;
    @Autowired
    private TrackingTaskService trackingtaskservice;
    @Autowired
    private CommonNoService     commonNoService;
    @Autowired
    private OperateLogService operateLogService;
    @Autowired
    private SOTDCS013 sotdcs013;
    private String ITEM_PROM_RESULT="";
    private String gzflag="";
 
    @Override
    public List<Map> queryFollowHistoryList(String id) throws ServiceBizException {
        LoginInfoDto loginInfo = ApplicationContextHelper.getBeanByType(LoginInfoDto.class);
        StringBuffer sb = new StringBuffer();
        sb.append(

                  "SELECT P.VER,\n" +
                  "       0 AS IS_SELECT,\n" + 
                  "       0 AS SAMEGRADE,\n" + 
                  "       P.ITEM_ID,\n" + 
                  "       P.DEALER_CODE,\n" + 
                  "       P.CUSTOMER_NO,\n" + 
                  "       P.INTENT_ID,\n" + 
                  "       P.CUSTOMER_NAME,\n" + 
                  "       P.CONTACTOR_NAME,\n" + 
                  "       P.PHONE,\n" + 
                  "       P.MOBILE,\n" + 
                  "       P.PRIOR_GRADE,\n" + 
                  "       P.NEXT_GRADE,\n" + 
                  "       P.SCHEDULE_DATE,\n" + 
                  "       P.ACTION_DATE,\n" + 
                  "       P.PROM_WAY,\n" +
                  "       P.CREATE_TYPE,\n" + 
                  "       P.PROM_CONTENT,\n" + 
                  "       P.PROM_RESULT,\n" + 
                  "       P.SOLD_BY,\n" +
                  "       TE.USER_ID,\n" + 
                  "       TE.USER_NAME,\n" + 
                  "       P.BOOKING_CUSTOMER_TYPE,\n" + 
                  "       P.BOOKING_DATE,\n" + 
                  "       case when P.BOOKING_CUSTOMER_TYPE = 12781001 then p.next_BOOKING_DATE else null end next_BOOKING_DATE ,\n" + 
                  "       P.SCENE,\n" + 
                  "       P.AUDITED_BY,\n" + 
                  "       P.AUDITING_REMARK,\n" + 
                  "       P.IS_AUDITING,\n" + 
                  "       P.NEXT_PROM_DATE,\n" + 
                  "       P.REAL_VISIT_ACTION,\n" +//add by lim  9个字段
                  "       P.REAL_CONTACTOR_NAME,\n" +
                  "       P.RELATED_ACTIVITY,\n" +
                  "       P.IS_SUCCESS_BOOKING,\n" +
                  "       P.REAL_ACTIVITY,\n" +
                  "       P.VISIT_ACTION,\n" +
                  "       P.REAL_PROM_WAY,\n" +
                  "       P.DRIVE_PLAN_ID,\n" +
                  "       P.NEXT_VISIT_ACTION,\n" +
                  "       P.NEXT_PROM_CONTENT,\n" +// over
                  "       P.BUY_CYCLE_P,\n" +
                  "       P.BUY_CYCLE_S,\n" +
                  "       P.BUY_CYCLE_I,\n" +
                  "       P.BUY_CYCLE_X,\n" +
                  "       P.BUY_CYCLE_T,\n" +
                  "       P.BUY_CYCLE_A,\n" +
                  "       P.BUY_CYCLE_C,\n" +
                  "       P.BUY_CYCLE_L,\n" +
                  "       P.BUY_CYCLE_O,\n" +
                  "       P.BUY_CYCLE_D,\n" +
                  "       P.RECEPTION_ID,\n" +// over  TT_CAMPAIGN_PLAN
                  "       CA1.CAMPAIGN_NAME AS REAL_ACTIVITY_NAME ,\n" +
                  "       CA2.CAMPAIGN_NAME AS RELATED_ACTIVITY_NAME ,\n" +
                  "       0 AS LAST_PROM_RESULT,\n" + 
                  "       '' AS FIRST_SCENE\n" + 
                  ",E.INTENT_BRAND,E.INTENT_SERIES,E.INTENT_MODEL,E.BRAND_NAME,E.SERIES_NAME,E.MODEL_NAME,E.INTENT_CONFIG,E.INTENT_COLOR,p.last_scene,cc.intent_level,cc.ADDRESS,cc.CERTIFICATE_NO,cc.CT_CODE,cc.LMS_REMARK,cc.IS_BIG_CUSTOMER,'' AS IS_CURRENT_RECORD \n" + 
                  "  FROM TT_SALES_PROMOTION_PLAN P\n" + 
                  "  inner join\n" + 
                  "(SELECT      a.customer_no,a.DEALER_CODE,b.INTENT_BRAND,BR.BRAND_NAME,b.INTENT_SERIES,SE.SERIES_NAME,\n" + 
                  "      b.INTENT_MODEL,MO.MODEL_NAME,b.INTENT_CONFIG,b.INTENT_COLOR,b.IS_MAIN_MODEL,A.INTENT_ID\n" + 
                  "      FROM TT_CUSTOMER_INTENT A   inner JOIN TT_CUSTOMER_INTENT_DETAIL B ON A.\n" + 
                  "      DEALER_CODE=B.DEALER_CODE\n" + 
                  "      AND A.INTENT_ID=B.INTENT_ID\n" + 
                  "      and  b.IS_MAIN_MODEL=12781001 "+
                  " LEFT JOIN  TM_MODEL MO ON  b.INTENT_MODEL=MO.MODEL_CODE AND b.DEALER_CODE=MO.DEALER_CODE\n "+
                  " LEFT  JOIN   TM_SERIES  SE   ON   b.INTENT_SERIES=SE.SERIES_CODE AND b.DEALER_CODE=SE.DEALER_CODE\n"+
                  " LEFT  JOIN   TM_BRAND   BR   ON   b.INTENT_BRAND = BR.BRAND_CODE AND b.DEALER_CODE=BR.DEALER_CODE)  E  on E.DEALER_CODE=p.DEALER_CODE\n" + 
                  "      AND E.INTENT_ID=p.INTENT_ID\n" +
                  " LEFT JOIN TT_CAMPAIGN_PLAN CA1 ON CA1.DEALER_CODE=P.DEALER_CODE AND CA1.CAMPAIGN_CODE=P.REAL_ACTIVITY" +
                  " LEFT JOIN TT_CAMPAIGN_PLAN CA2 ON CA2.DEALER_CODE=P.DEALER_CODE AND CA2.CAMPAIGN_CODE=P.RELATED_ACTIVITY" +
                  " LEFT JOIN TM_USER TE ON TE.DEALER_CODE=P.DEALER_CODE AND P.SOLD_BY=TE.USER_ID" +
                  " LEFT JOIN TM_POTENTIAL_CUSTOMER Cc ON CC.DEALER_CODE=P.DEALER_CODE AND CC.CUSTOMER_NO=P.CUSTOMER_NO" +
                  " WHERE P.DEALER_CODE = '"+ loginInfo.getDealerCode() + "'\n" + 
                  "   AND P.CUSTOMER_NO= ? AND P.D_KEY = " + DictCodeConstants.D_KEY + "\n" 
          
          );
        List<Object> queryList = new ArrayList<Object>();
        queryList.add(id);
        return Base.findAll(sb.toString(), queryList.toArray());
    }
    
    @Override
    public void auditSalesPromotionPlan(TtSalesPromotionPlanDTO salesProDto) throws ServiceBizException {
        String[] ids = salesProDto.getAuditList().split(",");
        LoginInfoDto loginInfo = ApplicationContextHelper.getBeanByType(LoginInfoDto.class);
        for (int i = 0; i < ids.length; i++) {
            long id = Long.parseLong(ids[i].toString());
            TtSalesPromotionPlanPO salesProPO = TtSalesPromotionPlanPO.findById(id);
            if(!StringUtils.isNullOrEmpty(salesProPO)){
                salesProPO.setString("AUDITING_REMARK", salesProDto.getAuditingRemark());
                salesProPO.setString("AUDITED_BY", String.valueOf(loginInfo.getUserId()));
                salesProPO.setInteger("IS_AUDITING",DictCodeConstants.STATUS_IS_YES);
                salesProPO.saveIt();
                
            }
        }
        
        
    }

    @Override
    public void modifySoldBy(TtSalesPromotionPlanDTO salesProDto) throws ServiceBizException {
        String[] ids = salesProDto.getNoList().split(",");
        LoginInfoDto loginInfo = ApplicationContextHelper.getBeanByType(LoginInfoDto.class);
        for (int i = 0; i < ids.length; i++) {
            long id = Long.parseLong(ids[i].toString());
            TtSalesPromotionPlanPO salesProPO = TtSalesPromotionPlanPO.findById(id);
            if(salesProPO!=null){
            PotentialCusPO potentialCusPo=PotentialCusPO.findByCompositeKeys(loginInfo.getDealerCode(),salesProPO.getString("CUSTOMER_NO"));
            PotentialCusPO potentialCusPo1=PotentialCusPO.findByCompositeKeys(loginInfo.getDealerCode(),salesProPO.getString("CUSTOMER_NO"));
            String[] customerNo = {salesProPO.getString("CUSTOMER_NO")};
            potentialCusPo.setString("SOLD_BY", salesProDto.getSoldBy());// 销售顾问
            List<Object> salesPromotionList = new ArrayList<Object>();
            salesPromotionList.add(salesProPO.getString("CUSTOMER_NO"));
            salesPromotionList.add(loginInfo.getDealerCode());
            salesPromotionList.add(Integer.parseInt(DictCodeConstants.D_KEY));
            List<TtSalesPromotionPlanPO> salesPromotionPO=TtSalesPromotionPlanPO.findBySQL("select * from TT_SALES_PROMOTION_PLAN where  CUSTOMER_NO= ? AND DEALER_CODE= ? AND D_KEY= ? ", salesPromotionList.toArray());
            System.out.println("salesPromotionPO"+salesPromotionPO.size());
            // CustomerTrackingPO traPo = CustomerTrackingPO.findById(id);\
            if(salesPromotionPO!=null&&salesPromotionPO.size()>0){
                for(int j=0;j<salesPromotionPO.size();j++){
                    TtSalesPromotionPlanPO salesPromotion = salesPromotionPO.get(j);
                    if(!StringUtils.isNullOrEmpty(salesPromotion)){
                        salesPromotion.setString("SOLD_BY", salesProDto.getSoldBy());// 销售顾问
                        salesPromotion.setString("OWNED_BY", salesProDto.getSoldBy());
                        salesPromotion.saveIt();
                    }
                }
            }
 /*           List<TtSalesPromotionPlanPO> PromotionPO=TtSalesPromotionPlanPO.findBySQL("select * from TT_SALES_PROMOTION_PLAN where  CUSTOMER_NO= ? AND DEALER_CODE= ? AND D_KEY= ? AND SCHEDULE_DATE>=CURRENT_DATE AND (PROM_RESULT IS NULL OR PROM_RESULT = 0 OR PROM_RESULT =13341009 ) ", salesPromotionList.toArray());
            System.out.println("PromotionPO"+PromotionPO.size());
            if(PromotionPO!=null&&PromotionPO.size()>0){
                for(int j=0;j<PromotionPO.size();j++){
                    TtSalesPromotionPlanPO salesPromotion = PromotionPO.get(j);
                    if(!StringUtils.isNullOrEmpty(salesPromotion)){
                        salesPromotion.setString("SOLD_BY", salesProDto.getSoldBy());// 销售顾问
                        salesPromotion.setString("OWNED_BY", salesProDto.getSoldBy());
                        salesPromotion.saveIt();
                    }
                }
            }*/
         // D级潜在客户使用再分配功能将同时分配潜客相同的保有客户
/*            List<Object> tmPoCusList = new ArrayList<Object>();
            tmPoCusList.add(salesProPO.getString("CUSTOMER_NO"));
            tmPoCusList.add(loginInfo.getDealerCode());
            tmPoCusList.add(Integer.parseInt(DictCodeConstants.D_KEY));
            tmPoCusList.add(Integer.parseInt(DictCodeConstants.DICT_INTENT_LEVEL_D));
            List<PotentialCusPO> tmPoCusPO =PotentialCusPO.findBySQL("select * from TM_POTENTIAL_CUSTOMER where  CUSTOMER_NO= ? AND DEALER_CODE= ? AND D_KEY= ? AND INTENT_LEVEL= ? ", tmPoCusList.toArray());
            System.out.println("CR关怀记录做相应修改"+tmPoCusPO.size());
            if(tmPoCusPO!=null&&tmPoCusPO.size()>0){
               
                List<Map> result=queryCusNoByPoCusNo(salesProPO.getString("CUSTOMER_NO"), loginInfo.getDealerCode());
                if(!StringUtils.isNullOrEmpty(result)){
                    String tmpCusNo = String.valueOf(result.get(0).get("CUSTOMER_NO"));
                    String soldBy = String.valueOf(result.get(0).get("SOLD_BY"));
                    List<Object> CusList = new ArrayList<Object>();
                    CusList.add(tmpCusNo);
                    CusList.add(loginInfo.getDealerCode());
                    CusList.add(Integer.parseInt(DictCodeConstants.D_KEY));                    
                    List<CustomerPO> tmCusPO =CustomerPO.findBySQL("select * from TM_CUSTOMER where  CUSTOMER_NO= ? AND DEALER_CODE= ? AND D_KEY= ? ", CusList.toArray());
                    if(!StringUtils.isNullOrEmpty(tmCusPO)&&tmCusPO.size()>0) {
                        for(int k =0;k<tmCusPO.size();k++){
                            CustomerPO PO = tmCusPO.get(k);
                            PO.setString("SOLD_BY", salesProDto.getSoldBy());// 销售顾问
                            PO.setString("OWNED_BY", salesProDto.getSoldBy());
                            PO.setString("LAST_SOLD_BY", soldBy);
                            PO.saveIt();
                            
                        }
                    }
                    
                 // CR关怀记录做相应修改
                List<TtSalesCrPO> salesCrPO=TtSalesCrPO.findBySQL("select * from TT_SALES_CR where  CUSTOMER_NO= ? AND DEALER_CODE= ? AND D_KEY= ? AND SCHEDULE_DATE>=CURRENT_DATE AND (CR_RESULT IS NULL OR CR_RESULT = 0) ", salesPromotionList.toArray());
                      if(salesCrPO!=null&&salesCrPO.size()>0){
                         
                          for(int a=0;a<salesCrPO.size();a++){
                            
                              TtSalesCrPO salesCr = salesCrPO.get(a);
                              salesCr.setString("SOLD_BY", salesProDto.getSoldBy());// 销售顾问
                              salesCr.setString("OWNED_BY", salesProDto.getSoldBy());
                              salesCr.saveIt();
                          }
                      }
                }
            }*/
            System.out.println("11111111111111122");
                System.out.println("111111111111111223");
                potentialCusPo.setString("FAIL_CONSULTANT", potentialCusPo1.getString("SOLD_BY"));
                potentialCusPo.setInteger("IS_UPLOAD", DictCodeConstants.STATUS_IS_NOT);
                potentialCusPo.setString("OWNED_BY", salesProDto.getSoldBy());
                potentialCusPo.setDate("CONSULTANT_TIME", new Date());
                potentialCusPo.saveIt();
                
                sotdcs013.getSOTDCS013(customerNo);
           
            }
        }
        
        
    }
    public List<Map> queryCusNoByPoCusNo(String poCusNo,String entityCode){
        StringBuffer sql = new StringBuffer();
        sql.append("SELECT A.CUSTOMER_NO, A.SOLD_BY FROM");
        sql.append(" TM_CUSTOMER A INNER JOIN TT_PO_CUS_RELATION B");
        sql.append(" ON A.DEALER_CODE = B.DEALER_CODE");
        sql.append(" AND A.CUSTOMER_NO = B.CUSTOMER_NO");
        sql.append(" WHERE 1 = 1");
        sql.append(" AND A.DEALER_CODE = '" + entityCode + "'");
        sql.append(" AND A.D_KEY = " + DictCodeConstants.D_KEY);
        sql.append(" AND B.PO_CUSTOMER_NO = '" + poCusNo + "'");
        List<Object> queryParams = new ArrayList<Object>();
        return Base.findAll(sql.toString(), queryParams.toArray());
    }

    /**
     * 潜客跟进查询
     * 
     * @author LGQ
     * @date 2016年1月3日
     * @param queryParam
     * @return
     * @throws ServiceBizException (non-Javadoc)
     * @see com.yonyou.dms.customer.service.customerManage.SalesPromotionService#querySalesPromotionInfo(java.util.Map)
     */

    @Override
    public PageInfoDto querySalesPromotionInfo(Map<String, String> queryParam,int flag) throws ServiceBizException {
        LoginInfoDto loginInfo = ApplicationContextHelper.getBeanByType(LoginInfoDto.class);
        StringBuffer sb = new StringBuffer();
        sb.append("SELECT P.VER,0 AS IS_SELECT,0 AS SAMEGRADE,P.CREATED_AT,P.ITEM_ID,P.DEALER_CODE,P.CUSTOMER_NO,P.INTENT_ID,P.CUSTOMER_NAME,P.CONTACTOR_NAME,\n"); 
        sb.append("P.PHONE,P.MOBILE,P.PRIOR_GRADE,P.NEXT_GRADE,P.SCHEDULE_DATE,P.ACTION_DATE,P.PROM_WAY,P.CREATE_TYPE,P.PROM_CONTENT,P.PROM_RESULT,\n"); 
        sb.append("P.SOLD_BY,TE.USER_NAME,TE.USER_ID,P.BOOKING_CUSTOMER_TYPE,P.BOOKING_DATE,case when P.BOOKING_CUSTOMER_TYPE = 12781001 then p.next_BOOKING_DATE else null end next_BOOKING_DATE ,\n"); 
        sb.append("P.SCENE,P.AUDITED_BY,P.AUDITING_REMARK,P.IS_AUDITING,P.NEXT_PROM_DATE,P.REAL_VISIT_ACTION,P.REAL_CONTACTOR_NAME,P.RELATED_ACTIVITY,\n");
        sb.append("P.IS_SUCCESS_BOOKING,P.REAL_ACTIVITY,P.VISIT_ACTION,P.REAL_PROM_WAY,P.DRIVE_PLAN_ID,P.NEXT_VISIT_ACTION,P.NEXT_PROM_CONTENT,P.BUY_CYCLE_P,\n");
        sb.append("P.BUY_CYCLE_S,P.BUY_CYCLE_I,P.BUY_CYCLE_X,P.BUY_CYCLE_T,P.BUY_CYCLE_A,P.BUY_CYCLE_C,P.BUY_CYCLE_L,P.BUY_CYCLE_O,P.BUY_CYCLE_D,\n" );
        sb.append("P.RECEPTION_ID,CA1.CAMPAIGN_NAME AS REAL_ACTIVITY_NAME ,CA2.CAMPAIGN_NAME AS RELATED_ACTIVITY_NAME ,0 AS LAST_PROM_RESULT,'' AS FIRST_SCENE\n"); 
        sb.append(",E.INTENT_BRAND,E.INTENT_SERIES,E.INTENT_MODEL,E.BRAND_NAME,E.SERIES_NAME,E.MODEL_NAME,E.INTENT_CONFIG,E.INTENT_COLOR,p.last_scene,cc.intent_level,cc.ADDRESS,cc.CERTIFICATE_NO,cc.CT_CODE,cc.LMS_REMARK,cc.IS_BIG_CUSTOMER,'' AS IS_CURRENT_RECORD \n"); 
        sb.append(" FROM TT_SALES_PROMOTION_PLAN P  inner join (SELECT      a.customer_no,a.DEALER_CODE,b.INTENT_BRAND,BR.BRAND_NAME,b.INTENT_SERIES,SE.SERIES_NAME,b.INTENT_MODEL,MO.MODEL_NAME,b.INTENT_CONFIG,b.INTENT_COLOR,b.IS_MAIN_MODEL,A.INTENT_ID\n"); 
        sb.append(" FROM TT_CUSTOMER_INTENT A   inner JOIN TT_CUSTOMER_INTENT_DETAIL B ON A.DEALER_CODE=B.DEALER_CODE  AND A.INTENT_ID=B.INTENT_ID and  b.IS_MAIN_MODEL=12781001 ");
        sb.append(" LEFT  JOIN   TM_BRAND   BR   ON   b.INTENT_BRAND = BR.BRAND_CODE AND b.DEALER_CODE=BR.DEALER_CODE LEFT  JOIN   TM_SERIES  SE   ON   b.INTENT_SERIES=SE.SERIES_CODE AND SE.BRAND_CODE=BR.BRAND_CODE AND b.DEALER_CODE=SE.DEALER_CODE LEFT JOIN  TM_MODEL MO ON  b.INTENT_MODEL=MO.MODEL_CODE AND MO.BRAND_CODE=SE.BRAND_CODE AND MO.SERIES_CODE=SE.SERIES_CODE AND b.DEALER_CODE=MO.DEALER_CODE)  E  on E.DEALER_CODE=p.DEALER_CODE\n" ); 
        sb.append("      AND E.INTENT_ID=p.INTENT_ID\n" );
        sb.append(" LEFT JOIN TT_CAMPAIGN_PLAN CA1 ON CA1.DEALER_CODE=P.DEALER_CODE AND CA1.CAMPAIGN_CODE=P.REAL_ACTIVITY" );
        sb.append( " LEFT JOIN TT_CAMPAIGN_PLAN CA2 ON CA2.DEALER_CODE=P.DEALER_CODE AND CA2.CAMPAIGN_CODE=P.RELATED_ACTIVITY" );
        sb.append(" LEFT JOIN TM_USER TE ON TE.DEALER_CODE=P.DEALER_CODE AND P.SOLD_BY=TE.USER_ID" );
        sb.append(" LEFT JOIN TM_POTENTIAL_CUSTOMER Cc ON CC.DEALER_CODE=P.DEALER_CODE AND CC.CUSTOMER_NO=P.CUSTOMER_NO");
        sb.append(" WHERE P.DEALER_CODE = '"+ loginInfo.getDealerCode() + "'\n"); 
        sb.append("   AND P.D_KEY = " + DictCodeConstants.D_KEY + "\n");
        List<Object> queryList = new ArrayList<Object>();
        this.setWhere(sb, queryParam, queryList,flag);
        PageInfoDto id = DAOUtil.pageQuery(sb.toString(), queryList);
        return id;
    }
    
    

    
    /**
    * @author LiGaoqi
    * @date 2017年2月27日
    * @param queryParam
    * @return
    * @throws ServiceBizException
    * (non-Javadoc)
    * @see com.yonyou.dms.customer.service.customerManage.SalesPromotionService#queryPotentialfollowforExport(java.util.Map)
    */
    	
    @Override
    public List<Map> queryPotentialfollowforExport(Map<String, String> queryParam,int flag) throws ServiceBizException {
        LoginInfoDto loginInfo = ApplicationContextHelper.getBeanByType(LoginInfoDto.class);
        StringBuffer sb = new StringBuffer();
        sb.append("SELECT P.VER,0 AS IS_SELECT,0 AS SAMEGRADE,P.CREATED_AT,P.ITEM_ID,P.DEALER_CODE,P.CUSTOMER_NO,P.INTENT_ID,P.CUSTOMER_NAME,P.CONTACTOR_NAME,\n"); 
        sb.append("P.PHONE,P.MOBILE,P.PRIOR_GRADE,P.NEXT_GRADE,P.SCHEDULE_DATE,P.ACTION_DATE,P.PROM_WAY,P.CREATE_TYPE,P.PROM_CONTENT,P.PROM_RESULT,\n"); 
        sb.append("P.SOLD_BY,TE.USER_NAME,TE.USER_ID,P.BOOKING_CUSTOMER_TYPE,P.BOOKING_DATE,case when P.BOOKING_CUSTOMER_TYPE = 12781001 then p.next_BOOKING_DATE else null end next_BOOKING_DATE ,\n"); 
        sb.append("P.SCENE,P.AUDITED_BY,P.AUDITING_REMARK,P.IS_AUDITING,P.NEXT_PROM_DATE,P.REAL_VISIT_ACTION,P.REAL_CONTACTOR_NAME,P.RELATED_ACTIVITY,\n");
        sb.append("P.IS_SUCCESS_BOOKING,P.REAL_ACTIVITY,P.VISIT_ACTION,P.REAL_PROM_WAY,P.DRIVE_PLAN_ID,P.NEXT_VISIT_ACTION,P.NEXT_PROM_CONTENT,P.BUY_CYCLE_P,\n");
        sb.append("P.BUY_CYCLE_S,P.BUY_CYCLE_I,P.BUY_CYCLE_X,P.BUY_CYCLE_T,P.BUY_CYCLE_A,P.BUY_CYCLE_C,P.BUY_CYCLE_L,P.BUY_CYCLE_O,P.BUY_CYCLE_D,\n" );
        sb.append("P.RECEPTION_ID,CA1.CAMPAIGN_NAME AS REAL_ACTIVITY_NAME ,CA2.CAMPAIGN_NAME AS RELATED_ACTIVITY_NAME ,0 AS LAST_PROM_RESULT,'' AS FIRST_SCENE\n"); 
        sb.append(",E.INTENT_BRAND,E.INTENT_SERIES,E.INTENT_MODEL,E.BRAND_NAME,E.SERIES_NAME,E.MODEL_NAME,E.INTENT_CONFIG,E.INTENT_COLOR,p.last_scene,cc.intent_level,cc.ADDRESS,cc.CERTIFICATE_NO,cc.CT_CODE,cc.LMS_REMARK,cc.IS_BIG_CUSTOMER,'' AS IS_CURRENT_RECORD \n"); 
        sb.append(" FROM TT_SALES_PROMOTION_PLAN P  inner join (SELECT      a.customer_no,a.DEALER_CODE,b.INTENT_BRAND,BR.BRAND_NAME,b.INTENT_SERIES,SE.SERIES_NAME,b.INTENT_MODEL,MO.MODEL_NAME,b.INTENT_CONFIG,b.INTENT_COLOR,b.IS_MAIN_MODEL,A.INTENT_ID\n"); 
        sb.append(" FROM TT_CUSTOMER_INTENT A   inner JOIN TT_CUSTOMER_INTENT_DETAIL B ON A.DEALER_CODE=B.DEALER_CODE  AND A.INTENT_ID=B.INTENT_ID and  b.IS_MAIN_MODEL=12781001 ");
        sb.append(" LEFT  JOIN   TM_BRAND   BR   ON   b.INTENT_BRAND = BR.BRAND_CODE AND b.DEALER_CODE=BR.DEALER_CODE LEFT  JOIN   TM_SERIES  SE   ON   b.INTENT_SERIES=SE.SERIES_CODE AND SE.BRAND_CODE=BR.BRAND_CODE AND b.DEALER_CODE=SE.DEALER_CODE LEFT JOIN  TM_MODEL MO ON  b.INTENT_MODEL=MO.MODEL_CODE AND MO.BRAND_CODE=SE.BRAND_CODE AND MO.SERIES_CODE=SE.SERIES_CODE AND b.DEALER_CODE=MO.DEALER_CODE)  E  on E.DEALER_CODE=p.DEALER_CODE\n" ); 
        sb.append("      AND E.INTENT_ID=p.INTENT_ID\n" );
        sb.append(" LEFT JOIN TT_CAMPAIGN_PLAN CA1 ON CA1.DEALER_CODE=P.DEALER_CODE AND CA1.CAMPAIGN_CODE=P.REAL_ACTIVITY" );
        sb.append( " LEFT JOIN TT_CAMPAIGN_PLAN CA2 ON CA2.DEALER_CODE=P.DEALER_CODE AND CA2.CAMPAIGN_CODE=P.RELATED_ACTIVITY" );
        sb.append(" LEFT JOIN TM_USER TE ON TE.DEALER_CODE=P.DEALER_CODE AND P.SOLD_BY=TE.USER_ID" );
        sb.append(" LEFT JOIN TM_POTENTIAL_CUSTOMER Cc ON CC.DEALER_CODE=P.DEALER_CODE AND CC.CUSTOMER_NO=P.CUSTOMER_NO");
        sb.append(" WHERE P.DEALER_CODE = '"+ loginInfo.getDealerCode() + "'\n"); 
        sb.append("   AND P.D_KEY = " + DictCodeConstants.D_KEY + "\n");
        List<Object> queryList = new ArrayList<Object>();
        this.setWhere(sb, queryParam, queryList,flag);
        List<Map> resultList = DAOUtil.findAll(sb.toString(), queryList);
        OperateLogDto operateLogDto=new OperateLogDto();
        operateLogDto.setOperateContent("跟进活动");
        operateLogDto.setOperateType(DictCodeConstants.LOG_CUSTOMER_MANAGEMENT);
        operateLogService.writeOperateLog(operateLogDto);
        return resultList;
    }

    public void setWhere(StringBuffer sb, Map<String, String> queryParam, List<Object> queryList,int flag) {
        LoginInfoDto loginInfo = ApplicationContextHelper.getBeanByType(LoginInfoDto.class);
      
        if (!StringUtils.isNullOrEmpty(queryParam.get("schedule_startdate"))) {
            sb.append(" and P.SCHEDULE_DATE >= ?");
            queryList.add(DateUtil.parseDefaultDate(queryParam.get("schedule_startdate")));
        }
        if (!StringUtils.isNullOrEmpty(queryParam.get("schedule_enddate"))) {
            sb.append(" and P.SCHEDULE_DATE < ?");
            queryList.add(DateUtil.addOneDay(queryParam.get("schedule_enddate")));
        }
        if (!StringUtils.isNullOrEmpty(queryParam.get("auditStatus"))) {
            sb.append(" and P.IS_AUDITING = ?");
            queryList.add(queryParam.get("auditStatus"));
        }
        if (!StringUtils.isNullOrEmpty(queryParam.get("action_startdate"))) {
            sb.append(" and P.ACTION_DATE >= ?");
            queryList.add(DateUtil.parseDefaultDate(queryParam.get("action_startdate")));
        }
        if (!StringUtils.isNullOrEmpty(queryParam.get("action_enddate"))) {
            sb.append(" and P.ACTION_DATE < ?");
            queryList.add(DateUtil.addOneDay(queryParam.get("action_enddate")));
        }
        if (!StringUtils.isNullOrEmpty(queryParam.get("priorGrade"))) {
            sb.append(" and P.PRIOR_GRADE = ?");
            queryList.add(Integer.parseInt(queryParam.get("priorGrade")));
        }
        if (!StringUtils.isNullOrEmpty(queryParam.get("nextGrade"))) {
            sb.append(" and P.NEXT_GRADE = ?");
            queryList.add(Integer.parseInt(queryParam.get("nextGrade")));
        }
        
        if (!StringUtils.isNullOrEmpty(queryParam.get("customerNo"))) {
            sb.append(" and Cc.CUSTOMER_NO like ?");
            queryList.add("%" + queryParam.get("customerNo") + "%");
        }
        if (!StringUtils.isNullOrEmpty(queryParam.get("customerName"))) {
            sb.append(" and Cc.CUSTOMER_NAME like ?");
            queryList.add("%" + queryParam.get("customerName") + "%");
        }
        if (!StringUtils.isNullOrEmpty(queryParam.get("bookingCustomerType"))) {
            sb.append(" and P.BOOKING_CUSTOMER_TYPE = ?");
            queryList.add(Integer.parseInt(queryParam.get("bookingCustomerType")));
        }
        if (!StringUtils.isNullOrEmpty(queryParam.get("intentBrand"))) {
            sb.append(" and E.INTENT_BRAND = ?");
            queryList.add(queryParam.get("intentBrand"));
        }
        if (!StringUtils.isNullOrEmpty(queryParam.get("intentSeries"))) {
            sb.append(" and E.INTENT_SERIES = ?");
            queryList.add(queryParam.get("intentSeries"));
        }
        if (!StringUtils.isNullOrEmpty(queryParam.get("intentModel"))) {
            sb.append(" and E.INTENT_MODEL = ?");
            queryList.add(queryParam.get("intentModel"));
        }
        if (!StringUtils.isNullOrEmpty(queryParam.get("intentConfig"))) {
            sb.append(" and E.INTENT_CONFIG = ?");
            queryList.add(queryParam.get("intentConfig"));
        }
        if (!StringUtils.isNullOrEmpty(queryParam.get("intentColor"))) {
            sb.append(" and E.INTENT_COLOR = ?");
            queryList.add(queryParam.get("intentColor"));
        }
        if (!StringUtils.isNullOrEmpty(queryParam.get("booking_startdate"))) {
            sb.append(" and P.BOOKING_DATE >= ?");
            queryList.add(DateUtil.parseDefaultDate(queryParam.get("booking_startdate")));
        }
        if (!StringUtils.isNullOrEmpty(queryParam.get("booking_enddate"))) {
            sb.append(" and P.BOOKING_DATE < ?");
            queryList.add(DateUtil.addOneDay(queryParam.get("booking_enddate")));
        }
        if (!StringUtils.isNullOrEmpty(queryParam.get("nextbooking_startdate"))) {
            sb.append(" and P.NEXT_BOOKING_DATE >= ?");
            queryList.add(DateUtil.parseDefaultDate(queryParam.get("nextbooking_startdate")));
        }
        if (!StringUtils.isNullOrEmpty(queryParam.get("nextbooking_enddate"))) {
            sb.append(" and P.NEXT_BOOKING_DATE < ?");
            queryList.add(DateUtil.addOneDay(queryParam.get("nextbooking_enddate")));
        }
        if (!StringUtils.isNullOrEmpty(queryParam.get("isLate"))&&queryParam.get("isLate").toString().equals(DictCodeConstants.DICT_IS_YES)) {
            String today=new SimpleDateFormat("yyyy-MM-dd").format(new Date());
            sb.append(" and P.SCHEDULE_DATE < ?");
            queryList.add(today);
        }
        //销售顾问可见客户属性只能是普通客户,大客户经理可见自己名下的大客户/普通客户
        StringBuilder sqlS = new StringBuilder("select * from tm_user_CTRL where DEALER_CODE= ? AND USER_ID= ? AND CTRL_CODE= '80900000' ");
        List<Object> bigparams = new ArrayList<Object>();
        bigparams.add(loginInfo.getDealerCode());
        bigparams.add(loginInfo.getUserId());
        List<Map> biglist = DAOUtil.findAll(sqlS.toString(), bigparams);
        if(biglist!=null&&biglist.size()>0){
            sb.append(" AND 1 = 1"); 
        }else{
            sb.append(" AND Cc.IS_BIG_CUSTOMER=" + DictCodeConstants.DICT_IS_NO); 
        }
        if (!StringUtils.isNullOrEmpty(queryParam.get("soldBy"))) {
            sb.append(" and P.SOLD_BY = ?");
            queryList.add(queryParam.get("soldBy"));
        }else{
            sb.append(DAOUtilGF.getOwnedByStr("P", loginInfo.getUserId(), loginInfo.getOrgCode(),  "45702000", loginInfo.getDealerCode()));//201004替换权限

        }
        /*
         * 如果flag 为(12781001)执行查询条件为执行状态的参数
         * 如果执行状态为 是(12781001)查询出跟进结果不为空的记录
         * 如果执行状态为 否(12781002)查询出跟进记录为空的记录
         */
        if(flag==12781001){
            if(!StringUtils.isNullOrEmpty(queryParam.get("executeStatus"))){
                if(queryParam.get("executeStatus").equals("12781001")){
                    sb.append(" AND (");
                    sb.append(" P.PROM_RESULT <> 0 AND P.PROM_RESULT IS NOT NULL");
                    sb.append(")");
                }
                if(queryParam.get("executeStatus").equals("12781002")){
                    sb.append(" AND (");
                    sb.append(" P.PROM_RESULT IS NULL OR P.PROM_RESULT=0");
                    sb.append(")");
                }
            }
           
        }
        /*
         * 如果flag 为(12781002)执行查询条件为审核状态的参数
         * 如果审核状态为 是(12781001)查询出已审核
         * 如果审核状态为 否(12781002)查询出未审核
         */
        if(flag==12781002 && !StringUtils.isNullOrEmpty(queryParam.get("executeStatus"))){
            sb.append(" AND P.PROM_RESULT IS NOT NULL AND P.PROM_RESULT<>0");
            sb.append(" AND P.IS_AUDITING= ? ");
            queryList.add(queryParam.get("executeStatus"));
        }
        if(flag==12781002 && StringUtils.isNullOrEmpty(queryParam.get("executeStatus"))){
            sb.append(" AND P.PROM_RESULT IS NOT NULL AND P.PROM_RESULT<>0");
        }
    }

    @Override
    public PageInfoDto queryBigCustomerHistoryIntent(String id) throws ServiceBizException {
        String[] ids = id.split(",");
        id = ids[0];
        StringBuffer sql=new StringBuffer();
        sql.append("SELECT");
        sql.append(" A.INTENT_BRAND,");
        sql.append(" A.INTENT_SERIES,");
        sql.append(" A.INTENT_MODEL,");
        sql.append(" A.INTENT_CONFIG,");
        sql.append(" A.INTENT_COLOR,");
        sql.append(" A.PURCHASE_COUNT,");
        sql.append(" A.COMPETITOR_BRAND,");
        sql.append(" A.INTENDING_BUY_TIME,A.DEALER_CODE,A.ITEM_ID,M.MODEL_NAME,S.SERIES_NAME,BR.BRAND_NAME,");
        sql.append(" A.INTENT_ID FROM TT_BIG_CUSTOMER_VISITING_INTENT A LEFT JOIN TT_CUSTOMER_INTENT_DETAIL B"
                + " ON A.INTENT_ID=B.INTENT_ID AND A.DEALER_CODE=B.DEALER_CODE AND B.IS_MAIN_MODEL=12781001"
                + " LEFT JOIN  TM_MODEL M ON  B.INTENT_MODEL=M.MODEL_CODE AND B.DEALER_CODE=M.DEALER_CODE"
                + " LEFT JOIN  TM_SERIES S ON B.INTENT_SERIES=S.SERIES_CODE AND B.DEALER_CODE=S.DEALER_CODE"
                + " LEFT JOIN  TM_BRAND BR ON B.INTENT_BRAND = BR.BRAND_CODE AND B.DEALER_CODE=BR.DEALER_CODE"
                + " WHERE A.ITEM_ID='"+id+"'");
        List<Object> queryList = new ArrayList<Object>();
        PageInfoDto result = DAOUtil.pageQuery(sql.toString(), queryList);
        return result;
    }
    
    public List<Map> queryProPlanJudge(long ITEM_ID,String entityCode,String cusNo) throws ServiceBizException {
        StringBuffer sql=new StringBuffer();
        sql.append(

                "SELECT (SELECT INTENT_LEVEL\n" );
        sql.append("          FROM TM_POTENTIAL_CUSTOMER\n"); 
        sql.append("         WHERE customer_no = '"+cusNo+"' AND DEALER_CODE = '"+entityCode+"')\n");
        sql.append("          AS KHJB,\n");
        sql.append("       (SELECT PRIOR_GRADE\n"); 
        sql.append("          FROM TT_SALES_PROMOTION_PLAN\n"); 
        sql.append("         WHERE customer_no = '"+cusNo+"' AND DEALER_CODE = '"+entityCode+"'\n" ); 
        sql.append("           AND ITEM_ID =\n" ); 
        sql.append("                  (SELECT max(ITEM_ID)\n" ); 
        sql.append("                     FROM TT_SALES_PROMOTION_PLAN\n"); 
        sql.append("                    WHERE customer_no = '"+cusNo+"'\n"); 
        sql.append("                      AND DEALER_CODE = '"+entityCode+"'))\n"); 
        sql.append("          AS ZHQGJ,\n"); 
        sql.append("       (SELECT NEXT_GRADE\n" ); 
        sql.append("          FROM TT_SALES_PROMOTION_PLAN\n" ); 
        sql.append("         WHERE customer_no = '"+cusNo+"' AND DEALER_CODE = '"+entityCode+"'\n"); 
        sql.append("           AND ITEM_ID =\n"); 
        sql.append("                  (SELECT max(ITEM_ID) FROM TT_SALES_PROMOTION_PLAN\n"); 
        sql.append("                    WHERE customer_no = '"+cusNo+"'\n"); 
        sql.append("                      AND DEALER_CODE = '"+entityCode+"'))\n" ); 
        sql.append("          AS ZHHGJ,(SELECT max(ITEM_ID) FROM TT_SALES_PROMOTION_PLAN WHERE customer_no = '"+cusNo+"' AND DEALER_CODE = '"+entityCode+"'  )\n"); 
        sql.append("          AS MAXITEMID,\n"); 
///00000000000

        sql.append("(SELECT PROM_RESULT\n" );
        sql.append("   FROM TT_SALES_PROMOTION_PLAN\n" ); 
        sql.append("  WHERE customer_no = '"+cusNo+"' AND DEALER_CODE = '"+entityCode+"' AND ITEM_ID = (SELECT max(ITEM_ID) FROM TT_SALES_PROMOTION_PLAN\n"); 
        sql.append("             WHERE customer_no = '"+cusNo+"' AND DEALER_CODE = '"+entityCode+"')) AS  ZHJGGJ,");
                
///111111111111111111               
//000
        sql.append("(select  PRIOR_GRADE  from   TT_SALES_PROMOTION_PLAN  where customer_no=\n");
        sql.append("'"+cusNo+"' and DEALER_CODE='"+entityCode+"' and  item_id="+ITEM_ID+") as ITEM_PRIOR_GRADE,\n");
        sql.append("(select  NEXT_GRADE  from   TT_SALES_PROMOTION_PLAN  where customer_no=\n"); 
        sql.append("'"+cusNo+"' and DEALER_CODE='"+entityCode+"' and  item_id="+ITEM_ID+") as ITEM_NEXT_GRADE,   ");   
        sql.append("(select  PROM_RESULT  from   TT_SALES_PROMOTION_PLAN  where customer_no='"+cusNo+"'\n");
        sql.append("      and DEALER_CODE='"+entityCode+"' and  item_id="+ITEM_ID+") as ITEM_PROM_RESULT FROM dual");
        List<Object> queryParams = new ArrayList<Object>();
        return Base.findAll(sql.toString(), queryParams.toArray());
    }
    
    @Override
    public void updateSalesPromotionInfo(long id, TtSalesPromotionPlanDTO salesProDto) throws ServiceBizException {
        String status = "U"; 
        System.out.println(id);
        LoginInfoDto loginInfo = ApplicationContextHelper.getBeanByType(LoginInfoDto.class);
        PotentialCusPO potentialCusPo=PotentialCusPO.findByCompositeKeys(loginInfo.getDealerCode(),salesProDto.getCustomerNo());
        if(potentialCusPo==null){
            throw new ServiceBizException("潜客"+salesProDto.getCustomerNo()+"不存在,请重新选择！");
        }else{
        	//试乘试驾新增字段保存
        	potentialCusPo.setString("IS_TEST_DRIVE", salesProDto.getIsTestDrive());
        	if("12781002".equals(salesProDto.getIsTestDrive())){            		
        		potentialCusPo.setString("TEST_DRIVE_REMARK", salesProDto.getTestDriveRemark());
        	}
        	potentialCusPo.saveIt();
        }
       
        int ver1 = potentialCusPo.getInteger("VER");
        
      //如果前台传的级别和后台不同时要提示而且不能保存
        if(!StringUtils.isNullOrEmpty(id)){
            System.out.println("如果前台传的级别和后台不同时要提示而且不能保存");
            TtSalesPromotionPlanPO promotionPlanPO =TtSalesPromotionPlanPO.findById(id);
            promotionPlanPO.setString("CUSTOMER_NO",salesProDto.getCustomerNo());
            if(potentialCusPo.getLong("IS_BIG_CUSTOMER")==12781001){
                promotionPlanPO.setLong("IS_BIG_CUSTOMER_PLAN", DictCodeConstants.IS_YES);
            }
            promotionPlanPO.setInteger("PRIOR_GRADE",salesProDto.getPriorGrade());
            promotionPlanPO.setInteger("NEXT_GRADE",salesProDto.getNextGrade());
            promotionPlanPO.setDate("SCHEDULE_DATE", salesProDto.getScheduleDate());
            promotionPlanPO.setInteger("PROM_WAY",salesProDto.getPromWay());
            promotionPlanPO.setString("PROM_CONTENT",salesProDto.getPromContent());
            promotionPlanPO.setInteger("PROM_RESULT",salesProDto.getPromResult());
            promotionPlanPO.setString("SCENE",salesProDto.getScene());
            //promotionPlanPO.setInteger("IS_AUDITING",DictCodeConstants.STATUS_IS_NOT);
            //promotionPlanPO.setString("AUDITED_BY",salesProDto.getAuditedBy());
            //promotionPlanPO.setString("AUDITING_REMARK",salesProDto.getAuditingRemark());
            if(!StringUtils.isNullOrEmpty(salesProDto.getBookingCustomerType())){
                promotionPlanPO.setInteger("BOOKING_CUSTOMER_TYPE", salesProDto.getBookingCustomerType());
                
            }else{
                promotionPlanPO.setInteger("BOOKING_CUSTOMER_TYPE", 12781002);
                
            }
            promotionPlanPO.setDate("BOOKING_DATE", salesProDto.getBookingDate());
            this.setSalesPromotionPo(promotionPlanPO, salesProDto);
            promotionPlanPO.saveIt();
            if(potentialCusPo.getLong("IS_BIG_CUSTOMER")==12781001){
                List<Object> queryList = new ArrayList<Object>();
                queryList.add(id);
                queryList.add(Integer.parseInt(DictCodeConstants.D_KEY));
                queryList.add(loginInfo.getDealerCode());
                List<TtBigCustomerVisitingIntentPO> customerVisitingIntentPO=TtBigCustomerVisitingIntentPO.findBySQL("select * from TT_BIG_CUSTOMER_VISITING_INTENT where ITEM_ID= ? AND D_KEY= ?  AND DEALER_CODE= ? ", queryList.toArray());
                if(customerVisitingIntentPO!=null&&customerVisitingIntentPO.size()>0){
                    for(int i=0;i<customerVisitingIntentPO.size();i++){
                        TtBigCustomerVisitingIntentPO customerVisitingIntent = customerVisitingIntentPO.get(i);
                        if(customerVisitingIntent!=null){
                            customerVisitingIntent.delete();
                        }
                    }
                }
                System.out.println("大客户意向明细信息新增");
                System.out.println(salesProDto.getBigCustomerIntentList().size());
                //大客户意向明细信息新增
                if(salesProDto.getBigCustomerIntentList().size()>0&&salesProDto.getBigCustomerIntentList()!=null){
                    for(TtBigCustomerVisitingIntentDTO bigIntentDTO : salesProDto.getBigCustomerIntentList()){
                        TtBigCustomerVisitingIntentPO PO = new TtBigCustomerVisitingIntentPO();
                        PO.setLong("ITEM_ID", id);
                        PO.setLong("INTENT_ID", salesProDto.getIntentId());
                        PO.setString("OWNED_BY", String.valueOf(loginInfo.getUserId()));
                        setBigCustomerVisitingIntent(bigIntentDTO,PO).saveIt();
                    }
                }
                
            }
            addAndUpdatePublicMethods(salesProDto,id,potentialCusPo,ver1,status);
            
            System.out.println("//新增展厅记录");
            if(!StringUtils.isNullOrEmpty(commonNoService.getDefalutPara("8098"))&&commonNoService.getDefalutPara("8098").equals("12781002")){
                System.out.println("//新增展厅记录");
                createAddRecord(salesProDto,loginInfo);
            }
            //更新
            if(salesProDto.getCustomerNo()!=null){
                List<Object> salesPromotionList = new ArrayList<Object>();
                salesPromotionList.add(salesProDto.getCustomerNo());
                salesPromotionList.add(loginInfo.getDealerCode());          
                TtSalesPromotionPlanPO UPromotionPO = TtSalesPromotionPlanPO.findFirst(" CUSTOMER_NO= ? AND DEALER_CODE= ? AND ACTION_DATE IS NOT NULL ORDER BY ITEM_ID DESC ",salesPromotionList.toArray());
                List<TtSalesPromotionPlanPO> salesPromotionPO=TtSalesPromotionPlanPO.findBySQL("select * from TT_SALES_PROMOTION_PLAN where  CUSTOMER_NO= ? AND DEALER_CODE= ? AND (PROM_RESULT IS NULL OR PROM_RESULT = 0) ", salesPromotionList.toArray());
                if(salesPromotionPO!=null){
                    for(int k = 0;k<salesPromotionPO.size();k++){
                        TtSalesPromotionPlanPO  salesPromotion = salesPromotionPO.get(k);
                        if(!StringUtils.isNullOrEmpty(UPromotionPO)){
                            if(!StringUtils.isNullOrEmpty(UPromotionPO.getString("SCENE"))){
                                salesPromotion.setString("LAST_SCENE",UPromotionPO.getString("SCENE"));
                            }else{
                                salesPromotion.setString("LAST_SCENE",null);
                            }
                            salesPromotion.saveIt();
                        }
                    
                    }
                }
                 
                
            }
            //ACTION(MaintainSalesPromotionPlanDetail)
            maintainSalesPromotionPlanDetail(status,salesProDto,loginInfo);
        }
        
        
    }

    /**
     * 潜客跟进新增
     * 
     * @author zhanshiwei
     * @date 2016年9月12日
     * @param salesProDto
     * @return
     * @throws ServiceBizException (non-Javadoc)
     * @see com.yonyou.dms.customer.service.customerManage.SalesPromotionService#addSalesPromotionInfo(com.yonyou.dms.customer.domains.DTO.customerManage.SalesPromotionDTO)
     */

    @Override
    public Long addSalesPromotionInfo(TtSalesPromotionPlanDTO salesProDto) throws ServiceBizException {
        try {
            String status = "A";
            LoginInfoDto loginInfo = ApplicationContextHelper.getBeanByType(LoginInfoDto.class);
            PotentialCusPO potentialCusPo=PotentialCusPO.findByCompositeKeys(loginInfo.getDealerCode(),salesProDto.getCustomerNo());
            if(potentialCusPo==null){
                throw new ServiceBizException("潜客"+salesProDto.getCustomerNo()+"不存在,请重新选择！");
            }else{
                //试乘试驾新增字段保存
                potentialCusPo.setString("IS_TEST_DRIVE", salesProDto.getIsTestDrive());
                if("12781002".equals(salesProDto.getIsTestDrive())){                    
                    potentialCusPo.setString("TEST_DRIVE_REMARK", salesProDto.getTestDriveRemark());
                }
                potentialCusPo.saveIt();
                //客户跟进任务
                int ver1 = potentialCusPo.getInteger("VER");
                List<Object> taskList = new ArrayList<Object>();
                taskList.add(salesProDto.getPriorGrade());
                taskList.add(Long.parseLong(DictCodeConstants.DICT_CUSTOMER_STATUS_LATENCY));
                taskList.add(DictCodeConstants.IS_YES);
                taskList.add(loginInfo.getDealerCode());
                List<TrackingTaskPO> pp=TrackingTaskPO.findBySQL("select * from TM_TRACKING_TASK where INTENT_LEVEL= ? AND CUSTOMER_STATUS= ? AND IS_VALID= ? AND DEALER_CODE= ? ", taskList.toArray());
                TtSalesPromotionPlanPO salesPo = new TtSalesPromotionPlanPO();
                this.setSalesPromotionPo(salesPo, salesProDto);
                if(pp!=null&&pp.size()>0){
                    TrackingTaskPO taskPo = pp.get(0);
                    salesPo.setLong("TASK_ID", taskPo.getLong("TASK_ID"));
                }
                salesPo.setString("CUSTOMER_NO",salesProDto.getCustomerNo());
                if(potentialCusPo.getLong("IS_BIG_CUSTOMER")==12781001){
                    salesPo.setLong("IS_BIG_CUSTOMER_PLAN", DictCodeConstants.IS_YES);
                }
                int ver = 1;
                salesPo.setInteger("PRIOR_GRADE",salesProDto.getPriorGrade());
                salesPo.setInteger("NEXT_GRADE",salesProDto.getNextGrade());
                salesPo.setDate("SCHEDULE_DATE", salesProDto.getScheduleDate());
                salesPo.setInteger("PROM_WAY",salesProDto.getPromWay());
                salesPo.setString("PROM_CONTENT",salesProDto.getPromContent());
                salesPo.setInteger("PROM_RESULT",salesProDto.getPromResult());
                salesPo.setString("SCENE",salesProDto.getScene());
                salesPo.setInteger("CREATE_TYPE",Integer.parseInt(DictCodeConstants.DICT_CREATE_TYPE_HANDWORK));
               /* salesPo.setInteger("IS_AUDITING",DictCodeConstants.STATUS_IS_NOT);
                salesPo.setString("AUDITED_BY",salesProDto.getAuditedBy());
                salesPo.setString("AUDITING_REMARK",salesProDto.getAuditingRemark());*/
                if(!StringUtils.isNullOrEmpty(salesProDto.getBookingCustomerType())){
                    salesPo.setInteger("BOOKING_CUSTOMER_TYPE", salesProDto.getBookingCustomerType());
                    salesPo.setDate("BOOKING_DATE", salesProDto.getBookingDate());
                }else{
                    salesPo.setInteger("BOOKING_CUSTOMER_TYPE", 12781002);
                    salesPo.setDate("BOOKING_DATE", null);
                }
                salesPo.setInteger("VER", ver);
                
                salesPo.saveIt();
                System.out.println(salesPo.getLongId());
              //大客户意向明细信息新增
                if(salesProDto.getBigCustomerIntentList().size()>0&&salesProDto.getBigCustomerIntentList()!=null){
                    System.out.println("大客户意向明细信息新增1");
                    for(TtBigCustomerVisitingIntentDTO bigIntentDTO : salesProDto.getBigCustomerIntentList()){
                        TtBigCustomerVisitingIntentPO PO = new TtBigCustomerVisitingIntentPO();
                        System.out.println("大客户意向明细信息新增2");
                        PO.setLong("ITEM_ID", salesPo.getLongId());
                        PO.setLong("INTENT_ID", salesProDto.getIntentId());
                        PO.setString("OWNED_BY", String.valueOf(loginInfo.getUserId()));
                        setBigCustomerVisitingIntent(bigIntentDTO,PO).saveIt();
                    }
                }
                System.out.println("新增结束1");
                addAndUpdatePublicMethods(salesProDto,salesPo.getLongId(),potentialCusPo,ver1,status);
                //新增展厅记录
                System.out.println("新增展厅记录1");
                System.out.println(commonNoService.getDefalutPara("8098"));
                if(!StringUtils.isNullOrEmpty(commonNoService.getDefalutPara("8098"))&&commonNoService.getDefalutPara("8098").equals("12781002")){
                    createAddRecord(salesProDto,loginInfo);
                }
                //更新
                if(salesProDto.getCustomerNo()!=null){
                    List<Object> salesPromotionList = new ArrayList<Object>();
                    salesPromotionList.add(salesProDto.getCustomerNo());
                    salesPromotionList.add(loginInfo.getDealerCode());          
                    TtSalesPromotionPlanPO UPromotionPO = TtSalesPromotionPlanPO.findFirst(" CUSTOMER_NO= ? AND DEALER_CODE= ? AND ACTION_DATE IS NOT NULL ORDER BY ITEM_ID DESC ",salesPromotionList.toArray());
                    List<TtSalesPromotionPlanPO> salesPromotionPO=TtSalesPromotionPlanPO.findBySQL("select * from TT_SALES_PROMOTION_PLAN where  CUSTOMER_NO= ? AND DEALER_CODE= ? AND (PROM_RESULT IS NULL OR PROM_RESULT = 0) ", salesPromotionList.toArray());
                    if(salesPromotionPO!=null&&!StringUtils.isNullOrEmpty(UPromotionPO)){
                        for(int k = 0;k<salesPromotionPO.size();k++){
                            TtSalesPromotionPlanPO  salesPromotion = salesPromotionPO.get(k);
                            
                            salesPromotion.setString("LAST_SCENE",UPromotionPO.getString("SCENE"));
                            salesPromotion.saveIt();
                        }
                    }
                     
                    
                }
                //ACTION(MaintainSalesPromotionPlanDetail)
                maintainSalesPromotionPlanDetail(status,salesProDto,loginInfo);
                logger.info("============================sotdcs007");
                sotdcs007.getSOTDCS007("A", salesProDto); 
                return salesPo.getLongId();
                    
            }
           
        } catch (Exception e) {
           return null;
        }
           
           
       
    }
    public TtBigCustomerVisitingIntentPO setBigCustomerVisitingIntent(TtBigCustomerVisitingIntentDTO DTO,TtBigCustomerVisitingIntentPO PO){
        PO.setString("INTENT_BRAND", DTO.getIntentBrand());
        PO.setString("INTENT_SERIES", DTO.getIntentSeries());
        PO.setString("INTENT_MODEL", DTO.getIntentModel());
        PO.setString("INTENT_CONFIG", DTO.getIntentConfig());
        PO.setString("INTENT_COLOR", DTO.getIntentColor());
        PO.setInteger("PURCHASE_COUNT", DTO.getPurchaseCount());
        PO.setInteger("INTENDING_BUY_TIME", DTO.getIntendingBuyTime());
        PO.setString("COMPETITOR_BRAND", DTO.getCompetitorBrand());
        
        return PO;
    }
    //ACTION(MaintainSalesPromotionPlanDetail)
    public void maintainSalesPromotionPlanDetail(String status,TtSalesPromotionPlanDTO salesProDto,LoginInfoDto loginInfo){
        List<Object> salesPromotionList = new ArrayList<Object>();
        salesPromotionList.add(salesProDto.getCustomerNo());
        salesPromotionList.add(loginInfo.getDealerCode());
        salesPromotionList.add(salesProDto.getSoldBy());
        salesPromotionList.add(Long.parseLong(DictCodeConstants.D_KEY));
        TtSalesPromotionPlanDPO planDetail = TtSalesPromotionPlanDPO.findFirst(" CUSTOMER_NO= ? AND DEALER_CODE= ? AND SOLD_BY= ? AND D_KEY= ? ", salesPromotionList.toArray());
        TtSalesPromotionPlanDPO planDetail2 = new TtSalesPromotionPlanDPO();
        if(planDetail!=null){
            if(!StringUtils.isNullOrEmpty(planDetail.getLong("ITEM_ID"))){
                planDetail2 = TtSalesPromotionPlanDPO.findById(planDetail.getLong("ITEM_ID"));
            }else{
                throw new ServiceBizException("系统错误！");
            }
            if("U".equals(status)||"A".equals(status)){
               if(!StringUtils.isNullOrEmpty(salesProDto.getPromResult())&&StringUtils.isNullOrEmpty(salesProDto.getScheduleDate())){
                   planDetail2.setDate("SCHEDULE_DATE", salesProDto.getScheduleDate());
                   planDetail2.setString("SCENE", salesProDto.getScene());  
               }
               planDetail2.setInteger("PROM_RESULT", salesProDto.getPromResult());
               planDetail2.saveIt();
            }
        }else{
            if("U".equals(status)||"A".equals(status)){
                planDetail2.setString("CUSTOMER_NO", salesProDto.getCustomerNo());
                if(!StringUtils.isNullOrEmpty(salesProDto.getPromResult())&&StringUtils.isNullOrEmpty(salesProDto.getScheduleDate())){
                    planDetail2.setDate("SCHEDULE_DATE", salesProDto.getScheduleDate());
                    planDetail2.setString("SCENE", salesProDto.getScene());
                    planDetail2.setInteger("UN_PROM_COUNT", 1);
                    planDetail2.setInteger("ALL_PROM_COUNT", 1);
                    planDetail2.setString("SOLD_BY", salesProDto.getSoldBy());
                    planDetail2.saveIt();
                }
            }
        }
        
        
    }
    //新增展厅记录
    public void createAddRecord(TtSalesPromotionPlanDTO salesProDto,LoginInfoDto loginInfo){
        //当跟进结果不为空的时候,如果跟进方式选择【展厅现场接待】，如果当天在展厅中没有这个客户的信息，就让系统自动在展厅记录中插入一条数据
        //具体是不是生成记录还要看[执行日期],如果执行日期为空就不处理,如果不为空,就查一下展厅里来访时间[执行日期]有没有这条记录,有就不用新增,没有就新增
        ///===================================================开始
        VisitingRecordPO PO = new VisitingRecordPO(); 
        if(!StringUtils.isNullOrEmpty(salesProDto.getActionDate())&&!StringUtils.isNullOrEmpty(salesProDto.getPromResult())&&!StringUtils.isNullOrEmpty(salesProDto.getPromWay())&&salesProDto.getPromWay()==13251002){//展厅现场接待
            List<Object> List = new ArrayList<Object>();
            List.add(salesProDto.getCustomerNo());
            List.add(loginInfo.getDealerCode());
            List.add(salesProDto.getActionDate());
            List<VisitingRecordPO> visitingPo = VisitingRecordPO.findBySQL("select * from TT_VISITING_RECORD where CUSTOMER_NO= ? AND DEALER_CODE= ? AND ACTION_DATE= ? ", List.toArray());
            if(StringUtils.isNullOrEmpty(visitingPo)&&!StringUtils.isNullOrEmpty(salesProDto.getCustomerNo())){
                
                PotentialCusPO potentialCusPo=PotentialCusPO.findByCompositeKeys(loginInfo.getDealerCode(),salesProDto.getCustomerNo());
                if(potentialCusPo!=null){
                    PO.setString("CUSTOMER_NO", potentialCusPo.getString("CUSTOMER_NO"));
                    PO.setString("CUSTOMER_NAME", potentialCusPo.getString("CUSTOMER_NAME"));
                    PO.setString("SOLD_BY", String.valueOf(loginInfo.getUserId()));//当前登录人
                    /////联系人处理==============开始
                    List<Object> linkList = new ArrayList<Object>();
                    linkList.add(salesProDto.getCustomerNo());
                    linkList.add(loginInfo.getDealerCode());
                    linkList.add(Long.parseLong(DictCodeConstants.D_KEY));
                    List<TtPoCusLinkmanPO> liknPo = TtPoCusLinkmanPO.findBySQL("select * from TT_PO_CUS_LINKMAN where CUSTOMER_NO= ? AND DEALER_CODE= ? AND D_KEY= ? ", linkList.toArray());
                    if(liknPo!=null&&liknPo.size()>0){
                        for(int i =0;i<liknPo.size();i++){
                            TtPoCusLinkmanPO link = liknPo.get(i);
                            if(link!=null){
                                if(!StringUtils.isNullOrEmpty(link.getString("CONTACTOR_NAME"))){
                                    if(link.getString("CONTACTOR_NAME").length()>10){
                                        PO.setString("CONTACTOR_NAME",link.getString("CONTACTOR_NAME").substring(0, 10));
                                    }else{
                                        PO.setString("CONTACTOR_NAME",link.getString("CONTACTOR_NAME"));
                                    }
                                }
                              //联系太长截取-------------------结束                               
                                //vrpo.setContactorName(cuslpo.getContactorName()); //联系人
                                if(!StringUtils.isNullOrEmpty(link.getString("PHONE"))){//电话
                                    PO.setString("CONTACTOR_PHONE",link.getString("PHONE"));//电话
                                }else{  
                                    //手机可能为空
                                    if(!StringUtils.isNullOrEmpty(link.getString("MOBILE"))){
                                        PO.setString("CONTACTOR_MOBILE",link.getString("MOBILE"));//手机   
                                    }else{
                                        if(!StringUtils.isNullOrEmpty(potentialCusPo.getString("CONTACTOR_PHONE"))){//电话
                                            PO.setString("CONTACTOR_PHONE",potentialCusPo.getString("CONTACTOR_PHONE"));//电话
                                        }else{
                                            PO.setString("CONTACTOR_MOBILE",potentialCusPo.getString("CONTACTOR_MOBILE"));//手机
                                        }                                       
                                    }
                                                                    
                                }
                            }
                        }
                    }else{
                        if(!StringUtils.isNullOrEmpty(potentialCusPo.getString("CUSTOMER_NAME"))){
                            if(potentialCusPo.getString("CUSTOMER_NAME").length()>10){
                                PO.setString("CONTACTOR_NAME",potentialCusPo.getString("CUSTOMER_NAME").substring(0, 10));
                            }else{
                                PO.setString("CONTACTOR_NAME",potentialCusPo.getString("CUSTOMER_NAME"));
                            }
                            if(!StringUtils.isNullOrEmpty(potentialCusPo.getString("CONTACTOR_PHONE"))){//电话
                                PO.setString("CONTACTOR_PHONE",potentialCusPo.getString("CONTACTOR_PHONE"));//电话
                            }else{
                                PO.setString("CONTACTOR_MOBILE",potentialCusPo.getString("CONTACTOR_MOBILE"));//手机
                            } 
                        }
                        
                    }
                    //联系人处理==============结束
                    PO.setInteger("VISITOR", 1);
                    PO.setInteger("VISIT_TYPE", Integer.valueOf(DictCodeConstants.DICT_VISIT_TYPE_IN_BODY));
                    PO.setInteger("INTENT_LEVEL", potentialCusPo.getInteger("INTENT_LEVEL"));
                    PO.setString("SCENE",salesProDto.getScene());
                    PO.setDate("VISIT_TIME", salesProDto.getActionDate());
                    PO.setInteger("CUS_SOURCE", potentialCusPo.getInteger("CUS_SOURCE"));
                    PO.setInteger("MEDIA_TYPE", potentialCusPo.getInteger("MEDIA_TYPE"));
                    PO.setInteger("IS_FIRST_VISIT", DictCodeConstants.STATUS_IS_NOT);
                    PO.setString("CAMPAIGN_CODE",potentialCusPo.getString("CAMPAIGN_CODE"));
                    PO.setLong("INTENT_ID",potentialCusPo.getLong("INTENT_ID"));
                    //还有就是如果有意向车系的时候,意向车系有多个的时候 取跟客户意向那一条
                    System.out.println("还有就是如果有意向车系的时候,意向车系有多个的时候 取跟客户意向那一条");
                    //取车系 车型  配置  颜色==========================开始
                    if(!StringUtils.isNullOrEmpty(potentialCusPo.getLong("INTENT_ID"))){
                        List<Object> queryList = new ArrayList<Object>();
                        queryList.add(potentialCusPo.getLong("INTENT_ID"));
                        queryList.add( loginInfo.getDealerCode());          
                        TtCustomerIntentDetailPO intentDetailpo = TtCustomerIntentDetailPO.findFirst(" INTENT_ID= ? AND DEALER_CODE= ? ",queryList.toArray());  
                        if(intentDetailpo!=null){
                          //车系
                            if (intentDetailpo.getString("INTENT_SERIES")!=null){
                                PO.setString("INTENT_SERIES",intentDetailpo.getString("INTENT_SERIES"));                                                   
                            }
                            //车型
                            if (intentDetailpo.getString("INTENT_MODEL")!=null){
                                PO.setString("INTENT_MODEL",intentDetailpo.getString("INTENT_MODEL"));                                                 
                            }   
                            //配置
                            if (intentDetailpo.getString("INTENT_CONFIG")!=null ){
                                PO.setString("INTENT_CONFIG",intentDetailpo.getString("INTENT_CONFIG"));        
                                                                             
                            }
                            //颜色
                            if (intentDetailpo.getString("COLOR_CODE")!=null){
                                PO.setString("COLOR_CODE",intentDetailpo.getString("COLOR_CODE"));                                                   
                            }
                        }
//                      取车系 车型  配置  颜色==========================结束
                        ///==================取试乘试驾==============开始
                        List<Object> IntentqueryList = new ArrayList<Object>();
                        IntentqueryList.add(salesProDto.getCustomerNo());
                        IntentqueryList.add(potentialCusPo.getLong("INTENT_ID"));
                        IntentqueryList.add(loginInfo.getDealerCode());
                        IntentqueryList.add(Integer.parseInt(DictCodeConstants.D_KEY));  
                        TtCusIntentPO intentpo = TtCusIntentPO.findFirst("CUSTOMER_NO= ? AND INTENT_ID= ? AND DEALER_CODE= ? AND D_KEY= ? ",IntentqueryList.toArray());           
                        if(intentpo!=null){
                            if (intentpo.getInteger("IS_TEST_DRIVE")!=null ){
                                PO.setInteger("IS_TEST_DRIVE", intentpo.getInteger("IS_TEST_DRIVE"));                                             
                            }else{
                                PO.setInteger("IS_TEST_DRIVE",DictCodeConstants.STATUS_IS_NOT);
                            }
                        }
                        
                    }
                    PO.setString("OWNED_BY",String.valueOf(loginInfo.getUserId()));//应该取登录人的
                    PO.setInteger("D_KEY",DictCodeConstants.D_KEY);
                    PO.saveIt();
       
                }
                  
            }
           
        }
        
    }
    
    //跟进活动，新增与修改的公共方法
    public void addAndUpdatePublicMethods(TtSalesPromotionPlanDTO salesProDto,long id,PotentialCusPO potentialCusPo,int ver,String status){
        LoginInfoDto login = ApplicationContextHelper.getBeanByType(LoginInfoDto.class);
        potentialCusPo.setLong("IS_UPLOAD",DictCodeConstants.IS_NOT);
        potentialCusPo.setInteger("VER", ver+1);
        if(salesProDto.getPromResult()!=null&&salesProDto.getPromResult()==13341004){
            if((salesProDto.getPriorGrade()!=null)&&(salesProDto.getPriorGrade()!=13101006||salesProDto.getPriorGrade()!=13101007)){
                potentialCusPo.setLong("FAIL_INTENT_LEVEL",Long.parseLong(salesProDto.getPriorGrade()+""));
                System.out.println("——————1"+Long.parseLong(salesProDto.getPriorGrade()+""));
            }
        }
        potentialCusPo.saveIt();
        if(status.equals("A")){
            if(!StringUtils.isNullOrEmpty(salesProDto.getPromResult())&&salesProDto.getPriorGrade()!=salesProDto.getNextGrade()&&!StringUtils.isNullOrEmpty(salesProDto.getNextGrade())){
                //
                System.out.println("——————2将跟进结果为空的记录删除");
                List<Object> promotionList = new ArrayList<Object>();
                promotionList.add(salesProDto.getCustomerNo());
                promotionList.add(login.getDealerCode());
                promotionList.add(Long.parseLong(DictCodeConstants.D_KEY));
                List<TtSalesPromotionPlanPO> promotionPo = TtSalesPromotionPlanPO.findBySQL("select * from TT_SALES_PROMOTION_PLAN where CUSTOMER_NO= ? AND DEALER_CODE= ? AND D_KEY= ? AND (PROM_RESULT is null or PROM_RESULT=0 )", promotionList.toArray());
                if(promotionPo!=null&&promotionPo.size()>0){
                    for(int i = 0;i<promotionPo.size();i++){
                        TtSalesPromotionPlanPO salesPlanPO = promotionPo.get(i);
                        if(salesPlanPO!=null){
                            salesPlanPO.delete();
                            System.out.println("——————3将跟进结果为空的记录删除");
                        }
                    }
                    
                }
                System.out.println("——————4");
                List<Object> taskList1 = new ArrayList<Object>();
                taskList1.add(salesProDto.getNextGrade());
                taskList1.add(Long.parseLong(DictCodeConstants.DICT_CUSTOMER_STATUS_LATENCY));
                taskList1.add(DictCodeConstants.IS_YES);
                taskList1.add(login.getDealerCode());
                List<TrackingTaskPO> taskPO=TrackingTaskPO.findBySQL("select * from TM_TRACKING_TASK where INTENT_LEVEL= ? AND CUSTOMER_STATUS= ? AND IS_VALID= ? AND DEALER_CODE= ? ", taskList1.toArray());
                if(taskPO!=null&&taskPO.size()>0){
                    for(int j = 0;j<taskPO.size();j++){
                        System.out.println("——————5");
                        TrackingTaskPO task = taskPO.get(j);
                        TtSalesPromotionPlanPO sPlanPo = new TtSalesPromotionPlanPO();
                        sPlanPo.setLong("TASK_ID", task.getLong("TASK_ID"));
                        sPlanPo.setDate("BOOKING_DATE", salesProDto.getNextBookingDate());
                        sPlanPo.setString("CUSTOMER_NO", salesProDto.getCustomerNo());
                        this.setSalesPromotionPo(sPlanPo, salesProDto);
                        sPlanPo.setInteger("PRIOR_GRADE",Integer.parseInt(task.getLong("INTENT_LEVEL").toString()));
                        sPlanPo.setInteger("NEXT_GRADE",null);
                        Calendar c = Calendar.getInstance();
                        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                        String dates=new String();
                        sPlanPo.setDate("ACTION_DATE",null);
                        if(task.getInteger("INTERVAL_DAYS")!=null){
                            c.setTime(new Date());
                            c.add(7, task.getInteger("INTERVAL_DAYS"));
                            dates = format.format(c.getTime()).toString();
                        }
                        if(salesProDto.getScheduleDate()!=null&&dates!=null){
                            if(salesProDto.getNextBookingDate()!=null){
                                sPlanPo.setDate("SCHEDULE_DATE", salesProDto.getNextBookingDate());
                            }else if(salesProDto.getNextPromDate()!=null){
                                sPlanPo.setDate("SCHEDULE_DATE", salesProDto.getNextPromDate());
                            }else{
                                try {
                                    sPlanPo.setDate("SCHEDULE_DATE", format.parse(dates));
                                } catch (ParseException e) {
                                    
                                    e.printStackTrace();
                                }
                            }
                        }
                        
                        sPlanPo.setInteger("PROM_WAY",task.getInteger("EXECUTE_TYPE"));
                        sPlanPo.setInteger("CREATE_TYPE",Integer.parseInt(DictCodeConstants.DICT_CREATE_TYPE_SYSTEM));
                        sPlanPo.setString("PROM_CONTENT",task.getString("TASK_CONTENT"));
                        sPlanPo.setInteger("PROM_RESULT",null);
                        sPlanPo.setString("SCENE",null);
        /*                if(salesProDto.getIsAuditing()!=null){
                            sPlanPo.setInteger("IS_AUDITING",salesProDto.getIsAuditing());
                        }else{
                            sPlanPo.setInteger("IS_AUDITING",12781002);
                        }
                       
                        sPlanPo.setString("AUDITED_BY",salesProDto.getAuditedBy());
                        sPlanPo.setString("AUDITING_REMARK",salesProDto.getAuditingRemark());*/
                        sPlanPo.saveIt();
                        
                    }
                }else{
                    if(!StringUtils.isNullOrEmpty(salesProDto.getNextBookingDate())||!StringUtils.isNullOrEmpty(salesProDto.getNextPromDate())){
                        TtSalesPromotionPlanPO sPromotionPo = new TtSalesPromotionPlanPO();
                        this.setSalesPromotionPo(sPromotionPo, salesProDto);
                        sPromotionPo.setDate("BOOKING_DATE", salesProDto.getNextBookingDate());
                        sPromotionPo.setString("CUSTOMER_NO", salesProDto.getCustomerNo());
                        sPromotionPo.setInteger("PRIOR_GRADE",salesProDto.getNextGrade());
                        sPromotionPo.setInteger("NEXT_GRADE",null);
                        if(salesProDto.getScheduleDate()!=null){
                            if(salesProDto.getNextBookingDate()!=null){
                                sPromotionPo.setDate("SCHEDULE_DATE", salesProDto.getNextBookingDate());
                            }else if(salesProDto.getNextPromDate()!=null){
                                sPromotionPo.setDate("SCHEDULE_DATE", salesProDto.getNextPromDate());
                            }else{
                               
                                sPromotionPo.setDate("SCHEDULE_DATE", new Date());
                                
                            }
                        }
                        sPromotionPo.setDate("ACTION_DATE",null);
                        sPromotionPo.setInteger("CREATE_TYPE", 13291001);
                        sPromotionPo.setInteger("PROM_RESULT",null);
                        sPromotionPo.setString("SCENE",null);
           /*             if(salesProDto.getIsAuditing()!=null){
                            sPromotionPo.setInteger("IS_AUDITING",salesProDto.getIsAuditing());
                        }else{
                            sPromotionPo.setInteger("IS_AUDITING",12781002);
                        }
                       
                        sPromotionPo.setString("AUDITED_BY",salesProDto.getAuditedBy());
                        sPromotionPo.setString("AUDITING_REMARK",salesProDto.getAuditingRemark());*/
                        sPromotionPo.saveIt();
                    }
                   
                }
              //如果跟进客户的前级别为F，应该把已完成的意向改为 未完成
                if(salesProDto.getPriorGrade()==13101007){
                    PotentialCusPO CusPo=PotentialCusPO.findByCompositeKeys(login.getDealerCode(),salesProDto.getCustomerNo());
                    CusPo.setLong("Intent_Finished",DictCodeConstants.IS_NOT);
                    CusPo.saveIt();
                }
              //如果促进前级别为FO级别，则清空掉战败类型，战败车型，战败原因，停止原因
                if(salesProDto.getPriorGrade()==13101007||salesProDto.getPriorGrade()==13101006){
                    if((salesProDto.getNextGrade()!=13101007)&&(salesProDto.getNextGrade()!=13101006)){
                        List<Object> intentList = new ArrayList<Object>();
                        intentList.add(salesProDto.getCustomerNo());
                        intentList.add(login.getDealerCode());
                        intentList.add(Long.parseLong(DictCodeConstants.D_KEY));
                        List<TtCusIntentPO> intentPo = TtCusIntentPO.findBySQL("select * from TT_CUSTOMER_INTENT where CUSTOMER_NO= ? AND DEALER_CODE= ? AND D_KEY= ? ", intentList.toArray());
                        if(intentPo!=null&&intentPo.size()>0){
                            for(int i=0;i<intentPo.size();i++){
                                TtCusIntentPO intent = intentPo.get(i);
                                intent.setString("FAIL_MODEL", "");
                                intent.setString("DR_CODE", "");
                                intent.setString("ABORT_REASON", "");
                                intent.setInteger("FAIL_TYPE", 0);
                                intent.saveIt();
                                
                            }
                            
                        }
                    }
                }
              //当级别发生变化时。。把客户资料意向级别修改
                PotentialCusPO CusPo1=PotentialCusPO.findByCompositeKeys(login.getDealerCode(),salesProDto.getCustomerNo());
                System.out.println("改变客户级别11111111111111");
                if(CusPo1!=null){
                    if(CusPo1.getLong("INTENT_LEVEL")==Long.parseLong(DictCodeConstants.DICT_INTENT_LEVEL_D)){
                        CusPo1.setLong("INTENT_LEVEL", Long.parseLong(DictCodeConstants.DICT_INTENT_LEVEL_D));
                    }else{
                        if(!StringUtils.isNullOrEmpty(salesProDto.getNextGrade())){
                            if(CusPo1.getInteger("INTENT_LEVEL")==salesProDto.getNextGrade()){
                                CusPo1.setDate("DDCN_UPDATE_DATE", new Date()) ;
                            }
                            CusPo1.setLong("INTENT_LEVEL", Long.parseLong(salesProDto.getNextGrade()+""));
                        }
                        CusPo1.setInteger("VER", ver+1);
                        CusPo1.setLong("IS_UPLOAD", DictCodeConstants.IS_NOT);
                        
                    }
                    CusPo1.saveIt();
                    
                }
                
                
            }
        }else{
            if("A".equals(status)&&salesProDto.getPromResult()!=null&&salesProDto.getPromResult()!=0){
                PotentialCusPO CusPo2=PotentialCusPO.findByCompositeKeys(login.getDealerCode(),salesProDto.getCustomerNo());
                CusPo2.setInteger("VER", ver+1);
                CusPo2.setLong("IS_UPLOAD", DictCodeConstants.IS_NOT);
                CusPo2.saveIt();
            }
        }
    
        gzflag ="1";
        if(!StringUtils.isNullOrEmpty(id)&&"U".equals(status)){
            List<Map> result = queryProPlanJudge(id,login.getDealerCode(),salesProDto.getCustomerNo());
            if(result!=null){
        
                ITEM_PROM_RESULT = String.valueOf(result.get(0).get("ITEM_PROM_RESULT"));
            }
        }
        if("U".equals(status)&&!StringUtils.isNullOrEmpty(salesProDto.getPromResult())&&salesProDto.getPriorGrade()!=salesProDto.getNextGrade()&&!StringUtils.isNullOrEmpty(salesProDto.getNextGrade())){
            if(!StringUtils.isNullOrEmpty(ITEM_PROM_RESULT)){
                System.out.println("1111111111111111111");
                System.out.println(ITEM_PROM_RESULT);
                gzflag ="2";
                
            }
            if(!StringUtils.isNullOrEmpty(gzflag)&&"2".equals(gzflag)){
                System.out.println("11111111111111111112");
                List<Object> task2 = new ArrayList<Object>();
                task2.add(salesProDto.getNextGrade());
                task2.add(Long.parseLong(DictCodeConstants.DICT_CUSTOMER_STATUS_LATENCY));
                task2.add(DictCodeConstants.IS_YES);
                task2.add(login.getDealerCode());
                List<TrackingTaskPO> taskPO2=TrackingTaskPO.findBySQL("select * from TM_TRACKING_TASK where INTENT_LEVEL= ? AND CUSTOMER_STATUS= ? AND IS_VALID= ? AND DEALER_CODE= ? ", task2.toArray());
                
                //将跟进结果为空的记录删除
                List<Object> promotion1 = new ArrayList<Object>();
                promotion1.add(salesProDto.getCustomerNo());
                promotion1.add(login.getDealerCode());
                promotion1.add(Long.parseLong(DictCodeConstants.D_KEY));
                List<TtSalesPromotionPlanPO> promotionPo1 = TtSalesPromotionPlanPO.findBySQL("select * from TT_SALES_PROMOTION_PLAN where CUSTOMER_NO= ? AND DEALER_CODE= ? AND D_KEY= ? AND (PROM_RESULT is null or PROM_RESULT=0 )", promotion1.toArray());
                if(promotionPo1!=null&&promotionPo1.size()>0){
                    for(int i = 0;i<promotionPo1.size();i++){
                        System.out.println("1111111111111111113");
                        TtSalesPromotionPlanPO salesPlanPO = promotionPo1.get(i);
                        if(salesPlanPO!=null){
                            System.out.println("1111111111111111114");
                            salesPlanPO.delete();
                        }
                    }
                    
                }
                if(salesProDto.getPromResult()==13341009){
                    if(taskPO2!=null&&taskPO2.size()>0){
                        System.out.println("1111111111111111115");
                        for(int j = 0;j<taskPO2.size();j++){
                            System.out.println("1111111111111111116");
                            TrackingTaskPO task = taskPO2.get(j);
                            TtSalesPromotionPlanPO sPlanPo = new TtSalesPromotionPlanPO();
                            sPlanPo.setLong("TASK_ID", task.getLong("TASK_ID"));
                            sPlanPo.setDate("BOOKING_DATE", salesProDto.getNextBookingDate());
                            sPlanPo.setString("CUSTOMER_NO", salesProDto.getCustomerNo());
                            this.setSalesPromotionPo(sPlanPo, salesProDto);
                            sPlanPo.setInteger("PRIOR_GRADE",Integer.parseInt(task.getLong("INTENT_LEVEL")+""));
                            sPlanPo.setInteger("NEXT_GRADE",null);
                            Calendar c = Calendar.getInstance();
                            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                            String dates=new String();
                            sPlanPo.setDate("ACTION_DATE",null);
                            if(task.getInteger("INTERVAL_DAYS")!=null){
                                c.setTime(new Date());
                                c.add(7, task.getInteger("INTERVAL_DAYS"));
                                dates = format.format(c.getTime()).toString();
                            }
                            if(salesProDto.getScheduleDate()!=null&&dates!=null){
                                if(salesProDto.getNextBookingDate()!=null){
                                    sPlanPo.setDate("SCHEDULE_DATE", salesProDto.getNextBookingDate());
                                }else if(salesProDto.getNextPromDate()!=null){
                                    sPlanPo.setDate("SCHEDULE_DATE", salesProDto.getNextPromDate());
                                }else{
                                    try {
                                        sPlanPo.setDate("SCHEDULE_DATE", format.parse(dates));
                                    } catch (ParseException e) {
                                        
                                        e.printStackTrace();
                                    }
                                }
                            }
                            sPlanPo.setInteger("CREATE_TYPE",Integer.parseInt(DictCodeConstants.DICT_CREATE_TYPE_SYSTEM));
                            sPlanPo.setInteger("PROM_WAY",task.getInteger("EXECUTE_TYPE"));
                            sPlanPo.setInteger("CREATE_TYPE",13291001);
                            sPlanPo.setString("PROM_CONTENT",task.getString("TASK_CONTENT"));
                            sPlanPo.setInteger("PROM_RESULT",null);
                            sPlanPo.setString("SCENE",null);
                    /*        if(salesProDto.getIsAuditing()!=null){
                                sPlanPo.setInteger("IS_AUDITING",salesProDto.getIsAuditing());
                            }else{
                                sPlanPo.setInteger("IS_AUDITING",12781002);
                            }
                           
                            sPlanPo.setString("AUDITED_BY",salesProDto.getAuditedBy());
                            sPlanPo.setString("AUDITING_REMARK",salesProDto.getAuditingRemark());*/
                            sPlanPo.saveIt();
                            
                        }
                    }else{
                        System.out.println("1111111111111111117");
                        if(!StringUtils.isNullOrEmpty(salesProDto.getNextBookingDate())||!StringUtils.isNullOrEmpty(salesProDto.getNextPromDate())){
                            TtSalesPromotionPlanPO sPromotionPo = new TtSalesPromotionPlanPO();
                            System.out.println("1111111111111111118");
                            this.setSalesPromotionPo(sPromotionPo, salesProDto);
                            sPromotionPo.setDate("BOOKING_DATE", salesProDto.getNextBookingDate());
                            sPromotionPo.setString("CUSTOMER_NO", salesProDto.getCustomerNo());
                            sPromotionPo.setInteger("PRIOR_GRADE",salesProDto.getNextGrade());
                            sPromotionPo.setInteger("NEXT_GRADE",null);
                            if(salesProDto.getScheduleDate()!=null){
                                if(salesProDto.getNextBookingDate()!=null){
                                    sPromotionPo.setDate("SCHEDULE_DATE", salesProDto.getNextBookingDate());
                                }else if(salesProDto.getNextPromDate()!=null){
                                    sPromotionPo.setDate("SCHEDULE_DATE", salesProDto.getNextPromDate());
                                }else{
                                   
                                    sPromotionPo.setDate("SCHEDULE_DATE", new Date());
                                    
                                }
                            }
                            sPromotionPo.setDate("ACTION_DATE",null);
                            sPromotionPo.setInteger("CREATE_TYPE", 13291001);
                            sPromotionPo.setInteger("PROM_RESULT",null);
                            sPromotionPo.setString("SCENE",null);
                     /*       if(salesProDto.getIsAuditing()!=null){
                                sPromotionPo.setInteger("IS_AUDITING",salesProDto.getIsAuditing());
                            }else{
                                sPromotionPo.setInteger("IS_AUDITING",12781002);
                            }
                           
                            sPromotionPo.setString("AUDITED_BY",salesProDto.getAuditedBy());
                            sPromotionPo.setString("AUDITING_REMARK",salesProDto.getAuditingRemark());*/
                            sPromotionPo.saveIt();
                        }
                    }
                }
           
                //当级别发生变化时。。把客户资料意向级别修改
                PotentialCusPO CusPo1=PotentialCusPO.findByCompositeKeys(login.getDealerCode(),salesProDto.getCustomerNo());
                System.out.println("1111111111111111119");
                if(CusPo1!=null){
                    System.out.println("11111111111111111110");
                    if(CusPo1.getLong("INTENT_LEVEL")==Long.parseLong(DictCodeConstants.DICT_INTENT_LEVEL_D)){
                        CusPo1.setLong("INTENT_LEVEL", Long.parseLong(DictCodeConstants.DICT_INTENT_LEVEL_D));
                    }else{
                        if(!StringUtils.isNullOrEmpty(salesProDto.getNextGrade())){
                            if(CusPo1.getInteger("INTENT_LEVEL")==salesProDto.getNextGrade()){
                                CusPo1.setDate("DDCN_UPDATE_DATE", new Date()) ;
                            }
                            CusPo1.setLong("INTENT_LEVEL", Long.parseLong(salesProDto.getNextGrade()+""));
                        }
                        CusPo1.setInteger("VER", ver+1);
                        CusPo1.setLong("IS_UPLOAD", DictCodeConstants.IS_NOT);
                        
                    }
                    CusPo1.saveIt();
                    
                }
                //如果跟进客户的前级别为F，应该把已完成的意向改为 未完成
                if(salesProDto.getPriorGrade()==13101007){
                    PotentialCusPO CusPo=PotentialCusPO.findByCompositeKeys(login.getDealerCode(),salesProDto.getCustomerNo());
                    CusPo.setLong("Intent_Finished",DictCodeConstants.IS_NOT);
                    CusPo.saveIt();
                }
              //如果促进前级别为FO级别，则清空掉战败类型，战败车型，战败原因，停止原因
                if(salesProDto.getPriorGrade()==13101007||salesProDto.getPriorGrade()==13101006){
                    if((salesProDto.getNextGrade()!=13101007)&&(salesProDto.getNextGrade()!=13101006)){
                        List<Object> intentList = new ArrayList<Object>();
                        intentList.add(salesProDto.getCustomerNo());
                        intentList.add(login.getDealerCode());
                        intentList.add(Long.parseLong(DictCodeConstants.D_KEY));
                        List<TtCusIntentPO> intentPo = TtCusIntentPO.findBySQL("select * from TT_CUSTOMER_INTENT where CUSTOMER_NO= ? AND DEALER_CODE= ? AND D_KEY= ? ", intentList.toArray());
                        if(intentPo!=null&&intentPo.size()>0){
                            for(int i=0;i<intentPo.size();i++){
                                TtCusIntentPO intent = intentPo.get(i);
                                intent.setString("FAIL_MODEL", "");
                                intent.setString("DR_CODE", "");
                                intent.setString("ABORT_REASON", "");
                                intent.setInteger("FAIL_TYPE", 0);
                                intent.saveIt();
                                
                            }
                            
                        }
                    }
                }
                if("U".equals(status)&&!StringUtils.isNullOrEmpty(salesProDto.getPromResult())&&salesProDto.getPriorGrade()==salesProDto.getNextGrade()&&!StringUtils.isNullOrEmpty(salesProDto.getNextGrade())){
                    if(commonNoService.getDefalutPara("1801").equals("12781001")&&!StringUtils.isNullOrEmpty(ITEM_PROM_RESULT)){
                        gzflag ="2";
                    }
                  //当级别发生变化时。。把客户资料意向级别修改
                    PotentialCusPO Cus1Po=PotentialCusPO.findByCompositeKeys(login.getDealerCode(),salesProDto.getCustomerNo());
                    if(StringUtils.isNullOrEmpty(Cus1Po)){
                        throw new ServiceBizException("潜客"+salesProDto.getCustomerNo()+"不存在！");
                    }
                    if(!StringUtils.isNullOrEmpty(gzflag)&&"2".equals(gzflag)){
                      //如果前台传的级别和后台不同时要提示而且不能保存 
               
                        List<Object> promotion = new ArrayList<Object>();
                        promotion.add(salesProDto.getCustomerNo());
                        promotion.add(login.getDealerCode());
                        promotion.add(Integer.parseInt(DictCodeConstants.DICT_CREATE_TYPE_SYSTEM));
                        promotion.add(salesProDto.getIntentId());
                        List<TtSalesPromotionPlanPO> promotionPO = TtSalesPromotionPlanPO.findBySQL("select * from TT_SALES_PROMOTION_PLAN where CUSTOMER_NO= ? AND DEALER_CODE= ? AND CREATE_TYPE= ? AND (PROM_RESULT is null or PROM_RESULT=0 ) AND INTENT_ID= ? ", promotion.toArray());
                        if(promotionPO!=null&&promotionPO.size()>0){
                            for(int ia=0;ia<promotion.size();ia++){
                                TtSalesPromotionPlanPO salesPromotionPO = promotionPO.get(ia);
                                if(salesPromotionPO!=null){
                                    salesPromotionPO.delete();
                                }
                            }
                        }
                        
                        TtSalesPromotionPlanPO proPlan = TtSalesPromotionPlanPO.findById(id);
                      //根据TASK_ID确定本条记录所对应的 跟进任务定义
                        TrackingTaskPO tracTask = new TrackingTaskPO();
                        if(!StringUtils.isNullOrEmpty(proPlan.getLong("TASK_ID"))){
                            tracTask = TrackingTaskPO.findById(proPlan.getLong("TASK_ID"));
                        }else{
                            tracTask = TrackingTaskPO.findById(Long.parseLong("0"));
                        }
                        TtSalesPromotionPlanPO pp11=new TtSalesPromotionPlanPO();
                        if(!StringUtils.isNullOrEmpty(salesProDto.getNextBookingDate())||!StringUtils.isNullOrEmpty(salesProDto.getNextPromDate())){
                            
                            pp11.setLong("TASK_ID", tracTask.getLong("TASK_ID"));
                            pp11.setInteger("PRIOR_GRADE",salesProDto.getPriorGrade());
                            pp11.setInteger("NEXT_GRADE",Integer.parseInt(""));
                            if(salesProDto.getScheduleDate()!=null){
                                if(salesProDto.getNextBookingDate()!=null){
                                    pp11.setDate("SCHEDULE_DATE", salesProDto.getNextBookingDate());
                                }else if(salesProDto.getNextPromDate()!=null){
                                    pp11.setDate("SCHEDULE_DATE", salesProDto.getNextPromDate());
                                }else{
                                   
                                    pp11.setDate("SCHEDULE_DATE", new Date());
                                    
                                }
                            }
                            pp11.setInteger("CREATE_TYPE",Integer.parseInt(DictCodeConstants.DICT_CREATE_TYPE_SYSTEM));
                            pp11.setDate("ACTION_DATE","");
                           
                            pp11.setInteger("PROM_RESULT",Integer.parseInt(""));
                            pp11.setString("SCENE",Integer.parseInt(""));
                /*            if(salesProDto.getIsAuditing()!=null&&salesProDto.getIsAuditing()!=0){
                                pp11.setInteger("IS_AUDITING",salesProDto.getIsAuditing());
                            }else{
                                pp11.setInteger("IS_AUDITING",12781002);
                            }
                           
                            pp11.setString("AUDITED_BY",salesProDto.getAuditedBy());
                            pp11.setString("AUDITING_REMARK",salesProDto.getAuditingRemark());*/
                            this.setSalesPromotionPo(pp11, salesProDto);
                            pp11.saveIt();
                        }else if(StringUtils.isNullOrEmpty(salesProDto.getNextBookingDate())&&StringUtils.isNullOrEmpty(salesProDto.getNextPromDate())){
                            if(tracTask!=null){
                                Calendar c = Calendar.getInstance();
                                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                                String dates=new String();
                                
                                if(tracTask.getInteger("INTERVAL_DAYS")!=null){
                                    c.setTime(new Date());
                                    c.add(7, tracTask.getInteger("INTERVAL_DAYS"));
                                    dates = format.format(c.getTime()).toString();
                                }
                                this.setSalesPromotionPo(pp11, salesProDto);
                                pp11.setLong("TASK_ID", tracTask.getLong("TASK_ID"));
                                pp11.setInteger("PRIOR_GRADE",salesProDto.getPriorGrade());
                                pp11.setInteger("NEXT_GRADE",Integer.parseInt(""));
                                if(salesProDto.getScheduleDate()!=null){
                                    if(salesProDto.getNextPromDate()!=null){
                                        pp11.setDate("SCHEDULE_DATE", salesProDto.getNextPromDate());
                                    }else{
                                        
                                        try {
                                            pp11.setDate("SCHEDULE_DATE", format.parse(dates));
                                        } catch (ParseException e) {
                                            // TODO Auto-generated catch block
                                            e.printStackTrace();
                                        }
                                        
                                    }
                                }
                                pp11.setInteger("CREATE_TYPE",Integer.parseInt(DictCodeConstants.DICT_CREATE_TYPE_SYSTEM));
                                pp11.setDate("ACTION_DATE","");
                               
                                pp11.setInteger("PROM_RESULT",Integer.parseInt(""));
                                pp11.setString("SCENE",Integer.parseInt(""));
                  /*              if(salesProDto.getIsAuditing()!=null&&salesProDto.getIsAuditing()!=0){
                                    pp11.setInteger("IS_AUDITING",salesProDto.getIsAuditing());
                                }else{
                                    pp11.setInteger("IS_AUDITING",12781002);
                                }
                               
                                pp11.setString("AUDITED_BY",salesProDto.getAuditedBy());
                                pp11.setString("AUDITING_REMARK",salesProDto.getAuditingRemark());*/
                            
                                pp11.saveIt();
                            }else{

                                if(!StringUtils.isNullOrEmpty(salesProDto.getNextBookingDate())||!StringUtils.isNullOrEmpty(salesProDto.getNextPromDate())){
                                    TtSalesPromotionPlanPO sPromotionPo = new TtSalesPromotionPlanPO();
                                    this.setSalesPromotionPo(sPromotionPo, salesProDto);
                                    sPromotionPo.setDate("BOOKING_DATE", salesProDto.getNextBookingDate());
                                    sPromotionPo.setString("CUSTOMER_NO", salesProDto.getCustomerNo());
                                    sPromotionPo.setInteger("PRIOR_GRADE",salesProDto.getNextGrade());
                                    sPromotionPo.setInteger("NEXT_GRADE",Integer.parseInt(""));
                                    if(salesProDto.getScheduleDate()!=null){
                                        if(salesProDto.getNextBookingDate()!=null){
                                            sPromotionPo.setDate("SCHEDULE_DATE", salesProDto.getNextBookingDate());
                                        }else if(salesProDto.getNextPromDate()!=null){
                                            sPromotionPo.setDate("SCHEDULE_DATE", salesProDto.getNextPromDate());
                                        }else{
                                           
                                            sPromotionPo.setDate("SCHEDULE_DATE", new Date());
                                            
                                        }
                                    }
                                    sPromotionPo.setDate("ACTION_DATE","");
                                    sPromotionPo.setInteger("CREATE_TYPE",Integer.parseInt(DictCodeConstants.DICT_CREATE_TYPE_SYSTEM));
                                    
                                    sPromotionPo.setInteger("PROM_RESULT",Integer.parseInt(""));
                                    sPromotionPo.setString("SCENE",Integer.parseInt(""));
                      /*              if(salesProDto.getIsAuditing()!=null&&salesProDto.getIsAuditing()!=0){
                                        sPromotionPo.setInteger("IS_AUDITING",salesProDto.getIsAuditing());
                                    }else{
                                        sPromotionPo.setInteger("IS_AUDITING",12781002);
                                    }
                                   
                                    sPromotionPo.setString("AUDITED_BY",salesProDto.getAuditedBy());
                                    sPromotionPo.setString("AUDITING_REMARK",salesProDto.getAuditingRemark());*/
                                    sPromotionPo.saveIt();
                                }
                               
                            
                            }
                        }
                        Cus1Po.setInteger("INTENT_LEVEL", salesProDto.getNextGrade());
                        Cus1Po.setDate("DDCN_UPDATE_DATE", new Date());
                        
                    }
                    Cus1Po.setInteger("IS_UPLOAD", DictCodeConstants.STATUS_IS_NOT); 
                    Cus1Po.setInteger("VER", ver+1);
                    Cus1Po.saveIt();
                }
                
            }
        }
        /*
         * 如果是手动新增，前后相同级别并且有跟进结果则生成跟进记录
         */
        if(!StringUtils.isNullOrEmpty(commonNoService.getDefalutPara("1801"))&&commonNoService.getDefalutPara("1801").equals("12781001")){
            if("A".equals(status)&&!StringUtils.isNullOrEmpty(salesProDto.getPromResult())&&salesProDto.getPriorGrade()==salesProDto.getNextGrade()&&!StringUtils.isNullOrEmpty(salesProDto.getNextGrade())){
              //如果下次邀约日期和下次预定时间不同时为空
                if(StringUtils.isNullOrEmpty(salesProDto.getNextBookingDate())||StringUtils.isNullOrEmpty(salesProDto.getNextPromDate())){
                    TtSalesPromotionPlanPO PromotionPO1 = new TtSalesPromotionPlanPO();
                    this.setSalesPromotionPo(PromotionPO1, salesProDto);
                    PromotionPO1.setDate("BOOKING_DATE", salesProDto.getNextBookingDate());
                    PromotionPO1.setString("CUSTOMER_NO", salesProDto.getCustomerNo());
                    if(!StringUtils.isNullOrEmpty(salesProDto.getPriorGrade())){
                        PromotionPO1.setInteger("PRIOR_GRADE",salesProDto.getPriorGrade());
                        List<Object> task1 = new ArrayList<Object>();
                        task1.add(salesProDto.getPriorGrade());
                        task1.add(Long.parseLong(DictCodeConstants.DICT_CUSTOMER_STATUS_LATENCY));
                        task1.add(DictCodeConstants.IS_YES);
                        task1.add(login.getDealerCode());
                        List<TrackingTaskPO> taskPO1=TrackingTaskPO.findBySQL("select * from TM_TRACKING_TASK where INTENT_LEVEL= ? AND CUSTOMER_STATUS= ? AND IS_VALID= ? AND DEALER_CODE= ? ", task1.toArray());
                        if(taskPO1!=null){
                            TrackingTaskPO ttPO =taskPO1.get(0);
                            PromotionPO1.setLong("TASK_ID", ttPO.getLong("TASK_ID"));
                        }
                    }
                    PromotionPO1.setInteger("NEXT_GRADE",Integer.parseInt(""));
                    if(salesProDto.getScheduleDate()!=null){
                        if(salesProDto.getNextBookingDate()!=null){
                            PromotionPO1.setDate("SCHEDULE_DATE", salesProDto.getNextBookingDate());
                        }else if(salesProDto.getNextPromDate()!=null){
                            PromotionPO1.setDate("SCHEDULE_DATE", salesProDto.getNextPromDate());
                        }else{
                           
                            PromotionPO1.setDate("SCHEDULE_DATE", new Date());
                            
                        }
                    }
                    PromotionPO1.setDate("ACTION_DATE","");
                    PromotionPO1.setInteger("CREATE_TYPE",Integer.parseInt(DictCodeConstants.DICT_CREATE_TYPE_SYSTEM));
                    PromotionPO1.setInteger("PROM_RESULT",Integer.parseInt(""));
                    PromotionPO1.setString("SCENE",Integer.parseInt(""));
            /*        if(salesProDto.getIsAuditing()!=null&&salesProDto.getIsAuditing()!=0){
                        PromotionPO1.setInteger("IS_AUDITING",salesProDto.getIsAuditing());
                    }else{
                        PromotionPO1.setInteger("IS_AUDITING",12781002);
                    }
                   
                    PromotionPO1.setString("AUDITED_BY",salesProDto.getAuditedBy());
                    PromotionPO1.setString("AUDITING_REMARK",salesProDto.getAuditingRemark());*/
                   
                    PromotionPO1.saveIt();
                }//如果下次邀约日期和下次预定时间同是为空
                else if(StringUtils.isNullOrEmpty(salesProDto.getNextBookingDate())&&StringUtils.isNullOrEmpty(salesProDto.getNextPromDate())){
                    List<Object> task2 = new ArrayList<Object>();
                    task2.add(salesProDto.getNextGrade());
                    task2.add(Long.parseLong(DictCodeConstants.DICT_CUSTOMER_STATUS_LATENCY));
                    task2.add(DictCodeConstants.IS_YES);
                    task2.add(login.getDealerCode());
                    List<TrackingTaskPO> taskPO2=TrackingTaskPO.findBySQL("select * from TM_TRACKING_TASK where INTENT_LEVEL= ? AND CUSTOMER_STATUS= ? AND IS_VALID= ? AND DEALER_CODE= ? ", task2.toArray());
                    if(taskPO2!=null&&taskPO2.size()>0){
                        //将跟进结果为空的记录删除
                        List<Object> promotion1 = new ArrayList<Object>();
                        promotion1.add(salesProDto.getCustomerNo());
                        promotion1.add(login.getDealerCode());
                        promotion1.add(Long.parseLong(DictCodeConstants.D_KEY));
                        List<TtSalesPromotionPlanPO> promotionPo1 = TtSalesPromotionPlanPO.findBySQL("select * from TT_SALES_PROMOTION_PLAN where CUSTOMER_NO= ? AND DEALER_CODE= ? AND D_KEY= ? AND (PROM_RESULT is null or PROM_RESULT=0 )", promotion1.toArray());
                        if(promotionPo1!=null&&promotionPo1.size()>0){
                            for(int i = 0;i<promotionPo1.size();i++){
                                TtSalesPromotionPlanPO salesPlanPO = promotionPo1.get(i);
                                if(salesPlanPO!=null){
                                    salesPlanPO.delete();
                                }
                            }
                            
                        }

                        for(int j = 0;j<taskPO2.size();j++){
                            TrackingTaskPO task = taskPO2.get(j);
                            TtSalesPromotionPlanPO sPlanPo = new TtSalesPromotionPlanPO();
                            this.setSalesPromotionPo(sPlanPo, salesProDto);
                            sPlanPo.setLong("TASK_ID", task.getLong("TASK_ID"));
                            sPlanPo.setDate("BOOKING_DATE", salesProDto.getNextBookingDate());
                            sPlanPo.setString("CUSTOMER_NO", salesProDto.getCustomerNo());
                           
                            sPlanPo.setInteger("PRIOR_GRADE",Integer.parseInt(task.getLong("INTENT_LEVEL")+""));
                            sPlanPo.setInteger("NEXT_GRADE",Integer.parseInt(""));
                            Calendar c = Calendar.getInstance();
                            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                            String dates=new String();
                            sPlanPo.setDate("ACTION_DATE","");
                            if(task.getInteger("INTERVAL_DAYS")!=null){
                                c.setTime(new Date());
                                c.add(7, task.getInteger("INTERVAL_DAYS"));
                                dates = format.format(c.getTime()).toString();
                            }
                            if(salesProDto.getScheduleDate()!=null&&dates!=null){
                                if(salesProDto.getNextBookingDate()!=null){
                                    sPlanPo.setDate("SCHEDULE_DATE", salesProDto.getNextBookingDate());
                                }else if(salesProDto.getNextPromDate()!=null){
                                    sPlanPo.setDate("SCHEDULE_DATE", salesProDto.getNextPromDate());
                                }else{
                                    try {
                                        sPlanPo.setDate("SCHEDULE_DATE", format.parse(dates));
                                    } catch (ParseException e) {
                                        
                                        e.printStackTrace();
                                    }
                                }
                            }
                            
                            sPlanPo.setInteger("PROM_WAY",task.getInteger("EXECUTE_TYPE"));
                            sPlanPo.setInteger("CREATE_TYPE",Integer.parseInt(DictCodeConstants.DICT_CREATE_TYPE_SYSTEM));
                            sPlanPo.setString("PROM_CONTENT",task.getString("TASK_CONTENT"));
                            sPlanPo.setInteger("PROM_RESULT",Integer.parseInt(""));
                            sPlanPo.setString("SCENE",Integer.parseInt(""));
                     /*       if(salesProDto.getIsAuditing()!=null&&salesProDto.getIsAuditing()!=0){
                                sPlanPo.setInteger("IS_AUDITING",salesProDto.getIsAuditing());
                            }else{
                                sPlanPo.setInteger("IS_AUDITING",12781002);
                            }
                           
                            sPlanPo.setString("AUDITED_BY",salesProDto.getAuditedBy());
                            sPlanPo.setString("AUDITING_REMARK",salesProDto.getAuditingRemark());*/
                            sPlanPo.saveIt();
                            
                        }
                    
                        
                    }else{
                        if(!StringUtils.isNullOrEmpty(salesProDto.getNextBookingDate())||!StringUtils.isNullOrEmpty(salesProDto.getNextPromDate())){
                            TtSalesPromotionPlanPO sPromotionPo = new TtSalesPromotionPlanPO();
                            this.setSalesPromotionPo(sPromotionPo, salesProDto);
                            sPromotionPo.setDate("BOOKING_DATE", salesProDto.getNextBookingDate());
                            sPromotionPo.setString("CUSTOMER_NO", salesProDto.getCustomerNo());
                            sPromotionPo.setInteger("PRIOR_GRADE",salesProDto.getNextGrade());
                            sPromotionPo.setInteger("NEXT_GRADE",Integer.parseInt(""));
                            if(salesProDto.getScheduleDate()!=null){
                                if(salesProDto.getNextBookingDate()!=null){
                                    sPromotionPo.setDate("SCHEDULE_DATE", salesProDto.getNextBookingDate());
                                }else if(salesProDto.getNextPromDate()!=null){
                                    sPromotionPo.setDate("SCHEDULE_DATE", salesProDto.getNextPromDate());
                                }else{
                                   
                                    sPromotionPo.setDate("SCHEDULE_DATE", new Date());
                                    
                                }
                            }
                            sPromotionPo.setDate("ACTION_DATE","");
                            sPromotionPo.setInteger("CREATE_TYPE",Integer.parseInt(DictCodeConstants.DICT_CREATE_TYPE_SYSTEM));
                            
                            sPromotionPo.setInteger("PROM_RESULT",Integer.parseInt(""));
                            sPromotionPo.setString("SCENE",Integer.parseInt(""));
           /*                 if(salesProDto.getIsAuditing()!=null&&salesProDto.getIsAuditing()!=0){
                                sPromotionPo.setInteger("IS_AUDITING",salesProDto.getIsAuditing());
                            }else{
                                sPromotionPo.setInteger("IS_AUDITING",12781002);
                            }
                           
                            sPromotionPo.setString("AUDITED_BY",salesProDto.getAuditedBy());
                            sPromotionPo.setString("AUDITING_REMARK",salesProDto.getAuditingRemark());*/
                            sPromotionPo.saveIt();
                        }
                       
                    }
                }
            }
        }
        
      
        
    }

    /**
     * 潜客跟进修改
     * 
     * @author zhanshiwei
     * @date 2016年9月12日
     * @param id
     * @param salesProDto
     * @throws ServiceBizException (non-Javadoc)
     * @see com.yonyou.dms.customer.service.customerManage.SalesPromotionService#modifySalesPromotionInfo(java.lang.Long,
     * com.yonyou.dms.customer.domains.DTO.customerManage.SalesPromotionDTO)
     */

    @Override
    public void modifySalesPromotionInfo(Long id, SalesPromotionDTO salesProDto) throws ServiceBizException {
        SalesPromotionPO salesProPo = SalesPromotionPO.findById(id);
        Map<String, Object> result = null;
        result = trackingtaskservice.queryTrackingTaskByIntentLevel(salesProDto.getNextGrade());
        if (salesProDto.getPromResult() == DictCodeConstants.TRACKING_RESULT_NOT
            || StringUtils.isNullOrEmpty(salesProDto.getPromResult())) {
            salesProDto.setPromResult(DictCodeConstants.TRACKING_RESULT_GOON);
        }
        // 跟进结果是战败申请，促进后级别为FO
        else if (salesProDto.getPromResult() == DictCodeConstants.COMPLAINT_TRACKING_RESULT_6) {
            salesProDto.setNextGrade(DictCodeConstants.FO_LEVEL);
        }
  /*      if (!StringUtils.isNullOrEmpty(salesProDto.getBookingIsArrive())
            && salesProDto.getBookingIsArrive() == DictCodeConstants.STATUS_IS_YES) {
            salesProDto.setNextscheduleDate(salesProDto.getNextArriveDate());
        } else {
            salesProDto.setNextscheduleDate(DateUtil.addDay(salesProDto.getScheduleDate(),
                                                            result == null ? 0 : Integer.parseInt(result.get("INTERVAL_DAYS").toString())));
        }*/
        /*
         * 5.根据促进后级别在跟进任务中的定义，自动填写下次计划日期，点击保存时，以促进后级别更新客户意向级别，系统自动生成一条跟进记录，生成规则如下：
         * 促进前级别：取当前跟进记录促进后级别；计划日期：取下次计划日期；是否接受邀约：为是；邀约日期：取当前跟进记录下次到店日期；销售顾问：取当前跟进记录销售顾问。
         */
        if (!StringUtils.isNullOrEmpty(salesProDto.getNextGrade()) && result != null) {
            SalesPromotionPO newsalesProPo = new SalesPromotionPO();
            newsalesProPo.setLong("POTENTIAL_CUSTOMER_ID", salesProPo.get("potential_customer_id"));// ID
            newsalesProPo.setInteger("PROM_RESULT", DictCodeConstants.TRACKING_RESULT_NOT);// 未跟进
            // newsalesProPo.setInteger("NEXT_GRADE", salesProDto.getNextGrade());// 促进后客户级别
            newsalesProPo.setInteger("PRIOR_GRADE", salesProDto.getNextGrade());// 促进后客户级别
            newsalesProPo.setString("TASK_NAME", result.get("TASK_NAME"));
            newsalesProPo.setDate("SCHEDULE_DATE", salesProDto.getNextscheduleDate());// 计划日期
            newsalesProPo.setInteger("IS_SUCCESS_BOOKING", DictCodeConstants.STATUS_IS_YES);// 是否邀约成功
            if(!StringUtils.isNullOrEmpty(salesProDto.getBookingIsArrive())&&salesProDto.getBookingIsArrive()==DictCodeConstants.STATUS_IS_YES){
                newsalesProPo.setDate("BOOKING_DATE", salesProDto.getNextArriveDate());// 邀约日期
            }
            newsalesProPo.setString("CONSULTANT", salesProDto.getConsultant());// 销售顾问
            newsalesProPo.setString("LAST_SCENE", salesProDto.getScene());//上次跟进经过
           // newsalesProPo.setString("BOOKING_IS_ARRIVE", salesProDto.getBookingIsArrive());//邀约是否到店
            // setSalesPromotionPo(newsalesProPo, salesProDto);
            newsalesProPo.saveIt();
        }
          //salesProPo.setInteger("BOOKING_IS_ARRIVE", salesProDto.getBookingIsArrive());// 邀约是否到店
        salesProPo.setInteger("PROM_RESULT", salesProDto.getPromResult());// 跟进结果
        //setSalesPromotionPo(salesProPo, salesProDto);
        // 点击保存时，以促进后级别更新客户意向级别
        this.modifypotentialCusInfo(salesProDto, salesProPo);
        
        salesProPo.saveIt();
    }
    
    /**
    * 更新潜客信息(以促进后级别更新客户意向级别)
    * @author zhanshiwei
    * @date 2016年10月19日
    * @param salesProDto
    * @throws ServiceBizException
    */
    	
    public void modifypotentialCusInfo(SalesPromotionDTO salesProDto,SalesPromotionPO salesProPo) throws ServiceBizException {
        PotentialCusPO potentialCusPo = PotentialCusPO.findById(salesProPo.get("potential_customer_id"));
        potentialCusPo.setInteger("INTENT_LEVEL", salesProDto.getNextGrade());
        potentialCusPo.saveIt();
    }

    /**
     * 设置SalesPromotionPO属性
     * 
     * @author zhanshiwei
     * @date 2016年9月20日
     * @param salesProPo
     * @param salesProDto
     */

    public void setSalesPromotionPo(TtSalesPromotionPlanPO salesProPo, TtSalesPromotionPlanDTO salesProDto) {
        if(!StringUtils.isNullOrEmpty(salesProDto.getBuyCycleP())&&(salesProDto.getBuyCycleP()==Integer.parseInt(DictCodeConstants.DICT_IS_CHECKED)||salesProDto.getBuyCycleP()==DictCodeConstants.STATUS_IS_YES)){
            salesProPo.setInteger("BUY_CYCLE_P", DictCodeConstants.STATUS_IS_YES);
        }else{
            salesProPo.setInteger("BUY_CYCLE_P", DictCodeConstants.STATUS_IS_NOT);
        }
        if(!StringUtils.isNullOrEmpty(salesProDto.getBuyCycleP())&&(salesProDto.getBuyCycleP()==Integer.parseInt(DictCodeConstants.DICT_IS_CHECKED)||salesProDto.getBuyCycleP()==DictCodeConstants.STATUS_IS_YES)){
            salesProPo.setInteger("BUY_CYCLE_S", DictCodeConstants.STATUS_IS_YES);
        }else{
            salesProPo.setInteger("BUY_CYCLE_S", DictCodeConstants.STATUS_IS_NOT);
        }
        if(!StringUtils.isNullOrEmpty(salesProDto.getBuyCycleI())&&(salesProDto.getBuyCycleI()==Integer.parseInt(DictCodeConstants.DICT_IS_CHECKED)||salesProDto.getBuyCycleI()==DictCodeConstants.STATUS_IS_YES)){
            salesProPo.setInteger("BUY_CYCLE_I", DictCodeConstants.STATUS_IS_YES);
        }else{
            salesProPo.setInteger("BUY_CYCLE_I", DictCodeConstants.STATUS_IS_NOT);
        }
        if(!StringUtils.isNullOrEmpty(salesProDto.getBuyCycleX())&&(salesProDto.getBuyCycleX()==Integer.parseInt(DictCodeConstants.DICT_IS_CHECKED)||salesProDto.getBuyCycleX()==DictCodeConstants.STATUS_IS_YES)){
            salesProPo.setInteger("BUY_CYCLE_X", DictCodeConstants.STATUS_IS_YES);
        }else{
            salesProPo.setInteger("BUY_CYCLE_X", DictCodeConstants.STATUS_IS_NOT);
        }
        if(!StringUtils.isNullOrEmpty(salesProDto.getBuyCycleT())&&(salesProDto.getBuyCycleT()==Integer.parseInt(DictCodeConstants.DICT_IS_CHECKED)||salesProDto.getBuyCycleT()==DictCodeConstants.STATUS_IS_YES)){
            salesProPo.setInteger("BUY_CYCLE_T", DictCodeConstants.STATUS_IS_YES);
        }else{
            salesProPo.setInteger("BUY_CYCLE_T", DictCodeConstants.STATUS_IS_NOT);
        }
        if(!StringUtils.isNullOrEmpty(salesProDto.getBuyCycleA())&&(salesProDto.getBuyCycleA()==Integer.parseInt(DictCodeConstants.DICT_IS_CHECKED)||salesProDto.getBuyCycleA()==DictCodeConstants.STATUS_IS_YES)){
            salesProPo.setInteger("BUY_CYCLE_A", DictCodeConstants.STATUS_IS_YES);
        }else{
            salesProPo.setInteger("BUY_CYCLE_A", DictCodeConstants.STATUS_IS_NOT);
        }
        if(!StringUtils.isNullOrEmpty(salesProDto.getBuyCycleC())&&(salesProDto.getBuyCycleC()==Integer.parseInt(DictCodeConstants.DICT_IS_CHECKED)||salesProDto.getBuyCycleC()==DictCodeConstants.STATUS_IS_YES)){
            salesProPo.setInteger("BUY_CYCLE_C", DictCodeConstants.STATUS_IS_YES);
        }else{
            salesProPo.setInteger("BUY_CYCLE_C", DictCodeConstants.STATUS_IS_NOT);
        }
        if(!StringUtils.isNullOrEmpty(salesProDto.getBuyCycleL())&&(salesProDto.getBuyCycleL()==Integer.parseInt(DictCodeConstants.DICT_IS_CHECKED)||salesProDto.getBuyCycleL()==DictCodeConstants.STATUS_IS_YES)){
            salesProPo.setInteger("BUY_CYCLE_L", DictCodeConstants.STATUS_IS_YES);
        }else{
            salesProPo.setInteger("BUY_CYCLE_L", DictCodeConstants.STATUS_IS_NOT);
        }
        if(!StringUtils.isNullOrEmpty(salesProDto.getBuyCycleO())&&(salesProDto.getBuyCycleO()==Integer.parseInt(DictCodeConstants.DICT_IS_CHECKED)||salesProDto.getBuyCycleO()==DictCodeConstants.STATUS_IS_YES)){
            salesProPo.setInteger("BUY_CYCLE_O", DictCodeConstants.STATUS_IS_YES);
        }else{
            salesProPo.setInteger("BUY_CYCLE_O", DictCodeConstants.STATUS_IS_NOT);
        }
        if(!StringUtils.isNullOrEmpty(salesProDto.getBuyCycleD())&&(salesProDto.getBuyCycleD()==Integer.parseInt(DictCodeConstants.DICT_IS_CHECKED)||salesProDto.getBuyCycleD()==DictCodeConstants.STATUS_IS_YES)){
            salesProPo.setInteger("BUY_CYCLE_D", DictCodeConstants.STATUS_IS_YES);
        }else{
            salesProPo.setInteger("BUY_CYCLE_D", DictCodeConstants.STATUS_IS_NOT);
        }
        if(!StringUtils.isNullOrEmpty(salesProDto.getRealVisitAction())){
            salesProPo.setString("REAL_VISIT_ACTION", StringUtils.listToString(salesProDto.getRealVisitAction(), ','));
        }
        if(!StringUtils.isNullOrEmpty(salesProDto.getVisitAction())){
            salesProPo.setString("VISIT_ACTION", StringUtils.listToString(salesProDto.getVisitAction(), ','));
        }
        salesProPo.setString("RELATED_ACTIVITY",salesProDto.getRelatedActivity());
        salesProPo.setString("REAL_ACTIVITY",salesProDto.getRealActivity());
        salesProPo.setInteger("IS_SUCCESS_BOOKING",salesProDto.getIsSuccessBooking());
        salesProPo.setInteger("REAL_PROM_WAY",salesProDto.getRealPromWay());
        salesProPo.setInteger("PROM_WAY",salesProDto.getPromWay());

        salesProPo.setLong("DRIVE_PLAN_ID",salesProDto.getDrivePlanId());
        salesProPo.setString("NEXT_VISIT_ACTION",salesProDto.getNextVisitAction());
        salesProPo.setInteger("NEXT_PROM_CONTENT",salesProDto.getNextPromContent());
        
        
       if(!StringUtils.isNullOrEmpty(salesProDto.getReceptionId())){
            salesProPo.setLong("RECEPTION_ID",salesProDto.getReceptionId());
        }
       
        salesProPo.setString("REAL_CONTACTOR_NAME",salesProDto.getRealContactorName());
        salesProPo.setDate("NEXT_BOOKING_DATE", salesProDto.getNextBookingDate());
        salesProPo.setLong("INTENT_ID",salesProDto.getIntentId());
        salesProPo.setString("CUSTOMER_NAME",salesProDto.getCustomerName());
        salesProPo.setString("CONTACTOR_NAME",salesProDto.getContactorName());
        salesProPo.setString("PHONE",salesProDto.getPhone());
        salesProPo.setString("MOBILE",salesProDto.getMobile());
        SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        //potentialCusPo.setString("VALIDITY_BEGIN_DATE",  format1.format(new Date()));
        salesProPo.setString("ACTION_DATE", format1.format(salesProDto.getActionDate()));
        salesProPo.setString("SOLD_BY",salesProDto.getSoldBy());
        salesProPo.setString("OWNED_BY",salesProDto.getSoldBy());
        salesProPo.setDate("NEXT_PROM_DATE", salesProDto.getNextPromDate());
    }

    /**
     * 跟进编辑查询
     * 
     * @author zhanshiwei
     * @date 2016年9月20日
     * @param id
     * @return
     * @throws ServiceBizException (non-Javadoc)
     * @see com.yonyou.dms.customer.service.customerManage.SalesPromotionService#querySalesPromotionInfo(java.lang.Long)
     */

    @SuppressWarnings("unchecked")
    @Override
    public Map<String, Object> querySalesPromotionInfo(Long id) throws ServiceBizException {
        LoginInfoDto loginInfo = ApplicationContextHelper.getBeanByType(LoginInfoDto.class);
        StringBuffer sb = new StringBuffer();
        sb.append(

                  "SELECT P.VER,\n" +
                  "       0 AS IS_SELECT,\n" + 
                  "       0 AS SAMEGRADE,\n" + 
                  "       P.ITEM_ID,\n" + 
                  "       P.DEALER_CODE,\n" + 
                  "       P.CUSTOMER_NO,\n" + 
                  "       P.INTENT_ID,\n" + 
                  "       P.CUSTOMER_NAME,\n" + 
                  "       P.CONTACTOR_NAME,\n" + 
                  "       P.PHONE,\n" + 
                  "       P.MOBILE,\n" + 
                  "       P.PRIOR_GRADE,\n" + 
                  "       P.NEXT_GRADE,\n" + 
                  "       P.SCHEDULE_DATE,\n" + 
                  "       P.ACTION_DATE,\n" + 
                  "       P.PROM_WAY,\n" +
                  "       P.CREATE_TYPE,\n" + 
                  "       P.PROM_CONTENT,\n" + 
                  "       P.PROM_RESULT,\n" + 
                  "       P.SOLD_BY,\n" +
                  "       TE.USER_ID,\n" + 
                  "       TE.USER_NAME,\n" + 
                  "       P.BOOKING_CUSTOMER_TYPE,\n" + 
                  "       P.BOOKING_DATE,\n" + 
                  "       case when P.BOOKING_CUSTOMER_TYPE = 12781001 then p.next_BOOKING_DATE else null end next_BOOKING_DATE ,\n" + 
                  "       P.SCENE,\n" + 
                  "       P.AUDITED_BY,\n" + 
                  "       P.AUDITING_REMARK,\n" + 
                  "       P.IS_AUDITING,\n" + 
                  "       P.NEXT_PROM_DATE,\n" + 
                  "       P.REAL_VISIT_ACTION,\n" +//add by lim  9个字段
                  "       P.REAL_CONTACTOR_NAME,\n" +
                  "       P.RELATED_ACTIVITY,\n" +
                  "       P.IS_SUCCESS_BOOKING,\n" +
                  "       P.REAL_ACTIVITY,\n" +
                  "       P.VISIT_ACTION,\n" +
                  "       P.REAL_PROM_WAY,\n" +
                  "       P.DRIVE_PLAN_ID,\n" +
                  "       P.NEXT_VISIT_ACTION,\n" +
                  "       P.NEXT_PROM_CONTENT,\n" +// over
                  "       P.BUY_CYCLE_P,\n" +
                  "       P.BUY_CYCLE_S,\n" +
                  "       P.BUY_CYCLE_I,\n" +
                  "       P.BUY_CYCLE_X,\n" +
                  "       P.BUY_CYCLE_T,\n" +
                  "       P.BUY_CYCLE_A,\n" +
                  "       P.BUY_CYCLE_C,\n" +
                  "       P.BUY_CYCLE_L,\n" +
                  "       P.BUY_CYCLE_O,\n" +
                  "       P.BUY_CYCLE_D,\n" +
                  "       P.RECEPTION_ID,\n" +// over  TT_CAMPAIGN_PLAN
                  "       CA1.CAMPAIGN_NAME AS REAL_ACTIVITY_NAME ,\n" +
                  "       CA2.CAMPAIGN_NAME AS RELATED_ACTIVITY_NAME ,\n" +
                  "       0 AS LAST_PROM_RESULT,\n" + 
                  "       '' AS FIRST_SCENE\n" + 
                  ",E.INTENT_BRAND,E.INTENT_SERIES,E.INTENT_MODEL,E.BRAND_NAME,E.SERIES_NAME,E.MODEL_NAME,E.INTENT_CONFIG,E.INTENT_COLOR,p.last_scene,cc.intent_level,cc.ADDRESS,cc.CERTIFICATE_NO,cc.CT_CODE,cc.LMS_REMARK,cc.IS_BIG_CUSTOMER,'' AS IS_CURRENT_RECORD, \n "
                  + "   cc.is_to_shop, cc.time_to_shop,cc.is_test_drive,cc.test_drive_remark " + 
                  "  FROM TT_SALES_PROMOTION_PLAN P\n" + 
                  "  left join\n" + 
                  "(SELECT      a.customer_no,a.DEALER_CODE,b.INTENT_BRAND,BR.BRAND_NAME,b.INTENT_SERIES,SE.SERIES_NAME,\n" + 
                  "      b.INTENT_MODEL,MO.MODEL_NAME,b.INTENT_CONFIG,b.INTENT_COLOR,b.IS_MAIN_MODEL,A.INTENT_ID\n" + 
                  "      FROM TT_CUSTOMER_INTENT A   left JOIN TT_CUSTOMER_INTENT_DETAIL B ON A.\n" + 
                  "      DEALER_CODE=B.DEALER_CODE\n" + 
                  "      AND A.INTENT_ID=B.INTENT_ID\n" + 
                  "      and  b.IS_MAIN_MODEL=12781001 "+
                  " LEFT JOIN  TM_MODEL MO ON  b.INTENT_MODEL=MO.MODEL_CODE AND b.DEALER_CODE=MO.DEALER_CODE\n "+
                  " LEFT  JOIN   TM_SERIES  SE   ON   b.INTENT_SERIES=SE.SERIES_CODE AND b.DEALER_CODE=SE.DEALER_CODE\n"+
                  " LEFT  JOIN   TM_BRAND   BR   ON   b.INTENT_BRAND = BR.BRAND_CODE AND b.DEALER_CODE=BR.DEALER_CODE)  E  on E.DEALER_CODE=p.DEALER_CODE\n" + 
                  "      AND E.INTENT_ID=p.INTENT_ID\n" +
                  " LEFT JOIN TT_CAMPAIGN_PLAN CA1 ON CA1.DEALER_CODE=P.DEALER_CODE AND CA1.CAMPAIGN_CODE=P.REAL_ACTIVITY" +
                  " LEFT JOIN TT_CAMPAIGN_PLAN CA2 ON CA2.DEALER_CODE=P.DEALER_CODE AND CA2.CAMPAIGN_CODE=P.RELATED_ACTIVITY" +
                  " LEFT JOIN TM_USER TE ON TE.DEALER_CODE=P.DEALER_CODE AND P.SOLD_BY=TE.USER_ID" +
                  " LEFT JOIN TM_POTENTIAL_CUSTOMER Cc ON CC.DEALER_CODE=P.DEALER_CODE AND CC.CUSTOMER_NO=P.CUSTOMER_NO" +
                  " WHERE P.DEALER_CODE = '"+ loginInfo.getDealerCode() + "'\n" + 
                  "   AND ITEM_ID= ? AND P.D_KEY = " + DictCodeConstants.D_KEY + "\n" 
          
          );
        List<Object> queryList = new ArrayList<Object>();
        queryList.add(id);
        System.err.println(""+sb.toString());
       /* DAOUtil.findFirst(sb.toString(), queryList);*/
        
        return DAOUtil.findAll(sb.toString(), queryList).get(0);
    }

    /**
     * 删除
     * 
     * @author zhanshiwei
     * @date 2016年9月13日
     * @param id
     * @throws ServiceBizException (non-Javadoc)
     * @see com.yonyou.dms.customer.service.customerManage.SalesPromotionService#deleteSalesPromotionInfoByid(java.lang.Long)
     */

    @Override
    public void deleteSalesPromotionInfoByid(Long id) throws ServiceBizException {
//删除跟进活动
        TtSalesPromotionPlanPO salesProPo = TtSalesPromotionPlanPO.findById(id);

        salesProPo.delete();
    }

    /**
     * 跟进记录查询
     * 
     * @author zhanshiwei
     * @date 2016年9月19日
     * @param id
     * @return
     * @throws ServiceBizException (non-Javadoc)
     * @see com.yonyou.dms.customer.service.customerManage.SalesPromotionService#querySalesPromotionAllByid(java.lang.Long)
     */

    @Override
    public List<Map> querySalesPromotionAllByid(Long id) throws ServiceBizException {
        
        StringBuffer sb = new StringBuffer();
        sb.append(" select sp.PROMOTION_ID,sp.DEALER_CODE,pu.CUSTOMER_NAME,pu.POTENTIAL_CUSTOMER_NO,\n");
        sb.append(" sp.TASK_NAME,sp.SCHEDULE_DATE,sp.ACTION_DATE,sp.BOOKING_DATE, \n");
        sb.append(" sp.BOOKING_IS_ARRIVE,sp.PROM_RESULT,sp.SCENE, \n");
        sb.append(" sp.CONSULTANT,em.EMPLOYEE_NAME,sp.POTENTIAL_CUSTOMER_ID,sp.PROM_WAY,sp.PROM_CONTENT \n");
        sb.append(" from TT_SALES_PROMOTION sp2 \n");
        sb.append(" left join TM_POTENTIAL_CUSTOMER pu  on sp2.POTENTIAL_CUSTOMER_ID=pu.POTENTIAL_CUSTOMER_ID\n");
        sb.append(" left join TT_SALES_PROMOTION sp on sp.POTENTIAL_CUSTOMER_ID=sp2.POTENTIAL_CUSTOMER_ID \n");
        sb.append(" left join TM_EMPLOYEE em  on sp.CONSULTANT=em.EMPLOYEE_NO\n");
        sb.append(" where 1=1\n");
        sb.append(" and sp2.PROMOTION_ID=?\n").append(" and sp.PROM_RESULT <> ").append(DictCodeConstants.TRACKING_RESULT_NOT);
        List<Object> queryList = new ArrayList<Object>();
        queryList.add(id);
        return DAOUtil.findAll(sb.toString(), queryList);
    }

    /**
     * 重新分配
     * 
     * @author zhanshiwei
     * @date 2016年9月13日
     * @param salesProDto
     * @throws ServiceBizException (non-Javadoc)
     * @see com.yonyou.dms.customer.service.customerManage.SalesPromotionService#modifySalesPromotionForConsultant(com.yonyou.dms.customer.domains.DTO.customerManage.SalesPromotionDTO)
     */

    @Override
    public void modifySalesPromotionForConsultant(SalesPromotionDTO salesProDto) throws ServiceBizException {
        String[] ids = salesProDto.getPromotionIds().split(",");
        for (int i = 0; i < ids.length; i++) {
            long id = Long.parseLong(ids[i]);
            SalesPromotionPO salesProPo = SalesPromotionPO.findById(id);
            salesProPo.setString("CONSULTANT", salesProDto.getConsultant());// 销售顾问
            salesProPo.saveIt();
        }

    }

    /**
     * 战败原因下拉
     * 
     * @author zhanshiwei
     * @date 2016年9月18日
     * @param customeId
     * @return
     * @throws ServiceBizException (non-Javadoc)
     * @see com.yonyou.dms.customer.service.customerManage.SalesPromotionService#queryDefeatModel(java.lang.Long)
     */

    @Override
    public List<Map> queryDefeatModel(Long customeId) throws ServiceBizException {
        StringBuffer sb = new StringBuffer("\n").append("select fm.FAIL_MODEL_ID,fm.DEALER_CODE,fm.INTENT_SERIES,fm.FAIL_MODEL,fm.FAIL_MODEL_DESC\n").append("      from TM_FAIL_MODEL fm, TM_SERIES se,TT_CUSTOMER_INTENT cuin\n").append("where fm.INTENT_SERIES=se.SERIES_CODE and fm.DEALER_CODE=se.DEALER_CODE and se.SERIES_CODE=cuin.INTENT_SERIES\n").append("and se.DEALER_CODE=cuin.DEALER_CODE and fm.IS_VALID=").append(DictCodeConstants.STATUS_IS_YES);
        List<Object> queryList = new ArrayList<Object>();
        if (!StringUtils.isEquals(customeId, "-1")){
            sb .append(" and cuin.POTENTIAL_CUSTOMER_ID=?\n");
            queryList.add(customeId);
        }
       
        return DAOUtil.findAll(sb.toString(), queryList);
    }

    /**
     * 待跟进查询，右侧快捷
     * 
     * @author yll
     * @date 2016年10月13日
     * @return
     * @throws ServiceBizException (non-Javadoc)
     * @see com.yonyou.dms.customer.service.customerManage.SalesPromotionService#quickQuery()
     */
    @Override
    public List<Map> quickQuery() throws ServiceBizException {
    	LoginInfoDto loginInfo = ApplicationContextHelper.getBeanByType(LoginInfoDto.class);
    	//判断用户要是DCS用户还是DMS用户
    	if(loginInfo.getSysType()==1){
    		return null;
    	}else{
    		StringBuffer sb = new StringBuffer();
    		sb.append("SELECT sp.LAST_SCENE, sp.PROMOTION_ID, sp.DEALER_CODE, pu.CUSTOMER_NAME, pu.POTENTIAL_CUSTOMER_NO, pu.CONTACTOR_MOBILE, sp.TASK_NAME, DATE_FORMAT( sp.SCHEDULE_DATE, '%y-%m-%d' ) AS SCHEDULE_DATE, tc.CODE_CN_DESC AS PRIOR_GRADE, CASE tc.CODE_ID WHEN '13101007' THEN 1 WHEN '13101001' THEN 2 WHEN '13101006' THEN 3 WHEN '13101002' THEN 4 WHEN '13101003' THEN 5 WHEN '13101004' THEN 6 WHEN '13101008' THEN 7 WHEN '13101005' THEN 8 WHEN '13101009' THEN 9 ELSE 10 END AS PX, sp.SCENE, sp.CONSULTANT, em.EMPLOYEE_NAME, ( SELECT SCENE FROM TT_SALES_PROMOTION sp2 WHERE sp2.PROMOTION_ID <> sp.PROMOTION_ID AND sp.POTENTIAL_CUSTOMER_ID = sp2.POTENTIAL_CUSTOMER_ID ORDER BY UPDATED_AT DESC LIMIT 1 ) AS LASTSCENE FROM TT_SALES_PROMOTION sp LEFT JOIN tc_code tc ON tc.CODE_ID = sp.PRIOR_GRADE LEFT JOIN TM_POTENTIAL_CUSTOMER pu ON sp.POTENTIAL_CUSTOMER_ID = pu.POTENTIAL_CUSTOMER_ID LEFT JOIN TM_EMPLOYEE em ON sp.CONSULTANT = em.EMPLOYEE_NO AND sp.DEALER_CODE = em.DEALER_CODE WHERE 1 = 1");
    		List<Object> queryList = new ArrayList<Object>();
    		String employeeNo = String.valueOf(loginInfo.getUserId());
    		sb.append(" and em.EMPLOYEE_NO=?");
    		queryList.add(employeeNo);
    		sb.append(" AND sp.PROM_RESULT=?");
    		queryList.add(DictCodeConstants.TRACKING_RESULT_NOT);//未跟进
    		sb.append(" and sp.SCHEDULE_DATE=?");//取当天的
    		queryList.add(DateUtil.formatDefaultDate(new Date()));
    		sb.append(" ORDER BY PX ASC");
    		return DAOUtil.findAll(sb.toString(), queryList);
    	}
    }
    
    //展厅接待(接待)
	@Override
	public Long addSalesPromotion(String itemId, TtSalesPromotionPlanDTO salesProDto) throws ServiceBizException {
		String status = "A";
        LoginInfoDto loginInfo = ApplicationContextHelper.getBeanByType(LoginInfoDto.class);
        PotentialCusPO potentialCusPo=PotentialCusPO.findByCompositeKeys(loginInfo.getDealerCode(),salesProDto.getCustomerNo());
        VisitingRecordPO visitPo = VisitingRecordPO.findByCompositeKeys(itemId,FrameworkUtil.getLoginInfo().getDealerCode());
        visitPo.setString("INTENT_LEVEL", salesProDto.getNextGrade());
        visitPo.setInteger("IS_STEP_FORWARD_GREETING", 12781001);
        visitPo.saveIt();
        if(potentialCusPo==null){
            throw new ServiceBizException("潜客"+salesProDto.getCustomerNo()+"不存在,请重新选择！");
        }else{
        	//试乘试驾新增字段保存
        	potentialCusPo.setString("IS_TEST_DRIVE", salesProDto.getIsTestDrive());
        	if("12781002".equals(salesProDto.getIsTestDrive())){            		
        		potentialCusPo.setString("TEST_DRIVE_REMARK", salesProDto.getTestDriveRemark());
        	}
        	potentialCusPo.saveIt();
            //客户跟进任务
            int ver1 = potentialCusPo.getInteger("VER");
            List<Object> taskList = new ArrayList<Object>();
            taskList.add(salesProDto.getPriorGrade());
            taskList.add(Long.parseLong(DictCodeConstants.DICT_CUSTOMER_STATUS_LATENCY));
            taskList.add(DictCodeConstants.IS_YES);
            taskList.add(loginInfo.getDealerCode());
            List<TrackingTaskPO> pp=TrackingTaskPO.findBySQL("select * from TM_TRACKING_TASK where INTENT_LEVEL= ? AND CUSTOMER_STATUS= ? AND IS_VALID= ? AND DEALER_CODE= ? ", taskList.toArray());
            TtSalesPromotionPlanPO salesPo = new TtSalesPromotionPlanPO();
            this.setSalesPromotionPo(salesPo, salesProDto);
            if(pp!=null&&pp.size()>0){
                TrackingTaskPO taskPo = pp.get(0);
                salesPo.setLong("TASK_ID", taskPo.getLong("TASK_ID"));
            }
            salesPo.setString("CUSTOMER_NO",salesProDto.getCustomerNo());
            if(potentialCusPo.getLong("IS_BIG_CUSTOMER")==12781001){
                salesPo.setLong("IS_BIG_CUSTOMER_PLAN", DictCodeConstants.IS_YES);
            }
            int ver = 1;
            salesPo.setInteger("PRIOR_GRADE",salesProDto.getPriorGrade());
            salesPo.setInteger("NEXT_GRADE",salesProDto.getNextGrade());
            salesPo.setDate("SCHEDULE_DATE", salesProDto.getScheduleDate());
            salesPo.setInteger("PROM_WAY",salesProDto.getPromWay());
            salesPo.setString("PROM_CONTENT",salesProDto.getPromContent());
            salesPo.setInteger("PROM_RESULT",salesProDto.getPromResult());
            salesPo.setString("SCENE",salesProDto.getScene());
            salesPo.setInteger("CREATE_TYPE",Integer.parseInt(DictCodeConstants.DICT_CREATE_TYPE_HANDWORK));
 /*           if(salesProDto.getIsAuditing()!=null&&salesProDto.getIsAuditing()!=0){
                salesPo.setInteger("IS_AUDITING",salesProDto.getIsAuditing());
            }else{
                salesPo.setInteger("IS_AUDITING",12781002);
            }
            salesPo.setString("AUDITED_BY",salesProDto.getAuditedBy());
            salesPo.setString("AUDITING_REMARK",salesProDto.getAuditingRemark());*/
            if(!StringUtils.isNullOrEmpty(salesProDto.getBookingCustomerType())){
                salesPo.setInteger("BOOKING_CUSTOMER_TYPE", salesProDto.getBookingCustomerType());
                salesPo.setDate("BOOKING_DATE", salesProDto.getBookingDate());
            }else{
                salesPo.setInteger("BOOKING_CUSTOMER_TYPE", 12781002);
                salesPo.setDate("BOOKING_DATE", null);
            }
            salesPo.setInteger("VER", ver);
            
            salesPo.saveIt();
            System.out.println(salesPo.getLongId());
          //大客户意向明细信息新增
            if(salesProDto.getBigCustomerIntentList().size()>0&&salesProDto.getBigCustomerIntentList()!=null){
                System.out.println("大客户意向明细信息新增1");
                for(TtBigCustomerVisitingIntentDTO bigIntentDTO : salesProDto.getBigCustomerIntentList()){
                    TtBigCustomerVisitingIntentPO PO = new TtBigCustomerVisitingIntentPO();
                    System.out.println("大客户意向明细信息新增2");
                    PO.setLong("ITEM_ID", salesPo.getLongId());
                    PO.setLong("INTENT_ID", salesProDto.getIntentId());
                    PO.setString("OWNED_BY", String.valueOf(loginInfo.getUserId()));
                    setBigCustomerVisitingIntent(bigIntentDTO,PO).saveIt();
                }
            }
            System.out.println("新增结束111");
            addAndUpdatePublicMethods(salesProDto,salesPo.getLongId(),potentialCusPo,ver1,status);
            //新增展厅记录
            System.out.println("新增展厅记录1111");
            System.out.println(commonNoService.getDefalutPara("8098"));
            if(!StringUtils.isNullOrEmpty(commonNoService.getDefalutPara("8098"))&&commonNoService.getDefalutPara("8098").equals("12781002")){
                createAddRecord(salesProDto,loginInfo);
            }
            //更新
            if(salesProDto.getCustomerNo()!=null){
                List<Object> salesPromotionList = new ArrayList<Object>();
                salesPromotionList.add(salesProDto.getCustomerNo());
                salesPromotionList.add(loginInfo.getDealerCode());          
                TtSalesPromotionPlanPO UPromotionPO = TtSalesPromotionPlanPO.findFirst(" CUSTOMER_NO= ? AND DEALER_CODE= ? AND ACTION_DATE IS NOT NULL ORDER BY ITEM_ID DESC ",salesPromotionList.toArray());
                List<TtSalesPromotionPlanPO> salesPromotionPO=TtSalesPromotionPlanPO.findBySQL("select * from TT_SALES_PROMOTION_PLAN where  CUSTOMER_NO= ? AND DEALER_CODE= ? AND (PROM_RESULT IS NULL OR PROM_RESULT = 0) ", salesPromotionList.toArray());
                if(salesPromotionPO!=null&&!StringUtils.isNullOrEmpty(UPromotionPO)){
                    for(int k = 0;k<salesPromotionPO.size();k++){
                        TtSalesPromotionPlanPO  salesPromotion = salesPromotionPO.get(k);
                        
                        salesPromotion.setString("LAST_SCENE",UPromotionPO.getString("SCENE"));
                        salesPromotion.saveIt();
                    }
                }
                 
                
            }
            //ACTION(MaintainSalesPromotionPlanDetail)
            maintainSalesPromotionPlanDetail(status,salesProDto,loginInfo);
             
            return salesPo.getLongId();
                
        }
	}

}
