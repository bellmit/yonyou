package com.yonyou.dms.vehicle.controller.afterSales.basicDataMgr;

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
import com.yonyou.dms.vehicle.domains.DTO.afterSales.basicDataMgr.TtWrBaseCodeDTO;
import com.yonyou.dms.vehicle.domains.PO.afterSales.basicDataMgr.TtWrBaseCodePO;
import com.yonyou.dms.vehicle.service.afterSales.basicDataMgr.BasicCodeService;
import com.yonyou.f4.mvc.annotation.TxnConn;

/**
 * 代码维护
 * @author Administrator
 *
 */
@Controller
@TxnConn
@RequestMapping("/basicCode")
public class BasicCodeController {
	private static final Logger logger = LoggerFactory.getLogger(DAOUtil.class);
	@Autowired
	BasicCodeService  basicCodeService;
	
	/**
	 * 代码维护查询
	 */
	@RequestMapping(value="/basicCodeSearch",method = RequestMethod.GET)
    @ResponseBody
    public PageInfoDto querybasicCode(@RequestParam Map<String, String> queryParam) {
    	logger.info("==============代码维护查询=============");
        PageInfoDto pageInfoDto =basicCodeService.BasicCodeQuery(queryParam);
        return pageInfoDto;  
    }
	/**
	 * 新增是代码维护
	 */
    @RequestMapping(value ="/addBasicCode",method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<TtWrBaseCodeDTO> addDealerPayment(@RequestBody TtWrBaseCodeDTO ptdto,UriComponentsBuilder uriCB){
    	logger.info("=====  新增代码维护=====");
    	Long id =basicCodeService.addBasicCode(ptdto);
        MultiValueMap<String,String> headers = new HttpHeaders();  
        headers.set("Location", uriCB.path("/basicCode/addBasicCode").buildAndExpand(id).toUriString());  
        return new ResponseEntity<>(ptdto,headers, HttpStatus.CREATED);  
    }	 
    /**
     * 修改代码维护
     */
    @RequestMapping(value = "/edit/{id}", method = RequestMethod.PUT)
    @ResponseBody
    public ResponseEntity<TtWrBaseCodeDTO> edit(@PathVariable(value = "id") Long id,@RequestBody TtWrBaseCodeDTO dto,UriComponentsBuilder uriCB){
    	logger.info("===== 修改代码维护=====");
    	basicCodeService.edit(id,dto);
    	MultiValueMap<String, String> headers = new HttpHeaders();
        headers.set("Location", uriCB.path("/basicCode/edit").buildAndExpand().toUriString());
        return new ResponseEntity<TtWrBaseCodeDTO>(headers, HttpStatus.CREATED);  	
    }
    /**
     * 代码维护信息回显
     */
    @RequestMapping(value = "/getBasicCode/{id}", method = RequestMethod.GET)
    @ResponseBody
    public Map<String,Object> getDirectCustomerById(@PathVariable(value = "id") Long id){
    	logger.info("=====代码维护信息回显=====");
    	TtWrBaseCodePO ptPo=basicCodeService.getBasicCodeById(id);
        return ptPo.toMap();
    }
    /**
     * 删除代码维护
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable("id") Long id, UriComponentsBuilder uriCB) {
    	logger.info("=====删除代码维护=====");
    	basicCodeService.delete(id);
    }

}
