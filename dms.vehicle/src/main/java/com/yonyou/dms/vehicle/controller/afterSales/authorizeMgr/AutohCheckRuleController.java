package com.yonyou.dms.vehicle.controller.afterSales.authorizeMgr;

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
import com.yonyou.dms.vehicle.domains.DTO.afterSales.basicDataMgr.TtWrAutoRuleDTO;
import com.yonyou.dms.vehicle.domains.PO.afterSales.basicDataMgr.TtWrAutoRulePO;
import com.yonyou.dms.vehicle.service.afterSales.authorizeMgr.AutohCheckRuleService;
import com.yonyou.f4.mvc.annotation.TxnConn;

@Controller
@TxnConn
@RequestMapping("/autohCheckRule")
public class AutohCheckRuleController {
	private static final Logger logger = LoggerFactory.getLogger(DAOUtil.class);
	@Autowired
	AutohCheckRuleService  autohCheckRuleService;
	/**
	 * 索赔自动审核规则管理查询
	 */
	@RequestMapping(value="/autohCheckRuleSearch",method = RequestMethod.GET)
    @ResponseBody
    public PageInfoDto autohCheckRuleSearch(@RequestParam Map<String, String> queryParam) {
    	logger.info("==============索赔自动审核规则管理查询=============");
        PageInfoDto pageInfoDto =autohCheckRuleService.AutohCheckRuleQuery(queryParam);
        return pageInfoDto;  
    }
	/**
	 * 启动
	 */
	 @RequestMapping(value = "/qidong/{id}", method = RequestMethod.DELETE)
	    @ResponseStatus(HttpStatus.NO_CONTENT)
	    public void qidong(@PathVariable("id") Long id, UriComponentsBuilder uriCB) {
	    	logger.info("=====启动=====");
	    	autohCheckRuleService.qidong(id);
	    }
	
	/**
	 * 停止
	 */
	 @RequestMapping(value = "/tingzhi/{id}", method = RequestMethod.DELETE)
	    @ResponseStatus(HttpStatus.NO_CONTENT)
	    public void tingzhi(@PathVariable("id") Long id, UriComponentsBuilder uriCB) {
	    	logger.info("===== 停止=====");
	    	autohCheckRuleService.tingzhi(id);
	    }
	 /**
	  * 查询所有授权顺序
	  */
	 @RequestMapping(value="/getAllLevel/{id}",method = RequestMethod.GET)
	    @ResponseBody
	    public List<Map> getAll(@RequestParam Map<String, String> queryParam,@PathVariable("id") Long id){
	    	logger.info("=====查询所有授权顺序=====");
	    	List<Map> LevelMapping = autohCheckRuleService.getAllLevel(queryParam,id);
	        return LevelMapping;
	    }
	 /**
	  * 通过id进行信息回显
	  */
	    @RequestMapping(value = "/getLevelById/{id}", method = RequestMethod.GET)
	    @ResponseBody
	    public Map<String,Object> getDirectCustomerById(@PathVariable(value = "id") Long id){
	    	logger.info("=====通过id进行信息回显=====");
	    	TtWrAutoRulePO ptPo=autohCheckRuleService.getLevelById(id);
	        return ptPo.toMap();
	    }
	    /**
	     * 修改
	     */
	    @RequestMapping(value = "/edit/{id}", method = RequestMethod.PUT)
	    @ResponseBody
	    public ResponseEntity<TtWrAutoRuleDTO> edit(@PathVariable(value = "id") Long id,@RequestBody TtWrAutoRuleDTO dto,UriComponentsBuilder uriCB){
	    	logger.info("===== 通过id进行修改授权顺序=====");
	    	autohCheckRuleService.edit(id,dto);
	    	MultiValueMap<String, String> headers = new HttpHeaders();
	        headers.set("Location", uriCB.path("/autohCheckRule/edit").buildAndExpand().toUriString());
	        return new ResponseEntity<TtWrAutoRuleDTO>(headers, HttpStatus.CREATED);  	
	    }

}
