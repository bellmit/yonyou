package com.yonyou.dms.gacfca;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yonyou.dcs.gacfca.SADCS095Cloud;
import com.yonyou.dms.DTO.gacfca.SADMS095Dto;
import com.yonyou.dms.common.domains.DTO.basedata.InvoiceRefundDTO;
import com.yonyou.dms.common.domains.PO.basedata.PotentialCusPO;
import com.yonyou.dms.common.domains.PO.basedata.TtPoCusLinkmanPO;
import com.yonyou.dms.common.domains.PO.basedata.UserPO;
import com.yonyou.dms.common.domains.PO.stockmanage.VehiclePO;
import com.yonyou.dms.commonSales.domains.PO.stockmanage.SalesOrderPO;
import com.yonyou.dms.framework.domains.PO.baseData.DictPO;
import com.yonyou.dms.framework.util.FrameworkUtil;
import com.yonyou.dms.function.common.DictCodeConstants;

@Service
public class SADMS095Impl implements SADMS095{
	
	private static final Logger logger = LoggerFactory.getLogger(SADMS095Impl.class);
	
	@Autowired SADCS095Cloud SADCS095Cloud;
	
    public String getSADMS095(InvoiceRefundDTO irdto){
        //        Long itemId = irdto.getItemId();// 地址 
        List<SADMS095Dto> voList;
        try {
        	System.err.println(irdto.getCustomerNo());
        	System.err.println(irdto.getSoNo());
        	System.err.println(irdto.getVin());
        	System.err.println(irdto.getGender());
        	System.err.println(irdto.getInvoiceAmount());
        	System.err.println(irdto.getInvoiceWriter());
    
        	logger.info("==================进入接口SADMS095====================");
			String cusNo = irdto.getCustomerNo();// 客户编号
			String soNo = irdto.getSoNo();// 订单编号
			String vin = irdto.getVin();// VIN
			String invoiceTypeCode = irdto.getInvoiceTypeCode();// 发票类型
			String invoiceNo = irdto.getInvoiceNo();// 发票编号
			Integer invoiceChargeType = irdto.getInvoiceChargeType();// 费用类型
			String invoiceAmount = irdto.getInvoiceAmount();// 发票金额
			String invoiceCustomer = irdto.getInvoiceCustomer();// 开票客户
			Long invoiceWriter = irdto.getInvoiceWriter();// 开票人员
			Date invoiceDate = irdto.getInvoiceDate();// 开票日期
			Integer ctCode = irdto.getCtCode();// 证件类型
			String certificateNo = irdto.getCertificateNo();// 证件编号
			Long transactor = irdto.getTransactor();// 经办人
			String remark = irdto.getRemark();// 备注
			Integer customerType = irdto.getCustomerType();// 客户类型
			String customerName = irdto.getCustomerName();// 客户名字
			String zipCode = irdto.getZipCode();// 邮编
			Double assessedPrice = irdto.getAssessedPrice();// 评估金额
			Double oldCarPurchase = irdto.getOldCarPurchase();// 收购金额
			String oldBrandCode = irdto.getOldBrandCode();// 品牌
			String oldSeriesCode = irdto.getOldSeriesCode();// 车系
			String oldModelCode = irdto.getOldModelCode();// 车型
			Integer isPermuted = irdto.getIspermuted();// 是否置换
			String permutedVin = irdto.getPermutedVin();// 置换车辆
			String permutedDesc = irdto.getPermutedDesc();// 二手车描述
			String assessedLicense = irdto.getAssessedLisence();// 车牌号
			String fileOldA = irdto.getFileOldA();// 收购协议ID
			String fileUrloldA = irdto.getFileUrloldA();// 收购协议URL
			String gender = irdto.getGender();// 性别
			String contactorMobile = irdto.getContactorMobile();// 手机
			String address = irdto.getAddress();// 地址
			voList = new ArrayList<SADMS095Dto>();
			SADMS095Dto vo = new SADMS095Dto();
			//
			vo.setDealerCode(FrameworkUtil.getLoginInfo().getDealerCode());
			vo.setCustomerType(customerType);
			vo.setCustomerName(customerName);
			vo.setZipCode(zipCode);
			vo.setCertificateNo(certificateNo);
			vo.setCtCode(ctCode);
			vo.setGender(Integer.parseInt(gender));
			vo.setContactorMobile(contactorMobile);
			vo.setAddress(address);
			vo.setOldCarPurchase(oldCarPurchase);
			vo.setOldBrandCode(oldBrandCode);
			vo.setOldSeriesCode(oldSeriesCode);
			vo.setOldModelCode(oldModelCode);
			vo.setIsPermuted(isPermuted);
			vo.setPermutedVin(permutedVin);
			vo.setPermutedDesc(permutedDesc);
			vo.setAssessedLicense(assessedLicense);
			vo.setFileOldA(fileOldA);
			vo.setFileUrloldA(fileUrloldA);
			vo.setAssessedPrice(assessedPrice);
			vo.setInvoiceDate(invoiceDate);
			vo.setInvoiceAmount(Double.parseDouble(invoiceAmount));
			vo.setInvoiceNo(invoiceNo);
			vo.setInvoiceChargeType(invoiceChargeType);
			vo.setInvoiceTypeCode(invoiceTypeCode);
			vo.setInvoiceCustomer(invoiceCustomer);
			UserPO userpo1 = new UserPO();
			List<Object> cus1 = new ArrayList<Object>();
			cus1.add(transactor);
			List<UserPO> listPote2 = UserPO.findBySQL("select * from Tm_User where User_Id=? ", cus1.toArray());
			if (listPote2.size() > 0) {
				userpo1 = (UserPO) listPote2.get(0);
				vo.setTransactor(userpo1.getString("User_Name"));
			}
			vo.setRemark(remark);
			vo.setSoNo(soNo);
			vo.setCustomerNo(cusNo);
			vo.setVin(vin);
			List<Object> cus2 = new ArrayList<Object>();
			cus2.add(cusNo);
			cus2.add(FrameworkUtil.getLoginInfo().getDealerCode());
			List<PotentialCusPO> listPo = PotentialCusPO.findBySQL(
					"select * from Tm_Potential_Customer where customer_no=? and dealer_code=?", cus2.toArray());
			//        PotentialCusPO cus =new PotentialCusPO();
			PotentialCusPO cuspo = new PotentialCusPO();
			if (listPo.size() > 0) {
				cuspo = (PotentialCusPO) listPo.get(0);
				vo.setCity(cuspo.getInteger("City"));
				vo.setBirthday(cuspo.getDate("Birthday"));
				vo.setProvince(cuspo.getInteger("Province"));
				vo.setDistrict(cuspo.getInteger("District"));
				vo.setEmail(cuspo.getString("EMail"));
				vo.setEducationLevel(cuspo.getInteger("Education_Level"));
				vo.setOwnerMarriage(cuspo.getInteger("Owner_Marriage"));
				vo.setFamilyIncome(cuspo.getInteger("Family_Income"));
				List<Object> cus3 = new ArrayList<Object>();
				cus3.add(cusNo);
				cus3.add(FrameworkUtil.getLoginInfo().getDealerCode());
				List<TtPoCusLinkmanPO> rsList = TtPoCusLinkmanPO.findBySQL(
						"select BEST_CONTACT_TIME from tt_po_cus_linkman where customer_no=? and dealer_code=?",
						cus3.toArray());
				if (rsList.size() > 0) {
					vo.setBestContactTime(GetStatusDesc(rsList.get(0).getInteger("BEST_CONTACT_TIME")));
				}

				vo.setHobby(cuspo.getString("Hobby"));
				vo.setIndustryFirst(GetStatusDesc(cuspo.getInteger("Industry_First")));
				vo.setIndustrySecond(GetStatusDesc(cuspo.getInteger("Industry_Second")));
				vo.setVocationType(GetStatusDesc(cuspo.getInteger("Vocation_Type")));
				vo.setPositionName(cuspo.getString("PositionName"));
			}
			//        UserPO user2 =new UserPO();
			UserPO userpo2 = new UserPO();
			List<Object> cus3 = new ArrayList<Object>();
			cus3.add(cuspo.get("Sold_By"));
			List<UserPO> listPote3 = UserPO.findBySQL("select * from Tm_User where User_Id=? ", cus3.toArray());
			if (listPote3.size() > 0) {
				userpo2 = (UserPO) listPote3.get(0);
				vo.setSoldBy(userpo2.getString("User_Name"));
			}
			vo.setIsModify(1);
			List<Object> cus4 = new ArrayList<Object>();
			cus4.add(FrameworkUtil.getLoginInfo().getDealerCode());
			cus4.add(soNo);
			cus4.add(DictCodeConstants.D_KEY);
			List<SalesOrderPO> listPote = SalesOrderPO.findBySQL(
					"select * from tt_sales_order where dealer_code=? and so_no=? and d_key=? ", cus4.toArray());
			//        SalesOrderPO son =new SalesOrderPO();
			SalesOrderPO sonpo = new SalesOrderPO();
			if (listPote.size() > 0) {
				sonpo = (SalesOrderPO) listPote.get(0);
				vo.setConfirmedDate(sonpo.getDate("Confirmed_Date"));
			}
			//        VehiclePO vpo = new VehiclePO();
			VehiclePO tvpo = new VehiclePO();
			List<Object> cus5 = new ArrayList<Object>();
			cus5.add(FrameworkUtil.getLoginInfo().getDealerCode());
			cus5.add(vin);
			List<VehiclePO> listPotal = VehiclePO.findBySQL("select * from tm_vehicle where dealer_code=? and vin=?  ",
					cus5.toArray());
			if (listPotal.size() > 0) {
				tvpo = (VehiclePO) listPotal.get(0);
				vo.setSalesMileage(tvpo.getDouble("Sales_Mileage"));
			}
			//        UserPO user =new UserPO();
			UserPO userpo = new UserPO();
			List<Object> cus6 = new ArrayList<Object>();
			cus6.add(FrameworkUtil.getLoginInfo().getDealerCode());
			cus6.add(invoiceWriter);
			List<UserPO> listPotel = UserPO.findBySQL("select * from Tm_User where dealer_code=? and User_Id=? ",
					cus6.toArray());
			if (listPote.size() > 0) {
				userpo = (UserPO) listPotel.get(0);
				vo.setInvoiceWriter(userpo.getString("User_Name"));
			}
			voList.add(vo);
			//调用厂端方法
			return SADCS095Cloud.handleExecutor(voList);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return "0";
		}
		
        
    }
    private String GetStatusDesc(Integer statusCode){
        String desc="";
        if (statusCode!=null && statusCode!=0){
            List<Object> cus7 = new ArrayList<Object>();
           
            cus7.add(statusCode);   
            DictPO po =new DictPO();
            List<DictPO> listPotel = DictPO.findBySQL("select * from tc_code where code_id=? ", cus7.toArray());
          if  (listPotel.size()>0){
              po=(DictPO) listPotel.get(0); 
          }        
        if(po!=null && po.getString("Status_Desc")!=null){
            desc=po.getString("Status_Desc");
        }}
        return desc;
    }
}
