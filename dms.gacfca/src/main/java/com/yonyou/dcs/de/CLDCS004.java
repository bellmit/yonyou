package com.yonyou.dcs.de;

import java.util.List;
/**
 * 
* @ClassName: CLDCS004 
* @Description: 市场活动（活动主单、车型清单）
* @author zhengzengliang 
* @date 2017年4月5日 下午6:52:02 
*
 */
public interface CLDCS004 {

	public String sendData(List<String> dealerList, String[] groupId) throws Exception ;
	
}
