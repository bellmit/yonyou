package com.yonyou.dms.vehicle.service.servicesTransacting;

import java.util.List;
import java.util.Map;

import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.function.exception.ServiceBizException;
import com.yonyou.dms.vehicle.domains.DTO.servicesTransacting.ServicesTransactingDTO;

/**
 * 服务项目办理Service
 * @author Benzc
 * @date 2017年4月13日
 */
public interface ServicesTransactingService {
	
	//服务项目办理分页查询
	public PageInfoDto QueryServicesTransacting(Map<String, String> queryParam);
	
	//险种查询
	public PageInfoDto QueryInsurance(Map<String, String> queryParam);
	
	//险种下拉框
	@SuppressWarnings("rawtypes")
	public List<Map> QuerySelectInsurance() throws ServiceBizException;
	
	//险种下拉框
	@SuppressWarnings("rawtypes")
	public List<Map> QuerySelectInsuratione() throws ServiceBizException;
	
	//根据订单编号查询服务项目
	public PageInfoDto QueryService(String id) throws ServiceBizException;
	
	//根据VIN查询保险办理结果
	public PageInfoDto QueryInsurance(String vin) throws ServiceBizException;
	
	//根据VIN查询税费办理结果
	public PageInfoDto QueryTax(String vin) throws ServiceBizException;
	
	//根据VIN查询牌照办理结果
	public PageInfoDto QueryLicense(String vin) throws ServiceBizException;
	
	//根据VIN查询贷款办理结果
	@SuppressWarnings("rawtypes")
	public Map QueryLoan(String vin) throws ServiceBizException;
    
	//新增
	public void addServiceTransacting(ServicesTransactingDTO servicesTransactingDTO, String soNo,String vin) throws ServiceBizException;

}
