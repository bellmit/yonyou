package com.yonyou.dcs.de;

import java.util.List;

import com.yonyou.dms.DTO.gacfca.SADMS003ForeReturnDTO;
import com.yonyou.f4.de.DEException;
import com.yonyou.f4.de.DEMessage;

/**
 * DCC建档客户信息反馈接收
 * @author Benzc
 * @date 2017年5月15日
 */
public interface SADCS003ForeReturn {
	
	public DEMessage execute(DEMessage deMsg) throws DEException;

}
