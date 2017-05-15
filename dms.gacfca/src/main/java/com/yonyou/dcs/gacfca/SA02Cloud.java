package com.yonyou.dcs.gacfca;

import java.util.List;

import com.yonyou.dms.common.domains.DTO.common.SalesorderShoppingDTO;
import com.yonyou.dms.function.exception.ServiceBizException;
/**
 * @ClassName: SA01Cloud 
 * @Description: TODO(调拨审批结果下发) 
 * @author xuqinqin
 */
public interface SA02Cloud {
	
	public String sendData(List<String> dealerCodes, SalesorderShoppingDTO vo) throws ServiceBizException;
	
}
