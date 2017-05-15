package com.yonyou.dms.manage.service.basedata.organization;

import java.util.Map;

import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.manage.domains.DTO.basedata.organization.DealerGroupDTO;

public interface DealerGroupMaintainService {

	PageInfoDto search(Map<String, String> param);

	void addDealerGroup(DealerGroupDTO dto);

	DealerGroupDTO editDealerGroupInit(String groupId);

	void editDealerGroup(DealerGroupDTO dto);

}
