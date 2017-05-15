package com.yonyou.dcs.gacfca;

import java.util.List;
import com.yonyou.dms.DTO.gacfca.WXBindingRsgDTO;
import com.yonyou.dms.common.domains.DTO.basedata.OutBoundReturnDTO;
import com.yonyou.dms.function.exception.ServiceBizException;

/**
 * 
 * Title:SendBoldMsgToDmsByWXCloud
 * Description: 微信绑定结果下发
 * @author DC
 * @date 2017年4月12日 下午5:30:04
 */
public interface SendBoldMsgToDmsByWXCloud {
	
	public String handleExecutor(List<OutBoundReturnDTO> dtoList) throws ServiceBizException;
	
	public String sendDate(WXBindingRsgDTO returnVo,String IsEntity) throws ServiceBizException;

}
