/**
 * 
 */
package com.yonyou.dms.gacfca;

import java.util.LinkedList;

import com.yonyou.dms.DTO.gacfca.ProductModelPriceDTO;
import com.yonyou.dms.function.exception.ServiceBizException;

/**
 * 业务描述：车型价格数据下发
 * @author Administrator
 *
 */
public interface CLDMS003Coud {
	public int getCLDMS003(LinkedList<ProductModelPriceDTO> voList,String dealerCode) throws ServiceBizException;
	
}
