/** 
*Copyright 2017 Yonyou Corporation Ltd. All Rights Reserved.
* This software is published under the terms of the Yonyou Software
* License version 1.0, a copy of which has been included with this
* distribution in the LICENSE.txt file.
*
* @Author : zhengcong
*
* @Date : 2017年5月9日
*
----------------------------------------------------------------------------------
*     Date       Who       Version     Comments
* 1. 2017年5月9日    zhengcong    1.0
*
*
*
*
----------------------------------------------------------------------------------
*/
package com.yonyou.dms.repair.controller.maintenancePackage;


import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.repair.service.maintenancePackage.MaintenancePackageService;
import com.yonyou.f4.mvc.annotation.TxnConn;
import com.yonyou.f4.mvc.controller.BaseController;

/**
 * 
 *保养套餐管理controller
 * @author zhengcong
 * @date 2017年5月9日
 */

@Controller
@TxnConn
@RequestMapping("/maintenancePackage/MaintenancePackage")
public class MaintenancePackageController extends BaseController{
	
    @Autowired
    private MaintenancePackageService  mpservice;
    
    /**
     * 根据条件查询
 * @author zhengcong
 * @date 2017年5月9日
     */
    @RequestMapping(value="/query",method = RequestMethod.GET)
    @ResponseBody
    public PageInfoDto query(@RequestParam Map<String, String> queryParam) {
        PageInfoDto pageInfoDto  = mpservice.query(queryParam);
        return pageInfoDto;
    }
    
    /**
     * 查询项目明细信息
     * @author zhengcong
     * @date 2017年5月9日
     */      
    @RequestMapping(value="/labourDetail/{PACKAGE_CODE}",method = RequestMethod.GET)
    @ResponseBody
    public PageInfoDto queryLabourDtail(@PathVariable(value = "PACKAGE_CODE") String PACKAGE_CODE) {
        PageInfoDto pageInfoDto  = mpservice.queryLabourDtail(PACKAGE_CODE);
        return pageInfoDto;
    } 
    
    /**
     * 查询配件明细信息
     * @author zhengcong
     * @date 2017年5月9日
     */      
    @RequestMapping(value="/partDetail/{PACKAGE_CODE}",method = RequestMethod.GET)
    @ResponseBody
    public PageInfoDto queryPartDtail(@PathVariable(value = "PACKAGE_CODE") String PACKAGE_CODE) {
        PageInfoDto pageInfoDto  = mpservice.queryPartDtail(PACKAGE_CODE);
        return pageInfoDto;
    } 
 

}
