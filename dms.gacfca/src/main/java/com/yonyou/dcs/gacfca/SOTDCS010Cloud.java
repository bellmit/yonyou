package com.yonyou.dcs.gacfca;

import java.util.List;

import com.yonyou.dms.DTO.gacfca.TiDmsUSwapDto;

/**
 * 
 * Title:SOTDCS010Cloud
 * Description: 更新客户信息（置换需求）(DMS更新)接收DMS->DCS
 * @author DC
 * @date 2017年4月14日 下午3:06:40
 * result msg 1：成功 0：失败
 */
public interface SOTDCS010Cloud {
	public String handleExecutor(List<TiDmsUSwapDto> dto) throws Exception;
}
