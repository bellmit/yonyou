
/** 
*Copyright 2017 Yonyou Corporation Ltd. All Rights Reserved.
* This software is published under the terms of the Yonyou Software
* License version 1.0, a copy of which has been included with this
* distribution in the LICENSE.txt file.
*
* @Project Name : dms.manage
*
* @File name : DefaultParaController.java
*
* @Author : zhanshiwei
*
* @Date : 2017年1月18日
*
----------------------------------------------------------------------------------
*     Date       Who       Version     Comments
* 1. 2017年1月18日    zhanshiwei    1.0
*
*
*
*
----------------------------------------------------------------------------------
*/

package com.yonyou.dms.manage.controller.basedata;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.util.UriComponentsBuilder;

import com.yonyou.dms.manage.domains.DTO.basedata.BasicParametersListDTO;
import com.yonyou.dms.manage.domains.DTO.basedata.TmDefaultParaListDTO;
import com.yonyou.dms.manage.service.basedata.parameter.DefaultParaService;
import com.yonyou.f4.mvc.annotation.TxnConn;
import com.yonyou.f4.mvc.controller.BaseController;

/**
 * 基础参数
 * 
 * @author zhanshiwei
 * @date 2017年1月18日
 */
@Controller
@TxnConn
@RequestMapping("/basedata/defaultPara")
public class DefaultParaController extends BaseController {

    @Autowired
    private DefaultParaService basiParaService;

    
    /**
    * 基础参数查询
    * @author zhanshiwei
    * @date 2017年1月18日
    * @param queryParam
    * @return
    */
    	
    @RequestMapping(method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Map<String, Object>> queryBasicParameters(@RequestParam Map<String, String> queryParam) {
        Map<String, Map<String, Object>> ruse = basiParaService.queryDefaultPara(queryParam);
        return ruse;
    }

    
    /**
    * 基础参数修改
    * @author zhanshiwei
    * @date 2017年1月18日
    * @param parameterList
    * @param uriCB
    * @return
    */
    	
    @RequestMapping(value = "/defaultParaInsertOrUp", method = RequestMethod.PUT)
    public ResponseEntity<BasicParametersListDTO> modifyCustomerReso(@RequestBody TmDefaultParaListDTO parameterList,
                                                                     UriComponentsBuilder uriCB) {
        basiParaService.modifyBasicParametersListDTO(parameterList);
        MultiValueMap<String, String> headers = new HttpHeaders();
        headers.set("Location",
                    uriCB.path("/basedata/defaultPara/defaultParaInsertOrUp").buildAndExpand().toUriString());
        return new ResponseEntity<>(headers, HttpStatus.CREATED);
    }
}
