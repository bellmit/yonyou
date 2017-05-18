package com.yonyou.dms.manage.service.basedata.bulletin;

import java.util.List;
import java.util.Map;

import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.manage.domains.DTO.bulletin.BulletinTypeDTO;

public interface BulletinTypeService {

	PageInfoDto search(Map<String, String> param);

	Map<String, String> checkType(String typename);

	void addBulletinType(BulletinTypeDTO dto);

	BulletinTypeDTO editBulletinTypeInit(Long typeId);

	List<Map> editBulletinTypeUserInit(Long typeId);

	void editBulletinType(BulletinTypeDTO dto);

	PageInfoDto searchUser(Map<String, String> param);

	void deleteUser(Long userId, Long typeId);

}
