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
import com.yonyou.dms.vehicle.domains.DTO.afterSales.basicDataMgr.TtWrBasicParaDTO;
import com.yonyou.dms.vehicle.service.afterSales.basicDataMgr.ClaimBasicParamsService;
import com.yonyou.f4.mvc.annotation.TxnConn;

/**
 * 索赔基本参数设定
 * @author Administrator
 *
 */
@Controller
@TxnConn
@RequestMapping("/basicParams")
public class ClaimBasicParamsController {
	private static final Logger logger = LoggerFactory.getLogger(DAOUtil.class);
	@Autowired
	ClaimBasicParamsService  claimBasicParamsService;
	
	/**
	 * 索赔基本参数设定查询
	 */
	@RequestMapping(value="/basicParamsSearch",method = RequestMethod.GET)
    @ResponseBody
    public PageInfoDto basicParamsCode(@RequestParam Map<String, String> queryParam) {
    	logger.info("==============索赔基本参数设定查询=============");
        PageInfoDto pageInfoDto =claimBasicParamsService.BasicParamsQuery(queryParam);
        return pageInfoDto;  
    }
	/**
	 * 索赔基本参数设定新增
	 */
    @RequestMapping(value ="/addbasicParams",method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<TtWrBasicParaDTO> addBasicParams(@RequestBody TtWrBasicParaDTO ptdto,UriComponentsBuilder uriCB){
    	logger.info("=====  索赔基本参数设定新增=====");
    	Long id =claimBasicParamsService.addBasicParams(ptdto);
        MultiValueMap<String,String> headers = new HttpHeaders();  
        headers.set("Location", uriCB.path("/basicParams/addbasicParams").buildAndExpand(id).toUriString());  
        return new ResponseEntity<>(ptdto,headers, HttpStatus.CREATED);  
    }	 
    /**
     * 通过id进行回显信息查询
     */
    @RequestMapping(value = "/getBasicParams/{id}", method = RequestMethod.GET)
    @ResponseBody
    public Map<String,Object> getBasicParamsById(@PathVariable(value = "id") Long id){
    	logger.info("=====代码维护信息回显=====");
    	Map m=claimBasicParamsService.getShuju(id);
        return m;
    }
    /**
     * 修改
     */
    @RequestMapping(value = "/edit/{id}", method = RequestMethod.PUT)
    @ResponseBody
    public ResponseEntity<TtWrBasicParaDTO> edit(@PathVariable(value = "id") Long id,@RequestBody TtWrBasicParaDTO dto,UriComponentsBuilder uriCB){
    	logger.info("===== 修改索赔基本参数设定=====");
    	claimBasicParamsService.edit(id,dto);
    	MultiValueMap<String, String> headers = new HttpHeaders();
        headers.set("Location", uriCB.path("/basicCode/edit").buildAndExpand().toUriString());
        return new ResponseEntity<TtWrBasicParaDTO>(headers, HttpStatus.CREATED);  	
    }
    /**
     * 索赔基本参数设定删除
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable("id") Long id, UriComponentsBuilder uriCB) {
    	logger.info("=====删除索赔基本参数设定=====");
    	claimBasicParamsService.delete(id);
    }
  
    /**
     * 索赔基本参数设定下发
     * @param uriCB
     * @return
     */
    @RequestMapping(value = "/xiafa", method = RequestMethod.PUT)
    @ResponseBody
    public ResponseEntity<TtWrBasicParaDTO> xiafa(UriComponentsBuilder uriCB){
    	logger.info("===== 下发=====");
    	claimBasicParamsService.getAll();
    	MultiValueMap<String, String> headers = new HttpHeaders();
        headers.set("Location", uriCB.path("/basicCode/xiafa").buildAndExpand().toUriString());
        return new ResponseEntity<TtWrBasicParaDTO>(headers, HttpStatus.CREATED);  	
    }

}
