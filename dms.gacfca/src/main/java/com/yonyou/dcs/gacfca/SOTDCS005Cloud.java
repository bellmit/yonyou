package com.yonyou.dcs.gacfca;

import java.util.List;

import com.yonyou.dms.DTO.gacfca.TiDmsNSwapDto;

/**
 * 
 * Title:SOTDCS005Cloud
 * Description: 创建客户信息（置换需求）(DMS新增)接收DMS->DCS
 * @author DC
 * @date 2017年4月13日 下午5:03:37
 * result msg 1：成功 0：失败
 */
public interface SOTDCS005Cloud {
	
	public String handleExecutor(List<TiDmsNSwapDto> dto) throws Exception;

}
