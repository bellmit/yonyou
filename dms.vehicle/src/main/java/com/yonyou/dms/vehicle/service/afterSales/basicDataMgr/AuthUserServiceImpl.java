package com.yonyou.dms.vehicle.service.afterSales.basicDataMgr;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yonyou.dms.common.domains.PO.basedata.TcUserPO;
import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.framework.domain.LoginInfoDto;
import com.yonyou.dms.framework.util.bean.ApplicationContextHelper;
import com.yonyou.dms.function.exception.ServiceBizException;
import com.yonyou.dms.manage.domains.DTO.personInfoManager.TcUserDTO;
import com.yonyou.dms.vehicle.dao.afterSales.basicDataMgr.AuthUserDao;

/**
 * 授权人员管理
 * @author Administrator
 *
 */
@Service
public class AuthUserServiceImpl implements AuthUserService{
	@Autowired
	AuthUserDao  authUserDao;
/**
 * 查询
 */
	@Override
	public PageInfoDto AuthUserQuery(Map<String, String> queryParam) {
		// TODO Auto-generated method stub
		return authUserDao.AuthUserQuery(queryParam);
	}

	/**
	 * 查询所有授权顺序
	 */
	@Override
	public List<Map> getAuthLevel(Map<String, String> queryParams) throws ServiceBizException {
		// TODO Auto-generated method stub
		return authUserDao.getAuthLevel(queryParams);
	}
/**
 * 回显信息
 */
	@Override
	public TcUserPO getAuthLevelById(Long id) {
		// TODO Auto-generated method stub
		return TcUserPO.findById(id);
	}

	/**
	 * 修改信息
	 */
	@Override
	public void edit(Long id, TcUserDTO dto) {
		LoginInfoDto loginInfo = ApplicationContextHelper.getBeanByType(LoginInfoDto.class);
		TcUserPO ptPo = TcUserPO.findById(id);
		   ptPo.setInteger("APPROVAL_LEVEL_CODE",dto.getApprovalLevelCode());
		   ptPo.setLong("UPDATE_BY", loginInfo.getUserId());
		   ptPo.setDate("UPDATE_DATE", new Date());
		   ptPo.saveIt();
		
	}



}
