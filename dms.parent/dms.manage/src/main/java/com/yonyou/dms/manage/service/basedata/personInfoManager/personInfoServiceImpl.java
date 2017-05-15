package com.yonyou.dms.manage.service.basedata.personInfoManager;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yonyou.dms.common.domains.PO.basedata.TcUserPO;
import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.framework.domain.LoginInfoDto;
import com.yonyou.dms.manage.dao.personInfoManager.PersonInfoDao;
import com.yonyou.dms.manage.domains.DTO.personInfoManager.TcUserDTO;


@Service
public class personInfoServiceImpl  implements PersonInfoService{
	
	@Autowired
	PersonInfoDao  personInfoDao;

	/**
	 * BY 用户ID,组织ID，公司ID 查询出指定用户的职务信息
	 */
	@Override
	public PageInfoDto personInfoQuery(Map<String, String> queryParam,LoginInfoDto loginInfo) {
		// TODO Auto-generated method stub
		return personInfoDao.personInfoQuery(queryParam,loginInfo);
	}
	
	
	
	/**
	 * 修改当前用户的信息
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





/*	@Override
	public Map getPersonInfo() {
		LoginInfoDto loginInfo = ApplicationContextHelper.getBeanByType(LoginInfoDto.class);
		   TcUserPO po = TcUserPO.findById(loginInfo.getUserId());
		   TcUserOnlinePO tc = TcUserOnlinePO.findById(loginInfo.getUserId());
		   //定义一个Map集合
	       Map<String,Object> map=new HashMap<String,Object>(); 
	       map.put("acnt", po.get("acnt"));
	       //String acnt = (String) po.get("acnt");
	       map.put("name", po.get("name"));
	       map.put("phone", po.get("phone"));
	       map.put("email", po.get("email"));
	       map.put("hand_phone", po.get("hand_phone"));
	       map.put("gender", po.get("gender"));
	       map.put("birthday", po.get("birthday"));
	       map.put("addr", po.get("addr"));
	       map.put("zip_code", po.get("zip_code"));
	       map.put("login_date", tc.get("login_date"));
	       return map;
		
	}*/
}
