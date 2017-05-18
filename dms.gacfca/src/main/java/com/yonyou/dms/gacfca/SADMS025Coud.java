package com.yonyou.dms.gacfca;

import java.util.LinkedList;

import com.yonyou.dms.DTO.gacfca.SADCS025Dto;
import com.yonyou.dms.function.exception.ServiceBizException;

/**
 * PAD建档率报表上报
 * @author Benzc
 * @date 2017年1月13日
 *
 */
public interface SADMS025Coud {
	
	public LinkedList<SADCS025Dto> getSADMS025() throws ServiceBizException;

}
