package com.yonyou.dcs.gacfca;

import java.util.List;

import com.yonyou.dms.DTO.gacfca.SEDMS070Dto;

public interface SEDMS070Cloud {
	
	public String handleExecutor(List<SEDMS070Dto> list) throws Exception;

}
