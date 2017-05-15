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
import com.yonyou.dms.manage.domains.DTO.basedata.organization.DealerInfoDTO;
import com.yonyou.dms.manage.service.basedata.organization.DealerInfoMaintainService;
import com.yonyou.f4.mvc.annotation.TxnConn;

/**
 * 经销商公司维护
 * @author Administrator
 *
 */
@Controller
@TxnConn
@RequestMapping("/organization/dealerInfoMain")
public class DealerInfoMaintainController {
	
	@Autowired
	private DealerInfoMaintainService dealerService;
	
	private Logger logger = LoggerFactory.getLogger(DealerInfoMaintainController.class);
	
	/**
	 * 页面初始化查询
	 * @param param
	 * @return
	 */
	@RequestMapping(method = RequestMethod.GET)
	@ResponseBody
	public PageInfoDto search(@RequestParam Map<String, String> param){
		logger.info("==================经销商公司维护查询==================");
		PageInfoDto dto = dealerService.searchDealerInfo(param);
		return dto;		
	}
	
	/**
	 * 详情查询
	 * @param companyId
	 * @return
	 */
	@RequestMapping(value = "/{companyId}", method = RequestMethod.GET)
	@ResponseBody
	public PageInfoDto searchDetail(@PathVariable String companyId){
		logger.info("==================经销商公司维护详情==================");
		PageInfoDto dto = dealerService.searchDealerDetail(companyId);
		return dto;	
	}
	
	/**
	 * 新增数据保存
	 * @param dto
	 * @param uriCB
	 * @return
	 */
	@RequestMapping(value = "/addDealerInfo", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<DealerInfoDTO> addDealerInfo(@RequestBody DealerInfoDTO dto,UriComponentsBuilder uriCB){
		logger.info("==================经销商公司维护新增==================");
		dealerService.addDealerInfo(dto);
        MultiValueMap<String,String> headers = new HttpHeaders();  
        headers.set("Location", uriCB.path("/addDealerInfo").buildAndExpand().toUriString());
        return new ResponseEntity<>(headers, HttpStatus.CREATED);  
	}
	
	/**
	 * 编辑页面回显数据加载
	 * @param companyId
	 * @return
	 */
	@RequestMapping(value = "/editDealerInfoInit/{companyId}", method = RequestMethod.GET)
	@ResponseBody
	public DealerInfoDTO editDealerInfoInit(@PathVariable Long companyId){
		logger.info("==================经销商公司维护编辑页面回显==================");
		DealerInfoDTO dto = dealerService.editDealerInfoQuery(companyId);
		return dto;		
	}
	
	/**
	 * 编辑数据保存
	 * @param dto
	 * @param uriCB
	 * @return
	 */
	@RequestMapping(value = "/editDealerInfo", method = RequestMethod.PUT)
	@ResponseBody
	public ResponseEntity<DealerInfoDTO>  editDealerInfo(@RequestBody DealerInfoDTO dto,UriComponentsBuilder uriCB){
		logger.info("==================经销商公司维护编辑保存==================");
		dealerService.editDealerInfo(dto);
        MultiValueMap<String,String> headers = new HttpHeaders();  
        headers.set("Location", uriCB.path("/editDealerInfo").buildAndExpand().toUriString());
        return new ResponseEntity<>(headers, HttpStatus.CREATED);  
	}

}
