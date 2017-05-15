package com.yonyou.dcs.de;

import com.yonyou.f4.de.DEException;
import com.yonyou.f4.de.DEMessage;

/**
 * 展厅日预测报告数据上报
 * @author Benzc
 * @date 2017年5月15年
 */
public interface SADCS010Day {
	
	public DEMessage execute(DEMessage deMsg) throws DEException;

}
