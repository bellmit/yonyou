package com.yonyou.dms.vehicle.service.activityManage;

import java.util.Map;

import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.function.exception.ServiceBizException;
import com.yonyou.dms.vehicle.domains.DTO.activityManage.ActivityManageDTO;

/**
* @author liujiming
* @date 2017年3月30日
*/
public interface ActivityDealerMaintainService {
	//活动经销商管理  查询
	public PageInfoDto getDealerManageQuery(Map<String,String> queryParam) throws ServiceBizException;
	//活动经销商管理   经销商列表查询
	public PageInfoDto getDealerEditQuery(Map<String,String> queryParam,Long activityId) throws ServiceBizException;
	//活动经销商管理   新增列表查询
	public PageInfoDto getDealerAddQuery(Map<String,String> queryParam,Long acvtivityIdTyd) throws ServiceBizException;

	//活动经销商管理   新增确定
	public void activityDealerAddSave(ActivityManageDTO amDto,Long acvtivityIdTyd) throws ServiceBizException;
	
	//活动经销商管理   删除
	public void activityDealerDelete(ActivityManageDTO amDto) throws ServiceBizException;

}




