package com.yonyou.dms.vehicle.service.oldPart;

import java.util.List;
import java.util.Map;

import com.yonyou.dms.framework.DAO.PageInfoDto;

public interface OldPartInventoryService {

	PageInfoDto findOutStorage(Map<String, String> queryParam);

	PageInfoDto findOne(String oldpartNo)throws Exception;

	List<Map> queryEmpInfoforExport(Map<String, String> queryParam)throws Exception;

	List<Map> queryEmpInfoforExportMX(String oldpartNo)throws Exception;

}
