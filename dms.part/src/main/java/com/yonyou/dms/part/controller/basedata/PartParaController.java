
/** 
*Copyright 2017 Yonyou Corporation Ltd. All Rights Reserved.
* This software is published under the terms of the Yonyou Software
* License version 1.0, a copy of which has been included with this
* distribution in the LICENSE.txt file.
*
* @Project Name : dms.part
*
* @File name : PartParaController.java
*
* @Author : zhanshiwei
*
* @Date : 2017年4月13日
*
----------------------------------------------------------------------------------
*     Date       Who       Version     Comments
* 1. 2017年4月13日    zhanshiwei    1.0
*
*
*
*
----------------------------------------------------------------------------------
*/

package com.yonyou.dms.part.controller.basedata;

import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.util.UriComponentsBuilder;

import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.part.domains.DTO.basedata.ListTtAdPartParaDTO;
import com.yonyou.dms.part.service.basedata.PartParaService;
import com.yonyou.f4.mvc.annotation.TxnConn;
import com.yonyou.f4.mvc.controller.BaseController;

/**
 * 配件订货参数
 * 
 * @author zhanshiwei
 * @date 2017年4月13日
 */
@Controller
@TxnConn
@RequestMapping("partManage/adPartPara")
public class PartParaController extends BaseController {

    @Autowired
    private PartParaService partparaservice;

    
    /**
    * 配件订货参数查询
    * @author zhanshiwei
    * @date 2017年4月13日
    * @param queryParams
    * @return
    */
    	
    @RequestMapping(method = RequestMethod.GET)
    @ResponseBody
    public PageInfoDto queryParPara(@RequestParam Map<String, String> queryParams) {
        PageInfoDto id = partparaservice.queryParPara(queryParams);
        return id;
    }
    
    @RequestMapping(method = RequestMethod.PUT)
    @ResponseStatus(HttpStatus.CREATED)
    public void addTtAdPartPara(@RequestBody  ListTtAdPartParaDTO partPdto, UriComponentsBuilder uriCB) {
        partparaservice.addTtAdPartPara(partPdto);
    }
}
