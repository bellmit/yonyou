package com.yonyou.dcs.gacfca;

import java.util.List;

import com.yonyou.dms.DTO.gacfca.SADCS060Dto;

/**
 * 
 * Title:SADCS060Cloud
 * Description: 订车接口数据接收
 * @author DC
 * @date 2017年4月7日 下午8:15:44
 * result msg 1：成功 0：失败
 */
public interface SADCS060Cloud {
	public String handleExecutor(List<SADCS060Dto> dtoList) throws Exception;
}
