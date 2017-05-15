package com.yonyou.dms.part.service.basedata;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.function.exception.ServiceBizException;
import com.yonyou.dms.part.dao.PartObsoleteMaterialDeaHistoryDao;


/**
 * 呆滞品交易历史查询
 * @author ZhaoZ
 *@date 2017年3月22日
 */
@SuppressWarnings("rawtypes")
@Service
public class PartObsoleteMaterialDeaHistoryServiceImpl implements PartObsoleteMaterialDeaHistoryService{

	@Autowired
	private  PartObsoleteMaterialDeaHistoryDao partDao;

	/**
	 * 呆滞品交易历史查询
	 */
	@Override
	public PageInfoDto findALLList(Map<String, String> queryParams) throws ServiceBizException {
		return partDao.allList(queryParams);
	}

	/**
	 * 呆滞品交易历史下载查询
	 */
	@Override
	public List<Map> queryDownLoad(Map<String, String> queryParams) throws ServiceBizException {
		return partDao.queryDownLoadList(queryParams);
	}

	//售后大区
	@Override
	public List<Map> getBigAreaList() throws ServiceBizException {
		return partDao.bigAreaList();
	}

	/**
	 * 售后小区
	 */
	@Override
	public List<Map> getSmallAreaList(Long bigArea) throws ServiceBizException {
		List<Map> list = new ArrayList<>();
		
		Map<String,Object> map = partDao.smallAreaList(bigArea);
		String ids =  (String) map.get("SMALL_AREA_ID");
		String names =  (String) map.get("SMALL_AREA_NAME");
		String[] smallID = ids.split(",");
		String[] smallName = names.split(",");
		for(int i=0;i<smallID.length;i++){
			Map<String,Object> small = new HashMap<>();
			small.put("SMALL_AREA_ID", smallID[i]);
			small.put("SMALL_AREA_NAME", smallName[i]);
			list.add(small);
		}
			return list;
	}
	
}
