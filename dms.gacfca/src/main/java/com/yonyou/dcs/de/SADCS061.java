package com.yonyou.dcs.de;

import com.yonyou.f4.de.DEException;
import com.yonyou.f4.de.DEMessage;
/**
 * 订车接口  接收
 * @author Benzc
 * @date 2017年5月15日
 */
public interface SADCS061 {
	
	public DEMessage execute(DEMessage deMsg) throws DEException;

}
