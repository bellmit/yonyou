package com.yonyou.dms.gacfca;

import com.yonyou.dms.function.exception.ServiceBizException;

public interface SEDMS022 {
	public int getSEDMS022(String itemId,String vin,String photo) throws ServiceBizException;
}
