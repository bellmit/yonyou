package com.yonyou.dms.manage.service.basedata.bulletin;

import java.util.Map;

import com.yonyou.dms.framework.DAO.PageInfoDto;

public interface BulletinQueryService {

	PageInfoDto search(Map<String, String> param);

	PageInfoDto searchDetail(Map<String, String> param);

	Map viewBulletin(Long bulletinId);

	PageInfoDto getNotReadList(Map<String, String> param, Long bulletinId);

	PageInfoDto getReadList(Map<String, String> param, Long bulletinId);

	PageInfoDto getSignList(Map<String, String> param, Long bulletinId);

	PageInfoDto getNotSignList(Map<String, String> param, Long bulletinId);

}
