package com.yonyou.dcs.de;

import com.yonyou.f4.de.DEException;
import com.yonyou.f4.de.DEMessage;

/**
 * LMS邀约到店撞单接口的反馈上报
 * @author Benzc
 * @date 2017年5月15日
 */
public interface SADCS003Return {
	
	public DEMessage execute(DEMessage deMsg) throws DEException;

}
