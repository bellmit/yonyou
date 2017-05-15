package com.yonyou.dcs.de;

import java.util.List;

/**
 * 车型组主数据下发
 * @author 夏威
 * @date 2017-4-1
 */
public interface CLDCS002 {
	public String sendData(List<String> dealerList, String[] groupId) throws Exception ;
}
