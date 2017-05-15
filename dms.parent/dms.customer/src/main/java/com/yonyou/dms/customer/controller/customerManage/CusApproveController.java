
/** 
*Copyright 2017 Yonyou Corporation Ltd. All Rights Reserved.
* This software is published under the terms of the Yonyou Software
* License version 1.0, a copy of which has been included with this
* distribution in the LICENSE.txt file.
*
* @Project Name : dms.customer
*
* @File name : CusApproveController.java
*
* @Author : Administrator
*
* @Date : 2017年1月5日
*
----------------------------------------------------------------------------------
*     Date       Who       Version     Comments
* 1. 2017年1月5日    Administrator    1.0
*
*
*
*
----------------------------------------------------------------------------------
*/
	
package com.yonyou.dms.customer.controller.customerManage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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

import com.yonyou.dms.common.domains.DTO.basedata.PotentialCusDTO;
import com.yonyou.dms.customer.service.customerManage.CusApproveService;
import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.framework.service.excel.ExcelExportColumn;
import com.yonyou.dms.framework.service.excel.ExcelGenerator;
import com.yonyou.dms.framework.util.FrameworkUtil;
import com.yonyou.dms.function.common.CommonConstants;
import com.yonyou.f4.mvc.annotation.TxnConn;

/**
* TODO 客户休眠申请审批
* @author Administrator
* @date 2017年1月5日
*/
@Controller
@TxnConn
@RequestMapping("/customerManage/cusapprove")
public class CusApproveController {
  
    @Autowired
    private CusApproveService cusApproveService;
    @Autowired
    private ExcelGenerator      excelService;
    
    /**
     * 客户休眠申请查询
     * 
     * @author zhanshiwei
     * @date 2016年9月1日
     * @param queryParam
     * @return
     */

    @RequestMapping(method = RequestMethod.GET)
    @ResponseBody
    public PageInfoDto queryPotentialCusInfo(@RequestParam Map<String, String> queryParam) {
        System.out.println(queryParam);
        PageInfoDto pageInfoDto = cusApproveService.queryApproveCusInfo(queryParam);
        return pageInfoDto;
    }
    
    /**
     * 根据ID查询休眠申请客户信息
     * 
     * @author zhanshiwei
     * @date 2016年9月1日
     * @param queryParam
     * @return
     */

    @SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> queryApproveInfo(@PathVariable(value = "id") String id) {
        List<Map> result = cusApproveService.queryApproveCusInfoByid(id);
        return result.get(0);
    }
    
    /**
     * 休眠客户审批
     * 
     * @author zhanshiwei
     * @date 2016年9月7日
     * @param id
     * @param visitDto
     * @param uriCB
     * @return
     */

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public ResponseEntity<PotentialCusDTO> modifyPotentialCusInfo(@PathVariable("id") String id,
                                                                      @RequestBody  PotentialCusDTO potentialCusDto,
                                                                      UriComponentsBuilder uriCB) {
        cusApproveService.modifyPotentialCusInfo(id, potentialCusDto);
        MultiValueMap<String, String> headers = new HttpHeaders();
        headers.set("Location", uriCB.path("/customerManage/cusapprove/{id}").buildAndExpand(id).toUriString());
        return new ResponseEntity<PotentialCusDTO>(headers, HttpStatus.CREATED);
    }
    

    /**
     * 潜客信息导出
     * 
     * @author LGQ
     * @date 2016年9月11日
     * @param queryParam
     * @param request
     * @param response
     * @throws Exception
     */

    @SuppressWarnings("rawtypes")
    @RequestMapping(value = "/export/excel", method = RequestMethod.GET)
    public void exportPotentialCus(@RequestParam Map<String, String> queryParam, HttpServletRequest request,
                                   HttpServletResponse response) throws Exception {
        List<Map> resultList = cusApproveService.queryPotentialCusforExport(queryParam);
        Map<String, List<Map>> excelData = new HashMap<String, List<Map>>();
        excelData.put("客户休眠申请审批", resultList);
        List<ExcelExportColumn> exportColumnList = new ArrayList<ExcelExportColumn>();
        exportColumnList.add(new ExcelExportColumn("CUSTOMER_NO", "客户编号"));
        exportColumnList.add(new ExcelExportColumn("CUSTOMER_NAME", "客户名称"));
        exportColumnList.add(new ExcelExportColumn("CUSTOMER_TYPE", "客户类型"));
        exportColumnList.add(new ExcelExportColumn("FOUND_DATE", "建档时间",CommonConstants.FULL_DATE_TIME_FORMAT));
        exportColumnList.add(new ExcelExportColumn("CUS_SOURCE", "客户来源"));
        exportColumnList.add(new ExcelExportColumn("INTENT_LEVEL", "意向级别"));
        exportColumnList.add(new ExcelExportColumn("CONTACTOR_PHONE", "联系电话"));
        exportColumnList.add(new ExcelExportColumn("CONTACTOR_MOBILE", "联系手机"));
        exportColumnList.add(new ExcelExportColumn("USER_NAME", "销售顾问"));
        exportColumnList.add(new ExcelExportColumn("DELAY_CONSULTANT", "延时再分配"));
        exportColumnList.add(new ExcelExportColumn("CONSULTANT_TIME", "再分配时间",CommonConstants.FULL_DATE_TIME_FORMAT));       
        exportColumnList.add(new ExcelExportColumn("CONTACTOR_NAME", "联系人姓名"));
        exportColumnList.add(new ExcelExportColumn("POSITION_NAME", "职务名称"));
        exportColumnList.add(new ExcelExportColumn("PHONE", "默认联系人电话"));
        exportColumnList.add(new ExcelExportColumn("MOBILE", "默认联系人手机"));
        exportColumnList.add(new ExcelExportColumn("REMARK", "备注"));
        
        exportColumnList.add(new ExcelExportColumn("BRAND_NAME", "意向品牌"));
        exportColumnList.add(new ExcelExportColumn("SERIES_NAME", "意向车系"));
        exportColumnList.add(new ExcelExportColumn("MODEL_NAME", "意向车型"));
        exportColumnList.add(new ExcelExportColumn("CONFIG_NAME", "意向配置"));
        exportColumnList.add(new ExcelExportColumn("COLOR_NAME", "意向颜色"));
        exportColumnList.add(new ExcelExportColumn("IS_TO_SHOP", "是否到店"));
        exportColumnList.add(new ExcelExportColumn("TIME_TO_SHOP", "首次到店时间",CommonConstants.FULL_DATE_TIME_FORMAT));
        exportColumnList.add(new ExcelExportColumn("LAST_ARRIVE_TIME", "最后一次进店时间",CommonConstants.FULL_DATE_TIME_FORMAT));       
        exportColumnList.add(new ExcelExportColumn("LAST_USER_NAME", "前销售顾问"));
        exportColumnList.add(new ExcelExportColumn("AUDIT_STATUS", "审批状态 "));
        exportColumnList.add(new ExcelExportColumn("VALIDITY_BEGIN_DATE", "客户有效开始日期",CommonConstants.FULL_DATE_TIME_FORMAT));
        
        
        
        
        // 生成excel 文件
        excelService.generateExcelForDms(excelData, exportColumnList,  FrameworkUtil.getLoginInfo().getDealerShortName()+"_客户休眠申请审批信息.xls", request, response);

    }


}
