
/** 
*Copyright 2016 Yonyou Corporation Ltd. All Rights Reserved.
* This software is published under the terms of the Yonyou Software
* License version 1.0, a copy of which has been included with this
* distribution in the LICENSE.txt file.
*
* @Project Name : dms.manage
*
* @File name : BasicComController.java
*
* @Author : zhanshiwei
*
* @Date : 2016年7月19日
*
----------------------------------------------------------------------------------
*     Date       Who       Version     Comments
* 1. 2016年7月19日    zhanshiwei    1.0
*
*
*
*
----------------------------------------------------------------------------------
*/

package com.yonyou.dms.manage.controller.basedata;

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

import com.yonyou.dms.framework.domains.DTO.baseData.BasicParametersDTO;
import com.yonyou.dms.framework.service.baseData.SystemParamService;
import com.yonyou.dms.manage.domains.DTO.basedata.BasicParametersListDTO;
import com.yonyou.dms.manage.service.basedata.parameter.BasicComService;
import com.yonyou.f4.mvc.annotation.TxnConn;
import com.yonyou.f4.mvc.controller.BaseController;

/**
 * 基础参数设置
 * 
 * @author zhanshiwei
 * @date 2016年7月19日
 */
@Controller
@TxnConn
@RequestMapping("/basedata/BasicParameters")
public class BasicComController extends BaseController {

    @Autowired
    private BasicComService basicComService;
    @Autowired
    private SystemParamService  systemparamservice;

    /**
     * 新增修改基础参数设置
     * 
     * @author zhanshiwei
     * @date 2016年8月2日
     * @param parameterList
     * @param uriCB
     * @return
     */

    @RequestMapping(value = "/basicInsertOrUp", method = RequestMethod.PUT)
    public ResponseEntity<BasicParametersListDTO> modifyCustomerReso(@RequestBody BasicParametersListDTO parameterList,
                                                                     UriComponentsBuilder uriCB) {
        basicComService.modifyBasicParametersListDTO(parameterList);
        MultiValueMap<String, String> headers = new HttpHeaders();
        headers.set("Location", uriCB.path("/basedata/BasicParameters/basicCommunal").buildAndExpand().toUriString());
        return new ResponseEntity<>(headers, HttpStatus.CREATED);
    }

    /**
     * 查询基础参数设置信息
     * 
     * @author zhanshiwei
     * @date 2016年8月2日
     * @param queryParam
     * @return
     */

    @RequestMapping(value = "/basicCommunal", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Map<String, Object>> queryBasicParameters(@RequestParam Map<String, String> queryParam) {
        Map<String, Map<String, Object>> ruse = basicComService.queryBasicParameters(queryParam);
        return ruse;
    }
    
    /**
     * 
     * 提供给下拉框的方法
     * @author yll
     * @date 2016年7月8日
     * @param queryParam
     * @return
     */
    @RequestMapping(value="/paying/bank",method = RequestMethod.GET)
    @ResponseBody
    public List<Map> queryBank(@RequestParam Map<String,String> queryParam) {
        List<Map> banklist = basicComService.queryBank(queryParam);
        return banklist;
    }

    /**
     * 根据参数类型(PARAM_TYPE)查询参数信息
     * 
     * @author zhanshiwei
     * @date 2016年8月5日
     * @param type
     * @param queryParam
     * @return
     */

    @RequestMapping(value = "/{type}", method = RequestMethod.GET)
    @ResponseBody
    public List<BasicParametersDTO> queryBasicParameterByType(@PathVariable Long type) {
        List<BasicParametersDTO> listBaseDto = systemparamservice.queryBasicParameterByType(type);
        return listBaseDto;
    }

    /**
     * 根据参数类型(PARAM_TYPE)、参数CODE(PARAM_CODE)查询参数信息
     * 
     * @author zhanshiwei
     * @date 2016年8月5日
     * @param type
     * @param code
     * @param queryParam
     * @return
     */

    @RequestMapping(value = "/{type}/{code}", method = RequestMethod.GET)
    @ResponseBody
    public BasicParametersDTO queryBasicParameterByTypeandCode(@PathVariable("type") Long type,
                                                               @PathVariable String code) {
        BasicParametersDTO basiDto = systemparamservice.queryBasicParameterByTypeandCode(type, code);
        return basiDto;
    }
}
