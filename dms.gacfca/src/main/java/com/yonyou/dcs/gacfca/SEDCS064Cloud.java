package com.yonyou.dcs.gacfca;

import java.util.List;

import com.yonyou.dms.DTO.gacfca.SEDCS064Dto;

public interface SEDCS064Cloud {
	
	public String handleExecutor(List<SEDCS064Dto> list) throws Exception;

}
