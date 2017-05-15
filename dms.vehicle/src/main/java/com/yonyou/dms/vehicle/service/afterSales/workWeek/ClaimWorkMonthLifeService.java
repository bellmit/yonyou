package com.yonyou.dms.vehicle.service.afterSales.workWeek;

import java.util.List;
import java.util.Map;

import com.yonyou.dms.function.domains.DTO.ImportResultDto;
import com.yonyou.dms.function.exception.ServiceBizException;
import com.yonyou.dms.vehicle.domains.DTO.activityManage.RecallActivityImportDTO;
import com.yonyou.dms.vehicle.domains.DTO.afterSales.workWeek.TmpWrClaimmonthDTO;

/**
 * 索赔工作越维护
 * @author Administrator
 *
 */
public interface ClaimWorkMonthLifeService {

	void deleteTmpRecallVehicleDcs();

	void saveTmpRecallVehicleDcs(TmpWrClaimmonthDTO rowDto);

	List<TmpWrClaimmonthDTO> checkData();
	//查询临时表里面的数据
	public List<Map> findTmpList() ;
	//删除临时表数据导入业务表
	public void saveAndDeleteData(Map<String, String> queryParam) throws ServiceBizException;
}
