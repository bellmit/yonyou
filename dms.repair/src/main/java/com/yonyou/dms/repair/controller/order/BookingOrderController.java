
/** 
*Copyright 2016 Yonyou Corporation Ltd. All Rights Reserved.
* This software is published under the terms of the Yonyou Software
* License version 1.0, a copy of which has been included with this
* distribution in the LICENSE.txt file.
*
* @Project Name : dms.repair
*
* @File name : BookingOrderController.java
*
* @Author : jcsi
*
* @Date : 2016年10月14日
*
----------------------------------------------------------------------------------
*     Date       Who       Version     Comments
* 1. 2016年10月14日    jcsi    1.0
*
*
*
*
----------------------------------------------------------------------------------
*/
	
package com.yonyou.dms.repair.controller.order;

import java.util.List;
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
import com.yonyou.dms.repair.domains.DTO.order.BookingOrderDTO;
import com.yonyou.dms.repair.domains.DTO.order.DrivingServiceOrderDTO;
import com.yonyou.dms.repair.service.order.BookingOrderService;
import com.yonyou.f4.mvc.annotation.TxnConn;
import com.yonyou.f4.mvc.controller.BaseController;

/**
* 预约
* @author jcsi
* @date 2016年10月14日
*/

@Controller
@TxnConn
@RequestMapping("/order/bookingOrder")
public class BookingOrderController extends BaseController{
    
    @Autowired
    private BookingOrderService bookingOrderService;
    
    
    /**
    * 查询
    * @author jcsi
    * @date 2016年10月14日
    * @param queryParam
    * @return
     */
    @RequestMapping(method=RequestMethod.GET)
    @ResponseBody
    public PageInfoDto search(@RequestParam Map<String,String> queryParam){
        return bookingOrderService.search(queryParam);    
    }
    

    /**
    * 新增
    * @author jcsi
    * @date 2016年10月17日
    * @param suggest
    * @param uriCB
    * @return
     */
    @RequestMapping(method=RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<BookingOrderDTO> saveBookingOrder(@RequestBody @Valid BookingOrderDTO bookingOrderDto,UriComponentsBuilder uriCB){
        Long id=bookingOrderService.addBookingOrder(bookingOrderDto);
        MultiValueMap<String, String> headers = new HttpHeaders();
        headers.set("Location",uriCB.path("/{id}").buildAndExpand(id).toUriString());
        return new ResponseEntity<BookingOrderDTO>(bookingOrderDto,headers, HttpStatus.CREATED);
    }
    
    /**
    * 修改
    * @author jcsi
    * @date 2016年10月17日
    * @param bookingOrderDto
     */
    @RequestMapping(value="/{id}/bookingOrder",method=RequestMethod.PUT)
    @ResponseBody
    public void editBookingOrder(@PathVariable Long id,@RequestBody @Valid BookingOrderDTO bookingOrderDto){
        bookingOrderService.editBookingOrder(id,bookingOrderDto);
    }
    
    
    
    
    /**
    * 保存取送车服务信息
    * @author jcsi
    * @date 2016年10月17日
    * @param drivingServiceOrderDto
     */
    @RequestMapping(value="/drivingServiceOrder",method=RequestMethod.POST)
    @ResponseBody
    public void addDrivingServiceOrder(@RequestBody DrivingServiceOrderDTO drivingServiceOrderDto){
        bookingOrderService.addDrivingServiceOrder(drivingServiceOrderDto);
    }
    
    /**
    * 查询预约单
    * @author jcsi
    * @date 2016年10月18日
    * @param id
    * @return
     */
    @RequestMapping(value="/{id}/bookingOrder",method=RequestMethod.GET)
    @ResponseBody
    public Map searchBookingOrderByBoId(@PathVariable Long id){
        return bookingOrderService.searchBookingOrderByBoId(id);
    }
    
    /**
    * 维修项目
    * @author jcsi
    * @date 2016年10月18日
    * @param id
    * @return
     */
    @RequestMapping(value="/{id}/boLabour",method=RequestMethod.GET)
    @ResponseBody
    public List<Map> searchBoLabourByBoId(@PathVariable Long id){
        return bookingOrderService.searchBoLabourByBoId(id);
    }
    
    /**
    * 维修配件
    * @author jcsi
    * @date 2016年10月18日
    * @param id
    * @return
     */
    @RequestMapping(value="/{id}/boPart",method=RequestMethod.GET)
    @ResponseBody
    public List<Map> searchBoPartByBoId(@PathVariable Long id){
        return bookingOrderService.searchBoPartByBoId(id);
    }
    
    /**
    * 附加项目
    * @author jcsi
    * @date 2016年10月18日
    * @param id
    * @return
     */
    @RequestMapping(value="/{id}/addItem",method=RequestMethod.GET)
    @ResponseBody
    public List<Map> searchAddItemByBoId(@PathVariable Long id){
        return bookingOrderService.searchAddItemByBoId(id);
    }
    
    /**
    * 预约取消
    * @author jcsi
    * @date 2016年10月19日
    * @param id
     */
    @RequestMapping(value="/{id}/orderStatus",method=RequestMethod.GET)
    @ResponseBody
    public void updateBookingOrderStatusByBoId(@PathVariable Long id){
        bookingOrderService.updateBookingOrderStatusByBoId(id);
    }
    
    /**
    * 删除
    * @author jcsi
    * @date 2016年10月27日
    * @param id
     */
    @RequestMapping(value="/{id}",method=RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteByBoId(@PathVariable Long id){
        bookingOrderService.deleteByBoId(id);
    }
    
    /**
     * 工单  维修项目
     * @author jcsi
     * @date 2016年10月18日
     * @param id
     * @return
      */
     @RequestMapping(value="/order/{id}/boLabour",method=RequestMethod.GET)
     @ResponseBody
     public List<Map> searchOrderLabourByBoId(@PathVariable Long id){
         return bookingOrderService.searchOrderLabourByBoId(id);
     }
     
     /**
     * 根据BO_ID 查询车辆信息
     * @author jcsi
     * @date 2016年10月27日
     * @param id
     * @return
      */
     @RequestMapping(value="/{id}/carInfos",method=RequestMethod.GET)
     @ResponseBody
     public Map searchCarIfoByBoId(@PathVariable Long id){
         return bookingOrderService.searchCarIfoByBoId(id);
     }

}
