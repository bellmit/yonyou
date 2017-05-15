package com.yonyou.dms.vehicle.service.threePack;

import java.util.List;
import java.util.Map;

import com.yonyou.dms.framework.DAO.PageInfoDto;

public interface ThreePackRepairAuditQueryService {
	//分页查询
		public PageInfoDto findthreePack(Map<String, String> queryParam) throws Exception ;

		public PageInfoDto queryList(Long id);

		public PageInfoDto queryInfo(Long id);

		public PageInfoDto queryLabourList(Long id);
		
		public List<Map> query(Long id);
}
