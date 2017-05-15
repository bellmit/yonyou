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
import com.yonyou.dms.vehicle.domains.DTO.afterSales.basicDataMgr.TtWrWarrantyDTO;
import com.yonyou.dms.vehicle.service.afterSales.basicDataMgr.ClaimQualityService;
import com.yonyou.f4.mvc.annotation.TxnConn;

/**
 * 索赔质保期维护
 * @author Administrator
 *
 */
@Controller
@TxnConn
@RequestMapping("/claimType")
public class ClaimQualityController {
	private static final Logger logger = LoggerFactory.getLogger(DAOUtil.class);
	@Autowired
	ClaimQualityService   claimQualityService;
	
	/**
	 * 索赔质保期维护查询
	 */
	@RequestMapping(value="/ClaimQualitySearch",method = RequestMethod.GET)
    @ResponseBody
    public PageInfoDto queryClaimQuality(@RequestParam Map<String, String> queryParam) {
    	logger.info("==============索赔质保期维护查询=============");
        PageInfoDto pageInfoDto =claimQualityService.ClaimQualityQuery(queryParam);
        return pageInfoDto;  
    }
	/**
	 * 索赔质保期维护新增
	 */
    @RequestMapping(value ="/addClaimQuality",method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<TtWrWarrantyDTO> add(@RequestBody TtWrWarrantyDTO ptdto,UriComponentsBuilder uriCB){
    	logger.info("=====  索赔质保期维护新增=====");
    	Long id =claimQualityService.add(ptdto);
        MultiValueMap<String,String> headers = new HttpHeaders();  
        headers.set("Location", uriCB.path("/claimType/addClaimQuality").buildAndExpand(id).toUriString());  
        return new ResponseEntity<>(ptdto,headers, HttpStatus.CREATED);  
    }	 
	/**
	 * 索赔质保期维护修改
	 */
	 @RequestMapping(value = "/edit/{id}", method = RequestMethod.PUT)
	    @ResponseBody
	    public ResponseEntity<TtWrWarrantyDTO> edit(@PathVariable(value = "id") Long id,@RequestBody TtWrWarrantyDTO dto,UriComponentsBuilder uriCB){
	    	logger.info("=====索赔质保期维护修改=====");
	    	claimQualityService.edit(id,dto);
	    	MultiValueMap<String, String> headers = new HttpHeaders();
	        headers.set("Location", uriCB.path("/claimType/edit").buildAndExpand().toUriString());
	        return new ResponseEntity<TtWrWarrantyDTO>(headers, HttpStatus.CREATED);  	
	    }
	
	/**
	 * 索赔质保期维护修改信息回显
	 */
	@RequestMapping(value = "/getClaimQuality/{id}", method = RequestMethod.GET)
    @ResponseBody
    public Map<String,Object> getClaimQualityById(@PathVariable(value = "id") Long id){
    	logger.info("=====索赔质保期维护修改信息回显=====");
    	Map m=claimQualityService.getShuju(id);
        return m;
    }
	
	/**
	 * 索赔质保期维护删除
	 */
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable("id") Long id, UriComponentsBuilder uriCB) {
    	logger.info("===== 索赔质保期维护删除=====");
    	claimQualityService.delete(id);
    }
    
    /**
     * 查询所有车系集合
     */
    @RequestMapping(value="/labourList",method = RequestMethod.GET)
    @ResponseBody
    public List<Map> getAll(@RequestParam Map<String, String> queryParam){
    	logger.info("=====查询所有车系集合=====");
    	List<Map> tenantMapping = claimQualityService.getAll(queryParam);
        return tenantMapping;
    }

}
