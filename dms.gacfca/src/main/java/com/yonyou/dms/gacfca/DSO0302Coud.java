package com.yonyou.dms.gacfca;

import com.yonyou.dms.function.exception.ServiceBizException;

public interface DSO0302Coud {
	public int getDSO0302(String vin,String soNo,String isSecondTime,String isConfirmed) throws ServiceBizException;
}
