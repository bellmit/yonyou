package com.yonyou.dms.gacfca;

import java.util.LinkedList;

import com.yonyou.dms.DTO.gacfca.ProperServiceManageDto;
import com.yonyou.dms.DTO.gacfca.SADMS064DTO;
import com.yonyou.dms.function.exception.ServiceBizException;

/**
 * 一对一客户经理绑定上报DCS
 * @author wangliang
 * @date 2017年2月14日
 */
public interface SEDMS015Coud {
	
	//public LinkedList<ProperServiceManageDto> getSEDMS015(String serviceAdvisor,String[] vin,String[] isSelected) throws ServiceBizException;
	public int getSEDMS015(String serviceAdvisor,String[] vin,String[] isSelected) throws ServiceBizException;
}
