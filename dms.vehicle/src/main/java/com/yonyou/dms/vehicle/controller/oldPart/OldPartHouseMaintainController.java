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
import com.yonyou.dms.vehicle.domains.DTO.oldPart.TmOldpartStorDTO;
import com.yonyou.dms.vehicle.domains.PO.oldPart.TmOldpartStorPO;
import com.yonyou.dms.vehicle.service.oldPart.OldPartHouseMaintainService;
import com.yonyou.f4.mvc.annotation.TxnConn;
import com.yonyou.f4.mvc.controller.BaseController;

@Controller
@TxnConn
@RequestMapping("/oldParthHouse")
public class OldPartHouseMaintainController extends BaseController {
	private static final Logger logger = LoggerFactory.getLogger(DAOUtil.class);
	@Autowired
	OldPartHouseMaintainService oservice;
	
    @RequestMapping(method = RequestMethod.GET)
    @ResponseBody
    public PageInfoDto queryDefeatReason(@RequestParam Map<String, String> queryParam) {
    	logger.info("查询旧件仓库信息");
        PageInfoDto pageInfoDto=oservice.getQuerySql(queryParam);
        return pageInfoDto;
    }
    
    @RequestMapping(value = "/add",method = RequestMethod.POST)
    public ResponseEntity<TmOldpartStorDTO> addMaterialGroup(@RequestBody @Valid TmOldpartStorDTO tcDto, UriComponentsBuilder uriCB) {
    	logger.info("新增旧件仓库信息");
        Long id = oservice.addTmOldpartStor(tcDto);
        MultiValueMap<String, String> headers = new HttpHeaders();
        headers.set("Location", uriCB.path("/oldParthHouse/add/{STOR_ID}").buildAndExpand(id).toUriString());
        return new ResponseEntity<TmOldpartStorDTO>(tcDto, headers, HttpStatus.CREATED);

    }
    
    @RequestMapping(value = "/{STOR_ID}", method = RequestMethod.PUT)
	public ResponseEntity<TmOldpartStorDTO> ModifyTmOldpartStor(@PathVariable("STOR_ID") Long id,@RequestBody TmOldpartStorDTO tcDto,UriComponentsBuilder uriCB) {
		logger.info("修改旧件仓库信息");
    	oservice.modifyTmOldpartStor(id, tcDto);
		MultiValueMap<String,String> headers = new HttpHeaders();  
		headers.set("Location", uriCB.path("/oldParthHouse/{STOR_ID}").buildAndExpand(id).toUriString());  
		return new ResponseEntity<TmOldpartStorDTO>(headers, HttpStatus.CREATED);
	}

    
    /**
     * 根据ID进行查询
     * @param id
     * @return
     */

    @RequestMapping(value="/{STOR_ID}",method=RequestMethod.GET)
    @ResponseBody
    public Map<String,Object> findById(@PathVariable(value = "STOR_ID") Long id){
    	TmOldpartStorPO model= TmOldpartStorPO.findById(id);
        return model.toMap();
    }
}
