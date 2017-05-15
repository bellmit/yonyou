package com.yonyou.dcs.gacfca;

import java.util.List;

import com.yonyou.dms.DTO.gacfca.SADCS029DTO;

/**
 * 总部监控经销商调价报表上报
 * 
 * @author lingxinglu
 */
public interface SADCS029Cloud {
	public String handleExecutor(List<SADCS029DTO> dto) throws Exception;
}
