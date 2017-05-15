package com.yonyou.dms.manage.controller.basedata.bulletin;

import java.util.ArrayList;
import java.util.HashMap;
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
import com.yonyou.dms.function.common.OemDictCodeConstants;
import com.yonyou.dms.manage.domains.DTO.bulletin.BulletinIssueDTO;
import com.yonyou.dms.manage.service.basedata.bulletin.BulletinIssueService;
import com.yonyou.f4.mvc.annotation.TxnConn;
import com.yonyou.f4.mvc.controller.BaseController;

/**
 * 通报发布
 * @author Administrator
 *
 */
@Controller
@TxnConn
@RequestMapping("/bulletin/bulletinIssue")
public class BulletinIssueController extends BaseController {
	
	@Autowired
	private BulletinIssueService service;
	
	private static final Logger logger = LoggerFactory.getLogger(BulletinTypeController.class);
	
	@RequestMapping(method = RequestMethod.GET)
	@ResponseBody
	public PageInfoDto search(@RequestParam Map<String,String> param){
		logger.info("==================通报发布查询================");
		PageInfoDto dto = service.search(param);
		return dto;
		
	}
	
	@RequestMapping(value="/getPosition",method = RequestMethod.GET)
	@ResponseBody
	public List<Map<String,Object>> getPosition(){
		List<Map<String,Object>> list = new ArrayList<>();
		Map<String,Object> map1 = new HashMap<>();
		Map<String,Object> map2 = new HashMap<>();
		map1.put("NAME", OemDictCodeConstants.POSITION_TYPE_01);
		map2.put("NAME", OemDictCodeConstants.POSITION_TYPE_02);
		list.add(map1);
		list.add(map2);
		return list;
	}
	
	/**
	 * 通报新增
	 * @param dto
	 * @param uriCB
	 * @return
	 */
    @RequestMapping(method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<BulletinIssueDTO> addBulletinIssue(@RequestBody @Valid BulletinIssueDTO dto ,UriComponentsBuilder uriCB){
		logger.info("==================通报发布================");
    	service.addBulletinIssue(dto);
        MultiValueMap<String, String> headers = new HttpHeaders();
        headers.set("Location", uriCB.path("").buildAndExpand().toUriString());
        return new ResponseEntity<BulletinIssueDTO>(headers, HttpStatus.CREATED);
	}
    
    /**
     * 根据typeId获取name
     * @param typeId
     * @return
     */
    @RequestMapping(value="/getTypeName/{typeId}",method = RequestMethod.GET)
	@ResponseBody
    public String getTypeName(@PathVariable Long typeId){
		return service.getTypeName(typeId);
    	
    }
}
