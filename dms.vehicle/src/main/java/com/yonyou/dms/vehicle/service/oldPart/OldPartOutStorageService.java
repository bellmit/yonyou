package com.yonyou.dms.vehicle.service.oldPart;

import java.util.List;
import java.util.Map;

import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.vehicle.domains.DTO.oldPart.TtOpStoreDTO;

public interface OldPartOutStorageService {

	PageInfoDto findOutStorage(Map<String, String> queryParam);

	PageInfoDto findOutById(Long id);

	List<Map> queryEmpInfoforExport(Map<String, String> queryParam)throws Exception;

	void modifyTmOldpartStor(Long id, TtOpStoreDTO tcDto);

}
