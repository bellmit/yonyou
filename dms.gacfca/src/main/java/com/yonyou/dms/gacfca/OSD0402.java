package com.yonyou.dms.gacfca;

import java.util.List;

import com.yonyou.dms.DTO.gacfca.PoCusWholeRepayClryslerDto;
import com.yonyou.dms.function.exception.ServiceBizException;

/**
 * 业务描述：OEM下发批售客户审批返利状态
 * 
 * @date 2017年1月10日
 * @author Benzc
 *
 */
public interface OSD0402 {

	public int getOSD0402(String dealerCode, List<PoCusWholeRepayClryslerDto> dtList) throws ServiceBizException;

}
