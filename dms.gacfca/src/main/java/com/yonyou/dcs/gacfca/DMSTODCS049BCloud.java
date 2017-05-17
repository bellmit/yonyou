package com.yonyou.dcs.gacfca;

import java.util.List;

import com.yonyou.dms.DTO.gacfca.SADCS049Dto;

/**
 * @Description:二手车置换意向明细数据接口
 * @author xuqinqin 
 */
public interface DMSTODCS049BCloud extends BaseCloud{
	public String handleExecutor(List<SADCS049Dto> dtos) throws Exception;
}
