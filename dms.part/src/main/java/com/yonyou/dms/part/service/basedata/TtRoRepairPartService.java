package com.yonyou.dms.part.service.basedata;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.function.exception.ServiceBizException;

/**
* 配件索赔审核service
* @author ZhaoZ
* @date 2017年3月28日
*/
public interface TtRoRepairPartService {
	
	//查询维修领料
	public PageInfoDto checkTtRoRepairPart(Map<String, String> queryParams)throws  ServiceBizException;
	//
	
	//
	public Map<String, Object> findDetailByClaimId(BigDecimal claimId)throws  ServiceBizException;

}
