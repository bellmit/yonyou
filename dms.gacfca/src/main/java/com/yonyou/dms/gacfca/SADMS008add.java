package com.yonyou.dms.gacfca;

import java.util.Date;

import com.yonyou.dms.function.exception.ServiceBizException;

/**
 * 业务描述：实销信息上报
 * 把上端传来的客户专门报一个作为以前下发销售线索的回馈
 * @author wangliang
 * @date 2017年4月19日
 *
 */
public interface SADMS008add {
	public int getSADMS008add(String customerNo,String vin,Date invoiceDate,String isDms) throws ServiceBizException;
}
