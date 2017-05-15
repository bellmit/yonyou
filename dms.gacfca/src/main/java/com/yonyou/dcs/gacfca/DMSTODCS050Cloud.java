package com.yonyou.dcs.gacfca;

import java.util.List;

import com.yonyou.dms.common.domains.DTO.basedata.SADCS050Dto;

/**
 * @Description:二手车置换率月报（周报）数据接收接口
 * @author xuqinqin 
 */
public interface DMSTODCS050Cloud {
	public String receiveData(List<SADCS050Dto> dto) throws Exception;
}
