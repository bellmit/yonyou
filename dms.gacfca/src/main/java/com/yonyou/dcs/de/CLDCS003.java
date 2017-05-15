package com.yonyou.dcs.de;

import java.util.List;

/**
 * 
* @ClassName: CLDCS003 
* @Description: 车型价格信息下发
* @author zhengzengliang 
* @date 2017年4月5日 上午11:53:26 
*
 */
public interface CLDCS003 {

	public String sendData(List<String> dealerList, String[] groupId) throws Exception ;
	
}
