package com.yonyou.dms.vehicle.service.threePack;

import java.util.Map;

import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.vehicle.domains.DTO.threePack.TtThreepackRepairDTO;

public interface ThreePackRepairAuditService {
	// 三包维修方案工时查询
	public PageInfoDto  threePackLabourList(Long id);
	//维修方案审核基本信息
	public Map<String,Object> threePackAuditInfo(Long id);
	//查询
	public PageInfoDto findAudit(Map<String, String> queryParam);
	//三包维修方案配件信息查询
	public PageInfoDto  threePackPartList(Long id);
	//查询车辆累计维修天数
	public Map<String ,Object> find(String vin);
	//审核
	public void modifyNopart(Long id, TtThreepackRepairDTO tcDto);
}
