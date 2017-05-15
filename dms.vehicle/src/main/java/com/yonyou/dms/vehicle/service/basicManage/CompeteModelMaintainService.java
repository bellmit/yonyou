package com.yonyou.dms.vehicle.service.basicManage;

import java.util.Map;

import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.function.exception.ServiceBizException;
import com.yonyou.dms.vehicle.domains.DTO.basicManage.TmCompeteBrandDTO;

public interface CompeteModelMaintainService {

	/**
	 * 查询
	 * @param queryParam
	 * @param loginInfo
	 * @return
	 * @throws ServiceBizException
	 */
	public PageInfoDto queryList(Map<String, String> queryParam)throws ServiceBizException ;

	/**
	 * 新增
	 * @param mgDto
	 * @return
	 * @throws ServiceBizException
	 */
	public Long addCompeteModelMaintain(TmCompeteBrandDTO mgDto) throws ServiceBizException;

	/**
	 * 修改
	 * @param id
	 * @param mgDto
	 * @throws ServiceBizException
	 */
	public void ModifyCompeteModelMaintain(Long id, TmCompeteBrandDTO mgDto) throws ServiceBizException;

	/**
	 * 根据ID获取信息
	 * @param id
	 * @return
	 * @throws ServiceBizException
	 */
	public Map<String, Object> queryDetail(Long id)throws ServiceBizException;
	
}
