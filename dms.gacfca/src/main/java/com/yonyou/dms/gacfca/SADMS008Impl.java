package com.yonyou.dms.gacfca;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.javalite.activejdbc.Base;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yonyou.dcs.gacfca.SADCS008Cloud;
import com.yonyou.dms.DTO.gacfca.LinkManListDto;
import com.yonyou.dms.DTO.gacfca.SalesOrderDto;
import com.yonyou.dms.DTO.gacfca.SecondCarListDto;
import com.yonyou.dms.common.domains.PO.basedata.CustomerPO;
import com.yonyou.dms.common.domains.PO.basedata.PotentialCusPO;
import com.yonyou.dms.common.domains.PO.basedata.UserPO;
import com.yonyou.dms.common.domains.PO.basedata.VsStockPO;
import com.yonyou.dms.common.domains.PO.stockmanage.CarownerPO;
import com.yonyou.dms.common.domains.PO.stockmanage.VehiclePO;
import com.yonyou.dms.commonSales.domains.PO.stockmanage.SalesOrderPO;
import com.yonyou.dms.framework.DAO.DAOUtil;
import com.yonyou.dms.framework.util.FrameworkUtil;
import com.yonyou.dms.function.common.CommonConstants;
import com.yonyou.dms.function.common.DictCodeConstants;
import com.yonyou.dms.function.exception.ServiceBizException;
import com.yonyou.dms.function.utils.common.StringUtils;

/**
 * 经销商车辆实销数据 上报
 * @author Benzc
 * @date 2017年1月11日
 *
 */
@Service
public class SADMS008Impl implements SADMS008{
    private static final Logger logger = LoggerFactory.getLogger(SADMS008Impl.class);
    @Autowired
    SADCS008Cloud SADCS008Cloud;

	@SuppressWarnings("rawtypes")
	@Override
	public int getSADMS008(String vin,String customerNo) throws ServiceBizException {
	    String msg="1";
	    try {

	        
	        String dealerCode = FrameworkUtil.getLoginInfo().getDealerCode();
	        String empNo = FrameworkUtil.getLoginInfo().getEmployeeNo();
	        Long userId = FrameworkUtil.getLoginInfo().getUserId();

	        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
	        LinkedList<SalesOrderDto> resultList = new LinkedList<SalesOrderDto>();
	        if(vin != null){
	                VehiclePO vpolist = VehiclePO.findByCompositeKeys(vin,dealerCode);
	                if(vpolist != null){
	        
	                    VsStockPO tspo = VsStockPO.findByCompositeKeys(dealerCode,vin);
	                    if(tspo!=null){
	                        //检查是不是一般销售订单的车  或者直销车 
	                        if(tspo.getString("SO_NO") != null && !"".equals(tspo.getString("SO_NO"))){
	                            SalesOrderPO salespo = new SalesOrderPO();
	                            salespo.setString("DEALER_CODE", dealerCode);
	                            salespo.setString("SO_NO",tspo.getString("SO_NO"));
	                            salespo.setInteger("D_KEY", 0);
	                            salespo.setLong("SOLD_BY", userId);
	                            SalesOrderPO saleslist = SalesOrderPO.findByCompositeKeys(dealerCode,tspo.getString("SO_NO"));
	                            VehiclePO mvpo =VehiclePO.findByCompositeKeys(vin,dealerCode);
	                            
	                            if(!StringUtils.isNullOrEmpty(mvpo.getString("CUSTOMER_NO"))&&customerNo.equals(mvpo.getString("CUSTOMER_NO"))){
	                                mvpo.setInteger("IS_UPLOAD",DictCodeConstants.STATUS_IS_YES);
	                                mvpo.setDate("SUBMIT_TIME", System.currentTimeMillis());
	                                mvpo.setLong("UPDATED_BY", userId);
	                                mvpo.setDate("UPDATED_AT", System.currentTimeMillis());
	                                mvpo.setString("EXCEPTION_CAUSE","");
	                                mvpo.setInteger("SUBMIT_STATUS", 13811003);//处理成功
	                                /*if(mvpo.getString("DEALER_CODE") == null || "".equals(mvpo.getString("DEALER_CODE")) || mvpo.getString("VIN")==null){     
	                                    return 0;
	                                }*/
	                                mvpo.saveIt();
	                                //tm_owner 车主信息

	                                CarownerPO po=CarownerPO.findByCompositeKeys(mvpo.getString("OWNER_NO"),dealerCode);
	                                
	                                // tm_customer 保有客户信息
	                                CustomerPO po1 =  CustomerPO.findByCompositeKeys(dealerCode,customerNo);

	                               
	                         
	                                List<Map> list =Base.findAll("SELECT * FROM tt_customer_linkman_info where DEALER_CODE=? and CUSTOMER_NO=? and IS_DEFAULT_CONTACTOR=? and D_KEY=?", new Object[]{dealerCode,customerNo,12781001,0});
	                                Map po2 =list.get(0);
	                                //tm_vs_stock 整车库存
	                                VsStockPO po3 =VsStockPO.findByCompositeKeys(dealerCode,vin);

	                                
	                                //tt_so_invoice 发票
	                                List<Map> list1 =Base.findAll("SELECT * FROM TT_SO_INVOICE where DEALER_CODE=? and SO_NO=? and  D_KEY=?", new Object[]{dealerCode,po3.getString("SO_NO"),0});
	                                Map po4=list1.get(0);
	                                //tm_insurance 保险公司
	                                Map po5 = null;
	                                if(!StringUtils.isNullOrEmpty(mvpo.getString("INSURATION_CODE"))){

	                                    List<Map> ins = Base.findAll("select DEALER_CODE,INSURATION_CODE from tm_insurance where INSURATION_CODE=? and DEALER_CODE=?" , new Object[]{mvpo.getString("INSURATION_CODE"),dealerCode});
	                                    po5 =ins.get(0);
	                                }
	                                
	                                //sold_by 姓名
	                                UserPO po6 =UserPO.findByCompositeKeys(FrameworkUtil.getLoginInfo().getDealerCode(),salespo.getLong("SOLD_BY"));

	                  
	                                List<Map> emp=Base.findAll(" select DEALER_CODE,EMPLOYEE_NO,MOBILE,E_MAIL from tm_employee where EMPLOYEE_NO=? AND DEALER_CODE= ?", new Object[]{po6.getString("USER_ID"),dealerCode});
	                                Map poE=emp.size()>0?emp.get(0):null;
	                                if(po1 != null){
	                                    SalesOrderDto dto = new SalesOrderDto();
	                                    dto.setDealerCode(dealerCode);
	                                    //添加车主编号
	                                    dto.setOwnerNo(mvpo.getString("OWNER_NO"));
	                                    if(!StringUtils.isNullOrEmpty(po1.getString("CUSTOMER_NAME"))){
	                                        dto.setCustomerName(po1.getString("CUSTOMER_NAME"));
	                                    }
	                                    if(po2 != null && !StringUtils.isNullOrEmpty(po2.get("CONTACTOR_NAME"))){
	                                        dto.setContactorName(po2.get("CONTACTOR_NAME").toString());
	                                        if(po2.get("BEST_CONTACT_TYPE")!=null)
	                                        dto.setBestContactType(GetStatusDesc(Integer.parseInt(po2.get("BEST_CONTACT_TYPE").toString())));
	                                        if(po2.get("BEST_CONTACT_TIME")!=null)
	                                        dto.setBestContactTime(GetStatusDesc(Integer.parseInt(po2.get("BEST_CONTACT_TIME").toString())));
	                                    }
	                                    if(!StringUtils.isNullOrEmpty(po1.getString("ADDRESS"))){
	                                        dto.setAddress(po1.getString("ADDRESS"));
	                                    }
	                                    if(!StringUtils.isNullOrEmpty(po1.getString("CONTACTOR_MOBILE"))){
	                                        dto.setPhone(po1.getString("CONTACTOR_MOBILE"));
	                                    }else if(!StringUtils.isNullOrEmpty(po1.getString("CONTACTOR_PHONE"))){
	                                        dto.setPhone(po1.getString("CONTACTOR_PHONE"));
	                                    }   
	                                    if(!StringUtils.isNullOrEmpty(po1.getInteger("CUSTOMER_TYPE").toString())){
	                                        dto.setCustomerType(po1.getInteger("CUSTOMER_TYPE"));
	                                    }
	                                    dto.setVin(vin);
	                                    dto.setProductCode(po3.getString("PRODUCT_CODE"));
	                                    dto.setSubmitTime(new Date(System.currentTimeMillis()));
	                                    dto.setLicense(mvpo.getString("LICENSE"));
	                                    dto.setSoNo(po3.getString("SO_NO"));
	                                    
	                                    if(po4 != null && po4.get("INVOICE_DATE") !=null && "".equals(po4.get("INVOICE_DATE"))){
	                                        dto.setInvoiceDate(format.parse(po4.get("INVOICE_DATE").toString()));
	                                        if(po4.get("INVOICE_NO")!=null)
	                                        dto.setInvoiceNo(po4.get("INVOICE_NO").toString());
	                                        if(po4.get("RECORD_DATE")!=null)
	                                        dto.setRecordDate(format.parse(po4.get("RECORD_DATE").toString()));//开票登记日期
	                                    }
	                                    
	                                    if(!StringUtils.isNullOrEmpty(po5.get("INSURATION_NAME"))){
	                                        dto.setInsurationName(po5.get("INSURATION_NAME").toString());
	                                    }
	                                    if(!StringUtils.isNullOrEmpty(mvpo.getDate("INSURANCE_BEGIN_DATE"))){
	                                        dto.setInsuranceBuyDate(mvpo.getDate("INSURANCE_BEGIN_DATE"));
	                                    }
	                                    
	                                    dto.setInstallmentAmount(salespo.getDouble("INSTALLMENT_AMOUNT"));
	                                    dto.setInstallmentNumber(salespo.getInteger("INSTALLMENT_NUMBER"));
	                                    if(!StringUtils.isNullOrEmpty(salespo.getDouble("FIRST_PERMENT_RATIO"))){
	                                        dto.setFirstPermentRatio(salespo.getDouble("FIRST_PERMENT_RATIO"));
	                                    }
	                                    
	                                    //成本明细增加字段
	                                    
	                                    if(salespo.getInteger("PROVINCE") != null)
	                                        dto.setUseProvince(salespo.getInteger("PROVINCE").toString());
	                                    if(salespo.getInteger("CITY") != null)
	                                        dto.setUseCity(salespo.getInteger("CITY").toString());
	                                    if(!StringUtils.isNullOrEmpty(salespo.getString("VIN"))){
	                                        dto.setNewCarVin(salespo.getString("VIN"));
	                                  
	                                    VehiclePO vehiclepo1 = VehiclePO.findByCompositeKeys(vin,dealerCode);

	                                    dto.setHandbookNo(vehiclepo1.getString("HAND_BOOK_NO"));
	                                    if(vehiclepo1!=null){
	                                        if(vehiclepo1.getString("SERIES")!=null)
	                                            dto.setNewCarSeriesCode(vehiclepo1.getString("SERIES"));
	                                        if(vehiclepo1.getString("MODEL")!=null)
	                                            dto.setNewCarModelCode(vehiclepo1.getString("MODEL"));
	                                        if(vehiclepo1.getString("BRAND")!=null)
	                                            dto.setNewCarBrandCode(vehiclepo1.getString("BRAND"));
	                                    }
	                                    }
	                                    
	                                    if(salespo.getDate("CREATED_TA")!=null)
	                                        dto.setReplacementDealDate(salespo.getDate("CREATED_AT"));
	                                    if(salespo.getDouble("OLD_CAR_PURCHASE")!=null){
	                                        dto.setUsedCarBuyAmount(salespo.getDouble("OLD_CAR_PURCHASE"));
	                                    }
	                                    if(salespo.getString("PERMUTED_DESC")!=null&&!"".equals(salespo.getString("PERMUTED_DESC"))){
	                                        dto.setUsedCarDescribe(salespo.getString("PERMUTED_DESC"));
	                                    }
	                                    //以上共九个字段
	                                    if(salespo.getDouble("LOAD_RATE") != null){
	                                        dto.setLoanRate(salespo.getDouble("LOAD_RATE")/100);
	                                    }
	                                    dto.setSalesDate(salespo.getDate("CONFIRMED_DATE"));
	                                    dto.setSalesMileage(mvpo.getDouble("SALE_MILEAGE"));
	                                    dto.setPayMode(salespo.getInteger("PAY_MODEL"));
	                                    if(salespo.getInteger("VEHICLE_PURPOSE")!=null)
	                                        dto.setVehicleType(salespo.getInteger("VEHICLE_PURPOSE"));
	                                    dto.setSoldBy(po6.getString("USER_NAME"));
	                                    //积分激励平台需求添加员工编号，员工手机号，员工邮箱
	                                    dto.setSoldById(po6.getString("USER_ID"));
	                                    if(poE.get("MOBILE")!=null){
	                                    	dto.setSoldMobile(poE.get("MOBILE").toString());
	                                    }
	                                    if(poE.get("E_MAIL")!=null){
	                                    	dto.setSoldEmail(poE.get("E_MAIL").toString());
	                                    }
	                                    dto.setWrtBeginDate(salespo.getDate("STOCK_OUT_DATE"));
	                                   
	                                    if(po1.getInteger("GENDER")!=null){
	                                        dto.setGender(po1.getInteger("GENDER"));
	                                    }
	                                    dto.setCtCode(po1.getInteger("CT_CODE"));
	                                    dto.setCertificateNo(po1.getString("CERTIFICATE_NO"));
	                                    if(!StringUtils.isNullOrEmpty(po1.getString("E_MAIL")))
	                                        dto.setEmail(po1.getString("E_MAIL"));
	                                    if(!StringUtils.isNullOrEmpty(po1.getString("ZIP_CODE")))
	                                        dto.setZipCode(po1.getString("ZIP_CODE"));
	                                    if(po1.getDate("BIRTHDAY")!=null){
	                                        dto.setBirthday(po1.getDate("BIRTHDAY"));
	                                    }
                                    	if(po1.getInteger("OWNER_MARRIAGE")!=null){
	                                        dto.setOwnerMarriage(po1.getInteger("OWNER_MARRIAGE"));
                                    	}
                                    	if(po1.getInteger("FAMILY_INCOME")!=null){
	                                        dto.setFamilyIncome(po1.getInteger("FAMILY_INCOME"));
                                    	}
                                    	if(po1.getInteger("EDUCATION_LEVEL")!=null){
	                                        dto.setEducationLevel(po1.getInteger("EDUCATION_LEVEL"));
                                    	}
                                    	if(po1.getInteger("PROVINCE")!=null){
	                                        dto.setProvince(po1.getInteger("PROVINCE"));
                                    	}
	                                    if(po1.getInteger("CITY")!=null){
	                                        dto.setCity(po1.getInteger("CITY"));
	                                    }
	                                    if(po1.getInteger("DISTRICT")!=null){
	                                        dto.setDistrict(po1.getInteger("DISTRICT"));
	                                    }
	                                    if(!StringUtils.isNullOrEmpty(po1.getString("REMARK")))
	                                        dto.setRemark(po1.getString("REMARK"));
	                                    
	                                    if(po4!=null && !StringUtils.isNullOrEmpty(po4.get("FILED_ID")))
	                                        dto.setFileId(po4.get("FILED_ID").toString());
	                                    if(po4!=null && !StringUtils.isNullOrEmpty(po4.get("FILED_URL")))
	                                        dto.setFileId(po4.get("FILED_URL").toString());
	                                    if(po1.getDate("MARRY_DATE")!=null)
	                                        dto.setWeddingDay(new SimpleDateFormat("yyyy-MM-dd").format(po1.getDate("MARRY_DATE")));
	                                    dto.setReference(po1.getString("RECOMMEND_EMP_NAME"));
	                                    dto.setReferenceTel(po1.getString("RECOMMEND_EMP_PHONE"));
	                                    dto.setCtmType(po1.getInteger("CUSTOMER_TYPE"));
	                                    dto.setFax(po1.getString("FAX"));
	                                    
	                                    if(!StringUtils.isNullOrEmpty(po1.getString("HOBBY"))){
	                                        dto.setHobby(po1.getString("HOBBY").replaceAll("[0-9]*",""));
	                                    }                                   
	                                    dto.setIndustryFirst(GetStatusDesc(po1.getInteger("INDUSTRY_FIRST")));
	                                    dto.setIndustrySecond(GetStatusDesc(po1.getInteger("INDUSTRY_SECOND")));
	                                    dto.setVocationType(GetStatusDesc(po1.getInteger("VOCATION_TYPE")));
	                                    dto.setPositionName(po1.getString("POSITION_NAME"));
	                                    dto.setIsFirstBuy(GetStatusDesc(po1.getInteger("IS_FIRST_BUY")));
	                                    dto.setIsPersonDriveCar(GetStatusDesc(po1.getInteger("IS_PERSON_DRIVER_CAR")));
	                                    if(!StringUtils.isNullOrEmpty(po1.getString("BUY_REASON"))){
	                                        dto.setBuyReason(po1.getString("BUY_REASON").replaceAll("[0-9]*",""));
	                                    }
	                                    dto.setBuyPurpose(GetStatusDesc(po1.getInteger("BUY_PURPOSE")));
	                                    dto.setChoiceReason(GetStatusDesc(po1.getInteger("CHOICE_REASON")));
	                                    dto.setCtmForm(GetStatusDesc(po1.getInteger("CUS_SOURCE")));
	                                    dto.setMediaType(GetStatusDesc(po1.getInteger("MEDIA_TYPE")));
	                                    dto.setVehiclePrice(salespo.getDouble("VEHICLE_PRICE"));
	                                    dto.setPriceAll(salespo.getDouble("ORDER_SUM"));
	                                    //又加了潜客的几个字段
	                                    PotentialCusPO pcpo =PotentialCusPO.findByCompositeKeys(dealerCode,salespo.getString("CUSTOMER_NO"));
	                                    if(pcpo!=null){
	                                        dto.setMediaDetail(pcpo.getString("MEDIA_DETAIL"));
	                                        dto.setFamilyMember(pcpo.getString("FAMILY_MEMBER"));
	                                        dto.setIm(pcpo.getString("IM"));
	                                        dto.setCompanyName(pcpo.getString("COMPANY_NAME"));
	                                        if(pcpo.getInteger("CUS_SOURCE")==13111016){
	                                            dto.setIsDccOffer(10011001);//是DDC转入
	                                        }else{
	                                            dto.setIsDccOffer(10011002);//不是DDC转入
	                                        }
	                                        dto.setOldCustomerVin(pcpo.getString("OLD_CUSTOMER_VIN"));//推荐人VIN号
	                                        dto.setExistBrand(pcpo.getString("BRAND_NOW"));
	                                        dto.setExistModel(pcpo.getString("MODEL_NOW"));
	                                        dto.setExistSeries(pcpo.getString("SERIES_NOW"));
	                                        dto.setPurchaseYear(pcpo.getString("OWN_YEAR"));
	                                        dto.setMileage(pcpo.getDouble("OWN_MILEAGE"));
	                                        if(pcpo.getLong("INTENT_ID")!=null && pcpo.getLong("INTENT_ID")!=0){
	                   
	                                            List<Map> list3=Base.findAll("SELECT * FROM tt_customer_intent WHERE D_KEY=? and DEALER_CODE=? and INTENT_ID=?",new Object[]{0,dealerCode,pcpo.getLong("INTENT_ID")});
	                                            if(list3!=null&&list3.size()>0){
	                                                Map intent = list3.get(0);
	                                                if(!StringUtils.isNullOrEmpty(intent.get("BUDGET_AMOUNT")))
	                                                dto.setBudgetAmount(Double.parseDouble(intent.get("BUDGET_AMOUNT").toString()));
	                                                if(!StringUtils.isNullOrEmpty(intent.get("DECISION_MAKER")))
	                                                dto.setEditor(intent.get("DECISION_MAKER").toString());
	                                         /*       TtCustomerIntentDetailPO detail = new TtCustomerIntentDetailPO();
	                                                detail.setString("DEALER_CODE", dealerCode);
	                                                detail.setLong("D_KEY", 0);
	                                                detail.setLong("INTENT_ID", pcpo.getLong("INTENT_ID"));
	                                                detail.setInteger("IS_MAIN_MODEL", 12781001);*/
	                                                List<Map> list4 = Base.findAll("SELECT * from tt_customer_intent_detail where DEALER_CODE=? and D_KEY=? and INTENT_ID=? and IS_MAIN_MODEL=?"
	                                                        , new Object[]{dealerCode,0,pcpo.getLong("INTENT_ID"),12781001});
	                                                if(list4 != null && list4.size()>0){
	                                                    Map detail=list4.get(0);
	                                                    if(!StringUtils.isNullOrEmpty(detail.get("INTENT_BRAND")))
	                                                    dto.setIntentionBrand(detail.get("INTENT_BRAND").toString());
	                                                    if(!StringUtils.isNullOrEmpty(detail.get("INTENT_COLOR")))
	                                                    dto.setIntentionColor(detail.get("INTENT_COLOR").toString());
	                                                    if(!StringUtils.isNullOrEmpty(detail.get("INTENT_MODEL")))
	                                                    dto.setIntentionModel(detail.get("INTENT_MODEL").toString());
	                                                    if(!StringUtils.isNullOrEmpty(detail.get("CHOOSE_REASON")))
	                                                    dto.setIntentionRemark(detail.get("CHOOSE_REASON").toString());
	                                                    if(!StringUtils.isNullOrEmpty(detail.get("INTENT_SERIES")))
	                                                    dto.setIntentionSeries(detail.get("INTENT_SERIES").toString());
	                                                    if(!StringUtils.isNullOrEmpty(detail.get("OTHER_REQUIREMENTS")))
	                                                    dto.setOtherNeed(detail.get("OTHER_REQUIREMENTS").toString());
	                                                }
	                                            }
	                                        }
	                                    }
	                                    //设置联系人Dto
	                                    LinkedList<LinkManListDto> dList = new LinkedList<LinkManListDto>();
	                                    LinkManListDto sdto = new LinkManListDto();
	                                    
	                                 /*   TtCustomerLinkmanInfoPO po7 = new TtCustomerLinkmanInfoPO();
	                                    po7.setString("DEALER_CODE", dealerCode);
	                                    po7.setString("CUSTOMER_NO", customerNo);
	                                    po7.setLong("D_KEY", 0);*/
	                                    List<Map> linkList = Base.findAll("SELECT * from tt_customer_linkman_info where DEALER_CODE=? and CUSTOMER_NO=? and D_KEY=?", new Object[]{dealerCode,customerNo,0});
	                                    if(null!=linkList && linkList.size() > 0){
	                                        for (Integer i = 0; i < linkList.size();i++){
	                                            Map po7 = linkList.get(i);
	                                            if(!StringUtils.isNullOrEmpty(po7.get("CONTACTOR_NAME")))
	                                            sdto.setName(po7.get("CONTACTOR_NAME").toString());
	                                            if(!StringUtils.isNullOrEmpty(po7.get("MOBILE")))
	                                                sdto.setMainPhone(po7.get("MOBILE").toString());
	                                            if(!StringUtils.isNullOrEmpty(po7.get("PHONE")))
	                                                sdto.setOtherPhone(po7.get("PHONE").toString());
	                                            if(!StringUtils.isNullOrEmpty(po7.get("REMARK")))
	                                                sdto.setContractReason(po7.get("REMARK").toString());
	                                            dList.add(sdto);
	                                        }
	                                        
	                                        dto.setLinkManList(dList);
	                                        //设置保有车辆Dto
	                                        if(salespo!=null && salespo.getString("PERMUTED_VIN")!=null){
	                                            LinkedList<SecondCarListDto> carList = new LinkedList<SecondCarListDto>();
	                                       /*     TtCustomerVehicleListPO carpo = new TtCustomerVehicleListPO();*/
	                                        /*    TtCustomerVehicleListPO usedcar = new TtCustomerVehicleListPO();
	                                            carpo.setString("DEALER_CODE", dealerCode);
	                                            carpo.setString("VIN", salespo.getString("PERMUTED_VIN"));
	                                            carpo.setLong("D_KEY", 0);*/
	                                            List<Map> oldcarList = Base.findAll("SELECT * from tt_customer_vehicle_list where DEALER_CODE=? and VIN=? and D_KEY=?"
	                                                    , new Object[]{dealerCode,salespo.getString("PERMUTED_VIN"),0});
	                                            
	                                            if(oldcarList != null && oldcarList.size()>0){
	                                                Map usedcar =oldcarList.get(0);
	                                                if(!StringUtils.isNullOrEmpty(usedcar.get("VIN"))){
	                                                    dto.setUsedCarVin(usedcar.get("VIN").toString());
	                                                }
	                                                if(!StringUtils.isNullOrEmpty(usedcar.get("LICENSE"))){
	                                                    dto.setUsedCarLicense(usedcar.get("LICENSE").toString());
	                                                }
	                                                if(!StringUtils.isNullOrEmpty(usedcar.get("PURCHASE_DATE"))){
	                                                    dto.setUsedCarLicenseDate(format.parse(usedcar.get("PURCHASE_DATE").toString()));
	                                                }
	                                                if(usedcar.get("MILEAGE")!=null)
	                                                    dto.setUsedCarMileage(Math.round(Double.parseDouble(usedcar.get("MILEAGE").toString())));
	                                                if(usedcar.get("BRAND_NAME")!=null)
	                                                    dto.setUsedCarBrandCode(usedcar.get("BRAND_NAME").toString());
	                                                if(usedcar.get("SERIES_NAME")!=null)
	                                                    dto.setUsedCarSeriesCode(usedcar.get("SERIES_NAME").toString());
	                                                if(usedcar.get("MODEL_NAME")!=null)
	                                                    dto.setUsedCarModelCode(usedcar.get("MODEL_NAME").toString());
	                                            }
	                                        }
	                                        
	                                        SalesOrderPO salespo1 = SalesOrderPO.findByCompositeKeys(dealerCode,tspo.getString("SO_NO"));
	                                   /*     salespo1.setString("DEALER_CODE", dealerCode);
	                                        salespo1.setString("SO_NO", tspo.getString("SO_NO"));
	                                        salespo1.setLong("D_KEY", 0);
	                                        salespo1.setInteger("EC_ORDER", 12781001);
	                                        List salespo1s = SalesOrderPO.findBySQL("DEALER_CODE=? and SO_NO=? and D_KEY=? and EC_ORDER=?"
	                                                , new Object[]{dealerCode,tspo.getString("SO_NO"),0,12781001});*/
	                                        if(salespo1 != null && !StringUtils.isNullOrEmpty(salespo1.getString("EC_ORDER"))&&"12781001".equals(salespo1.getString("EC_ORDER"))){
	                                           
	                                            List<Map> submislist = null;
	                                
	                                            submislist = querySalesOrder(dealerCode,tspo.getString("SO_NO"));
	                                       
	                                            if(submislist!=null&&submislist.size()>0){
	                                                //前台数据接收
	                                                Map bean = submislist.get(0);
	                                                if(!StringUtils.isNullOrEmpty(bean.get("BRAND_CODE")))
	                                                dto.setBrandCode(bean.get("BRAND_CODE").toString());
	                                                if(!StringUtils.isNullOrEmpty(bean.get("COLOR_CODE")))
	                                                dto.setColorCode(bean.get("COLOR_CODE").toString());
	                                                if(!StringUtils.isNullOrEmpty(bean.get("CUSTOMER_NAME")))
	                                                dto.setCustomerName(bean.get("CUSTOMER_NAME").toString());
	                                                if(!StringUtils.isNullOrEmpty(bean.get("DEPOSIT_AMOUNT")))
	                                                dto.setDepositAmount(Float.parseFloat(bean.get("DEPOSIT_AMOUNT").toString()));
	                                                if(!StringUtils.isNullOrEmpty(bean.get("DETERMINED_TIME")))
	                                                dto.setDepositDate(format.parse(bean.get("DETERMINED_TIME").toString()));
	                                                if(!StringUtils.isNullOrEmpty(bean.get("EC_ORDER_NO")))
	                                                dto.setEcOrderNo(bean.get("EC_ORDER_NO").toString());
	                                                if(!StringUtils.isNullOrEmpty(bean.get("BRAND_CODE")))
	                                                dto.setDealerCode(dealerCode);
	                                                if(!StringUtils.isNullOrEmpty(bean.get("DELIVERY_MODE_ELEC")))
	                                                dto.setEscComfirmType(Integer.parseInt(bean.get("DELIVERY_MODE_ELEC").toString()));
	                                                if(!StringUtils.isNullOrEmpty(bean.get("CONFIG_CODE")))
	                                                dto.setGroupCode(bean.get("CONFIG_CODE").toString());
	                                                if(!StringUtils.isNullOrEmpty(bean.get("CERTIFICATE_NO")))
	                                                dto.setIdCrad(bean.get("CERTIFICATE_NO").toString());
	                                                if(!StringUtils.isNullOrEmpty(bean.get("MODEL_CODE")))
	                                                dto.setModelCode(bean.get("MODEL_CODE").toString());
	                                                if(!StringUtils.isNullOrEmpty(bean.get("YEAR_MODEL")))
	                                                dto.setModelYear(bean.get("YEAR_MODEL").toString());
	                                                if(!StringUtils.isNullOrEmpty(bean.get("SO_STATUS")))
	                                                dto.setOrderStatus(Integer.parseInt(bean.get("SO_STATUS").toString()));
	                                                if(!StringUtils.isNullOrEmpty(bean.get("RETAIL_FINANCE")))
	                                                dto.setRetailFinance(bean.get("RETAIL_FINANCE").toString());
	                                                if(!StringUtils.isNullOrEmpty(bean.get("CONTACTOR_MOBILE")))
	                                                dto.setTel(bean.get("CONTACTOR_MOBILE").toString());
	                                                if(!StringUtils.isNullOrEmpty(bean.get("INTENT_TRIM")))
	                                                dto.setTrimCode(bean.get("INTENT_TRIM").toString());
	                                                if(!StringUtils.isNullOrEmpty(bean.get("SERIES_CODE")))
	                                                dto.setSeriesCode(bean.get("SERIES_CODE").toString());
	                                            }
	                                        }
	                                        resultList.add(dto);
	                                    }
	                                }
	                            }
	                            
	                        
	                    }
	                }
	                    logger.info("============================开始经销商车辆实销数据 上报SADMS008");
	                    msg = SADCS008Cloud.receiveDate(resultList);
	                    logger.info("============================结束经销商车辆实销数据 上报SADMS008");
	            }else{
	                msg="2";
	            }
	       
	        }
	        
	        return Integer.parseInt(msg);      
	    
        } catch (Exception e) {
            logger.info("============================经销商车辆实销数据 上报SADMS008失败");
            e.printStackTrace();
            return 0; 
        }
	}
	
	@SuppressWarnings("rawtypes")
	private String GetStatusDesc(Integer statusCode){
		String desc="";
		if (statusCode!=null && statusCode!=0){
		List<Map> list =Base.findAll("SELECT * FROM TM_SYSTEM_STATUS WHERE STATUS_CODE=?", new Object[]{statusCode});
		if(list!=null &&list.size()>0){
		    if(!StringUtils.isNullOrEmpty(list.get(0).get("STATUS_DESC")))
			desc=list.get(0).get("STATUS_DESC").toString();
		}}
		return desc;
	}
	
	@SuppressWarnings("rawtypes")
	public List<Map> querySalesOrder(String dealerCode,String soNo) throws Exception {
		List<Object> list = new ArrayList<Object>();
		String sql="SELECT a.DEALER_CODE,a.DELIVERY_MODE_ELEC,A.CUSTOMER_NAME, b.YEAR_MODEL,a.EC_ORDER_NO,B.BRAND_CODE,B.SERIES_CODE, B.MODEL_CODE, B.CONFIG_CODE, COALESCE(A.COLOR_CODE,B.COLOR_CODE) AS COLOR_CODE, A.SO_STATUS,A.CERTIFICATE_NO, A.CT_CODE, d.RETAIL_FINANCE,  d.DEPOSIT_AMOUNT , d.DETERMINED_TIME,  d.INTENT_TRIM,d.CONTACTOR_MOBILE FROM TT_SALES_ORDER A LEFT JOIN ("+CommonConstants.VM_VS_PRODUCT+") B ON A.PRODUCT_CODE = B.PRODUCT_CODE AND A.DEALER_CODE = B.DEALER_CODE AND A.D_KEY = B.D_KEY LEFT JOIN TM_VS_STOCK C ON  A.DEALER_CODE = C.DEALER_CODE AND A.VIN = C.VIN INNER JOIN TT_ES_CUSTOMER_ORDER d ON a.dealer_code=d.dealer_code AND a.EC_ORDER_NO=d.EC_ORDER_NO and a.customer_no=d.customer_no WHERE  1=1 and  A.D_KEY = 0 and a.dealer_code= '"+dealerCode+"' and a.so_no= '"+soNo+"'and a.EC_ORDER=12781001 ";
		System.err.println(sql.toString());
		List<Map> result = DAOUtil.findAll(sql, list);
		return result;		
	}
	
	

}
