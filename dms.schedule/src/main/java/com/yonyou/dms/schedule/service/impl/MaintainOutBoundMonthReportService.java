/**
 * 
 */
package com.yonyou.dms.schedule.service.impl;

import java.util.List;
import java.util.Map;

import com.yonyou.dms.function.exception.ServiceBizException;

/**
 * @author Administrator
 *
 */
public interface MaintainOutBoundMonthReportService {
	public int performExecute() throws ServiceBizException;
	
	@SuppressWarnings("rawtypes")
	public List<Map> getOutBoundReturn(int range,int overDate) throws Exception;
	
	public int querySixMonthFailNum(Long soldBy) throws Exception;//获得销售顾问 最近六个月绩效通过数
	
	@SuppressWarnings("rawtypes")
	public List<Map> getOutBoundEntityReturn(int range,int overDate) throws Exception;//获取经销商为维度的核实结果月报统计
	
	public int queryEntitySixMonthFailNum() throws Exception;//获取经销商六个月未 达标数
}
