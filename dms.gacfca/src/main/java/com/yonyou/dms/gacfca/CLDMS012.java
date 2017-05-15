package com.yonyou.dms.gacfca;

import java.util.LinkedList;

import com.yonyou.dms.common.domains.DTO.basedata.OutBoundReturnDTO;
import com.yonyou.dms.function.exception.ServiceBizException;

/**
 * 接收 400 外呼核实结果
 * @author wangliang
 * @date 2017年4月18日
 */
public interface CLDMS012 {
	public int getCLDMS012(LinkedList<OutBoundReturnDTO> voList, String dealerCode) throws ServiceBizException;
}
