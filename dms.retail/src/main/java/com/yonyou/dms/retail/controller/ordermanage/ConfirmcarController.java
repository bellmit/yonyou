
/** 
*Copyright 2016 Yonyou Corporation Ltd. All Rights Reserved.
* This software is published under the terms of the Yonyou Software
* License version 1.0, a copy of which has been included with this
* distribution in the LICENSE.txt file.
*
* @Project Name : dms.retail
*
* @File name : ConfirmcarController.java
*
* @Author : zhongsw
*
* @Date : 2016年9月27日
*
----------------------------------------------------------------------------------
*     Date       Who       Version     Comments
* 1. 2016年9月27日    zhongsw    1.0
*
*
*
*
----------------------------------------------------------------------------------
*/
	
package com.yonyou.dms.retail.controller.ordermanage;

import java.util.List;
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
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.util.UriComponentsBuilder;

import com.yonyou.dms.common.domains.DTO.basedata.ConfirmCarAndUpdateCustomerDTO;
import com.yonyou.dms.common.domains.DTO.stockmanage.VehicleDTO;
import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.function.exception.ServiceBizException;
import com.yonyou.dms.retail.domains.DTO.ordermanage.SalesOrderDTO;
import com.yonyou.dms.retail.service.ordermanage.ConfirmcarService;
import com.yonyou.f4.mvc.annotation.TxnConn;
import com.yonyou.f4.mvc.controller.BaseController;

/**
* 交车确认控制类
* @author zhongsw
* @date 2016年9月27日
*/

@Controller
@TxnConn
@RequestMapping("/ordermanage/confirmcar")
public class ConfirmcarController extends BaseController{
    @Autowired
    private ConfirmcarService confirmcarService;
    
    /**查询
     */
         
     @RequestMapping(method = RequestMethod.GET)
     @ResponseBody
     public PageInfoDto searchConfirmcar(@RequestParam Map<String, String> queryParam) {
         PageInfoDto pageInfoDto = confirmcarService.searchConfirmcar(queryParam);
         return pageInfoDto;
     }
     
     /**
      * 保有客户信息
      * 
      * @author LGQ
      * @date 2016年1月1日
      * @param queryParam
      * @return
      */

     @RequestMapping(value = "/test/{id}", method = RequestMethod.GET)
     @ResponseBody
     public List<Map>  searchTest(@PathVariable(value = "id") String id) {
         List<Map> result  = confirmcarService.searchTest(id); 
         return result;
     }
     /**
      * 保修登记卡信息
      * 
      * @author LGQ
      * @date 2016年1月1日
      * @param queryParam
      * @return
      */

     @RequestMapping(value = "/confirm/{id}", method = RequestMethod.GET)
     @ResponseBody
     public Map<String, Object>  searchConfirm(@PathVariable(value = "id") String id) {
         List<Map> result  = confirmcarService.searchConfirm(id); 
         return result.get(0);
     }
     
     /**
      * 打印
      * 
      * @author LGQ
      * @date 2016年1月1日
      * @param queryParam
      * @return
      */

     @RequestMapping(value = "/print/{id}", method = RequestMethod.GET)
     @ResponseBody
     public Map<String, Object>  searchPrint(@PathVariable(value = "id") String id) {
         List<Map> result  = confirmcarService.searchPrint(id); 
         return result.get(0);
     }
     /**
      *查询界面保有客户信息保存
      * 
      * @author LGQ
      * @date 2016年1月1日
      * @param queryParam
      * @return
      */
     @RequestMapping(value = "/savecustomerinfo/car", method = RequestMethod.PUT)
     @ResponseStatus(HttpStatus.CREATED)
     public void updateCustomerInfo(@RequestBody ConfirmCarAndUpdateCustomerDTO ConfirmCarDto,
                                            UriComponentsBuilder uriCB) {
         confirmcarService.saveCustomerInfo(ConfirmCarDto);
     }
     /**
      * 修改信息
      * 
      * @author LGQ
      * @date 2016年1月1日
      * @param queryParam
      * @return
      */
     @RequestMapping(value = "/updatecustomerinfo/car", method = RequestMethod.PUT)
     @ResponseStatus(HttpStatus.CREATED)
     public void saveCustomerInfo(@RequestBody ConfirmCarAndUpdateCustomerDTO ConfirmCarDto,
                                            UriComponentsBuilder uriCB) {
         confirmcarService.updateCustomerInfo(ConfirmCarDto);
     }
     /**
      * 修改信息
      * 
      * @author LGQ
      * @date 2016年1月1日
      * @param queryParam
      * @return
      */
     @RequestMapping(value = "/change/vin/car", method = RequestMethod.PUT)
     @ResponseStatus(HttpStatus.CREATED)
     public void addSalesOrder(@RequestBody  SalesOrderDTO salesOrderDTO,
                                            UriComponentsBuilder uriCB) {
         confirmcarService.addSalesOrder(salesOrderDTO);
     }
     /**
      * 修改VIN号
      */
     @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
     @ResponseBody
     public ResponseEntity<VehicleDTO> updateVIN(@PathVariable("id") Long id,@RequestBody SalesOrderDTO salesOrderDTO,UriComponentsBuilder uriCB) throws ServiceBizException{
         confirmcarService.updateVIN(id,salesOrderDTO);
         MultiValueMap<String,String> headers = new HttpHeaders();  
         headers.set("Location", uriCB.path("/ordermanage/confirmcar/{id}").buildAndExpand(id).toUriString());  
         return new ResponseEntity<VehicleDTO>(headers, HttpStatus.CREATED);  
     }
     
     /**
      * 交车确认
      */
     @RequestMapping(value = "/{id}/updateCar", method = RequestMethod.PUT)
     @ResponseBody
     public ResponseEntity<VehicleDTO> updateCar(@PathVariable("id") Long id,@RequestBody VehicleDTO vehicleDTO,UriComponentsBuilder uriCB) throws ServiceBizException{
         confirmcarService.updateCar(id,vehicleDTO);
         MultiValueMap<String,String> headers = new HttpHeaders();  
         headers.set("Location", uriCB.path("/ordermanage/confirmcar/{id}/updateCar").buildAndExpand(id).toUriString());  
         return new ResponseEntity<VehicleDTO>(headers, HttpStatus.CREATED);  
     }
     
     /**
      * 
     * 待交车查询，右侧快捷
     * @author yll
     * @date 2016年10月13日
     * @param queryBuilder
     * @param range
     * @param state
     * @return
      */
     @RequestMapping(value="/quickQuery",method=RequestMethod.GET)
     @ResponseBody
     public List<Map> queryInWorkOrder(){
         List<Map> pageInfoDto=confirmcarService.quickQuery();
         return pageInfoDto;
     }

}
