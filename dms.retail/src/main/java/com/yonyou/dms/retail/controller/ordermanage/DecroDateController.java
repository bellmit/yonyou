
/** 
*Copyright 2016 Yonyou Corporation Ltd. All Rights Reserved.
* This software is published under the terms of the Yonyou Software
* License version 1.0, a copy of which has been included with this
* distribution in the LICENSE.txt file.
*
* @Project Name : dms.retail
*
* @File name : DecroDateController.java
*
* @Author : zsw
*
* @Date : 2016年9月5日
*
----------------------------------------------------------------------------------
*     Date       Who       Version     Comments
* 1. 2016年9月5日    zsw    1.0
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
import org.springframework.web.util.UriComponentsBuilder;

import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.function.exception.ServiceBizException;
import com.yonyou.dms.retail.domains.DTO.ordermanage.DecorationDTO;
import com.yonyou.dms.retail.service.ordermanage.DecroDateService;
import com.yonyou.f4.mvc.annotation.TxnConn;
import com.yonyou.f4.mvc.controller.BaseController;

/**
* 装潢项目操作类
* @author zsw
* @date 2016年9月5日
*/
@Controller
@TxnConn
@RequestMapping("/ordermanage/decroDate")
public class DecroDateController extends BaseController{
    @Autowired
    private DecroDateService decroDateService;
    
    /**查询
    * 
    * @author zhongsw
    * @date 2016年9月11日
    * @param queryParam
    * @return
    */
    	
    @RequestMapping(method = RequestMethod.GET)
    @ResponseBody
    public PageInfoDto queryDecorDate(@RequestParam Map<String, String> queryParam) {
        PageInfoDto pageInfoDto = decroDateService.queryDecroDate(queryParam);
        return pageInfoDto;
    }
    
    /**
     * 
     * 新增用户信息
     * @author zhongshiwei
     * @date 
     * @param 
     * @return 新增信息
     */
    @RequestMapping(value = "/decro", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<DecorationDTO> addDecro(@RequestBody DecorationDTO decroDTO,UriComponentsBuilder uriCB) throws ServiceBizException{
        Long id = decroDateService.insertDecro(decroDTO);
        MultiValueMap<String,String> headers = new HttpHeaders();  
        headers.set("Location", uriCB.path("/ordermanage/decroDate/decro/{id}").buildAndExpand(id).toUriString());  
        return new ResponseEntity<DecorationDTO>(decroDTO,headers, HttpStatus.CREATED);  
    }
    
    /**
     * 
     * 根据ID 修改用户信息
     * @author zhongsw
     * @date 2016年9月9日
     * @param id
     * @param 
     * @param uriCB
     * @return 修改信息
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    @ResponseBody
    public ResponseEntity<DecorationDTO> updateDecro(@PathVariable("id") Long id,@RequestBody DecorationDTO decroDTO,UriComponentsBuilder uriCB) throws ServiceBizException{
        decroDateService.updateDecro(id,decroDTO);
        MultiValueMap<String,String> headers = new HttpHeaders();  
        headers.set("Location", uriCB.path("/ordermanage/decroDate/{id}").buildAndExpand(id).toUriString());  
        return new ResponseEntity<DecorationDTO>(headers, HttpStatus.CREATED);  
    }

    
    /**明细子表
    * 
    * @author zhongsw
    * @date 2016年9月6日
    * @param id
    * @return
    */
    	
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @ResponseBody
    public List<Map> getDecoraById(@PathVariable("id") Long id) {
        List<Map> decro= decroDateService.getDecoraById(id);
        return decro;
    }
    /**编辑明细主表数据查询
     * 
     * @author zhongsw
     * @date 2016年9月6日
     * @param id
     * @return
     */
         
     @RequestMapping(value = "/{id}/edit", method = RequestMethod.GET)
     @ResponseBody
     public Map editDecoraById(@PathVariable("id") Long id) {
         Map decro= decroDateService.editDecoraById(id);
         return decro;
     }
     
     /**编辑明细主表数据子表查询
      * 
      * @author zhongsw
      * @date 2016年9月6日
      * @param id
      * @return
      */
          
      @RequestMapping(value = "/{id}/editItem", method = RequestMethod.GET)
      @ResponseBody
      public List<Map> editDecoraByIdItem(@PathVariable("id") Long id) {
          List<Map> decro= decroDateService.editDecoraByIdItem(id);
          return decro;
      }
    /**
     * 根据ID 删除用户信息
     * @author zhongsw
     * @date 2016年8月5日
     * @param id
     * @param uriCB
     * @throws ServiceBizException
     */

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    @ResponseBody
    public void deleteDecro(@PathVariable("id") Long id,UriComponentsBuilder uriCB) throws ServiceBizException{
        decroDateService.deleteDecroById(id);
    }
    
    
    /**
    * 销售订单功能双击主表显示子表字段信息
    * @author zhongsw
    * @date 2016年9月19日
    * @param id
    * @return
    */
    	
    @RequestMapping(value = "/{id}/all", method = RequestMethod.GET)
    @ResponseBody
    public PageInfoDto searchSalesOrderItem(@PathVariable("id") Long id){
        PageInfoDto sale=decroDateService.searchSalesOrderItem(id);
        return sale;
    }
    
}
