/**
 * 
 */
package com.yonyou.dms.customer.service.customerManage;

import java.util.List;

import java.util.Map;

import com.yonyou.dms.common.domains.DTO.basedata.OwnerCusDTO;
import com.yonyou.dms.common.domains.DTO.basedata.PotentialCusDTO;
import com.yonyou.dms.function.exception.ServiceBizException;

/**
 * @author Administrator
 *
 */
@SuppressWarnings({ "rawtypes", "unchecked" })
public interface OwnerCusService {
	  public String addOwnerCusInfo(OwnerCusDTO ownerCusDto, String ownerNo) throws ServiceBizException;
	  
	  public List<Map> queryOwnerCusByEmployee(String employeeNo,String vin,String dealerCode) throws ServiceBizException;

	  public List<Map> queryOwnerCusByFamily(String employeeNo,String dealerCode) throws ServiceBizException;
	  
	  public List<Map> queryOwnerCusHistroy(String employeeNo,String dealerCode) throws ServiceBizException;
	  
	  public List<Map> queryOwnerCusIntent(String employeeNo,String dealerCode,String intentId) throws ServiceBizException;
	  
	  public List<Map> queryOwnerCusVehicle(String dealerCode) throws ServiceBizException;

	  public List<Map> queryOwnerCusByLinkman(String employeeNo,String dealerCode) throws ServiceBizException;

	  public List<Map> queryOwnerCusByInsurance(String vin,String dealerCode) throws ServiceBizException;
	
	  public List<Map> queryOwnerCusByTreat(String employeeNo,String vin,String dealerCode) throws ServiceBizException;
	
	  public List<Map> queryOwnerName(String ownerNo,String vin,String dealerCode) throws ServiceBizException;

	  public List<Map> queryOwnerVehicle(String employeeNo,String vin,String dealerCode) throws ServiceBizException;
	  
	  public List<Map> queryOwnerCusforExport(Map<String, String> queryParam) throws ServiceBizException;

	  public void modifyOwnerCusInfo(String enployeeNo,String vin,String ownerNo,OwnerCusDTO ownerCusDto) throws ServiceBizException;

	  public void modifySoldBy(OwnerCusDTO ownerCusDto) throws ServiceBizException;
}
