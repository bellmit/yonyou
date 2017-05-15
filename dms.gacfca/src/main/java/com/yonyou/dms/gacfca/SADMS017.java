package com.yonyou.dms.gacfca;

import java.util.List;

import com.yonyou.dms.DTO.gacfca.SADMS017Dto;
import com.yonyou.dms.function.exception.ServiceBizException;

/**
 * 业务描述：置换返利申请结果下发
 * 
 * @author Benzc
 * @date 2017年1月10日
 * 
 */
public interface SADMS017{
	
	public int getSADMS017(String dealerCode,List<SADMS017Dto> dtList) throws ServiceBizException;

}
