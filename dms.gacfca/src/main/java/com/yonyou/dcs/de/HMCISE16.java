package com.yonyou.dcs.de;

import com.yonyou.dms.common.domains.DTO.basedata.TtWrClaimDcsDTO;
import com.yonyou.dms.function.exception.ServiceBizException;

/**
 * @Title: HMCISE16Cloud
 * @Description:索赔单驳回下发
 * @author xuqinqin 
 * @date 2017年5月4日 
 */
public interface HMCISE16 {

	public String sendAllData(TtWrClaimDcsDTO dto) throws ServiceBizException;
	
}
