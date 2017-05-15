/**
 * 
 */
package com.yonyou.dms.schedule.service.impl;

import java.util.List;

import com.yonyou.dms.common.domains.PO.basedata.TtSalesCrPO;
import com.yonyou.dms.function.exception.ServiceBizException;
import com.yonyou.dms.schedule.domains.DTO.TtSalesCrDTO;

/**
 * @author xhy
 *保有客户关怀
 *@date 2017年2月23日
 */
public interface ExecuteCrcTaskService {
	public int performExecute() throws ServiceBizException;
	
	@SuppressWarnings("rawtypes")
	public List selectCrcTask() throws ServiceBizException;
	
	@SuppressWarnings("rawtypes")
	public List selectCrcCustomer(String intervalDays,String today) throws ServiceBizException;
	
	@SuppressWarnings("rawtypes")
	public List checkCustomer(String customerNo,String taskId) throws ServiceBizException;
	
	public void updateCrcTask(TtSalesCrPO salcrConPo,TtSalesCrDTO salcrpo) throws ServiceBizException;
	
	
}
