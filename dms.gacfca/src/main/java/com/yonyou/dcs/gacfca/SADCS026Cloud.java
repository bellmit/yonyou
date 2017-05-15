package com.yonyou.dcs.gacfca;

import java.util.List;

import com.yonyou.dms.DTO.gacfca.SADCS026DTO;

/**
 * 配件入库来源监控报表数据上报
 * 
 * @author
 *
 */
public interface SADCS026Cloud {
	public String handleExecutor(List<SADCS026DTO> dto) throws Exception;
}
