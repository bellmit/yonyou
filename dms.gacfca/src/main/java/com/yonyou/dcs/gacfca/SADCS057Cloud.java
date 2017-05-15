package com.yonyou.dcs.gacfca;

import java.util.List;

import com.yonyou.dms.DTO.gacfca.SA057DTO;

/**
 * 
 * Title:SADCS057Cloud
 * Description: 试乘试驾统计报表上报
 * @author yh135
 * @date 2017年4月7日 上午11:27:36
 * result msg 1：成功 0：失败
 */
public interface SADCS057Cloud {
	
	public String handleExecutor(List<SA057DTO> dto) throws Exception;

}
