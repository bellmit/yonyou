
/** 
 *Copyright 2016 Yonyou Corporation Ltd. All Rights Reserved.
 * This software is published under the terms of the Yonyou Software
 * License version 1.0, a copy of which has been included with this
 * distribution in the LICENSE.txt file.
 *
 * @Project Name : dms.retail
 *
 * @File name : ConfirmcarServiceImpl.java
 *
 * @Author : zhongsw
 *
 * @Date : 2016年9月27日
 *
----------------------------------------------------------------------------------
 *     Date       Who       Version     Comments
 * 1. 2016年9月27日    zhongsw    1.0
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

import com.yonyou.dms.common.domains.DTO.basedata.ConfirmCarAndUpdateCustomerDTO;
import com.yonyou.dms.common.domains.DTO.stockmanage.VehicleDTO;
import com.yonyou.dms.common.domains.PO.basedata.CustomerPO;
import com.yonyou.dms.common.domains.PO.basedata.EntityRelationshipPO;
import com.yonyou.dms.common.domains.PO.basedata.PotentialCusPO;
import com.yonyou.dms.common.domains.PO.basedata.QcsArchivesPO;
import com.yonyou.dms.common.domains.PO.basedata.SoInvoicePO;
import com.yonyou.dms.common.domains.PO.basedata.TmOwnerSubclassPO;
import com.yonyou.dms.common.domains.PO.basedata.TmVehicleSubclassPO;
import com.yonyou.dms.common.domains.PO.basedata.TrackingTaskPO;
import com.yonyou.dms.common.domains.PO.basedata.TtAlertMsgMappingPO;
import com.yonyou.dms.common.domains.PO.basedata.TtAlertMsgPO;
import com.yonyou.dms.common.domains.PO.basedata.TtCusIntentPO;
import com.yonyou.dms.common.domains.PO.basedata.TtCustomerLinkmanInfoPO;
import com.yonyou.dms.common.domains.PO.basedata.TtMsgTypeMappingPO;
import com.yonyou.dms.common.domains.PO.basedata.TtOrderStatusUpdatePO;
import com.yonyou.dms.common.domains.PO.basedata.TtPoCusLinkmanPO;
import com.yonyou.dms.common.domains.PO.basedata.TtSalesCrPO;
import com.yonyou.dms.common.domains.PO.basedata.TtSalesPromotionPlanPO;
import com.yonyou.dms.common.domains.PO.basedata.TtVsDeliveryItemPO;
import com.yonyou.dms.common.domains.PO.basedata.TtVsStockDeliveryPO;
import com.yonyou.dms.common.domains.PO.basedata.UserPO;
import com.yonyou.dms.common.domains.PO.basedata.VsProductPO;
import com.yonyou.dms.common.domains.PO.basedata.VsStockLogPO;
import com.yonyou.dms.common.domains.PO.basedata.VsStockPO;
import com.yonyou.dms.common.domains.PO.stockmanage.CarownerPO;
import com.yonyou.dms.common.domains.PO.stockmanage.VehiclePO;
import com.yonyou.dms.common.service.monitor.Utility;
import com.yonyou.dms.commonSales.domains.PO.stockmanage.SalesOrderPO;
import com.yonyou.dms.framework.DAO.DAOUtil;
import com.yonyou.dms.framework.DAO.DAOUtilGF;
import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.framework.domain.LoginInfoDto;
import com.yonyou.dms.framework.service.CommonNoService;
import com.yonyou.dms.framework.util.FrameworkUtil;
import com.yonyou.dms.framework.util.bean.ApplicationContextHelper;
import com.yonyou.dms.function.common.CommonConstants;
import com.yonyou.dms.function.common.DictCodeConstants;
import com.yonyou.dms.function.exception.ServiceBizException;
import com.yonyou.dms.function.utils.common.CommonUtils;
import com.yonyou.dms.function.utils.common.StringUtils;
import com.yonyou.dms.retail.domains.DTO.ordermanage.SalesOrderDTO;

/**
 * 交车确认实现
 * @author LGQ
 * @date 2017年2月07日
 */
@Service
public class ConfirmcarServiceImpl implements ConfirmcarService{
    @Autowired
    private CommonNoService    commonNoService;
    
    

	
    
    
    /**
    * @author LiGaoqi
    * @date 2017年5月3日
    * @param salesOrderDTO
    * @return
    * @throws ServiceBizException
    * (non-Javadoc)
    * @see com.yonyou.dms.retail.service.ordermanage.ConfirmcarService#addSalesOrder(com.yonyou.dms.retail.domains.DTO.ordermanage.SalesOrderDTO)
    */
    	
    @Override
    public void addSalesOrder(SalesOrderDTO salesOrderDTO) throws ServiceBizException {
        LoginInfoDto loginInfo = ApplicationContextHelper.getBeanByType(LoginInfoDto.class);
        SalesOrderPO updatePo = SalesOrderPO.findByCompositeKeys(loginInfo.getDealerCode(),salesOrderDTO.getSoNo());
        String uodateVin="";
        if(updatePo!=null){
            if(!StringUtils.isNullOrEmpty(updatePo.getString("VIN"))){
                uodateVin = updatePo.getString("VIN");
            }
        }
        VsStockPO vpo = VsStockPO.findByCompositeKeys(loginInfo.getDealerCode(),salesOrderDTO.getVin());
        if(!StringUtils.isNullOrEmpty(salesOrderDTO.getDeliveryMode())&&salesOrderDTO.getDeliveryMode()==13021001){
            if(vpo!=null){
                vpo.setString("SO_NO", salesOrderDTO.getSoNo());
                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                System.out.println("日期"+format.format(new Date()));
               // vpo.setString("DISPATCHED_DATE", format.format(new Date()));
                vpo.setInteger("DISPATCHED_STATUS", DictCodeConstants.DICT_DISPATCHED_STATUS_HAVE_DISPATCHED);//已配车
                vpo.setInteger("D_KEY", Integer.parseInt(DictCodeConstants.D_KEY));
                vpo.saveIt();
            }
        }
        if(!StringUtils.isNullOrEmpty(salesOrderDTO.getVin())&&!StringUtils.isNullOrEmpty(uodateVin)&&!salesOrderDTO.getVin().equals(uodateVin)){
            VsStockPO updatevpo = VsStockPO.findByCompositeKeys(loginInfo.getDealerCode(),uodateVin);
            if(updatevpo!=null){
                updatevpo.setString("SO_NO", null);
                //updatevpo.setDate("DISPATCHED_DATE", null);
                updatevpo.setInteger("DISPATCHED_STATUS", DictCodeConstants.DICT_DISPATCHED_STATUS_NOT_DISPATCHED);//未配车
                updatevpo.setInteger("D_KEY", Integer.parseInt(DictCodeConstants.D_KEY));
                updatevpo.saveIt();
            }
        }
        SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        System.out.println("日期"+format1.format(new Date()));
        //po.setString("SHEET_CREATE_DATE",  format1.format(new Date()));

        updatePo.setString("DISPATCHED_DATE",format1.format(new Date()));
        updatePo.setString("VIN",salesOrderDTO.getVin());
        updatePo.saveIt();
    }

    /**
    * @author LiGaoqi
    * @date 2017年2月10日
    * @param ConfirmCarDTO
    * @throws ServiceBizException
    * (non-Javadoc)
    * @see com.yonyou.dms.retail.service.ordermanage.ConfirmcarService#saveCustomerInfo(com.yonyou.dms.common.domains.DTO.basedata.ConfirmCarAndUpdateCustomerDTO)
    */
    	
    @Override
    public void saveCustomerInfo(ConfirmCarAndUpdateCustomerDTO ConfirmCarDTO) throws ServiceBizException {
       LoginInfoDto loginInfo = ApplicationContextHelper.getBeanByType(LoginInfoDto.class); 
       String ascstr="";
       String entityCode = loginInfo.getDealerCode();
       String vin =ConfirmCarDTO.getuVin();
       String soNo =ConfirmCarDTO.getUsoNo();
       String customerNo =ConfirmCarDTO.getCustomerNo();
       ascstr = loginInfo.getDealerName();
       String Tag=this.checkCustomerInfo(vin, entityCode);
       if(!StringUtils.isNullOrEmpty(ConfirmCarDTO.getuVin())){
           VsStockPO skp1 = VsStockPO.findByCompositeKeys(entityCode,vin);
           if(!StringUtils.isNullOrEmpty(skp1)){
           
           }
       }
       if(!DictCodeConstants.DICT_IS_NO.equals(Tag)){
           if(!DictCodeConstants.DICT_IS_YES.equals(Tag)){
               //更新保有客户信息
              customerNo=Tag;
              CustomerPO customerPO = CustomerPO.findByCompositeKeys(entityCode,customerNo);
              customerPO.setInteger("CUSTOMER_TYPE", ConfirmCarDTO.getCustomerType());
              customerPO.setString("CUSTOMER_NAME", ConfirmCarDTO.getCusName());
              customerPO.setInteger("GENDER", ConfirmCarDTO.getGender());
              customerPO.setDate("BIRTHDAY", ConfirmCarDTO.getBirthday());
              customerPO.setInteger("CT_CODE", ConfirmCarDTO.getCtCode());
              customerPO.setString("CERTIFICATE_NO", ConfirmCarDTO.getCertificateNo());
              customerPO.setString("ADDRESS", ConfirmCarDTO.getAddress());
              customerPO.setInteger("BUY_PURPOSE", ConfirmCarDTO.getBuyReason());
              customerPO.setString("BUY_REASON", ConfirmCarDTO.getBuyPurpose());
              customerPO.saveIt();
              //更新车辆信息
              VehiclePO vehiclePO = VehiclePO.findByCompositeKeys(vin,entityCode);
              vehiclePO.setString("LICENSE", ConfirmCarDTO.getLicense());
              vehiclePO.setDate("LICENSE_DATE",ConfirmCarDTO.getLicenseDate());
              vehiclePO.setDate("SALES_DATE",ConfirmCarDTO.getSalesDate());
              vehiclePO.setDouble("SALES_MILEAGE", ConfirmCarDTO.getSalesMileage());
              vehiclePO.setString("SALES_AGENT_NAME", ascstr);
              /** 存入车辆表里的销售顾问为名字 */
              SalesOrderPO orderPOcon = SalesOrderPO.findByCompositeKeys(entityCode,soNo);
              if(orderPOcon!=null&&!StringUtils.isNullOrEmpty(orderPOcon.getInteger("BUSINESS_TYPE"))&&orderPOcon.getInteger("BUSINESS_TYPE")==13001001
                      &&!StringUtils.isNullOrEmpty(orderPOcon.getInteger("SO_STATUS"))&&orderPOcon.getInteger("SO_STATUS")==13011030
                      &&!StringUtils.isNullOrEmpty(orderPOcon.getInteger("D_KEY"))&&orderPOcon.getInteger("D_KEY")==0&&!StringUtils.isNullOrEmpty(orderPOcon.getString("SOLD_BY"))){
                  UserPO userpoa = UserPO.findByCompositeKeys(FrameworkUtil.getLoginInfo().getDealerCode(),orderPOcon.getString("SOLD_BY"));
                  if(userpoa!=null){
                      vehiclePO.setString("CONSULTANT", userpoa.getString("USER_NAME"));
                  }  
              }
              if(!StringUtils.isNullOrEmpty(vin)){
                  //二级网点业务-车辆子表更新
                  List<Object> TmsubVList = new ArrayList<Object>();
                  TmsubVList.add(vin);
                  TmsubVList.add(loginInfo.getDealerCode());          
                  List<TmVehicleSubclassPO> TmsubVpo = TmVehicleSubclassPO.findBySQL("select * from TM_VEHICLE_SUBCLASS where VIN= ? AND DEALER_CODE= ? ",TmsubVList.toArray());           
                  if(TmsubVpo!=null&&TmsubVpo.size()>0){
                      for(int s=0;s<TmsubVpo.size();s++){
                          TmVehicleSubclassPO poSub = TmsubVpo.get(s);
                          poSub.setString("OWNER_NO", vehiclePO.getString("OWNER_NO"));
                          poSub.setString("CONSULTANT", vehiclePO.getString("CONSULTANT"));
                          poSub.setInteger("IS_SELF_COMPANY", vehiclePO.getInteger("IS_SELF_COMPANY"));
                          poSub.setInteger("IS_SELF_COMPANY_INSURANCE", vehiclePO.getInteger("IS_SELF_COMPANY_INSURANCE"));
                          poSub.setInteger("IS_VALID", vehiclePO.getInteger("IS_VALID"));
                          poSub.setInteger("NO_VALID_REASON", vehiclePO.getInteger("NO_VALID_REASON"));
                          poSub.setDate("FIRST_IN_DATE", vehiclePO.getDate("FIRST_IN_DATE"));
                          poSub.setDate("LAST_MAINTAIN_DATE", vehiclePO.getDate("LAST_MAINTAIN_DATE"));
                          poSub.setDate("LAST_MAINTENANCE_DATE", vehiclePO.getDate("LAST_MAINTENANCE_DATE"));
                          poSub.setDate("DISCOUNT_EXPIRE_DATE", vehiclePO.getDate("DISCOUNT_EXPIRE_DATE"));
                          poSub.setDate("ADJUST_DATE", vehiclePO.getDate("ADJUST_DATE"));
                          poSub.setString("CHIEF_TECHNICIAN", vehiclePO.getString("CHIEF_TECHNICIAN"));
                          poSub.setString("SERVICE_ADVISOR", vehiclePO.getString("SERVICE_ADVISOR"));
                          poSub.setString("INSURANCE_ADVISOR", vehiclePO.getString("INSURANCE_ADVISOR"));
                          poSub.setString("MAINTAIN_ADVISOR", vehiclePO.getString("MAINTAIN_ADVISOR"));
                          poSub.setDouble("LAST_MAINTAIN_MILEAGE", vehiclePO.getDouble("LAST_MAINTAIN_MILEAGE"));
                          poSub.setDouble("LAST_MAINTENANCE_MILEAGE", vehiclePO.getDouble("LAST_MAINTENANCE_MILEAGE"));
                          poSub.setDouble("PRE_PAY", vehiclePO.getDouble("PRE_PAY"));
                          poSub.setDouble("ARREARAGE_AMOUNT", vehiclePO.getDouble("ARREARAGE_AMOUNT"));
                          poSub.setString("DISCOUNT_MODE_CODE", vehiclePO.getString("DISCOUNT_MODE_CODE"));
                          poSub.setString("ADJUSTER", vehiclePO.getString("ADJUSTER"));
                          poSub.setString("NO_VALID_REASON", vehiclePO.getString("NO_VALID_REASON"));
                          
                          poSub.saveIt();
                         
                      }
                  }
                  vehiclePO.saveIt();
              }
              //
              this.updateOwnerInfo(entityCode, customerNo, vin);
           }
       }
       else if(DictCodeConstants.DICT_IS_NO.equals(Tag)){
           
       }
//     更新QCSArchives 表 | DMS-3415 保存时校验是否有QCS流程，如果有QCS流程，且QCS流程表中的VIN码为空，则将当销售订单中的VIN码更新到QCS流程表中 
               
        
    }
    
    /**
     * 
     * 功能描述：交车确认中，已经生成了保佑客户信息和车主车辆信息，在出库中对保佑客户信息维护添加，更新后的保佑客户信息，更新到车主记录中
     * @author wanghui
     * @throws Exception 
     *
     */
    public int updateOwnerInfo(
        String entityCode,
        String customerNo,
        String vin)  throws ServiceBizException {
        CustomerPO customerPO = CustomerPO.findByCompositeKeys(entityCode,customerNo);
        if(!StringUtils.isNullOrEmpty(customerPO)){
            String groupCodeVehicle="";
            String groupCodeOwner = "";
            groupCodeVehicle=Utility.getGroupEntity(entityCode, "TM_VEHICLE");
            groupCodeOwner=Utility.getGroupEntity(entityCode, "TM_OWNER");
            VehiclePO vehPO = VehiclePO.findByCompositeKeys(vin,groupCodeVehicle);
            if(vehPO!=null){
                List<Object> vsubList = new ArrayList<Object>();
                vsubList.add(entityCode);
                vsubList.add(vin);
                vsubList.add(vehPO.getString("DEALER_CODE"));          
                List<TmVehicleSubclassPO> vsubPO = TmVehicleSubclassPO.findBySQL("select * from TM_VEHICLE_SUBCLASS where DEALER_CODE= ? AND VIN= ? AND MAIN_ENTITY= ? ",vsubList.toArray());           
                if(vsubPO!=null){
                    TmVehicleSubclassPO PO = vsubPO.get(0);
                    if(Utility.isPrivateField(entityCode, "TM_VEHICLE", "CONSULTANT")){
                        vehPO.setString("CONSULTANT", PO.getString("CONSULTANT"));
                    }
                    if(Utility.isPrivateField(entityCode, "TM_VEHICLE", "IS_SELF_COMPANY")){
                        vehPO.setInteger("IS_SELF_COMPANY", PO.getInteger("IS_SELF_COMPANY"));
                    }
                    if(Utility.isPrivateField(entityCode, "TM_VEHICLE", "FIRST_IN_DATE")){
                        vehPO.setDate("FIRST_IN_DATE", PO.getDate("FIRST_IN_DATE"));
                    }
                    if(Utility.isPrivateField(entityCode, "TM_VEHICLE", "CHIEF_TECHNICIAN")){
                        vehPO.setString("CHIEF_TECHNICIAN", PO.getString("CHIEF_TECHNICIAN"));
                    }
                    if(Utility.isPrivateField(entityCode, "TM_VEHICLE", "SERVICE_ADVISOR")){
                        vehPO.setString("SERVICE_ADVISOR", PO.getString("SERVICE_ADVISOR"));
                    }
                    if(Utility.isPrivateField(entityCode, "TM_VEHICLE", "INSURANCE_ADVISOR")){
                        vehPO.setString("INSURANCE_ADVISOR", PO.getString("INSURANCE_ADVISOR"));
                    }
                    if(Utility.isPrivateField(entityCode, "TM_VEHICLE", "MAINTAIN_ADVISOR")){
                        vehPO.setString("MAINTAIN_ADVISOR", PO.getString("MAINTAIN_ADVISOR"));
                    }
                    if(Utility.isPrivateField(entityCode, "TM_VEHICLE", "LAST_MAINTAIN_DATE")){
                        vehPO.setDate("LAST_MAINTAIN_DATE", PO.getDate("LAST_MAINTAIN_DATE"));
                    }
                    if(Utility.isPrivateField(entityCode, "TM_VEHICLE", "LAST_MAINTAIN_MILEAGE")){
                        vehPO.setDouble("LAST_MAINTAIN_MILEAGE", PO.getDouble("LAST_MAINTAIN_MILEAGE"));
                    }
                    if(Utility.isPrivateField(entityCode, "TM_VEHICLE", "LAST_MAINTENANCE_DATE")){
                        vehPO.setDate("LAST_MAINTENANCE_DATE", PO.getDate("LAST_MAINTENANCE_DATE"));
                    }
                    
                    //
                    if(Utility.isPrivateField(entityCode, "TM_VEHICLE", "LAST_MAINTENANCE_MILEAGE")){
                        vehPO.setDouble("LAST_MAINTENANCE_MILEAGE", PO.getDouble("LAST_MAINTENANCE_MILEAGE"));
                    }
                    if(Utility.isPrivateField(entityCode, "TM_VEHICLE", "PRE_PAY")){
                        vehPO.setDouble("PRE_PAY", PO.getDouble("PRE_PAY"));
                    }
                    if(Utility.isPrivateField(entityCode, "TM_VEHICLE", "ARREARAGE_AMOUNT")){
                        vehPO.setDouble("ARREARAGE_AMOUNT", PO.getDouble("ARREARAGE_AMOUNT"));
                    }
                    if(Utility.isPrivateField(entityCode, "TM_VEHICLE", "DISCOUNT_MODE_CODE")){
                        vehPO.setString("DISCOUNT_MODE_CODE", PO.getString("DISCOUNT_MODE_CODE"));
                    }
                    if(Utility.isPrivateField(entityCode, "TM_VEHICLE", "DISCOUNT_EXPIRE_DATE")){
                        vehPO.setDate("DISCOUNT_EXPIRE_DATE", PO.getDate("DISCOUNT_EXPIRE_DATE"));
                    }
                    if(Utility.isPrivateField(entityCode, "TM_VEHICLE", "IS_SELF_COMPANY_INSURANCE")){
                        vehPO.setInteger("IS_SELF_COMPANY_INSURANCE", PO.getInteger("IS_SELF_COMPANY_INSURANCE"));
                    }
                    if(Utility.isPrivateField(entityCode, "TM_VEHICLE", "ADJUST_DATE")){
                        vehPO.setDate("ADJUST_DATE", PO.getDate("ADJUST_DATE"));
                    }
                    if(Utility.isPrivateField(entityCode, "TM_VEHICLE", "ADJUSTER")){
                        vehPO.setString("ADJUSTER", PO.getString("ADJUSTER"));
                    }
                    if(Utility.isPrivateField(entityCode, "TM_VEHICLE", "IS_VALID")){
                        vehPO.setInteger("IS_VALID", PO.getInteger("IS_VALID"));
                    }
                    if(Utility.isPrivateField(entityCode, "TM_VEHICLE", "NO_VALID_REASON")){
                        vehPO.setInteger("NO_VALID_REASON", PO.getInteger("NO_VALID_REASON"));
                    }
                }

                CarownerPO ownerPOb = CarownerPO.findByCompositeKeys(groupCodeOwner,vehPO.getString("OWNER_NO"));
                if(ownerPOb!=null){
                    if(!StringUtils.isNullOrEmpty(customerPO.getInteger("CUSTOMER_TYPE"))&&customerPO.getInteger("CUSTOMER_TYPE").toString().equals(DictCodeConstants.DICT_CUSTOMER_TYPE_INDIVIDUAL)) {
                        //个人
                          ownerPOb.setInteger("OWNER_PROPERTY", Integer.parseInt(DictCodeConstants.DICT_OWNER_PROPERTY_PERSONAL));
                      }
                      if(!StringUtils.isNullOrEmpty(customerPO.getInteger("CUSTOMER_TYPE"))&&customerPO.getInteger("CUSTOMER_TYPE").toString().equals(DictCodeConstants.DICT_CUSTOMER_TYPE_COMPANY)) {
                        //公司
                            ownerPOb.setInteger("OWNER_PROPERTY", Integer.parseInt(DictCodeConstants.DICT_OWNER_PROPERTY_COMPANY));

                        }
                      ownerPOb.setInteger("GENDER", customerPO.getInteger("GENDER"));
                      ownerPOb.setDate("BIRTHDAY", customerPO.getDate("BIRTHDAY"));
                      ownerPOb.setString("ZIP_CODE", customerPO.getString("ZIP_CODE"));
                      ownerPOb.setInteger("PROVINCE", customerPO.getInteger("PROVINCE"));
                      ownerPOb.setInteger("CITY", customerPO.getInteger("CITY"));
                      ownerPOb.setInteger("DISTRICT", customerPO.getInteger("DISTRICT"));
                      ownerPOb.setString("PHONE", customerPO.getString("CONTACTOR_PHONE"));
                      ownerPOb.setString("MOBILE", customerPO.getString("CONTACTOR_MOBILE"));
                      ownerPOb.setString("ADDRESS", customerPO.getString("ADDRESS"));
                      ownerPOb.setString("E_MAIL", customerPO.getString("E_MAIL"));
                      ownerPOb.setString("CERTIFICATE_NO", customerPO.getString("CERTIFICATE_NO"));
                      ownerPOb.setString("HOBBY", customerPO.getString("HOBBY"));
                      ownerPOb.setInteger("INDUSTRY_FIRST", customerPO.getInteger("INDUSTRY_FIRST"));
                      ownerPOb.setInteger("INDUSTRY_SECOND", customerPO.getInteger("INDUSTRY_SECOND"));
                      ownerPOb.setDate("FOUND_DATE", customerPO.getDate("FOUND_DATE"));
                      ownerPOb.setDate("SUBMIT_TIME", customerPO.getDate("SUBMIT_TIME"));
                      ownerPOb.setDate("DOWN_TIMESTAMP", customerPO.getDate("DOWN_TIMESTAMP"));
                      ownerPOb.setInteger("IS_UPLOAD", Integer.parseInt(DictCodeConstants.DICT_IS_NO));
                      ownerPOb.setString("OWNER_NAME", customerPO.getString("CUSTOMER_NAME"));
                      ownerPOb.setInteger("CT_CODE", customerPO.getInteger("CT_CODE"));
                      ownerPOb.saveIt();
                }
            
                //
            }
        }
        return 1;
    }

    /**
    * @author LiGaoqi
    * @date 2017年2月6日
    * @param id
    * @return
    * @throws ServiceBizException
    * (non-Javadoc)
    * @see com.yonyou.dms.retail.service.ordermanage.ConfirmcarService#searchConfirm(java.lang.String)
    */
    	
    @Override
    public List<Map> searchConfirm(String soNo) throws ServiceBizException {
        LoginInfoDto loginInfo = ApplicationContextHelper.getBeanByType(LoginInfoDto.class);
        StringBuffer sql = new StringBuffer("");
        List<Object> queryParams = new ArrayList<Object>();
        SalesOrderPO sales = SalesOrderPO.findByCompositeKeys(loginInfo.getDealerCode(),soNo);
        String vin =sales.getString("VIN");
        System.out.println("1111111111111111112222222222222222222");
        System.out.println(sales.getString("VIN"));
        System.out.println("SO_NO:"+soNo);
        System.out.println("VIN:"+vin);
        if(checkOwnerInfo(vin)!=null&&checkOwnerInfo(vin).size()>0){
            System.out.println(checkOwnerInfo(vin));
            List<Object> vehicleList = new ArrayList<Object>();
            vehicleList.add(loginInfo.getDealerCode());
            vehicleList.add(vin);
            VehiclePO PO = new VehiclePO();
            List<VehiclePO> vehiclePo = VehiclePO.findBySQL("select * from TM_VEHICLE where DEALER_CODE= ? AND VIN= ? ",vehicleList.toArray());
            if(vehiclePo!=null&&vehiclePo.size()>0){
               PO = vehiclePo.get(0);
            }
            List<Object> customerList = new ArrayList<Object>();
            customerList.add(loginInfo.getDealerCode());
            customerList.add(Integer.parseInt(DictCodeConstants.D_KEY));
            customerList.add(PO.getString("CUSTOMER_NO"));
            List<CustomerPO> customerPo = CustomerPO.findBySQL("select * from tm_customer where DEALER_CODE= ? AND D_KEY= ? AND CUSTOMER_NO= ? ",customerList.toArray());
            if(customerPo!=null&&customerPo.size()>0){
                sql.append(" SELECT D.CUSTOMER_NO AS PO_CUSTOMER_NO,D.INTENT_LEVEL,'' AS SO_NO,TL.CONTACTOR_NAME AS LINKED_NAME,A.VIN,A.MODEL,A.SERIES,A.BRAND,A.SERVICE_ADVISOR as ZS_MANAGER,A.COLOR,A.APACKAGE,D.CUSTOMER_NAME AS P_CUSTOMER_NAME,D.CONTACTOR_PHONE AS P_CONTACTOR_PHONE,D.CONTACTOR_MOBILE AS P_CONTACTOR_MOBILE,  ");
                sql.append("  date_format(A.LICENSE_DATE,'%Y-%m-%d') AS LICENSE_DATE,A.LICENSE,date_format(A.SALES_DATE,'%Y-%m-%d') AS SALES_DATE,A.SALES_MILEAGE,");
                sql.append("B.*,date_format(B.BIRTHDAY,'%Y-%m-%d') AS BIRTHDAY1, E.PRODUCT_CODE  FROM ("+CommonConstants.VM_VEHICLE+") A ");
                sql.append("LEFT JOIN TM_CUSTOMER B ON A.DEALER_CODE=B.DEALER_CODE AND A.CUSTOMER_NO=B.CUSTOMER_NO ");
                sql.append("LEFT JOIN TT_PO_CUS_RELATION C ON A.CUSTOMER_NO=C.CUSTOMER_NO AND A.DEALER_CODE=C.DEALER_CODE ");
                sql.append("LEFT JOIN TM_POTENTIAL_CUSTOMER D ON A.DEALER_CODE=D.DEALER_CODE AND C.PO_CUSTOMER_NO=D.CUSTOMER_NO ");
                sql.append(" LEFT JOIN TM_VS_STOCK E ON A.VIN= E.VIN AND A.DEALER_CODE=E.DEALER_CODE  ");
                sql.append(" LEFT JOIN TT_PO_CUS_LINKMAN TL ON TL.IS_DEFAULT_CONTACTOR="+DictCodeConstants.DICT_IS_YES+" AND D.CUSTOMER_NO=TL.CUSTOMER_NO AND D.DEALER_CODE=TL.DEALER_CODE ");
                sql.append("WHERE A.DEALER_CODE='"+ loginInfo.getDealerCode()+ "' AND A.VIN='"+ vin+ "' AND B.CUSTOMER_NO='"+ PO.getString("CUSTOMER_NO") + "' ");
            }      
        }else{
           SalesOrderPO salesPo = SalesOrderPO.findByCompositeKeys(loginInfo.getDealerCode(),soNo);
            // 潜在
            sql.append(" SELECT TL.MOBILE AS LINKED_MOBILE,B.CUSTOMER_NO AS PO_CUSTOMER_NO,'' AS CUSTOMER_NO,A.SO_NO,TL.CONTACTOR_NAME AS LINKED_NAME,A.VIN,A.BRAND_CODE AS BRAND,A.SERVICE_ADVISOR as ZS_MANAGER,A.SERIES_CODE AS SERIES,A.MODEL_CODE AS MODEL,A.CONFIG_CODE AS APACKAGE,A.COLOR_CODE AS COLOR,B.CUSTOMER_NAME AS P_CUSTOMER_NAME,B.CONTACTOR_PHONE AS P_CONTACTOR_PHONE,B.CONTACTOR_MOBILE AS P_CONTACTOR_MOBILE,");
            sql.append("A.LICENSE,A.SALES_DATE,A.LICENSE_DATE,A.SALES_MILEAGE,B.*,A.PRODUCT_CODE  FROM (");
            sql.append("SELECT D.MODEL_CODE,D.BRAND_CODE,D.SERIES_CODE,D.CONFIG_CODE,E.SERVICE_ADVISOR ,COALESCE(D.COLOR_CODE,C.COLOR_CODE) as COLOR_CODE,C.SO_NO,C.VIN,C.LICENSE,C.CUSTOMER_NO, C.PRODUCT_CODE,");
            sql.append("C.DEALER_CODE,C.CREATED_AT AS SALES_DATE,0 AS SALES_MILEAGE,'' AS LICENSE_DATE  FROM TT_SALES_ORDER C LEFT JOIN ("+CommonConstants.VM_VS_PRODUCT+") D ON C.DEALER_CODE=D.DEALER_CODE ");
            sql.append("AND C.PRODUCT_CODE=D.PRODUCT_CODE LEFT JOIN ("+CommonConstants.VM_VEHICLE+") E ON C.DEALER_CODE=E.DEALER_CODE AND C.VIN=E.VIN");
            sql.append(") A LEFT JOIN TM_POTENTIAL_CUSTOMER B ON A.DEALER_CODE=B.DEALER_CODE ");
            sql.append("AND A.CUSTOMER_NO=B.CUSTOMER_NO LEFT JOIN TT_PO_CUS_LINKMAN TL ON TL.IS_DEFAULT_CONTACTOR="+DictCodeConstants.DICT_IS_YES+" AND B.CUSTOMER_NO=TL.CUSTOMER_NO AND B.DEALER_CODE=TL.DEALER_CODE  "); 
            sql.append("WHERE A.DEALER_CODE='"+  loginInfo.getDealerCode() + "' ");
            sql.append(" and A.VIN= ? ");
            sql.append(" and B.CUSTOMER_NO= ?");
            sql.append(" and A.SO_NO= ?");
            queryParams.add(vin);
            queryParams.add(salesPo.getString("CUSTOMER_NO"));
            queryParams.add(soNo); 
            
        }
        System.out.println(sql.toString());
        return Base.findAll(sql.toString(), queryParams.toArray());
    
    }
    
    

    
    /**
    * @author LiGaoqi
    * @date 2017年3月27日
    * @param id
    * @return
    * @throws ServiceBizException
    * (non-Javadoc)
    * @see com.yonyou.dms.retail.service.ordermanage.ConfirmcarService#searchPrint(java.lang.String)
    */
    	
    @Override
    public List<Map> searchPrint(String soNo) throws ServiceBizException {
        LoginInfoDto loginInfo = ApplicationContextHelper.getBeanByType(LoginInfoDto.class);
        StringBuffer sql = new StringBuffer("");
        List<Object> queryParams = new ArrayList<Object>();
        SalesOrderPO sales = SalesOrderPO.findByCompositeKeys(loginInfo.getDealerCode(),soNo);
        String vin =sales.getString("VIN");
        System.out.println(sales.getString("VIN"));
        System.out.println("SO_NO:"+soNo);
        System.out.println("VI;N:"+vin);
        sql.append("SELECT DATE(NOW()) AS NOW_DATE,A.*,B.STORAGE_NAME,co.COLOR_NAME,O.CUSTOMER_NAME,mo.MODEL_NAME FROM TT_SALES_ORDER O LEFT JOIN TM_VS_STOCK A ON O.VIN=A.VIN AND O.DEALER_CODE=A.DEALER_CODE LEFT JOIN TM_STORAGE B ON A.DEALER_CODE=B.DEALER_CODE AND A.STORAGE_CODE=B.STORAGE_CODE");
        sql.append(" LEFT JOIN ("+CommonConstants.VM_VS_PRODUCT+") C ON A.PRODUCT_CODE = C.PRODUCT_CODE AND A.DEALER_CODE = C.DEALER_CODE AND A.D_KEY = C.D_KEY");
        sql.append(" left  join   tm_brand   br   on   C.BRAND_CODE = br.BRAND_CODE and C.DEALER_CODE=br.DEALER_CODE");
        sql.append(" left  join   TM_SERIES  se   on   C.SERIES_CODE=se.SERIES_CODE and br.BRAND_CODE=se.BRAND_CODE and C.DEALER_CODE=se.DEALER_CODE");
        sql.append(" left  join   TM_MODEL   mo   on   C.MODEL_CODE=mo.MODEL_CODE and mo.BRAND_CODE=se.BRAND_CODE and mo.SERIES_CODE=se.SERIES_CODE and C.DEALER_CODE=mo.DEALER_CODE");
        sql.append(" left  join   tm_configuration pa   on   C.CONFIG_CODE=pa.CONFIG_CODE and pa.BRAND_CODE=mo.BRAND_CODE and pa.SERIES_CODE=pa.SERIES_CODE and pa.MODEL_CODE=mo.MODEL_CODE and C.DEALER_CODE=pa.DEALER_CODE");
        sql.append(" left  join   tm_color   co   on   C.COLOR_CODE = co.COLOR_CODE and C.DEALER_CODE=co.DEALER_CODE where O.VIN='"+vin+"' AND O.DEALER_CODE='"+loginInfo.getDealerCode()+"'");
        return Base.findAll(sql.toString(), queryParams.toArray());
    
    
    }

    @Override
    public List<Map> searchTest(String id) throws ServiceBizException {
	    String[] ids = id.split(",");
	    System.out.println(ids.length);
	    
	    LoginInfoDto loginInfo = ApplicationContextHelper.getBeanByType(LoginInfoDto.class);
	    StringBuffer sql = new StringBuffer("");
	    List<Object> queryParams = new ArrayList<Object>();
	    String soNo = ids[0];
	    SalesOrderPO sales = SalesOrderPO.findByCompositeKeys(loginInfo.getDealerCode(),soNo);
	    String vin = "";
	    if(sales!=null&&!StringUtils.isNullOrEmpty(sales.getString("VIN"))){
	        vin=sales.getString("VIN");
	    }
	    System.out.println("ids[1]"+ids[1]);
	    if(ids[1].isEmpty()){
	        System.out.println("无语");
	        vin="";
	    }
	    System.out.println("SO_NO:"+soNo);
	    System.out.println("VIN:"+vin);
        if(checkOwnerInfo(vin)!=null&&checkOwnerInfo(vin).size()>0){
            List<Object> vehicleList = new ArrayList<Object>();
            vehicleList.add(loginInfo.getDealerCode());
            vehicleList.add(vin);
            VehiclePO PO = new VehiclePO();
            List<VehiclePO> vehiclePo = VehiclePO.findBySQL("select * from TM_VEHICLE where DEALER_CODE= ? AND VIN= ? ",vehicleList.toArray());
            if(vehiclePo!=null&&vehiclePo.size()>0){
               PO = vehiclePo.get(0);
            }
            List<Object> customerList = new ArrayList<Object>();
            customerList.add(loginInfo.getDealerCode());
            customerList.add(Integer.parseInt(DictCodeConstants.D_KEY));
            customerList.add(PO.getString("CUSTOMER_NO"));
            List<CustomerPO> customerPo = CustomerPO.findBySQL("select * from tm_customer where DEALER_CODE= ? AND D_KEY= ? AND CUSTOMER_NO= ? ",customerList.toArray());
            if(customerPo!=null&&customerPo.size()>0){
                sql.append(" SELECT D.CUSTOMER_NO AS PO_CUSTOMER_NO,D.INTENT_LEVEL,'' AS SO_NO,TL.CONTACTOR_NAME AS LINKED_NAME,A.VIN,A.MODEL,A.SERIES,A.BRAND,A.SERVICE_ADVISOR as ZS_MANAGER,A.COLOR,A.APACKAGE,D.CUSTOMER_NAME AS P_CUSTOMER_NAME,D.CONTACTOR_PHONE AS P_CONTACTOR_PHONE,D.CONTACTOR_MOBILE AS P_CONTACTOR_MOBILE,  ");
                sql.append("  date_format(A.LICENSE_DATE,'%Y-%m-%d') AS LICENSE_DATE,A.LICENSE,date_format(A.SALES_DATE,'%Y-%m-%d') AS SALES_DATE,A.SALES_MILEAGE,");
                sql.append("B.*,date_format(B.BIRTHDAY,'%Y-%m-%d') AS BIRTHDAY1, E.PRODUCT_CODE  FROM ("+CommonConstants.VM_VEHICLE+") A ");
                sql.append("LEFT JOIN TM_CUSTOMER B ON A.DEALER_CODE=B.DEALER_CODE AND A.CUSTOMER_NO=B.CUSTOMER_NO ");
                sql.append("LEFT JOIN TT_PO_CUS_RELATION C ON A.CUSTOMER_NO=C.CUSTOMER_NO AND A.DEALER_CODE=C.DEALER_CODE ");
                sql.append("LEFT JOIN TM_POTENTIAL_CUSTOMER D ON A.DEALER_CODE=D.DEALER_CODE AND C.PO_CUSTOMER_NO=D.CUSTOMER_NO ");
                sql.append(" LEFT JOIN TM_VS_STOCK E ON A.VIN= E.VIN AND A.DEALER_CODE=E.DEALER_CODE  ");
                sql.append(" LEFT JOIN TT_PO_CUS_LINKMAN TL ON TL.IS_DEFAULT_CONTACTOR="+DictCodeConstants.DICT_IS_YES+" AND D.CUSTOMER_NO=TL.CUSTOMER_NO AND D.DEALER_CODE=TL.DEALER_CODE ");
                sql.append("WHERE A.DEALER_CODE='"+ loginInfo.getDealerCode()+ "' AND A.VIN='"+ vin+ "' AND B.CUSTOMER_NO='"+ PO.getString("CUSTOMER_NO") + "' ");
            }
            
        }else{
           SalesOrderPO salesPo = SalesOrderPO.findByCompositeKeys(loginInfo.getDealerCode(),soNo);
            // 潜在
            sql.append(" SELECT B.CUSTOMER_NO AS PO_CUSTOMER_NO,'' AS CUSTOMER_NO,A.SO_NO,TL.CONTACTOR_NAME AS LINKED_NAME,A.VIN,A.BRAND_CODE AS BRAND,A.SERVICE_ADVISOR as ZS_MANAGER,A.SERIES_CODE AS SERIES,A.MODEL_CODE AS MODEL,A.CONFIG_CODE AS APACKAGE,A.COLOR_CODE AS COLOR,B.CUSTOMER_NAME AS P_CUSTOMER_NAME,B.CONTACTOR_PHONE AS P_CONTACTOR_PHONE,B.CONTACTOR_MOBILE AS P_CONTACTOR_MOBILE,");
            sql.append("A.LICENSE,date_format(A.SALES_DATE,'%Y-%m-%d') AS SALES_DATE,date_format(A.LICENSE_DATE,'%Y-%m-%d') AS LICENSE_DATE,A.SALES_MILEAGE,B.*,date_format(B.BIRTHDAY,'%Y-%m-%d') AS BIRTHDAY1,A.PRODUCT_CODE  ");
            sql.append("FROM (SELECT D.MODEL_CODE,D.BRAND_CODE,D.SERIES_CODE,D.CONFIG_CODE,E.SERVICE_ADVISOR ,COALESCE(D.COLOR_CODE,C.COLOR_CODE) as COLOR_CODE,C.SO_NO,C.VIN,C.LICENSE,C.CUSTOMER_NO, C.PRODUCT_CODE,");
            sql.append("C.DEALER_CODE,C.CREATED_AT AS SALES_DATE,0 AS SALES_MILEAGE,'' AS LICENSE_DATE  FROM TT_SALES_ORDER C LEFT JOIN ("+CommonConstants.VM_VS_PRODUCT+") D ON C.DEALER_CODE=D.DEALER_CODE ");
            sql.append("AND C.PRODUCT_CODE=D.PRODUCT_CODE LEFT JOIN ("+CommonConstants.VM_VEHICLE+") E ON C.DEALER_CODE=E.DEALER_CODE AND C.VIN=E.VIN");
            sql.append(") A LEFT JOIN TM_POTENTIAL_CUSTOMER B ON A.DEALER_CODE=B.DEALER_CODE ");
            sql.append("AND A.CUSTOMER_NO=B.CUSTOMER_NO LEFT JOIN TT_PO_CUS_LINKMAN TL ON TL.IS_DEFAULT_CONTACTOR="+DictCodeConstants.DICT_IS_YES+" AND B.CUSTOMER_NO=TL.CUSTOMER_NO AND B.DEALER_CODE=TL.DEALER_CODE  "); 
            sql.append("WHERE A.DEALER_CODE='"+  loginInfo.getDealerCode() + "' ");
           if(StringUtils.isNullOrEmpty(vin)||vin.equals("")||vin==null){
                sql.append(" and A.VIN IS NULL ");
            }else{
                sql.append(" and A.VIN= ? ");
                queryParams.add(vin);
            }
           
            sql.append(" and B.CUSTOMER_NO= ?");
            sql.append(" and A.SO_NO= ?");

            queryParams.add(salesPo.getString("CUSTOMER_NO"));
            queryParams.add(soNo);  

        }
        return Base.findAll(sql.toString(), queryParams.toArray());
    }
    /**
     * 校验是否存在保佑客户信息
     * @throws Exception 
     */
    public String checkCustomerInfo(String vin, String entityCode)
            throws ServiceBizException {
        String Tag = DictCodeConstants.DICT_IS_NO;
        //根据VIN查TM_VEHICLE,查CUSTOMER_NO有没信息,有：已经有保佑客户信息,否:没有保佑客户信息
        VehiclePO vehPO = new VehiclePO();
        LoginInfoDto loginInfo = ApplicationContextHelper.getBeanByType(LoginInfoDto.class); 
        String groupCodeVehicle="";
        groupCodeVehicle=Utility.getGroupEntity(loginInfo.getDealerCode(), "TM_VEHICLE");
        List<Map> vinList = this.checkOwnerInfo(entityCode, vin);
        if (vinList != null & vinList.size() > 0)
        {
            vehPO = VehiclePO.findByCompositeKeys(vin,groupCodeVehicle);
            if(vehPO!=null){
                List<Object> vsubList = new ArrayList<Object>();
                vsubList.add(loginInfo.getDealerCode());
                vsubList.add(vin);
                vsubList.add(vehPO.getString("DEALER_CODE"));          
                List<TmVehicleSubclassPO> vsubPO = TmVehicleSubclassPO.findBySQL("select * from TM_VEHICLE_SUBCLASS where DEALER_CODE= ? AND VIN= ? AND MAIN_ENTITY= ? ",vsubList.toArray());           
                if(vsubPO!=null){
                    TmVehicleSubclassPO PO = vsubPO.get(0);
                    if(Utility.isPrivateField(loginInfo.getDealerCode(), "TM_VEHICLE", "CONSULTANT")){
                        vehPO.setString("CONSULTANT", PO.getString("CONSULTANT"));
                    }
                    if(Utility.isPrivateField(loginInfo.getDealerCode(), "TM_VEHICLE", "IS_SELF_COMPANY")){
                        vehPO.setInteger("IS_SELF_COMPANY", PO.getInteger("IS_SELF_COMPANY"));
                    }
                    if(Utility.isPrivateField(loginInfo.getDealerCode(), "TM_VEHICLE", "FIRST_IN_DATE")){
                        vehPO.setDate("FIRST_IN_DATE", PO.getDate("FIRST_IN_DATE"));
                    }
                    if(Utility.isPrivateField(loginInfo.getDealerCode(), "TM_VEHICLE", "CHIEF_TECHNICIAN")){
                        vehPO.setString("CHIEF_TECHNICIAN", PO.getString("CHIEF_TECHNICIAN"));
                    }
                    if(Utility.isPrivateField(loginInfo.getDealerCode(), "TM_VEHICLE", "SERVICE_ADVISOR")){
                        vehPO.setString("SERVICE_ADVISOR", PO.getString("SERVICE_ADVISOR"));
                    }
                    if(Utility.isPrivateField(loginInfo.getDealerCode(), "TM_VEHICLE", "INSURANCE_ADVISOR")){
                        vehPO.setString("INSURANCE_ADVISOR", PO.getString("INSURANCE_ADVISOR"));
                    }
                    if(Utility.isPrivateField(loginInfo.getDealerCode(), "TM_VEHICLE", "MAINTAIN_ADVISOR")){
                        vehPO.setString("MAINTAIN_ADVISOR", PO.getString("MAINTAIN_ADVISOR"));
                    }
                    if(Utility.isPrivateField(loginInfo.getDealerCode(), "TM_VEHICLE", "LAST_MAINTAIN_DATE")){
                        vehPO.setDate("LAST_MAINTAIN_DATE", PO.getDate("LAST_MAINTAIN_DATE"));
                    }
                    if(Utility.isPrivateField(loginInfo.getDealerCode(), "TM_VEHICLE", "LAST_MAINTAIN_MILEAGE")){
                        vehPO.setDouble("LAST_MAINTAIN_MILEAGE", PO.getDouble("LAST_MAINTAIN_MILEAGE"));
                    }
                    if(Utility.isPrivateField(loginInfo.getDealerCode(), "TM_VEHICLE", "LAST_MAINTENANCE_DATE")){
                        vehPO.setDate("LAST_MAINTENANCE_DATE", PO.getDate("LAST_MAINTENANCE_DATE"));
                    }
                    
                    //
                    if(Utility.isPrivateField(loginInfo.getDealerCode(), "TM_VEHICLE", "LAST_MAINTENANCE_MILEAGE")){
                        vehPO.setDouble("LAST_MAINTENANCE_MILEAGE", PO.getDouble("LAST_MAINTENANCE_MILEAGE"));
                    }
                    if(Utility.isPrivateField(loginInfo.getDealerCode(), "TM_VEHICLE", "PRE_PAY")){
                        vehPO.setDouble("PRE_PAY", PO.getDouble("PRE_PAY"));
                    }
                    if(Utility.isPrivateField(loginInfo.getDealerCode(), "TM_VEHICLE", "ARREARAGE_AMOUNT")){
                        vehPO.setDouble("ARREARAGE_AMOUNT", PO.getDouble("ARREARAGE_AMOUNT"));
                    }
                    if(Utility.isPrivateField(loginInfo.getDealerCode(), "TM_VEHICLE", "DISCOUNT_MODE_CODE")){
                        vehPO.setString("DISCOUNT_MODE_CODE", PO.getString("DISCOUNT_MODE_CODE"));
                    }
                    if(Utility.isPrivateField(loginInfo.getDealerCode(), "TM_VEHICLE", "DISCOUNT_EXPIRE_DATE")){
                        vehPO.setDate("DISCOUNT_EXPIRE_DATE", PO.getDate("DISCOUNT_EXPIRE_DATE"));
                    }
                    if(Utility.isPrivateField(loginInfo.getDealerCode(), "TM_VEHICLE", "IS_SELF_COMPANY_INSURANCE")){
                        vehPO.setInteger("IS_SELF_COMPANY_INSURANCE", PO.getInteger("IS_SELF_COMPANY_INSURANCE"));
                    }
                    if(Utility.isPrivateField(loginInfo.getDealerCode(), "TM_VEHICLE", "ADJUST_DATE")){
                        vehPO.setDate("ADJUST_DATE", PO.getDate("ADJUST_DATE"));
                    }
                    if(Utility.isPrivateField(loginInfo.getDealerCode(), "TM_VEHICLE", "ADJUSTER")){
                        vehPO.setString("ADJUSTER", PO.getString("ADJUSTER"));
                    }
                    if(Utility.isPrivateField(loginInfo.getDealerCode(), "TM_VEHICLE", "IS_VALID")){
                        vehPO.setInteger("IS_VALID", PO.getInteger("IS_VALID"));
                    }
                    if(Utility.isPrivateField(loginInfo.getDealerCode(), "TM_VEHICLE", "NO_VALID_REASON")){
                        vehPO.setInteger("NO_VALID_REASON", PO.getInteger("NO_VALID_REASON"));
                    }
                }
                
            }
            if (vehPO.getString("CUSTOMER_NO") != null && vehPO.getString("CUSTOMER_NO").length() > 0)
            {
                CustomerPO customerPO2 = CustomerPO.findByCompositeKeys(entityCode,vehPO.getString("CUSTOMER_NO"));
                if (customerPO2 != null)
                {
                    if(customerPO2.getString("CUSTOMER_NO")!=null){
                      //保佑客户信息存在
                        Tag = customerPO2.getString("CUSTOMER_NO");//12781001
                    }else{
                      //保佑客户信息存在
                        Tag = DictCodeConstants.DICT_IS_YES;//12781001
                    }
                    
                }
            }
        }
        return Tag;
    }
    public List<Map> checkOwnerInfo(String vin, String entityCode) {
        StringBuffer sql = new StringBuffer("");
        sql.append(" SELECT A.* FROM (" + CommonConstants.VM_VEHICLE + ") A,(" + CommonConstants.VM_OWNER
                   + ") B WHERE A.DEALER_CODE=B.DEALER_CODE AND A.OWNER_NO=B.OWNER_NO  AND A.VIN='" + vin
                   + "' AND A.DEALER_CODE='" + entityCode
                   + "' AND A.CUSTOMER_NO IS NOT NULL AND LENGTH(TRIM(A.CUSTOMER_NO)) <=12");
        List<Object> queryParams = new ArrayList<Object>();
        return Base.findAll(sql.toString(), queryParams.toArray());
    }
    
	public List<Map> checkOwnerInfo(String vin) throws ServiceBizException {
	        LoginInfoDto loginInfo = ApplicationContextHelper.getBeanByType(LoginInfoDto.class);
	        System.out.println("VIN:"+vin);
	        System.out.println("121121212");
	        StringBuffer sql = new StringBuffer("");
	        sql
	                .append(" SELECT A.* FROM ("+CommonConstants.VM_VEHICLE+") A,("+CommonConstants.VM_OWNER+") B WHERE A.DEALER_CODE=B.DEALER_CODE AND A.OWNER_NO=B.OWNER_NO  AND A.VIN='"
	                        + vin
	                        + "' AND A.DEALER_CODE='"
	                        + loginInfo.getDealerCode()
	                        + "' AND A.CUSTOMER_NO IS NOT NULL AND LENGTH(TRIM(A.CUSTOMER_NO)) <=12");
	        List<Object> queryParams = new ArrayList<Object>();
	        System.out.println("121121212");
	        System.out.println(Base.findAll(sql.toString(), queryParams.toArray()).size());
	        return Base.findAll(sql.toString(), queryParams.toArray());
	    }

    /**
	 * 查询
	 */
	@Override
	public PageInfoDto searchConfirmcar(Map<String, String> queryParam) throws ServiceBizException {
	    LoginInfoDto loginInfo = ApplicationContextHelper.getBeanByType(LoginInfoDto.class);
	    StringBuffer sql = new StringBuffer("");
	    List<Object> params = new ArrayList<Object>();
	    sql.append(" SELECT W.*,TA2.ENGINE_NO,TA2.KEY_NUMBER,TA2.CERTIFICATE_NUMBER,TA2.financial_bill_no,TA2.supervise_type FROM (SELECT TA1.DELIVERING_DATE,TA1.VIN,TA1.CONTACTOR_NAME,TA1.MOBILE,TA1.PHONE,TA1.SO_NO,TA1.SO_STATUS,TA1.BUSINESS_TYPE,TA1.LOCK_USER,"); 
	    sql.append(" TA1.SHEET_CREATE_DATE,TA1.ORDER_RECEIVABLE_SUM,TA1.DEALER_CODE,TA1.SOLD_BY,TA1.CUSTOMER_TYPE,TA1.CT_CODE,TA1.BRAND_NAME,TA1.SERIES_NAME,TA1.MODEL_NAME,TA1.CONFIG_NAME,TA1.COLOR_NAME,TA1.USER_NAME,"); 
	    sql.append(" TA1.CERTIFICATE_NO,TA1.VER,TA1.MODEL_CODE, TA1.CONFIG_CODE,TA1.PAY_OFF,COALESCE(TA1.COLOR_CODE,TA1.COLOR_CODE) as COLOR_CODE,"); 
	    sql.append(" CASE WHEN TA1.BUSINESS_TYPE=13001002 THEN P.CUSTOMER_CODE ELSE TA1.CUSTOMER_NO END AS CUSTOMER_NO,"); 
	    sql.append(" CASE WHEN TA1.BUSINESS_TYPE=13001002 THEN P.CUSTOMER_NAME ELSE TA1.CUSTOMER_NAME END AS CUSTOMER_NAME,"); 
	    sql.append(" CASE WHEN TA1.BUSINESS_TYPE=13001002 THEN P.CONTRACT_NO ELSE TA1.CONTRACT_NO END AS CONTRACT_NO, "); 
	    sql.append(" CASE WHEN TA1.BUSINESS_TYPE=13001002 THEN P.AGREEMENT_BEGIN_DATE ELSE TA1.CONTRACT_DATE END AS CONTRACT_DATE,COMPLETE_TAG,LOSSES_PAY_OFF "); 
	    sql.append(" FROM (SELECT A.DELIVERING_DATE,A.VIN,A.CUSTOMER_NAME,A.CONTACTOR_NAME,A.PHONE,A.MOBILE,A.SO_NO,A.SO_STATUS,A.CONSIGNEE_CODE, ");
	    sql.append(" A.BUSINESS_TYPE,A.SHEET_CREATE_DATE,A.ORDER_RECEIVABLE_SUM, A.CUSTOMER_NO,A.SOLD_BY,A.CUSTOMER_TYPE,A.DEALER_CODE,A.LOCK_USER,");
	    sql.append(" A.CONTRACT_NO,A.CONTRACT_DATE,A.CT_CODE,A.CERTIFICATE_NO,A.VER,A.PAY_OFF,A.COMPLETE_TAG,A.LOSSES_PAY_OFF,BR.BRAND_NAME,SE.SERIES_NAME,MO.MODEL_NAME,PA.CONFIG_NAME,CO.COLOR_NAME,EM.USER_NAME,B.MODEL_CODE, B.CONFIG_CODE,COALESCE(A.COLOR_CODE,B.COLOR_CODE) as COLOR_CODE  ");
	    sql.append(" FROM TT_SALES_ORDER A LEFT JOIN ("+CommonConstants.VM_VS_PRODUCT+") B ON A.PRODUCT_CODE = B.PRODUCT_CODE AND A.DEALER_CODE = B.DEALER_CODE AND A.D_KEY = B.D_KEY");
	    sql.append(" LEFT JOIN TM_BRAND BR ON BR.BRAND_CODE=B.BRAND_CODE AND BR.DEALER_CODE=A.DEALER_CODE");
        sql.append(" LEFT JOIN TM_SERIES SE ON SE.SERIES_CODE=B.SERIES_CODE AND BR.BRAND_CODE=SE.BRAND_CODE AND SE.DEALER_CODE=A.DEALER_CODE");
        sql.append(" LEFT JOIN TM_MODEL MO ON MO.MODEL_CODE=B.MODEL_CODE AND SE.SERIES_CODE=MO.SERIES_CODE AND MO.BRAND_CODE=SE.BRAND_CODE AND MO.DEALER_CODE=A.DEALER_CODE");
        sql.append(" LEFT JOIN TM_CONFIGURATION PA ON PA.CONFIG_CODE=B.CONFIG_CODE AND MO.MODEL_CODE=PA.MODEL_CODE AND PA.SERIES_CODE=MO.SERIES_CODE AND MO.BRAND_CODE=PA.BRAND_CODE AND PA.DEALER_CODE=A.DEALER_CODE");
        sql.append(" left  join   tm_color   co   on   B.COLOR_CODE = co.COLOR_CODE and A.DEALER_CODE=co.DEALER_CODE\n");
        sql.append(" left join TM_USER em  on A.SOLD_BY=em.USER_ID AND A.DEALER_CODE=em.DEALER_CODE\n");
	    sql.append(" WHERE A.D_KEY = " + DictCodeConstants.D_KEY + " AND (A.ORDER_SORT != " + DictCodeConstants.FDICT_SALES_ORDER_SORT_PRE  +" OR A.ORDER_SORT IS NULL) AND (A.SO_STATUS = " + DictCodeConstants.DICT_SO_STATUS_DELIVERY_CONFIRMING +" OR A.SO_STATUS = " +DictCodeConstants.DICT_SO_STATUS_HAVE_CONFIRMED +")" +" AND (A.BUSINESS_TYPE != " + DictCodeConstants.DICT_SO_TYPE_SERVICE + " OR (A.BUSINESS_TYPE = 13001004 ))");
	    sql.append(" AND A.DEALER_CODE = '" + loginInfo.getDealerCode() +"'");
	      if (!StringUtils.isNullOrEmpty(queryParam.get("businessType"))) {
	            sql.append(" and A.BUSINESS_TYPE= ?");
	            params.add(queryParam.get("businessType"));
	        }
	        if (!StringUtils.isNullOrEmpty(queryParam.get("soNo"))) {
	            sql.append(" and A.SO_NO like ?");
	            params.add("%" + queryParam.get("soNo") + "%");
	        }
	        if (!StringUtils.isNullOrEmpty(queryParam.get("soStatus"))) {
	            sql.append(" and A.SO_STATUS= ?");
	            params.add(queryParam.get("soStatus"));
	        }
	        if (!StringUtils.isNullOrEmpty(queryParam.get("vin"))) {
	            sql.append(" and A.VIN like ?");
	            params.add("%" + queryParam.get("vin") + "%");
	        }
	        sql.append(DAOUtilGF.getOwnedByStr("A", loginInfo.getUserId(), loginInfo.getOrgCode(),  "70106000", loginInfo.getDealerCode()));
	      //替换权限
        sql.append(")TA1  LEFT JOIN ("+CommonConstants.VM_PART_CUSTOMER+") P ON TA1.DEALER_CODE=P.DEALER_CODE AND TA1.CONSIGNEE_CODE=P.CUSTOMER_CODE) AS W");
        sql.append(" LEFT JOIN ( SELECT C.DEALER_CODE,C.VIN,C.ENGINE_NO,C.KEY_NUMBER,C.CERTIFICATE_NUMBER,c.financial_bill_no,c.supervise_type"); 
        sql.append(" FROM TM_VS_STOCK C WHERE 1=1 AND C.D_KEY = "+DictCodeConstants.D_KEY+ " AND C.DEALER_CODE = '"+ loginInfo.getDealerCode()+"' AND C.DISPATCHED_STATUS = "+ DictCodeConstants.DICT_DISPATCHED_STATUS_HAVE_DISPATCHED+ " UNION ALL SELECT DEALER_CODE,VIN,ENGINE_NO,KEY_NUMBER,CERTIFICATE_NUMBER,financial_bill_no,supervise_type FROM TT_VS_SHIPPING_NOTIFY D"); 
        sql.append(" WHERE NOT EXISTS(SELECT E.DEALER_CODE,E.VIN FROM TM_VS_STOCK E WHERE E.DEALER_CODE = D.DEALER_CODE AND E.VIN = D.VIN ) ) TA2  ON W.VIN = TA2.VIN ");
        sql.append(" WHERE 1=1");
        if (!StringUtils.isNullOrEmpty(queryParam.get("customerName"))) {
            sql.append(" and W.CUSTOMER_NAME like ?");
            params.add("%" + queryParam.get("customerName") + "%");
        }
        
        sql.append(" ORDER BY SO_STATUS");
        System.out.println(sql);
		PageInfoDto pageInfoDto = DAOUtil.pageQuery(sql.toString(),params);
		return pageInfoDto;
	}

	/**
	 * 修改VIN号
	 */
	@Override
	public void updateVIN(Long id, SalesOrderDTO salesOrderDTO) throws ServiceBizException {
		SalesOrderPO salesOrderPO = SalesOrderPO.findById(id);
		this.updateStockDispatched_status(salesOrderDTO, salesOrderPO);
		salesOrderPO.setString("SO_NO", salesOrderDTO.getSoNo());// 销售单号
		salesOrderPO.setString("CUSTOMER_NO", salesOrderDTO.getCustomerNo());// 客户编号
		salesOrderPO.setString("CUSTOMER_NAME", salesOrderDTO.getCustomerName());// 客户名称
		salesOrderPO.setDate("SHEET_CREATE_DATE", salesOrderDTO.getSheetCreateDate());// 开单日期
		salesOrderPO.setString("SHEET_CREATED_BY", salesOrderDTO.getSheetCreatedBy());// 开单人
		salesOrderPO.setDate("CONTRACT_DATE", salesOrderDTO.getContractDate());// 签约日期
		salesOrderPO.setString("CONTRACT_NO", salesOrderDTO.getContractNo());// 合约编号
		salesOrderPO.setDouble("CONTRACT_EARNEST", salesOrderDTO.getContractEarnest());// 合约定金
		salesOrderPO.setString("CONSULTANT", salesOrderDTO.getConsultant());// 销售顾问

		salesOrderPO.setDate("DELIVERING_DATE", salesOrderDTO.getDeliveringDate());// 预交车时间
		salesOrderPO.setString("PAY_MODE", salesOrderDTO.getPayMode());// 支付方式
		salesOrderPO.setString("VS_STOCK_ID", salesOrderDTO.getVsStockId());// 库存id
		salesOrderPO.setInteger("PRODUCT_ID", salesOrderDTO.getProductId());
		salesOrderPO.setDouble("VEHICLE_PRICE", salesOrderDTO.getVehiclePrice());// 车辆价格
		salesOrderPO.setDouble("UPHOLSTER_SUM", salesOrderDTO.getUpholsterSum());// 装潢金额
		salesOrderPO.setDouble("PRESENT_SUM", salesOrderDTO.getPresentSum());// 赠送金额
		salesOrderPO.setDouble("SERVICE_SUM", salesOrderDTO.getServiceSum());// 服务项目金额
		salesOrderPO.setDouble("OFFSET_AMOUNT", salesOrderDTO.getOffsetAmount());// 抵扣金额
		salesOrderPO.setDouble("ORDER_SUM", salesOrderDTO.getOrderSum());// 订单总额
		salesOrderPO.setDouble("ORDER_RECEIVABLE_SUM", salesOrderDTO.getOrderReceivableSum());// 订单应收
		salesOrderPO.setString("OLD_SO_NO", salesOrderDTO.getOldSoNo());// 原销售订单号
		salesOrderPO.setString("RETURN_REASON", salesOrderDTO.getReturnReason());// 退回原因
		salesOrderPO.setDouble("PENALTY_AMOUNT", salesOrderDTO.getPenaltyAmount());// 违约金
		salesOrderPO.setDouble("RO_RECEIVABLE_SUM", salesOrderDTO.getRoReceivableSum());// 退单应收
		salesOrderPO.saveIt();
	}


	
    /**
    * @author LiGaoqi
    * @date 2017年2月7日
    * @param ConfirmCarDTO
    * @throws ServiceBizException
    * (non-Javadoc)
    * @see com.yonyou.dms.retail.service.ordermanage.ConfirmcarService#updateCustomerInfo(com.yonyou.dms.common.domains.DTO.basedata.ConfirmCarAndUpdateCustomerDTO)
    */
    	
    @Override
    public void updateCustomerInfo(ConfirmCarAndUpdateCustomerDTO ConfirmCarDTO) throws ServiceBizException {
        List<Map> result = this.searchConfirm(ConfirmCarDTO.getUsoNo());
        boolean isColorChange = false;
        LoginInfoDto loginInfo = ApplicationContextHelper.getBeanByType(LoginInfoDto.class);
        if(!StringUtils.isEquals(result.get(0).get("CUSTOMER_NAME"), ConfirmCarDTO.getCustomerName())||!StringUtils.isEquals(result.get(0).get("CONTACTOR_MOBILE"), ConfirmCarDTO.getContactorMobile())
                ||!StringUtils.isEquals(result.get(0).get("CERTIFICATE_NO"), ConfirmCarDTO.getCertificateNo())||!StringUtils.isEquals(result.get(0).get("CUSTOMER_TYPE"), ConfirmCarDTO.getCustomerType().toString())
                ||!StringUtils.isEquals(result.get(0).get("GENDER"), ConfirmCarDTO.getGender().toString())||!StringUtils.isEquals(result.get(0).get("ADDRESS"), ConfirmCarDTO.getAddress())
                ||!StringUtils.isEquals(result.get(0).get("ZIP_CODE"), ConfirmCarDTO.getZipCode())||!StringUtils.isEquals(result.get(0).get("E_MAIL"), ConfirmCarDTO.geteMail())
                ||!StringUtils.isEquals(result.get(0).get("COLOR"), ConfirmCarDTO.getColor())||!StringUtils.isEquals(result.get(0).get("LINKED_NAME"), ConfirmCarDTO.getLinkedName())
                ||!StringUtils.isEquals(result.get(0).get("ZS_MANAGER"), ConfirmCarDTO.getZsManager())||!StringUtils.isEquals(result.get(0).get("CT_CODE"), ConfirmCarDTO.getCtCode().toString())){
            if(!StringUtils.isEquals(result.get(0).get("COLOR"), ConfirmCarDTO.getColor())){
                isColorChange=true;
                
            }
          //根据 订单号 和vin 在销售订单中 获取 潜客号
            SalesOrderPO salesPo = SalesOrderPO.findByCompositeKeys(loginInfo.getDealerCode(),ConfirmCarDTO.getUsoNo());
            if(!StringUtils.isNullOrEmpty(salesPo)){
                if(!StringUtils.isNullOrEmpty(salesPo.getInteger("BUSINESS_TYPE"))&&salesPo.getInteger("BUSINESS_TYPE")!=13001004){
                    if(!StringUtils.isNullOrEmpty(ConfirmCarDTO.getLinkedName())){
                        List<Object> linkList = new ArrayList<Object>();
                        linkList.add(salesPo.getString("CUSTOMER_NO"));
                        linkList.add(loginInfo.getDealerCode());
                        linkList.add(ConfirmCarDTO.getLinkedName());
                        linkList.add(DictCodeConstants.STATUS_IS_YES);
                        linkList.add(Integer.parseInt(DictCodeConstants.D_KEY));
                        List<TtPoCusLinkmanPO> liknPo = TtPoCusLinkmanPO.findBySQL("select * from TT_PO_CUS_LINKMAN where CUSTOMER_NO= ? AND DEALER_CODE= ? AND CONTACTOR_NAME= ? AND IS_DEFAULT_CONTACTOR= ? AND D_KEY= ? ", linkList.toArray());
                        if(liknPo!=null&&liknPo.size()>0){
                            for(int i=0;i<liknPo.size();i++){
                                TtPoCusLinkmanPO po = liknPo.get(i);
                                po.setString("CONTACTOR_NAME", ConfirmCarDTO.getLinkedName());
                                po.saveIt();
                            }
                        }
                    }else{
                        TtPoCusLinkmanPO po = new TtPoCusLinkmanPO();
                        po.setString("CONTACTOR_NAME", ConfirmCarDTO.getLinkedName());
                        po.setString("CUSTOMER_NO", salesPo.getString("CUSTOMER_NO"));
                        po.setString("MOBILE", ConfirmCarDTO.getContactorMobile());
                        po.setInteger("D_KEY", Integer.parseInt(DictCodeConstants.D_KEY));
                        po.setInteger("IS_DEFAULT_CONTACTOR", DictCodeConstants.STATUS_IS_YES);
                        po.setString("OWNED_BY", loginInfo.getUserId().toString());
                        po.saveIt();
                    }
                }
            }
          //校验是否存在保有客户信息
          //根据VIN查TM_VEHICLE,查CUSTOMER_NO有没信息,有：已经有保佑客户信息,否:没有保佑客户信息
            String customerNo = null;
            String ownerNo = null;
            VehiclePO vPO = VehiclePO.findByCompositeKeys(ConfirmCarDTO.getuVin(),loginInfo.getDealerCode());
            if(vPO!=null){
                customerNo = vPO.getString("CUSTOMER_NO");
            }
            System.out.println("交车走着有保客");
            if(!StringUtils.isNullOrEmpty(customerNo)&&!StringUtils.isNullOrEmpty(salesPo.getInteger("BUSINESS_TYPE"))&&salesPo.getInteger("BUSINESS_TYPE")!=13001004){
              //如果存在保有客户信息则 必须将用户保修登记卡中修改的信息同步到潜客和保有客户表中
                List<Object> CusPOList = new ArrayList<Object>();
                CusPOList.add(loginInfo.getDealerCode());
                CusPOList.add(Integer.parseInt(DictCodeConstants.D_KEY));
                CusPOList.add(result.get(0).get("PO_CUSTOMER_NO"));
             
               
                List<PotentialCusPO> cusPo = PotentialCusPO.findBySQL("select * from TM_POTENTIAL_CUSTOMER where  DEALER_CODE= ? AND D_KEY= ? AND CUSTOMER_NO= ? ", CusPOList.toArray());
                if(cusPo!=null&&cusPo.size()>0){
                    for(int j=0;j<cusPo.size();j++){
                        PotentialCusPO cus = cusPo.get(j);
                        cus.setString("CUSTOMER_NAME", ConfirmCarDTO.getCustomerName());
                        cus.setInteger("CUSTOMER_TYPE", ConfirmCarDTO.getCustomerType());
                       
                       cus.setString("CONTACTOR_MOBILE", ConfirmCarDTO.getContactorMobile());
                       
                       System.out.println("保存11");
                        cus.setInteger("GENDER", ConfirmCarDTO.getGender());
                        cus.setString("ADDRESS", ConfirmCarDTO.getAddress());
                        cus.setInteger("CT_CODE", ConfirmCarDTO.getCtCode());
                        cus.setString("CERTIFICATE_NO", ConfirmCarDTO.getCertificateNo());
                        cus.setString("ZIP_CODE", ConfirmCarDTO.getZipCode());
                        cus.setString("E_MAIL", ConfirmCarDTO.geteMail());
                        cus.saveIt();
                        
                    }
                }
              //保有
                List<Object> CustomerPOList = new ArrayList<Object>();
                CustomerPOList.add(loginInfo.getDealerCode());
                CustomerPOList.add(Integer.parseInt(DictCodeConstants.D_KEY));
                CustomerPOList.add(customerNo);
          
               
                List<CustomerPO> customerPo = CustomerPO.findBySQL("select * from TM_CUSTOMER where  DEALER_CODE= ? AND D_KEY= ? AND CUSTOMER_NO= ? ", CustomerPOList.toArray());
                if(customerPo!=null&&customerPo.size()>0){
                    for(int k=0;k<customerPo.size();k++){
                        CustomerPO customer = customerPo.get(k);
                        customer.setString("CUSTOMER_NAME", ConfirmCarDTO.getCustomerName());
                        customer.setInteger("CUSTOMER_TYPE", ConfirmCarDTO.getCustomerType());
                        customer.setString("CONTACTOR_MOBILE", ConfirmCarDTO.getContactorMobile());
                        customer.setInteger("GENDER", ConfirmCarDTO.getGender());
                        customer.setString("ADDRESS", ConfirmCarDTO.getAddress());
                        customer.setInteger("CT_CODE", ConfirmCarDTO.getCtCode());
                        customer.setString("CERTIFICATE_NO", ConfirmCarDTO.getCertificateNo());
                        customer.setString("ZIP_CODE", ConfirmCarDTO.getZipCode());
                        customer.setString("E_MAIL", ConfirmCarDTO.geteMail());
                        customer.saveIt();
                    }
                }
                //
            }else{
                if(!StringUtils.isNullOrEmpty(salesPo.getInteger("BUSINESS_TYPE"))&&salesPo.getInteger("BUSINESS_TYPE")!=13001004){
                    List<Object> CusPOList = new ArrayList<Object>();
                    CusPOList.add(loginInfo.getDealerCode());
                    CusPOList.add(Integer.parseInt(DictCodeConstants.D_KEY));

                    System.out.println("交车走着没有保客");
                    
                    CusPOList.add(result.get(0).get("PO_CUSTOMER_NO").toString());
                    System.out.println("潜客编号"+result.get(0).get("PO_CUSTOMER_NO"));
               
                    List<PotentialCusPO> cusPo = PotentialCusPO.findBySQL("select * from TM_POTENTIAL_CUSTOMER where  DEALER_CODE= ? AND D_KEY= ? AND CUSTOMER_NO= ? ", CusPOList.toArray());
                    if(cusPo!=null&&cusPo.size()>0){
                        for(int j=0;j<cusPo.size();j++){
                            PotentialCusPO cus = cusPo.get(j);
                            cus.setString("CUSTOMER_NAME", ConfirmCarDTO.getCustomerName());
                            cus.setInteger("CUSTOMER_TYPE", ConfirmCarDTO.getCustomerType());
                           
                            cus.setString("CONTACTOR_MOBILE", ConfirmCarDTO.getContactorMobile());
                           
                            cus.setInteger("GENDER", ConfirmCarDTO.getGender());
                            cus.setString("ADDRESS", ConfirmCarDTO.getAddress());
                            cus.setInteger("CT_CODE", ConfirmCarDTO.getCtCode());
                            cus.setString("CERTIFICATE_NO", ConfirmCarDTO.getCertificateNo());
                            cus.setString("ZIP_CODE", ConfirmCarDTO.getZipCode());
                            cus.setString("E_MAIL", ConfirmCarDTO.geteMail());
                            System.out.println("保存10");
                            cus.saveIt();
                        }
                    }
                }
                
            }
            SalesOrderPO so1 = SalesOrderPO.findByCompositeKeys(loginInfo.getDealerCode(),ConfirmCarDTO.getUsoNo());
            so1.setString("CUSTOMER_NAME", ConfirmCarDTO.getCustomerName());
            so1.setInteger("CUSTOMER_TYPE", ConfirmCarDTO.getCustomerType());
            so1.setString("ADDRESS", ConfirmCarDTO.getAddress());
            so1.setInteger("CT_CODE", ConfirmCarDTO.getCtCode());
            so1.setString("CERTIFICATE_NO", ConfirmCarDTO.getCertificateNo());
            so1.saveIt();
//          车主信息是否存在 存在则同时进行修改
            List<Object> vehicleList = new ArrayList<Object>();
            vehicleList.add(loginInfo.getDealerCode());
            vehicleList.add(ConfirmCarDTO.getuVin());
            List<VehiclePO> vehiclePo = VehiclePO.findBySQL("select * from TM_VEHICLE where  DEALER_CODE= ? AND VIN= ? AND OWNER_NO NOT LIKE '%888888888888%' ", vehicleList.toArray());
            if(vehiclePo!=null&&vehiclePo.size()>0){
                VehiclePO vePo = vehiclePo.get(0);
                ownerNo = vePo.getString("OWNER_NO");
            }
            if(!StringUtils.isNullOrEmpty(ownerNo)&&!StringUtils.isNullOrEmpty(salesPo.getInteger("BUSINESS_TYPE"))&&salesPo.getInteger("BUSINESS_TYPE")!=13001004){
                CarownerPO ownerPO = CarownerPO.findByCompositeKeys(ownerNo,loginInfo.getDealerCode());
                if(ownerPO!=null){
                ownerPO.setString("OWNER_NAME", ConfirmCarDTO.getCustomerName());
                ownerPO.setString("Mobile", ConfirmCarDTO.getContactorMobile());
                ownerPO.setInteger("GENDER", ConfirmCarDTO.getGender());
                ownerPO.setString("ADDRESS", ConfirmCarDTO.getAddress());
                ownerPO.setInteger("CT_CODE", ConfirmCarDTO.getCtCode());
                ownerPO.setString("CERTIFICATE_NO", ConfirmCarDTO.getCertificateNo());
                ownerPO.setString("ZIP_CODE", ConfirmCarDTO.getZipCode());
                ownerPO.setString("E_MAIL", ConfirmCarDTO.geteMail());
                ownerPO.saveIt();
                }
            }
            //
            if(isColorChange&&!StringUtils.isNullOrEmpty(salesPo.getInteger("BUSINESS_TYPE"))&&salesPo.getInteger("BUSINESS_TYPE")!=13001004){
                VehiclePO vPO1 = VehiclePO.findByCompositeKeys(ConfirmCarDTO.getuVin(),loginInfo.getDealerCode());
                if(vPO1!=null){
                    vPO1.setString("COLOR", ConfirmCarDTO.getColor());
                    vPO1.saveIt();
                }
                SalesOrderPO orderPO2 = SalesOrderPO.findByCompositeKeys(loginInfo.getDealerCode(),ConfirmCarDTO.getUsoNo());
                if(orderPO2!=null){
                    orderPO2.setString("COLOR_CODE", ConfirmCarDTO.getColor());
                    orderPO2.saveIt();
                }
            }
            if(!StringUtils.isNullOrEmpty(ConfirmCarDTO.getZsManager())&&!StringUtils.isNullOrEmpty(salesPo.getInteger("BUSINESS_TYPE"))&&salesPo.getInteger("BUSINESS_TYPE")!=13001004){
                VehiclePO vPO2 = VehiclePO.findByCompositeKeys(ConfirmCarDTO.getuVin(),loginInfo.getDealerCode());
                if(vPO2!=null){
                    vPO2.setString("SERVICE_ADVISOR", loginInfo.getEmployeeNo());
                    vPO2.saveIt();
                }
            }
        }
        List<Map> resault =this.isInWay(ConfirmCarDTO.getuVin(), loginInfo.getDealerCode());
        if(!StringUtils.isNullOrEmpty(resault)&&resault.size()>0){ throw new
             ServiceBizException("车辆"+ConfirmCarDTO.getuVin()+"为在途车,不能交车确认！"); 
        }
        SalesOrderPO salesOrder = SalesOrderPO.findByCompositeKeys(loginInfo.getDealerCode(),ConfirmCarDTO.getUsoNo());
        if(salesOrder!=null){
            if(salesOrder.getInteger("BUSINESS_TYPE").toString().equals(DictCodeConstants.DICT_SO_TYPE_SERVICE)){
                salesOrder.setInteger("SO_STATUS",Integer.parseInt(DictCodeConstants.DICT_SO_STATUS_HAVE_OUT_STOCK));
                salesOrder.setInteger("IS_UPLOAD", Integer.parseInt(DictCodeConstants.DICT_IS_NO)); 
                salesOrder.setDate("CONFIRMED_DATE", new Date());
                salesOrder.setString("CONFIRMED_BY", loginInfo.getUserId());
                }else{
                salesOrder.setInteger("SO_STATUS",Integer.parseInt(DictCodeConstants.DICT_SO_STATUS_HAVE_CONFIRMED)); 
                salesOrder.setDate("CONFIRMED_DATE", new Date());
                salesOrder.setString("CONFIRMED_BY", loginInfo.getUserId());
                }
            salesOrder.setInteger("D_KEY", Integer.parseInt(DictCodeConstants.D_KEY));
            salesOrder.saveIt();
         // 新增提醒信息 
            String msgInfo = "交车提醒 销售订单号:" + ConfirmCarDTO.getUsoNo() +";VIN:" + ConfirmCarDTO.getuVin();
            TtAlertMsgPO alertMsgPO = new TtAlertMsgPO(); 
            alertMsgPO.setString("MSG_INFO", msgInfo);
            alertMsgPO.setDate("MSG_TIMESTAMP", new Date());
            alertMsgPO.setInteger("ALERT_MSG_TYPE",
            Integer.parseInt(DictCodeConstants.DICT_SO_STATUS_HAVE_CONFIRMED));
            alertMsgPO.setInteger("D_KEY", Integer.parseInt(DictCodeConstants.D_KEY));
            alertMsgPO.saveIt(); 
            // 查询映射配置 
            List<Object> mappingList = new ArrayList<Object>();
            mappingList.add(Integer.parseInt(DictCodeConstants.DICT_SO_STATUS_HAVE_CONFIRMED));
            mappingList.add(Integer.parseInt(DictCodeConstants.D_KEY));
            mappingList.add(loginInfo.getDealerCode()); 
            List<TtMsgTypeMappingPO> mappingPO=TtMsgTypeMappingPO.findBySQL("select * from TT_MSG_TYPE_MAPPING where ALERT_MSG_TYPE= ? AND D_KEY= ? AND DEALER_CODE= ? ", mappingList.toArray());
            if(mappingPO!=null&&mappingPO.size()>0){
                for(int j=0;j<mappingPO.size();j++){ 
                    TtMsgTypeMappingPO mapping = mappingPO.get(j); 
                    // 配置提醒人
                    TtAlertMsgMappingPO newPo = new TtAlertMsgMappingPO(); 
                    newPo.setString("USER_ID",loginInfo.getEmployeeNo()); 
                    newPo.setLong("MSG_ID", alertMsgPO.getLongId());
                    newPo.setInteger("IS_READ", Integer.parseInt(DictCodeConstants.DICT_IS_NO));
                    newPo.setString("USER_ID", loginInfo.getEmployeeNo()); 
                    newPo.setInteger("D_KEY",Integer.parseInt(DictCodeConstants.D_KEY)); 
                    newPo.saveIt();
                    } 
                }
          //新增订单审核历史
            if(!StringUtils.isNullOrEmpty(salesOrder.getInteger("BUSINESS_TYPE"))&&(salesOrder.getInteger("BUSINESS_TYPE")==Integer.parseInt(DictCodeConstants.DICT_SO_TYPE_ENTRUST_DELIVERY)
                    ||salesOrder.getInteger("BUSINESS_TYPE")==Integer.parseInt(DictCodeConstants.DICT_SO_TYPE_GENERAL))){ 
                TtOrderStatusUpdatePO orderStatusUpdatePO = new TtOrderStatusUpdatePO(); 
                orderStatusUpdatePO.setString("SO_NO", ConfirmCarDTO.getUsoNo());
                orderStatusUpdatePO.setInteger("SO_STATUS",Integer.parseInt(DictCodeConstants.DICT_SO_STATUS_HAVE_CONFIRMED));
                orderStatusUpdatePO.setString("OWNED_BY", loginInfo.getUserId().toString());
                orderStatusUpdatePO.setInteger("IS_UPLOAD", Integer.parseInt(DictCodeConstants.DICT_IS_NO));
                orderStatusUpdatePO.setDate("ALTERATION_TIME", new Date());
                orderStatusUpdatePO.setInteger("D_KEY", Integer.parseInt(DictCodeConstants.D_KEY));
                orderStatusUpdatePO.saveIt(); 
                }
         // 修改车辆配车状态 
            VsStockPO stockPO = VsStockPO.findByCompositeKeys(loginInfo.getDealerCode(),ConfirmCarDTO.getuVin());
            stockPO.setInteger("D_KEY", Integer.parseInt(DictCodeConstants.D_KEY));
            stockPO.setInteger("DISPATCHED_STATUS",DictCodeConstants.DICT_DISPATCHED_STATUS_DELIVERY_CONFIRM); 
            stockPO.saveIt();
            SalesOrderPO order = SalesOrderPO.findByCompositeKeys(loginInfo.getDealerCode(),ConfirmCarDTO.getUsoNo());
            int customerIsNotArrived=1; //自动出库
            if(!StringUtils.isNullOrEmpty(commonNoService.getDefalutPara("8004"))&&commonNoService.getDefalutPara("8004").equals("12781001")){ //客户信息是否到店参数控制
                if(!StringUtils.isNullOrEmpty(commonNoService.getDefalutPara("8055"))&&commonNoService.getDefalutPara("8055").equals("12781001")){ 
                    // 客户信息为未到店不能做出库操作
                    PotentialCusPO cusPO = PotentialCusPO.findByCompositeKeys(loginInfo.getDealerCode(),order.getString("CUSTOMER_NO"));
                    if(!StringUtils.isNullOrEmpty(cusPO)){ 
                        if(!StringUtils.isNullOrEmpty(cusPO.getInteger("IS_TO_SHOP"))&&cusPO.getInteger("IS_TO_SHOP")!=12781001){
                        customerIsNotArrived=0; 
                        } 
                    } 
                    if(customerIsNotArrived==1){ // 自动出库
                        if(!StringUtils.isNullOrEmpty(order.getInteger("IS_SPEEDINESS"))&&order.getInteger("IS_SPEEDINESS")!=DictCodeConstants.STATUS_IS_YES){
                            if(!salesOrder.getInteger("BUSINESS_TYPE").toString().equals(DictCodeConstants.DICT_SO_TYPE_SERVICE)){ // return
                                System.out.println("保存17");
                                this.executeMaintainVehcileOutSystem(ConfirmCarDTO.getUsoNo(),loginInfo.getDealerCode(),loginInfo.getUserId().toString()); 
                                }
                            }
                        } 
                    }else{ // 自动出库
                        if(!StringUtils.isNullOrEmpty(order.getInteger("IS_SPEEDINESS"))&&order.getInteger("IS_SPEEDINESS")!=DictCodeConstants.STATUS_IS_YES){

                            if(!salesOrder.getInteger("BUSINESS_TYPE").toString().equals(DictCodeConstants.DICT_SO_TYPE_SERVICE)){ // return
                                this.executeMaintainVehcileOutSystem(ConfirmCarDTO.getUsoNo(),loginInfo.getDealerCode(),loginInfo.getUserId().toString()); 
                                
                            } 
                    
                        } 
                     
                    }
                }
        }
        //上报接口DSO0302 参数：SO_NO，VIN，IS_SECOND_TIME：12781002，IS_CONFIRMED：12781002
                 
    }

    public List<Map> isInWay(String vin, String entityCode) {
        String sql = null;
        sql = "SELECT * FROM TT_VS_SHIPPING_NOTIFY WHERE DEALER_CODE = '" + entityCode + "' AND VIN ='" + vin + "' "+ " AND VIN not in (select vin From TM_VS_STOCK where DEALER_CODE = '" + entityCode + "')";
        List<Object> queryParams = new ArrayList<Object>();
        return Base.findAll(sql.toString(), queryParams.toArray());
    }
    
    
    public List<Map> findAllocationApplyByCnd(String soNo, String entityCode) {
        StringBuffer sql = new StringBuffer();
        sql.append("select a.* from Tt_Allocation_Apply a,TT_SALES_ORDER s where a.DEALER_CODE='"+entityCode+"' and a.DEALER_CODE=S.DEALER_CODE ");
        sql.append("and a.APPLY_NO=s.APPLY_NO and s.SO_NO='"+soNo+"' and s.BUSINESS_TYPE="+DictCodeConstants.DICT_SO_TYPE_ALLOCATION+" ");
        sql.append("and a.CONSIGNEE_CODE = '"+entityCode+"'");
        List<Object> queryParams = new ArrayList<Object>();
        return Base.findAll(sql.toString(), queryParams.toArray());
    }
    
    public void executeMaintainVehcileOutSystem(String soNo, String dealerCode,
                                               String userId) throws ServiceBizException {
        SalesOrderPO order = SalesOrderPO.findByCompositeKeys(dealerCode, soNo);
        Date OutPutSalesDate = null;
        Date dSalesDate = new Date();
        int i = 0;
        if (!StringUtils.isNullOrEmpty(commonNoService.getDefalutPara("3339"))
            && commonNoService.getDefalutPara("3339").equals("12781001")) {// 保修手册号码必须填
            VsStockPO stockPO = VsStockPO.findByCompositeKeys(dealerCode, order.getString("VIN"));
            if (!StringUtils.isNullOrEmpty(stockPO)
                && stockPO.getInteger("STOCK_STATUS") == DictCodeConstants.DICT_STORAGE_STATUS_IN_STORAGE) {
                if (!StringUtils.isNullOrEmpty(stockPO.getString("WARRANTY_NUMBER"))) {
                    i = 1;
                   
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
            System.out.println("// 创建出库单明细");
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
                System.out.println("// 创建出库单明细1");
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
            System.out.println("// 创建出库单明细3");
            VsStockPO stock = VsStockPO.findByCompositeKeys(dealerCode, order.getString("VIN"));
            // 反向检查车辆是否已经做过出库(车辆库存根据vin,和 库存状态 出库)
            if (!StringUtils.isNullOrEmpty(stock)
                && stock.getInteger("STOCK_STATUS") == DictCodeConstants.DICT_STORAGE_STATUS_MOVE_OUT) {
                throw new ServiceBizException("该" + order.getString("VIN") + "VIN码车辆已经出库！");
            }
            // 【车辆出库时销售日期有值就取销售日期，没有就查费用类型是购车费用的发票，有就取这个开票日期，没有就取当前日期】定义成【A】
            // ,车辆出库自动算保险和保修日期的开始日期就以【A】为准。
            if (!StringUtils.isNullOrEmpty(order.getString("VIN"))) {
                System.out.println("// 创建出库单明细5");
                VehiclePO carPO = VehiclePO.findByCompositeKeys(order.getString("VIN"), dealerCode);
                if (carPO != null) {
                    if (!StringUtils.isNullOrEmpty(carPO.getDate("SALES_DATE"))) {
                        System.out.println("// 创建出库单明细6");
                        List<Object> invoicerList = new ArrayList<Object>();
                        invoicerList.add(order.getString("SO_NO"));
                        invoicerList.add(order.getString("VIN"));
                        invoicerList.add(dealerCode);
                        invoicerList.add(Integer.parseInt(DictCodeConstants.D_KEY));
                        SoInvoicePO invoicerPo = SoInvoicePO.findFirst(" SO_NO= ? AND VIN= ? AND DEALER_CODE= ? AND D_KEY= ? ",
                                                                       invoicerList.toArray());
                        if (!StringUtils.isNullOrEmpty(invoicerPo)) {
                            if (!StringUtils.isNullOrEmpty(invoicerPo.getDate("INVOICE_DATE"))) {
                                System.out.println("// 创建出库单明细7");
                                dSalesDate = invoicerPo.getDate("INVOICE_DATE");
                                OutPutSalesDate = dSalesDate;
                            }
                        } else {
                            dSalesDate = carPO.getDate("SALES_DATE");
                        }

                    }
                }
            }
            // 同步更新车辆的省市县
            if (!StringUtils.isNullOrEmpty(order.getString("VIN"))
                && !StringUtils.isNullOrEmpty(order.getString("SO_NO"))) {
                if (!StringUtils.isNullOrEmpty(commonNoService.getDefalutPara("1300"))
                    && commonNoService.getDefalutPara("1300").equals("12781001")) {
                    System.out.println("// 创建出库单明细8");
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
                System.out.println("// 创建出库单明细9");
                stock.setInteger("STOCK_OUT_TYPE", deliveryPO.getInteger("STOCK_OUT_TYPE"));
                stock.setString("SD_NO", deliveryPO.getString("SD_NO"));
            }
            stock.setString("SO_NO", order.getString("SO_NO"));
            stock.setInteger("STOCK_STATUS", DictCodeConstants.DICT_STORAGE_STATUS_MOVE_OUT);
            stock.setInteger("DISPATCHED_STATUS", DictCodeConstants.DICT_DISPATCHED_STATUS_HAVE_DELIVER);
            stock.setString("LAST_STOCK_OUT_BY", userId);
            stock.setDate("LATEST_STOCK_OUT_DATE", new Date());
            stock.setDate("FIRST_STOCK_OUT_DATE", new Date());
            stock.saveIt();
            // 修改销售定单字段
            //如果销售出库回写销售订单中是否上报写为否12781002
            SalesOrderPO uorder = SalesOrderPO.findByCompositeKeys(dealerCode, soNo);
            uorder.setString("STOCK_OUT_BY", userId);
            uorder.setDate("STOCK_OUT_DATE", new Date());
            uorder.setInteger("SO_STATUS", Integer.parseInt(DictCodeConstants.DICT_SO_STATUS_HAVE_OUT_STOCK));
            uorder.setInteger("IS_UPLOAD", Integer.parseInt(DictCodeConstants.DICT_IS_NO));
            uorder.saveIt();
            System.out.println("// 创建出库单明细10");
          //修改QCSales中的出库日期
            List<Object> wsItemList = new ArrayList<Object>();
            wsItemList.add(dealerCode);
            wsItemList.add(order.getString("SO_NO"));
            List<QcsArchivesPO> wsItemPo = QcsArchivesPO.findBySQL("select * from TT_QCS_ARCHIVES where DEALER_CODE= ? AND SO_NO= ? ",
                                                                   wsItemList.toArray());
            if (wsItemPo != null && wsItemPo.size() > 0) {
                System.out.println("// 创建出库单明细11");
                for (int k = 0; k < wsItemPo.size(); k++) {
                    QcsArchivesPO qscPo = wsItemPo.get(k);
                    qscPo.setInteger("IS_PDI_START", DictCodeConstants.STATUS_IS_YES);
                    qscPo.setDate("PDI_START_DATE", new Date());
                    qscPo.saveIt();
                }

            }
            System.out.println("// 创建出库单明细12");
            // 一般订单 委托订单记录订单审核历史
            if (!StringUtils.isNullOrEmpty(order.getInteger("BUSINESS_TYPE"))
                && (order.getInteger("BUSINESS_TYPE") == Integer.parseInt(DictCodeConstants.DICT_SO_TYPE_GENERAL)
                    || order.getInteger("BUSINESS_TYPE") == Integer.parseInt(DictCodeConstants.DICT_SO_TYPE_ENTRUST_DELIVERY))) {
                TtOrderStatusUpdatePO orderStatusUpdatePO = new TtOrderStatusUpdatePO();
                orderStatusUpdatePO.setString("SO_NO", order.getString("SO_NO"));
                orderStatusUpdatePO.setInteger("SO_STATUS",
                                               Integer.parseInt(DictCodeConstants.DICT_SO_STATUS_HAVE_OUT_STOCK));
                orderStatusUpdatePO.setString("OWNED_BY", userId);
                orderStatusUpdatePO.setInteger("IS_UPLOAD", Integer.parseInt(DictCodeConstants.DICT_IS_NO));
                orderStatusUpdatePO.setDate("ALTERATION_TIME", new Date());
                orderStatusUpdatePO.setInteger("D_KEY", Integer.parseInt(DictCodeConstants.D_KEY));
                orderStatusUpdatePO.saveIt();
                System.out.println("// 创建出库单明细13");
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
                        sPlanPo.setInteger("IS_AUDITING", DictCodeConstants.STATUS_IS_NOT);
                        sPlanPo.setString("SOLD_BY", potentialPo.getString("SOLD_BY"));
                        sPlanPo.setString("OWNED_BY", potentialPo.getString("SOLD_BY"));
                        sPlanPo.saveIt();

                    }
                }
                /**
                 * 根据VIN判断是否有保有客户信息，没有则新增把潜在客户信息保存到保有客户信息中
                 */
                String customerNOB = insertIntoCustomer(dealerCode, order.getString("VIN"), order.getString("SO_NO"),
                                                        order.getString("CUSTOMER_NO"), userId);
                /**
                 * 跟进CR关怀
                 */
                SystemTrack(customerNOB,order.getString("VIN"),dealerCode,userId);
             // 2潜在客户意向表中是否完成意向为是
                List<Object> queryList = new ArrayList<Object>();
                queryList.add(potentialPo.getString("CUSTOMER_NO"));
                queryList.add(Integer.parseInt(DictCodeConstants.D_KEY));
                queryList.add(dealerCode);          
                List<TtCusIntentPO> intentpo = TtCusIntentPO.findBySQL("select * from TT_CUSTOMER_INTENT where CUSTOMER_NO= ? AND D_KEY= ? AND DEALER_CODE= ? ",queryList.toArray());           
                if(intentpo!=null&&intentpo.size()>0){
                    for(int j=0;j<intentpo.size();j++){
                        TtCusIntentPO intent = intentpo.get(j);
                        intent.setInteger("INTENT_FINISHED", DictCodeConstants.STATUS_IS_YES);
                        intent.saveIt();
                    }
                }
                /**
                 * 回写维修工单的车主编号，姓名，车主性质，判断lock_user是否为空，不为空的则不能出库，抛异常
                 * 回写配件销售单 客户名称，客户姓名
                 */
                /**
                 * 根据客户编号在车辆资料表里查是否有记录,有则修改,无则增加,
                 * 根据查出来相对应的车主编号在车主表里查是否有记录,有则修改,无则增加
                 * 2008-05-29
                 * 
                 * @author wanghui
                 */
                /**
                 * 检测是否已经在保存保有客户信息中生成了车主记录
                 */
                String Tag = "";
                List<Map> ownerList = this.checkOwnerInfo(order.getString("VIN"), dealerCode);
                if(StringUtils.isNullOrEmpty(ownerList)){
                    Tag = DictCodeConstants.DICT_IS_YES;
                }
                if(DictCodeConstants.DICT_IS_YES.equals(Tag.trim())){
                    String ownerNo=commonNoService.getSystemOrderNo(CommonConstants.OWNER_PREFIX);
       
                    
                    //
                    CarownerPO ownerPOb = new CarownerPO();
                    CustomerPO customerPO2 = CustomerPO.findByCompositeKeys(dealerCode,customerNOB);
                    ownerPOb.setString("OWNER_NO", ownerNo);
                    if(customerPO2!=null){
                       if(!StringUtils.isNullOrEmpty(customerPO2.getInteger("CUSTOMER_TYPE"))&&customerPO2.getInteger("CUSTOMER_TYPE").toString().equals(DictCodeConstants.DICT_CUSTOMER_TYPE_INDIVIDUAL)) {
                         //个人
                           ownerPOb.setInteger("OWNER_PROPERTY", Integer.parseInt(DictCodeConstants.DICT_OWNER_PROPERTY_PERSONAL));
                           ownerPOb.setString("PHONE", customerPO2.getString("CONTACTOR_PHONE"));
                           ownerPOb.setString("MOBILE", customerPO2.getString("CONTACTOR_MOBILE"));
                       }
                       if(!StringUtils.isNullOrEmpty(customerPO2.getInteger("CUSTOMER_TYPE"))&&customerPO2.getInteger("CUSTOMER_TYPE").toString().equals(DictCodeConstants.DICT_CUSTOMER_TYPE_COMPANY)) {
                         //公司
                             ownerPOb.setInteger("OWNER_PROPERTY", Integer.parseInt(DictCodeConstants.DICT_OWNER_PROPERTY_COMPANY));
                             ownerPOb.setString("DECISION_MARKER_PHONE", customerPO2.getString("CONTACTOR_PHONE"));
                             ownerPOb.setString("DECISION_MARKER_MOBILE", customerPO2.getString("CONTACTOR_MOBILE"));
                         }
                       ownerPOb.setInteger("GENDER", customerPO2.getInteger("GENDER"));
                       ownerPOb.setDate("BIRTHDAY", customerPO2.getDate("BIRTHDAY"));
                       ownerPOb.setString("ZIP_CODE", customerPO2.getString("ZIP_CODE"));
                       ownerPOb.setInteger("PROVINCE", customerPO2.getInteger("PROVINCE"));
                       ownerPOb.setInteger("CITY", customerPO2.getInteger("CITY"));
                       ownerPOb.setInteger("DISTRICT", customerPO2.getInteger("DISTRICT"));
                       ownerPOb.setString("ADDRESS", customerPO2.getString("ADDRESS"));
                       ownerPOb.setString("E_MAIL", customerPO2.getString("E_MAIL"));
                       ownerPOb.setString("CERTIFICATE_NO", customerPO2.getString("CERTIFICATE_NO"));
                       ownerPOb.setString("HOBBY", customerPO2.getString("HOBBY"));
                       ownerPOb.setInteger("INDUSTRY_FIRST", customerPO2.getInteger("INDUSTRY_FIRST"));
                       ownerPOb.setInteger("INDUSTRY_SECOND", customerPO2.getInteger("INDUSTRY_SECOND"));
                       ownerPOb.setDate("FOUND_DATE", customerPO2.getDate("FOUND_DATE"));
                       ownerPOb.setDate("SUBMIT_TIME", customerPO2.getDate("SUBMIT_TIME"));
                       ownerPOb.setDate("DOWN_TIMESTAMP", customerPO2.getDate("DOWN_TIMESTAMP"));
                       ownerPOb.setInteger("IS_UPLOAD", Integer.parseInt(DictCodeConstants.DICT_IS_NO));
                       ownerPOb.setString("OWNER_NAME", customerPO2.getString("CUSTOMER_NAME"));
                       ownerPOb.setInteger("CT_CODE", customerPO2.getInteger("CT_CODE"));
                       ownerPOb.saveIt();
                     //二级网点业务
                       System.out.println("// 创建出库单明细14");
                       List<Object> relatList = new ArrayList<Object>();
                       relatList.add("BROTHER_MODE");
                       relatList.add("TM_OWNER");
                       relatList.add(dealerCode);          
                       List<EntityRelationshipPO> relatpo = EntityRelationshipPO.findBySQL("select * from TM_ENTITY_RELATIONSHIP where RELATIONSHIP_MODE= ? AND BIZ_CODE= ? AND PARENT_ENTITY= ? ",relatList.toArray());           
                       EntityRelationshipPO relat = null;
                       if(relatpo!=null&&relatpo.size()>0){
                           relat=relatpo.get(0);
                       }
                      if(!StringUtils.isNullOrEmpty(relat)){
                          TmOwnerSubclassPO subpo = new TmOwnerSubclassPO();
                          subpo.setString("MAIN_ENTITY", dealerCode);
                          subpo.setString("OWNER_NO", ownerNo);
                          if(relat.getString("CHILD_ENTITY").equals(dealerCode)){
                              System.out.println("// 创建出库单明细15");
                              subpo.setDouble("PRE_PAY", ownerPOb.getDouble("PRE_PAY"));
                              subpo.setDouble("ARREARAGE_AMOUNT", ownerPOb.getDouble("ARREARAGE_AMOUNT"));
                          }
                          subpo.saveIt();
                          
                      }
     
                   // 1.检查车辆资料表里是否存在
                      //库存中找该车钥匙编号
                     
                   /*   VsStockPO stockpo= VsStockPO.findByCompositeKeys(dealerCode,order.getString("VIN"));*/
                  
                      List<Object> relat1List = new ArrayList<Object>();
                      relat1List.add("BROTHER_MODE");
                      relat1List.add("TM_VEHICLE");
                      relat1List.add(dealerCode); 
                      List<EntityRelationshipPO> relat1po = EntityRelationshipPO.findBySQL("select * from TM_ENTITY_RELATIONSHIP where RELATIONSHIP_MODE= ? AND BIZ_CODE= ? AND CHILD_ENTITY= ? ",relat1List.toArray());           
                      if(relat1po!=null){
                          EntityRelationshipPO shipPO =relat1po.get(0);
                          if(!StringUtils.isNullOrEmpty(shipPO.getString("PARENT_ENTITY"))){
                              dealerCode = shipPO.getString("PARENT_ENTITY");
                          }
                      }
                      
                      VehiclePO vehPO = VehiclePO.findByCompositeKeys(order.getString("VIN"),dealerCode);
                      LoginInfoDto loginInfo = ApplicationContextHelper.getBeanByType(LoginInfoDto.class);
                      if(vehPO!=null){
                          List<Object> vsubList = new ArrayList<Object>();
                          vsubList.add(loginInfo.getDealerCode());
                          vsubList.add(order.getString("VIN"));
                          vsubList.add(vehPO.getString("DEALER_CODE"));          
                          List<TmVehicleSubclassPO> vsubPO = TmVehicleSubclassPO.findBySQL("select * from TM_VEHICLE_SUBCLASS where DEALER_CODE= ? AND VIN= ? AND MAIN_ENTITY= ? ",vsubList.toArray());           
                          if(vsubPO!=null){
                            
                              TmVehicleSubclassPO PO = vsubPO.get(0);
                              if(Utility.isPrivateField(loginInfo.getDealerCode(), "TM_VEHICLE", "CONSULTANT")){
                                  vehPO.setString("CONSULTANT", PO.getString("CONSULTANT"));
                              }
                              if(Utility.isPrivateField(loginInfo.getDealerCode(), "TM_VEHICLE", "IS_SELF_COMPANY")){
                                  vehPO.setInteger("IS_SELF_COMPANY", PO.getInteger("IS_SELF_COMPANY"));
                              }
                              if(Utility.isPrivateField(loginInfo.getDealerCode(), "TM_VEHICLE", "FIRST_IN_DATE")){
                                  vehPO.setDate("FIRST_IN_DATE", PO.getDate("FIRST_IN_DATE"));
                              }
                              if(Utility.isPrivateField(loginInfo.getDealerCode(), "TM_VEHICLE", "CHIEF_TECHNICIAN")){
                                  vehPO.setString("CHIEF_TECHNICIAN", PO.getString("CHIEF_TECHNICIAN"));
                              }
                              if(Utility.isPrivateField(loginInfo.getDealerCode(), "TM_VEHICLE", "SERVICE_ADVISOR")){
                                  vehPO.setString("SERVICE_ADVISOR", PO.getString("SERVICE_ADVISOR"));
                              }
                              if(Utility.isPrivateField(loginInfo.getDealerCode(), "TM_VEHICLE", "INSURANCE_ADVISOR")){
                                  vehPO.setString("INSURANCE_ADVISOR", PO.getString("INSURANCE_ADVISOR"));
                              }
                              if(Utility.isPrivateField(loginInfo.getDealerCode(), "TM_VEHICLE", "MAINTAIN_ADVISOR")){
                                  vehPO.setString("MAINTAIN_ADVISOR", PO.getString("MAINTAIN_ADVISOR"));
                              }
                              if(Utility.isPrivateField(loginInfo.getDealerCode(), "TM_VEHICLE", "LAST_MAINTAIN_DATE")){
                                  vehPO.setDate("LAST_MAINTAIN_DATE", PO.getDate("LAST_MAINTAIN_DATE"));
                              }
                              if(Utility.isPrivateField(loginInfo.getDealerCode(), "TM_VEHICLE", "LAST_MAINTAIN_MILEAGE")){
                                  vehPO.setDouble("LAST_MAINTAIN_MILEAGE", PO.getDouble("LAST_MAINTAIN_MILEAGE"));
                              }
                              if(Utility.isPrivateField(loginInfo.getDealerCode(), "TM_VEHICLE", "LAST_MAINTENANCE_DATE")){
                                  vehPO.setDate("LAST_MAINTENANCE_DATE", PO.getDate("LAST_MAINTENANCE_DATE"));
                              }
                              
                              if(Utility.isPrivateField(loginInfo.getDealerCode(), "TM_VEHICLE", "LAST_MAINTENANCE_MILEAGE")){
                                  vehPO.setDouble("LAST_MAINTENANCE_MILEAGE", PO.getDouble("LAST_MAINTENANCE_MILEAGE"));
                              }
                              if(Utility.isPrivateField(loginInfo.getDealerCode(), "TM_VEHICLE", "PRE_PAY")){
                                  vehPO.setDouble("PRE_PAY", PO.getDouble("PRE_PAY"));
                              }
                              if(Utility.isPrivateField(loginInfo.getDealerCode(), "TM_VEHICLE", "ARREARAGE_AMOUNT")){
                                  vehPO.setDouble("ARREARAGE_AMOUNT", PO.getDouble("ARREARAGE_AMOUNT"));
                              }
                              if(Utility.isPrivateField(loginInfo.getDealerCode(), "TM_VEHICLE", "DISCOUNT_MODE_CODE")){
                                  vehPO.setString("DISCOUNT_MODE_CODE", PO.getString("DISCOUNT_MODE_CODE"));
                              }
                              if(Utility.isPrivateField(loginInfo.getDealerCode(), "TM_VEHICLE", "DISCOUNT_EXPIRE_DATE")){
                                  vehPO.setDate("DISCOUNT_EXPIRE_DATE", PO.getDate("DISCOUNT_EXPIRE_DATE"));
                              }
                              if(Utility.isPrivateField(loginInfo.getDealerCode(), "TM_VEHICLE", "IS_SELF_COMPANY_INSURANCE")){
                                  vehPO.setInteger("IS_SELF_COMPANY_INSURANCE", PO.getInteger("IS_SELF_COMPANY_INSURANCE"));
                              }
                              if(Utility.isPrivateField(loginInfo.getDealerCode(), "TM_VEHICLE", "ADJUST_DATE")){
                                  vehPO.setDate("ADJUST_DATE", PO.getDate("ADJUST_DATE"));
                              }
                              if(Utility.isPrivateField(loginInfo.getDealerCode(), "TM_VEHICLE", "ADJUSTER")){
                                  vehPO.setString("ADJUSTER", PO.getString("ADJUSTER"));
                              }
                              if(Utility.isPrivateField(loginInfo.getDealerCode(), "TM_VEHICLE", "IS_VALID")){
                                  vehPO.setInteger("IS_VALID", PO.getInteger("IS_VALID"));
                              }
                              if(Utility.isPrivateField(loginInfo.getDealerCode(), "TM_VEHICLE", "NO_VALID_REASON")){
                                  vehPO.setInteger("NO_VALID_REASON", PO.getInteger("NO_VALID_REASON"));
                              }
                          }
                          
                      }
                      System.out.println("// 创建出库单明细18");
                      UserPO userpo = UserPO.findByCompositeKeys(FrameworkUtil.getLoginInfo().getDealerCode(),order.getString("SOLD_BY"));
                      VsStockPO sk = VsStockPO.findByCompositeKeys(loginInfo.getDealerCode(),order.getString("VIN"));
                      if(vehPO!=null){
                          String groupCode = loginInfo.getDealerCode();
                          List<Object> vsList = new ArrayList<Object>();
                          vsList.add("BROTHER_MODE");
                          vsList.add("TM_VS_PRODUCT");
                          vsList.add(loginInfo.getDealerCode()); 
                          List<EntityRelationshipPO> vsListpo = EntityRelationshipPO.findBySQL("select * from TM_ENTITY_RELATIONSHIP where RELATIONSHIP_MODE= ? AND BIZ_CODE= ? AND CHILD_ENTITY= ? ",vsList.toArray());           
                          if(vsListpo!=null){
                              EntityRelationshipPO shipPO =relat1po.get(0);
                              if(!StringUtils.isNullOrEmpty(shipPO.getString("PARENT_ENTITY"))){
                                  groupCode = shipPO.getString("PARENT_ENTITY");
                              }
                          }
                       
                          // 有改车辆
                          
                          VsProductPO productPO = VsProductPO.findByCompositeKeys(groupCode,order.getString("PRODUCT_CODE"));
                          if(productPO!=null){
                              vehPO.setString("BRAND", productPO.getString("BRAND_CODE"));
                              vehPO.setString("SERIES", productPO.getString("SERIES_CODE"));
                              vehPO.setString("MODEL", productPO.getString("MODEL_CODE"));
                              vehPO.setString("COLOR", productPO.getString("COLOR_CODE"));
                              vehPO.setString("APACKAGE", productPO.getString("CONFIG_CODE"));
                              vehPO.setString("CUSTOMER_NO", customerNOB);
                              vehPO.setString("YEAR_MODEL", productPO.getString("YEAR_MODEL"));
                              vehPO.setString("MODEL_YEAR", productPO.getString("YEAR_MODEL"));
                          }
                          if(sk!=null){
                              vehPO.setString("YEAR_MODEL", sk.getString("YEAR_MODEL"));
                              vehPO.setString("MODEL_YEAR", sk.getString("YEAR_MODEL"));
                          }
                          if(!StringUtils.isNullOrEmpty(OutPutSalesDate)){
                              vehPO.setDate("SALES_DATE", OutPutSalesDate);// comg20101219 有开票登记时的开票登记日期让保有客户的日期在出库时，不设置为空
                          }
                          vehPO.setString("CONSULTANT", userpo.getString("USER_NAME"));
                          vehPO.setString("LICENSE", DictCodeConstants.CON_LICENSE_NULL);
                          vehPO.setDouble("VEHICLE_PRICE", order.getDouble("VEHICLE_PRICE"));
                          vehPO.setString("CONTRACT_NO", order.getString("CONTRACT_NO"));
                          vehPO.setDate("CONTRACT_DATE", order.getDate("CONTRACT_DATE"));
                          vehPO.setString("OWNER_NO", ownerNo);
                          vehPO.setInteger("VEHICLE_PURPOSE", order.getInteger("VEHICLE_PURPOSE"));
                          vehPO.setInteger("IS_SELF_COMPANY", DictCodeConstants.STATUS_IS_YES);
                          vehPO.setString("SALES_AGENT_NAME", loginInfo.getDealerName());
                        //保修结束里程 
                          
                          if(!StringUtils.isNullOrEmpty(commonNoService.getDefalutPara("1070"))
                                  &&Double.parseDouble(commonNoService.getDefalutPara("1070"))>0){
                              vehPO.setDouble("WRT_END_MILEAGE", Double.parseDouble(commonNoService.getDefalutPara("1070")));
                          }
                        //保险
                         if(!StringUtils.isNullOrEmpty(commonNoService.getDefalutPara("2066"))
                                  &&Integer.parseInt(commonNoService.getDefalutPara("2066"))>0){
                             vehPO.setDate("INSURANCE_BEGIN_DATE",dSalesDate);
                             Calendar calendar=Calendar.getInstance();   
                             calendar.setTime(dSalesDate);   
                             calendar.add(Calendar.DATE,Integer.parseInt(commonNoService.getDefalutPara("2066")));   
                             Date d =calendar.getTime(); 
                             vehPO.setDate("INSURANCE_END_DATE",d);  
                         }
                       //保修
                         if(!StringUtils.isNullOrEmpty(commonNoService.getDefalutPara("1071"))
                                  &&Integer.parseInt(commonNoService.getDefalutPara("1071"))>0){
                             vehPO.setDate("WRT_BEGIN_DATE",dSalesDate);
                             Calendar calendar1=Calendar.getInstance();   
                             calendar1.setTime(dSalesDate);   
                             calendar1.add(Calendar.DATE,Integer.parseInt(commonNoService.getDefalutPara("1071")));   
                             Date d1 =calendar1.getTime(); 
                             vehPO.setDate("WRT_END_DATE",d1);
                         }
                       //二级网点业务-车辆子表更新
                         List<Object> TmsubVList = new ArrayList<Object>();
                         TmsubVList.add(order.getString("VIN"));
                         TmsubVList.add(loginInfo.getDealerCode());          
                         List<TmVehicleSubclassPO> TmsubVpo = TmVehicleSubclassPO.findBySQL("select * from TM_VEHICLE_SUBCLASS where VIN= ? AND DEALER_CODE= ? ",TmsubVList.toArray());           
                         if(TmsubVpo!=null&&TmsubVpo.size()>0){
                             for(int s=0;s<TmsubVpo.size();s++){
                                 TmVehicleSubclassPO poSub = TmsubVpo.get(s);
                                 poSub.setString("OWNER_NO", ownerNo);
                                 poSub.setString("CONSULTANT", vehPO.getString("CONSULTANT"));
                                 poSub.setInteger("IS_SELF_COMPANY", vehPO.getInteger("IS_SELF_COMPANY"));
                                 poSub.setInteger("IS_SELF_COMPANY_INSURANCE", vehPO.getInteger("IS_SELF_COMPANY_INSURANCE"));
                                 poSub.setInteger("IS_VALID", vehPO.getInteger("IS_VALID"));
                                 poSub.setInteger("NO_VALID_REASON", vehPO.getInteger("NO_VALID_REASON"));
                                 
                                 poSub.setDate("FIRST_IN_DATE", vehPO.getDate("FIRST_IN_DATE"));
                                 poSub.setDate("LAST_MAINTAIN_DATE", null);
                                 poSub.setDate("LAST_MAINTENANCE_DATE", vehPO.getDate("LAST_MAINTENANCE_DATE"));
                                 poSub.setDate("DISCOUNT_EXPIRE_DATE", vehPO.getDate("DISCOUNT_EXPIRE_DATE"));
                                 poSub.setDate("ADJUST_DATE", vehPO.getDate("ADJUST_DATE"));
                                 
                                 poSub.setString("CHIEF_TECHNICIAN", vehPO.getString("CHIEF_TECHNICIAN"));
                                 poSub.setString("SERVICE_ADVISOR", vehPO.getString("SERVICE_ADVISOR"));
                                 poSub.setString("INSURANCE_ADVISOR", vehPO.getString("INSURANCE_ADVISOR"));
                                 poSub.setString("MAINTAIN_ADVISOR", vehPO.getString("MAINTAIN_ADVISOR"));
                                 poSub.setDouble("LAST_MAINTAIN_MILEAGE", vehPO.getDouble("LAST_MAINTAIN_MILEAGE"));
                                 poSub.setDouble("LAST_MAINTENANCE_MILEAGE", vehPO.getDouble("LAST_MAINTENANCE_MILEAGE"));
                                 poSub.setDouble("PRE_PAY", vehPO.getDouble("PRE_PAY"));
                                 poSub.setDouble("ARREARAGE_AMOUNT", vehPO.getDouble("ARREARAGE_AMOUNT"));
                                 
                                 poSub.setString("DISCOUNT_MODE_CODE", vehPO.getString("DISCOUNT_MODE_CODE"));
                                 poSub.setString("ADJUSTER", vehPO.getString("ADJUSTER"));
                                 poSub.saveIt();
                             }
                         }
                         //vehPO.setString("PRODUCT_CODE", order.getString("PRODUCT_CODE"));
                         vehPO.saveIt();
   
                         
                 
                         //
                      }else{
                          String groupCode = loginInfo.getDealerCode();
                          List<Object> vsList = new ArrayList<Object>();
                          vsList.add("BROTHER_MODE");
                          vsList.add("TM_VS_PRODUCT");
                          vsList.add(loginInfo.getDealerCode()); 
                          List<EntityRelationshipPO> vsListpo = EntityRelationshipPO.findBySQL("select * from TM_ENTITY_RELATIONSHIP where RELATIONSHIP_MODE= ? AND BIZ_CODE= ? AND CHILD_ENTITY= ? ",vsList.toArray());           
                          if(vsListpo!=null){
                              EntityRelationshipPO shipPO =relat1po.get(0);
                              if(!StringUtils.isNullOrEmpty(shipPO.getString("PARENT_ENTITY"))){
                                  groupCode = shipPO.getString("PARENT_ENTITY");
                              }
                          }
                          System.out.println("插入车辆表里客户编号字段");
                          VehiclePO tmVehiclePO2 = new VehiclePO();
                          tmVehiclePO2.setString("DEALER_CODE", dealerCode);
                          tmVehiclePO2.setString("VIN", order.getString("VIN"));
                          tmVehiclePO2.setString("CONSULTANT", userpo.getString("USER_NAME"));
                          tmVehiclePO2.setString("CUSTOMER_NO", customerNOB);
                          tmVehiclePO2.setString("OWNER_NO", ownerNo);
                          VsProductPO productPO = VsProductPO.findByCompositeKeys(groupCode,order.getString("PRODUCT_CODE"));
                          if(productPO!=null){
                              tmVehiclePO2.setString("BRAND", productPO.getString("BRAND_CODE"));
                              tmVehiclePO2.setString("SERIES", productPO.getString("SERIES_CODE"));
                              tmVehiclePO2.setString("MODEL", productPO.getString("MODEL_CODE"));
                              tmVehiclePO2.setString("COLOR", productPO.getString("COLOR_CODE"));
                              tmVehiclePO2.setString("APACKAGE", productPO.getString("CONFIG_CODE"));
                              /*vehPO.setString("CUSTOMER_NO", customerNOB);
                              vehPO.setString("YEAR_MODEL", productPO.getString("YEAR_MODEL"));
                              vehPO.setString("MODEL_YEAR", productPO.getString("YEAR_MODEL"));*/
                          }
                          if(sk!=null){
                              tmVehiclePO2.setString("YEAR_MODEL", sk.getString("YEAR_MODEL"));
                              tmVehiclePO2.setString("MODEL_YEAR", sk.getString("YEAR_MODEL"));
                          }
                          if(!StringUtils.isNullOrEmpty(OutPutSalesDate)){
                              tmVehiclePO2.setDate("SALES_DATE", OutPutSalesDate);// comg20101219 有开票登记时的开票登记日期让保有客户的日期在出库时，不设置为空
                          }                         
                          tmVehiclePO2.setString("LICENSE", DictCodeConstants.CON_LICENSE_NULL);
                          tmVehiclePO2.setDouble("VEHICLE_PRICE", order.getDouble("VEHICLE_PRICE"));
                          tmVehiclePO2.setString("CONTRACT_NO", order.getString("CONTRACT_NO"));
                          tmVehiclePO2.setDate("CONTRACT_DATE", order.getDate("CONTRACT_DATE"));
                          tmVehiclePO2.setDate("FOUND_DATE", new Date());
                          tmVehiclePO2.setDate("PRODUCT_DATE", sk.getDate("PRODUCT_DATE"));
                          tmVehiclePO2.setString("ENGINE_NO", sk.getString("ENGINE_NO"));
                          tmVehiclePO2.setString("KEY_NUMBER", sk.getString("KEY_NUMBER"));
                          tmVehiclePO2.setInteger("VEHICLE_PURPOSE", order.getInteger("VEHICLE_PURPOSE"));
                          tmVehiclePO2.setInteger("IS_SELF_COMPANY", DictCodeConstants.STATUS_IS_YES);
                          tmVehiclePO2.setString("SALES_AGENT_NAME", loginInfo.getDealerName());
//保修结束里程 
                          
                          if(!StringUtils.isNullOrEmpty(commonNoService.getDefalutPara("1070"))
                                  &&Double.parseDouble(commonNoService.getDefalutPara("1070"))>0){
                              tmVehiclePO2.setDouble("WRT_END_MILEAGE", Double.parseDouble(commonNoService.getDefalutPara("1070")));
                          }
                        //保险
                         if(!StringUtils.isNullOrEmpty(commonNoService.getDefalutPara("2066"))
                                  &&Integer.parseInt(commonNoService.getDefalutPara("2066"))>0){
                             tmVehiclePO2.setDate("INSURANCE_BEGIN_DATE",dSalesDate);
                             Calendar calendar=Calendar.getInstance();   
                             calendar.setTime(dSalesDate);   
                             calendar.add(Calendar.DATE,Integer.parseInt(commonNoService.getDefalutPara("2066")));   
                             Date d =calendar.getTime(); 
                             tmVehiclePO2.setDate("INSURANCE_END_DATE",d);  
                         }
                       //保修
                         if(!StringUtils.isNullOrEmpty(commonNoService.getDefalutPara("1071"))
                                  &&Integer.parseInt(commonNoService.getDefalutPara("1071"))>0){
                             tmVehiclePO2.setDate("WRT_BEGIN_DATE",dSalesDate);
                             Calendar calendar1=Calendar.getInstance();   
                             calendar1.setTime(dSalesDate);   
                             calendar1.add(Calendar.DATE,Integer.parseInt(commonNoService.getDefalutPara("1071")));   
                             Date d1 =calendar1.getTime(); 
                             tmVehiclePO2.setDate("WRT_END_DATE",d1);
                         }
                         tmVehiclePO2.setString("PRODUCT_CODE", order.getString("PRODUCT_CODE"));
                         tmVehiclePO2.saveIt();
                         System.out.println("新增加车辆资料信息");
                         //二级网点业务
                         List<Object> subVList = new ArrayList<Object>();
                          subVList.add("BROTHER_MODE");
                          subVList.add("TM_VEHICLE");
                          subVList.add(dealerCode);          
                          List<EntityRelationshipPO> subVpo = EntityRelationshipPO.findBySQL("select * from TM_ENTITY_RELATIONSHIP where RELATIONSHIP_MODE= ? AND BIZ_CODE= ? AND PARENT_ENTITY= ? ",relatList.toArray());           
                          EntityRelationshipPO erelat = null;
                          if(subVpo!=null&&subVpo.size()>0){
                              erelat=subVpo.get(0);
                          }
                          if(!StringUtils.isNullOrEmpty(erelat)){
                              TmVehicleSubclassPO poSub = new TmVehicleSubclassPO();
                              poSub.setString("MAIN_ENTITY", dealerCode);
                              poSub.setString("DEALER_CODE", erelat.getString("CHILD_ENTITY"));
                              poSub.setString("OWNER_NO", ownerNo);
                              poSub.setString("Vin", order.getString("VIN"));
                            //不共享业务字段
                              if(erelat.getString("CHILD_ENTITY").equals(loginInfo.getDealerCode())){
                                  poSub.setString("CONSULTANT", vehPO.getString("CONSULTANT"));
                                  poSub.setInteger("IS_SELF_COMPANY", vehPO.getInteger("IS_SELF_COMPANY"));
                                  poSub.setInteger("IS_SELF_COMPANY_INSURANCE", vehPO.getInteger("IS_SELF_COMPANY_INSURANCE"));
                                  poSub.setInteger("IS_VALID", vehPO.getInteger("IS_VALID"));
                                  poSub.setInteger("NO_VALID_REASON", vehPO.getInteger("NO_VALID_REASON"));
                                  
                                  poSub.setDate("FIRST_IN_DATE", vehPO.getDate("FIRST_IN_DATE"));
                                  poSub.setDate("LAST_MAINTAIN_DATE", vehPO.getDate("LAST_MAINTAIN_DATE"));
                                  poSub.setDate("LAST_MAINTENANCE_DATE", vehPO.getDate("LAST_MAINTENANCE_DATE"));
                                  poSub.setDate("DISCOUNT_EXPIRE_DATE", vehPO.getDate("DISCOUNT_EXPIRE_DATE"));
                                  poSub.setDate("ADJUST_DATE", vehPO.getDate("ADJUST_DATE"));
                                  
                                  poSub.setString("CHIEF_TECHNICIAN", vehPO.getString("CHIEF_TECHNICIAN"));
                                  poSub.setString("SERVICE_ADVISOR", vehPO.getString("SERVICE_ADVISOR"));
                                  poSub.setString("INSURANCE_ADVISOR", vehPO.getString("INSURANCE_ADVISOR"));
                                  poSub.setString("MAINTAIN_ADVISOR", vehPO.getString("MAINTAIN_ADVISOR"));
                                  poSub.setDouble("LAST_MAINTAIN_MILEAGE", vehPO.getDouble("LAST_MAINTAIN_MILEAGE"));
                                  poSub.setDouble("LAST_MAINTENANCE_MILEAGE", vehPO.getDouble("LAST_MAINTENANCE_MILEAGE"));
                                  poSub.setDouble("PRE_PAY", vehPO.getDouble("PRE_PAY"));
                                  poSub.setDouble("ARREARAGE_AMOUNT", vehPO.getDouble("ARREARAGE_AMOUNT"));
                                  
                                  poSub.setString("DISCOUNT_MODE_CODE", vehPO.getString("DISCOUNT_MODE_CODE"));
                                  poSub.setString("ADJUSTER", vehPO.getString("ADJUSTER"));

                              }
                              poSub.saveIt();
                          }

                          
                          
                      }
                    }
                }else{
                  //交车确认中是否已经生成车主信息
                    LoginInfoDto loginInfo = ApplicationContextHelper.getBeanByType(LoginInfoDto.class);
                    System.out.println("检查车辆表里是否有该VIN码车辆");
                    List<Object> relat1List = new ArrayList<Object>();
                    relat1List.add("BROTHER_MODE");
                    relat1List.add("TM_VEHICLE");
                    relat1List.add(dealerCode); 
                    List<EntityRelationshipPO> relat1po = EntityRelationshipPO.findBySQL("select * from TM_ENTITY_RELATIONSHIP where RELATIONSHIP_MODE= ? AND BIZ_CODE= ? AND CHILD_ENTITY= ? ",relat1List.toArray());           
                    if(relat1po!=null){
                        EntityRelationshipPO shipPO =relat1po.get(0);
                        if(!StringUtils.isNullOrEmpty(shipPO.getString("PARENT_ENTITY"))){
                            dealerCode = shipPO.getString("PARENT_ENTITY");
                        }
                    }
                    
                    VehiclePO vehPO = VehiclePO.findByCompositeKeys(order.getString("VIN"),dealerCode);
                    if(vehPO!=null){
                        List<Object> vsubList = new ArrayList<Object>();
                        vsubList.add(loginInfo.getDealerCode());
                        vsubList.add(order.getString("VIN"));
                        vsubList.add(vehPO.getString("DEALER_CODE"));  

                        List<TmVehicleSubclassPO> vsubPO = TmVehicleSubclassPO.findBySQL("select * from TM_VEHICLE_SUBCLASS where DEALER_CODE= ? AND VIN= ? AND MAIN_ENTITY= ? ",vsubList.toArray());           

                        if(!StringUtils.isNullOrEmpty(vsubPO)&&vsubPO.size()>0){
                            TmVehicleSubclassPO PO = vsubPO.get(0);
                            if(Utility.isPrivateField(loginInfo.getDealerCode(), "TM_VEHICLE", "CONSULTANT")){
                                vehPO.setString("CONSULTANT", PO.getString("CONSULTANT"));
                            }
                            if(Utility.isPrivateField(loginInfo.getDealerCode(), "TM_VEHICLE", "IS_SELF_COMPANY")){
                                vehPO.setInteger("IS_SELF_COMPANY", PO.getInteger("IS_SELF_COMPANY"));
                            }
                            if(Utility.isPrivateField(loginInfo.getDealerCode(), "TM_VEHICLE", "FIRST_IN_DATE")){
                                vehPO.setDate("FIRST_IN_DATE", PO.getDate("FIRST_IN_DATE"));
                            }
                            if(Utility.isPrivateField(loginInfo.getDealerCode(), "TM_VEHICLE", "CHIEF_TECHNICIAN")){
                                vehPO.setString("CHIEF_TECHNICIAN", PO.getString("CHIEF_TECHNICIAN"));
                            }
                            if(Utility.isPrivateField(loginInfo.getDealerCode(), "TM_VEHICLE", "SERVICE_ADVISOR")){
                                vehPO.setString("SERVICE_ADVISOR", PO.getString("SERVICE_ADVISOR"));
                            }
                            if(Utility.isPrivateField(loginInfo.getDealerCode(), "TM_VEHICLE", "INSURANCE_ADVISOR")){
                                vehPO.setString("INSURANCE_ADVISOR", PO.getString("INSURANCE_ADVISOR"));
                            }
                            if(Utility.isPrivateField(loginInfo.getDealerCode(), "TM_VEHICLE", "MAINTAIN_ADVISOR")){
                                vehPO.setString("MAINTAIN_ADVISOR", PO.getString("MAINTAIN_ADVISOR"));
                            }
                            if(Utility.isPrivateField(loginInfo.getDealerCode(), "TM_VEHICLE", "LAST_MAINTAIN_DATE")){
                                vehPO.setDate("LAST_MAINTAIN_DATE", PO.getDate("LAST_MAINTAIN_DATE"));
                            }
                            if(Utility.isPrivateField(loginInfo.getDealerCode(), "TM_VEHICLE", "LAST_MAINTAIN_MILEAGE")){
                                vehPO.setDouble("LAST_MAINTAIN_MILEAGE", PO.getDouble("LAST_MAINTAIN_MILEAGE"));
                            }
                            if(Utility.isPrivateField(loginInfo.getDealerCode(), "TM_VEHICLE", "LAST_MAINTENANCE_DATE")){
                                vehPO.setDate("LAST_MAINTENANCE_DATE", PO.getDate("LAST_MAINTENANCE_DATE"));
                            }
                            
                            //
                            if(Utility.isPrivateField(loginInfo.getDealerCode(), "TM_VEHICLE", "LAST_MAINTENANCE_MILEAGE")){
                                vehPO.setDouble("LAST_MAINTENANCE_MILEAGE", PO.getDouble("LAST_MAINTENANCE_MILEAGE"));
                            }
                            if(Utility.isPrivateField(loginInfo.getDealerCode(), "TM_VEHICLE", "PRE_PAY")){
                                vehPO.setDouble("PRE_PAY", PO.getDouble("PRE_PAY"));
                            }
                            if(Utility.isPrivateField(loginInfo.getDealerCode(), "TM_VEHICLE", "ARREARAGE_AMOUNT")){
                                vehPO.setDouble("ARREARAGE_AMOUNT", PO.getDouble("ARREARAGE_AMOUNT"));
                            }
                            if(Utility.isPrivateField(loginInfo.getDealerCode(), "TM_VEHICLE", "DISCOUNT_MODE_CODE")){
                                vehPO.setString("DISCOUNT_MODE_CODE", PO.getString("DISCOUNT_MODE_CODE"));
                            }
                            if(Utility.isPrivateField(loginInfo.getDealerCode(), "TM_VEHICLE", "DISCOUNT_EXPIRE_DATE")){
                                vehPO.setDate("DISCOUNT_EXPIRE_DATE", PO.getDate("DISCOUNT_EXPIRE_DATE"));
                            }
                            if(Utility.isPrivateField(loginInfo.getDealerCode(), "TM_VEHICLE", "IS_SELF_COMPANY_INSURANCE")){
                                vehPO.setInteger("IS_SELF_COMPANY_INSURANCE", PO.getInteger("IS_SELF_COMPANY_INSURANCE"));
                            }
                            if(Utility.isPrivateField(loginInfo.getDealerCode(), "TM_VEHICLE", "ADJUST_DATE")){
                                vehPO.setDate("ADJUST_DATE", PO.getDate("ADJUST_DATE"));
                            }
                            if(Utility.isPrivateField(loginInfo.getDealerCode(), "TM_VEHICLE", "ADJUSTER")){
                                vehPO.setString("ADJUSTER", PO.getString("ADJUSTER"));
                            }
                            if(Utility.isPrivateField(loginInfo.getDealerCode(), "TM_VEHICLE", "IS_VALID")){
                                vehPO.setInteger("IS_VALID", PO.getInteger("IS_VALID"));
                            }
                            if(Utility.isPrivateField(loginInfo.getDealerCode(), "TM_VEHICLE", "NO_VALID_REASON")){
                                vehPO.setInteger("NO_VALID_REASON", PO.getInteger("NO_VALID_REASON"));
                            }
                        }
                        
                    }
                    if(vehPO!=null){
                        CarownerPO ownerPOb = CarownerPO.findByCompositeKeys(dealerCode,vehPO.getString("OWNER_NO"));
                        CustomerPO customerPO2 = CustomerPO.findByCompositeKeys(dealerCode,vehPO.getString("CUSTOMER_NO"));
                        if(customerPO2!=null&&ownerPOb!=null){
                            if(!StringUtils.isNullOrEmpty(customerPO2.getInteger("CUSTOMER_TYPE"))&&customerPO2.getInteger("CUSTOMER_TYPE").toString().equals(DictCodeConstants.DICT_CUSTOMER_TYPE_INDIVIDUAL)) {
                                //个人
                                  ownerPOb.setInteger("OWNER_PROPERTY", Integer.parseInt(DictCodeConstants.DICT_OWNER_PROPERTY_PERSONAL));
                              }
                              if(!StringUtils.isNullOrEmpty(customerPO2.getInteger("CUSTOMER_TYPE"))&&customerPO2.getInteger("CUSTOMER_TYPE").toString().equals(DictCodeConstants.DICT_CUSTOMER_TYPE_COMPANY)) {
                                //公司
                                    ownerPOb.setInteger("OWNER_PROPERTY", Integer.parseInt(DictCodeConstants.DICT_OWNER_PROPERTY_COMPANY));

                                }
                              ownerPOb.setInteger("GENDER", customerPO2.getInteger("GENDER"));
                              ownerPOb.setDate("BIRTHDAY", customerPO2.getDate("BIRTHDAY"));
                              ownerPOb.setString("ZIP_CODE", customerPO2.getString("ZIP_CODE"));
                              ownerPOb.setInteger("PROVINCE", customerPO2.getInteger("PROVINCE"));
                              ownerPOb.setInteger("CITY", customerPO2.getInteger("CITY"));
                              ownerPOb.setInteger("DISTRICT", customerPO2.getInteger("DISTRICT"));
                              ownerPOb.setString("PHONE", customerPO2.getString("CONTACTOR_PHONE"));
                              ownerPOb.setString("MOBILE", customerPO2.getString("CONTACTOR_MOBILE"));
                              ownerPOb.setString("ADDRESS", customerPO2.getString("ADDRESS"));
                              ownerPOb.setString("E_MAIL", customerPO2.getString("E_MAIL"));
                              ownerPOb.setString("CERTIFICATE_NO", customerPO2.getString("CERTIFICATE_NO"));
                              ownerPOb.setString("HOBBY", customerPO2.getString("HOBBY"));
                              ownerPOb.setInteger("INDUSTRY_FIRST", customerPO2.getInteger("INDUSTRY_FIRST"));
                              ownerPOb.setInteger("INDUSTRY_SECOND", customerPO2.getInteger("INDUSTRY_SECOND"));
                              ownerPOb.setDate("FOUND_DATE", customerPO2.getDate("FOUND_DATE"));
                              ownerPOb.setDate("SUBMIT_TIME", customerPO2.getDate("SUBMIT_TIME"));
                              ownerPOb.setDate("DOWN_TIMESTAMP", customerPO2.getDate("DOWN_TIMESTAMP"));
                              ownerPOb.setInteger("IS_UPLOAD", Integer.parseInt(DictCodeConstants.DICT_IS_NO));
                              ownerPOb.setString("OWNER_NAME", customerPO2.getString("CUSTOMER_NAME"));
                              ownerPOb.setInteger("CT_CODE", customerPO2.getInteger("CT_CODE"));
                              ownerPOb.saveIt();
                        }
                    }
                    
                }
                LoginInfoDto login = ApplicationContextHelper.getBeanByType(LoginInfoDto.class);
             // 修改明细表里是否入帐,入帐人字段
                TtVsStockDeliveryPO delivery1PO = TtVsStockDeliveryPO.findByCompositeKeys(login.getDealerCode(),sdNo);
                if(delivery1PO!=null){
                    delivery1PO.setInteger("IS_ALL_FINISHED", Integer.parseInt(DictCodeConstants.DICT_IS_YES));
                    delivery1PO.saveIt();
                }
                TtVsDeliveryItemPO deliveryItemPO1 = TtVsDeliveryItemPO.findByCompositeKeys(login.getDealerCode(),sdNo,order.getString("VIN"));
                if(deliveryItemPO1!=null){
                    deliveryItemPO1.setInteger("IS_FINISHED", Integer.parseInt(DictCodeConstants.DICT_IS_YES));
                    deliveryItemPO1.saveIt();
                }
                
                //
            }

         // 增加车辆库存日志信息
            VsStockLogPO logPO = new VsStockLogPO();
            logPO.setString("VIN", order.getString("VIN"));
            logPO.setString("OPERATED_BY", userId);
            logPO.setString("PRODUCT_CODE", order.getString("PRODUCT_CODE"));
            logPO.setDate("OPERATE_DATE", order.getDate("OPERATE_DATE"));
            logPO.setString("STORAGE_CODE", order.getString("STORAGE_CODE"));
            logPO.setString("STORAGE_POSITION_CODE", order.getString("STORAGE_POSITION_CODE"));
            LoginInfoDto login1 = ApplicationContextHelper.getBeanByType(LoginInfoDto.class);
            VsStockPO vsStoPO = VsStockPO.findByCompositeKeys(login1.getDealerCode(),order.getString("VIN"));
            if(vsStoPO!=null){
                logPO.setDouble("PURCHASE_PRICE", vsStoPO.getDouble("PURCHASE_PRICE"));
                logPO.setDouble("ADDITIONAL_COST", vsStoPO.getDouble("ADDITIONAL_COST"));
                logPO.setInteger("STOCK_STATUS", vsStoPO.getInteger("STOCK_STATUS"));
                logPO.setInteger("DISPATCHED_STATUS", DictCodeConstants.DICT_DISPATCHED_STATUS_HAVE_DELIVER);
                logPO.setDouble("OLD_DIRECTIVE_PRICE", vsStoPO.getDouble("OLD_DIRECTIVE_PRICE"));
                logPO.setDouble("DIRECTIVE_PRICE", vsStoPO.getDouble("DIRECTIVE_PRICE"));
                logPO.setInteger("MAR_STATUS", vsStoPO.getInteger("MAR_STATUS"));
                logPO.setInteger("IS_SECONDHAND", vsStoPO.getInteger("IS_SECONDHAND"));
                logPO.setInteger("IS_VIP", vsStoPO.getInteger("IS_VIP"));
                logPO.setInteger("IS_TEST_DRIVE_CAR", vsStoPO.getInteger("IS_TEST_DRIVE_CAR"));
                logPO.setInteger("IS_CONSIGNED", vsStoPO.getInteger("IS_CONSIGNED"));
                logPO.setInteger("IS_PROMOTION", vsStoPO.getInteger("IS_PROMOTION"));
                logPO.setInteger("IS_PURCHASE_RETURN", vsStoPO.getInteger("IS_PURCHASE_RETURN"));
                logPO.setInteger("IS_PRICE_ADJUSTED", vsStoPO.getInteger("IS_PRICE_ADJUSTED"));
                logPO.setInteger("OEM_TAG", vsStoPO.getInteger("OEM_TAG"));
                logPO.setString("ADJUST_REASON", order.getString("ADJUST_REASON"));
                if(!StringUtils.isNullOrEmpty(order.getInteger("BUSINESS_TYPE"))&&order.getInteger("BUSINESS_TYPE").toString().equals(DictCodeConstants.DICT_SO_TYPE_GENERAL)){
                    logPO.setInteger("OPERATION_TYPE", Integer.parseInt(DictCodeConstants.DICT_VEHICLE_STORAGE_TYPE_SALE_OUT));
                }
                if(!StringUtils.isNullOrEmpty(order.getInteger("BUSINESS_TYPE"))&&order.getInteger("BUSINESS_TYPE").toString().equals(DictCodeConstants.DICT_SO_TYPE_ENTRUST_DELIVERY)){
                    logPO.setInteger("OPERATION_TYPE", Integer.parseInt(DictCodeConstants.DICT_VEHICLE_STORAGE_TYPE_OUT));
                }
                if(!StringUtils.isNullOrEmpty(order.getInteger("BUSINESS_TYPE"))&&order.getInteger("BUSINESS_TYPE").toString().equals(DictCodeConstants.DICT_SO_TYPE_ALLOCATION)){
                    logPO.setInteger("OPERATION_TYPE", Integer.parseInt(DictCodeConstants.DICT_VEHICLE_STORAGE_TYPE_ALLOCATE_OUT));
                }
                

            }
            logPO.setString("OWNED_BY", userId);
            logPO.saveIt();
            //
          //调拨出库
          /*  if(!StringUtils.isNullOrEmpty(order.getInteger("BUSINESS_TYPE"))&&order.getInteger("BUSINESS_TYPE").toString().equals(DictCodeConstants.DICT_SO_TYPE_ALLOCATION)){
              //根据销售单号对应的调拨申请单号
                if(!StringUtils.isNullOrEmpty(soNo)){
                    List<Map> resultList = findAllocationApplyByCnd(soNo,login1.getDealerCode());
                    if(resultList!=null&&resultList.size()>0){
                        
                    }
                }
            }*/
        }
    }
    /**
     * 自动生成CR关怀
     */
    public void SystemTrack(
        String cusNo,
        String vin,
        String dealerCode,
        String userid) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Calendar c = Calendar.getInstance();
        /**
         * 把这个客户的跟进记录中没有跟进的记录删掉 caoyang 修改
         */
        List<Object> promotionPlanList = new ArrayList<Object>();
        promotionPlanList.add(dealerCode);
        promotionPlanList.add(cusNo);
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
        taskList.add(Long.parseLong(DictCodeConstants.DICT_CUSTOMER_STATUS_TENURE));
        taskList.add(dealerCode);
        List<TrackingTaskPO> taskPO = TrackingTaskPO.findBySQL("select * from TM_TRACKING_TASK where CUSTOMER_STATUS= ? AND DEALER_CODE= ? ",taskList.toArray());
        if (taskPO != null && taskPO.size() > 0) {
            for (int n = 0; n < taskPO.size(); n++) {
                TrackingTaskPO task = taskPO.get(n);
                TtSalesCrPO sPlanPo = new TtSalesCrPO();
                System.out.println(cusNo);
                sPlanPo.setLong("TASK_ID", task.getLong("TASK_ID"));
                sPlanPo.setString("CR_NAME", task.getString("TASK_NAME"));
                sPlanPo.setString("CUSTOMER_NO",cusNo);
                sPlanPo.setString("CR_CONTEXT", task.getString("TASK_CONTENT"));
                sPlanPo.setInteger("CR_TYPE", task.getInteger("EXECUTE_TYPE"));
                sPlanPo.setInteger("CREATE_TYPE", 13291001);
                String dates = new String();
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
                List<Object> customerLinkList = new ArrayList<Object>();
                customerLinkList.add(cusNo);
                customerLinkList.add(dealerCode);
                List<TtCustomerLinkmanInfoPO> customerLinkListPO = TtCustomerLinkmanInfoPO.findBySQL("select * from TT_CUSTOMER_LINKMAN_INFO where CUSTOMER_NO= ? AND DEALER_CODE= ? ",customerLinkList.toArray());
                if(customerLinkListPO!=null&&customerLinkListPO.size()>0){
                    TtCustomerLinkmanInfoPO customerLinkmanInfoPO =customerLinkListPO.get(0);
                    sPlanPo.setString("CR_LINKER", customerLinkmanInfoPO.getString("CONTACTOR_NAME"));
                    sPlanPo.setString("LINK_PHONE", customerLinkmanInfoPO.getString("PHONE"));
                    sPlanPo.setString("LINK_MOBILE", customerLinkmanInfoPO.getString("MOBILE"));
                }
                CustomerPO cusmerPO = CustomerPO.findByCompositeKeys(dealerCode,cusNo);
                if(cusmerPO!=null){
                    sPlanPo.setString("SOLD_BY", cusmerPO.getString("SOLD_BY"));
                    sPlanPo.setString("OWNED_BY", cusmerPO.getString("SOLD_BY"));
                }
                sPlanPo.setString("VIN", vin);
                sPlanPo.saveIt();
            }
        }
        
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
            System.out.println("1"+customerNO);
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
                    PO.setString("REPORT_AUDITING_REMARK", cusPO.getString("REPORT_AUDITING_REMARK"));
                    PO.setString("REPORT_ABORT_REASON", cusPO.getString("REPORT_ABORT_REASON"));
                    PO.setString("REMARK", cusPO.getString("REMARK"));
                    PO.setString("OWNED_BY", cusPO.getString("SOLD_BY"));
                    PO.setDate("CONSULTANT_TIME", cusPO.getDate("CONSULTANT_TIME"));
                    System.out.println("2"+customerNO);
                    PO.saveIt();

                }
            }
        }
        return customerNO;
    }

    
  
    /**
	 * 交车确认更改配车状态
	 * @author zhanshiwei
	 * @date 2016年11月16日
	 */

	public void updateStockDispatched_status(SalesOrderDTO salesOrderDTO,SalesOrderPO salesOrderPO){


	}
	/**
	 * 更新"交车确认中"为"已交车确认"
	 * @author zhongsw
	 * @date 2016年9月28日
	 * @param id
	 * @throws ServiceBizException
	 * (non-Javadoc)
	 * @see com.yonyou.dms.retail.service.ordermanage.ConfirmcarService#updateCar(java.lang.Long)
	 */
	@Override
	public void updateCar(Long id,VehicleDTO vehicleDTO) throws ServiceBizException {
		SalesOrderPO salesOrderPO = SalesOrderPO.findById(id);
		String s=salesOrderPO.getString("VS_STOCK_ID");

		if(s==null){
			throw new ServiceBizException("请填写VIN号！");
		}else {
			StringBuilder sqlSb = new StringBuilder("select VS_STOCK_ID,DEALER_CODE,OWN_STOCK_STATUS,TRAFFIC_MAR_STATUS,IS_LOCK FROM tm_vs_stock  WHERE 1=1");
			List<Object> params = new ArrayList<Object>();
			sqlSb.append(" and OWN_STOCK_STATUS=?");
			params.add(DictCodeConstants.DISPATCHED_STATUS_INSTOCK);
			sqlSb.append(" and TRAFFIC_MAR_STATUS=?");
			params.add(DictCodeConstants.TRAFFIC_MAR_STATUS_YES);
			sqlSb.append(" and IS_LOCK=?");
			params.add(DictCodeConstants.STATUS_IS_NOT);
			sqlSb.append(" and VS_STOCK_ID=?");
			params.add(s);
			List<Map> list=DAOUtil.findAll(sqlSb.toString(), params);
			if(CommonUtils.isNullOrEmpty(list)){
				throw new ServiceBizException("只能对'库存状态'是'在库'，'运损状态'是'正常'，'是否锁定'为'否'的车辆进行交车确认");
			}
		}
		
	}

	/**
	 * 待交车查询，右侧快捷
	 * @author yll
	 * @date 2016年10月13日
	 * @return
	 * @throws ServiceBizException
	 * (non-Javadoc)
	 * @see com.yonyou.dms.customer.service.customerManage.SalesPromotionService#quickQuery()
	 */
	@Override
	public List<Map> quickQuery() throws ServiceBizException {
		StringBuffer sb = new StringBuffer();
		sb.append("SELECT tso.SO_NO_ID, tso.DEALER_CODE, tso.SO_NO, DATE_FORMAT(tso.DELIVERING_DATE, '%y-%m-%d' ) AS DELIVERING_DATE, tso.CUSTOMER_NO, tso.CUSTOMER_NAME, tso.SO_STATUS, tc.CODE_CN_DESC as SO_STATUS_CN, vwp.CONFIG_NAME, te.EMPLOYEE_NAME FROM TT_SALES_ORDER tso LEFT JOIN tc_code tc ON tc.CODE_ID=tso.SO_STATUS LEFT JOIN vw_productinfo vwp ON vwp.PRODUCT_ID = tso.PRODUCT_ID   LEFT JOIN tm_employee te ON tso.CONSULTANT = te.EMPLOYEE_NO and te.DEALER_CODE=tso.DEALER_CODE WHERE 1 = 1 AND tso.OLD_SO_NO IS NULL");
		List<Object> queryList = new ArrayList<Object>();
		LoginInfoDto loginInfo = ApplicationContextHelper.getBeanByType(LoginInfoDto.class);
		String employeeNo=loginInfo.getEmployeeNo();
		sb.append(" and te.EMPLOYEE_NO=?");
		queryList.add(employeeNo);
		sb.append("	AND tso.SO_STATUS <>14041009 AND tso.SO_STATUS <>14041010 AND tso.SO_STATUS <>14041011 AND tso.SO_STATUS <>14041012 AND tso.SO_STATUS <>14041013 GROUP BY tso.SO_NO_ID ORDER BY tso.DELIVERING_DATE ASC");
		return DAOUtil.findAll(sb.toString(), queryList);
	}
}
