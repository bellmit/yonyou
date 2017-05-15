package com.yonyou.dcs.gacfca;

import java.util.List;

import com.yonyou.dms.DTO.gacfca.SADMS017Dto;
import com.yonyou.dms.function.exception.ServiceBizException;

/**
 * 二手车置换客户返利审批数据下发
 * 
 * @author Administrator
 *
 */
public interface SADCS017Cloud {
	public String execute(String replaceApplyNo) throws ServiceBizException;

	public List<SADMS017Dto> getDataList(String replaceApplyNo) throws ServiceBizException;

}
