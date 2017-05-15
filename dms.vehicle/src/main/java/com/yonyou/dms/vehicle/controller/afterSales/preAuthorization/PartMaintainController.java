package com.yonyou.dms.vehicle.controller.afterSales.preAuthorization;

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
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.util.UriComponentsBuilder;

import com.yonyou.dms.framework.DAO.DAOUtil;
import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.vehicle.domains.DTO.afterSales.basicDataMgr.TtWrForeapprovalptDTO;
import com.yonyou.dms.vehicle.service.afterSales.preAuthorization.PartMaintainService;
import com.yonyou.f4.mvc.annotation.TxnConn;

/**
 * 预授权监控配件维护
 * @author Administrator
 *
 */
@Controller
@TxnConn
@RequestMapping("/partMaintainJianKong")
public class PartMaintainController {
	private static final Logger logger = LoggerFactory.getLogger(DAOUtil.class);
	@Autowired
	PartMaintainService  partMaintainService;
	/**
	 * 预授权监控配件维护查询
	 * @param queryParam
	 * @return
	 */
	@RequestMapping(value="/partMaintainSearch",method = RequestMethod.GET)
    @ResponseBody
    public PageInfoDto PartMaintainSearch(@RequestParam Map<String, String> queryParam) {
    	logger.info("============== 预授权监控配件维护查询=============");
        PageInfoDto pageInfoDto =partMaintainService.PartMaintainQuery(queryParam);
        return pageInfoDto;  
    }
	/**
	 * 删除
	 */
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable("id") Long id, UriComponentsBuilder uriCB) {
    	logger.info("===== 预授权监控配件维护删除=====");
    	partMaintainService.delete(id);
    }
    /**
     * 查询所有配件代码
     */
    @RequestMapping(value="/peiJianList",method = RequestMethod.GET)
    @ResponseBody
    public List<Map> getAll(@RequestParam Map<String, String> queryParam){
    	logger.info("=====查询所有配件代码=====");
    	List<Map> tenantMapping = partMaintainService.getAll(queryParam);
        return tenantMapping;
    }
    /**
     * 新增
     */
	@RequestMapping(value ="/addFanKui",method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<TtWrForeapprovalptDTO> addBasicParams(@RequestBody TtWrForeapprovalptDTO ptdto,UriComponentsBuilder uriCB){
    	logger.info("=====  新增预授权监控配件维护=====");
    	Long id =partMaintainService.add(ptdto);
        MultiValueMap<String,String> headers = new HttpHeaders();  
        headers.set("Location", uriCB.path("/partMaintainJianKong/addFanKui").buildAndExpand(id).toUriString());  
        return new ResponseEntity<>(ptdto,headers, HttpStatus.CREATED);  
    }
}
