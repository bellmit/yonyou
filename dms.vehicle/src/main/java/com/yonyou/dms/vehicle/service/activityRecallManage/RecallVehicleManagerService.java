package com.yonyou.dms.vehicle.service.activityRecallManage;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.function.exception.ServiceBizException;
import com.yonyou.dms.vehicle.domains.DTO.activityManage.RecallServiceActDTO;
import com.yonyou.dms.vehicle.domains.DTO.activityManage.TmpRecallVehicleDcsDTO;

/**
* @author liujiming
* @date 2017年4月13日
*/
public interface RecallVehicleManagerService {
	//召回车辆管理 查询
	public PageInfoDto recallVehicleManagerQuery(Map<String,String> queryParam) throws ServiceBizException;
	//召回车辆管理下载
	public void getRecallInitQueryDownload(Map<String, String> queryParam, HttpServletRequest request,
			HttpServletResponse response) throws ServiceBizException;
	//修改、明细 回显信息
	public Map recallDutyDealerInit(Long id) throws ServiceBizException;
	//修改责任经销商
	public void recallDealerUpdate(RecallServiceActDTO rsDto,Long id) throws ServiceBizException;
	//批量修改责任经销商
	public void recallDealersUpdate(RecallServiceActDTO rsDto) throws ServiceBizException;
	
	// 检查临时表数据
	public List<TmpRecallVehicleDcsDTO>  checkData() throws ServiceBizException;
	
	// 向临时表插入数据
	public void insertTmpRecallVehicleDcs(TmpRecallVehicleDcsDTO rowDto) throws ServiceBizException;
	//查询临时表数据
	public List<Map> findTmpRecallVehicleDcsList(Map<String,String> queryParam) throws ServiceBizException;
	//
	public void importSaveAndDelete() throws ServiceBizException;
	
}
