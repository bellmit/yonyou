package com.yonyou.dms.vehicle.service.orderManage;

import java.util.Map;

import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.vehicle.domains.DTO.orderManager.PayRemindDto;

public interface PayRemindService {
	/**
	 * 付款通知护查询
	 * 
	 * @param queryParam
	 * @return
	 */
	public PageInfoDto payRemind(Map<String, String> queryParam);

	/**
	 * 付款新增
	 * 
	 * @param pyDto
	 * @return
	 */
	public PayRemindDto addPayaremind(PayRemindDto pyDto);

	/**
	 * 修改付款通知
	 * 
	 * @param payDto
	 * 
	 * @param pyDto
	 * @return
	 */

	public void editPayaremind(Long id, PayRemindDto payDto);

	/**
	 * 删除
	 * 
	 * @param payDto
	 * @param id
	 */
	public void delPayaremind(PayRemindDto payDto, Long id);

}
