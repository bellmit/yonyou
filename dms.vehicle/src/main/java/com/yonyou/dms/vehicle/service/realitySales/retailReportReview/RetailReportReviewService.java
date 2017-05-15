package com.yonyou.dms.vehicle.service.realitySales.retailReportReview;

import java.util.List;
import java.util.Map;

import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.framework.domain.LoginInfoDto;
import com.yonyou.dms.function.exception.ServiceBizException;

public interface RetailReportReviewService {

	/**
	 * 零售审核查询
	 * @param queryParam
	 * @param loginInfo
	 * @return
	 * @throws ServiceBizException
	 */
	public PageInfoDto retailReportReviewQuery(Map<String, String> queryParam, LoginInfoDto loginInfo)throws ServiceBizException;

	/**
	 * 零售审核下载
	 * @param queryParam
	 * @param loginInfo
	 * @return
	 * @throws ServiceBizException
	 */
	public List<Map> retailReportReviewQuerySuccList(Map<String, String> queryParam, LoginInfoDto loginInfo)throws ServiceBizException;

	/**
	 * 零售详细查询
	 * @param id
	 * @return
	 * @throws ServiceBizException
	 */
	public Map<String, Object> queryDetail(Long id)throws ServiceBizException;

	/**
	 * 批量审核
	 * @param rrrpDto
	 * @param loginInfo 
	 * @throws ServiceBizException
	 */
	public void batchPass(String nvdrId, LoginInfoDto loginInfo)throws ServiceBizException;

}
