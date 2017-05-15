package com.yonyou.dcs.de;

import java.util.List;

import com.yonyou.dms.common.domains.DTO.common.SalesorderShoppingDTO;
import com.yonyou.dms.function.exception.ServiceBizException;

/**
 * @Description:调拨审批结果下发接口
 * @author xuqinqin 
 */
public interface SA02 {
	
	public String sendData(List<String> dealerCodes, SalesorderShoppingDTO vo) throws ServiceBizException;
	
}
