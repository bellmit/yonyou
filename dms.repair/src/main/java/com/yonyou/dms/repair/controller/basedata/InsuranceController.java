
/** 
 *Copyright 2016 Yonyou Corporation Ltd. All Rights Reserved.
 * This software is published under the terms of the Yonyou Software
 * License version 1.0, a copy of which has been included with this
 * distribution in the LICENSE.txt file.
 *
 * @Project Name : dms.repair
 *
 * @File name : Insurance.java
 *
 * @Author : zhongshiwei
 *
 * @Date : 2016年7月1日
 *
----------------------------------------------------------------------------------
 *     Date       Who       Version     Comments
 * 1. 2016年7月1日    zhongshiwei    1.0
 *
 *
 *
 *
----------------------------------------------------------------------------------
 */

package com.yonyou.dms.repair.controller.basedata;

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
import com.yonyou.dms.function.exception.ServiceBizException;
import com.yonyou.dms.repair.domains.DTO.basedata.InsuranceDTO;
import com.yonyou.dms.repair.domains.PO.basedata.InsurancePo;
import com.yonyou.dms.repair.service.basedata.InsuranceService;
import com.yonyou.f4.mvc.annotation.TxnConn;
import com.yonyou.f4.mvc.controller.BaseController;

/**
 * 保险公司信息
 * TODO description
 * @author zhongshiwei
 * @date 2016年7月1日
 */
@Controller
@TxnConn
@RequestMapping("/basedata/insuranceCo")
public class InsuranceController extends BaseController{

    @Autowired
    private InsuranceService insuranceService;

    /**
     * 根据查询条件返回对应的用户数据
     * @author zhongshiwei
     * @date 2016年6月29日
     * @param insuranceSQL 查询条件
     * @return 查询结果
     */
    @RequestMapping(method = RequestMethod.GET)
    @ResponseBody
    public PageInfoDto insuranceSql(@RequestParam Map<String, String> insuranceSQL) throws ServiceBizException{
        PageInfoDto pageInfoDto = insuranceService.InsuranceSQL(insuranceSQL);
        return pageInfoDto;
    }        

    /**
     * 根据id查找
     * @author zhongshiwei
     * @date 2016年7月1日
     * @param id
     * @throws ServiceBizException 
     */
    @RequestMapping(value="/{id}",method=RequestMethod.GET)
    @ResponseBody
    public  Map<String,Object>  findById(@PathVariable String id) throws ServiceBizException{
    	InsurancePo wtpo= insuranceService.findByCode(id);
    	return wtpo.toMap();
    }

    /**
     * 保险公司下拉框加载
     * @author ZhengHe
     * @date 2016年8月10日
     * @return
     */
    @RequestMapping(value="/insurance/dicts",method = RequestMethod.GET)
    @ResponseBody
    public List<Map> selectEmployees() {
        List<Map> lplist = insuranceService.selectInsurance();
        return lplist;
    }

}
