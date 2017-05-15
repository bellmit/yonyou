package com.yonyou.dms.repair.controller.basedata;

import java.util.Map;

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

import com.yonyou.dms.common.domains.PO.basedata.TmModelLabourPO;
import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.repair.service.basedata.ModelLabourService;
import com.yonyou.f4.mvc.annotation.TxnConn;
import com.yonyou.f4.mvc.controller.BaseController;
/**
 * 维修类型
 * @author zhl
 * @date 2017年3月27日
 */
@Controller
@TxnConn
@RequestMapping("/basedata/modelLabours")
public class ModelLabourController extends BaseController{
	
    @Autowired
    private ModelLabourService   modelLabourService;


    /**
     * 查询
     * @author zhl
     * @date 2017年3月27日
     * @param queryParam
     * @return
     */

    @RequestMapping(method = RequestMethod.GET)
    @ResponseBody
    public PageInfoDto queryRepairType(@RequestParam Map<String, String> queryParam) {
        PageInfoDto pageInfoDto = modelLabourService.QueryModelLabour(queryParam);
        return pageInfoDto;
    }
    
    //新增  
    @RequestMapping(value="/addCarTypes",method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<Map<String, String>>  addCarTypesInfo(@RequestBody Map<String, String> map, UriComponentsBuilder uriCB) {
    	modelLabourService.addCarTypes(map);
        MultiValueMap<String, String> headers = new HttpHeaders();
        headers.set("Location", uriCB.path("/basedata/modelLabours").buildAndExpand().toUriString());
    	return new ResponseEntity<Map<String, String>>(headers, HttpStatus.CREATED);
    }
    
    //修改
    @RequestMapping(value = "/savemodlabour", method = RequestMethod.PUT)
    public ResponseEntity<Map<String, String>> updateCarType(@RequestBody Map<String, String> map,UriComponentsBuilder uriCB) {
    	modelLabourService.updateCarType(map);
         MultiValueMap<String,String> headers = new HttpHeaders();  
         headers.set("Location", uriCB.path("/basedata/modelLabours").buildAndExpand().toUriString());  
     	return new ResponseEntity<Map<String, String>>(headers, HttpStatus.CREATED);  
     }
    //根据CODE查询
    @RequestMapping(value="/{id}",method=RequestMethod.GET)
    @ResponseBody
    public Map<String,Object> findById(@PathVariable String id){
    	TmModelLabourPO laPo= modelLabourService.findByCode(id);
        return laPo.toMap();
    }
}
