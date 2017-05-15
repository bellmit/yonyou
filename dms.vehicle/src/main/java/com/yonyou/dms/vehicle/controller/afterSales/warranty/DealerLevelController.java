package com.yonyou.dms.vehicle.controller.afterSales.warranty;

import java.math.BigDecimal;
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

import com.yonyou.dms.framework.DAO.DAOUtil;
import com.yonyou.dms.framework.DAO.OemDAOUtil;
import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.vehicle.domains.DTO.afterSales.warranty.TtWrDealerLevelDTO;
import com.yonyou.dms.vehicle.service.afterSales.warranty.DealerLevelMaintainService;
import com.yonyou.f4.mvc.annotation.TxnConn;

/**
 * 经销商等级维护
 * @author zhiahongmiao 
 *
 */
@Controller
@TxnConn
@RequestMapping("/DealerLevelMain")
public class DealerLevelController {
	private static final Logger logger = LoggerFactory.getLogger(DAOUtil.class);
	@Autowired
	DealerLevelMaintainService  dealerLevelMaintainService;
	
    /**
     * 查询
     */
    @RequestMapping(value="/Query",method = RequestMethod.GET)
    @ResponseBody
    public PageInfoDto query(@RequestParam Map<String, String> queryParam) {
    	logger.info("----------------------经销商等级维护-----------------------");
    	PageInfoDto pageInfoDto = dealerLevelMaintainService.DealerLevelQuery(queryParam);   	
        return pageInfoDto;               
    }
    
	/**
	 * 新增
	 */
	@RequestMapping(value = "/Add", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<TtWrDealerLevelDTO> add(@RequestBody TtWrDealerLevelDTO dto, UriComponentsBuilder uriCB) {
		logger.info("----------------------经销商等级新增-----------------------");
		Long id = dealerLevelMaintainService.add(dto);
		MultiValueMap<String, String> headers = new HttpHeaders();
		headers.set("Location", uriCB.path("/DealerLevelMain/Add").buildAndExpand(id).toUriString());
		return new ResponseEntity<>(dto, headers, HttpStatus.CREATED);
	}
	
 	/**
	 * 修改初始化
	 */
	@RequestMapping(value="/QueryDealerLevel/{id}",method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> queryDealerLevel(@PathVariable(value = "id") BigDecimal id) {
		 logger.info("----------------------经销商等级修改-----------------------");
		 StringBuilder sql = new StringBuilder();
		 sql.append("select td.DEALER_CODE,td.DEALER_SHORTNAME DEALER_NAME,dl.* from tt_wr_dealer_level_dcs dl \n");
		 sql.append("left join tm_dealer td on dl.DEALER_ID = td.DEALER_ID where dl.ID = "+id+" \n");
		 Map<String, Object> mapA = OemDAOUtil.findFirst(sql.toString(), null);
		 return mapA;
	}
	
	/**
	 * 修改保存
	 */
	@RequestMapping(value = "/Update", method = RequestMethod.PUT)
	@ResponseBody
	public ResponseEntity<TtWrDealerLevelDTO> update(@RequestBody TtWrDealerLevelDTO dto, UriComponentsBuilder uriCB) {
		logger.info("----------------------经销商等级修改保存-----------------------");
		dealerLevelMaintainService.update(dto);
		MultiValueMap<String,String> headers = new HttpHeaders();  
	    headers.set("Location", uriCB.path("/Update").buildAndExpand().toUriString());
	    return new ResponseEntity<>(headers, HttpStatus.CREATED);
	}
}
