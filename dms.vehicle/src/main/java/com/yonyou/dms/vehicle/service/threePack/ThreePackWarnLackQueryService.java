package com.yonyou.dms.vehicle.service.threePack;

import java.util.List;
import java.util.Map;

import com.yonyou.dms.framework.DAO.PageInfoDto;

public interface ThreePackWarnLackQueryService {
	public PageInfoDto getTmRetaillist(Map<String, String> queryParam) throws Exception ;
	
	public List<Map> queryEmpInfoforExport(Map<String, String> queryParam) throws Exception;
}
