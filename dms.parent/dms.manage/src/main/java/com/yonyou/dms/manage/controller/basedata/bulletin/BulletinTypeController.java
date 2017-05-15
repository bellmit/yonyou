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
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.util.UriComponentsBuilder;

import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.manage.domains.DTO.bulletin.BulletinTypeDTO;
import com.yonyou.dms.manage.service.basedata.bulletin.BulletinTypeService;
import com.yonyou.f4.mvc.annotation.TxnConn;

/**
 * 通报类别维护
 * @author Administrator
 *
 */
@Controller
@TxnConn
@RequestMapping("/bulletin/bulletinType")
public class BulletinTypeController {
	
	@Autowired
	private BulletinTypeService typeService;
	
	private static final Logger logger = LoggerFactory.getLogger(BulletinTypeController.class);
	
	@RequestMapping(method = RequestMethod.GET)
	@ResponseBody
	public PageInfoDto search(@RequestParam Map<String,String> param){
		logger.info("==================通报类别维护初始化查询================");
		PageInfoDto dto = typeService.search(param);
		return dto;		
	}
	
	/**
	 * 状态选择框
	 * @return
	 */
	@RequestMapping(value="selectStatus",method = RequestMethod.GET)
	@ResponseBody
	public List<Map<String,Object>> selectStatus(){
		logger.info("==================通报类别维护状态选择框================");
		List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
		Map<String,Object> map1 = new HashMap<String,Object>();
		Map<String,Object> map2 = new HashMap<String,Object>();
		map1.put("code", 0);
		map1.put("value", "注销");
		map2.put("code", 1);
		map2.put("value", "可用");
		list.add(map1);
		list.add(map2);
		return list;
		
	}
	
	/**
	 * 数据新增 保存
	 * @param dto
	 * @param uriCB
	 * @return
	 */
	@RequestMapping(method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<BulletinTypeDTO> addBulletinType(@RequestBody BulletinTypeDTO dto,UriComponentsBuilder uriCB ){
		logger.info("==================通报类别维护新增================");
		typeService.addBulletinType(dto);
        MultiValueMap<String,String> headers = new HttpHeaders();  
        headers.set("Location", uriCB.path("/addDealerInfo").buildAndExpand().toUriString());
        return new ResponseEntity<>(headers, HttpStatus.CREATED);  
	}
	
	/**
	 * 修改类别数据回显
	 * @param typeId
	 * @return
	 */
	@RequestMapping(value="/editType/{typeId}",method = RequestMethod.GET)
	@ResponseBody
	public BulletinTypeDTO editBulletinTypeInit(@PathVariable Long typeId){
		logger.info("==================通报类别维护修改数据回显================");
		BulletinTypeDTO dto = typeService.editBulletinTypeInit(typeId);
		return dto;
		
	}
	
	/**
	 * 修改人员数据回显
	 * @param typeId
	 * @return
	 */
	@RequestMapping(value="/editUser/{typeId}",method = RequestMethod.GET)
	@ResponseBody
	public List<Map> editBulletinTypeUserInit(@PathVariable Long typeId){
		logger.info("==================通报类别维护修改人员列表回显================");
		List<Map> list = typeService.editBulletinTypeUserInit(typeId);
		return list;		
	}
	
	/**
	 * 修改数据保存
	 * @param dto
	 * @param uriCB
	 * @return
	 */
	@RequestMapping(method = RequestMethod.PUT)
	@ResponseBody
	public ResponseEntity<BulletinTypeDTO> editBulletinType(@RequestBody @Valid BulletinTypeDTO dto,UriComponentsBuilder uriCB ){
		logger.info("==================通报类别维护修改保存================");
		typeService.editBulletinType(dto);
        MultiValueMap<String,String> headers = new HttpHeaders();  
        headers.set("Location", uriCB.path("/addDealerInfo").buildAndExpand().toUriString());
        return new ResponseEntity<>(headers, HttpStatus.CREATED);  
	}
	
	/**
	 * 查询人员
	 * @param param
	 * @return
	 */
	@RequestMapping(value="/searchUser",method = RequestMethod.GET)
	@ResponseBody
	public PageInfoDto searchUser(@RequestParam Map<String,String> param){
		logger.info("==================通报类别维护人员查询================");
		PageInfoDto dto = typeService.searchUser(param);
		return dto;
		
	}
	
	/**
	 * 根据用户id删除
	 * @param userId
	 * @param uriCB
	 */
    @RequestMapping(value = "/{userId}/{typeId}", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
	public void deleteUser(@PathVariable("userId") Long userId,@PathVariable("typeId")Long typeId, UriComponentsBuilder uriCB){
    	typeService.deleteUser(userId,typeId);
    }

}
