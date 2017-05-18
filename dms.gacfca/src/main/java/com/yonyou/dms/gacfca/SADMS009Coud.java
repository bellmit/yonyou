package com.yonyou.dms.gacfca;

import java.util.LinkedList;

import com.yonyou.dms.DTO.gacfca.SA009Dto;
import com.yonyou.dms.function.exception.ServiceBizException;

/**
 * 业务描述：发票多次扫描上报
 * 
 * @author Benzc
 * @date 2017年1月13日
 * 
 */
public interface SADMS009Coud {
	
	public LinkedList<SA009Dto> getSADMS009() throws ServiceBizException;

}
