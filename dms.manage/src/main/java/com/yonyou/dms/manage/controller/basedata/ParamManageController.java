package com.yonyou.dms.manage.controller.basedata;

import java.util.ArrayList;
import java.util.HashMap;
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

import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.function.common.OemDictCodeConstants;
import com.yonyou.dms.manage.domains.DTO.basedata.ParamManageDTO;
import com.yonyou.dms.manage.service.basedata.paramManage.paramManageService;
import com.yonyou.f4.mvc.annotation.TxnConn;
import com.yonyou.f4.mvc.controller.BaseController;

@Controller
@TxnConn
@RequestMapping("/basedata/params")
public class ParamManageController extends BaseController {
	
	private Logger logger = LoggerFactory.getLogger(WorkWeekDefinitionController.class);
	
	@Autowired
	private paramManageService service;
	
	@RequestMapping("/getParamType")
	@ResponseBody
	public List<Map<String,Object>> getParamType(){
		List<Map<String,Object>> list = new ArrayList<>();
		Map<String,Object> map1 = new HashMap<String, Object>();
		Map<String,Object> map2 = new HashMap<String, Object>();
		Map<String,Object> map3 = new HashMap<String, Object>();
		Map<String,Object> map4 = new HashMap<String, Object>();
		Map<String,Object> map5 = new HashMap<String, Object>();
		map1.put("code", 1);
		map1.put("data", "销售预测比例下限");
		map2.put("code", 2);
		map2.put("data", "销售预测时间设置");
		map3.put("code", 3);
		map3.put("data", "配额分配时间设置");
		map4.put("code", 4);
		map4.put("data", "配额转换率下限");
		map5.put("code", 5);
		map5.put("data", "配额转换率上限");
		list.add(map1);
		list.add(map2);
//		list.add(map3);
//		list.add(map4);
//		list.add(map5);
		return list;		
	}
	
	/**
	 * 列表初始化查询方法
	 * @param param
	 * @return
	 */
	@RequestMapping(value = "/search",method = RequestMethod.GET)
	@ResponseBody
	public PageInfoDto search(@RequestParam Map<String,String> param){
		logger.info("===================业务参数维护首页查询=================");
		PageInfoDto dto = service.search(param);
		return dto;
	}
	
	/**
	 * 销售预测比例下限修改回显
	 * @param seriesId
	 * @return
	 */
	@RequestMapping(value = "/findForecastProportion/{seriesId}",method = RequestMethod.GET)
	@ResponseBody
	public Map findForecastProportion(@PathVariable Long seriesId){
		logger.info("===================销售预测比例下限修改=================");
		Map map = service.findForecastProportion(seriesId);
		return map;
	}
	
	/**
	 * 销售预测比例下限修改保存
	 * @param dto
	 * @param uriCB
	 * @return
	 */
	@RequestMapping(value = "/editForecastProportion",method = RequestMethod.PUT)
	@ResponseBody
	public ResponseEntity<ParamManageDTO> editForecastProportion(@RequestBody ParamManageDTO dto,UriComponentsBuilder uriCB){
		logger.info("===================销售预测比例下限修改保存=================");
		service.editForecastProportion(dto);
        MultiValueMap<String, String> headers = new HttpHeaders();
        headers.set("Location", uriCB.path("/basedata/params/eidtForecastProportion").buildAndExpand().toUriString());
        return new ResponseEntity<ParamManageDTO>(headers, HttpStatus.CREATED);
	}
	
	/**
	 * 配额转换率下限
	 * @param modelId
	 * @return
	 */
	@RequestMapping(value = "/findConversionRate/{modelId}",method = RequestMethod.GET)
	@ResponseBody
	public Map findConversionRate(@PathVariable Long modelId){
		logger.info("===================配额转换率下限修改=================");
		Map map = service.findConversionRate(modelId);
		return map;
	}
	
	/**
	 * 配额转换率上限修改保存
	 * @param param
	 * @return
	 */
	@RequestMapping(value = "/saveOrderRate",method = RequestMethod.GET)
	@ResponseBody
	public Map saveOrderRate(@RequestParam Map<String,String> param){
		logger.info("===================配额转换率上限修改保存=================");
		Map map = service.saveOrderRate(param);
		return map;
		
	}
	
	/**
	 * 配额转换率上限 回显
	 * @return
	 */
	@RequestMapping(value = "/getOrderRate",method = RequestMethod.GET)
	@ResponseBody
	public ParamManageDTO getOrderRate(){
		logger.info("===================配额转换率上限 回显=================");
		ParamManageDTO dto = service.getOrderRate();
		return dto;
		
	}
	
	/**
	 * 销售预测时间测试  左选框
	 * @return
	 */
	@RequestMapping(value = "/getDealerLeft",method = RequestMethod.GET)
	@ResponseBody 
	public List<Map> getDealerLeft(){
		logger.info("===================销售预测时间测试  左选框=================");
		List<Map> list = service.getDealerLeft();
		return list;
	}
	
	/**
	 * 销售预测时间测试  右选框
	 * @return
	 */
	@RequestMapping(value = "/getDealerRight",method = RequestMethod.GET)
	@ResponseBody 
	public List<Map> getDealerRight(){
		logger.info("===================销售预测时间测试  右选框=================");
		List<Map> list = service.getDealerRight();
		return list;
	}
	
	/**
	 * 销售预测时间测试  保存
	 * @param param
	 * @return
	 */
	@RequestMapping(value = "/saveDealerAndDate/{ids}/{date}",method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<ParamManageDTO> saveDealerAndDate(@RequestParam Map<String,String> param,@PathVariable String ids,@PathVariable String date){
		logger.info("==================销售预测时间测试  保存==================");
		Integer type = OemDictCodeConstants.PARAM_TYPE_02;
		service.saveDealerAndDate(ids,date,type);
        return new ResponseEntity<ParamManageDTO>(new HttpHeaders(), HttpStatus.CREATED);
	}
	
	/**
	 * 配额分配时间设置  左选框
	 * @return
	 */
	@RequestMapping(value = "/getOrgLeft",method = RequestMethod.GET)
	@ResponseBody 
	public List<Map> getOrgLeft(){
		logger.info("===================配额分配时间设置  左选框=================");
		List<Map> list = service.getOrgLeft();
		return list;
	}
	
	/**
	 * 配额分配时间设置  左选框
	 * @return
	 */
	@RequestMapping(value = "/getOrgRight",method = RequestMethod.GET)
	@ResponseBody 
	public List<Map> getOrgRight(){
		logger.info("===================配额分配时间设置  右选框=================");
		List<Map> list = service.getOrgRight();
		return list;
	}
	
	/**
	 * 配额分配时间设置  保存
	 * @param param
	 * @return
	 */
	@RequestMapping(value = "/saveOrgAndDate/{ids}/{date}",method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<ParamManageDTO> saveOrgAndDate(@RequestParam Map<String,String> param,@PathVariable String ids,@PathVariable String date){
		logger.info("==================配额分配时间设置  保存==================");
		Integer type = OemDictCodeConstants.PARAM_TYPE_03;
		service.saveDealerAndDate(ids,date,type);
        return new ResponseEntity<ParamManageDTO>(new HttpHeaders(), HttpStatus.CREATED);
	}

}
