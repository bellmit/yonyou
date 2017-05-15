package com.yonyou.dcs.de;

import com.yonyou.dms.common.domains.DTO.basedata.TtWrClaimDcsDTO;
import com.yonyou.dms.function.exception.ServiceBizException;

/**
 * @Title: HMCISE17
 * @Description:索赔单删除下发
 * @author xuqinqin 
 * @date 2017年5月4日 
 */
public interface HMCISE17 {

	public String sendAllData(TtWrClaimDcsDTO dto) throws ServiceBizException;
	
}
