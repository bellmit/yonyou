
/** 
*Copyright 2017 Yonyou Corporation Ltd. All Rights Reserved.
* This software is published under the terms of the Yonyou Software
* License version 1.0, a copy of which has been included with this
* distribution in the LICENSE.txt file.
*
* @Project Name : dms.part
*
* @File name : StuffPriceAdjustController.java
*
* @Author : zhanshiwei
*
* @Date : 2017年5月9日
*
----------------------------------------------------------------------------------
*     Date       Who       Version     Comments
* 1. 2017年5月9日    zhanshiwei    1.0
*
*
*
*
----------------------------------------------------------------------------------
*/

package com.yonyou.dms.part.controller.partOrder;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.util.UriComponentsBuilder;

import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.function.exception.ServiceBizException;
import com.yonyou.dms.part.domains.DTO.partOrder.TtRepairOrderDTO;
import com.yonyou.dms.part.service.partOrder.StuffPriceAdjustService;
import com.yonyou.f4.mvc.annotation.TxnConn;
import com.yonyou.f4.mvc.controller.BaseController;

/**
 * 发料价格调整
 * 
 * @author zhanshiwei
 * @date 2017年5月9日
 */

@Controller
@TxnConn
@RequestMapping("/partOrder/stuffPriceAdjust")
public class StuffPriceAdjustController extends BaseController {

    @Autowired
    private StuffPriceAdjustService stuffpriceadjustservice;

    /**
     * 业务描述：查询工单信息
     * 
     * @author zhanshiwei
     * @date 2017年5月9日
     * @param queryParam
     * @return
     */

    @RequestMapping(value = "/selectRepairOrder", method = RequestMethod.GET)
    @ResponseBody
    public PageInfoDto queryRepairOrder(@RequestParam Map<String, String> queryParam) {
        PageInfoDto id = stuffpriceadjustservice.queryRepairOrder(queryParam);
        return id;

    }

    /**
     * @author zhanshiwei
     * @date 2017年5月9日
     * @param queryParam
     * @return
     */

    @RequestMapping(value = "/{roNo}/queryPartSendPrice", method = RequestMethod.GET)
    @ResponseBody
    @ResponseStatus(HttpStatus.CREATED)
    public List<Map> queryPartSendPrice(@RequestParam Map<String, String> queryParam,
                                        @PathVariable("roNo") String roNo) {
        queryParam.put("roNo", roNo);
        return stuffpriceadjustservice.queryPartSendPrice(queryParam);
    }
    

    
    /**
    * 业务描述:配件发料价格调整引起的工单修改
    * @author 
    * @date 2017年5月10日
    * @param ttRepairDto
    * @param uriCB
     * @throws Exception 
     * @throws ServiceBizException 
    */
    	
    @RequestMapping(value = "/changePartSendPrice", method = RequestMethod.PUT)
    @ResponseBody
    @ResponseStatus(HttpStatus.CREATED)
    public void  changePartSendPrice(@RequestBody  TtRepairOrderDTO ttRepairDto, UriComponentsBuilder uriCB) throws ServiceBizException, Exception {

        stuffpriceadjustservice.changePartSendPrice(ttRepairDto);
    }
    
    
}
