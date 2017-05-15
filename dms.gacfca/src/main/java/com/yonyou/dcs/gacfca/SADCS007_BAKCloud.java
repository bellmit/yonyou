package com.yonyou.dcs.gacfca;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import com.yonyou.dms.DTO.gacfca.SA007Dto;
import com.yonyou.dms.function.exception.ServiceBizException;
/**
 * @ClassName: SADCS007_BAKCloud 
 * @Description: TODO(经销商之间车辆调拨下发) 
 * @author xuqinqin
 */
public interface SADCS007_BAKCloud {
	
	public String sendData() throws ServiceBizException;
	
	public List<Map> getSendDate()throws ServiceBizException;
	
	public LinkedList<SA007Dto> setDto(List<Map> list)throws ServiceBizException;
}
