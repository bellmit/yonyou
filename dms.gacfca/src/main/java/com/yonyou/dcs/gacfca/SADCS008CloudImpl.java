package com.yonyou.dcs.gacfca;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yonyou.dcs.dao.SaleVehicleSaleDao;
import com.yonyou.dcs.dao.TtVsNvdrDao;
import com.yonyou.dcs.util.DEConstant;
import com.yonyou.dms.DTO.gacfca.LinkManListDto;
import com.yonyou.dms.DTO.gacfca.SalesOrderDto;
import com.yonyou.dms.DTO.gacfca.SecondCarListDto;
import com.yonyou.dms.common.domains.PO.basedata.FsFileuploadPO;
import com.yonyou.dms.common.domains.PO.basedata.TiSalesJecCustomerPO;
import com.yonyou.dms.common.domains.PO.basedata.TiSalesJecVehiclePO;
import com.yonyou.dms.common.domains.PO.basedata.TiWxCustomerPO;
import com.yonyou.dms.common.domains.PO.basedata.TmVehicleNodeHistoryPO;
import com.yonyou.dms.common.domains.PO.basedata.TmVehiclePO;
import com.yonyou.dms.common.domains.PO.basedata.TtVehicleNodeHistoryPO;
import com.yonyou.dms.common.domains.PO.basedata.TtVsCustomerOldPO;
import com.yonyou.dms.common.domains.PO.basedata.TtVsCustomerPO;
import com.yonyou.dms.common.domains.PO.basedata.TtVsLinkmanPO;
import com.yonyou.dms.common.domains.PO.basedata.TtVsNvdrPO;
import com.yonyou.dms.common.domains.PO.basedata.TtVsSalesReportPO;
import com.yonyou.dms.common.domains.PO.basedata.TtVsSecondCarPO;
import com.yonyou.dms.common.domains.PO.basedata.TtVsVhclChngPO;
import com.yonyou.dms.function.common.OemDictCodeConstants;
import com.yonyou.dms.function.exception.ServiceBizException;
import com.yonyou.dms.function.utils.common.CommonUtils;


/**
 * return msg 0 error 1 success
 * @author 夏威
 *
 */
@SuppressWarnings({"unchecked","rawtypes"})
@Service
public class SADCS008CloudImpl extends BaseCloudImpl implements SADCS008Cloud {
	
	private static final Logger logger = LoggerFactory.getLogger(SADCS008CloudImpl.class);
	
	@Autowired
	SaleVehicleSaleDao saleDao;
	
	@Autowired
	TtVsNvdrDao vsNvdrDao;
	
	@Override
	public String receiveDate(List<SalesOrderDto> dtoList) throws Exception {
		String msg = "1";
		logger.info("====经销商车辆实销数据上报接收开始===="); 
		for (SalesOrderDto entry : dtoList) {
			try {
				String ctmId = insertVehicleSaleData(entry);
				// 成功写入实销数据将产生ctmId
				/**
				 * 实销成功后
				 *	1：将客户信息插入JEC客户接口表【TiSalesJecCustomer】
				 *	2：将车辆信息插入JEC车辆接口表【TiSalesJecVehiclePO】
				 *	然后DCS通过接口【SI19】将数据发给JEC系统(DCS->JEC)
				 */
				if (ctmId != null) {
					String jecCustomerId = insertSalesJecCustomer(entry, ctmId);
					insertSalesJecVehicle(entry, jecCustomerId);
				}
			} catch (Exception e) {
				logger.error("经销商车辆实销数据上报接收失败", e);
				msg = "0";
				throw new ServiceBizException(e);
			}
		}
		logger.info("====经销商车辆实销数据上报接收结束===="); 
		return msg;
	}
	
	
	/**
	 * 为JEC车辆管理导入数据
	 * 
	 * @param vo
	 * @throws Exception
	 */
	public synchronized void insertSalesJecVehicle(SalesOrderDto vo, String ctmId) throws Exception {
		Map<String, Object> map = saleDao.getSaDcsDealerCode(vo.getDealerCode());
		String dealerId = String.valueOf(map.get("DEALER_ID"));// 上报经销商信息

		// ===================车辆实销车辆信息_jec （新车）
		TiSalesJecVehiclePO salesJecVehiclePO = new TiSalesJecVehiclePO();
		salesJecVehiclePO.setLong("JEC_CUSTOMER_ID",Long.parseLong(ctmId)); // jec客户ID(主表SEQUENCE_ID)
		salesJecVehiclePO.setString("BUY_CITY",vo.getCity() + "");
		salesJecVehiclePO.setString("BUY_DEALER",dealerId); // 购车经销商
		salesJecVehiclePO.setString("BUY_PROVINCE",vo.getProvince() + "");
		// salesJecVehiclePO.setBuyStatus(buyStatus) //购车状态Timestamp
		if (vo.getSalesDate() != null) {
			salesJecVehiclePO.setString("BUY_TIME",formatDate(vo.getSalesDate())); // 购车日期
		}
		salesJecVehiclePO.setString("CAR_CODE",vo.getLicense());
		salesJecVehiclePO.setLong("CREATE_BY",DEConstant.DE_CREATE_BY);
		salesJecVehiclePO.setTimestamp("CREATE_DATE",new Date());
		if (vo.getSalesMileage() != null) {
			salesJecVehiclePO.setString("DELIVER_MILEAGE",String.valueOf(vo.getSalesMileage().intValue()));
		}
		salesJecVehiclePO.setString("IS_NEW_CAR","1"); // 是否新车 1：是，2：否

		// 查询车辆物料信息
		// 查询发动机号
		Map<String, Object> vehicleEngineNoMap = saleDao.getVehicleEngineNoByVin(vo.getVin());
		String engineNo = (String) vehicleEngineNoMap.get("ENGINE_NO");
		salesJecVehiclePO.setString("MOTOR_CODE",engineNo);
		// 查询物料id
		Map<String, Object> vehicleMaterialIdMap = saleDao.getVehicleMaterialIdByVin(vo.getVin());
		Long materialId = (Long) vehicleMaterialIdMap.get("MATERIAL_ID");
		// 查询具体物料
		if (materialId != null) {
			Map<String, Object> vehicleMaterialsMap = saleDao.queryVhclMaterialByMaterialId(materialId.longValue());
			if (vehicleMaterialsMap != null) {
				String colorCode = (String) vehicleMaterialsMap.get("COLOR_CODE"); // 颜色
				String groupCode = (String) vehicleMaterialsMap.get("GROUP_CODE"); // 下端车款
											// 对应上端车款
				String modleCode = (String) vehicleMaterialsMap.get("SERIES_CODE"); // 下端车型
												// 对应上端车系
				salesJecVehiclePO.setString("COLOR",colorCode);
				salesJecVehiclePO.setString("MODEL",saleDao.queryTreeCodeByGroupCode(modleCode));
				salesJecVehiclePO.setString("STYLE",saleDao.queryTreeCodeByGroupCode(groupCode));
			}
		}
		salesJecVehiclePO.setString("TRUNK_CODE",vo.getVin()); // 车架号
		// salesJecVehiclePO.setUpdateBy(updateBy)
		// salesJecVehiclePO.setUpdateDate(updateDate)
		salesJecVehiclePO.saveIt();

		// =======================车辆实销车辆信息_jec(二手车)
		List<SecondCarListDto> secondCarList = vo.getSecondCarList();
		if (secondCarList != null) {
			for (int i = 0; i < secondCarList.size(); i++) {
				SecondCarListDto secondCarListVo = secondCarList.get(i);
				salesJecVehiclePO = new TiSalesJecVehiclePO();
				salesJecVehiclePO.setLong("JEC_CUSTOMER_ID",Long.parseLong(ctmId));// 车辆实销客户ID(主表SEQUENCE_ID)
				salesJecVehiclePO.setString("BUY_CITY",vo.getCity() + ""); // 二手车vo里暂无城市信息，目前取主vo里的城市信息
				salesJecVehiclePO.setString("BUY_DEALER",dealerId); // 购车经销商
				salesJecVehiclePO.setString("BUY_PROVINCE",vo.getProvince() + ""); // 二手车vo里暂无省份信息，目前取主vo里的省份信息
				// salesJecVehiclePO.setBuyStatus(buyStatus) //购车状态Timestamp
				if (secondCarListVo.getBuyDate() != null) {
					salesJecVehiclePO.setString("BUY_TIME",formatDate(secondCarListVo.getBuyDate())); // 购车日期
				}
				salesJecVehiclePO.setString("CAR_CODE",secondCarListVo.getLicense());
				// salesJecVehiclePO.setColor(secondCarListVo.getColorName());//颜色通过vin查询物料中取得
				salesJecVehiclePO.setLong("CREATE_BY",DEConstant.DE_CREATE_BY);
				salesJecVehiclePO.setTimestamp("CREATE_DATE",new Date());
				if (vo.getSalesMileage() != null) {
					salesJecVehiclePO.setString("DELIVER_MILEAGE",String.valueOf(vo.getSalesMileage().intValue()));
				}
				salesJecVehiclePO.setString("IS_NEW_CAR","2"); // 是否新车 1：是，2：否

				// 查询发动机号
				Map<String, Object> secondCarVehicleEngineNoMap = saleDao.getVehicleEngineNoByVin(secondCarListVo.getVin());
				if (secondCarVehicleEngineNoMap != null) {
					String secondCarVehicleEngineNo = (String) secondCarVehicleEngineNoMap.get("ENGINE_NO");
					salesJecVehiclePO.setString("MOTOR_CODE",secondCarVehicleEngineNo);// 发动机编码
				}

				// 查询物料id
				Map<String, Object> secondCarVehicleMaterialIdMap = saleDao.getVehicleMaterialIdByVin(secondCarListVo.getVin());
				if (secondCarVehicleMaterialIdMap != null) {
					BigDecimal secondCarVehicleMaterialId = (BigDecimal) secondCarVehicleMaterialIdMap.get("MATERIAL_ID");
					// 查询具体物料
					if (secondCarVehicleMaterialId != null) {
						Map<String, Object> secondCarVehicleMaterialsMap = saleDao.queryVhclMaterialByMaterialId(secondCarVehicleMaterialId.longValue());
						if (secondCarVehicleMaterialsMap != null) {
							String secondCarColorCode = (String) secondCarVehicleMaterialsMap.get("COLOR_CODE"); // 颜色
							String secondCarGroupCode = (String) secondCarVehicleMaterialsMap.get("GROUP_CODE"); // 下端车款
														// 对应上端车款
							String secondCarModelCode = (String) secondCarVehicleMaterialsMap.get("SERIES_CODE"); // 购买车型 对应车系

							salesJecVehiclePO.setString("COLOR",secondCarColorCode);

							salesJecVehiclePO.setString("MODEL",saleDao.queryTreeCodeByGroupCode(secondCarModelCode));
							salesJecVehiclePO.setString("STYLE",saleDao.queryTreeCodeByGroupCode(secondCarGroupCode));
						}
					}
				}
				salesJecVehiclePO.setString("TRUNK_CODE",secondCarListVo.getVin()); // 车架号
				// salesJecVehiclePO.setUpdateBy(updateBy)
				// salesJecVehiclePO.setUpdateDate(updateDate)
				salesJecVehiclePO.saveIt();
			}
		}

	}
	
	
	/**
	 * 保存上报的车辆验收数据信息 返回TT_VS_CUSTOMER中所对应的客户id
	 * 
	 * @param vo
	 * @throws Exception
	 */
	public synchronized String insertVehicleSaleData(SalesOrderDto vo) throws Exception {
		Map<String, Object> map = saleDao.getSaDcsDealerCode(vo.getDealerCode());
		String dealerId = String.valueOf(map.get("DEALER_ID"));// 上报经销商信息
		Map<String, Object> vehicleMap = saleDao.getVehicleIdByVinForExist(vo.getVin(), dealerId);

		if (vehicleMap == null){// 判读该车辆实销信息是否存在
			logger.info("====经销商车辆实销数据上报出错，不存在VIN为" + vo.getVin() + "的车辆.");
			throw new ServiceBizException("====经销商车辆实销数据上报出错，不存在VIN为" + vo.getVin() + "的车辆.");
		} else {
			//保存实销信息
			String ctmId = insertSalesOrder(vo,dealerId);
			
			// 写入nvdrdata信息(零售上报 )
			/**
			 * 通过实销上报上来的车辆标记为交车已上报
			 * 即可在DCS总部端审核（实销管理 > 实销管理 > 零售上报审核）
			 */
			if (vo.getVin() != null && !isNvdrVinExist(vo.getVin())) {// 不存在则插入
				insertNVDRData(vo, dealerId);
			} else {// 存在则更新
				updateNVDRData(vo);
			}

			// 写入二手车信息
			insertSecondCar(vo, ctmId);
			
			return ctmId;
		}
	}
	
	/**
	 * 写入二手车信息
	 * @param vo
	 * @throws Exception
	 */
	public void insertSecondCar(SalesOrderDto vo, String ctmId) throws Exception{
		logger.info("=======保存二手车信息 start=========");
		List<SecondCarListDto> secondCarList = vo.getSecondCarList();
		if (secondCarList != null) {
			for (int i = 0; i < secondCarList.size(); i++) {
				SecondCarListDto secondCarListVo = secondCarList.get(i);
				TtVsSecondCarPO ttVsSecondCarPO = new TtVsSecondCarPO();
				ttVsSecondCarPO.setTimestamp("ANNUAL_INSPECTION_DATE",secondCarListVo.getAnnualInspectionDate());
				ttVsSecondCarPO.setString("BUSINESS",secondCarListVo.getBusiness());
				ttVsSecondCarPO.setTimestamp("BUY_DATE",secondCarListVo.getBuyDate());
				ttVsSecondCarPO.setString("COLOR_NAME",secondCarListVo.getColorName());
				ttVsSecondCarPO.setLong("CREATE_BY",DEConstant.DE_CREATE_BY);
				ttVsSecondCarPO.setTimestamp("CREATE_DATE",new Date());
				ttVsSecondCarPO.setLong("CTM_ID",Long.parseLong(ctmId));
				ttVsSecondCarPO.setString("CUSTOMER_TYPE",secondCarListVo.getCustomerType());
				ttVsSecondCarPO.setString("DRIVING_LICENSE",secondCarListVo.getDrivingLicense());
				ttVsSecondCarPO.setString("EMISSIONS",secondCarListVo.getEmissions());
				ttVsSecondCarPO.setString("ENGINE_NUM",secondCarListVo.getEngineNum());
				ttVsSecondCarPO.setString("FUEL_TYPE",secondCarListVo.getFuelType());
				ttVsSecondCarPO.setString("GEAR_FORM",secondCarListVo.getGearForm());
				ttVsSecondCarPO.setString("HBBJ",secondCarListVo.getHbbj());
				ttVsSecondCarPO.setInteger("IS_ARC",0);
				ttVsSecondCarPO.setInteger("IS_DEL",0);
				ttVsSecondCarPO.setString("LICENSE",secondCarListVo.getLicense());
//
//				Long lmId = SequenceManager.getSequence();
//				ttVsSecondCarPO.setLmId(lmId);// 主键

				ttVsSecondCarPO.setDouble("MILEAGE",secondCarListVo.getMileage());
				ttVsSecondCarPO.setString("MODEL_NAME",secondCarListVo.getModelName());
				ttVsSecondCarPO.setString("ORIGIN_CERTIFICATE",secondCarListVo.getOriginCertificate());
				ttVsSecondCarPO.setTimestamp("PRODUCTION_DATE",secondCarListVo.getProductionDate());
				ttVsSecondCarPO.setString("PURCHASE_TAX",secondCarListVo.getPurchaseTax());
				ttVsSecondCarPO.setString("REGISTRY",secondCarListVo.getRegistry());
				ttVsSecondCarPO.setString("REMARK",secondCarListVo.getRemark());
				ttVsSecondCarPO.setTimestamp("SCRAP_DATE",secondCarListVo.getScrapDate());
				ttVsSecondCarPO.setString("SERIES_NAME",secondCarListVo.getSeriesName());
				ttVsSecondCarPO.setTimestamp("TRAFFIC_INSURE_DATA",secondCarListVo.getTrafficInsureData());
				ttVsSecondCarPO.setString("TRAFFIC_INSURE_INFO",secondCarListVo.getTrafficInsureInfo());
				ttVsSecondCarPO.setLong("UPDATE_BY",DEConstant.DE_UPDATE_BY);
				ttVsSecondCarPO.setTimestamp("UPDATE_DATE",new Date());
				ttVsSecondCarPO.setString("USE_TYPE",secondCarListVo.getUseType());
				ttVsSecondCarPO.setString("VEHICLE_ALLOCATION",secondCarListVo.getVehicleAllocation());
				ttVsSecondCarPO.setString("VEHICLE_AND_VESSEL_TAX",secondCarListVo.getVehicleAndVesselTax());
				ttVsSecondCarPO.setInteger("VER",0);
				ttVsSecondCarPO.setString("VIN",secondCarListVo.getVin());
				ttVsSecondCarPO.setInteger("IS_ASSESSED",secondCarListVo.getIsAssessed());
				ttVsSecondCarPO.setDouble("ASSESSED_PRICE",secondCarListVo.getAssessedPrice());
				ttVsSecondCarPO.saveIt();
			}
		}
		logger.info("=======保存二手车信息 end=========");
	}
	
	
	
	public synchronized void insertNVDRData(SalesOrderDto vo, String dealerId) throws Exception {
		TtVsNvdrPO ttVsNvdrPO = new TtVsNvdrPO();
		ttVsNvdrPO.setString("BUSINCONTACT_NAME",vo.getContactorName()); // 业务联系人名称
		ttVsNvdrPO.setLong("BUSINESS_ID",Long.parseLong(dealerId)); // dealerId
		ttVsNvdrPO.setString("BUSINESS_NAME",vo.getCompanyName()); // 公司名称
		ttVsNvdrPO.setString("BUSINESS_PHONE",vo.getCompanyPhone()); // 工作电话
		// ttVsNvdrPO.setBusinmanageId(businmanageId) //业务经理ID
		if (vo.getCity() != null) {
			ttVsNvdrPO.setString("CITY",vo.getCity().toString()); // 城市
		}
		// ttVsNvdrPO.setCountry(country) //国家
		ttVsNvdrPO.setLong("CREATE_BY",DEConstant.DE_CREATE_BY);
		ttVsNvdrPO.setTimestamp("CREATE_DATE",new Date());
		// ttVsNvdrPO.setCustomerLanguage(customerLanguage) //客户语言
		// ttVsNvdrPO.setDeliveryDate(deliveryDate) //交付时间
		ttVsNvdrPO.setString("EMAIL",vo.getEmail()); // 电子邮件
		ttVsNvdrPO.setString("FAX_NUMBER",vo.getFax()); // 传真
		ttVsNvdrPO.setString("FIRST_NAME",vo.getCustomerName()); // 第一名称
		// ttVsNvdrPO.setIsArc(isArc)
		// ttVsNvdrPO.setIsContact(isContact) //是否接触
		// ttVsNvdrPO.setIsDel(isDel)
		// ttVsNvdrPO.setLease(lease) //Lease/Finance Terms
		ttVsNvdrPO.setString("MOBILE_PHONE",vo.getPhone()); // 手机
		// ttVsNvdrPO.setNationality(nationality) //国籍

//		Long nvdrId = SequenceManager.getSequence();
//		ttVsNvdrPO.setNvdrId(nvdrId);

		ttVsNvdrPO.setInteger("NVDR_STATUS",OemDictCodeConstants.VEHICLE_RETAIL_STATUS_02); // 零售交车状态
																		// 20971001;//待审核
		if (vo.getMileage() != null) {
			ttVsNvdrPO.setLong("ODOMETER",vo.getMileage().longValue());// 里程
		}
		ttVsNvdrPO.setString("POSTAL_CODE",vo.getZipCode()); // 邮政编号
		// ttVsNvdrPO.setPreferredTitle(preferredTitle) //主要标题
		// ttVsNvdrPO.setPrimaryPhone(primaryPhone) //主电话
		if (vo.getProvince() != null) {
			ttVsNvdrPO.setString("PROVINCE",vo.getProvince().toString());
		}// 省
			// ttVsNvdrPO.setRegistrationDate(registrationDate) //登记日期
			// ttVsNvdrPO.setRegistrationNumber(registrationNumber) //注册号码
		ttVsNvdrPO.setInteger("REPORT_TYPE",OemDictCodeConstants.RETAIL_REPORT_TYPE_02);// 零售上报类型
																	// 交车上报20851002
		// ttVsNvdrPO.setSalesmanageId(salesmanageId) //销售经理
		// ttVsNvdrPO.setSalespersonId(salespersonId) //销售ID
		// ttVsNvdrPO.setSaleType(saleType) //销售类型
		// ttVsNvdrPO.setSalutation(salutation) //称呼
		// ttVsNvdrPO.setSecondName(secondName) //第二名称
		ttVsNvdrPO.setInteger("STATUS",OemDictCodeConstants.VEHICLE_REPORT_STATUS_03); // 交车上报状态
																	// 已上报20981003
		ttVsNvdrPO.setString("STREET_ADDRESS",vo.getAddress()); // 地址
		// ttVsNvdrPO.setThirdName(thirdName) //第三名称
		// ttVsNvdrPO.setTitle(title) //标题
		// ttVsNvdrPO.setUpdateBy(DEConstant.DE_UPDATE_BY);
		// ttVsNvdrPO.setUpdateDate(new Date());
		// ttVsNvdrPO.setUrbanization(urbanization) //城市化
		// ttVsNvdrPO.setVer(ver)
		ttVsNvdrPO.setString("VIN",vo.getVin());
		ttVsNvdrPO.setTimestamp("REPORT_DATE",vo.getSubmitTime());
		ttVsNvdrPO.saveIt();
	}

	public synchronized void updateNVDRData(SalesOrderDto vo) throws Exception {
		TtVsNvdrPO selVsNvdrPO =  TtVsNvdrPO.findFirst(" VIN = ? ", vo.getVin());
		selVsNvdrPO.setInteger("STATUS",OemDictCodeConstants.VEHICLE_REPORT_STATUS_03); // 交车上报状态
																	// 已上报20981003
		selVsNvdrPO.setTimestamp("REPORT_DATE",vo.getSubmitTime());

		selVsNvdrPO.saveIt();
	}
	
	
	/**
	 * 将接收到的实销信息
	 * 1:将实销表中已存在此车相关的历史实销记录置为无效状态
	 * 2：增加客户联系人信息
	 * 3：新增实销信息
	 * 4：插入微信车主信息接口表中
	 * 5：发票信息上传
	 * 6：修改车辆信息
	 * 7：更新节点车辆状态时间
	 * @param vo
	 * @param vehicleId
	 * @param nowDate
	 * @param saleId
	 * @throws Exception
	 */
	public String insertSalesOrder(SalesOrderDto vo,String dealerId) throws Exception {
		// 实销信息不存在 获得车辆ID信息
		Map<String, Object> vehicleIdMap = saleDao.getVehicleIdByVin(vo.getVin());
		String vehicleId = vehicleIdMap.get("VEHICLE_ID").toString();

		// 将实销表中已存在此车相关的历史实销记录置为无效状态
		logger.info("*************************** 将实销表中已存在此车相关的历史实销记录置为无效状态 ******************************");
		TtVsSalesReportPO conSales = TtVsSalesReportPO.findFirst(" VEHICLE_ID = ? ", vehicleId);
		conSales.setInteger("STATUS",OemDictCodeConstants.STATUS_DISABLE);
		conSales.setLong("UPDATE_BY",DEConstant.DE_CREATE_BY);
		conSales.setTimestamp("UPDATE_DATE",new Date());
		conSales.saveIt();

		Date nowDate = new Date();
//		String saleId = SequenceManager.getSequence("");
		
		TtVsSalesReportPO salesPO = new TtVsSalesReportPO();// 是否屏蔽已传过的
//		salesPO.setReportId(Long.parseLong(saleId));
		// 获得上报的客户信息

		String customName = vo.getCustomerName();// 客户名字
		String cardNum = vo.getCertificateNo();// 卡号
//		Integer cardType = vo.getCtCode();// 卡类型
		Integer cardType = null;
		if (vo.getCtCode() != null) {
			cardType = getLocalStatusByDealerStatus(vo.getCtCode());// 卡类型
		}

		Map<String, Object> customExistMap = saleDao.getCustomInfo(customName, cardNum, cardType, dealerId);
		// 获得经销商详细信息
		Map<String, Object> dealerInfoMap = saleDao.getDealerInfo(dealerId);
		String oemCompanyID = dealerInfoMap.get("OEM_COMPANY_ID").toString();
		String dealerCompanyID = dealerInfoMap.get("COMPANY_ID").toString();
		String ctmId = null;
		if (customExistMap != null){// 客户存在 实销直接获得客户id 不在存储客户信息
			logger.info("*************************** 存在客户信息 ******************************");
			ctmId = customExistMap.get("CTM_ID").toString();
			salesPO.setLong("CTM_ID",Long.parseLong(ctmId));
		} else {
			logger.info("*************************** 不存在客户信息 ******************************");
			// 增客户信息
			TtVsCustomerPO customPO = new TtVsCustomerPO();
			customPO.setLong("CTM_ID",Long.parseLong(ctmId));
			// customPO.setVehicleId(Long.parseLong(vehicleId));
			customPO.setString("CTM_NAME",vo.getCustomerName());
			customPO.setString("MAIN_PHONE",vo.getPhone());
			customPO.setString("ADDRESS",vo.getAddress());
	
			
			if (vo.getGender() != null) {
				Integer tempSex = getLocalStatusByDealerStatus(vo.getGender());
				customPO.setInteger("SEX",tempSex);
			}
			if (vo.getCtCode() != null) {
				Integer tempCardType = getLocalStatusByDealerStatus(vo.getCtCode());
				customPO.setInteger("CARD_TYPE",tempCardType);
			}
			customPO.setString("CARD_NUM",vo.getCertificateNo());
			customPO.setString("EMAIL",vo.getEmail());
			customPO.setString("POST_CODE",vo.getZipCode());
			customPO.setDate("BIRTHDAY",vo.getBirthday());
			if (vo.getOwnerMarriage() != null) {
				Integer tempMarrInfo = getLocalStatusByDealerStatus(vo.getOwnerMarriage());
				customPO.setInteger("IS_MARRIED",tempMarrInfo);
			}
			if (vo.getFamilyIncome() != null) {
				/*
				 * Integer tempIncome = getLocalStatusByDealerStatus(vo
				 * .getFamilyIncome()); customPO.setIncome(tempIncome);
				 */
				customPO.setInteger("INCOME",vo.getFamilyIncome());
			}
			if (vo.getEducationLevel() != null) {
				Integer tempEducation = getLocalStatusByDealerStatus(vo.getEducationLevel());
				customPO.setInteger("EDUCATION",tempEducation);
			}
			String tempProvince = vo.getProvince() + "";
			String tempCity = vo.getCity() + "";
			String tempTown = vo.getDistrict() + "";
			if (null != vo.getProvince() && tempProvince.trim().length() > 0) {
				customPO.setLong("PROVINCE",Long.parseLong(tempProvince));
			}
			if (null != vo.getCity() && tempCity.trim().length() > 0) {
				customPO.setLong("CITY",Long.parseLong(tempCity));
			}
			if (null != vo.getDistrict() && tempTown.trim().length() > 0) {
				customPO.setLong("TOWN",Long.parseLong(tempTown));
			}
			// customPO.setRemark(vo.getRemark());
			customPO.setInteger("Status",OemDictCodeConstants.STATUS_ENABLE);
			// customPO.setDealerId(Long.parseLong(dealerId));
			customPO.setString("SALES_ADVISER",vo.getSoldBy());// 销售顾问
			
			customPO.setString("SOLD_BYID",vo.getSoldById());//销售顾问id
			customPO.setString("SOLD_EMAIL",vo.getSoldEmail());	//销售顾问邮箱
			customPO.setString("SOLD_MOBILE",vo.getSoldMobile());//销售顾问电话
			
			customPO.setLong("DLR_COMPANY_ID",Long.parseLong(dealerCompanyID));
			customPO.setLong("OEM_COMPANY_ID",Long.parseLong(oemCompanyID));
			customPO.setTimestamp("CREATE_DATE",nowDate);
			customPO.setLong("CREATE_BY",DEConstant.DE_CREATE_BY);

			customPO.setString("WEDDING_DAY",vo.getWeddingDay());
			//保客推荐时，添加老客户到新客户推荐人字段
			if(vo.getCtmForm().equals("保客推荐")){
				Map<String, Object> vehicleIdMapOld = saleDao.getOldVehicleIdByVin(vo.getOldCustomerVin());
				if(vehicleIdMapOld == null || vehicleIdMapOld.equals("")){
					logger.info("====添加推荐人时，不存在老客户Vin为" + vo.getOldCustomerVin() + "的车辆.");
					customPO.setString("REFERENCE",vo.getReference());
				}else{
					String vehicleIdOld = vehicleIdMapOld.get("VEHICLE_ID").toString();
//					String sql = "select CTM_ID from TT_VS_SALES_REPORT where VEHICLE_ID='"+vehicleIdOld+"' and STATUS=10011001";
					List<TtVsSalesReportPO> salesReportList = TtVsSalesReportPO.find(" VEHICLE_ID= ? and STATUS=10011001 ", vehicleIdOld);
					//保客推荐老客户在客户表不存在情况下过滤以下逻辑             2016-4-21bug版本
					if(salesReportList != null && salesReportList.size() > 0){
//						String sql1 = "select CTM_NAME from TT_VS_CUSTOMER where CTM_ID='"+salesReportList.get(0).getLong("CTM_ID")+"'";
						List<TtVsCustomerPO> ctm = TtVsCustomerPO.find(" CTM_ID = ? ", salesReportList.get(0).getLong("CTM_ID"));
						if(ctm != null && ctm.size() > 0){
							customPO.setString("REFERENCE",ctm.get(0).getString("CTM_NAME"));
						}else{
							logger.info("====添加推荐人时，老客户:" + salesReportList.get(0).getLong("CTM_ID") + "，在客户实销上报表中不存在！！！");
						}
						
					}else{
						logger.info("====添加推荐人时，不存在老客户Vin为" + vo.getOldCustomerVin() + "的车辆，在实销上报表中不存在！！！");
					}

				}
			}else{
				customPO.setString("REFERENCE",vo.getReference());
			}
			
			customPO.setString("REFERENCE_TEL",vo.getReferenceTel());
			
			Integer ctmType = 0;
			if (vo.getCtmType() != null && vo.getCtmType() == 10181001) {
				ctmType = 20291001;// 个人
			} else if (vo.getCtmType() != null && vo.getCtmType() == 10181002) {
				ctmType = 20291002;// 公司
			}
			customPO.setInteger("CTM_TYPE",ctmType);
			customPO.setString("FAX",vo.getFax());
			customPO.setString("BEST_CONTACT_TYPE",vo.getBestContactType());
			customPO.setString("BEST_CONTACT_TIME",vo.getBestContactTime());
			customPO.setString("HOBBY",vo.getHobby());
			customPO.setString("INDUSTRY_FIRST",vo.getIndustryFirst());
			customPO.setString("INDUSTRY_SECOND",vo.getIndustrySecond());
			customPO.setString("VOCATION_TYPE",vo.getVocationType());
			customPO.setString("POSITION_NAME",vo.getPositionName());
			customPO.setString("IS_FIRST_BUY",vo.getIsFirstBuy());
			customPO.setString("IS_PERSON_DRIVE_CAR",vo.getIsPersonDriveCar());
			customPO.setString("BUY_PURPOSE",vo.getBuyPurpose());
			customPO.setString("CHOICE_REASON",vo.getChoiceReason());
			customPO.setString("BUY_REASON",vo.getBuyReason());
			customPO.setString("CTM_FORM",vo.getCtmForm());
			customPO.setString("MEDIA_TYPE",vo.getMediaType());
			customPO.setString("MEDIA_DETAIL",vo.getMediaDetail());
			customPO.setString("FAMILY_MEMBER",vo.getFamilyMember());
			customPO.setString("IM",vo.getIm());
			customPO.setString("COMPANY_PHONE",vo.getCompanyPhone());
			customPO.setString("COMPANY_NAME",vo.getCompanyName());
			customPO.setString("EXIST_BRAND",vo.getExistBrand());
			customPO.setString("EXIST_SERIES",vo.getExistSeries());
			customPO.setString("EXIST_MODEL",vo.getExistModel());
			customPO.setString("PURCHASE_YEAR",vo.getPurchaseYear());
			customPO.setDouble("MILEAGE",vo.getMileage());
			customPO.setString("PURCHASE_DIFFER",vo.getPurchaseDiffer());
			customPO.setString("EDITOR",vo.getEditor());
			customPO.setDouble("BUDGET_AMOUNT",vo.getBudgetAmount());
			customPO.setString("INTENTION_BRAND",vo.getIntentionBrand());
			customPO.setString("INTENTION_SERIES",vo.getIntentionSeries());
			customPO.setString("INTENTION_MODEL",vo.getIntentionModel());
			customPO.setString("INTENTION_COLOR",vo.getIntentionColor());
			customPO.setString("INTENTION_REMARK",vo.getIntentionRemark());
			customPO.setString("OTHER_NEED",vo.getOtherNeed());
			customPO.setString("OWNER_NO",vo.getOwnerNo());
			//is_dcc_offer
			if (null != vo.getIsDccOffer()) {
				customPO.setInteger("IS_DCC_OFFER",vo.getIsDccOffer());
			}
			//end
			logger.info("*************************** 保存客户信息 start ******************************");
			customPO.saveIt();
			logger.info("*************************** 保存客户信息 end ******************************");
			
			//当客户来源为保客推荐时，新增老客户信息    by wangJian 2015-10-15  start
			String ctmForm = vo.getCtmForm();
			if(ctmForm.equals("保客推荐")){
				logger.info("=======存入OLD_VIN,NEW_CTM到保客表 start=========");
//				String ctmOldId = SequenceManager.getSequence("");
				TtVsCustomerOldPO ctmOld = new TtVsCustomerOldPO();
//				ctmOld.setId(Long.valueOf(ctmOldId));
				ctmOld.setString("VIN",vo.getOldCustomerVin());//老客户VIN
				ctmOld.setLong("CTM_ID",Long.parseLong(ctmId));
				ctmOld.setTimestamp("CREATE_DATE",new Date());
				ctmOld.setLong("CREATE_BY",DEConstant.DE_CREATE_BY);
				ctmOld.saveIt();
				logger.info("=======存入OLD_VIN,NEW_CTM到保客表 end=========");
			
			}//end
			
			List<LinkManListDto> linkManList = vo.getLinkManList();
			// 增加客户联系人信息
			for (int i = 0; i < linkManList.size(); i++) {
				LinkManListDto tempLinkVO = (LinkManListDto) linkManList.get(i);
				TtVsLinkmanPO linkManPO = new TtVsLinkmanPO();
//				String linkManId = SequenceManager.getSequence("");
//				linkManPO.setLmId(Long.parseLong(linkManId));
				linkManPO.setLong("OEM_COMPANY_ID",Long.parseLong(oemCompanyID));
				linkManPO.setLong("DLR_COMPANY_ID",Long.parseLong(dealerCompanyID));
				linkManPO.setString("NAME",tempLinkVO.getName());
				linkManPO.setString("MAIN_PHONE",tempLinkVO.getMainPhone());
				linkManPO.setString("OTHER_PHONE",tempLinkVO.getOtherPhone());
				linkManPO.setString("CONTRACT_REASON",tempLinkVO.getContractReason());
				linkManPO.setInteger("STATUS",OemDictCodeConstants.STATUS_ENABLE);
				linkManPO.setTimestamp("CREATE_DATE",nowDate);
				linkManPO.setLong("CREATE_BY",DEConstant.DE_CREATE_BY);
				linkManPO.saveIt();
			}
			salesPO.setLong("CTM_ID",Long.parseLong(ctmId));
		}

		salesPO.setLong("OEM_COMPANY_ID",Long.parseLong(oemCompanyID));
		salesPO.setLong("DLR_COMPANY_ID",Long.parseLong(dealerCompanyID));
		salesPO.setLong("DEALER_ID",Long.parseLong(dealerId));
		salesPO.setLong("VEHICLE_ID",Long.parseLong(vehicleId));
		salesPO.setString("VEHICLE_NO",vo.getLicense());
		salesPO.setString("CONTRACT_NO",vo.getSoNo());
		salesPO.setString("INVOICE_NO",vo.getInvoiceNo());
		salesPO.setTimestamp("INVOICE_DATE",vo.getInvoiceDate());
		salesPO.setString("INSURANCE_COMPANY",vo.getInsurationName());
		salesPO.setTimestamp("INSURANCE_DATE",vo.getInsuranceBuyDate());
		String tempMile = vo.getSalesMileage() + "";
		if(tempMile!=null && tempMile.equals("")){
			salesPO.setFloat("MILES",Float.parseFloat(tempMile));
		}else{
			salesPO.setFloat("MILES",null);
		}
		if(vo.getPayMode()!=null){
			Integer tempPayMent = getLocalStatusByDealerStatus(vo.getPayMode());
			salesPO.setInteger("PAYMENT",tempPayMent);
		}
		if(vo.getVehicleType()!=null){
			Integer tempVehiType = getLocalStatusByDealerStatus(vo.getVehicleType());// 车辆用途
			salesPO.setInteger("VEHICLE_TYPE",tempVehiType);// 车辆用途 存到车辆性质列，已跟lihongbin确认
		}
		salesPO.setString("SALES_MAN",vo.getSoldBy());
		salesPO.setTimestamp("SALES_DATE",vo.getRecordDate());//开票登记日期   (2015-07-02)  之前取create_date时间错，现在修改的  已跟baojie确认
		salesPO.setTimestamp("CONSIGNATION_DATE",vo.getSalesDate());
		salesPO.setInteger("STATUS",OemDictCodeConstants.STATUS_ENABLE);
		salesPO.setDouble("PRICE",vo.getVehiclePrice());
		salesPO.setString("MEMO",vo.getRemark());
		salesPO.setTimestamp("CREATE_DATE",nowDate);
		salesPO.setLong("CREATE_BY",DEConstant.DE_CREATE_BY);
		salesPO.setInteger("LOAN_ORG",vo.getLoanOrg());
		salesPO.setDouble("INSTALLMENT_AMOUNT",vo.getInstallmentAmount());
		salesPO.setInteger("INSTALLMENT_NUMBER",vo.getInstallmentNumber());
		salesPO.setDouble("FIRST_PERMENT_RATIO",vo.getFirstPermentRatio());
		salesPO.setFloat("LOAN_RATE",vo.getLoanRate());
		logger.info("=======保存实销信息 start=========");
		salesPO.saveIt();
		logger.info("=======保存实销信息 end=========");

		// 将实销信息通过原接口插入微信车主信息接口表中 add by dengweili 20140319
		logger.info("=======保存微信车主信息 start=========");
		TiWxCustomerPO wxcustomer = new TiWxCustomerPO();
//		Long wxCustomerId = SequenceManager.getSequence();
//		wxcustomer.setCustomerId(wxCustomerId);
		wxcustomer.setLong("SALES_REPORT_ID",salesPO.getLongId());
		wxcustomer.setLong("CREATE_BY",DEConstant.DE_CREATE_BY);
		wxcustomer.setTimestamp("CREATE_DATE",nowDate);
		wxcustomer.setString("IS_SCAN","0");
		wxcustomer.saveIt();
		logger.info("=======保存微信车主信息 end=========");
		// 将实销信息通过原接口插入微信车主信息接口表中 add by dengweili 20140319

		// 发票信息上传
		logger.info("=======更新发票信息 start=========");
		Map<String, Object> ywzjMap = saleDao.getReportIdIsExist(salesPO.getLongId());
		if (ywzjMap == null || ywzjMap.size() <= 0) {
			if (vo.getFileId() != null) {
				FsFileuploadPO fsFileupdateConditionPO = FsFileuploadPO.findFirst(" FJID = ? ", vo.getFileId());
//				fsFileupdateConditionPO.setFjid(Long.valueOf(vo.getFileId()));

//				FsFileuploadPO fsFileuploadPO = new FsFileuploadPO();
				fsFileupdateConditionPO.setLong("YWZJ",salesPO.getLongId());
				fsFileupdateConditionPO.saveIt();
				/*
				 * fsFileuploadPO.setFileurl("http://10.180.6.175:8082/dms/"
				 * + vo.getFileName());
				 * fsFileuploadPO.setFilename(vo.getFileName());
				 * fsFileuploadPO.setFileid(vo.getFileName());
				 */
				// fsFileuploadPO.setFileid(vo.getFileId());
				// fsFileuploadPO.setFileurl(vo.getFileUrl());
				// fsFileuploadPO.setStatus(10011001);
				// fsFileuploadPO.setCreateBy(9999999999L);
				// fsFileuploadPO.setCreateDate(new Date());
//				saleDao.update(fsFileupdateConditionPO, fsFileuploadPO);
			}
		}
		logger.info("=======更新发票信息 end=========");

		// 修改车辆信息
		TmVehiclePO paraVehiclePO = TmVehiclePO.findById(vehicleId);
//		paraVehiclePO.setVehicleId(Long.parseLong(vehicleId));
		paraVehiclePO.setTimestamp("PURCHASE_DATE",vo.getInvoiceDate());
		// valueVehiclePO.setWarrantyDate(vo.getWrtBeginDate());
		// valueVehiclePO.setManualNo(vo.getHandbookNo());
		paraVehiclePO.setDouble("MILEAGE",vo.getMileage());
		paraVehiclePO.setLong("UPDATE_BY",DEConstant.DE_CREATE_BY);
		paraVehiclePO.setTimestamp("UPDATE_DATE",nowDate);
		paraVehiclePO.setString("LICENSE_NO",vo.getLicense());
		paraVehiclePO.setInteger("LIFE_CYCLE",OemDictCodeConstants.LIF_CYCLE_05);
		/* modify by sumin 20151116 start*/
		// 获得车辆业务类别
		String businessType = saleDao.getVehicleBusinessType(vehicleId, null);
		if (!"".equals(CommonUtils.checkNull(businessType))) {
			if (OemDictCodeConstants.GROUP_TYPE_DOMESTIC.equals(businessType)) {
				
				/*
				 * 国产车业务处理
				 */
				
				paraVehiclePO.setInteger("NODE_STATUS",OemDictCodeConstants.K4_VEHICLE_NODE_20);
				
				// 更新车辆节点日期记录表（存储日期）
				TtVehicleNodeHistoryPO conVehicleNodeHistoryPo =  TtVehicleNodeHistoryPO.findFirst(" VEHICLE_ID = ? ", vehicleId);
//				conVehicleNodeHistoryPo.setVehicleId(Long.parseLong(vehicleId));	// 车辆ID
//				TtVehicleNodeHistoryPO setVehicleNodeHistoryPo = new TtVehicleNodeHistoryPO();
				conVehicleNodeHistoryPo.setTimestamp("YYSX_DATE",nowDate);	// 已实销日期
				conVehicleNodeHistoryPo.setInteger("IS_DEL",0);
				conVehicleNodeHistoryPo.setLong("UPDATE_BY",DEConstant.DE_CREATE_BY);
				conVehicleNodeHistoryPo.setTimestamp("UPDATE_DATE",nowDate);
//				factory.update(conVehicleNodeHistoryPo, setVehicleNodeHistoryPo);
				conVehicleNodeHistoryPo.saveIt();
				
				// 增加车籍信息
				TtVsVhclChngPO vehicleChangPO = new TtVsVhclChngPO();
				vehicleChangPO.setLong("VEHICLE_ID",Long.parseLong(vehicleId));
				vehicleChangPO.setInteger("CHANGE_CODE",OemDictCodeConstants.K4_VEHICLE_NODE_20);
				vehicleChangPO.setTimestamp("CHANGE_DATE",nowDate);
				vehicleChangPO.setString("CHANGE_NAME","实销上报");
				vehicleChangPO.setString("CHANGE_DESC","经销商车辆实销上报");
				vehicleChangPO.setString("CHANGE_MEMO","经销商车辆实销上报");
				vehicleChangPO.setLong("CREATE_BY",DEConstant.DE_CREATE_BY);
				vehicleChangPO.setTimestamp("CREATE_DATE",nowDate);
				vehicleChangPO.saveIt();
				
			} else if (OemDictCodeConstants.GROUP_TYPE_IMPORT.equals(businessType)) {
				
				/*
				 * 进口车业务处理
				 */
				
				paraVehiclePO.setInteger("NODE_STATUS",OemDictCodeConstants.VEHICLE_NODE_16);
				
				// 增加车籍信息
				TtVsVhclChngPO vehicleChangPO = new TtVsVhclChngPO();
				vehicleChangPO.setLong("VEHICLE_ID",Long.parseLong(vehicleId));
				vehicleChangPO.setInteger("CHANGE_CODE",OemDictCodeConstants.VEHICLE_CHANGE_TYPE_10);
				vehicleChangPO.setTimestamp("CHANGE_DATE",nowDate);
				vehicleChangPO.setString("CHANGE_NAME","实销上报");
				vehicleChangPO.setString("CHANGE_DESC","经销商车辆实销上报");
				vehicleChangPO.setString("CHANGE_MEMO","经销商车辆实销上报");
				vehicleChangPO.setLong("CREATE_BY",DEConstant.DE_CREATE_BY);
				vehicleChangPO.setTimestamp("CREATE_DATE",nowDate);
				vehicleChangPO.saveIt();
			} else {

			}
		} else {
			paraVehiclePO.setInteger("NODE_STATUS",OemDictCodeConstants.VEHICLE_NODE_16);
		}
		/* modify by sumin 20151116 end*/
		paraVehiclePO.saveIt();

		//更新节点车辆状态时间 add by luoyg
		logger.info("=======更新节点车辆状态时间 start=========");
		List<TmVehicleNodeHistoryPO> tmhList = TmVehicleNodeHistoryPO.find(" VEHICLE_ID = ? ", paraVehiclePO.getLong("VEHICLE_ID"));
		TmVehicleNodeHistoryPO tmhPO = new TmVehicleNodeHistoryPO();
		if(tmhList.size()==0){
			tmhPO.setLong("VEHICLE_ID", paraVehiclePO.getLong("VEHICLE_ID"));
			tmhPO.saveIt();
		}else{
			tmhPO = tmhList.get(0);
		}
		tmhPO.setTimestamp("SALE_DATE",nowDate);
		tmhPO.saveIt();
		logger.info("=======更新节点车辆状态时间 end=========");
		return ctmId;
	}
	
	
	
	
	// 判断指定的vin对应的nvdr是否存在
	private boolean isNvdrVinExist(String vin) throws Exception {
		String nvdrId = vsNvdrDao.queryVsNvdrIdByVin(vin);
		return nvdrId != null;
	}
	
	
	public Integer getLocalStatusByDealerStatus(Integer dealerStatus) {
		// 车辆用途 存到车辆性质字段，已跟lihongbin确认
		if (dealerStatus == 11931001) {
			return OemDictCodeConstants.VEHICLE_USE_09; // 下端 商务车-->上端 大客户购车
		}
		if (dealerStatus == 11931002) {
			return OemDictCodeConstants.VEHICLE_USE_01; // 下端 私家车-->上端 普通
		}
		if (dealerStatus == 11931003) {
			return OemDictCodeConstants.VEHICLE_USE_05; // 下端 出租车-->上端 服务用车
		}
		if (dealerStatus == 11931004) {
			return OemDictCodeConstants.VEHICLE_USE_06; // 下端 警车-->上端 其他
		}
		if (dealerStatus == 11931005) {
			return OemDictCodeConstants.VEHICLE_USE_04; // 下端 租赁公司-->上端 公司租赁车
		}
		// 车辆性质
		/*
		 * if (dealerStatus == 11931001) { return
		 * Constant.VEHICLE_NATURE_TYPE_01; } if (dealerStatus == 11931002) {
		 * return Constant.VEHICLE_NATURE_TYPE_02; } if (dealerStatus ==
		 * 11931003) { return Constant.VEHICLE_NATURE_TYPE_03; } if
		 * (dealerStatus == 11931004) { return Constant.VEHICLE_NATURE_TYPE_04;
		 * } if (dealerStatus == 11931005) { return
		 * Constant.VEHICLE_NATURE_TYPE_05; }
		 */
		// 付款方式
		if (dealerStatus == 10251001) {
			return OemDictCodeConstants.VEHICLE_SALE_PAY_TYPE_01;
		}
		if (dealerStatus == 10251003) {
			return OemDictCodeConstants.VEHICLE_SALE_PAY_TYPE_02;
		}
		if (dealerStatus == 10251002) {
			return OemDictCodeConstants.VEHICLE_SALE_PAY_TYPE_03;
		}
		// 性别
		if (dealerStatus == 10061001) {
			return OemDictCodeConstants.MAN;
		}
		if (dealerStatus == 10061002) {
			return OemDictCodeConstants.WOMEN;
		}
		if (dealerStatus == 10061003) {
			return OemDictCodeConstants.NONO;
		}
		// 婚姻状态
		if (dealerStatus == 11191001) {
			return OemDictCodeConstants.MARRIED_TYPE_01;
		}
		if (dealerStatus == 11191002) {
			return OemDictCodeConstants.MARRIED_TYPE_02;
		}
		// 教育状态
		if (dealerStatus == 11161001) {
			return OemDictCodeConstants.EDUCATION_TYPE_01;
		}
		if (dealerStatus == 11161002) {
			return OemDictCodeConstants.EDUCATION_TYPE_02;
		}
		if (dealerStatus == 11161003) {
			return OemDictCodeConstants.EDUCATION_TYPE_03;
		}
		if (dealerStatus == 11161004) {
			return OemDictCodeConstants.EDUCATION_TYPE_04;
		}
		if (dealerStatus == 11161005) {
			return OemDictCodeConstants.EDUCATION_TYPE_05;
		}
		if (dealerStatus == 11161006) {
			return OemDictCodeConstants.EDUCATION_TYPE_06;
		}
		// 家庭月收入
		if (dealerStatus == 11181001) {
			return OemDictCodeConstants.INCOME_TYPE_01;
		}
		if (dealerStatus == 11181002) {
			return OemDictCodeConstants.INCOME_TYPE_02;
		}
		if (dealerStatus == 11181003) {
			return OemDictCodeConstants.INCOME_TYPE_03;
		}
		if (dealerStatus == 11181004) {
			return OemDictCodeConstants.INCOME_TYPE_04;
		}
		if (dealerStatus == 11181005) {
			return OemDictCodeConstants.INCOME_TYPE_05;
		}
		if (dealerStatus == 11181006) {
			return OemDictCodeConstants.INCOME_TYPE_06;
		}

		// 证件类型
		if (dealerStatus == 12391001) {
			return OemDictCodeConstants.CARD_TYPE_01;
		}
		if (dealerStatus == 12391002) {
			return OemDictCodeConstants.CARD_TYPE_04;
		}
		if (dealerStatus == 12391003) {
			return OemDictCodeConstants.CARD_TYPE_02;
		}
		if (dealerStatus == 12391004) {
			return OemDictCodeConstants.CARD_TYPE_05;
		}
		if (dealerStatus == 12391005) {
			return OemDictCodeConstants.CARD_TYPE_03;
		}
		if (dealerStatus == 12391006) {
			return OemDictCodeConstants.CARD_TYPE_06;
		}
		if (dealerStatus == 12391007) {
			return OemDictCodeConstants.CARD_TYPE_07;
		}
		return 0;
	}
	
	/**
	 * 将时间格式化为 yyyy-MM-dd hh:mm:ss
	 * 
	 * @param Date
	 *            date
	 */
	private String formatDate(Date date) {
		SimpleDateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd");
		String result = dateformat.format(date);
		return result;
	}
	
	/**
	 * 为JEC客户管理导入数据
	 * 
	 * @param vo
	 * @throws Exception
	 */
	public String insertSalesJecCustomer(SalesOrderDto vo, String ctmId) throws Exception{
		TiSalesJecCustomerPO salesJecCustomerPO = new TiSalesJecCustomerPO();
		
		salesJecCustomerPO.setString("CODE",ctmId); // 客户唯一ID
		// salesJecCustomerPO.setAcceptPost(acceptPost) //请勿打扰
		salesJecCustomerPO.setString("ADDRESS",vo.getAddress());
		salesJecCustomerPO.setString("CELL_PHONE",vo.getPhone());

		// 往jecCustomer表里写数据的时候 如果是直辖市 那么city字段写入区域（县）信息
		if (isSpCityCode(vo.getCity())) {
			salesJecCustomerPO.setString("CITY",vo.getDistrict() + "");
		} else {
			salesJecCustomerPO.setString("CITY",vo.getCity() + "");
		}
		salesJecCustomerPO.setLong("CREATE_BY",DEConstant.DE_CREATE_BY);
		salesJecCustomerPO.setTimestamp("CREATE_DATE",new Date());
		// salesJecCustomerPO.setDirveAge(dirveAge) //驾龄 暂无
		salesJecCustomerPO.setString("EMAIL",vo.getEmail());
		salesJecCustomerPO.setString("GENDER",vo.getGender() + "");

		// 将类似于;:旅游:室内健身:逛街/购物这样的dms爱好字段转换为code
		if (vo.getHobby() != null) {
			salesJecCustomerPO.setString("HOBBY",convertDmsHobbyToJec(vo.getHobby()));
		}
		salesJecCustomerPO.setString("ID_OR_COMPCODE",vo.getCertificateNo());// 身份证号码/企业代码暂无
																	// 目前填的是证件号码
		salesJecCustomerPO.setString("IS_SCAN","0");
		salesJecCustomerPO.setString("NAME",vo.getCustomerName());// 车主姓名/企业名称
		salesJecCustomerPO.setString("POST_CODE",vo.getZipCode());
		salesJecCustomerPO.setString("PROVINCE",vo.getProvince() + "");
		// salesJecCustomerPO.setUpdateBy(updateBy)
		// salesJecCustomerPO.setUpdateDate(updateDate)
		salesJecCustomerPO.saveIt();
		return salesJecCustomerPO.getLongId().toString();
	}
	
	// 往jecCustomer表里写数据的时候 如果是直辖市 那么city字段写入区域（县）信息
	public boolean isSpCityCode(Integer cityCode) {
			// 北京上海天津重庆
		return cityCode != null
				&& (cityCode.equals(1101) || cityCode.equals(3101)
						|| cityCode.equals(1201) || cityCode.equals(5001));
	}
	
	private Map hobbyMap;
	
	
	private Map initHobbyMap() {
		hobbyMap = new HashMap<>();
		List<Map<String, Object>> hobbyList = saleDao.queryHobbyInfo();
		if (hobbyList != null && hobbyList.size() > 0) {
			for (int i = 0; i < hobbyList.size(); i++) {
				Map<String, Object> hobbyInfo = hobbyList.get(i);
				hobbyMap.put(hobbyInfo.get("RELATION_CN"),
						hobbyInfo.get("RELATION_CODE"));
			}
		}
		return hobbyMap;
	}
	
	public String convertDmsHobbyToJec(String dmsHobby) {
		if (hobbyMap == null) {
			initHobbyMap();
		}
		StringBuffer result = new StringBuffer();
		Set hobbySet = hobbyMap.keySet();
		Iterator it = hobbySet.iterator();
		while (it.hasNext()) {
			String hobbyName = (String) it.next();
			if (dmsHobby.indexOf(":" + hobbyName) != -1) {
				String hobbyCode = hobbyMap.get(hobbyName).toString();
				if (result.length() > 0) {
					result.append(",");
				}
				result.append(hobbyCode);
			}
		}
		
		return result.toString();
	}
	
}
