package com.yonyou.dms.vehicle.service.oldPart;

import java.util.Map;

import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.vehicle.domains.DTO.oldPart.TmOldpartReturnaddrDTO;

public interface OldPartReturnAddrService {

	void modifyTmOldpartStor(Long id, TmOldpartReturnaddrDTO tcDto);

	Long addTmOldpartStor(TmOldpartReturnaddrDTO tcDto);

	PageInfoDto getQuerySql(Map<String, String> queryParam);



}
