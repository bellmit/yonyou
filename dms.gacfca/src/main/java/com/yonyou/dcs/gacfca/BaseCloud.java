package com.yonyou.dcs.gacfca;

import java.util.List;
import java.util.Map;
@SuppressWarnings("rawtypes")
public interface BaseCloud {
	/**
	 * 
	 * @Title: getAllDmsCode
	 * @Description: (查询下端所有的entityCode)
	 */
	public List<String> getAllDmsCode(Integer sendType) throws Exception;
	/**
	 * 
	 * @Title: getAllDcsCode
	 * @Description: (查询上端所有的DealerCode)
	 */
	public List<String> getAllDcsCode(Integer sendType) throws Exception;
	/**
	 * 根据上端dealerId查询下端entityCode（售后）
	 * 先根据dealerId查询companyCode,再根据companyCode对应entityCode
	 * @param dealerId 上端经销商ID
	 * @return entityCode 下端经销商公司Code
	 */
	public Map<String, Object> getDmsDealerCodeForDealerId(Long dealerId) throws Exception;
	/**
	 * 根据上端dealerId查询下端entityCode(销售)
	 * 先根据dealerId查询companyCode,再根据companyCode对应entityCode
	 * @param dealerId 上端经销商ID
	 * @return entityCode 下端经销商公司Code
	 */
	public Map<String, Object> getAllDmsDealerCodeForDealerId(Long dealerId)  throws Exception;
	
	/**
	 * 根据上端dealerId查询上端dealerCode
	 * @param dealerId  上端dealerId
	 * @return dealerCode   上端经销商代码
	 * @throws Exception 
	 */
	public String getDealerCode(Long dealerId) throws Exception;
	
	/**
	 * @Description: TODO(根据下端entityCode查询上端dealerCode 区分销售和售后,此方法为售后)
	 * @param @param
	 *            dealerCode 下端entityCode
	 * @param @return
	 *            上端dealerCode
	 */
	public Map getSeDcsDealerCode(String dealerCode) throws Exception;
	
	/**
	 * @Description: TODO(根据下端entityCode查询上端dealerCode 区分销售和售后,此方法为销售)
	 * @param @param
	 *            dealerCode 下端entityCode
	 * @param @return
	 *            上端dealerCode
	 */
	public Map getSaDcsDealerCode(String dealerCode) throws Exception ;
	
	/**
	 * 根据上端dealerCode查询下端entityCode
	 * 先根据dealerCode查询companyCode,再根据companyCode对应entityCode
	 * 
	 * @param dealerCode
	 *            上端经销商Code
	 * @return DMS_CODE 下端经销商公司Code
	 */
	public Map getDmsDealerCode(String dealerCode) throws Exception;
	
}
