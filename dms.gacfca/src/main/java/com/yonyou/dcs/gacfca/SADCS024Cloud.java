package com.yonyou.dcs.gacfca;

import java.util.List;

import com.yonyou.dms.DTO.gacfca.SEDMS022DTO;

/**
 * 功能：发票多次扫描上报 result msg 1：成功 0：失败
 *
 */
public interface SADCS024Cloud {
	public String handleExecutor(List<SEDMS022DTO> dto) throws Exception;
}
