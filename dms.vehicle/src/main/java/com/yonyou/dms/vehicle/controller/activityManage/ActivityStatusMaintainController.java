package com.yonyou.dms.vehicle.controller.activityManage;

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
import com.yonyou.dms.vehicle.service.activityManage.ActivityStatusMaintainService;
import com.yonyou.f4.mvc.annotation.TxnConn;

/**
*
* 
* @author liujiming
* @date 2017年3月30日
*/
@Controller
@TxnConn
@RequestMapping("/activityStatusMaintain")
public class ActivityStatusMaintainController {
	
	// 定义日志接口
	private static final Logger logger = LoggerFactory.getLogger(ActivityStatusMaintainController.class);
	
	@Autowired
	private ActivityStatusMaintainService  asmService;
	
	
	
	
	
	
	
	/**
     *车辆活动状态查询
     * @param queryParam 查询条件
     * @return pageInfoDto 查询结果
     */
    @RequestMapping(value="/statusQuery",method = RequestMethod.GET)
    @ResponseBody
    public PageInfoDto activityInitQuery(@RequestParam Map<String, String> queryParam) {
    	logger.info("============车辆活动状态查询 04==============");
    	PageInfoDto pageInfoDto = asmService.activityStatusQuery(queryParam);   	
        return pageInfoDto;
        
        
    }
	
    /**
     * 车辆活动信息
     * @param activityId
     * @return
     */
    @RequestMapping(value="/detailCarMsg/{VIN}/{ACTIVITY_ID}/{CTM_ID}/{LM_ID}",method = RequestMethod.GET)
    @ResponseBody
    public Map detailCarMsgQuery(@PathVariable(value = "VIN") String VIN,
    		@PathVariable(value = "ACTIVITY_ID") Long activityId,
    		@PathVariable(value = "CTM_ID") Long ctmId,
    		@PathVariable(value = "LM_ID") String lmId) {
    	logger.info("============ 明细（车辆信息）04==============");
    	Map map = asmService.getDetailCarMsgQuery(VIN, activityId);   	
        return map;   
    }
	
    /**
     * 车主信息
     * @param activityId
     * @return
     */
    @RequestMapping(value="/detailGTMMsg/{VIN}/{ACTIVITY_ID}/{CTM_ID}/{LM_ID}",method = RequestMethod.GET)
    @ResponseBody
    public Map detailGTMMsgQuery(@PathVariable(value = "VIN") String VIN,
    		@PathVariable(value = "ACTIVITY_ID") Long activityId,
    		@PathVariable(value = "CTM_ID") Long ctmId,
    		@PathVariable(value = "LM_ID") String lmId) {
    	logger.info("============ 明细（车主信息）04==============");
    	Map map = asmService.getDetailGTMMsgQuery(ctmId);   	
        return map;   
    }
    
    /**
     * 联系人信息
     * @param activityId
     * @return
     */
    @RequestMapping(value="/detailLinkManMsg/{VIN}/{ACTIVITY_ID}/{CTM_ID}/{LM_ID}",method = RequestMethod.GET)
    @ResponseBody
    public Map detailLinkManMsgQuery(@PathVariable(value = "VIN") String VIN,
    		@PathVariable(value = "ACTIVITY_ID") Long activityId,
    		@PathVariable(value = "CTM_ID") Long ctmId,
    		@PathVariable(value = "LM_ID") String lmId) {
    	logger.info("============ 明细（联系人信息）04==============");
    	Map map = asmService.getDetailLinkManMsgQuery(lmId);   	
        return map;   
    }
	
}










