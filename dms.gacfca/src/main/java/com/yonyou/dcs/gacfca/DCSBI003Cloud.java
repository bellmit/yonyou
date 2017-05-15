package com.yonyou.dcs.gacfca;

import java.util.List;

import com.yonyou.dms.DTO.gacfca.TmUserDTO;

/**
 * 
* @ClassName: DCSBI003Cloud 
* @Description: 用户数据上报
* @author zhengzengliang 
* @date 2017年4月11日 下午3:16:46 
*
 */
public interface DCSBI003Cloud {
	public String receiveDate(List<TmUserDTO> dto) throws Exception;
}
