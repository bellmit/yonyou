package com.yonyou.dcs.gacfca;

import java.util.List;

import com.yonyou.dms.DTO.gacfca.PoCusWholeRepayClryslerDto;
import com.yonyou.dms.function.exception.ServiceBizException;
/**
 * @ClassName: SADCS015_1Cloud 
 * @Description: TODO(大客户报备返利审批数据下发) 
 * @author xuqinqin
 */
public interface SADCS015_1Cloud {
	
	public String sendData(String wsNo, String dealerCode) throws ServiceBizException;
	
	public List<PoCusWholeRepayClryslerDto> getDataList(String wsNo, String dealerCode) throws ServiceBizException;
}
