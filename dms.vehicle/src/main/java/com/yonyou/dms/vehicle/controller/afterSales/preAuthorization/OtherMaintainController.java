package com.yonyou.dms.vehicle.controller.afterSales.preAuthorization;

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
import com.yonyou.dms.vehicle.domains.DTO.afterSales.basicDataMgr.TtWrForeapprovalotheritemDTO;
import com.yonyou.dms.vehicle.service.afterSales.preAuthorization.OtherMaintainService;
import com.yonyou.f4.mvc.annotation.TxnConn;

/**
 * 预授权其他费用维护
 * @author Administrator
 *
 */
@Controller
@TxnConn
@RequestMapping("/otherMaintain")
public class OtherMaintainController {
	private static final Logger logger = LoggerFactory.getLogger(DAOUtil.class);
	@Autowired
	OtherMaintainService  otherMaintainService;
	
	/**
	 *  预授权其他费用维护查询
	 */
	@RequestMapping(value="/otherMaintainSearch",method = RequestMethod.GET)
    @ResponseBody
    public PageInfoDto otherMaintainSearch(@RequestParam Map<String, String> queryParam) {
    	logger.info("==============预授权其他费用维护查询=============");
        PageInfoDto pageInfoDto =otherMaintainService.OtherMaintainQuery(queryParam);
        return pageInfoDto;  
    }
	
	/**
	 * 查询所有项目名称
	 */
	@RequestMapping(value="/getAll",method = RequestMethod.GET)
    @ResponseBody
    public  List<Map> getOtherMaintain(){
		logger.info("==============查询所有项目名称=============");
		List<Map> getYears=otherMaintainService.getAll();
		return getYears;
	}
	/**
	 * 删除
	 */
	  @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	    @ResponseStatus(HttpStatus.NO_CONTENT)
	    public void delete(@PathVariable("id") Long id, UriComponentsBuilder uriCB) {
	    	logger.info("===== 预授权其他费用维护删除=====");
	    	otherMaintainService.delete(id);
	    }
	  /**
	   * 新增
	   */
	  @RequestMapping(value ="/addOtherMaintain",method = RequestMethod.POST)
	    @ResponseBody
	    public ResponseEntity<TtWrForeapprovalotheritemDTO> addOtherMaintains(@RequestBody TtWrForeapprovalotheritemDTO ptdto,UriComponentsBuilder uriCB){
	    	logger.info("=====  新增预授权其他费用维护=====");
	    	Long id =otherMaintainService.add(ptdto);
	        MultiValueMap<String,String> headers = new HttpHeaders();  
	        headers.set("Location", uriCB.path("/otherMaintain/addOtherMaintain").buildAndExpand(id).toUriString());  
	        return new ResponseEntity<>(ptdto,headers, HttpStatus.CREATED);  
	    }
	
	
	
}
