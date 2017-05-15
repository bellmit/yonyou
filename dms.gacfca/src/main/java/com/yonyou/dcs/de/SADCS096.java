package com.yonyou.dcs.de;

import com.yonyou.f4.de.DEException;
import com.yonyou.f4.de.DEMessage;

/**
 * 大客户组织架构权限审批数据接收
 * @author Benzc
 * @date 5月15日
 */
public interface SADCS096 {
	
	public DEMessage execute(DEMessage deMsg) throws DEException;

}
