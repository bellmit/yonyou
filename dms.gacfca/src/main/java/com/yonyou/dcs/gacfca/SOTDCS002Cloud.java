package com.yonyou.dcs.gacfca;

import java.util.List;

import com.yonyou.dms.DTO.gacfca.TiDmsNSalesPersonnelDto;

/**
 * 
 * Title:SOTDCS002Cloud
 * Description: 展厅销售人员信息同步(DMS新增)上报接收DMS->DCS
 * @author DC
 * @date 2017年4月13日 下午3:42:00
 * result msg 1：成功 0：失败
 */
public interface SOTDCS002Cloud {
	
	public String handleExecutor(List<TiDmsNSalesPersonnelDto> dto) throws Exception;
}
