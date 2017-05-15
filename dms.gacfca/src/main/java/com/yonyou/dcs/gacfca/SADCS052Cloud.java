package com.yonyou.dcs.gacfca;

import java.util.List;

import com.yonyou.dms.DTO.gacfca.SADMS052DTO;

public interface SADCS052Cloud {
	public String handleExecutor(List<SADMS052DTO> dto) throws Exception;
}
