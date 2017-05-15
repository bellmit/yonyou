package com.yonyou.dcs.gacfca;

import java.util.List;

import com.yonyou.dms.DTO.gacfca.TiDmsNTestDriveDTO;

/**
 * 
 * Title:SOTDCS004Cloud
 * Description: 创建客户信息（试乘试驾）(DMS新增)接收DMS->DCS
 * @author DC
 * @date 2017年4月13日 下午4:45:03
 * result msg 1：成功 0：失败
 */
public interface SOTDCS004Cloud {
	
	public String handleExecutor(List<TiDmsNTestDriveDTO> dto) throws Exception;
}
