package com.yonyou.dms.gacfca;

import java.util.List;

import com.yonyou.dms.DTO.gacfca.SA007Dto;
import com.yonyou.dms.function.exception.ServiceBizException;



/**
 * 业务描述：广汽菲克的 车辆调拨信息下发 
 * 
 * @date 2017年1月6日
 * @author Benzc
 *
 */
public interface SADMS007 {
	
	public int getSADMS007(String dealer_code,List<SA007Dto> dtlist) throws ServiceBizException, Exception;

}
