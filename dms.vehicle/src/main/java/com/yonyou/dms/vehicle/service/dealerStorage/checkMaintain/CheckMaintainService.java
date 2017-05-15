package com.yonyou.dms.vehicle.service.dealerStorage.checkMaintain;

import java.util.List;
import java.util.Map;

import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.framework.domain.LoginInfoDto;
import com.yonyou.dms.function.exception.ServiceBizException;

public interface CheckMaintainService {

	/**
	 * 车辆详细验收查询(车厂端)
	 * @param queryParam
	 * @param loginInfo
	 * @return
	 * @throws ServiceBizException
	 */
	public PageInfoDto queryList(Map<String, String> queryParam,LoginInfoDto loginInfo) throws ServiceBizException;

	/**
	 * 车辆验收信息下载(车厂的端)
	 * @param queryParam
	 * @param loginInfo
	 * @return
	 * @throws ServiceBizException
	 */
	public List<Map> findVehicleCheckSuccList(Map<String, String> queryParam,LoginInfoDto loginInfo)throws ServiceBizException;

	/**
	 * 根据ID 获取车辆验收详细信息(车厂端)
	 * @param id
	 * @param loginInfo
	 * @param inspectionId
	 * @return
	 * @throws ServiceBizException
	 */
	public Map<String, Object> queryDetail(String id,LoginInfoDto loginInfo,Long inspectionId) throws ServiceBizException;

	/**
	 * 车辆详细验收(经销商端)
	 * @param queryParam
	 * @param loginInfo
	 * @return
	 * @throws ServiceBizException
	 */
	public PageInfoDto queryDealerList(Map<String, String> queryParam, LoginInfoDto loginInfo)throws ServiceBizException;

	/**
	 * 根据ID 获取车辆验收详细信息的质损信息(经销商端)
	 * @param id
	 * @param loginInfo
	 * @return
	 * @throws ServiceBizException
	 */
	public Map<String, Object> queryDealerDetail(Long id, LoginInfoDto loginInfo)throws ServiceBizException;
	
	/**
	 * 经销商段 质量损坏信息
	 * @param id
	 * @return
	 * @throws ServiceBizException
	 */
	public PageInfoDto queryDealerDetail2(Long id)throws ServiceBizException;
	
	/**
	 * 车厂端质量损坏信息
	 * @param id
	 * @return
	 * @throws ServiceBizException
	 */
	public PageInfoDto queryDealerDetail3(Long id)throws ServiceBizException;

	/**
	 * 经销商端车辆验收信息下载
	 * @param queryParam
	 * @param loginInfo
	 * @return
	 * @throws ServiceBizException
	 */
	public List<Map> findDealerVehicleCheckSuccList(Map<String, String> queryParam, LoginInfoDto loginInfo)throws ServiceBizException;

}
