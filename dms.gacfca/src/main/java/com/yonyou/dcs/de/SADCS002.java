package com.yonyou.dcs.de;

import com.yonyou.f4.de.DEException;
import com.yonyou.f4.de.DEMessage;

/**
 * 经销商车辆验收数据上报
 * @author Benzc
 * @date 2017年5月15日
 */
public interface SADCS002 {
	
	public DEMessage execute(DEMessage deMsg) throws DEException;

}
