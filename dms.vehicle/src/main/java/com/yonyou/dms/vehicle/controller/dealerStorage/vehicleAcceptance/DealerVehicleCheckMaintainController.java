package com.yonyou.dms.vehicle.controller.dealerStorage.vehicleAcceptance;

import java.util.List;
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

import com.yonyou.dms.framework.DAO.DAOUtil;
import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.framework.domain.LoginInfoDto;
import com.yonyou.dms.framework.util.bean.ApplicationContextHelper;
import com.yonyou.dms.vehicle.domains.DTO.checkManagement.TtVsInspectionDTO;
import com.yonyou.dms.vehicle.service.dealerStorage.vehicleAcceptance.DealerVehicleCheckMaintainService;
import com.yonyou.f4.mvc.annotation.TxnConn;

/**
 * 车辆验收Controller
 * @author yh135
 *
 */
@Controller
@TxnConn
@RequestMapping("/dealerCheckMaintain")
public class DealerVehicleCheckMaintainController {
	
	private static final Logger logger = LoggerFactory.getLogger(DAOUtil.class);
	
	@Autowired
	private DealerVehicleCheckMaintainService service;
	
	
	/**
	 * 待验收车辆查询
	 * @author DC
	 * @date 2017年2月10日
	 * @param queryParam
	 * @return
	 */
	@RequestMapping(method = RequestMethod.GET)
	@ResponseBody
	public PageInfoDto queryList(@RequestParam Map<String, String> queryParam) {
		logger.info("============待验收车辆查询===============");
		/** 当前登录信息 */
	    LoginInfoDto loginInfo = ApplicationContextHelper.getBeanByType(LoginInfoDto.class);
		PageInfoDto pageInfoDto = service.queryList(queryParam,loginInfo);
		return pageInfoDto;
	}
	
	/**
     * 根据ID 获取车辆验收详细信息(车厂端)
     * @param id VEHICLE_ID
     * @return
     */

    @RequestMapping(value = "/dealer/detail/{id}", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> findDealerCheckMaintainDetail(@PathVariable(value = "id") Long id) {
    	/**当前登录信息**/
    	LoginInfoDto loginInfo = ApplicationContextHelper.getBeanByType(LoginInfoDto.class);
    	logger.info("============根据ID获取车辆验收详细信息 ===============");
    	Map<String, Object> map = service.queryDetail(id,loginInfo);
        return map;
    }
    
    /**
     * 车辆验收
     * @param tviDTO
     */
    @RequestMapping(value = "/dealer/detail/acceptance", method = RequestMethod.PUT)
	@ResponseStatus(HttpStatus.NO_CONTENT)
    public void dealerVehicleDtialCheck(@RequestBody @Valid TtVsInspectionDTO tviDTO) {
    	logger.info("============车辆验收===============");
    	/**  当前登录信息 **/
	    LoginInfoDto loginInfo = ApplicationContextHelper.getBeanByType(LoginInfoDto.class);
	    logger.debug("userIds:" + tviDTO.getVehicleId());
	    service.dealerVehicleDtialCheck(tviDTO,loginInfo);
    	
    }
    
    /**
     * 生成实际到车时间
     * @author DC
     * @date 2017年3月12日
     * @return
     */
    @SuppressWarnings("rawtypes")
	@RequestMapping(value="/hour",method = RequestMethod.GET)
    @ResponseBody
    public List<Map> getHourList(){
    	logger.info("=====小时加载=====");
    	List<Map> tenantMapping = service.getHourList();
        return tenantMapping;
    }
	
}
