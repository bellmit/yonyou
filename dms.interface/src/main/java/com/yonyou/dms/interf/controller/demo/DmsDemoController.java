/*
* Copyright 2016 Yonyou Auto Information Technology（Shanghai） Co., Ltd. All Rights Reserved.
*
* This software is published under the terms of the YONYOU Software
* License version 1.0, a copy of which has been included with this
* distribution in the LICENSE.txt file.
*
* @Project Name : marketing-dealer-open-api
*
* @File name : RestReceiveDemoController.java
*
* @Author : sangdeliang
*
* @Date : 2016年11月29日
*
----------------------------------------------------------------------------------
*     Date       Who       Version     Comments
* 1. 2016年11月29日    sangdeliang    1.0
*
*
*
*
----------------------------------------------------------------------------------
*/
package com.yonyou.dms.interf.controller.demo;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.yonyou.dms.framework.manager.TransactionDealerManager;
import com.yonyou.dms.framework.manager.interf.AutoTransactionAction;
import com.yonyou.dms.framework.manager.interf.AutoTransactionDataAction;
import com.yonyou.dms.interf.domains.DTO.demo.RepairBookApplyDto;
import com.yonyou.dms.interf.domains.DTO.demo.RepairBookTimeDto;
import com.yonyou.f4.mvc.controller.BaseController;

/**
 * @author zhangxc @ description 使用OKHTTP 接收养修预约的消息
 * @date 2016年11月29日
 */
@Controller
@RequestMapping("/demo")
public class DmsDemoController extends BaseController{

    private Logger logger = LoggerFactory.getLogger(this.getClass());
    
    //事务控制器
    @Autowired
    TransactionDealerManager<Object> autoTransManager;
    /**
     * @author zhangxianchao @ description 返回经销商的养修时间参数(无工位)(DEMO FOR DMS_TEAM)
     * @date 2016年12月5日
     * @param bookParamDto
     * @param headers
     * @param response
     * @return
     * @throws IOException
     */
    @RequestMapping(value = { "/bookingTime/v1/{appCode}/{dealerCode}/{bookingDate}" }, method = RequestMethod.GET, produces = "application/json;charset=UTF-8", headers = "accept=application/json")
    @ResponseBody
    public List<RepairBookTimeDto> bookingTime(@RequestHeader HttpHeaders headers,
                                                             @PathVariable String appCode,
                                                             @PathVariable String dealerCode,
                                                             @PathVariable String bookingDate) {
        // #######处理相关业务信息================================================================
        List<RepairBookTimeDto> timeDtoList = new ArrayList<>();
        // 模拟9:00~18:00
        for (int i = 9; i <= 17; i++) {
            RepairBookTimeDto timeDto = new RepairBookTimeDto();
            timeDto.setBeginTime(i + ":00");
            timeDto.setEndTime((i + 1) + ":00");
            timeDtoList.add(timeDto);
        }
        return timeDtoList;

    }

    /**
     * @author zhangxianchao @ description 返回经销商的养修时间参数(包含工位)(DEMO FOR DMS_TEAM)
     * @date 2016年12月5日
     * @param bookParamDto
     * @param headers
     * @param response
     * @return
     * @throws IOException
     */
    @RequestMapping(value = { "/bookingTimeWithNumber/v1/{appCode}/{dealerCode}/{bookingDate}" }, method = RequestMethod.GET)
    @ResponseBody
    public List<RepairBookTimeDto> bookingTimeWithNumber(@PathVariable("appCode") String appCode,
                                                                       @PathVariable("dealerCode") String dealerCode,
                                                                       @PathVariable("bookingDate") String bookingDate) {
        // #######处理相关业务信息================================================================
        final List<RepairBookTimeDto> timeDtoList = new ArrayList<>();
        
        //进行事务控制
        autoTransManager.autoTransExcute(dealerCode, new AutoTransactionAction() {
            //实现自已的业务逻辑
            public void autoTransAction() {
                // 模拟9:00~18:00
                for (int i = 9; i <= 17; i++) {
                    RepairBookTimeDto timeDto = new RepairBookTimeDto();
                    timeDto.setBeginTime(i + ":00");
                    timeDto.setEndTime((i + 1) + ":00");
                    timeDto.setSpareStationNum(Long.parseLong("10"));
                    timeDto.setTotalStationNum(Long.parseLong("99"));
                    timeDtoList.add(timeDto);
                }
            }
        });
        return timeDtoList;
    }

    /**
     * @author zhangxianchao @ description 返回养修预约申请结果 (DEMO FOR DMS_TEAM)
     * @date 2016年12月6日
     * @param repairApplyDto
     * @param headers
     * @param response
     * @throws IOException
     */
    @RequestMapping(value = { "/bookingOrder/v1/{appCode}/{dealerCode}/{bookingNo}" }, method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public void bookingOrder(@RequestBody RepairBookApplyDto repairApplyDto,
                             @PathVariable("appCode") String appCode,
                             @PathVariable("dealerCode") String dealerCode,
                             @PathVariable("bookingNo") String bookingDate) {
        
        //执行保存操作
        autoTransManager.autoTransExcute(dealerCode, repairApplyDto, new AutoTransactionDataAction<Object>() {
            public void autoTransAction(Object dataValue) {
                RepairBookApplyDto repairApplyDto = (RepairBookApplyDto)dataValue;
                logger.debug("保存成功："+repairApplyDto.toString());
            }
        });
        logger.info("HAS ENTER repairBookingApply AND APPLY SUCCESS");
    }

    /**
     * @author zhangxianchao @ description 返回养修预约申请结果 (DEMO FOR DMS_TEAM)
     * @date 2016年12月6日
     * @param repairApplyDto
     * @param headers
     * @param response
     * @throws IOException
     */
    @RequestMapping(value = { "/bookingOrder/v1/{appCode}/{dealerCode}/{bookingNo}" }, method = RequestMethod.PUT)
    @ResponseStatus(HttpStatus.CREATED)
    public void bookingOrderUpdate(@RequestBody RepairBookApplyDto repairApplyDto,
                                   @PathVariable("appCode") String appCode,
                                   @PathVariable("dealerCode") String dealerCode,
                                   @PathVariable("bookingNo") String bookingDate){
        logger.info("HAS ENTER repairBookingApply AND APPLY SUCCESS");
    }

    /**
     * @author zhangxianchao @ description 返回养修预约取消结果 (DEMO FOR DMS_TEAM)
     * @date 2016年12月6日
     * @param repairCancelDto
     * @param headers
     * @param response
     * @throws IOException
     */
    @RequestMapping(value = { "/bookingOrder/v1/{appCode}/{dealerCode}/{bookingNo}" }, method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void repairBookingCancel(@PathVariable("appCode") String appCode,
                                    @PathVariable("dealerCode") String dealerCode,
                                    @PathVariable("bookingNo") String bookingDate){
        logger.info("HAS ENTER repairBookingCancel AND CANCEL SUCCESS");
    }
}
