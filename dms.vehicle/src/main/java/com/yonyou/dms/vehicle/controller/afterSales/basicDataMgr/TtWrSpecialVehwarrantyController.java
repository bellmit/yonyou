package com.yonyou.dms.vehicle.controller.afterSales.basicDataMgr;

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
import com.yonyou.dms.vehicle.domains.DTO.afterSales.basicDataMgr.TtWrSpecialVehwarrantyDTO;
import com.yonyou.dms.vehicle.domains.PO.afterSales.basicDataMgr.TtWrSpecialVehwarrantyPO;
import com.yonyou.dms.vehicle.service.afterSales.basicDataMgr.TtWrSpecialVehwarrantyService;
import com.yonyou.f4.mvc.annotation.TxnConn;
/**
 * 特殊车辆质保期维护
 * @author Administrator
 *
 */
@Controller
@TxnConn
@RequestMapping("/specialVehwarranty")
public class TtWrSpecialVehwarrantyController {
	private static final Logger logger = LoggerFactory.getLogger(DAOUtil.class);
	@Autowired
	TtWrSpecialVehwarrantyService  ttWrSpecialVehwarrantyService;
	
	/**
	 * 特殊车辆质保期维护查询
	 * @author Administrator
	 *
	 */
	@RequestMapping(value="/specialVehwarrantySearch",method = RequestMethod.GET)
    @ResponseBody
    public PageInfoDto specialVehwarrantySearch(@RequestParam Map<String, String> queryParam) {
    	logger.info("==============特殊车辆质保期维护查询=============");
        PageInfoDto pageInfoDto =ttWrSpecialVehwarrantyService.SpecialVehwarrantyQuery(queryParam);
        return pageInfoDto;  
    }
	
	/**
	 * 新增特殊车辆质保期维护数据
	 */
	 @RequestMapping(value ="/addspecialVehwarranty",method = RequestMethod.POST)
	    @ResponseBody
	    public ResponseEntity<TtWrSpecialVehwarrantyDTO> addspecialVehwarranty(@RequestBody TtWrSpecialVehwarrantyDTO ptdto,UriComponentsBuilder uriCB){
	    	logger.info("===== 新增索赔其他费用设定=====");
	    	ttWrSpecialVehwarrantyService.add(ptdto);
	        MultiValueMap<String,String> headers = new HttpHeaders();  
	        return new ResponseEntity<>(ptdto,headers, HttpStatus.CREATED);  
	    }	
	 /**
	  *特殊车辆质保期维护回显
	  */
	 @RequestMapping(value = "/specialVehwarrantyById/{id}", method = RequestMethod.GET)
	    @ResponseBody
	    public Map<String,Object> getOtherFeeById(@PathVariable(value = "id") Long id){
	    	logger.info("=====特殊车辆质保期维护回显=====");
	    	TtWrSpecialVehwarrantyPO ptPo=ttWrSpecialVehwarrantyService.getSpecialVehwarrantyById(id);
	        return ptPo.toMap();
	    }
	 
	 
	 
	 
	 
	 /**
	  * 特殊车辆质保期维护修改
	  */
	 @RequestMapping(value = "/edit/{id}", method = RequestMethod.PUT)
	    @ResponseBody
	    public ResponseEntity<TtWrSpecialVehwarrantyDTO> edit(@PathVariable(value = "id") Long id,@RequestBody TtWrSpecialVehwarrantyDTO dto,UriComponentsBuilder uriCB){
	    	logger.info("===== 特殊车辆质保期维护修改=====");
	    	ttWrSpecialVehwarrantyService.edit(id, dto);
	    	MultiValueMap<String, String> headers = new HttpHeaders();
	        headers.set("Location", uriCB.path("/specialVehwarranty/edit").buildAndExpand().toUriString());
	        return new ResponseEntity<TtWrSpecialVehwarrantyDTO>(headers, HttpStatus.CREATED);  	
	    }
	 
	 
	 
	 
	 /**
	  * 特殊车辆质保期维护删除
	  */
	 @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	    @ResponseStatus(HttpStatus.NO_CONTENT)
	    public void delete(@PathVariable("id") Long id, UriComponentsBuilder uriCB) {
	    	logger.info("=====特殊车辆质保期维护删除=====");
	    	ttWrSpecialVehwarrantyService.delete(id);
	    }
	
	
}
