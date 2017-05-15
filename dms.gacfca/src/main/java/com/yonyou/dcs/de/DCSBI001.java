package com.yonyou.dcs.de;

import com.yonyou.f4.de.DEException;
import com.yonyou.f4.de.DEMessage;
/**
 * 
* @ClassName: DCSBI001 
* @Description: 销售订单上报数据
* @author zhengzengliang 
* @date 2017年4月6日 下午3:27:38 
*
 */
public interface DCSBI001 {
	public DEMessage execute(DEMessage deMsg) throws DEException;
}
