package com.yonyou.dcs.gacfca;

import java.util.List;

import com.yonyou.dms.DTO.gacfca.ProperServiceManageDto;

/**
 * @Description:交车客户、客户经理重绑
 * 
 *
 */
public interface SADCS018Cloud {
	public String handleExecutor(List<ProperServiceManageDto> dto) throws Exception;
}
