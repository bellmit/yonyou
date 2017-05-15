package com.yonyou.dcs.gacfca;

import java.util.List;

import com.yonyou.dms.DTO.gacfca.ProperServManInfoDTO;

/**
 * @Description:4经销商专属客户经理列表接受
 * 
 */
public interface SADCS019Cloud {
	public String handleExecutor(List<ProperServManInfoDTO> dto) throws Exception;
}
