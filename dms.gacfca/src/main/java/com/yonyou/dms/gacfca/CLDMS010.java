package com.yonyou.dms.gacfca;

import java.util.LinkedList;

import com.yonyou.dms.DTO.gacfca.LabourPayDTO;
import com.yonyou.dms.function.exception.ServiceBizException;

/**
 * @description 工时单价下发
 * @author Administrator
 *
 */
public interface CLDMS010 {
	public int getCLDMS010(LinkedList<LabourPayDTO> volist,String dealerCode) throws ServiceBizException;
}
