package com.yonyou.dms.manage.service.basedata.bulletin;

import java.text.ParseException;
import java.util.List;
import java.util.Map;

import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.manage.domains.DTO.bulletin.BulletinIssueDTO;

public interface BulletinMaintainService {

	PageInfoDto search(Map<String, String> param);

	List<Map> getAllBulletinType();

	Map editBulletinInit(Long bulletinId) throws ParseException;

	PageInfoDto getDealers(Map<String, String> param,Long bulletinId);

	PageInfoDto getFiles(Map<String, String> param,Long bulletinId);

	void editBulletin(BulletinIssueDTO dto);

	void deleteBulletin(Long bulletinId);

}
