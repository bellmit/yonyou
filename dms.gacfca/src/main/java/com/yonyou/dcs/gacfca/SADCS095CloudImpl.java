package com.yonyou.dcs.gacfca;

import java.util.Date;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.yonyou.dcs.dao.RetailInformationChangeDao;
import com.yonyou.dcs.util.DEConstant;
import com.yonyou.dms.DTO.gacfca.SADMS095Dto;
import com.yonyou.dms.common.domains.PO.basedata.TtVsCustomerPO;
import com.yonyou.dms.common.domains.PO.basedata.TtVsSalesReportPO;
import com.yonyou.dms.common.domains.PO.basedata.TtVsSecondCarPO;
import com.yonyou.dms.function.common.OemDictCodeConstants;
import com.yonyou.dms.function.exception.ServiceBizException;
import com.yonyou.dms.function.utils.common.StringUtils;

/**
 * 
 * Title:SADCS095CloudImpl
 * Description: 经销商零售信息变更
 * @author DC
 * @date 2017年4月10日 下午5:08:00
 * result msg 1：成功 0：失败
 */
@Service
public class SADCS095CloudImpl extends BaseCloudImpl implements SADCS095Cloud {
	
	private static final Logger logger = LoggerFactory.getLogger(SADCS095CloudImpl.class);
	
	@Autowired
	RetailInformationChangeDao retailInfo;
	
	@Override
	public String handleExecutor(List<SADMS095Dto> dtoList) throws Exception {
		String msg = "1";
		logger.info("====经销商零售信息变更上报接收开始====");
		for (SADMS095Dto entry : dtoList) {
			try {
				updateRetailInfoChangeDate(entry);
			} catch (Exception e) {
				logger.error("经销商零售信息变更上报接收失败", e);
				msg = "0";
				throw new ServiceBizException(e);
			}
			logger.info("====经销商零售信息变更上报接收结束====");
		}
		return msg;
	}

	private void updateRetailInfoChangeDate(SADMS095Dto vo) throws Exception {
		if(null == vo.getDealerCode() || "".equals(vo.getDealerCode())){
			throw new Exception("DE消息中DealerCode为空！");
		}
		if(vo.getIsModify() != 1){
			throw new Exception("上报出错，不是变更数据,isModify:"+vo.getIsModify());
		} 
		Map<String, Object> vehicleIdMap = retailInfo.getVehicleIdByVin(vo.getVin());
		logger.info("====实销车辆VIN===="+vo.getVin());
		String vehicleId = null;
		if(null != vehicleIdMap && vehicleIdMap.size() > 0){
			/*String vin = null != vehicleIdMap.get("VIN") ? vehicleIdMap.get("VIN").toString() : "";
			if(Utility.testIsNull(vin)){
				throw new RpcException("该车未实销，不能更改实销相关信息！vin="+vo.getVin());
			}*/
			vehicleId = null != vehicleIdMap.get("VEHICLE_ID") ? vehicleIdMap.get("VEHICLE_ID").toString() : "";
			logger.info("====实销车辆ID===="+vehicleId);
			
			logger.info("====将变更前的实销车辆信息保存到历史表====");
			retailInfo.insertSalesReport(vehicleId);//将变更前的实销车辆信息保存到历史表
			
			//更新实销信息
			TtVsSalesReportPO desSales = TtVsSalesReportPO.findFirst(" VEHICLE_ID = ? ", vehicleId);
			String tempMile = null;
			if(null != vo.getSalesMileage()){
				double sm = vo.getSalesMileage();
				tempMile = sm + "";
			}
			logger.info("====实销车辆tempMile：===="+tempMile);
			if (null != vo.getSalesMileage() &&tempMile.trim().length() > 0) {
				desSales.setFloat("MILES", Float.parseFloat(tempMile));//车辆交付公里数
			}
			desSales.setDate("CONSIGNATION_DATE", vo.getConfirmedDate());//销售日期
			desSales.setString("CONTRACT_NO", vo.getSoNo());//订单编号
			desSales.setDate("INVOICE_DATE", vo.getInvoiceDate());//开票日期
			desSales.setString("INVOICE_NO", vo.getInvoiceNo());//发票编号
			desSales.setString("INVOICE_TYPE_CODE", vo.getInvoiceTypeCode());//发票类型
			desSales.setDouble("INVOICE_AMOUNT", vo.getInvoiceAmount());//发票金额
			desSales.setString("INVOICE_CUSTOMER", vo.getInvoiceCustomer());//费用类型
			desSales.setString("INVOICE_WRITER", vo.getInvoiceWriter());//开票客户
			desSales.setString("TRANSACTOR", vo.getTransactor());//开票人员
			desSales.setInteger("INVOICE_CHARGE_TYPE", vo.getInvoiceChargeType());//经办人
			desSales.setString("MEMO", vo.getRemark());//备注
			desSales.setString("IS_MODIFY", vo.getIsModify());//是否更改
			desSales.setLong("UPDATE_BY", DEConstant.DE_CREATE_BY);
			desSales.setDate("UPDATE_DATE", new Date());
			desSales.setInteger("IS_SCAN", 0);
			desSales.saveIt();
		}else{
			throw new Exception("该车未实销，不能更改实销相关信息！vin="+vo.getVin());
		}

		Map<String, Object> customExistMap = null;
		String ctmId = null;
		if(null != vehicleId){
			customExistMap  = retailInfo.getCustomInfo(vehicleId);
		}
		if (customExistMap != null){
			ctmId = customExistMap.get("CTM_ID").toString();

			logger.info("====将变更前的客户信息保存到历史表中====");
			retailInfo.insertCustomInfo(ctmId);//将变更前的客户信息保存到历史表中
			TtVsCustomerPO upCustomPO = TtVsCustomerPO.findById(Long.parseLong(ctmId));//主键
			upCustomPO.setString("CTM_NO", vo.getCustomerNo());//客户编号
			upCustomPO.setString("CTM_NAME", vo.getCustomerName());//人名
			upCustomPO.setString("MAIN_PHONE", vo.getContactorMobile());//电话
			Integer ctmType = 0;
			if (vo.getCustomerType() != null && vo.getCustomerType() == 10181001) {
				ctmType = 20291001;// 个人
			} else if (vo.getCustomerType() != null && vo.getCustomerType() == 10181002) {
				ctmType = 20291002;// 公司
			}
			upCustomPO.setInteger("CTM_TYPE", ctmType);//客户类型
			if (vo.getCtCode() != null) {
				Integer tempCardType = getLocalStatusByDealerStatus(vo.getCtCode());
				upCustomPO.setInteger("CARD_TYPE", tempCardType);//证件类型
			}
			upCustomPO.setString("CARD_NUM", vo.getCertificateNo());//证件编号
			if (vo.getGender() != null) {
				Integer tempSex = getLocalStatusByDealerStatus(vo.getGender());
				upCustomPO.setInteger("SEX", tempSex);//性别
			}
			upCustomPO.setString("ADDRESS", vo.getAddress());//地址
			upCustomPO.setString("POST_CODE", vo.getZipCode());//邮编

			String tempProvince = vo.getProvince() + "";
			String tempCity = vo.getCity() + "";
			String tempTown = vo.getDistrict() + "";
			if (null != vo.getProvince() &&tempProvince.trim().length() > 0) {
				upCustomPO.setLong("PROVINCE", Long.parseLong(tempProvince));//省
			}
			if (null != vo.getCity() && tempCity.trim().length() > 0) {
				upCustomPO.setLong("CITY", Long.parseLong(tempCity));//市
			}
			if (null != vo.getDistrict() && tempTown.trim().length() > 0) {
				upCustomPO.setLong("TOWN", Long.parseLong(tempTown));//区
			}
			upCustomPO.setString("EMAIL", vo.getEmail());//邮件
			upCustomPO.setDate("BIRTHDAY", vo.getBirthday());//生日
			if (vo.getOwnerMarriage() != null) {
				Integer tempMarrInfo = getLocalStatusByDealerStatus(vo.getOwnerMarriage());
				upCustomPO.setInteger("IS_MARRIED", tempMarrInfo);//是否结婚
			}
			if (vo.getEducationLevel() != null) {
				Integer tempEducation = getLocalStatusByDealerStatus(vo.getEducationLevel());
				upCustomPO.setInteger("EDUCATION", tempEducation);//教育程度
			}
			if (vo.getFamilyIncome() != null) {
				upCustomPO.setInteger("INCOME", vo.getFamilyIncome());//家庭月收
			}
			upCustomPO.setString("BEST_CONTACT_TIME", vo.getBestContactTime());//最佳联系时间
			upCustomPO.setString("HOBBY", vo.getHobby());//爱好
			upCustomPO.setString("INDUSTRY_FIRST", vo.getIndustryFirst());//所在行业大类
			upCustomPO.setString("INDUSTRY_SECOND", vo.getIndustrySecond());//所在行业二类
			upCustomPO.setString("VOCATION_TYPE", vo.getVocationType());//职位
			upCustomPO.setString("POSITION_NAME", vo.getPositionName());//职务
			upCustomPO.setString("SALES_ADVISER", vo.getSoldBy());//销售顾问
			upCustomPO.setLong("UPDATE_BY", DEConstant.DE_UPDATE_BY);
			upCustomPO.setDate("UPDATE_DATE", new Date());
			upCustomPO.saveIt();
		}
		
		//处理二手车信息
		logger.info("====二手车更新正式表表开始====");
		if(vo.getPermutedVin() != null){
			Map<String, Object> vsSecondMap = retailInfo.getSecondCarById(vo.getPermutedVin());
			if(null != vsSecondMap && vsSecondMap.size() > 0){
				String lmId = null != vsSecondMap.get("LM_ID") ? vsSecondMap.get("LM_ID").toString() : "";
				if(!StringUtils.isNullOrEmpty(lmId)){
					logger.info("====二手车写入历史表开始====");
					
					retailInfo.copySecondCarToHistory(vo.getPermutedVin());
				
					TtVsSecondCarPO updateValue = TtVsSecondCarPO.findFirst(" VIN = ? ", vo.getPermutedVin());
					updateValue.setDouble("ASSESSED_PRICE", vo.getAssessedPrice());//评估价格
					updateValue.setString("LICENSE", vo.getAssessedLicense());//车牌号
					updateValue.setDouble("OLD_CAR_PURCHASE", vo.getOldCarPurchase());//收购价格
					updateValue.setString("OLD_BRAND_CODE", vo.getOldBrandCode());//品牌
					updateValue.setString("OLD_SERIES_CODE", vo.getOldSeriesCode());//车系
					updateValue.setString("OLD_MODEL_CODE", vo.getOldModelCode());//车型
					updateValue.setInteger("IS_PERMUTED", vo.getIsPermuted());//是否置换
					updateValue.setString("REMARK", vo.getPermutedDesc());//二手车描述
					updateValue.setString("FILE_OLD_A", vo.getFileOldA());//收购协议ID
					updateValue.setString("FILE_URLOLD_A", vo.getFileUrloldA());//收购协议URL
					updateValue.setLong("UPDATE_BY", DEConstant.DE_UPDATE_BY);
					updateValue.setDate("UPDATE_DATE", new Date());
					updateValue.saveIt();
				}
			}else{
				TtVsSecondCarPO secondCarPO =new TtVsSecondCarPO();
				//6月份 bug修改
				if(ctmId != null && ctmId.length() > 0){
					secondCarPO.setLong("CTM_ID", Long.parseLong(ctmId));
				}
				secondCarPO.setString("VIN", vo.getPermutedVin());
				secondCarPO.setDouble("ASSESSED_PRICE", vo.getAssessedPrice());//评估价格
				secondCarPO.setString("LICENSE", vo.getAssessedLicense());//车牌号
				secondCarPO.setDouble("OLD_CAR_PURCHASE", vo.getOldCarPurchase());//收购价格
				secondCarPO.setString("OLD_BRAND_CODE", vo.getOldBrandCode());//品牌
				secondCarPO.setString("OLD_SERIES_CODE", vo.getOldSeriesCode());//车系
				secondCarPO.setString("OLD_MODEL_CODE", vo.getOldModelCode());//车型
				secondCarPO.setInteger("ID_PERMUTED", vo.getIsPermuted());//是否置换
				secondCarPO.setString("REMARK", vo.getPermutedDesc());//二手车描述
				secondCarPO.setString("FOLE_OLD_A", vo.getFileOldA());//收购协议ID
				secondCarPO.setString("FILE_URLOLD_A", vo.getFileUrloldA());//收购协议URL
				secondCarPO.setLong("CREATE_BY", DEConstant.DE_CREATE_BY);
				secondCarPO.setDate("CREATE_DATE", new Date());
				secondCarPO.insert();
			}
		}
		
	}
	
	public Integer getLocalStatusByDealerStatus(Integer dealerStatus) {
		// 车辆用途 存到车辆性质字段
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
		 * OemDictCodeConstants.VEHICLE_NATURE_TYPE_01; } if (dealerStatus == 11931002) {
		 * return OemDictCodeConstants.VEHICLE_NATURE_TYPE_02; } if (dealerStatus ==
		 * 11931003) { return OemDictCodeConstants.VEHICLE_NATURE_TYPE_03; } if
		 * (dealerStatus == 11931004) { return OemDictCodeConstants.VEHICLE_NATURE_TYPE_04;
		 * } if (dealerStatus == 11931005) { return
		 * OemDictCodeConstants.VEHICLE_NATURE_TYPE_05; }
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
		
		//费用类型
		if(dealerStatus == 13181001){
			return OemDictCodeConstants.INVOICE_CHARGE_TYPE_01;//购车费用
		}
		if(dealerStatus == 13181002){
			return OemDictCodeConstants.INVOICE_CHARGE_TYPE_02;//代办服务费
		}
		if(dealerStatus == 13181003){
			return OemDictCodeConstants.INVOICE_CHARGE_TYPE_03;//精品装潢费
		}
		if(dealerStatus == 13181004){
			return OemDictCodeConstants.INVOICE_CHARGE_TYPE_04;//保险费用
		}
		if(dealerStatus == 13181005){
			return OemDictCodeConstants.INVOICE_CHARGE_TYPE_05;//购税费用
		}
		if(dealerStatus == 13181006){
			return OemDictCodeConstants.INVOICE_CHARGE_TYPE_06;//牌照费用
		}
		if(dealerStatus == 13181007){
			return OemDictCodeConstants.INVOICE_CHARGE_TYPE_07;//信贷费用
		}
		return 0;
	}

}
