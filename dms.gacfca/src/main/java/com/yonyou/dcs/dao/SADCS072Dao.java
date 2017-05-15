package com.yonyou.dcs.dao;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.yonyou.dms.DTO.gacfca.VehicleCustomerDTO;
import com.yonyou.dms.common.Util.CheckUtil;
import com.yonyou.dms.framework.DAO.OemBaseDAO;
import com.yonyou.dms.framework.DAO.OemDAOUtil;
import com.yonyou.dms.function.utils.common.CommonUtils;

@Repository
public class SADCS072Dao extends OemBaseDAO {
	/**
	 * 查询所有存有车主信息的经销商
	 * @param ctmId
	 * @param dealerCode
	 * @return
	 */
	public List<Map> getSendDealer(String vin,String dmsCode){
		List<Map> list = null;
		StringBuffer sql = new StringBuffer("\n");
		sql.append("SELECT T5.DMS_CODE FROM ( \n");
		sql.append("    SELECT T3.DEALER_CODE \n");
		sql.append("      FROM TT_VS_SALES_REPORT T1 \n");
		sql.append("      INNER JOIN TM_VEHICLE_DEC T2 ON T1.VEHICLE_ID = T2.VEHICLE_ID \n");
		sql.append("      LEFT JOIN TM_DEALER T3 ON T2.DEALER_ID = T3.DEALER_ID \n");
		sql.append("      WHERE T2.VIN = '"+vin+"' \n");
		sql.append("    UNION \n");
		sql.append("    SELECT DISTINCT CASE WHEN RIGHT(DEALER_CODE,LENGTH(DEALER_CODE)-(LENGTH(DEALER_CODE)-1))='A' THEN REPLACE(DEALER_CODE,'A','') ELSE DEALER_CODE END  \n");
		sql.append("      FROM TT_WR_REPAIR \n");
		sql.append("      WHERE VIN =  '"+vin+"' \n");
		sql.append("  ) T4 \n");
		sql.append("  INNER JOIN TI_DEALER_RELATION T5 ON T5.DCS_CODE = T4.DEALER_CODE \n");
		if (!CheckUtil.checkNull(dmsCode)) {
			sql.append("  WHERE T5.DMS_CODE != '"+dmsCode+"' \n");
		}
		list = OemDAOUtil.findAll(sql.toString(), null);
		return list;
	}
	/**
	 * 查询车主资料
	 * @param ctmId
	 * @return
	 */
	public LinkedList<VehicleCustomerDTO> getDataList(String vin){
		StringBuffer sql = new StringBuffer("\n");
		sql.append("SELECT T5.DMS_CODE, \n");//DMS经销商
		sql.append("       T2.CTM_TYPE, \n");//客户类型
		sql.append("       T2.CTM_NAME, \n");//客户名称
		sql.append("       T2.MAIN_PHONE, \n");//手机号码
		sql.append("       T2.OTHER_PHONE, \n");//电话号码
		sql.append("       CASE \n");
		sql.append("          WHEN T2.CARD_TYPE = 20301001 THEN 12391001 \n");//如果证件类型为DCS值，则转换为DMS值，再下发给DMS（以下同）
		sql.append("          WHEN T2.CARD_TYPE = 20301002 THEN 12391003 \n");
		sql.append("          WHEN T2.CARD_TYPE = 20301003 THEN 12391005 \n");
		sql.append("          WHEN T2.CARD_TYPE = 20301004 THEN 12391002 \n");
		sql.append("          WHEN T2.CARD_TYPE = 20301005 THEN 12391004 \n");
		sql.append("          WHEN T2.CARD_TYPE = 20301006 THEN 12391006 \n");
		sql.append("          WHEN T2.CARD_TYPE = 20301007 THEN 12391007 \n");
		sql.append("       END AS CARD_TYPE, \n");//证件类型
		sql.append("       T2.CARD_NUM, \n");//证件号码
		sql.append("       CASE \n");
		sql.append("          WHEN T2.SEX = 10031001 THEN 10061001 \n");
		sql.append("          WHEN T2.SEX = 10031002 THEN 10061002 \n");
		sql.append("          WHEN T2.SEX = 10031003 THEN 10061003 \n");
		sql.append("       END AS SEX,\n"); //性别
		sql.append("       CASE \n");
		sql.append("          WHEN T2.INCOME = 20311001 THEN 11181001 \n");
		sql.append("          WHEN T2.INCOME = 20311002 THEN 11181002 \n");
		sql.append("          WHEN T2.INCOME = 20311003 THEN 11181003 \n");
		sql.append("          WHEN T2.INCOME = 20311004 THEN 11181004 \n");
		sql.append("          WHEN T2.INCOME = 20311005 THEN 11181005 \n");
		sql.append("          WHEN T2.INCOME = 20311006 THEN 11181006 \n");
		sql.append("       END AS INCOME, \n");//家庭月收入
		sql.append("       T2.BIRTHDAY, \n");//出生年月
		sql.append("       CASE \n");
		sql.append("          WHEN T2.IS_MARRIED = 20331001 THEN 11191001 \n");
		sql.append("          WHEN T2.IS_MARRIED = 20331002 THEN 11191002 \n");
		sql.append("       END AS IS_MARRIED, \n");//婚姻状态
		sql.append("       T2.PROVINCE, \n");//省
		sql.append("       T2.CITY, \n");//市
		sql.append("       T2.TOWN,\n"); //区
		sql.append("       T2.ADDRESS, \n");//地址
		sql.append("       T6.INDUSTRY_CODE INDUSTRY_FIRST, \n");//行业大类
		sql.append("       T7.INDUSTRY_CODE INDUSTRY_SECOND, \n");//行业小类
		sql.append("       T2.POST_CODE, \n");//邮编
		sql.append("       T2.EMAIL, \n");//电子邮件
		sql.append("       T3.VIN, \n");
		sql.append("       T2.MAINTAIN_DATE \n");//更新时间
		sql.append("    FROM TT_VS_SALES_REPORT T1 \n");
		sql.append("    INNER JOIN TT_VS_CUSTOMER T2 ON T1.CTM_ID = T2.CTM_ID \n");
		sql.append("    INNER JOIN TM_VEHICLE_DEC T3 ON T1.VEHICLE_ID = T3.VEHICLE_ID \n");
		sql.append("    LEFT JOIN TM_DEALER T4 ON T3.DEALER_ID = T4.DEALER_ID \n");
		sql.append("    LEFT JOIN TI_DEALER_RELATION T5 ON T4.DEALER_CODE = T5.DCS_CODE \n");
		sql.append("    LEFT JOIN TM_INDUSTRY_DCS T6 ON T2.INDUSTRY_FIRST = T6.INDUSTRY_NAME \n");
		sql.append("    LEFT JOIN TM_INDUSTRY_DCS T7 ON T2.INDUSTRY_SECOND = T7.INDUSTRY_NAME \n");
		sql.append("    WHERE T3.VIN = '"+vin+"'\n");
		
		List<Map> listMap=OemDAOUtil.findAll(sql.toString(), null);
		LinkedList<VehicleCustomerDTO> dtoList=null;
		VehicleCustomerDTO dto=null;
		if(null!=listMap&&listMap.size()>0){
			dtoList=new LinkedList<VehicleCustomerDTO>();
			for(int i=0;i<listMap.size();i++){
				dto=new VehicleCustomerDTO();
				dto.setDealerCode(CommonUtils.checkNull(listMap.get(i).get("DMS_CODE")));
				dto.setOwnerProperty(Integer.parseInt(CommonUtils.checkNull(listMap.get(i).get("CTM_TYPE"))));
				dto.setOwnerName(CommonUtils.checkNull(listMap.get(i).get("CTM_NAME")));
				dto.setContactorPhone(CommonUtils.checkNull(listMap.get(i).get("OTHER_PHONE")));//电话
				dto.setContactorMobile(CommonUtils.checkNull(listMap.get(i).get("MAIN_PHONE")));//手机
				dto.setCtCode(Integer.parseInt(CommonUtils.checkNull(listMap.get(i).get("CARD_TYPE"))));
				dto.setCertificateNo(CommonUtils.checkNull(listMap.get(i).get("CARD_NUM")));
				dto.setGender(Integer.parseInt(CommonUtils.checkNull(listMap.get(i).get("SEX"))));
				dto.setFamilyIncome(Integer.parseInt(CommonUtils.checkNull(listMap.get(i).get("INCOME"))));
				dto.setBirthday(CommonUtils.parseDate(CommonUtils.checkNull(listMap.get(i).get(("BIRTHDAY")))));
				dto.setOwnerMarriage(Integer.parseInt(CommonUtils.checkNull(listMap.get(i).get("IS_MARRIED"))));
				dto.setProvince(Integer.parseInt(CommonUtils.checkNull(listMap.get(i).get("PROVINCE"))));
				dto.setCity(Integer.parseInt(CommonUtils.checkNull(listMap.get(i).get("CITY"))));
				dto.setDistrict(Integer.parseInt(CommonUtils.checkNull(listMap.get(i).get("TOWN"))));
				dto.setAddress(CommonUtils.checkNull(listMap.get(i).get("ADDRESS")));
				dto.setIndustryFirst(Integer.parseInt(CommonUtils.checkNull(listMap.get(i).get("INDUSTRY_FIRST"))));
				dto.setIndustrySecond(Integer.parseInt(CommonUtils.checkNull(listMap.get(i).get("INDUSTRY_SECOND"))));
				dto.setZipCode(CommonUtils.checkNull(listMap.get(i).get("POST_CODE")));
				dto.setEmail(CommonUtils.checkNull(listMap.get(i).get("EMAIL")));
				dto.setVin(CommonUtils.checkNull(listMap.get(i).get("VIN")));
				String upDate=CommonUtils.checkNull(listMap.get(i).get("MAINTAIN_DATE"));
				dto.setUpDate(CommonUtils.parseDate(upDate));
			}
		}
		
		return dtoList;
	}
}
