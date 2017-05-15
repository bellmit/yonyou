
/** 
*Copyright 2017 Yonyou Corporation Ltd. All Rights Reserved.
* This software is published under the terms of the Yonyou Software
* License version 1.0, a copy of which has been included with this
* distribution in the LICENSE.txt file.
*
* @Project Name : dms.part
*
* @File name : BookPartController.java
*
* @Author : dingchaoyu
*
* @Date : 2017年4月19日
*
----------------------------------------------------------------------------------
*     Date       Who       Version     Comments
* 1. 2017年4月19日    dingchaoyu    1.0
*
*
*
*
----------------------------------------------------------------------------------
*/
	
package com.yonyou.dms.part.controller.basedata;

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
import com.yonyou.dms.part.domains.DTO.basedata.BookPartDTO;
import com.yonyou.dms.part.service.basedata.BookPartService;
import com.yonyou.f4.mvc.annotation.TxnConn;
import com.yonyou.f4.mvc.controller.BaseController;

/**
* TODO description
* @author dingchaoyu
* @date 2017年4月19日
*/
@Controller
@TxnConn
@RequestMapping("/basedata/BookPart")
public class BookPartController extends BaseController {
    
    @Autowired
    private BookPartService bookPartService;
    
    /**
     * 查询主数据
     */
    @RequestMapping(value="/item/{OBLIGATED_NO}",method = RequestMethod.GET)
    @ResponseBody
    public PageInfoDto queryPartObligatedItem(@PathVariable(value = "OBLIGATED_NO") String id) {
        return bookPartService.queryPartObligatedItem(id);
    }
    
    /**
     * 预留单号
     */
    @RequestMapping(value="/oblgno",method = RequestMethod.GET)
    @ResponseBody
    public PageInfoDto queryPartObligated(@RequestParam Map<String, String> queryParam) {
        return bookPartService.queryPartObligated(queryParam);
    }
    
    /**
     * 工单号
     */
    @RequestMapping(value="/rono",method = RequestMethod.GET)
    @ResponseBody
    public PageInfoDto queryRepairOrderBy(@RequestParam Map<String, String> queryParam){
        return bookPartService.queryRepairOrderBy(queryParam);
    }
    
    /**
     * 查询配件
     */
    @RequestMapping(value="/part",method = RequestMethod.GET)
    @ResponseBody
    public PageInfoDto queryStockInfo(@RequestParam Map<String, String> queryParam){
        return bookPartService.queryStockInfo(queryParam);
    }
    
    /**
     * 查询配件明细
     */
    @RequestMapping(value="/parts/{PART_NO}/{STORAGE_CODE}",method = RequestMethod.GET)
    @ResponseBody
    public PageInfoDto queryPartInfo(@PathVariable("PART_NO") String id,@PathVariable("STORAGE_CODE") String ids){
        return bookPartService.queryPartInfo(id,ids);
    }
    
    /**
     * 查询配件明细
     */
    @RequestMapping(value="/parts",method = RequestMethod.GET)
    @ResponseBody
    public Map queryPartInfos(@RequestParam Map<String, String> queryParam){
        return bookPartService.queryPartInfos(queryParam);
    }
    
    /**
     * 关单
     */
    @RequestMapping(value="/close",method = RequestMethod.PUT)
    @ResponseBody
    public void performExecute(@RequestBody BookPartDTO bookPartDTO){
        bookPartService.performExecute(bookPartDTO);
    }
    
    /**
     * 预留
     */
    @RequestMapping(value="/hold",method = RequestMethod.PUT)
    public ResponseEntity<String> performExecutes(@RequestBody BookPartDTO bookPartDTO, UriComponentsBuilder uriCB){
       String s = bookPartService.performExecutes(bookPartDTO);
       MultiValueMap<String, String> headers = new HttpHeaders();
       headers.set("Location", uriCB.path("/vehicleStock/stockIn").buildAndExpand().toUriString());
       return new ResponseEntity<String>(s, headers, HttpStatus.CREATED)  ;
    }
    
    /**
     * 查询工单清单
     */
    @RequestMapping(value="/rolist/{RO_NO}",method = RequestMethod.GET)
    @ResponseBody
    public PageInfoDto queryPartsFromRepairOrder(@PathVariable("RO_NO") String id){
        return bookPartService.queryPartsFromRepairOrder(id);
    }
    
    /**
     * 替换件  
     */
    @RequestMapping(value="/replace/{PART_NO}",method = RequestMethod.GET)
    @ResponseBody
    public PageInfoDto QueryPartsReplace(@PathVariable("PART_NO") String id){
        return bookPartService.QueryPartsReplace(id);
    }
    
    /**
     * 查询删除件
     */
    @RequestMapping(value="/querys",method = RequestMethod.GET)
    @ResponseBody
    public void queryPart(@RequestParam Map<String, String> map){
        bookPartService.queryPart(map);
    }
    /**
     * 车间借料
     * @author wantao
     * @date 2017年5月5日
     * @param param
     * @return 
     * @return
      */
    @RequestMapping(value="/{roNo}/queryPartWorkshopItem",method = RequestMethod.GET)
    @ResponseBody
    public List<Map> queryPartWorkshopItem(@PathVariable("roNo") String roNo){
        return bookPartService.queryPartWorkshopItem(roNo);
    }
    /**
     * 车间借料
     * @author wantao
     * @date 2017年5月9日
     * @param param
     * @return 
     * @return
      */
    @RequestMapping(value="/{partNo}/{STORAGE_CODE}/{Ro_No}",method = RequestMethod.GET)
    @ResponseBody
    public Map<String,Object> queryPartWorkshopDetail(@PathVariable(value = "Ro_No") String roNo,@PathVariable(value = "STORAGE_CODE") String storageCode,@PathVariable(value = "partNo") String partNo){
        return bookPartService.queryPartWorkshopDetail(roNo,storageCode,partNo);
    }
    /**
     * 车间借料-查询配件明细
     * @author wantao
     * @date 2017年5月9日
     * @param param
     * @return 
     * @return
      */
    @RequestMapping(value="/partsVehicle/{partNo}/{STORAGE_CODE}",method = RequestMethod.GET)
    @ResponseBody
    public List<Map> queryPartInfoVehicle(@PathVariable("partNo") String id,@PathVariable("STORAGE_CODE") String ids){
        return bookPartService.queryPartInfoVehicle(id,ids);
    }
}
