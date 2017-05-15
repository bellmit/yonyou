package com.yonyou.dcs.de;

import com.yonyou.f4.de.DEException;
import com.yonyou.f4.de.DEMessage;

/**
 * 投保单上端接口
 * @author Benzc
 * @date 2017年5月15日
 */
public interface SADCS058 {
	
	public DEMessage execute(DEMessage deMsg) throws DEException;

}
