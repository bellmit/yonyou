package com.yonyou.dcs.de;

import com.yonyou.f4.de.DEException;
import com.yonyou.f4.de.DEMessage;

/**
 * 
* @ClassName: DCSBI002 
* @Description: 展厅客户数据上报
* @author zhengzengliang 
* @date 2017年4月6日 下午6:59:23 
*
 */
public interface DCSBI002 {
	
	public DEMessage execute(DEMessage deMsg) throws DEException;

}
