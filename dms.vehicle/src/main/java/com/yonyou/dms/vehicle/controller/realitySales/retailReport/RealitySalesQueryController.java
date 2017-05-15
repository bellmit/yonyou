package com.yonyou.dms.vehicle.controller.realitySales.retailReport;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yonyou.dms.framework.DAO.DAOUtil;
import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.framework.domain.LoginInfoDto;
import com.yonyou.dms.framework.util.bean.ApplicationContextHelper;
import com.yonyou.dms.vehicle.domains.DTO.dlrinvtyManage.TtVsNvdrDTO;
import com.yonyou.dms.vehicle.service.realitySales.retailReport.RealitySalesQueryService;
import com.yonyou.f4.mvc.annotation.TxnConn;

/**
 * 零售上报Controller
 * @author DC
 *
 */
@Controller
@TxnConn
@RequestMapping("/realitySalesQuery")
public class RealitySalesQueryController {
	
	private static final Logger logger = LoggerFactory.getLogger(DAOUtil.class);
	
	@Autowired
	private RealitySalesQueryService service;
	
	/**
	 * 零售上报查询
	 * @param queryParam
	 * @return
	 */
	@RequestMapping(method = RequestMethod.GET)
	@ResponseBody
	public PageInfoDto realitySalesQuery(@RequestParam Map<String, String> queryParam){
		logger.info("============零售上报查询===============");
		/**  当前登录信息 **/
	    LoginInfoDto loginInfo = ApplicationContextHelper.getBeanByType(LoginInfoDto.class);
		PageInfoDto pageInfoDto = service.realitySalesQueryQuery(queryParam,loginInfo);
		return pageInfoDto;
	}
	
	/**
     * 根据ID 零售上报信息
     * @param id DEALER_ID
     * @return
     */
    @RequestMapping(value = "/detail/{id}/{vin}", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> findRealitySalesQueryDetail(@PathVariable(value = "id") String id,@PathVariable(value = "vin") String vin) {
    	LoginInfoDto loginInfo = ApplicationContextHelper.getBeanByType(LoginInfoDto.class);
    	logger.info("============根据ID 零售上报信息===============");
    	Map<String, Object> map = service.queryDetail(id,vin,loginInfo);
        return map;
    }
    
    /**
     * 零售上报
     */
    @RequestMapping(value = "/approved", method = RequestMethod.PUT)
    @ResponseBody
    public void realitySalesReporting(@RequestBody TtVsNvdrDTO tvnDto){
    	logger.info("============零售上报===============");
    	/**  当前登录信息 **/
	    LoginInfoDto loginInfo = ApplicationContextHelper.getBeanByType(LoginInfoDto.class);
	    service.realitySalesReporting(tvnDto,loginInfo);
    }

}
