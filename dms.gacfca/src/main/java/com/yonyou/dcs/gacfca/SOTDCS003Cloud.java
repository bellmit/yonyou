package com.yonyou.dcs.gacfca;

import java.util.List;

import com.yonyou.dms.DTO.gacfca.TiDmsNCustomerInfoDto;

/**
 * 
 * Title:SOTDCS003Cloud
 * Description: 客户接待信息/需求分析(DMS新增)上报接收DMS->DCS
 * @author DC
 * @date 2017年4月13日 下午4:25:43
 * result msg 1：成功 0：失败
 */
public interface SOTDCS003Cloud {
	
	public String handleExecutor(List<TiDmsNCustomerInfoDto> dto) throws Exception;
}
