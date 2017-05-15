package com.yonyou.dcs.gacfca;

import java.util.List;

import com.yonyou.dms.DTO.gacfca.SADCS025Dto;

/**
 * PAD建档率报表
 * 
 * @author Administrator result msg 1：成功 0：失败
 */
public interface SADCS025Cloud {
	public String handleExecutor(List<SADCS025Dto> dto) throws Exception;
}
