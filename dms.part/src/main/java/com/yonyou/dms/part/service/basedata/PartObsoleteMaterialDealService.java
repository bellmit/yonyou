package com.yonyou.dms.part.service.basedata;

import java.util.List;
import java.util.Map;

import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.function.exception.ServiceBizException;

/**
 * 呆滞品交易确认
 * @author ZhaoZ
 *@date 2017年4月12日
 */
public interface PartObsoleteMaterialDealService {
	
	//呆滞品交易查询
	public PageInfoDto queryPartObsoleteMaterial(Map<String, String> queryParams)throws ServiceBizException;
	//下载查询
	public List<Map> downloadInfo(Map<String, String> queryParams)throws ServiceBizException;

}
