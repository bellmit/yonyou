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
import com.yonyou.dms.vehicle.domains.DTO.afterSales.basicDataMgr.TtWrPartwarrantyDTO;
import com.yonyou.dms.vehicle.domains.PO.afterSales.basicDataMgr.TtWrPartwarrantyPO;
import com.yonyou.dms.vehicle.service.afterSales.basicDataMgr.ClaimPartService;
import com.yonyou.f4.mvc.annotation.TxnConn;

/**
 * 索赔配件质保期维护
 * @author Administrator
 *
 */
@Controller
@TxnConn
@RequestMapping("/ClaimPart")
public class ClaimPartController{
	private static final Logger logger = LoggerFactory.getLogger(DAOUtil.class);
	@Autowired
	ClaimPartService  claimPartService;
	
	/**
	 *索赔配件质保期维护查询 
	 * @param queryParam
	 * @return
	 */
	@RequestMapping(value="/ClaimPartSearch",method = RequestMethod.GET)
    @ResponseBody
    public PageInfoDto queryClaimPart(@RequestParam Map<String, String> queryParam) {
    	logger.info("==============索赔配件质保期维护查询 =============");
        PageInfoDto pageInfoDto =claimPartService.ClaimQualityQuery(queryParam);
        return pageInfoDto;  
    }
	/**
	 * 索赔配件质保期维护信息回显
	 * @param id
	 * @return
	 */
    @RequestMapping(value = "/getClaimPart/{id}", method = RequestMethod.GET)
    @ResponseBody
    public Map<String,Object> getClaimPartById(@PathVariable(value = "id") Long id){
    	logger.info("=====代码维护信息回显=====");
    	TtWrPartwarrantyPO ptPo=claimPartService.getClaimPartById(id);
        return ptPo.toMap();
    }
    /**
     * 查询所有配件代码
     */
    @RequestMapping(value="/peiJianList",method = RequestMethod.GET)
    @ResponseBody
    public List<Map> getAll(@RequestParam Map<String, String> queryParam){
    	logger.info("=====查询所有配件代码=====");
    	List<Map> tenantMapping = claimPartService.getAll(queryParam);
        return tenantMapping;
    }
    /**
     * 修改索赔配件质保期维护
     */
	 @RequestMapping(value = "/edit/{id}", method = RequestMethod.PUT)
	    @ResponseBody
	    public ResponseEntity<TtWrPartwarrantyDTO> edit(@PathVariable(value = "id") Long id,@RequestBody TtWrPartwarrantyDTO dto,UriComponentsBuilder uriCB){
	    	logger.info("=====索赔质保期维护修改=====");
	    	claimPartService.edit(id,dto);
	    	MultiValueMap<String, String> headers = new HttpHeaders();
	        headers.set("Location", uriCB.path("/ClaimPart/edit").buildAndExpand().toUriString());
	        return new ResponseEntity<TtWrPartwarrantyDTO>(headers, HttpStatus.CREATED);  	
	    }
	 /**
	  * 删除索赔配件质保期维护
	  */
	 
	    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	    @ResponseStatus(HttpStatus.NO_CONTENT)
	    public void delete(@PathVariable("id") Long id, UriComponentsBuilder uriCB) {
	    	logger.info("===== 删除索赔配件质保期维护=====");
	    	claimPartService.delete(id);
	    }
	    /**
	     * 新增索赔配件质保期维护
	     */
	    @RequestMapping(value ="/addClaimPart",method = RequestMethod.POST)
	    @ResponseBody
	    public ResponseEntity<TtWrPartwarrantyDTO> add(@RequestBody TtWrPartwarrantyDTO ptdto,UriComponentsBuilder uriCB){
	    	logger.info("=====  新增索赔配件质保期维护=====");
	    	Long id =claimPartService.add(ptdto);
	        MultiValueMap<String,String> headers = new HttpHeaders();  
	        headers.set("Location", uriCB.path("/claimType/addClaimQuality").buildAndExpand(id).toUriString());  
	        return new ResponseEntity<>(ptdto,headers, HttpStatus.CREATED);  
	    }	 

}
