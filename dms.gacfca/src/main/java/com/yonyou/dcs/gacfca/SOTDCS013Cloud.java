package com.yonyou.dcs.gacfca;

import java.util.List;

import com.yonyou.dms.DTO.gacfca.TiDmsUSalesQuotasDto;

/**
 * 
 * Title:SOTDCS013Cloud
 * Description: 销售人员分配信息接收DMS更新DMS->DCS
 * @author DC
 * @date 2017年4月14日 下午4:10:23
 * result msg 1：成功 0：失败
 */
public interface SOTDCS013Cloud {

	public String handleExecutor(List<TiDmsUSalesQuotasDto> dto) throws Exception;
	
}
