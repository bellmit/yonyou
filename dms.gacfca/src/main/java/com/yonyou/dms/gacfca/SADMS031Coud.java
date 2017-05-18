package com.yonyou.dms.gacfca;

import java.util.List;

import com.yonyou.dms.DTO.gacfca.SADCS031Dto;
import com.yonyou.dms.function.exception.ServiceBizException;

/**
 * 下发大客户车型系列
 * @author Benzc
 * @date 2017年1月10日
 */
public interface SADMS031Coud {
	
	public int getSADMS031(String dealerCode,List<SADCS031Dto> dtList) throws ServiceBizException;

}
