
/** 
*Copyright 2017 Yonyou Corporation Ltd. All Rights Reserved.
* This software is published under the terms of the Yonyou Software
* License version 1.0, a copy of which has been included with this
* distribution in the LICENSE.txt file.
*
* @Project Name : dms.part
*
* @File name : LendStuffController.java
*
* @Author : dingchaoyu
*
* @Date : 2017年5月10日
*
----------------------------------------------------------------------------------
*     Date       Who       Version     Comments
* 1. 2017年5月10日    dingchaoyu    1.0
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
import com.yonyou.dms.part.domains.DTO.basedata.LendStuffDTO;
import com.yonyou.dms.part.service.basedata.LendStuffService;
import com.yonyou.f4.mvc.annotation.TxnConn;

/**
* TODO description
* @author dingchaoyu
* @date 2017年5月10日
*/
@SuppressWarnings("rawtypes")
@Controller
@TxnConn
@RequestMapping("/basedata/lendStuff")
public class LendStuffController {
    
    @Autowired
    private LendStuffService lendStuffController;
    
    /**
     * 维修类型下拉
     * @author dingchaoyu
     * @date 2016年7月27日
     * @param queryParam
     * @return
     */

    @RequestMapping(value="/queryType",method = RequestMethod.GET)
    @ResponseBody
    public List<Map> queryRepairType() {
        return lendStuffController.queryRepairType();
    }
    
    /**
     * 查询
     * @author dingchaoyu
     * @date 2016年7月27日
     * @param queryParam
     * @return
     */

    @RequestMapping(value="/query",method = RequestMethod.GET)
    @ResponseBody
    public PageInfoDto query(@RequestParam Map map) {
        PageInfoDto pageInfoDto = lendStuffController.query(map);
        return pageInfoDto;
    }
    
    /**
     * 查询维修项目
     * @author dingchaoyu
     * @date 2016年7月27日
     * @param queryParam
     * @return
     */

    @RequestMapping(value="/queryRepair/{roNo}",method = RequestMethod.GET)
    @ResponseBody
    public PageInfoDto queryRepair(@PathVariable("roNo") String id) {
        PageInfoDto pageInfoDto = lendStuffController.queryRepair(id);
        return pageInfoDto;
    }
    
    /**
     * 查询负库存
     * @author dingchaoyu
     * @date 2016年7月27日
     * @param queryParam
     * @return
     */

    @RequestMapping(value="/queryStock",method = RequestMethod.GET)
    @ResponseBody
    public PageInfoDto queryStock(@RequestParam Map dto) {
        return lendStuffController.queryStock(dto);
    }
    
    /**
     * 查询负库存
     * @author dingchaoyu
     * @date 2016年7月27日
     * @param queryParam
     * @return
     */

    @RequestMapping(value="/queryStocks",method = RequestMethod.PUT)
    @ResponseBody
    public ResponseEntity<String> queryStocks(@RequestBody LendStuffDTO dto, UriComponentsBuilder uriCB) {
        String s = lendStuffController.queryStocks(dto);
        MultiValueMap<String, String> headers = new HttpHeaders();
        headers.set("Location", uriCB.path("/basedata/lendStuff").buildAndExpand().toUriString());
        return new ResponseEntity<String>(s, headers, HttpStatus.CREATED);
    }
    
    /**
     * 出库
     */
    @RequestMapping(value="/save",method = RequestMethod.PUT)
    public ResponseEntity<String> partWorkshopItem(@RequestBody LendStuffDTO dto, UriComponentsBuilder uriCB){
       lendStuffController.partWorkshopItem(dto);
       MultiValueMap<String, String> headers = new HttpHeaders();
       headers.set("Location", uriCB.path("/basedata/lendStuff").buildAndExpand().toUriString());
       return new ResponseEntity<String>( headers, HttpStatus.CREATED);
    }
}
