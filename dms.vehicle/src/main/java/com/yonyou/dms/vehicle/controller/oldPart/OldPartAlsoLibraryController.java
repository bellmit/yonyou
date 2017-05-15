package com.yonyou.dms.vehicle.controller.oldPart;

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
import com.yonyou.dms.framework.service.excel.ExcelGenerator;
import com.yonyou.dms.vehicle.domains.DTO.oldPart.TtOpStoreDTO;
import com.yonyou.dms.vehicle.service.oldPart.OldPartAlsoLibraryService;
import com.yonyou.f4.mvc.annotation.TxnConn;
import com.yonyou.f4.mvc.controller.BaseController;
@Controller
@TxnConn
@RequestMapping("/alsoLibrary")
public class OldPartAlsoLibraryController extends BaseController{
	private static final Logger logger = LoggerFactory.getLogger(DAOUtil.class);


	@Autowired
	OldPartAlsoLibraryService oservice;
	
	@Autowired
	ExcelGenerator excelService;
	
	@RequestMapping(method = RequestMethod.GET)
    @ResponseBody
    public PageInfoDto queryDefeatReason(@RequestParam Map<String, String> queryParam) throws Exception {
    	logger.info("旧件库存信息");
        PageInfoDto pageInfoDto=oservice.findOutStorage(queryParam);
        return pageInfoDto;
	}
	@RequestMapping(value = "/find/{STORE_ID}",method = RequestMethod.GET)
    @ResponseBody
    public PageInfoDto querybyId(@PathVariable(value = "STORE_ID") Long id) throws Exception {
    	logger.info("旧件待入库信息");
        PageInfoDto pageInfoDto=oservice.findOutById(id);
        return pageInfoDto;
	}
	 @RequestMapping(value = "/{STORE_ID}", method = RequestMethod.PUT)
		public ResponseEntity<TtOpStoreDTO> ModifyTmOldpartStor(@PathVariable("STORE_ID") Long id,@RequestBody TtOpStoreDTO tcDto,UriComponentsBuilder uriCB) {
			logger.info("旧件还库");
	    	oservice.modifyTmOldpartStor(id, tcDto);
			MultiValueMap<String,String> headers = new HttpHeaders();  
			headers.set("Location", uriCB.path("/outStorage/{STORE_ID}").buildAndExpand(id).toUriString());  
			return new ResponseEntity<TtOpStoreDTO>(headers, HttpStatus.CREATED);
		}

}
