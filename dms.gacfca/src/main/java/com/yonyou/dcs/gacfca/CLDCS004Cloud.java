package com.yonyou.dcs.gacfca;

import java.util.LinkedList;
import java.util.List;

import com.yonyou.dms.DTO.gacfca.TmMarketActivityDto;
import com.yonyou.dms.function.exception.ServiceBizException;

/**
 * 
* @ClassName: CLDCS004Cloud 
* @Description: 市场活动（活动主单、车型清单）
* @author zhengzengliang 
* @date 2017年4月5日 下午7:12:56 
*
 */
public interface CLDCS004Cloud {

	public String execute(List<String> dealerList,String[] groupId) throws ServiceBizException;
	
	public LinkedList<TmMarketActivityDto> getDataList() throws ServiceBizException;
	
}
