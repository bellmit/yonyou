package com.yonyou.dcs.gacfca;

import java.util.List;

import com.yonyou.dms.common.domains.DTO.basedata.OutBoundReturnDTO;
import com.yonyou.dms.function.exception.ServiceBizException;

/**
 * 
 * Title:SendBoldMsgToDmsCloud
 * Description: 车主核实结果下发
 * @author DC
 * @date 2017年4月12日 下午5:28:39
 */
public interface SendBoldMsgToDmsCloud {
	
	public String handleExecutor(List<OutBoundReturnDTO> dtoList) throws ServiceBizException;
	
	public String sendData(OutBoundReturnDTO returnVo) throws ServiceBizException;

}
