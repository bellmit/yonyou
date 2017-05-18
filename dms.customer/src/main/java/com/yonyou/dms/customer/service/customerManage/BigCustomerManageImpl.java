
/** 
*Copyright 2017 Yonyou Corporation Ltd. All Rights Reserved.
* This software is published under the terms of the Yonyou Software
* License version 1.0, a copy of which has been included with this
* distribution in the LICENSE.txt file.
*
* @Project Name : dms.customer
*
* @File name : BigCustomerManageImpl.java
*
* @Author : Administrator
*
* @Date : 2017年1月12日
*
----------------------------------------------------------------------------------
*     Date       Who       Version     Comments
* 1. 2017年1月12日1    Administrator    1.0
*
*
*
*
----------------------------------------------------------------------------------
*/
	
package com.yonyou.dms.customer.service.customerManage;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.javalite.activejdbc.Base;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yonyou.dms.common.domains.DTO.basedata.BigCustomerCarDTO;
import com.yonyou.dms.common.domains.DTO.basedata.BigCustomerDTO;
import com.yonyou.dms.common.domains.DTO.monitor.OperateLogDto;
import com.yonyou.dms.common.domains.PO.basedata.BigCustomerAmountPO;
import com.yonyou.dms.common.domains.PO.basedata.EmployeePo;
import com.yonyou.dms.common.domains.PO.basedata.PoCusWholesalePO;
import com.yonyou.dms.common.domains.PO.basedata.TtWsConfigInfoPO;
import com.yonyou.dms.common.service.monitor.OperateLogService;
import com.yonyou.dms.framework.DAO.DAOUtil;
import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.framework.domain.LoginInfoDto;
import com.yonyou.dms.framework.service.CommonNoService;
import com.yonyou.dms.framework.service.FileStoreService;
import com.yonyou.dms.framework.util.FrameworkUtil;
import com.yonyou.dms.framework.util.bean.ApplicationContextHelper;
import com.yonyou.dms.function.common.CommonConstants;
import com.yonyou.dms.function.common.DictCodeConstants;
import com.yonyou.dms.function.exception.ServiceBizException;
import com.yonyou.dms.function.utils.common.DateUtil;
import com.yonyou.dms.function.utils.common.StringUtils;
import com.yonyou.dms.gacfca.DSO0401Coud;



/**
* TODO description
* @author Administrator
* @date 2017年1月12日
*/
@Service
public class BigCustomerManageImpl implements BigCustomerManageService{
    @Autowired
    private CommonNoService commonNoService;
    @Autowired
    private OperateLogService operateLogService;
    @Autowired
    FileStoreService fileStoreService;
    @Autowired
    DSO0401Coud DSO0401;
    @Override
    public PageInfoDto queryBigCustomer(Map<String, String> queryParam) throws ServiceBizException {
        String number = "";
        String cuName = "";
        String cuType = "";
        String cuStatus = "";
        String cuCustomerStatus = "";
        String cuSoldBy = "";
        String cuSoldBy2 = "";
        String cuIsBigCustomer = "";
        StringBuffer sql = new StringBuffer();
        List<Object> queryList = new ArrayList<Object>();
        if (!StringUtils.isNullOrEmpty(queryParam.get("customerNo")) )
        {
            number =" AND A.CUSTOMER_NO LIKE ?";
            queryList.add("%" + queryParam.get("customerNo") + "%");
        }
        else
        {
            number = " and 1 = 1";
        }
        if (!StringUtils.isNullOrEmpty(queryParam.get("customerName")))
        {
            cuName =" AND A.CUSTOMER_NAME LIKE ?";
            queryList.add("%" + queryParam.get("customerName") + "%");
        }
        else
        {
            cuName = " and 1 = 1";
        }
        if (!StringUtils.isNullOrEmpty(queryParam.get("customerType")))
        {
            cuType = " and CUSTOMER_TYPE  =  " + queryParam.get("customerType") + "  ";
        }
        else
        {
            cuType = " and 1 = 1";
        }
        if (!StringUtils.isNullOrEmpty(queryParam.get("intentLevel")))
        {
            cuStatus = " and INTENT_LEVEL  = " + queryParam.get("intentLevel") + " ";
        }
        else
        {
            cuStatus = " and 1 = 1";
        }
//        if (customerStatus!=null && "fortestdrive".equals(customerStatus)) {
//            cuCustomerStatus = " and exists ( select 1 from tt_drive_plan p where p.PLAN_STATUS = 33311002 and " +
//                    " p.DEALER_CODE=a.DEALER_CODE and p.CUSTOMER_CODE=a.customer_no)";
//        }else if (customerStatus != null && customerStatus.length() > 0)
//        {
//            if(customerStatus.equals(DictDataConstant.DICT_CUSTOMER_STATUS_LATENCY)){
//                cuCustomerStatus  = " and INTENT_LEVEL  != " + DictDataConstant.DICT_INTENT_LEVEL_D + " ";
//            }
//        }
//        else
//        {
          cuCustomerStatus = " and 1 = 1";
//        }

        if (!StringUtils.isNullOrEmpty(queryParam.get("soldBy")))
        {
            cuSoldBy = " and SOLD_BY  = " + queryParam.get("soldBy") + " ";
        }
//        else
//        {
//            if(functionCode != null && functionCode.length() > 0)
//            cuSoldBy2=Utility.getOwnedByStr(conn, "A", userid, orgCode, functionCode, entityCode);
//        }
        
        //销售顾问可见客户属性只能是普通客户,大客户经理可见自己名下的大客户/普通客户
        //销售顾问可见客户属性只能是普通客户,大客户经理可见自己名下的大客户/普通客户
        StringBuilder sqlS = new StringBuilder("select * from tm_user_CTRL where DEALER_CODE= ? AND USER_ID= ? AND CTRL_CODE= '80900000' ");
        List<Object> bigparams = new ArrayList<Object>();
        bigparams.add(FrameworkUtil.getLoginInfo().getDealerCode());
        bigparams.add(FrameworkUtil.getLoginInfo().getUserId());
        
        List<Map> biglist = Base.findAll(sqlS.toString(), bigparams.toArray());
        if (biglist!=null && biglist.size()>0){
            cuIsBigCustomer = " and 1 = 1";
        } else {
            cuIsBigCustomer = " AND A.IS_BIG_CUSTOMER=" + DictCodeConstants.IS_NOT;
        }

        sql.append(" SELECT A.* "//, 0.0 AS INTENT_ID"配合前台的公用界面,增加一个没用的字段,以便与下面的queryCustomerAndIntent返回的字段一样
                //00
                +"  ,c.INTENT_BRAND,c.INTENT_SERIES,c.INTENT_MODEL,c.INTENT_CONFIG,c.INTENT_COLOR,d.DEPOSIT_AMOUNT,c.IS_MAIN_MODEL, 'CUSTOMER' AS TAG   "
                //11
                + " from TM_POTENTIAL_CUSTOMER A  " 
                +" left join  TT_CUSTOMER_INTENT_DETAIL c on c.DEALER_CODE = A.DEALER_CODE and c.INTENT_ID=A.INTENT_ID   "
                +" left join  TT_ES_CUSTOMER_ORDER d on d.EC_ORDER_NO = A.EC_ORDER_NO  and a.DEALER_CODE=d.DEALER_CODE and a.customer_no=d.customer_no "
                + " WHERE a.DEALER_CODE = '" + FrameworkUtil.getLoginInfo().getDealerCode()
                + "' and A.INTENT_LEVEL!="+DictCodeConstants.DICT_INTENT_LEVEL_F+" and  A.INTENT_LEVEL!="+DictCodeConstants.DICT_INTENT_LEVEL_FO
                + " AND a.D_KEY = 0 "  + cuName + number + cuType
                + cuStatus
                + cuCustomerStatus + cuSoldBy+cuSoldBy2+cuIsBigCustomer);
        if (!StringUtils.isNullOrEmpty(queryParam.get("isMainModel"))) {
            sql.append("  and c.IS_MAIN_MODEL="+queryParam.get("isMainModel")+"   ");
            
        }       
          
            if (!StringUtils.isNullOrEmpty(queryParam.get("contactorPhone")) )
            {
                number =" AND A.CONTACTOR_PHONE LIKE ?";
                queryList.add("%" + queryParam.get("contactorPhone") + "%");
            }  if (!StringUtils.isNullOrEmpty(queryParam.get("contactorMobile")) )
            {
                number =" AND A.CONTACTOR_MOBILE LIKE ?";
                queryList.add("%" + queryParam.get("contactorMobile") + "%");
            }

      
        PageInfoDto id = DAOUtil.pageQuery(sql.toString(), queryList);
        System.out.println(id);
        return id;
    }
    @Override
    public PageInfoDto findAllProduct(Map<String, String> queryParam,String wsAppType) throws ServiceBizException {
       
        StringBuilder sb = new StringBuilder();

        sb.append(" SELECT A.*,PACKAGE.WS_GROUP_CODE,co.CONFIG_NAME,cl.COLOR_NAME " +
                " FROM  ("+CommonConstants.VM_VS_PRODUCT+")  A LEFT JOIN TM_ACT_FLEET_GROUP_PACKAGE PACKAGE ON A.dealer_code = PACKAGE.dealer_code AND A.CONFIG_CODE = PACKAGE.CONFIG_CODE " +
                " inner join TM_BIG_CUSTOMER_DEFINITION B on B.brand_code=A.brand_code and B.SERIES_CODE=A.SERIES_CODE and B.dealer_code=A.dealer_code "+
                " left join tm_configuration co on co.brand_code=A.brand_code and co.SERIES_CODE=A.SERIES_CODE and co.dealer_code=A.dealer_code and co.model_code=a.model_code and co.config_code=a.config_code "+
                " left join tm_color cl on cl.color_code=A.color_code and cl.dealer_code=A.dealer_code "+
                " WHERE  1=1 and B.IS_VALID=10011001 and B.IS_DELETE=0 and A.OEM_TAG="+DictCodeConstants.STATUS_IS_YES+" and B.PS_TYPE='"+wsAppType+"'" +
                     " AND A.dealer_code = '"+FrameworkUtil.getLoginInfo().getDealerCode() +"'" +
                     " AND A.D_KEY = "  + CommonConstants.D_KEY+
                     " AND A.IS_VALID  = "  + DictCodeConstants.STATUS_IS_YES);
        List<Object> queryList = new ArrayList<Object>();
        if (!StringUtils.isNullOrEmpty(queryParam.get("brandCode"))){
            sb.append(" AND A.BRAND_CODE=?");
            queryList.add(queryParam.get("brandCode"));
        }
        if (!StringUtils.isNullOrEmpty(queryParam.get("seriesCode"))){
            sb.append(" AND A.SERIES_CODE=?");
            queryList.add(queryParam.get("seriesCode"));
        }
        if (!StringUtils.isNullOrEmpty(queryParam.get("modelCode"))){
            sb.append(" AND A.MODEL_CODE=?");
            queryList.add(queryParam.get("modelCode"));
        }
        if (!StringUtils.isNullOrEmpty(queryParam.get("configCode"))){
            sb.append(" AND A.CONFIG_CODE=?");
            queryList.add(queryParam.get("configCode"));
        }
        if (!StringUtils.isNullOrEmpty(queryParam.get("productCode"))){
            sb.append(" AND A.PRODUCT_CODE=?");
            queryList.add(queryParam.get("productCode"));
        }
     if(!StringUtils.isNullOrEmpty(queryParam.get("min"))){
         sb.append(" AND A.DIRECTIVE_PRICE >=" + queryParam.get("min") + " ");
     }
     if(!StringUtils.isNullOrEmpty(queryParam.get("max"))){
         sb.append(" AND A.DIRECTIVE_PRICE <=" + queryParam.get("max") + " ");
     }
        
        return DAOUtil.pageQuery(sb.toString(), queryList);
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
        if (StringUtils.isNullOrEmpty(param)) {
            sb.append(" AND ");
            if (StringUtils.isNullOrEmpty(alias)) {
                sb.append(alias + "." + field);
            } else {
                sb.append(field);
            }
            sb.append(" = '" + param + "' ");
        }
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
        if (StringUtils.isNullOrEmpty(param)) {
            sb.append("AND ");
            if (StringUtils.isNullOrEmpty(alias)) {
                sb.append(alias + "." + field);
            } else {
                sb.append(field);
            }
            sb.append(" LIKE '%" + param + "%' ");
        }
    }
    
    public String addBiGCusInfo(BigCustomerDTO bigCustomerDto, String WsNo) throws ServiceBizException {
        
        //校验购买数量
        System.out.println("//校验购买数量");
        System.out.println(FrameworkUtil.getLoginInfo().getEmployeeNo());
        int sum=0;
        Long intentId = commonNoService.getId("ID");
        
        if(bigCustomerDto.getInvent().size()>0 && bigCustomerDto.getInvent() !=null){
            for(BigCustomerCarDTO bigCustomerCardto : bigCustomerDto.getInvent()){
                sum= sum+bigCustomerCardto.getPurchaseCount();    
            } 
        }
        List<Object> cus = new ArrayList<Object>();
        cus.add(FrameworkUtil.getLoginInfo().getDealerCode());
        cus.add(bigCustomerDto.getWsAppType());
        cus.add(bigCustomerDto.getCustomerKind());
        List<BigCustomerAmountPO> bigCustomerAmountpo = BigCustomerAmountPO.findBySQL(" SELECT  NUMBER FROM tm_big_customer_AMOUNT where IS_VALID=10011001 AND IS_DELETE=0 and dealer_code=? and ps_type=? and employee_type=?", cus.toArray());
       if (bigCustomerAmountpo.size()==0){
           throw new ServiceBizException("客户类型对应的最低申请数量为0，不允许创建报备单。");
       }
        
       if (bigCustomerAmountpo.size() >0){
      int big=(int)bigCustomerAmountpo.get(0).get("NUMBER");
          if (sum-big<0){
              throw new ServiceBizException("大客户报备时，需达到最低申请数量，才能提交。"); 
          }
       }
        if (!StringUtils.isNullOrEmpty(bigCustomerDto.getDlrPrincipalPhone())) {
            EmployeePo po =EmployeePo.findByCompositeKeys(FrameworkUtil.getLoginInfo().getDealerCode(),FrameworkUtil.getLoginInfo().getEmployeeNo()); 
            String a ="-";
            System.out.println("11111111111111111111111111111");
            System.out.println(FrameworkUtil.getLoginInfo().getEmployeeNo());
            if (bigCustomerDto.getDlrPrincipalPhone().length()==11 && a.indexOf(bigCustomerDto.getDlrPrincipalPhone())<0){
                po.setString("Mobile",bigCustomerDto.getDlrPrincipalPhone());
                
                }else
                {                       
                    po.setString("Phone",bigCustomerDto.getDlrPrincipalPhone());
                 
                }
            po.setString("UPDATED_BY", FrameworkUtil.getLoginInfo().getUserId().toString());
            po.saveIt();
        }
       PoCusWholesalePO poCuspo = new PoCusWholesalePO();
       poCuspo.setString("WS_NO", WsNo);
       poCuspo.setInteger("customer_kind", bigCustomerDto.getCustomerKind());
       poCuspo.setString("customer_Name", bigCustomerDto.getCustomerName());
       poCuspo.setInteger("Wsthree_Type", bigCustomerDto.getWsthreeType());
       poCuspo.setInteger("Ws_Status", bigCustomerDto.getWsStatus());
       poCuspo.setInteger("Ws_App_Type", bigCustomerDto.getWsAppType());
       poCuspo.setString("Customer_No", bigCustomerDto.getCustomerNo());
       poCuspo.setString("project_Remark", bigCustomerDto.getProjectRemark());
       poCuspo.setString("Company_Name", bigCustomerDto.getCompanyName());
       poCuspo.setString("Contactor_Name", bigCustomerDto.getContactorName());
       poCuspo.setString("Position_Name", bigCustomerDto.getPositionName());
       poCuspo.setString("Phone", bigCustomerDto.getPhone());
       poCuspo.setString("Mobile", bigCustomerDto.getMobile());
       poCuspo.setString("fax", bigCustomerDto.getFax());
       poCuspo.setString("Dlr_Principal", bigCustomerDto.getDlrPrincipal());
       poCuspo.setString("Dlr_Principal_Phone", bigCustomerDto.getDlrPrincipalPhone());
       poCuspo.setDate("Estimate_Apply_Time", bigCustomerDto.getEstimateApplyTime());
       poCuspo.setString("Ws_Auditor", bigCustomerDto.getWsAuditor());
       poCuspo.setString("Ws_Auditing_Remark", bigCustomerDto.getWsAuditingRemark());
       poCuspo.setDate("Auditing_Date", bigCustomerDto.getAuditingDate());
       poCuspo.setDate("Submit_Time", bigCustomerDto.getSubmitTime());
       poCuspo.saveIt();
       
      
       if(bigCustomerDto.getInvent().size()>0 && bigCustomerDto.getInvent() !=null){
           for(BigCustomerCarDTO bigCustomerCardto : bigCustomerDto.getInvent()){
               this.setConfig(bigCustomerCardto,intentId,WsNo).saveIt();
           } 
       }
   
 
       System.out.println("测试附件");
       System.out.println(bigCustomerDto.getDmsFileIds());
       System.out.println(bigCustomerDto.getDmsFileIds1());
      fileStoreService.addFileUploadInfo(bigCustomerDto.getDmsFileIds(), WsNo, DictCodeConstants.FILE_TYPE_CAR_INFO_INSPECTION);
     System.out.println("1111111111111111111111111111111111111");
       return WsNo;
    }
    
    
    
 public String modifyBiGCusInfo(BigCustomerDTO bigCustomerDto,String wsNo) throws ServiceBizException {
        
        //校验购买数量
        int sum=0;
        Long intentId = commonNoService.getId("ID");
        if(bigCustomerDto.getInvent().size()>0 && bigCustomerDto.getInvent() !=null){
            for(BigCustomerCarDTO bigCustomerCardto : bigCustomerDto.getInvent()){
                sum= sum+bigCustomerCardto.getPurchaseCount();    
            } 
        }
        List<Object> cus = new ArrayList<Object>();
        cus.add(FrameworkUtil.getLoginInfo().getDealerCode());
        cus.add(bigCustomerDto.getWsAppType());
        cus.add(bigCustomerDto.getCustomerKind());
        List<BigCustomerAmountPO> bigCustomerAmountpo = BigCustomerAmountPO.findBySQL(" SELECT  NUMBER FROM tm_big_customer_AMOUNT where IS_VALID=10011001 AND IS_DELETE=0 and dealer_code=? and ps_type=? and employee_type=?", cus.toArray());
       if (bigCustomerAmountpo.size()==0){
           throw new ServiceBizException("客户类型对应的最低申请数量为0，不允许创建报备单。");
       }
        
       if (bigCustomerAmountpo.size() >0){
      int big=(int)bigCustomerAmountpo.get(0).get("NUMBER");
          if (sum-big<0){
              throw new ServiceBizException("大客户报备时，需达到最低申请数量，才能提交。"); 
          }
       }
        if (!StringUtils.isNullOrEmpty(bigCustomerDto.getDlrPrincipalPhone())) {
            EmployeePo po =EmployeePo.findByCompositeKeys(FrameworkUtil.getLoginInfo().getDealerCode(),FrameworkUtil.getLoginInfo().getEmployeeNo()); 
            String a ="-";
            if (bigCustomerDto.getDlrPrincipalPhone().length()==11 && a.indexOf(bigCustomerDto.getDlrPrincipalPhone())<0){
                po.setString("Mobile",bigCustomerDto.getDlrPrincipalPhone());
                }else
                {                       
                    po.setString("Phone",bigCustomerDto.getDlrPrincipalPhone());
                }
            po.saveIt();
            
        }
       PoCusWholesalePO poCuspo = PoCusWholesalePO.findByCompositeKeys(FrameworkUtil.getLoginInfo().getDealerCode(),wsNo);      
       poCuspo.setInteger("customer_kind", bigCustomerDto.getCustomerKind());
       poCuspo.setString("customer_Name", bigCustomerDto.getCustomerName());
       poCuspo.setInteger("Wsthree_Type", bigCustomerDto.getWsthreeType());
       poCuspo.setInteger("Ws_Status", bigCustomerDto.getWsStatus());
       poCuspo.setInteger("Ws_App_Type", bigCustomerDto.getWsAppType());
       poCuspo.setString("Customer_No", bigCustomerDto.getCustomerNo());
       poCuspo.setString("project_Remark", bigCustomerDto.getProjectRemark());
       poCuspo.setString("Company_Name", bigCustomerDto.getCompanyName());
       poCuspo.setString("Contactor_Name", bigCustomerDto.getContactorName());
       poCuspo.setString("Position_Name", bigCustomerDto.getPositionName());
       poCuspo.setString("Phone", bigCustomerDto.getPhone());
       poCuspo.setString("Mobile", bigCustomerDto.getMobile());
       poCuspo.setString("fax", bigCustomerDto.getFax());
       poCuspo.setString("Dlr_Principal", bigCustomerDto.getDlrPrincipal());
       poCuspo.setString("Dlr_Principal_Phone", bigCustomerDto.getDlrPrincipalPhone());
       poCuspo.setDate("Estimate_Apply_Time", bigCustomerDto.getEstimateApplyTime());
       poCuspo.setString("Ws_Auditor", bigCustomerDto.getWsAuditor());
       poCuspo.setString("Ws_Auditing_Remark", bigCustomerDto.getWsAuditingRemark());
       poCuspo.setDate("Auditing_Date", bigCustomerDto.getAuditingDate());
       poCuspo.setDate("Submit_Time", bigCustomerDto.getSubmitTime());
       poCuspo.saveIt();
       
       TtWsConfigInfoPO.delete(" WS_NO= ? AND DEALER_CODE= ?", wsNo,FrameworkUtil.getLoginInfo().getDealerCode());
       if(bigCustomerDto.getInvent().size()>0 && bigCustomerDto.getInvent() !=null){
           for(BigCustomerCarDTO bigCustomerCardto : bigCustomerDto.getInvent()){
               this.setConfig(bigCustomerCardto,intentId,wsNo).saveIt();
           } 
       }
       System.out.println("测试附件");
       System.out.println(bigCustomerDto.getDmsFileIds());
      fileStoreService.updateFileUploadInfo(bigCustomerDto.getDmsFileIds(), wsNo, DictCodeConstants.FILE_TYPE_CAR_INFO_INSPECTION);
      //fileStoreService.updateFileUploadInfo(bigCustomerDto.getDmsFileIds(), wsNo, DictCodeConstants.FILE_TYPE_CAR_INFO_INSPECTION);
      System.out.println("1111111111111111111111111111111111111");
        return wsNo;
    }
    @Override
    public Map<String, Object> employeSaveBeforeEvent(Map<String, String> queryParam) throws ServiceBizException {
      //同一政策类别、同一公司的报备单，您确定继续保存
        Map<String, Object> result = new HashMap<String, Object>();
        List<Object> cus1 = new ArrayList<Object>();
        cus1.add(FrameworkUtil.getLoginInfo().getDealerCode());
        System.out.println(queryParam.get("companyName"));
        System.out.println("wwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwww");
        cus1.add(queryParam.get("companyName"));
        if(!StringUtils.isNullOrEmpty(queryParam.get("wsAppType"))){
            cus1.add(queryParam.get("wsAppType"));     
        }else{
            cus1.add(queryParam.get("wsAppType1")); 
        }   
        StringBuilder isDefaultManagerSql = new StringBuilder(" SELECT  t.CUSTOMER_NO,t.dealer_code  " +
                "   from tt_po_cus_wholesale t  where t.dealer_code =? " +
                " and t.COMPANY_NAME=? and t.WS_APP_TYPE=? and (WS_STATUS=15981002 or WS_STATUS=15981003) " +
                " and not exists (SELECT 1 FROM TT_WHOLESALE_REPAY F WHERE T.WS_NO = F.WS_NO and T.dealer_code=f.dealer_code )");
      
        List<Map> isDefaultManagerResult = DAOUtil.findAll(isDefaultManagerSql.toString(), cus1);
        if(isDefaultManagerResult!=null&&isDefaultManagerResult.size()>0){
            result.put("isDefaultManagerResult", isDefaultManagerResult);   
        }
        return result;
    }
    public TtWsConfigInfoPO setConfig(BigCustomerCarDTO Dto,Long intentId,String WsNo){       
        TtWsConfigInfoPO wsconfiginfopo=new TtWsConfigInfoPO();
         //  wsconfiginfopo.setLong("ITEM_ID", intentId);
           wsconfiginfopo.setString("WS_NO",WsNo); 
           wsconfiginfopo.setString("BRAND_CODE",Dto.getBrandCode());
           wsconfiginfopo.setString("MODEL_CODE",Dto.getModelCode());
           wsconfiginfopo.setString("SERIES_CODE",Dto.getSeriesCode());
           wsconfiginfopo.setString("CONFIG_CODE",Dto.getConfigCode());
           wsconfiginfopo.setString("COLOR_CODE",Dto.getColorCode());
           wsconfiginfopo.setInteger("PURCHASE_COUNT",Dto.getPurchaseCount());
           return wsconfiginfopo;
       }
    public PageInfoDto queryBigCustomerWs(Map<String, String> queryParam) throws ServiceBizException {
        StringBuilder sql = new StringBuilder();
        sql.append(
                   "SELECT 12781002 AS IS_SELECTED,\n" +
                   "       A.WS_NO,\n " + 
                   "       A.dealer_code,\n " + 
                   "       A.WS_TYPE,\n" + 
                   "       A.WS_STATUS,\n" + 
                   "       A.WS_APP_TYPE,\n" + 
                   "       A.CUSTOMER_NO,\n" + 
                   "       A.COMPANY_NAME,\n" + 
                   "       A.ESTIMATE_APPLY_TIME,\n" + 
                   "       A.LARGE_CUSTOMER_NO,\n" + 
                   "       A.FAX_TIME,\n" + 
                   "       A.FAX,\n" + 
                   "       A.IS_FAX_RECEIVED,\n" + 
                   "       A.CUSTOMER_NAME,\n" + 
                   "       A.CUSTOMER_CHARACTER,\n" + 
                   "       A.CONTACTOR_NAME,\n" + 
                   "       A.POSITION_NAME,\n" + 
                   "       A.PHONE,\n" + 
                   "       A.MOBILE,\n" + 
                   "       A.IS_SECOND_REPORT,\n" + 
                   "       A.WS_AUDITOR,\n" + 
                   "       A.WS_AUDITING_REMARK,\n" + 
                   "       A.AUDITING_DATE,\n" + 
                   "       A.FIRST_SUBMIT_TIME,\n" + 
                   "       A.SUBMIT_TIME,\n" + 
                   "       A.VER,\n" + 
                   "       A.WSTHREE_TYPE,\n" + 
                   "       A.OWNED_BY,\n" + 
                   "       A.CONFIGURE_REMARK,\n" + 
                   "       A.PROJECT_REMARK,\n" + 
                   "       A.WS_CONTENT,\n" + 
                   "       A.DLR_PRINCIPAL,\n" + 
                   "       a.POS_REMARK,\n" + 
                   "       A.DLR_PRINCIPAL_PHONE,\n" + 
                   "       A.POS_NAME,\n" + 
                   "       A.POS_LINKMAN,\n" + 
                   "       A.POS_LINKMAN_PHONE,\n" + 
                   "       A.ORGAN_TYPE_CODE,\n" + 
                   "       A.CUSTOMER_KIND,\n" + 
                   "       B.ZIP_CODE,\n" + 
                   "       A.FILE_APPLY_ID,\n" + 
                   "       A.FILE_APPLY_URL,\n" + 
                   "       A.FILE_CONTRACT_ID,\n" + 
                   "       A.FILE_CONTRACT_URL,\n" + 
                   "       A.CONTRACT_FILE_AID,\n" + 
                   "       A.CONTRACT_FILE_AURL,\n" + 
                   "       A.CONTRACT_FILE_BID,\n" + 
                   "       A.CONTRACT_FILE_BURL,\n" + 
                   "       B.ADDRESS\n" + 
                   "---111\n"
                   +"FROM TT_PO_CUS_WHOLESALE A inner join TM_POTENTIAL_CUSTOMER B on A.CUSTOMER_NO=B.CUSTOMER_NO " +
                    " AND A.dealer_code=B.dealer_code WHERE A.dealer_code='"+FrameworkUtil.getLoginInfo().getDealerCode()+"' AND A.D_KEY=0 ");
           //查询条件
        List<Object> queryList = new ArrayList<Object>();
        if (!StringUtils.isNullOrEmpty(queryParam.get("wsNo")))
        {
            sql.append(" AND A.WS_NO LIKE ?");
            queryList.add("%" + queryParam.get("wsNo") + "%");
        }
        if (!StringUtils.isNullOrEmpty(queryParam.get("customerNo")))
        {
            sql.append(" AND A.CUSTOMER_NO LIKE ?");
            queryList.add("%" + queryParam.get("customerNo") + "%");
        }
        if (!StringUtils.isNullOrEmpty(queryParam.get("customerName")))
        {
            sql.append(" AND A.CUSTOMER_NAME LIKE ?");
            queryList.add("%" + queryParam.get("customerName") + "%");
        }
        if (!StringUtils.isNullOrEmpty(queryParam.get("phone")))
        {
            sql.append(" AND A.PHONE LIKE ?");
            queryList.add("%" + queryParam.get("phone") + "%");
        }
        if (!StringUtils.isNullOrEmpty(queryParam.get("mobile")))
        {
            sql.append(" AND A.MOBILE LIKE ?");
            queryList.add("%" + queryParam.get("mobile") + "%");
        }
        if (!StringUtils.isNullOrEmpty(queryParam.get("dlrPrincipal")))
        {
            sql.append(" AND A.DLR_PRINCIPAL ?");
            queryList.add("%" + queryParam.get("dlrPrincipal") + "%");
        }
           if(!StringUtils.isNullOrEmpty(queryParam.get("wsStatus"))){
               sql.append(" AND A.WS_STATUS="+queryParam.get("wsStatus")+" ");
           }
           if (!StringUtils.isNullOrEmpty(queryParam.get("beginSubmitTime"))) {
               sql.append(" and c.SUBMIT_TIME>= ?");
               queryList.add(DateUtil.parseDefaultDate(queryParam.get("beginSubmitTime")));
           }
           if (!StringUtils.isNullOrEmpty(queryParam.get("endSubmitTime"))) {
               sql.append(" and c.SUBMIT_TIME<?");
               queryList.add(DateUtil.addOneDay(queryParam.get("endSubmitTime")));
           }
           if (!StringUtils.isNullOrEmpty(queryParam.get("beginAuditingDate"))) {
               sql.append(" and c.AUDITING_DATE>= ?");
               queryList.add(DateUtil.parseDefaultDate(queryParam.get("beginAuditingDate")));
           }
           if (!StringUtils.isNullOrEmpty(queryParam.get("endAuditingDate"))) {
               sql.append(" and c.AUDITING_DATE<?");
               queryList.add(DateUtil.addOneDay(queryParam.get("endAuditingDate")));
           }
          
        return  DAOUtil.pageQuery(sql.toString(), queryList);
    }
    
    
    
    /**
    * @author LiGaoqi
    * @date 2017年4月6日
    * @param id
    * @return
    * @throws ServiceBizException
    * (non-Javadoc)
    * @see com.yonyou.dms.customer.service.customerManage.BigCustomerManageService#CheckStatus(java.lang.String)
    */
    	
    @Override
    public String CheckStatus(String id) throws ServiceBizException {
                String msg="0";
                StringBuffer sb = new StringBuffer();
                sb.append("select A.DEALER_CODE,COUNT(1) AS STATUS_ONE from TT_PO_CUS_WHOLESALE A inner join TM_POTENTIAL_CUSTOMER B on A.CUSTOMER_NO=B.CUSTOMER_NO AND A.dealer_code=B.dealer_code WHERE A.WS_STATUS=15981004 AND A.dealer_code='"+FrameworkUtil.getLoginInfo().getDealerCode()+"' AND A.D_KEY=0 ");
                List<Object> queryList = new ArrayList<Object>();
                Date date=new Date();//取时间
                Calendar calendar = new GregorianCalendar();
                calendar.setTime(date);
                calendar.add(calendar.DATE,-6);//把日期往后增加一天.整数往后推,负数往前移动
                date=calendar.getTime(); //这个时间就是日期往后推一天的结果 
                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
                String dateString = formatter.format(date);
                    sb.append(" and A.AUDITING_DATE>= ?");
                    queryList.add(DateUtil.parseDefaultDate(dateString));

                List<Map> result = DAOUtil.findAll(sb.toString(), queryList);
                if(result!=null&&result.size()>0){
                    
                    msg=result.get(0).get("STATUS_ONE").toString();
                }
         
       
        return msg;
    }
    
    
    
    /**
    * @author LiGaoqi
    * @date 2017年4月6日
    * @param id
    * @return
    * @throws ServiceBizException
    * (non-Javadoc)
    * @see com.yonyou.dms.customer.service.customerManage.BigCustomerManageService#CheckStatus1(java.lang.String)
    */
    	
    @Override
    public String CheckStatus1(String id) throws ServiceBizException {

        String msg="0";
        StringBuffer sb = new StringBuffer();
        sb.append("select A.DEALER_CODE,COUNT(1) AS STATUS_ONE from TT_PO_CUS_WHOLESALE A inner join TM_POTENTIAL_CUSTOMER B on A.CUSTOMER_NO=B.CUSTOMER_NO AND A.dealer_code=B.dealer_code WHERE A.WS_STATUS=15981005 AND A.dealer_code='"+FrameworkUtil.getLoginInfo().getDealerCode()+"' AND A.D_KEY=0 ");
        List<Object> queryList = new ArrayList<Object>();
        Date date=new Date();//取时间
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(date);
        calendar.add(calendar.DATE,-6);//把日期往后增加一天.整数往后推,负数往前移动
        date=calendar.getTime(); //这个时间就是日期往后推一天的结果 
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        String dateString = formatter.format(date);
            sb.append(" and A.AUDITING_DATE>= ?");
            queryList.add(DateUtil.parseDefaultDate(dateString));

        List<Map> result = DAOUtil.findAll(sb.toString(), queryList);
        if(result!=null&&result.size()>0){
           
            msg=result.get(0).get("STATUS_ONE").toString();
        }
 

return msg;

    }
    public PageInfoDto queryBigCustomerWsCar(String id) throws ServiceBizException {
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT  A.ITEM_ID,A.WS_NO,A.DEALER_CODE,A.BRAND_CODE,A.SERIES_CODE,A.MODEL_CODE,A.CONFIG_CODE,A.COLOR_CODE,A.PURCHASE_COUNT,B.CONFIG_NAME,A.IS_RES_APPLY FROM TT_WS_CONFIG_INFO A  ");
        sql.append(" left join  ("+CommonConstants.VM_CONFIGURATION+")   B   ON A.CONFIG_CODE= B.CONFIG_CODE  AND A.DEALER_CODE= B.DEALER_CODE  AND A.BRAND_CODE = B.BRAND_CODE AND A.SERIES_CODE = B.SERIES_CODE AND A.MODEL_CODE = B.MODEL_CODE "); 
        sql.append(" WHERE A.DEALER_CODE='"+FrameworkUtil.getLoginInfo().getDealerCode()+"' AND A.D_KEY=0 ");
        List<Object> queryList = new ArrayList<Object>();
        if(!StringUtils.isNullOrEmpty(id)){
            sql.append(" AND A.WS_NO='"+id+"'");
        }        
        return  DAOUtil.pageQuery(sql.toString(), queryList);
    }
    public PageInfoDto queryBigCustomerWsCarbyWsNo(Map<String, String> queryParam,String wsNo) throws ServiceBizException {
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT  A.ITEM_ID,A.WS_NO,A.DEALER_CODE,A.BRAND_CODE,A.SERIES_CODE,A.MODEL_CODE,A.CONFIG_CODE,A.COLOR_CODE,A.PURCHASE_COUNT,B.CONFIG_NAME,A.IS_RES_APPLY FROM TT_WS_CONFIG_INFO A  ");
        sql.append(" left join  ("+CommonConstants.VM_CONFIGURATION+")   B   ON A.CONFIG_CODE= B.CONFIG_CODE  AND A.DEALER_CODE= B.DEALER_CODE  AND A.BRAND_CODE = B.BRAND_CODE AND A.SERIES_CODE = B.SERIES_CODE AND A.MODEL_CODE = B.MODEL_CODE "); 
        sql.append(" WHERE A.DEALER_CODE='"+FrameworkUtil.getLoginInfo().getDealerCode()+"' AND A.D_KEY=0 ");
        List<Object> queryList = new ArrayList<Object>();
        sql.append(" AND A.WS_NO='"+wsNo+"'");        
        return  DAOUtil.pageQuery(sql.toString(), queryList);
    }
    public List<Map> queryOwnerCusBywsNo(String wsNo)
            throws ServiceBizException {
        StringBuilder sql = new StringBuilder();
        sql.append(
                   "SELECT 12781002 AS IS_SELECTED,\n" +
                   "       A.WS_NO,\n " + 
                   "       A.dealer_code,\n " + 
                   "       A.WS_TYPE,\n" + 
                   "       A.WS_STATUS,\n" + 
                   "       A.WS_APP_TYPE,\n" + 
                   "       A.CUSTOMER_NO,\n" + 
                   "       A.COMPANY_NAME,\n" + 
                   "       A.ESTIMATE_APPLY_TIME,\n" + 
                   "       A.LARGE_CUSTOMER_NO,\n" + 
                   "       A.FAX_TIME,\n" + 
                   "       A.FAX,\n" + 
                   "       A.IS_FAX_RECEIVED,\n" + 
                   "       A.CUSTOMER_NAME,\n" + 
                   "       A.CUSTOMER_CHARACTER,\n" + 
                   "       A.CONTACTOR_NAME,\n" + 
                   "       A.POSITION_NAME,\n" + 
                   "       A.PHONE,\n" + 
                   "       A.MOBILE,\n" + 
                   "       A.IS_SECOND_REPORT,\n" + 
                   "       A.WS_AUDITOR,\n" + 
                   "       A.WS_AUDITING_REMARK,\n" + 
                   "       A.AUDITING_DATE,\n" + 
                   "       A.FIRST_SUBMIT_TIME,\n" + 
                   "       A.SUBMIT_TIME,\n" + 
                   "       A.VER,\n" + 
                   "       A.WSTHREE_TYPE,\n" + 
                   "       A.OWNED_BY,\n" + 
                   "       A.CONFIGURE_REMARK,\n" + 
                   "       A.PROJECT_REMARK,\n" + 
                   "       A.WS_CONTENT,\n" + 
                   "       A.DLR_PRINCIPAL,\n" + 
                   "       a.POS_REMARK,\n" + 
                   "       A.DLR_PRINCIPAL_PHONE,\n" + 
                   "       A.POS_NAME,\n" + 
                   "       A.POS_LINKMAN,\n" + 
                   "       A.POS_LINKMAN_PHONE,\n" + 
                   "       A.ORGAN_TYPE_CODE,\n" + 
                   "       A.CUSTOMER_KIND,\n" + 
                   "       B.ZIP_CODE,\n" + 
                   "       A.FILE_APPLY_ID,\n" + 
                   "       A.FILE_APPLY_URL,\n" + 
                   "       A.FILE_CONTRACT_ID,\n" + 
                   "       A.FILE_CONTRACT_URL,\n" + 
                   "       A.CONTRACT_FILE_AID,\n" + 
                   "       A.CONTRACT_FILE_AURL,\n" + 
                   "       A.CONTRACT_FILE_BID,\n" + 
                   "       A.CONTRACT_FILE_BURL,\n" + 
                   "       B.ADDRESS\n" + 
                   "---333\n"
                   +"FROM TT_PO_CUS_WHOLESALE A inner join TM_POTENTIAL_CUSTOMER B on A.CUSTOMER_NO=B.CUSTOMER_NO " +
                    " AND A.dealer_code=B.dealer_code WHERE A.dealer_code='"+FrameworkUtil.getLoginInfo().getDealerCode()+"' AND A.D_KEY=0 and A.ws_no='"+wsNo+"'");      
        return Base.findAll(sql.toString());
    }
    public PageInfoDto queryBigCustomerWsCarbyWsNoHis(Map<String, String> queryParam,String wsNo) throws ServiceBizException {
        StringBuilder sql = new StringBuilder();
        sql.append(" SELECT dealer_code,WS_NO,AUDITING_DATE,WS_AUDITING_REMARK,WS_STATUS FROM tm_big_customer_HISTORY where " +
                " dealer_code='"+FrameworkUtil.getLoginInfo().getDealerCode()+"' and WS_NO='"+wsNo+"'" 
                    );
        List<Object> queryList = new ArrayList<Object>();       
        return  DAOUtil.pageQuery(sql.toString(), queryList);
    }
    public List<Map> queryRetainCustrackforExport(Map<String, String> queryParam) throws ServiceBizException {
        StringBuilder sql = new StringBuilder();
        sql.append(
                   "SELECT 12781002 AS IS_SELECTED,\n" +
                   "       A.WS_NO,\n " + 
                   "       A.dealer_code,\n " + 
                   "       A.WS_TYPE,\n" + 
                   "       A.WS_STATUS,\n" + 
                   "       A.WS_APP_TYPE,\n" + 
                   "       A.CUSTOMER_NO,\n" + 
                   "       A.COMPANY_NAME,\n" + 
                   "       A.ESTIMATE_APPLY_TIME,\n" + 
                   "       A.LARGE_CUSTOMER_NO,\n" + 
                   "       A.FAX_TIME,\n" + 
                   "       A.FAX,\n" + 
                   "       A.IS_FAX_RECEIVED,\n" + 
                   "       A.CUSTOMER_NAME,\n" + 
                   "       A.CUSTOMER_CHARACTER,\n" + 
                   "       A.CONTACTOR_NAME,\n" + 
                   "       A.POSITION_NAME,\n" + 
                   "       A.PHONE,\n" + 
                   "       A.MOBILE,\n" + 
                   "       A.IS_SECOND_REPORT,\n" + 
                   "       A.WS_AUDITOR,\n" + 
                   "       A.WS_AUDITING_REMARK,\n" + 
                   "       A.AUDITING_DATE,\n" + 
                   "       A.FIRST_SUBMIT_TIME,\n" + 
                   "       A.SUBMIT_TIME,\n" + 
                   "       A.VER,\n" + 
                   "       A.WSTHREE_TYPE,\n" + 
                   "       A.OWNED_BY,\n" + 
                   "       A.CONFIGURE_REMARK,\n" + 
                   "       A.PROJECT_REMARK,\n" + 
                   "       A.WS_CONTENT,\n" + 
                   "       A.DLR_PRINCIPAL,\n" + 
                   "       a.POS_REMARK,\n" + 
                   "       A.DLR_PRINCIPAL_PHONE,\n" + 
                   "       A.POS_NAME,\n" + 
                   "       A.POS_LINKMAN,\n" + 
                   "       A.POS_LINKMAN_PHONE,\n" + 
                   "       A.ORGAN_TYPE_CODE,\n" + 
                   "       A.CUSTOMER_KIND,\n" + 
                   "       B.ZIP_CODE,\n" + 
                   "       A.FILE_APPLY_ID,\n" + 
                   "       A.FILE_APPLY_URL,\n" + 
                   "       A.FILE_CONTRACT_ID,\n" + 
                   "       A.FILE_CONTRACT_URL,\n" + 
                   "       A.CONTRACT_FILE_AID,\n" + 
                   "       A.CONTRACT_FILE_AURL,\n" + 
                   "       A.CONTRACT_FILE_BID,\n" + 
                   "       A.CONTRACT_FILE_BURL,\n" + 
                   "       B.ADDRESS\n" + 
                   "---111\n"
                   +"FROM TT_PO_CUS_WHOLESALE A inner join TM_POTENTIAL_CUSTOMER B on A.CUSTOMER_NO=B.CUSTOMER_NO " +
                    " AND A.dealer_code=B.dealer_code WHERE A.dealer_code='"+FrameworkUtil.getLoginInfo().getDealerCode()+"' AND A.D_KEY=0 ");
           //查询条件
        List<Object> queryList = new ArrayList<Object>();
        if (!StringUtils.isNullOrEmpty(queryParam.get("wsNo")))
        {
            sql.append(" AND A.WS_NO LIKE ?");
            queryList.add("%" + queryParam.get("wsNo") + "%");
        }
        if (!StringUtils.isNullOrEmpty(queryParam.get("customerNo")))
        {
            sql.append(" AND A.CUSTOMER_NO LIKE ?");
            queryList.add("%" + queryParam.get("customerNo") + "%");
        }
        if (!StringUtils.isNullOrEmpty(queryParam.get("customerName")))
        {
            sql.append(" AND A.CUSTOMER_NAME LIKE ?");
            queryList.add("%" + queryParam.get("customerName") + "%");
        }
        if (!StringUtils.isNullOrEmpty(queryParam.get("phone")))
        {
            sql.append(" AND A.PHONE LIKE ?");
            queryList.add("%" + queryParam.get("phone") + "%");
        }
        if (!StringUtils.isNullOrEmpty(queryParam.get("mobile")))
        {
            sql.append(" AND A.MOBILE LIKE ?");
            queryList.add("%" + queryParam.get("mobile") + "%");
        }
        if (!StringUtils.isNullOrEmpty(queryParam.get("dlrPrincipal")))
        {
            sql.append(" AND A.DLR_PRINCIPAL ?");
            queryList.add("%" + queryParam.get("dlrPrincipal") + "%");
        }
           if(!StringUtils.isNullOrEmpty(queryParam.get("wsStatus"))){
               sql.append(" AND A.WS_STATUS="+queryParam.get("wsStatus")+" ");
           }
           if (!StringUtils.isNullOrEmpty(queryParam.get("beginSubmitTime"))) {
               sql.append(" and c.SUBMIT_TIME>= ?");
               queryList.add(DateUtil.parseDefaultDate(queryParam.get("beginSubmitTime")));
           }
           if (!StringUtils.isNullOrEmpty(queryParam.get("endSubmitTime"))) {
               sql.append(" and c.SUBMIT_TIME<?");
               queryList.add(DateUtil.addOneDay(queryParam.get("endSubmitTime")));
           }
           if (!StringUtils.isNullOrEmpty(queryParam.get("beginAuditingDate"))) {
               sql.append(" and c.SUBMIT_TIME>= ?");
               queryList.add(DateUtil.parseDefaultDate(queryParam.get("beginAuditingDate")));
           }
           if (!StringUtils.isNullOrEmpty(queryParam.get("endAuditingDate"))) {
               sql.append(" and c.AUDITING_DATE<?");
               queryList.add(DateUtil.addOneDay(queryParam.get("endAuditingDate")));
           }
        List<Map> resultList = DAOUtil.findAll(sql.toString(), queryList);
        OperateLogDto operateLogDto=new OperateLogDto();
        operateLogDto.setOperateContent("大客户导出");
        operateLogDto.setOperateType(DictCodeConstants.LOG_CUSTOMER_MANAGEMENT);
        operateLogService.writeOperateLog(operateLogDto);
        return resultList;
    }
    
    /**
    * @author LiGaoqi
    * @date 2017年4月18日
    * @param id
    * @param customerNo
    * @return
    * @throws ServiceBizException
    * (non-Javadoc)
    * @see com.yonyou.dms.customer.service.customerManage.BigCustomerManageService#CheckData2(java.lang.String, java.lang.String)
    */
    	
    @Override
    public String CheckData2(String id, String employeeType) throws ServiceBizException {
        String msg="0";
        LoginInfoDto loginInfo = ApplicationContextHelper.getBeanByType(LoginInfoDto.class);
        StringBuffer sb = new StringBuffer();
                sb.append(" SELECT  NUMBER,DEALER_CODE FROM tm_big_customer_AMOUNT where " +
                        " IS_VALID=10011001 AND IS_DELETE=0 and DEALER_CODE='"+loginInfo.getDealerCode()+"' and ps_type='"+id+"' and employee_type='"+employeeType+"'" 
                            );
                List<Object> queryList = new ArrayList<Object>();
                List<Map> result = DAOUtil.findAll(sb.toString(), queryList);
                if(result!=null&&result.size()>0){
                    msg=result.get(0).get("NUMBER").toString();
                }
     
        return msg;
    }
    
    /**
    * @author LiGaoqi
    * @date 2017年4月18日
    * @param customerNo
    * @return
    * @throws ServiceBizException
    * (non-Javadoc)
    * @see com.yonyou.dms.customer.service.customerManage.BigCustomerManageService#CheckGenJin(java.lang.String)
    */
    	
    @Override
    public String CheckGenJin(String customerNo) throws ServiceBizException {
        String msg="12781002";
        LoginInfoDto loginInfo = ApplicationContextHelper.getBeanByType(LoginInfoDto.class);
        StringBuffer sb = new StringBuffer();
        sb.append(" select customer_no,dealer_code from TT_SALES_PROMOTION_PLAN where dealer_code='"+loginInfo.getDealerCode()+"'  ");
        sb.append(" and customer_no='"+customerNo+"' and  ACTION_DATE>=DATE_SUB(CURDATE(), INTERVAL 2 WEEK) ");
                List<Object> queryList = new ArrayList<Object>();
                List<Map> result = DAOUtil.findAll(sb.toString(), queryList);
                if(result!=null&&result.size()>0){
                    msg="12781001";
                }
     

        // TODO Auto-generated method stub
       
        return msg;
    }
    
    /**
    * @author LiGaoqi
    * @date 2017年5月4日
    * @param wsNo
    * @return
    * @throws ServiceBizException
    * (non-Javadoc)
    * @see com.yonyou.dms.customer.service.customerManage.BigCustomerManageService#totalCarCount(java.lang.String)
    */
    	
    @Override
    public int totalCarCount(String wsNo) throws ServiceBizException {
        StringBuffer sql = new StringBuffer();
        sql.append("SELECT  A.ITEM_ID,A.WS_NO,A.DEALER_CODE,A.BRAND_CODE,A.SERIES_CODE,A.MODEL_CODE,A.CONFIG_CODE,A.COLOR_CODE,A.PURCHASE_COUNT,B.CONFIG_NAME,A.IS_RES_APPLY FROM TT_WS_CONFIG_INFO A  ");
        sql.append(" left join  ("+CommonConstants.VM_CONFIGURATION+")   B   ON A.CONFIG_CODE= B.CONFIG_CODE  AND A.DEALER_CODE= B.DEALER_CODE  AND A.BRAND_CODE = B.BRAND_CODE AND A.SERIES_CODE = B.SERIES_CODE AND A.MODEL_CODE = B.MODEL_CODE "); 
        sql.append(" WHERE A.DEALER_CODE='"+FrameworkUtil.getLoginInfo().getDealerCode()+"' AND A.D_KEY=0 ");
        List<Object> queryList = new ArrayList<Object>();
        sql.append(" AND A.WS_NO='"+wsNo+"'"); 
        List<Map> result = DAOUtil.findAll(sql.toString(), queryList);
        int sum=0;
        if(result.size()>0){
            for(int i=0;i<result.size();i++){
                sum =sum+Integer.parseInt(result.get(i).get("PURCHASE_COUNT").toString());
            }
        }
        return sum;
    }
    
    /**
    * @author LiGaoqi
    * @date 2017年5月5日
    * @param wsNo
    * @return
    * @throws ServiceBizException
    * (non-Javadoc)
    * @see com.yonyou.dms.customer.service.customerManage.BigCustomerManageService#uploanBigCustomer(java.lang.String)
    */
    	
    @Override
    public String uploanBigCustomer(String wsNo) throws ServiceBizException {
        int flag=1;
        try {
           
            flag= DSO0401.getDSO0401(wsNo);
        } catch (Exception e) {
            throw new ServiceBizException(e.getMessage()); 
        }
        if("0".equals(flag)){
            throw new ServiceBizException("大客户报备上报失败"); 
        }
        return null;
    }
    
    
    
}
