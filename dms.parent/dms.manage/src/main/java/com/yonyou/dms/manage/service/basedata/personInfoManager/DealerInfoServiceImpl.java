package com.yonyou.dms.manage.service.basedata.personInfoManager;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yonyou.dms.common.domains.PO.basedata.TcUserPO;
import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.framework.domain.LoginInfoDto;
import com.yonyou.dms.manage.dao.personInfoManager.DealerInfoDao;
import com.yonyou.dms.manage.domains.DTO.personInfoManager.TcUserDTO;

@Service
public class DealerInfoServiceImpl  implements DealerInfoService{
	@Autowired
	DealerInfoDao  dealerInfodao;
	
	
	/**
	 * BY 用户ID,组织ID，公司ID 查询出指定经销商用户的职务信息
	 */
	@Override
	public PageInfoDto dealerInfoQuery(Map<String, String> queryParam,LoginInfoDto loginInfo) {
		// TODO Auto-generated method stub
		return dealerInfodao.dealerInfoQuery(queryParam,loginInfo);
	}
	
	/**
	 * 修改当前经销商用户的信息
	 */
	@Override
	public void modifyUserinfo(TcUserDTO dbDto) {
		TcUserPO dbPo=TcUserPO.findById(dbDto.getUserId());
	        setdbPo(dbPo, dbDto);
	        dbPo.saveIt();
	}
	private void setdbPo(TcUserPO dbPo, TcUserDTO dbDto) {
		// TODO Auto-generated method stub
        dbPo.setString("NAME",dbDto.getName());
        dbPo.setInteger("GENDER",dbDto.getGender());
        dbPo.setString("HAND_PHONE",dbDto.getHandPhone());
        dbPo.setString("PHONE",dbDto.getPhone());
        dbPo.setString("EMAIL",dbDto.getEmail());
        dbPo.setDate("BIRTHDAY",dbDto.getBirthday());
        dbPo.setString("ADDR",dbDto.getAddr());
        dbPo.setString("ZIP_CODE",dbDto.getZipCode());
        
        
        
        
        
        
        
        
	}

}
