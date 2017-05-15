package com.yonyou.dms.repair.controller.activity;

import java.util.Map;

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
import com.yonyou.dms.repair.domains.DTO.activity.ActivityDTO;
import com.yonyou.dms.repair.service.activity.ActivityService;
import com.yonyou.f4.mvc.annotation.TxnConn;
import com.yonyou.f4.mvc.controller.BaseController;

/**
* 服务活动
* @author wantao
* @date 2017年4月18日
*/
@Controller
@TxnConn
@RequestMapping("/activity/serviceActivity")
public class ActivityController extends BaseController{
	@Autowired
	private ActivityService activityService;
	/**
	 * 查询
	* @author wantao
	* @date 2017年4月19日
	* @param queryParam
	* @return
	 */
	@RequestMapping(method=RequestMethod.GET)
	@ResponseBody
	public PageInfoDto search(@RequestParam Map<String,String> queryParam){
		return activityService.search(queryParam);
	}
	
	/**
	 * 新增
	* @author wantao
	* @date 2017年4月24日
	* @param activityDto
	* @param uriCB
	* @return
	 */
	@RequestMapping(method=RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<ActivityDTO> saveReceiveMoney(@RequestBody ActivityDTO activityDto, UriComponentsBuilder uriCB){
		String[] id=activityService.addActivity(activityDto);
        MultiValueMap<String, String> headers = new HttpHeaders();
        headers.set("Location", uriCB.path("/activity/serviceActivity/{id}").buildAndExpand((Object[])id).toUriString());
        //activityService.saveActivityTempTable(null,activityDto);
        return new ResponseEntity<ActivityDTO>(activityDto, headers, HttpStatus.CREATED);
    }
	
	/**
	 * 查询活动车辆信息
	* @author wantao
	* @date 2017年04月25日
	* @param queryParam
	* @return
	 */
	@RequestMapping(value="/activityVehicle",method=RequestMethod.GET)
	@ResponseBody
	public PageInfoDto queryActivityVehicle(@RequestParam Map<String,String> param){
		return activityService.queryActivityVehicle(param);
	}
	
	/**
	 * 查询活动车辆明细信息
	* @author wantao
	* @date 2017年04月25日
	* @param id
	* @return
	 */
	@RequestMapping(value="/{id}/VehicleDetail",method=RequestMethod.GET)
	@ResponseBody
	public PageInfoDto queryVehicleDetail(@PathVariable String id){
	    return activityService.queryVehicleDetail(id);	
	}

}
