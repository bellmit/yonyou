package com.yonyou.dcs.gacfca;

import java.util.List;

import com.yonyou.dms.DTO.gacfca.SADMS051DTO;

/**
 * 经营月报数据上报
 * 
 * @author lingxinglu
 *
 */
public interface SADCS051Cloud {
	public String handleExecutor(List<SADMS051DTO> dto) throws Exception;
}
