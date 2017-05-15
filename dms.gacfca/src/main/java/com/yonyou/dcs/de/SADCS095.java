package com.yonyou.dcs.de;

import com.yonyou.f4.de.DEException;
import com.yonyou.f4.de.DEMessage;

/**
 * 经销商零售信息变更上报
 * @author Benzc
 * @date 2017年5月15日
 */
public interface SADCS095 {
	
	public DEMessage execute(DEMessage deMsg) throws DEException;

}
