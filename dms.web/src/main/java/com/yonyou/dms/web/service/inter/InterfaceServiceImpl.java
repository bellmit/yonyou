package com.yonyou.dms.web.service.inter;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.function.exception.ServiceBizException;
import com.yonyou.dms.web.dao.inter.InterfaceDao;
import com.yonyou.dms.web.domains.PO.inter.DeMsgInfoPO;
@Service
public class InterfaceServiceImpl implements InterfaceService{
	@Autowired
	InterfaceDao dao;

	@Override
	public PageInfoDto getQuerySql(Map<String, String> queryParam) {
		
		return dao.getInterfaceMsgList(queryParam);
	}

	@Override
	public void deleteUserById(Long id) throws ServiceBizException {
	DeMsgInfoPO po =DeMsgInfoPO.findById(id);
	po.deleteCascadeShallow();
		
	}


}
