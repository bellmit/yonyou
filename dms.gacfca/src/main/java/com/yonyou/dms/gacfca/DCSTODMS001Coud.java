package com.yonyou.dms.gacfca;

import java.util.LinkedList;

import com.yonyou.dms.DTO.gacfca.DCSTODMS001DTO;
import com.yonyou.dms.function.exception.ServiceBizException;

/**
 * 下发撞单的信息
 * @author Benzc
 * @date 2017年4月20日
 */
public interface DCSTODMS001Coud {
	
	public LinkedList<DCSTODMS001DTO> getDCSTODMS001(@SuppressWarnings("rawtypes") LinkedList dtoList,String dealerCode) throws ServiceBizException;

}
