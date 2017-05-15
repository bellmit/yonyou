package com.yonyou.dcs.gacfca;

import java.util.List;

import com.yonyou.dms.DTO.gacfca.SADMS003ForeReturnDTO;

/**
 * 
* @ClassName: SADCS003ReturnCloud 
* @Description: LMS邀约到店撞单接口的反馈上报接收(反馈状态，1表示实销，2表示撞单，3表示休眠)
* @author zhengzengliang 
* @date 2017年4月13日 上午11:19:11 
*
 */
public interface SADCS003ReturnCloud {
	
	public String receiveDate(List<SADMS003ForeReturnDTO> dto) throws Exception;

}
