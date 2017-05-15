package com.yonyou.dcs.gacfca;

import java.util.List;

import com.yonyou.dms.DTO.gacfca.PoCusWholeRepayClryslerDto;
import com.yonyou.dms.function.exception.ServiceBizException;

/**
 * 大客户报备返利审批数据下发
 * 
 * @author lingxinglu
 *
 */
public interface SADCS015Cloud {

	public String execute(String wsNo, String dealerCode) throws ServiceBizException;

	public List<PoCusWholeRepayClryslerDto> getDataList(String wsNo, String dealerCode) throws ServiceBizException;
}
