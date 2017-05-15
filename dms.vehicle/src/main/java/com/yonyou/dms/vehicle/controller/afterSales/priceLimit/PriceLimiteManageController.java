package com.yonyou.dms.vehicle.controller.afterSales.priceLimit;

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
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.util.UriComponentsBuilder;

import com.yonyou.dms.common.domains.PO.basedata.TmLimiteCposPO;
import com.yonyou.dms.framework.DAO.DAOUtil;
import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.vehicle.domains.DTO.afterSales.basicDataMgr.TmLimiteCposDTO;
import com.yonyou.dms.vehicle.service.afterSales.priceLimit.PriceLimiteManageService;
import com.yonyou.f4.mvc.annotation.TxnConn;

/**
 *  车系限价管理 
 * @author Administrator
 *
 */
@Controller
@TxnConn
@RequestMapping("/priceLimiteManage")
public class PriceLimiteManageController {
	private static final Logger logger = LoggerFactory.getLogger(DAOUtil.class);
	@Autowired
	PriceLimiteManageService  priceLimiteManageService;
	
	/**
	 * 车系限价管理查询
	 */
	@RequestMapping(value="/priceLimiteQuery",method = RequestMethod.GET)
    @ResponseBody
   public PageInfoDto priceLimiteSearch(@RequestParam Map<String, String> queryParam) {
   	logger.info("==============  车系限价管理查询=============");
       PageInfoDto pageInfoDto =priceLimiteManageService.PriceLimiteManageQuery(queryParam);
       return pageInfoDto;  
   }
	/**
	 * 查询所有品牌代码
	 * @param queryParams
	 * @return
	 */ 
	   @RequestMapping(value="/GetAllBrandCode",method = RequestMethod.GET)
	    @ResponseBody
	    public List<Map> GetAllBrandCode(){
	    	logger.info("=====查询所有品牌代码=====");
	    	List<Map> tenantMapping = priceLimiteManageService.getBrandCode();
	        return tenantMapping;
	    }
	 /**
	  *   通过品牌代码查询所有车系代码
	  * @param queryParams
	  * @return
	  */
	   @RequestMapping(value="/GetAllCheXing/{brandCode}",method = RequestMethod.GET)
	    @ResponseBody
	    public List<Map> GetAllCheXing(@PathVariable(value = "brandCode") String brandCode){
	    	logger.info("=====  通过品牌代码查询所有车系代码=====");
	    	List<Map> tenantMapping = priceLimiteManageService.getSeriesCode(brandCode);
	        return tenantMapping;
	    }
/*	   @RequestMapping(value="/GetAllCheXing2/{brandCode}",method = RequestMethod.GET)
	    @ResponseBody
	    public List<Map> GetAllCheXing2(@PathVariable(value = "brandCode") String brandCode){
	    	logger.info("=====  通过品牌代码查询所有车系代码=====");
	    	List<Map> tenantMapping2 = priceLimiteManageService.getSeriesCode2(brandCode);
	        return tenantMapping2;
	    }*/
	   
	   /**
	    * 车系限价管理下载
	    * @param queryParam
	    * @param request
	    * @param response
	    */
		@RequestMapping(value = "/download", method = RequestMethod.GET)
		public void download2(@RequestParam Map<String, String> queryParam, HttpServletRequest request,
				HttpServletResponse response) {
			logger.info("============车系限价管理下载===============");
			priceLimiteManageService.download(queryParam, response, request);
		}
		
		/**
		 * 车系现价管理信息新增
		 */
		 @RequestMapping(value ="/addPriceLimite",method = RequestMethod.POST)
		 @ResponseBody
		    public ResponseEntity<TmLimiteCposDTO> add(@RequestBody TmLimiteCposDTO ptdto,UriComponentsBuilder uriCB){
		    	logger.info("=====   车系现价管理信息新增=====");
		    	Long id =priceLimiteManageService.add(ptdto);
		        MultiValueMap<String,String> headers = new HttpHeaders();  
		        headers.set("Location", uriCB.path("/priceLimiteManage/addPriceLimite").buildAndExpand(id).toUriString());  
		        return new ResponseEntity<TmLimiteCposDTO>(ptdto,headers, HttpStatus.CREATED);  
		    }	 	
		 
		 /**
		  * 删除车系现价管理
		  */
		    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
		    @ResponseStatus(HttpStatus.NO_CONTENT)
		    public void delete(@PathVariable("id") Long id, UriComponentsBuilder uriCB) {
		    	logger.info("===== 删除车系现价管理=====");
		    	priceLimiteManageService.delete(id);
		    }
		    
		    /**
		     * 下发
		     * @param id
		     * @param uriCB
		     */
		    @RequestMapping(value = "/xiafa/{id}", method = RequestMethod.DELETE)
		    @ResponseStatus(HttpStatus.NO_CONTENT)
		    public void xiafa(@PathVariable("id") Long id, UriComponentsBuilder uriCB) {
		    	logger.info("===== 下发车系现价管理=====");
		    	priceLimiteManageService.delete(id);
		    }
		    
		    /**
		     * 修改时的信息回显
		     */ 
	    @RequestMapping(value = "/getTmLimiteById/{id}", method = RequestMethod.GET)
		    @ResponseBody
		    public Map<String,Object> getTmLimiteById(@PathVariable(value = "id") Long id){
		    	logger.info("=====索赔其他费用设定修改时信息回显=====");
		    	TmLimiteCposPO ptPo=priceLimiteManageService.getTmLimiteById(id);
		        return ptPo.toMap();
		    }
	/*	    @RequestMapping(value="/getTmLimiteById/{id}",method = RequestMethod.GET)
		    @ResponseBody
		    public Map<String,Object> getTmLimiteById(@PathVariable(value = "id") Long id){
		    	logger.info("=====修改时的信息回显=====");
		    	Map LimiteMapping = priceLimiteManageService.getTmLimiteById(id);
		        return LimiteMapping;
		    }*/
	    
	    /**
	     * 修改信息
	     */
	    @RequestMapping(value = "/edit/{id}", method = RequestMethod.PUT)
	    @ResponseBody
	    public ResponseEntity<TmLimiteCposDTO> edit(@PathVariable(value = "id") Long id,@RequestBody TmLimiteCposDTO dto,UriComponentsBuilder uriCB){
	    	logger.info("===== 通过id进行修改车系限价管理信息=====");
	    	priceLimiteManageService.edit(id,dto);
	    	MultiValueMap<String, String> headers = new HttpHeaders();
	        headers.set("Location", uriCB.path("/priceLimiteManage/edit").buildAndExpand().toUriString());
	        return new ResponseEntity<TmLimiteCposDTO>(headers, HttpStatus.CREATED);  	
	    }
	    
	    
}
