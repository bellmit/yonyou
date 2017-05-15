package com.yonyou.dms.vehicle.controller.afterSales.warranty;

import java.math.BigDecimal;
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

import com.yonyou.dms.framework.DAO.DAOUtil;
import com.yonyou.dms.framework.DAO.OemDAOUtil;
import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.vehicle.domains.DTO.afterSales.warranty.TtWrWorrkhourLevelDTO;
import com.yonyou.dms.vehicle.service.afterSales.warranty.WorkHourLevelService;
import com.yonyou.f4.mvc.annotation.TxnConn;

/**
 * 工时等级维护
 * @author zhanghongyi 
 *
 */
@Controller
@TxnConn
@RequestMapping("/workHourLevel")
public class WorkHourLevelController {
	private static final Logger logger = LoggerFactory.getLogger(DAOUtil.class);
	@Autowired
	WorkHourLevelService  workHourLevelService;
	
    /**
     * 查询
     */
    @RequestMapping(value="/query",method = RequestMethod.GET)
    @ResponseBody
    public PageInfoDto query(@RequestParam Map<String, String> queryParam) {
    	logger.info("----------------------工时等级维护-----------------------");
    	PageInfoDto pageInfoDto = workHourLevelService.query(queryParam);   	
        return pageInfoDto;               
    }
    
	/**
	 * 新增
	 */
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<TtWrWorrkhourLevelDTO> add(@RequestBody TtWrWorrkhourLevelDTO dto, UriComponentsBuilder uriCB) {
		logger.info("----------------------工时等级新增-----------------------");
		Long id = workHourLevelService.add(dto);
		MultiValueMap<String, String> headers = new HttpHeaders();
		headers.set("Location", uriCB.path("/DealerLevelMain/add").buildAndExpand(id).toUriString());
		return new ResponseEntity<>(dto, headers, HttpStatus.CREATED);
	}
	
 	/**
	 * 修改初始化
	 */
	@RequestMapping(value="/queryWorkHourLevel/{id}",method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> queryWorkHourLevel(@PathVariable(value = "id") BigDecimal id) {
		 logger.info("----------------------工时等级修改-----------------------");
		 StringBuilder sql = new StringBuilder();
		 sql.append("select TWWL.ID,TWWL.DER_LEVEL,TWWL.WT_CODE,TWWT.WT_NAME,TWWL.WORK_PRICE, \n");
		 sql.append("TWWL.STATUS,TWWL.REMARK \n");
		 sql.append("from TT_WR_WORRKHOUR_LEVEL_DCS TWWL LEFT OUTER JOIN  TT_WR_WARRANTY_TYPE_DCS TWWT on TWWL.WT_ID=TWWT.ID \n");
		 sql.append("where TWWL.ID = "+id+" \n");
		 Map<String, Object> mapA = OemDAOUtil.findFirst(sql.toString(), null);
		 return mapA;
	}
	
	/**
	 * 修改保存
	 */
	@RequestMapping(value = "/update", method = RequestMethod.PUT)
	@ResponseBody
	public ResponseEntity<TtWrWorrkhourLevelDTO> update(@RequestBody TtWrWorrkhourLevelDTO dto, UriComponentsBuilder uriCB) {
		logger.info("----------------------工时等级修改保存-----------------------");
		workHourLevelService.update(dto);
		MultiValueMap<String,String> headers = new HttpHeaders();  
	    headers.set("Location", uriCB.path("/update").buildAndExpand().toUriString());
	    return new ResponseEntity<>(headers, HttpStatus.CREATED);
	}
	
	/**
	* 获取保修类型
	*/
    @RequestMapping(value="/getWarrantyType",method = RequestMethod.GET)
    @ResponseBody
    public List<Map> getWarrantyType(@RequestParam Map<String, String> queryParams){
    	logger.info("==========获取保修类型==========");
    	List<Map> mapping = workHourLevelService.getWarrantyType(queryParams);
        return mapping;
    }
}
