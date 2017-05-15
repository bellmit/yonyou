package com.yonyou.dms.gacfca;

import java.text.SimpleDateFormat;
import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yonyou.dcs.gacfca.SendDmsMsgToBoldCloud;
import com.yonyou.dms.DTO.gacfca.WarrantyRegistDTO;
import com.yonyou.dms.common.domains.PO.basedata.PotentialCusPO;
import com.yonyou.dms.common.domains.PO.basedata.VsProductPO;
import com.yonyou.dms.common.domains.PO.stockmanage.VehiclePO;
import com.yonyou.dms.commonSales.domains.PO.stockmanage.SalesOrderPO;
import com.yonyou.dms.framework.util.FrameworkUtil;
import com.yonyou.dms.function.common.CommonConstants;
import com.yonyou.dms.function.common.DictCodeConstants;
import com.yonyou.dms.function.exception.ServiceBizException;

@Service
public class SalesReturnUploadImpl implements SalesReturnUpload {
	
	@Autowired SendDmsMsgToBoldCloud SendDmsMsgToBoldCloud;

	@Override
	public int getSalesReturnUpload(String stockInType, String[] vin)
			throws ServiceBizException {

		// 先判断车辆入库类型是否是销售退回 不是则不上报信息
		if ("13071005".equals(stockInType) || "13071006".equals(stockInType)) {
			try {
				System.err.println(stockInType);
				System.err.println(vin);
				String dealerCode = FrameworkUtil.getLoginInfo().getDealerCode();

				LinkedList<WarrantyRegistDTO> resultList = new LinkedList<WarrantyRegistDTO>();

				// 遍历入库的车辆
				for (int i = 0; i < vin.length; i++) {
					// 根据vin 和 订单类型找到对应的一般销售订单，(针对一车多次销售退回的)根据交车时间倒排序
					System.err.println(vin[i]);
					List<SalesOrderPO> list = SalesOrderPO.find("DEALER_CODE = ? AND VIN = '"+vin[i]+"'", dealerCode);
					System.err.println(list);
					SalesOrderPO orderPO = list.get(i);

					if (orderPO != null) {
						VehiclePO vp = new VehiclePO();

						List<VehiclePO> list1 = VehiclePO.find("VIN = '"+vin[i]+"' AND DEALER_CODE = ?", dealerCode);
						vp = list1.get(0);

						String productCode = orderPO.getString("PRODUCT_CODE");
						VsProductPO productPO = new VsProductPO();
						List<VsProductPO> list2 = VsProductPO.find("DEALER_CODE = ? AND PRODUCT_CODE = '"+productCode+"' AND D_KEY = '"+CommonConstants.D_KEY+"'", dealerCode);
						productPO = list2.get(0);
						String customerNo = orderPO.getString("CUSTOMER_NO");
						PotentialCusPO poCustomer = new PotentialCusPO();
						List<PotentialCusPO> customerList = PotentialCusPO.find("DEALER_CODE = ? AND CUSTOMER_NO = '" + customerNo + "' AND D_KEY = '"+CommonConstants.D_KEY+"' ", dealerCode);
						if (customerList != null && customerList.size() > 0) {
							poCustomer = customerList.get(0);
							WarrantyRegistDTO vo = new WarrantyRegistDTO();
							vo.setDealerCode(dealerCode);
							vo.setDmsOwnerId(customerNo);// 车主号
							vo.setName(poCustomer.getString("CUSTOMER_NAME"));// 车主名
							if (poCustomer.getInteger("CUSTOMER_TYPE").equals(10181001)) {
								vo.setClientType("1");// 客户类型 客户
							} else {
								vo.setClientType("2");// 公司
							}

							vo.setIdOrCompCode(poCustomer.getString("CERTIFICATE_NO"));// 身份证号
							vo.setAddress(poCustomer.getString("ADDRESS"));// 地址
							if (poCustomer.getInteger("GENDER").equals("10061001")) {
								vo.setGender("1");// 性别 男
							} else if (poCustomer.getInteger("GENDER").equals("10061002")) {
								vo.setGender("2");// 女
							} else {
								vo.setGender("");
							}

							if (poCustomer.getString("PROVINCE") != null) {
								vo.setProvinceId(poCustomer.getInteger("PROVINCE").toString());// 省份
							}
							if (poCustomer.getString("CITY") != null) {
								vo.setCityId(poCustomer.getString("CITY").toString());// 城市
							}
							if (poCustomer.getString("DISTRICT") != null) {
								vo.setDistrict(poCustomer.getInteger("DISTRICT").toString());// 区县
							}
							vo.setPostCode(poCustomer.getString("ZIP_CODE"));// 邮编
							vo.setCellphone(poCustomer.getString("CONTACTOR_MOBILE"));// 联系手机
							vo.setEmail(poCustomer.getString("E_MAIL"));// 邮箱
							SimpleDateFormat dateformat1 = new SimpleDateFormat("yyyy-MM-dd");
							String buyTime = dateformat1.format(orderPO.getDate("CONFIRMED_DATE"));
							vo.setBuyTime(buyTime);// 交车确认日期
							vo.setVin(vin[i]);
							vo.setBrandId(productPO.getString("BRAND_CODE"));// 品牌
							vo.setModeId(productPO.getString("SERIES_CODE"));// 车系
							vo.setStyleId(productPO.getString("MODEL_CODE"));// 车型
							vo.setColorId(orderPO.getString("COLOR_CODE"));// 颜色
							if (DictCodeConstants.DICT_IS_YES.equals(poCustomer.getString("IS_SECOND_TIME"))) {
								vo.setIsSecondTime("1");// 是否二次上报 1是 2、不是
							} else {
								vo.setIsSecondTime("2");
							}
							vo.setDealerCode(dealerCode);
							if (vp.getString("IS_OVERTIME").equals("12781001")) {// 如果是逾期的
								vo.setIsOverDue("1");// 逾期
							} else {
								vo.setIsOverDue("2");//
							}
							vo.setIsSalesReturnStatus("1");// s是销售退回

							// 车辆做了销售退回要将车辆表中的是否超期，专属经理进行更改
							VehiclePO vp1 = VehiclePO.findByCompositeKeys(dealerCode, vin[i]);
							vp1.setString("SERVICE_ADVISOR", null);
							vp1.setString("IS_OVERTIME", "12781002");
							vp1.saveIt();
							resultList.add(vo);

						}
					}
				}
				SendDmsMsgToBoldCloud.handleExecutor(resultList);
			} catch (Exception e) {
				e.printStackTrace();
				return 0;
			}
		}

		return 1;
	}

}
