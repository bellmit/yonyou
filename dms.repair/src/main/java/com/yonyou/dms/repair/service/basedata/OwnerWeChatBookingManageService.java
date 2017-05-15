/**
 * 
 */
package com.yonyou.dms.repair.service.basedata;

import java.util.List;
import java.util.Map;

import com.yonyou.dms.framework.DAO.PageInfoDto;

/**
 * @author yangjie
 *
 */
@SuppressWarnings("rawtypes")
public interface OwnerWeChatBookingManageService {

	/**
	 * 查询主页面信息方法
	 * @param queryParam
	 * @return
	 */
	PageInfoDto findAll(Map<String, String> queryParam);

	/**
	 * 查询微信卡券信息方法
	 * @return
	 */
	PageInfoDto findAllWeChatCards(String id);

	/**
	 * 查询预约类型下拉框
	 * @return
	 */
	List<Map> findAllResType();

	/**
	 * 查询客户经理
	 * @return
	 */
	List<Map> findEmployee();

	/**
	 * 预约确认
	 * @param id
	 * @param param
	 */
	void btnComfirm(String id, Map<String, String> param);

	/**
	 * 导出数据
	 * @return
	 */
	List<Map> findAllForExcel();

	List<Map> findEmployees();

}
