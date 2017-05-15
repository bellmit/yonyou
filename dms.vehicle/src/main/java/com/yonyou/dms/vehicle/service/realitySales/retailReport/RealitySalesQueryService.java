package com.yonyou.dms.vehicle.service.realitySales.retailReport;

import java.util.Map;

import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.framework.domain.LoginInfoDto;
import com.yonyou.dms.function.exception.ServiceBizException;
import com.yonyou.dms.vehicle.domains.DTO.dlrinvtyManage.TtVsNvdrDTO;

public interface RealitySalesQueryService {

	/**
	 * 零售上报查询
	 * @param queryParam
	 * @param loginInfo
	 * @return
	 * @throws ServiceBizException
	 */
	public PageInfoDto realitySalesQueryQuery(Map<String, String> queryParam, LoginInfoDto loginInfo)throws ServiceBizException;

	public Map<String, Object> queryDetail(String id, String vin, LoginInfoDto loginInfo) throws ServiceBizException;

	public void realitySalesReporting(TtVsNvdrDTO tvnDto, LoginInfoDto loginInfo)throws ServiceBizException;


}
