package com.yonyou.dms.manage.controller.basedata.bulletin;

import java.text.ParseException;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

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
import com.yonyou.dms.manage.domains.DTO.bulletin.BulletinIssueDTO;
import com.yonyou.dms.manage.service.basedata.bulletin.BulletinMaintainService;

/**
 * 通报查询
 * @author Administrator
 *
 */
@Controller
@RequestMapping("/bulletin/bulletinMaintain")
public class BulletinMaintainController {
	
	
	private static final Logger logger = LoggerFactory.getLogger(BulletinMaintainController.class);
		
	@Autowired
	private BulletinMaintainService mainservice;
	
	/**
	 * 首页初始化
	 * @param param
	 * @return
	 */
	@RequestMapping(method = RequestMethod.GET)
	@ResponseBody
	private PageInfoDto search(@RequestParam Map<String,String> param){
		logger.info("==================通报维护初始化查询================");
		PageInfoDto dto = mainservice.search(param);
		return dto;		
	}
	
	/**
	 * 通报类别选择框
	 * @return
	 */
	@RequestMapping(value="/getAllBulletinType",method = RequestMethod.GET)
	@ResponseBody
	private List<Map> getAllBulletinType(){
		logger.info("==================通报维护类别选择框查询================");
		return mainservice.getAllBulletinType();
		
	}
	
	@RequestMapping(value="/{bulletinId}",method = RequestMethod.GET)
	@ResponseBody
	public Map editBulletinInit(@PathVariable Long bulletinId) throws ParseException{
		logger.info("==================通报维护修改回显================");
		Map map = mainservice.editBulletinInit(bulletinId);
		return map;
	}
	
	@RequestMapping(value="/getDealers/{bulletinId}",method = RequestMethod.GET)
	@ResponseBody
	public PageInfoDto getDealers(@RequestParam Map<String,String> param,@PathVariable Long bulletinId){
		PageInfoDto dto = mainservice.getDealers(param,bulletinId);
		return dto;
	}
	
	@RequestMapping(value="/getFiles/{bulletinId}",method = RequestMethod.GET)
	@ResponseBody
	public PageInfoDto getFiles(@RequestParam Map<String,String> param,@PathVariable Long bulletinId){
		PageInfoDto dto = mainservice.getFiles(param,bulletinId);
		return dto;
	}
	
    @RequestMapping(method = RequestMethod.PUT)
	@ResponseBody
	public ResponseEntity<BulletinIssueDTO> editBulletin(@RequestBody @Valid BulletinIssueDTO dto ,UriComponentsBuilder uriCB){
		logger.info("==================通报维护修改================");
		mainservice.editBulletin(dto);
        MultiValueMap<String, String> headers = new HttpHeaders();
        headers.set("Location", uriCB.path("").buildAndExpand().toUriString());
        return new ResponseEntity<BulletinIssueDTO>(headers, HttpStatus.CREATED);
	}
   
	@RequestMapping(value="/{bulletinId}",method = RequestMethod.DELETE)
	@ResponseBody
    public void deleteBulletin(@PathVariable Long bulletinId){
		logger.info("==================通报维护删除================");
		mainservice.deleteBulletin(bulletinId);
	}

}
