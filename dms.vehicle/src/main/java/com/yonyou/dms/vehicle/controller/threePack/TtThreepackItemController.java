package com.yonyou.dms.vehicle.controller.threePack;

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
import org.springframework.web.util.UriComponentsBuilder;

import com.yonyou.dms.framework.DAO.DAOUtil;
import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.vehicle.domains.DTO.threePack.TtThreepackItemDTO;
import com.yonyou.dms.vehicle.domains.PO.threePack.TtThreepackItemPO;
import com.yonyou.dms.vehicle.service.threePack.TtThreepackItemService;
import com.yonyou.f4.mvc.annotation.TxnConn;
import com.yonyou.f4.mvc.controller.BaseController;

@Controller
@TxnConn
@RequestMapping("/item")
public class TtThreepackItemController extends BaseController{
	
	private static final Logger logger = LoggerFactory.getLogger(DAOUtil.class);
	
	@Autowired
	TtThreepackItemService tservice;
	
	@RequestMapping(method = RequestMethod.GET)
    @ResponseBody
    public PageInfoDto queryDefeatReason(@RequestParam Map<String, String> queryParam) throws Exception {
    	logger.info("查询三包项目设定信息");
        PageInfoDto pageInfoDto=tservice.findItem(queryParam);
        return pageInfoDto;
    }
	
	 @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
		public ResponseEntity<TtThreepackItemDTO> ModifyTcBank(@PathVariable("id") Long id,@RequestBody TtThreepackItemDTO tcDto,UriComponentsBuilder uriCB) {
			logger.info("修改三包项目设定信息");
	    	tservice.modifyItem(id, tcDto);
			MultiValueMap<String,String> headers = new HttpHeaders();  
			headers.set("Location", uriCB.path("/item/{id}").buildAndExpand(id).toUriString());  
			return new ResponseEntity<TtThreepackItemDTO>(headers, HttpStatus.CREATED);
		}
	    
	    @RequestMapping(value="/{id}",method=RequestMethod.GET)
	    @ResponseBody
	    public Map<String ,Object> findById(@PathVariable(value = "id") Long id){
	    	TtThreepackItemPO map = TtThreepackItemPO.findById(id);
	        return map.toMap();
	    }

}
