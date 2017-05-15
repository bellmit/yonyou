
/** 
 *Copyright 2016 Yonyou Corporation Ltd. All Rights Reserved.
 * This software is published under the terms of the Yonyou Software
 * License version 1.0, a copy of which has been included with this
 * distribution in the LICENSE.txt file.
 *
 * @Project Name : dms.retail
 *
 * @File name : SoAuditingServiceImpl.java
 *
 * @Author : xukl
 *
 * @Date : 2016年9月28日
 *
----------------------------------------------------------------------------------
 *     Date       Who       Version     Comments
 * 1. 2016年9月28日    xukl    1.0
 *
 *
 *
 *
----------------------------------------------------------------------------------
 */

package com.yonyou.dms.retail.service.ordermanage;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.javalite.activejdbc.Base;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yonyou.dms.common.domains.PO.basedata.CustomerPO;
import com.yonyou.dms.common.domains.PO.basedata.PotentialCusPO;
import com.yonyou.dms.common.domains.PO.basedata.QcsArchivesPO;
import com.yonyou.dms.common.domains.PO.basedata.SoAuditingPO;
import com.yonyou.dms.common.domains.PO.basedata.TrackingTaskPO;
import com.yonyou.dms.common.domains.PO.basedata.TtCusIntentPO;
import com.yonyou.dms.common.domains.PO.basedata.TtCustomerGatheringPO;
import com.yonyou.dms.common.domains.PO.basedata.TtIntentReceiveMoneyPO;
import com.yonyou.dms.common.domains.PO.basedata.TtIntentSalesOrderPO;
import com.yonyou.dms.common.domains.PO.basedata.TtOrderStatusUpdatePO;
import com.yonyou.dms.common.domains.PO.basedata.TtSalesPromotionPlanPO;
import com.yonyou.dms.common.domains.PO.basedata.TtVsDeliveryItemPO;
import com.yonyou.dms.common.domains.PO.basedata.TtVsStockDeliveryPO;
import com.yonyou.dms.common.domains.PO.basedata.VsStockPO;
import com.yonyou.dms.common.domains.PO.stockmanage.VehiclePO;
import com.yonyou.dms.commonSales.domains.PO.stockmanage.SalesOrderPO;
import com.yonyou.dms.framework.DAO.DAOUtil;
import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.framework.domain.LoginInfoDto;
import com.yonyou.dms.framework.service.CommonNoService;
import com.yonyou.dms.framework.util.bean.ApplicationContextHelper;
import com.yonyou.dms.function.common.CommonConstants;
import com.yonyou.dms.function.common.DictCodeConstants;
import com.yonyou.dms.function.exception.ServiceBizException;
import com.yonyou.dms.function.utils.common.StringUtils;
import com.yonyou.dms.retail.domains.DTO.ordermanage.SoAuditingDTO;

/**
 * 经理审核实现
 * 
 * @author xukl
 * @date 2016年9月28日
 */
@SuppressWarnings("rawtypes")
@Service
public class ManageVerifyServiceImpl implements ManageVerifyService {


    @Autowired
    private CommonNoService    commonNoService;

    /**
     * @author LiGaoqi
     * @date 2017年1月17日
     * @param queryParam
     * @return
     * @throws ServiceBizException (non-Javadoc)
     * @see com.yonyou.dms.retail.service.ordermanage.ManageVerifyService#querySalesOrdersCancel(java.util.Map)
     */

    @Override
    public PageInfoDto querySalesOrdersAudit(Map<String, String> queryParam) throws ServiceBizException {
        LoginInfoDto loginInfo = ApplicationContextHelper.getBeanByType(LoginInfoDto.class);
        StringBuffer sql = new StringBuffer("");
        List<Object> params = new ArrayList<Object>();
        sql.append(" SELECT * FROM (SELECT A.VER,A.BUSINESS_TYPE, A.SO_NO,A.EC_ORDER_NO,A.DELIVERY_MODE_ELEC,A.ESC_ORDER_STATUS,A.ESC_TYPE,A.SHEET_CREATE_DATE,A.CERTIFICATE_NO,A.SO_STATUS,A.AUDITED_BY,A.ORDER_SORT,A.CONSIGNEE_CODE,");
        sql.append("CASE WHEN A.BUSINESS_TYPE=" + DictCodeConstants.DICT_SO_TYPE_ALLOCATION+ " THEN D.CUSTOMER_NO ELSE A.CUSTOMER_NO END AS CUSTOMER_NO," + "CASE WHEN A.BUSINESS_TYPE="+ DictCodeConstants.DICT_SO_TYPE_ALLOCATION+ " THEN D.CUSTOMER_NAME ELSE A.CUSTOMER_NAME END AS CUSTOMER_NAME," + "CASE WHEN A.BUSINESS_TYPE="+ DictCodeConstants.DICT_SO_TYPE_ALLOCATION+ " THEN D.CONTRACT_NO ELSE A.CONTRACT_NO END AS CONTRACT_NO," + "CASE WHEN A.BUSINESS_TYPE="+ DictCodeConstants.DICT_SO_TYPE_ALLOCATION+ " THEN D.CONTRACT_DATE ELSE A.CONTRACT_DATE END AS CONTRACT_DATE, ");
        sql.append("A.VIN,co.COLOR_NAME,B.MODEL_CODE,MO.MODEL_NAME,B.CONFIG_CODE,PA.CONFIG_NAME,B.SERIES_CODE,SE.SERIES_NAME,COALESCE(A.COLOR_CODE,B.COLOR_CODE) as COLOR_CODE,BR.BRAND_NAME,A.CUSTOMER_TYPE,A.CONTACTOR_NAME,");
        sql.append("A.PHONE,A.MOBILE,A.CT_CODE,A.SOLD_BY,EM.USER_NAME,A.RE_AUDITED_BY,A.DEALER_CODE,TD.DEFAULT_VALUE AS IS_ACCOUNTANT_AUDITING, ");
        sql.append(" B.PRODUCT_CODE,B.PRODUCT_NAME,A.VEHICLE_PRICE,A.DIRECTIVE_PRICE,B.BRAND_CODE,");
        sql.append(" A.STORAGE_CODE,A.STORAGE_POSITION_CODE,A.DISPATCHED_DATE,A.DISPATCHED_BY,A.REMARK,A.DEAL_STATUS, ");
        sql.append(" A.OTHER_PAY_OFF, A.OTHER_AMOUNT_OBJECT, A.OTHER_AMOUNT,A.LOCK_USER,a.IS_SPEEDINESS,");
        sql.append("(SELECT ROUND(SUM(VS.LIMIT_PRICE * SP.PART_QUANTITY),2) FROM TT_SO_PART SP LEFT JOIN TM_PART_INFO VS ON ");
        sql.append("SP.DEALER_CODE = VS.DEALER_CODE AND SP.PART_NO = VS.PART_NO WHERE  SP.DEALER_CODE = A.DEALER_CODE AND SP.SO_NO = A.SO_NO ");
        sql.append(" AND (SP.ACCOUNT_MODE = 13121002 OR SP.ACCOUNT_MODE = 13121003)) LIMIT_PRICE,APPLY_NO ");
        sql.append(" FROM TT_SALES_ORDER A " + " LEFT JOIN (" + CommonConstants.VM_VS_PRODUCT+ ") B ON A.PRODUCT_CODE = B.PRODUCT_CODE AND A.DEALER_CODE = B.DEALER_CODE " + " AND B.D_KEY = "+ DictCodeConstants.D_KEY + " ");
        sql.append(" LEFT JOIN TM_BRAND BR ON BR.BRAND_CODE=B.BRAND_CODE AND BR.DEALER_CODE=A.DEALER_CODE");
        sql.append(" LEFT JOIN TM_SERIES SE ON SE.SERIES_CODE=B.SERIES_CODE AND BR.BRAND_CODE=SE.BRAND_CODE AND SE.DEALER_CODE=A.DEALER_CODE");
        sql.append(" LEFT JOIN TM_MODEL MO ON MO.MODEL_CODE=B.MODEL_CODE AND SE.SERIES_CODE=MO.SERIES_CODE AND MO.BRAND_CODE=SE.BRAND_CODE AND MO.DEALER_CODE=A.DEALER_CODE");
        sql.append(" LEFT JOIN TM_CONFIGURATION PA ON PA.CONFIG_CODE=B.CONFIG_CODE AND MO.MODEL_CODE=PA.MODEL_CODE AND PA.SERIES_CODE=MO.SERIES_CODE AND MO.BRAND_CODE=PA.BRAND_CODE AND PA.DEALER_CODE=A.DEALER_CODE");
        sql.append(" left  join   tm_color   co   on   B.COLOR_CODE = co.COLOR_CODE and A.DEALER_CODE=co.DEALER_CODE\n");
        sql.append(" left join TM_USER em  on A.SOLD_BY=em.USER_ID AND A.DEALER_CODE=em.DEALER_CODE\n");
        sql.append(" LEFT JOIN (SELECT DEALER_CODE,CUSTOMER_CODE AS CUSTOMER_NO,CUSTOMER_NAME,CONTRACT_NO,AGREEMENT_BEGIN_DATE AS CONTRACT_DATE ");
        sql.append(" FROM (" + CommonConstants.VM_PART_CUSTOMER+ ") PC) D ON A.DEALER_CODE=D.DEALER_CODE AND A.CONSIGNEE_CODE=D.CUSTOMER_NO LEFT JOIN TM_DEFAULT_PARA TD ON A.DEALER_CODE=TD.DEALER_CODE AND TD.ITEM_CODE=8032 WHERE 1=1 ");
        sql.append(" AND A.DEALER_CODE = '" + loginInfo.getDealerCode() + "'" + " AND A.D_KEY = "+ DictCodeConstants.D_KEY + " AND A.SO_STATUS = " + DictCodeConstants.DICT_SO_STATUS_MANAGER_AUDITING+ " AND A.AUDITED_BY = " + loginInfo.getUserId());
        if (!StringUtils.isNullOrEmpty(queryParam.get("businessType"))) {
            sql.append(" and A.BUSINESS_TYPE= ?");
            params.add(queryParam.get("businessType"));
        }
        if (!StringUtils.isNullOrEmpty(queryParam.get("soNo"))) {
            sql.append(" and A.SO_NO like ?");
            params.add("%" + queryParam.get("soNo") + "%");
        }
        if (!StringUtils.isNullOrEmpty(queryParam.get("ecOrderNo"))) {
            sql.append(" and A.EC_ORDER_NO like ?");
            params.add("%" + queryParam.get("ecOrderNo") + "%");
        }
        if (!StringUtils.isNullOrEmpty(queryParam.get("vin"))) {
            sql.append(" and A.VIN like ?");
            params.add("%" + queryParam.get("vin") + "%");
        }
        sql.append(") E WHERE 1=1 ");
        if (!StringUtils.isNullOrEmpty(queryParam.get("customerName"))) {
            sql.append(" and E.CUSTOMER_NAME like ?");
            params.add("%" + queryParam.get("customerName") + "%");
        }
        PageInfoDto result = DAOUtil.pageQuery(sql.toString(), params);
        System.out.println(sql.toString());
        return result;
    }
    /**
     * @author LiGaoqi
     * @date 2017年1月17日
     * @param potentialCusDto
     * @throws ServiceBizException (non-Javadoc)
     * @see com.yonyou.dms.retail.service.ordermanage.ManageVerifyService#auditSalesOrder(com.yonyou.dms.retail.domains.DTO.ordermanage.SoAuditingDTO)
     */
    @Override
    public PageInfoDto queryAudtiDetail(String soNo) throws ServiceBizException {
        LoginInfoDto loginInfo = ApplicationContextHelper.getBeanByType(LoginInfoDto.class);
        List<Object> queryList = new ArrayList<Object>();
        StringBuffer sql = new StringBuffer("");
        sql.append(" SELECT B.*,C.USER_NAME AS SUBMIT_NAME ,(case when (B.AUDITED_BY=11111111111111) then B.Auditing_Postil else A.user_name end)USER_NAME FROM TT_SO_AUDITING B  left join   TM_USER A on   B.AUDITED_BY = A.USER_ID and  a.DEALER_CODE=b.DEALER_CODE left join   TM_USER C on   B.SUBMIT_AUDITED_BY = C.USER_ID and  c.DEALER_CODE=b.DEALER_CODE   ");
        sql.append(" WHERE  b.D_KEY = 0"+ " AND B.DEALER_CODE = '" + loginInfo.getDealerCode()+ "' AND B.SO_NO = '" + soNo +"'");
        PageInfoDto result = DAOUtil.pageQuery(sql.toString(), queryList);
        return result;
    }

    
    /**
     * @author LiGaoqi
     * @date 2017年1月17日
     * @param potentialCusDto
     * @throws ServiceBizException (non-Javadoc)
     * @see com.yonyou.dms.retail.service.ordermanage.ManageVerifyService#auditSalesOrder(com.yonyou.dms.retail.domains.DTO.ordermanage.SoAuditingDTO)
     */
    @Override
    public List<Map> manageAudit(String id,String by) throws ServiceBizException {
        String pp1 = "";
        String pp2 = "";
        List<Map>  listresault =null;
        LoginInfoDto loginInfo = ApplicationContextHelper.getBeanByType(LoginInfoDto.class);
        if (id.equals(DictCodeConstants.DICT_SO_TYPE_GENERAL)) {
            if (!StringUtils.isNullOrEmpty(commonNoService.getDefalutPara("8001"))
                && !StringUtils.isNullOrEmpty(commonNoService.getDefalutPara("8032"))) {
                pp1 = commonNoService.getDefalutPara("8001");
                pp2 = commonNoService.getDefalutPara("8032");
            }
        } else if (id.equals(DictCodeConstants.DICT_SO_TYPE_SERVICE)) {
            if (!StringUtils.isNullOrEmpty(commonNoService.getDefalutPara("8033"))
                && !StringUtils.isNullOrEmpty(commonNoService.getDefalutPara("8034"))) {
                pp1 = commonNoService.getDefalutPara("8033");
                pp2 = commonNoService.getDefalutPara("8034");
            }

        } else if (id.equals(DictCodeConstants.DICT_SO_TYPE_ALLOCATION)) {
            if (!StringUtils.isNullOrEmpty(commonNoService.getDefalutPara("8001"))
                && !StringUtils.isNullOrEmpty(commonNoService.getDefalutPara("8032"))) {
                pp1 = commonNoService.getDefalutPara("8001");
                pp2 = commonNoService.getDefalutPara("8032");
            }

        }
        if (!StringUtils.isNullOrEmpty(commonNoService.getDefalutPara("1801"))
            && commonNoService.getDefalutPara("1801").equals("12781001")
            && (id == null
                ||id.equals(DictCodeConstants.DICT_SO_TYPE_GENERAL)
                || id.equals(DictCodeConstants.DICT_SO_TYPE_ALLOCATION))) {
            if (pp1 != null) {
                if (pp1.equals(DictCodeConstants.DICT_IS_YES)) {
                    pp2 = DictCodeConstants.DICT_IS_NO;
                } else {
                    pp1 = DictCodeConstants.DICT_IS_YES;
                    pp2 = DictCodeConstants.DICT_IS_YES;
                }

            } else {
                pp2 = DictCodeConstants.DICT_IS_YES;
            }
            System.out.println("pp1"+pp1);
            System.out.println("pp2"+pp2);
            listresault = this.queryAuditingUser(DictCodeConstants.DICT_AUDITING_TYPE_FINANCE, pp1, pp2, loginInfo.getDealerCode(), Long.parseLong(by));
        return listresault;
        } else {
            System.out.println("pp1"+pp1);
            System.out.println("pp2"+pp2);
            listresault=this.queryAuditingUser(DictCodeConstants.DICT_AUDITING_TYPE_FINANCE, pp1, pp2, loginInfo.getDealerCode(), Long.parseLong(by));
        return listresault;    
        }
    }
    /**
     * @author LiGaoqi
     * @date 2017年1月17日
     * @throws ServiceBizException (non-Javadoc)
     * @see com.yonyou.dms.retail.service.ordermanage.ManageVerifyService#auditSalesOrder(com.yonyou.dms.retail.domains.DTO.ordermanage.SoAuditingDTO)
     */
    @Override
    public List<Map> commidAudit(String id,String by) throws ServiceBizException {

        String pp1 = "";
        String pp2 = "";
        List<Map>  listresault =null;
        LoginInfoDto loginInfo = ApplicationContextHelper.getBeanByType(LoginInfoDto.class);
        if (id.equals(DictCodeConstants.DICT_SO_TYPE_GENERAL)) {
            if (!StringUtils.isNullOrEmpty(commonNoService.getDefalutPara("8001"))
                && !StringUtils.isNullOrEmpty(commonNoService.getDefalutPara("8032"))) {
                pp1 = commonNoService.getDefalutPara("8001");
                pp2 = commonNoService.getDefalutPara("8032");
            }
        } else if (id.equals(DictCodeConstants.DICT_SO_TYPE_SERVICE)) {
            if (!StringUtils.isNullOrEmpty(commonNoService.getDefalutPara("8033"))
                && !StringUtils.isNullOrEmpty(commonNoService.getDefalutPara("8034"))) {
                pp1 = commonNoService.getDefalutPara("8033");
                pp2 = commonNoService.getDefalutPara("8034");
            }

        } else if (id.equals(DictCodeConstants.DICT_SO_TYPE_ALLOCATION)) {
            if (!StringUtils.isNullOrEmpty(commonNoService.getDefalutPara("8001"))
                && !StringUtils.isNullOrEmpty(commonNoService.getDefalutPara("8032"))) {
                pp1 = commonNoService.getDefalutPara("8001");
                pp2 = commonNoService.getDefalutPara("8032");
            }

        }
        if (!StringUtils.isNullOrEmpty(commonNoService.getDefalutPara("1801"))
            && commonNoService.getDefalutPara("1801").equals("12781001")
            && (id == null
                ||id.equals(DictCodeConstants.DICT_SO_TYPE_GENERAL)
                || id.equals(DictCodeConstants.DICT_SO_TYPE_ALLOCATION))) {
            if (pp1 != null) {
                if (pp1.equals(DictCodeConstants.DICT_IS_YES)) {
                    pp2 = DictCodeConstants.DICT_IS_NO;
                } else {
                    pp1 = DictCodeConstants.DICT_IS_YES;
                    pp2 = DictCodeConstants.DICT_IS_YES;
                }

            } else {
                pp2 = DictCodeConstants.DICT_IS_YES;
            }
            System.out.println("pp1"+pp1);
            System.out.println("pp2"+pp2);
            listresault = this.queryAuditingUser(null, pp1, pp2, loginInfo.getDealerCode(), Long.parseLong(by));
        return listresault;
        } else {
            System.out.println("pp1"+1);
            System.out.println("pp2"+2);
            listresault=this.queryAuditingUser(null, pp1, pp2, loginInfo.getDealerCode(), Long.parseLong(by));
        return listresault;    
        }
    
    }
    
    @Override
    public void saveCommitAudit(SoAuditingDTO auditDto) throws ServiceBizException {
        //更改审核人
        LoginInfoDto loginInfo = ApplicationContextHelper.getBeanByType(LoginInfoDto.class);
        SalesOrderPO salesOrderPo = SalesOrderPO.findByCompositeKeys(loginInfo.getDealerCode(),
                                                                     auditDto.getAuditList());
        if(!StringUtils.isNullOrEmpty(salesOrderPo)){
            System.out.println("更改审核人");
            System.out.println(auditDto.getAuditByList());
            salesOrderPo.setString("AUDITED_BY", auditDto.getAuditByList());
            salesOrderPo.setInteger("IS_UPLOAD", Integer.parseInt(DictCodeConstants.DICT_IS_NO));
            salesOrderPo.saveIt();
            SoAuditingPO anPo = new SoAuditingPO();
            anPo.setString("SO_NO", auditDto.getAuditList());
            anPo.setLong("AUDITED_BY", Long.parseLong(auditDto.getAuditByList()));
            //anPo.setDate("AUDITING_DATE", new Date());
            anPo.setLong("SUBMIT_AUDITED_BY", loginInfo.getUserId());
            anPo.setDate("COMMIT_TIME", new Date());
            anPo.setInteger("AUDITED_BY_IDENTITY", Integer.parseInt(DictCodeConstants.DICT_AUDITING_TYPE_MANAGE));
            if(auditDto.getTreatmentAdvice()!=null){
                anPo.setString("TREATMENT_ADVICE", auditDto.getTreatmentAdvice());
            }
            anPo.saveIt();
        }
    }
    /**
     * @author LiGaoqi
     * @date 2017年1月20日
     * @param SoAuditingDTO
     * @throws ServiceBizException (non-Javadoc)
     * @see com.yonyou.dms.retail.service.ordermanage.ManageVerifyService#auditSalesOrder(com.yonyou.dms.retail.domains.DTO.ordermanage.SoAuditingDTO)
     */

    @Override
    public void auditSalesOrder(SoAuditingDTO auditDto) throws ServiceBizException {
        String auditingType = DictCodeConstants.DICT_AUDITING_TYPE_MANAGE;
        LoginInfoDto loginInfo = ApplicationContextHelper.getBeanByType(LoginInfoDto.class);
        List<Map>  listresault =null;
        if (!StringUtils.isNullOrEmpty(auditDto)) {
            if (!StringUtils.isNullOrEmpty(auditDto.getAuditingResult())
                && auditDto.getAuditingResult() == Integer.parseInt(DictCodeConstants.DICT_AUDITING_RESULT_ACCEPT)
                && !StringUtils.isNullOrEmpty(auditDto.getBusinessTypeList())
                && !auditDto.getBusinessTypeList().equals(DictCodeConstants.DICT_SO_TYPE_RERURN)) {
                if (((!StringUtils.isNullOrEmpty(commonNoService.getDefalutPara("8032"))
                      && commonNoService.getDefalutPara("8032").equals("12781001")
                      && auditDto.getBusinessTypeList().equals(DictCodeConstants.DICT_SO_TYPE_GENERAL))
                     || (commonNoService.getDefalutPara("8034").equals("12781001")
                         && auditDto.getBusinessTypeList().equals(DictCodeConstants.DICT_SO_TYPE_SERVICE))
                     || (!auditDto.getBusinessTypeList().equals(DictCodeConstants.DICT_SO_TYPE_SERVICE)
                         && !auditDto.getBusinessTypeList().equals(DictCodeConstants.DICT_SO_TYPE_GENERAL)))
                    && (!auditDto.getIsSpeedinessList().equals(DictCodeConstants.DICT_IS_YES))) {
                    String pp1 = "";
                    String pp2 = "";
                   
                    if (auditDto.getBusinessTypeList().equals(DictCodeConstants.DICT_SO_TYPE_GENERAL)) {
                        if (!StringUtils.isNullOrEmpty(commonNoService.getDefalutPara("8001"))
                            && !StringUtils.isNullOrEmpty(commonNoService.getDefalutPara("8032"))) {
                            pp1 = commonNoService.getDefalutPara("8001");
                            pp2 = commonNoService.getDefalutPara("8032");
                        }
                    } else if (auditDto.getBusinessTypeList().equals(DictCodeConstants.DICT_SO_TYPE_SERVICE)) {
                        if (!StringUtils.isNullOrEmpty(commonNoService.getDefalutPara("8033"))
                            && !StringUtils.isNullOrEmpty(commonNoService.getDefalutPara("8034"))) {
                            pp1 = commonNoService.getDefalutPara("8033");
                            pp2 = commonNoService.getDefalutPara("8034");
                        }

                    } else if (auditDto.getBusinessTypeList().equals(DictCodeConstants.DICT_SO_TYPE_ALLOCATION)) {
                        if (!StringUtils.isNullOrEmpty(commonNoService.getDefalutPara("8001"))
                            && !StringUtils.isNullOrEmpty(commonNoService.getDefalutPara("8032"))) {
                            pp1 = commonNoService.getDefalutPara("8001");
                            pp2 = commonNoService.getDefalutPara("8032");
                        }

                    }
                    if (!StringUtils.isNullOrEmpty(commonNoService.getDefalutPara("1801"))
                        && commonNoService.getDefalutPara("1801").equals("12781001")
                        && (auditDto.getBusinessTypeList() == null
                            || auditDto.getBusinessTypeList().equals(DictCodeConstants.DICT_SO_TYPE_GENERAL)
                            || auditDto.getBusinessTypeList().equals(DictCodeConstants.DICT_SO_TYPE_ALLOCATION))) {
                        if (pp1 != null) {
                            if (pp1.equals(DictCodeConstants.DICT_IS_YES)) {
                                pp2 = DictCodeConstants.DICT_IS_NO;
                            } else {
                                pp1 = DictCodeConstants.DICT_IS_YES;
                                pp2 = DictCodeConstants.DICT_IS_YES;
                            }

                        } else {
                            pp2 = DictCodeConstants.DICT_IS_YES;
                        }
                        listresault = this.queryAuditingUser(DictCodeConstants.DICT_AUDITING_TYPE_FINANCE, pp1, pp2, loginInfo.getDealerCode(), loginInfo.getUserId());
                    } else {
                        listresault=this.queryAuditingUser(DictCodeConstants.DICT_AUDITING_TYPE_FINANCE, pp1, pp2, loginInfo.getDealerCode(), loginInfo.getUserId());
                    }
                } else {
                    // ACTION:AUTO_AUDITING,审核参数缺省时自动审核
                    if (!StringUtils.isNullOrEmpty(auditDto.getBusinessTypeList())) {
                        SalesOrderPO salesOrderPo = SalesOrderPO.findByCompositeKeys(loginInfo.getDealerCode(),
                                                                                     auditDto.getAuditList());

                        if (!StringUtils.isNullOrEmpty(commonNoService.getDefalutPara("8003"))
                            && commonNoService.getDefalutPara("8003").equals("12781002")) {
                            salesOrderPo.setInteger("SO_STATUS",
                                                    Integer.parseInt(DictCodeConstants.DICT_SO_STATUS_DELIVERY_CONFIRMING));
                            salesOrderPo.setInteger("IS_UPLOAD", Integer.parseInt(DictCodeConstants.DICT_IS_NO));
                        } else if (!StringUtils.isNullOrEmpty(commonNoService.getDefalutPara("1233"))
                                   && commonNoService.getDefalutPara("1233").equals("12781001")) {// 长久
                            salesOrderPo.setInteger("SO_STATUS",
                                                    Integer.parseInt(DictCodeConstants.DICT_SO_STATUS_DELIVERY_CONFIRMING));
                            salesOrderPo.setInteger("IS_UPLOAD", Integer.parseInt(DictCodeConstants.DICT_IS_NO));
                        }
                        salesOrderPo.saveIt();
                        SalesOrderPO salesOrder = SalesOrderPO.findByCompositeKeys(loginInfo.getDealerCode(),
                                                                                     auditDto.getAuditList());
                        if(!StringUtils.isNullOrEmpty(salesOrder)){
                           if(!StringUtils.isNullOrEmpty(salesOrder.getString("INTENT_SO_NO"))&&(
                                   !commonNoService.getDefalutPara("8036").equals("12781001")&&salesOrder.getInteger("SO_STATUS")>Integer.parseInt(DictCodeConstants.DICT_SO_STATUS_NOT_AUDIT)||
                                   commonNoService.getDefalutPara("8036").equals("12781001")&&salesOrder.getInteger("SO_STATUS")>Integer.parseInt(DictCodeConstants.DICT_SO_STATUS_ACCOUNTANT_AUDITING))) {
                               List<Object> intentReceiveMoneyList = new ArrayList<Object>();
                               intentReceiveMoneyList.add(loginInfo.getDealerCode());
                               intentReceiveMoneyList.add(salesOrder.getString("INTENT_SO_NO"));
                               List<TtIntentReceiveMoneyPO> receivePo = TtIntentReceiveMoneyPO.findBySQL("select * from TT_INTENT_RECEIVE_MONEY where DEALER_CODE= ? AND INTENT_SO_NO= ? ",intentReceiveMoneyList.toArray());
                              if(receivePo!=null&&receivePo.size()>0){
                                  TtCustomerGatheringPO gatheringPO = new TtCustomerGatheringPO();
                                  for(int j=0;j<receivePo.size();j++){
                                      TtIntentReceiveMoneyPO receive = receivePo.get(j);
                                      if(!StringUtils.isNullOrEmpty(receive.getInteger("IS_ADVANCE_PAYMENTS"))&&receive.getInteger("IS_ADVANCE_PAYMENTS")!=12781001){
                                          String No = commonNoService.getSystemOrderNo(CommonConstants.SRV_SKDH);
                                          gatheringPO.setString("BILL_NO",receive.getString("BILL_NO"));
                                          gatheringPO.setString("CUSTOMER_NO",salesOrder.getString("CUSTOMER_NO"));
                                          gatheringPO.setString("SO_NO",receive.getString("SO_NO"));
                                          gatheringPO.setInteger("GATHERING_TYPE",receive.getInteger("GATHERING_TYPE"));
                                          gatheringPO.setString("OWNED_BY",String.valueOf(loginInfo.getUserId()));
                                          gatheringPO.setString("PAY_TYPE_CODE",receive.getString("0004"));
                                          gatheringPO.setDouble("RECEIVE_AMOUNT",receive.getDouble("RECEIVE_AMOUNT"));
                                          gatheringPO.setDate("RECEIVE_DATE",receive.getDate("RECEIVE_DATE"));
                                          gatheringPO.setString("RECEIVE_NO",No);
                                          gatheringPO.setDate("RECORD_DATE",new Date());
                                          gatheringPO.setString("RECORDER",receive.getString("RECORDER"));
                                          gatheringPO.setString("TRANSACTOR",receive.getString("TRANSACTOR"));
                                          gatheringPO.setString("WRITEOFF_BY",receive.getString("WRITEOFF_BY"));
                                          gatheringPO.setDate("WRITEOFF_DATE",new Date());
                                          gatheringPO.setInteger("WRITEOFF_TAG",DictCodeConstants.STATUS_IS_YES);
                                          gatheringPO.setString("REMARK",receive.getString("REMARK"));
                                          gatheringPO.saveIt();
                                          //更新客户资料表中收款总金额和帐户可用余额
                                          if(!StringUtils.isNullOrEmpty(salesOrder.getString("CUSTOMER_NO"))){
                                              PotentialCusPO cusPo = PotentialCusPO.findByCompositeKeys(loginInfo.getDealerCode(),salesOrder.getString("CUSTOMER_NO"));
                                              if(cusPo!=null){
                                                  Double receiveAmount = receive.getDouble("RECEIVE_AMOUNT");
                                                  Double gatheredSum = 0D;
                                                  Double usableAmount = 0D;
                                                  if(cusPo.getDouble("GATHERED_SUM")!=null){
                                                      gatheredSum = cusPo.getDouble("GATHERED_SUM")+receiveAmount;
                                                  }else{
                                                      gatheredSum = receiveAmount;
                                                  }
                                                  cusPo.setDouble("GATHERED_SUM", gatheredSum);
                                                  if(cusPo.getDouble("USABLE_AMOUNT")!=null){
                                                      usableAmount = cusPo.getDouble("USABLE_AMOUNT")+receiveAmount;
                                                  }else{
                                                      usableAmount = receiveAmount;
                                                  }
                                                  cusPo.setDouble("USABLE_AMOUNT", usableAmount);
                                                  cusPo.saveIt();
                                              }
                                          }
                                          receive.setInteger("IS_ADVANCE_PAYMENTS", 12781001);
                                          receive.saveIt();
                                      }   

                                  }
                                  List<Object> intentReceiveList = new ArrayList<Object>();
                                  intentReceiveList.add(loginInfo.getDealerCode());
                                  intentReceiveList.add(salesOrder.getString("INTENT_SO_NO"));
                                  List<TtIntentReceiveMoneyPO> intentReceivePo = TtIntentReceiveMoneyPO.findBySQL("select * from TT_INTENT_RECEIVE_MONEY where DEALER_CODE= ? AND INTENT_SO_NO= ? AND (IS_ADVANCE_PAYMENTS != 12781001 OR IS_ADVANCE_PAYMENTS IS NULL) ",intentReceiveList.toArray());
                                  if(!StringUtils.isNullOrEmpty(intentReceivePo)&&intentReceivePo.size()>0){
                                      TtIntentSalesOrderPO intentOrderPO = TtIntentSalesOrderPO.findByCompositeKeys(loginInfo.getDealerCode(),salesOrder.getString("INTENT_SO_NO"));
                                      if(intentOrderPO!=null){
                                          intentOrderPO.setInteger("SO_STATUS", Integer.parseInt(DictCodeConstants.DICT_INTENT_SO_STATUS_HAVE_CLOSED));
                                          intentOrderPO.saveIt();
                                      }
                                  }
                              }
                               
                           }
                           System.out.println("更改记录");
                           if(setUpdateAuditing(loginInfo.getDealerCode(), auditDto, loginInfo.getUserId())==0){
                               setSoAuditingPO(auditDto, loginInfo.getDealerCode(), loginInfo.getUserId()).saveIt();
                           }
                        }
                        
                    }
                }

            }
            if((!StringUtils.isNullOrEmpty(listresault)&&listresault.size()>0)||auditDto.getAuditingResult()==Integer.parseInt(DictCodeConstants.DICT_AUDITING_RESULT_REJECT)||auditDto.getBusinessTypeList().equals(DictCodeConstants.DICT_SO_TYPE_RERURN)){
                SalesOrderPO pagePO = SalesOrderPO.findByCompositeKeys(loginInfo.getDealerCode(),auditDto.getAuditList());
                boolean rejectstatus = false;//false  插入  true 不插入
                if(pagePO!=null&&!StringUtils.isNullOrEmpty(pagePO.getInteger("DEAL_STATUS"))&&pagePO.getInteger("DEAL_STATUS")==Integer.parseInt(DictCodeConstants.DICT_COMPLAINT_DEAL_STATUS_DEALED)){
                    throw new  ServiceBizException("该订单在处理状态中，暂不能处理！"); 
                }
                if(auditingType.equals(DictCodeConstants.DICT_AUDITING_TYPE_MANAGE)){
                  //如果是驳回，将订单状态改为驳回，如果是同意，则将订单状态改为财务审核中
                    if(!StringUtils.isNullOrEmpty(auditDto.getAuditingResult())&&auditDto.getAuditingResult()==Integer.parseInt(DictCodeConstants.DICT_AUDITING_RESULT_REJECT)){
                        pagePO.setInteger("SO_STATUS", Integer.parseInt(DictCodeConstants.DICT_SO_STATUS_MANAGER_REJECT));
                        pagePO.setInteger("IS_UPLOAD", Integer.parseInt(DictCodeConstants.DICT_IS_NO));
                        pagePO.setString("AUDITED_BY", "0");
                        rejectstatus=true;
                        setUpdateAuditing(loginInfo.getDealerCode(), auditDto, loginInfo.getUserId());
                    }else{
                        if(pagePO.getInteger("BUSINESS_TYPE")!=null&&pagePO.getInteger("BUSINESS_TYPE")==Integer.parseInt(DictCodeConstants.DICT_SO_TYPE_RERURN)){
                          //如果是销售退回，经理审核通过，将订单状态改为等待退回入库
                            System.out.println("___________________________________11111111111111112");
                            pagePO.setInteger("SO_STATUS", Integer.parseInt(DictCodeConstants.DICT_SO_STATUS_WAIT_UNTREAT_IN_STOCK));
                          //做销售退回的时候潜在客户级别如果是D级就改成N级  --add bcy
                            SalesOrderPO cusOrderPO = SalesOrderPO.findByCompositeKeys(loginInfo.getDealerCode(),auditDto.getAuditList());
                            if(cusOrderPO!=null&&!StringUtils.isNullOrEmpty(cusOrderPO.getString("CUSTOMER_NO"))){
                                PotentialCusPO cusPO = PotentialCusPO.findByCompositeKeys(loginInfo.getDealerCode(),cusOrderPO.getString("CUSTOMER_NO"));
                                if(!StringUtils.isNullOrEmpty(cusPO)){
                                  //插入意向
                                  TtCusIntentPO cc = new TtCusIntentPO();
                                  Long intentId = commonNoService.getId("ID");
                                  cc.setString("CUSTOMER_NO", cusOrderPO.getString("CUSTOMER_NO"));
                                  cc.setLong("INTENT_ID", intentId);
                                  cc.setInteger("INTENT_FINISHED", Integer.parseInt(DictCodeConstants.DICT_IS_NO));
                                  cc.setInteger("IS_UPLOAD", Integer.parseInt(DictCodeConstants.DICT_IS_NO));
                                  cc.saveIt();
                                  System.out.println("___________________________________1111111111111113");
                                //修改客户
                                  if(!StringUtils.isNullOrEmpty(cusPO.getInteger("INTENT_LEVEL"))&&cusPO.getInteger("INTENT_LEVEL")!=Integer.parseInt(DictCodeConstants.DICT_INTENT_LEVEL_N)){
                                      System.out.println("___________________________________1111111111111111");
                                      cusPO.setLong("INTENT_ID", intentId);
                                      cusPO.setInteger("INTENT_LEVEL", Integer.parseInt(DictCodeConstants.DICT_INTENT_LEVEL_N));
                                      cusPO.setDate("DDCN_UPDATE_DATE", new Date());
                                      cusPO.saveIt();
                                  }
                                }
                            }
                            if(setUpdateAuditing(loginInfo.getDealerCode(), auditDto, loginInfo.getUserId())==0){
                                setSoAuditingPO(auditDto, loginInfo.getDealerCode(), loginInfo.getUserId()).saveIt();
                            }
                        }else{
                          //其他类型订单,经理审核通过，但是不选择财务审核人，此时不做任何操作。
                                pagePO.setString("RE_AUDITED_BY", auditDto.getAuditByList());
                                pagePO.setInteger("SO_STATUS", Integer.parseInt(DictCodeConstants.DICT_SO_STATUS_ACCOUNTANT_AUDITING));
                                pagePO.setInteger("IS_UPLOAD", Integer.parseInt(DictCodeConstants.DICT_IS_NO));
                              //新增订单审核历史
                                if (!StringUtils.isNullOrEmpty(pagePO.getInteger("BUSINESS_TYPE"))
                                    && (pagePO.getInteger("BUSINESS_TYPE") == Integer.parseInt(DictCodeConstants.DICT_SO_TYPE_GENERAL)
                                        || pagePO.getInteger("BUSINESS_TYPE") == Integer.parseInt(DictCodeConstants.DICT_SO_TYPE_ENTRUST_DELIVERY))) {
                                    TtOrderStatusUpdatePO orderStatusUpdatePO = new TtOrderStatusUpdatePO();
                                    orderStatusUpdatePO.setString("SO_NO", pagePO.getString("SO_NO"));
                                    orderStatusUpdatePO.setInteger("SO_STATUS",
                                                                   Integer.parseInt(DictCodeConstants.DICT_SO_STATUS_ACCOUNTANT_AUDITING));
                                    orderStatusUpdatePO.setString("OWNED_BY", String.valueOf(loginInfo.getUserId()));
                                    orderStatusUpdatePO.setInteger("IS_UPLOAD", Integer.parseInt(DictCodeConstants.DICT_IS_NO));
                                    orderStatusUpdatePO.setDate("ALTERATION_TIME", new Date());
                                    orderStatusUpdatePO.setInteger("D_KEY", Integer.parseInt(DictCodeConstants.D_KEY));
                                    orderStatusUpdatePO.saveIt();
                                }
                                //更新记录
                                if(setUpdateAuditing(loginInfo.getDealerCode(), auditDto, loginInfo.getUserId())==0){
                                     setSoAuditingPO(auditDto, loginInfo.getDealerCode(), loginInfo.getUserId()).saveIt();
                                 }else{
                                   //增加一条提交审核人记录
                                     if(rejectstatus == false){//驳回时不插入
                                         SoAuditingPO anPo = new SoAuditingPO();
                                         anPo.setString("SO_NO", auditDto.getAuditList());
                                         anPo.setLong("AUDITED_BY", Long.parseLong(listresault.get(0).get("USER_ID").toString()));
                                         //anPo.setDate("AUDITING_DATE", new Date());
                                         anPo.setLong("SUBMIT_AUDITED_BY", loginInfo.getUserId());
                                         SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                                         String d = format.format(new Date());
                                         anPo.setString("COMMIT_TIME", d);
                                         anPo.setInteger("AUDITED_BY_IDENTITY", Integer.parseInt(DictCodeConstants.DICT_AUDITING_TYPE_FINANCE));
                                         if(auditDto.getTreatmentAdvice()!=null){
                                             anPo.setString("TREATMENT_ADVICE", auditDto.getTreatmentAdvice());
                                         }
                                         anPo.saveIt();
                                     }
                                 
                             }
                        }
                        
                 
                    }
                }
                pagePO.setInteger("D_KEY", Integer.parseInt(DictCodeConstants.D_KEY));
              
                pagePO.saveIt();
               
                SalesOrderPO sOrderPo = SalesOrderPO.findByCompositeKeys(loginInfo.getDealerCode(),auditDto.getAuditList());
                if(sOrderPo!=null){
                    if(!StringUtils.isNullOrEmpty(sOrderPo.getString("INTENT_SO_NO"))&&(
                            !commonNoService.getDefalutPara("8036").equals("12781001")&&sOrderPo.getInteger("SO_STATUS")>Integer.parseInt(DictCodeConstants.DICT_SO_STATUS_NOT_AUDIT)||
                            commonNoService.getDefalutPara("8036").equals("12781001")&&sOrderPo.getInteger("SO_STATUS")>Integer.parseInt(DictCodeConstants.DICT_SO_STATUS_ACCOUNTANT_AUDITING))) {
                        List<Object> intentReceiveMoneyList = new ArrayList<Object>();
                        intentReceiveMoneyList.add(loginInfo.getDealerCode());
                        intentReceiveMoneyList.add(sOrderPo.getString("INTENT_SO_NO"));
                        List<TtIntentReceiveMoneyPO> receivePo = TtIntentReceiveMoneyPO.findBySQL("select * from TT_INTENT_RECEIVE_MONEY where DEALER_CODE= ? AND INTENT_SO_NO= ? ",intentReceiveMoneyList.toArray());
                       if(receivePo!=null&&receivePo.size()>0){
                           TtCustomerGatheringPO gatheringPO = new TtCustomerGatheringPO();
                           for(int j=0;j<receivePo.size();j++){
                               TtIntentReceiveMoneyPO receive = receivePo.get(j);
                               if(!StringUtils.isNullOrEmpty(receive.getInteger("IS_ADVANCE_PAYMENTS"))&&receive.getInteger("IS_ADVANCE_PAYMENTS")!=12781001){
                                   String No = commonNoService.getSystemOrderNo(CommonConstants.SRV_SKDH);
                                   gatheringPO.setString("BILL_NO",receive.getString("BILL_NO"));
                                   gatheringPO.setString("CUSTOMER_NO",sOrderPo.getString("CUSTOMER_NO"));
                                   gatheringPO.setString("SO_NO",receive.getString("SO_NO"));
                                   gatheringPO.setInteger("GATHERING_TYPE",receive.getInteger("GATHERING_TYPE"));
                                   gatheringPO.setString("OWNED_BY",String.valueOf(loginInfo.getUserId()));
                                   gatheringPO.setString("PAY_TYPE_CODE",receive.getString("0004"));
                                   gatheringPO.setDouble("RECEIVE_AMOUNT",receive.getDouble("RECEIVE_AMOUNT"));
                                   gatheringPO.setDate("RECEIVE_DATE",receive.getDate("RECEIVE_DATE"));
                                   gatheringPO.setString("RECEIVE_NO",No);
                                   gatheringPO.setDate("RECORD_DATE",new Date());
                                   gatheringPO.setString("RECORDER",receive.getString("RECORDER"));
                                   gatheringPO.setString("TRANSACTOR",receive.getString("TRANSACTOR"));
                                   gatheringPO.setString("WRITEOFF_BY",receive.getString("WRITEOFF_BY"));
                                   gatheringPO.setDate("WRITEOFF_DATE",new Date());
                                   gatheringPO.setInteger("WRITEOFF_TAG",DictCodeConstants.STATUS_IS_YES);
                                   gatheringPO.setString("REMARK",receive.getString("REMARK"));
                                   gatheringPO.saveIt();
                                   //更新客户资料表中收款总金额和帐户可用余额
                                   if(!StringUtils.isNullOrEmpty(sOrderPo.getString("CUSTOMER_NO"))){
                                       PotentialCusPO cusPo = PotentialCusPO.findByCompositeKeys(loginInfo.getDealerCode(),sOrderPo.getString("CUSTOMER_NO"));
                                       if(cusPo!=null){
                                           Double receiveAmount = receive.getDouble("RECEIVE_AMOUNT");
                                           Double gatheredSum = 0D;
                                           Double usableAmount = 0D;
                                           if(cusPo.getDouble("GATHERED_SUM")!=null){
                                               gatheredSum = cusPo.getDouble("GATHERED_SUM")+receiveAmount;
                                           }else{
                                               gatheredSum = receiveAmount;
                                           }
                                           cusPo.setDouble("GATHERED_SUM", gatheredSum);
                                           if(cusPo.getDouble("USABLE_AMOUNT")!=null){
                                               usableAmount = cusPo.getDouble("USABLE_AMOUNT")+receiveAmount;
                                           }else{
                                               usableAmount = receiveAmount;
                                           }
                                           cusPo.setDouble("USABLE_AMOUNT", usableAmount);
                                           cusPo.saveIt();
                                       }
                                   }
                                   receive.setInteger("IS_ADVANCE_PAYMENTS", 12781001);
                                   receive.saveIt();
                               }   

                           }
                           List<Object> intentReceiveList = new ArrayList<Object>();
                           intentReceiveList.add(loginInfo.getDealerCode());
                           intentReceiveList.add(sOrderPo.getString("INTENT_SO_NO"));
                           List<TtIntentReceiveMoneyPO> intentReceivePo = TtIntentReceiveMoneyPO.findBySQL("select * from TT_INTENT_RECEIVE_MONEY where DEALER_CODE= ? AND INTENT_SO_NO= ? AND (IS_ADVANCE_PAYMENTS != 12781001 OR IS_ADVANCE_PAYMENTS IS NULL) ",intentReceiveList.toArray());
                           if(!StringUtils.isNullOrEmpty(intentReceivePo)&&intentReceivePo.size()>0){
                               TtIntentSalesOrderPO intentOrderPO = TtIntentSalesOrderPO.findByCompositeKeys(loginInfo.getDealerCode(),sOrderPo.getString("INTENT_SO_NO"));
                               if(intentOrderPO!=null){
                                   intentOrderPO.setInteger("SO_STATUS", Integer.parseInt(DictCodeConstants.DICT_INTENT_SO_STATUS_HAVE_CLOSED));
                                   intentOrderPO.saveIt();
                               }
                           }
                       }
                        
                    }
                 if(!StringUtils.isNullOrEmpty(sOrderPo.getInteger("BUSINESS_TYPE"))&&sOrderPo.getInteger("BUSINESS_TYPE")==Integer.parseInt(DictCodeConstants.DICT_SO_TYPE_RERURN)&&sOrderPo.getInteger("SO_STATUS")>Integer.parseInt(DictCodeConstants.DICT_SO_STATUS_ACCOUNTANT_AUDITING)){
                     SalesOrderPO ssOrderPo = SalesOrderPO.findByCompositeKeys(loginInfo.getDealerCode(),sOrderPo.getString("OLD_SO_NO"));
                     if(ssOrderPo!=null&&!StringUtils.isNullOrEmpty(ssOrderPo.getString("INTENT_SO_NO"))){
                         TtIntentSalesOrderPO intentSalesOrder = TtIntentSalesOrderPO.findByCompositeKeys(loginInfo.getDealerCode(),ssOrderPo.getString("INTENT_SO_NO"));
                         intentSalesOrder.setInteger("SO_STATUS", Integer.parseInt(DictCodeConstants.DICT_INTENT_SO_STATUS_FAIL));
                         intentSalesOrder.saveIt();
                     }
                 }else if(sOrderPo.getInteger("BUSINESS_TYPE")==Integer.parseInt(DictCodeConstants.DICT_SO_STATUS_HAVE_CANCEL)){
                     TtIntentSalesOrderPO intentSalesOrder = TtIntentSalesOrderPO.findByCompositeKeys(loginInfo.getDealerCode(),sOrderPo.getString("INTENT_SO_NO"));
                     intentSalesOrder.setInteger("SO_STATUS", Integer.parseInt(DictCodeConstants.DICT_INTENT_SO_STATUS_HAVE_BALANCE));
                     intentSalesOrder.setInteger("IS_SALES_ORDER",Integer.parseInt(DictCodeConstants.DICT_IS_NO));
                     intentSalesOrder.saveIt();
                 }
                 if((sOrderPo.getInteger("PAY_OFF")==12781001||(sOrderPo.getInteger("LOSSES_PAY_OFF")!=null&&sOrderPo.getInteger("LOSSES_PAY_OFF")==12781001)
                         ||(sOrderPo.getInteger("ORDER_SORT")!=null&&sOrderPo.getInteger("ORDER_SORT")==13861002)||!commonNoService.getDefalutPara("8036").equals("12781001"))&&(commonNoService.getDefalutPara("8003").equals("12781001")||(sOrderPo.getInteger("ORDER_SORT")!=null&&sOrderPo.getInteger("ORDER_SORT")==13861002))
                         &&(sOrderPo.getInteger("SO_STATUS")==13011025||sOrderPo.getInteger("SO_STATUS")==13011030)){
                     if(sOrderPo.getInteger("BUSINESS_TYPE")==Integer.parseInt(DictCodeConstants.DICT_SO_TYPE_SERVICE)){
                         sOrderPo.setInteger("SO_STATUS", 13011035);
                         sOrderPo.saveIt();
                     }
                 }
                }
                //
            }
        }

    }
    public SoAuditingPO setSoAuditingPO(SoAuditingDTO auditDto,String dealerCode,long userId){
        SoAuditingPO aPo = new SoAuditingPO();
        aPo.setString("SO_NO", auditDto.getAuditList());
        aPo.setInteger("AUDITING_RESULT", auditDto.getAuditingResult());
        aPo.setString("AUDITING_POSTIL", auditDto.getAuditingPostil());
        aPo.setLong("AUDITED_BY", userId);
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String d = format.format(new Date());
        aPo.setString("AUDITING_DATE", d);
        aPo.setLong("SUBMIT_AUDITED_BY", userId);
        aPo.setString("COMMIT_TIME", d);
        if(auditDto.getTreatmentAdvice()!=null){
            aPo.setString("TREATMENT_ADVICE", auditDto.getTreatmentAdvice());
        }
        return aPo;
    }
    public int  setUpdateAuditing(String dealerCode,SoAuditingDTO auditDto,long userId){
        int i=0;
        System.out.println("111111111111111111111111111111111222222222222222222222222222222");
        List<Object> wsItemList = new ArrayList<Object>();
        wsItemList.add(dealerCode);
        wsItemList.add(auditDto.getAuditList());
        List<SoAuditingPO> wsItemPo = SoAuditingPO.findBySQL("select * from TT_SO_AUDITING where DEALER_CODE= ? AND SO_NO= ? order by CREATED_AT desc ",wsItemList.toArray());
        if(wsItemPo!=null&&wsItemPo.size()>0){
            SoAuditingPO aPo = wsItemPo.get(0);
            aPo.setInteger("AUDITING_RESULT", auditDto.getAuditingResult());
            aPo.setString("AUDITING_POSTIL", auditDto.getAuditingPostil());
            aPo.setLong("AUDITED_BY", userId);
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String d = format.format(new Date());
            System.out.println("1222222222222222222222222222222");
            System.out.println(new Date());
            System.out.println(d);
            aPo.setString("COMMIT_TIME", d);
            aPo.setString("AUDITING_DATE", d);
            aPo.saveIt();
            i=1;
        }
        return i;
    }
    //
    public int executeMaintainVehcileOutSystem(String soNo, String dealerCode,
                                               String userId) throws ServiceBizException {
        SalesOrderPO order = SalesOrderPO.findByCompositeKeys(dealerCode, soNo);
        int i = 0;
        if (!StringUtils.isNullOrEmpty(commonNoService.getDefalutPara("3339"))
            && commonNoService.getDefalutPara("3339").equals("12781001")) {// 保修手册号码必须填
            VsStockPO stockPO = VsStockPO.findByCompositeKeys(dealerCode, order.getString("VIN"));
            if (!StringUtils.isNullOrEmpty(stockPO)
                && stockPO.getInteger("STOCK_STATUS") == DictCodeConstants.DICT_STORAGE_STATUS_IN_STORAGE) {
                if (!StringUtils.isNullOrEmpty(stockPO.getString("WARRANTY_NUMBER"))) {
                    i = 1;
                    return 1;
                }
            }
        }
        if (i == 0) {
            // ------------------
            // 创建出库单
            String sdNo = commonNoService.getSystemOrderNo(CommonConstants.SD_NO);
            TtVsStockDeliveryPO stockDeliveryPO = new TtVsStockDeliveryPO();
            stockDeliveryPO.setString("SD_NO", sdNo);
            if (!StringUtils.isNullOrEmpty(order.getInteger("BUSINESS_TYPE"))
                && order.getInteger("BUSINESS_TYPE") == Integer.parseInt(DictCodeConstants.DICT_SO_TYPE_GENERAL)) {
                stockDeliveryPO.setInteger("STOCK_OUT_TYPE", DictCodeConstants.DICT_STOCK_OUT_TYPE_SALE);
            }
            if (!StringUtils.isNullOrEmpty(order.getInteger("BUSINESS_TYPE"))
                && order.getInteger("BUSINESS_TYPE") == Integer.parseInt(DictCodeConstants.DICT_SO_TYPE_ENTRUST_DELIVERY)) {
                stockDeliveryPO.setInteger("STOCK_OUT_TYPE", DictCodeConstants.DICT_STOCK_OUT_TYPE_DELIVERY);
            }
            if (!StringUtils.isNullOrEmpty(order.getInteger("BUSINESS_TYPE"))
                && order.getInteger("BUSINESS_TYPE") == Integer.parseInt(DictCodeConstants.DICT_SO_TYPE_ALLOCATION)) {
                stockDeliveryPO.setInteger("STOCK_OUT_TYPE", DictCodeConstants.DICT_STOCK_OUT_TYPE_ALLOCATION);
            }
            stockDeliveryPO.setDate("SHEET_CREATE_DATE", new Date());
            stockDeliveryPO.setString("SHEET_CREATE_BY", userId);
            stockDeliveryPO.setInteger("IS_ALL_FINISHED", DictCodeConstants.STATUS_IS_YES);
            stockDeliveryPO.setString("REMARK", "系统自动创建出库单");
            stockDeliveryPO.setString("OWNED_BY", userId);
            stockDeliveryPO.saveIt();
            // 创建出库单明细
            TtVsDeliveryItemPO deliveryItemPO = new TtVsDeliveryItemPO();
            deliveryItemPO.setString("OWNED_BY", userId);
            deliveryItemPO.setString("VIN", order.getString("VIN"));
            deliveryItemPO.setString("SD_NO", sdNo);
            deliveryItemPO.setString("SO_NO", order.getString("SO_NO"));
            deliveryItemPO.setString("PRODUCT_CODE", order.getString("PRODUCT_CODE"));
            deliveryItemPO.setString("STORAGE_CODE", order.getString("STORAGE_CODE"));
            deliveryItemPO.setString("STORAGE_POSITION_CODE", order.getString("STORAGE_POSITION_CODE"));
            deliveryItemPO.setDouble("VEHICLE_PRICE", order.getDouble("VEHICLE_PRICE"));
            deliveryItemPO.setInteger("STOCK_STATUS", DictCodeConstants.DICT_STORAGE_STATUS_MOVE_OUT);
            deliveryItemPO.setInteger("DISPATCHED_STATUS", DictCodeConstants.DICT_DISPATCHED_STATUS_HAVE_DELIVER);
            VsStockPO stockPO = VsStockPO.findByCompositeKeys(dealerCode, order.getString("VIN"));
            if (stockPO != null) {
                deliveryItemPO.setDouble("PURCHASE_PRICE", stockPO.getDouble("PURCHASE_PRICE"));
                deliveryItemPO.setDouble("ADDITIONAL_COST", stockPO.getDouble("ADDITIONAL_COST"));
                deliveryItemPO.setInteger("MAR_STATUS", stockPO.getInteger("MAR_STATUS"));
                deliveryItemPO.setInteger("IS_SECONDHAND", stockPO.getInteger("IS_SECONDHAND"));
                deliveryItemPO.setInteger("IS_VIP", stockPO.getInteger("IS_VIP"));
                deliveryItemPO.setInteger("IS_TEST_DRIVE_CAR", stockPO.getInteger("IS_TEST_DRIVE_CAR"));
                deliveryItemPO.setInteger("IS_CONSIGNED", stockPO.getInteger("IS_CONSIGNED"));
                deliveryItemPO.setInteger("IS_PROMOTION", stockPO.getInteger("IS_PROMOTION"));
                deliveryItemPO.setInteger("IS_PURCHASE_RETURN", stockPO.getInteger("IS_PURCHASE_RETURN"));
                deliveryItemPO.setInteger("OEM_TAG", stockPO.getInteger("OEM_TAG"));
                deliveryItemPO.setInteger("IS_PRICE_ADJUSTED", stockPO.getInteger("IS_PRICE_ADJUSTED"));
                deliveryItemPO.setDouble("OLD_DIRECTIVE_PRICE", stockPO.getDouble("OLD_DIRECTIVE_PRICE"));
                deliveryItemPO.setDouble("DIRECTIVE_PRICE", stockPO.getDouble("DIRECTIVE_PRICE"));
                deliveryItemPO.setString("ADJUST_REASON", stockPO.getString("ADJUST_REASON"));
                deliveryItemPO.setString("REMARK", stockPO.getString("REMARK"));
            }
            deliveryItemPO.setInteger("IS_FINISHED", DictCodeConstants.STATUS_IS_YES);
            deliveryItemPO.saveIt();
            VsStockPO stock = VsStockPO.findByCompositeKeys(dealerCode, order.getString("VIN"));
            // 反向检查车辆是否已经做过出库(车辆库存根据vin,和 库存状态 出库)
            if (!StringUtils.isNullOrEmpty(stock)
                && stock.getInteger("STOCK_STATUS") == DictCodeConstants.DICT_STORAGE_STATUS_MOVE_OUT) {
                throw new ServiceBizException("该" + order.getString("VIN") + "VIN码车辆已经出库！");
            }
            // 【车辆出库时销售日期有值就取销售日期，没有就查费用类型是购车费用的发票，有就取这个开票日期，没有就取当前日期】定义成【A】
            // ,车辆出库自动算保险和保修日期的开始日期就以【A】为准。
        /*    if (!StringUtils.isNullOrEmpty(order.getString("VIN"))) {
                VehiclePO carPO = VehiclePO.findByCompositeKeys(order.getString("VIN"), dealerCode);
                if (carPO != null) {
                    if (!StringUtils.isNullOrEmpty(carPO.getDate("SALES_DATE"))) {
                        List<Object> invoicerList = new ArrayList<Object>();
                        invoicerList.add(order.getString("SO_NO"));
                        invoicerList.add(order.getString("VIN"));
                        invoicerList.add(dealerCode);
                        invoicerList.add(Integer.parseInt(DictCodeConstants.D_KEY));
                        SoInvoicePO invoicerPo = SoInvoicePO.findFirst(" SO_NO= ? AND VIN= ? AND DEALER_CODE= ? AND D_KEY= ? ",
                                                                       invoicerList.toArray());
        

                    }
                }
            }*/
            // 同步更新车辆的省市县
            if (!StringUtils.isNullOrEmpty(order.getString("VIN"))
                && !StringUtils.isNullOrEmpty(order.getString("SO_NO"))) {
                if (!StringUtils.isNullOrEmpty(commonNoService.getDefalutPara("1300"))
                    && commonNoService.getDefalutPara("1300").equals("12781001")) {
                    VehiclePO vehiclerpo = VehiclePO.findByCompositeKeys(order.getString("VIN"), dealerCode);
                    vehiclerpo.setInteger("PROVINCE", order.getInteger("PROVINCE"));
                    vehiclerpo.setInteger("CITY", order.getInteger("CITY"));
                    vehiclerpo.setInteger("DISTRICT", order.getInteger("DISTRICT"));
                    if (StringUtils.isNullOrEmpty(order.getInteger("PROVINCE"))
                        && StringUtils.isNullOrEmpty(order.getInteger("CITY"))
                        && StringUtils.isNullOrEmpty(order.getInteger("DISTRICT"))) {
                        throw new ServiceBizException("请在销售订单中填入省市县信息!");
                    }
                    vehiclerpo.saveIt();
                }
            }
            // 修改车辆库存表 车辆状态为 出库 配车状态为 交车出库
            // 回写车辆库存表里出库业务类型,销售定单号
            TtVsStockDeliveryPO deliveryPO = TtVsStockDeliveryPO.findByCompositeKeys(dealerCode, sdNo);
            stock.setInteger("IS_LOCK", DictCodeConstants.STATUS_IS_NOT);
            stock.setString("OWNED_BY", userId);
            if (deliveryPO != null) {
                stock.setInteger("STOCK_OUT_TYPE", deliveryPO.getInteger("STOCK_OUT_TYPE"));
                stock.setString("SD_NO", deliveryPO.getInteger("SD_NO"));
            }
            stock.setString("SO_NO", order.getString("SO_NO"));
            stock.setInteger("STOCK_STATUS", DictCodeConstants.DICT_STORAGE_STATUS_MOVE_OUT);
            stock.setInteger("DISPATCHED_STATUS", DictCodeConstants.DICT_DISPATCHED_STATUS_HAVE_DELIVER);
            stock.setString("LAST_STOCK_OUT_BY", userId);
            stock.setDate("LATEST_STOCK_OUT_DATE", new Date());
            stock.setDate("FIRST_STOCK_OUT_DATE", new Date());
            stock.saveIt();
            // 修改销售定单字段//如果销售出库回写销售订单中是否上报写为否12781002
            List<Object> wsItemList = new ArrayList<Object>();
            wsItemList.add(dealerCode);
            wsItemList.add(order.getString("SO_NO"));
            List<QcsArchivesPO> wsItemPo = QcsArchivesPO.findBySQL("select * from TT_QCS_ARCHIVES where DEALER_CODE= ? AND SO_NO= ? ",
                                                                   wsItemList.toArray());
            if (wsItemPo != null && wsItemPo.size() > 0) {
                for (int k = 0; k < wsItemPo.size(); k++) {
                    QcsArchivesPO qscPo = wsItemPo.get(k);
                    qscPo.setInteger("IS_PDI_START", DictCodeConstants.STATUS_IS_YES);
                    qscPo.setDate("PDI_START_DATE", new Date());
                    qscPo.saveIt();
                }

            }
            // 一般订单 委托订单记录订单审核历史
            if (!StringUtils.isNullOrEmpty(order.getInteger("BUSINESS_TYPE"))
                && (order.getInteger("BUSINESS_TYPE") == Integer.parseInt(DictCodeConstants.DICT_SO_TYPE_GENERAL)
                    || order.getInteger("BUSINESS_TYPE") == Integer.parseInt(DictCodeConstants.DICT_SO_TYPE_ENTRUST_DELIVERY))) {
                TtOrderStatusUpdatePO orderStatusUpdatePO = new TtOrderStatusUpdatePO();
                orderStatusUpdatePO.setString("SO_NO", order.getInteger("SO_NO"));
                orderStatusUpdatePO.setInteger("SO_STATUS",
                                               Integer.parseInt(DictCodeConstants.DICT_SO_STATUS_HAVE_OUT_STOCK));
                orderStatusUpdatePO.setString("OWNED_BY", userId);
                orderStatusUpdatePO.setInteger("IS_UPLOAD", Integer.parseInt(DictCodeConstants.DICT_IS_NO));
                orderStatusUpdatePO.setDate("ALTERATION_TIME", new Date());
                orderStatusUpdatePO.setInteger("D_KEY", Integer.parseInt(DictCodeConstants.D_KEY));
                orderStatusUpdatePO.saveIt();
            }
            // 委托交车出库，同样也设置客户的级别
            if (deliveryPO != null && !StringUtils.isNullOrEmpty(deliveryPO.getInteger("STOCK_OUT_TYPE"))
                && deliveryPO.getInteger("STOCK_OUT_TYPE") == DictCodeConstants.DICT_STOCK_OUT_TYPE_DELIVERY
                && !"".equals(order.getString("VIN"))) {
                // 1.修改潜在客户资料表里,CUSTOMER_STATUS改为基盘客户,INTENT_LEVEL改为N级
                PotentialCusPO potentialPo = new PotentialCusPO();
                if (!StringUtils.isNullOrEmpty(order.getString("CUSTOMER_NO"))) {
                    potentialPo = PotentialCusPO.findByCompositeKeys(dealerCode, order.getString("CUSTOMER_NO"));
                } else {
                    throw new ServiceBizException("丢失主键!");
                }
                if (!StringUtils.isNullOrEmpty(potentialPo)
                    && (StringUtils.isNullOrEmpty(potentialPo.getInteger("INTENT_LEVEL"))
                        || potentialPo.getInteger("INTENT_LEVEL") != Integer.parseInt(DictCodeConstants.DICT_INTENT_LEVEL_D))) {
                    potentialPo.setInteger("INTENT_LEVEL", Integer.parseInt(DictCodeConstants.DICT_INTENT_LEVEL_D));
                    potentialPo.setDate("DDCN_UPDATE_DATE", new Date());
                    potentialPo.saveIt();
                } else {
                    throw new ServiceBizException("丢失主键!");
                }
                /**
                 * 把这个客户的跟进记录中没有跟进的记录删掉 caoyang 修改
                 */
                List<Object> promotionPlanList = new ArrayList<Object>();
                promotionPlanList.add(dealerCode);
                promotionPlanList.add(order.getString("CUSTOMER_NO"));
                promotionPlanList.add(Integer.parseInt(DictCodeConstants.D_KEY));
                List<TtSalesPromotionPlanPO> promotionPlanPO = TtSalesPromotionPlanPO.findBySQL("select * from TT_SALES_PROMOTION_PLAN where DEALER_CODE= ? AND CUSTOMER_NO= ? AND D_KEY= ? and (PROM_RESULT is null OR PROM_RESULT=0)",
                                                                                                promotionPlanList.toArray());
                if (promotionPlanPO != null && promotionPlanPO.size() > 0) {
                    for (int p = 0; p < promotionPlanPO.size(); p++) {
                        TtSalesPromotionPlanPO promotionPlan = promotionPlanPO.get(p);
                        if (promotionPlan != null) {
                            // 删除
                            promotionPlan.delete();
                        }
                    }
                }
                // 2潜在客户意向表中是否完成意向为是
                List<Object> cusIntentList = new ArrayList<Object>();
                cusIntentList.add(dealerCode);
                cusIntentList.add(order.getString("CUSTOMER_NO"));
                cusIntentList.add(Integer.parseInt(DictCodeConstants.D_KEY));
                List<TtCusIntentPO> CusIntentPO = TtCusIntentPO.findBySQL("select * from TT_CUSTOMER_INTENT where DEALER_CODE= ? AND CUSTOMER_NO= ? AND D_KEY= ? ",
                                                                          cusIntentList.toArray());
                if (CusIntentPO != null && CusIntentPO.size() > 0) {
                    for (int m = 0; m < CusIntentPO.size(); m++) {
                        TtCusIntentPO intent = CusIntentPO.get(m);
                        if (intent != null) {
                            intent.setInteger("INTENT_FINISHED", DictCodeConstants.STATUS_IS_YES);
                            intent.saveIt();
                        }
                    }
                }
            }
            // 如果是销售出库则将客户资料表里客户级别改为基盘客户,客户意向改为N级,客户的意向状态改为完成
            if (deliveryPO != null && !StringUtils.isNullOrEmpty(deliveryPO.getInteger("STOCK_OUT_TYPE"))
                && deliveryPO.getInteger("STOCK_OUT_TYPE") == DictCodeConstants.DICT_STOCK_OUT_TYPE_SALE) {
                PotentialCusPO potentialPo = new PotentialCusPO();
                if (!StringUtils.isNullOrEmpty(order.getString("CUSTOMER_NO"))) {
                    potentialPo = PotentialCusPO.findByCompositeKeys(dealerCode, order.getString("CUSTOMER_NO"));
                } else {
                    throw new ServiceBizException("丢失主键!");
                }
                if (!StringUtils.isNullOrEmpty(potentialPo)
                    && (StringUtils.isNullOrEmpty(potentialPo.getInteger("INTENT_LEVEL"))
                        || potentialPo.getInteger("INTENT_LEVEL") != Integer.parseInt(DictCodeConstants.DICT_INTENT_LEVEL_D))) {
                    potentialPo.setInteger("INTENT_LEVEL", Integer.parseInt(DictCodeConstants.DICT_INTENT_LEVEL_D));
                    potentialPo.setDate("DDCN_UPDATE_DATE", new Date());
                    potentialPo.saveIt();
                }
                /**
                 * 把这个客户的跟进记录中没有跟进的记录删掉 caoyang 修改
                 */
                List<Object> promotionPlanList = new ArrayList<Object>();
                promotionPlanList.add(dealerCode);
                promotionPlanList.add(order.getString("CUSTOMER_NO"));
                promotionPlanList.add(Integer.parseInt(DictCodeConstants.D_KEY));
                List<TtSalesPromotionPlanPO> promotionPlanPO = TtSalesPromotionPlanPO.findBySQL("select * from TT_SALES_PROMOTION_PLAN where DEALER_CODE= ? AND CUSTOMER_NO= ? AND D_KEY= ? and (PROM_RESULT is null OR PROM_RESULT=0)",
                                                                                                promotionPlanList.toArray());
                if (promotionPlanPO != null && promotionPlanPO.size() > 0) {
                    for (int p = 0; p < promotionPlanPO.size(); p++) {
                        TtSalesPromotionPlanPO promotionPlan = promotionPlanPO.get(p);
                        if (promotionPlan != null) {
                            // 删除
                            promotionPlan.delete();
                        }
                    }
                }
                // 创建跟进记录
                List<Object> taskList = new ArrayList<Object>();
                taskList.add(Long.parseLong(DictCodeConstants.DICT_INTENT_LEVEL_D));
                taskList.add(Long.parseLong(DictCodeConstants.DICT_CUSTOMER_STATUS_LATENCY));
                taskList.add(DictCodeConstants.IS_YES);
                taskList.add(dealerCode);
                List<TrackingTaskPO> taskPO = TrackingTaskPO.findBySQL("select * from TM_TRACKING_TASK where INTENT_LEVEL= ? AND CUSTOMER_STATUS= ? AND IS_VALID= ? AND DEALER_CODE= ? ",
                                                                       taskList.toArray());
                if (taskPO != null && taskPO.size() > 0) {
                    for (int n = 0; n < taskPO.size(); n++) {
                        TrackingTaskPO task = taskPO.get(n);
                        TtSalesPromotionPlanPO sPlanPo = new TtSalesPromotionPlanPO();
                        sPlanPo.setLong("TASK_ID", task.getLong("TASK_ID"));
                        sPlanPo.setLong("INTENT_ID", potentialPo.getLong("INTENT_ID"));
                        sPlanPo.setString("CUSTOMER_NO", order.getString("CUSTOMER_NO"));
                        sPlanPo.setString("CUSTOMER_NAME", potentialPo.getString("CUSTOMER_NAME"));
                        sPlanPo.setInteger("PRIOR_GRADE", task.getInteger("INTENT_LEVEL"));
                        sPlanPo.setString("PROM_CONTENT", task.getString("TASK_CONTENT"));
                        sPlanPo.setInteger("PROM_WAY", task.getInteger("EXECUTE_TYPE"));
                        sPlanPo.setInteger("CREATE_TYPE", 13291001);
                        Calendar c = Calendar.getInstance();
                        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                        String dates = new String();
                        sPlanPo.setDate("ACTION_DATE", "");
                        if (task.getInteger("INTERVAL_DAYS") != null) {
                            c.setTime(new Date());
                            c.add(7, task.getInteger("INTERVAL_DAYS"));
                            dates = format.format(c.getTime()).toString();
                        }
                        try {
                            sPlanPo.setDate("SCHEDULE_DATE", format.parse(dates));
                        } catch (ParseException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                        sPlanPo.setString("CONTACTOR_NAME", potentialPo.getString("CONTACTOR_NAME"));
                        sPlanPo.setString("PHONE", potentialPo.getString("CONTACTOR_PHONE"));
                        sPlanPo.setString("MOBILE", potentialPo.getString("CONTACTOR_MOBILE"));
                        sPlanPo.saveIt();

                    }
                }
                /**
                 * 根据VIN判断是否有保有客户信息，没有则新增把潜在客户信息保存到保有客户信息中
                 */
                 insertIntoCustomer(dealerCode, order.getString("VIN"), order.getString("SO_NO"),
                                                        order.getString("CUSTOMER_NO"), userId);
            }
        }
        return 0;
    }

    /**
     * //保有客户增加
     */
    public String insertIntoCustomer(String entityCode, String vin, String soNo, String pCustomerNo, String userId) {
        String customerNO = "";// 保有客户编号;1.新生成的。2.已经存在的
        List<Map> result = this.checkOwnerInfo(vin, entityCode);
        if (result != null && result.size() > 0) {
            // 有保有客户信息
            VehiclePO vPO = VehiclePO.findByCompositeKeys(vin, entityCode);
            customerNO = vPO.getString("CUSTOMER_NO");
        } else {
            // 没有保有客户信息,新增保有客户信息,根据出库单里的销售订单，查销售订单中的客户编号,把潜在客户信息带出保存为保有客户信息
            SalesOrderPO salePo = SalesOrderPO.findByCompositeKeys(entityCode, soNo);
            if (salePo != null) {
                PotentialCusPO cusPO = PotentialCusPO.findByCompositeKeys(entityCode, salePo.getString("CUSTOMER_NO"));
                if (cusPO != null) {
                    CustomerPO PO = new CustomerPO();
                    customerNO = commonNoService.getSystemOrderNo(CommonConstants.CUSTOMER_PREFIX);
                    PO.setString("CUSTOMER_NO", customerNO);
                    PO.setString("CUSTOMER_NAME", cusPO.getString("CUSTOMER_NAME"));
                    PO.setString("LARGE_CUSTOMER_NO", cusPO.getString("LARGE_CUSTOMER_NO"));
                    PO.setString("SOD_CUSTOMER_ID", cusPO.getString("SOD_CUSTOMER_ID"));
                    PO.setInteger("CUSTOMER_TYPE", cusPO.getInteger("CUSTOMER_TYPE"));
                    PO.setInteger("GENDER", cusPO.getInteger("GENDER"));
                    PO.setDate("BIRTHDAY", cusPO.getDate("BIRTHDAY"));
                    PO.setString("ZIP_CODE", cusPO.getString("ZIP_CODE"));
                    PO.setInteger("COUNTRY_CODE", cusPO.getInteger("COUNTRY_CODE"));
                    PO.setInteger("PROVINCE", cusPO.getInteger("PROVINCE"));
                    PO.setInteger("CITY", cusPO.getInteger("CITY"));
                    PO.setInteger("DISTRICT", cusPO.getInteger("DISTRICT"));
                    PO.setString("ADDRESS", cusPO.getString("ADDRESS"));
                    PO.setString("CONTACTOR_PHONE", cusPO.getString("CONTACTOR_PHONE"));
                    PO.setString("CONTACTOR_MOBILE", cusPO.getString("CONTACTOR_MOBILE"));
                    PO.setString("FAX", cusPO.getString("FAX"));
                    PO.setString("E_MAIL", cusPO.getString("E_MAIL"));
                    PO.setInteger("BEST_CONTACT_TYPE", cusPO.getInteger("BEST_CONTACT_TYPE"));
                    PO.setInteger("CT_CODE", cusPO.getInteger("CT_CODE"));
                    PO.setString("CERTIFICATE_NO", cusPO.getString("CERTIFICATE_NO"));
                    PO.setInteger("EDUCATION_LEVEL", cusPO.getInteger("EDUCATION_LEVEL"));
                    PO.setInteger("OWNER_MARRIAGE", cusPO.getInteger("OWNER_MARRIAGE"));
                    PO.setInteger("ORGAN_TYPE_CODE", cusPO.getInteger("ORGAN_TYPE_CODE"));
                    PO.setInteger("INDUSTRY_FIRST", cusPO.getInteger("INDUSTRY_FIRST"));
                    PO.setInteger("INDUSTRY_SECOND", cusPO.getInteger("INDUSTRY_SECOND"));
                    PO.setInteger("VOCATION_TYPE", cusPO.getInteger("VOCATION_TYPE"));
                    PO.setInteger("FAMILY_INCOME", cusPO.getInteger("FAMILY_INCOME"));
                    PO.setInteger("AGE_STAGE", cusPO.getInteger("AGE_STAGE"));
                    PO.setInteger("IS_CRPVIP", cusPO.getInteger("IS_CRPVIP"));
                    PO.setInteger("IS_FIRST_BUY", cusPO.getInteger("IS_FIRST_BUY"));
                    PO.setInteger("HAS_DRIVER_LICENSE", cusPO.getInteger("HAS_DRIVER_LICENSE"));
                    PO.setInteger("IS_PERSON_DRIVE_CAR", cusPO.getInteger("IS_PERSON_DRIVE_CAR"));
                    PO.setInteger("BUY_PURPOSE", cusPO.getInteger("BUY_PURPOSE"));
                    PO.setInteger("CHOICE_REASON", cusPO.getInteger("CHOICE_REASON"));
                    PO.setInteger("CUS_SOURCE", cusPO.getInteger("CUS_SOURCE"));
                    PO.setInteger("MEDIA_TYPE", cusPO.getInteger("MEDIA_TYPE"));
                    PO.setInteger("INTENT_LEVEL", cusPO.getInteger("INTENT_LEVEL"));
                    PO.setInteger("INIT_LEVEL", cusPO.getInteger("INIT_LEVEL"));
                    PO.setInteger("IS_WHOLESALER", cusPO.getInteger("IS_WHOLESALER"));
                    PO.setInteger("IS_DIRECT", cusPO.getInteger("IS_DIRECT"));
                    PO.setInteger("IS_UPLOAD", cusPO.getInteger("IS_UPLOAD"));
                    PO.setString("HOBBY", cusPO.getString("HOBBY"));
                    PO.setString("ORGAN_TYPE", cusPO.getString("ORGAN_TYPE"));
                    PO.setString("POSITION_NAME", cusPO.getString("POSITION_NAME"));
                    PO.setString("BUY_REASON", cusPO.getString("BUY_REASON"));
                    PO.setString("CAMPAIGN_CODE", cusPO.getString("CAMPAIGN_CODE"));
                    PO.setString("FAIL_CONSULTANT", cusPO.getString("FAIL_CONSULTANT"));
                    PO.setString("DELAY_CONSULTANT", cusPO.getString("DELAY_CONSULTANT"));
                    PO.setString("SOLD_BY", cusPO.getString("SOLD_BY"));
                    PO.setString("DCRC_SERVICE", cusPO.getString("DCRC_SERVICE"));
                    PO.setString("RECOMMEND_EMP_NAME", cusPO.getString("RECOMMEND_EMP_NAME"));
                    PO.setString("MODIFY_REASON", cusPO.getString("MODIFY_REASON"));
                    PO.setString("REPORT_REMARK", cusPO.getString("REPORT_REMARK"));
                    PO.setString("AUDITING_REMARK", cusPO.getString("AUDITING_REMARK"));
                    PO.setString("REPORT_ABORT_REASON", cusPO.getString("REPORT_ABORT_REASON"));
                    PO.setString("REMARK", cusPO.getString("REMARK"));
                    PO.setString("OWNED_BY", cusPO.getString("SOLD_BY"));
                    PO.setDate("CONSULTANT_TIME", cusPO.getDate("CONSULTANT_TIME"));

                }
            }
        }
        return customerNO;
    }

    //
    public List<Map> isInWay(String vin, String entityCode) {
        String sql = null;
        sql = "SELECT * FROM TT_VS_SHIPPING_NOTIFY WHERE DEALER_CODE = '" + entityCode + "' AND VIN ='" + vin + "' "+ " AND VIN not in (select vin From TM_VS_STOCK where DEALER_CODE = '" + entityCode + "')";
        List<Object> queryParams = new ArrayList<Object>();
        return Base.findAll(sql.toString(), queryParams.toArray());
    }

    public List<Map> checkOwnerInfo(String vin, String entityCode) {
        StringBuffer sql = new StringBuffer("");
        sql.append(" SELECT A.* FROM (" + CommonConstants.VM_VEHICLE + ") A,(" + CommonConstants.VM_OWNER+ ") B WHERE A.DEALER_CODE=B.DEALER_CODE AND A.OWNER_NO=B.OWNER_NO  AND A.VIN='" + vin+ "' AND A.DEALER_CODE='" + entityCode+ "' AND A.CUSTOMER_NO IS NOT NULL AND LENGTH(TRIM(A.CUSTOMER_NO)) <=12");
        List<Object> queryParams = new ArrayList<Object>();
        return Base.findAll(sql.toString(), queryParams.toArray());
    }

    public List<Map> queryAuditingUser(String auditingType, String DefaultPara1, String DefaultPara2, String entityCode,
                                       Long userId) {
        StringBuffer sql = new StringBuffer("");/*70105000=202004 70101300=202005*/
        sql.append(" SELECT A.USER_ID,A.USER_CODE,A.USER_NAME,A.EMPLOYEE_NO,A.ORG_CODE,B.EMPLOYEE_NAME, "
                   + " CASE WHEN MENU_ID='70105000' THEN '经理审核' ELSE '财务审核' END AS FUNCTION_NAME"
                   + " ,CASE WHEN MENU_ID = '70101300' THEN '70302000' ELSE MENU_ID END AS FUNCTION_CODE  "
                   + " FROM TM_USER A ,TM_EMPLOYEE B,TM_USER_MENU C " + " WHERE A.DEALER_CODE = B.DEALER_CODE   " + // --A.ORG_CODE                                                                                                  // +
                   " AND A.DEALER_CODE = C.DEALER_CODE AND A.EMPLOYEE_NO = B.EMPLOYEE_NO " +
                   " AND C.USER_ID = A.USER_ID  " + // AND A.D_KEY =" + CommonConstant.D_KEY +
                   " AND A.DEALER_CODE = '" + entityCode + "'" + " AND A.USER_STATUS = "
                   + DictCodeConstants.DICT_IN_USE_START + " AND A.USER_ID != " + userId);

        if (null != auditingType && auditingType.equals(DictCodeConstants.DICT_AUDITING_TYPE_FINANCE)) {
            sql.append("  AND MENU_ID = '70101300' ");
        } else if (null != auditingType && auditingType.equals(DictCodeConstants.DICT_AUDITING_TYPE_MANAGE)) {
            if ((DefaultPara1 != null && DefaultPara1.trim().equals(DictCodeConstants.DICT_IS_YES.trim()))
                && (DefaultPara2 == null || !DefaultPara2.trim().equals(DictCodeConstants.DICT_IS_YES.trim()))) {
                sql.append("  AND MENU_ID = '70105000' ");
            } else if ((DefaultPara2 != null && DefaultPara2.trim().equals(DictCodeConstants.DICT_IS_YES.trim()))
                       && (DefaultPara1 == null || !DefaultPara1.trim().equals(DictCodeConstants.DICT_IS_YES.trim()))) {
                sql.append("  AND MENU_ID = '70101300' ");
            } else {
                sql.append("  AND (MENU_ID = '70105000' OR MENU_ID = '70101300' )");
            }
        } else {
            sql.append("  AND MENU_ID = '70105000' ");
        }
        List<Object> queryParams = new ArrayList<Object>();
        return Base.findAll(sql.toString(), queryParams.toArray());
    }

}
