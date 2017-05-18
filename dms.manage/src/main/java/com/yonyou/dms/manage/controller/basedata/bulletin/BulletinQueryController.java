package com.yonyou.dms.manage.controller.basedata.bulletin;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.manage.service.basedata.bulletin.BulletinQueryService;
import com.yonyou.f4.mvc.controller.BaseController;

/**
 * 通报查询
 * @author Administrator
 *
 */
@Controller
@RequestMapping("/bulletin/bulletinQuery")
public class BulletinQueryController extends BaseController {
	
	@Autowired
	private BulletinQueryService service;
	
	private static final Logger logger = LoggerFactory.getLogger(BulletinQueryController.class);

	/**
	 * 首页初始化
	 * @param param
	 * @return
	 */
	@RequestMapping(method = RequestMethod.GET)
	@ResponseBody
	public PageInfoDto search(@RequestParam Map<String,String> param){
		logger.info("==================通报查询================");
		PageInfoDto dto = service.search(param);
		return dto;		
	}

	/**
	 * 详细
	 * @param param
	 * @param typeId
	 * @return
	 */
	@RequestMapping(value="/viewDetail/{typeId}",method = RequestMethod.GET)
	@ResponseBody
	public PageInfoDto searchDetail(@RequestParam Map<String,String> param,@PathVariable String typeId){
		logger.info("==================通报查询详细================");
		param.put("typeId", typeId);
		PageInfoDto dto = service.searchDetail(param);
		return dto;		
	}
	
	/**
	 * 通告查看
	 * @param bulletinId
	 * @return
	 */
	@RequestMapping(value="/viewBulletin/{bulletinId}",method = RequestMethod.GET)
	@ResponseBody
	public Map<String,Object> viewBulletin(@PathVariable Long bulletinId){
		logger.info("==================通报查看================");
		Map map = service.viewBulletin(bulletinId);
		return map;		
	}
	
	/**
	 * 未读人员列表
	 * @param param
	 * @param bulletinId
	 * @return
	 */
	@RequestMapping(value="/getNotReadList/{bulletinId}",method = RequestMethod.GET)
	@ResponseBody
	public PageInfoDto getNotReadList(@RequestParam Map<String,String> param,@PathVariable Long bulletinId){
		logger.info("==================通报查看未读人员列表================");
		PageInfoDto dto = service.getNotReadList(param,bulletinId);
		return dto;
	}
	
	/**
	 * 已读人员列表
	 * @param param
	 * @param bulletinId
	 * @return
	 */
	@RequestMapping(value="/getReadList/{bulletinId}",method = RequestMethod.GET)
	@ResponseBody
	public PageInfoDto getReadList(@RequestParam Map<String,String> param,@PathVariable Long bulletinId){
		logger.info("==================通报查看已读人员列表================");
		PageInfoDto dto = service.getReadList(param,bulletinId);
		return dto;
	}
	
	/**
	 * 签收人员列表
	 * @param param
	 * @param bulletinId
	 * @return
	 */
	@RequestMapping(value="/getSignList/{bulletinId}",method = RequestMethod.GET)
	@ResponseBody
	public PageInfoDto getSignList(@RequestParam Map<String,String> param,@PathVariable Long bulletinId){
		logger.info("==================通报查看签收人员列表================");
		PageInfoDto dto = service.getSignList(param,bulletinId);
		return dto;
	}
	
	/**
	 * 未签收人员列表
	 * @param param
	 * @param bulletinId
	 * @return
	 */
	@RequestMapping(value="/getNotSignList/{bulletinId}",method = RequestMethod.GET)
	@ResponseBody
	public PageInfoDto getNotSignList(@RequestParam Map<String,String> param,@PathVariable Long bulletinId){
		logger.info("==================通报查看未签收人员列表================");
		PageInfoDto dto = service.getNotSignList(param,bulletinId);
		return dto;
	}

}
