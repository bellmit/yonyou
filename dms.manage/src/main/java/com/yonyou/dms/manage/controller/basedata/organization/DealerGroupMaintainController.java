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
import com.yonyou.dms.manage.domains.DTO.basedata.organization.DealerGroupDTO;
import com.yonyou.dms.manage.service.basedata.organization.DealerGroupMaintainService;
import com.yonyou.f4.mvc.annotation.TxnConn;

/**
 * 经销商集团维护
 * @author Administrator
 *
 */
@Controller
@TxnConn
@RequestMapping("/organization/dealerGroupMaintain")
public class DealerGroupMaintainController {
	
	@Autowired
	private DealerGroupMaintainService dealerService;
	
	private static final Logger logger = LoggerFactory.getLogger(DealerGroupMaintainController.class);
	
	/**
	 * 首页初始化查询
	 * @param param
	 * @return
	 */
	@RequestMapping(method = RequestMethod.GET)
	@ResponseBody
	public PageInfoDto search(@RequestParam Map<String, String> param){
		logger.info("==================经销商集团维护查询=================");
		PageInfoDto dto = dealerService.search(param);
		return dto;	
	}
	
	/**
	 * 新增数据保存
	 * @param dto
	 * @param uriCB
	 * @return
	 */
	@RequestMapping(value = "/addDealerGroup", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<DealerGroupDTO> addDealerGroup(@RequestBody DealerGroupDTO dto,UriComponentsBuilder uriCB){
		logger.info("==================经销商集团维护新增=================");
		dealerService.addDealerGroup(dto);
        MultiValueMap<String,String> headers = new HttpHeaders();  
        headers.set("Location", uriCB.path("/addDealerInfo").buildAndExpand().toUriString());
        return new ResponseEntity<>(headers, HttpStatus.CREATED);  
	}
	
	/**
	 * 修改页面回显
	 * @param groupId
	 * @return
	 */
	@RequestMapping(value = "/{groupId}", method = RequestMethod.GET)
	@ResponseBody
	public DealerGroupDTO editDealerGroupInit(@PathVariable String groupId){
		logger.info("==================经销商集团维护修改页面回显=================");
		DealerGroupDTO dto = dealerService.editDealerGroupInit(groupId);
		return dto;	
	}
	
	/**
	 * 修改数据保存
	 * @param dto
	 * @param uriCB
	 * @return
	 */
	@RequestMapping(value = "/editDealerGroup", method = RequestMethod.PUT)
	@ResponseBody
	public ResponseEntity<DealerGroupDTO> editDealerGroup(@RequestBody DealerGroupDTO dto,UriComponentsBuilder uriCB){
		logger.info("==================经销商集团维护修改保存=================");
		dealerService.editDealerGroup(dto);
        MultiValueMap<String,String> headers = new HttpHeaders();  
        headers.set("Location", uriCB.path("/addDealerInfo").buildAndExpand().toUriString());
        return new ResponseEntity<>(headers, HttpStatus.CREATED);  
	}

}
