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
import com.yonyou.dms.vehicle.domains.DTO.afterSales.basicDataMgr.TtWrForeotheritemsRuleDTO;
import com.yonyou.dms.vehicle.service.afterSales.preAuthorization.TtWrForeotheritemsRuleService;
import com.yonyou.f4.mvc.annotation.TxnConn;

/**
 * 预授权其他费用规则维护
 * @author Administrator
 *
 */
@Controller
@TxnConn
@RequestMapping("/ttWrForeotheritemsRule")
public class TtWrForeotheritemsRuleController {
	private static final Logger logger = LoggerFactory.getLogger(DAOUtil.class);
	@Autowired
	TtWrForeotheritemsRuleService  ttWrForeotheritemsRuleService;
	//预授权其他费用规则维护
	@RequestMapping(value="/ttWrForepartRuleSearch",method = RequestMethod.GET)
    @ResponseBody
   public PageInfoDto ttWrForepartRuleSearch(@RequestParam Map<String, String> queryParam) {
   	logger.info("============== 预授权配件规则维护查询 =============");
       PageInfoDto pageInfoDto =ttWrForeotheritemsRuleService.TtWrForeotheritemsRuleQuery(queryParam);
       return pageInfoDto;  
   }
	//删除
	 @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	   @ResponseStatus(HttpStatus.NO_CONTENT)
	    public void delete(@PathVariable("id") Long id, UriComponentsBuilder uriCB) {
		   logger.info("===== 删除预授权其他费用规则维护=====");
		   ttWrForeotheritemsRuleService.delete(id);
		    }
	 
	 //修改
	 @RequestMapping(value = "/modify/{id}", method = RequestMethod.PUT)
	   @ResponseBody
	   public ResponseEntity<TtWrForeotheritemsRuleDTO> ModifyForepartRule(@PathVariable(value = "id") Long id,@RequestBody TtWrForeotheritemsRuleDTO ptdto,UriComponentsBuilder uriCB){
	   	  logger.info("=====修改预授权其他费用规则维护=====");
	   	  ttWrForeotheritemsRuleService.modifyTtWrForeotheritemsRule(id, ptdto);
	       MultiValueMap<String,String> headers = new HttpHeaders();  
	       headers.set("Location", uriCB.path("/modify/{id}").buildAndExpand(id).toUriString());  
	       return new ResponseEntity<>(ptdto,headers, HttpStatus.CREATED);  
	   }
	 
	 //新增
	 @RequestMapping(value ="/addForeotheritemsRule",method = RequestMethod.POST)
	   @ResponseBody
	   public ResponseEntity<TtWrForeotheritemsRuleDTO> addForeotheritemsRule(@RequestBody TtWrForeotheritemsRuleDTO ptdto,UriComponentsBuilder uriCB){
	   	logger.info("===== 添加预授权其他费用规则维护=====");
	   	Long id =ttWrForeotheritemsRuleService.addTtWrForeotheritemsRule(ptdto);
	       MultiValueMap<String,String> headers = new HttpHeaders();  
	       headers.set("Location", uriCB.path("/ttWrForeotheritemsRule/addForeotheritemsRule").buildAndExpand(id).toUriString());  
	       return new ResponseEntity<>(ptdto,headers, HttpStatus.CREATED);  
	   }
	 
	 
	 //信息回显
	 @RequestMapping(value = "/getForeotheritemsRuleById/{id}", method = RequestMethod.GET)
	   @ResponseBody
	   public  Map getCustomerById(@PathVariable(value = "id") Long id){
	   	logger.info("=====通过id查询预授权其他费用规则维护基本信息=====");
	   	 Map m=ttWrForeotheritemsRuleService.getTtWrForeotheritemsRuleById(id);
	       return m;
	   }
	 
	 //查询所有授权顺序
	 @RequestMapping(value="/getLevel",method = RequestMethod.GET)
	    @ResponseBody
	    public List<Map> getLevel(){
	    	logger.info("=====查询授权顺序=====");
	    	List<Map> LevelMapping = ttWrForeotheritemsRuleService.getLevel();
	        return LevelMapping;
	    } 
	 //查询所有项目代码
	    @RequestMapping(value="/xiangmuList",method = RequestMethod.GET)
	    @ResponseBody
	    public List<Map> getAll(@RequestParam Map<String, String> queryParam){
	    	logger.info("=====查询所有项目代码=====");
	    	List<Map> otheritemsMapping = ttWrForeotheritemsRuleService.getAll(queryParam);
	        return otheritemsMapping;
	    }
	 
}
