package com.yonyou.dms.vehicle.service.dealerStorage.checkMaintain;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.framework.domain.LoginInfoDto;
import com.yonyou.dms.function.exception.ServiceBizException;
import com.yonyou.dms.vehicle.dao.dealerStorage.checkManagement.CheckMaintainDao;

@Service
public class CheckMaintainServiceImpl implements CheckMaintainService{
	
	@Autowired
	CheckMaintainDao dao;

	/**
	 * 加载查询
	 */
	@Override
	public PageInfoDto queryList(Map<String, String> queryParam,LoginInfoDto loginInfo) throws ServiceBizException {
		PageInfoDto pgInfo = dao.queryList(queryParam,loginInfo);
		return pgInfo;
	}

	/**
	 * 信息下载
	 */
	@Override
	public List<Map> findVehicleCheckSuccList(Map<String, String> queryParam,LoginInfoDto loginInfo) throws ServiceBizException {
		List<Map> list = dao.queryVehicleCheckForExport(queryParam,loginInfo);
		return list;
	}

	/***
	 * 根据ID获取详细信息
	 */
	@Override
	public Map<String, Object> queryDetail(String id,LoginInfoDto loginInfo,Long inspectionId) throws ServiceBizException {
		Map<String, Object> map = dao.queryDetail(id,loginInfo,inspectionId);
		return map;
	}

	/**
	 * 加载查询(经销商端)
	 */
	@Override
	public PageInfoDto queryDealerList(Map<String, String> queryParam, LoginInfoDto loginInfo)
			throws ServiceBizException {
		PageInfoDto pgInfo = dao.queryDealerList(queryParam,loginInfo);
		return pgInfo;
	}

	/**
	 * 根据ID获取详细信息(经销商端)
	 */
	@Override
	public Map<String, Object> queryDealerDetail(Long id, LoginInfoDto loginInfo) throws ServiceBizException {
		Map<String, Object> map = dao.queryDealerDetail(id,loginInfo);
		return map;
	}

	/**
	 * 信息下载(经销商端)
	 */
	@Override
	public List<Map> findDealerVehicleCheckSuccList(Map<String, String> queryParam, LoginInfoDto loginInfo)
			throws ServiceBizException {
		List<Map> list = dao.findDealerVehicleCheckSuccList(queryParam,loginInfo);
		return list;
	}

	/**
	 * 质量损坏信息
	 */
	@Override
	public PageInfoDto queryDealerDetail2(Long id) throws ServiceBizException {
		PageInfoDto map = dao.queryDealerDetail2(id);
		return map;
	}
	
	/**
	 * 质量损坏信息
	 */
	@Override
	public PageInfoDto queryDealerDetail3(Long id) throws ServiceBizException {
		PageInfoDto map = dao.queryDealerDetail2(id);
		return map;
	}
	
}
