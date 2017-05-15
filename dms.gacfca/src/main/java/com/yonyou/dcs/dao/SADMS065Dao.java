package com.yonyou.dcs.dao;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.yonyou.dms.DTO.gacfca.OwnerVehicleDto;
import com.yonyou.dms.framework.DAO.OemBaseDAO;
import com.yonyou.dms.framework.DAO.OemDAOUtil;
import com.yonyou.dms.function.utils.common.CommonUtils;

/**
 * 大客户直销生成售后资料
 * @author luoyang
 *
 */
@Repository
public class SADMS065Dao extends OemBaseDAO {

	public LinkedList<OwnerVehicleDto> getVehicelOwnerInfo(String vinList) throws Exception {
		StringBuffer sql = getSql(vinList);
		List<Map> mapList = OemDAOUtil.findAll(sql.toString(), null);
		LinkedList<OwnerVehicleDto> list = setOwnerVehicleDtoList(mapList);
		return list;
	}
	
	LinkedList<OwnerVehicleDto> setOwnerVehicleDtoList(List<Map> mapList) throws Exception {
		LinkedList<OwnerVehicleDto> resultList = new LinkedList<OwnerVehicleDto>();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		if(mapList != null && !mapList.isEmpty()){
			for(Map map : mapList){
				
				String dealerCode = map.get("DMS_CODE") == null ? null : CommonUtils.checkNull(map.get("DMS_CODE"));
				String entityCode = map.get("DMS_CODE") == null ? null : CommonUtils.checkNull(map.get("DMS_CODE"));
				String customerName = map.get("CUSTOMERNAME") == null ? null : CommonUtils.checkNull(map.get("CUSTOMERNAME"));
				String ownerNo = map.get("OWNERNO") == null ? null : CommonUtils.checkNull(map.get("OWNERNO"));
				Integer gender = CommonUtils.checkNull(map.get("GENDER")) == "" ? null : Integer.parseInt(CommonUtils.checkNull(map.get("GENDER")));
				Date birthday = CommonUtils.checkNull(map.get("BIRTHDAY")) == "" ? null : sdf.parse(CommonUtils.checkNull(map.get("BIRTHDAY")));
				String zipCode = map.get("ZIPCODE") == null ? null : CommonUtils.checkNull(map.get("ZIPCODE"));
				Integer province = CommonUtils.checkNull(map.get("PROVINCE")) == "" ? null : Integer.parseInt(CommonUtils.checkNull(map.get("PROVINCE")));
				Integer city = CommonUtils.checkNull(map.get("CITY")) == "" ? null : Integer.parseInt(CommonUtils.checkNull(map.get("CITY")));
				Integer district = CommonUtils.checkNull(map.get("DISTRICT")) == "" ? null : Integer.parseInt(CommonUtils.checkNull(map.get("DISTRICT")));
				Integer ctCode = CommonUtils.checkNull(map.get("CTCODE")) == "" ? null : Integer.parseInt(CommonUtils.checkNull(map.get("CTCODE")));
				String certificateNo = map.get("CERTIFICATENO") == null ? null : CommonUtils.checkNull(map.get("CERTIFICATENO"));
				String hobby = map.get("HOBBY") == null ? null : CommonUtils.checkNull(map.get("HOBBY"));
				String industryFirst = map.get("INDUSTRYFIRST") == null ? null : CommonUtils.checkNull(map.get("INDUSTRYFIRST"));
				String industrySecond = map.get("INDUSTRYSECOND") == null ? null : CommonUtils.checkNull(map.get("INDUSTRYSECOND"));
				Integer educationLevel = CommonUtils.checkNull(map.get("EDUCATIONLEVEL")) == "" ? null : Integer.parseInt(CommonUtils.checkNull(map.get("EDUCATIONLEVEL")));
				Date submitTime = CommonUtils.checkNull(map.get("SUBMITTIME")) == "" ? null : sdf.parse(CommonUtils.checkNull(map.get("SUBMITTIME")));             
				Integer ctmType = CommonUtils.checkNull(map.get("CTMTYPE")) == "" ? null : Integer.parseInt(CommonUtils.checkNull(map.get("CTMTYPE")));
				String soldBy = map.get("SOLDBY") == null ? null : CommonUtils.checkNull(map.get("SOLDBY"));				
				String phone = map.get("PHONE") == null ? null : CommonUtils.checkNull(map.get("PHONE"));
				String address = map.get("ADDRESS") == null ? null : CommonUtils.checkNull(map.get("ADDRESS"));
				String email = map.get("EMAIL") == null ? null : CommonUtils.checkNull(map.get("EMAIL"));
				
				//车辆信息
				String vin = map.get("VIN") == null ? null : CommonUtils.checkNull(map.get("VIN"));
				String productCode = map.get("PRODUCTCODE") == null ? null : CommonUtils.checkNull(map.get("PRODUCTCODE"));
				String license = map.get("LICENSE") == null ? null : CommonUtils.checkNull(map.get("LICENSE"));
				String engineNo = map.get("ENGINENO") == null ? null : CommonUtils.checkNull(map.get("ENGINENO"));
				String modelYear = map.get("MODELYEAR") == null ? null : CommonUtils.checkNull(map.get("MODELYEAR"));
				String brand = map.get("BRAND") == null ? null : CommonUtils.checkNull(map.get("BRAND"));
				String series = map.get("SERIES") == null ? null : CommonUtils.checkNull(map.get("SERIES"));
				String model = map.get("MODEL") == null ? null : CommonUtils.checkNull(map.get("MODEL"));
				String color = map.get("COLOR") == null ? null : CommonUtils.checkNull(map.get("COLOR"));
				Double vehiclePrice = CommonUtils.checkNull(map.get("VEHICLEPRICE")) == "" ? null : Double.parseDouble(CommonUtils.checkNull(map.get("VEHICLEPRICE")));
				String salesAdviser = map.get("SALESADVISER") == null ? null : CommonUtils.checkNull(map.get("SALESADVISER"));
				Date salesDate = CommonUtils.checkNull(map.get("SALESDATE")) == "" ? null : sdf.parse(CommonUtils.checkNull(map.get("SALESDATE")));
				Integer vehiclePurpose = CommonUtils.checkNull(map.get("VEHICLEPURPOSE")) == "" ? null : Integer.parseInt(CommonUtils.checkNull(map.get("VEHICLEPURPOSE")));
				Date wrtBeginDate = CommonUtils.checkNull(map.get("WRTBEGINDATE")) == "" ? null : sdf.parse(CommonUtils.checkNull(map.get("WRTBEGINDATE")));
				Date wrtEndDate = CommonUtils.checkNull(map.get("WRTENDDATE")) == "" ? null : sdf.parse(CommonUtils.checkNull(map.get("WRTENDDATE")));
				Double wrtBeginMileage = CommonUtils.checkNull(map.get("WRTBEGINMILEAGE")) == "" ? null : Double.parseDouble(CommonUtils.checkNull(map.get("WRTBEGINMILEAGE")));
				Double wrtEndMileage = CommonUtils.checkNull(map.get("WRTENDMILEAGE")) == "" ? null : Double.parseDouble(CommonUtils.checkNull(map.get("WRTENDMILEAGE")));                 
				
				Date bindingDate = CommonUtils.checkNull(map.get("BIND_DATE")) == "" ? null : sdf.parse(CommonUtils.checkNull(map.get("BIND_DATE")));
				String isBinding = map.get("IS_BINDING") == null ? null : CommonUtils.checkNull(map.get("IS_BINDING"));
				
				OwnerVehicleDto dto = new OwnerVehicleDto();
				dto.setAddress(address);
				dto.setBindingDate(bindingDate);
				dto.setBirthday(birthday);
				dto.setBrand(brand);
				dto.setCertificateNo(certificateNo);
				dto.setCity(city);
				dto.setColor(color);
				dto.setCtCode(ctCode);
				dto.setCtmType(ctmType);
				dto.setCustomerName(customerName);
				dto.setDealerCode(dealerCode);
				dto.setDistrict(district);
				dto.setEducationLevel(educationLevel);
				dto.setEmail(email);
				dto.setEngineNo(engineNo);
				dto.setEntityCode(entityCode);
				dto.setGender(gender);
				dto.setHobby(hobby);
				dto.setIndustryFirst(industryFirst);
				dto.setIndustrySecond(industrySecond);
				dto.setIsBinding(isBinding);
				dto.setLicense(license);
				dto.setModel(model);
				dto.setModelYear(modelYear);
				dto.setOwnerNo(ownerNo);
				dto.setPhone(phone);
				dto.setProductCode(productCode);
				dto.setProvince(province);
				dto.setSalesAdviser(salesAdviser);
				dto.setSalesDate(salesDate);
				dto.setSeries(series);
				dto.setSoldBy(soldBy);
				dto.setSubmitTime(submitTime);
				dto.setVehiclePrice(vehiclePrice);
				dto.setVehiclePurpose(vehiclePurpose);
				dto.setVin(vin);
				dto.setWrtBeginDate(wrtBeginDate);
				dto.setWrtBeginMileage(wrtBeginMileage);
				dto.setWrtEndDate(wrtEndDate);
				dto.setWrtEndMileage(wrtEndMileage);
				dto.setZipCode(zipCode);
				resultList.add(dto);
			}
		}
		return resultList;
	}

	StringBuffer getSql(String vinList){
		StringBuffer sql = new StringBuffer();
		sql.append(" \n");
		sql.append("SELECT DISTINCT \n");
		sql.append("  tdr.DMS_CODE,	-- 送达方DMS经销商代码 \n");
		sql.append("  TBCD.CUSTOMER_NAME CUSTOMERNAME,	-- 车主名称 \n");
		sql.append("  TBCD.CUSTOMER_NO OWNERNO,	-- 车主编号 \n");
		sql.append("  CASE TBCD.LINKMAN_SEX WHEN '10031001' THEN '10061001' WHEN '10031002' THEN '10061002' WHEN '10031003' THEN '10061003' ELSE null END AS GENDER,	-- 性别 \n");
		sql.append("  null BIRTHDAY,	-- 生日 \n");
		sql.append("  null ZIPCODE,	-- 邮编 \n");
		sql.append("  null PROVINCE,	-- 省 \n");
		sql.append("  null CITY,	-- 市 \n");
		sql.append("  null DISTRICT,	-- 县 \n");
		sql.append("  CASE TBCD.ID_TYPE WHEN '20301001' THEN '12391001' WHEN '20301004' THEN '12391002' \n");
		sql.append("  WHEN '20301002' THEN '12391003' WHEN '20301005' THEN '12391004' WHEN '20301003' THEN '12391005' \n");
		sql.append("  WHEN '20301006' THEN '12391006' WHEN '20301007' THEN '12391007' ELSE null END AS CTCODE,	-- 证件类型 \n");
		sql.append("  TBCD.ID_NO CERTIFICATENO,	-- 证件号码 \n");
		sql.append("  null HOBBY,	-- 爱好 \n");
		sql.append("  null INDUSTRYFIRST,	-- 所在行业大类 \n");
		sql.append("  null INDUSTRYSECOND,	-- 所在行业二类 \n");
		sql.append("  null EDUCATIONLEVEL, -- 学历 \n");
		sql.append("  null SUBMITTIME,	-- 提交日期 \n");
		sql.append("  CASE TBCD.CUSTOMER_TYPE WHEN '20291002' THEN '11901001' WHEN '20291001' THEN '11901002' ELSE null END AS CTMTYPE,	-- 客户类型 \n");
		sql.append("  null SOLDBY,	-- 销售顾问 \n");
		sql.append("  TBCD.LINKMAN_TEL PHONE,	-- 手机/电话==不存储 \n");
		sql.append("  TBCD.LINKMAN_ADDR ADDRESS,	-- 地址==不存储 \n");
		sql.append("  null EMAIL,	-- 邮编==不存储 \n");
		sql.append("  TV.VIN VIN,	-- 车架号 \n");
		sql.append("  RTRIM(CHAR(VM.MATERIAL_CODE || VM.COLOR_CODE || VM.TRIM_CODE || VM.MODEL_YEAR)) AS PRODUCTCODE,	-- 产品代码 \n");
		sql.append("  TV.LICENSE_NO LICENSE,	-- 车牌号 \n");
		sql.append("  TV.ENGINE_NO ENGINENO,	-- 发动机号 \n");
		sql.append("  VM.MODEL_YEAR MODELYEAR,	-- 车型年 \n");
		sql.append("  VM.BRAND_CODE BRAND,	-- 品牌 \n");
		sql.append("  VM.SERIES_CODE SERIES,	-- 车系 \n");
		sql.append("  VM.MODEL_CODE MODEL,	-- 车型 \n");
		sql.append("  VM.COLOR_CODE COLOR,	-- 颜色 \n");
		sql.append("  null VEHICLEPRICE,	-- 车辆价格 \n");
		sql.append("  null SALESADVISER,	-- 销售顾问//原来是有soldBy \n");
		sql.append("  TVO.INVOICE_DATE SALESDATE,	-- 销售日期  \n");
		sql.append("  CASE TV.VEHICLE_USAGE WHEN '20841009' THEN '11931001' WHEN '20841001' THEN '11931002' WHEN '20841006' THEN '11931004' \n");
		sql.append("  WHEN '20841005' THEN '11931003' WHEN '20841004' THEN '11931005' ELSE '' END AS VEHICLEPURPOSE,	-- 车辆用途 \n");
		sql.append("  TV.PRODUCT_DATE WRTBEGINDATE,	-- 保修起始日期 \n");
		sql.append("  null WRTENDDATE,	-- 保修结束日期 \n");
		sql.append("  TV.MILEAGE WRTBEGINMILEAGE,	-- 保修起始里程 \n");
		sql.append("  null WRTENDMILEAGE,	-- 保修结束里程 \n");
		sql.append("  null IS_BINDING,null BIND_DATE    -- 微信绑定,绑定时间	\n");
		sql.append("FROM TT_VS_ORDER TVO LEFT JOIN TM_VEHICLE_DEC TV ON TVO.VIN = TV.VIN 	-- 订单表表 \n");
		sql.append("	INNER JOIN TT_BIG_CUSTOMER_DIRECT_ORDER TBCDO ON TVO.ORDER_ID = TBCDO.ORDER_ID	-- 直销客户关系表	\n");
		sql.append("	INNER JOIN TT_BIG_CUSTOMER_DIRECT TBCD ON TBCDO.DIRECT_ID = TBCD.DIRECT_ID	-- 直销客户表	\n");
		sql.append("	INNER JOIN TM_DEALER TD	ON TD.DEALER_ID = TVO.RECEIVES_DEALER_ID	-- 经销商表（送达方ID）	\n");
		sql.append("	INNER JOIN TI_DEALER_RELATION tdr on tdr.DCS_CODE=TD.DEALER_CODE	\n");
		sql.append("	INNER JOIN ("+getVwMaterialSql()+") VM ON TV.MATERIAL_ID = VM.MATERIAL_ID	-- 视图	\n");
		sql.append("	WHERE 1=1 \n");
		sql.append("		AND TV.VIN in ('"+vinList+"') \n");
		System.out.println("SQL:"+sql);
		return sql;
	}
	
}
