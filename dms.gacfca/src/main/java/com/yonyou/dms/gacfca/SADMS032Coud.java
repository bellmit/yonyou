package com.yonyou.dms.gacfca;

import java.util.List;

import com.yonyou.dms.DTO.gacfca.SADCS032Dto;
import com.yonyou.dms.function.exception.ServiceBizException;

/**
 * 大客户政策车型数据下发
 * @author Benzc
 * @date 2017年1月10日
 *
 */
public interface SADMS032Coud {
	
	public int getSADMS032(String dealerCode,List<SADCS032Dto> dtList) throws ServiceBizException;

}
