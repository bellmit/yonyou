package com.yonyou.dms.manage.service.basedata.personInfoManager;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yonyou.dms.common.domains.PO.basedata.TcUserPO;
import com.yonyou.dms.framework.DAO.OemDAOUtil;
import com.yonyou.dms.function.exception.ServiceBizException;
import com.yonyou.dms.function.utils.common.StringUtils;
import com.yonyou.dms.manage.dao.personInfoManager.EditPasswordDao;
import com.yonyou.dms.manage.domains.DTO.personInfoManager.TcUserDTO;

@Service
public class EditPasswordServiceImpl  implements EditPasswordService{
	@Autowired
	EditPasswordDao  editPasswordDao;

	
/**
 * 通过用户编号得到密码
 */
	@Override
	public String getPasswordByUserCode(Long userid)  throws ServiceBizException{
		  String psaaword = null;
	        StringBuilder sqlSb = new StringBuilder("select PASSWORD  from  tc_user  where  USER_ID = ?");
	        List<Object> queryParams = new ArrayList<Object>();
	        queryParams.add(userid);
	        List<Map> list = OemDAOUtil.findAll(sqlSb.toString(), queryParams);
	        if (list.size() > 0) {

	            psaaword = (String) list.get(0).get("PASSWORD");
	        }
	        return psaaword;
	}
	
	/**
	 * 进行修改密码
	 */
    @Override
    public void modifyUser(TcUserDTO userDto) throws ServiceBizException {
    	TcUserPO userPO = TcUserPO.findById(userDto.getUserId());
        setUser(userPO, userDto);
        userPO.saveIt();

    }

    /**
     * 设置用户
     * @param userPO
     * @param userDto
     */
	private void setUser(TcUserPO userPO, TcUserDTO userDto) {
	        if (!StringUtils.isNullOrEmpty(userDto.getPassword())) {
	            userPO.setString("PASSWORD", userDto.getPassword());  
	        }
	}


}
