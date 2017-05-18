package com.yonyou.dms.gacfca;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yonyou.dcs.gacfca.SendDmsMsgToBoldCloud;
import com.yonyou.dms.DTO.gacfca.WarrantyRegistDTO;
import com.yonyou.dms.common.domains.PO.basedata.PotentialCusPO;
import com.yonyou.dms.common.domains.PO.basedata.SystemStatusPO;
import com.yonyou.dms.common.domains.PO.basedata.VsProductPO;
import com.yonyou.dms.common.domains.PO.stockmanage.VehiclePO;
import com.yonyou.dms.commonSales.domains.PO.stockmanage.SalesOrderPO;
import com.yonyou.dms.framework.util.FrameworkUtil;
import com.yonyou.dms.function.exception.ServiceBizException;
import com.yonyou.dms.function.utils.common.StringUtils;

@Service
public class DSO0302CoudImpl implements DSO0302Coud{
	
	private static final Logger logger = LoggerFactory.getLogger(DSO0302CoudImpl.class);
	
	@Autowired SendDmsMsgToBoldCloud SendDmsMsgToBoldCloud;
	
	@Override
	public int getDSO0302(String vin,String soNo,String isSecondTime,String isConfirmed) throws ServiceBizException {
		try {
			logger.info("===================DSO0302开始====================");
			List<WarrantyRegistDTO> resList = new ArrayList<WarrantyRegistDTO>();
			String dealerCode = FrameworkUtil.getLoginInfo().getDealerCode();
			List<SalesOrderPO> salesList = SalesOrderPO.find("VIN = ? AND SO_NO = ?", vin, soNo);
			System.err.println(salesList);			
			if (salesList != null && salesList.size() > 0) {
				SalesOrderPO orderPO = salesList.get(0);
				VehiclePO vp = VehiclePO.findByCompositeKeys(vin, dealerCode);
				String productCode = orderPO.getString("PRODUCT_CODE");
				VsProductPO productPO = VsProductPO.findByCompositeKeys(dealerCode, productCode);
				String customerNo = orderPO.getString("CUSTOMER_NO");
				PotentialCusPO poCustomer = PotentialCusPO.findByCompositeKeys(dealerCode, customerNo);
				if (!StringUtils.isNullOrEmpty(poCustomer)) {
					poCustomer.setString("IS_SECOND_TIME", isSecondTime);
					poCustomer.setDate("OUTBOUND_UPLOAD_TIME", new Date());
					poCustomer.setString("OB_IS_SUCCESS", 70031003);
					poCustomer.saveIt();
					if ("12781002".equals(isSecondTime)) {
						isSecondTime = "2";
					} else {
						isSecondTime = "1";
					}
					WarrantyRegistDTO dto = new WarrantyRegistDTO();
					dto.setDealerCode(dealerCode);
					dto.setDmsOwnerId(customerNo);
					dto.setName(poCustomer.getString("CUSTOMER_NAME"));//车主名
					if ("10181001".equals(poCustomer.getString("CUSTOMER_TYPE"))) {
						dto.setClientType("1");//客户类型 客户
					} else {
						dto.setClientType("2");//公司
					}
					dto.setIdOrCompCode(poCustomer.getString("CERTIFICATE_NO"));//身份证号
					dto.setAddress(poCustomer.getString("ADDRESS"));//地址
					if ("10061001".equals(poCustomer.getString("GENDER"))) {
						dto.setGender("1");//性别 男
					} else if ("10061002".equals(poCustomer.getString("GENDER"))) {
						dto.setGender("2");//女
					} else {
						dto.setGender("");
					}

					if (poCustomer.getString("PROVINCE") != null) {
						dto.setProvinceId(poCustomer.getString("PROVINCE").toString());//省份					
					}
					if (poCustomer.getString("CITY") != null) {
						dto.setCityId(poCustomer.getString("CITY").toString());//城市					
					}
					if (poCustomer.getString("DISTRICT") != null) {
						dto.setDistrict(poCustomer.getString("DISTRICT").toString());//区县					
					}
					dto.setPostCode(poCustomer.getString("ZIP_CODE"));//邮编
					dto.setCellphone(poCustomer.getString("CONTACTOR_MOBILE"));//联系手机
					dto.setEmail(poCustomer.getString("E_MAIL"));//邮箱
					dto.setBuyTime(orderPO.getString("CONFIRMED_DATE"));//交车确认日期
					dto.setVin(vin);
					dto.setBrandId(productPO.getString("BRAND_CODE"));//品牌
					dto.setModeId(productPO.getString("SERRIES_CODE"));//车系
					dto.setStyleId(productPO.getString("MODEL_CODE"));//车型
					dto.setColorId(orderPO.getString("COLOR_CODE"));//颜色
					dto.setIsSecondTime(isSecondTime);//是否二次上报	1是 2、不是
					if ("12781001".equals(vp.getString("IS_OVERTIME"))) {//如果是逾期的
						dto.setIsOverDue("1");//逾期
					} else {
						dto.setIsOverDue("2");//
					}
					dto.setIsSalesReturnStatus("2");//不是销售退回
					if ("12781001".equals(isConfirmed)) {
						dto.setCompanyName(poCustomer.getString("COMPANY_NAME"));
						//根据车辆用途code找到对应的字典名称
						SystemStatusPO sysPo = SystemStatusPO.findById(orderPO.getString("VEHICLE_PURPOSE"));
						if (!StringUtils.isNullOrEmpty(sysPo)) {
							dto.setVehiclePurpose(sysPo.getString("STATUS_DESC"));
						}
						dto.setEngineNo(vp.getString("ENGINE_NO"));
						dto.setOrderNo(soNo);
					}

					resList.add(dto);
				}
			}
			logger.info("===================SendDmsMsgToBoldCloud开始123======================");
			SendDmsMsgToBoldCloud.handleExecutor(resList);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			logger.info("===========================DSO0302接口异常================================");
			return 0;
		}
		
		return 1;
	}
}
