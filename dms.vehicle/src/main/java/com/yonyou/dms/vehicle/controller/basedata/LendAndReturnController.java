
/** 
*Copyright 2017 Yonyou Corporation Ltd. All Rights Reserved.
* This software is published under the terms of the Yonyou Software
* License version 1.0, a copy of which has been included with this
* distribution in the LICENSE.txt file.
*
* @Project Name : dms.vehicle
*
* @File name : LendAndReturnController.java
*
* @Author : yangjie
*
* @Date : 2017年1月13日
*
----------------------------------------------------------------------------------
*     Date       Who       Version     Comments
* 1. 2017年1月13日    yangjie    1.0
*
*
*
*
----------------------------------------------------------------------------------
*/

package com.yonyou.dms.vehicle.controller.basedata;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
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
import com.yonyou.dms.framework.service.CommonNoService;
import com.yonyou.dms.function.common.CommonConstants;
import com.yonyou.dms.vehicle.service.stockManage.LendAndReturnService;
import com.yonyou.f4.mvc.annotation.TxnConn;

/**
 * TODO description
 * 
 * @author yangjie
 * @date 2017年1月13日
 */

@RequestMapping("/LendAndReturn")
@TxnConn
@Controller
@SuppressWarnings("rawtypes")
public class LendAndReturnController {

    @Autowired
    private LendAndReturnService lendAndReturnService;

    @Autowired
    private CommonNoService      commonNoService;

    /**
     * 查询所有借出归回单 TODO description
     * 
     * @author yangjie
     * @date 2017年1月13日
     * @param query
     * @return
     */
    @RequestMapping(value = "/findAllList", method = RequestMethod.GET)
    @ResponseBody
    public PageInfoDto findAllList(@RequestParam Map<String, String> query) {
        return lendAndReturnService.findAllDetails(query);
    }

    /**
     * 查询所有明细 TODO description
     * 
     * @author yangjie
     * @date 2017年1月13日
     * @param map
     * @return
     */
    @RequestMapping(value = "/findAllItem/{slNo}", method = RequestMethod.GET)
    @ResponseBody
    public List<Map> findAllItem(@PathVariable(value = "slNo") String stNo) {
        String no = stNo.substring(5, stNo.length());
        if (StringUtils.isNotBlank(no)) {
            return lendAndReturnService.findAllItem(no);
        } else {
            return null;
        }
    }

    /**
     * 查询车辆信息 TODO description
     * 
     * @author yangjie
     * @date 2017年1月13日
     * @param query
     * @return
     */
    @RequestMapping(value = "/findAllVehicle", method = RequestMethod.GET)
    @ResponseBody
    public PageInfoDto findAllVehicle(@RequestParam Map<String, String> query) {
        return lendAndReturnService.findAllVehicleInfo(query);
    }

    /**
     * 归还操作 TODO description
     * 
     * @author yangjie
     * @date 2017年1月13日
     * @param param
     * @param uriCB
     * @return
     */
    @RequestMapping(value = "/btnReturn", method = RequestMethod.PUT)
    public ResponseEntity<Map<String, Object>> btnReturn(@RequestBody Map<String,Object> param,
                                                         UriComponentsBuilder uriCB) {
        lendAndReturnService.btnReturn(param);
        MultiValueMap<String, String> headers = new HttpHeaders();
        headers.set("Location", uriCB.path("/LendAndReturn/findAllItem").buildAndExpand().toUriString());
        return new ResponseEntity<Map<String, Object>>(headers, HttpStatus.CREATED);
    }
    
    /**
     * 新增借出归还信息
    * TODO description
    * @author yangjie
    * @date 2017年1月13日
    * @param param
    * @param uriCB
    * @return
     */
    @RequestMapping(value = "/btnSaveAdd", method = RequestMethod.POST)
    public ResponseEntity<Map<String, Object>> btnSaveAdd(@RequestBody Map<String,Object> param,
                                                          UriComponentsBuilder uriCB){
        
        String no = commonNoService.getSystemOrderNo(CommonConstants.SAL_ZZKC_GHD);
        param.put("slNo", no);
        
        lendAndReturnService.addOrEditItem(param, true);
        lendAndReturnService.addOrEditDetails(param);
        
        MultiValueMap<String, String> headers = new HttpHeaders();
        headers.set("Location", uriCB.path("/LendAndReturn/findAllItem").buildAndExpand().toUriString());
        return new ResponseEntity<Map<String, Object>>(headers, HttpStatus.CREATED);
    }
    
    /**
     * 修改借出归还信息
    * TODO description
    * @author yangjie
    * @date 2017年1月13日
    * @param param
    * @param uriCB
    * @return
     */
    @RequestMapping(value = "/btnSaveEdit", method = RequestMethod.PUT)
    public ResponseEntity<Map<String, Object>> btnSaveEdit(@RequestBody Map<String,Object> param,
                                                          UriComponentsBuilder uriCB){
        
        lendAndReturnService.addOrEditItem(param, false);
        lendAndReturnService.addOrEditDetails(param);
        
        MultiValueMap<String, String> headers = new HttpHeaders();
        headers.set("Location", uriCB.path("/LendAndReturn/findAllItem").buildAndExpand().toUriString());
        return new ResponseEntity<Map<String, Object>>(headers, HttpStatus.CREATED);
    }
    
    /**
     * 查询SL_NO和VIN
    * TODO description
    * @author yangjie
    * @date 2017年1月13日
    * @param slNo
    * @param vin
    * @return
     */
    @RequestMapping(value="/findByVin/{slNo}/{vin}",method=RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> findSlnoVin(@PathVariable String slNo,@PathVariable String vin){
        Map<String, Object> map = new HashMap<String,Object>();
        map.put("SL_NO", slNo);
        map.put("VIN", vin);
        return map;
    }
}
