package com.yonyou.dms.vehicle.service.oldPart;

import java.util.Map;

import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.vehicle.domains.DTO.oldPart.TmOldpartStorDTO;

public interface OldPartHouseMaintainService {

	PageInfoDto getQuerySql(Map<String, String> queryParam);

	Long addTmOldpartStor(TmOldpartStorDTO tcDto);

	void modifyTmOldpartStor(Long id, TmOldpartStorDTO tcDto);

}
