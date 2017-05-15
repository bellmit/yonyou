package com.yonyou.dcs.gacfca;

import java.util.List;

import com.yonyou.dms.DTO.gacfca.SADMS056Dto;

/**
 * 
 * Title:SADCS056Cloud 
 * Description: 试乘试驾分析数据上报
 * @author DC
 * @date 2017年4月7日 上午10:21:43
 * result msg 1：成功 0：失败
 */
public interface SADCS056Cloud {
	
	public String handleExecutor(List<SADMS056Dto> dto) throws Exception;

}
