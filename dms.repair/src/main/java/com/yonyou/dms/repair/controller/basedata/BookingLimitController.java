
/** 
*Copyright 2016 Yonyou Corporation Ltd. All Rights Reserved.
* This software is published under the terms of the Yonyou Software
* License version 1.0, a copy of which has been included with this
* distribution in the LICENSE.txt file.
*
* @Project Name : dms.web
*
* @File name : BookingLimitController.java
*
* @Author : zhanshiwei
*
* @Date : 2016年10月12日
*
----------------------------------------------------------------------------------
*     Date       Who       Version     Comments
* 1. 2016年10月12日    zhanshiwei    1.0
*
*
*
*
----------------------------------------------------------------------------------
*/

package com.yonyou.dms.repair.controller.basedata;

import java.util.Map;

import javax.validation.Valid;

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

import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.function.exception.ServiceBizException;
import com.yonyou.dms.repair.domains.DTO.basedata.BookingLimitDTO;
import com.yonyou.dms.repair.domains.PO.basedata.BookingLimitPO;
import com.yonyou.dms.repair.service.basedata.BookingLimitService;
import com.yonyou.f4.mvc.annotation.TxnConn;
import com.yonyou.f4.mvc.controller.BaseController;

/**
 * 预约限量设置
 * 
 * @author zhanshiwei
 * @date 2016年10月12日
 */
@Controller
@TxnConn
@RequestMapping("/basedata/reservationLimit")
public class BookingLimitController extends BaseController {

    @Autowired
    private BookingLimitService bookinglimitservice;

    /**
     * 查询预约限量设置
     * 
     * @author zhanshiwei
     * @date 2016年10月12日
     * @param queryParam
     * @return
     * @throws ServiceBizException
     */

    @RequestMapping(method = RequestMethod.GET)
    @ResponseBody
    public PageInfoDto queryBookingLimit(@RequestParam Map<String, String> queryParam) throws ServiceBizException {
        PageInfoDto pageInfoDto = bookinglimitservice.queryBookingLimit(queryParam);
        return pageInfoDto;
    }

    /**
     * 根据ID预约限量设置
     * 
     * @author zhanshiwei
     * @date 2016年10月12日
     * @param id
     * @return
     * @throws ServiceBizException
     */

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> findBookingLimitById(@PathVariable Long id) throws ServiceBizException {
        BookingLimitPO limipo = bookinglimitservice.findBookingLimitById(id);
        return limipo.toMap();
    }

    /**
     * 新增预约限量设置
     * 
     * @author zhanshiwei
     * @date 2016年10月12日
     * @param limidto
     * @param uriCB
     * @return
     * @throws ServiceBizException
     */

    @RequestMapping(method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<BookingLimitDTO> addBookingLimit(@RequestBody BookingLimitDTO limidto,
                                                           UriComponentsBuilder uriCB) throws ServiceBizException {
        String id = bookinglimitservice.addBookingLimit(limidto);
        MultiValueMap<String, String> headers = new HttpHeaders();
        headers.set("Location", uriCB.path("/basedata/reservationLimit/{id}").buildAndExpand(id).toUriString());
        return new ResponseEntity<BookingLimitDTO>(limidto, headers, HttpStatus.CREATED);
    }

    /**
     * 修改预约限量设置
     * 
     * @author zhanshiwei
     * @date 2016年10月12日
     * @param id
     * @param limidto
     * @param uriCB
     * @return
     */

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public ResponseEntity<BookingLimitDTO> modifyBookingLimit(@PathVariable("id") Long id,
                                                              @RequestBody @Valid BookingLimitDTO limidto,
                                                              UriComponentsBuilder uriCB) {
        bookinglimitservice.modifyBookingLimit(id, limidto);
        MultiValueMap<String, String> headers = new HttpHeaders();
        headers.set("Location", uriCB.path("/basedata/reservationLimit/{id}").buildAndExpand(id).toUriString());
        return new ResponseEntity<BookingLimitDTO>(headers, HttpStatus.CREATED);
    }
    
    /**
    * 删除预约限量设置
    * @author zhanshiwei
    * @date 2016年10月13日
    * @param id
    * @param uriCB
    */
    	
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteBookingLimit(@PathVariable("id") Long id, UriComponentsBuilder uriCB) {
        bookinglimitservice.deleteBookingLimit(id);
    }
}
