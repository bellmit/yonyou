package com.yonyou.dms.manage.controller.market;

import java.util.Map;

import javax.validation.Valid;

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
import org.springframework.web.util.UriComponentsBuilder;

import com.yonyou.dms.framework.DAO.DAOUtil;
import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.manage.domains.DTO.market.TmMarketActivityDTO;
import com.yonyou.dms.manage.domains.PO.market.TmMarketActivityPO;
import com.yonyou.dms.manage.service.market.TmMarketService;
import com.yonyou.f4.mvc.annotation.TxnConn;
import com.yonyou.f4.mvc.controller.BaseController;
/**
 * 市场活动维护
 * @author Administrator
 *
 */
@Controller
@TxnConn
@RequestMapping("/market")
public class TmMarketController extends BaseController{
	
	private static final Logger logger = LoggerFactory.getLogger(DAOUtil.class);
	
	@Autowired
	TmMarketService tmservice;
	
	 @RequestMapping(method = RequestMethod.GET)
	    @ResponseBody
	    public PageInfoDto queryDefeatReason(@RequestParam Map<String, String> queryParam) {
	    	logger.info("查询市场活动信息");
	        PageInfoDto pageInfoDto=tmservice.getQuerySql(queryParam);
	        return pageInfoDto;
	    }
	    
	    @RequestMapping(value = "/add",method = RequestMethod.POST)
	    public ResponseEntity<TmMarketActivityDTO> addMaterialGroup(@RequestBody @Valid TmMarketActivityDTO tcDto, UriComponentsBuilder uriCB) {
	    	logger.info("新增市场活动");
	        Long id = tmservice.addTcBank(tcDto);
	        MultiValueMap<String, String> headers = new HttpHeaders();
	        headers.set("Location", uriCB.path("/market/add/{id}").buildAndExpand(id).toUriString());
	        return new ResponseEntity<TmMarketActivityDTO>(tcDto, headers, HttpStatus.CREATED);

	    }
	    
	    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
		public ResponseEntity<TmMarketActivityDTO> ModifyTcBank(@PathVariable("id") Long id,@RequestBody TmMarketActivityDTO tcDto,UriComponentsBuilder uriCB) {
			logger.info("修改市场活动信息");
			tmservice.modifyTcBank(id, tcDto);
			MultiValueMap<String,String> headers = new HttpHeaders();  
			headers.set("Location", uriCB.path("/market/{id}").buildAndExpand(id).toUriString());  
			return new ResponseEntity<TmMarketActivityDTO>(headers, HttpStatus.CREATED);
		}
	    /**
	     * 根据ID进行查询
	     * @param id
	     * @return
	     */

	    @RequestMapping(value="/{id}",method=RequestMethod.GET)
	    @ResponseBody
	    public Map<String,Object> findById(@PathVariable(value = "id") Long id){
	    	TmMarketActivityPO dr = TmMarketActivityPO.findById(id);
	        return dr.toMap();
	    }

}
