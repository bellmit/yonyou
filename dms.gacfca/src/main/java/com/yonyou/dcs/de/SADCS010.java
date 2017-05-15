package com.yonyou.dcs.de;

import com.yonyou.f4.de.DEException;
import com.yonyou.f4.de.DEMessage;

/**
 * 展厅预测报告数据上报
 * @author Benzc
 * @date 2017年5月15日
 */
public interface SADCS010 {
	
	public DEMessage execute(DEMessage deMsg) throws DEException;

}
