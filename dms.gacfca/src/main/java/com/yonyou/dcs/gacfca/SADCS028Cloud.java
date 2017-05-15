package com.yonyou.dcs.gacfca;

import java.util.List;

import com.yonyou.dms.DTO.gacfca.WXBindingDTO;

/**
 * 当月进厂客户微信绑定率统计报表数据
 * 
 * @author lingxinglu
 *
 */
public interface SADCS028Cloud {
	public String handleExecutor(List<WXBindingDTO> dto) throws Exception;
}
