package com.yonyou.dms.manage.service.basedata.bulletin;

import java.util.Map;

import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.manage.domains.DTO.bulletin.BulletinIssueDTO;

public interface BulletinIssueService {

	PageInfoDto search(Map<String, String> param);

	void addBulletinIssue(BulletinIssueDTO dto);

	String getTypeName(Long typeId);

}
