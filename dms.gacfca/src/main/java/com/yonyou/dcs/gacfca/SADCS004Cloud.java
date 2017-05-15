package com.yonyou.dcs.gacfca;

import java.util.List;

import com.yonyou.dms.DTO.gacfca.SameToDccDto;
import com.yonyou.dms.function.exception.ServiceBizException;

/**
 * 
* @ClassName: SADCS004Cloud 
* @Description: 销售信息撞单上报接收（反馈状态，1表示成功，2表示失败，3表示休眠）
* @author zhengzengliang 
* @date 2017年4月13日 上午11:30:45 
*
 */
public interface SADCS004Cloud {
	
	public String receiveDate(List<SameToDccDto> dto) throws ServiceBizException;

}
