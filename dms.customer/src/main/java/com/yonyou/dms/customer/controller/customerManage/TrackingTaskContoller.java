
/** 
*Copyright 2016 Yonyou Corporation Ltd. All Rights Reserved.
* This software is published under the terms of the Yonyou Software
* License version 1.0, a copy of which has been included with this
* distribution in the LICENSE.txt file.
*
* @Project Name : dms.customer
*
* @File name : TrackingTaskContoller.java
*
* @Author : zhanshiwei
*
* @Date : 2016年9月6日
*
----------------------------------------------------------------------------------
*     Date       Who       Version     Comments
* 1. 2016年9月6日    zhanshiwei    1.0
*
*
*
*
----------------------------------------------------------------------------------
*/

package com.yonyou.dms.customer.controller.customerManage;

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
import org.springframework.web.util.UriComponentsBuilder;

import com.yonyou.dms.common.domains.PO.basedata.TrackingTaskPO;
import com.yonyou.dms.customer.domains.DTO.customerManage.TrackingTaskDTO;
import com.yonyou.dms.customer.service.customerManage.TrackingTaskService;
import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.function.exception.ServiceBizException;
import com.yonyou.f4.mvc.annotation.TxnConn;
import com.yonyou.f4.mvc.controller.BaseController;

/**
 * 跟进任务定义
 * 
 * @author zhanshiwei
 * @date 2016年9月6日
 */
@Controller
@TxnConn
@RequestMapping("/customerManage/trackingtask")
public class TrackingTaskContoller extends BaseController {

    @Autowired
    private TrackingTaskService trackingtaskservice;

    
    /**
    * 跟进任务定义 查询潜客
    * @author zhanshiwei
    * @date 2016年9月7日
    * @param queryParam
    * @return
    */
    	
    @RequestMapping(method = RequestMethod.GET)
    @ResponseBody
    public PageInfoDto queryTrackingTask(@RequestParam Map<String, String> queryParam) {
        PageInfoDto pageInfoDto = trackingtaskservice.queryTrackingTask(queryParam);
        return pageInfoDto;
    }
    
    /**
    * 根据ID 查询 跟进任务定义
    * @author zhanshiwei
    * @date 2016年9月7日
    * @param id 跟进任务定义ID
    * @return
    */
    	
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> queryTrackingTaskByid(@PathVariable(value = "id") Long id) {
        TrackingTaskPO trackTaskPo = trackingtaskservice.queryTrackingTaskByid(id);
        return trackTaskPo.toMap();
    }

    
    /**
    * 跟进任务定义 新增
    * @author zhanshiwei
    * @date 2016年9月7日
    * @param trackTaskDto
    * @param uriCB
    * @return
    */
    	
    @RequestMapping(method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<TrackingTaskDTO> addTrackingTask(@RequestBody @Valid TrackingTaskDTO trackTaskDto,
                                                           UriComponentsBuilder uriCB) {
        Long id = trackingtaskservice.addTrackingTask(trackTaskDto);
        MultiValueMap<String, String> headers = new HttpHeaders();
        headers.set("Location", uriCB.path("/customerManage/trackingtask").buildAndExpand(id).toUriString());
        return new ResponseEntity<TrackingTaskDTO>(trackTaskDto, headers, HttpStatus.CREATED);
    }

    
    /**
    * 跟进任务定义 修改
    * @author zhanshiwei
    * @date 2016年9月7日
    * @param id
    * @param trackTaskDto
    * @param uriCB
    * @return
    */
    	
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public ResponseEntity<TrackingTaskDTO> modifyTrackingTask(@PathVariable("id") Long id,
                                                              @RequestBody TrackingTaskDTO trackTaskDto,
                                                              UriComponentsBuilder uriCB) {
        trackingtaskservice.modifyTrackingTask(id, trackTaskDto);
        MultiValueMap<String, String> headers = new HttpHeaders();
        headers.set("Location", uriCB.path("/customerManage/trackingtask/{id}").buildAndExpand(id).toUriString());
        return new ResponseEntity<TrackingTaskDTO>(headers, HttpStatus.CREATED);
    }
        
    /**
    * 根据级别查询间隔天数
    * @author zhanshiwei
    * @date 2016年9月20日
    * @param intentLevel
    * @return
    */
    	
    @RequestMapping(value = "intervalDays/{intentLevel}", method = RequestMethod.GET)
    @ResponseBody
    public Integer queryTrackingTaskByIntentLevel(@PathVariable(value = "intentLevel") Integer intentLevel) {
        Map<String, Object> resultMap = trackingtaskservice.queryTrackingTaskByIntentLevel(intentLevel);
        return resultMap!=null?Integer.parseInt(resultMap.get("interval_days").toString()):0; 
    }
    
    /**
     * 根据ID 删除用户信息
     * @author wangxin
     * @date 2016年12月23日
     * @param taskID
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    @ResponseBody
    public void deleteTrackingTack(@PathVariable("id") Long id,UriComponentsBuilder uriCB) throws ServiceBizException{
    	trackingtaskservice.deleteTrackingTaskById(id);
    }
    
}

