package com.yonyou.dcs.gacfca;

import com.yonyou.dms.function.exception.ServiceBizException;
/**
 * @Title: SADCS072Cloud
 * @Description:车主资料下发接口
 * 1.总部修改车主信息后，将修改后的车主信息下发所有存有车主信息的经销商
 * 2.DMS修改车主信息后，将车主信息推送给总部，总部将接收到的车主信息下发至除上报经销商外的所有存在车主信息的经销商
 * @author xuqinqin 
 */
public interface SADCS072Cloud {

	public String sendData(String vin,String dealerCode) throws ServiceBizException;
	
}
