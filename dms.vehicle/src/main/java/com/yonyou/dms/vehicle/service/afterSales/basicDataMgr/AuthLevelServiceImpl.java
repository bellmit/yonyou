package com.yonyou.dms.vehicle.service.afterSales.basicDataMgr;

import java.util.Date;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.framework.domain.LoginInfoDto;
import com.yonyou.dms.framework.util.bean.ApplicationContextHelper;
import com.yonyou.dms.function.exception.ServiceBizException;
import com.yonyou.dms.vehicle.dao.afterSales.basicDataMgr.AuthLevelDao;
import com.yonyou.dms.vehicle.domains.DTO.afterSales.basicDataMgr.TtWrAuthlevelDTO;
import com.yonyou.dms.vehicle.domains.PO.afterSales.basicDataMgr.TtWrAuthlevelPO;

/**
 * 授权级别维护
 * @author Administrator
 *
 */
@Service
public class AuthLevelServiceImpl implements AuthLevelService{
	@Autowired
	AuthLevelDao  authLevelDao;

	/**
	 * 授权级别维护查询
	 */
	@Override
	public PageInfoDto AuthLevelQuery(Map<String, String> queryParam) {
		// TODO Auto-generated method stub
		return authLevelDao.AuthLevelQuery(queryParam);
	}

	

/**
 * 信息修改
 */
	@Override
	public void edit(Long id, TtWrAuthlevelDTO dto) {
	 	LoginInfoDto loginInfo = ApplicationContextHelper.getBeanByType(LoginInfoDto.class);
	 	TtWrAuthlevelPO ptPo = TtWrAuthlevelPO.findById(id);
	 	  if(dto.getLevelName()!=null){
		   ptPo.setString("LEVEL_NAME", dto.getLevelName());
		   ptPo.setLong("UPDATE_BY", loginInfo.getUserId());
		   ptPo.setDate("UPDATE_DATE", new Date());
		   ptPo.setInteger("VER",0);
		   ptPo.setInteger("IS_DEL",0);
		   ptPo.saveIt();
	 	  }else{
	 		  throw new ServiceBizException("授权顺序名称不能为空，请输入！"); 
	 	  }
	}

/**
 * 信息回显
 */
	@Override
	public TtWrAuthlevelPO getAuthLevelById(Long id) {
		return TtWrAuthlevelPO.findById(id);
	}
	

}
