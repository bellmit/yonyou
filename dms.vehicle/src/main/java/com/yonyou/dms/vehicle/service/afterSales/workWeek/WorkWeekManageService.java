package com.yonyou.dms.vehicle.service.afterSales.workWeek;

import java.util.List;
import java.util.Map;

import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.vehicle.domains.DTO.afterSales.workWeek.TmpWeekDTO;

/**
 * 工作周查询
 * @author Administrator
 *
 */
public interface WorkWeekManageService {
	//周查询
	public PageInfoDto WorkWeekManageQuery(Map<String, String> queryParam);

	//删除临时表数据
	public void deleteTmpRecallVehicleDcs();

	//插入临时表数据
	public void saveTmpRecallVehicleDcs(TmpWeekDTO rowDto);

	//检查临时表数据
	public List<TmpWeekDTO> checkData();

	//查询临时表数据
	public List<Map> findTmpList();

	//导入业务表数据
	public void saveAndDeleteData(Map<String, String> queryParam);

}
