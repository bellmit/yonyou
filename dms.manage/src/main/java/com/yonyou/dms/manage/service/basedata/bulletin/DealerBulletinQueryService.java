package com.yonyou.dms.manage.service.basedata.bulletin;

import java.util.Map;

import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.manage.domains.DTO.bulletin.BulletinReplyDTO;

public interface DealerBulletinQueryService {

	PageInfoDto search(Map<String, String> param);

	PageInfoDto searchDetail(Map<String, String> param);

	Map<String, Object> viewBulletin(Long bulletinId);

	boolean isSign(Long bulletinId, Integer isSign);

	void replyBulletin(BulletinReplyDTO dto);

}
