package com.yonyou.dms.manage.service.basedata.organization;

import java.util.Map;

import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.manage.domains.DTO.basedata.organization.CarCompanyDTO;

public interface CarCompanyMaintainService {

	PageInfoDto searchCompanyInfo(Map<String, String> param);

	void addCarCompany(CarCompanyDTO dto);

	CarCompanyDTO editCarCompanyInit(Long companyId);

	void editCarCompany(CarCompanyDTO dto);

}
