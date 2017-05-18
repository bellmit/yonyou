
/** 
*Copyright 2017 Yonyou Corporation Ltd. All Rights Reserved.
* This software is published under the terms of the Yonyou Software
* License version 1.0, a copy of which has been included with this
* distribution in the LICENSE.txt file.
*
* @Project Name : dms.vehicle
*
* @File name : TransferRepositoryController.java
*
* @Author : zhengcong
*
* @Date : 2017年3月27日
*
----------------------------------------------------------------------------------
*     Date       Who       Version     Comments
* 1. 2017年3月27日    zhengcong    1.0
*
*
*
*
----------------------------------------------------------------------------------
*/

package com.yonyou.dms.customer.controller.OwnerVehicle;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.util.UriComponentsBuilder;

import com.yonyou.dms.customer.service.OwnerVehicle.CusManagerBindService;
import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.framework.service.CommonNoService;
import com.yonyou.dms.framework.service.excel.ExcelExportColumn;
import com.yonyou.dms.framework.service.excel.ExcelGenerator;
import com.yonyou.dms.framework.util.FrameworkUtil;
import com.yonyou.dms.function.common.CommonConstants;
import com.yonyou.dms.function.exception.ServiceBizException;
import com.yonyou.f4.mvc.annotation.TxnConn;
import com.yonyou.f4.mvc.controller.BaseController;

/**
 * 
 *专属客户经理绑定controller
 * @author zhengcong
 * @date 2017年3月27日
 */

@Controller
@TxnConn
@RequestMapping("/ownerVehicle/cusManagerBind")
public class CusManagerBindController extends BaseController{
	
    @Autowired
    private CusManagerBindService  cmbservice;
    
    @Autowired
    private ExcelGenerator excelService;

    private  String[] allvin;
    /**
     * 根据条件查询
 * @author zhengcong
 * @date 2017年3月27日
     */
    @RequestMapping(value="/findAll",method = RequestMethod.GET)
    @ResponseBody
    public PageInfoDto queryAll(@RequestParam Map<String, String> queryParam) {
        PageInfoDto pageInfoDto  = cmbservice.queryAll(queryParam);
        return pageInfoDto;
    }
    
  
    /**
     * 
     * 批量设置专属客户经理
     * @author zhengcong
     * @date 2017年3月28日
     */

    
    @RequestMapping(value = "/setCusManager", method = RequestMethod.PUT)
    public ResponseEntity<Map<String, String>> editLocation(@RequestBody Map map,
                                                           UriComponentsBuilder uriCB) {

        String manager = map.get("cusManager").toString();
        this.allvin = map.get("vins").toString().split(";");
        System.out.println(this.allvin.toString());
        cmbservice.setCusManager(allvin,manager);
        
        MultiValueMap<String, String> headers = new HttpHeaders();
        headers.set("Location", uriCB.path("/ownerVehicle/cusManagerBind/setCusManager").buildAndExpand().toUriString());
        return new ResponseEntity<Map<String, String>>(headers, HttpStatus.CREATED);
    } 
    
    
    /**
     * 查询导出excel
     * @author zhengcong
     * @date 2017年3月29日
     */
    @RequestMapping(value="/export",method = RequestMethod.GET)
    public void excel(@RequestParam Map<String, String> queryParam, HttpServletRequest request,
                      HttpServletResponse response){
        List<Map> resultList =cmbservice.queryToExport(queryParam);
        Map<String, List<Map>> excelData = new HashMap<String, List<Map>>();
        excelData.put(FrameworkUtil.getLoginInfo().getDealerShortName()+"_专属客户经理绑定", resultList);
        List<ExcelExportColumn> exportColumnList = new ArrayList<ExcelExportColumn>();
        exportColumnList.add(new ExcelExportColumn("VIN","VIN"));
        exportColumnList.add(new ExcelExportColumn("OWNER_NO","车主编号"));
        exportColumnList.add(new ExcelExportColumn("OWNER_NAME","车主名称"));
        exportColumnList.add(new ExcelExportColumn("SERVICE_ADVISOR","专属客户经理"));
        exportColumnList.add(new ExcelExportColumn("SALES_DATE","销售日期"));
        exportColumnList.add(new ExcelExportColumn("MOBILE","手机"));
        exportColumnList.add(new ExcelExportColumn("PHONE","电话"));
        exportColumnList.add(new ExcelExportColumn("LAST_SERVICE_ADVISOR","上次维修客户经理"));
        exportColumnList.add(new ExcelExportColumn("BRAND_NAME","品牌"));
        exportColumnList.add(new ExcelExportColumn("SERIES_NAME","车系"));
        exportColumnList.add(new ExcelExportColumn("MODEL_NAME","车型"));
        excelService.generateExcelForDms(excelData, exportColumnList, FrameworkUtil.getLoginInfo().getDealerShortName()+"_专属客户经理绑定.xls", request, response);
    }


}
