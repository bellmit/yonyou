package com.yonyou.dms.vehicle.service.activityManage;

import java.sql.Timestamp;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.function.common.OemDictCodeConstants;
import com.yonyou.dms.function.exception.ServiceBizException;
import com.yonyou.dms.vehicle.dao.activityManage.ActivityIssueMaintainDao;
import com.yonyou.dms.vehicle.domains.PO.ActivityManage.TtWrActivityPO;

/**
* @author liujiming
* @date 2017年3月31日
*/
@Service
public class ActivityIssueMaintainServiceImpl implements ActivityIssueMaintainService{
	
	
	@Autowired
	private ActivityIssueMaintainDao aimDao;
	
	
	/**
	 * 服务活动发布 查询
	 */
	@Override
	public PageInfoDto getActivityIssueQuery(Map<String, String> queryParam) throws ServiceBizException {
		PageInfoDto pageInfoDto = aimDao.getActivityIssueQueryList(queryParam);
		return pageInfoDto;
	}

	/**
	 * 服务活动发布      经销商 查询
	 */
	@Override
	public PageInfoDto getActivityIssueQueryDealer(Map<String, String> queryParam,Long activityId) throws ServiceBizException {
		PageInfoDto pageInfoDto = aimDao.getDealerQuery(queryParam, activityId);
		return pageInfoDto;
	}
	/**
	 * 服务活动   发布 
	 */
	@Override
	public void activityIssue(Long activityId) throws ServiceBizException {
		TtWrActivityPO  savePo = TtWrActivityPO.findById(activityId);		
			savePo.set("STATUS", OemDictCodeConstants.ACTIVITY_STATUS_02);
			savePo.setTimestamp("PUBLISH_DATE", new Timestamp(System.currentTimeMillis()));
			//savePo.saveIt();
		
		
		
		//老系统 接口代码
//		TtWrActivityPO selPo =new TtWrActivityPO();
//		selPo.setActivityId(Long.parseLong(activityId));
//		TtWrActivityPO valuePo =new TtWrActivityPO();
//		String status = String.valueOf(Constant.ACTIVITY_STATUS_02); //ACTIVITY_STATUS_02 = 40171002; 设置为已发布
//		valuePo.setStatus(Integer.parseInt(status));
//		valuePo.setPublishDate(new Date());
//		SEDCS005 osc = new SEDCS005();
//		osc.execute(selPo);
//		/** 发布服务活动方法*/
//		dao.activityIssue(selPo,valuePo);
//		act.setOutData("returnValue", "true");
		
		
		
		
		
	}
	/**
	 * 服务活动   取消发布 
	 */
	@Override
	public void activityCancleIssue(Long activityId) throws ServiceBizException {
		TtWrActivityPO  savePo = TtWrActivityPO.findById(activityId);		
		savePo.set("STATUS", OemDictCodeConstants.ACTIVITY_STATUS_03);
		savePo.setTimestamp("PUBLISH_DATE", new Timestamp(System.currentTimeMillis()));
		//savePo.saveIt();
		
		//老系统 接口代码
//		TtWrActivityPO selPo =new TtWrActivityPO();
//		selPo.setActivityId(Long.parseLong(activityId));
//		TtWrActivityPO valuePo =new TtWrActivityPO();
//		String status = String.valueOf(Constant.ACTIVITY_STATUS_03); //ACTIVITY_STATUS_03 = 40171003; //发布取消
//		valuePo.setStatus(Integer.parseInt(status));
//		SEDCS005 osc = new SEDCS005();
//		osc.executeCancel(selPo);
//		*//** 发布取消方法*//*
//		dao.activityCancelIssue(selPo,valuePo);
//		act.setOutData("returnValue", "true");
		
	}

}
