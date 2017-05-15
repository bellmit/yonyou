package com.yonyou.dcs.gacfca;

import java.util.List;

import com.yonyou.dms.DTO.gacfca.PoCusWholeRepayClryslerDto;

/**
 * 大客户报备返利数据接收
 * 
 *
 */
public interface SADCS014Cloud {
	public String handleExecutor(List<PoCusWholeRepayClryslerDto> dto) throws Exception;
}
