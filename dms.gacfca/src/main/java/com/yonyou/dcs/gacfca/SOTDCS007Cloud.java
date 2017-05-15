package com.yonyou.dcs.gacfca;

import java.util.List;

import com.yonyou.dms.DTO.gacfca.TiDmsNCultivateDto;

/**
 * 
 * Title:SOTDCS005CloudImpl
 * Description: 创建客户信息（置换需求）(DMS新增)接收DMS->DCS
 * @author DC
 * @date 2017年4月13日 下午5:35:20
 * result msg 1：成功 0：失败
 */
public interface SOTDCS007Cloud {
	
	public String handleExecutor(List<TiDmsNCultivateDto> dto) throws Exception;
}
