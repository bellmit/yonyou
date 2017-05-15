package com.yonyou.dcs.gacfca;

import java.util.List;

import com.yonyou.dms.DTO.gacfca.TiDmsNTestDriveCarDto;

/**
 * 
 * Title:SOTDCS014Cloud
 * Description: 创建客户信息（试驾车辆信息）(DMS新增)接收DMS->DCS
 * @author DC
 * @date 2017年4月14日 下午4:20:40
 * result msg 1：成功 0：失败
 */
public interface SOTDCS014Cloud {
	
	public String handleExecutor(List<TiDmsNTestDriveCarDto> dto) throws Exception;

}
