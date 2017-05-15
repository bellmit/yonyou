package com.yonyou.dms.gacfca;

import java.util.LinkedList;

import com.yonyou.dms.DTO.gacfca.DMSTODCS001DTO;
import com.yonyou.dms.function.exception.ServiceBizException;

/**
 * 上报撞单的信息
 * @author Benzc
 * @date 2017年4月20日
 */
public interface DMSTODCS001 {
	
	public LinkedList<DMSTODCS001DTO> getDMSTODCS001(String[] cusno,String[] actiondate,String[] promresult) throws ServiceBizException;

}
