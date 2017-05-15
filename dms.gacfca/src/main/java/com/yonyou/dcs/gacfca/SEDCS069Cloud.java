package com.yonyou.dcs.gacfca;

import java.util.List;

import com.yonyou.dms.DTO.gacfca.SEDMS069Dto;

public interface SEDCS069Cloud {
	
	public String handleExecutor(List<SEDMS069Dto> list) throws Exception;

}
