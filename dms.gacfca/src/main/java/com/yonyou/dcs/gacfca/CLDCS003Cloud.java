package com.yonyou.dcs.gacfca;

import java.util.LinkedList;
import java.util.List;

import com.yonyou.dms.DTO.gacfca.ProductModelPriceDTO;
import com.yonyou.dms.function.exception.ServiceBizException;

/**
 * 
* @ClassName: CLDCS003Cloud 
* @Description: 车型价格信息下发
* @author zhengzengliang 
* @date 2017年4月5日 下午2:16:06 
*
 */
public interface CLDCS003Cloud {
	
	public String execute(List<String> dealerList,String[] groupId) throws ServiceBizException;
	
	public LinkedList<ProductModelPriceDTO> getDataList(String[] groupId,String dealerCode)
			throws ServiceBizException;

}
