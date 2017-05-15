/**
 * 
 */
package com.yonyou.dms.schedule.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.javalite.activejdbc.Base;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yonyou.dcs.gacfca.SendDmsMsgToBoldCloud;
import com.yonyou.dms.DTO.gacfca.WarrantyRegistDTO;
import com.yonyou.dms.common.domains.PO.basedata.PotentialCusPO;
import com.yonyou.dms.common.domains.PO.basedata.VsProductPO;
import com.yonyou.dms.common.domains.PO.stockmanage.VehiclePO;
import com.yonyou.dms.framework.util.Utility;
import com.yonyou.dms.function.common.CommonConstants;
import com.yonyou.dms.function.common.DictCodeConstants;
import com.yonyou.dms.function.exception.ServiceBizException;

/**
 * 监控交车确认时保修登记信息上报情况(未成功上报的重新上报) 
 * @author Administrator
 * @date 2017年2月23日
 *
 */
@Service
public class CheckOutBoundUploadServiceImpl implements CheckOutBoundUploadService{
	
	@Autowired SendDmsMsgToBoldCloud SendDmsMsgToBoldCloud;

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public int performExecute() throws ServiceBizException {
		try {
			//先查询该经销商下是否存在2015-5 月之后的 已经交车的 销售订单状态为 已交车或完成或已关单的但潜客中
			//客户核实状态为空的客户和订单信息
			List<Map> list = this.queryMatchData();
			System.err.println(list);
			List<WarrantyRegistDTO> resultList = new ArrayList<WarrantyRegistDTO>();
			if (list != null && list.size() > 0) {
				System.out.println("jinlaile ma ");
				for (int i = 0; i < list.size(); i++) {
					Map po = list.get(i);
					String soNo = (String) po.get("SO_NO");

					String vin = (String) po.get("VIN");
					String isSecondTime = DictCodeConstants.DICT_IS_NO;
					//TtSalesOrderPO orderPO = new TtSalesOrderPO();

					List salesList = Base.findAll("SELECT *  FROM tt_sales_order WHERE SO_NO=? AND VIN=? AND D_KEY=? ",
							new Object[] { soNo, vin, CommonConstants.D_KEY });
					VehiclePO vp = new VehiclePO();
					List vehicleList = VehiclePO.findBySQL("select *  from tm_vehicle where VIN=?",
							new Object[] { vin });
					if (salesList != null && salesList.size() > 0) {
						Map orderPO = (Map) salesList.get(0);
						String productCode = (String) orderPO.get("PRODUCT_CODE");
						VsProductPO productPO = new VsProductPO();
						List proList = VsProductPO.findBySQL(
								"select *  from tm_vs_product where d_key=? and product_code=? ",
								new Object[] { CommonConstants.D_KEY, productCode });
						productPO = (VsProductPO) proList.get(0);
						String customerNo = (String) orderPO.get("CUSTOMER_NO");
						List customerList = PotentialCusPO.findBySQL(
								"select *  from TM_POTENTIAL_CUSTOMER where d_key=? and customer_no=?",
								new Object[] { CommonConstants.D_KEY, customerNo });
						PotentialCusPO poCustomer2 = new PotentialCusPO();
						//poCustomer2=PotentialCusPO.findFirst("select *  from TM_POTENTIAL_CUSTOMER where d_key=? and customer_no like '%"+customerNo+"%' ", new Object[]{CommonConstants.D_KEY});
						poCustomer2 = (PotentialCusPO) customerList.get(0);
						try {
							poCustomer2.setInteger("IS_SECOND_TIME", Utility.getInt(isSecondTime));
						} catch (Exception e) {
							e.printStackTrace();
						}
						poCustomer2.setDate("OUTBOUND_UPLOAD_TIME", new Date());
						poCustomer2.setLong("OB_IS_SUCCESS",
								Integer.parseInt(DictCodeConstants.DICT_OUTBOUND_RETURN_STATUS_CHECKING));
						poCustomer2.saveIt();
						if (DictCodeConstants.DICT_IS_NO.equals(isSecondTime)) {
							isSecondTime = "2";
						} else {
							isSecondTime = "1";
						}
						if (customerList != null && customerList.size() > 0) {
							PotentialCusPO poCustomer = new PotentialCusPO();
							poCustomer = (PotentialCusPO) customerList.get(0);
							WarrantyRegistDTO vo = new WarrantyRegistDTO();
							vo.setDmsOwnerId(customerNo);//车主号
							vo.setName(poCustomer.getString("CUSTOMER_NAME"));//车主名
							if (poCustomer.getLong("CUSTOMER_TYPE") == 10181001) {
								vo.setClientType("10181001");//客户类型 客户
							} else {
								vo.setClientType("10181002");//公司
							}

							vo.setIdOrCompCode(poCustomer.getString("CERTIFICATE_NO"));//身份证号
							vo.setAddress(poCustomer.getString("ADDRESS"));//地址	 
							if (poCustomer.getLong("GENDER") == 10061001) {
								vo.setGender("10061001");//性别 男
							} else if (poCustomer.getLong("GENDER") == 10061002) {
								vo.setGender("10061002");//女
							} else {
								vo.setGender("");
							}
							if (poCustomer.getString("PROVINCE") != null) {
								vo.setProvinceId(poCustomer.getString("PROVINCE").toString());//省份					
							}
							if (poCustomer.getString("CITY") != null) {
								vo.setCityId(poCustomer.getString("CITY").toString());//城市					
							}
							if (poCustomer.getString("DISTRICT") != null) {
								vo.setDistrict(poCustomer.getString("DISTRICT").toString());//区县					
							}
							vo.setPostCode(poCustomer.getString("ZIP_CODE"));//邮编
							vo.setCellphone(poCustomer.getString("CONTACTOR_MOBILE"));//联系手机
							vo.setEmail(poCustomer.getString("E_MAIL"));//邮箱
							SimpleDateFormat dateformat1 = new SimpleDateFormat("yyyy-MM-dd");
							String buyTime = dateformat1.format((Date) orderPO.get("CONFIRMED_DATE"));
							vo.setBuyTime(buyTime);//交车确认日期
							vo.setVin(vin);
							vo.setBrandId(productPO.getString("BRAND_CODE"));//品牌
							vo.setModeId(productPO.getString("SERIES_CODE"));//车系
							vo.setStyleId(productPO.getString("MODEL_CODE"));//车型
							vo.setColorId((String) orderPO.get("COLOR_CODE"));//颜色
							vo.setIsSecondTime(isSecondTime);//是否二次上报	1是 2、不是
							vo.setIsOverDue("2");//没有逾期
							vo.setIsSalesReturnStatus("2");//不是销售退回 
							vo.setCompanyName(poCustomer.getString("COMPANY_NAME"));
							//根据车辆用途code找到对应的字典名称
							//TcCodePO sysPo = new TcCodePO(); 
							List sysLists = Base.findAll("select *  from tc_code where code_id=?",
									new Object[] { Integer.parseInt(orderPO.get("VEHICLE_PURPOSE").toString()) });
							if (sysLists != null && sysLists.size() > 0) {
								Map sysPo = (Map) sysLists.get(0);
								vo.setVehiclePurpose((String) sysPo.get("CODE_CN_DESC"));
							}
							if (vehicleList != null && vehicleList.size() > 0) {
								vp = (VehiclePO) vehicleList.get(0);
								vo.setEngineNo(vp.getString("ENGINE_NO"));
							}
							vo.setOrderNo(soNo);

							resultList.add(vo);

						}

					}

				}
			}
			SendDmsMsgToBoldCloud.handleExecutor(resultList);
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return 0;
		}
		return 1;
	}
	
	//查询已正常交车核实结果为空的数据的
	@SuppressWarnings({ "rawtypes" })
	@Override
	public List  queryMatchData() throws ServiceBizException {
		StringBuffer sql = new StringBuffer("");
		sql.append(" select a.so_no,a.vin from tt_sales_order a inner join  tm_potential_customer b  ");
		sql.append(" on a.dealer_code=b.dealer_code and a.customer_no=b.customer_no where  ");
		sql.append(" b.ob_is_success is null and ( a.so_status=13011030 OR a.so_status=13011035 OR a.so_status=13011075) and ");
		sql.append(" a.confirmed_date>='2015-05-01 00:00:00' ");
		 List<Map> rsLists = Base.findAll(sql.toString());
			
         return rsLists;
		
	}
	
}
