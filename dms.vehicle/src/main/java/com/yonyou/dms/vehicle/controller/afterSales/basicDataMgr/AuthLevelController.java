package com.yonyou.dms.vehicle.controller.afterSales.basicDataMgr;

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

import com.yonyou.dms.framework.DAO.DAOUtil;
import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.vehicle.domains.DTO.afterSales.basicDataMgr.TtWrAuthlevelDTO;
import com.yonyou.dms.vehicle.domains.PO.afterSales.basicDataMgr.TtWrAuthlevelPO;
import com.yonyou.dms.vehicle.service.afterSales.basicDataMgr.AuthLevelService;
import com.yonyou.f4.mvc.annotation.TxnConn;

/**
 * 授权级别维护
 * @author Administrator
 *
 */
@Controller
@TxnConn
@RequestMapping("/authLevel")
public class AuthLevelController {
	private static final Logger logger = LoggerFactory.getLogger(DAOUtil.class);
	@Autowired
	AuthLevelService  authLevelService;
	/**
	 * 授权级别维护查询
	 */
	@RequestMapping(value="/authLevelSearch",method = RequestMethod.GET)
    @ResponseBody
    public PageInfoDto authLevelSearch(@RequestParam Map<String, String> queryParam) {
    	logger.info("==============授权级别维护查询=============");
        PageInfoDto pageInfoDto =authLevelService.AuthLevelQuery(queryParam);
        return pageInfoDto;  
    }

	/**
	 *修改授权级别的信息回显 
	 */
    @RequestMapping(value = "/getAuthLevel/{id}", method = RequestMethod.GET)
    @ResponseBody
    public Map<String,Object> getAuthLevelById(@PathVariable(value = "id") Long id){
    	logger.info("=====修改授权级别的信息回显=====");
    	TtWrAuthlevelPO ptPo=authLevelService.getAuthLevelById(id);
        return ptPo.toMap();
    }
    
    /**
     * 修改授权级别的信息
     */
    @RequestMapping(value = "/edit/{id}", method = RequestMethod.PUT)
    @ResponseBody
    public ResponseEntity<TtWrAuthlevelDTO> edit(@PathVariable(value = "id") Long id,@RequestBody TtWrAuthlevelDTO dto,UriComponentsBuilder uriCB){
    	logger.info("===== 修改授权级别的信息=====");
    	authLevelService.edit(id,dto);
    	MultiValueMap<String, String> headers = new HttpHeaders();
        headers.set("Location", uriCB.path("/authLevel/edit").buildAndExpand().toUriString());
        return new ResponseEntity<TtWrAuthlevelDTO>(headers, HttpStatus.CREATED);  	
    }
	
	
	

}
