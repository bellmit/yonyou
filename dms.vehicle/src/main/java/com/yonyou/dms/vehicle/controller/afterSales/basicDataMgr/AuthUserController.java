package com.yonyou.dms.vehicle.controller.afterSales.basicDataMgr;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.util.UriComponentsBuilder;

import com.yonyou.dms.common.domains.PO.basedata.TcUserPO;
import com.yonyou.dms.framework.DAO.DAOUtil;
import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.manage.domains.DTO.personInfoManager.TcUserDTO;
import com.yonyou.dms.vehicle.service.afterSales.basicDataMgr.AuthUserService;
import com.yonyou.f4.mvc.annotation.TxnConn;

/**
 * 授权人员管理
 * @author Administrator
 *
 */
@Controller
@TxnConn
@RequestMapping("/authUser")
public class AuthUserController{
	private static final Logger logger = LoggerFactory.getLogger(DAOUtil.class);
	@Autowired
	AuthUserService  authUserService;
	@RequestMapping(value="/authUserSearch",method = RequestMethod.GET)
    @ResponseBody
    public PageInfoDto otherFeeSearch(@RequestParam Map<String, String> queryParam) {
    	logger.info("============== 授权人员管理查询=============");
        PageInfoDto pageInfoDto =authUserService.AuthUserQuery(queryParam);
        return pageInfoDto;  
    }
	/**
	 * 查询所有授权顺序
	 * @param queryParams
	 * @return
	 */
    @RequestMapping(value="/authUserLevel",method = RequestMethod.GET)
    @ResponseBody
    public List<Map> getauthUser(@RequestParam Map<String, String> queryParams){
    	logger.info("=====查询所有授权顺序=====");
    	List<Map> tenantMapping = authUserService.getAuthLevel(queryParams);
        return tenantMapping;
    }
    /**
     * 回显信息
     */
    @RequestMapping(value = "/getAuthUser/{id}", method = RequestMethod.GET)
    @ResponseBody
    public Map<String,Object> getDirectCustomerById(@PathVariable(value = "id") Long id){
    	logger.info("=====授权人员管理信息回显=====");
    	TcUserPO ptPo=authUserService.getAuthLevelById(id);
        return ptPo.toMap();
    }
    
    /**
     * 修改信息
     */
    @RequestMapping(value = "/edit/{id}", method = RequestMethod.PUT)
    @ResponseBody
    public ResponseEntity<TcUserDTO> edit(@PathVariable(value = "id") Long id,@RequestBody TcUserDTO dto,UriComponentsBuilder uriCB){
    	logger.info("===== 通过id进行修改授权人员管理=====");
    	authUserService.edit(id,dto);
    	MultiValueMap<String, String> headers = new HttpHeaders();
        headers.set("Location", uriCB.path("/authUser/edit").buildAndExpand().toUriString());
        return new ResponseEntity<TcUserDTO>(headers, HttpStatus.CREATED);  	
    }
}
