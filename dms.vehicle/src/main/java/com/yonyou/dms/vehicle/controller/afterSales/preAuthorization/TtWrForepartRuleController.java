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
import com.yonyou.dms.vehicle.domains.DTO.afterSales.basicDataMgr.TtWrForepartRuleDTO;
import com.yonyou.dms.vehicle.service.afterSales.preAuthorization.TtWrForepartRuleService;
import com.yonyou.f4.mvc.annotation.TxnConn;

/**
 * 预授权配件规则维护
 * @author Administrator
 *
 */
@Controller
@TxnConn
@RequestMapping("/ttWrForepartRuleController")
public class TtWrForepartRuleController {
	private static final Logger logger = LoggerFactory.getLogger(DAOUtil.class);
	@Autowired
	TtWrForepartRuleService  ttWrForepartRuleService;
	
	//预授权配件规则维护查询
	@RequestMapping(value="/ttWrForepartRuleSearch",method = RequestMethod.GET)
    @ResponseBody
   public PageInfoDto ttWrForepartRuleSearch(@RequestParam Map<String, String> queryParam) {
   	logger.info("============== 预授权配件规则维护查询 =============");
       PageInfoDto pageInfoDto =ttWrForepartRuleService.TtWrForepartRuleQuery(queryParam);
       return pageInfoDto;  
   }
	  //删除预授权配件规则维护信息
   @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
   @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable("id") Long id, UriComponentsBuilder uriCB) {
	   logger.info("===== 删除预授权配件规则维护信息=====");
	   ttWrForepartRuleService.delete(id);
	    }
  //新增
   @RequestMapping(value ="/addForepartRule",method = RequestMethod.POST)
   @ResponseBody
   public ResponseEntity<TtWrForepartRuleDTO> addForelabour(@RequestBody TtWrForepartRuleDTO ptdto,UriComponentsBuilder uriCB){
   	logger.info("===== 添加预授权配件规则维护=====");
   	Long id =ttWrForepartRuleService.addForepartRuleRule(ptdto);
       MultiValueMap<String,String> headers = new HttpHeaders();  
       headers.set("Location", uriCB.path("/ttWrForepartRuleController/addForepartRule").buildAndExpand(id).toUriString());  
       return new ResponseEntity<>(ptdto,headers, HttpStatus.CREATED);  
   }
   
   
   //修改
   
   @RequestMapping(value = "/modify/{id}", method = RequestMethod.PUT)
   @ResponseBody
   public ResponseEntity<TtWrForepartRuleDTO> ModifyForepartRule(@PathVariable(value = "id") Long id,@RequestBody TtWrForepartRuleDTO ptdto,UriComponentsBuilder uriCB){
   	  logger.info("=====修改预授权配件规则维护=====");
   	  ttWrForepartRuleService.modifyTtWrForepartRule(id, ptdto);
       MultiValueMap<String,String> headers = new HttpHeaders();  
       headers.set("Location", uriCB.path("/modify/{id}").buildAndExpand(id).toUriString());  
       return new ResponseEntity<>(ptdto,headers, HttpStatus.CREATED);  
   }
   
   
   //信息回显
   @RequestMapping(value = "/getForepartRuleById/{id}", method = RequestMethod.GET)
   @ResponseBody
   public  Map getCustomerById(@PathVariable(value = "id") Long id){
   	logger.info("=====通过id查询预授权配件规则维护基本信息=====");
   	 Map m=ttWrForepartRuleService.getForepartRuleById(id);
       return m;
   }
   
   //查询所有授权顺序
	 @RequestMapping(value="/getLevel",method = RequestMethod.GET)
	    @ResponseBody
	    public List<Map> getLevel(){
	    	logger.info("=====查询授权顺序=====");
	    	List<Map> LevelMapping = ttWrForepartRuleService.getLevel();
	        return LevelMapping;
	    }
	
}
