package com.yonyou.dms.retail.service.basedata;

import java.util.List;
import java.util.Map;

import com.yonyou.dms.framework.DAO.PageInfoDto;

public interface TmRetailSumService {
public PageInfoDto getTmRetaillist(Map<String, String> queryParam) throws Exception ;
	
	public List<Map> queryEmpInfoforExport(Map<String, String> queryParam) throws Exception;


}
