package com.yonyou.dcs.gacfca;

import java.util.List;

import com.yonyou.dms.DTO.gacfca.PoCusWholeClryslerDto;
import com.yonyou.dms.function.exception.ServiceBizException;

/**
 * 大客户报备审批结果下发
 * 
 * @author
 *
 */
public interface SADCS013Colud {
	public String execute(String wsNo, String dealerCode) throws ServiceBizException;

	public List<PoCusWholeClryslerDto> getDataList(String wsNo, String dealerCode) throws ServiceBizException;

}
