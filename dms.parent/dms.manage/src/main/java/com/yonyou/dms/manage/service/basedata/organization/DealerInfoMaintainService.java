package com.yonyou.dms.manage.service.basedata.organization;

import java.util.Map;

import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.manage.domains.DTO.basedata.organization.DealerInfoDTO;

public interface DealerInfoMaintainService {

	PageInfoDto searchDealerInfo(Map<String, String> param);

	PageInfoDto searchDealerDetail(String companyId);

	boolean addDealerInfo(DealerInfoDTO dto);

	DealerInfoDTO editDealerInfoQuery(Long companyId);

	void editDealerInfo(DealerInfoDTO dto);

}
