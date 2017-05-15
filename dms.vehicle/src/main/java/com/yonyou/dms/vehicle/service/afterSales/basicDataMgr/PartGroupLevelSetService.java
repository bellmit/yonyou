package com.yonyou.dms.vehicle.service.afterSales.basicDataMgr;

import java.util.List;
import java.util.Map;

import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.function.domains.DTO.ImportResultDto;
import com.yonyou.dms.vehicle.domains.DTO.afterSales.basicDataMgr.TtPartGroupLevelSetTempDTO;

/**
 * 配件分组级别设定
 * @author Administrator
 *
 */
public interface PartGroupLevelSetService {
	//分组级别设定查询
	public PageInfoDto  LevelSetQuery(Map<String, String> queryParam);
    //导入业务表，删除临时表数据
	public void importSaveAndDelete();
   //查询临时表的所有数据
	public List<Map> findTmpPartGroupLevelSetList(Map<String, String> queryParam);
	public void insertTmpRecallVehicleDcs(TtPartGroupLevelSetTempDTO rowDto);
	ImportResultDto<TtPartGroupLevelSetTempDTO> checkData(TtPartGroupLevelSetTempDTO list) throws Exception;
}