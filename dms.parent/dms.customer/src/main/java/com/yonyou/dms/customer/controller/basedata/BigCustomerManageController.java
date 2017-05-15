
/** 
*Copyright 2017 Yonyou Corporation Ltd. All Rights Reserved.
* This software is published under the terms of the Yonyou Software
* License version 1.0, a copy of which has been included with this
* distribution in the LICENSE.txt file.
*
* @Project Name : dms.customer
*
* @File name : BigCustomerManageController.java
*
* @Author : Administrator
*
* @Date : 2017年1月12日
*
----------------------------------------------------------------------------------
*     Date       Who       Version     Comments
* 1. 2017年1月12日    Administrator    1.0
*
*
*
*
----------------------------------------------------------------------------------
*/
	
package com.yonyou.dms.customer.controller.basedata;

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

import com.yonyou.dms.common.domains.DTO.basedata.BigCustomerDTO;
import com.yonyou.dms.customer.service.customerManage.BigCustomerManageService;
import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.framework.domain.LoginInfoDto;
import com.yonyou.dms.framework.service.CommonNoService;
import com.yonyou.dms.framework.service.FileStoreService;
import com.yonyou.dms.framework.service.excel.ExcelExportColumn;
import com.yonyou.dms.framework.service.excel.ExcelGenerator;
import com.yonyou.dms.framework.util.FrameworkUtil;
import com.yonyou.dms.framework.util.bean.ApplicationContextHelper;
import com.yonyou.dms.function.common.CommonConstants;
import com.yonyou.dms.gacfca.DSO0401;
import com.yonyou.dms.gacfca.DSO0401Impl;
import com.yonyou.f4.mvc.annotation.TxnConn;

/**
* TODO description
* @author Administrator
* @date 2017年1月12日
*/
@Controller
@TxnConn
@RequestMapping("/BigCustomer/manage")
public class BigCustomerManageController {
    @Autowired
    private BigCustomerManageService bigcustomermanage;
    @Autowired
    private CommonNoService commonNoService;
    @Autowired
    private ExcelGenerator excelService; 
    @Autowired
    FileStoreService fileStoreService;
 
    
    //审批单查询
    @RequestMapping(method = RequestMethod.GET)
    @ResponseBody
    public PageInfoDto queryBigCustomerWs(@RequestParam Map<String, String> queryParam) {
        PageInfoDto pageInfoDto = bigcustomermanage.queryBigCustomerWs(queryParam);
        return pageInfoDto;
    }
    /**
     * 检查潜客电话
     * 
     * @author LGQ
     * @date 2016年2月20日
     * @param queryParam
     * @return
     */

    @RequestMapping(value = "/Check/Status/{id}", method = RequestMethod.GET)
    @ResponseBody
    public String CheckStatus(@PathVariable(value = "id") String id) {
        String pageInfoDto = bigcustomermanage.CheckStatus(id); 
        return pageInfoDto;
    }
    
    /**
     * 检查潜客电话
     * 
     * @author LGQ
     * @date 2016年2月20日
     * @param queryParam
     * @return
     */

    @RequestMapping(value = "/Check/Status1/{id}", method = RequestMethod.GET)
    @ResponseBody
    public String CheckStatus1(@PathVariable(value = "id") String id) {
        String pageInfoDto = bigcustomermanage.CheckStatus1(id); 
        return pageInfoDto;
    }
    //审批车辆
    @RequestMapping(value = "/car/{id}",method = RequestMethod.GET)
    @ResponseBody
    public PageInfoDto queryBigCustomerWsCar(@PathVariable(value = "id") String id) {
        PageInfoDto pageInfoDto = bigcustomermanage.queryBigCustomerWsCar(id);
        return pageInfoDto;
    }
    //审查历史
    @RequestMapping(value = " {WS_NO}/history",method = RequestMethod.GET)
    @ResponseBody
    public PageInfoDto queryBigCustomerWsCarbyWsNoHis(@RequestParam Map<String, String> queryParam,@PathVariable("WS_NO") String wsNo) {
        PageInfoDto pageInfoDto = bigcustomermanage.queryBigCustomerWsCarbyWsNoHis(queryParam,wsNo);
        return pageInfoDto;
    }
    //修改查车
    @RequestMapping(value = "{WS_NO}/car",method = RequestMethod.GET)
    @ResponseBody
    public PageInfoDto queryBigCustomerWsCarbyWsNo(@RequestParam Map<String, String> queryParam,@PathVariable("WS_NO") String wsNo) {
        PageInfoDto pageInfoDto = bigcustomermanage.queryBigCustomerWsCarbyWsNo(queryParam,wsNo);
        return pageInfoDto;
    }
   //修改查报备单
    @RequestMapping(value = "{WS_NO}/in",method = RequestMethod.GET)
    @ResponseBody
    public Map queryOwnerCusByEmployeeNo(@PathVariable(value = "WS_NO") String wsNo) {       
        Map pp=new HashMap();
        List<Map> addressList = bigcustomermanage.queryOwnerCusBywsNo(wsNo);
        if (addressList.size()>0){
            pp.putAll(addressList.get(0));  
        }
        return pp;
    }
    //大客户查询
    @RequestMapping(value = "/bigcustomer", method = RequestMethod.GET)
    @ResponseBody
    public PageInfoDto queryBigCustomer(@RequestParam Map<String, String> queryParam) {
        PageInfoDto pageInfoDto = bigcustomermanage.queryBigCustomer(queryParam);
        return pageInfoDto;
    }
   
    //车型查询
    @RequestMapping(value = "/product/{wsAppType}/search" , method = RequestMethod.GET)
    @ResponseBody
    public PageInfoDto findAllProduct(@RequestParam Map<String, String> queryParam,@PathVariable("wsAppType") String wsAppType){
        return bigcustomermanage.findAllProduct(queryParam,wsAppType);
    }
    
  
    //新增保存
    @RequestMapping(method = RequestMethod.POST)
    @ResponseBody
    public  ResponseEntity<Map<String, Object>> addOwnerCusInfo(@RequestBody  BigCustomerDTO bigCustomerDto,
                                                                UriComponentsBuilder uriCB) {

 /*       System.out.println("2222222222222");
        System.out.println(importFile);
        String fileId = fileStoreService.writeFile(importFile);
        //创建文件DTO 对象 
        FileUploadInfoDto fileUploadDto = new FileUploadInfoDto();
        fileUploadDto.setFileId(fileId);
        fileUploadDto.setFileName(importFile.getOriginalFilename());
        fileUploadDto.setFileSize((int)importFile.getSize());
        fileUploadDto.setIsValid(DictCodeConstants.STATUS_NOT_VALID);
        
        Long fileUploadInfoId = fileStoreService.insertFileUploadInfo(fileUploadDto);
        Map<String,String> returnMap = new HashMap<>();
        returnMap.put("fileUploadId", fileUploadInfoId.toString());*/
        
        String WsNo = bigcustomermanage.addBiGCusInfo(bigCustomerDto,commonNoService.getSystemOrderNo(CommonConstants.SRV_PSSPDH));
   
        //commonNoService.getSystemOrderNo(CommonConstants.SRV_PSSPDH
     
        MultiValueMap<String, String> headers = new HttpHeaders();
        headers.set("Location", uriCB.path("/BigCustomer/manage").buildAndExpand(WsNo).toUriString());
        return new ResponseEntity<Map<String, Object>>(headers, HttpStatus.CREATED);
    }
    
    //新增保存
    @RequestMapping(value = "/{WS_NO}/save",method = RequestMethod.PUT)
    @ResponseBody
    public  ResponseEntity<Map<String, Object>> modifyOwnerCusInfo(@RequestBody  BigCustomerDTO bigCustomerDto,
                                                                UriComponentsBuilder uriCB,@PathVariable("WS_NO") String wsNo) {
        LoginInfoDto loginInfo = ApplicationContextHelper.getBeanByType(LoginInfoDto.class);
   //     String WsNo = bigcustomermanage.addBiGCusInfo(bigCustomerDto,
   //                                                       commonNoService.getSystemOrderNo(CommonConstants.SRV_PSSPDH));
        String WsNo = bigcustomermanage.modifyBiGCusInfo(bigCustomerDto,wsNo);
        MultiValueMap<String, String> headers = new HttpHeaders();
        headers.set("Location", uriCB.path("/BigCustomer/manage").buildAndExpand(WsNo).toUriString());
        return new ResponseEntity<Map<String, Object>>(headers, HttpStatus.CREATED);
    }
    
    //上报
    @RequestMapping(value = "/maintian/{WS_NO}",method = RequestMethod.PUT)
    @ResponseBody
    public  ResponseEntity<Map<String, Object>> bigCustomerDMSToDCS(@RequestBody  BigCustomerDTO bigCustomerDto,
                                                                UriComponentsBuilder uriCB,@PathVariable("WS_NO") String wsNo) {
        bigcustomermanage.uploanBigCustomer(wsNo);
        MultiValueMap<String, String> headers = new HttpHeaders();
        headers.set("Location", uriCB.path("/BigCustomer/manage").buildAndExpand(wsNo).toUriString());
        return new ResponseEntity<Map<String, Object>>(headers, HttpStatus.CREATED);
    }
    //校验
    @RequestMapping(value = "/checkdata", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> BigCusSaveBeforeEvent(@RequestParam Map<String, String> queryParam) {
        Map<String, Object> result = bigcustomermanage.employeSaveBeforeEvent(queryParam);
        System.out.println(result);
        if(result!=null&&!result.isEmpty()){
            result.put("success", "true");     
        }else{
            result=new HashMap<String, Object>();
            result.put("success", "false"); 
        }
        return result;
    }
    
    
    /**
     * 检查跟进记录
     * 
     * @author LGQ
     * @date 2016年2月20日
     * @param queryParam
     * @return
     */

    @RequestMapping(value = "/Check/{cusNo}/CheckGenJin", method = RequestMethod.GET)
    @ResponseBody
    public String CheckGenJin(@PathVariable(value = "cusNo") String cusNo) {
        String pageInfoDto = bigcustomermanage.CheckGenJin(cusNo); 
        return pageInfoDto;
    }
    
    /**
     * 申请数量
     * 
     * @author LGQ
     * @date 2016年2月20日
     * @param queryParam
     * @return
     */

    @RequestMapping(value = "/total/{wsNo}/totalCarCount", method = RequestMethod.GET)
    @ResponseBody
    public int totalCarCount(@PathVariable(value = "wsNo") String wsNo) {
        int pageInfoDto = bigcustomermanage.totalCarCount(wsNo); 
        return pageInfoDto;
    }
    
    /**
     * 检查潜客电话
     * 
     * @author LGQ
     * @date 2016年2月20日
     * @param queryParam
     * @return
     */

    @RequestMapping(value = "/Check/{wsT}/CheckData2/{cusK}", method = RequestMethod.GET)
    @ResponseBody
    public String CheckData2(@PathVariable(value = "wsT") String wst,@PathVariable(value = "cusK") String cusk) {
        String pageInfoDto = bigcustomermanage.CheckData2(wst,cusk); 
        return pageInfoDto;
    }
    @RequestMapping(value="/export/excel",method = RequestMethod.GET)
    public void excel(@RequestParam Map<String, String> queryParam, HttpServletRequest request,
                      HttpServletResponse response){
        List<Map> resultList =bigcustomermanage.queryRetainCustrackforExport(queryParam);
        Map<String, List<Map>> excelData = new HashMap<String, List<Map>>();
        excelData.put(FrameworkUtil.getLoginInfo().getDealerShortName()+"_大客户报备", resultList);
        List<ExcelExportColumn> exportColumnList = new ArrayList<ExcelExportColumn>();
        exportColumnList.add(new ExcelExportColumn("WS_NO","报备单号"));
        exportColumnList.add(new ExcelExportColumn("WS_STATUS","报备状态"));
        exportColumnList.add(new ExcelExportColumn("AUDITING_DATE","审核日期"));
        exportColumnList.add(new ExcelExportColumn("WS_APP_TYPE","政策类别"));
        exportColumnList.add(new ExcelExportColumn("CUSTOMER_KIND","客户类别"));
        exportColumnList.add(new ExcelExportColumn("WSTHREE_TYPE","客户类型细分"));
        exportColumnList.add(new ExcelExportColumn("WS_AUDITING_REMARK","审核备注"));
        exportColumnList.add(new ExcelExportColumn("CUSTOMER_NO","客户编号"));
        exportColumnList.add(new ExcelExportColumn("CUSTOMER_NAME","客户名称"));
        exportColumnList.add(new ExcelExportColumn("CONTACTOR_NAME","客户名称"));
        exportColumnList.add(new ExcelExportColumn("PHONE","联系电话"));
        exportColumnList.add(new ExcelExportColumn("MOBILE","联系手机"));
        exportColumnList.add(new ExcelExportColumn("DLR_PRINCIPAL_PHONE","负责人电话"));
        exportColumnList.add(new ExcelExportColumn("ZIP_CODE","邮编"));
        exportColumnList.add(new ExcelExportColumn("ADDRESS","地址"));
        exportColumnList.add(new ExcelExportColumn("FIRST_SUBMIT_TIME","首次上报日期"));
     
        excelService.generateExcel(excelData, exportColumnList, FrameworkUtil.getLoginInfo().getDealerShortName()+"_大客户报备.xls", request, response);
    }
}
