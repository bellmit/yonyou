package com.yonyou.dcs.gacfca;

import java.util.List;

import com.yonyou.dms.DTO.gacfca.TiDmsUCultivateDTO;

/**
 * 
 * Title:SOTDCS012Cloud
 * Description: 更新客户信息（客户培育）(DMS更新)接收DMS->DCS
 * @author DC
 * @date 2017年4月14日 下午3:59:09
 * result msg 1：成功 0：失败
 */
public interface SOTDCS012Cloud {
	
	public String handleExecutor(List<TiDmsUCultivateDTO> dto) throws Exception;

}
