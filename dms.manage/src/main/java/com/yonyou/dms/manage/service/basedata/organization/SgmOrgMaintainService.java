package com.yonyou.dms.manage.service.basedata.organization;

import java.util.Map;

import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.manage.domains.DTO.basedata.organization.SgmOrgDTO;

public interface SgmOrgMaintainService {

	PageInfoDto searchSgmOrg(Map<String, String> param);

	void addSgmOrg(SgmOrgDTO dto);

	SgmOrgDTO editSgmOrgInit(Long orgId);

	void editSgmOrg(SgmOrgDTO dto);

}
