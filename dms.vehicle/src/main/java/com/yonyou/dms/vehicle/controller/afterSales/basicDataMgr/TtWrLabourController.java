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
import com.yonyou.dms.vehicle.domains.DTO.afterSales.basicDataMgr.TtWrLabourDTO;
import com.yonyou.dms.vehicle.domains.PO.afterSales.basicDataMgr.TtWrLabourPO;
import com.yonyou.dms.vehicle.service.afterSales.basicDataMgr.TtWrLabourService;
import com.yonyou.f4.mvc.annotation.TxnConn;

/**
 * 索赔工时维护
 * @author Administrator
 *
 */
@Controller
@TxnConn
@RequestMapping("/wrLabour")
public class TtWrLabourController {
	private static final Logger logger = LoggerFactory.getLogger(DAOUtil.class);
	@Autowired
	TtWrLabourService  ttWrLabourService;
	/**
	 * 查询所有车系集合
	 */
    @RequestMapping(value="/labourList",method = RequestMethod.GET)
    @ResponseBody
    public List<Map> getBigOrg(@RequestParam Map<String, String> queryParams){
    	logger.info("=====查询所有车系集合=====");
    	List<Map> tenantMapping = ttWrLabourService.getlabour(queryParams);
        return tenantMapping;
    }
    /**
     * 索赔工时维护查询
     */
	@RequestMapping(value="/labourSearch",method = RequestMethod.GET)
    @ResponseBody
    public PageInfoDto querylabour(@RequestParam Map<String, String> queryParam) {
    	logger.info("==============索赔工时维护查询=============");
        PageInfoDto pageInfoDto =ttWrLabourService.LabourQuery(queryParam);
        return pageInfoDto;  
    }
	/**
	 * 删除索赔工时维护
	 */
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable("id") Long id, UriComponentsBuilder uriCB) {
    	logger.info("===== 删除索赔工时维护=====");
    	ttWrLabourService.delete(id);
    }
    /**
     * 通过id修改索赔工时维护时的回显信息
     */
    @RequestMapping(value = "/getLabour/{id}", method = RequestMethod.GET)
    @ResponseBody
    public Map<String,Object> getDirectCustomerById(@PathVariable(value = "id") Long id){
    	logger.info("=====代码维护信息回显=====");
    	TtWrLabourPO ptPo=ttWrLabourService.getLabourById(id);
        return ptPo.toMap();
    }
    
    /**
     * 通过id进行修改索赔工时维护
     */
    @RequestMapping(value = "/edit/{id}", method = RequestMethod.PUT)
    @ResponseBody
    public ResponseEntity<TtWrLabourDTO> edit(@PathVariable(value = "id") Long id,@RequestBody TtWrLabourDTO dto,UriComponentsBuilder uriCB){
    	logger.info("===== 通过id进行修改索赔工时维护=====");
    	ttWrLabourService.edit(id,dto);
    	MultiValueMap<String, String> headers = new HttpHeaders();
        headers.set("Location", uriCB.path("/wrLabour/edit").buildAndExpand().toUriString());
        return new ResponseEntity<TtWrLabourDTO>(headers, HttpStatus.CREATED);  	
    }
    /**
     * 新增索赔工时维护
     */
    @RequestMapping(value ="/addLabour",method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<TtWrLabourDTO> addLabourt(@RequestBody TtWrLabourDTO ptdto,UriComponentsBuilder uriCB){
    	logger.info("===== 新增索赔工时维护=====");
    	ttWrLabourService.addLabour(ptdto);
        MultiValueMap<String,String> headers = new HttpHeaders();  
        return new ResponseEntity<>(ptdto,headers, HttpStatus.CREATED);  
    }	

}
