package com.yonyou.dms.vehicle.controller.oldPart;

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
import org.springframework.web.util.UriComponentsBuilder;

import com.yonyou.dms.framework.DAO.DAOUtil;
import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.vehicle.domains.DTO.oldPart.TtOpOldpartDTO;
import com.yonyou.dms.vehicle.service.oldPart.OldPartCountService;
import com.yonyou.f4.mvc.annotation.TxnConn;
import com.yonyou.f4.mvc.controller.BaseController;
/**
 * 旧件清点
 * @author Administrator
 *
 */
@Controller
@TxnConn
@RequestMapping("/count")
public class OldPartCountController extends BaseController {
	private static final Logger logger = LoggerFactory.getLogger(DAOUtil.class);

	@Autowired
	OldPartCountService oservice;
	@RequestMapping(method = RequestMethod.GET)
    @ResponseBody
    public PageInfoDto queryDefeatReason(@RequestParam Map<String, String> queryParam) throws Exception {
    	logger.info("旧件清点信息");
        PageInfoDto pageInfoDto=oservice.findCount(queryParam);
        return pageInfoDto;
	}
	@RequestMapping(value = "/returnaddr",method = RequestMethod.GET)
    @ResponseBody
    public List<Map> findreturnAddr(@RequestParam Map<String, String> queryParam) throws Exception {
    	logger.info("返还地信息");
    	List<Map> pageInfoDto=oservice.findreturnAddr(queryParam);
        return pageInfoDto;
	}
	@RequestMapping(value = "/{OLDPART_ID}",method = RequestMethod.GET)
    @ResponseBody
    public PageInfoDto querybyId(@PathVariable(value = "OLDPART_ID") Long id) throws Exception {
    	logger.info("旧件清点信息");
        PageInfoDto pageInfoDto=oservice.findCountById(id);
        return pageInfoDto;
	}
   
    @RequestMapping(value = "/modify/{OLDPART_ID}", method = RequestMethod.POST)
	public ResponseEntity<TtOpOldpartDTO> ModifyTmOldpartStor(@PathVariable("OLDPART_ID") Long id,@RequestBody TtOpOldpartDTO tcDto,UriComponentsBuilder uriCB) {
		logger.info("清点信息");
    	oservice.modifyTmOldpartStor(id, tcDto);
		MultiValueMap<String,String> headers = new HttpHeaders();  
		headers.set("Location", uriCB.path("/count/modify/{OLDPART_ID}").buildAndExpand(id).toUriString());  
		return new ResponseEntity<TtOpOldpartDTO>(headers, HttpStatus.CREATED);
	}
}
