
/** 
*Copyright 2016 Yonyou Corporation Ltd. All Rights Reserved.
* This software is published under the terms of the Yonyou Software
* License version 1.0, a copy of which has been included with this
* distribution in the LICENSE.txt file.
*
* @Project Name : dms.part
*
* @File name : PartProfitController.java
*
* @Author : xukl
*
* @Date : 2016年8月12日
*
----------------------------------------------------------------------------------
*     Date       Who       Version     Comments
* 1. 2016年8月12日    xukl    1.0
*
*
*
*
----------------------------------------------------------------------------------
*/
	
package com.yonyou.dms.part.controller.stockmanage;

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

import com.yonyou.dms.common.domains.PO.basedata.TtPartProfitPO;
import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.framework.service.CommonNoService;
import com.yonyou.dms.function.common.CommonConstants;
import com.yonyou.dms.part.domains.DTO.stockmanage.PartProfitDto;
import com.yonyou.dms.part.service.stockmanage.PartProfitService;
import com.yonyou.f4.mvc.annotation.TxnConn;
import com.yonyou.f4.mvc.controller.BaseController;

/**
* 配件报溢控制类
* @author xukl
* @date 2016年8月12日
*/
@SuppressWarnings("rawtypes")
@Controller
@TxnConn
@RequestMapping("/stockmanage/partProfits")
public class PartProfitController extends BaseController {
    @Autowired
    private PartProfitService partProfitService;
    @Autowired
    private CommonNoService  commonNoService;
    /**
    * 根据查询条件查询
    * @author xukl
    * @date 2016年8月15日
    * @param queryParam
    * @return
    */
    @RequestMapping(method = RequestMethod.GET)
    @ResponseBody
    public PageInfoDto queryPartProfits(@RequestParam Map<String, String> queryParam) {
        PageInfoDto pageInfoDto = partProfitService.qryPartProfit(queryParam);
        return pageInfoDto;
    }
    /**
     * 根据id查询配件报溢信息
     * @author xukl
     * @date 2016年8月15日
     * @param id
     * @return
     */
         
     @RequestMapping(value = "/{id}",method = RequestMethod.GET)
     @ResponseBody
     public Map<String,Object> getPartAllocateInById(@PathVariable(value = "id") Long id) {
         TtPartProfitPO PartProfitPO = partProfitService.getPartProfitById(id);
         return PartProfitPO.toMap();
     }
     /**
      * 通过主表ID查询配件报溢明细表数据
      * @author xukl
      * @date 2016年8月15日
      * @param id
      * @return
      */
      
    @RequestMapping(value = "/{id}/Items",method = RequestMethod.GET)
      @ResponseBody
      public List<Map> getPartAllocateInItems(@PathVariable(value = "id") Long id) {
          List<Map> list = partProfitService.getPartProfitItemsById(id);
          return list;
      }
     
    /**
    * 新增
    * @author xukl
    * @date 2016年8月16日
    * @param partProfitDto
    * @param uriCB
    * @return
    */
    	
    @RequestMapping(method = RequestMethod.POST)
     public ResponseEntity<Map<String,Object>> addAllocateIn(@RequestBody @Valid PartProfitDto partProfitDto, UriComponentsBuilder uriCB) {
    	TtPartProfitPO partProfitPO = partProfitService.addPartProfit(commonNoService.getSystemOrderNo(CommonConstants.PART_Profit_PREFIX),partProfitDto);
         MultiValueMap<String, String> headers = new HttpHeaders();
         headers.set("Location", uriCB.path("/stockmanage/partProfits/{id}").buildAndExpand(partProfitPO.getId()).toUriString());
         return new ResponseEntity<Map<String,Object>>(partProfitPO.toMap(), headers, HttpStatus.CREATED);

     }
     
     /**
     * 修改
     * @author xukl
     * @date 2016年8月10日
     * @param id
     * @param partAllocateInDto
     * @param uriCB
     * @return
     */
         
     @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
     public ResponseEntity<PartProfitDto> ModifyPartProfit(@PathVariable("id") Long id,@RequestBody @Valid PartProfitDto partProfitDto,UriComponentsBuilder uriCB) {
         partProfitService.updatePartProfit(id, partProfitDto);
         MultiValueMap<String, String> headers = new HttpHeaders();  
         headers.set("Location", uriCB.path("/stockmanage/partprofits/{id}").buildAndExpand(id).toUriString());  
         return new ResponseEntity<PartProfitDto>(headers, HttpStatus.CREATED);  
     }
     /**
      * 删除
      * @author xukl
      * @date 2016年8月15日
      * @param id
      * @param uriCB
      */
          
      @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
      @ResponseStatus(HttpStatus.NO_CONTENT)
      public void deleteUser(@PathVariable("id") Long id, UriComponentsBuilder uriCB) {
          partProfitService.deletePartProfitbyId(id);;
      }
     
     /**
     * 入账
     * @author xukl
     * @date 2016年8月15日
     * @param id
     */
         
     @RequestMapping(value="/{id}/profitInWhse",method=RequestMethod.PUT)
     @ResponseBody
     public void updateOrderStatusById(@PathVariable Long id,@RequestBody @Valid PartProfitDto partProfitDto){
         //保存
         partProfitService.updatePartProfit(id, partProfitDto);
         //入账
         partProfitService.doProfitInWhouse(id);
     }
}
