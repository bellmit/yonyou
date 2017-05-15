package com.yonyou.dcs.gacfca;

import java.util.List;

import com.yonyou.dms.common.domains.DTO.basedata.SA013Dto;

/**
 * @Description:展厅流量报表接口
 * @author xuqinqin 
 */
public interface SEDCS014Cloud {
	public String receiveDate(List<SA013Dto> dto) throws Exception;
}
