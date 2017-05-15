package com.yonyou.dcs.gacfca;

import java.util.List;

import com.yonyou.dms.DTO.gacfca.DMSTODCS004Dto;


public interface DMSTODCS004Cloud {
	
	public String handleExecutor(List<DMSTODCS004Dto> list) throws Exception;

}
