package com.yonyou.dms.manage.controller.personInfoManager;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.util.UriComponentsBuilder;

import com.yonyou.dms.common.domains.PO.basedata.TcUserPO;
import com.yonyou.dms.framework.DAO.DAOUtil;
import com.yonyou.dms.framework.domain.LoginInfoDto;
import com.yonyou.dms.framework.util.bean.ApplicationContextHelper;
import com.yonyou.dms.function.exception.ServiceBizException;
import com.yonyou.dms.function.utils.security.MD5Util;
import com.yonyou.dms.manage.domains.DTO.basedata.user.UserDto;
import com.yonyou.dms.manage.domains.DTO.personInfoManager.TcUserDTO;
import com.yonyou.dms.manage.service.basedata.personInfoManager.EditPasswordService;
import com.yonyou.f4.mvc.annotation.TxnConn;
import com.yonyou.f4.mvc.controller.BaseController;

@Controller
@TxnConn
@RequestMapping("/editUser")
@ResponseBody
/**
 * 
 * 快速修改密码
 * @author yll
 * @date 2016年8月24日
 * @param brandDto
 * @param uriCB
 * @return
 */
public class EditPasswordController extends BaseController{
	// 定义日志接口
		private static final Logger logger = LoggerFactory.getLogger(DAOUtil.class);
		
		@Autowired
		private EditPasswordService   editPasswordService;
		

		@RequestMapping(value = "/editpassword", method = RequestMethod.PUT)
		public ResponseEntity<UserDto> editpassword(@RequestBody TcUserDTO userDto,UriComponentsBuilder uriCB) {
			logger.info("==========密码修改===========");
			MultiValueMap<String,String> headers = new HttpHeaders();  
			//获取页面参数
			Long userid=userDto.getUserId();
			String password=userDto.getPassword();//旧密码
			String newPassword=userDto.getNewPassword();//新密码
			String passwordMD5=editPasswordService.getPasswordByUserCode(userid);
			String newPasswordMD5=MD5Util.getEncryptedPwd(newPassword);
			
			//输入的旧密码与数据库旧密码相等，就可以进行修改
			if(MD5Util.validPassword(password,passwordMD5)){
				//throw new ServiceBizException("旧密码验证失败！");
				//输入的旧密码与新密码不相等，进行修改
			if(!passwordMD5.equals(newPasswordMD5)){
				userDto.setPassword(newPasswordMD5);
				editPasswordService.modifyUser(userDto);
				headers.set("Location", uriCB.path("/editUser/editpassword").buildAndExpand(userid).toUriString());  
		      	}
			}
			else{
				throw new ServiceBizException("原密码不正确，请重新输入!");
	      	}
			return new ResponseEntity<>(headers, HttpStatus.CREATED);
		}
		
		
		
		/**
		 * 查询账号和用户名称
		 */
	    @TxnConn
	    @RequestMapping(value = "/tcUserInfo", method = RequestMethod.GET)
	    @ResponseBody
    public Map<String,Object> getTcUserinfoById() {
	    	
	    	LoginInfoDto loginInfo = ApplicationContextHelper.getBeanByType(LoginInfoDto.class);
	    	TcUserPO po = TcUserPO.findById(loginInfo.getUserId());
	    	return po.toMap();

          
    }
		
		
		
}
