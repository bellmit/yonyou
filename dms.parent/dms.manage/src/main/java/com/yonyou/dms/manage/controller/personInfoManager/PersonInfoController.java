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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.util.UriComponentsBuilder;

import com.yonyou.dms.common.domains.PO.basedata.TcUserPO;
import com.yonyou.dms.framework.DAO.DAOUtil;
import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.framework.domain.LoginInfoDto;
import com.yonyou.dms.framework.util.bean.ApplicationContextHelper;
import com.yonyou.dms.manage.domains.DTO.personInfoManager.TcUserDTO;
import com.yonyou.dms.manage.service.basedata.personInfoManager.PersonInfoService;
import com.yonyou.f4.mvc.annotation.TxnConn;
import com.yonyou.f4.mvc.controller.BaseController;


/**
 * 个人信息维护
 * @author Administrator
 *
 */
@Controller
@TxnConn
@RequestMapping("/personInfoManager")
@ResponseBody
public class PersonInfoController extends BaseController{
	// 定义日志接口
	private static final Logger logger = LoggerFactory.getLogger(DAOUtil.class);
	
	@Autowired
	private PersonInfoService   personInfoService;
	
	@RequestMapping(value = "/personInfoQuery", method = RequestMethod.GET)
	// 查询职位信息
	public  PageInfoDto   personInfoQuery(@RequestParam Map<String, String> queryParam) {
		logger.info("==============职位信息查询=============");
		LoginInfoDto loginInfo = ApplicationContextHelper.getBeanByType(LoginInfoDto.class);
		PageInfoDto personInfoDto = personInfoService.personInfoQuery(queryParam,loginInfo);
		return personInfoDto;
	}
	
	
	
	
				/**
				 *通过userid 获得当前用户的信息
				 * @return
				 */
			    @TxnConn
			    @RequestMapping(value = "/personinfo", method = RequestMethod.GET)
			    @ResponseBody
		    public Map<String,Object> getPersoninfoById() {
			    	logger.info("==============通过userid 获得当前用户的信息=============");
			    	LoginInfoDto loginInfo = ApplicationContextHelper.getBeanByType(LoginInfoDto.class);
			    	TcUserPO po = TcUserPO.findById(loginInfo.getUserId());
			    	return po.toMap();
		
		          
		    }
			       
			    /**
			     * 修改当前用户的信息
			     * @author ZhengHe
			     * @date 2016年7月7日
			     * @param id
			     * @param dbdto经销商基本信息
			     * @param uriCB
			     * @return
			     */
			    @TxnConn
			    @RequestMapping(value="/updateUserInfo",method=RequestMethod.PUT)
			    public ResponseEntity<TcUserDTO> modifyUserInfo(@RequestBody TcUserDTO dbdto,UriComponentsBuilder uriCB){
			    	logger.info("============== 修改当前用户的信息=============");
			    	LoginInfoDto loginInfo = ApplicationContextHelper.getBeanByType(LoginInfoDto.class);
			        dbdto.setUserId(loginInfo.getUserId());
			    	personInfoService.modifyUserinfo(dbdto);
			        MultiValueMap<String,String> headers = new HttpHeaders();  
			        headers.set("Location", uriCB.path("/updateUserInfo").buildAndExpand().toUriString());
			        return new ResponseEntity<>(headers, HttpStatus.CREATED);  
			    }
			    
	
	
	
	
	
}
