package com.yonyou.dcs.de;

import com.yonyou.f4.de.DEException;
import com.yonyou.f4.de.DEMessage;
/**
 * 销售信息撞单上报
 * @author Benzc
 * @date 2017年5月15日
 */
public interface SADCS004 {
	
	public DEMessage execute(DEMessage deMsg) throws DEException;

}
