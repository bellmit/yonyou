package com.yonyou.dms.vehicle.controller.afterSales.basicDataMgr;

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
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.util.UriComponentsBuilder;

import com.yonyou.dms.framework.DAO.DAOUtil;
import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.vehicle.domains.DTO.afterSales.basicDataMgr.TtWrForeapprovallabDTO;
import com.yonyou.dms.vehicle.service.afterSales.basicDataMgr.LabourMaintainService;
import com.yonyou.f4.mvc.annotation.TxnConn;

/**
 * 预授权维修项目维护
 * @author Administrator
 *
 */
@Controller
@TxnConn
@RequestMapping("/labourMaintain")
public class LabourMaintainController {
	private static final Logger logger = LoggerFactory.getLogger(DAOUtil.class);
	@Autowired
	LabourMaintainService  labourMaintainService;
	/**
	 * 预授权维修项目维护查询
	 */
	@RequestMapping(value="/labourMaintainSearch",method = RequestMethod.GET)
    @ResponseBody
    public PageInfoDto labourMaintainSearch(@RequestParam Map<String, String> queryParam) {
    	logger.info("============== 预授权维修项目维护查询=============");
        PageInfoDto pageInfoDto =labourMaintainService.labourMaintainQuery(queryParam);
        return pageInfoDto;  
    }
   /**
    * 得到所有车系
    */
	   @RequestMapping(value="/GetAllCheXi",method = RequestMethod.GET)
	    @ResponseBody
	    public List<Map> GetAllCheXing(@RequestParam Map<String, String> queryParams){
	    	logger.info("=====得到所有车系=====");
	    	List<Map> tenantMapping = labourMaintainService.getAllCheXing(queryParams);
	        return tenantMapping;
	    }
	   
	   /**
	    * 删除预授权维修项目
	    */
	   @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	    @ResponseStatus(HttpStatus.NO_CONTENT)
	    public void delete(@PathVariable("id") Long id, UriComponentsBuilder uriCB) {
	    	logger.info("=====删除预授权维修项目=====");
	    	labourMaintainService.delete(id);
	    }
	   
	   /**
	    * 通过查询工时信息新增
	    */
	   @RequestMapping(value="/GongShiList",method = RequestMethod.GET)
	    @ResponseBody
	    public List<Map> getAll(@RequestParam Map<String, String> queryParam){
	    	logger.info("===== 通过查询工时信息新增=====");
	    	List<Map> tenantMapping = labourMaintainService.getAll(queryParam);
	        return tenantMapping;
	    }
	   
	   /**
	    * 新增预授权维修项目
	    * @param ptdto
	    * @param uriCB
	    * @return
	    */

	   @RequestMapping(value ="/add",method = RequestMethod.POST)
	    @ResponseBody
	    public ResponseEntity<TtWrForeapprovallabDTO> addDealerPayment(@RequestBody TtWrForeapprovallabDTO ptdto,UriComponentsBuilder uriCB){
	    	logger.info("=====  新增预授权维修项目=====");
	    	Long id =labourMaintainService.add(ptdto);
	        MultiValueMap<String,String> headers = new HttpHeaders();  
	        headers.set("Location", uriCB.path("/labourMaintain/add").buildAndExpand(id).toUriString());  
	        return new ResponseEntity<>(ptdto,headers, HttpStatus.CREATED);  
	    }	 
	
	
	
	
	
	
}
