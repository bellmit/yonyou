
/** 
*Copyright 2016 Yonyou Corporation Ltd. All Rights Reserved.
* This software is published under the terms of the Yonyou Software
* License version 1.0, a copy of which has been included with this
* distribution in the LICENSE.txt file.
*
* @Project Name : dms.retail
*
* @File name : SalessynthesisServiceImpl.java
*
* @Author : zhongsw
*
* @Date : 2016年10月8日
*
----------------------------------------------------------------------------------
*     Date       Who       Version     Comments
* 1. 2016年10月8日    zhongsw    1.0
*
*
*
*
----------------------------------------------------------------------------------
*/
	
package com.yonyou.dms.retail.service.ordermanage;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yonyou.dms.common.domains.DTO.monitor.OperateLogDto;
import com.yonyou.dms.common.domains.PO.basedata.PotentialCusPO;
import com.yonyou.dms.common.service.monitor.OperateLogService;
import com.yonyou.dms.commonSales.domains.PO.stockmanage.SalesOrderPO;
import com.yonyou.dms.framework.DAO.DAOUtil;
import com.yonyou.dms.framework.DAO.DAOUtilGF;
import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.framework.domain.LoginInfoDto;
import com.yonyou.dms.framework.util.bean.ApplicationContextHelper;
import com.yonyou.dms.function.common.CommonConstants;
import com.yonyou.dms.function.common.DictCodeConstants;
import com.yonyou.dms.function.exception.ServiceBizException;
import com.yonyou.dms.retail.domains.DTO.ordermanage.SalesOrderDTO;

/**
* 销售综合查询实现
* @author zhongsw
* @date 2016年10月8日
*/

@Service
public class SalessynthesisServiceImpl implements SalessynthesisService{
    
    @Autowired
    private OperateLogService operateLogService;

    
    /**查询
    * @author zhongsw
    * @date 2016年10月8日
    * @param queryParam
    * @return
    * @throws ServiceBizException
    * (non-Javadoc)
    * @see com.yonyou.dms.retail.service.ordermanage.SalessynthesisService#searchsalesSynthesis(java.util.Map)
    */
    	
    @Override
    public PageInfoDto searchsalesSynthesis(Map<String, String> queryParam) throws ServiceBizException {
    	LoginInfoDto loginInfo = ApplicationContextHelper.getBeanByType(LoginInfoDto.class);
        StringBuilder sb = new StringBuilder();
                sb.append(" SELECT DISTINCT * FROM ( SELECT AK.MEDIA_DETAIL as MEDIA_DETAIL,a.OBLIGATED_OPERATOR,a.BALANCE_CLOSE_TIME,A.REMARK,RB.BRAND_NAME AS ER_BRAND_NAME,OM.MODEL_NAME AS ER_MODEL_NAME,");
                        sb.append(" A.BUSINESS_TYPE,A.SO_NO,A.SO_STATUS,A.ORDER_SUM,SHEET_CREATE_DATE,A.SOLD_BY,em.USER_NAME,CONFIRMED_DATE,A.CONSIGNEE_CODE,A.CUSTOMER_TYPE,A.DELIVERY_MODE_ELEC,A.EC_ORDER,A.EC_ORDER_NO,"); 
                        sb.append(" CASE WHEN A.BUSINESS_TYPE="+DictCodeConstants.DICT_SO_TYPE_ALLOCATION+" THEN T.CUSTOMER_NO ELSE A.CUSTOMER_NO END AS CUSTOMER_NO," );
                        sb.append(" COALESCE(COALESCE(ak.CUSTOMER_NAME,bk.customer_name),t.customer_name) AS CUSTOMER_NAME,");
                         
                        sb.append(" a.CONTRACT_NO,A.LOSSES_PAY_OFF,A.IS_PAD_ORDER,A.IS_ELECTRICITY," );
                        sb.append(" CASE WHEN A.BUSINESS_TYPE="+DictCodeConstants.DICT_SO_TYPE_ALLOCATION+" THEN T.CONTRACT_DATE ELSE A.CONTRACT_DATE END AS CONTRACT_DATE," );
                        sb.append(" CASE WHEN A.BUSINESS_TYPE="+DictCodeConstants.DICT_SO_TYPE_ALLOCATION+" THEN T.CONTRACT_MATURITY ELSE A.CONTRACT_MATURITY END AS CONTRACT_MATURITY," );
                        sb.append(" A.VIN,br.BRAND_NAME,se.SERIES_NAME,mo.MODEL_NAME,A.DIRECTIVE_PRICE,A.ADDRESS,pa.CONFIG_NAME,co.COLOR_NAME,COALESCE(A.COLOR_CODE,B.COLOR_CODE) as COLOR_CODE,A.CONTACTOR_NAME," );

                        sb.append(" E.INVOICE_DATE AS SALES_DATE,SP.PART_NO,MN.USER_NAME AS OPERATOR,b.brand_code,B.series_code,B.model_code,B.config_code," );
                        sb.append(" M.USER_NAME AS AUDITED_BY,N.USER_NAME AS RE_AUDITED_BY,A.DELIVERING_DATE,A.IS_SPEEDINESS, " );
                        sb.append(" A.OTHER_PAY_OFF, A.ENTERING_DATE,A.ABORTING_REASON,A.OTHER_AMOUNT_OBJECT, A.OTHER_AMOUNT,INSTALLMENT_AMOUNT,INSTALLMENT_NUMBER,LOAN_ORG,PAY_MODE, ");//lim add field
                        sb.append(" A.PHONE,A.CT_CODE,A.CERTIFICATE_NO,E.INVOICE_NO,A.DEALER_CODE,A.PAY_OFF,A.IS_PAD_CONFIRM,A.OWNED_BY,a.LOCK_USER,A.LICENSE,TAB.WS_TYPE,A.INVOICE_DATE,"); 
                        sb.append(" A.VEHICLE_PRICE,A.GARNITURE_SUM,A.UPHOLSTER_SUM,A.INSURANCE_SUM,A.TAX_SUM,A.PLATE_SUM,A.OTHER_SERVICE_SUM,A.LOAN_SUM,");
                        sb.append(" A.OFFSET_AMOUNT,A.PRESENT_SUM,A.ORDER_RECEIVABLE_SUM,A.ORDER_PAYED_AMOUNT,A.ORDER_DERATED_SUM,A.ORDER_ARREARAGE_AMOUNT,A.CONSIGNED_SUM,");
                        sb.append(" A.CON_RECEIVABLE_SUM,A.CON_PAYED_AMOUNT,A.CON_ARREARAGE_AMOUNT,A.ABORTING_DATE,a.apply_no,A.OEM_DEAL_STATUS, ");
                        sb.append(" A.PERMUTED_VIN,  ");
                        sb.append(" ( SELECT COMMIT_TIME FROM TT_SO_AUDITING R WHERE R.DEALER_CODE=a.DEALER_CODE  AND R.SO_NO=A.SO_NO AND R.COMMIT_TIME IS NOT NULL ORDER BY  R.ITEM_ID limit 1 ) FIRST_COMMIT_TIME,");
                        sb.append(" ( SELECT COMMIT_TIME FROM TT_SO_AUDITING R WHERE R.DEALER_CODE=a.DEALER_CODE  AND R.SO_NO=A.SO_NO AND R.COMMIT_TIME IS NOT NULL ORDER BY  R.ITEM_ID DESC limit 1 ) LAST_COMMIT_TIME,");
                        sb.append(" CASE WHEN SUBSTR(a.SO_NO,3,6)=SUBSTR(a.CUSTOMER_NO,3,6) THEN 12781001 ELSE 12781002 END as IS_NOW_ORDER_CAR, " );
                        sb.append(" A.PERMUTED_DATE ");
                        sb.append(" FROM TT_SALES_ORDER A ");
                        sb.append(" LEFT JOIN  TM_VEHICLE TV on A.PERMUTED_VIN=TV.vin AND A.DEALER_CODE=TV.DEALER_CODE");
                        sb.append(" left  join   tm_brand   RB   on   TV.BRAND = RB.BRAND_CODE and TV.DEALER_CODE=RB.DEALER_CODE");
                        sb.append(" left  join   TM_SERIES  ES   on   TV.SERIES=ES.SERIES_CODE and RB.BRAND_CODE=ES.BRAND_CODE and RB.DEALER_CODE=ES.DEALER_CODE");
                        sb.append(" left  join   TM_MODEL   OM   on   TV.MODEL=OM.MODEL_CODE and OM.BRAND_CODE=ES.BRAND_CODE AND OM.series_code=ES.series_code and ES.DEALER_CODE=OM.DEALER_CODE");
                        sb.append(" LEFT JOIN (SELECT DEALER_CODE,CUSTOMER_NO,CUSTOMER_NAME,MEDIA_DETAIL FROM TM_POTENTIAL_CUSTOMER ) AK ");
                        sb.append(" ON AK.DEALER_CODE = A.DEALER_CODE  AND AK.CUSTOMER_NO = A.CUSTOMER_NO ");
                        sb.append(" left join (SELECT DEALER_CODE,OWNER_NO AS CUSTOMER_NO,OWNER_NAME AS CUSTOMER_NAME FROM TM_OWNER )bk ON bK.DEALER_CODE = A.DEALER_CODE  AND bK.CUSTOMER_NO = A.CUSTOMER_NO ");
                        sb.append(" LEFT JOIN (SELECT A.DEALER_CODE,A.VIN,A.SALES_DATE,b.so_no  FROM ("+CommonConstants.VM_VEHICLE +") A,TT_SALES_ORDER B " );
                        sb.append(" WHERE A.DEALER_CODE = B.DEALER_CODE AND A.VIN = B.VIN " );
                        sb.append(" AND B.BUSINESS_TYPE = "+DictCodeConstants.DICT_SO_TYPE_GENERAL );
                        sb.append(" AND (B.SO_STATUS = "+DictCodeConstants.DICT_SO_STATUS_CLOSED );
                        sb.append(" or B.SO_STATUS = "+DictCodeConstants.DICT_SO_STATUS_HAVE_OUT_STOCK );
                        sb.append(" or B.SO_STATUS = "+DictCodeConstants.DICT_SO_STATUS_HAVE_CONFIRMED +")");
                        sb.append(" ) K ON K.DEALER_CODE = A.DEALER_CODE AND K.VIN = A.VIN   and a.so_no=K.so_no  " );
                        sb.append(" LEFT JOIN TM_USER M  ON A.DEALER_CODE = M.DEALER_CODE AND A.AUDITED_BY = M.USER_ID " );
                        sb.append(" LEFT JOIN TM_USER N  ON A.DEALER_CODE = N.DEALER_CODE AND A.RE_AUDITED_BY = N.USER_ID " );
                        sb.append(" LEFT JOIN TM_USER MN  ON A.DEALER_CODE = MN.DEALER_CODE AND A.OBLIGATED_OPERATOR = MN.EMPLOYEE_NO " );
                        sb.append(" LEFT JOIN (SELECT DEALER_CODE,CUSTOMER_CODE AS CUSTOMER_NO,CUSTOMER_NAME,CONTRACT_NO,AGREEMENT_BEGIN_DATE AS CONTRACT_DATE," );
                        sb.append(" AGREEMENT_END_DATE AS CONTRACT_MATURITY FROM ("+CommonConstants.VM_PART_CUSTOMER +" ) O ) T ON A.DEALER_CODE=T.DEALER_CODE AND A.CONSIGNEE_CODE=T.CUSTOMER_NO " );
                        sb.append(" LEFT JOIN (SELECT * FROM ( "+CommonConstants.VM_VS_PRODUCT +") VP WHERE 1=1 ");
                        sb.append(" )B ON A.PRODUCT_CODE = B.PRODUCT_CODE AND A.DEALER_CODE = B.DEALER_CODE AND A.D_KEY = B.D_KEY" );
                        sb.append(" left  join   tm_brand   br   on   B.BRAND_CODE = br.BRAND_CODE and B.DEALER_CODE=br.DEALER_CODE");
                        sb.append(" left  join   TM_SERIES  se   on   B.SERIES_CODE=se.SERIES_CODE and br.BRAND_CODE=se.BRAND_CODE and br.DEALER_CODE=se.DEALER_CODE");
                        sb.append(" left  join   TM_MODEL   mo   on   B.MODEL_CODE=mo.MODEL_CODE and mo.BRAND_CODE=se.BRAND_CODE AND mo.series_code=se.series_code and se.DEALER_CODE=mo.DEALER_CODE");
                        sb.append(" left  join   tm_configuration pa   on   B.CONFIG_CODE=pa.CONFIG_CODE and pa.brand_code=mo.brand_code and pa.series_code=mo.series_code and pa.model_code=mo.model_code and mo.DEALER_CODE=pa.DEALER_CODE");
                        sb.append(" left  join   tm_color   co   on   B.COLOR_CODE = co.COLOR_CODE and B.DEALER_CODE=co.DEALER_CODE");
                        sb.append(" LEFT JOIN (SELECT * FROM TT_SO_INVOICE SI WHERE 1=1 AND SI.INVOICE_CHARGE_TYPE = " +DictCodeConstants.INVOICE_CHARGE_TYPE_BUYCAR );
                        sb.append(" ) E ON A.SO_NO = E.SO_NO AND A.DEALER_CODE = E.DEALER_CODE AND A.D_KEY = E.D_KEY" );
                        sb.append(" LEFT JOIN TT_SO_PART SP ON" );
                        sb.append(" A.SO_NO = SP.SO_NO AND A.DEALER_CODE = SP.DEALER_CODE   LEFT JOIN ( SELECT DISTINCT N.DEALER_CODE, N.SALES_ITEM_ID," );
                        sb.append(" M.WS_NO, N.SO_NO, N.VIN,K.WS_TYPE FROM TT_PO_CUS_WHOLESALE K, TT_WS_CONFIG_INFO M, TT_WS_SALES_INFO N WHERE " );
                        sb.append(" M.DEALER_CODE = N.DEALER_CODE  AND M.ITEM_ID = N.ITEM_ID AND K.DEALER_CODE = M.DEALER_CODE  AND K.WS_NO = M.WS_NO AND " );
                        sb.append(" K.WS_STATUS = 20141002 ) TAB " );
                        sb.append(" ON A.DEALER_CODE = TAB.DEALER_CODE  AND A.SO_NO = TAB.SO_NO " );
                        sb.append("  LEFT JOIN TT_CUSTOMER_VEHICLE_LIST VL  ON VL.VIN = A.PERMUTED_VIN AND VL.DEALER_CODE = A.DEALER_CODE");
                        sb.append( " LEFT JOIN TM_USER em ON em.USER_ID=A.SOLD_BY ) sw");
                        sb.append(" WHERE 1=1 ");
                
                if( queryParam.get("lossesPayOff") !=""&& queryParam.get("lossesPayOff") !=null){
    				if( queryParam.get("lossesPayOff").equals(DictCodeConstants.DICT_IS_YES)){
    					sb.append(" and so_no in  (select distinct so_no from Tt_Customer_Gathering a ");
    					sb.append( " inner join tm_pay_type b on a.pay_type_code=b.pay_type_code and a.dealer_code=b.dealer_code and Is_no_pay=12781001) ");
    				}else if( queryParam.get("lossesPayOff").equals(DictCodeConstants.DICT_IS_NO)){
    					sb.append(" and so_no not in (select distinct so_no from Tt_Customer_Gathering a ");
    					sb.append( " inner join tm_pay_type b on a.pay_type_code=b.pay_type_code and a.dealer_code=b.dealer_code and Is_no_pay=12781001) ");
    				}
    			}
                
                this.sqlToLike(sb, queryParam.get("customerNo"), "CUSTOMER_NO", "");
                this.sqlToLike(sb, queryParam.get("soNo"), "SO_NO", "");
                this.sqlToLike(sb, queryParam.get("applyNo"), "APPLY_NO", "");
                this.sqlToLike(sb, queryParam.get("soStatus"), "SO_STATUS", "");
                this.sqlToEquals(sb, queryParam.get("soldBy"), "SOLD_BY", "");
                this.sqlToLike(sb, queryParam.get("contractNo"), "CONTRACT_NO", "");
                this.sqlToLike(sb, queryParam.get("customerName"), "CUSTOMER_NAME", "");
                this.sqlToLike(sb, queryParam.get("certificateNo"), "CERTIFICATE_NO", "");
                this.sqlToLike(sb, queryParam.get("license"), "LICENSE", "");
                this.sqlToLike(sb, queryParam.get("vin"), "VIN", "");
                this.sqlToLike(sb, queryParam.get("invoiceNo"), "INVOICE_NO", "");
                this.sqlToEquals(sb, queryParam.get("brandName"), "BRAND_CODE", "");
                this.sqlToEquals(sb, queryParam.get("seriesName"), "SERIES_CODE", "");
                this.sqlToEquals(sb, queryParam.get("modelName"), "MODEL_CODE", "");
                this.sqlToEquals(sb, queryParam.get("configName"), "CONFIG_CODE", "");
                this.sqlToEquals(sb, queryParam.get("colorCode"), "COLOR_CODE", "");
                if(queryParam.get("sheetCreateDateFrom")!=null&&queryParam.get("sheetCreateDateTo")!=null){
                    	 this.sqlToDate(sb, queryParam.get("sheetCreateDateFrom").concat(" 00:00:00"), queryParam.get("sheetCreateDateTo").concat(" 23:59:59"), "SHEET_CREATE_DATE", "");
                }
               
                this.sqlToLike(sb, queryParam.get("payOff"), "PAY_OFF", "");
                this.sqlToLike(sb, queryParam.get("partNo"), "PART_NO", "");
                if(queryParam.get("contractMaturityFrom")!=null&&queryParam.get("contractMaturityTo")!=null){
                		this.sqlToDate(sb, queryParam.get("contractMaturityFrom").concat(" 00:00:00"), queryParam.get("contractMaturityTo").concat(" 23:59:59"), "CONTRACT_MATURITY", "");
    			}	
                this.sqlToLike(sb, queryParam.get("businessType"), "BUSINESS_TYPE", "");
                
                if(queryParam.get("salesDateFrom")!=null&&queryParam.get("salesDateTo")!=null){
                		this.sqlToDate(sb, queryParam.get("salesDateFrom").concat(" 00:00:00"), queryParam.get("salesDateTo").concat(" 23:59:59"), "SALES_DATE", "");
                }
                this.sqlToLike(sb, queryParam.get("obligatedOperator"), "OBLIGATED_OPERATOR", "");
                this.sqlToLike(sb, queryParam.get("wsType"), "WS_TYPE", "");
                if(queryParam.get("balanceCloseTimeFrom")!=null&&queryParam.get("balanceCloseTimeTo")!=null){
                		this.sqlToDate(sb, queryParam.get("balanceCloseTimeFrom").concat(" 00:00:00"), queryParam.get("balanceCloseTimeTo").concat(" 23:59:59"), "BALANCE_CLOSE_TIME", "");
                }
                if(queryParam.get("permutedDateFrom")!=null&&queryParam.get("permutedDateTo")!=null){
                		this.sqlToDate(sb, queryParam.get("permutedDateFrom").concat(" 00:00:00"), queryParam.get("permutedDateTo").concat(" 23:59:59"), "PERMUTED_DATE", "");
                }
                if(queryParam.get("deliveringDateFrom")!=null&&queryParam.get("deliveringDateTo")!=null){
                		this.sqlToDate(sb, queryParam.get("deliveringDateFrom").concat(" 00:00:00"), queryParam.get("deliveringDateTo").concat(" 23:59:59"), "DELIVERING_DATE", "");
                }
                if(queryParam.get("confirmedDateFrom")!=null&&queryParam.get("confirmedDateTo")!=null){
                		this.sqlToDate(sb, queryParam.get("confirmedDateFrom").concat(" 00:00:00"), queryParam.get("confirmedDateTo").concat(" 23:59:59"), "CONFIRMED_DATE", "");
               }
                if(queryParam.get("firstCommitTimeFrom")!=null&&queryParam.get("firstCommitTimeTo")!=null){
                		this.sqlToDate(sb, queryParam.get("firstCommitTimeFrom").concat(" 00:00:00"), queryParam.get("firstCommitTimeTo").concat(" 23:59:59"), "FIRST_COMMIT_TIME", "");
                }
                if(queryParam.get("lastCommitTimeFrom")!=null&&queryParam.get("lastCommitTimeTo")!=null){
                		this.sqlToDate(sb, queryParam.get("lastCommitTimeFrom").concat(" 00:00:00"), queryParam.get("lastCommitTimeTo").concat(" 23:59:59"), "LAST_COMMIT_TIME", "");
                }
                
                this.sqlToLike(sb, queryParam.get("isElectricity"), "IS_ELECTRICITY", "");
                this.sqlToLike(sb, queryParam.get("isPadOrder"), "IS_PAD_ORDER", "");
               
                this.sqlToLike(sb, queryParam.get("ecOrderNo"), "EC_ORDER_NO", "");
                this.sqlToLike(sb, queryParam.get("deliberyModeElec"), "DELIVERY_MODE_ELEC", "");
                sb.append(DAOUtilGF.getOwnedByStr("", loginInfo.getUserId(), loginInfo.getOrgCode(),  "70109000", loginInfo.getDealerCode()));
                
                List<Object> queryList = new ArrayList<Object>();
                
                PageInfoDto id = DAOUtil.pageQuery(sb.toString(), queryList);
                return id;  
                
    }
    
    /**
     * TODO 拼接sql语句模糊查询
     * 
     * @author yangjie
     * @date 2016年12月28日
     * @param param 查询条件
     * @param field 查询的字段
     * @param alias 表的别名
     * @return
     */
    public void sqlToLike(StringBuilder sb, String param, String field, String alias) {
        if (StringUtils.isNotBlank(param)) {
            sb.append("AND ");
            if (StringUtils.isNotBlank(alias)) {
                sb.append(alias + "." + field);
            } else {
                sb.append(field);
            }
            sb.append(" LIKE '%" + param + "%' ");
        }
    }

    /**
     * TODO 拼接sql语句等量查询
     * 
     * @author yangjie
     * @date 2016年12月28日
     * @param param 查询条件
     * @param field 查询的字段
     * @param alias 表的别名
     * @return
     */
    public void sqlToEquals(StringBuilder sb, String param, String field, String alias) {
        if (StringUtils.isNotBlank(param)) {
            sb.append(" AND ");
            if (StringUtils.isNotBlank(alias)) {
                sb.append(alias + "." + field);
            } else {
                sb.append(field);
            }
            sb.append(" = '" + param + "' ");
        }
    }
    
    /**
     * TODO 拼接sql语句时间查询(单个字段)
     * 
     * @author yangjie
     * @date 2016年12月28日
     * @param begin 开始时间
     * @param end 结束时间
     * @param field 查询的字段
     * @param alias 表的别名
     * @return
     */
    public void sqlToDate(StringBuilder sb, String begin, String end, String field, String alias) {
        if (StringUtils.isNotBlank(begin) && StringUtils.isNotBlank(end)) {
            sb.append("AND ");
            if (StringUtils.isNotBlank(alias)) {
                sb.append(alias + "." + field);
            } else {
                sb.append(field);
            }
            sb.append(" between '" + begin + "' AND '" + end + "' ");
        } else if (StringUtils.isNotBlank(begin) && !StringUtils.isNotBlank(end)) {
            sb.append("AND ");
            if (StringUtils.isNotBlank(alias)) {
                sb.append(alias + "." + field);
            } else {
                sb.append(field);
            }
            sb.append(" >= '" + begin + "' ");
        } else if (!StringUtils.isNotBlank(begin) && StringUtils.isNotBlank(end)) {
            sb.append("AND ");
            if (StringUtils.isNotBlank(alias)) {
                sb.append(alias + "." + field);
            } else {
                sb.append(field);
            }
            sb.append(" <= '" + end + "' ");
        }
    }
    public void sqlToTime(StringBuilder sb, String begin, String end, String field, String alias){
    	 if (StringUtils.isNotBlank(begin) && StringUtils.isNotBlank(end)) {
             sb.append("AND ");
             if (StringUtils.isNotBlank(alias)) {
                 sb.append(alias + "." + field);
             } else {
                 sb.append(field);
             }
             sb.append(" like '%" + begin + "%'");
         }
    }
    
    
    
    
    
    
    
    
    
    

    
    /**查询审核记录
    * @author zhongsw
    * @date 2016年10月9日
    * @param queryParam
    * @return
    * @throws ServiceBizException
    * (non-Javadoc)
    * @see com.yonyou.dms.retail.service.ordermanage.SalessynthesisService#searchAuditing(java.util.Map)
    */
    	
    @Override
    public PageInfoDto searchAuditing(String id) throws ServiceBizException {
        StringBuffer sbsql = new StringBuffer("SELECT tsa.ITEM_ID, tsa.SO_NO, tsa.DEALER_CODE, tsa.AUDITING_POSTIL, tsa.AUDITING_DATE, tsa.AUDITED_BY, te.USER_NAME as AUDITED_BY_NAME, tsa.SUBMIT_AUDITED_BY, te.USER_NAME as SUBMIT_AUDITED_BY_NAME, tsa.COMMIT_TIME, tsa.AUDITED_BY_IDENTITY, tsa.AUDITING_RESULT FROM tt_so_auditing tsa left join tm_user  te  on te.USER_ID = tsa.AUDITED_BY  left join tt_sales_order tso on tso.SO_NO = tsa.SO_NO where tsa.SO_NO = ? and tsa.DEALER_CODE=?");
        List<Object> params = new ArrayList<Object>();
        LoginInfoDto loginInfo = ApplicationContextHelper.getBeanByType(LoginInfoDto.class);
        params.add(id);
        params.add(loginInfo.getDealerCode());
        PageInfoDto pageInfoDto = DAOUtil.pageQuery(sbsql.toString(), params);
        return pageInfoDto;
    }


    
    /**查询客户信息(基础信息)
    * @author zhongsw
    * @date 2016年10月10日
    * @param id
    * @return
    * @throws ServiceBizException
    * (non-Javadoc)
    * @see com.yonyou.dms.retail.service.ordermanage.SalessynthesisService#queryCustomerInfoById(java.lang.Long)
    */
    	
    @SuppressWarnings("unchecked")
    @Override
    public Map<String, Object> queryCustomerInfoById(Long id) throws ServiceBizException {
        SalesOrderPO sop=SalesOrderPO.findById(id);
        StringBuffer sb=new StringBuffer("select tpc.POTENTIAL_CUSTOMER_ID,tso.SO_NO_ID,tso.SO_NO,tso.CUSTOMER_NO,tso.DEALER_CODE,tpc.POTENTIAL_CUSTOMER_NO,tpc.CUSTOMER_NAME,tpc.CUSTOMER_TYPE,tpc.GENDER,tpc.BIRTHDAY,tpc.MOBILE,tpc.PHONE,tpc.PROVINCE,tpc.CITY,tpc.DISTRICT,tpc.ADDRESS,tpc.ZIP_CODE,tpc.E_MAIL,tpc.FAX,tpc.CT_CODE,tpc.CERTIFICATE_NO,tpc.EDU_LEVEL,tpc.MARRIAGE,tpc.CHILDREN_NUMBER,tpc.FAMILY_INCOME,tpc.HOBBY,tpc.CUS_SOURCE,tpc.MEDIA_TYPE,tpc.INTENT_LEVEL,tpc.LAST_CONSULTANT,tpc.CONSULTANT,tpc.FAIL_CONSULTANT,tpc.DELAY_CONSULTANT,tpc.CONSULTANT_TIME,tpc.IS_FIRST_ARRIVE,tpc.FIRST_ARRIVE_TIME,tpc.IS_SECOND_TEH_SHOP,tpc.SECOND_ARRIVE_TIME,tpc.IS_FIRST_BUY,tpc.BUY_PURPOSE,tpc.BUY_REASON,tpc.GATHERED_SUM,tpc.ORDER_PAYED_SUM,tpc.USABLE_AMOUNT,tpc.UN_WRITEOFF_SUM,tpc.FOUND_DATE,tpc.CONTACTOR_NAME,tpc.CONTACTOR_GENDER,tpc.CONTACTOR_PHONE,tpc.CONTACTOR_MOBILE,tpc.CONTACTOR_ADDRESS,tpc.CONTACTOR_EMAIL,tpc.REMARK,tpc.AIL_INTENT_LEVEL,tpc.FAIL_MODEL,tpc.DR_CODE,tpc.DR_DESC,tpc.DR_TIME,tpc.CAMPAIGN_CODE,tpc.DCRC_SERVICE from TT_SALES_ORDER tso left join TM_POTENTIAL_CUSTOMER tpc on tso.CUSTOMER_NO=tpc.POTENTIAL_CUSTOMER_NO and tso.CUSTOMER_NAME=tpc.CUSTOMER_NAME where tso.SO_NO=? ");
        List<Object> queryParams = new ArrayList<Object>();
        queryParams.add(sop.getString("SO_NO"));
        Map<String, Object> map=DAOUtil.findFirst(sb.toString(), queryParams);
        Long soId= Long.parseLong(map.get("POTENTIAL_CUSTOMER_ID")+"");
        return PotentialCusPO.findById(soId).toMap();
    }


    
    /**查询客户信息(意向信息)
    * @author zhongsw
    * @date 2016年10月10日
    * @param id
    * @return
    * @throws ServiceBizException
    * (non-Javadoc)
    * @see com.yonyou.dms.retail.service.ordermanage.SalessynthesisService#queryCustomerInfoByParendId(java.lang.Long)
    */
    	
    @SuppressWarnings("rawtypes")
	@Override
    public List<Map> queryCustomerInfoByParendId(Long id) throws ServiceBizException {
        StringBuffer sb = new StringBuffer();
        sb.append("select tso.SO_NO_ID,tso.SO_NO,tso.DEALER_CODE,pu.POTENTIAL_CUSTOMER_ID,pu.CUSTOMER_NAME,pu.POTENTIAL_CUSTOMER_NO,pu.FOUND_DATE,\n");
        sb.append(" pu.CONSULTANT,em.USER_NAME,pu.CUSTOMER_TYPE,pu.CUS_SOURCE,pu.INTENT_LEVEL,pu.MOBILE,pu.ADDRESS,\n");
        sb.append(" ci.QUOTED_PRICE,ci.REMARK,ci.INTENT_BRAND,ci.INTENT_SERIES,ci.INTENT_MODEL,ci.INTENT_CONFIG,\n");
        sb.append(" br.BRAND_NAME,se.SERIES_NAME,pa.CONFIG_NAME,mo.MODEL_NAME\n");
        sb.append(" from TT_SALES_ORDER tso \n");
        sb.append(" left join TM_POTENTIAL_CUSTOMER pu on tso.CUSTOMER_NO=pu.POTENTIAL_CUSTOMER_NO \n");
        sb.append(" left join TM_USER em  on pu.CONSULTANT=em.USER_ID\n");
        sb.append(" left join   TT_CUSTOMER_INTENT    ci on pu.POTENTIAL_CUSTOMER_ID=ci.POTENTIAL_CUSTOMER_ID\n");
        sb.append(" left  join   TM_PACKAGE pa   on   ci.INTENT_CONFIG=pa.PACKAGE_ID\n");
        sb.append(" left  join   TM_MODEL   mo   on   ci.INTENT_MODEL=mo.MODEL_ID\n");
        sb.append(" left  join   TM_SERIES  se   on   ci.INTENT_SERIES=se.SERIES_ID\n");
        sb.append(" left  join   tm_brand   br   on   ci.INTENT_BRAND = br.BRAND_ID\n");
        sb.append(" where 1=1 and tso.SO_NO_ID=?");
        List<Object> queryParams = new ArrayList<Object>();
        queryParams.add(id);
        return DAOUtil.findAll(sb.toString(), queryParams);
    }


    
    /**销售退回明细界面
    * @author zhongsw
    * @date 2016年10月10日
    * @param id
    * @return
    * @throws ServiceBizException
    * (non-Javadoc)
    * @see com.yonyou.dms.retail.service.ordermanage.SalessynthesisService#queryorderInfoById(java.lang.Long)
    */
    	
    @SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
    public Map<String, Object> queryorderInfoById(Long id) throws ServiceBizException {
        
        StringBuffer sbsql = new StringBuffer("SELECT tso.SO_NO_ID, tso.DEALER_CODE, tso.SO_NO, tso.BUSINESS_TYPE, tso.CUSTOMER_NO AS POTENTIAL_CUSTOMER_NO, tso.CUSTOMER_NAME,tso.SHEET_CREATE_DATE, tso.SHEET_CREATED_BY, tso.CONTRACT_DATE, tso.CONTRACT_NO, tso.CONTRACT_EARNEST, tso.SO_STATUS, tso.CONSULTANT, tso.DELIVERING_DATE, tso.CONFIRMED_DATE, tso.PAY_MODE, tso.VS_STOCK_ID, tso.VEHICLE_ID, tso.PRODUCT_ID, tso.VEHICLE_PRICE as DIRECTIVE_PRICE, tso.UPHOLSTER_SUM, tso.PRESENT_SUM, tso.SERVICE_SUM, tso.OFFSET_AMOUNT, tso.ORDER_SUM, tso.ORDER_RECEIVABLE_SUM, tso.ORDER_PAYED_AMOUNT, tso.REMARK, tpc.MOBILE,tpc.ADDRESS, vp.BRAND_ID,vp.SERIES_ID,vp.MODEL_ID,vp.PRODUCT_CODE,vp.PACKAGE_ID,vp.COLOR_ID,vp.PRODUCT_NAME,tvs.CERTIFICATE_NUMBER,tv.VIN,tso.PENALTY_AMOUNT,tv.ENGINE_NO FROM tt_sales_order tso LEFT JOIN tm_potential_customer tpc on tpc.POTENTIAL_CUSTOMER_NO = tso.CUSTOMER_NO left join vw_productinfo vp on vp.PRODUCT_ID = tso.PRODUCT_ID left join tm_vs_stock tvs on tvs.VS_STOCK_ID = tso.VS_STOCK_ID left join tm_vehicle tv on tv.VEHICLE_ID = tso.VEHICLE_ID where tso.BUSINESS_TYPE=10041003 and tso.SO_NO_ID = ?");
        List<Object> params = new ArrayList<Object>();
        params.add(id);
        Map map = DAOUtil.findFirst(sbsql.toString(), params);
        return map;
    }
    
   
  
    /**订单解锁
     * @author xiahaiyang
     * @date 2017年1月13日
     * @param
     * @return
     * @throws ServiceBizException
     * (non-Javadoc)
     * @see com.yonyou.dms.retail.service.ordermanage.SalessynthesisService#queryorderInfoById(java.lang.Long)
     */
    @Override
    public void modifySalesOrderInfo(SalesOrderDTO salesOrderDto) throws ServiceBizException {
        String sono = salesOrderDto.getNoList();
        LoginInfoDto loginInfo = ApplicationContextHelper.getBeanByType(LoginInfoDto.class);
        SalesOrderPO salesOrderPo=SalesOrderPO.findByCompositeKeys(loginInfo.getDealerCode(),sono);
           // CustomerTrackingPO traPo = CustomerTrackingPO.findById(id);
        salesOrderPo.setString("LOCK_USER",null);// 锁定人
        salesOrderPo.saveIt();
        
        
    }
    
    
    /**
     * 导出查询
     * 
     * @author xiahaiyang
     * @date 2017年1月16日
     * @param queryParam
     * @return
     * @throws ServiceBizException (non-Javadoc)
     * @see com.yonyou.dms.customer.service.customerManage.PotentialCusService#queryPotentialCusforExport(java.util.Map)
     */
    @SuppressWarnings({ "unchecked", "rawtypes" })
    @Override
    public List<Map> querySalesOrderInfoExport(Map<String, String> queryParam) throws ServiceBizException {
    	LoginInfoDto loginInfo = ApplicationContextHelper.getBeanByType(LoginInfoDto.class);
        StringBuilder sb = new StringBuilder();
                sb.append(" SELECT DISTINCT * FROM ( SELECT AK.MEDIA_DETAIL as MEDIA_DETAIL,a.OBLIGATED_OPERATOR,a.BALANCE_CLOSE_TIME,A.REMARK,");
                        sb.append(" A.BUSINESS_TYPE,A.SO_NO,A.SO_STATUS,A.ORDER_SUM,SHEET_CREATE_DATE,A.SOLD_BY,em.USER_NAME,CONFIRMED_DATE,A.CONSIGNEE_CODE,A.CUSTOMER_TYPE,A.DELIVERY_MODE_ELEC,A.EC_ORDER,A.EC_ORDER_NO,"); 
                        sb.append(" CASE WHEN A.BUSINESS_TYPE="+DictCodeConstants.DICT_SO_TYPE_ALLOCATION+" THEN T.CUSTOMER_NO ELSE A.CUSTOMER_NO END AS CUSTOMER_NO," );
                        sb.append(" COALESCE(COALESCE(ak.CUSTOMER_NAME,bk.customer_name),t.customer_name) AS CUSTOMER_NAME,");
                         
                        sb.append(" a.CONTRACT_NO,A.LOSSES_PAY_OFF,A.IS_PAD_ORDER,A.IS_ELECTRICITY," );
                        sb.append(" CASE WHEN A.BUSINESS_TYPE="+DictCodeConstants.DICT_SO_TYPE_ALLOCATION+" THEN T.CONTRACT_DATE ELSE A.CONTRACT_DATE END AS CONTRACT_DATE," );
                        sb.append(" CASE WHEN A.BUSINESS_TYPE="+DictCodeConstants.DICT_SO_TYPE_ALLOCATION+" THEN T.CONTRACT_MATURITY ELSE A.CONTRACT_MATURITY END AS CONTRACT_MATURITY," );
                        sb.append(" A.VIN,br.BRAND_NAME,se.SERIES_NAME,mo.MODEL_NAME,A.DIRECTIVE_PRICE,A.ADDRESS,pa.CONFIG_NAME,co.COLOR_NAME,COALESCE(A.COLOR_CODE,B.COLOR_CODE) as COLOR_CODE,A.CONTACTOR_NAME," );

                        sb.append(" E.INVOICE_DATE AS SALES_DATE,SP.PART_NO,MN.USER_NAME AS OPERATOR," );
                        sb.append(" M.USER_NAME AS AUDITED_BY,N.USER_NAME AS RE_AUDITED_BY,A.DELIVERING_DATE,A.IS_SPEEDINESS, " );
                        sb.append(" A.OTHER_PAY_OFF, A.ENTERING_DATE,A.ABORTING_REASON,A.OTHER_AMOUNT_OBJECT, A.OTHER_AMOUNT,INSTALLMENT_AMOUNT,INSTALLMENT_NUMBER,LOAN_ORG,PAY_MODE, ");//lim add field
                        sb.append(" A.PHONE,A.CT_CODE,A.CERTIFICATE_NO,E.INVOICE_NO,A.DEALER_CODE,A.PAY_OFF,A.IS_PAD_CONFIRM,A.OWNED_BY,a.LOCK_USER,A.LICENSE,TAB.WS_TYPE,A.INVOICE_DATE,"); 
                        sb.append(" A.VEHICLE_PRICE,A.GARNITURE_SUM,A.UPHOLSTER_SUM,A.INSURANCE_SUM,A.TAX_SUM,A.PLATE_SUM,A.OTHER_SERVICE_SUM,A.LOAN_SUM,");
                        sb.append(" A.OFFSET_AMOUNT,A.PRESENT_SUM,A.ORDER_RECEIVABLE_SUM,A.ORDER_PAYED_AMOUNT,A.ORDER_DERATED_SUM,A.ORDER_ARREARAGE_AMOUNT,A.CONSIGNED_SUM,");
                        sb.append(" A.CON_RECEIVABLE_SUM,A.CON_PAYED_AMOUNT,A.CON_ARREARAGE_AMOUNT,A.ABORTING_DATE,a.apply_no,A.OEM_DEAL_STATUS, ");
                        sb.append(" A.PERMUTED_VIN,  ");
                        sb.append(" ( SELECT COMMIT_TIME FROM TT_SO_AUDITING R WHERE R.DEALER_CODE=a.DEALER_CODE  AND R.SO_NO=A.SO_NO AND R.COMMIT_TIME IS NOT NULL ORDER BY  R.ITEM_ID limit 1 ) FIRST_COMMIT_TIME,");
                        sb.append(" ( SELECT COMMIT_TIME FROM TT_SO_AUDITING R WHERE R.DEALER_CODE=a.DEALER_CODE  AND R.SO_NO=A.SO_NO AND R.COMMIT_TIME IS NOT NULL ORDER BY  R.ITEM_ID DESC limit 1 ) LAST_COMMIT_TIME,");
                        sb.append(" CASE WHEN SUBSTR(a.SO_NO,3,6)=SUBSTR(a.CUSTOMER_NO,3,6) THEN 12781001 ELSE 12781002 END as IS_NOW_ORDER_CAR, " );
                        sb.append(" A.PERMUTED_DATE ");
                        sb.append(" FROM TT_SALES_ORDER A ");
                        sb.append(" LEFT JOIN (SELECT DEALER_CODE,CUSTOMER_NO,CUSTOMER_NAME,MEDIA_DETAIL FROM TM_POTENTIAL_CUSTOMER ) AK ");
                        sb.append(" ON AK.DEALER_CODE = A.DEALER_CODE  AND AK.CUSTOMER_NO = A.CUSTOMER_NO ");
                        sb.append(" left join (SELECT DEALER_CODE,OWNER_NO AS CUSTOMER_NO,OWNER_NAME AS CUSTOMER_NAME FROM TM_OWNER )bk ON bK.DEALER_CODE = A.DEALER_CODE  AND bK.CUSTOMER_NO = A.CUSTOMER_NO ");
                        sb.append(" LEFT JOIN (SELECT A.DEALER_CODE,A.VIN,A.SALES_DATE,b.so_no  FROM ("+CommonConstants.VM_VEHICLE +") A,TT_SALES_ORDER B " );
                        sb.append(" WHERE A.DEALER_CODE = B.DEALER_CODE AND A.VIN = B.VIN " );
                        sb.append(" AND B.BUSINESS_TYPE = "+DictCodeConstants.DICT_SO_TYPE_GENERAL );
                        sb.append(" AND (B.SO_STATUS = "+DictCodeConstants.DICT_SO_STATUS_CLOSED );
                        sb.append(" or B.SO_STATUS = "+DictCodeConstants.DICT_SO_STATUS_HAVE_OUT_STOCK );
                        sb.append(" or B.SO_STATUS = "+DictCodeConstants.DICT_SO_STATUS_HAVE_CONFIRMED +")");
                        sb.append(" ) K ON K.DEALER_CODE = A.DEALER_CODE AND K.VIN = A.VIN   and a.so_no=K.so_no  " );
                        sb.append(" LEFT JOIN TM_USER M  ON A.DEALER_CODE = M.DEALER_CODE AND A.AUDITED_BY = M.USER_ID " );
                        sb.append(" LEFT JOIN TM_USER N  ON A.DEALER_CODE = N.DEALER_CODE AND A.RE_AUDITED_BY = N.USER_ID " );
                        sb.append(" LEFT JOIN TM_USER MN  ON A.DEALER_CODE = N.DEALER_CODE AND A.OBLIGATED_OPERATOR = MN.USER_ID " );
                        sb.append(" LEFT JOIN (SELECT DEALER_CODE,CUSTOMER_CODE AS CUSTOMER_NO,CUSTOMER_NAME,CONTRACT_NO,AGREEMENT_BEGIN_DATE AS CONTRACT_DATE," );
                        sb.append(" AGREEMENT_END_DATE AS CONTRACT_MATURITY FROM ("+CommonConstants.VM_PART_CUSTOMER +" ) O ) T ON A.DEALER_CODE=T.DEALER_CODE AND A.CONSIGNEE_CODE=T.CUSTOMER_NO " );
                        sb.append(" LEFT JOIN (SELECT * FROM ( "+CommonConstants.VM_VS_PRODUCT +") VP WHERE 1=1 ");
                        sb.append(" )B ON A.PRODUCT_CODE = B.PRODUCT_CODE AND A.DEALER_CODE = B.DEALER_CODE AND A.D_KEY = B.D_KEY" );
                        sb.append(" left  join   tm_brand   br   on   B.BRAND_CODE = br.BRAND_CODE and B.DEALER_CODE=br.DEALER_CODE");
                        sb.append(" left  join   TM_SERIES  se   on   B.SERIES_CODE=se.SERIES_CODE and br.BRAND_CODE=se.BRAND_CODE and br.DEALER_CODE=se.DEALER_CODE");
                        sb.append(" left  join   TM_MODEL   mo   on   B.MODEL_CODE=mo.MODEL_CODE and mo.BRAND_CODE=se.BRAND_CODE AND mo.series_code=se.series_code and se.DEALER_CODE=mo.DEALER_CODE");
                        sb.append(" left  join   tm_configuration pa   on   B.CONFIG_CODE=pa.CONFIG_CODE and pa.brand_code=mo.brand_code and pa.series_code=mo.series_code and pa.model_code=mo.model_code and mo.DEALER_CODE=pa.DEALER_CODE");
                        sb.append(" left  join   tm_color   co   on   B.COLOR_CODE = co.COLOR_CODE and B.DEALER_CODE=co.DEALER_CODE");
                        sb.append(" LEFT JOIN (SELECT * FROM TT_SO_INVOICE SI WHERE 1=1 AND SI.INVOICE_CHARGE_TYPE = " +DictCodeConstants.INVOICE_CHARGE_TYPE_BUYCAR );
                        sb.append(" ) E ON A.SO_NO = E.SO_NO AND A.DEALER_CODE = E.DEALER_CODE AND A.D_KEY = E.D_KEY" );
                        sb.append(" LEFT JOIN TT_SO_PART SP ON" );
                        sb.append(" A.SO_NO = SP.SO_NO AND A.DEALER_CODE = SP.DEALER_CODE   LEFT JOIN ( SELECT DISTINCT N.DEALER_CODE, N.SALES_ITEM_ID," );
                        sb.append(" M.WS_NO, N.SO_NO, N.VIN,K.WS_TYPE FROM TT_PO_CUS_WHOLESALE K, TT_WS_CONFIG_INFO M, TT_WS_SALES_INFO N WHERE " );
                        sb.append(" M.DEALER_CODE = N.DEALER_CODE  AND M.ITEM_ID = N.ITEM_ID AND K.DEALER_CODE = M.DEALER_CODE  AND K.WS_NO = M.WS_NO AND " );
                        sb.append(" K.WS_STATUS = 20141002 ) TAB " );
                        sb.append(" ON A.DEALER_CODE = TAB.DEALER_CODE  AND A.SO_NO = TAB.SO_NO " );
                        sb.append("  LEFT JOIN TT_CUSTOMER_VEHICLE_LIST VL  ON VL.VIN = A.PERMUTED_VIN AND VL.DEALER_CODE = A.DEALER_CODE");
                        sb.append( " LEFT JOIN TM_USER em ON em.USER_ID=A.SOLD_BY ) sw");
                        sb.append(" WHERE 1=1 ");
                
                if( queryParam.get("lossesPayOff") !=""&& queryParam.get("lossesPayOff") !=null){
    				if( queryParam.get("lossesPayOff").equals(DictCodeConstants.DICT_IS_YES)){
    					sb.append(" and so_no in  (select distinct so_no from Tt_Customer_Gathering a ");
    					sb.append( " inner join tm_pay_type b on a.pay_type_code=b.pay_type_code and a.dealer_code=b.dealer_code and Is_no_pay=12781001) ");
    				}else if( queryParam.get("lossesPayOff").equals(DictCodeConstants.DICT_IS_NO)){
    					sb.append(" and so_no not in (select distinct so_no from Tt_Customer_Gathering a ");
    					sb.append( " inner join tm_pay_type b on a.pay_type_code=b.pay_type_code and a.dealer_code=b.dealer_code and Is_no_pay=12781001) ");
    				}
    			}
                
                this.sqlToLike(sb, queryParam.get("customerNo"), "CUSTOMER_NO", "");
                this.sqlToLike(sb, queryParam.get("soNo"), "SO_NO", "");
                this.sqlToLike(sb, queryParam.get("applyNo"), "APPLY_NO", "");
                this.sqlToLike(sb, queryParam.get("soStatus"), "SO_STATUS", "");
                this.sqlToEquals(sb, queryParam.get("soldBy"), "SOLD_BY", "");
                this.sqlToLike(sb, queryParam.get("contractNo"), "CONTRACT_NO", "");
                this.sqlToLike(sb, queryParam.get("customerName"), "CUSTOMER_NAME", "");
                this.sqlToLike(sb, queryParam.get("certificateNo"), "CERTIFICATE_NO", "");
                this.sqlToLike(sb, queryParam.get("license"), "LICENSE", "");
                this.sqlToLike(sb, queryParam.get("vin"), "VIN", "");
                this.sqlToLike(sb, queryParam.get("invoiceNo"), "INVOICE_NO", "");
                this.sqlToEquals(sb, queryParam.get("brandName"), "BRAND_CODE", "");
                this.sqlToEquals(sb, queryParam.get("seriesName"), "SERIES_CODE", "");
                this.sqlToEquals(sb, queryParam.get("modelName"), "MODEL_CODE", "");
                this.sqlToEquals(sb, queryParam.get("configName"), "CONFIG_CODE", "");
                this.sqlToEquals(sb, queryParam.get("colorName"), "COLOR_CODE", "");
                if(queryParam.get("sheetCreateDateFrom")!=null&&queryParam.get("sheetCreateDateTo")!=null){
                    	 this.sqlToDate(sb, queryParam.get("sheetCreateDateFrom"), queryParam.get("sheetCreateDateTo"), "SHEET_CREATE_DATE", "");
                }
               
                this.sqlToLike(sb, queryParam.get("payOff"), "PAY_OFF", "");
                this.sqlToLike(sb, queryParam.get("partNo"), "PART_NO", "");
                if(queryParam.get("contractMaturityFrom")!=null&&queryParam.get("contractMaturityTo")!=null){
                		this.sqlToDate(sb, queryParam.get("contractMaturityFrom"), queryParam.get("contractMaturityTo").concat(" 23:59:59"), "CONTRACT_MATURITY", "");
    			}	
                this.sqlToLike(sb, queryParam.get("businessType"), "BUSINESS_TYPE", "");
                
                if(queryParam.get("salesDateFrom")!=null&&queryParam.get("salesDateTo")!=null){
                		this.sqlToDate(sb, queryParam.get("salesDateFrom"), queryParam.get("salesDateTo"), "SALES_DATE", "");
                }
                this.sqlToLike(sb, queryParam.get("obligatedOperator"), "OBLIGATED_OPERATOR", "");
                this.sqlToLike(sb, queryParam.get("wsType"), "WS_TYPE", "");
                if(queryParam.get("balanceCloseTimeFrom")!=null&&queryParam.get("balanceCloseTimeTo")!=null){
                		this.sqlToDate(sb, queryParam.get("balanceCloseTimeFrom"), queryParam.get("balanceCloseTimeTo"), "BALANCE_CLOSE_TIME", "");
                }
                if(queryParam.get("permutedDateFrom")!=null&&queryParam.get("permutedDateTo")!=null){
                		this.sqlToDate(sb, queryParam.get("permutedDateFrom"), queryParam.get("permutedDateTo"), "PERMUTED_DATE", "");
                }
                if(queryParam.get("deliveringDateFrom")!=null&&queryParam.get("deliveringDateTo")!=null){
                		this.sqlToDate(sb, queryParam.get("deliveringDateFrom"), queryParam.get("deliveringDateTo"), "DELIVERING_DATE", "");
                }
                if(queryParam.get("confirmedDateFrom")!=null&&queryParam.get("confirmedDateTo")!=null){
                		this.sqlToDate(sb, queryParam.get("confirmedDateFrom"), queryParam.get("confirmedDateTo"), "CONFIRMED_DATE", "");
               }
                if(queryParam.get("firstCommitTimeFrom")!=null&&queryParam.get("firstCommitTimeTo")!=null){
                		this.sqlToDate(sb, queryParam.get("firstCommitTimeFrom"), queryParam.get("firstCommitTimeTo"), "FIRST_COMMIT_TIME", "");
                }
                if(queryParam.get("lastCommitTimeFrom")!=null&&queryParam.get("lastCommitTimeTo")!=null){
                		this.sqlToDate(sb, queryParam.get("lastCommitTimeFrom"), queryParam.get("lastCommitTimeTo"), "LAST_COMMIT_TIME", "");
                }
                
                this.sqlToLike(sb, queryParam.get("isElectricity"), "IS_ELECTRICITY", "");
                this.sqlToLike(sb, queryParam.get("isPadOrder"), "IS_PAD_ORDER", "");
               
                this.sqlToLike(sb, queryParam.get("ecOrderNo"), "EC_ORDER_NO", "");
                this.sqlToLike(sb, queryParam.get("deliberyModeElec"), "DELIVERY_MODE_ELEC", "");
                sb.append(DAOUtilGF.getOwnedByStr("", loginInfo.getUserId(), loginInfo.getOrgCode(),  "202008", loginInfo.getDealerCode()));
        List<Map> list  = null;
        List<Object> queryList = new ArrayList<Object>();
        list = DAOUtil.findAll(sb.toString(), queryList);
       for (Map map : list) {
           if (map.get("BUSINESS_TYPE") != null && map.get("BUSINESS_TYPE") != "") {
                if (Integer.parseInt(map.get("BUSINESS_TYPE").toString()) == 13001001) {
                    map.put("BUSINESS_TYPE", "一般销售订单");
                } else if (Integer.parseInt(map.get("BUSINESS_TYPE").toString()) == 13001002) {
                    map.put("BUSINESS_TYPE", "车辆调拨");
                }else if (Integer.parseInt(map.get("BUSINESS_TYPE").toString()) == 13001003) {
                    map.put("BUSINESS_TYPE", "受托交车");
                }else if (Integer.parseInt(map.get("BUSINESS_TYPE").toString()) == 13001004) {
                    map.put("BUSINESS_TYPE", "服务销售");
                }else if (Integer.parseInt(map.get("BUSINESS_TYPE").toString()) == 13001005) {
                    map.put("BUSINESS_TYPE", "销售退回");
                }                        
            }
            
            if (map.get("SO_STATUS") != null && map.get("SO_STATUS") != "") {
                if (Integer.parseInt(map.get("SO_STATUS").toString()) == 13011010) {
                    map.put("SO_STATUS", "未提交审核");
                } else if (Integer.parseInt(map.get("SO_STATUS").toString()) == 13011015) {
                    map.put("SO_STATUS", "经理审核中");
                } else if (Integer.parseInt(map.get("SO_STATUS").toString()) == 13011020) {
                    map.put("SO_STATUS", "财务审核中");
                }else if (Integer.parseInt(map.get("SO_STATUS").toString()) == 13011025) {
                    map.put("SO_STATUS", "交车确认中");
                }else if (Integer.parseInt(map.get("SO_STATUS").toString()) == 13011030) {
                    map.put("SO_STATUS", "已交车确认");
                }else if (Integer.parseInt(map.get("SO_STATUS").toString()) == 13011035) {
                    map.put("SO_STATUS", "已完成");
                }else if (Integer.parseInt(map.get("SO_STATUS").toString()) == 13011040) {
                    map.put("SO_STATUS", "经理驳回");
                }else if (Integer.parseInt(map.get("SO_STATUS").toString()) == 13011045) {
                    map.put("SO_STATUS", "财务驳回");
                }else if (Integer.parseInt(map.get("SO_STATUS").toString()) == 13011050) {
                    map.put("SO_STATUS", "系统驳回");
                }else if (Integer.parseInt(map.get("SO_STATUS").toString()) == 13011055) {
                    map.put("SO_STATUS", "已取消");
                }else if (Integer.parseInt(map.get("SO_STATUS").toString()) == 13011060) {
                    map.put("SO_STATUS", "已退回");
                }else if (Integer.parseInt(map.get("SO_STATUS").toString()) == 13011065) {
                    map.put("SO_STATUS", "等待退回入库");
                }else if (Integer.parseInt(map.get("SO_STATUS").toString()) == 13011070) {
                    map.put("SO_STATUS", "退回已入库");
                }else if (Integer.parseInt(map.get("SO_STATUS").toString()) == 13011075) {
                    map.put("SO_STATUS", "已关单 ");
                }                      
            }
            if (map.get("IS_SPEEDINESS") != null && map.get("IS_SPEEDINESS") != "") {
                if (Integer.parseInt(map.get("IS_SPEEDINESS").toString()) == 12781001) {
                    map.put("IS_SPEEDINESS", "是");
                } else if (Integer.parseInt(map.get("IS_SPEEDINESS").toString()) == 12781002) {
                    map.put("IS_SPEEDINESS", "否");
                }
            }
            if (map.get("IS_NOW_ORDER_CAR") != null && map.get("IS_NOW_ORDER_CAR") != "") {
                if (Integer.parseInt(map.get("IS_NOW_ORDER_CAR").toString()) == 12781001) {
                    map.put("IS_NOW_ORDER_CAR", "是");
                } else if (Integer.parseInt(map.get("IS_NOW_ORDER_CAR").toString()) == 12781002) {
                    map.put("IS_NOW_ORDER_CAR", "否");
                }
            }
            if (map.get("OTHER_PAY_OFF") != null && map.get("OTHER_PAY_OFF") != "") {
                if (Integer.parseInt(map.get("OTHER_PAY_OFF").toString()) == 12781001) {
                    map.put("OTHER_PAY_OFF", "是");
                } else if (Integer.parseInt(map.get("OTHER_PAY_OFF").toString()) == 12781002) {
                    map.put("OTHER_PAY_OFF", "否");
                }
            }
            if (map.get("PAY_MODE") != null && map.get("PAY_MODE") != "") {
                if (Integer.parseInt(map.get("PAY_MODE").toString()) == 10251001) {
                    map.put("PAY_MODE", "一次付清");
                } else if (Integer.parseInt(map.get("PAY_MODE").toString()) == 10251003) {
                    map.put("PAY_MODE", "分期付款");
                }
            }
            if (map.get("IS_PAD_CONFIRM") != null && map.get("IS_PAD_CONFIRM") != "") {
                if (Integer.parseInt(map.get("IS_PAD_CONFIRM").toString()) == 12781001) {
                    map.put("IS_PAD_CONFIRM", "是");
                } else if (Integer.parseInt(map.get("IS_PAD_CONFIRM").toString()) == 12781002) {
                    map.put("IS_PAD_CONFIRM", "否");
                }
            }
            if (map.get("WS_TYPE") != null && map.get("WS_TYPE") != "") {
                if (Integer.parseInt(map.get("WS_TYPE").toString()) == 15971001) {
                    map.put("WS_TYPE", "集团销售");
                } else if (Integer.parseInt(map.get("WS_TYPE").toString()) == 15971002) {
                    map.put("WS_TYPE", "影响力人物");
                } else if (Integer.parseInt(map.get("WS_TYPE").toString()) == 15971003) {
                    map.put("WS_TYPE", "大客户团购销售");
                }
            }
            if (map.get("CUSTOMER_TYPE") != null && map.get("CUSTOMER_TYPE") != "") {
                if (Integer.parseInt(map.get("CUSTOMER_TYPE").toString()) == 10181001) {
                    map.put("CUSTOMER_TYPE", "个人");
                } else if (Integer.parseInt(map.get("CUSTOMER_TYPE").toString()) == 10181002) {
                    map.put("CUSTOMER_TYPE", "公司");
                }
            }
            if (map.get("CT_CODE") != null && map.get("CT_CODE") != "") {
                if (Integer.parseInt(map.get("CT_CODE").toString()) == 12391001) {
                    map.put("CT_CODE", "居民身份证");
                } else if (Integer.parseInt(map.get("CT_CODE").toString()) == 12391002) {
                    map.put("CT_CODE", "护照");
                } else if (Integer.parseInt(map.get("CT_CODE").toString()) == 12391003) {
                    map.put("CT_CODE", "军官证");
                } else if (Integer.parseInt(map.get("CT_CODE").toString()) == 12391004) {
                    map.put("CT_CODE", "士兵证");
                } else if (Integer.parseInt(map.get("CT_CODE").toString()) == 12391005) {
                    map.put("CT_CODE", "警官证");
                } else if (Integer.parseInt(map.get("CT_CODE").toString()) == 12391006) {
                    map.put("CT_CODE", "其他");
                } else if (Integer.parseInt(map.get("CT_CODE").toString()) == 12391007) {
                    map.put("CT_CODE", "机构代码");
                }
            }
       }
            OperateLogDto operateLogDto=new OperateLogDto();
            operateLogDto.setOperateContent("销售综合查询导出");
            operateLogDto.setOperateType(DictCodeConstants.LOG_CUSTOMER_MANAGEMENT);
            operateLogService.writeOperateLog(operateLogDto);
       
         
       
    return list;
        
    }
    
    
    /**
     * 根据id查询销售单
     * 
     * @author xukl
     * @date 2016年9月18日
     * @param id
     * @return
     * @throws ServiceBizException (non-Javadoc)
     * @see com.yonyou.dms.retail.service.ordermanage.SalesOrderService#getSalesOrderById(java.lang.Long)
     */
	@SuppressWarnings("rawtypes")
	@Override
	public Map getSalesOrderById(String id) throws ServiceBizException {
		 StringBuilder sb = new StringBuilder();
	        sb.append(" SELECT DISTINCT * FROM ( SELECT AK.MEDIA_DETAIL as MEDIA_DETAIL,a.OBLIGATED_OPERATOR,a.BALANCE_CLOSE_TIME,A.REMARK,");
	                sb.append(" A.BUSINESS_TYPE,A.SO_NO,A.SO_STATUS,A.ORDER_SUM,SHEET_CREATE_DATE,A.SOLD_BY,em.USER_NAME,CONFIRMED_DATE,A.CONSIGNEE_CODE,A.CUSTOMER_TYPE,A.DELIVERY_MODE_ELEC,A.EC_ORDER,A.EC_ORDER_NO," );
	                sb.append(" CASE WHEN A.BUSINESS_TYPE="+DictCodeConstants.DICT_SO_TYPE_ALLOCATION+" THEN T.CUSTOMER_NO ELSE A.CUSTOMER_NO END AS CUSTOMER_NO," );
	                sb.append(" COALESCE(COALESCE(ak.CUSTOMER_NAME,bk.customer_name),t.customer_name) AS CUSTOMER_NAME,");
	                sb.append(" a.CONTRACT_NO,br.BRAND_NAME,se.SERIES_NAME,mo.MODEL_NAME,pa.CONFIG_NAME,co.COLOR_NAME," );
	                sb.append(" CASE WHEN A.BUSINESS_TYPE="+DictCodeConstants.DICT_SO_TYPE_ALLOCATION+" THEN T.CONTRACT_DATE ELSE A.CONTRACT_DATE END AS CONTRACT_DATE," );
	                sb.append(" CASE WHEN A.BUSINESS_TYPE="+DictCodeConstants.DICT_SO_TYPE_ALLOCATION+" THEN T.CONTRACT_MATURITY ELSE A.CONTRACT_MATURITY END AS CONTRACT_MATURITY," );
	                sb.append(" A.VIN,B.BRAND_CODE,B.SERIES_CODE,B.MODEL_CODE,A.DIRECTIVE_PRICE,A.ADDRESS,B.CONFIG_CODE,COALESCE(A.COLOR_CODE,B.COLOR_CODE) as COLOR_CODE,A.CONTACTOR_NAME," );
	                sb.append(" E.INVOICE_DATE AS SALES_DATE,MN.USER_NAME AS OPERATOR," );
	                sb.append(" M.USER_NAME AS AUDITED_BY,N.USER_NAME AS RE_AUDITED_BY,A.DELIVERING_DATE,A.IS_SPEEDINESS, " );
	                sb.append(" A.OTHER_PAY_OFF, A.ENTERING_DATE,A.ABORTING_REASON,A.OTHER_AMOUNT_OBJECT, A.OTHER_AMOUNT,INSTALLMENT_AMOUNT,INSTALLMENT_NUMBER,LOAN_ORG,PAY_MODE, ");//lim add field
	                sb.append(" A.PHONE,A.CT_CODE,A.CERTIFICATE_NO,E.INVOICE_NO,A.DEALER_CODE,A.PAY_OFF,A.IS_PAD_CONFIRM,A.OWNED_BY,a.LOCK_USER,A.LICENSE,TAB.WS_TYPE,A.INVOICE_DATE," );
	                sb.append(" A.VEHICLE_PRICE,A.GARNITURE_SUM,A.UPHOLSTER_SUM,A.INSURANCE_SUM,A.TAX_SUM,A.PLATE_SUM,A.OTHER_SERVICE_SUM,A.LOAN_SUM,");
	                sb.append(" A.OFFSET_AMOUNT,A.PRESENT_SUM,A.ORDER_RECEIVABLE_SUM,A.ORDER_PAYED_AMOUNT,A.ORDER_DERATED_SUM,A.ORDER_ARREARAGE_AMOUNT,A.CONSIGNED_SUM,");
	                sb.append(" A.CON_RECEIVABLE_SUM,A.CON_PAYED_AMOUNT,A.CON_ARREARAGE_AMOUNT,A.ABORTING_DATE,a.apply_no,A.OEM_DEAL_STATUS, ");
	                sb.append(" A.PERMUTED_VIN, "); 
	                sb.append(" ( SELECT COMMIT_TIME FROM TT_SO_AUDITING R WHERE R.DEALER_CODE=a.DEALER_CODE  AND R.SO_NO=A.SO_NO AND R.COMMIT_TIME IS NOT NULL ORDER BY  R.ITEM_ID limit 1 ) FIRST_COMMIT_TIME,");
	                sb.append(" ( SELECT COMMIT_TIME FROM TT_SO_AUDITING R WHERE R.DEALER_CODE=a.DEALER_CODE  AND R.SO_NO=A.SO_NO AND R.COMMIT_TIME IS NOT NULL ORDER BY  R.ITEM_ID DESC limit 1 ) LAST_COMMIT_TIME,");
	                sb.append(" CASE WHEN SUBSTR(a.SO_NO,3,6)=SUBSTR(a.CUSTOMER_NO,3,6) THEN 12781001 ELSE 12781002 END as IS_NOW_ORDER_CAR, " );
	                sb.append(" A.PERMUTED_DATE ");
	                sb.append(" FROM TT_SALES_ORDER A ");
	                sb.append(" LEFT JOIN (SELECT DEALER_CODE,CUSTOMER_NO,CUSTOMER_NAME,MEDIA_DETAIL FROM TM_POTENTIAL_CUSTOMER ) AK ");
	                sb.append(" ON AK.DEALER_CODE = A.DEALER_CODE  AND AK.CUSTOMER_NO = A.CUSTOMER_NO ");
	                sb.append(" left join (SELECT DEALER_CODE,OWNER_NO AS CUSTOMER_NO,OWNER_NAME AS CUSTOMER_NAME FROM TM_OWNER )bk ON bK.DEALER_CODE = A.DEALER_CODE  AND bK.CUSTOMER_NO = A.CUSTOMER_NO ");
	                sb.append(" LEFT JOIN (SELECT A.DEALER_CODE,A.VIN,A.SALES_DATE,b.so_no  FROM ("+CommonConstants.VM_VEHICLE +") A,TT_SALES_ORDER B " );
	                sb.append(" WHERE A.DEALER_CODE = B.DEALER_CODE AND A.VIN = B.VIN " );
	                sb.append(" AND B.BUSINESS_TYPE = "+DictCodeConstants.DICT_SO_TYPE_GENERAL );
	                sb.append(" AND (B.SO_STATUS = "+DictCodeConstants.DICT_SO_STATUS_CLOSED );
	                sb.append(" or B.SO_STATUS = "+DictCodeConstants.DICT_SO_STATUS_HAVE_OUT_STOCK );
	                sb.append(" or B.SO_STATUS = "+DictCodeConstants.DICT_SO_STATUS_HAVE_CONFIRMED +")");
	                sb.append(" ) K ON K.DEALER_CODE = A.DEALER_CODE AND K.VIN = A.VIN   and a.so_no=K.so_no  " );
	                sb.append(" LEFT JOIN TM_USER M  ON A.DEALER_CODE = M.DEALER_CODE AND A.AUDITED_BY = M.USER_ID " );
	                sb.append(" LEFT JOIN TM_USER N  ON A.DEALER_CODE = N.DEALER_CODE AND A.RE_AUDITED_BY = N.USER_ID " );
	                sb.append(" LEFT JOIN TM_USER MN  ON A.DEALER_CODE = N.DEALER_CODE AND A.OBLIGATED_OPERATOR = MN.USER_ID " );
	                sb.append(" LEFT JOIN (SELECT DEALER_CODE,CUSTOMER_CODE AS CUSTOMER_NO,CUSTOMER_NAME,CONTRACT_NO,AGREEMENT_BEGIN_DATE AS CONTRACT_DATE," );
	                sb.append(" AGREEMENT_END_DATE AS CONTRACT_MATURITY FROM ("+CommonConstants.VM_PART_CUSTOMER +" ) O ) T ON A.DEALER_CODE=T.DEALER_CODE AND A.CONSIGNEE_CODE=T.CUSTOMER_NO " );
	                sb.append(" LEFT JOIN (SELECT * FROM ( "+CommonConstants.VM_VS_PRODUCT +") VP WHERE 1=1 ");
	                sb.append(" )B ON A.PRODUCT_CODE = B.PRODUCT_CODE AND A.DEALER_CODE = B.DEALER_CODE AND A.D_KEY = B.D_KEY");
	                sb.append(" left  join   tm_configuration pa   on   B.CONFIG_CODE=pa.CONFIG_CODE and B.DEALER_CODE=pa.DEALER_CODE");
                    sb.append(" left  join   TM_MODEL   mo   on   B.MODEL_CODE=mo.MODEL_CODE and B.DEALER_CODE=mo.DEALER_CODE");
                    sb.append(" left  join   TM_SERIES  se   on   B.SERIES_CODE=se.SERIES_CODE and B.DEALER_CODE=se.DEALER_CODE");
                    sb.append(" left  join   tm_brand   br   on   B.BRAND_CODE = br.BRAND_CODE and B.DEALER_CODE=br.DEALER_CODE");
                    sb.append(" left  join   tm_color   co   on   B.COLOR_CODE = co.COLOR_CODE and B.DEALER_CODE=co.DEALER_CODE");
	                sb.append(" LEFT JOIN (SELECT * FROM TT_SO_INVOICE SI WHERE 1=1 AND SI.INVOICE_CHARGE_TYPE = " +DictCodeConstants.INVOICE_CHARGE_TYPE_BUYCAR );
	                sb.append(" ) E ON A.SO_NO = E.SO_NO AND A.DEALER_CODE = E.DEALER_CODE AND A.D_KEY = E.D_KEY" );
	                sb.append(" LEFT JOIN TT_SO_PART SP ON" );
	                sb.append(" A.SO_NO = SP.SO_NO AND A.DEALER_CODE = SP.DEALER_CODE   LEFT JOIN ( SELECT DISTINCT N.DEALER_CODE, N.SALES_ITEM_ID," );
	                sb.append(" M.WS_NO, N.SO_NO, N.VIN,K.WS_TYPE FROM TT_PO_CUS_WHOLESALE K, TT_WS_CONFIG_INFO M, TT_WS_SALES_INFO N WHERE " );
	                sb.append(" M.DEALER_CODE = N.DEALER_CODE  AND M.ITEM_ID = N.ITEM_ID AND K.DEALER_CODE = M.DEALER_CODE  AND K.WS_NO = M.WS_NO AND " );
	                sb.append(" K.WS_STATUS = 20141002 ) TAB " );
	                sb.append(" ON A.DEALER_CODE = TAB.DEALER_CODE  AND A.SO_NO = TAB.SO_NO " );
	                sb.append("  LEFT JOIN TT_CUSTOMER_VEHICLE_LIST VL  ON VL.VIN = A.PERMUTED_VIN AND VL.DEALER_CODE = A.DEALER_CODE");
	                sb.append(" LEFT JOIN TM_USER em ON em.USER_ID=A.SOLD_BY ");
	                sb.append(" WHERE A.SO_NO=? )SW");
	        List<Object> params = new ArrayList<Object>();
	        params.add(id);
	        Map map = DAOUtil.findFirst(sb.toString(), params);
	        return map;
	}
	
	 /**
     * 根据id查询保险详细
     * 
     * @author xhy
     * @date 2017年3月13日
     * @param id
     * @return
     * @throws ServiceBizException (non-Javadoc)
     * @see com.yonyou.dms.retail.service.ordermanage.SalesOrderService#getInsuranceById(java.lang.Long)
     */
	@SuppressWarnings("rawtypes")
	@Override
	public Map getInsuranceById(String soNo) throws ServiceBizException {
		 StringBuilder sb = new StringBuilder();
		 
		 sb.append(" select distinct a.*,"); 
		 sb.append( " TB.BRAND_NAME,TS.SERIES_NAME,TM.MODEL_NAME,d.INSURATION_NAME "); 
		 sb.append(" from TM_INS_PROPOSAL a ,"); 
		 sb.append(" TT_SALES_ORDER b ,TM_VEHICLE c , TM_INSURANCE d ,TM_BRAND TB, TM_SERIES TS,TM_MODEL TM "); 
		 sb.append( " where a.VIN=b.VIN and a.VIN=c.VIN and a.INSURATION_CODE=d.INSURATION_CODE "); 
		 sb.append( " and c.brand=TB.BRAND_CODE AND C.SERIES=TS.SERIES_CODE AND C.MODEL=TM.MODEL_CODE "); 
		 sb.append(  "and a.SO_NO like '%"+soNo+"%'"); 
					
			/* List<Object> params = new ArrayList<Object>();
		        params.add(soNo);*/
			 Map map = DAOUtil.findFirst(sb.toString(), null);
		     return map;
	}
	
	  /**
     * 通过id查询潜客信息
     * 
     * @author zhanshiwei
     * @date 2016年9月9日
     * @param id
     * @return
     * @throws ServiceBizException (non-Javadoc)
     * @see com.yonyou.dms.customer.service.customerManage.PotentialCusService#queryPotentialCusInfo(java.lang.Long)
     */

    @SuppressWarnings("rawtypes")
	@Override
    public List<Map> queryPotentialCusInfoByid(String id) throws ServiceBizException {
        StringBuffer sb = new StringBuffer("select distinct cus.*,CASE WHEN  cus.CONTACTOR_MOBILE IS NULL THEN  cus.CONTACTOR_PHONE ELSE  cus.CONTACTOR_MOBILE END AS CONTACTOR_MOBILEANDPHONE,ci.INTENT_SERIES,ve.LICENSE,ve.MILEAGE,se.SERIES_NAME,mo.MODEL_NAME,"
        		+ "tl.CONTACTOR_NAME,tl.POSITION_NAME as POSITION,tl.E_MAIL as MAIL,tl.PHONE,tl.MOBILE,ti.BUDGET_AMOUNT,ti.DECISION_MAKER,ti.PURCHASE_TYPE,ti.BILL_TYPE,ti.IS_BUDGET_ENOUGH ,so.DELIVERING_DATE,so.CONFIRMED_DATE   from\n")
                .append("TM_POTENTIAL_CUSTOMER cus\n")
                .append("left join TT_CUSTOMER_INTENT ti on cus.CUSTOMER_NO=ti.CUSTOMER_NO and cus.INTENT_ID=ti.INTENT_ID\n")
                .append(" left join   tt_customer_intent_detail ci on cus.INTENT_ID=ci.INTENT_ID and IS_MAIN_MODEL=12781001\n")
                .append("left join TT_PO_CUS_LINKMAN tl on cus.CUSTOMER_NO=tl.CUSTOMER_NO and cus.DEALER_CODE=tl.DEALER_CODE\n")
                .append(" left  join   TM_SERIES  se   on   ci.INTENT_SERIES=se.SERIES_CODE and cus.DEALER_CODE=se.DEALER_CODE\n")
                .append(" left  join   TM_MODEL  mo   on   ci.INTENT_MODEL=mo.MODEL_CODE and cus.DEALER_CODE=mo.DEALER_CODE\n")
                .append(" left  join   TM_VEHICLE  ve   on   cus.CUSTOMER_NO=ve.CUSTOMER_NO and cus.DEALER_CODE=ve.DEALER_CODE \n")
                .append(" left  join   TT_SALES_ORDER  so   on   cus.CUSTOMER_NO=so.CUSTOMER_NO and cus.DEALER_CODE=so.DEALER_CODE \n")
                .append("where cus.CUSTOMER_NO=? ");
        System.out.println(sb.toString());
        System.err.println(sb.toString());
        List<Object> queryList = new ArrayList<Object>();
        queryList.add(id);
        List<Map> result = DAOUtil.findAll(sb.toString(), queryList);
        return result;
        /*LoginInfoDto loginInfo = ApplicationContextHelper.getBeanByType(LoginInfoDto.class);
        System.out.println(loginInfo.getDealerCode());
        System.out.println(id);
        return PotentialCusPO.findByCompositeKeys(loginInfo.getDealerCode(),id);*/
    }
    
    /**
     * 查询打印权限
     * 
     * @author xhy
     * @date 2017年3月22日
     * @param id
     * @return
     * @throws ServiceBizException (non-Javadoc)
     * @see com.yonyou.dms.customer.service.customerManage.PotentialCusService#queryMenuAction()
     */
	@SuppressWarnings("rawtypes")
	@Override
	public List<Map> queryMenuAction() throws ServiceBizException {
		LoginInfoDto loginInfo = ApplicationContextHelper.getBeanByType(LoginInfoDto.class);
		Long userId=loginInfo.getUserId();
		StringBuffer sql=new StringBuffer("");
		sql.append("select um.* from tm_user_menu um left join tm_user_menu_action uma on um.user_menu_id=uma.user_menu_id where um.user_id="+userId+" and uma.menu_curing_id=16778202 ");
		List<Map> list=DAOUtil.findAll(sql.toString(), null);
		
		return list;
	}



    
    

}
