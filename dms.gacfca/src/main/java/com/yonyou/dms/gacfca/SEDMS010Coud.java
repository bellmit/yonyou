package com.yonyou.dms.gacfca;

import java.util.List;

import com.yonyou.dms.DTO.gacfca.RepairOrderSchemeDTO;

/**
 * @description 上报三包维修方案
 * @author Administrator
 *
 */
public interface SEDMS010Coud {
	public List<RepairOrderSchemeDTO> getSEDMS010(String dealerCode,Long userId,String roNo,Integer sSchemeStatus,Double inMileage,
			String serviceAdvisorAss,String vin,String ownerName,String model,String roCreateDate, String roTroubleDesc,String reMark1, String[] labourCode,
			String[] labourName,String[] stdLabourHour,String[] lbRemark,String[] partCode,String[] ptMum,String[] warmTimes,
			String[] warmItemName,String[] partNo,String[] partName,String[] partQuantity);
}
