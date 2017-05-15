package com.yonyou.dcs.gacfca;

import java.util.List;

import com.yonyou.dms.common.domains.DTO.basedata.SADMS063Dto;


/**
 * @Description: TODO(留存订单上报接口) 
 * @author xuqinqin
 */
public interface SADCS063Cloud {
	public String receiveDate(List<SADMS063Dto> dtos) throws Exception;
}
