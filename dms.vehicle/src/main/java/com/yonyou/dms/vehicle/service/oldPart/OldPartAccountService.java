package com.yonyou.dms.vehicle.service.oldPart;

import java.util.List;
import java.util.Map;

import com.yonyou.dms.framework.DAO.PageInfoDto;

public interface OldPartAccountService {

	PageInfoDto findOutStorage(Map<String, String> queryParam);

	List<Map> queryEmpInfoforExport(Map<String, String> queryParam)throws Exception ;

}
