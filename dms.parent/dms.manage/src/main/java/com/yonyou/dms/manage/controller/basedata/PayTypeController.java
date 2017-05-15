package com.yonyou.dms.manage.controller.basedata;

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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.util.UriComponentsBuilder;

import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.function.exception.ServiceBizException;
import com.yonyou.dms.manage.domains.DTO.basedata.PayTypeDto;
import com.yonyou.dms.manage.domains.PO.basedata.PayTypePO;
import com.yonyou.dms.manage.service.basedata.payType.PayTypeService;
import com.yonyou.f4.mvc.annotation.TxnConn;
import com.yonyou.f4.mvc.controller.BaseController;

/**
 * 
 * @author Benzc
 * @date 2016年12月21日
 */
@Controller
@TxnConn
@RequestMapping("/basedata/payType")
public class PayTypeController extends BaseController{
	
	@Autowired
    private PayTypeService service;
    
	/*
	 * 查询
	 */
    @RequestMapping(method = RequestMethod.GET)
    @ResponseBody
    public PageInfoDto findAllAmount(Map<String, String> queryParams) {
        return service.findAllAmount(queryParams);
    }
    
    /*
     * 新增
     */
    @RequestMapping(method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<PayTypeDto> addPayType(@RequestBody PayTypeDto payDto,UriComponentsBuilder uriCB) throws ServiceBizException{
        service.insertPayTypePo(payDto);
        MultiValueMap<String,String> headers = new HttpHeaders();  
        headers.set("Location", uriCB.path("/basedata/payType/{id}").buildAndExpand(payDto.getPayTypeCode()).toUriString());  
        return new ResponseEntity<PayTypeDto>(payDto,headers, HttpStatus.CREATED);  
    }
    
    /**
     * 修改
     * 
     * @author Benzc
     * @date 2016年12月23日
     * @param id
     * @param colordto
     * @param uriCB
     * @return
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public ResponseEntity<PayTypeDto> updateColor(@PathVariable("id") String id, @RequestBody PayTypeDto payDto,UriComponentsBuilder uriCB) {
    	service.updatePayType(id, payDto);
		MultiValueMap<String, String> headers = new HttpHeaders();
		headers.set("Location", uriCB.path("/basedata/payType/{id}").buildAndExpand(id).toUriString());
		return new ResponseEntity<PayTypeDto>(headers, HttpStatus.CREATED);
	}
    
    /**
     * 通过id查询类型信息 TODO description
     * 
     * @author Benzc
     * @date 2016年12月23日
     * @param id
     * @return
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> findById(@PathVariable String id) throws ServiceBizException {
        PayTypePO po = service.findById(id);
        return po.toMap();
    }
    

}
