package com.yonyou.dcs.gacfca;

import java.util.LinkedList;
import java.util.List;

import com.yonyou.dms.DTO.gacfca.CLDMS002Dto;
import com.yonyou.dms.function.exception.ServiceBizException;

/**
 * 2017-03-27
 * @author 夏威
 * 车型组主数据（品牌、车系、车型、配置）
 */
public interface CLDCS002Cloud extends BaseCloud{

	public String execute() throws ServiceBizException;
	
	public LinkedList<CLDMS002Dto> getDataList(String[] groupId,String dealerCode) throws ServiceBizException;
	
	public List<String> sendData(List<String> dealerList,String[] groupIds);
	
}
