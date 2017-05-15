package com.yonyou.dms.vehicle.controller.threePack;

import java.util.HashMap;
import java.util.List;
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
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.util.UriComponentsBuilder;

import com.yonyou.dms.framework.DAO.DAOUtil;
import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.function.exception.ServiceBizException;
import com.yonyou.dms.vehicle.domains.DTO.threePack.TtThreepackWarnParaDTO;
import com.yonyou.dms.vehicle.service.threePack.TtThreepackWarnParaService;
import com.yonyou.f4.mvc.annotation.TxnConn;
import com.yonyou.f4.mvc.controller.BaseController;
@Controller
@TxnConn
@RequestMapping("/warn")
public class TtThreepackWarnParaController extends BaseController{
	private static final Logger logger = LoggerFactory.getLogger(DAOUtil.class);
	
	@Autowired
	TtThreepackWarnParaService tservice;
	 @RequestMapping(method = RequestMethod.GET)
	    @ResponseBody
	    public PageInfoDto queryDefeatReason(@RequestParam Map<String, String> queryParam) throws Exception {
	    	logger.info("查询三包预警信息");
	        PageInfoDto pageInfoDto=tservice.findthreePack(queryParam);
	        return pageInfoDto;
	    }
	    
	    @RequestMapping(value = "/add",method = RequestMethod.POST)
	    public ResponseEntity<TtThreepackWarnParaDTO> addMaterialGroup(@RequestBody @Valid TtThreepackWarnParaDTO tcDto, UriComponentsBuilder uriCB) {
	    	logger.info("新增三包预警信息");
	        Long id = tservice.addMinclass(tcDto);
	        MultiValueMap<String, String> headers = new HttpHeaders();
	        headers.set("Location", uriCB.path("/warn/add/{id}").buildAndExpand(id).toUriString());
	        return new ResponseEntity<TtThreepackWarnParaDTO>(tcDto, headers, HttpStatus.CREATED);

	    }
	    
	    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
		public ResponseEntity<TtThreepackWarnParaDTO> ModifyTcBank(@PathVariable("id") Long id,@RequestBody TtThreepackWarnParaDTO tcDto,UriComponentsBuilder uriCB) {
			logger.info("修改三包预警信息");
	    	tservice.modifyMinclass(id, tcDto);
			MultiValueMap<String,String> headers = new HttpHeaders();  
			headers.set("Location", uriCB.path("/warn/{id}").buildAndExpand(id).toUriString());  
			return new ResponseEntity<TtThreepackWarnParaDTO>(headers, HttpStatus.CREATED);
		}
	    
	    @RequestMapping(value="/{id}",method=RequestMethod.GET)
	    @ResponseBody
	    public Map<String ,Object> findById(@PathVariable(value = "id") Long id){
	    	Map<String ,Object> map=new HashMap<String ,Object>();
	    	List<Map> model= tservice.findById(id);
	    	map =  model.get(0);
	        return map;
	    }
	    @RequestMapping(value = "/delete/{id}", method = RequestMethod.DELETE)
	    @ResponseStatus(HttpStatus.NO_CONTENT)
	    public void deleteCharge(@PathVariable("id") Long id,UriComponentsBuilder uriCB) throws ServiceBizException{
	        tservice.deleteById(id);
	    }
}
