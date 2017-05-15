package com.yonyou.dms.vehicle.controller.vehicleAllot;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.vehicle.service.vehicleAllot.VehicleAllotApproveService;
import com.yonyou.f4.mvc.annotation.TxnConn;

/**
 * 车辆调拨审批
 * @author Administrator
 *
 */
@Controller
@TxnConn
@RequestMapping("/vehicleAllotMange/approve")
public class VehicleAllotApproveController {
	
	@Autowired
	private VehicleAllotApproveService approveService;
	
	private static final Logger logger = LoggerFactory.getLogger(DealerAllotApplyController.class);
	
	/**
	 * 首页列表加载
	 * @param param
	 * @return
	 */
	@RequestMapping(method = RequestMethod.GET)
	@ResponseBody
    public PageInfoDto search(@RequestParam Map<String, String> param) {
		logger.info("==================车辆调拨审批查询================");
		PageInfoDto pageInfoDto = approveService.searchVehicleAllotApprove(param);
		return pageInfoDto;   	
    }
	
	/**
	 * 审批
	 * @param ids
	 * @param opinion 审批意见
	 * @param result 1为同意  0为驳回
	 * @return
	 * @throws Exception 
	 */
    @RequestMapping(value = "/checkStatus", method = RequestMethod.GET)
    @ResponseBody
	public Map<String, String> checkStatus(@RequestParam Map<String,String> param) throws Exception{
		logger.info("==================车辆调拨审批================");
		String[] ids = param.get("ids").split(",");
		String opinion = param.get("opinion");
		String result = param.get("result");
    	Map<String, String> flag = approveService.checkStatus(ids,opinion,result);
		return flag;		
	}

	/**
	 * 下拉框选择品牌
	 * @param params
	 * @return
	 */
    @RequestMapping(value="/selectBrandName",method = RequestMethod.GET)
    @ResponseBody
	public List<Map> selectBrandName(@RequestParam Map<String,String> params){
		List<Map> list = approveService.selectBrandName(params);
		return list;	
	}
	
    /**
     * 下拉框选择车系
     * @param params
     * @return
     */
    @RequestMapping(value="/selectSeriesName",method = RequestMethod.GET)
    @ResponseBody
	public List<Map> selectSeriesName(@RequestParam Map<String,String> params){
		List<Map> list = approveService.selectSeriesName(params);
		return list;
		
	}
    
    /**
     * 下拉框选择车款
     * @param params
     * @return
     */
    @RequestMapping(value="/selectGroupName",method = RequestMethod.GET)
    @ResponseBody
    public List<Map> selectGroupName(@RequestParam Map<String,String> params){
		List<Map> list = approveService.selectGroupName(params);
		return list;
    }
    
    /**
     * 下拉框选择年款
     * @param params
     * @return
     */
    @RequestMapping(value="/selectModelYear",method = RequestMethod.GET)
    @ResponseBody
    public List<Map> selectModelYear(@RequestParam Map<String,String> params){
		List<Map> list = approveService.selectModelYear(params);
		return list;
    } 
    
    /**
     * 下拉框选择颜色
     * @param params
     * @return
     */
    @RequestMapping(value="/selectColorName",method = RequestMethod.GET)
    @ResponseBody
    public List<Map> selectColorName(@RequestParam Map<String,String> params){
		List<Map> list = approveService.selectColorName(params);
		return list;
    } 
    
    /**
     * 下拉框选择内饰
     * @param params
     * @return
     */
    @RequestMapping(value="/selectTrimName",method = RequestMethod.GET)
    @ResponseBody
    public List<Map> selectTrimName(@RequestParam Map<String,String> params){
		List<Map> list = approveService.selectTrimName(params);
		return list;
    }
	
}
