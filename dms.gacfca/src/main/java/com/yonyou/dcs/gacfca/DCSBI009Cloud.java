package com.yonyou.dcs.gacfca;

import java.util.List;

import com.yonyou.dms.DTO.gacfca.TmVsProductDTO;


/**
 * 
* @ClassName: DCSBI009Cloud 
* @Description: 产品数据上报
* @author zhengzengliang 
* @date 2017年4月11日 下午4:27:43 
*
 */
public interface DCSBI009Cloud {
	public String receiveDate(List<TmVsProductDTO> dto) throws Exception;
}
