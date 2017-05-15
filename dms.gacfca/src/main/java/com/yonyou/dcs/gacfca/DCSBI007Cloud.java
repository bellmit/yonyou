package com.yonyou.dcs.gacfca;

import java.util.List;

import com.yonyou.dms.DTO.gacfca.TtTestDriveDTO;

/**
 * 
* @ClassName: DCSBI007Cloud 
* @Description: 试乘试驾数据上报
* @author zhengzengliang 
* @date 2017年4月11日 下午4:13:32 
*
 */
public interface DCSBI007Cloud {

	public String receiveDate(List<TtTestDriveDTO> dto) throws Exception;
	
}
