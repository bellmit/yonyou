package com.yonyou.dms.manage.service.basedata.personInfoManager;

import com.yonyou.dms.function.exception.ServiceBizException;
import com.yonyou.dms.manage.domains.DTO.personInfoManager.TcUserDTO;

public interface EditPasswordService {
	
	
    //通过用户编号得到用户密码
	String getPasswordByUserCode(Long userid) throws ServiceBizException;


	//通过userid进行修改密码
	void modifyUser( TcUserDTO userDto);


}
