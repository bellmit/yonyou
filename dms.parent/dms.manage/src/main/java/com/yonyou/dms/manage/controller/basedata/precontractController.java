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

import com.yonyou.dms.common.domains.DTO.basedata.BookingTypeCodeDTO;
import com.yonyou.dms.common.domains.PO.basedata.BookingTypePO;
import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.function.exception.ServiceBizException;
import com.yonyou.dms.manage.domains.DTO.basedata.PayTypeDto;
import com.yonyou.dms.manage.service.basedata.precontract.precontractService;
import com.yonyou.f4.mvc.annotation.TxnConn;
import com.yonyou.f4.mvc.controller.BaseController;

@Controller
@TxnConn
@RequestMapping("/basedata/precontract")
public class precontractController  extends BaseController{
	@Autowired
    private precontractService precontractService;
	
	/**
	 * 查询
	 * @param queryParams
	 * @return
	 */
    @RequestMapping(method = RequestMethod.GET)
    @ResponseBody
    public PageInfoDto findAllAmount(Map<String, String> queryParams) {
        return precontractService.findAllAmount(queryParams);
    }
   /**
    * 新增
    * @param bookingTypeCodeDTO
    * @param uriCB
    * @return
    * @throws ServiceBizException
    */
    @RequestMapping(method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<BookingTypeCodeDTO> addPayType(@RequestBody BookingTypeCodeDTO bookingTypeCodeDTO,UriComponentsBuilder uriCB) throws ServiceBizException{
    	precontractService.insertPayTypePo(bookingTypeCodeDTO);
        MultiValueMap<String,String> headers = new HttpHeaders();  
        headers.set("Location", uriCB.path("/basedata/precontract/{id}").buildAndExpand(bookingTypeCodeDTO.getBookingTypeCode()).toUriString());  
        return new ResponseEntity<BookingTypeCodeDTO>(bookingTypeCodeDTO,headers, HttpStatus.CREATED);  
    }
    
    /**
     * 修改
     * 
     * @param id
     * @param colordto
     * @param uriCB
     * @return
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public ResponseEntity<PayTypeDto> updateColor(@PathVariable("id") String id, @RequestBody BookingTypeCodeDTO bookingTypeCodeDTO,UriComponentsBuilder uriCB) {
    	precontractService.updatePayType(id, bookingTypeCodeDTO);
		MultiValueMap<String, String> headers = new HttpHeaders();
		headers.set("Location", uriCB.path("/basedata/precontract/{id}").buildAndExpand(id).toUriString());
		return new ResponseEntity<PayTypeDto>(headers, HttpStatus.CREATED);
	}
    
    /**
     * 通过id查询类型信息 TODO description
     * 
     * @param id
     * @return
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> findById(@PathVariable String id) throws ServiceBizException {
        BookingTypePO bookingTypePO = precontractService.findById(id);
        return bookingTypePO.toMap();
    }
    
}
