package com.yonyou.dms.vehicle.controller.threePack;

import java.util.HashMap;
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
import com.yonyou.dms.vehicle.domains.DTO.threePack.TtThreepackRepairDTO;
import com.yonyou.dms.vehicle.service.threePack.ThreePackRepairAuditService;
import com.yonyou.f4.mvc.annotation.TxnConn;
import com.yonyou.f4.mvc.controller.BaseController;
/**
 * 维修方案审核
 * @author Administrator
 *
 */
@Controller
@TxnConn
@RequestMapping("/repairAudit")
public class ThreePackRepairAuditController extends BaseController{
	private static final Logger logger = LoggerFactory.getLogger(DAOUtil.class);
	@Autowired
	ThreePackRepairAuditService tservice;
	 @RequestMapping(method = RequestMethod.GET)
	    @ResponseBody
	    public PageInfoDto queryDefeatReason(@RequestParam Map<String, String> queryParam) throws Exception {
	    	logger.info("查询维修方案审核信息");
	        PageInfoDto pageInfoDto=tservice.findAudit(queryParam);
	        return pageInfoDto;
	    }

	  @RequestMapping(value="/info/{id}",method = RequestMethod.GET)
	    @ResponseBody
	    public Map<String,Object> queryInfo(@PathVariable(value = "id") Long id) throws Exception {
	    	logger.info("维修方案审核基本信息");
	    	Map<String,Object> pageInfoDto=new HashMap<>();
	    	pageInfoDto=tservice.threePackAuditInfo(id);
	        return pageInfoDto;
	    }

	  @RequestMapping(value="/labourList/{id}",method = RequestMethod.GET)
	    @ResponseBody
	    public PageInfoDto queryLabourList(@PathVariable(value = "id") Long id) throws Exception {
	    	logger.info("三包维修方案工时查询");
	    	PageInfoDto pageInfoDto=tservice.threePackLabourList(id);
	        return pageInfoDto;
	    }
	  @RequestMapping(value="/list/{id}",method = RequestMethod.GET)
	    @ResponseBody
	    public PageInfoDto threePackPartList(@PathVariable(value = "id") Long id) throws Exception {
	    	logger.info("三包维修方案配件查询");
	    	PageInfoDto pageInfoDto=tservice.threePackPartList(id);
	        return pageInfoDto;
	    }
	
	    @RequestMapping(value="/{vin}",method=RequestMethod.GET)
	    @ResponseBody
	    public Map<String ,Object> findById(@PathVariable(value = "vin") String vin){
	    	Map<String ,Object> map= tservice.find(vin);
	  
	        return map;
	    }
	    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
		public ResponseEntity<TtThreepackRepairDTO> ModifyTcBank(@PathVariable("id") Long id,@RequestBody TtThreepackRepairDTO tcDto,UriComponentsBuilder uriCB) {
			logger.info("三包维修方案审核");
	    	tservice.modifyNopart(id, tcDto);
			MultiValueMap<String,String> headers = new HttpHeaders();  
			headers.set("Location", uriCB.path("/repairAudit/{id}").buildAndExpand(id).toUriString());  
			return new ResponseEntity<TtThreepackRepairDTO>(headers, HttpStatus.CREATED);
		}
}
