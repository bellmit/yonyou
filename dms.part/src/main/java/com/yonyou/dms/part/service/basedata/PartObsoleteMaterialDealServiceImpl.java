package com.yonyou.dms.part.service.basedata;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.function.exception.ServiceBizException;
import com.yonyou.dms.part.dao.PartObsoleteMaterialDealDao;

/**
 * 呆滞品交易确认
 * @author ZhaoZ
 *@date 2017年4月12日
 */
@Service
public class PartObsoleteMaterialDealServiceImpl implements PartObsoleteMaterialDealService{

	@Autowired
	private  PartObsoleteMaterialDealDao partDao;
	
	/**
	 * 呆滞品交易查询
	 */
	@Override
	public PageInfoDto queryPartObsoleteMaterial(Map<String, String> queryParams) throws ServiceBizException {
		return partDao.getPartObsoleteMaterial(queryParams);
	}

	/**
	 * 下载查询
	 */
	@Override
	public List<Map> downloadInfo(Map<String, String> queryParams) throws ServiceBizException {
		return partDao.getdownloadList(queryParams);
	}

}
