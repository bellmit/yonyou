package com.yonyou.dcs.gacfca;

import java.util.List;

import com.yonyou.dms.DTO.gacfca.TtVisitingRecordDTO;


/**
 * 
* @ClassName: DCSBI002Cloud 
* @Description: 展厅客户数据上报
* @author zhengzengliang 
* @date 2017年4月6日 下午7:47:43 
*
 */
public interface DCSBI002Cloud {
	public String receiveDate(List<TtVisitingRecordDTO> dto) throws Exception;
}
