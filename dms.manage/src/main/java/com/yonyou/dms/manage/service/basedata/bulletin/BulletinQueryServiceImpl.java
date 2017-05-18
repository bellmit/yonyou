package com.yonyou.dms.manage.service.basedata.bulletin;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.manage.dao.bulletin.BulletinQueryDao;
import com.yonyou.f4.mvc.annotation.TxnConn;

@TxnConn
@Service
public class BulletinQueryServiceImpl implements BulletinQueryService {
	
	@Autowired
	private BulletinQueryDao dao;

	@Override
	public PageInfoDto search(Map<String, String> param) {
		PageInfoDto dto = dao.search(param);
		return dto;
	}

	@Override
	public PageInfoDto searchDetail(Map<String, String> param) {
		PageInfoDto dto = dao.searchDetail(param);
		return dto;
	}

	@Override
	public Map viewBulletin(Long bulletinId) {
		Map map = dao.viewBulletin(bulletinId);
		return map;
	}

	@Override
	public PageInfoDto getNotReadList(Map<String,String> param,Long bulletinId) {
		PageInfoDto dto = dao.getNotReadList(param,bulletinId);
		return dto;
	}

	@Override
	public PageInfoDto getReadList(Map<String, String> param, Long bulletinId) {
		PageInfoDto dto = dao.getReadList(param,bulletinId);
		return dto;
	}

	@Override
	public PageInfoDto getSignList(Map<String, String> param, Long bulletinId) {
		PageInfoDto dto = dao.getSignList(param,bulletinId);
		return dto;
	}

	@Override
	public PageInfoDto getNotSignList(Map<String, String> param, Long bulletinId) {
		PageInfoDto dto = dao.getNotSignList(param,bulletinId);
		return dto;
	}
	

}
