package com.yonyou.dms.part.service.basedata;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.function.exception.ServiceBizException;
import com.yonyou.dms.part.dao.PartAllotAaDao;

/**
* 配件调拨单查询service
* @author ZhaoZ
* @date 2017年3月30日
*/
@Service
public class PartAllotServiceAaImpl implements PartAllotAaService{

	@Autowired
	private  PartAllotAaDao partDao;
	/**
	 * 配件调拨单查询
	 */
	@Override
	public PageInfoDto queryAllot(Map<String, String> queryParams) throws ServiceBizException {
		return partDao.getAllotInfo(queryParams);
	}
	/**
	 * 配件调拨单查询
	 */
	@Override
	public List<Map> queryDownLoad(Map<String, String> queryParams) {
		return partDao.getDownLoadInfo(queryParams);
	}
	/**
	 * 配件调拨单查询明细
	 */
	@Override
	public PageInfoDto queryAllotOutDeInfo(BigDecimal id) throws ServiceBizException {
		return partDao.allotOutDeInfo(id);
	}
	/**
	 * 配件调拨单明细查询
	 */
	@Override
	public PageInfoDto partAllotDetailInfo(Map<String, String> queryParams) throws ServiceBizException {
		return partDao.getAllotDetailInfo(queryParams);
	}
	/**
	 * 配件调拨单明细下载查询
	 */
	@Override
	public List<Map> querydownAllotDetailInfo(Map<String, String> queryParams) throws ServiceBizException {
		return partDao.getdownAllotDetailInfo(queryParams);
	}
	
}
