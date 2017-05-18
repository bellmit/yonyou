/**
 * 
 */
package com.yonyou.dms.gacfca;

import java.util.List;

import com.yonyou.dms.DTO.gacfca.TtActivityResultDto;
import com.yonyou.dms.function.exception.ServiceBizException;

/**
 * @author Administrator
 *服务活动完成情况下发
 */
public interface SEDMS055Coud {
	public int getSEDMS055(List<TtActivityResultDto> voList,String dealerCode) throws ServiceBizException;
}
