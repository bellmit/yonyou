package com.yonyou.dms.vehicle.service.activityManage;

import java.sql.Date;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.framework.domain.LoginInfoDto;
import com.yonyou.dms.framework.util.bean.ApplicationContextHelper;
import com.yonyou.dms.function.exception.ServiceBizException;
import com.yonyou.dms.vehicle.dao.activityManage.ActivityDealerMaintainDao;
import com.yonyou.dms.vehicle.domains.DTO.activityManage.ActivityManageDTO;
import com.yonyou.dms.vehicle.domains.PO.ActivityManage.TtWrActivityDealerDcsPO;

/**
* @author liujiming
* @date 2017年3月30日
*/
@Service
public class ActivityDealerMaintainServiceImpl implements ActivityDealerMaintainService{
	
	@Autowired
	private ActivityDealerMaintainDao admDao;
	
	/**
	 * 
	 */
	@Override
	public PageInfoDto getDealerManageQuery(Map<String, String> queryParam) throws ServiceBizException {
		PageInfoDto pageInfoDto = admDao.getDealerManageQuery(queryParam);
		return pageInfoDto;
	}

	@Override
	public PageInfoDto getDealerEditQuery(Map<String, String> queryParam,Long activityId) throws ServiceBizException {
		PageInfoDto pageInfoDto = admDao.getDealerEditQuery(queryParam, activityId);
		return pageInfoDto;
	}
	/**
	 * 新增页面  经销商查询
	 */
	@Override
	public PageInfoDto getDealerAddQuery(Map<String, String> queryParam,Long acvtivityIdTyd) throws ServiceBizException {
		PageInfoDto pageInfoDto = admDao.getDealerAddQuery(queryParam,acvtivityIdTyd);
		return pageInfoDto;
	}
	/**
	 * 新增 确定
	 */
	@Override
	public void activityDealerAddSave(ActivityManageDTO amDto, Long acvtivityIdTyd) throws ServiceBizException {
		//获取当前用户
				LoginInfoDto loginInfo = ApplicationContextHelper.getBeanByType(LoginInfoDto.class);				
				
				String[] strArray = amDto.getGroupIds().split(",");
				for(int i=0;i<strArray.length;i++){
					String[] dealerArray =  strArray[i].split("#");
					TtWrActivityDealerDcsPO savePo = new TtWrActivityDealerDcsPO();
					savePo.set("ACTIVITY_ID", acvtivityIdTyd);
					savePo.set("DEALER_CODE", dealerArray[0]);
					savePo.set("DEALER_ID", dealerArray[1]);
					savePo.set("DEALER_NAME", dealerArray[2]);
					savePo.set("CREATE_BY", loginInfo.getUserId());
					savePo.setTimestamp("UPDATE_DATE", new Date(System.currentTimeMillis()));
					savePo.insert();
				}
		
	}
	/**
	 * 批量删除
	 */
	@Override
	public void activityDealerDelete(ActivityManageDTO amDto) throws ServiceBizException {
		String[] strArray = amDto.getGroupIds().split(",");
		for(int i=0;i<strArray.length;i++){
			Long id = Long.parseLong(strArray[i].toString());
			TtWrActivityDealerDcsPO deletePo = new TtWrActivityDealerDcsPO();
			deletePo.delete("    ID = ?  ", id);
		}
		
	}

	
	
	
}



