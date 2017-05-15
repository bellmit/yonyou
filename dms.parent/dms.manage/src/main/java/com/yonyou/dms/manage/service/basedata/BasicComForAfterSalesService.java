/**
 * 
 */
package com.yonyou.dms.manage.service.basedata;

import java.util.List;
import java.util.Map;

/**
 * @author yangjie
 *
 */
@SuppressWarnings("rawtypes")
public interface BasicComForAfterSalesService {

	/**
	 * 查询所有基础参数
	 * @return
	 */
	Map<String,String> findAllRepair();

	/**
	 * 下拉框查询仓库
	 * @return
	 */
	List<Map> findStorageCode();

	/**
	 * 下拉框查询发票类型 
	 * @return
	 */
	List<Map> findInvoiceCode();

	/**
	 * 下拉框查询付款方式
	 * @return
	 */
	List<Map> findPaymentCode();

	/**
	 * 下拉框查询收费形式
	 * @return
	 */
	List<Map> findChargeCode();

	/**
	 * 查询配件基础参数
	 * @return
	 */
	Map<String, String> findAllPart();

	/**
	 * 查询报表基础参数
	 * @return
	 */
	Map<String, String> findAllReport();

	/**
	 * 查询积分基础参数
	 * @return
	 */
	Map<String, String> findAllIntegral();

	/**
	 * 修改方法
	 * @param param
	 */
	void updateBasicParam(Map<String, String> param);

}
