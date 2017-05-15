package com.yonyou.dms.web.service.basedata.authority;

import java.util.Date;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yonyou.dms.common.domains.PO.basedata.SysUserPosePO;
import com.yonyou.dms.common.domains.PO.basedata.TcUserPO;
import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.framework.domain.LoginInfoDto;
import com.yonyou.dms.framework.util.bean.ApplicationContextHelper;
import com.yonyou.dms.function.common.OemDictCodeConstants;
import com.yonyou.dms.function.exception.ServiceBizException;
import com.yonyou.dms.function.utils.common.StringUtils;
import com.yonyou.dms.function.utils.security.MD5Util;
import com.yonyou.dms.web.dao.authority.DealerUserDao;
import com.yonyou.dms.web.domains.DTO.basedata.authority.SysUserDTO;

@Service
public class DealerUserServiceImpl implements DealerUserService {
	
	@Autowired
	private DealerUserDao dao;

	@Override
	public PageInfoDto queryList(Map<String, String> queryParam) throws ServiceBizException {
		
		return dao.queryList(queryParam);
	}

	@Override
	public PageInfoDto getPoseList(Map<String, String> queryParam) throws ServiceBizException {
		return dao.getPoseList(queryParam);
	}

	@Override
	public Long addDealerUser(SysUserDTO userDto) throws ServiceBizException {
		LoginInfoDto loginInfo = ApplicationContextHelper.getBeanByType(LoginInfoDto.class);
		TcUserPO userPO = new TcUserPO();
		
		setPO(userPO,userDto,1);
		userPO.saveIt();
		Long billId = userPO.getLongId();
		if(null!=userDto.getAddIds() && userDto.getAddIds().length()>0){
			String[] ids = userDto.getAddIds().split(",");
			for (int i = 0; i < ids.length; i++) {
				SysUserPosePO supPo = new SysUserPosePO();
				supPo.setLong("POSE_ID", ids[i]);
				supPo.setLong("USER_ID", billId);
				supPo.setInteger("CREATE_BY", loginInfo.getUserId());
				supPo.setTimestamp("CREATE_DATE", new Date());
				supPo.saveIt();
			}
		}
		return billId;
	}
	
	/**
	 * 用户数据映射
	 * @param mgPO
	 * @param mgDto
	 */
	private void setPO(TcUserPO userPO, SysUserDTO userDto,int type) {
		LoginInfoDto loginInfo = ApplicationContextHelper.getBeanByType(LoginInfoDto.class);
		userPO.setString("ACNT", userDto.getAcnt());
		userPO.setString("NAME", userDto.getName());
		userPO.setInteger("GENDER", userDto.getGender());
		userPO.setString("HAND_PHONE", userDto.getHandPhone());
		userPO.setString("PHONE", userDto.getPhone());
		userPO.setString("EMAIL", userDto.getEmail());
		if(!StringUtils.isNullOrEmpty(userDto.getPassword())){
			userPO.setString("PASSWORD", MD5Util.getEncryptedPwd(userDto.getPassword()));
		}
		userPO.setInteger("USER_TYPE", OemDictCodeConstants.SYS_USER_DEALER);
		userPO.setInteger("USER_STATUS", userDto.getUserStatus());
		if(type==1){
			userPO.setLong("COMPANY_ID", userDto.getCompanyId());
			userPO.setInteger("CREATE_BY", loginInfo.getUserId());
			userPO.setTimestamp("CREATE_DATE", new Date());
		}else{
			userPO.setInteger("UPDATE_BY", loginInfo.getUserId());
			userPO.setTimestamp("UPDATE_DATE", new Date());
		}
	}
	
	/**
	 * 修改经销商用户信息
	 */
	@Override
	public void modifyDealerUser(Long id, SysUserDTO userDto) throws ServiceBizException {
		LoginInfoDto loginInfo = ApplicationContextHelper.getBeanByType(LoginInfoDto.class);
		TcUserPO userPO = TcUserPO.findById(id);
		setPO(userPO,userDto,2);
		userPO.saveIt();
		if(null!=userDto.getAddIds() && userDto.getAddIds().length()>0){
			SysUserPosePO.delete(" USER_ID = ? ", id);
			String[] ids = userDto.getAddIds().split(",");
			for (int i = 0; i < ids.length; i++) {
				SysUserPosePO supPo = new SysUserPosePO();
				supPo.setLong("POSE_ID", ids[i]);
				supPo.setLong("USER_ID", id);
				supPo.setInteger("CREATE_BY", loginInfo.getUserId());
				supPo.setTimestamp("CREATE_DATE", new Date());
				supPo.saveIt();
			}
		}
	}
}
