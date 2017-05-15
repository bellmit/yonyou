package com.yonyou.dms.vehicle.service.k4Order;

import java.util.List;
import java.util.Map;

import com.yonyou.dms.function.exception.ServiceBizException;
import com.yonyou.dms.vehicle.domains.DTO.k4Order.TmpVsRebateImpDTO;

/**
 * @author liujiming
 * @date 2017年3月7日
 */
public interface DealerRebateImportService {
	// 删除返利临时表数据
	public void deleteTmpVsRebateImp() throws ServiceBizException;

	// 向临时表插入数据
	public void insertTmpVsRebateImp(TmpVsRebateImpDTO rowDto) throws ServiceBizException;

	// 检查临时表数据
	public List<TmpVsRebateImpDTO> checkData() throws ServiceBizException;

	// 查询临时表中数据
	public List<Map> dealerRebateImportQuery() throws ServiceBizException;

	// 将临时表中数据插入业务表
	public void insertToRebate() throws ServiceBizException;

}
