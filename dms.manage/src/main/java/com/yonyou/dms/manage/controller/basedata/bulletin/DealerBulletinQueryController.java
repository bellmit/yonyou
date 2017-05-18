package com.yonyou.dms.manage.controller.basedata.bulletin;

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
import com.yonyou.dms.manage.domains.DTO.bulletin.BulletinReplyDTO;
import com.yonyou.dms.manage.service.basedata.bulletin.DealerBulletinQueryService;

/**
 * 通报查询（经销商）
 * @author Administrator
 *
 */
@Controller
@RequestMapping("/bulletin/dealerQuery")
public class DealerBulletinQueryController {
	
	@Autowired
	private DealerBulletinQueryService service;
	
	private static final Logger logger = LoggerFactory.getLogger(DealerBulletinQueryController.class);
	
	/**
	 * 首页初始化    通报类型列表
	 * @param param
	 * @return
	 */
	@RequestMapping(method = RequestMethod.GET)
	@ResponseBody
	public PageInfoDto search(@RequestParam Map<String,String> param){
		logger.info("==================通报查询（经销商）=================");
		PageInfoDto dto = service.search(param);
		return dto;
	}
	
	/**
	 * 进入通报列表
	 * @param param
	 * @param typeId
	 * @return
	 */
	@RequestMapping(value="/searchDetail/{typeId}",method = RequestMethod.GET)
	@ResponseBody
	public PageInfoDto searchDetail(@RequestParam Map<String,String> param,@PathVariable String typeId){
		logger.info("==================通报查询（经销商）进入通报列表=================");
		param.put("typeId", typeId);
		PageInfoDto dto = service.searchDetail(param);
		return dto;
	}

	/**
	 * 查看通报详情
	 * @param bulletinId
	 * @return
	 */
	@RequestMapping(value="/viewBulletin/{bulletinId}",method = RequestMethod.GET)
	@ResponseBody
	public Map<String,Object> viewBulletin(@PathVariable Long bulletinId){
		logger.info("==================通报查询（经销商）查看通报详情=================");
		Map<String,Object> map = service.viewBulletin(bulletinId);
		return map;
	}

	/**
	 * 设置是否签收
	 * @param bulletinId
	 * @param isSign
	 * @return
	 */
	@RequestMapping(value="/isSign/{bulletinId}/{isSign}",method = RequestMethod.PUT)
	@ResponseBody
	public boolean isSign(@PathVariable Long bulletinId,@PathVariable Integer isSign){
		return service.isSign(bulletinId,isSign);
		
	}
	
	/**
	 * 回复通报
	 * @param dto
	 * @param uriCB
	 * @return
	 */
    @RequestMapping(value="/replyBulletin",method = RequestMethod.PUT)
	@ResponseBody
	public ResponseEntity<BulletinReplyDTO> replyBulletin(@RequestBody BulletinReplyDTO dto,UriComponentsBuilder uriCB){
		logger.info("==================通报查询（经销商）回复通报=================");
    	service.replyBulletin(dto);
    	MultiValueMap<String, String> headers = new HttpHeaders();
        headers.set("Location", uriCB.path("").buildAndExpand().toUriString());
        return new ResponseEntity<BulletinReplyDTO>(headers, HttpStatus.CREATED);
    }

}
