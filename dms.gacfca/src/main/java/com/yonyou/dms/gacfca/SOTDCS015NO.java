package com.yonyou.dms.gacfca;

import java.util.Date;

import com.yonyou.dms.function.exception.ServiceBizException;

/**
 * 更新客户信息(休眠，订单，交车客户状态数据传递)DMS更新 接口
 * 订单 NO
 * @author wangliang
 * @date 2017年2月24日
 */
public interface SOTDCS015NO {
	
	public int getSOTDCS015NO(String customerNo,String date) throws ServiceBizException;

}
