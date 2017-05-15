package com.yonyou.dms.vehicle.controller.afterSales.warranty;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
import com.yonyou.dms.vehicle.domains.DTO.afterSales.warranty.TtWrSpecialPartDTO;
import com.yonyou.dms.vehicle.service.afterSales.warranty.SpecialPartService;
import com.yonyou.f4.mvc.annotation.TxnConn;

/**
 * 特殊零部件维护
 * @author zhanghongyi 
 *
 */
@Controller
@TxnConn
@RequestMapping("/specialPart")
public class SpecialPartController {
	private static final Logger logger = LoggerFactory.getLogger(DAOUtil.class);
	@Autowired
	SpecialPartService  specialPartService;
	
    /**
     * 查询
     */
    @RequestMapping(value="/query",method = RequestMethod.GET)
    @ResponseBody
    public PageInfoDto query(@RequestParam Map<String, String> queryParam) {
    	logger.info("----------------------特殊零部件维护-----------------------");
    	PageInfoDto pageInfoDto = specialPartService.query(queryParam);   	
        return pageInfoDto;               
    }
    
	/**
	 * 新增
	 */
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<TtWrSpecialPartDTO> add(@RequestBody TtWrSpecialPartDTO dto, UriComponentsBuilder uriCB) {
		logger.info("----------------------特殊零部件新增-----------------------");
		Long id = specialPartService.add(dto);
		MultiValueMap<String, String> headers = new HttpHeaders();
		headers.set("Location", uriCB.path("/DealerLevelMain/add").buildAndExpand(id).toUriString());
		return new ResponseEntity<>(dto, headers, HttpStatus.CREATED);
	}
	
 	/**
	 * 修改初始化
	 */
	@RequestMapping(value="/querySpecialPart/{id}",method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> queryWarrantyPartPrice(@PathVariable(value = "id") BigDecimal id) {
		 logger.info("----------------------特殊零部件修改-----------------------");
		 StringBuilder sql = new StringBuilder();
		 sql.append("select TWSP.ID,TWSP.MVS,TWSP.PT_CODE,TWSP.PT_NAME,TWSP.STATUS,TWSP.WR_PRICE,TWSP.IS_CAN_MOD \n");
		 sql.append("FROM TT_WR_SPECIAL_PART_DCS TWSP \n");
		 sql.append("where TWSP.ID = "+id+" \n");
		 Map<String, Object> mapA = OemDAOUtil.findFirst(sql.toString(), null);
		 return mapA;
	}
	
	/**
	 * 修改保存
	 */
	@RequestMapping(value = "/update", method = RequestMethod.PUT)
	@ResponseBody
	public ResponseEntity<TtWrSpecialPartDTO> update(@RequestBody TtWrSpecialPartDTO dto, UriComponentsBuilder uriCB) {
		logger.info("----------------------特殊零部件修改保存-----------------------");
		specialPartService.update(dto);
		MultiValueMap<String,String> headers = new HttpHeaders();  
	    headers.set("Location", uriCB.path("/update").buildAndExpand().toUriString());
	    return new ResponseEntity<>(headers, HttpStatus.CREATED);
	}
	
	/**
	* 获取MVS
	*/
    @RequestMapping(value="/getMVS",method = RequestMethod.GET)
    @ResponseBody
    public List<Map> getMVS(@RequestParam Map<String, String> queryParams){
    	logger.info("==========获取MVS==========");
    	List<Map> mapping = specialPartService.getMVS(queryParams);
        return mapping;
    }
    
	/**
	 * 下载
	 * @param queryParam 查询条件
	 */
    @RequestMapping(value = "/download", method = RequestMethod.GET)
	public void download(@RequestParam Map<String, String> queryParam, HttpServletRequest request,
		HttpServletResponse response) {
    	logger.info("============特殊零部件下载  ===============");
    	specialPartService.download(queryParam, request, response);
	}
}
