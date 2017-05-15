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
import com.yonyou.dms.vehicle.domains.DTO.afterSales.basicDataMgr.TtWrForelabourRuleDTO;
import com.yonyou.dms.vehicle.service.afterSales.preAuthorization.TtWrForelabourRuleService;
import com.yonyou.f4.mvc.annotation.TxnConn;

/**
 *  索赔预授权工时规则维护
 * @author Administrator
 *
 */
@Controller
@TxnConn
@RequestMapping("/ttWrForelabourRule")
public class TtWrForelabourRuleController {
	private static final Logger logger = LoggerFactory.getLogger(DAOUtil.class);
	@Autowired
	TtWrForelabourRuleService  ttWrForelabourRuleService;
	/**
	 * 索赔预授权工时规则维护查询
	 */
	
	@RequestMapping(value="/ttWrForelabourSearch",method = RequestMethod.GET)
    @ResponseBody
   public PageInfoDto PreclaimPreQueryOemSearch(@RequestParam Map<String, String> queryParam) {
   	logger.info("============== 索赔预授权工时规则维护查询 =============");
       PageInfoDto pageInfoDto =ttWrForelabourRuleService.TtWrForelabourRuleQuery(queryParam);
       return pageInfoDto;  
   }
	/**
	 * 查询所有授权顺序
	 * @return
	 */
	 @RequestMapping(value="/getAllLevel/{labourCode}",method = RequestMethod.GET)
	    @ResponseBody
	    public List<Map> getAll(@PathVariable(value = "labourCode") String labourCode){
	    	logger.info("=====查询所有授权顺序=====");
	    	List<Map> LevelMapping = ttWrForelabourRuleService.getAllLevel(labourCode);
	        return LevelMapping;
	    }
	 
	 @RequestMapping(value="/getLevel",method = RequestMethod.GET)
	    @ResponseBody
	    public List<Map> getLevel(){
	    	logger.info("=====查询授权顺序=====");
	    	List<Map> LevelMapping = ttWrForelabourRuleService.getLevel();
	        return LevelMapping;
	    }
	
	 /**
	  * 删除
	  * @param id
	  * @param uriCB
	  */
	  @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	    @ResponseStatus(HttpStatus.NO_CONTENT)
	    public void delete(@PathVariable("id") Long id, UriComponentsBuilder uriCB) {
	    	logger.info("===== 索赔预授权工时规则维护信息删除=====");
	    	ttWrForelabourRuleService.delete(id);
	    }
	  
	  /**
	   * 索赔预授权工时规则维护信息回显
	   */
	  @RequestMapping(value = "/getForelabourById/{id}", method = RequestMethod.GET)
	    @ResponseBody
	    public  Map getCustomerById(@PathVariable(value = "id") Long id){
	    	logger.info("=====通过id查询基本信息=====");
	    	 Map m=ttWrForelabourRuleService.getForelabourRuleById(id);
	        return m;
	    }
	  /**
	   * 新增
	   */
	    @RequestMapping(value ="/addForelabour",method = RequestMethod.POST)
	    @ResponseBody
	    public ResponseEntity<TtWrForelabourRuleDTO> addForelabour(@RequestBody TtWrForelabourRuleDTO ptdto,UriComponentsBuilder uriCB){
	    	logger.info("===== 添加索赔预授权工时规则维护=====");
	    	Long id =ttWrForelabourRuleService.addForelabourRule(ptdto);
	        MultiValueMap<String,String> headers = new HttpHeaders();  
	        headers.set("Location", uriCB.path("/ttWrForelabourRule/addForelabour").buildAndExpand(id).toUriString());  
	        return new ResponseEntity<>(ptdto,headers, HttpStatus.CREATED);  
	    }
	    
	    /**
	     * 修改
	     * @param id
	     * @param ptdto
	     * @param uriCB
	     * @return
	     */
	    @RequestMapping(value = "/modify/{id}", method = RequestMethod.PUT)
	    @ResponseBody
	    public ResponseEntity<TtWrForelabourRuleDTO> ModifyForelabour(@PathVariable(value = "id") Long id,@RequestBody TtWrForelabourRuleDTO ptdto,UriComponentsBuilder uriCB){
	    	logger.info("=====修改索赔预授权工时规则维护=====");
	    	ttWrForelabourRuleService.modifyTtWrForelabourRule(id, ptdto);
	        MultiValueMap<String,String> headers = new HttpHeaders();  
	        headers.set("Location", uriCB.path("/modify/{id}").buildAndExpand(id).toUriString());  
	        return new ResponseEntity<>(ptdto,headers, HttpStatus.CREATED);  
	    }

}
