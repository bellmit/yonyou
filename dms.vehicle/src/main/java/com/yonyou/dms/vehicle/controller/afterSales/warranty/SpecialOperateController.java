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
import com.yonyou.dms.vehicle.domains.DTO.afterSales.warranty.TtWrOperateDTO;
import com.yonyou.dms.vehicle.service.afterSales.warranty.SpecialOperateService;
import com.yonyou.f4.mvc.annotation.TxnConn;

/**
 * 特殊操作代码维护
 * @author zhanghongyi 
 *
 */
@Controller
@TxnConn
@RequestMapping("/specialOperate")
public class SpecialOperateController {
	private static final Logger logger = LoggerFactory.getLogger(DAOUtil.class);
	@Autowired
	SpecialOperateService  specialOperateService;
	
    /**
     * 查询
     */
    @RequestMapping(value="/query",method = RequestMethod.GET)
    @ResponseBody
    public PageInfoDto query(@RequestParam Map<String, String> queryParam) {
    	logger.info("----------------------特殊操作代码维护-----------------------");
    	PageInfoDto pageInfoDto = specialOperateService.query(queryParam);   	
        return pageInfoDto;               
    }
    
	/**
	 * 新增
	 */
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<TtWrOperateDTO> add(@RequestBody TtWrOperateDTO dto, UriComponentsBuilder uriCB) {
		logger.info("----------------------特殊操作代码新增-----------------------");
		specialOperateService.add(dto);
		MultiValueMap<String,String> headers = new HttpHeaders();  
	    headers.set("Location", uriCB.path("/add").buildAndExpand().toUriString());
	    return new ResponseEntity<>(headers, HttpStatus.CREATED);
	}
	
 	/**
	 * 修改初始化
	 */
	@RequestMapping(value="/querySpecialOperate/{id}",method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> querySpecialOperate(@PathVariable(value = "id") BigDecimal id) {
		 logger.info("----------------------特殊操作代码修改-----------------------");
		 StringBuilder sql = new StringBuilder();
		 sql.append("SELECT TWO.ID,TWO.OPT_CODE,TWO.OPT_NAME_EN,TWO.OPT_NAME_CN,TWO.INVOICE_CODE, \n");
		 sql.append("case when  TWO.IS_SP=10041001 then 10011001 else 10011002 end STATUS \n");
		 sql.append("FROM TT_WR_OPERATE_DCS TWO \n");
		 sql.append("where TWO.ID = "+id+" \n");
		 Map<String, Object> mapA = OemDAOUtil.findFirst(sql.toString(), null);
		 return mapA;
	}
	
	/**
	 * 修改保存
	 */
	@RequestMapping(value = "/update", method = RequestMethod.PUT)
	@ResponseBody
	public ResponseEntity<TtWrOperateDTO> update(@RequestBody TtWrOperateDTO dto, UriComponentsBuilder uriCB) {
		logger.info("----------------------特殊操作代码修改保存-----------------------");
		specialOperateService.update(dto);
		MultiValueMap<String,String> headers = new HttpHeaders();  
	    headers.set("Location", uriCB.path("/update").buildAndExpand().toUriString());
	    return new ResponseEntity<>(headers, HttpStatus.CREATED);
	}
    
	/**
	 * 下载
	 * @param queryParam 查询条件
	 */
    @RequestMapping(value = "/download", method = RequestMethod.GET)
	public void download(@RequestParam Map<String, String> queryParam, HttpServletRequest request,
		HttpServletResponse response) {
    	logger.info("============特殊操作代码下载  ===============");
    	specialOperateService.download(queryParam, request, response);
	}
}
