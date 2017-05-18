package com.yonyou.dms.gacfca;

import com.yonyou.dms.function.exception.ServiceBizException;

/**
 * 经销商车辆实销数据 上报
 * @author Benzc
 * @date 2017年1月11日
 *
 */
public interface SADMS008Coud {
	
	public int getSADMS008(String vin,String customerNo) throws ServiceBizException;

}
