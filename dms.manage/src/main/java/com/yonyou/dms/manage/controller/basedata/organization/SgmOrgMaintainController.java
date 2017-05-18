package com.yonyou.dms.manage.controller.basedata.organization;

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

import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.manage.domains.DTO.basedata.organization.SgmOrgDTO;
import com.yonyou.dms.manage.service.basedata.organization.SgmOrgMaintainService;
import com.yonyou.f4.mvc.annotation.TxnConn;

/**
 * 公司组织维护
 * @author Administrator
 *
 */
@RequestMapping("/organization/sgmOrgMaintain")
@Controller
@TxnConn
public class SgmOrgMaintainController {
	
	@Autowired
	private SgmOrgMaintainService sgmOrgService;
	
	private Logger logger = LoggerFactory.getLogger(SgmOrgMaintainController.class);
	
	/**
	 * 页面初始化查询
	 * @return
	 */
	@RequestMapping(method = RequestMethod.GET)
	@ResponseBody
	public PageInfoDto search(@RequestParam Map<String, String> param){
		logger.info("==================公司组织维护查询==================");
		PageInfoDto dto = sgmOrgService.searchSgmOrg(param);
		return dto;	
	}
	
	/**
	 * 新增数据保存
	 * @param dto
	 * @param uriCB
	 * @return
	 */
	@RequestMapping(value = "/addSgmOrg", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<SgmOrgDTO> addSgmOrg(@RequestBody SgmOrgDTO dto,UriComponentsBuilder uriCB){
		logger.info("==================公司组织维护新增==================");
		sgmOrgService.addSgmOrg(dto);
        MultiValueMap<String,String> headers = new HttpHeaders();  
        headers.set("Location", uriCB.path("/addSgmOrg").buildAndExpand().toUriString());
        return new ResponseEntity<>(headers, HttpStatus.CREATED);  		
	}
	
	/**
	 * 详情加载
	 * @param orgId
	 * @return
	 */
	@RequestMapping(value = "/{orgId}", method = RequestMethod.GET)
	@ResponseBody
	public SgmOrgDTO editSgmOrgInit(@PathVariable Long orgId){
		logger.info("==================公司组织维护详情加载==================");
		SgmOrgDTO dto = sgmOrgService.editSgmOrgInit(orgId);
		return dto;		
	}
	
	/**
	 * 修改数据保存
	 * @param dto
	 * @param uriCB
	 * @return
	 */
	@RequestMapping(value = "/editSgmOrg", method = RequestMethod.PUT)
	@ResponseBody
	public ResponseEntity<SgmOrgDTO> editSgmOrg(@RequestBody SgmOrgDTO dto,UriComponentsBuilder uriCB){
		logger.info("==================公司组织维护修改==================");
		sgmOrgService.editSgmOrg(dto);
        MultiValueMap<String,String> headers = new HttpHeaders();  
        headers.set("Location", uriCB.path("/editSgmOrg").buildAndExpand().toUriString());
        return new ResponseEntity<>(headers, HttpStatus.CREATED);  	
	}
	
}
