
/** 
*Copyright 2017 Yonyou Corporation Ltd. All Rights Reserved.
* This software is published under the terms of the Yonyou Software
* License version 1.0, a copy of which has been included with this
* distribution in the LICENSE.txt file.
*
* @Project Name : dms.retail
*
* @File name : SalesOrderCancelServiceImp.java
*
* @Author : LiGaoqi
*
* @Date : 2017年1月13日
*
----------------------------------------------------------------------------------
*     Date       Who       Version     Comments
* 1. 2017年1月13日    LiGaoqi    1.0
*
*
*
*
----------------------------------------------------------------------------------
*/
	
package com.yonyou.dms.retail.service.ordermanage;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.yonyou.dms.common.domains.DTO.basedata.SalesOrderCancelDTO;
import com.yonyou.dms.common.domains.PO.basedata.PotentialCusPO;
import com.yonyou.dms.common.domains.PO.basedata.TmPartStockPO;
import com.yonyou.dms.common.domains.PO.basedata.TtIntentSalesOrderPO;
import com.yonyou.dms.common.domains.PO.basedata.TtOrderStatusUpdatePO;
import com.yonyou.dms.common.domains.PO.basedata.TtPartObligatedItemPO;
import com.yonyou.dms.common.domains.PO.basedata.TtPartObligatedPO;
import com.yonyou.dms.common.domains.PO.basedata.TtSalesPromotionPlanPO;
import com.yonyou.dms.common.domains.PO.basedata.TtWsDatumDetailPO;
import com.yonyou.dms.common.domains.PO.basedata.TtWsSalesInfoPO;
import com.yonyou.dms.common.domains.PO.basedata.VsStockPO;
import com.yonyou.dms.commonSales.domains.PO.stockmanage.SalesOrderPO;
import com.yonyou.dms.framework.DAO.DAOUtil;
import com.yonyou.dms.framework.DAO.DAOUtilGF;
import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.framework.domain.LoginInfoDto;
import com.yonyou.dms.framework.util.bean.ApplicationContextHelper;
import com.yonyou.dms.function.common.CommonConstants;
import com.yonyou.dms.function.common.DictCodeConstants;
import com.yonyou.dms.function.exception.ServiceBizException;
import com.yonyou.dms.function.utils.common.DateUtil;
import com.yonyou.dms.function.utils.common.StringUtils;


/**
* TODO description
* @author LiGaoqi
* @date 2017年1月13日
*/
@Service
public class SalesOrderCancelServiceImp implements SalesOrderCancelService {
 
    
    /**
    * @author LiGaoqi
    * @date 2017年1月13日
    * @param queryParam
    * @return
    * @throws ServiceBizException
    * (non-Javadoc)
    * @see com.yonyou.dms.retail.service.ordermanage.SalesOrderCancelService#qrySalesOrdersCancel(java.util.Map)
    */

    @Override
    public PageInfoDto querySalesOrdersCancel(Map<String, String> queryParam) throws ServiceBizException {
        List<Object> params = new ArrayList<Object>();
        LoginInfoDto loginInfo = ApplicationContextHelper.getBeanByType(LoginInfoDto.class);
        StringBuffer sql = new StringBuffer("");
        
        sql.append(" SELECT em.USER_NAME,br.BRAND_NAME,se.SERIES_NAME,mo.MODEL_NAME,pa.CONFIG_NAME,co.COLOR_NAME,A.DEALER_CODE,A.EC_ORDER_NO,A.BUSINESS_TYPE,A.SO_NO,A.SHEET_CREATE_DATE,A.SOLD_BY,");
        sql.append("(case when A.CUSTOMER_NAME is null then A.CONSIGNEE_NAME else A.CUSTOMER_NAME end) AS CUSTOMER_NAME,A.VIN,A.PAY_OFF,");
        sql.append(" B.MODEL_CODE,B.CONFIG_CODE,COALESCE(A.COLOR_CODE,B.COLOR_CODE) as COLOR_CODE,A.ORDER_RECEIVABLE_SUM,A.ABORTING_FLAG,A.SO_STATUS,A.ORDER_DERATED_SUM, ");
        sql.append(" A.ORDER_PAYED_AMOUNT,A.ORDER_ARREARAGE_AMOUNT,A.CON_RECEIVABLE_SUM,A.PENALTY_AMOUNT,A.ABORTING_REASON,");
        sql.append(" A.CON_PAYED_AMOUNT,A.CON_ARREARAGE_AMOUNT,");
        sql.append("(case when A.CUSTOMER_NO is null then A.CONSIGNEE_CODE else A.CUSTOMER_NO end) AS CUSTOMER_NO,A.CUSTOMER_TYPE,A.VER,");
        sql.append(" A.CONTACTOR_NAME,A.MOBILE,A.PHONE,A.CONTRACT_NO,A.CONTRACT_DATE,A.CT_CODE,A.CERTIFICATE_NO");
        sql.append(" FROM TT_SALES_ORDER A  ");
        sql.append(" LEFT JOIN ("+CommonConstants.VM_VS_PRODUCT+") B ON A.PRODUCT_CODE = B.PRODUCT_CODE AND A.DEALER_CODE =B.DEALER_CODE AND A.D_KEY=B.D_KEY left join TM_USER em  on A.SOLD_BY=em.USER_ID AND A.DEALER_CODE =em.DEALER_CODE ");
        sql.append(" left  join   tm_configuration pa   on   B.CONFIG_CODE=pa.CONFIG_CODE and B.DEALER_CODE=pa.DEALER_CODE");
        sql.append(" left  join   TM_MODEL   mo   on   B.MODEL_CODE=mo.MODEL_CODE and B.DEALER_CODE=mo.DEALER_CODE");
        sql.append(" left  join   TM_SERIES  se   on   B.SERIES_CODE=se.SERIES_CODE and B.DEALER_CODE=se.DEALER_CODE");
        sql.append(" left  join   tm_brand   br   on   B.BRAND_CODE = br.BRAND_CODE and B.DEALER_CODE=br.DEALER_CODE");
        sql.append(" left  join   tm_color   co   on   B.COLOR_CODE = co.COLOR_CODE and B.DEALER_CODE=co.DEALER_CODE");
        sql.append(" WHERE A.SO_STATUS != "
                + DictCodeConstants.DICT_SO_STATUS_HAVE_UNTREAD//已退回
                + " AND A.SO_STATUS != "
                + DictCodeConstants.DICT_SO_STATUS_HAVE_CANCEL//已取消
                + " AND A.SO_STATUS != "
                + DictCodeConstants.DICT_SO_STATUS_HAVE_OUT_STOCK//已完成
                + " AND A.SO_STATUS != "
                + DictCodeConstants.DICT_SO_STATUS_UNTREAT_HAVE_IN_STOCK//退回已入库
                + " AND A.SO_STATUS != "
                + DictCodeConstants.DICT_SO_STATUS_CLOSED//已关单
                + " AND A.SO_STATUS != "
                + DictCodeConstants.DICT_SO_STATUS_HAVE_CONFIRMED//已交车确认
                + " AND A.D_KEY =" + DictCodeConstants.D_KEY
                +" AND COALESCE(A.DEAL_STATUS,12781002)<>12781001");
        if (!StringUtils.isNullOrEmpty(queryParam.get("customerName"))) {
            sql.append(" and A.CUSTOMER_NAME like ?");
            params.add("%" + queryParam.get("customerName") + "%");
        }
        if (!StringUtils.isNullOrEmpty(queryParam.get("soNo"))) {
            sql.append(" and A.SO_NO like ?");
            params.add("%" + queryParam.get("soNo") + "%");
        }
        if (!StringUtils.isNullOrEmpty(queryParam.get("contractNo"))) {
            sql.append(" and A.CONTRACT_NO like ?");
            params.add("%" + queryParam.get("contractNo") + "%");
        }

        if (!StringUtils.isNullOrEmpty(queryParam.get("sheetCreateDate_startdate"))) {
            sql.append(" and A.SHEET_CREATE_DATE>= ?");
            params.add(DateUtil.parseDefaultDate(queryParam.get("sheetCreateDate_startdate")));
        }
        if (!StringUtils.isNullOrEmpty(queryParam.get("sheetCreateDate_enddate"))) {
            sql.append(" and A.SHEET_CREATE_DATE<?");
            params.add(DateUtil.addOneDay(queryParam.get("sheetCreateDate_enddate")));
        }
        System.out.println(DAOUtilGF.getOwnedByStr("A", loginInfo.getUserId(), loginInfo.getOrgCode(),  "70107000", loginInfo.getDealerCode()));
        if(" AND A.OWNED_BY".equals(DAOUtilGF.getOwnedByStr("A", loginInfo.getUserId(), loginInfo.getOrgCode(),  "70107000", loginInfo.getDealerCode()))){
            sql.append(" AND 1=1");
        }else{
            
            sql.append(DAOUtilGF.getOwnedByStr("A", loginInfo.getUserId(), loginInfo.getOrgCode(),  "70107000", loginInfo.getDealerCode()));
            //替换权限
        }
       
        sql.append(" ORDER BY A.ABORTING_FLAG");
        
       
        PageInfoDto result = DAOUtil.pageQuery(sql.toString(), params);
        System.out.println("11111111111111");
        return result;
    }

    
    /**
    * @author LiGaoqi
    * @date 2017年1月13日
    * @param salesOrderCancelDTO
    * @throws ServiceBizException
    * 申请取消
    * @see com.yonyou.dms.retail.service.ordermanage.SalesOrderCancelService#cancelApply(com.yonyou.dms.common.domains.DTO.basedata.SalesOrderCancelDTO)
    */
    	
    @Override
    public void cancelApply(SalesOrderCancelDTO salesOrderCancelDTO) throws ServiceBizException {
        LoginInfoDto loginInfo = ApplicationContextHelper.getBeanByType(LoginInfoDto.class);
        String[] nos = salesOrderCancelDTO.getApplyList().split(",");
        for (int i = 0; i < nos.length; i++){
            String soNo = nos[i];
            System.out.println(soNo);
            if(!StringUtils.isNullOrEmpty(soNo)){
                SalesOrderPO salesOrderPo = SalesOrderPO.findByCompositeKeys(loginInfo.getDealerCode(),soNo);
                System.out.println(salesOrderPo);
                if(salesOrderPo!=null){
                salesOrderPo.setInteger("ABORTING_FLAG", DictCodeConstants.STATUS_IS_YES);
                salesOrderPo.setString("ABORTING_BY", loginInfo.getEmployeeNo());
                salesOrderPo.setDate("ABORTING_DATE", new Date());
                System.out.println(salesOrderCancelDTO.getPenaltyAmount());
                salesOrderPo.setDouble("PENALTY_AMOUNT", salesOrderCancelDTO.getPenaltyAmount());
                salesOrderPo.setInteger("ABORTING_REASON", salesOrderCancelDTO.getAbortingReason());
                salesOrderPo.saveIt(); 
                //销售顾问要不要一起变
                }
            }
        }
       
        
    }


    
    /**
    * 取消订单
    * @author LiGaoqi
    * @date 2017年1月13日
    * @param salesOrderCancelDTO
    * @throws ServiceBizException
    * (non-Javadoc)
    * @see com.yonyou.dms.retail.service.ordermanage.SalesOrderCancelService#cancelOrder(com.yonyou.dms.common.domains.DTO.basedata.SalesOrderCancelDTO)
    */
    	
    @Override
    public void cancelOrder(SalesOrderCancelDTO salesOrderCancelDTO) throws ServiceBizException {
        LoginInfoDto loginInfo = ApplicationContextHelper.getBeanByType(LoginInfoDto.class);
        String[] nos = salesOrderCancelDTO.getApplyList().split(",");
        String obligatedNo = "";
        for (int i = 0; i < nos.length; i++){
            String soNo = nos[i];
            if(!StringUtils.isNullOrEmpty(soNo)){
                SalesOrderPO salesOrderPo = SalesOrderPO.findByCompositeKeys(loginInfo.getDealerCode(),soNo);
                SalesOrderPO salesOrder = SalesOrderPO.findByCompositeKeys(loginInfo.getDealerCode(),soNo);
                System.out.println(salesOrderPo);
                if(salesOrderPo!=null){
                    List<Object> partObligatedList = new ArrayList<Object>();
                    partObligatedList.add(soNo);
                    partObligatedList.add(loginInfo.getDealerCode());
                    partObligatedList.add(Long.parseLong(DictCodeConstants.D_KEY));
                    partObligatedList.add(DictCodeConstants.STATUS_IS_NOT);
                    partObligatedList.add(Integer.parseInt(DictCodeConstants.DICT_SO_TYPE_SERVICE));
                    List<TtPartObligatedPO> partObligatedCon = TtPartObligatedPO.findBySQL("select * from TT_PART_OBLIGATED where SHEET_NO= ? AND DEALER_CODE= ? AND D_KEY= ? AND OBLIGATED_CLOSE= ? AND SHEET_NO_TYPE= ? ", partObligatedList.toArray());
                    if(StringUtils.isNullOrEmpty(partObligatedCon)){
                        throw new ServiceBizException("系统错误!");
                    }
                    if(partObligatedCon!=null&&partObligatedCon.size()>0){
                        TtPartObligatedPO partObligated =partObligatedCon.get(0);
                        obligatedNo = partObligated.getString("OBLIGATED_NO");
                    }
                    List<Object> partObligatedItemList = new ArrayList<Object>();
                    partObligatedItemList.add(obligatedNo);
                    partObligatedItemList.add(loginInfo.getDealerCode());
                    partObligatedItemList.add(Long.parseLong(DictCodeConstants.D_KEY));
                    List<TtPartObligatedItemPO> partObligatedItemCon = TtPartObligatedItemPO.findBySQL("select * from TT_PART_OBLIGATED_ITEM where OBLIGATED_NO= ? AND DEALER_CODE= ? AND D_KEY= ? ", partObligatedItemList.toArray());
                    if(partObligatedItemCon!=null&&partObligatedItemCon.size()>0){
                        for(int j=0;j<partObligatedItemCon.size();j++){
                            TtPartObligatedItemPO partObligatedItem = partObligatedItemCon.get(j);
                            if(partObligatedItem!=null){
                                if(!StringUtils.isNullOrEmpty(partObligatedItem.getString("PART_NO"))){
                                    List<Object> partList = new ArrayList<Object>();
                                    partList.add(partObligatedItem.getString("PART_NO"));
                                    partList.add(partObligatedItem.getString("STORAGE_CODE"));
                                    partList.add(loginInfo.getDealerCode());
                                    partList.add(Long.parseLong(DictCodeConstants.D_KEY));
                                    List<TmPartStockPO> partPO = TmPartStockPO.findBySQL("select * from TM_PART_STOCK where PART_NO= ? AND STORAGE_CODE= ? AND DEALER_CODE= ? AND D_KEY= ? ", partList.toArray());
                                    if(!StringUtils.isNullOrEmpty(partPO)&&partPO.size()>0){
                                        for(int a=0;a<partPO.size();a++){
                                            TmPartStockPO part = partPO.get(a);
                                            part.setDouble("LOCKED_QUANTITY", part.getDouble("LOCKED_QUANTITY")-partObligatedItem.getDouble("QUANTITY"));
                                            part.saveIt();
                                        }
                                    }
                                }
                                partObligatedItem.delete();
                                
                            }
                           
                        }
                        TtPartObligatedPO ObligatedCon = TtPartObligatedPO.findByCompositeKeys(obligatedNo,loginInfo.getDealerCode());
                        if(ObligatedCon!=null){
                            ObligatedCon.setInteger("OBLIGATED_CLOSE", DictCodeConstants.STATUS_IS_YES);
                            ObligatedCon.saveIt();
                        }
                        
                    }
                  //已处理
                    if(!StringUtils.isNullOrEmpty(salesOrderPo.getInteger("DEAL_STATUS"))&&salesOrderPo.getInteger("DEAL_STATUS")==DictCodeConstants.STATUS_IS_NOT){
                        throw new ServiceBizException("该订单在处理状态中，暂不能处理！");
                    }
                 /*   if(salesOrderPo.getInteger("SO_STATUS")==Integer.parseInt(DictCodeConstants.DICT_SO_STATUS_NOT_AUDIT)){
                        IS_NOT_AUDIT = "12781001";
                    }*/
                 // 修改订单状态
                    salesOrderPo.setInteger("SO_STATUS", Integer.parseInt(DictCodeConstants.DICT_SO_STATUS_HAVE_CANCEL));
                    salesOrderPo.setString("ABORTED_BY", loginInfo.getEmployeeNo());
                    salesOrderPo.setDate("ABORTED_DATE",new Date());
                    salesOrderPo.setInteger("Aborting_Reason",salesOrderCancelDTO.getAbortingReason());
                    salesOrderPo.setDouble("PENALTY_AMOUNT", salesOrderCancelDTO.getPenaltyAmount());
                    salesOrderPo.setInteger("IS_UPLOAD", DictCodeConstants.STATUS_IS_NOT);
                    salesOrderPo.setInteger("D_KEY", Integer.parseInt(DictCodeConstants.D_KEY));
                  //收款判断
                    if(!StringUtils.isNullOrEmpty(salesOrderPo.getInteger("BUSINESS_TYPE"))&&(salesOrderPo.getInteger("BUSINESS_TYPE")==Integer.parseInt(DictCodeConstants.DICT_SO_TYPE_GENERAL)||salesOrderPo.getInteger("BUSINESS_TYPE")==Integer.parseInt(DictCodeConstants.DICT_SO_TYPE_SERVICE))){
                        if((!StringUtils.isNullOrEmpty(salesOrderPo.getDouble("ORDER_PAYED_AMOUNT"))&&salesOrderPo.getDouble("ORDER_PAYED_AMOUNT")> 0.0)||(!StringUtils.isNullOrEmpty(salesOrderPo.getDouble("CON_PAYED_AMOUNT"))&&salesOrderPo.getDouble("CON_PAYED_AMOUNT")> 0.0)){
                            throw new ServiceBizException("该订单存在收款，请先退款！");
                        }
                    }
                  //记订单审核历史
                    if(!StringUtils.isNullOrEmpty(salesOrderPo.getInteger("BUSINESS_TYPE"))&&(salesOrderPo.getInteger("BUSINESS_TYPE")==Integer.parseInt(DictCodeConstants.DICT_SO_TYPE_GENERAL)||salesOrderPo.getInteger("BUSINESS_TYPE")==Integer.parseInt(DictCodeConstants.DICT_SO_TYPE_ENTRUST_DELIVERY))){
                        if(!StringUtils.isNullOrEmpty(salesOrderPo.getInteger("SO_STATUS"))&&(salesOrderPo.getInteger("SO_STATUS")==Integer.parseInt(DictCodeConstants.DICT_SO_STATUS_MANAGER_AUDITING)||salesOrderPo.getInteger("SO_STATUS")==Integer.parseInt(DictCodeConstants.DICT_SO_STATUS_MANAGER_REJECT)||salesOrderPo.getInteger("SO_STATUS")==Integer.parseInt(DictCodeConstants.DICT_SO_STATUS_SYSTEM_REJECT))){
                            List<Object> orderStatusList = new ArrayList<Object>();
                            orderStatusList.add(soNo);
                            orderStatusList.add(loginInfo.getDealerCode());
                            orderStatusList.add(Long.parseLong(DictCodeConstants.D_KEY));
                            List<TtOrderStatusUpdatePO> orderStatusPo = TtOrderStatusUpdatePO.findBySQL("select * from TT_ORDER_STATUS_UPDATE where SO_NO= ? AND DEALER_CODE= ? AND D_KEY= ? ", orderStatusList.toArray());
                            if(!StringUtils.isNullOrEmpty(orderStatusPo)&&orderStatusPo.size()>0){
                                TtOrderStatusUpdatePO orderStatus = new TtOrderStatusUpdatePO();
                                orderStatus.setString("SO_NO",soNo);
                                orderStatus.setInteger("SO_STATUS",Integer.parseInt(DictCodeConstants.DICT_SO_TYPE_GENERAL));
                                orderStatus.setString("OWNED_BY",loginInfo.getEmployeeNo());
                                orderStatus.setInteger("IS_UPLOAD",DictCodeConstants.STATUS_IS_NOT);
                                orderStatus.setDate("ALTERATION_TIME", new Date());
                                orderStatus.setInteger("D_KEY", Integer.parseInt(DictCodeConstants.D_KEY)); 
                                orderStatus.saveIt();
                                
                            }
                            
                        }else{
                            TtOrderStatusUpdatePO orderStatus = new TtOrderStatusUpdatePO();
                            orderStatus.setString("SO_NO",soNo);
                            orderStatus.setInteger("SO_STATUS",Integer.parseInt(DictCodeConstants.DICT_SO_TYPE_GENERAL));
                            orderStatus.setString("OWNED_BY",loginInfo.getEmployeeNo());
                            orderStatus.setInteger("IS_UPLOAD",DictCodeConstants.STATUS_IS_NOT);
                            orderStatus.setDate("ALTERATION_TIME", new Date());
                            orderStatus.setInteger("D_KEY", Integer.parseInt(DictCodeConstants.D_KEY)); 
                            orderStatus.saveIt();
                        }
                    }
                    salesOrderPo.saveIt();
                 // 修改车辆配车状态
                    SalesOrderPO salesOrderPo1 = SalesOrderPO.findByCompositeKeys(loginInfo.getDealerCode(),soNo);
                    if(salesOrderPo1.getInteger("BUSINESS_TYPE")!=Integer.parseInt(DictCodeConstants.DICT_SO_TYPE_SERVICE)&&salesOrderPo1.getInteger("BUSINESS_TYPE")!=Integer.parseInt(DictCodeConstants.DICT_SO_TYPE_RERURN)){
                        if(!StringUtils.isNullOrEmpty(salesOrderPo1.getInteger("DELIVERY_MODE"))&&salesOrderPo1.getInteger("DELIVERY_MODE")==Integer.parseInt(DictCodeConstants.DICT_DELIVERY_MODE_LOCAL)){
                            List<Object> VsStockList = new ArrayList<Object>();
                            VsStockList.add(salesOrderPo1.getString("VIN"));
                            VsStockList.add(loginInfo.getDealerCode());
                            VsStockList.add(Long.parseLong(DictCodeConstants.D_KEY));
                            List<VsStockPO> VsStockListPo = VsStockPO.findBySQL("select * from TM_VS_STOCK where VIN= ? AND DEALER_CODE= ? AND D_KEY= ? ", VsStockList.toArray());
                            if(!StringUtils.isNullOrEmpty(VsStockListPo)&&VsStockListPo.size()>0){
                                for(int b=0;b<VsStockListPo.size();b++){
                                    VsStockPO stock = VsStockListPo.get(b);
                                    stock.setString("SO_NO", null);
                                    stock.setInteger("DISPATCHED_STATUS",DictCodeConstants.DICT_DISPATCHED_STATUS_NOT_DISPATCHED);
                                    stock.setInteger("D_KEY",Integer.parseInt(DictCodeConstants.D_KEY));
                                    stock.saveIt();
                                }
                            }
                        }
                    }
                  //查询订单数
                    int count=2;
                    count = this.queryPOCustomerOrderCount(salesOrderPo1.getString("CUSTOMER_NO"), loginInfo.getDealerCode());
                    if(count<=1&&!StringUtils.isNullOrEmpty(salesOrderPo1.getInteger("BUSINESS_TYPE"))&&salesOrderPo1.getInteger("BUSINESS_TYPE")==Integer.parseInt(DictCodeConstants.DICT_SO_TYPE_GENERAL)){
                        PotentialCusPO potentialCusPo=PotentialCusPO.findByCompositeKeys(loginInfo.getDealerCode(),salesOrderPo1.getString("CUSTOMER_NO")); 
                        potentialCusPo.setInteger("INTENT_LEVEL", Integer.parseInt(DictCodeConstants.DICT_INTENT_LEVEL_N));
                        potentialCusPo.setInteger("D_KEY", Integer.parseInt(DictCodeConstants.D_KEY));
                        potentialCusPo.setDate("DDCN_UPDATE_DATE",new Date());
                        potentialCusPo.saveIt();
                    }
                    /**生成销售订单后跟进活动中会生成一条"促进前级别"为O级的跟进记录，如果该记录没有进行跟进时 那么删除*/
                    List<Object> promotionPlanList = new ArrayList<Object>();
                    promotionPlanList.add(salesOrderPo1.getString("CUSTOMER_NO"));                   
                    promotionPlanList.add(loginInfo.getDealerCode());
                    promotionPlanList.add(13101008);
                    promotionPlanList.add(Integer.parseInt(DictCodeConstants.D_KEY));
                    List<TtSalesPromotionPlanPO> promotionPlanPo = TtSalesPromotionPlanPO.findBySQL("select * from TT_SALES_PROMOTION_PLAN where CUSTOMER_NO= ? AND DEALER_CODE= ? AND PRIOR_GRADE= ? AND D_KEY= ? ", promotionPlanList.toArray());
                    if(!StringUtils.isNullOrEmpty(promotionPlanPo)&&promotionPlanPo.size()>0){
                        TtSalesPromotionPlanPO promotionPlan = promotionPlanPo.get(0);
                        if(!StringUtils.isNullOrEmpty(promotionPlan)){
                            promotionPlan.delete();
                        }
                    }
                    /**
                     * 1)在销售订单做取消操作时，增加判断，如果被取消的销售订单存在批售销售信息，则删除批售销售信息；
                     * 2)在销售订单做销售退回操作时，增加判断，如果被退回的销售订单存在批售销售信息，且批售销售信息未上报或为驳回拒绝状态，则删除批售销售信息；
                     */
                    if(!StringUtils.isNullOrEmpty(salesOrder.getInteger("BUSINESS_TYPE"))&&salesOrder.getInteger("BUSINESS_TYPE")==Integer.parseInt(DictCodeConstants.DICT_SO_TYPE_GENERAL)){
                        List<Object> queryList = new ArrayList<Object>();
                        queryList.add(loginInfo.getDealerCode()); 
                        queryList.add(soNo);
                        queryList.add(salesOrder.getString("VIN"));
                        queryList.add(Integer.parseInt(DictCodeConstants.DICT_WHOLESALE_CAR_STATUS_NOT));
                        queryList.add(Integer.parseInt(DictCodeConstants.DICT_WHOLESALE_CAR_STATUS_REFUSE));
                        TtWsSalesInfoPO po = TtWsSalesInfoPO.findFirst(" DEALER_CODE= ? AND SO_NO= ? AND VIN= ? AND (AUDITING_STATUS= ? OR AUDITING_STATUS= ?) ",queryList.toArray());           
                        if(!StringUtils.isNullOrEmpty(po)){
                            List<Object> wsItemList = new ArrayList<Object>();
                            wsItemList.add(loginInfo.getDealerCode()); 
                            wsItemList.add(po.getLong("SALES_ITEM_ID"));
                            List<TtWsDatumDetailPO> wsItemPo = TtWsDatumDetailPO.findBySQL("select * from TT_WS_DATUM_DETAIL where DEALER_CODE= ? AND SALES_ITEM_ID= ? ", wsItemList.toArray());
                            if(wsItemPo!=null&&wsItemPo.size()>0)
                            {
                                for(int c=0;c<wsItemPo.size();c++){
                                    TtWsDatumDetailPO wsItem = wsItemPo.get(c);
                                    wsItem.delete();
                                }
                                
                            }
                           
                            po.delete();
                        }
                    }
                    if(!StringUtils.isNullOrEmpty(salesOrder.getInteger("BUSINESS_TYPE"))&&salesOrder.getInteger("BUSINESS_TYPE")==Integer.parseInt(DictCodeConstants.DICT_SO_TYPE_RERURN)&&salesOrder.getInteger("SO_STATUS")>Integer.parseInt(DictCodeConstants.DICT_SO_STATUS_ACCOUNTANT_AUDITING)){
                        List<Object> orderList = new ArrayList<Object>();
                        orderList.add(salesOrder.getString("OLD_SO_NO"));
                        orderList.add(loginInfo.getDealerCode());
                        orderList.add(Integer.parseInt(DictCodeConstants.D_KEY));
                        SalesOrderPO orderPo = SalesOrderPO.findFirst(" SO_NO= ? AND DEALER_CODE= ? AND D_KEY= ? ", orderList.toArray());
                        if(orderPo!=null){
                            if(!StringUtils.isNullOrEmpty(orderPo.getString("INTENT_SO_NO"))){
                                List<Object> IntentSalesOrderList = new ArrayList<Object>();
                                IntentSalesOrderList.add(orderPo.getString("INTENT_SO_NO"));
                                IntentSalesOrderList.add(loginInfo.getDealerCode());
                                IntentSalesOrderList.add(Integer.parseInt(DictCodeConstants.D_KEY));
                                List<TtIntentSalesOrderPO> intentSalesOrderPO = TtIntentSalesOrderPO.findBySQL("select * from TT_INTENT_SALES_ORDER where INTENT_SO_NO= ? AND DEALER_CODE= ? AND D_KEY= ? ", IntentSalesOrderList.toArray());
                                if(intentSalesOrderPO!=null&&intentSalesOrderPO.size()>0){
                                    for(int k=0;k<intentSalesOrderPO.size();k++){
                                        TtIntentSalesOrderPO intentSalesOrder = intentSalesOrderPO.get(k);
                                        intentSalesOrder.setInteger("SO_STATUS", Integer.parseInt(DictCodeConstants.DICT_INTENT_SO_STATUS_FAIL));
                                        intentSalesOrder.saveIt();
                                    }
                                    
                                }
                            }
                        }
                    }else if(salesOrder.getInteger("BUSINESS_TYPE")==Integer.parseInt(DictCodeConstants.DICT_SO_STATUS_HAVE_CANCEL)){
                        if(!StringUtils.isNullOrEmpty(salesOrder.getString("INTENT_SO_NO"))){
                            List<Object> IntentSalesOrderList = new ArrayList<Object>();
                            IntentSalesOrderList.add(salesOrder.getString("INTENT_SO_NO"));
                            IntentSalesOrderList.add(loginInfo.getDealerCode());
                            IntentSalesOrderList.add(Integer.parseInt(DictCodeConstants.D_KEY));
                            List<TtIntentSalesOrderPO> intentSalesOrderPO = TtIntentSalesOrderPO.findBySQL("select * from TT_INTENT_SALES_ORDER where INTENT_SO_NO= ? AND DEALER_CODE= ? AND D_KEY= ? ", IntentSalesOrderList.toArray());
                            if(intentSalesOrderPO!=null&&intentSalesOrderPO.size()>0){
                                for(int k=0;k<intentSalesOrderPO.size();k++){
                                    TtIntentSalesOrderPO intentSalesOrder = intentSalesOrderPO.get(k);
                                    intentSalesOrder.setInteger("SO_STATUS", Integer.parseInt(DictCodeConstants.DICT_INTENT_SO_STATUS_HAVE_BALANCE));
                                    intentSalesOrder.setInteger("IS_SALES_ORDER", DictCodeConstants.STATUS_IS_NOT);
                                    intentSalesOrder.saveIt();
                                }
                                
                            }
                        }
                    }
                }
            }
          
        }
        
    }
    
    //查询订单数
    public int queryPOCustomerOrderCount(String customerNo,String entityCode){
        StringBuffer sql = new StringBuffer("");
        sql.append("  SELECT a.DEALER_CODE,a.SO_STATUS,a.CUSTOMER_NO FROM TT_SALES_ORDER   a ,TM_POTENTIAL_CUSTOMER  b where    ");
        sql.append(" a.CUSTOMER_NO= b.CUSTOMER_NO  and a.DEALER_CODE=b.DEALER_CODE  and a.D_KEY=b.D_KEY   ");
        sql.append(" and  b.INTENT_LEVEL="+DictCodeConstants.DICT_INTENT_LEVEL_D+"  and b.CUSTOMER_NO='"+customerNo+"' "+" and a.DEALER_CODE = '" + entityCode + "'"+ " AND a.D_KEY =" + DictCodeConstants.D_KEY
                );
        List<Object> queryList = new ArrayList<Object>();
        //queryList.add(mobile);
        List<Map> result = DAOUtil.findAll(sql.toString(), queryList);
        return result.size();
    }

}
