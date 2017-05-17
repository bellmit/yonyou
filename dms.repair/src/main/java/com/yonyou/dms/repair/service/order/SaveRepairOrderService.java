/**
 * 
 */
package com.yonyou.dms.repair.service.order;

import java.util.List;
import java.util.Map;

import com.yonyou.dms.common.domains.DTO.basedata.RepairOrderDetailsDTO;

/**
 * @author yangjie
 *
 */
public interface SaveRepairOrderService {

	/**
	 * 保存主方法
	 * @param dto
	 */
	void btnSave(RepairOrderDetailsDTO dto);

	/**
	 * 工单保存获取可参加的所有服务活动信息
	 * @param query
	 * @return
	 */
	List<Map> getAllEnterableActivityInfo(Map<String, String> query);

	/**
	 * 三包授权
	 * @param param
	 */
	void btn3BaoAccredit(Map<String, String> param);

	/**
	 * 工单保存时的三包预警判断
	 * @param param
	 * @return
	 */
	Map getTripleInfo(Map<String, String> param);

	/**
	 * 出险单相关
	 * @param param
	 */
	void occurInsuranceAbout(Map<String, String> param);

	/**
	 * 保存工单时存入赔付、旧件处理信息
	 * @param param
	 */
	void saveSettlementOldpart(Map<String, String> param);

}
