package com.yonyou.dms.vehicle.controller.activityManage;

import java.util.Map;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.vehicle.domains.DTO.activityManage.ActivityManageDTO;
import com.yonyou.dms.vehicle.service.activityManage.ActivityDealerMaintainService;
import com.yonyou.f4.mvc.annotation.TxnConn;

/**
*
* 
* @author liujiming
* @date 2017年3月30日
*/
@Controller
@TxnConn
@RequestMapping("/activityDealerMaintain")
public class ActivityDealerMaintainController {
	
	// 定义日志接口
	private static final Logger logger = LoggerFactory.getLogger(ActivityDealerMaintainController.class);
	
	@Autowired
	private ActivityDealerMaintainService admService;
	
	public static Long  acvtivityIdTyd; //用于保存活动id
	
	/**
     *活动经销商管理 查询	
     * @param queryParam 查询条件
     * @return pageInfoDto 查询结果
     */
    @RequestMapping(value="/dealerManageQuery",method = RequestMethod.GET)
    @ResponseBody
    public PageInfoDto activityDealerManageQuery(@RequestParam Map<String, String> queryParam) {
    	logger.info("============ 活动经销商维护  02==============");
    	PageInfoDto pageInfoDto = admService.getDealerManageQuery(queryParam);   	
        return pageInfoDto;
                
    }
    
    /**
     * 经销商维护  特约店查询
     * @param activityId
     * @return
     */
    @RequestMapping(value="/activityDealerQuery/{ACTIVITY_ID}",method = RequestMethod.GET)
    @ResponseBody
    public PageInfoDto activityDealerEditQuery(@PathVariable(value = "ACTIVITY_ID") Long activityId,
    		@RequestParam Map<String, String> queryParam) {
    	logger.info("============活动经销商维护    经销商列表  02==============");
    	acvtivityIdTyd = activityId;
    	PageInfoDto pageInfoDto = admService.getDealerEditQuery(queryParam, activityId);   	
        return pageInfoDto;    
    }
    
    /**
     * 经销商维护  特约店查询
     * @param activityId
     * @return
     */
    @RequestMapping(value="/activityQueryAdd",method = RequestMethod.GET)
    @ResponseBody
    public PageInfoDto activityDealerAddQuery(@RequestParam Map<String, String> queryParam) {
    	logger.info("============活动经销商维护    新增 查询  02==============");
    	PageInfoDto pageInfoDto = admService.getDealerAddQuery(queryParam,acvtivityIdTyd);  	
        return pageInfoDto;    
    }
    
    
    /**
     * 其他项目 删除
     * @param activityId
     * @return
     */    
    @RequestMapping(value = "/dealerDelete", method = RequestMethod.PUT)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void activityDealerDelete(@RequestBody @Valid ActivityManageDTO amDto) {
    	logger.info("============ 活动经销商维护   删除02==============");
    	admService.activityDealerDelete(amDto);
    }
    
    
    /**
     * 其他项目维护 确定
     * @param activityId
     * @return
     */
    @RequestMapping(value="/activityDealer/addSave",method = RequestMethod.PUT)
    @ResponseBody
    public void activityDealerAddSave(@RequestParam Map<String, String> queryParam,@RequestBody @Valid ActivityManageDTO amDto) {
    	logger.info("============  活动经销商维护  确定02==============");
    	admService.activityDealerAddSave(amDto, acvtivityIdTyd);
    } 
    
}















