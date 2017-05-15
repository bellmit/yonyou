package com.yonyou.dms.vehicle.controller.oldPart;

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
import com.yonyou.dms.vehicle.domains.DTO.oldPart.TmOldpartReturnaddrDTO;
import com.yonyou.dms.vehicle.domains.PO.oldPart.TmOldpartReturnaddrPO;
import com.yonyou.dms.vehicle.service.oldPart.OldPartReturnAddrService;
import com.yonyou.f4.mvc.annotation.TxnConn;
import com.yonyou.f4.mvc.controller.BaseController;


@Controller
@TxnConn
@RequestMapping("/oldPartReturn")
public class OldPartReturnAddrController  extends BaseController{
	private static final Logger logger = LoggerFactory.getLogger(DAOUtil.class);

	@Autowired
	OldPartReturnAddrService oservice;
	 @RequestMapping(method = RequestMethod.GET)
	    @ResponseBody
	    public PageInfoDto queryDefeatReason(@RequestParam Map<String, String> queryParam) {
	    	logger.info("查询旧件返还地信息");
	        PageInfoDto pageInfoDto=oservice.getQuerySql(queryParam);
	        return pageInfoDto;
	    }
	    
	    @RequestMapping(value = "/add",method = RequestMethod.POST)
	    public ResponseEntity<TmOldpartReturnaddrDTO> addMaterialGroup(@RequestBody @Valid TmOldpartReturnaddrDTO tcDto, UriComponentsBuilder uriCB) {
	    	logger.info("新增旧件返还地信息");
	        Long id = oservice.addTmOldpartStor(tcDto);
	        MultiValueMap<String, String> headers = new HttpHeaders();
	        headers.set("Location", uriCB.path("/oldPartReturn/add/{RETURNADDR_ID}").buildAndExpand(id).toUriString());
	        return new ResponseEntity<TmOldpartReturnaddrDTO>(tcDto, headers, HttpStatus.CREATED);

	    }
	    
	    @RequestMapping(value = "/{RETURNADDR_ID}", method = RequestMethod.PUT)
		public ResponseEntity<TmOldpartReturnaddrDTO> ModifyTmOldpartStor(@PathVariable("RETURNADDR_ID") Long id,@RequestBody TmOldpartReturnaddrDTO tcDto,UriComponentsBuilder uriCB) {
			logger.info("修改旧件返还地信息");
	    	oservice.modifyTmOldpartStor(id, tcDto);
			MultiValueMap<String,String> headers = new HttpHeaders();  
			headers.set("Location", uriCB.path("/oldPartReturn/{RETURNADDR_ID}").buildAndExpand(id).toUriString());  
			return new ResponseEntity<TmOldpartReturnaddrDTO>(headers, HttpStatus.CREATED);
		}

	    
	    /**
	     * 根据ID进行查询
	     * @param id
	     * @return
	     */

	    @RequestMapping(value="/{RETURNADDR_ID}",method=RequestMethod.GET)
	    @ResponseBody
	    public Map<String,Object> findById(@PathVariable(value = "RETURNADDR_ID") Long id){
	    	TmOldpartReturnaddrPO map=TmOldpartReturnaddrPO.findById(id);
	        return map.toMap();
	    }
}
