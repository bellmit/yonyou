package com.yonyou.dms.part.service.basedata;

import java.util.List;
import java.util.Map;

import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.function.exception.ServiceBizException;

/**
 * 呆滞品交易历史查询
 * @author ZhaoZ
 *@date 2017年4月13日
 */
public interface PartObsoleteMaterialDeaHistoryService {

	//呆滞品交易历史查询
	public PageInfoDto findALLList(Map<String, String> queryParams)throws ServiceBizException;
	//呆滞品交易历史下载查询
	public List<Map> queryDownLoad(Map<String, String> queryParams)throws ServiceBizException;
	//大区
	public List<Map> getBigAreaList()throws ServiceBizException;
	//小区
	public List<Map> getSmallAreaList(Long bigArea)throws ServiceBizException;

}
