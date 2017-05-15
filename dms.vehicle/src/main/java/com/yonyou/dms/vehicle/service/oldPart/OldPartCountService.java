package com.yonyou.dms.vehicle.service.oldPart;

import java.util.List;
import java.util.Map;

import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.vehicle.domains.DTO.oldPart.TtOpOldpartDTO;

public interface OldPartCountService {

	PageInfoDto findCount(Map<String, String> queryParam);

	List<Map> findreturnAddr(Map<String, String> queryParam);

	PageInfoDto findCountById(Long id);

	void modifyTmOldpartStor(Long id, TtOpOldpartDTO tcDto);

}
