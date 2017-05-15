package com.yonyou.dms.vehicle.service.activityManage;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.function.exception.ServiceBizException;
import com.yonyou.dms.vehicle.dao.activityManage.ActivityStatusMaintainDao;

/**
* @author liujiming
* @date 2017年3月31日
*/
@Service
public class ActivityStatusMaintainServiceImpl implements ActivityStatusMaintainService{
	
	@Autowired
	private ActivityStatusMaintainDao  asmDao;
	
	
	
	
	
	/**
	 * 车辆活动状态查询
	 */
	@Override
	public PageInfoDto activityStatusQuery(Map<String, String> queryParam) throws ServiceBizException {
		PageInfoDto pageInfoDto = asmDao.getActivityStatusQuery(queryParam);
		return pageInfoDto;
	}




	/**
	 * 车辆信息
	 */
	@Override
	public Map getDetailCarMsgQuery(String VIN, Long activityId) throws ServiceBizException {
		Map map = new HashMap<>();
		List<Map> list = asmDao.getDetailCarMsgQuery(VIN, activityId);
		if(list != null && list.size() >0){
			for(int i=0; i<list.size();i++){
				map = list.get(i);
			}
		}
		return map;
	}




	/**
	 * 车主信息
	 */
	@Override
	public Map getDetailGTMMsgQuery(Long ctmId) throws ServiceBizException {
		Map map = new HashMap<>();
		List<Map> list = asmDao.getDetailGTMMsgQuery(ctmId);
		if(list != null && list.size() >0){
			for(int i=0; i<list.size();i++){
				map = list.get(i);
			}
		}
		return map;
	}




	/**
	 * 联系人信息
	 */
	@Override
	public Map getDetailLinkManMsgQuery(String lmId) throws ServiceBizException {
			Long id = (long) 0;
		if(lmId != null && ! "".equals(lmId) && ! "{[LM_ID]}".equals(lmId)){
			 id = Long.parseLong(lmId);
		}
		   
		
		
		Map map = new HashMap<>();
		List<Map> list = asmDao.getDetailLinkManMsgQuery(id);
		if(list != null && list.size() >0){
			for(int i=0; i<list.size();i++){
				map = list.get(i);
			}
		}
		return map;
	}

}
